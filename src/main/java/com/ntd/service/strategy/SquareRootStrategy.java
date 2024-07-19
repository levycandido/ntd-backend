package com.ntd.service.strategy;

import com.ntd.service.dao.RecordDTO;
import com.ntd.service.exception.InvalidSquareRootNumberException;

public class SquareRootStrategy implements OperationStrategy {
    @Override
    public double execute(RecordDTO recordDTO, double balance, double cost) {

        if (recordDTO.getFirstValue() < 0) {
            throw new InvalidSquareRootNumberException("Square root of a negative number is not allowed.");
        }

        return Math.sqrt(recordDTO.getFirstValue());
    }
}
