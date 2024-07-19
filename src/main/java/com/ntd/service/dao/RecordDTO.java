package com.ntd.service.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecordDTO {

    private Long id;
    private OperationDTO operation;
    private String userId;
    private Double amount;
    private Double firstValue;
    private Double secValue;
    private Double userBalance;
    private String operationResponse;
    private LocalDateTime date;

}
