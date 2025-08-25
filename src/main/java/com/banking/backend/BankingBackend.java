package com.banking.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.banking.backend.dbAccess.DBQueries;

@SpringBootApplication
public class BankingBackend {

	public static void main(String[] args) {
		SpringApplication.run(BankingBackend.class, args);
		System.out.println(DBQueries.CREATE_ACCOUNT);
	}

}
