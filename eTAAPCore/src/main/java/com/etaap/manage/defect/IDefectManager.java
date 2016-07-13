package com.etaap.manage.defect;





/**
 * The Interface IDefectManager.
 */
public interface IDefectManager {
	
	/**
	 * Creates the defect builder.
	 *
	 * @param defName the def name
	 * @param testcaseId the testcase id
	 * @param workspaceId the workspace id
	 * @param projId the proj id
	 * @param DefSeverity the def severity
	 * @param DefOwner the def owner
	 * @param DefNotes the def notes
	 * @param storyId the story id
	 */
	
	public void createDefectBuilder(String defName, String testcaseId, String workspaceId, String projId, String defSeverity,
			String defOwner, String defNotes, String storyId, String attachmentPath);
	
	//added for updating testcase result - create a new abstract class to add testcase related
	/**
	 * Update test case result.
	 *
	 * @param defName the def name
	 * @param testcaseId the testcase id
	 * @param workspaceId the workspace id
	 * @param projId the proj id
	 * @param DefSeverity the def severity
	 * @param DefOwner the def owner
	 * @param DefNotes the def notes
	 * @param storyId the story id
	 */
	public void updateTestCaseResult(String defName, String testcaseId, String workspaceId, String projId, String defSeverity,
			String defOwner, String defNotes, String storyId);
	
	/**
	 * Verify defect exists.
	 *
	 * @param testcaseId the testcase id
	 * @param projId the proj id
	 * @param storyId the story id
	 * @param defName the def name
	 * @return true, if successful
	 */
	public boolean verifyDefectExists(String testcaseId, String projId, String storyId, String defName);
	
	/**
	 * Verify ifdefect closed.
	 *
	 * @return true, if successful
	 */
	public boolean verifyIfdefectClosed();
	
	/**
	 * Reopen defect.
	 */
	public void reopenDefect();
	
	/**
	 * Gets the defect status.
	 *
	 * @return the defect status
	 */
	public String getDefectStatus();
		
	/**
	 * Query defect id.
	 *
	 * @param testcaseId the testcase id
	 * @param storyId the story id
	 * @param projId the proj id
	 * @param defName the def name
	 * @return the string
	 */
	public String queryDefectID(String testcaseId, String storyId, String projId, String defName);
	
	/**
	 * Query defect status.
	 *
	 * @param testcaseId the testcase id
	 * @param storyId the story id
	 * @param projId the proj id
	 * @param defName the def name
	 * @return the string
	 */
	public String queryDefectStatus(String testcaseId, String storyId, String projId, String defName);
	
}
