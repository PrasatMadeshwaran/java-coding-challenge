package com.crewmeister.cmcodingchallenge.data.service;

import com.crewmeister.cmcodingchallenge.commons.helper.CurrencyDailyRateMapper;
import com.crewmeister.cmcodingchallenge.commons.model.CurrencyDailyRateDto;
import com.crewmeister.cmcodingchallenge.data.entity.CurrencyDailyRate;
import com.crewmeister.cmcodingchallenge.data.repository.CurrencyDailyRateRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Data service Implementation for Daily Rates CRUD operation.
 * 
 * Handles Caching of specific key searches
 * 
 */
@Service
public class DailyCurrencyRateServiceImpl implements DailyCurrencyRateService {

	private static final Logger LOGGER = LogManager.getLogger(DailyCurrencyRateServiceImpl.class.getName());

	@Autowired
	private CurrencyDailyRateRepository currencyDailyRateRepository;

	@Autowired
	private CurrencyDailyRateMapper mapper;
	
	/**
	 * Function saves all rates, read from Exchange rate file.
	 * 
	 * On every load it evicts all of old caching data.
	 * 
	 * @param rateList<DailyRate>
	 * 			List of <DailyRate> entity
	 * 
	 * @return List<DailyRate> 
	 * 		 Successful saved records.
	 */
	@Override
	@CacheEvict(value = "ratesCache", allEntries = true)
	public List<CurrencyDailyRate> saveAllRates(List<CurrencyDailyRate> rateList) {
		LOGGER.info("Flushing batchSize" + rateList.size());
		Iterable<CurrencyDailyRate> response = currencyDailyRateRepository.saveAll(rateList);

		List<CurrencyDailyRate> entityList = new ArrayList<CurrencyDailyRate>();
		response.forEach(entityList::add);
		return entityList;
	}

	/**
	 * Function to fetch all available currencies.
	 * 
	 * 
	 * @return List<String> 
	 * 		 List of currency codes.
	 */
	@Override
	public List<String> findAllCurrencies() {
		return currencyDailyRateRepository.findDistinctCurrencies();
	}

	/**
	 * Function to fetch all available rates.
	 * 
	 * 
	 * @return List<DailyRateDto> 
	 * 		 List of <DailyRateDto> DTOs.
	 */
	@Override
	public List<CurrencyDailyRateDto> findAllRates() {
		Iterable<CurrencyDailyRate> dailyRateList = currencyDailyRateRepository.findAll();
		List<CurrencyDailyRate> entityList = new ArrayList<CurrencyDailyRate>();
		dailyRateList.forEach(entityList::add);
		return entityList.stream().map(mapper::toDto).collect(Collectors.toList());
	}
	

	/**
	 * Function to fetch all available rates by pagination.
	 * 
	 * @param page
	 * 		 Integer page number
	 * 
	 * @param size
	 * 		 Integer page size
	 * 
	 * @param sort
	 * 		 String field name
	 * 
	 * @return List<DailyRateDto> 
	 * 		 List of <DailyRateDto> DTOs.
	 */
	@Override
	public List<CurrencyDailyRateDto> findAllRatesByPage(int page, int size, String sort) {
		Pageable paging = PageRequest.of(page, size, Sort.by(sort));
		Page<CurrencyDailyRate> dailyRateList = currencyDailyRateRepository.findAll(paging);

		return dailyRateList.stream().map(mapper::toDto).collect(Collectors.toList());
	}

	/**
	 * Function to fetch all available rates with sorting.
	 * 
	 * @param sort
	 * 		 String field name
	 * 
	 * @return List<DailyRateDto> 
	 * 		 List of <DailyRateDto> DTOs.
	 */
	@Override
	public List<CurrencyDailyRateDto> findAllRatesBySort(String sort) {
		Sort sortOrder = Sort.by(sort);
		Iterable<CurrencyDailyRate> list = currencyDailyRateRepository.findAll(sortOrder);
		List<CurrencyDailyRate> entityList = new ArrayList<CurrencyDailyRate>();
		list.forEach(entityList::add);

		return entityList.stream().map(mapper::toDto).collect(Collectors.toList());
	}
	

	/**
	 * Function to fetch rates for requested date.
	 * On 1st request it caches based on date as key.
	 * 
	 * @param date
	 * 		 <Date> rate date
	 * 
	 * @return List<DailyRateDto> 
	 * 		 List of <DailyRateDto> DTOs.
	 */
	@Override
	@Cacheable(value = "ratesCache", key = "#date")
	public List<CurrencyDailyRateDto> findByRateDate(Date date) {
		LOGGER.info("Getting Rate from DB with Date {}.", date);
		List<CurrencyDailyRate> currencyDailyRateList = currencyDailyRateRepository.findByRateDate(date);

		return currencyDailyRateList.stream().map(mapper::toDto).collect(Collectors.toList());
	}

	/**
	 * Function to fetch rates for requested date and currency.
	 * On 1st request it caches based on date and currency code as key.
	 * 
	 * @param rateDate
	 * 		 <Date> rate date
	 * 
	 * @param sourceCurrency
	 * 		 <String> Currency code
	 * 
	 * @return List<DailyRateDto> 
	 * 		 List of <DailyRateDto> DTOs.
	 */
	@Override
	@Cacheable(value = "ratesCache", key = "{#rateDate, #sourceCurrency}")
	public CurrencyDailyRateDto findByRateDateAndSourceCurrency(Date rateDate, String sourceCurrency) {
		LOGGER.info("Getting Rate from DB with Date {}. and Currency {}.", rateDate, sourceCurrency);
		CurrencyDailyRate rateEntity = currencyDailyRateRepository.findByRateDateAndSourceCurrency(rateDate, sourceCurrency);
		return mapper.toDto(rateEntity);

	}

	@Override
	public List<Object[]> findMaximumRateDateByCurrency() {
		return currencyDailyRateRepository.findMaximumRateDateByCurrency();
	}

}
