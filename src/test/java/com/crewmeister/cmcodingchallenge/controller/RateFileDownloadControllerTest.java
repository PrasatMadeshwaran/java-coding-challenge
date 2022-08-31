package com.crewmeister.cmcodingchallenge.controller;

import com.crewmeister.cmcodingchallenge.commons.fileprocessor.CurrencyRateFileDownloader;
import com.crewmeister.cmcodingchallenge.commons.fileprocessor.CurrencyRateFileParser;
import com.crewmeister.cmcodingchallenge.currency.CurrencyRateFileDownloadController;
import com.crewmeister.cmcodingchallenge.data.entity.CurrencyDailyRate;
import com.crewmeister.cmcodingchallenge.data.repository.StatusRepository;
import com.crewmeister.cmcodingchallenge.data.service.DailyCurrencyRateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CurrencyRateFileDownloadController.class)
public class RateFileDownloadControllerTest {
	
	@MockBean
	private CurrencyRateFileDownloader currencyRateFileDownloader;

	@MockBean
	private CurrencyRateFileParser dailyRateXmlParser;

	@MockBean
	private DailyCurrencyRateService dailyCurrencyRateService;
	
	@MockBean
	private StatusRepository statusRepository;

	@Autowired
	MockMvc mockMvc;
	
	private List<CurrencyDailyRate> currencyDailyRateList;

	@BeforeEach
	public void setup() {

		currencyDailyRateList = new ArrayList<CurrencyDailyRate>();
		CurrencyDailyRate rateAUD = new CurrencyDailyRate();
		rateAUD.setId(1);
		rateAUD.setSourceCurrency("AUD");
		rateAUD.setRateDate(Date.valueOf("2021-11-11"));
		rateAUD.setExchangeRate(new BigDecimal(0.434));

		CurrencyDailyRate rateUSD = new CurrencyDailyRate();
		rateUSD.setId(2);
		rateUSD.setSourceCurrency("USD");
		rateUSD.setRateDate(Date.valueOf("2021-11-11"));
		rateUSD.setExchangeRate(new BigDecimal(0.435));
		currencyDailyRateList.add(rateAUD);
		currencyDailyRateList.add(rateUSD);

	}
	
	@Test
	public void testGetFile() throws Exception {

		Mockito.when(currencyRateFileDownloader.downloadfile("D.BGN.EUR.BB.AC.000")).thenReturn(200);
		Mockito.when(dailyRateXmlParser.parseXml("D.BGN.EUR.BB.AC.000")).thenReturn(currencyDailyRateList);
		Mockito.when(dailyCurrencyRateService.saveAllRates(currencyDailyRateList)).thenReturn(currencyDailyRateList);

		mockMvc.perform(get("/api/file/{fileName}", "D.BGN.EUR.BB.AC.000"))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$").value("D.BGN.EUR.BB.AC.000 downloaded successfully"));
	}
	
	@Test
	public void testGetFileNotFound() throws Exception {

		Mockito.when(currencyRateFileDownloader.downloadfile("D.XYZ")).thenReturn(404);

		mockMvc.perform(get("/api/file/{fileName}", "D.XYZ"))
				.andExpect(status().isNotFound())
				.andExpect(MockMvcResultMatchers.jsonPath("$").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Unable to download File D.XYZ"));
	}


}
