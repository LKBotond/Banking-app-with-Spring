package com.banking.backend.dto.transaction;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferDTO {
    String sessionToken;
    Long sender;
    Long receiver;
    BigDecimal sum;
}
