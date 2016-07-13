/**
 * 
 */
package com.etaap.defect.tools.jira;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.etaap.core.resources.KDConstants;

/**
 * @author etouch
 *
 */
public enum JiraDefectStatusMap {

	NEW("New", KDConstants.NEW_DEFECT.getValue()), IN_PROGRESS("In Progress",
			KDConstants.UPDATE_DEFECT.getValue()), DONE("Closed",
					KDConstants.CLOSE_DEFECT.getValue()), REOPEN("Reopened", KDConstants.REOPEN_DEFECT.getValue());

	private final String jiraStatusName;

	private final String commonStatusName;

	private static final Map<String, String> defectStatusMap = Collections.unmodifiableMap(initializeMapping());

	JiraDefectStatusMap(String jiraStatusName, String commonStatusName) {
		this.jiraStatusName = jiraStatusName;
		this.commonStatusName = commonStatusName;
	}

	/**
	 * @return the jiraStatusName
	 */
	public String getJiraStatusName() {
		return jiraStatusName;
	}

	/**
	 * @return the commonStatusName
	 */
	public String getCommonStatusName() {
		return commonStatusName;
	}

	private static Map<String, String> initializeMapping() {
		Map<String, String> statusMap = new HashMap<>();

		for (JiraDefectStatusMap jiraDefectStatusMap : JiraDefectStatusMap.values()) {
			statusMap.put(jiraDefectStatusMap.jiraStatusName, jiraDefectStatusMap.commonStatusName);
		}

		return statusMap;
	}

	public static String getCommonStatusName(String jiraStatusName) {

		if (defectStatusMap.containsKey(jiraStatusName))
			return defectStatusMap.get(jiraStatusName);

		return null;

	}
}
