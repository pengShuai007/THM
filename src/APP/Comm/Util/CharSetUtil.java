package APP.Comm.Util;

import java.io.UnsupportedEncodingException;

public class CharSetUtil {

	/**
	 * comments:
	 * 
	 * sjl modify 2013-10-15上午11:21:16
	 */
	  /**
 *  
 * 修改人：  ypf <br/>  
 * 修改时间：2014年10月29日 11:28 <br/>  
 * 修改备注：修改文件编译时报编码错误的提示<br/>
 * 任务号：KYEEAPP-692
 * @创建人 ypf
 * @版本
 */
	public static String transfer(String content,String sourceCharSet,String destCharSet){
		String s1=null;
		String s2=null;
		String theReturn=null;
		if(sourceCharSet.equalsIgnoreCase(destCharSet)){
			return content;
		}
		
		s1 = convertToCharSet(sourceCharSet);
		s2 = convertToCharSet(destCharSet);
		
//		theReturn=ISO2GBK(content);
		try {
			theReturn=new String(content.getBytes(s1), s2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			HLogger.error(e);
			theReturn=content;
		}
		return theReturn;
	}


	private static String convertToCharSet(String sourceCharSet) {
		String s1="GBK";
		if(sourceCharSet.equalsIgnoreCase("ASCII")){
			s1="iso-8859-1";			
		}else	if(sourceCharSet.equalsIgnoreCase("GBK")){
			s1="GBK";
		}
		return s1;
	}
	
	
	/**
	 * comments:
	 * 
	 * sjl modify 2013-10-15上午11:17:42
	 */
	public static String ISO2GBK(String source) {
		String theReturn = null;
//		BufferedReader br = null;
		// new InputstreamReader()
		if (source == null) {
		} else {
			try {
				theReturn = new String(source.getBytes("ISO-8859-1"), "GBK");
			} catch (Exception e) {
				HLogger.error(e);
			}
		}
		return theReturn;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
