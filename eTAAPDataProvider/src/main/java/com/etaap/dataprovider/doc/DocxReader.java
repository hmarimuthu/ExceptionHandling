package com.etaap.dataprovider.doc;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.apache.xmlbeans.XmlException;

import com.etaap.common.util.LogUtil;
import com.etaap.core.exception.ExceptionListener;
import com.etaap.core.exception.HandleException;
import com.etaap.core.exception.handler.ExceptionHandler;

public class DocxReader implements ExceptionListener {

	private static Log log = LogUtil.getLog(DocxReader.class);

	private XWPFDocument doc = null;

	@HandleException(expected = { Exception.class })
	public DocxReader(String filePath) {
		try {
			InputStream fs = new FileInputStream(filePath);
			this.doc = new XWPFDocument(OPCPackage.open(fs));
		} catch (Exception e) {
			ExceptionHandler ex = new ExceptionHandler();
			ex.handleit(this, e);
		}

	}

	/*
	 * This method returns the paragraphs from .docx document as a String
	 */
	@HandleException(expected = IOException.class)
	public String readParagraphs() throws IOException {
		XWPFWordExtractor extractor = null;
		StringBuilder sb = new StringBuilder();
		try {
			extractor = new XWPFWordExtractor(doc);
			sb.append(extractor.getText());
		} finally {
			if (extractor != null)
				extractor.close();
		}

		return sb.toString();
	}

	/*
	 * This method returns the Header from the .docx file.
	 */
	@HandleException(expected = { XmlException.class, IOException.class })
	public String readHeader(int pageNumber) throws XmlException, IOException {

		StringBuilder sb = new StringBuilder();
		XWPFHeaderFooterPolicy headerStore = new XWPFHeaderFooterPolicy(doc);
		String header = (headerStore.getHeader(pageNumber)).getText();
		sb.append(header);
		return sb.toString();

	}

	/*
	 * This method returns the footer from .docx file
	 */
	@HandleException(expected = { Exception.class })
	public String readFooter(int pageNumber) {

		StringBuilder sb = new StringBuilder();
		try {
			XWPFHeaderFooterPolicy headerStore = new XWPFHeaderFooterPolicy(doc);
			String footer = (headerStore.getFooter(pageNumber)).getText();
			sb.append(footer);
		} catch (Exception e) {
			ExceptionHandler ex = new ExceptionHandler();
			ex.handleit(this, e);
		}
		return sb.toString();

	}

	/*
	 * This method returns List of images read from the .docx file
	 */
	@HandleException(expected = { Exception.class })
	public List<byte[]> readImages() {

		List<byte[]> result = new ArrayList<>();

		try {
			for (XWPFPictureData picture : doc.getAllPictures()) {
				result.add(picture.getData());
			}

		} catch (Exception e) {
			ExceptionHandler ex = new ExceptionHandler();
			ex.handleit(this, e);
		}

		return result;
	}
}// end of class