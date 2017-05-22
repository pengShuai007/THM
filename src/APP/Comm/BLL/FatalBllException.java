package APP.Comm.BLL;

import APP.Comm.Util.JsonUtil.JsonAlertType;

/**任务：*描述：当前类型的Exception才会输出为Error级别的日志
 *人员：施建龙  *时间：2015年5月26日下午4:14:28 **/
public class FatalBllException extends BaseBllException{

	public FatalBllException(String customMessage, Throwable e,
			JsonAlertType alertType) {
		super(customMessage, e, alertType);
		// TODO Auto-generated constructor stub
	}

	public FatalBllException(String customMessage, Throwable e,
			String errorCode, JsonAlertType alertType) {
		super(customMessage, e, errorCode, alertType);
		// TODO Auto-generated constructor stub
	}

	public FatalBllException(String customMessage, Throwable e, String errorCode) {
		super(customMessage, e, errorCode);
		// TODO Auto-generated constructor stub
	}

	public FatalBllException(String customMessage, Throwable e) {
		super(customMessage, e);
		// TODO Auto-generated constructor stub
	}

	public FatalBllException(Throwable e, JsonAlertType alertType) {
		super(e, alertType);
		// TODO Auto-generated constructor stub
	}

	public FatalBllException(Throwable e) {
		super(e);
		// TODO Auto-generated constructor stub
	}

	public FatalBllException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 任务：KYEEAPPC-2870
	 * 说明：扩展FatalBllException，用来存储端的异常信息，监控使用，业务请不要使用该方法
	 * 作者：冯泽
	 * 时间：2015-8-5 13:37:35
	 * @param alertType
	 * @param errorCode
	 * @param message
	 * @param errorMsgForMonitor
	 * @param originalTerminalJsonMessage
	 */
	public FatalBllException(JsonAlertType alertType,String errorCode,String message,String errorMsgForMonitor,String originalTerminalJsonMessage) {
		super(alertType,errorCode,message,errorMsgForMonitor,originalTerminalJsonMessage);
		// TODO Auto-generated constructor stub
	}
}
