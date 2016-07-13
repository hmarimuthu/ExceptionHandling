package com.etaap.manage.defect;

/**
 * Defect Title has values to build a defect title.
 * 
 * @author rThangavelu
 *
 */

public class DefectTitle {

	private String testBedName;
	private String testSuiteName;
	private String testCaseName;
	private String testDataLabel;
	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTestBedName() {
		return testBedName.trim();
	}

	public void setTestBedName(String testBedName) {
		this.testBedName = testBedName;
	}

	public String getTestSuiteName() {
		return testSuiteName.trim();
	}

	public void setTestSuiteName(String testSuiteName) {
		this.testSuiteName = testSuiteName;
	}

	public String getTestCaseName() {
		return testCaseName.trim();
	}

	public void setTestCaseName(String testCaseName) {
		this.testCaseName = testCaseName;
	}

	public String getTestDataLabel() {
		return testDataLabel.trim();
	}

	public void setTestDataLabel(String testDataLabel) {
		this.testDataLabel = testDataLabel;
	}

}
