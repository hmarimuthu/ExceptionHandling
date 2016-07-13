package com.etaap.manage.defect;

import java.util.Date;

import org.apache.commons.logging.Log;

import com.etaap.common.util.CommonUtil;
import com.etaap.common.util.LogUtil;
import com.etaap.core.TestBedManager;
import com.etaap.core.current.CurrentTestCase;
import com.etaap.core.resources.KDConstants;

/**
 * Defect Builder will build values for properties of Defect
 * 
 * @author rThangavelu
 *
 */
public class DefectBuilder {

	private static Log log = LogUtil.getLog(DefectBuilder.class);

	private Defect defect;

	/**
	 * 
	 * @param defect
	 */
	public DefectBuilder(Defect defect) {
		this.defect = defect;

		// Creating defect title
		buidDefectTitle();
	}

	/**
	 * Build defect title in the format Test_Bed_Name - Test_Suite_Name
	 * Test_Action_Name - Data_Label_Name
	 */
	private void buidDefectTitle() {
		CurrentTestCase currentTestcase = TestBedManager.INSTANCE.getCurrentTestCase();
		String testSuite = currentTestcase.getTestSuiteName();
		String testBed = currentTestcase.getTestBedName();
		String testMethod = currentTestcase.getTestMethodName();
		String defectTitle = "" + testBed + " - " + testSuite + ": " + testMethod;
		defect.getTitle().setTitle(defectTitle);
	}

	public void buildDefect(Throwable exception) {

		// creating description
		// buildDefectDecription(exception);

		// setting priority
		defect.setPriority("Medium");

	}

	// /**
	// * @param dataLabel
	// * @return
	// */
	// private String getDataLabel() {
	// String dataLabel = "";
	//
	// if( testActionExecutor.getKdDataSet() != null ){
	//
	// Map<String, Object> kdDataMap =
	// testActionExecutor.getKdDataSet().getDataSetMap();
	//
	// if( ( kdDataMap != null ) && ( kdDataMap.size() > 0 )) {
	//
	// if(kdDataMap.containsKey(KDConstants.TD_LABEL.getValue()) &&
	// StringUtils.isNotBlank(kdDataMap.get(KDConstants.TD_LABEL.getValue()).toString())
	// ){
	// dataLabel = "-
	// "+kdDataMap.get(KDConstants.TD_LABEL.getValue()).toString();
	// }
	// }
	// }
	//
	// return dataLabel;
	// }

	// /**
	// * @param exception
	// */
	// private void buildDefectDecription(Throwable exception) {
	//
	// LinkedList<ActionElement> actionElements =
	// testActionExecutor.getTestAction().getActionElements();
	//
	// StringBuffer description = new StringBuffer();
	//
	// description.append("Step to follow: \n\n");
	//
	// for(ActionElement actionElement : actionElements){
	//
	// buildTestActionSteps(actionElements.indexOf(actionElement),
	// actionElements, description, actionElement);
	// }
	//
	// description.append("\n Expected:
	// "+testActionExecutor.getTestAction().getActionName()+" should pass \n");
	//
	// description.append("\n Actual: \n");
	//
	// description.append(CommonUtil.getErrorDetails(exception)+" \n\n");
	//
	// defect.setDescription(description);
	//
	// }

	// /**
	// * @param index
	// * @param actionElements
	// * @param description
	// * @param actionElement
	// */
	// private void buildTestActionSteps(int index, LinkedList<ActionElement>
	// actionElements,
	// StringBuffer description, ActionElement actionElement) {
	//
	// description.append( (index + 1 )+ " - ");
	//
	// String action = actionElement.getKeyword().getAction();
	//
	// if( AssertKeywordLibrary.contains(action)) {
	//
	// String assertValue = String.valueOf(
	// testActionExecutor.getKdDataSet().getDataSetMap().get(actionElement.getName()).toString());
	// description.append("Verify "+actionElement.getIdType()+" "+action+"
	// "+assertValue+"\n");
	// }
	// else {
	// description.append ( action + " "+actionElement.getValue() );
	//
	// if(
	// testActionExecutor.getKdDataSet().getDataSetMap().containsKey(actionElement.getName())
	// ){
	// String dataValue = String.valueOf(
	// testActionExecutor.getKdDataSet().getDataSetMap().get(actionElement.getName()).toString());
	// description.append(" = "+dataValue+" \n");
	// }
	// else {
	// description.append("\n");
	// }
	// }
	// }

	public void buildDefectNotes(String defectWorkflowType, Throwable throwable) {

		StringBuilder defectNotes = new StringBuilder();

		if (defectWorkflowType.equalsIgnoreCase(KDConstants.UPDATE_DEFECT.getValue())) {
			defectNotes.append("Defect still existing as on " + new Date());
		} else if (defectWorkflowType.equalsIgnoreCase(KDConstants.CLOSE_DEFECT.getValue())) {
			defectNotes.append("Defect resolved. Closing on " + new Date());
		} else if (defectWorkflowType.equalsIgnoreCase(KDConstants.REOPEN_DEFECT.getValue())) {
			defectNotes.append("Defect reoccured on " + new Date() + " : Reopening");
		}

		if (throwable != null) {
			defectNotes.append("\n Issue observed this time ==>" + CommonUtil.getErrorDetails(throwable));
		}

		defect.setNotes(defectNotes);

	}

}
