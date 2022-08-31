package com.crewmeister.cmcodingchallenge.commons.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * Swagger configuration for API Documentation
 * 
 * 
 * Document UI reachable at:  http://{host}/swagger-ui.html
 * Document Rest API reachable at:  http://{host}/v2/api-docs
 * 
 */

@Configuration
@EnableSwagger2
public class ApiDocumentConfig {

	/**
	 * Method to set paths to be included through swagger
	 *
	 * @return Docket
	 */
	@Bean
	public Docket postsApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).pathMapping("/").select()
				.paths(regex("/api.*")).build();
	}

	/**
	 * Method to set swagger info
	 *
	 * @return ApiInfoBuilder
	 */
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("EUR-FX exchange rate APIs").description("Rest Service implementation for the Daily EUR-FX Exchange rates.").version("1.0").build();
	}

}