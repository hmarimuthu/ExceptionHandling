package com.etaap.core;

import org.apache.commons.logging.Log;
import org.openqa.selenium.WebDriver;

import com.etaap.common.util.LogUtil;
import com.etaap.core.config.BrowserConfig;
import com.etaap.core.config.PlatformConfig;

/**
 * This class is primarily responsible for creating {@link WebDriver} This class
 * holds information of testBed.
 *
 * @author eTouch Systems Corporation
 * @version 1.0
 */
public class TestBed {

	/** The log. */
	static Log log = LogUtil.getLog(TestBed.class);

	/** The Constant MaxWaitSeconds. */
	public static final int MAXWAITSECONDS = 60;

	/** The driver. */
	private Object driver = null;

	/** The testbed class name. */
	private String[] testbedClassName;

	/** The test bed name such as IE, Chrome, iPad, Android. */
	private String testBedName;

	/** All the browser related information will be configured here. */
	private BrowserConfig browser;

	/** All the Platform/OS related information will be configured here. */
	private PlatformConfig platform;

	/** Port number has to be mention for parallel Execution */
	private String port;

	private String TestType;

	/**
	 * Gets the test bed name.
	 *
	 * @return the test bed name
	 */
	public String getTestBedName() {
		return testBedName;
	}

	/**
	 * Sets the test bed name.
	 *
	 * @param testBedName
	 *            the new test bed name
	 */
	public void setTestBedName(String testBedName) {
		this.testBedName = testBedName;
	}

	/**
	 * Gets the driver.
	 *
	 * @return the driver
	 */
	public Object getDriver() {
		return driver;
	}

	/**
	 * Sets the driver.
	 *
	 * @param driver
	 *            the new driver
	 */
	public void setDriver(Object driver) {
		this.driver = driver;
	}

	/**
	 * Gets the testbed class name.
	 *
	 * @return the testbed class name
	 */
	public String[] getTestbedClassName() {
		return testbedClassName;
	}

	/**
	 * Sets the testbed class name.
	 *
	 * @param testbedClassName
	 *            the new testbed class name
	 */
	public void setTestbedClassName(String[] testbedClassName) {
		this.testbedClassName = testbedClassName;
	}

	/**
	 * Gets the browser.
	 *
	 * @return the browser
	 */
	public BrowserConfig getBrowser() {
		return browser;
	}

	/**
	 * Sets the browser.
	 *
	 * @param browser
	 *            the new browser
	 */
	public void setBrowser(BrowserConfig browser) {
		this.browser = browser;
	}

	/**
	 * Gets the platform.
	 *
	 * @return the platform
	 */
	public PlatformConfig getPlatform() {
		return platform;
	}

	/**
	 * Sets the platform.
	 *
	 * @param platform
	 *            the new platform
	 */
	public void setPlatform(PlatformConfig platform) {
		this.platform = platform;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getTestType() {
		return TestType;
	}

	public void setTestType(String testType) {
		TestType = testType;
	}

}