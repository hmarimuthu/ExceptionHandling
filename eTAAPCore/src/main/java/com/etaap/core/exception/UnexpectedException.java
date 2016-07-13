package com.etaap.core.exception;

public class UnexpectedException extends Exception {

	/**
	 * Instantiates a new taf data provider exception.
	 * 
	 * /** The message.
	 */
	private String message;

	public UnexpectedException(String message) {
		super();
		this.message = message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage() {
		return message;
	}

}
