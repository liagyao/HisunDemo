package com.hisuntech.utils;


import com.hisuntech.entity.Field;
import com.hisuntech.entity.Table;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 读取Excel文件的工具类
 * @Author liling
 * @Create 2018.7.27
 */
public class ReadExcelUtil {

    public static void readExcel(File file){
        try {
            //创建输入流，读取Excel
            InputStream inputStream = new FileInputStream(file);
            Workbook workbook = Workbook.getWorkbook(inputStream);
            int sheetSize = workbook.getNumberOfSheets();
            for (int numSheet=0; numSheet<sheetSize; numSheet++){
                Sheet currentSheet = workbook.getSheet(numSheet);
                System.out.println(currentSheet.getName());
                if (numSheet >= 2){
                    Table table = getCreateTableInfo(currentSheet);
                    StringBuffer sql = outSQL(table);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
    }

    public static Table getCreateTableInfo(Sheet currentSheet){
        Cell cell = currentSheet.getCell(2,4);
        System.out.println("单元格的值:"+cell.getContents());
        System.out.println("行:"+currentSheet.getRows());
        System.out.println("列:"+currentSheet.getColumns());
        String tableEnName = currentSheet.getCell(2,2).getContents();
        String tableChName = currentSheet.getCell(2,3).getContents();
        String databaseBrand = currentSheet.getCell(7,4).getContents();
        String databaseName = null;
        List<Field> fieldList = new ArrayList<Field>();
        Field field = new Field();
        //定义接受的变量
        String fieldEnName = null;     //字段英文名
        String fieldChName = null;     //字段中文名
        String fieldType = null;           //字段类型
        String isNullAble = null;          //字段是否为空（是，否）
        String isPrimaryKey = null;    //字段是否是主键（是，否）
        String defaultValue = null;           //字段的默认值
        String indexNum = null;              //索引编号（数字）
        String indexType = null;              //索引类型（D，U）
        String fieldDescription = null;    //字段说明
        Table table = new Table();
        //从表格中取出各个字段
        for (int rowNum = 6; rowNum < currentSheet.getRows(); rowNum++){
              for (int columnNum = 2; columnNum < currentSheet.getColumns(); columnNum++){
                  String cellInfo = currentSheet.getCell(columnNum,rowNum).getContents();
                    if (columnNum == 2){
                        fieldEnName = cellInfo;
                    }else if (columnNum == 3){
                        fieldChName = cellInfo;
                    }else if (columnNum == 4){
                        fieldType = cellInfo;
                    }else if (columnNum == 5){
                        isNullAble = cellInfo;
                    }else if (columnNum == 6){
                        isPrimaryKey = cellInfo;
                    }else if (columnNum == 7){
                        defaultValue = cellInfo;
                    }else if (columnNum == 8){
                        indexNum = cellInfo;
                    }else if (columnNum == 9){
                        indexType = cellInfo;
                    }else if (columnNum == 10){
                        fieldDescription = cellInfo;
                    }
                System.out.print(currentSheet.getCell(columnNum,rowNum).getContents() + " ");
            }
            field = new Field(fieldEnName, fieldChName, fieldType, isNullAble, isPrimaryKey, defaultValue, indexNum, indexType, fieldDescription);
            fieldList.add(field);
        }

        table.setDatabaseBrand(databaseBrand);
        table = new Table(tableChName, tableEnName, fieldList, databaseBrand,databaseName);
        return table;
    }

    public static StringBuffer outSQL(Table table){
        StringBuffer createSQL = new StringBuffer();
        StringBuffer commentSQL = new StringBuffer();
        if ("ORACLE".equals(table.getDatabaseBrand())){
            createSQL = createSQL.append("CREATE TABLE").append("  "+table.getTableEnName()).append("(");
            List<Field> fieldList = table.getFields();
            int count = fieldList.size();           //计数器，观察是否到了最后一个元素
            //拼接SQL
            for (Field field: fieldList) {
                String fieldEnName = field.getFieldEnName();
                String fieldType = field.getFieldType();
                String isNullAble = field.getIsNullAble();
                String isPrimaryKey = field.getIsPrimaryKey();
                String defaultValue = field.getDefaultValue();
                createSQL.append("  "+fieldEnName).append("  "+fieldType);
                if (defaultValue != ""){
                    createSQL.append("  "+"DEFAULT"+defaultValue);
                }
                if ("是".equals(isPrimaryKey)){
                    createSQL.append("  "+"PRIMARY KEY PK_"+fieldEnName);
                }
                if ("否".equals(isNullAble)){
                    if (count == 1){
                        createSQL.append("  "+"NOT NULL");
                    }else{
                        createSQL.append("  "+"NOT NULL,");
                    }
                }else if ("是".equals(isNullAble) && count == 1){
                    createSQL.append("");
                }
                count--;
            }
            createSQL.append(");");
            System.out.println("\n建表SQL："+createSQL);
            //添加注释
            commentSQL.append("COMMENT ON TABLE").append("  "+table.getTableEnName()).append("  "+"IS").append("  "+"'"+table.getTableChName()+"'"+";");
            fieldList.stream().forEach(field ->{
                String fieldEnName = field.getFieldEnName();
                String fieldDescription = field.getFieldDescription();
                commentSQL.append("COMMENT ON COLUMN").append("  "+table.getTableEnName()+".").append(fieldEnName).append("  "+"IS").append("  "+"'"+fieldDescription+"'"+";");
            });
            System.out.println("\n注释SQL："+commentSQL);
        }
        return createSQL.append(commentSQL);
    }

    public static void main(String[] args) {
        File file = new File("F:\\HiSunTech\\templateExcel.xls");
        readExcel(file);
    }
}
