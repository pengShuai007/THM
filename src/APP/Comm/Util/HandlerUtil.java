package APP.Comm.Util;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 任务： 描述： 人员：施建龙 时间：2015年1月18日下午6:17:58
 **/
public class HandlerUtil {

	public static String getHospitalID(HttpServletRequest request) {
		String hospitalID = request.getParameter("hospitalID");
		if (DotNetToJavaStringHelper.isNullOrEmpty(hospitalID)) {
			hospitalID = request.getParameter("HOSPITAL_ID");
			if (DotNetToJavaStringHelper.isNullOrEmpty(hospitalID)) {
				/**
				 * 任务： 描述：医院ID目前有3个关键字：hospitalID，hospitalId，HOSPITAL_ID 人员：施建龙
				 * 时间：2015年1月13日上午9:41:09
				 **/
				hospitalID = request.getParameter("hospitalId");
				if (DotNetToJavaStringHelper.isNullOrEmpty(hospitalID)) {
					hospitalID = "-1";
				}
			}
		}
		return hospitalID;
	}

	/**
	 * 从request请求中取用户ID
	 */
	public static String getUserId(HttpServletRequest _request) {
		String userId = _request.getParameter("operateCurrent_UserId");
		if (DotNetToJavaStringHelper.isNullOrEmpty(userId)) {
			userId = _request.getParameter("USER_ID");
			if (DotNetToJavaStringHelper.isNullOrEmpty(userId)) {
				userId = _request.getParameter("userId");
				if (DotNetToJavaStringHelper.isNullOrEmpty(userId)) {
					userId = "-1";
				}
			}
		}
		return userId;
	}

	/**
	 * 从request请求中取Op
	 */
	public static String getOp(HttpServletRequest _request) {
		String op = _request.getParameter("op");
		if (DotNetToJavaStringHelper.isNullOrEmpty(op)) {
			op = _request.getParameter("reqReserved");
			if (DotNetToJavaStringHelper.isNullOrEmpty(op)) {
				op = "";
			}
		}
		return op;
	}
	
	/**
	 * 从request请求中取用户来源
	 */
	public static String getUserSource(HttpServletRequest _request) {
		String userSource = _request.getParameter("userSource");
		if (DotNetToJavaStringHelper.isNullOrEmpty(userSource)) {
			userSource = _request.getParameter("operateUserSource");
			if (DotNetToJavaStringHelper.isNullOrEmpty(userSource)) {
				userSource = _request.getParameter("USER_SOURCE");
				if (DotNetToJavaStringHelper.isNullOrEmpty(userSource)) {
					userSource = "0";
				}
			}
		}
		return userSource;
	}
	
	/**
	 * 
	* <pre>
	* 任务:KYEEAPPC-3039
	* 描述:取得公共服务类型PUBLIC_SERVICE_TYPE
	* 作者:houhy
	* 日期:2015年8月12日下午7:47:37
	* @param _request
	* @return
	* </pre>
	 */
	public static String getPublicServiceType(HttpServletRequest _request) {
		String publicServiceType = _request.getParameter("PUBLIC_SERVICE_TYPE");
		if (DotNetToJavaStringHelper.isNullOrEmpty(publicServiceType)) {
			publicServiceType=_request.getParameter("publicServiceType");
			if(DotNetToJavaStringHelper.isNullOrEmpty(publicServiceType))
				publicServiceType = "0";
		}else if("1".equals(publicServiceType)){
			publicServiceType="0";
		}
		return publicServiceType;
	}
	
	/**
	* <pre>
	* 任务:KYEEAPPC-3039
	* 描述:获取版本号
	* 作者:houhy
	* 日期:2015年8月12日下午8:32:23
	* @param _request
	* @return
	* </pre>
	*/
	public static String getApkVersion(HttpServletRequest _request) {
		String opVersion = _request.getParameter("opVersion");
		return opVersion;
	}
	
	/**
	 * 返回request请求参数Map对应的Json字符串
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String getRequestMapString(HttpServletRequest request){
		// 参数Map
	    Map properties = request.getParameterMap();
	    // 返回值Map
	    Map mapTemp = new HashMap();
	    Iterator entries = properties.entrySet().iterator();
	    Map.Entry entry;
	    String name = "";
	    String value = "";
	    while (entries.hasNext()) {
	        entry = (Map.Entry) entries.next();
	        name = (String) entry.getKey();
	        Object valueObj = entry.getValue();
	        if(null == valueObj){
	            value = "";
	        }else if(valueObj instanceof String[]){
	            String[] values = (String[])valueObj;
	            for(int i=0;i<values.length;i++){
	                value = values[i] + ",";
	            }
	            value = value.substring(0, value.length()-1);
	        }else{
	            value = valueObj.toString();
	        }
	        mapTemp.put(name, value);
	    }
	    return JsonUtil.getJsonGson().toJson(mapTemp);
	}
	
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (DotNetToJavaStringHelper.isNullOrEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (DotNetToJavaStringHelper.isNullOrEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (DotNetToJavaStringHelper.isNullOrEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		// 多重代理
		if (!DotNetToJavaStringHelper.isNullOrEmpty(ip) && ip.length() > 15) {
			if (ip.indexOf(",") > 0) {
				ip = ip.substring(0, ip.indexOf(","));
			}
		}
		return ip;
	}
	/**
	 * 
	 * <pre>
	 * 描述： 获取服务器IP
	 * 作者：lijunqiang 
	 * 时间：2016年4月14日上午9:53:48
	 * @return
	 * returnType：String
	 * </pre>
	 */
	public static String getServerIp() {
		return getServerIp(null);
	}
	public static String getServerIp(HttpServletRequest request) {
		InetAddress inet = null;
		String ip = "";
		try {
			inet = InetAddress.getLocalHost();
			ip = inet.getHostAddress();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (request!=null && (DotNetToJavaStringHelper.isNullOrEmpty(ip) || "unknown".equalsIgnoreCase(ip))) {
			ip = request.getLocalAddr();
		}
		return ip;
	}
}
