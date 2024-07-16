package com.ntd.repository;

import com.ntd.entity.Operation;
import com.ntd.entity.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class OperationRepositoryTest {

    @Autowired
    private OperationRepository operationRepository;

    private Operation operation;

    @BeforeEach
    public void setUp() {
        operation = new Operation();
        operation.setType(Type.ADDITION);
        operation.setCost(10.0);
        operationRepository.save(operation);
    }

    @Test
    public void testFindByType() {
        Optional<Operation> foundOperation = operationRepository.findByType(Type.fromString("addition"));

        assertTrue(foundOperation.isPresent());
        assertEquals(Type.ADDITION, foundOperation.get().getType());
        assertEquals(10.0, foundOperation.get().getCost());
    }

    @Test
    public void testFindByTypeNotFound() {
        Optional<Operation> foundOperation = operationRepository.findByType(Type.SUBTRACTION);

        assertFalse(foundOperation.isPresent());
    }
}
