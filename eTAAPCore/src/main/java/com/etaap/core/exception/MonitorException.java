/*
 * 
 */
package com.etaap.core.exception;

/**
 * Custom exception thrown by {@link SeleniumMonitor}.
 * 
 * @author eTouch Systems Corporation version 1.0
 *
 */
public class MonitorException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2877956248330753496L;

	/** The message. */
	private String message;

	/**
	 * Instantiates a new monitor exception.
	 *
	 * @param message
	 *            the message
	 */
	public MonitorException(String message) {
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
