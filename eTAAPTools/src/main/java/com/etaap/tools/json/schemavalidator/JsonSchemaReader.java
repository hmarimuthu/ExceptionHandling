/*
 * 
 */
package com.etaap.tools.json.schemavalidator;

import java.io.IOException;
import java.net.URL;

import org.apache.commons.logging.Log;

import com.etaap.common.util.LogUtil;
import com.etaap.core.exception.HandleException;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.util.JsonLoader;

/**
 * This class reads JSON schema.
 * 
 * @author eTouch Systems Corporation
 * @version 1.0
 *
 */
public class JsonSchemaReader implements IReader {

	/** The log. */
	private static Log log = LogUtil.getLog(JsonSchemaReader.class);

	/** The schema. */
	private JsonNode schema;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.etaap.tools.json.schemavalidator.IReader#loadFromResource(java.lang.
	 * String)
	 */
	@HandleException(expected = { IOException.class })
	public void loadFromResource(String resource) throws IOException {

		schema = JsonLoader.fromResource(resource);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.etaap.tools.json.schemavalidator.IReader#loadFromURL(java.lang.
	 * String)
	 */
	@HandleException(expected = { IOException.class })
	public void loadFromURL(String url) throws IOException {

		URL urlObj = new URL(url);
		schema = JsonLoader.fromURL(urlObj);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.etaap.tools.json.schemavalidator.IReader#loadFromPath(java.lang.
	 * String)
	 */
	@HandleException(expected = { IOException.class })
	public void loadFromPath(String path) throws IOException {

		schema = JsonLoader.fromPath(path);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.etaap.tools.json.schemavalidator.IReader#loadFromString(java.lang.
	 * String)
	 */
	@HandleException(expected = { IOException.class })
	public void loadFromString(String json) throws IOException {

		schema = JsonLoader.fromString(json);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.etaap.tools.json.schemavalidator.IReader#getNode()
	 */
	public Object getNode() {
		return schema;
	}

}
