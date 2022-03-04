package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {
	private static ArrayList <String> headerList = new ArrayList <String> ();
    private static ArrayList <String> testdataList = new ArrayList <String> ();
    //public static String TCLanguage;
   
 
    public static void readExcel(String scenarioName)throws IOException
	{
    	System.out.println("Read Excecl for case: "+scenarioName);
    	XSSFWorkbook excelWBook;
        XSSFSheet excelWSheet;

        DataFormatter dataFormatter = new DataFormatter();
        
        String path = "resource/TestData.xlsx";

        try {
            FileInputStream excelFile = new FileInputStream(path);
            excelWBook = new XSSFWorkbook(excelFile);
            excelWSheet = excelWBook.getSheetAt(0);
            headerList.clear();
            testdataList.clear();

            for (Row row: excelWSheet) {
                String cellValue1stCol = dataFormatter.formatCellValue(row.getCell(0));
                //Setup the array list for header
                
                
              if (row.getRowNum() >= 3) {
                if (cellValue1stCol.trim().equals("Test Case ID")) {
                    for (Cell cell: row) {
                        String cellValue = dataFormatter.formatCellValue(cell);
                     //   System.out.print(cellValue +"\t;");
                        headerList.add(cellValue);
                    }
                    
                    System.out.println();
                }
                //Setup the array list for test data
                else if (cellValue1stCol.trim().equals(scenarioName.trim())) {
                    for (Cell cell: row) {
                        String cellValue = dataFormatter.formatCellValue(cell);
                     //   System.out.print(cellValue +"\t;");
                        testdataList.add(cellValue);
                    }
                    System.out.println();
                    
                }
                    
                }
            }
           
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        int hlsize = headerList.size();
        int dlsize = testdataList.size();
        System.out.println("hlsize:"+hlsize);
        System.out.println("dlsize:"+dlsize);
	}
    
    public static String getValue(String fieldName) {
    	int ColIndex = headerList.indexOf(fieldName.trim());
    	String fieldValue = testdataList.get(ColIndex).trim();
    	return fieldValue;
     }
  
    
    
    /*public static void writeRuntime(String scenarioName, String runTime, String status,boolean run_flag) {
    		String path = "resource/Runtime.xlsx";	
	    	if (!run_flag) {
	    		System.out.println("============no need to write runtime excel============");
			return;
		}else {
			File result = new File(path);
			if (!result.exists()) {
				System.out.println("============runtime excel not exists============");
				return;
			}
		}
    		
		System.out.println("write Runtime excle : "+scenarioName);
		XSSFWorkbook excelWBook;
	    XSSFSheet excelWSheet;
	
	    DataFormatter dataFormatter = new DataFormatter();
	    
	   
	
	    try {
	        FileInputStream excelFile = new FileInputStream(path);
	        excelWBook = new XSSFWorkbook(excelFile);
	        excelWSheet = excelWBook.getSheetAt(0);
	        boolean write_flag = false;
	        
	        Calendar my_calendar = Calendar.getInstance();
	        String run_date = String.valueOf(my_calendar.get(Calendar.MONTH) + 1) + "/" + String.valueOf(my_calendar.get(Calendar.DATE)) + "/" 
	        		+ String.valueOf(my_calendar.get(Calendar.YEAR));
	
	        for (Row row: excelWSheet) {
	            String cellValue1stCol = dataFormatter.formatCellValue(row.getCell(0));
	
	            if (cellValue1stCol.trim().equals(scenarioName.trim())) {
	            		writeCell(row,1,run_date);
			        writeCell(row,2,runTime);
			        writeCell(row,3,status);
	            		write_flag = true;
	            		break;
	            }
	        }
	        
	        if (!write_flag) {
	        		Row row = excelWSheet.createRow(excelWSheet.getLastRowNum() + 1);
	        		writeCell(row,0,scenarioName.trim());
		        writeCell(row,1,run_date);
		        writeCell(row,2,runTime);
		        writeCell(row,3,status);
			}
	        
	        FileOutputStream outputStream = new FileOutputStream(path);
	        outputStream.flush();
	        excelWBook.write(outputStream);
	        outputStream.close();
	        excelFile.close();
	    } catch (Exception e) {
	    		System.out.println("write runtime excel fail!");
	        e.printStackTrace();
	    }
	}*/
    
   /* private static void writeCell(Row row, int cell_index, String cell_value) {
		if (row.getCell(cell_index) != null) {
			row.getCell(cell_index).setCellValue(cell_value);
		}else {
			row.createCell(cell_index).setCellValue(cell_value);
		}
	}*/
}
