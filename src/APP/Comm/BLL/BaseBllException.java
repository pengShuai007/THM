package APP.Comm.BLL;

import java.lang.reflect.InvocationTargetException;

import APP.Comm.Util.DotNetToJavaStringHelper;
import APP.Comm.Util.JsonUtil.JsonAlertType;

/**
 * functions：BLL 自定义业务异常处理类 作者：左仁帅 时间：2013年1月7日 15:28:13
 */
public class BaseBllException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Throwable e = null;
	// 定制错误提示信息
	String customMessage = null;
	/**
	 * 任务： 描述：添加errorCode属性，用于规范异常处理 默认值为0000500，表示为系统繁忙 人员：施建龙
	 * 时间：2014年12月17日下午1:15:15
	 **/
	private String errorCode = "0000500";
	String errorSql = null;
	String errorMsgForMonitor = null;
	private Boolean netException = null;
	//Edit start fengze KYEEAPPC-6090 端原始json信息 2016年5月6日 10:32:49
	private String originalTerminalJsonMessage = null;
	//Edit end fengze KYEEAPPC-6090 端原始json信息 2016年5月6日 10:32:49
	
	/*
	 * 异常输出信息提示方式，默认弹出
	 */
	private JsonAlertType alertType = JsonAlertType.ALERT;
	
	public BaseBllException(Throwable e) {
		this.e = e;
	}

	/**
	 * 构造方法
	 * 
	 * @param message
	 *            业务异常消息
	 */
	public BaseBllException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param innerException
	 */
	public BaseBllException(String message, RuntimeException innerException) {
		super(message, innerException);
	}

	/**
	 * 任务： 描述：异常代码、消息、原始Exception，可通过构造方法统一处理 人员：施建龙 时间：2015年2月11日下午12:25:52
	 **/
	public BaseBllException(String customMessage, Throwable e, String errorCode) {
		this.setCustomMessage(customMessage);
		this.e = e;
		this.errorCode = errorCode;
	}
	/**
	 * 任务： 描述：使用构造完成定制信息和内嵌异常的处理 人员：施建龙 时间：2015年2月2日上午11:45:25
	 **/
	/**任务：*描述：早期未定义errorCode时的定制信息版本，保留原始的e信息，同时提供个性化定制的异常消息
	 *人员：施建龙  *时间：2015年5月26日下午4:12:35 **/
	public BaseBllException(String customMessage, Throwable e) {
		this.setCustomMessage(customMessage);
		this.e = e;
	}
	/**任务：*描述：异常处理类型，后期添加，兼容上一个构造方法
	 *人员：施建龙  *时间：2015年5月26日下午4:07:02 **/
	public BaseBllException(Throwable e, JsonAlertType alertType) {
		this.e = e;
		this.alertType = alertType;
	}
	
	/**
	 * 任务： 描述：使用构造完成定制信息和内嵌异常的处理 人员：houhy 时间：2015年4月21日 14:09:35
	 **/
	public BaseBllException(String customMessage, Throwable e,
			JsonAlertType alertType) {
		this.setCustomMessage(customMessage);
		this.e = e;
		this.alertType = alertType;
	}
	/**
	 * 任务： 描述：异常代码、消息、原始Exception，可通过构造方法统一处理 人员：houhy 时间：2015年4月21日 14:08:45
	 * 
	 * @param customMessage
	 * @param e
	 * @param errorCode
	 * @param alertType
	 */
	public BaseBllException(String customMessage, Throwable e,
			String errorCode, JsonAlertType alertType) {
		this.setCustomMessage(customMessage);
		this.e = e;
		this.errorCode = errorCode;
		this.alertType = alertType;
	}
	/**
	 * 任务：KYEEAPPC-2870
	 * 说明：扩展BaseBllException，监控端异常使用，业务请不要使用该构造方法
	 * 作者：冯泽
	 * 时间：2015-8-5 13:40:14
	 * @param alertType
	 * @param errorCode
	 * @param message
	 * @param errorMsgForMonitor
	 * @param alertType
	 */
	public BaseBllException(JsonAlertType alertType,String errorCode,String message,String errorMsgForMonitor,String originalTerminalJsonMessage) {
		super(message);
		this.errorMsgForMonitor = errorMsgForMonitor;
		this.alertType = alertType;
		this.errorCode = errorCode;
		this.originalTerminalJsonMessage = originalTerminalJsonMessage;
	}
	public Throwable getE() {
		return e;
	}

	public void setE(Exception e) {
		this.e = e;
	}

	/**
	 * comments:
	 * 
	 * sjl modify 2013-10-16下午7:25:38
	 */
	@Override
	public String getMessage() {
		String message=null;
		
		if(customMessage!=null){
			message=customMessage;
		}else{
			if(!(e == null)){
				if(e instanceof InvocationTargetException){
//					return (((InvocationTargetException)e).getCause().getMessage());
					/**
					 *任务：
					 *描述：1、从getCause修改为getTargetException；2、修改为toString方法获取异常信息
					 *人员：施建龙
					 *时间：2014年8月14日下午4:16:02
					 **/
					return (((InvocationTargetException)e).getTargetException().toString());
				}else{
					return e.toString();
				}
			}else{
				return super.getMessage();
			}
		}
		/**
		 * comments:
		 * 
		 * sjl modify 2014年1月27日下午1:13:10
		 */
		if(message!=null){
//			 || message.length()==0
//			message=this.toString();
//			message="";
			message=message.replace("APP.Comm.BLL.BaseBllException:","");
		}
		return message;
	}

	/**
	 * comments:
	 * 
	 * sjl modify 2013-10-16下午7:25:45
	 */
	@Override
	public String toString() {
		if (!(e == null)) {
			if (e instanceof InvocationTargetException) {
				return (((InvocationTargetException) e).getCause().toString());
			} else {
				return e.toString();
			}
		} else {
			return super.toString();
		}
	}

	public static Throwable getFinalException(Throwable e) {
		if (e instanceof BaseBllException) {
			if (((BaseBllException) e).getE() != null) {
				if (((BaseBllException) e).getE() instanceof InvocationTargetException) {
					if (((InvocationTargetException) ((BaseBllException) e)
							.getE()).getCause() instanceof BaseBllException) {
						return getFinalException((BaseBllException) ((InvocationTargetException) ((BaseBllException) e)
								.getE()).getCause());
					} else {
						return ((InvocationTargetException) ((BaseBllException) e)
								.getE()).getCause();
					}
				} else {
					return getFinalException(((BaseBllException) e).getE());
				}
			}
			return e;
		} else {
			return e;
		}
	}

	public static Throwable getInvocationTargetException(Throwable e) {
		if (e instanceof BaseBllException) {
			if (((BaseBllException) e).getE() != null) {
				if (((BaseBllException) e).getE() instanceof InvocationTargetException) {
					return ((BaseBllException) e).getE();
				} else {
					return getFinalException(((BaseBllException) e).getE());
				}
			}
			return null;
		} else {
			return null;
		}
	}

	public String getCustomMessage() {
		return customMessage;
	}

	public void setCustomMessage(String customMessage) {
		this.customMessage = customMessage;
	}

	public boolean isNetException()
	{
		boolean  isNetException = false;
		
		if (netException != null) {
			isNetException = netException;
		} else {
			if (!(e == null)) {
				if (e instanceof InvocationTargetException) {
					Throwable temp=((InvocationTargetException) e)
							.getTargetException();
					if(temp instanceof BaseBllException){
						return ((BaseBllException)temp).isNetException();
					}else{
						return false;
					}
				} else {
					if(e instanceof BaseBllException){
						return ((BaseBllException)e).isNetException();
					}else{
						if(netException != null){
							isNetException = netException;
						}
					}
				}
			} else {
				if(netException != null){
					isNetException = netException;
				}
			}
		}
		return isNetException;
	}

	/**
	 * 
	* <pre>
	* 任务:KYEEAPPC-2870
	* 描述:获取错误SQL
	* 作者:冯泽
	* 日期:2015年7月31日 下午2:18:16
	* @return
	* </pre>
	 */
	public String getErrorSql()
	{
		String strErrorSql = null;
		
		if (errorSql != null) {
			strErrorSql = errorSql;
		} else {
			if (!(e == null)) {
				if (e instanceof InvocationTargetException) {
					// return
					// (((InvocationTargetException)e).getCause().getMessage());
					/**
					 * 任务： 描述：1、从getCause修改为getTargetException；2、
					 * 修改为toString方法获取异常信息 人员：施建龙 时间：2014年8月14日下午4:16:02
					 **/
					// return
					// (((InvocationTargetException)e).getTargetException().toString());
					/**
					 * 任务： 描述：修改为getMessage(); 人员：施建龙 时间：2015年2月11日上午10:55:33
					 **/
					/**任务：*描述：若((InvocationTargetException) e)
							.getTargetException()不为BaseBllException,则返回""系统繁忙！""
					 *人员：施建龙  *时间：2015年5月17日下午8:12:53 **/
					Throwable temp=((InvocationTargetException) e)
							.getTargetException();
					if(temp instanceof BaseBllException){
						return ((BaseBllException)temp).getErrorSql();
					}else{
						return "";
					}
				} else {
					/**任务：*描述：若内嵌的Exception为BaseBllException，则进行递归处理
					 * 若不是BaseBllException，则直接返回"系统繁忙"
					 *人员：施建龙  *时间：2015年5月17日下午3:52:39 **/
					if(e instanceof BaseBllException){
					// return e.toString();
						return ((BaseBllException)e).getErrorSql();
					}else{
						return "";
					}
				}
			} else {
				return "";
			}
		}
		/**
		 * comments:
		 * 
		 * sjl modify 2014年1月27日下午1:13:10
		 */
		if (strErrorSql != null) {
			// || message.length()==0
			// message=this.toString();
			// message="";
			strErrorSql = strErrorSql.replace("APP.Comm.BLL.BaseBllException:", "");
		}
		return strErrorSql;
	}
	/**
	 * 
	* <pre>
	* 任务:KYEEAPPC-2870
	* 描述:获取端异常信息
	* 作者:冯泽
	* 日期:2015年8月5日 下午1:48:32
	* @return
	* </pre>
	 */
	public String getErrorMsgForMonitor()
	{
		String strErrorMsgForMonitor = null;
		
		if (errorMsgForMonitor != null) {
			strErrorMsgForMonitor = errorMsgForMonitor;
		} else {
			if (!(e == null)) {
				if (e instanceof InvocationTargetException) {
					// return
					// (((InvocationTargetException)e).getCause().getMessage());
					/**
					 * 任务： 描述：1、从getCause修改为getTargetException；2、
					 * 修改为toString方法获取异常信息 人员：施建龙 时间：2014年8月14日下午4:16:02
					 **/
					// return
					// (((InvocationTargetException)e).getTargetException().toString());
					/**
					 * 任务： 描述：修改为getMessage(); 人员：施建龙 时间：2015年2月11日上午10:55:33
					 **/
					/**任务：*描述：若((InvocationTargetException) e)
							.getTargetException()不为BaseBllException,则返回""系统繁忙！""
					 *人员：施建龙  *时间：2015年5月17日下午8:12:53 **/
					Throwable temp=((InvocationTargetException) e)
							.getTargetException();
					if(temp instanceof BaseBllException){
						return ((BaseBllException)temp).getErrorMsgForMonitor();
					}else{
						return "";
					}
				} else {
					/**任务：*描述：若内嵌的Exception为BaseBllException，则进行递归处理
					 * 若不是BaseBllException，则直接返回"系统繁忙"
					 *人员：施建龙  *时间：2015年5月17日下午3:52:39 **/
					if(e instanceof BaseBllException){
					// return e.toString();
						return ((BaseBllException)e).getErrorMsgForMonitor();
					}else{
						return "";
					}
				}
			} else {
				return "";
			}
		}
		/**
		 * comments:
		 * 
		 * sjl modify 2014年1月27日下午1:13:10
		 */
		if (strErrorMsgForMonitor != null) {
			// || message.length()==0
			// message=this.toString();
			// message="";
			strErrorMsgForMonitor = strErrorMsgForMonitor.replace("APP.Comm.BLL.BaseBllException:", "");
		}
		return strErrorMsgForMonitor;
	}
	/**任务：*描述：此方法提供输出至前端app的显示日志，不再前台用户层面显示系统级错误信息
	 * 最里层BaseBllException的customMessage优先
	 *人员：施建龙  *时间：2015年5月17日下午3:50:41 **/
	public String getViewMessage() {
		String message = null;
		
		/**任务：*描述：输出值前端的提示信息，不能出现系统级异常，因此需要先判断是有有e对象，然后
		 * e对象是否为BaseBllException类型
		 *人员：施建龙  *时间：2015年7月31日下午1:57:31 **/
			if (!(e == null)) {
				if (e instanceof InvocationTargetException) {
					// return
					// (((InvocationTargetException)e).getCause().getMessage());
					/**
					 * 任务： 描述：1、从getCause修改为getTargetException；2、
					 * 修改为toString方法获取异常信息 人员：施建龙 时间：2014年8月14日下午4:16:02
					 **/
					// return
					// (((InvocationTargetException)e).getTargetException().toString());
					/**
					 * 任务： 描述：修改为getMessage(); 人员：施建龙 时间：2015年2月11日上午10:55:33
					 **/
					/**任务：*描述：若((InvocationTargetException) e)
							.getTargetException()不为BaseBllException,则返回""系统繁忙！""
					 *人员：施建龙  *时间：2015年5月17日下午8:12:53 **/
					Throwable temp=((InvocationTargetException) e)
							.getTargetException();
					if(temp instanceof BaseBllException){
						message= ((BaseBllException)temp).getViewMessage();
					}else{
						//Edit start fengze APPCOMMERCIALBUG-1299 将“系统内部错误”改为“系统繁忙” 2015-9-7 16:16:59
						message= "系统繁忙！";
						//Edit end fengze APPCOMMERCIALBUG-1299 将“系统内部错误”改为“系统繁忙” 2015-9-7 16:16:59
					}
				} else {
					/**任务：*描述：若内嵌的Exception为BaseBllException，则进行递归处理
					 * 若不是BaseBllException，则直接返回"系统繁忙"
					 *人员：施建龙  *时间：2015年5月17日下午3:52:39 **/
					if(e instanceof BaseBllException){
					// return e.toString();
						message= ((BaseBllException)e).getViewMessage();
					}else{
						//Edit start fengze APPCOMMERCIALBUG-1299 将“系统内部错误”改为“系统繁忙” 2015-9-7 16:16:59
						message= "系统繁忙！";
						//Edit end fengze APPCOMMERCIALBUG-1299 将“系统内部错误”改为“系统繁忙” 2015-9-7 16:16:59
					}
				}
			} else {
				if(!DotNetToJavaStringHelper.isNullOrEmpty(customMessage)){
					message=customMessage;
				}else{
					message= super.getMessage();
				}
			}
		/**
		 * comments:
		 * 
		 * sjl modify 2014年1月27日下午1:13:10
		 */
		if (message != null) {
			// || message.length()==0
			// message=this.toString();
			// message="";
			message = message.replace("APP.Comm.BLL.BaseBllException:", "");
		}
		return message;
	}
	/**
	 * <pre>
	 * 任务：KYEEAPPC-1584
	 * 描述：获取错误代码
	 * 作者：houhy 
	 * 时间：2015年2月28日下午4:25:06
	 * @return
	 * </pre>
	 */
	public String getErrorCode() {
		String _errorCode = null;
		if (!(e == null)) {
			if (e instanceof InvocationTargetException) {
				/**任务：*描述：只有针对BaseBllException才能通过getE获取上层异常
				 *人员：施建龙  *时间：2015年5月19日下午4:56:09 **/
				Throwable targetException =((InvocationTargetException) e).getTargetException();
				Throwable t=null;
				if(targetException instanceof BaseBllException){
					t = getFinalException(((BaseBllException)targetException ).getE());
				}else{
					t = targetException;
				}
				
				if (t instanceof BaseBllException)
					_errorCode = ((BaseBllException) t).getErrorCode();
				else {
					_errorCode = errorCode;
				}

			} else if (e instanceof BaseBllException) {
				// edit start KYEEAPPTEST-1759 获取业务类异常代码 houhy 2015年5月6日
				// 17:20:51
				Throwable t =null;
				t = getFinalException((BaseBllException)e);
				if (t != null) {
					if (t instanceof BaseBllException) {
						_errorCode = ((BaseBllException)t).getErrorCode();
					}
				}
				// edit end KYEEAPPTEST-1759 获取业务类异常代码 houhy 2015年5月6日 17:20:51
			} else {
				_errorCode = errorCode;
			}
		}
		if (DotNetToJavaStringHelper.isNullOrEmpty(_errorCode)) {
			_errorCode = errorCode;
		}
		// return errorCode;
		return _errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public JsonAlertType getAlertType() {
		JsonAlertType _alertType = alertType;
		if (!(e == null)) {
			if (e instanceof InvocationTargetException) {
				/**任务：*描述：
				 *人员：施建龙  *时间：2015年6月12日上午9:52:02 **/
				
				/**任务：*描述：只有针对BaseBllException才能通过getE获取上层异常
				 *人员：施建龙  *时间：2015年5月19日下午4:56:09 **/
				Throwable targetException =((InvocationTargetException) e).getTargetException();
				Throwable t=null;
				if(targetException instanceof BaseBllException){
					t = getFinalException(((BaseBllException)targetException ).getE());
				}else{
					t = targetException;
				}
				/**任务：*描述：
				 *人员：施建龙  *时间：2015年6月12日上午9:53:44 **/
//				Throwable t = getFinalException(((BaseBllException) ((InvocationTargetException) e)
//						.getTargetException()).getE());
//				if (t == null) {
//					t = getFinalException((BaseBllException) ((InvocationTargetException) e)
//							.getTargetException());
//				}
				if (t instanceof BaseBllException)
					_alertType = ((BaseBllException) t).alertType;
				else {
					_alertType = alertType;
				}

			} else if (e instanceof BaseBllException) {
				// edit start KYEEAPPTEST-1759 获取业务类异常提示类型 houhy 2015年5月6日
				// 17:20:51
				Throwable t = ((BaseBllException) e).getE();
				if (t != null) {
					if (t instanceof BaseBllException) {
						e = ((BaseBllException) t).getE();
						_alertType = getAlertType();
					}
				}
				// edit end KYEEAPPTEST-1759 获取业务类异常提示类型 houhy 2015年5月6日
				// 17:20:51
			} else {
				_alertType = alertType;
			}
		}
		return alertType;
	}

	public void setAlertType(JsonAlertType alertType) {
		this.alertType = alertType;
	}
}