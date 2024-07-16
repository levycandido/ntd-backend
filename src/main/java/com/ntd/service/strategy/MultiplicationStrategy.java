package com.ntd.service.strategy;

import com.ntd.service.dao.RecordDTO;

public class MultiplicationStrategy implements OperationStrategy {
    @Override
    public double execute(RecordDTO recordDTO, double balance, double cost) {
        return recordDTO.getFirstValue() * recordDTO.getSecValue();
    }
}