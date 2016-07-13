/*
 * 
 */
package com.etaap.core.config.util;

import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.logging.Log;

import com.etaap.common.util.LogUtil;
import com.etaap.core.TestBed;
import com.etaap.core.TestBedManager;
import com.etaap.core.config.TestBedManagerConfiguration;
import com.etaap.core.config.WebConfig;
import com.etaap.core.resources.ManageDefectParam;
import com.etaap.core.resources.PlatformType;
import com.etaap.core.resources.RecordVideoParam;
import com.etaap.core.resources.TakeScreenshotParam;
import com.etaap.core.resources.TestBedType;
import com.etaap.core.resources.TestEnvironmentType;
import com.etaap.core.resources.TestToolType;
import com.etaap.core.resources.TestTypes;

/**
 * Utility to validate/get params of configuration file (DevConfig.yml)
 * 
 * @author rThangavelu
 *
 */
public class ConfigUtil {

	/** The log. */
	static Log log = LogUtil.getLog(ConfigUtil.class);

	/** The tb mgr config. */
	private static com.etaap.core.config.TestBedManagerConfiguration tbMgrConfig = TestBedManagerConfiguration
			.getInstance();

	/** The test web environment. */
	private static String testWebEnvironment = tbMgrConfig.getWebConfig().getTestEnv();

	/**
	 * Added a private construtor for Sonar purpose
	 */
	private ConfigUtil() {

	}

	/**
	 * If it is windows platform then return true.
	 *
	 * @param testBed
	 *            the test bed
	 * @return true, if is windows
	 */
	public static boolean isWindows(TestBed testBed) {

		boolean result = false;

		if (testBed.getPlatform().getName().equalsIgnoreCase(PlatformType.WINDOWS.toString())) {
			result = true;
		}
		return result;
	}

	/**
	 * If it is Mac platform then return true.
	 *
	 * @param testBed
	 *            the test bed
	 * @return true, if is mac
	 */
	public static boolean isMac(TestBed testBed) {

		boolean result = false;

		if (testBed.getPlatform().getName().equalsIgnoreCase(PlatformType.MAC.toString())) {
			result = true;
		}
		return result;
	}

	/**
	 * If it is Linux platform then return true.
	 *
	 * @param testBed
	 *            the test bed
	 * @return true, if is linux
	 */
	public static boolean isLinux(TestBed testBed) {

		boolean result = false;

		if (testBed.getPlatform().getName().equalsIgnoreCase(PlatformType.LINUX.toString())) {
			result = true;
		}
		return result;
	}

	/**
	 * If it is Android platform then return true.
	 *
	 * @param testBed
	 *            the test bed
	 * @return true, if is android
	 */
	public static boolean isAndroid(TestBed testBed) {

		boolean result = false;

		if (testBed.getPlatform().getName().equalsIgnoreCase(PlatformType.ANDROID.toString())) {
			result = true;
		}
		return result;
	}

	/**
	 * If it is iOS platform then return true.
	 *
	 * @param testBed
	 *            the test bed
	 * @return true, if is i os
	 */
	public static boolean isiOS(TestBed testBed) {

		boolean result = false;

		if (testBed.getPlatform().getName().equalsIgnoreCase(PlatformType.IOS.toString())) {
			result = true;
		}
		return result;
	}

	/**
	 * If it is FirefoxOS platform then return true.
	 *
	 * @param testBed
	 *            the test bed
	 * @return true, if is firefox os
	 */
	public static boolean isFirefoxOS(TestBed testBed) {

		boolean result = false;

		if (testBed.getPlatform().getName().equalsIgnoreCase(PlatformType.FIREFOXOS.toString())) {
			result = true;
		}
		return result;
	}

	/**
	 * Checks whether testScripts are going to run again Local Environment or
	 * not.
	 *
	 * @return true, if is local env
	 */
	public static boolean isLocalEnv(String testBedName) {

		boolean result = false;
		if (TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getTestType().equalsIgnoreCase(
				TestTypes.WEB.name()) && TestEnvironmentType.LOCAL.getTestEnv().equals(testWebEnvironment)) {

			result = true;

		}

		return result;
	}

	/**
	 * Checks whether testScripts are going to run again Remote Environment or
	 * not.
	 *
	 * @return true, if is remote env
	 */
	public static boolean isRemoteEnv(String testBedName) {

		boolean result = false;

		if (TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getTestType().equalsIgnoreCase(
				TestTypes.WEB.name()) && TestEnvironmentType.REMOTE.getTestEnv().equals(testWebEnvironment)) {

			result = true;

		}

		return result;
	}

