package stock.com.cn;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
 
public class ReadExcel {
    /**
     * 读取excel文件
     * @param args
     */
  
    public static void main(String[] args) {
        Workbook wb = null; 
        Sheet sheet = null;
        Row row = null;
  
        String filePath = "D:\\wencai/20210104 涨幅；20210104开盘涨幅_....xlsx";
        System.out.println("filePath========"+filePath);
        wb = readExcel(filePath);
        if (wb != null) {
            try {
                List<List<List<Object>>> list = new ArrayList<>();
                 
                System.err.println("页签数量：" + wb.getNumberOfSheets());
                // 循环页签
                for (int sheetNum = 0; sheetNum < wb.getNumberOfSheets(); sheetNum++) {
                    // 指定页签的值
                    sheet = wb.getSheetAt(sheetNum);
                    // 定义存放一个页签中所有数据的List
                    List<List<Object>> sheetList = new ArrayList<>();
                 
                    System.err.println("行总数：" + sheet.getLastRowNum());
                    // 循环行
                    for (int rowNum = 0; rowNum <= sheet.getLastRowNum(); rowNum++) {
                        // 指定行的值
                        row = sheet.getRow(rowNum);
                        //System.out.println("row================"+row);
                        // 定义存放一行数据的List
                        List<Object> rowList = new ArrayList<>();
                        // 循环列
                        System.err.println("列总数：" + row.getLastCellNum());
                        for (int cellNum = 0; cellNum < row.getLastCellNum(); cellNum++) {
                            Cell cell = sheet.getRow(rowNum).getCell(cellNum);
                            rowList.add(getStringCellValue(cell));
                        }
                        sheetList.add(rowList);
                    }
                    list.add(sheetList);
                }
                System.err.println(list.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
     
     
       //判断文件格式
      static Workbook readExcel(String filePath){
        if(filePath==null){
            return null;
        }
        String extString = filePath.substring(filePath.lastIndexOf("."));
         
        try {
            //@SuppressWarnings("resource")
//            System.out.println("filePath====>>>>>"+filePath);
            InputStream is = new FileInputStream(filePath);
            if(".xls".equals(extString)){
                return new HSSFWorkbook(is);
            }else if(".xlsx".equals(extString)){
                return new XSSFWorkbook(is);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
  
     
     
    //@SuppressWarnings("deprecation")
    public static String getStringCellValue(Cell cell) {
        String cellvalue = "";
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
        case Cell.CELL_TYPE_STRING:
            cellvalue = cell.getStringCellValue();
            break;
        case Cell.CELL_TYPE_NUMERIC:
            if (DateUtil.isCellDateFormatted(cell)) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = cell.getDateCellValue();
                cellvalue = sdf.format(date);
            } else {
                cellvalue = String.valueOf(cell.getNumericCellValue());
            }
            break;
        case Cell.CELL_TYPE_BOOLEAN:
            cellvalue = String.valueOf(cell.getBooleanCellValue());
            break;
        case Cell.CELL_TYPE_BLANK:
            cellvalue = "";
            break;
        default:
            cellvalue = "";
            break;
        }
        if (cellvalue == "") {
            return "";
        }
        return cellvalue;
    }
     
}