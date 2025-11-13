package com.banking.backend.dao.deletion;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.banking.backend.dbAccess.DBQueries;
import com.banking.backend.dao.BaseDaoImpl;

@Repository
public class DeletionDaoImpl extends BaseDaoImpl implements DeletionDao {

    public DeletionDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public void deleteUser(long userID) {
        updateDB(DBQueries.DELETE_USER, userID);
    }

    public void deleteAccount(long accountID) {
        System.out.println("deletion Query: " + DBQueries.DELETE_ACCOUNT);
        updateDB(DBQueries.DELETE_ACCOUNT, accountID);
    }
}
