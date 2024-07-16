package com.ntd.service;

import com.ntd.entity.Operation;
import com.ntd.entity.Type;
import com.ntd.repository.OperationRepository;
import com.ntd.service.exception.OperationNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OperationServiceTest {

    @Mock
    private OperationRepository operationRepository;

    @InjectMocks
    private OperationService operationService;

    private Operation operation;

    @BeforeEach
    public void setUp() {
        operation = new Operation(1L, Type.ADDITION, 10.0);
    }

    @Test
    public void testFindById() {
        when(operationRepository.findById(1L)).thenReturn(Optional.of(operation));

        Operation foundOperation = operationService.findById(1L);

        assertNotNull(foundOperation);
        assertEquals(Type.ADDITION, foundOperation.getType());
        assertEquals(10.0, foundOperation.getCost());
    }

    @Test
    public void testFindAll() {
        Operation operation2 = new Operation(2L, Type.SUBTRACTION, 5.0);
        List<Operation> operations = Arrays.asList(operation, operation2);

        when(operationRepository.findAll()).thenReturn(operations);

        List<Operation> foundOperations = operationService.findAll();

        assertNotNull(foundOperations);
        assertEquals(2, foundOperations.size());
    }

    @Test
    public void testFindByType() {
        when(operationRepository.findByType(Type.ADDITION)).thenReturn(Optional.of(operation));

        Operation foundOperation = operationService.findByType("ADDITION");

        assertNotNull(foundOperation);
        assertEquals(Type.ADDITION, foundOperation.getType());
    }

    @Test
    public void testFindByTypeNotFound() {
        when(operationRepository.findByType(Type.DIVISION)).thenReturn(Optional.empty());

        Exception exception = assertThrows(OperationNotFoundException.class, () -> {
            operationService.findByType("DIVISION");
        });

        String expectedMessage = "Operation not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
