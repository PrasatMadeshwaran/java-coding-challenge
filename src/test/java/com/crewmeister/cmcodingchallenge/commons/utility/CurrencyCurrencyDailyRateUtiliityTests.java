package com.crewmeister.cmcodingchallenge.commons.utility;

import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class CurrencyCurrencyDailyRateUtiliityTests {

	@Test
	public void testGetDateValue() {
		Date dateResponse = CurrencyDailyRateUtiliity.getDateValue("2021-10-10");
		LocalDate localDate = dateResponse.toLocalDate();
		assertThat(localDate.getDayOfMonth()).isEqualTo(10);

	}
	
	@Test
	public void testFormatDate() {
		String dateResponse = CurrencyDailyRateUtiliity.formatDate(Date.valueOf("2021-11-11"));
		assertThat(dateResponse).isEqualTo("2021-11-11");

	}
}
