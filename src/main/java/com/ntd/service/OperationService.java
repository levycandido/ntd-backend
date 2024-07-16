package com.ntd.service;

import com.ntd.entity.Operation;
import com.ntd.entity.Type;
import com.ntd.repository.OperationRepository;
import com.ntd.service.exception.OperationNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperationService {
    @Autowired
    private OperationRepository operationRepository;

    public Operation findById(Long id) {
        return operationRepository.findById(id).orElse(null);
    }

    public List<Operation> findAll() {
        return operationRepository.findAll();
    }

    public Operation findByType(String type) {
        return operationRepository.findByType(Type.fromString(type))
                .orElseThrow(() -> new OperationNotFoundException("Operation not found"));
    }

    private static Type getType(String type) {
        return Type.valueOf(type);
    }
}
