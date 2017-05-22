package APP.Comm;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class SystemResource {
	public static final String LOGIN_BACKGROUND_IMAGE = "LOGIN_BACKGROUND_IMAGE";

	public static final String MAIN_BACKGROUND_IMAGE = "MAIN_BACKGROUND_IMAGE";

	/**
	 * 背景图默认文件名称 施建龙 2013年10月7日17:44:36
	 */
	public static final String DEFAULT_LOGIN_BACKGROUND_IMAGE = "Resources/Images/Default/DefaultBackground.jpg";

	public static final String DEFAULT_LAYOUT_TOP_BACKGROUND_IMAGE = "Resources/Images/Default/DefaultBackground.jpg";

	public static final String DEFAULT_SUB_MAIN_BACKGROUND_IMAGE = "Resources/Images/Default/DefaultBackground.jpg";

	public static final String DEFAULT_LOGO = "Resources/Images/main/his_logo.png";
	public static final String DEFAULT_CONTENT = "欢迎使用趣医APP，技术支持电话：400-080-1010 ";
	public static final String DEFAULT_COPYRIGHT = "Copyright2014  趣医科技";

	public static final String DEFAULT_UPLOAD_IMAGE_PATH = "Resources/Images/Upload/";
	public static final String DEFAULT_IMAGE_PATH = "Resources/Images/Default/";

	public static final String SYS_MAIN_COMMENTS = "SYS_MAIN_COMMENTS";
	public static final String SYS_COPYRIGHT_COMMENTS = "SYS_COPYRIGHT_COMMENTS";
	public static final String SYS_MAIN_LOGO_PATH = "SYS_MAIN_LOGO_PATH";
	public static final String SYS_LAYOUT_STYLE = "SYS_LAYOUT_STYLE";
	public static final String DEFAULT_STYLE_CONFIG = "DEFAULT";

	public static final String SYS_MAIN_UNIT = "";
	public static final String SYS_MAIN_UNIT_1 = "";
	public static final String SYS_MAIN_UNIT_2 = "";
	public static final String SYS_MAIN_UNIT_3 = "";
	public static final String SYS_MAIN_UNIT_4 = "";
	/**
	 * main页面的背景图片参数 施建龙 2013年10月7日17:37:18
	 */
	public static final String LAYOUT_TOP_BACKGROUND_IMAGE = "LAYOUT_TOP_BACKGROUND_IMAGE";
	public static final String SUB_MAIN_BACKGROUND_IMAGE = "SUB_MAIN_BACKGROUND_IMAGE";

	public static String AppPath = "";

	private static String contextPath = "/";
	
	private static String appRealPath="";
	
	private static String nowDayTime = "00:00:00";
	/**
	 * 获取应用在文件系统中的路径
	 * 
	 * @return
	 */
	public static String GetAppPath() {
		return AppPath;
	}

	public static String getContextPath() {
		return contextPath;
	}

	public static String getNowDayTime() {
		Date nowdate = new Date();
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		nowDayTime = dateFormat.format(nowdate);
		nowDayTime = nowDayTime.substring(0,nowDayTime.length() - 1);
		return nowDayTime;
	}
	
	public static void setContextPath(String contextPath) {
		SystemResource.contextPath = contextPath;
	}
	
	public static String getAppRealPath() {
		return appRealPath;
	}
	public static void setAppRealPath(String appRealPath) {
		SystemResource.appRealPath = appRealPath;
	}
}