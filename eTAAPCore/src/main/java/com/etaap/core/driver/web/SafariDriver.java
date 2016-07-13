package com.etaap.core.driver.web;

import java.io.IOException;
import java.net.MalformedURLException;




import org.apache.commons.logging.Log;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.etaap.common.util.LogUtil;
import com.etaap.core.TestBed;
import com.etaap.core.config.TestBedManagerConfiguration;
import com.etaap.core.driver.DriverBuilder;
import com.etaap.core.exception.DriverException;
import com.etaap.core.exception.HandleException;
import com.etaap.core.config.util.ConfigUtil;
import com.etaap.webui.selenium.SeleniumDriver;

/**
 * The Class SafariDriver.
 */
public class SafariDriver extends DriverBuilder {

	private static Log log = LogUtil.getLog(SafariDriver.class);

	/**
	 * Instantiates a new safari driver.
	 * 
	 * @param testBed
	 *            the test bed
	 * @throws DriverException
	 *             the driver exception
	 */
	public SafariDriver(TestBed testBed)  {
		super(testBed);
	}

	/**
	 * Creates Driver for Safari.
	 * 
	 * @throws DriverException
	 *             the driver exception
	 */
	@Override
	@HandleException( expected ={ DriverException.class, MalformedURLException.class })
	public void buildDriver() throws DriverException {
		if (ConfigUtil.isLocalEnv(testBed.getTestBedName())) {

			// if the tool is mentioned as selenium in devconfig.yml then create
			// a Selenium safari Driver
			if (ConfigUtil.isSelenium()) {

				try {
					Runtime.getRuntime().exec("java -jar " + TestBedManagerConfiguration.INSTANCE.getWebConfig().getSeleniumServerPath());
				} catch (IOException e) {

					log.debug("IOException", e);
				}

				driver = SeleniumDriver.buildSafariDriver();
			}
		} else if (ConfigUtil.isRemoteEnv(testBed.getTestBedName())) {

			if (ConfigUtil.isSelenium()) {
				capabilities = DesiredCapabilities.safari();
				capabilities.setBrowserName("safari");
				if (ConfigUtil.isWindows(testBed)) {
					capabilities.setPlatform(Platform.WINDOWS);
				} else if (ConfigUtil.isMac(testBed)) {
					capabilities.setPlatform(Platform.MAC);
				}
				driver = SeleniumDriver.buildRemoteDriver(capabilities);
			}

		} else if (ConfigUtil.isBrowserStackEnv(testBed.getTestBedName())) {
			capabilities = DesiredCapabilities.safari();
			buildBrowserstackCapabilities();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.etaap.core.driver.DriverBuilder#getDriver()
	 */
	@Override
	public Object getDriver(){
		return driver;
	}

}
