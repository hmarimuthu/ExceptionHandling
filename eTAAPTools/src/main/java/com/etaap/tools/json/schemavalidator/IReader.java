/*
 * 
 */
package com.etaap.tools.json.schemavalidator;

import java.io.IOException;

import com.etaap.core.exception.ResourceLoadException;

/**
 * Reader contract for loading resource.
 * 
 * @author eTouch Systems Corporation
 * @version 1.0
 *
 */
public interface IReader {

	/**
	 * Load from resource.
	 *
	 * @param resource
	 *            the resource
	 * @throws ResourceLoadException
	 *             the resource load exception
	 */
	void loadFromResource(String resource) throws IOException;

	/**
	 * Load from url.
	 *
	 * @param url
	 *            the url
	 * @throws ResourceLoadException
	 *             the resource load exception
	 */
	void loadFromURL(String url) throws IOException;

	/**
	 * Load from path.
	 *
	 * @param path
	 *            the path
	 * @throws ResourceLoadException
	 *             the resource load exception
	 */
	void loadFromPath(String path) throws IOException;

	/**
	 * Load from string.
	 *
	 * @param json
	 *            the json
	 * @throws ResourceLoadException
	 *             the resource load exception
	 */
	void loadFromString(String json) throws IOException;

	/**
	 * Gets the node.
	 *
	 * @return the node
	 */
	Object getNode();

}
