package com.ntd.service.dao;

import com.ntd.entity.Type;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode()
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperationDTO {

    private Long id;
    @Enumerated(EnumType.STRING)
    private Type type;
    private Double cost;
}
