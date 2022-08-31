package com.crewmeister.cmcodingchallenge.data.repository;

import com.crewmeister.cmcodingchallenge.data.entity.CurrencyDailyRate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

/**
 * Repository for DAILY_RATE entity
 * 
 */

@Repository
public interface CurrencyDailyRateRepository extends PagingAndSortingRepository<CurrencyDailyRate, Long> {

	@Query("SELECT DISTINCT a.sourceCurrency FROM DailyRate a")
	List<String> findDistinctCurrencies();

	List<CurrencyDailyRate> findByRateDate(Date rateDate);

	CurrencyDailyRate findByRateDateAndSourceCurrency(Date rateDate, String sourceCurrency);
	
	@Query("SELECT a.sourceCurrency as sourceCurrency, MAX(a.rateDate) as rateDate FROM DailyRate a GROUP BY a.sourceCurrency")
	List<Object[]> findMaximumRateDateByCurrency();


}
