package com.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class JsonfileUtil {
	
	/**
	 * 
	 * <pre>
	 * CopyRight(c) 2014-2015
	 * 创建人：刘健
	 * 日期：2014年6月4日上午10:39:07
	 * 描述： 读文件，返回字符串
	 *</pre>
	 */
	public static String ReadFile(String path) {
		File file = new File(path);
		BufferedReader reader = null;
		String laststr = "";
		try {
			// System.out.println("以行为单位读取文件内容，一次读一整行：");
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				System.out.println("line " + line + ": " + tempString);
				laststr = laststr + tempString;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {

				}
			}
		}
		return laststr;
	}
	
	/**
	 * 
	 * <pre>
	 * CopyRight(c) 2014-2015
	 * 创建人：刘健
	 * 日期：2014年6月4日上午10:41:35
	 * 描述：把json格式的字符串写到文件 
	 *</pre>
	 */
	public static void writeFile(String filePath, String sets) throws IOException {    
		FileWriter fw = new FileWriter(filePath);    
		PrintWriter out = new PrintWriter(fw);    
		out.write(sets);    
		out.println();    
		fw.close();    
		out.close(); 
	}
	
	
	/**
     * 
     * <pre>
     * 任务：APPCOMMERCIALBUG-286
     * 描述：对特殊字符进行处理 
     * 作者：hnh 
     * 时间：2015年2月1日下午4:00:38
     * @param s
     * @return
     * returnType：String
     * </pre>
     */
    public static String string2Json(String s) {        
        StringBuffer sb = new StringBuffer();        
        for (int i=0; i<s.length(); i++) {  
            char c = s.charAt(i);    
             switch (c){  
             case '\"':        
                 sb.append("\\\"");        
                 break;        
             case '\\':        
                 sb.append("\\\\");        
                 break;        
             case '/':        
                 sb.append("\\/");        
                 break;        
             case '\b':        
                 sb.append("\\b");        
                 break;        
             case '\f':        
                 sb.append("\\f");        
                 break;        
             case '\n':        
                 sb.append("\\n");        
                 break;        
             case '\r':        
                 sb.append("\\r");        
                 break;        
             case '\t':        
                 sb.append("\\t");        
                 break;        
             default:        
                 sb.append(c);     
             }  
        }      
        return sb.toString();     
    }
}
