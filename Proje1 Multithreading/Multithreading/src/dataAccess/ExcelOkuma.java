package dataAccess;


import entities.Data;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;


public class ExcelOkuma implements IDataRepositoryDal{
    
    public ArrayList<Data> verileriCek(){
        ArrayList<Data> veriler = new ArrayList<Data>();
        long starttime = System.currentTimeMillis();
        try { 
            File file = new File("C:\\Users\\mirac\\OneDrive\\Masaüstü\\KOU Prolab\\Yazlab 1\\Proje2 Multithreading\\veriler800.xlsx");
            Workbook workbook = WorkbookFactory.create(file);        
            Iterator<Sheet> sheetIterator = workbook.sheetIterator();
            Sheet productSheet = sheetIterator.next(); 
            Iterator<Row> rowIterator = productSheet.rowIterator();
            int i = 0;
            
            while (rowIterator.hasNext()) {
                Data data = new Data();
                Row row = rowIterator.next();
                
                if (row.getRowNum() == 0) {
                    // baslik satirini atla
                    continue;
                }
                
                Cell cell1 = row.getCell(0);
                data.setId(Integer.valueOf((int) cell1.getNumericCellValue()));
                
                Cell cell2 = row.getCell(1);
                data.setProduct(cell2.getStringCellValue());
                
                Cell cell3 = row.getCell(2);
                data.setIssue(cell3.getStringCellValue());
                
                Cell cell4 = row.getCell(3);
                data.setCompany(cell4.getStringCellValue());
                
                Cell cell5 = row.getCell(4);
                data.setState(cell5.getStringCellValue());
                
                Cell cell6 = row.getCell(5);
                data.setZipCode(cell6.getStringCellValue());
                
                Cell cell7 = row.getCell(6);
                data.setComplaintId(Integer.valueOf((int)cell7.getNumericCellValue()));
                
                veriler.add(data);
                i++;
            }
        } catch (IOException ex) {
            Logger.getLogger(ExcelOkuma.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidFormatException ex) {
            Logger.getLogger(ExcelOkuma.class.getName()).log(Level.SEVERE, null, ex);
        } catch (EncryptedDocumentException ex) {
            Logger.getLogger(ExcelOkuma.class.getName()).log(Level.SEVERE, null, ex);
        }
        long endtime = System.currentTimeMillis();
        double sonuc = (double)(endtime-starttime)/1000;
        System.out.println(veriler.size() + " veri basarıyla cekildi...  sure: "+sonuc);
        
        return veriler;
    }
}
