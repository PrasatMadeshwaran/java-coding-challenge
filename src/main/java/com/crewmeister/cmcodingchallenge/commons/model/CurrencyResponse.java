package com.crewmeister.cmcodingchallenge.commons.model;

import org.springframework.http.HttpStatus;

public class CurrencyResponse {

	private String status;

	private String message;

	private Object data;

	public CurrencyResponse(HttpStatus httpStatus, String message) {
		super();
		this.status = httpStatus.name();
		this.message = message;
	}

	public CurrencyResponse(HttpStatus httpStatus, String message, Object data) {
		this(httpStatus, message);
		this.data = data;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
