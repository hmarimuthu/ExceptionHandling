package com.etaap.dataprovider.reader;

import org.apache.commons.logging.Log;
import org.apache.http.HttpResponse;

import com.etaap.common.constants.UtilConstants;
import com.etaap.common.util.LogUtil;
import com.etaap.dataprovider.doc.DocReader;
import com.etaap.dataprovider.doc.DocxReader;
import com.etaap.dataprovider.excel.reader.XlsReader;
import com.etaap.dataprovider.excel.reader.XlsxReader;
import com.etaap.dataprovider.pdf.PDFFileReader;
import com.etaap.dataprovider.txt.TextReader;

public class FileReader {

	private static Log log = LogUtil.getLog(FileReader.class);

	private String fileType = null;

	public FileReader(HttpResponse response) {
		this.fileType = response.getEntity().getContentType().getValue();

	}

	public String getMIMEType() {
		return fileType;
	}

	public Object getReader(String filePath) {
		Object reader;
		if (fileType.equals(UtilConstants.PDF_TYPE)) {
			return new PDFFileReader();
		} else if (fileType.equals(UtilConstants.XLS_TYPE)) {
			reader = new XlsReader(filePath);
		} else if (fileType.equals(UtilConstants.XLSX_TYPE)) {
			reader = new XlsxReader(filePath);
		} else if (fileType.equals(UtilConstants.DOC_TYPE)) {
			reader = new DocReader(filePath);
		} else if (fileType.equals(UtilConstants.DOCX_TYPE)) {
			reader = new DocxReader(filePath);
		} else if (fileType.equals(UtilConstants.TXT_TYPE)) {
			reader = new TextReader(filePath);
		} else {
			reader = null;
		}
		return reader;
	}

}
