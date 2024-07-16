package com.ntd.service;

import com.ntd.ObjectMapperUtils;
import com.ntd.entity.Operation;
import com.ntd.entity.Record;
import com.ntd.entity.Type;
import com.ntd.entity.User;
import com.ntd.repository.RecordRepository;
import com.ntd.service.dao.RecordDTO;
import com.ntd.service.dao.UserDTO;
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

@Service
public class RecordService {

    private final RecordRepository recordRepository;
    private final UserService userService;
    private final OperationService operationService;

    @Autowired
    public RecordService(RecordRepository recordRepository, UserService userService, OperationService operationService) {
        this.recordRepository = recordRepository;
        this.userService = userService;
        this.operationService = operationService;
    }

    @Transactional
    public RecordDTO createRecord(RecordDTO recordDTO) {

        UserDTO userDTO = userService.findByEmail(recordDTO.getUserId());

        Operation operation = operationService
                .findByType(recordDTO.getOperation().toLowerCase());

        Double cost = operation.getCost();
        if (isBalanceSufficient(recordDTO.getUserId(), cost)) {
            throw new InsufficientBalanceException("User balance is not sufficient to create this record.");
        }

        // Apply the strategy based on the operation type
        OperationStrategy strategy = OperationStrategyFactory.getStrategy(Type.valueOf(recordDTO.getOperation().toUpperCase()));
        Double result = strategy.execute(recordDTO, recordDTO.getFirstValue(), recordDTO.getSecValue());

        // Update user balance and save user
        User user = userService.save(ObjectMapperUtils.map(userDTO, User.class));

        Record record = new Record();
        record.setOperationResponse(result);
        record.setAmount(cost);
        record.setDate(LocalDateTime.now());
        record.setOperation(operation);
        record.setFirstValue(recordDTO.getFirstValue());
        record.setSecondValue(recordDTO.getSecValue());
        record.setUserBalance(recordDTO.getUserBalance() - operation.getCost());
        record.setUser(user);

        Record recordSaved = recordRepository
                .save(record);
        if (recordSaved.getUser() != null) {
           recordSaved.getUser().setPassword(null);
        }

        return ObjectMapperUtils.map(recordSaved, RecordDTO.class);
    }

   private boolean isBalanceSufficient(String username, double operationCost) {
        User user = getUser(username);
        return !(user.getBalance() >= operationCost);
    }

    private User getUser(String username) {
        return ObjectMapperUtils.map(userService.findByEmail(username), User.class);
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

    public void save(Record record) {
        recordRepository.save(record);
    }

    public Page<RecordDTO> findByUserId(String userId, Pageable pageable) {
        Page<Record> recordPage = recordRepository.findByUserId(userId, pageable);
        return recordPage.map(this::convertToDto);
    }

    private RecordDTO convertToDto(Record record) {
      return ObjectMapperUtils.map(record, RecordDTO.class);
    }

}
