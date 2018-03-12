//package com.iqa.utilities;
//
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//
///**
// * Created by louis on 2018/09/04.
// */
//public class EVerifyReadResultsTemplate {
//
//    private static final String FILE_NAME = "/home/louis/results_template.xlsx";
//
//    public static void main(String[] args) {
//
//        try {
//
//            FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
//            Workbook workbook = new XSSFWorkbook(excelFile);
//            Sheet datatypeSheet = workbook.getSheetAt(0);
//            Iterator<Row> iterator = datatypeSheet.iterator();
//            Map<String,String> results=new HashMap<>() ;
//            int count = 0;
//            ArrayList headers=new ArrayList();
//
//
//
//            while (iterator.hasNext()) {
//
//                Row currentRow = iterator.next();
//                Iterator<Cell> cellIterator = currentRow.iterator();
//
//                while (cellIterator.hasNext()) {
//
//                    Cell currentCell = cellIterator.next();
//                    //getCellTypeEnum shown as deprecated for version 3.15
//                    //getCellTypeEnum ill be renamed to getCellType starting from version 4.0
//
//                    if (currentCell.getCellTypeEnum() == CellType.STRING) {
//
//                        System.out.print(currentCell.getStringCellValue() + "--");
//                    } else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
//                        System.out.print(currentCell.getNumericCellValue() + "--");
//                    }
//
//                    if(count==0){
//                        headers.add(currentCell.getStringCellValue());
//                        System.out.println("Header: "+currentCell.getStringCellValue() + "--");
//                    }
//
//                }
//                System.out.println();
//                count++;
//            }
//            System.out.print("");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//}