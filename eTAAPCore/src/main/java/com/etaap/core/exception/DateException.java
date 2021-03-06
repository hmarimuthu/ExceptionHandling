/*
 * 
 */
package com.etaap.core.exception;

/**
 * Custom exception thrown by {@link DateUtil}.
 * 
 * @author eTouch Systems Corporation
 * @version 1.0
 *
 */
public class DateException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8725397299202741008L;

	/** The message. */
	private String message;

	/**
	 * Instantiates a new date exception.
	 *
	 * @param message
	 *            the message
	 */
	public DateException(String message) {
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
