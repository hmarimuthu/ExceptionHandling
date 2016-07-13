package com.etaap.dataprovider.excel.util;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.etaap.common.util.LogUtil;
import com.etaap.core.exception.handler.ExceptionHandler;
import com.etaap.dataprovider.TestParameters;
import com.etaap.dataprovider.excel.reader.XlsxReader;

public class ExcelUtil {

	/** The log. */
	private static Log log = LogUtil.getLog(ExcelUtil.class);

	/**
	 * CommonMethod(readExcelData) which reads the data from the excel sheet.
	 * 
	 * @param filePath
	 *            the file path
	 * @param sheetName
	 *            the sheet name
	 * @param tableName
	 *            the table name
	 * @return the test parameters[][]
	 */
	public static TestParameters[][] readExcelDataParams(String filePath, String sheetName, String tableName) {
		TestParameters[][] testData = null;
		int testParamColSize = 1;

		try (HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(filePath))) {
			HSSFSheet sheet = workbook.getSheet(sheetName);
			log.debug("sheetName------------------" + sheetName);
			HSSFCell[] boundaryCells = com.etaap.common.util.ExcelUtil.findCell(sheet, tableName);
			log.debug("tableName------------------" + tableName);
			HSSFCell startCell = boundaryCells[0];
			HSSFCell endCell = boundaryCells[1];
			int headerRow = startCell.getRowIndex();
			int startRow = startCell.getRowIndex() + 1;
			int endRow = endCell.getRowIndex();
			int startCol = startCell.getColumnIndex() + 1;
			int endCol = endCell.getColumnIndex() - 1;

			testData = new TestParameters[endRow - startRow + 1][testParamColSize];
			for (int i = 0; i < (endRow - startRow + 1); i++) {
				testData[i][testParamColSize - 1] = new TestParameters();
			}

			for (int i = startRow; i < endRow + 1; i++) {
				for (int j = startCol; j < endCol + 1; j++) {
					if (null == sheet.getRow(i).getCell(j)
							|| sheet.getRow(i).getCell(j).getCellType() == HSSFCell.CELL_TYPE_BLANK) {
						testData[i - startRow][testParamColSize - 1]
								.setParamMap(sheet.getRow(headerRow).getCell(j).getStringCellValue(), "");
					} else if (sheet.getRow(i).getCell(j).getCellType() == HSSFCell.CELL_TYPE_STRING) {
						testData[i - startRow][testParamColSize - 1].setParamMap(
								sheet.getRow(headerRow).getCell(j).getStringCellValue(),
								sheet.getRow(i).getCell(j).getStringCellValue());
					} else if (sheet.getRow(i).getCell(j).getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
						Double temp = sheet.getRow(i).getCell(j).getNumericCellValue();
						testData[i - startRow][testParamColSize - 1].setParamMap(
								sheet.getRow(headerRow).getCell(j).getStringCellValue(),
								String.valueOf(temp.intValue()));
					} else if (sheet.getRow(i).getCell(j).getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {
						Boolean temp = sheet.getRow(i).getCell(j).getBooleanCellValue();
						testData[i - startRow][testParamColSize - 1].setParamMap(
								sheet.getRow(headerRow).getCell(j).getStringCellValue(),
								String.valueOf(temp.booleanValue()));
					}

				}
			}
		} catch (IOException e) {
			log.debug("Could not read the Excel sheet");
			log.debug("FileNotFoundException", e);
			ExceptionHandler ex = new ExceptionHandler();
			ex.handleit(ExcelUtil.class, new IOException("Could not read the Excel sheet"));

		}
		return testData;
	}

	/**
	 * Gets the data.
	 * 
	 * @param xls
	 *            the xls
	 * @param testCaseName
	 *            the test case name
	 * @return the data
	 */
	public static Object[][] getData(XlsxReader xls, String testCaseName) {

		log.debug("Reader sheet name:" + xls + ", testcasename:" + testCaseName);
		// if the test data sheet is not present for a test case
		if (!xls.isSheetExist(testCaseName)) {
			return new Object[1][0];
		}

		int rows = xls.getRowCount(testCaseName);
		int cols = xls.getColumnCount(testCaseName);

		// Retrieving data from excel
		Object[][] data = new Object[rows - 1][cols - 3];
		for (int rowNum = 2; rowNum <= rows; rowNum++) {
			for (int colNum = 0; colNum < cols - 3; colNum++) {
				data[rowNum - 2][colNum] = xls.getCellData(testCaseName, colNum, rowNum);
			}
		}
		return data;
	}

	// checks Runmode for dataSet
	/**
	 * Gets the data set runmodes.
	 * 
	 * @param xlsFile
	 *            the xls file
	 * @param sheetName
	 *            the sheet name
	 * @return the data set runmodes
	 */
	public static String[] getDataSetRunmodes(XlsxReader xlsFile, String sheetName) {
		String[] runmodes = null;
		if (!xlsFile.isSheetExist(sheetName)) {
			xlsFile = null;
			sheetName = null;
			runmodes = new String[1];
			runmodes[0] = "Y";
			xlsFile = null;
			sheetName = null;
			return runmodes;
		}
		runmodes = new String[xlsFile.getRowCount(sheetName) - 1];
		for (int i = 2; i <= runmodes.length + 1; i++) {
			runmodes[i - 2] = xlsFile.getCellData(sheetName, "Runmode", i);
			log.debug("Runmodes of sheet:" + xlsFile.getCellData(sheetName, "Runmode", i));
		}
		xlsFile = null;
		sheetName = null;
		return runmodes;

	}

	/**
	 * Report data set result. updating results for a particular data set
	 * 
	 * @param xls
	 *            the xls
	 * @param testCaseName
	 *            the test case name
	 * @param rowNum
	 *            the row num
	 * @param result
	 *            the result
	 */
	public static void reportDataSetResult(XlsxReader xls, String testCaseName, int rowNum, String result) {
		xls.setCellData(testCaseName, "Results", rowNum, result);
	}

	/**
	 * Report data set result class link. updating results for a particular data
	 * set
	 * 
	 * @param xls
	 *            the xls
	 * @param testCaseName
	 *            the test case name
	 * @param rowNum
	 *            the row num
	 * @param result
	 *            the result
	 */
	public static void reportDataSetResultClassLink(XlsxReader xls, String testCaseName, int rowNum, String result) {
		xls.setCellData(testCaseName, "ClassregLnk", rowNum, result);
	}

	/**
	 * Report data set result class id. updating results for a particular data
	 * set
	 * 
	 * @param xls
	 *            the xls
	 * @param testCaseName
	 *            the test case name
	 * @param rowNum
	 *            the row num
	 * @param result
	 *            the result
	 */
	public static void reportDataSetResultClassId(XlsxReader xls, String testCaseName, int rowNum, String result) {
		xls.setCellData(testCaseName, "ClassId", rowNum, result);
	}

	/**
	 * Gets the row num.
	 * 
	 * @param xls
	 *            the xls
	 * @param id
	 *            the id
	 * @return the row num
	 */
	public static int getRowNum(XlsxReader xls, String id) {
		for (int i = 2; i <= xls.getRowCount("Test Cases"); i++) {
			String tcid = xls.getCellData("Test Cases", "TCID", i);
			if (tcid.equals(id)) {
				return i;
			}
		}
		return -1;
	}

}
