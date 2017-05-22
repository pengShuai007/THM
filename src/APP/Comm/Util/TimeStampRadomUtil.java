package APP.Comm.Util;

import java.text.SimpleDateFormat;

public class TimeStampRadomUtil {

	public static String GetARadomValue(){
		String result = "";

		String timeStamp = "";
		int random = 0;
		random = (int) (Math.random() * 10000); // 获取0到99的随机数

		Long currentDate = System.currentTimeMillis();

		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		timeStamp = formatter.format(currentDate);// 获取带毫秒的时间戳

		result = timeStamp + random;

		return result;
	}
	
	/*public static void main(String[] args) {
		for(int i = 0;i<10000;i++)
		System.out.println(GetARadomValue());
		//System.out.println(GetARadomValue().length());
	}*/
}
