package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

    //文件路径
    public String filePath;
    //文件名，不包含文件后缀.xls
    public String fileName;
    //sheet名
    public String caseName;

    public XSSFWorkbook workbook;
    public XSSFSheet sheet;
    public int rows;
    public int columns;
    public ArrayList<String> arrKey = new ArrayList<String>();
    public String sourceFile;


    /**
     *
     */
    public ExcelUtils(String filePath, String fileName, String caseName) {
        super();
        this.filePath = filePath;
        this.fileName = fileName;
        this.caseName = caseName;

    }

    public Object[][] getExcelData() throws IOException {
        FileInputStream ExcelFile = new FileInputStream(this.setPath(filePath, fileName));
        workbook = new XSSFWorkbook(ExcelFile);
        sheet = workbook.getSheet(caseName);
        rows = sheet.getPhysicalNumberOfRows();
        columns = sheet.getRow(0).getPhysicalNumberOfCells();

        //定义返回
        HashMap<String, String>[][] arrMap = new HashMap[rows -1][1];
        if(rows > 1) {
            for(int i = 0; i < rows-1; i++) {
                arrMap[i][0] = new HashMap<String, String>(100);
            }
        } else {
            System.out.println("excel中没有数据");
        }

        //获取第一行数据，作为hashMap的key
        for(int c = 0; c < columns; c++) {
            String cellValue = sheet.getRow(0).getCell(c).getStringCellValue();
            arrKey.add(cellValue);
        }

        //遍历所有单元格的值放置到hashMap
        for(int r = 1; r < rows; r++) {
            for(int c = 0; c < columns; c++) {
                String cellValue = sheet.getRow(r).getCell(c).getStringCellValue();
                arrMap[r - 1][0].put(arrKey.get(c), cellValue);
            }
        }

        return arrMap;
    }

    public String setPath(String path, String fileName) throws IOException {
        File directory = new File(".");
        sourceFile = directory.getCanonicalPath() + path + fileName + ".xlsx";
        return sourceFile;
    }
}
