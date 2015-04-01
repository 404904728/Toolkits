package searchPhoneGsd;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Test {
	 //查询手机号码所属地区
	 public static synchronized String getMobileAddress(String mobile) throws Exception
	  {
	   String address = "";
	   try
	   {
	    mobile = mobile.trim();
	    if (mobile.matches("^(13|15|18|17|14)\\d{9}$") || mobile.matches("^(013|015|018|017|014)\\d{9}$")) //以13，15，18开头，后面九位全为数字
	   {
	     String url = "http://www.ip138.com:8080/search.asp?action=mobile&mobile=" + mobile;
	     Document doc = Jsoup.connect(url).get();
	     while(null == doc || doc.text().length() < 1){
	    	 doc = Jsoup.connect(url).get();
	     }
	     address = doc.select("td.tdc2").eq(1).text();
	    }
	   }
	   catch(Exception e)
	   {
	    address = "未知";
	    System.out.println("手机所属地查询失败====================");
	   }
	   return address;
	  }
	 
	 public static void main(String[] args) throws Exception {
		//System.out.println(getMobileAddress("15820479818"));
		 FileOutputStream os = new FileOutputStream("d:/xiao1.xls");
		 HSSFWorkbook wb = new HSSFWorkbook();
		 HSSFSheet sheet = wb.createSheet("gao");
		 
		 InputStream is = new FileInputStream(new File("d:/xiao.xls"));
		 HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
		 HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
			if (hssfSheet == null) {
				throw new Exception();
			}
			int num = 0;
			for (num = 1;num <= hssfSheet.getLastRowNum(); num++) {
				HSSFRow dataRow = hssfSheet.getRow(num);
				System.out.println(dataRow.getCell(0).getStringCellValue() +" ========= " + dataRow.getCell(1).getStringCellValue());
				dataRow.getCell(2).setCellValue(getMobileAddress(dataRow.getCell(1).getStringCellValue()));
				HSSFRow row = sheet.createRow(num-1);
				HSSFCell cell = row.createCell(0);
				cell.setCellValue(dataRow.getCell(0).getStringCellValue());
				HSSFCell cell1 = row.createCell(1);
				cell1.setCellValue(dataRow.getCell(1).getStringCellValue());
				HSSFCell cell2 = row.createCell(2);
				cell2.setCellValue(getMobileAddress(dataRow.getCell(1).getStringCellValue()));
			}
			wb.write(os);
			os.close();
		    System.out.println("0000000000000000000000000000000000000000000000000000");
	}
}
