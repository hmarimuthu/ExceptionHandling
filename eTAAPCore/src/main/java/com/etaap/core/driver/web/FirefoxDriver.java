/**
 * 
 **/
package com.etaap.core.driver.web;

import java.net.MalformedURLException;

import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.etaap.core.TestBed;
import com.etaap.core.config.util.ConfigUtil;
import com.etaap.core.exception.DriverException;
import com.etaap.core.exception.HandleException;
import com.etaap.webui.selenium.SeleniumDriver;

/**
 * Helps to build Firefox Driver firefox profile is not included yet.
 * 
 * @author eTouch
 */
public class FirefoxDriver extends WebDriver {

	/**
	 * Instantiates a new firefox driver.
	 * 
	 * @param testBed
	 *            the test bed
	 * @throws DriverException
	 *             the driver exception
	 */
	public FirefoxDriver(TestBed testBed) throws DriverException {
		super(testBed);
	}

	/**
	 * Builds Firefox Driver according to the given configuration values in
	 * config.yml
	 * 
	 * @throws DriverException
	 *             the driver exception
	 */
	@Override
	@HandleException(expected = { DriverException.class, MalformedURLException.class })
	public void buildDriver() throws DriverException {

		if (ConfigUtil.isLocalEnv(testBed.getTestBedName())) {
			// if tool is given devConfig.yml as Selenium, then create a
			// selenium FireFox driver
			if (ConfigUtil.isSelenium()) {
				driver = SeleniumDriver.buildFireFoxDriver();
			}
		} else if (ConfigUtil.isRemoteEnv(testBed.getTestBedName())) {

			if (ConfigUtil.isSelenium()) {

				FirefoxProfile ffProfile = new FirefoxProfile();
				ffProfile.setEnableNativeEvents(false);
				capabilities = DesiredCapabilities.firefox();
				capabilities.setCapability(org.openqa.selenium.firefox.FirefoxDriver.PROFILE, testBed);
				driver = SeleniumDriver.buildRemoteDriver(capabilities);

			}

		} else if (ConfigUtil.isBrowserStackEnv(testBed.getTestBedName())) {
			capabilities = DesiredCapabilities.firefox();
			buildBrowserstackCapabilities();
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.etaap.core.driver.DriverBuilder#getDriver()
	 **/
	@Override
	public Object getDriver() {
		return driver;
	}
}