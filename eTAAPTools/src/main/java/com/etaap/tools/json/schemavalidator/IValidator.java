/*
 * 
 */
package com.etaap.tools.json.schemavalidator;

import com.etaap.core.exception.ValidationException;
import com.github.fge.jsonschema.exceptions.ProcessingException;

/**
 * Contract for validating data.
 * 
 * @author eTouch Systems Corporation
 * @version 1.0
 *
 */
public interface IValidator {

	/**
	 * Validate.
	 *
	 * @param schema
	 *            the schema
	 * @param data
	 *            the data
	 * @return true, if successful
	 * @throws ValidationException
	 *             the validation exception
	 */
	boolean validate(Object schema, Object data) throws ProcessingException;

	/**
	 * Generate report.
	 *
	 * @throws ValidationException
	 *             the validation exception
	 */
	void generateReport() throws ValidationException;

}
