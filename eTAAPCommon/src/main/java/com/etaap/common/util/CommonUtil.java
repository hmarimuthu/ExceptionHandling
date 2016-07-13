package com.etaap.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.http.client.ClientProtocolException;

import com.etaap.common.exception.ListException;
import com.etaap.core.exception.ExceptionListener;
import com.etaap.core.exception.HandleException;

/**
 * This class contains common utility functions.
 * 
 * @author eTouch Systems Corporation version 1.0
 * 
 */
public class CommonUtil implements ExceptionListener {

	/** The log. */
	static Log log = LogUtil.getLog(CommonUtil.class);

	/**
	 * Checks if is list in order.
	 * 
	 * @param sourceList1
	 *            the source list1
	 * @param sourceList2
	 *            the source list2
	 * @return true, if is list in order
	 * @throws ListException
	 *             the list exception
	 */
	@HandleException(expected = { ListException.class })
	public static synchronized boolean isListInOrder(List<String> sourceList1, List<String> sourceList2)
			throws ListException {
		boolean result = true;
		if (isNull(sourceList1) || isNull(sourceList2)) {
			log.error("failed to check order, source list 1 or 2 is not initialized - (" + sourceList1 + ","
					+ sourceList2 + ")");
			throw new ListException("failed to check order, source list 1 or 2 is not initialized");
		}
		if (sourceList1.size() != sourceList2.size()) {
			log.info("failed to check order, source list 1 or 2 size is different - (" + sourceList1.size() + ","
					+ sourceList2.size() + ")");
			result = false;
		} else {
			for (int i = 0; i < sourceList1.size(); i++) {
				if (!sourceList1.get(i).equals(sourceList2.get(i))) {
					result = false;
				}
			}
		}
		return result;
	}

	/**
	 * Compare text.
	 * 
	 * @param str1
	 *            the str1
	 * @param str2
	 *            the str2
	 * @return true, if successful
	 */
	public static synchronized boolean compareText(String str1, String str2) {
		if (isNull(str1) || isNull(str2)) {
			log.info("string 1 or 2 is not initialized - (" + str1 + "," + str2 + ")");
		}
		return str1.equals(str2);
	}

	/**
	 * Checks if is null.
	 * 
	 * @param objVal
	 *            the obj val
	 * @return true, if is null
	 */
	public static synchronized boolean isNull(Object objVal) {
		if (objVal == null)
			return true;
		return false;
	}

	/**
	 * Sop.
	 * 
	 * @param printStmt
	 *            the print stmt
	 */
	public static synchronized void sop(String printStmt) {

		log.debug(printStmt);
	}

	@HandleException(expected = { IOException.class })
	public static String formatInputStream(InputStream is) throws IOException {

		StringBuilder sb = new StringBuilder();

		String line;
		try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (IOException e) {
			log.error("IOException", e);
			throw e;
		}
		return sb.toString();

	}

	public static String replaceText(String reportTemplate, String locaterKey, String text) {
		return reportTemplate.replace(locaterKey, text);
	}

	public static String getErrorDetails(Throwable throwable) {

		StringBuffer errorDetails = new StringBuffer();

		if (throwable.getCause() != null)
			errorDetails.append(throwable.getCause());
		else if ((throwable.getMessage() != null) && (!throwable.getMessage().isEmpty()))
			errorDetails.append(throwable.getMessage());

		return errorDetails.toString();
	}

	/**
	 * Returns the Page response status code, viz. 200, 301, 302, 400, 404, 500
	 * 
	 * @param pageUrl
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static String getResponseCode(String pageUrl) throws IOException, ClientProtocolException {

		URL url = new URL(pageUrl);
		HttpURLConnection huc = (HttpURLConnection) url.openConnection();
		huc.setRequestMethod("GET");
		huc.connect();
		int responseCode = huc.getResponseCode();
		return String.valueOf(responseCode);

	}
}
