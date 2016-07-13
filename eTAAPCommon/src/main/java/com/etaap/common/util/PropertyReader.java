/*
 * 
 */
package com.etaap.common.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;

import com.etaap.core.exception.ExceptionListener;
import com.etaap.core.exception.HandleException;
import com.etaap.core.exception.handler.ExceptionHandler;

/**
 * The Class PropertyReader.
 */
public class PropertyReader implements ExceptionListener {

	/** The object rep. */
	public Properties objectRep;

	private static Log log = LogUtil.getLog(PropertyReader.class);

	/**
	 * Gets the object rep.
	 * 
	 * @param fileName
	 *            the file name
	 * @return the object rep
	 */
	public Properties getObjectRep(String fileName) {
		if (objectRep == null) {
			objectRep = createRepository(fileName);

		}
		return objectRep;
	}

	/**
	 * Creates the repository.
	 * 
	 * @param fileName
	 *            the file name
	 * @return the properties
	 */
	@HandleException(expected = { IOException.class })
	private Properties createRepository(String fileName) {
		objectRep = new Properties();
		InputStream ip = null;
		try {
			ip = new FileInputStream(fileName);

			objectRep.load(ip);
		} catch (IOException e) {

			log.debug("FileNotFoundException", e);
			ExceptionHandler ex = new ExceptionHandler();
			ex.handleit(this, e);

		}

		return objectRep;

	}

}
