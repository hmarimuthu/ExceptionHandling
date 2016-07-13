/*
 * 
 */
package com.etaap.reader;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.yaml.snakeyaml.Yaml;

import com.etaap.common.util.LogUtil;
import com.etaap.core.config.TestBedManagerConfiguration;
import com.etaap.core.exception.ExceptionListener;
import com.etaap.core.exception.HandleException;
import com.etaap.core.exception.handler.ExceptionHandler;

/**
 * This Class ConfigurationReader, helps to read the yaml configuration file and
 * load the values as POJO class(TestBedManagerConfiguration) It is a Singleton
 * class.
 */
public class ConfigurationReader implements ExceptionListener {

	static Log log = LogUtil.getLog(ConfigurationReader.class);

	/**
	 * Read config.
	 * 
	 * @param ipStream
	 *            the ip stream
	 * @return the test bed manager configuration
	 */
	@HandleException(expected = { Exception.class })
	public static TestBedManagerConfiguration readConfig(InputStream ipStream) {
		TestBedManagerConfiguration config = null;
		try {

			Yaml yaml = new Yaml();
			// read the config file and load it into TestBedManagerConfiguration

			config = yaml.loadAs(ipStream, TestBedManagerConfiguration.class);

		} catch (Exception e) {
			log.debug(" Error occured during file load " + e.getMessage());
			log.debug(e);
			ExceptionHandler ex = new ExceptionHandler();
			ex.handleit(ConfigurationReader.class, new Exception("Error occured during file load"));
		} finally {
			// Close the ipStreams
			if (ipStream != null) {
				try {
					ipStream.close();
				} catch (IOException e) {
					log.debug("IOException", e);
					ExceptionHandler ex = new ExceptionHandler();
					ex.handleit(ConfigurationReader.class, new Exception("Error occured during ipStream close"));
				}
			}
		}
		return config;
	}
}
