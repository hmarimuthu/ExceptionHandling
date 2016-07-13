package com.etaap.core.listener;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.openqa.selenium.WebDriverException;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

import com.etaap.common.util.LogUtil;
import com.etaap.core.TestBedManager;
import com.etaap.core.config.DefectConfig;
import com.etaap.core.config.TestBedManagerConfiguration;
import com.etaap.core.config.util.ConfigUtil;
import com.etaap.core.videorecord.VideoRecorder;
import com.etaap.manage.defect.DefectTitle;

/**
 * Listener for Test method. 
 * @author rThangavelu
 *
 */
public class MethodListener implements IInvokedMethodListener {

	/** The log. */
	static Log log = LogUtil.getLog(MethodListener.class);
	

	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
		
		setupCurrentTestCase(method);
		
		if (ConfigUtil.isVideoRecordingOn()){
			startVideoRecording();
		}
		
		if(ConfigUtil.isTakeScreenShotOn()){
			takeScreenShot();
		}
		
		
	
	}

	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
			

	}

	/**
	 * set Current Test case
	 * @param method
	 */
	private void setupCurrentTestCase(IInvokedMethod method) {
		if (method.isTestMethod()) {
			String methodName = method.getTestMethod().getMethodName();
			log.debug("Method Name " + methodName);

			TestBedManager.INSTANCE.getCurrentTestCase().setTestMethodName(
					methodName);

		}
	}
	
	/**
	 * Start video recording
	 */
	private void startVideoRecording(){
		VideoRecorder videoRecorder=new VideoRecorder(null);
		videoRecorder.startRecording();
		
	}
	
	
	/** 
	 * Take screen shot of the current screen
	 */
	private void takeScreenShot(){
		VideoRecorder videoRecorder=new VideoRecorder(null);
		// videoRecorder.takeScreenshot(imagePath, imgName, driver);
	}

	
	
	
	
	

}
