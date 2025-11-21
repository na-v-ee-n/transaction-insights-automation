package com.transactioninsights.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtil {

    private static final String FILE_PATH = "src/test/resources/testcases/testcases.xlsx";

    public static Object[][] getTestCases() {
        List<Object[]> data = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(FILE_PATH);
                Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            int rowCount = sheet.getLastRowNum();

            for (int i = 1; i <= rowCount; i++) { // Start from 1 to skip header
                Row row = sheet.getRow(i);
                if (row != null) {
                    String testCaseId = getCellValue(row.getCell(0));
                    String testCaseTitle = getCellValue(row.getCell(1));
                    String preConditions = getCellValue(row.getCell(2));
                    String steps = getCellValue(row.getCell(3));
                    String expectedResult = getCellValue(row.getCell(4));

                    data.add(new Object[] { testCaseId, testCaseTitle, preConditions, steps, expectedResult });
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data.toArray(new Object[0][]);
    }

    public static void updateTestCaseStatus(String testCaseId, String status) {
        try (FileInputStream fis = new FileInputStream(FILE_PATH);
                Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            int rowCount = sheet.getLastRowNum();

            for (int i = 1; i <= rowCount; i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    String currentId = getCellValue(row.getCell(0));
                    if (currentId.equals(testCaseId)) {
                        Cell statusCell = row.createCell(5); // Column 5 is "Automated"
                        statusCell.setCellValue(status);
                        break;
                    }
                }
            }

            try (FileOutputStream fos = new FileOutputStream(FILE_PATH)) {
                workbook.write(fos);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getCellValue(Cell cell) {
        if (cell == null)
            return "";
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return "";
        }
    }
}
