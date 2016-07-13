package com.etaap.core;

import java.io.InputStream;

import org.apache.commons.logging.Log;

import com.etaap.common.util.LogUtil;
import com.etaap.core.config.DefectConfig;
import com.etaap.core.config.TestBedManagerConfiguration;
import com.etaap.core.config.TestngConfig;
import com.etaap.core.config.WebConfig;
import com.etaap.core.config.util.ConfigUtil;
import com.etaap.core.current.CurrentDrivers;
import com.etaap.core.current.CurrentTestBeds;
import com.etaap.core.current.CurrentTestCase;
import com.etaap.core.exception.HandleException;
import com.etaap.core.exception.TafDataProviderException;
import com.etaap.manage.defect.IDefectManager;

/**
 * Test Bed Manager loads the profile for the test environment. This class
 * initializes {@link TestBed}
 * 
 * @author eTouch Systems Corporation
 * @version 1.0
 * 
 */
public enum TestBedManager {

	/** The instance to create a Singleton object of TestbedManager. */
	INSTANCE;

	/** The log. */
	static Log log = LogUtil.getLog(TestBedManager.class);

	/**
	 * This object helps to hold information about the testbed which is
	 * executing right now.
	 */

	CurrentTestBeds currentTestBeds = new CurrentTestBeds();

	/**
	 * Maintain list of drivers(browsers) opened and not closed. Every testBed
	 * has its own list of drivers
	 */
	CurrentDrivers currentDrivers = new CurrentDrivers();

	/**
	 * To maintain information of currentTestcase
	 */
	CurrentTestCase currentTestCase = new CurrentTestCase();

	/** The defect. */
	private IDefectManager defect = null;

	/**
	 * Reads configuration file and create profile and sets in test bed.
	 * 
	 * @param ipStream
	 *            the new config
	 * @param currentTestBeds
	 *            the current test beds
	 * @throws Exception
	 *             the exception
	 */
	@HandleException(expected = { TafDataProviderException.class })
	public void setConfig(InputStream ipStream, String[] currentTestBeds) throws TafDataProviderException {

		TestBedManagerConfiguration.setIpStream(ipStream);
		TestBedManagerConfiguration.getInstance();

		if (validateCurrentTestBed(currentTestBeds) && ConfigUtil.isWebTestTypeEnabled()) {

			if (currentTestBeds != null && currentTestBeds.length > 0) {
				TestBedManagerConfiguration.getInstance().getWebConfig().setCurrentTestBeds(currentTestBeds);
			} else {
				throw new TafDataProviderException("Please mention paramter in Maven command for TestBed Name");
			}

		}

	}

	private boolean validateCurrentTestBed(String[] currentTestBeds) {
		boolean result = false;
		if (currentTestBeds.length > 0 && currentTestBeds[0].length() > 0 && !currentTestBeds[0].isEmpty()) {

			result = true;

		}
		return result;
	}

	/**
	 * Returns profile.
	 * 
	 * @return Profile configuration instance.
	 */
	public TestBedManagerConfiguration getProfile() {
		return TestBedManagerConfiguration.getInstance();
	}

	/**
	 * Returns base URL.
	 * 
	 * @return profile base URL
	 */
	public String loadBaseURL() {
		WebConfig webConfig = TestBedManagerConfiguration.getInstance().getWebConfig();
		return webConfig.getURL();
	}

	/**
	 * Returns defect instance.
	 * 
	 * @return DefectManager
	 */
	public IDefectManager getDefect() {
		return this.defect;
	}

	/**
	 * Gets the defect config.
	 * 
	 * @return the defect config
	 */
	public DefectConfig getDefectConfig() {
		return TestBedManagerConfiguration.INSTANCE.getDefectConfig();
	}

	/**
	 * Gets the testng config.
	 * 
	 * @return the testng config
	 */
	public TestngConfig getTestngConfig() {
		return TestBedManagerConfiguration.INSTANCE.getTestngConfig();
	}

	public CurrentTestBeds getCurrentTestBeds() {
		return currentTestBeds;
	}

	void setCurrentTestBeds(CurrentTestBeds currentTestBeds) {
		this.currentTestBeds = currentTestBeds;
	}

	public CurrentDrivers getCurrentDrivers() {
		return currentDrivers;
	}

	void setCurrentDrivers(CurrentDrivers currentDrivers) {
		this.currentDrivers = currentDrivers;
	}

	public CurrentTestCase getCurrentTestCase() {
		return currentTestCase;
	}

	void setCurrentTestCase(CurrentTestCase currentTestCase) {
		this.currentTestCase = currentTestCase;
	}

}
