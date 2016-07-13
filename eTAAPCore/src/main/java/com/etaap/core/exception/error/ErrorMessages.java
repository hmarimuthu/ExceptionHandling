package com.etaap.core.exception.error;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.logging.Log;

import com.etaap.common.util.LogUtil;

/**
 * Error Message will maintain list of error/exception occurred during the execution of test cases
 * @author rThangavelu
 *
 */

public class ErrorMessages {
	
	static Log log = LogUtil.getLog(ErrorMessages.class);
	private static List<ErrorInstance> errorList =new ArrayList<>();
	
	private static ErrorMessages errorMessages=new ErrorMessages();
	
	
	private ErrorMessages(){
	}
	
	public static ErrorMessages getInstance(){
		if(errorMessages==null){
			errorMessages= new ErrorMessages();
		}
		return errorMessages;
			
	}
	
	
	
	/**
	 * Add a new errorInstance
	 * @param errInstance
	 */
	public void addErrorInstance(@NotNull ErrorInstance   errInstance){
			errorList.add(errInstance);
			
	}

	
	
	/**
	 * Returns size of the errorList
	 * @return
	 */
	public int size() {
        return errorList.size();
    }


	
	 /**
     * Returns <tt>true</tt> if this map contains no key-value mappings.
     *
     * @return <tt>true</tt> if this map contains no key-value mappings
     */
    public boolean isEmpty() {
        return errorList.isEmpty();
    }


    /**
     * send the error details to the requester
     * @param index
     * @return
     */
    public ErrorInstance getDetails(int index) {
    	ErrorInstance errInstance=errorList.get(index);
		log.debug(" err instance at index : " + index + " is  = " + errInstance.toString());
    	
    	return errInstance; 
    }
    
    
    /**
     * Checks whether the element is available in the list or  not
     * @param element
     * @return
     */
    public boolean contains(Object element) {
        return errorList.contains(element);
    }

 

    /**
     * Add a new element to ErrorList
     * @param value
     * @return
     */
    public boolean add(ErrorInstance value) {
        return errorList.add (value);
    }

    
    /**
     * Remove the unwanted item from the list
     * @param key
     * @return
     */
    public boolean remove(ErrorInstance key) {
        return errorList.remove(key);
    }
    
    
   

  
  /**
   * This method is used for debugging purpose.
   * Prints list of ErrorInstance  
   */
    public void logErrorList(){
    	if(errorList!=null){
    		for(ErrorInstance errInstance:errorList){
    			logError(errInstance);
    		}
    		
    	}
    	
    }
    
    /**
     * Log individual Error details
     * @param errInstance
     */
    public void logError(ErrorInstance errInstance){
    	log.info(" ==============================================");
		log.info(" Error detail : Exception ----- " +  errInstance.getException() + " \n Occured in :" + errInstance.getInstance() + " \n StackTrace information : " + errInstance.getSte());
		log.info(" ==============================================");
		
		System.out.println(" ==============================================");
		System.out.println("   Error detail : Exception ----- " +  errInstance.getException() + "\n Occured in :" + 
		errInstance.getInstance() 
				+ " \n StackTrace information : " + errInstance.getSte().getClassName() +"."+ errInstance.getSte().getMethodName() + "  at Line number  " + errInstance.getSte().getLineNumber());
		
		System.out.println(" ==============================================");
    }

}
