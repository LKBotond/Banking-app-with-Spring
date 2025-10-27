package com.banking.backend.dao.masterRecord;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import com.banking.backend.dao.BaseDaoImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.banking.backend.dbAccess.DBQueries;
import com.banking.backend.domain.masterRecord.MasterRecord;

@Repository
public class MasterRecordDaoImpl extends BaseDaoImpl implements MasterRecordDao {

    public MasterRecordDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    private RowMapper<MasterRecord> masterRecordRowMapper() {
        return (rs, _) -> new MasterRecord(
                rs.getLong("id"),
                rs.getLong("sender_id"),
                rs.getLong("receiver_id"),
                rs.getString("transaction_type"),
                rs.getBigDecimal("funds"),
                rs.getObject("transaction_date", OffsetDateTime.class));
    }

    @Override
    public List<MasterRecord> getReceiverData(long receiverID, int limit, int offset) {
        return getResultList(DBQueries.GET_RECEIVER_DATA, masterRecordRowMapper(), receiverID, limit, offset);
    }

    @Override
    public List<MasterRecord> getSenderData(long senderID, int limit, int offset) {
        return getResultList(DBQueries.GET_SENDER_DATA, masterRecordRowMapper(), senderID, limit, offset);
    }

    @Override
    public List<MasterRecord> getTransactionData(long senderID, long receiverID, int limit, int offset) {
        return getResultList(DBQueries.GET_TRANSACTIONS_DATA, masterRecordRowMapper(), senderID, receiverID, limit,
                offset);
    }

    @Override
    public void recordTransfer(long senderID, long receiverID, BigDecimal funds) {
        updateDB(DBQueries.RECORD_TRANSFER, senderID, receiverID, funds);
    }

    @Override
    public void recordDeposit(long receiverID, BigDecimal funds) {
        updateDB(DBQueries.RECORD_DEPOSIT, receiverID, funds);
    }

    @Override
    public void recordWithdrawal(long senderID, BigDecimal funds) {
        updateDB(DBQueries.RECORD_WITHDRAWAL, senderID, funds);
    }

}
