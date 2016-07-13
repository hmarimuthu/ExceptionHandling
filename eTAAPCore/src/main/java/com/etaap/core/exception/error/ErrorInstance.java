package com.etaap.core.exception.error;

import org.apache.commons.logging.Log;

import com.etaap.common.util.LogUtil;
import com.etaap.core.exception.ExceptionListener;

/**
 * Instance of an Error
 * 
 * @author rThangavelu
 *
 */
public class ErrorInstance {

	static Log log = LogUtil.getLog(ErrorInstance.class);
	EtaapError eTaapError;
	ExceptionListener instance;
	StackTraceElement ste;
	Exception exception;
	Class<?> exClass;

	/**
	 * getter for eTaapError
	 * 
	 * @return
	 */
	public EtaapError geteTaapError() {
		return eTaapError;
	}

	/**
	 * Setter for eTaapError
	 * 
	 * @param eTaapError
	 */
	public void seteTaapError(EtaapError eTaapError) {
		this.eTaapError = eTaapError;
	}

	public ExceptionListener getInstance() {
		return instance;
	}

	public void setInstance(ExceptionListener instance) {
		this.instance = instance;
	}

	public StackTraceElement getSte() {
		return ste;
	}

	public void setSte(StackTraceElement ste) {
		this.ste = ste;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception e) {
		this.exception = e;
	}

	public void setExceptionClass(Class<?> exClass) {
		this.exClass = exClass;
	}

	public Class<?> getExceptionClass() {
		return exClass;
	}

}
