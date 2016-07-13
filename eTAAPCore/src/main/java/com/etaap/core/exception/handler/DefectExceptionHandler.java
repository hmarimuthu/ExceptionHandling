package com.etaap.core.exception.handler;

import java.util.HashMap;

import org.apache.commons.logging.Log;

import com.etaap.common.util.LogUtil;
import com.etaap.core.exception.ExceptionListener;
import com.etaap.core.exception.error.ErrorInstance;
import com.etaap.core.exception.error.ErrorMessages;
import com.etaap.core.exception.error.EtaapError;

public class DefectExceptionHandler extends ExceptionHandler {
	static Log log = LogUtil.getLog(ExceptionHandler.class);

	/**
	 * This method receives exception and the instance which raised the
	 * exception
	 * 
	 * @param instance
	 * @param e
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

	/**
	 * Build a Hashmap for className and ClassName Grows directly proportional
	 * to custom exception
	 */
	private HashMap<String, String> buildErrorMessageMap() {
		HashMap<String, String> solutionMap = new HashMap<>();
		solutionMap.put("DefectUtil.isDefectToolSupported", "201.Need to be Updated");
		// Used for unit testing
		// solutionMap.put("TestDefectExceptionHandler.isDefectToolSupported",
		// "1000.Unit testing DefectExceptionhandler");
		solutionMap.put("DefectToolManager.getDefectTool", "202.Need to be updated");

		return solutionMap;
	}

	public void staticHandle(Class<?> clazz, Exception e) {
		if (ExceptionListener.class.isAssignableFrom(clazz)) {

			StackTraceElement ste = getStackTraceElement(clazz, e);
			String className = getClassName(clazz);
			String methodName = ste.getMethodName();
			HashMap<String, String> solutionMap = buildErrorMessageMap();
			String errorSolution = solutionMap.get(className + "." + methodName);

			EtaapError error = new EtaapError();
			if (errorSolution != null) {
				error.setErrorCode(getErrorCode(errorSolution));
			}
			error.setErrorDetails(e.getMessage());
			if (errorSolution != null) {
				error.setSolution(getSolution(errorSolution));
			}

			ErrorInstance errInstance = new ErrorInstance();
			errInstance.seteTaapError(error);
			errInstance.setExceptionClass(clazz);
			errInstance.setException(e);
			errInstance.setSte(ste);

			ErrorMessages errorMessages = ErrorMessages.getInstance();
			errorMessages.add(errInstance);
			errorMessages.logError(errInstance);

		}

	}

	protected String getErrorCode(String errorSolution) {
		return errorSolution.substring(0, errorSolution.indexOf('.'));

	}

	protected String getSolution(String errorSolution) {
		String solution = "";
		int firstchar = errorSolution.indexOf('.') + 1;
		if (firstchar > 0) {
			solution = errorSolution.substring(firstchar);
		}
		return solution;

	}

	protected StackTraceElement getStackTraceElement(Class<?> clazz, Exception e) {
		StackTraceElement foundSte = null;
		if (e != null) {

			for (StackTraceElement ste : e.getStackTrace()) {
				if (clazz.getName().equalsIgnoreCase(ste.getClassName())) {
					foundSte = ste;
					break;

				}

			}
		}
		return foundSte;

	}

	protected StackTraceElement getStackTraceElement(ExceptionListener instance, Exception e) {
		StackTraceElement foundSte = null;
		if (e != null) {

			for (StackTraceElement ste : e.getStackTrace()) {
				if (instance.getClass().getName().equalsIgnoreCase(ste.getClassName())) {
					foundSte = ste;
					break;

				}

			}
		}
		return foundSte;

	}

	public static String getClassName(Class c) {
		String className = c.getName();
		int firstChar;
		firstChar = className.lastIndexOf('.') + 1;
		if (firstChar > 0) {
			className = className.substring(firstChar);
		}
		return className;
	}

}
