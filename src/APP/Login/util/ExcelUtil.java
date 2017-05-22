package APP.Login.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExcelUtil {
	public static void writeExcel(String dirFile,String fileName,List list){
		String currentDate = (new SimpleDateFormat("yy-MM-dd HH:mm")).format(new Date());
		FileInputStream fs = null;
		HSSFWorkbook wb = null;
		POIFSFileSystem ps = null;
		FileOutputStream out = null;
		try{
			File dir = new File(dirFile);
			if(!dir.exists()){
				dir.mkdirs();
			}
			File file = new File(dirFile+"\\"+fileName);
			if(!file.exists()){
				WritableWorkbook workbook = Workbook.createWorkbook(file);  
				WritableSheet sheet = workbook.createSheet("测试结果",0);
				Label currentTime = new Label(0,0,"操作时间");
		        sheet.addCell(currentTime);
				Label startTime = new Label(1,0,"重复执行时间");
		        sheet.addCell(startTime);
		        Label op = new Label(2,0,"请求");
		        sheet.addCell(op);
		        Label sponNumber = new Label(3,0,"实际处理并发数");
		        sheet.addCell(sponNumber);
		        Label minSponTime = new Label(4,0,"最小响应时间");
		        sheet.addCell(minSponTime);
		        Label maxSponTime = new Label(5,0,"最大响应时间");
		        sheet.addCell(maxSponTime);
		        Label averageSponTime = new Label(6,0,"平均响应时间");
		        sheet.addCell(averageSponTime);
		        Label sponCorrect = new Label(7,0,"准确率");
		        sheet.addCell(sponCorrect);
		        Label QPS = new Label(8,0,"QPS");
		        sheet.addCell(QPS);
		        workbook.write();
		        workbook.close();
			}
			
			fs=new FileInputStream(dirFile+"\\"+fileName); 
	        ps=new POIFSFileSystem(fs);  //使用POI提供的方法得到excel的信息  
	        wb=new HSSFWorkbook(ps);    
	        HSSFSheet sheet=wb.getSheet("测试结果");  //获取到工作表，因为一个excel可能有多个工作表  
	        HSSFRow row=sheet.getRow(0);  //获取第一行（excel中的行默认从0开始，所以这就是为什么，一个excel必须有字段列头），即，字段列头，便于赋值  
//	        System.out.println(sheet.getLastRowNum()+" "+row.getLastCellNum());  //分别得到最后一行的行号，和一条记录的最后一个单元格  
	          
	        out=new FileOutputStream(dirFile+"\\"+fileName); 
	        int col=row.getPhysicalNumberOfCells();//总列数
	        row=sheet.createRow((short)(sheet.getLastRowNum()+1)); //在现有行号后追加数据  
	        row.createCell(0).setCellValue(currentDate);
	        for(int i=1;i<col;i++){
	        	row.createCell(i).setCellValue(list.get(i-1).toString());
	        }
	  
	          
	        out.flush();  
	        wb.write(out);    
	         
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				fs.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static List readExcel(String fileName){
		List<List<String>> list = new ArrayList<List<String>>();
		try {  
            File file = new File(fileName); // 创建文件对象  
            Workbook wb = Workbook.getWorkbook(file); // 从文件流中获取Excel工作区对象（WorkBook）  
            Sheet sheet = wb.getSheet(0); // 从工作区中取得页（Sheet）  
            for (int i = 0; i < sheet.getRows(); i++) { // 循环打印Excel表中的内容  
            	List<String> list2 = new ArrayList<String>();
                for (int j = 0; j < sheet.getColumns(); j++) {  
                    Cell cell = sheet.getCell(j, i);  
                    list2.add(cell.getContents());  
                } 
                list.add(list2);
            }  
            return list;
        } catch (BiffException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } 
		return list;
	}
	
	public static void removeAllExcel(String dirFile,String fileName){
		FileInputStream fs = null;
		HSSFWorkbook wb = null;
		POIFSFileSystem ps = null;
		FileOutputStream out = null;
		try{
			File dir = new File(dirFile);
			if(!dir.exists()){
				dir.mkdirs();
			}
			File file = new File(dirFile+"\\"+fileName);
			if(file.exists()){
				fs=new FileInputStream(dirFile+"\\"+fileName); 
		        ps=new POIFSFileSystem(fs);  //使用POI提供的方法得到excel的信息  
		        wb=new HSSFWorkbook(ps);    
		        HSSFSheet sheet=wb.getSheet("测试结果");  //获取到工作表，因为一个excel可能有多个工作表  
		        out=new FileOutputStream(dirFile+"\\"+fileName); 
		        System.out.println("~~~~~~~~~~~~~~~~~~~~~"+sheet.getLastRowNum());
		        for (int i = 1; i < sheet.getLastRowNum(); i++) {
		        	sheet.removeRow(sheet.getRow(i));
				}
		          
		        out.flush();  
		        wb.write(out); 
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				fs.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		String dirFile = "D:\\StressTest\\result";
		String fileName = "result.xls";
		removeAllExcel(dirFile, fileName);
	}
}
