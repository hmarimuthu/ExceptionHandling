/*
 * 
 */
package com.etaap.core.driver.web;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.logging.Log;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.etaap.common.util.LogUtil;
import com.etaap.core.TestBed;
import com.etaap.core.driver.DriverBuilder;
import com.etaap.core.driver.DriverConstantUtil;
import com.etaap.core.exception.DriverException;
import com.etaap.core.exception.HandleException;
import com.etaap.core.config.util.ConfigUtil;
import com.etaap.webui.selenium.SeleniumDriver;

/**
 * The Class ChromeDriver.
 */
public class ChromeDriver extends DriverBuilder{

	/** The log. */
	private static Log log = LogUtil.getLog(ChromeDriver.class);

	/**
	 * Instantiates a new chrome driver.
	 * @param testBed the test bed
	 * @throws DriverException the driver exception
	 */
	public ChromeDriver(TestBed testBed) throws DriverException {
		super(testBed);
	}
	
	/**
	 * Creates driver for Chrome.
	 * @throws Exception 
	 * @throws DriverException the driver exception
	 */
	@Override
	@HandleException( expected ={ DriverException.class })
	public void buildDriver() throws DriverException 
	{
		if(ConfigUtil.isLocalEnv(testBed.getTestBedName()))
		{
			// if it is a Selenium tool, then create selenium ChromeDriver
			if(ConfigUtil.isSelenium()){
				File chromeDriverFile=getChromeDriverFile();
				log.debug("Found Driver file");
				driver =SeleniumDriver.buildChromeDriver(chromeDriverFile);
			}
		} else if(ConfigUtil.isRemoteEnv(testBed.getTestBedName()))
		{
			if(ConfigUtil.isSelenium()){
				capabilities = DesiredCapabilities.chrome();	
				driver = SeleniumDriver.buildRemoteDriver(capabilities);
			}
		}
		else if(ConfigUtil.isBrowserStackEnv(testBed.getTestBedName()))
		{
			capabilities = DesiredCapabilities.chrome();
			buildBrowserstackCapabilities();
		}
	}
	
	/**
	 * This method returns Driver file for Chrome Driver
	 * If is in mentioned in config.yml then set that as system property
	 * otherwise, use the defaults Chromedriver from library based on the given platform
	 * @return the chrome driver file
	 */
	public File getChromeDriverFile(){
		
		File file;
		if(testBed.getBrowser().getDriverLocation()!=null){			
			file =new File(testBed.getBrowser().getDriverLocation());
		}else{
			if(ConfigUtil.isWindows(testBed))
			{
				file = new File(DriverConstantUtil.CHROME_WINDOWS_DRIVER_FILE);
			}
			else
			{
				file = new File(DriverConstantUtil.CHROME_MAC_DRIVER_FILE);
			}
		}
		return file;
	}

	/* (non-Javadoc)
	 * @see com.etaap.core.driver.DriverBuilder#getDriver()
	 */
	@Override
	public Object getDriver() {
		return driver;
	}
}