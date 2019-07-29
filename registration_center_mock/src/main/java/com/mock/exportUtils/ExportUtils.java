package com.mock.exportUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExportUtils {
	
	public static void Xml2Excel(String filePath) throws IOException {
        XSSFWorkbook workbook1 = new XSSFWorkbook(new FileInputStream(new File(filePath)));
        SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook(workbook1, 100);
        SXSSFSheet first = sxssfWorkbook.getSheetAt(0);
        for (int i = 0; i < 1000000; i++) {
            SXSSFRow row = first.createRow(i);
            for (int j = 0; j < 11; j++) {
                if (i == 0) {
                    // 首行
                    row.createCell(j).setCellValue("column" + j);
                } else {
                    // 数据
                    if (j == 0) {
                        CellUtil.createCell(row, j, String.valueOf(i));
                    } else
                        CellUtil.createCell(row, j, String.valueOf(Math.random()));
                }
            }
        }
        FileOutputStream out = new FileOutputStream("/Users/micezhao/Downloads/workbook1.xlsx");
        sxssfWorkbook.write(out);
        out.close();
    }
	
	
	public static void main(String[] args) {
		for (int j = 0; j < 10; j++) {
				int i = j;
				System.out.println(i);
		}
	}
}
