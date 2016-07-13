package com.etaap.core;

import org.apache.commons.logging.Log;
import org.testng.TestNG;

import com.etaap.common.util.LogUtil;

/**
 * To execute POM testcases
 * @author rThangavelu
 *
 */
public class TestcaseExecutor extends TestNG{
	
	/** The log. */
	static Log log = LogUtil.getLog(TestBedManager.class);
	
	/**
	 * Execute test ng.
	 */
	public void executeTestNG() {

		TestNG testNG = TestSuiteManager.INSTANCE.buildTestSuites();

		log.info("Test suites begin to run");
		testNG.run();

	}

}
