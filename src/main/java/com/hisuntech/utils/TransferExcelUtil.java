package com.hisuntech.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Description  通过POI读取Excel文件
 * @author liling
 * @create 2018.6
 */
public class TransferExcelUtil {

    public static void readExcel(String path){
        try {
            InputStream is = new FileInputStream(path);
//            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
            Workbook workbook = new HSSFWorkbook(is);
//            Cell cell = new HSSFCell();
            int sheetSize = workbook.getNumberOfSheets();
            for (int sheetNum = 0; sheetNum < sheetSize; sheetNum++){
                Sheet currentSheet = workbook.getSheetAt(sheetNum);
                if (checkString(currentSheet.getSheetName())){

                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 判断字符串是否全为英文字母，是则返回true
     * @param str
     * @return
     */
    public static boolean checkString(String str){
        boolean enStr = str.matches("[a-zA-Z]+");
        return enStr;
    }
}
