package com.crewmeister.cmcodingchallenge.data.service;

import com.crewmeister.cmcodingchallenge.commons.helper.CurrencyDailyRateMapper;
import com.crewmeister.cmcodingchallenge.commons.model.CurrencyDailyRateDto;
import com.crewmeister.cmcodingchallenge.data.entity.CurrencyDailyRate;
import com.crewmeister.cmcodingchallenge.data.repository.CurrencyDailyRateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class DailyCurrencyRateServiceTest {

	@InjectMocks
	DailyCurrencyRateServiceImpl rateService;

	@Mock
	CurrencyDailyRateRepository repository;

	@Mock
	private CurrencyDailyRateMapper mapper;

	private List<CurrencyDailyRate> currencyDailyRateList;

	private Page<CurrencyDailyRate> dailyRatePage;

	private List<CurrencyDailyRate> currencyDailyRateListForDate;

	private List<String> currencyList;

	private CurrencyDailyRateDto rateAUDDto;

	private CurrencyDailyRateDto rateUSDDto;

	@BeforeEach
	public void setup() {

		currencyList = new ArrayList<>(Arrays.asList("BRL", "CAD", "CHF"));

		currencyDailyRateList = new ArrayList<CurrencyDailyRate>();
		CurrencyDailyRate rateAUD = new CurrencyDailyRate();
		rateAUD.setId(1);
		rateAUD.setSourceCurrency("AUD");
		rateAUD.setRateDate(Date.valueOf("2021-11-11"));
		rateAUD.setExchangeRate(new BigDecimal(0.434));

		CurrencyDailyRate rateUSD = new CurrencyDailyRate();
		rateUSD.setId(2);
		rateUSD.setSourceCurrency("USD");
		rateUSD.setRateDate(Date.valueOf("2021-11-12"));
		rateUSD.setExchangeRate(new BigDecimal(0.435));
		currencyDailyRateList.add(rateAUD);
		currencyDailyRateList.add(rateUSD);

		dailyRatePage = new PageImpl<>(currencyDailyRateList);

		rateAUDDto = new CurrencyDailyRateDto();
		rateAUDDto.setId(1);
		rateAUDDto.setSourceCurrency("AUD");
		rateAUDDto.setRateDate(Date.valueOf("2021-11-11"));
		rateAUDDto.setExchangeRate(new BigDecimal(0.434));

		rateUSDDto = new CurrencyDailyRateDto();
		rateUSDDto.setId(2);
		rateUSDDto.setSourceCurrency("USD");
		rateUSDDto.setRateDate(Date.valueOf("2021-11-12"));
		rateUSDDto.setExchangeRate(new BigDecimal(0.435));

		currencyDailyRateListForDate = new ArrayList<CurrencyDailyRate>();
		currencyDailyRateListForDate.add(rateAUD);
	}

	@Test
	public void testSaveAllRates() {

		when(repository.saveAll(currencyDailyRateList)).thenReturn(currencyDailyRateList);

		List<CurrencyDailyRate> response = rateService.saveAllRates(currencyDailyRateList);
		assertEquals(2, response.size());
	}

	@Test
	public void testFindAllRates() {

		when(repository.findAll()).thenReturn(currencyDailyRateList);
		when(mapper.toDto(currencyDailyRateList.get(0))).thenReturn(rateAUDDto);
		when(mapper.toDto(currencyDailyRateList.get(1))).thenReturn(rateUSDDto);

		List<CurrencyDailyRateDto> response = rateService.findAllRates();
		assertEquals(2, response.size());
	}

	@Test
	public void testFindAllRatesByPage() {
		Pageable paging = PageRequest.of(1, 2, Sort.by("rateDate"));
		when(repository.findAll(paging)).thenReturn(dailyRatePage);
		when(mapper.toDto(currencyDailyRateList.get(0))).thenReturn(rateAUDDto);
		when(mapper.toDto(currencyDailyRateList.get(1))).thenReturn(rateUSDDto);

		List<CurrencyDailyRateDto> response = rateService.findAllRatesByPage(1, 2, "rateDate");
		assertEquals(2, response.size());
		assertEquals(Date.valueOf("2021-11-11"), response.get(0).getRateDate());

	}

	@Test
	public void testFindAllRatesBySort() {
		Sort sort = Sort.by("rateDate");
		when(repository.findAll(sort)).thenReturn(dailyRatePage);
		when(mapper.toDto(currencyDailyRateList.get(0))).thenReturn(rateAUDDto);
		when(mapper.toDto(currencyDailyRateList.get(1))).thenReturn(rateUSDDto);

		List<CurrencyDailyRateDto> response = rateService.findAllRatesBySort("rateDate");
		assertEquals(2, response.size());
		assertEquals(Date.valueOf("2021-11-11"), response.get(0).getRateDate());

	}

	@Test
	public void testFindByRateDate() {
		when(repository.findByRateDate(Date.valueOf("2021-11-11"))).thenReturn(currencyDailyRateListForDate);
		when(mapper.toDto(currencyDailyRateList.get(0))).thenReturn(rateAUDDto);

		List<CurrencyDailyRateDto> response = rateService.findByRateDate(Date.valueOf("2021-11-11"));
		assertEquals(1, response.size());
		assertEquals(Date.valueOf("2021-11-11"), response.get(0).getRateDate());

	}

	@Test
	public void testFindByRateDateAndSourceCurrency() {
		when(repository.findByRateDateAndSourceCurrency(Date.valueOf("2021-11-11"), "AUD"))
				.thenReturn(currencyDailyRateList.get(0));
		when(mapper.toDto(currencyDailyRateList.get(0))).thenReturn(rateAUDDto);

		CurrencyDailyRateDto response = rateService.findByRateDateAndSourceCurrency(Date.valueOf("2021-11-11"), "AUD");
		assertEquals(Date.valueOf("2021-11-11"), response.getRateDate());
		assertEquals("AUD", response.getSourceCurrency());

	}

	@Test
	public void testFindAllCurrencies() {
		when(repository.findDistinctCurrencies()).thenReturn(currencyList);

		List<String> response = rateService.findAllCurrencies();
		assertEquals(3, response.size());

	}

}
