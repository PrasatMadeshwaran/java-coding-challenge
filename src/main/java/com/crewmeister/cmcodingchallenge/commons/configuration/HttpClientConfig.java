package com.crewmeister.cmcodingchallenge.commons.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;


/**
 * RestTemplate configuration
 * Used for Synchronous HTTP requests calls
 * 
 */

@Configuration
public class HttpClientConfig {

	/**
	 * Creates Rest Template to use in invoking endpoints 
	 * 
	 * @return RestTemplate 
	 */
	@Bean
	public RestTemplate restTemplate() {

		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setConnectTimeout(3000);
		factory.setReadTimeout(3000);
		return new RestTemplate(factory);
	}
}
