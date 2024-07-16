package com.ntd.service.strategy;

import com.ntd.service.dao.RecordDTO;

public class SquareRootStrategy implements OperationStrategy {
    @Override
    public double execute(RecordDTO recordDTO, double balance, double cost) {
        return Math.sqrt(recordDTO.getFirstValue());
    }
}
