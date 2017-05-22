package APP.Login.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class URL_Code {
	//汉字转化为乱码
	public static String encode(String hanzi){
		String urlString = null;
		try {
			urlString = URLEncoder.encode(hanzi, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return urlString;
	}
	
	//乱码转化为汉字
	public static String decode(String luanma){
		String urlString = null;
		try {
			urlString = URLDecoder.decode(luanma, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return urlString;
	}
	
	public static void main(String[] args) {
		String luanma = "0308153538105%2F%E7%99%BD%E5%A4%A9+10:00";
		String luanma2 = "%E8%B6%A3%E5%8C%BB%E7%BD%91";
		String hanzi = "趣医院";
//		System.out.println(decode(luanma));
		System.out.println(decode(luanma2));
		System.out.println(encode(hanzi));
	}
}
