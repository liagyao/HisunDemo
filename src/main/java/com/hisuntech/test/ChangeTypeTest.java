package com.hisuntech.test;

import com.hisuntech.entity.Field;
import com.hisuntech.entity.Table;
import com.hisuntech.utils.TypeMappingUtil;

import java.util.ArrayList;
import java.util.List;

public class ChangeTypeTest {
    public static void main(String[] args) {

        Field f1=new Field("masterNo","科目组号","CHAR(6)","否","是","","","D","科目组号");
        Field f2=new Field("chartOfAccountsFlag","科目组号","DBCLOB","否","是","","","D","科目组号");
        Field f3=new Field("shortName","科目组号","BIGINT","否","是","","","D","科目组号");
        Field f4=new Field("longName","科目组号","CLOB","否","是","","","D","科目组号");
        Field f5=new Field("effectiveDate","科目组号","DBCLOB","否","是","","","D","科目组号");

        List<Field> fields=new ArrayList<>();
      //  fields.add(f1);
        fields.add(f1);
        fields.add(f2);
        fields.add(f3);
        fields.add(f4);
        fields.add(f5);
        Table table=new Table("name","tableEnName",fields,"databaseName","MYSQL");
        List<Table> tables=new ArrayList<>();
        tables.add(table);
        TypeMappingUtil.ChangeTypeALL(tables);
        for (int i = 0; i <tables.size() ; i++) {
            for (int j = 0; j < tables.get(i).getFields().size(); j++) {
                System.out.println(tables.get(i).getFields().get(j).getFieldType());
            }
        }


    }
}
