/*
 * 
 */
package com.etaap.defect.tools.resources;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.etaap.defect.tools.jira.Jira;
import com.etaap.defect.tools.rally.Rally;
import com.etaap.manage.defect.DefectTool;

/**
 * The Enum DefectToolType.
 */
public enum DefectToolType {

	/** The rally. */
	RALLY("Rally", new Rally()),

	/** The jira. */
	JIRA("Jira", new Jira());

	/** The toolName. */
	private String toolName;

	private DefectTool defectToolInstance;

	private static final Map<String, DefectTool> defectToolObjectMap = Collections.unmodifiableMap(initializeMapping());

	/**
	 * Instantiates a new defect toolName type.
	 * 
	 * @param toolName
	 *            the toolName
	 */
	DefectToolType(String toolName, DefectTool defectToolInstance) {
		this.toolName = toolName;
		this.defectToolInstance = defectToolInstance;
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
	 * @return the defectToolInstance
	 */
	public DefectTool getDefectToolInstance() {
		return defectToolInstance;
	}

	/**
	 * Checks if is supported.
	 * 
	 * @param toolName
	 *            the toolName name
	 * @return true, if is supported
	 */
	public static boolean isSupported(String toolName) {
		DefectToolType[] dtList = DefectToolType.values();
		for (DefectToolType dt : dtList) {
			if (dt.getToolName().equalsIgnoreCase(toolName)) {
				return true;
			}
		}
		return false;
	}

	// defectToolMap is not requried...over engineering..same thing can be done
	// in getter method

	private static Map<String, DefectTool> initializeMapping() {
		Map<String, DefectTool> objectMap = new HashMap<>();

		for (DefectToolType defectToolType : DefectToolType.values()) {
			objectMap.put(defectToolType.toolName, defectToolType.defectToolInstance);
		}

		return objectMap;
	}

	public static DefectTool getDefectTool(String toolName) {

		if (defectToolObjectMap.containsKey(toolName))
			return defectToolObjectMap.get(toolName);

		return null;

	}

}
