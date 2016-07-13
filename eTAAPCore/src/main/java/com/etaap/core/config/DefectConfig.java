package com.etaap.core.config;

/**
 * The Class DefectConfig.
 */
public class DefectConfig {

	String manageDefect;

	String defectManagementTool;

	JiraConfig jiraConfig;

	RallyConfig rallyConfig;

	public String getManageDefect() {
		return manageDefect;
	}

	public void setManageDefect(String manageDefect) {
		this.manageDefect = manageDefect;
	}

	public String getDefectManagementTool() {
		return defectManagementTool;
	}

	public void setDefectManagementTool(String defectManagementTool) {
		this.defectManagementTool = defectManagementTool;
	}

	public JiraConfig getJiraConfig() {
		return jiraConfig;
	}

	public void setJiraConfig(JiraConfig jiraConfig) {
		this.jiraConfig = jiraConfig;
	}

	public RallyConfig getRallyConfig() {
		return rallyConfig;
	}

	public void setRallyConfig(RallyConfig rallyConfig) {
		this.rallyConfig = rallyConfig;
	}

}