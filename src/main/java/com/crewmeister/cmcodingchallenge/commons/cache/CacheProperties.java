package com.crewmeister.cmcodingchallenge.commons.cache;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 
 *  Properties bean for Redis Caching
 *
 */

@Configuration
@Getter
@Setter
public class CacheProperties {

	private int redisPort;
	private String redisHost;

	public CacheProperties(@Value("${spring.redis.port}") int redisPort,
			@Value("${spring.redis.host}") String redisHost) {
		this.redisPort = redisPort;
		this.redisHost = redisHost;
	}

	public int getRedisPort() {
		return redisPort;
	}

	public void setRedisPort(int redisPort) {
		this.redisPort = redisPort;
	}

	public String getRedisHost() {
		return redisHost;
	}

	public void setRedisHost(String redisHost) {
		this.redisHost = redisHost;
	}

}
