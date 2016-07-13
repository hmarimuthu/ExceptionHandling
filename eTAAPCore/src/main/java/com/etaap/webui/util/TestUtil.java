package com.etaap.webui.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;

import com.etaap.common.util.CommonUtil;
import com.etaap.common.util.LogUtil;
import com.etaap.core.TestBedManager;
import com.etaap.core.config.TestBedManagerConfiguration;
import com.etaap.core.config.util.ConfigUtil;
import com.etaap.core.driver.DriverManager;
import com.etaap.core.exception.DriverException;
import com.etaap.core.exception.ExceptionListener;
import com.etaap.core.exception.HandleException;
import com.etaap.core.exception.handler.ExceptionHandler;

import io.appium.java_client.AppiumDriver;

/**
 * The Class TestUtil.
 */
public class TestUtil implements ExceptionListener {

	/** The log. */
	static Log log = LogUtil.getLog(TestUtil.class);
	public static String propFilePath = "src//test//resources//test.properties";

	@HandleException(expected = { FileNotFoundException.class, IOException.class, DriverException.class })
	public static void initialize() {
		CommonUtil.sop(" On initialize");
		String configFileName = "src//test//resources//testConfig.yml";

		InputStream in = null;
		String[] tArray = { "", "" };
		try {
			in = convertFileToInputStream(configFileName);
			CommonUtil.sop(" Dev config file input stream is ready");
			TestBedManager.INSTANCE.setConfig(in, tArray);
		} catch (Exception e) {
			ExceptionHandler ex = new ExceptionHandler();
			ex.handleit(TestUtil.class, e);

		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
				ExceptionHandler ex = new ExceptionHandler();
				ex.handleit(TestUtil.class, e);

			}

		}

		// TestBedManager.INSTANCE.executeTestNG();
	}

	/**
	 * This method helps to convert from file to InputStream
	 * 
	 * @param fileName
	 * @return
	 * @throws DriverException
	 */

	@HandleException(expected = { FileNotFoundException.class, DriverException.class })
	private static InputStream convertFileToInputStream(String fileName) throws DriverException {
		InputStream ipStream = null;
		if (fileName != null) {

			try {
				ipStream = new FileInputStream(new File(fileName));
			} catch (FileNotFoundException e) {
				ExceptionHandler ex = new ExceptionHandler();
				ex.handleit(TestUtil.class, e);

			}
		} else {
			log.error(" File name is null - " + fileName);
			throw new DriverException("failed to read profile configuration/TestNG, file name is missing");
		}
		return ipStream;
	}

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {

	}

	/**
	 * Pick config file.
	 * 
	 * @param currentEnvironment
	 *            the current environment
	 * @return the string
	 */
	public static String pickConfigFile(String currentEnvironment) {

		String defaultConfigFile = "devConfig.yml";
		if (currentEnvironment != null && currentEnvironment.length() > 0) {
			return currentEnvironment + "Config.yml";
		} else {
			return defaultConfigFile;
		}
	}

	/**
	 * Creates the driver.
	 * 
	 * @param create
	 *            the create
	 * @return the web driver
	 * @throws DriverException
	 *             the driver exception
	 */
	public static WebDriver createDriver(ITestContext context, boolean isCreate) throws DriverException {
		String testBedName = context.getCurrentXmlTest().getAllParameters().get("testBedName");

		WebDriver driverObj = (WebDriver) TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getDriver();
		if (driverObj == null && isCreate) {
			driverObj = (WebDriver) new DriverManager().buildDriver(testBedName).getDriver();
			TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).setDriver(driverObj);
		}

		return driverObj;

	}

	@HandleException(expected = { IOException.class })
	public static Properties loadProps(String propFilePath) {
		Properties prop = new Properties();
		InputStream inStream = null;

		try {

			inStream = new FileInputStream(propFilePath);

			// load properties from the file to the props object.
			prop.load(inStream);

		} catch (IOException e) {
			ExceptionHandler ex = new ExceptionHandler();
			ex.handleit(TestUtil.class, e);
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
					ExceptionHandler ex = new ExceptionHandler();
					ex.handleit(TestUtil.class, e);
				}
			}

		}

		return prop;
	}

	/**
	 * Close driver.
	 * 
	 * @param driverObj
	 *            the driver obj
	 */
	@HandleException(expected = { Exception.class })
	public static void closeDriver(WebDriver driverObj) {
		log.debug("After Method SOP");
		if (driverObj != null) {
			try {
				driverObj.close();
				Thread.sleep(5000);
				driverObj.quit();

			} catch (Exception e) {
				ExceptionHandler ex = new ExceptionHandler();
				ex.handleit(TestUtil.class, e);
			}

		}

		if (ConfigUtil.isSafariEnabled())
			try {
				shutdownSeleniumServer();
			} catch (IOException e) {
				ExceptionHandler ex = new ExceptionHandler();
				ex.handleit(TestUtil.class, e);
			}
	}

	public static void closeMobileDriver(AppiumDriver driverObj) {
		if (driverObj != null) {
			driverObj.quit();
		}
	}

	public static String formatJsonString(String str) {
		str = str.replace("\"", "\\\"");
		str = str.replace("\n", "\\n");
		return str;
	}

	public static void copyFile(File source, File destination) throws IOException {

		Files.copy(source.toPath(), destination.toPath());

	}

	public static boolean isCopyToServer() {
		return TestBedManagerConfiguration.INSTANCE.getVideoConfig().getCopyToServer().equalsIgnoreCase("yes");

	}

	public static void shutdownSeleniumServer() throws IOException {
		URL seleniumHub = new URL("http://" + TestBedManagerConfiguration.INSTANCE.getWebConfig().getHub()
				+ ":4444/selenium-server/driver/?cmd=shutDownSeleniumServer");
		URLConnection connection = seleniumHub.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			log.info("Shutting down selenium server: response - " + inputLine);
		}
		in.close();
	}

}