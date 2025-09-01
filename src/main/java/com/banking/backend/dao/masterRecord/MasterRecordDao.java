package com.banking.backend.dao.masterRecord;

import java.math.BigDecimal;
import java.util.List;
import com.banking.backend.domain.masterRecord.MasterRecord;

public interface MasterRecordDao {
    List<MasterRecord> getReceiverData(long receiverID, int limit, int offset);

    List<MasterRecord> getSenderData(long senderID, int limit, int offset);

    List<MasterRecord> getTransactionData(long senderID, long receiverID, int limit, int offset);

    void recordTransfer(long senderID, long receiverID, BigDecimal funds);

    void recordDeposit(long receiverID, BigDecimal funds);

    void recordWithdrawal(long senderID, BigDecimal funds);
}
