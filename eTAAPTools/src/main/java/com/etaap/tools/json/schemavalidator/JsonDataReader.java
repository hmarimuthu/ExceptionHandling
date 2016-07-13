package com.etaap.tools.json.schemavalidator;

import java.io.IOException;
import java.net.URL;

import org.apache.commons.logging.Log;

import com.etaap.common.util.LogUtil;
import com.etaap.core.exception.HandleException;
import com.etaap.core.infra.mail.EmailValidator;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.util.JsonLoader;

/**
 * This class reads JSON date.
 * 
 * @author eTouch Systems Corporation
 * @version 1.0
 *
 */
public class JsonDataReader implements IReader {

	private static Log log = LogUtil.getLog(EmailValidator.class);

	/** The data. */
	private JsonNode data;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.etaap.tools.json.schemavalidator.IReader#loadFromResource(java.lang.
	 * String)
	 */
	@Override
	@HandleException(expected = { IOException.class })
	public void loadFromResource(String resource) throws IOException {

		data = JsonLoader.fromResource(resource);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.etaap.tools.json.schemavalidator.IReader#loadFromURL(java.lang.
	 * String)
	 */@Override
	@HandleException(expected = { IOException.class })
	public void loadFromURL(String url) throws IOException {

		URL urlObj = new URL(url);
		data = JsonLoader.fromURL(urlObj);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.etaap.tools.json.schemavalidator.IReader#loadFromPath(java.lang.
	 * String)
	 */
	@Override
	@HandleException(expected = { IOException.class })
	public void loadFromPath(String path) throws IOException {

		data = JsonLoader.fromPath(path);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.etaap.tools.json.schemavalidator.IReader#loadFromString(java.lang.
	 * String)
	 */@Override
	@HandleException(expected = { IOException.class })
	public void loadFromString(String json) throws IOException {

		data = JsonLoader.fromString(json);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.etaap.tools.json.schemavalidator.IReader#getNode()
	 */
	@Override
	public Object getNode() {
		return data;
	}

}
