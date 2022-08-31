package com.crewmeister.cmcodingchallenge.commons.utility;

import com.crewmeister.cmcodingchallenge.exception.FileParsingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Utility class for Date and other operation
 * 
 */

public class CurrencyDailyRateUtiliity {

	private static DateFormat dateFormat = new SimpleDateFormat(CurrencyConstant.DATE_FORMAT);

	private static final Logger LOGGER = LogManager.getLogger(CurrencyDailyRateUtiliity.class.getName());

	private CurrencyDailyRateUtiliity() {

	}

	public static synchronized Date getDateValue(String inputDate) throws FileParsingException {
		Date sqlStartDate = null;
		if (inputDate.trim() != "") {
			try {
				dateFormat.setTimeZone(TimeZone.getTimeZone(CurrencyConstant.TIME_ZONE));
				java.util.Date date = dateFormat.parse(inputDate);
				sqlStartDate = new Date(date.getTime());
			} catch (Exception exception) {
				LOGGER.error(exception);
			}

		}
		return sqlStartDate;
	}

	public static String formatDate(Date inputDate) {
		return dateFormat.format(inputDate);
	}

	public static Date getCurrentDate() {
		dateFormat.setTimeZone(TimeZone.getTimeZone(CurrencyConstant.TIME_ZONE));
		final String gmtTime = dateFormat.format(new java.util.Date());
		return getDateValue(gmtTime);
	}

	public static Date addDays(Date inputDate, int days) {
		Calendar c = Calendar.getInstance();
		c.setTime(inputDate);
		c.add(Calendar.DATE, days);
		return new Date(c.getTimeInMillis());
	}

	public static Date getLastWorkingDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.setTimeZone(TimeZone.getTimeZone(CurrencyConstant.TIME_ZONE));

		int day = cal.get(Calendar.DAY_OF_WEEK);
		switch (day) {
		case Calendar.SATURDAY:
			cal.add(Calendar.DATE, -1);
			return new Date(cal.getTimeInMillis());
		case Calendar.SUNDAY:
			cal.add(Calendar.DATE, -2);
			return new Date(cal.getTimeInMillis());
		default:
			return date;

		}
	}

}
