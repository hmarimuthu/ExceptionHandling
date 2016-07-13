package com.etaap.manage.defect;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import com.etaap.common.util.LogUtil;
import com.etaap.core.config.TestBedManagerConfiguration;
import com.etaap.core.resources.KDConstants;
import com.etaap.manage.defect.resources.SupportedDefectTools;

/**
 * @author etouch
 * 
 */
public class DefectToolManager {

	private static Log log = LogUtil.getLog(DefectToolManager.class);

	private DefectTool defectTool;

	private DefectBuilder defectBuilder;

	private File screenshot = null;

	private Throwable exception;

	public DefectToolManager(WebDriver driver) throws Exception {

		this.defectTool = getDefectTool();
		this.defectBuilder = new DefectBuilder(new Defect());

	}

	/**
	 * This getter for defectTool.
	 * 
	 * @return
	 * @throws Exception
	 */
	public DefectTool getDefectTool() throws Exception {

		String defectToolName = TestBedManagerConfiguration.INSTANCE.getDefectManagementTool();

		DefectTool defectToolValue = null;

		if (SupportedDefectTools.isSupported(defectToolName)) {
			//defectToolValue = SupportedDefectTools.getDefectTool(defectToolName);
		} else {
			throw new Exception("Defect Tool not supported");
		}

		return defectToolValue;
	}

//	/**
//	 * This method is used to get the Screen Recording Filename.
//	 * 
//	 * @param testActionExecutor
//	 * @return
//	 */
//	private String getScreenRecordingFileName(DefectTitle defectTitle) {
//		String screenRecorderFileName = defectTitle.getTestBedName() + "-" + defectTitle.getTestSuiteName() + "-"
//				+ defectTitle.getTestAction().getActionName() + "_ScreenRecording";
//		return screenRecorderFileName;
//	}

	

	public void manageDefect(String methodExecutionStatus) throws Exception {

		// Opening connection
		defectTool.openConnection();

		boolean isDefectLogged = defectTool.isDefectLogged("Title"); //defectBuilder.getDefectTitle());

		if (methodExecutionStatus.equalsIgnoreCase(KDConstants.EXECUTION_FAILED.getValue())) {
			manageFailedDefects(isDefectLogged);
		} else if (methodExecutionStatus.equalsIgnoreCase(KDConstants.EXECUTION_PASSED.getValue())) {
			managePassedDefects(isDefectLogged);
		}

		// Closing connection
		defectTool.closeConnection();

	}

	/**
	 * @param isDefectLogged
	 * @throws Exception
	 */
	private void managePassedDefects(boolean isDefectLogged) throws Exception {

		// Verify if the Defect is already logged
		if (isDefectLogged) {
			closeResolvedDefect();
		}
	}

	/**
	 * @throws Exception
	 */
	private void closeResolvedDefect() throws Exception {

		String defectStatus = defectTool.getDefectStatus();

		if (!defectStatus.equalsIgnoreCase(KDConstants.CLOSE_DEFECT.getValue())) {
			// Close the defect
			defectBuilder.buildDefectNotes(KDConstants.CLOSE_DEFECT.getValue(), null);
			//defectTool.closeDefect(defectBuilder.getDefectNotes());
		}
	}

	/**
	 * @param isDefectLogged
	 * @throws Exception
	 */
	private void manageFailedDefects(boolean isDefectLogged) throws Exception {

		// Verify if the Defect is already logged
		if (isDefectLogged) {
			updateExistingDefect();
		} else {
			createNewDefect();
		}
	}

	/**
	 * @throws Exception
	 * 
	 */
	private void createNewDefect() throws Exception {
		// Log a new defect
		defectBuilder.buildDefect(getException());
		//defectTool.logDefect(defectBuilder.getDefectTitle(), defectBuilder.getDefectDescription(), defectBuilder.getDefectPriority(), getScreenshot());
	}

	/**
	 * @throws Exception
	 */
	private void updateExistingDefect() throws Exception {

		String defectStatus = defectTool.getDefectStatus();

		if (defectStatus.equalsIgnoreCase(KDConstants.CLOSE_DEFECT.getValue())) {
			// reOpen the defect
			defectBuilder.buildDefectNotes(KDConstants.REOPEN_DEFECT.getValue(), getException());
			//defectTool.reOpenDefect(defectBuilder.getDefectNotes(), getScreenshot());
		} else {
			// just update the defect with comments
			defectBuilder.buildDefectNotes(KDConstants.UPDATE_DEFECT.getValue(), getException());
			//defectTool.updateDefect(defectBuilder.getDefectNotes(), getScreenshot());
		}
	}

	/**
	 * @return the exception
	 */
	public Throwable getException() {
		return exception;
	}

	/**
	 * @param exception
	 *            the exception to set
	 */
	public void setException(Throwable exception) {
		this.exception = exception;
	}

	

	
	
}