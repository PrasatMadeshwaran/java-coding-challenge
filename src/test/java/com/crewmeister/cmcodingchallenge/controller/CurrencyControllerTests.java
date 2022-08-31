package com.crewmeister.cmcodingchallenge.controller;

import com.crewmeister.cmcodingchallenge.commons.model.CurrencyDailyRateDto;
import com.crewmeister.cmcodingchallenge.currency.CurrencyController;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CurrencyController.class)
public class CurrencyControllerTests {

	@MockBean
    DailyCurrencyRateService dailyCurrencyRateService;
	
	@MockBean
	StatusRepository statusRepository;

	@Autowired
	MockMvc mockMvc;

	private List<String> currencyList;
	private List<CurrencyDailyRateDto> dailyRateList;
	private CurrencyDailyRateDto specificRate;

	@BeforeEach
	public void setup() {
		currencyList = new ArrayList<>(Arrays.asList("BRL", "CAD", "CHF"));

		dailyRateList = new ArrayList<CurrencyDailyRateDto>();
		CurrencyDailyRateDto rateAUD = new CurrencyDailyRateDto();
		rateAUD.setId(1);
		rateAUD.setSourceCurrency("AUD");
		rateAUD.setRateDate(Date.valueOf("2021-11-11"));
		rateAUD.setExchangeRate(new BigDecimal(0.434));

		CurrencyDailyRateDto rateUSD = new CurrencyDailyRateDto();
		rateUSD.setId(2);
		rateUSD.setSourceCurrency("USD");
		rateUSD.setRateDate(Date.valueOf("2021-11-11"));
		rateUSD.setExchangeRate(new BigDecimal(0.435));
		dailyRateList.add(rateAUD);
		dailyRateList.add(rateUSD);

		specificRate = new CurrencyDailyRateDto();
		specificRate.setId(3);
		specificRate.setSourceCurrency("AUD");
		specificRate.setRateDate(Date.valueOf("2021-11-12"));
		specificRate.setExchangeRate(new BigDecimal(0.434));
	}

	@Test
	public void testGetCurrencies() throws Exception {
		Mockito.when(dailyCurrencyRateService.findAllCurrencies()).thenReturn(currencyList);

		mockMvc.perform(get("/api/currencies")).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray())
				.andExpect(MockMvcResultMatchers.jsonPath("$.data[1]").value("CAD"));
	}

	@Test
	public void testGetRates() throws Exception {

		Mockito.when(dailyCurrencyRateService.findAllRates()).thenReturn(dailyRateList);

		mockMvc.perform(get("/api/rates")).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].source_currency").value("USD"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].exchange_rate").value(0.435));

	}

	@Test
	public void testGetRatesNotFound() throws Exception {

		Mockito.when(dailyCurrencyRateService.findAllRates()).thenReturn(Collections.emptyList());

		mockMvc.perform(get("/api/rates")).andExpect(status().isNotFound())
				.andExpect(MockMvcResultMatchers.jsonPath("$").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("No rates found"));

	}

	@Test
	public void testGetRatesByDate() throws Exception {

		Mockito.when(dailyCurrencyRateService.findByRateDate(Date.valueOf("2021-11-11"))).thenReturn(dailyRateList);

		mockMvc.perform(get("/api/rates/{date}", Date.valueOf("2021-11-11"))).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].rate_date").value("2021-11-11"));
	}

	@Test
	public void testGetRatesByDateNotFound() throws Exception {

		Mockito.when(dailyCurrencyRateService.findByRateDate(Date.valueOf("2022-11-11")))
				.thenReturn(Collections.emptyList());

		mockMvc.perform(get("/api/rates/{date}", Date.valueOf("2022-11-11"))).andExpect(status().isNotFound())
				.andExpect(MockMvcResultMatchers.jsonPath("$").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("No rates found for date 2022-11-11"));
	}

	@Test
	public void testConvertCurrency() throws Exception {

		Mockito.when(dailyCurrencyRateService.findByRateDateAndSourceCurrency(Date.valueOf("2021-11-12"), "AUD"))
				.thenReturn(specificRate);

		mockMvc.perform(get("/api/convertcurrency/date/{date}/currency/{currency}/amount/{amount}",
				Date.valueOf("2021-11-12"), "AUD", new BigDecimal(100))).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.result").value("230.41"));
	}

	@Test
	public void testConvertCurrencyNotFound() throws Exception {

		Mockito.when(dailyCurrencyRateService.findByRateDateAndSourceCurrency(Date.valueOf("2022-11-12"), "AUD"))
				.thenReturn(null);

		mockMvc.perform(get("/api/convertcurrency/date/{date}/currency/{currency}/amount/{amount}",
				Date.valueOf("2022-11-12"), "AUD", new BigDecimal(100))).andExpect(status().isNotFound())
				.andExpect(MockMvcResultMatchers.jsonPath("$").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("No rates found for date 2022-11-12 currency  AUD"));
	}

}
