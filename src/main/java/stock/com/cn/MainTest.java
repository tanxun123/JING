package stock.com.cn;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class MainTest {
	public static DecimalFormat df = new DecimalFormat("######0.00");
	static {
		df.setRoundingMode(RoundingMode.HALF_UP);
	}

	public static void main(String[] args) throws IOException {
		//	有问题2.5  2.8  5.20 
		//  单个文件
		//			List<List<List<Object>>> dataList=getDataFromExcel("D:\\wencai/20210104 涨幅;20210104开盘涨幅_....xlsx");
		//			System.out.println(getSqlFromData(dataList,"20210104"));

//		dealFolder("D:\\wencai/test","jingjia");

//		dealFolder("D:\\wencai/test","lanban");

		dealFolder("D:\\wencai/test","shouban");

		//	获取交易日历
//			printWenCaiByDate("file/SH_JYRL_2021.txt");
		//找出缺少数据的天数
//		findLackOfExcel("file/SH_JYRL_2020.txt","D:\\wencai/test");

		
		
	}



	private static void dealFolder(String filepath,String type)
	{
		File fileF = new File(filepath);
		File[] files = fileF.listFiles();
		if (files == null)
		{
			return;
		}
		try
		{
			for (File file : files) {
				if (file.isDirectory())
				{
					//					dealFolder(file.getCanonicalPath());
					continue;
				}
				else  {
					switch (type) {
					case "jingjia":
						System.out.println("-- file.getAbsolutePath():"+file.getAbsolutePath());
						List<List<List<Object>>> dataList=getDataFromExcel(file.getAbsolutePath());
						String date=file.getName().substring(0,8);
						System.out.println(getSqlFromData(dataList,date));
						ReadFile.WriteToFile(getSqlFromData(dataList,date), "D:/wencai/result.txt");
						break;
					case "lanban":
						System.out.println("-- file.getAbsolutePath():"+file.getAbsolutePath());
						dataList=getDataFromExcel(file.getAbsolutePath());
						date=file.getName().substring(0,8);
						System.out.println(getLanBanSqlFromData(dataList,date));
						ReadFile.WriteToFile(getLanBanSqlFromData(dataList,date), "D:/wencai/result.txt");
						break;
					case "shouban":
						System.out.println("-- file.getAbsolutePath():"+file.getAbsolutePath());
						dataList=getDataFromExcel(file.getAbsolutePath());
						date=file.getName().substring(0,8);
						System.out.println(getShouBanSqlFromData(dataList,date));
						ReadFile.WriteToFile(getShouBanSqlFromData(dataList,date), "D:/wencai/result.txt");
						break;
					default:
						break;
					}
					
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}


	public static String getSqlFromData(List<List<List<Object>>> list,String date){
		List<List<Object>> list1=list.get(0);
		boolean hasData=false;
		StringBuffer sb=new StringBuffer("insert  into jingjia(date,code,name,nnextkpzf,nextkpzf,kpzf,nnextzf,nextzf,zf,kpcje,kphsl,ltsz,hslpm,ssts,month,year) VALUES\r\n ");
		for(int i=2;i<list1.size()-1;i++){
			//			000155.SZ	川能动力	9.968186638	1.516919487	3.13253012	1.06044539	10.03500583	3.25301205	22277400	0.20492126	18834100000
			try {
				String code=(String) list1.get(i).get(0);
				String name=(String) list1.get(i).get(1);
				double nnextkpzf=Double.valueOf(df.format(Double.valueOf((String) list1.get(i).get(2))));
				double nextkpzf=Double.valueOf(df.format(Double.valueOf((String) list1.get(i).get(3))));
				double kpzf=Double.valueOf(df.format(Double.valueOf((String) list1.get(i).get(4))));
				double nnextzf=Double.valueOf(df.format(Double.valueOf((String) list1.get(i).get(5))));
				double nextzf=Double.valueOf(df.format(Double.valueOf((String) list1.get(i).get(6))));
				double zf=Double.valueOf(df.format(Double.valueOf((String) list1.get(i).get(7))));
				double cje=Double.valueOf(df.format(Double.valueOf((String) list1.get(i).get(8))/10000));
				double hsl=Double.valueOf(df.format(Double.valueOf((String) list1.get(i).get(9))));
				double ltsz=Double.valueOf(df.format(Double.valueOf((String) list1.get(i).get(10))/10000/10000));
				String ssts=(String) list1.get(i).get(11);
				String year=date.substring(0, 4);
				String month=date.substring(0, 6);

				sb.append("("+date +",");
				sb.append("'"+code+"',");
				sb.append("'"+name+"',");
				sb.append(nnextkpzf+",");
				sb.append(nextkpzf+",");
				sb.append(kpzf+",");
				sb.append(nnextzf+",");
				sb.append(nextzf+",");
				sb.append(zf+",");
				sb.append(cje+",");
				sb.append(hsl+",");
				sb.append(ltsz+",");
				sb.append(i-1 +",");
				sb.append(ssts+",");
				sb.append(month+",");
				sb.append(year);
				hasData=true;
			} catch (Exception e) {
//				System.out.println("test:"+list1.get(i).get(11));
				e.printStackTrace();
				
				continue;
			}
			sb.append(")\r\n,");
		}
		sb.setLength(sb.length()-1);
		sb.append(";");
		if(!hasData){
			//排除所有的涨幅都是--
			return "";
		}
		return sb.toString();
	}
	
	
	public static String getLanBanSqlFromData(List<List<List<Object>>> list,String date){
		List<List<Object>> list1=list.get(0);
		boolean hasData=false;
		StringBuffer sb=new StringBuffer("insert  into lanban(date,code,name,zdzf,jzf,d3,d2,d1,ztlb,scztsj,zzztsj,fdje,month,year) VALUES\r\n ");
		for(int i=2;i<list1.size()-1;i++){
			try {
				String code=(String) list1.get(i).get(0);
				String name=(String) list1.get(i).get(1);
				double zdzf=Double.valueOf(df.format(Double.valueOf((String) list1.get(i).get(2))));
				double jzf=Double.valueOf(df.format(Double.valueOf((String) list1.get(i).get(3))));
				jzf=Double.valueOf(df.format(jzf-100));
				double d3=Double.valueOf(df.format(Double.valueOf((String) list1.get(i).get(4))));
				double d2=Double.valueOf(df.format(Double.valueOf((String) list1.get(i).get(5))));
				double d1=Double.valueOf(df.format(Double.valueOf((String) list1.get(i).get(6))));
				String ztlb=(String) list1.get(i).get(7);
				String scztsj=(String) list1.get(i).get(8);
				String zzztsj=(String) list1.get(i).get(9);
				String fdje="0";
				if(!((String) list1.get(i).get(10)).equals("--")){
				fdje=df.format(Double.valueOf((String) list1.get(i).get(10))/10000);
				}
				String year=date.substring(0, 4);
				String month=date.substring(0, 6);

				sb.append("("+date +",");
				sb.append("'"+code+"',");
				sb.append("'"+name+"',");
				sb.append(zdzf+",");
				sb.append(jzf+",");
				sb.append(d3+",");
				sb.append(d2+",");
				sb.append(d1+",");
				sb.append("'"+ztlb+"',");
				sb.append("'"+scztsj+"',");
				sb.append("'"+zzztsj+"',");
				sb.append(fdje+",");
				sb.append(month+",");
				sb.append(year);
				hasData=true;
			} catch (Exception e) {
//				System.out.println("test:"+list1.get(i).get(11));
				e.printStackTrace();
				
				continue;
			}
			sb.append(")\r\n,");
		}
		sb.setLength(sb.length()-1);
		sb.append(";");
		if(!hasData){
			//排除所有的涨幅都是--
			return "";
		}
		return sb.toString();
	}
	
	public static String getShouBanSqlFromData(List<List<List<Object>>> list,String date){
		List<List<Object>> list1=list.get(0);
		boolean hasData=false;
		StringBuffer sb=new StringBuffer("insert  into shouban(date,code,name,d2zf,d1zf,lb,dzf,hsl,ltsz,ztlb,scztsj,ssts,month,year) VALUES\r\n ");
		for(int i=2;i<list1.size()-1;i++){
			try {
				String code=(String) list1.get(i).get(0);
				String name=(String) list1.get(i).get(1);
				if("--".equals((String) list1.get(i).get(4))||"--".equals((String) list1.get(i).get(2))||"--".equals((String) list1.get(i).get(3))){
					continue;
				}
				double d2zf=Double.valueOf(df.format(Double.valueOf((String) list1.get(i).get(2))));
				double d1zf=Double.valueOf(df.format(Double.valueOf((String) list1.get(i).get(3))));
				double lb=Double.valueOf(df.format(Double.valueOf((String) list1.get(i).get(4))));
				double dzf=Double.valueOf(df.format(Double.valueOf((String) list1.get(i).get(5))*100));
				dzf=dzf-100;
				dzf=Double.valueOf(df.format(dzf));
				double hsl=Double.valueOf(df.format(Double.valueOf((String) list1.get(i).get(6))));
				double ltsz=Double.valueOf(df.format(Double.valueOf((String) list1.get(i).get(7))/10000/10000));
				String ztlb=(String) list1.get(i).get(8);
				String scztsj=(String) list1.get(i).get(9);
				scztsj=scztsj.trim();
				double ssts=Double.valueOf(df.format(Double.valueOf((String) list1.get(i).get(10))));
				String year=date.substring(0, 4);
				String month=date.substring(0, 6);

				sb.append("("+date +",");
				sb.append("'"+code+"',");
				sb.append("'"+name+"',");
				sb.append(d2zf+",");
				sb.append(d1zf+",");
				sb.append(lb+",");
				sb.append(dzf+",");
				sb.append(hsl+",");
				sb.append(ltsz+",");
				sb.append("'"+ztlb+"',");
				sb.append("'"+scztsj+"',");
				sb.append((int)(ssts)+",");
				sb.append(month+",");
				sb.append(year);
				hasData=true;
			} catch (Exception e) {
//				System.out.println("test:"+list1.get(i).get(11));
				e.printStackTrace();
				
				continue;
			}
			sb.append(")\r\n,");
		}
		sb.setLength(sb.length()-1);
		sb.append(";");
		if(!hasData){
			//排除所有的涨幅都是--
			return "";
		}
		return sb.toString();
	}


	public static void findLackOfExcel(String tradeDateFilePath,String excelFoldPath) throws IOException{
		//写入交易日
		String dates=ReadFile.readFile01(tradeDateFilePath);
		String arrs[]=dates.split("\r\n");

		List<Integer> listDate=new ArrayList<Integer>();
		for(String a:arrs){
			String ss[]=a.split("\\|");
			if(ss[1].equals("1")){
				if(Integer.valueOf(ss[0])<20210601){
					listDate.add(Integer.valueOf(ss[0]));
				}
			}
		}


		File fileF = new File(excelFoldPath);
		File[] files = fileF.listFiles();
		if (files == null)
		{
			return;
		}
		try
		{
			for (File file : files) {
				if (file.isDirectory())
				{
					continue;
				}
				else  {
					String date=file.getName().substring(0,8);
					listDate.remove(Integer.valueOf(date));
				}
			}
			System.out.println(listDate);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void printWenCaiByDate(String path) throws IOException{
//		String selectSql="date1涨幅;date1开盘涨幅;date1非一字板;date1开盘成交额大于1千万前20;剔除st股;date1总市值<500亿;date1上市天数;date1开盘换手率前20;非创业板;非科创板;非st;date2开盘涨幅;date2涨幅;";
		//竞价选股sql
//		String selectSql="date1涨幅;date1开盘涨幅;date1非一字板;date1开盘成交额大于1千万前20;剔除st股;date1总市值<500亿;date1上市天数;date1开盘换手率前20;非创业板;非科创板;非st;date2开盘涨幅;date2涨幅;date3开盘涨幅;date3涨幅";
		//兰板选股sql
		String selectSql="date1涨停非连板;剔除st;date1涨停原因;date1涨幅;date2涨幅;date3涨幅;date3最高涨幅;(date3均价/date2收盘价)*100;date2开盘涨幅; date2首次涨停时间不等于最终涨停时间;date2开盘非涨停;date2成交量/date1成交量；date1收盘价/date2往前20天最低价";
		//首板选股sql
//		String selectSql="date1非涨停;date2涨停及时间;date2开盘非涨停;date2成交量/date1成交量;date1收盘价，date1收盘价/date2往前20日最低价;date2换手率;date2涨幅;date3涨跌幅排序;流通市值;非st;非新股;date2涨停原因";
		
		String dates=ReadFile.readFile01(path);
		String arrs[]=dates.split("\r\n");

		List<Integer> listDate=new ArrayList<Integer>();
		for(String a:arrs){
			String ss[]=a.split("\\|");
			if(ss[1].equals("1")){
				listDate.add(Integer.valueOf(ss[0]));
			}
		}

		for(int i=0;i<listDate.size()-2;i++){
//			if(listDate.get(i)>20210101&&listDate.get(i)<20210601){
				String sql=selectSql.replaceAll("date1", listDate.get(i)+"");
				sql= sql.replaceAll("date2", listDate.get(i+1)+"");
				sql= sql.replaceAll("date3", listDate.get(i+2)+"");
				System.out.println(sql);
//			}
		}
	}


	public static List<List<List<Object>>> getDataFromExcel(String filePath){

		Workbook wb = null; 
		Sheet sheet = null;
		Row row = null;

		System.out.println("-- filePath========"+filePath);
		wb = ReadExcel.readExcel(filePath);
		if (wb != null) {
			try {
				List<List<List<Object>>> list = new ArrayList<>();

				//            System.err.println("页签数量：" + wb.getNumberOfSheets());
				// 循环页签
				for (int sheetNum = 0; sheetNum < wb.getNumberOfSheets(); sheetNum++) {
					// 指定页签的值
					sheet = wb.getSheetAt(sheetNum);
					// 定义存放一个页签中所有数据的List
					List<List<Object>> sheetList = new ArrayList<>();

					System.err.println("-- 行总数：" + sheet.getLastRowNum());
					// 循环行
					for (int rowNum = 0; rowNum <= sheet.getLastRowNum(); rowNum++) {
						// 指定行的值
						row = sheet.getRow(rowNum);
						//System.out.println("row================"+row);
						// 定义存放一行数据的List
						List<Object> rowList = new ArrayList<>();
						// 循环列
						//                    System.err.println("列总数：" + row.getLastCellNum());
						for (int cellNum = 0; cellNum < row.getLastCellNum(); cellNum++) {
							Cell cell = sheet.getRow(rowNum).getCell(cellNum);
							rowList.add(ReadExcel.getStringCellValue(cell));
						}
						sheetList.add(rowList);
					}
					list.add(sheetList);
				}
				//            System.err.println(list.toString());
				return list;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;

	}

}
