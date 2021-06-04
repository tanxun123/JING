package stock.com.cn;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.stream.Stream;

public class ReadFile {

	/**
    * 一行一行读取文件，适合字符读取，若读取中文字符时会出现乱码
    * 
    * 流的关闭顺序：先打开的后关，后打开的先关，
    *       否则有可能出现java.io.IOException: Stream closed异常
    * 
    * @throws IOException 
    */
   public static String readFile01(String filePath) throws IOException {
	   StringBuffer sb=new StringBuffer();
       FileReader fr=new FileReader(filePath);
       BufferedReader br=new BufferedReader(fr);
       String line="";
       while ((line=br.readLine())!=null) {
    	   sb.append(line).append("\r\n");
       }
       br.close();
       fr.close();
       return sb.toString();
   }

   /**
    * 一行一行读取文件，解决读取中文字符时出现乱码
    * 
    * 流的关闭顺序：先打开的后关，后打开的先关，
    *       否则有可能出现java.io.IOException: Stream closed异常
    * 
    * @throws IOException 
    */
   public static String readFile02(String filePath) throws IOException {
	   StringBuffer sb=new StringBuffer();
       FileInputStream fis=new FileInputStream(filePath);
       InputStreamReader isr=new InputStreamReader(fis, "UTF-8");
       BufferedReader br = new BufferedReader(isr);
       //简写如下
       String line="";
       String[] arrs=null;
       while ((line=br.readLine())!=null) {
    	   sb.append(line).append("\r\n");
       }
       br.close();
       isr.close();
       fis.close();
       return sb.toString();
   }


	public static void WriteToFile(String str, String filePath)
	{
		try
		{
			File file = new File(filePath);
			if (!file.exists())
			{
				if (!file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
				}
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(str);
			bw.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
