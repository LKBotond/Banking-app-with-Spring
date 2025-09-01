package com.banking.backend.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public abstract class BaseDaoImpl {
    protected final JdbcTemplate jdbcTemplate;

    protected BaseDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    protected <T> Optional<T> getScalar(String sql, Class<T> type, Object... params) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(sql, type, params));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    protected <T> List<T> getSingleColumnList(String sql, Class<T> type, Object... params) {
        return jdbcTemplate.queryForList(sql, type, params);
    }

    protected void addData(String sql, Object... params) {
        jdbcTemplate.update(sql, params);
    }

    protected <T> List<T> getResultList(String sql, RowMapper<T> rowMapper, Object... params) {
        return jdbcTemplate.query(sql, rowMapper, params);
    }
}
