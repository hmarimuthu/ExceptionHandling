package com.etaap.core.config;

/**
 * The Enum ScreenOrientation.
 */
public enum ScreenOrientation {

	/** The landscape. */
	LANDSCAPE("Landscape"),
	/** The portrait. */
	PORTRAIT("Portrait");

	/** The orientation. */
	private String orientation;

	/**
	 * Instantiates a new screen orientation.
	 *
	 * @param orientation
	 *            the orientation
	 */
	private ScreenOrientation(String orientation) {
		this.orientation = orientation;
	}

	/**
	 * Gets the orientation.
	 *
	 * @return the orientation
	 */
	public String getOrientation() {
		return orientation;
	}

}
