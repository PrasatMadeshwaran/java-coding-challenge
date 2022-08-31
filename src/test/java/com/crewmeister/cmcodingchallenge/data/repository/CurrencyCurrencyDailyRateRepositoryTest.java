package com.crewmeister.cmcodingchallenge.data.repository;

import com.crewmeister.cmcodingchallenge.data.entity.CurrencyDailyRate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CurrencyCurrencyDailyRateRepositoryTest {

	@Autowired
	CurrencyDailyRateRepository currencyDailyRateRepository;

	@BeforeEach
	public void setUp() {
		CurrencyDailyRate rateAUD = new CurrencyDailyRate();
		rateAUD.setSourceCurrency("AUD");
		rateAUD.setRateDate(Date.valueOf("2021-11-11"));
		rateAUD.setExchangeRate(new BigDecimal(0.434));
		rateAUD.setTargetCurrency("EUR");
		rateAUD.setUpdatedTimestamp(new Timestamp(System.currentTimeMillis()));
		rateAUD.setUpdatedUser("DAILY_RATE_TEST");
		currencyDailyRateRepository.save(rateAUD);
	}

	@Test
	public void testSaveRate() {
		CurrencyDailyRate rateAUD = new CurrencyDailyRate();
		rateAUD.setSourceCurrency("AUD");
		rateAUD.setRateDate(Date.valueOf("2021-11-11"));
		rateAUD.setExchangeRate(new BigDecimal(0.434));
		rateAUD.setTargetCurrency("EUR");
		rateAUD.setUpdatedTimestamp(new Timestamp(System.currentTimeMillis()));
		rateAUD.setUpdatedUser("DAILY_RATE_TEST");
		currencyDailyRateRepository.save(rateAUD);

		Iterable<CurrencyDailyRate> rateList = currencyDailyRateRepository.findAll();
		assertThat(rateList).extracting(CurrencyDailyRate::getSourceCurrency).containsOnly("AUD");

	}

    @Test
	public void testFindAllRates() {
		Iterable<CurrencyDailyRate> rateList = currencyDailyRateRepository.findAll();
		assertThat(rateList).hasSize(1);

	}

	@Test
	public void testFindAllCurrencies() {
		List<String> rateList = currencyDailyRateRepository.findDistinctCurrencies();
		assertThat(rateList.get(0)).hasToString("AUD");
	}

	@Test
	public void testFindByRateDate() {
		List<CurrencyDailyRate> rateList = currencyDailyRateRepository.findByRateDate(Date.valueOf("2021-11-11"));
		assertThat(rateList).extracting(CurrencyDailyRate::getRateDate).containsOnly(Date.valueOf("2021-11-11"));
	}

	@Test
	public void testFindByRateDateAndSourceCurrency() {
		CurrencyDailyRate rate = currencyDailyRateRepository.findByRateDateAndSourceCurrency(Date.valueOf("2021-11-11"), "AUD");
		assertThat(rate.getRateDate()).isEqualTo(Date.valueOf("2021-11-11"));
		assertThat(rate.getSourceCurrency()).isEqualTo("AUD");

	}

}
