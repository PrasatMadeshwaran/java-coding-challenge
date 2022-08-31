package com.crewmeister.cmcodingchallenge.commons.helper;

import com.crewmeister.cmcodingchallenge.commons.model.CurrencyDailyRateDto;
import com.crewmeister.cmcodingchallenge.data.entity.CurrencyDailyRate;
import org.springframework.stereotype.Component;

/**
 * Mapper class for Entity to DTO mapping.
 * 
 * 
 */
@Component
public class CurrencyDailyRateMapper {

	public CurrencyDailyRateDto toDto(CurrencyDailyRate entity) {
		CurrencyDailyRateDto dto = null;
		if (null != entity) {
		    dto = new CurrencyDailyRateDto();
			dto.setId(entity.getId());
			dto.setSourceCurrency(entity.getSourceCurrency());
			dto.setTargetCurrency(entity.getTargetCurrency());
			dto.setRateDate(entity.getRateDate());
			dto.setExchangeRate(entity.getExchangeRate());
			dto.setExchangeRateDifference(entity.getExchangeRateDifference());
			dto.setHolidayStatus(entity.getHolidayStatus());
			dto.setUpdatedUser(entity.getUpdatedUser());
			dto.setUpdatedTimestamp(entity.getUpdatedTimestamp());
		}
		return dto;
	}
}
