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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecordServiceTest {

    @Mock
    private RecordRepository recordRepository;

    @Mock
    private UserService userService;

    @Mock
    private OperationService operationService;

    @InjectMocks
    private RecordService recordService;

    private RecordDTO recordDTO;
    private User user;
    private Operation operation;

    @BeforeEach
    public void setUp() {
        recordDTO = new RecordDTO();
        recordDTO.setUserId("testUser");
        recordDTO.setFirstValue(100.0);
        recordDTO.setSecValue(50.0);
        recordDTO.setUserBalance(200.0);

        user = new User();
        user.setUsername("testUser");
        user.setBalance(200.0);
    }

    @Test
    public void testCreateRecord_success_addition() {
        recordDTO.setOperation("addition");
        operation = new Operation(1L, Type.ADDITION, 50.0);

        when(userService.findByEmail("testUser"))
                .thenReturn(ObjectMapperUtils.map(user, UserDTO.class));
        when(operationService.findByType("addition")).thenReturn(operation);
        when(recordRepository.save(any(Record.class))).thenReturn(new Record());

        RecordDTO result = recordService.createRecord(recordDTO);

        assertNotNull(result);
        verify(userService, times(1)).save(any(User.class));
        verify(recordRepository, times(1)).save(any(Record.class));
    }

    @Test
    public void testCreateRecord_success_subtraction() {
        recordDTO.setOperation("subtraction");
        operation = new Operation(1L, Type.SUBTRACTION, 50.0);

        when(userService.findByEmail("testUser")).thenReturn(ObjectMapperUtils.map(user, UserDTO.class));
        when(operationService.findByType("subtraction")).thenReturn(operation);
        when(recordRepository.save(any(Record.class))).thenReturn(new Record());

        RecordDTO result = recordService.createRecord(recordDTO);

        assertNotNull(result);
        verify(userService, times(1)).save(any(User.class));
        verify(recordRepository, times(1)).save(any(Record.class));
    }

    @Test
    public void testCreateRecord_success_multiplication() {
        recordDTO.setOperation("multiplication");
        operation = new Operation(1L, Type.MULTIPLICATION, 50.0);

        when(userService.findByEmail("testUser")).thenReturn(ObjectMapperUtils.map(user, UserDTO.class));
        when(operationService.findByType("multiplication")).thenReturn(operation);
        when(recordRepository.save(any(Record.class))).thenReturn(new Record());

        RecordDTO result = recordService.createRecord(recordDTO);

        assertNotNull(result);
        verify(userService, times(1)).save(any(User.class));
        verify(recordRepository, times(1)).save(any(Record.class));
    }

    @Test
    public void testCreateRecord_success_division() {
        recordDTO.setOperation("division");
        operation = new Operation(1L, Type.DIVISION, 50.0);

        when(userService.findByEmail("testUser")).thenReturn(ObjectMapperUtils.map(user, UserDTO.class));
        when(operationService.findByType("division")).thenReturn(operation);
        when(recordRepository.save(any(Record.class))).thenReturn(new Record());

        RecordDTO result = recordService.createRecord(recordDTO);

        assertNotNull(result);
        verify(userService, times(1)).save(any(User.class));
        verify(recordRepository, times(1)).save(any(Record.class));
    }

    @Test
    public void testCreateRecord_success_squareRoot() {
        recordDTO.setOperation("square_root");
        operation = new Operation(1L, Type.SQUARE_ROOT, 50.0);

        when(userService.findByEmail("testUser")).thenReturn(ObjectMapperUtils.map(user, UserDTO.class));
        when(operationService.findByType("square_root")).thenReturn(operation);
        when(recordRepository.save(any(Record.class))).thenReturn(new Record());

        RecordDTO result = recordService.createRecord(recordDTO);

        assertNotNull(result);
        verify(userService, times(1)).save(any(User.class));
        verify(recordRepository, times(1)).save(any(Record.class));
    }

    @Test
    public void testCreateRecord_success_randomString() {
        recordDTO.setOperation("random_string");
        operation = new Operation(1L, Type.RANDOM_STRING, 50.0);

        when(userService.findByEmail("testUser")).thenReturn(ObjectMapperUtils.map(user, UserDTO.class));
        when(operationService.findByType("random_string")).thenReturn(operation);
        when(recordRepository.save(any(Record.class))).thenReturn(new Record());

        RecordDTO result = recordService.createRecord(recordDTO);

        assertNotNull(result);
        verify(userService, times(1)).save(any(User.class));
        verify(recordRepository, times(1)).save(any(Record.class));
    }

    @Test
    public void testCreateRecord_insufficientBalance() {
        recordDTO.setOperation("addition");
        recordDTO.setUserBalance(30.0);
        user.setBalance(30.0);
        operation = new Operation(1L, Type.ADDITION, 50.0);

        when(userService.findByEmail("testUser")).thenReturn(ObjectMapperUtils.map(user, UserDTO.class));
        when(operationService.findByType("addition")).thenReturn(operation);

        assertThrows(InsufficientBalanceException.class, () -> recordService.createRecord(recordDTO));
    }

    @Test
    public void testUpdateRecord() {
        Record record = new Record();
        when(recordRepository.save(any(Record.class))).thenReturn(record);

        RecordDTO result = recordService.updateRecord(1L, recordDTO);

        assertNotNull(result);
        verify(recordRepository, times(1)).save(any(Record.class));
    }

    @Test
    public void testGetAllRecords() {
        Record record = new Record();
        when(recordRepository.findAll()).thenReturn(Collections.singletonList(record));

        List<RecordDTO> results = recordService.getAllRecords();

        assertFalse(results.isEmpty());
        verify(recordRepository, times(1)).findAll();
    }

    @Test
    public void testGetAllRecords_noRecordsFound() {
        when(recordRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(IllegalArgumentException.class, () -> recordService.getAllRecords());
    }

    @Test
    public void testGetRecord() {
        Record record = new Record();
        when(recordRepository.findById(1L)).thenReturn(Optional.of(record));

        RecordDTO result = recordService.getRecord(1L);

        assertNotNull(result);
        verify(recordRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetRecord_notFound() {
        when(recordRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> recordService.getRecord(1L));
    }

    @Test
    public void testFindByUserId() {
        Record record = new Record();
        Pageable pageable = PageRequest.of(0, 10);
        Page<Record> page = new PageImpl<>(Collections.singletonList(record));
        when(recordRepository.findByUserId("testUser", pageable)).thenReturn(page);

        Page<RecordDTO> result = recordService.findByUserId("testUser", pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(recordRepository, times(1)).findByUserId("testUser", pageable);
    }
}
