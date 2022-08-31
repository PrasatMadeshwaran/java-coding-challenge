package com.crewmeister.cmcodingchallenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom Exception for Rate File not found.
 * 
 * 
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class RateFileNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1481772055837900978L;

	public RateFileNotFoundException() {
		super();
	}

	public RateFileNotFoundException(String exception) {
		super(exception);
	}

	public RateFileNotFoundException(Throwable throwable) {
		super(throwable);
	}

	public RateFileNotFoundException(String exception, Throwable throwable) {
		super(exception, throwable);
	}
}
