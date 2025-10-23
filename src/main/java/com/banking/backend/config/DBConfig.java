package com.banking.backend.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DBConfig {
    @Bean
    public JdbcTemplate jdbcTemplate(final DataSource dataSource)
    {
        return new JdbcTemplate(dataSource);
    }
    
}
