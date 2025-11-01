package com.banking.backend.dto.account;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountDTO {
    private Long id;
    private BigDecimal funds;
}
