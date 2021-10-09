package com.tester.httpServer.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FileUtils {
	public static String[][] xlsTables(String file) {
		int rownum = 0;
		int colnum = 0;
		Workbook wb = null;
		Sheet sheet = null;
		Row row = null;
		String cellData = null;
		wb = readExcel(file);
		String[][] strs = new String[rownum][colnum];
		if (wb != null) {
			// 获取第一个sheet
			sheet = wb.getSheetAt(0);
			// 获取最大行数
			rownum = sheet.getPhysicalNumberOfRows();
			// 获取第一行
			row = sheet.getRow(0);
			// 获取最大列数
			colnum = row.getPhysicalNumberOfCells();
			strs = new String[rownum][colnum];
			for (int i = 0; i < rownum; i++) {
				row = sheet.getRow(i);
				for (int j = 0; j < colnum; j++) {
					cellData = (String) getCellFormatValue(row.getCell(j));
					strs[i][j] = cellData;
				}
			}
		}

		// return list;
		return strs;
	}

	// 读取excel
	@SuppressWarnings("resource")
	public static Workbook readExcel(String file) {
		Workbook wb = null;
		if (file == null) {
			return null;
		}
		String extString = file.substring(file.lastIndexOf("."));
		InputStream is = null;
		try {
			is = new FileInputStream(file);
			if (".xls".equals(extString)) {
				return wb = new HSSFWorkbook(is);
			} else if (".xlsx".equals(extString)) {
				return wb = new XSSFWorkbook(is);
			} else {
				return wb = null;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return wb;
	}

	@SuppressWarnings("deprecation")
	public static Object getCellFormatValue(Cell cell) {
		Object cellValue = null;
		if (cell != null) {
			// 判断cell类型
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC: {
				cellValue = String.valueOf(cell.getNumericCellValue());
				break;
			}
			case Cell.CELL_TYPE_FORMULA: {
				// 判断cell是否为日期格式
				if (DateUtil.isCellDateFormatted(cell)) {
					// 转换为日期格式YYYY-mm-dd
					cellValue = cell.getDateCellValue();
				} else {
					// 数字
					cellValue = String.valueOf(cell.getNumericCellValue());
				}
				break;
			}
			case Cell.CELL_TYPE_STRING: {
				cellValue = cell.getRichStringCellValue().getString();
				break;
			}
			default:
				cellValue = "";
			}
			// cellValue = cell.getRichStringCellValue().getString();
		} else {
			cellValue = "";
		}
		return cellValue;
	}

	@SuppressWarnings("resource")
	public static ArrayList<String> readConfigFile(String filename) {
		Scanner scanner;
		ArrayList<String> content = new ArrayList<String>();
		try {
			scanner = new Scanner(new File(filename));
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				content.add(line);
			}
		} catch (FileNotFoundException e) {
//			e.printStackTrace();
			System.out.println("默认配置不存在");
		}
		return content;
	}
}