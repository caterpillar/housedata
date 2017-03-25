package com.wula.housedata.util;

import org.apache.poi.hssf.usermodel.*;

/**
 * Created by lishaohua on 17-3-18.
 */
public class ExcelFileCreater {
    private HSSFWorkbook wb = new HSSFWorkbook();
    HSSFSheet sheet;

    public ExcelFileCreater(String sheetName, String[] titles) {
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        this.sheet = wb.createSheet(sheetName);
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow row = sheet.createRow(0);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        HSSFCell cell = null;
        //创建标题
        for(int i=0;i<titles.length;i++){
            cell = row.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(style);
        }
    }

    public void appandValues(String[][] values) {
        HSSFRow row = null;
        //创建内容
        for(int i=0;i<values.length;i++){
            row = sheet.createRow(i + 1);
            for(int j=0;j<values[i].length;j++){
                row.createCell(j).setCellValue(values[i][j]);
            }
        }
    }
}
