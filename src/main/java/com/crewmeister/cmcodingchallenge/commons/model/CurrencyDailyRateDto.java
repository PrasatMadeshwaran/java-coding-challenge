package com.crewmeister.cmcodingchallenge.commons.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Daily Rate DTO 
 * 
 */

@ToString
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CurrencyDailyRateDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 75571990793535724L;

	@JsonIgnore
	private long id;
	
    @ApiModelProperty(notes = "Source Currency Code",name="sourceCurrency",required=true, value="AUD")
	private String sourceCurrency;
    
    @ApiModelProperty(notes = "Tagrget Currency Code",name="targetCurrency",required=true, value="EUR")
	private String targetCurrency;
    
    @ApiModelProperty(notes = "Date of Exchnage Rate",name="rateDate",required=true, value="2021-11-12")
	private Date rateDate;
    
    @ApiModelProperty(notes = "Exchnage Rate",name="exchangeRate",required=true, value="1.9558")
	private BigDecimal exchangeRate;
    
    @ApiModelProperty(notes = "Exchnage Rate Difference",name="exchangeRateDifference",required=true, value="0.9558")
	private BigDecimal exchangeRateDifference;
    
    @ApiModelProperty(notes = "Holiday Status K = Holiday, N = Non Holiday",name="exchangeRateDifference",required=true, value="N")
	private String holidayStatus;
    
	@JsonIgnore
	private String updatedUser;
	@JsonIgnore
	private Timestamp updatedTimestamp;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSourceCurrency() {
		return sourceCurrency;
	}

	public void setSourceCurrency(String sourceCurrency) {
		this.sourceCurrency = sourceCurrency;
	}

	public String getTargetCurrency() {
		return targetCurrency;
	}

	public void setTargetCurrency(String targetCurrency) {
		this.targetCurrency = targetCurrency;
	}

	public Date getRateDate() {
		return rateDate;
	}

	public void setRateDate(Date rateDate) {
		this.rateDate = rateDate;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public BigDecimal getExchangeRateDifference() {
		return exchangeRateDifference;
	}

	public void setExchangeRateDifference(BigDecimal exchangeRateDifference) {
		this.exchangeRateDifference = exchangeRateDifference;
	}

	public String getHolidayStatus() {
		return holidayStatus;
	}

	public void setHolidayStatus(String holidayStatus) {
		this.holidayStatus = holidayStatus;
	}

	public String getUpdatedUser() {
		return updatedUser;
	}

	public void setUpdatedUser(String updatedUser) {
		this.updatedUser = updatedUser;
	}

	public Timestamp getUpdatedTimestamp() {
		return updatedTimestamp;
	}

	public void setUpdatedTimestamp(Timestamp updatedTimestamp) {
		this.updatedTimestamp = updatedTimestamp;
	}

}
