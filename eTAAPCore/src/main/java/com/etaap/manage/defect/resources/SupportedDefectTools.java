package com.etaap.manage.defect.resources;

/**
 * List of Supported Defect Tools in eTAAP
 * 
 * @author rThangavelu
 *
 */
public enum SupportedDefectTools {

	/** The rally. */
	RALLY("Rally"),

	/** The jira. */
	JIRA("Jira");

	/** The toolName. */
	private String toolName;

	/**
	 * Instantiates a new defect toolName type.
	 * 
	 * @param toolName
	 *            the toolName
	 */
	SupportedDefectTools(String toolName) {
		this.toolName = toolName;
	}

	/**
	 * Gets the toolName.
	 * 
	 * @return the toolName
	 */
	public String getToolName() {
		return toolName;
	}

	/**
	 * Checks if is supported.
	 * 
	 * @param toolName
	 *            the toolName name
	 * @return true, if is supported
	 */
	public static boolean isSupported(String toolName) {
		SupportedDefectTools[] dtList = SupportedDefectTools.values();
		for (SupportedDefectTools dt : dtList) {
			if (dt.getToolName().equalsIgnoreCase(toolName)) {
				return true;
			}
		}
		return false;
	}

}
