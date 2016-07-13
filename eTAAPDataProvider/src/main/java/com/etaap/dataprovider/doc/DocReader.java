package com.etaap.dataprovider.doc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFFooter;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.xmlbeans.XmlException;

import com.etaap.common.util.CommonUtil;
import com.etaap.common.util.LogUtil;
import com.etaap.core.exception.ExceptionListener;
import com.etaap.core.exception.HandleException;
import com.etaap.core.exception.handler.ExceptionHandler;

public class DocReader implements ExceptionListener {

	/** The Log object **/
	private static Log log = LogUtil.getLog(DocReader.class);
	private XWPFDocument doc = null;
	File file = null;

	@HandleException(expected = { Exception.class })
	public DocReader(String filePath) {
		try {
			file = new File(filePath);
			FileInputStream fis = new FileInputStream(file.getAbsolutePath());
			this.doc = new XWPFDocument(fis);

		} catch (Exception e) {
			ExceptionHandler ex = new ExceptionHandler();
			ex.handleit(this, e);
		}

	}

	/*
	 * This method returns the paragraphs from .doc document as a String
	 */
	public StringBuilder readParagraphs() {

		StringBuilder sb = new StringBuilder();
		/** Get the total number of paragraphs **/
		List<XWPFParagraph> paragraphs = doc.getParagraphs();

		CommonUtil.sop("Total Paragraphs: " + paragraphs.size());

		for (int i = 0; i < paragraphs.size(); i++) {

			CommonUtil.sop("Length of paragraph " + (i + 1) + ": " + paragraphs.get(i).getParagraphText().length());
			sb.append(paragraphs.get(i).getParagraphText() + "\n");

		}

		return sb;

	}

	/*
	 * This method returns the Header from the .doc file.
	 */
	@HandleException(expected = { IOException.class, XmlException.class })
	public String readHeader(int pageNumber) throws IOException, XmlException {

		StringBuilder sb = new StringBuilder();
		XWPFHeaderFooterPolicy headerStore = new XWPFHeaderFooterPolicy(doc);
		XWPFHeader header = headerStore.getHeader(pageNumber);
		sb.append(header.getText());
		return sb.toString();

	}

	/*
	 * This method returns the footer from .doc file
	 */
	@HandleException(expected = { IOException.class, XmlException.class })
	public String readFooter(int pageNumber) throws IOException, XmlException {
		StringBuilder sb = new StringBuilder();
		XWPFHeaderFooterPolicy footerStore = new XWPFHeaderFooterPolicy(doc);
		XWPFFooter footer = footerStore.getFooter(pageNumber);
		sb.append(footer.getText());

		return sb.toString();

	}

	/*
	 * This method returns the document summary as a String
	 */
	public String readDocumentSummary() {

		StringBuilder sb = new StringBuilder();

		return sb.toString();

	}

	/*
	 * This method returns List of images read from the .doc file
	 */
	@HandleException(expected = { Exception.class })
	public List<byte[]> readImages() {
		List<byte[]> result = new ArrayList<>();

		try {

			for (int i = 0; i < doc.getAllPictures().size(); i++) {
				result.add(doc.getAllPictures().get(i).getData());
			}
		} catch (Exception e) {
			ExceptionHandler ex = new ExceptionHandler();
			ex.handleit(this, e);
		}

		return result;

	}

}
