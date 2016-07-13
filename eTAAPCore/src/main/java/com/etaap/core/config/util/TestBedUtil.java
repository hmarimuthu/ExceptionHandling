package com.etaap.core.config.util;

import org.apache.commons.logging.Log;

import com.etaap.common.util.LogUtil;
import com.etaap.core.TestBed;
import com.etaap.core.config.TestBedConfig;
import com.etaap.core.config.TestBedManagerConfiguration;
import com.etaap.core.resources.TestBedType;
import com.etaap.core.resources.TestTypes;
import com.etaap.webui.ITafElementFactory;
import com.etaap.webui.TafElementFactoryManager;
import com.etaap.webui.WebElementFactory;

/**
 * A helper class to interface TestNG concurrency usage.
 *
 * @author <a href="mailto:the_mindstorm@evolva.ro>Alex Popescu</a>
 */
public class TestBedUtil {

	/** The log. */
	static Log log = LogUtil.getLog(TestBedUtil.class);

	/**
	 * @return true if the current thread was created by TestNG.
	 */
	public static String identifyTestBed() {
		String testBedName = null;

		ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();

		int noThreads = currentGroup.activeCount();

		Thread[] lstThreads = new Thread[noThreads];
		currentGroup.enumerate(lstThreads);

		for (int i = 0; i < noThreads; i++) {
			log.debug("Thread No:" + i + " = " + lstThreads[i].getName());

			if (TestBedType.isSupported(lstThreads[i].getName())) {
				testBedName = lstThreads[i].getName();
				break;
			}
		}

		return testBedName;

	}

	public static final TestBed currentTestBedInfo() {
		TestBed testBed = null;
		String testBedName = identifyTestBed();

		if (testBedName != null) {
			testBed = loadTestBedDetails(testBedName);
		}
		return testBed;
	}

	/**
	 * 
	 * Loads Web Testbed details from devConfig.xml
	 *
	 * @param testBedName
	 *            the test bed name
	 * @return the test bed
	 */
	/*
	 * public static TestBed loadTestBedDetails(String testBedName) {
	 * 
	 * TestBed currentTestBed = null; TestBedManagerConfiguration
	 * testBedMgrConfig = TestBedManagerConfiguration .getInstance(); if
	 * (ConfigUtil.isWebTestTypeEnabled()) { for (TestBedConfig tbConfig :
	 * testBedMgrConfig.getWebConfig() .getTestBeds()) {
	 * 
	 * if (tbConfig.getTestBedName().equalsIgnoreCase(testBedName)) {
	 * currentTestBed = copyTestBedDetails(tbConfig); break; } } } if
	 * (ConfigUtil.isMobileTestTypeEnabled()) { if (currentTestBed == null) {
	 * for (TestBedConfig tbConfig : testBedMgrConfig
	 * .getMobileConfig().getTestBeds()) { if
	 * (tbConfig.getTestBedName().equalsIgnoreCase(testBedName)) {
	 * currentTestBed = copyTestBedDetails(tbConfig); break; } } } } return
	 * currentTestBed;` }
	 */

	/**
	 * 
	 * Loads Web Testbed details from devConfig.xml
	 *
	 * @param testBedName
	 *            the test bed name
	 * @return the test bed
	 */
	public static TestBed loadTestBedDetails(String testBedName) {

		TestBed currentTestBed = null;
		TestBedManagerConfiguration testBedMgrConfig = TestBedManagerConfiguration.getInstance();
		ITafElementFactory webElementFactory = new WebElementFactory();

		if (ConfigUtil.isWebTestTypeEnabled()) {
			populateFactoryManager(TestTypes.WEB.getTestType(), webElementFactory);
			for (TestBedConfig tbConfig : testBedMgrConfig.getWebConfig().getTestBeds()) {

				if (tbConfig.getTestBedName().equalsIgnoreCase(testBedName)) {
					currentTestBed = copyTestBedDetails(tbConfig, TestTypes.WEB.getTestType());
					break;
				}
			}
		}

		return currentTestBed;
	}

	private static void populateFactoryManager(String testType, ITafElementFactory factory) {
		TafElementFactoryManager.setFactory(testType, factory);
	}

	/**
	 * Copy test bed details.
	 *
	 * @param testBedConfig
	 *            the test bed config
	 * @return the test bed
	 */
	private static TestBed copyTestBedDetails(TestBedConfig testBedConfig, String testType) {
		TestBed currentTestBed = new TestBed();
		if (testBedConfig != null) {

			currentTestBed.setTestBedName(testBedConfig.getTestBedName());
			currentTestBed.setPlatform(testBedConfig.getPlatform());
			currentTestBed.setBrowser(testBedConfig.getBrowser());
			currentTestBed.setTestBedName(testBedConfig.getTestBedName());
			currentTestBed.setTestbedClassName(testBedConfig.getTestbedClassName());
			currentTestBed.setPort(testBedConfig.getPort());
			currentTestBed.setTestType(testType);

		}

		return currentTestBed;
	}

}
