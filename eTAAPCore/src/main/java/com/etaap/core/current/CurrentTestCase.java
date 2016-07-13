package com.etaap.core.current;


/**
 * This class has information on current Test case
 * @author rThangavelu
 *
 */
public class CurrentTestCase {
	
	String testSuiteName;
	String testBedName;
	String testAction;
	String testMethodName;
	String dataLabel;
	
	
	
	
	public String getTestSuiteName() {
		return testSuiteName;
	}
	public void setTestSuiteName(String testSuiteName) {
		this.testSuiteName = testSuiteName;
	}
	public String getTestBedName() {
		return testBedName;
	}
	public void setTestBedName(String testBedName) {
		this.testBedName = testBedName;
	}
	public String getTestAction() {
		return testAction;
	}
	public void setTestAction(String testAction) {
		this.testAction = testAction;
	}
	public String getTestMethodName() {
		return testMethodName;
	}
	public void setTestMethodName(String testMethodName) {
		this.testMethodName = testMethodName;
	}
	public String getDataLabel() {
		return dataLabel;
	}
	public void setDataLabel(String dataLabel) {
		this.dataLabel = dataLabel;
	}
	
	

}
