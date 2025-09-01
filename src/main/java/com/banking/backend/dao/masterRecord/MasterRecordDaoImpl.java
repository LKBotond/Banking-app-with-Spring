package com.banking.backend.dao.masterRecord;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.banking.backend.dao.BaseDaoImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.banking.backend.dbAccess.DBQueries;
import com.banking.backend.domain.accounts.Account;

@Repository
public class MasterRecordDaoImpl extends BaseDaoImpl implements MasterRecordDao {

    public MasterRecordDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    /*
     * private RowMapper<Account> masterRecordRowMapper() {
     * 
     * }
     */

    @Override
    public Optional<List<Map<String, Object>>> getReceiverData(long receiverID) {
        List<Map<String, Object>> resultset = jdbcTemplate.queryForList(DBQueries.GET_RECEIVER_DATA, receiverID);
        return resultset.isEmpty() ? Optional.empty() : Optional.of(resultset);
    }

    @Override
    public Optional<List<Map<String, Object>>> getSenderData(long senderID) {
        List<Map<String, Object>> resultset = jdbcTemplate.queryForList(DBQueries.GET_SENDER_DATA, senderID);
        return resultset.isEmpty() ? Optional.empty() : Optional.of(resultset);
    }

    @Override
    public Optional<List<Map<String, Object>>> getTransactionData(long senderID, long receiverID) {
        List<Map<String, Object>> resultset = jdbcTemplate.queryForList(DBQueries.GET_TRANSACTIONS_DATA, senderID,
                receiverID);
        return resultset.isEmpty() ? Optional.empty() : Optional.of(resultset);
    }

    @Override
    public void recordTransfer(long senderID, long receiverID, BigDecimal funds) {
        jdbcTemplate.update(DBQueries.RECORD_TRANSFER, senderID, receiverID, funds);
    }

    @Override
    public void recordDeposit(long receiverID, BigDecimal funds) {
        jdbcTemplate.update(DBQueries.RECORD_DEPOSIT, receiverID, funds);
    }

    @Override
    public void recordWithdrawal(long senderID, BigDecimal funds) {
        jdbcTemplate.update(DBQueries.RECORD_WITHDRAWAL, senderID, funds);
    }

}
