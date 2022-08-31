package com.crewmeister.cmcodingchallenge.data.service;

import com.crewmeister.cmcodingchallenge.commons.model.CurrencyDailyRateDto;
import com.crewmeister.cmcodingchallenge.data.entity.CurrencyDailyRate;

import java.sql.Date;
import java.util.List;

/**
 * Data service for Daily Rates CRUD operation.
 * 
 */
public interface DailyCurrencyRateService {

	public List<CurrencyDailyRate> saveAllRates(List<CurrencyDailyRate> rateList);

	public List<CurrencyDailyRateDto> findAllRates();

	public List<CurrencyDailyRateDto> findAllRatesByPage(int page, int size, String sort);

	public List<CurrencyDailyRateDto> findAllRatesBySort(String sort);

	public List<CurrencyDailyRateDto> findByRateDate(Date date);

	public CurrencyDailyRateDto findByRateDateAndSourceCurrency(Date rateDate, String sourceCurrency);

	public List<String> findAllCurrencies();
	
	public List<Object[]> findMaximumRateDateByCurrency();

}
