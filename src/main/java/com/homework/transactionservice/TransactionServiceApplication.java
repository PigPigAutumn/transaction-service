package com.homework.transactionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The startup entry point of TransactionService.
 *
 * @author Jiaqi HUANG
 * @version 0.0.1-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
 */
@SpringBootApplication
public class TransactionServiceApplication {

	/**
	 * Entry point of the main method
	 *
	 * @param args Program startup parameters
	 */
	public static void main(String[] args) {
		SpringApplication.run(TransactionServiceApplication.class, args);
	}

}
