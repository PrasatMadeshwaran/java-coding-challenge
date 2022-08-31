package com.crewmeister.cmcodingchallenge;

import com.crewmeister.cmcodingchallenge.commons.cache.CacheProperties;
import com.crewmeister.cmcodingchallenge.commons.configuration.ApplicationConfig;
import com.crewmeister.cmcodingchallenge.commons.configuration.HttpClientConfig;

import com.crewmeister.cmcodingchallenge.currency.CurrencyController;
import com.crewmeister.cmcodingchallenge.currency.CurrencyRateFileDownloadController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CmCodingChallengeApplicationTests {

	@Autowired
	CurrencyController currencyController;

	@Autowired
    CurrencyRateFileDownloadController currencyRateFileDownloadController;

	@Autowired
	CacheProperties redisProperties;

	@Autowired
	ApplicationConfig applicationConfig;

	@Autowired
	HttpClientConfig restTemplateConfig;

	@Test
	void contextLoadsCurrencyController() {
		assertThat(currencyController).isNotNull();
	}

	@Test
	void contextLoadsFileDownloadController() {
		assertThat(currencyRateFileDownloadController).isNotNull();
	}

	@Test
	void contextLoadsRedisProperties() {
		assertThat(redisProperties).isNotNull();
	}

	@Test
	void contextLoadsApplicationConfig() {
		assertThat(applicationConfig).isNotNull();
	}

	@Test
	void contextLoadsRestTemplateConfig() {
		assertThat(restTemplateConfig).isNotNull();
	}
}
