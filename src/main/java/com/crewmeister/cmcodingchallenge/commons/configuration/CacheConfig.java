package com.crewmeister.cmcodingchallenge.commons.configuration;

import com.crewmeister.cmcodingchallenge.commons.cache.CacheProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import redis.embedded.RedisServer;

/**
 * Redis caching server configuration
 * 
 */

@Configuration
@EnableRedisRepositories
public class CacheConfig {

	@Bean
	public LettuceConnectionFactory redisConnectionFactory(CacheProperties redisProperties) {
		return new LettuceConnectionFactory(redisProperties.getRedisHost(), redisProperties.getRedisPort());
	}

	@Bean
	public RedisTemplate<?, ?> redisTemplate(LettuceConnectionFactory connectionFactory) {
		RedisTemplate<byte[], byte[]> template = new RedisTemplate<>();
		template.setConnectionFactory(connectionFactory);
		return template;
	}

	@Bean
	public RedisServer prepareRedisServer(CacheProperties redisProperties) {
		return new RedisServer(redisProperties.getRedisPort());

	}
}