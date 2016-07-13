package com.etaap.core.exception.handler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import javax.validation.constraints.NotNull;

import org.apache.commons.logging.Log;
import org.bouncycastle.jce.provider.JDKGOST3410Signer.ecgost3410;

import com.etaap.common.util.LogUtil;
import com.etaap.core.driver.DriverManager;
import com.etaap.core.exception.DefectException;
import com.etaap.core.exception.DriverException;
import com.etaap.core.exception.ExceptionListener;
import com.etaap.core.exception.HandleException;


/**
 * This class will receive exceptions raised in framework. Will delegate those
 * exceptions to corresponding classes to handle it.
 * 
 * Methods in exception handler will throw exceptions. handleit method will
 * handle those exceptions as well
 * 
 * @author rThangavelu
 *
 */
public class ExceptionHandler {

	static Log log = LogUtil.getLog(ExceptionHandler.class);

	/**
	 * This method receives exception and the instance which raised the
	 * exception
	 * 
	 * @param instance
	 * @param e
	 */
	
	public  void handleit(ExceptionListener instance, Exception e) {
		
		StackTraceElement ste = getStackTraceElement(instance, e);
		Annotation[] as = getMethodAnnotations(instance,
				ste.getMethodName());
		for (Annotation a : as) {
			if (a.annotationType().equals(HandleException.class)) {
				HandleException h = (HandleException) a;

					try {
						identifyExceptionHandler(h,instance, e);
					} catch (ClassNotFoundException | NoSuchMethodException
							 | InstantiationException |IllegalAccessException 
							 | IllegalArgumentException | InvocationTargetException e1){  
							
						log.debug(e1);
					}

				}


			}
		

	}
	
	/**
	 * This method receives exception and the instance which raised the
	 * exception
	 * 
	 * @param Class that belongs to a static method that lead to exception
	 * @param e
	 */
	
	public  void handleit(Class<?> clazz, Exception e) {
		
		StackTraceElement ste = getStackTraceElement(clazz, e);
		Annotation[] as = getMethodAnnotations(clazz,
				ste.getMethodName());
		for (Annotation a : as) {
			if (a.annotationType().equals(HandleException.class)) {
				HandleException h = (HandleException) a;

					try {
						identifyExceptionHandler(h,clazz, e);
					} catch (ClassNotFoundException | NoSuchMethodException
							 | InstantiationException |IllegalAccessException 
							 | IllegalArgumentException | InvocationTargetException e1){  
							
						log.debug(e1);
					}

				}


			}
		

	}

	/**
	 * Identify the exception and send it to the right handler
	 * 
	 * @param e
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws ClassNotFoundException
	 */
	private void identifyExceptionHandler(HandleException h,ExceptionListener instance,  Exception e)
			throws ClassNotFoundException, NoSuchMethodException,
			 InstantiationException, IllegalAccessException,
			 InvocationTargetException {
		
		for (Class<?> clazz : h.expected()) {
			
			if (clazz.equals(e.getClass())) {
				
				try {
					
					String className = identifyHandler(e);
					 invokeHandler(className,instance,  e);
					
					
				} catch (ClassNotFoundException | NoSuchMethodException
						 | InstantiationException
						 | IllegalArgumentException
						| InvocationTargetException e1) {
					log.debug(e1);
				}
			}
		}

	}
	
	/**
	 * Identify the exception and send it to the right handler
	 * @param HandleException 
	 * @param class that lead to exception
	 * @param e
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws ClassNotFoundException
	 */
	private void identifyExceptionHandler(HandleException h,Class<?> clazz,  Exception e)
			throws ClassNotFoundException, NoSuchMethodException,
			 InstantiationException, IllegalAccessException,
			 InvocationTargetException {
		
		for (Class<?> clazzHe : h.expected()) {
			
			if (clazzHe.equals(e.getClass())) {
				
				try {
					
					String className = identifyHandler(e);
					 invokeHandler(className,clazz,  e);
					
					
				} catch (ClassNotFoundException | NoSuchMethodException
						 | InstantiationException
						 | IllegalArgumentException
						| InvocationTargetException e1) {
					log.debug(e1);
				}
			}
		}

	}


	/**
	 * Build a Hashmap for className and ClassName
	 * Grows directly proportional to custom exception
	 */
	private HashMap<String, Class<? extends ExceptionHandler>> buildHandlerMap() {
		HashMap<String, Class<? extends ExceptionHandler>> handlerMap = new HashMap<>();
		handlerMap.put(DriverException.class.getName(),
				DriverExceptionHandler.class);
		
		handlerMap.put(IllegalStateException.class.getName(),
				DriverExceptionHandler.class);
		
		handlerMap.put(DefectException.class.getName(),
				DefectExceptionHandler.class); 
		return handlerMap;
	}

