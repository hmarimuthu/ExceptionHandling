/*
 * Builds the IDriver
 */
package com.etaap.core.driver;

import com.etaap.core.TestBed;
import com.etaap.core.exception.DriverException;

/**
 * The Interface IDriver.
 */
public interface IDriver {
	
 /**
 * Builds the driver.
 *
 * @param profile the profile
 * @throws DriverException the driver exception
 */
public void buildDriver(TestBed profile) throws DriverException;
	
	/**
	 * Gets the driver.
	 *
	 * @return the driver
	 */
	public Object getDriver();
}