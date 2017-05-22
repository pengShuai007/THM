package APP.Login.login;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import APP.Login.util.Encryption;
import APP.Login.util.MD5;
import APP.Login.util.RequestUrl;
import APP.Login.util.SystemUtil;

public class Login {
	private static final Logger LOG = LoggerFactory.getLogger(Login.class);
	public static final String URL1 = "http://"+SystemUtil.URLCLOUD +"/APP/user/action/LoginAction.jspx?";
	public static final String URL2 = "APPOINT_SOURCE=0&CHANNEL_ID=&IMEI_ID=&LOGIN_FLAG=0&PHONEOPERATINGSYS=0&PHONETYPE=&PHONEVERSIONNUM=&PUBLIC_SERVICE_TYPE=0&isAutoPwdLogin=false&isLogin=false&loc=c&op=login&opVersion=2.1.20&operateUserSource=0&userSource=0";

	public static JSONObject login(String phone_number,String password) throws Exception {
		
		String MD5_password = Encryption.getEncryption(password);
		String url3 = URL2+"&PASSWORD="+MD5_password+"&USER_CODE="+phone_number;
		String md5_temp = MD5.string2MD5(url3);
		String QY_CHECK_SUFFIX = MD5.string2MD5(md5_temp+"@@"+SystemUtil.token);
		String url = URL1+url3+"&QY_CHECK_SUFFIX="+QY_CHECK_SUFFIX;
		JSONObject json = RequestUrl.excuteRequestUrl(url);
		LOG.info("响应时间："+json.getString("RESPOND_TIME")+"======>>登录信息===>>参数===>>[phone_number:" + phone_number
				+ "]====" + "返回结果：" + json.getString("success") + ";message：" + json.getString("message"));
		return json;
	}
	
	public static void main(String[] args) throws Exception {
		String phone_number = "18292835973";
		String password = "123456";
		Long token = SystemUtil.token;
		login(phone_number, password);

//		String url1 = "http://114.215.140.171:8888/APP/user/action/LoginAction.jspx?";
//		String url2 = "APPOINT_SOURCE=0&APP_UUID=0ed0c1eb-342c-41ee-bac9-d6fcc3c8d404&CHANNEL_ID="
//				+ "&HOSPITAL_ID=1501&IMEI_ID=&INPUT_CARD_NO=&PHONEOPERATINGSYS=0"
//				+ "&PHONETYPE=&PHONEVERSIONNUM=&PUBLIC_SERVICE_TYPE=0&USER_ID=2165730"
//				+ "&USER_VS_ID=921453&hospitalID=1501&isLogin=true&loc=c&op=getTerminalReport"
//				+ "&opVersion=2.1.10&operateCurrent_UserId=2165730&operateUserSource=0&SEARCH_FLAG=1";
//
//		String QY_CHECK_SUFFIX = MD5.string2MD5(MD5.string2MD5(url2)+"@@"+SystemUtil.token);
	}
}
