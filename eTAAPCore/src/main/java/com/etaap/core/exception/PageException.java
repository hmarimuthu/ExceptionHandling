package com.etaap.core.exception;

/**
 * Custom exception thrown by {@link DateUtil}.
 * 
 * @author eTouch Systems Corporation
 * @version 1.0
 * 
 */
public class PageException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 905820338501598364L;

	/** The message. */
	private String message;

	/**
	 * Instantiates a new page exception.
	 * 
	 * @param message
	 *            the message
	 */
	public PageException(String message) {
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
