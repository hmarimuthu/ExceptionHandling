package com.etaap.common.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.etaap.core.exception.ExceptionListener;
import com.etaap.core.exception.HandleException;
import com.etaap.core.exception.handler.ExceptionHandler;

/**
 * This class contains utility methods for reading excel data.
 * 
 * @author eTouch Systems Corporation
 * @version 1.0
 * 
 */
public class ExcelUtil implements ExceptionListener {

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
	 * @return the string[][]
	 */

	/*
	 * This method reads the data from Excel files with .xls extension.
	 */
	@HandleException(expected = { IOException.class })
	public static String[][] readExcelData(String filePath, String sheetName, String tableName) {
		String[][] testData = null;

		try (HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(filePath))) {
			HSSFSheet sheet = workbook.getSheet(sheetName);
			log.debug("sheetName------------------" + sheetName);
			HSSFCell[] boundaryCells = findCell(sheet, tableName);
			log.debug("tableName------------------" + tableName);
			HSSFCell startCell = boundaryCells[0];
			HSSFCell endCell = boundaryCells[1];
			int startRow = startCell.getRowIndex() + 1;
			int endRow = endCell.getRowIndex();
			int startCol = startCell.getColumnIndex() + 1;
			int endCol = endCell.getColumnIndex() - 1;
			testData = new String[endRow - startRow + 1][endCol - startCol + 1];

			for (int i = startRow; i < endRow + 1; i++) {
				for (int j = startCol; j < endCol + 1; j++) {
					if (null == sheet.getRow(i).getCell(j)
							|| sheet.getRow(i).getCell(j).getCellType() == HSSFCell.CELL_TYPE_BLANK) {
						testData[i - startRow][j - startCol] = "";
					} else {

						if (sheet.getRow(i).getCell(j).getCellType() == HSSFCell.CELL_TYPE_STRING) {
							testData[i - startRow][j - startCol] = sheet.getRow(i).getCell(j).getStringCellValue();
						} else if (sheet.getRow(i).getCell(j).getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							Double temp = sheet.getRow(i).getCell(j).getNumericCellValue();
							testData[i - startRow][j - startCol] = String.valueOf(temp.intValue());
						} else if (sheet.getRow(i).getCell(j).getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {
							Boolean temp = sheet.getRow(i).getCell(j).getBooleanCellValue();
							testData[i - startRow][j - startCol] = String.valueOf(temp.booleanValue());
						}
					}

				}
			}
		} catch (IOException e) {
			log.debug("Could not read the Excel sheet");
			log.debug("IOException", e);
			ExceptionHandler ex = new ExceptionHandler();
			ex.handleit(ExcelUtil.class, e);

		}
		return testData;
	}

	/**
	 * Find cell.
	 * 
	 * @param sheet
	 *            the sheet
	 * @param text
	 *            the text
	 * @return the HSSF cell[]
	 */
	public static Cell[] findCell(Sheet sheet, String text) {
		String pos = "start";
		Cell[] cells = new Cell[2];
		for (Row row : sheet) {
			for (Cell cell : row) {
				if (cell.getCellType() == Cell.CELL_TYPE_STRING
						&& text.equalsIgnoreCase(cell.getStringCellValue().trim())) {
					if (pos.equalsIgnoreCase("start")) {
						cells[0] = (Cell) cell;
						pos = "end";
					} else {
						cells[1] = (Cell) cell;
					}
				}
			}
		}
		return cells;
	}

	/**
	 * Find range.
	 * 
	 * @param sheet
	 *            the sheet
	 * @param text
	 *            the text
	 * @return the HSSF cell[]
	 */
	public static HSSFCell[] findRange(HSSFSheet sheet, String text) {
		String pos = "start";
		HSSFCell[] cells = new HSSFCell[2];
		for (Row row : sheet) {
			for (Cell cell : row) {
				if (text.equals(cell.getStringCellValue())) {
					if (pos.equalsIgnoreCase("start")) {
						cells[0] = (HSSFCell) cell;
						pos = "end";
					} else {
						cells[1] = (HSSFCell) cell;
					}
				}
			}
		}
		return cells;
	}

	/**
	 * Find cell.
	 * 
	 * @param sheet
	 *            the sheet
	 * @param text
	 *            the text
	 * @return the HSSF cell[]
	 */
	public static HSSFCell[] findCell(HSSFSheet sheet, String text) {
		String pos = "start";
		HSSFCell[] cells = new HSSFCell[2];
		for (Row row : sheet) {
			for (Cell cell : row) {
				if (cell.getCellType() == Cell.CELL_TYPE_STRING && text.equals(cell.getStringCellValue())) {

					if (pos.equalsIgnoreCase("start")) {
						cells[0] = (HSSFCell) cell;
						pos = "end";
					} else {
						cells[1] = (HSSFCell) cell;
					}
				}
			}
		}
		return cells;
	}

	public static boolean isCellBlank(Cell cell) {

		if ((cell == null) || (cell.getCellType() == Cell.CELL_TYPE_BLANK)) {
			return true;
		} else {
			return false;
		}
	}

	public static Cell findCell(Sheet sheet, int startRow, int endRow, int searchColumnIndex) {

		Cell cell = null;

		for (int rowIndex = startRow; rowIndex <= endRow; rowIndex++) {

			Row row = sheet.getRow(rowIndex);

			if (!ExcelUtil.isCellBlank(row.getCell(searchColumnIndex))) {
				cell = row.getCell(searchColumnIndex);
			}

		}

		return cell;
	}

	public static Cell[] findCell(Sheet sheet, String textLookup, int startCol, int endCol) {

		Cell[] cells = new Cell[2];

		for (Row row : sheet) {

			if (!ExcelUtil.isCellBlank(row.getCell(startCol))) {

				Cell startCell = row.getCell(startCol);

				if (startCell.getCellType() == Cell.CELL_TYPE_STRING
						&& textLookup.equals(startCell.getStringCellValue().trim())) {
					cells[0] = (Cell) startCell;
				}
			}

			if (!ExcelUtil.isCellBlank(row.getCell(endCol))) {

				Cell endCell = row.getCell(endCol);

				if (endCell.getCellType() == Cell.CELL_TYPE_STRING
						&& textLookup.equals(endCell.getStringCellValue().trim())) {
					cells[1] = (Cell) endCell;
				}
			}

		}

		return cells;
	}

	@HandleException(expected = { NoSuchMethodException.class, SecurityException.class, ClassNotFoundException.class })
	public Method getMethod(String className, String methodName, Class[] parameterClasses)
			throws NoSuchMethodException, ClassNotFoundException {
		return Class.forName(className).getMethod(methodName, parameterClasses);
	}

	public static Object parserNumberFormat(double numericCellValue) {

		long integerValue = Math.round(numericCellValue);

		if (numericCellValue == integerValue) {
			return (Long) integerValue;
		} else {
			return (Double) numericCellValue;
		}
	}

}
