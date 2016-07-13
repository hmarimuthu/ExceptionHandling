/*
 * 
 */
package com.etaap.core.exception;

/**
 * Custom exception thrown for exception related to list.
 * 
 * @author eTouch Systems Corporation
 * @version 1.0
 *
 */
public class ListException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 964508126899888111L;

	/** The message. */
	private String message;

	/**
	 * Instantiates a new list exception.
	 *
	 * @param message
	 *            the message
	 */
	public ListException(String message) {
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
