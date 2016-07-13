package com.etaap.common.util;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;

import com.etaap.core.exception.ExceptionListener;
import com.etaap.core.exception.HandleException;
import com.etaap.core.exception.handler.ExceptionHandler;

public class KDPropertiesUtil implements ExceptionListener {

	private static KDPropertiesUtil instance = null;
	private Properties properties;

	private static Log log = LogUtil.getLog(KDPropertiesUtil.class);

	protected KDPropertiesUtil() throws IOException {

		properties = new Properties();
		properties.load(getClass().getClassLoader().getResourceAsStream("KD.properties"));

	}

	@HandleException(expected = { IOException.class })
	public static KDPropertiesUtil getInstance() {
		if (instance == null) {
			try {
				instance = new KDPropertiesUtil();
			} catch (IOException e) {
				log.debug("IOException", e);
				ExceptionHandler ex = new ExceptionHandler();
				ex.handleit(KDPropertiesUtil.class, e);

			}
		}
		return instance;
	}

	public String getProperty(String key) {
		return properties.getProperty(key);
	}

}
