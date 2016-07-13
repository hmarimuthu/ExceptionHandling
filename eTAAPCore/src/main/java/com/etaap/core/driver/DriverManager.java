package com.etaap.core.driver;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import org.apache.commons.logging.Log;

import com.etaap.common.util.LogUtil;
import com.etaap.core.TestBed;
import com.etaap.core.config.util.TestBedUtil;
import com.etaap.core.driver.web.ChromeDriver;
import com.etaap.core.driver.web.FirefoxDriver;
import com.etaap.core.driver.web.IEDriver;
import com.etaap.core.driver.web.SafariDriver;
import com.etaap.core.exception.DriverException;
import com.etaap.core.exception.ExceptionListener;
import com.etaap.core.exception.HandleException;
import com.etaap.core.exception.handler.ExceptionHandler;
import com.etaap.core.resources.TestBedType;

/**
 * Gets the driver based on the profile information.
 * 
 * @author eTouch Systems Corporation
 * @version 1.0
 */
public class DriverManager implements ExceptionListener {

	/** The log. */
	static Log log = LogUtil.getLog(DriverManager.class);

	/** The driver map. */
	private static HashMap<String, Class<? extends DriverBuilder>> driverMap = null;

	/**
	 * Builds the driver map.
	 */
	private static void buildDriverMap() {
		driverMap = new HashMap<>();
		// Web Browser class
		driverMap.put(TestBedType.CHROME.toString(), ChromeDriver.class);
		driverMap.put(TestBedType.SAFARI.toString(), SafariDriver.class);
		driverMap.put(TestBedType.FIREFOX.toString(), FirefoxDriver.class);
		driverMap.put(TestBedType.INTERNETEXPLORER.toString(), IEDriver.class);

	}

	/**
	 * Creates a TestDFriver according to the specification in testbed.
	 * 
	 * @param testBedName
	 *            the test bed name
	 * @return the driver
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws ClassNotFoundException
	 * @throws Exception
	 * @throws DriverException
	 *             the driver exception
	 */
	@HandleException(expected = { IllegalStateException.class, DriverException.class, ClassNotFoundException.class,
			NoSuchMethodException.class, SecurityException.class, InstantiationException.class,
			IllegalAccessException.class, InvocationTargetException.class })
	public DriverBuilder buildDriver(String testBedName) {
		DriverBuilder driverBuilder = null;
		try {
			buildDriverMap();

			Class<? extends DriverBuilder> clazz = driverMap.get(testBedName.toLowerCase());
			TestBed testBed = TestBedUtil.loadTestBedDetails(testBedName);

			driverBuilder = this.createDriver(clazz.getName(), testBed);
		} catch (Exception e) {

			ExceptionHandler ex = new ExceptionHandler();
			ex.handleit(this, e);
		}

		return driverBuilder;
	}

	/**
	 * Creates the driver.
	 * 
	 * @param className
	 *            the class name
	 * @return the driver builder
	 * @throws ClassNotFoundException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws DriverException
	 *             the driver exception
	 */
	@HandleException(expected = { DriverException.class, ClassNotFoundException.class, NoSuchMethodException.class,
			SecurityException.class, InstantiationException.class, IllegalAccessException.class,
			IllegalArgumentException.class, InvocationTargetException.class, IllegalStateException.class })
	private DriverBuilder createDriver(String className, TestBed testBed) throws Exception {
		Class<?> clazz;
		DriverBuilder driverObject;

		clazz = Class.forName(className);

		Constructor<?> ctor = clazz.getConstructor(TestBed.class);
		driverObject = (DriverBuilder) ctor.newInstance(testBed);
		driverObject.buildDriver();

		return driverObject;
	}
}