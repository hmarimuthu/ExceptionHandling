package com.etaap.core.exception.error;

/**
 * This class gives information on Error code, Details about the error
 * also, information on how to fix the error.
 * 
 * To Extend to for client support, 
 * On the next version, we can add, Log details on when and how this error 
 * and history of resolution, who handled this issue
 * @author rThangavelu
 *
 */
public class EtaapError {
	
	String errorCode;
	String errorDetails;
	String solution;
	
	
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorDetails() {
		return errorDetails;
	}
	public void setErrorDetails(String errorDetails) {
		this.errorDetails = errorDetails;
	}
	public String getSolution() {
		return solution;
	}
	public void setSolution(String solution) {
		this.solution = solution;
	}
	
	

}
