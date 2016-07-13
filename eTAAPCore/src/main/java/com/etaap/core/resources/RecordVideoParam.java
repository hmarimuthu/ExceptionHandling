package com.etaap.core.resources;

/**
 * Values of RecordVideoParam in config file.
 * 
 * @author rThangavelu
 *
 */
public enum RecordVideoParam {

	YES("yes"), NO("no");

	private String value;

	/**
	 * @param value
	 */
	private RecordVideoParam(String value) {
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

}
