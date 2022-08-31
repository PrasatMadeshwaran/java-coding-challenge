package com.crewmeister.cmcodingchallenge.data.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class StatusRepository {

	private static final Logger LOGGER = LogManager.getLogger(StatusRepository.class.getName());

	private HashOperations<String, String, String> hashOperations;

	public StatusRepository(RedisTemplate<String, String> redisTemplate) {
		this.hashOperations = redisTemplate.opsForHash();
	}

	public void createStatus(String currency, String status) {
		hashOperations.put("STATUS", currency, status);
		LOGGER.info(String.format("Status with Currency %s created", currency, status));

	}

	public String getStatus(String currency) {
		return (String) hashOperations.get("STATUS", currency);
	}

	public Map<String, String> getAllStatus() {
		return hashOperations.entries("STATUS");
	}

	public void updateStatus(String currency, String status) {
		hashOperations.put("STATUS", currency, status);
		LOGGER.info(String.format("Status with Currency %s updated", currency, status));
	}

	public void deleteStatus(String currency) {
		hashOperations.delete("STATUS", currency);
		LOGGER.info(String.format("Status with Currency %s deleted", currency));
	}

}
