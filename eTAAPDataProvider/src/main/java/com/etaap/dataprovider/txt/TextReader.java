package com.etaap.dataprovider.txt;

import java.io.BufferedReader;
import java.io.FileReader;

import org.apache.commons.logging.Log;

import com.etaap.common.util.LogUtil;
import com.etaap.core.exception.ExceptionListener;
import com.etaap.core.exception.HandleException;
import com.etaap.core.exception.handler.ExceptionHandler;

public class TextReader implements ExceptionListener {

	private static Log log = LogUtil.getLog(TextReader.class);

	String filePath = null;

	public TextReader(String filePath) {
		this.filePath = filePath;
	}

	@HandleException(expected = { Exception.class })
	public String readText() {
		String fileText = null;
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				sb.append(System.getProperty("line.separator"));
				line = br.readLine();
			}
			fileText = sb.toString();
		} catch (Exception e) {
			log.error(e);
			String exResult = e.getMessage();
			ExceptionHandler ex = new ExceptionHandler();
			ex.handleit(this, new Exception(exResult));
		}
		return fileText;
	}

	public static void main(String args[]) {
		log.debug(new TextReader("C:\\Lavanya\\Project\\eTouch\\eTap_framework\\eTap_Framework_flow.txt").readText());
	}

}
