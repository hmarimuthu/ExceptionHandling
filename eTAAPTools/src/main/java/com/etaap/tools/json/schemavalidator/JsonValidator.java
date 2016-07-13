/*
 * 
 */
package com.etaap.tools.json.schemavalidator;

import org.apache.commons.logging.Log;

import com.etaap.common.util.LogUtil;
import com.etaap.core.exception.HandleException;
import com.etaap.core.exception.ValidationException;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.exceptions.ProcessingException;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.fge.jsonschema.report.ProcessingMessage;
import com.github.fge.jsonschema.report.ProcessingReport;

/**
 * This class validates JSON data against schema.
 * 
 * @author eTouch Systems Corporation
 * @version 1.0
 *
 */
public class JsonValidator implements IValidator {

	/** The log. */
	private static Log log = LogUtil.getLog(JsonValidator.class);

	/** The report. */
	ProcessingReport report;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.etaap.tools.json.schemavalidator.IValidator#validate(java.lang.
	 * Object, java.lang.Object)
	 */
	@HandleException(expected = { ProcessingException.class })
	public boolean validate(Object schema, Object data) throws ProcessingException {

		final boolean success;

		final JsonSchemaFactory factory = JsonSchemaFactory.byDefault();

		final JsonSchema jsonschema = factory.getJsonSchema((JsonNode) schema);

		report = jsonschema.validate((JsonNode) data);

		success = report.isSuccess();

		return success;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.etaap.tools.json.schemavalidator.IValidator#generateReport()
	 */
	@HandleException(expected = { ValidationException.class })
	public void generateReport() throws ValidationException {

		log.info("start generate report");
		for (final ProcessingMessage message : report) {
			log.info(message);
		}

		log.info("end generate report");

	}

}
