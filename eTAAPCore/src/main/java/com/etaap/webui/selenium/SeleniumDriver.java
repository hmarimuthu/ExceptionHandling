package com.etaap.webui.selenium;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.etaap.common.util.LogUtil;
import com.etaap.core.TestBed;
import com.etaap.core.config.TestBedManagerConfiguration;
import com.etaap.core.config.util.TestBedUtil;
import com.etaap.core.exception.DriverException;
import com.etaap.core.exception.HandleException;

/**
 * The Class SeleniumDriver.
 */
public class SeleniumDriver {

	/** The log. */
	static Log log = LogUtil.getLog(SeleniumDriver.class);

	/**
	 * Builds the fire fox driver.
	 * 
	 * @return the firefox driver
	 * @throws DriverException
	 *             the driver exception
	 */
	@HandleException( expected ={DriverException.class })
	public static FirefoxDriver buildFireFoxDriver()  {
		return new FirefoxDriver();
	}

	/**
	 * Builds the fire fox driver.
	 * 
	 * @param ffProfile
	 *            the ff profile
	 * @return the firefox driver
	 */
	public static FirefoxDriver buildFireFoxDriver(FirefoxProfile ffProfile) {
		return new FirefoxDriver(ffProfile);
	}

	/**
	 * Builds the safari driver.
	 * 
	 * @return the safari driver
	 * @throws DriverException
	 *             the driver exception
	 */
	@HandleException( expected ={DriverException.class })
	public static RemoteWebDriver buildSafariDriver() {

		DesiredCapabilities dc = DesiredCapabilities.safari();
		RemoteWebDriver driver = null;
		try {
			driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), dc);
		} catch (MalformedURLException e) {

			log.debug("MalformedURLException", e);
		}
		return driver;
	}

	/**
	 * Builds the chrome driver.
	 * 
	 * @param file
	 *            the file
	 * @return the chrome driver
	 * @throws DriverException
	 *             the driver exception
	 */
	@HandleException( expected ={DriverException.class })
	public static ChromeDriver buildChromeDriver(File file)  {

		if (file != null) {

			System.setProperty("webdriver.chrome.driver", file.getPath());

		}

		return new ChromeDriver();
	}

	/**
	 * Builds the ie driver.
	 * 
	 * @param file
	 *            the file
	 * @return the internet explorer driver
	 * @throws DriverException
	 *             the driver exception
	 */
	@HandleException( expected ={DriverException.class })
	public static InternetExplorerDriver buildIEDriver(File file) {
		if (file != null) {
			System.out.println("IE Driver File path");
			System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
		}

		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, org.openqa.selenium.UnexpectedAlertBehaviour.ACCEPT);
		cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);

		return new InternetExplorerDriver(cap);

	}

	/**
	 * Builds the url.
	 * 
	 * @param capabilities
	 *            the capabilities
	 * @return the string
	 */
	
	protected static String buildURL(DesiredCapabilities capabilities) {
		String hubLocation = null;
		TestBedManagerConfiguration tbMgrConfig = TestBedManagerConfiguration.getInstance();
		String hub = tbMgrConfig.getWebConfig().getHub();
		String port = tbMgrConfig.getWebConfig().getPort();

		TestBed testBed = TestBedUtil.currentTestBedInfo();

		// defalut hub to localhost
		if (hub == null || hub.isEmpty()) {
			hub = "localhost";
		}

		// default port to 4444
		if (port == null || port.isEmpty()) {
			port = "4444";
		}

		hubLocation = "http://" + hub + ":" + port + "/wd/hub";

		// add proxy setting to the webdriver
		String proxyString = tbMgrConfig.getWebConfig().getProxy();
		String noProxyString = tbMgrConfig.getWebConfig().getNoProxy();
		if (proxyString != null && !proxyString.isEmpty()) {
			Proxy proxysetting = new Proxy();
			proxysetting.setHttpProxy(proxyString).setFtpProxy(proxyString).setSslProxy(proxyString);
			if (noProxyString != null && !proxyString.isEmpty()) {
				proxysetting.setNoProxy(noProxyString);
			}

			capabilities.setCapability(CapabilityType.PROXY, proxysetting);
		}

		return hubLocation;
	}

	/**
	 * Builds the remote driver.
	 * 
	 * @param capabilities
	 *            the capabilities
	 * @return the remote web driver
	 * @throws DriverException
	 *             the driver exception
	 */
	@HandleException( expected ={DriverException.class, MalformedURLException.class })
	public static RemoteWebDriver buildRemoteDriver(DesiredCapabilities capabilities)throws DriverException  {
		RemoteWebDriver driver = null;
			try {
				driver = new RemoteWebDriver(new URL(buildURL(capabilities)), capabilities);
			} catch (MalformedURLException e) {
				throw new DriverException(" Problem in creating Remote Web Driver. Please check the URL " + e.getMessage());
			}
		
		return driver;
	}
}
