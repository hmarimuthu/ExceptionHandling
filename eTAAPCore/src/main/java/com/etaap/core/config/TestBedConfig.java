package com.etaap.core.config;

/**
 * The Class TestBedConfig.
 */
public class TestBedConfig {

	/** The test bed name. */
	private String testBedName;

	/** The testbed class name. */
	private String[] testbedClassName;


	/** The browser. */
	private BrowserConfig browser;

	/** The platform. */
	private PlatformConfig platform;


	/** Port for mobile Config */
	private String port;

	/**
	 * Do Not define this property in DevConfig.yaml This is for internal use
	 * only
	 */
	private Object driver;

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

	public Object getDriver() {
		return driver;
	}

	public void setDriver(Object driver) {
		this.driver = driver;
	}

}
