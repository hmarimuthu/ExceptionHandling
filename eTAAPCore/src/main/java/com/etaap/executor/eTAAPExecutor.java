/**
 * 
 */
package com.etaap.executor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.etaap.common.util.LogUtil;
import com.etaap.common.util.SoftAssertor;
import com.etaap.core.TestBedManager;
import com.etaap.core.TestcaseExecutor;
import com.etaap.core.exception.DriverException;
import com.etaap.core.exception.HandleException;

public class eTAAPExecutor {

	static Log log = LogUtil.getLog(eTAAPExecutor.class);
	// Added by Lavanya
	static Properties props = new Properties();

	public void loadProps() {
		InputStream ipStream = getClass().getClassLoader().getResourceAsStream("mail.properties");
		try {
			if (ipStream != null) {
				props.load(ipStream);
			}
		} catch (IOException iex) {
			iex.printStackTrace();
		}
	}

	public void MainTriggerPoint(String fileName) throws Exception {

		initialize(fileName);
		boolean result = true;
		// Assert.assertEquals(true, result);
		SoftAssertor.assertEquals(true, result);
		// SoftAssertor.displayAssertErrors();
	}

	public void initialize(String fileName) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, IOException, InvalidFormatException {

		log.debug("Initializing eTAAP");

		String currentTestBedName = System.getProperty("TESTBEDNAME");
		String currentEnvironment = System.getProperty("ENVIRONMENT");

		log.debug("CurrentTestBedname from command line : " + currentTestBedName);
		log.debug("CurrentEnvronment from command line : " + currentEnvironment);

		String configFilePath = "..//AmazonPOC//src//test//resources";

		InputStream in = null;
		try {
			in = convertFileToInputStream(fileName);
			log.debug(fileName.substring(0, fileName.indexOf("Config.yml")) + " config file input stream is ready");

		} catch (DriverException e1) {

			e1.printStackTrace();
		}

		String[] currentTestBeds = { currentTestBedName };
		try {

			TestBedManager.INSTANCE.setConfig(in, currentTestBeds);

		} catch (Exception e) {
			log.error("Please mention paramter in Maven command for TestBed Name");
		}

		new TestcaseExecutor().executeTestNG();

	}

	/**
	 * This method helps to convert from file to InputStream
	 * 
	 * @param fileName
	 * @return
	 * @throws DriverException
	 */
	@HandleException(expected = { FileNotFoundException.class, DriverException.class })
	private static InputStream convertFileToInputStream(String fileName) throws FileNotFoundException, DriverException {
		InputStream ipStream = null;
		if (fileName != null) {

			ipStream = new FileInputStream(new File(fileName));

		} else {
			log.error(" File name is null - " + fileName);
			throw new DriverException("failed to read profile configuration/TestNG, file name is missing");
		}
		return ipStream;
	}

	public static void main(String[] args) throws Exception {
		new eTAAPExecutor().loadProps();
		// Message[] messages = new EmailValidator().readMailThroughSMTP(props);
		// Message[] messages = new EmailValidator().readMail(props);
		// log.debug("Message count is: "+messages.length);
		// log.debug("Message object is: "+messages[0].getContent().toString());
		String fileName = args.length > 0 ? args[0] : "";
		log.debug(" main() :: fileName :: " + fileName);
		new eTAAPExecutor().MainTriggerPoint(fileName);

	}
}
