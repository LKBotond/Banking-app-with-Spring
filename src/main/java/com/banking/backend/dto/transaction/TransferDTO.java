package com.banking.backend.dto.transaction;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferDTO {
    Long id;
    Long fromId;
    Long toId;
    BigDecimal sum;
    OffsetDateTime moment;
}
