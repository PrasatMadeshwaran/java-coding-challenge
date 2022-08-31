package com.crewmeister.cmcodingchallenge.commons.fileprocessor;

import com.crewmeister.cmcodingchallenge.commons.utility.CurrencyConstant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.Arrays;

/**
 * Service class for Rate File Download
 * 
 */

@Service
public class CurrencyRateFileDownloaderImpl implements CurrencyRateFileDownloader {

	private static final Logger LOGGER = LogManager.getLogger(CurrencyRateFileDownloaderImpl.class.getName());

	private RestTemplate restTemplate;

	@Value("${app.rate.file.target}")
	private String dailyRateFolder;

	@Value("${app.rate.download.url}")
	private String dailyRateUrl;

	@Value("${app.rate.download.format}")
	private String rateDownloadFormat;

	@Autowired
	private CurrencyRateFileParser currencyRateFileParser;

	@Autowired
	public CurrencyRateFileDownloaderImpl(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	/**
	 * Downloads rate file from Bank's endpoint and stores in local mount
	 * 
	 * @param fileName Input file name
	 * 
	 * @return status code Integer status code
	 */

	@Override
	public Integer downloadfile(String fileName) {
		String url = dailyRateUrl + fileName + rateDownloadFormat;

		Boolean isUpdated = false;
		int statusCode;
		String targetFile = dailyRateFolder + fileName;
		Path tagetFilePath = Paths.get(targetFile + CurrencyConstant.RATE_FILE_EXTENSION);
		if (Files.exists(tagetFilePath)) {
			isUpdated = currencyRateFileParser.verifyFile(fileName, new Date(System.currentTimeMillis()));
			if (!isUpdated) {
				try {
					Files.delete(tagetFilePath);
				} catch (IOException exception) {
					LOGGER.error(exception);
					LOGGER.error(
							"EventListenerExecute : Failed to clean Rate file directory" + exception.getLocalizedMessage());
				}
			}
		}

		Path path = Paths.get(dailyRateFolder);
		try {
			Files.createDirectories(path);
		} catch (IOException e) {
			LOGGER.error("Unable to create directory with error " + e);
			LOGGER.error("Unable to create directory " + path);
		}

		if (!isUpdated) {			
			RequestCallback requestCallback = request -> request.getHeaders()
					.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM, MediaType.ALL));

			// Streaming the response to file
			statusCode = restTemplate.execute(url, HttpMethod.GET, requestCallback, clientHttpResponse -> {
				Files.copy(clientHttpResponse.getBody(), tagetFilePath);
				return clientHttpResponse.getStatusCode().value();
			});
			
			LOGGER.info("Downloading completed ----- " + fileName);
		} else {
			LOGGER.info("Updated File is available. Redircting request for " + path);
			statusCode = 200;
		}

		return statusCode;

	}

}
