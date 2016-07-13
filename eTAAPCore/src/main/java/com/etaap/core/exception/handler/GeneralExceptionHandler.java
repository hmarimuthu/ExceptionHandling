package com.etaap.core.exception.handler;

import com.etaap.core.exception.ExceptionListener;
import com.etaap.core.exception.error.ErrorInstance;
import com.etaap.core.exception.error.ErrorMessages;
import com.etaap.core.exception.error.EtaapError;

public class GeneralExceptionHandler extends ExceptionHandler {

	/**
	 * Handles General Runtime Exception
	 */
	public void handleit(ExceptionListener instance, Exception e) {

		EtaapError error = new EtaapError();
		error.setErrorCode("101");
		error.setErrorDetails(e.getMessage());
		error.setSolution("Not sure yet");

		ErrorInstance errInstance = new ErrorInstance();
		errInstance.setInstance(instance);
		errInstance.seteTaapError(error);
		errInstance.setException(e);
		errInstance.setSte(getStackTraceElement(instance, e));

		ErrorMessages errorMessages = ErrorMessages.getInstance();
		errorMessages.add(errInstance);
		errorMessages.logError(errInstance);
	}

}
