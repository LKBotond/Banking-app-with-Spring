package com.banking.backend.dao.masterRecord;

import java.util.Optional;
import java.util.List;
import java.util.Map;

public interface MasterRecordDao {
    Optional<List<Map<String, Object>>> getReceiverData(long receiverID);

    Optional<List<Map<String, Object>>> getSenderData(long senderID);

    Optional<List<Map<String, Object>>> getTransactionData(long senderID, long receiverID);

    void recordTransfer(long senderID, long receiverID, long funds);
}
