package com.crewmeister.cmcodingchallenge.data.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Daily rate table entity.
 * 
 */
@Entity
@Table(name = "DAILY_RATE")
public class CurrencyDailyRate implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2741389845842132037L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "std_currency")
	private String sourceCurrency;
	
	@Column(name = "erx_partner_currency")
	private String targetCurrency;
		
	@Column(name = "rate_date")
	private Date rateDate;
	
	@Column(name = "exchange_rate")
	private BigDecimal exchangeRate;
	
	@Column(name = "bbk_diff")
	private BigDecimal exchangeRateDifference;
	
	@Column(name = "obs_status")
	private String holidayStatus;
	
	@Column(name = "updated_by")
	private String updatedUser;

	@Column(name = "updated_timestamp")
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

	@Override
	public String toString() {
		return "DailyRate [id=" + id + ", sourceCurrency=" + sourceCurrency + ", targetCurrency=" + targetCurrency
				+ ", rateDate=" + rateDate + ", exchangeRate=" + exchangeRate + ", exchangeRateDifference="
				+ exchangeRateDifference + ", holidayStatus=" + holidayStatus + ", updatedUser=" + updatedUser
				+ ", updatedTimestamp=" + updatedTimestamp + "]";
	}

}
