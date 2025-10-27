package com.banking.backend.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.banking.backend.exceptions.DataBaseAccessException;

public abstract class BaseDaoImpl {
    protected final JdbcTemplate jdbcTemplate;

    protected BaseDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 
     * @param <T>    Optional return type
     * @param sql    Query for the DB
     * @param type   Data type looked for
     * @param params set of parameters needed for the query
     * @return A single scalar value from the database / Optional.empty() if no row
     *         is found.
     * @throws DataBaseAccessException if other JDBC issues occur.
     */
    protected <T> Optional<T> getScalar(String sql, Class<T> type, Object... params) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(sql, type, params));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty(); // expected: no row
        } catch (DataAccessException e) {
            throw new DataBaseAccessException("Failed to fetch scalar value", e);
        }
    }

    /**
     * 
     * @param <T>    Optional return type
     * @param sql    Query for the DB
     * @param type   Data type looked for
     * @param params set of parameters needed for the query
     * @return a list of single-column values.
     * @throws DataBaseAccessException on SQL errors.
     */
    protected <T> List<T> getSingleColumnList(String sql, Class<T> type, Object... params) {
        try {
            return jdbcTemplate.queryForList(sql, type, params);
        } catch (DataAccessException e) {
            throw new DataBaseAccessException("Failed to fetch single-column list", e);
        }
    }

    /**
     * Executes update/insert/delete operations in the db.
     * 
     * @param sql    Query for the DB
     * @param params set of parameters needed for the query
     * @throws DataBaseAccessException on SQL errors.
     */
    protected void updateDB(String sql, Object... params) {
        try {
            jdbcTemplate.update(sql, params);
        } catch (DataAccessException e) {
            throw new DataBaseAccessException("Failed to execute update", e);
        }
    }

    /**
     * 
     * @param <T>       Optional return type
     * @param sql       Query for the DB
     * @param rowMapper row mapper for a specific domain object
     * @param params    set of parameters needed for the query
     * @return a list of rows necessary for domain objects.
     * @throws DataBaseAccessException on SQL errors.
     */
    protected <T> List<T> getResultList(String sql, RowMapper<T> rowMapper, Object... params) {
        try {
            return jdbcTemplate.query(sql, rowMapper, params);
        } catch (DataAccessException e) {
            throw new DataBaseAccessException("Failed to fetch result list", e);
        }
    }

    /**
     * 
     * @param <T>       Optional return type
     * @param sql       Query for the DB
     * @param rowMapper row mapper for a specific domain object
     * @param params    set of parameters needed for the query
     * @return a single row of data necessary for a domain object.
     * @throws DataBaseAccessException on SQL errors.
     */
    protected <T> Optional<T> getSingleRow(String sql, RowMapper<T> rowMapper, Object... params) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(sql, rowMapper, params));
        } catch (DataAccessException e) {
            throw new DataBaseAccessException("Failed to fetch single row", e);
        }
    }
}
