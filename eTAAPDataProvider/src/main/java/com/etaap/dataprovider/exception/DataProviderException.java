/*
 * 
 */
package com.etaap.dataprovider.exception;

import java.io.PrintWriter;


/**
 * Custom exception thrown if error in reading Test Data.
 * 
 * @author eTouch Systems Corporation
 * @version 1.0
 *
 */
public class DataProviderException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7269203182682595345L;

	/** The message. */
	private String message;
	
	/**
	 * Instantiates a new taf data provider exception.
	 *
	 * @param message the message
	 */
	public DataProviderException(String message) {
		super();
		this.message = message;
	}

	/* (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage() {
		return message;
	}

	/* (non-Javadoc)
	 * @see java.lang.Throwable#printStackTrace(java.io.PrintWriter)
	 */
	@Override
	public void printStackTrace(PrintWriter s) {
		super.printStackTrace(s);
	}

}
