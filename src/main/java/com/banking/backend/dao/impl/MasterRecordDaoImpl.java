package com.banking.backend.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.banking.backend.dao.MasterRecordDao;
import com.banking.backend.dbAccess.DBQueries;

@Repository
public class MasterRecordDaoImpl implements MasterRecordDao {

    private final JdbcTemplate jdbcTemplate;

    public MasterRecordDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

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
    public void recordTransfer(long senderID, long receiverID, long funds) {
        jdbcTemplate.update(DBQueries.RECORD_TRANSFER, senderID, receiverID, funds);
    }
}
