package com.ntd.service.strategy;

import com.ntd.service.dao.RecordDTO;

public interface OperationStrategy {
    double execute(RecordDTO recordDTO, double balance, double cost);
}