	/**
	 * Identify Exception handler based on the exception name
	 * 
	 * @param className
	 * @param e
	 * @throws ClassNotFoundException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	private ExceptionHandler invokeHandler(String className,
			ExceptionListener instance, Exception e) throws ClassNotFoundException, NoSuchMethodException,
			 InstantiationException, IllegalAccessException, InvocationTargetException {

		Class<?> clazz = Class.forName(className);
	    
		Class[] paramArray=new Class[2];
		paramArray[0] = ExceptionListener.class;
		paramArray[1]= Exception.class;
		Method handleMethod=clazz.getDeclaredMethod("handleit", paramArray);
		handleMethod.invoke(clazz.newInstance(), instance, e);
		return (ExceptionHandler)clazz.newInstance();
	}
	
	/**
	 * Identify Exception handler based on the exception name
	 * 
	 * @param className ( Custom ExceptionHandler class)
	 * @param exClazz ( Class that lead to exception)
	 * @param e
	 * @throws ClassNotFoundException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	private ExceptionHandler invokeHandler(String className,
			Class<?> exClazz, Exception e) throws ClassNotFoundException, NoSuchMethodException,
			 InstantiationException, IllegalAccessException, InvocationTargetException {

		Class<?> clazz = Class.forName(className);
	    
		Class[] paramArray=new Class[2];
		paramArray[0] = Class.class;
		paramArray[1]= Exception.class;
		Method handleMethod=clazz.getDeclaredMethod("staticHandle", paramArray);
		handleMethod.invoke(clazz.newInstance(), exClazz, e);
		return (ExceptionHandler)clazz.newInstance();
	}

	/**
	 * 
	 * @param e
	 */
	private String identifyHandler(Exception e) {
		HashMap<String, Class<? extends ExceptionHandler>> handlerMap = buildHandlerMap();
		Class handler= handlerMap.get(e.getClass().getName());
		String handlerName;
	
		if(handler!=null){				
			handlerName=handler.getName();				
		}else{
			log.info(e.getClass().getName() + "not found in handlerMap");
			handlerName="GeneralExceptionHandler";			
		}
		return handlerName;

	}

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
	
	protected StackTraceElement getStackTraceElement(
			Class<?> clazz, Exception e) {
		StackTraceElement foundSte = null;
		if (e != null) {

			for (StackTraceElement ste : e.getStackTrace()) {
				if (clazz.getName()
						.equalsIgnoreCase(ste.getClassName())) {
					foundSte = ste;
					break;

				}

			}
		}
		return foundSte;

	}

	/**
	 * Get list of parameter of a method in the given Class
	 * 
	 * @param clazz
	 * @param methodName
	 * @return
	 */
	public Class<?>[] getMethodParams(@NotNull Class clazz, String methodName) {
		Class<?>[] paramClazz = null;

		for (Method m : clazz.getDeclaredMethods()) {
			if (m.getName().equalsIgnoreCase(methodName)) {
				paramClazz = m.getParameterTypes();
				break;
			}
		}

		return paramClazz;

	}

	/**
	 * Get list of Annotations defined in that method in the given Class
	 * 
	 * @param class1
	 * @param methodName
	 * @return
	 * 
	 *         Have to tackle overloaded methods
	 */
	public Annotation[] getMethodAnnotations(ExceptionListener instance, String methodName) {
		Annotation[] annotations = null;
		for (Method m :instance.getClass().getDeclaredMethods()) {
			if (m.getName().equalsIgnoreCase(methodName)) {
				try {
					
					Method foundMethod=instance.getClass().getDeclaredMethod(methodName,m.getParameterTypes());
					annotations = foundMethod.getAnnotations();
					break;
				} catch (NoSuchMethodException | SecurityException e) {
					log.debug(e);
				}
				
				
			}
		}

		return annotations;

	}
	
	/**
	 * Get list of Annotations defined in that method in the given Class
	 * 
	 * @param class1
	 * @param methodName
	 * @return
	 * 
	 *         Have to tackle overloaded methods
	 */
	public Annotation[] getMethodAnnotations(Class<?> clazz, String methodName) {
		Annotation[] annotations = null;
		for (Method m :clazz.getDeclaredMethods()) {
			if (m.getName().equalsIgnoreCase(methodName)) {
				try {
					
					Method foundMethod=clazz.getDeclaredMethod(methodName,m.getParameterTypes());
					annotations = foundMethod.getAnnotations();
					break;
				} catch (NoSuchMethodException | SecurityException e) {
					log.debug(e);
				}
				
				
			}
		}

		return annotations;

	}
	
	

	

}
