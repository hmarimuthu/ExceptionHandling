package com.etaap.manage.defect;

import java.net.URL;
/**
 * A POJO class to keep information of each defect
 * @author rThangavelu
 *
 */
public class Defect {
	
	private DefectTitle title;
	
	private StringBuilder description;
	
	private StringBuilder notes;
	
	private URL attachments;
	
	private String priority;
	
	private String seveority;
	
	private String status;
	
	private String resolution;
	
	private String environment;

	public DefectTitle getTitle() {
		return title;
	}

	public void setTitle(DefectTitle title) {
		this.title = title;
	}

	public StringBuilder getDescription() {
		return description;
	}

	public void setDescription(StringBuilder description) {
		this.description = description;
	}

	public StringBuilder getNotes() {
		return notes;
	}

	public void setNotes(StringBuilder notes) {
		this.notes = notes;
	}

	public URL getAttachments() {
		return attachments;
	}

	public void setAttachments(URL attachments) {
		this.attachments = attachments;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getSeveority() {
		return seveority;
	}

	public void setSeveority(String seveority) {
		this.seveority = seveority;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	
	
	
	

}
