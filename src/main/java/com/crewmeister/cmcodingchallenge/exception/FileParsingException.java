package com.crewmeister.cmcodingchallenge.exception;

/**
 * Custom Exception for Rate file processing.
 * 
 */
public class FileParsingException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4307316199339108769L;

	public FileParsingException() {
		super();
	}

	public FileParsingException(String exception) {
		super(exception);
	}

	public FileParsingException(Throwable throwable) {
		super(throwable);
	}

	public FileParsingException(String exception, Throwable throwable) {
		super(exception, throwable);
	}
}
