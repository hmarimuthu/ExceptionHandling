package com.etaap.manage.defect;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

/**
 * @author etouch
 *
 */
public interface DefectTool {

	public abstract void openConnection() throws URISyntaxException;

	public abstract void closeConnection() throws IOException;

	public abstract String getDefectStatus();

	public abstract boolean isDefectLogged(String defectTitle)
			throws InterruptedException, ExecutionException, IOException;

	public abstract void logDefect(String defectTitle, StringBuilder defectDescription, String defectPriority,
			File fileAttachment) throws URISyntaxException, IOException;

	public abstract void closeDefect(StringBuilder comments) throws URISyntaxException, IOException;

	public abstract void reOpenDefect(StringBuilder comments, File fileAttachment)
			throws URISyntaxException, IOException;

	public abstract void updateDefect(StringBuilder comments, File fileAttachment)
			throws URISyntaxException, IOException;

}
