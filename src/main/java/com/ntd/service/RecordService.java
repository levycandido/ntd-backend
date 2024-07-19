package com.ntd.service;

import com.ntd.ObjectMapperUtils;
import com.ntd.entity.Operation;
import com.ntd.entity.Record;
import com.ntd.entity.Type;
import com.ntd.entity.User;
import com.ntd.repository.RecordRepository;
import com.ntd.service.dao.RecordDTO;
import com.ntd.service.exception.InsufficientBalanceException;
import com.ntd.service.strategy.OperationStrategy;
import com.ntd.service.strategy.OperationStrategyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.ntd.service.utils.RandomStringGenerator.createRandomString;

@Service
public class RecordService {

    private final RecordRepository recordRepository;
    private final UserService userService;
    private final OperationService operationService;

    @Autowired
    public RecordService(RecordRepository recordRepository,
                         UserService userService,
                         OperationService operationService) {
        this.recordRepository = recordRepository;
        this.userService = userService;
        this.operationService = operationService;
    }

    @Transactional
    public RecordDTO createRecord(RecordDTO recordDTO) {
        String resultString = null;
        Double result = null;

        User user = userService.findByEmail(recordDTO.getUserId());

        Operation operation = operationService
                .findByType(recordDTO.getOperation().getType().getType());

        Double cost = operation.getCost();

        if (isBalanceSufficient(user.getBalance(), cost)) {
            throw new InsufficientBalanceException("User balance is not sufficient to create this record.");
        }

        if (operation.getType() == Type.RANDOM_STRING) {
            resultString = createRandomString(10);
        } else {
            result = getResult(recordDTO);
        }

        // Update user balance and save user
        double newUserBalance = user.getBalance() - cost;
        user.setBalance(newUserBalance);

        User userUpdated = userService.save(user);

        Record record = getRecord(recordDTO, cost, newUserBalance, operation, userUpdated, resultString, result);

        return ObjectMapperUtils.map(this.save(record), RecordDTO.class);
    }

    private static Double getResult(RecordDTO recordDTO) {
        // strategy based on the operation type
        OperationStrategy strategy = OperationStrategyFactory
                .getStrategy(Type.valueOf(recordDTO.getOperation().getType().getType().toUpperCase()));

        return strategy.execute(recordDTO, recordDTO.getFirstValue(), recordDTO.getSecValue());
    }

    private static Record getRecord(RecordDTO recordDTO, Double cost, double newUserBalance, Operation operation, User userUpdated, String resultString, Double result) {
        Record record = new Record();
        record.setAmount(cost);
        record.setUserBalance(newUserBalance); //TODO: UI should Get this field from user class
        record.setOperation(operation);
        record.setUser(userUpdated);
        record.setDate(LocalDateTime.now());

        completeRecord(recordDTO, resultString, record, result);
        return record;
    }

    private static void completeRecord(RecordDTO recordDTO, String resultString, Record record, Double result) {
        if (resultString != null) {
            record.setOperationResponse(resultString);
        } else {
            record.setOperationResponse(result.toString());
            record.setFirstValue(recordDTO.getFirstValue());
            record.setSecondValue(recordDTO.getSecValue());
        }
    }

    private boolean isBalanceSufficient(double currencyBalance, double operationCost) {
        return !(currencyBalance >= operationCost);
    }

    @Transactional
    public RecordDTO updateRecord(Long id, RecordDTO recordDTO) {
        recordDTO.setId(id);
        Record record = ObjectMapperUtils.map(recordDTO, Record.class);
        return ObjectMapperUtils.map(recordRepository.save(record), RecordDTO.class);
    }

    @Transactional(readOnly = true)
    public List<RecordDTO> getAllRecords() {
        List<Record> bookings = recordRepository.findAll();
        if (bookings.isEmpty()) {
            throw new IllegalArgumentException("No records found");
        }

        return ObjectMapperUtils.mapAll(bookings, RecordDTO.class);
    }

    @Transactional
    public RecordDTO getRecord(Long id) {
        Record record = recordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));
        return ObjectMapperUtils.map(record, RecordDTO.class);
    }

    public Record save(Record record) {
        return recordRepository.save(record);
    }

    public Page<RecordDTO> findByUserId(String userId, Pageable pageable) {
        Page<Record> recordPage = recordRepository.findByUserId(userId, pageable);
        return recordPage.map(this::convertToDto);
    }

    private RecordDTO convertToDto(Record record) {
        return ObjectMapperUtils.map(record, RecordDTO.class);
    }

}
