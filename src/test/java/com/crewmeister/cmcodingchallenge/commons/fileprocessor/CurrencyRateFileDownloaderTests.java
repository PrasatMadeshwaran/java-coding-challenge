package com.crewmeister.cmcodingchallenge.commons.fileprocessor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CurrencyRateFileDownloaderTests {

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private CurrencyRateFileDownloaderImpl fileDownloaderService;

	@SuppressWarnings("unchecked")
	@Test
	public void testDownloadfile() {
		ReflectionTestUtils.setField(fileDownloaderService, "dailyRateFolder", "dailyrate/files/");
		ReflectionTestUtils.setField(fileDownloaderService, "dailyRateUrl",
				"https://api.statistiken.bundesbank.de/rest/download/BBEX3/");
		ReflectionTestUtils.setField(fileDownloaderService, "rateDownloadFormat", "?format=sdmx&lang=en");

		when(restTemplate.execute(ArgumentMatchers.anyString(), ArgumentMatchers.any(HttpMethod.class),
				ArgumentMatchers.any(RequestCallback.class), ArgumentMatchers.any(ResponseExtractor.class)))
						.thenReturn(200);

		Path path = Paths.get("dailyrate/files/");

		try (MockedStatic<Files> staticFiles = Mockito.mockStatic(Files.class)) {
			staticFiles.when(() -> Files.createDirectories(path)).thenReturn(null);
			int response = fileDownloaderService.downloadfile("D.AUD.EUR.BB.AC.000.xml");
			assertThat(response).isEqualTo(200);
			staticFiles.verify(() -> Files.createDirectories(path));
		}

	}

}
