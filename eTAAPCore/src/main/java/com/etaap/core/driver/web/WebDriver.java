/*
 * 
 */
package com.etaap.core.driver.web;

import com.etaap.core.TestBed;
import com.etaap.core.driver.DriverBuilder;
import com.etaap.core.exception.DriverException;


/**
 * An Abstract class for webrelated browser drivers.
 */
public abstract class WebDriver  extends DriverBuilder{

	/**
	 * Instantiates a new web driver.
	 *
	 * @param testBed the test bed
	 *
	 */
	public WebDriver(TestBed testBed){
		super(testBed);
		
	}

	
}