	/**
	 * Checks whether testScripts are going to run again Remote Environment or
	 * not.
	 *
	 * @return true, if is browser stack env
	 */
	public static boolean isBrowserStackEnv(String testBedName) {

		boolean result = false;

		if (TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getTestType().equals(TestTypes.WEB)
				&& TestEnvironmentType.BROWSERSTACK.getTestEnv().equals(testWebEnvironment)) {

			result = true;

		}

		return result;
	}

	/**
	 * Check whether selenium is given for webTesting.
	 *
	 * @return true, if is selenium
	 */
	public static boolean isSelenium() {
		boolean result = false;
		if (tbMgrConfig.getWebConfig().getTool().equalsIgnoreCase(TestToolType.SELENIUM.getTestToolType())) {
			result = true;
		}
		return result;
	}

	/**
	 * To check whether the WebTestType is included or not.
	 *
	 * @return true, if is web test type enabled
	 */
	public static boolean isWebTestTypeEnabled() {
		TestBedManagerConfiguration tbMgrConfig = TestBedManagerConfiguration.getInstance();
		boolean isWebEnabled = false;

		for (String testType : tbMgrConfig.getTestTypes()) {
			if (testType.equalsIgnoreCase(TestTypes.WEB.getTestType())) {
				isWebEnabled = true;
				break;
			}
		}

		return isWebEnabled;
	}

	/**
	 * To check whether the Mobile Test Types is included or not.
	 *
	 * @return true, if is mobile test type enabled
	 */
	public static boolean isMobileTestTypeEnabled() {
		TestBedManagerConfiguration tbMgrConfig = TestBedManagerConfiguration.getInstance();
		boolean isMobileEnabled = false;

		for (String testType : tbMgrConfig.getTestTypes()) {
			if (testType.equalsIgnoreCase(TestTypes.MOBILE.getTestType())) {
				isMobileEnabled = true;
				break;
			}
		}

		return isMobileEnabled;
	}

	/**
	 * To check whether the Safari browser is included in web testbeds or not.
	 *
	 * @return true, if is Safari is added
	 */
	public static boolean isSafariEnabled() {
		TestBedManagerConfiguration tbMgrConfig = TestBedManagerConfiguration.getInstance();
		boolean isSafariEnabled = false;

		for (String browserType : tbMgrConfig.getWebConfig().getCurrentTestBeds()) {
			if (browserType.equalsIgnoreCase(TestBedType.SAFARI.getTestBedName())) {
				isSafariEnabled = true;
				break;
			}
		}

		return isSafariEnabled;
	}

	/**
	 * Check whether DefectManage is on/off
	 * 
	 * @return
	 */
	public static boolean isManageDefectOn() {

		boolean result = false;
		String manageDefectValue = TestBedManagerConfiguration.getInstance().getDefectConfig().getManageDefect();

		if (manageDefectValue.equalsIgnoreCase(ManageDefectParam.ON.name())) {
			result = true;

		}
		return result;

	}

	/**
	 * Check whether VideoRecording is on/off
	 * 
	 * @return
	 */
	public static boolean isVideoRecordingOn() {

		boolean result = false;
		String recordVideo = TestBedManagerConfiguration.getInstance().getVideoConfig().getRecordVideo();

		if (recordVideo.equalsIgnoreCase(RecordVideoParam.YES.toString())) {
			result = true;

		}
		return result;

	}

	/**
	 * Check whether TakeScreenShot is on/off
	 * 
	 * @return
	 */
	public static boolean isTakeScreenShotOn() {

		boolean result = false;
		String takeScreenShot = TestBedManagerConfiguration.getInstance().getVideoConfig().getTakeScreenshot();

		if (takeScreenShot.equalsIgnoreCase(TakeScreenshotParam.YES.toString())) {
			result = true;

		}
		return result;

	}

	/**
	 * 
	 * @return Browser names
	 */
	public static Collection<String> getTestBeds() {

		Collection<String> testBedColls = null;

		if (ConfigUtil.isWebTestTypeEnabled()) {
			WebConfig webConfig = TestBedManagerConfiguration.getInstance().getWebConfig();

			testBedColls = Arrays.asList(webConfig.getCurrentTestBeds());
		}

		return testBedColls;
	}

}
