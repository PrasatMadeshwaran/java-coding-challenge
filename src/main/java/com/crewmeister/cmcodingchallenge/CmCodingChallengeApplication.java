package com.crewmeister.cmcodingchallenge;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CmCodingChallengeApplication {

	private static final Logger LOGGER = LogManager.getLogger(CmCodingChallengeApplication.class.getName());

	public static void main(String[] args) {

		SpringApplication.run(CmCodingChallengeApplication.class, args);
		LOGGER.info("Currency Spring Boot app started ");
		LOGGER.info("GET /api/currencies service started");
		LOGGER.info("GET /api/rates service started");
		LOGGER.info("GET /api/convertcurrency service started");
	}

}
