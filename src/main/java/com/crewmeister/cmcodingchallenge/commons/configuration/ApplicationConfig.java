package com.crewmeister.cmcodingchallenge.commons.configuration;

import com.crewmeister.cmcodingchallenge.commons.utility.CurrencyConstant;
import com.crewmeister.cmcodingchallenge.data.repository.StatusRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import redis.embedded.RedisServer;

import javax.annotation.PreDestroy;
import java.util.List;

/**
 * Boot Application configuration
 * 
 * Manages <RedisServer>. Start/Stop server with Boot Clean up Exchange Rate
 * files from local mount {dailyrate/files/}. Downloads Exchange Rate files from
 * Bank's site Asynchronously
 *
 */

@Configuration
@EnableScheduling
public class ApplicationConfig {

	private static final Logger LOGGER = LogManager.getLogger(ApplicationConfig.class.getName());

	@Value("${app.rate.download.currencies}")
	public List<String> currencies;

	@Value("${app.rate.file.target}")
	private String dailyRateFolder;

	@Value("${app.file.api.endpoint}")
	private String dailyRateDownloadEndpoint;

	@Value("${app.rate.filename.pattern}")
	private String rateFileName;

	@Autowired
	private RedisServer redisServer;

	@Autowired
	private StatusRepository statusRepository;

	/**
	 * <ApplicationReadyEvent> listener
	 * 
	 * Start <RedisServer> Clean up Exchange Rate files from local mount
	 * {dailyrate/files/} Downloads Exchange Rate files from Bank's site
	 * 
	 * Invokes endpoint {/api/file/{fileName}} to download and process rate files
	 * Asynchronously
	 *
	 */
	@EventListener(ApplicationReadyEvent.class)
	public void eventListenerExecute() {
		processRateFile();
	}

	@Scheduled(cron = "${rate.refresh.cron.expression}")
	public void rateUploaderShedule() {
		processRateFile();

	}

	private void processRateFile() {

		for (String currency : currencies) {
			statusRepository.createStatus(currency, CurrencyConstant.RATE_PROCESSING_STATUS_LOADING);
			String fileNameToDownload = rateFileName.replace(":currency", currency);
			WebClient webClient = WebClient.create();
			Mono<String> response = webClient.get().uri(dailyRateDownloadEndpoint + fileNameToDownload).retrieve()
					.bodyToMono(String.class);
			Flux.merge(response).subscribe(LOGGER::info);

		}
	}

	@Bean
	public javax.validation.Validator localValidatorFactoryBean() {
		return new LocalValidatorFactoryBean();
	}

	/**
	 * Hook to clean resources
	 */

	@PreDestroy
	public void onShutDown() {
		if (null != redisServer) {
			redisServer.stop();
			redisServer = null;
		}
	}
}
