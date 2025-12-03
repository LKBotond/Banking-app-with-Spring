package com.banking.backend.dto.transaction;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OperationDTO {
    String sessionToken;
    long accountId;
    BigDecimal sum;
}
