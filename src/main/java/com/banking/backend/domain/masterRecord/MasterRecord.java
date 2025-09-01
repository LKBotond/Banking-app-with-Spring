package com.banking.backend.domain.masterRecord;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MasterRecord {
    long id;
    long senderID;
    long receiverID;
    String transactionType;
    BigDecimal funds;
    OffsetDateTime transactionDate;

}
