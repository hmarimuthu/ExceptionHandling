package com.etaap.core.resources;

/**
 * Enum for Manage defect.
 * If Value is on then eTAAP will file/manage defect in the given defectManagement tool
 * If value is off, eTAAP will not file/manage any defect
 * @author rThangavelu
 *
 */
public enum ManageDefectParam {
	
	/** manage defects */
	ON("on", true),

	/** Don't do defect management */
	OFF("off", false);

	private String name;
	
	/** The manageDefect. */
	private boolean manageDefect;



	/**
	 * 
	 * @param name
	 * @param isManage
	 */
	ManageDefectParam(String name, boolean isManage) {
		this.name=name;
		this.manageDefect = isManage;
	}

	/**
	 * Check the given values is supported or not
	 * @param value
	 * @return
	 */
	public static boolean isSupported(String value) {
		ManageDefectParam[] defectParams = ManageDefectParam.values();
		for (ManageDefectParam dt : defectParams) {
			if (dt.name.equalsIgnoreCase(value)) {
				return true;
			}
		}
		return false;
	}

	
	
}
