package com.etaap.core.resources;

/**
 * Values of TakeScreenShot in config file.
 * 
 * @author rThangavelu
 *
 */
public enum TakeScreenshotParam {

	YES("yes"), NO("no");

	private String value;

	/**
	 * @param value
	 */
	private TakeScreenshotParam(String value) {
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

}
