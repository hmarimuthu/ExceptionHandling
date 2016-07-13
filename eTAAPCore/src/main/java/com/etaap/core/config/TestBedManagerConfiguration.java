package com.etaap.core.config;

import java.io.InputStream;
import java.util.HashMap;

import org.apache.commons.logging.Log;

import com.etaap.common.util.LogUtil;
import com.etaap.reader.ConfigurationReader;

/**
 * public class TestBedManagerConfiguration creates testBedss based on
 * configuration input.
 * 
 * @author eTouch Systems Corporation
 * @version 1.0
 * 
 */
public class TestBedManagerConfiguration {

	/** The instance. */
	public static TestBedManagerConfiguration INSTANCE;

	/** The test types. */
	private String[] testTypes;

	/** Path of the file which has set of Keywords to execute testcases */
	private String keywordDrivenFilePath;

	/** To Configure XLS test data. */
	private HashMap<String, String> xlsDataConfig;

	/** The web config. */
	private WebConfig webConfig;

	/** The ip stream. */
	protected static InputStream ipStream = null;

	/** The defect config. */
	private DefectConfig defectConfig;

	/** The testng config. */
	private TestngConfig testngConfig;

	/** The db config. */
	private DBConfig dbConfig;

	/** The video config. */
	private VideoConfig videoConfig;

	/** The is set. */
	private static boolean isSet = false;

	/** The log. */
	static Log log = LogUtil.getLog(TestBedManagerConfiguration.class);

	private String defectManagementTool;

	private JiraConfig jiraConfig;

	private RallyConfig rallyConfig;

	/**
	 * Instantiates a new test bed manager configuration.
	 */
	private TestBedManagerConfiguration() {
	}

	/**
	 * Gets the single instance of TestBedManagerConfiguration.
	 * 
	 * @return single instance of TestBedManagerConfiguration
	 */
	public static TestBedManagerConfiguration getInstance() {
		if (INSTANCE == null) {
			isSet = true;
			INSTANCE = ConfigurationReader.readConfig(ipStream);

		}

		return INSTANCE;
	}

	/**
	 * Checks if is sets the.
	 * 
	 * @return true, if is sets the
	 */
	public boolean isSet() {
		return isSet;
	}

	/**
	 * Sets the sets the.
	 * 
	 * @param isSet
	 *            the new sets the
	 */
	public void setSet(boolean isSet) {
		this.isSet = isSet;
	}

	/**
	 * Gets the web config.
	 * 
	 * @return the web config
	 */
	public WebConfig getWebConfig() {
		return webConfig;
	}

	/**
	 * Sets the web config.
	 * 
	 * @param webConfig
	 *            the new web config
	 */
	public void setWebConfig(WebConfig webConfig) {
		this.webConfig = webConfig;
	}

	/**
	 * Gets the test types.
	 * 
	 * @return the test types
	 */
	public String[] getTestTypes() {
		return testTypes;
	}

	/**
	 * Sets the test types.
	 * 
	 * @param testTypes
	 *            the new test types
	 */
	public void setTestTypes(String[] testTypes) {
		this.testTypes = testTypes;
	}

	/**
	 * Sets the ip stream.
	 * 
	 * @param ipStream
	 *            the new ip stream
	 */
	public static void setIpStream(InputStream ipStream) {
		TestBedManagerConfiguration.ipStream = ipStream;
	}

	/**
	 * Gets the defect config.
	 * 
	 * @return the defect config
	 */
	public DefectConfig getDefectConfig() {
		return defectConfig;
	}

	/**
	 * Sets the defect config.
	 * 
	 * @param defectConfig
	 *            the new defect config
	 */
	public void setDefectConfig(DefectConfig defectConfig) {
		this.defectConfig = defectConfig;
	}

	/**
	 * Gets the testng config.
	 * 
	 * @return the testng config
	 */
	public TestngConfig getTestngConfig() {
		return testngConfig;
	}

	/**
	 * Sets the testng config.
	 * 
	 * @param testngConfig
	 *            the new testng config
	 */
	public void setTestngConfig(TestngConfig testngConfig) {
		this.testngConfig = testngConfig;
	}

	/**
	 * Gets the db config.
	 * 
	 * @return the db config
	 */
	public DBConfig getDbConfig() {
		return dbConfig;
	}

	/**
	 * Sets the db config.
	 * 
	 * @param dbConfig
	 *            the new db config
	 */
	public void setDbConfig(DBConfig dbConfig) {
		this.dbConfig = dbConfig;
	}

	/**
	 * Gets the xls data config.
	 * 
	 * @return the xls data config
	 */
	public HashMap<String, String> getXlsDataConfig() {
		return xlsDataConfig;
	}

	/**
	 * Sets the xls data config.
	 * 
	 * @param xlsDataConfig
	 *            the xls data config
	 */
	public void setXlsDataConfig(HashMap<String, String> xlsDataConfig) {
		this.xlsDataConfig = xlsDataConfig;
	}

	public String getKeywordDrivenFilePath() {
		return keywordDrivenFilePath;
	}

	public void setKeywordDrivenFilePath(String keywordDrivenFilePath) {
		this.keywordDrivenFilePath = keywordDrivenFilePath;
	}

	/**
	 * Gets the video config.
	 * 
	 * @return the video config
	 */
	public VideoConfig getVideoConfig() {
		return videoConfig;
	}

	/**
	 * Sets the video config.
	 * 
	 * @param videoConfig
	 *            the new video config
	 */
	public void setVideoConfig(VideoConfig videoConfig) {
		this.videoConfig = videoConfig;
	}

	/**
	 * @return the defectManagementTool
	 */
	public String getDefectManagementTool() {
		return defectManagementTool;
	}

	/**
	 * @param defectManagementTool
	 *            the defectManagementTool to set
	 */
	public void setDefectManagementTool(String defectManagementTool) {
		this.defectManagementTool = defectManagementTool;
	}

	/**
	 * @return the jiraConfig
	 */
	public JiraConfig getJiraConfig() {
		return jiraConfig;
	}

	/**
	 * @param jiraConfig
	 *            the jiraConfig to set
	 */
	public void setJiraConfig(JiraConfig jiraConfig) {
		this.jiraConfig = jiraConfig;
	}

	/**
	 * @return the rallyConfig
	 */
	public RallyConfig getRallyConfig() {
		return rallyConfig;
	}

	/**
	 * @param rallyConfig
	 *            the rallyConfig to set
	 */
	public void setRallyConfig(RallyConfig rallyConfig) {
		this.rallyConfig = rallyConfig;
	}

}
