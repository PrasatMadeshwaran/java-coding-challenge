package com.crewmeister.cmcodingchallenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom Exception for Record not found.
 * 
 * 
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class RateNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 46033285794456749L;

	public RateNotFoundException() {
		super();
	}

	public RateNotFoundException(String exception) {
		super(exception);
	}

	public RateNotFoundException(Throwable throwable) {
		super(throwable);
	}

	public RateNotFoundException(String exception, Throwable throwable) {
		super(exception, throwable);
	}
}
