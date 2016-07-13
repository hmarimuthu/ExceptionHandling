package com.etaap.core.exception.handler;

import org.openqa.selenium.WebDriver;

import com.etaap.core.driver.DriverBuilder;
import com.etaap.core.exception.ExceptionListener;
import com.etaap.core.exception.error.ErrorMessages;
import com.etaap.core.exception.error.EtaapError;
import com.etaap.core.exception.error.ErrorInstance;

/**
 * To handler Driver related Exceptions
 * @author rThangavelu
 *
 */
public class DriverExceptionHandler extends ExceptionHandler{

	
	/**
	 * Handles only Driver related exceptions
	 * @param instance
	 * @param e
	 */
	public void handleit(ExceptionListener instance, Exception e)  {
		StackTraceElement ste=getStackTraceElement(instance, e);

		if(instance instanceof DriverBuilder){
			DriverBuilder driverBuilder=(DriverBuilder)instance;
			if(driverBuilder!=null){
					WebDriver driver=(WebDriver)driverBuilder.getDriver();
					if(driver!=null){
						driver.close();
						driver.quit();
					}
			}
		}
		EtaapError error=new EtaapError();
		error.setErrorCode("101");
		error.setErrorDetails(e.getMessage());
		error.setSolution("Not sure yet");
		
		ErrorInstance errInstance=new ErrorInstance();
		errInstance.setInstance(instance);
		errInstance.seteTaapError(error);
		errInstance.setException(e);
		errInstance.setSte(getStackTraceElement(instance,e));
		
		ErrorMessages errorMessages=ErrorMessages.getInstance();
		errorMessages.add(errInstance);
		errorMessages.logError(errInstance);
		
	}
	
	
	
	/**
	 * Get full Stack trace Element
	 */
	protected StackTraceElement getStackTraceElement(
			ExceptionListener instance, Exception e) {
		StackTraceElement foundSte = null;
		if (e != null) {

			for (StackTraceElement ste : e.getStackTrace()) {
				if (instance.getClass().getName()
						.equalsIgnoreCase(ste.getClassName())) {
					foundSte = ste;
					break;

				}

			}
		}
		return foundSte;

	}
		
	
	
}
