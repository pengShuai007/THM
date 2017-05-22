package APP.Comm.BLL;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import APP.Comm.Config.SystemParams;
import APP.Comm.DataBase.Helper.IDataBase;
import APP.Comm.DotNet.HttpContext;
import APP.Comm.Util.CommonUtil;
import APP.Comm.Util.DotNetToJavaStringHelper;
import APP.Comm.Util.HLogger;
import APP.Comm.Util.HandlerUtil;
import APP.Comm.View.GridRequestParameters;

/**
 * BLL层类的父类 作者：左仁帅 编写时间：2013-01-05 09:45:30
 */
public class BaseBLL {

	private AppUser _appUser;

	private Map<String, String[]> _postParams;

	private GridRequestParameters _gridRequestParameters;

	private IDataBase _baseDB;

	public HttpContext context;

	private java.util.Map<Object, Object> _attrParams;

	// 记录方法参数信息的开关缓存
	public static String paramsOnOff = null;

	/**
	 * 业务处理类的容器对象，当业务处理类需要获取容器对象属性或者方法时使用 施建龙 2013年10月29日23:05:48
	 */
	private BLLContainer bllContainer = null;

	public final void setBllContainer(BLLContainer bllContainer) {
		this.bllContainer = bllContainer;
	}

	public final BLLContainer getBllContainer() {
		return this.bllContainer;
	}

	/**
	 * <pre>
	 * 作者:秦晓东
	 * 日期:2014年2月21日 上午2:14:59
	 * 描述:当前登录用户
	 * </pre>
	 */
	public final AppUser getAppUser() {
		return this._appUser;
	}

	public final void setAppUser(AppUser value) {
		this._appUser = value;
	}

	/**
	 * content 上下文post方式提交的参数集
	 */
	public final Map<String, String[]> getPostParams() {
		return _postParams;
	}

	public final void setPostParams(Map<String, String[]> value) {
		_postParams = value;
	}

	/**
	 * content 上下文get方式提交的参数集
	 */
	public final Map<String, String[]> getGetParams() {
		// return _getParams;
		return _postParams;
	}

	public final void setGetParams(Map<String, String[]> value) {
		// _getParams = value;
		_postParams = value;
	}

	/**
	 * 页面Grid插件请求参数对象
	 */
	public final GridRequestParameters getGridRequestParameters() {
		return _gridRequestParameters;
	}

	public final void setGridRequestParameters(GridRequestParameters value) {
		_gridRequestParameters = value;
	}

	/**
	 * BaseDB 数据库操作对象
	 */
	public final IDataBase getBaseDB() {
		return _baseDB;
	}

	public final void setBaseDB(IDataBase value) {
		_baseDB = value;
	}

	/**
	 * 扩展参数集合,直接通过集合Add就行，该变量无set方法.
	 */
	public final java.util.Map<Object, Object> getAttrParams() {
		if (this._attrParams == null) {
			this._attrParams = new HashMap<Object, Object>();
		}
		return this._attrParams;
	}

	/**
	 * 描述: 记录云op参数信息的方法<br/>
	 * 创建人: 党智康<br/>
	 * 任务单号：KYEEAPP-894
	 * 
	 * @since Ver 1.1
	 */
	private void recoderParameter(String parameterName) {
		try {
			if (_postParams != null && !_postParams.isEmpty()) {
				String loc = CommonUtil.getRequestMapParaValue(_postParams, "loc");
				String op = CommonUtil.getRequestMapParaValue(_postParams, "op");
				// 如果是云端的请求则进行以下记录参数的操作
				if (loc == null || "".equals(loc) || "c".equals(loc)) {
					/**
					 * 描述：多线程导致数据库连接关闭，引起无限抛出日志，直到栈溢出 修改人：党智康 修改时间：2014年11月6日
					 * 21:40 任务单号：KYEEAPPTEST-730
					 */
					// 当缓存中取不到时，则去数据库中取开关的信息值
					// if (paramsOnOff!= null)
					// paramsOnOff = new RecordOpInfoBll().getRecoderOnOff() +
					// "";
					/**
					 * 描述：修改存储接口信息的bll层 修改人：党智康 修改时间：2014年11月18日 11:34
					 * 任务单号：KYEEAPP-1019
					 */
					// 当为1时为记录参数
					if ("1".equals(paramsOnOff)) {
						// Edit begin 党智康 2014年11月25日 16:02:16
						// 记录接口参数列表信息中加入版本号
						// 任务：KYEEAPP-1019
						// RecordOpInfoBll recordOpInfoBll = new
						// RecordOpInfoBll();
						// String version = recordOpInfoBll.getVersionInfo();
						String version = SystemParams.getParamaValue("WEB_BUILDER_VERSION_APP");
						String opUrl = context.getRequest().getRequestURI();
						String allOpPath = opUrl.substring(5, opUrl.lastIndexOf(".")).replace("/", ".");
						String bllName = getBllName(5);
						// recordOpInfoBll.recoderOPInfo(allOpPath + "." + op
						// + "---->" + bllName, parameterName, version);
						// Edit end
					}
				}
			}
		} catch (Exception e) {
			HLogger.Error(e);
			e.printStackTrace();
		}
	}

	/**
	 * comments: sjl modify 2013-11-2上午11:48:30
	 */
	public String getParameterValue(String paraName) {
		String parString = CommonUtil.getRequestMapParaValue(_postParams, paraName);
		if (parString.startsWith("\\u")) {
			try {
				parString = URLDecoder.decode(paraName, "UTF-8");
				/**
				 * app到云，记录云上接口的参数信息
				 */
				// Edit start KYEEAPPMAINTENANCE-45 liuxingping
				getParameterValueKey(paraName, parString);
				// Edit end KYEEAPPMAINTENANCE-45 liuxingping
			} catch (UnsupportedEncodingException e) {
				HLogger.Error(e);
				e.printStackTrace();
			}
		}
		return parString;
	}

	/**
	 * 描述: 取到num层的堆栈方法的全路径名称 创建人: ypf 任务单号：KYEEAPP-894
	 * 
	 * @param num
	 * @return
	 * @since Ver 1.1
	 */
	private String getBllName(int num) {
		Thread thread = Thread.currentThread();
		StackTraceElement[] nowStack = thread.getStackTrace();
		return nowStack[num].getClassName() + "." + nowStack[num].getMethodName();
	}

	/**
	 * comments: sjl modify 2013-11-2下午12:08:03
	 */
	public String getAttrParameterValue(String paraName) {
		// 记录云到端，云上bll层接口的参数信息
		// Edit start KYEEAPPMAINTENANCE-45 liuxingping
		// getParameterValueKey(paraName, _attrParams.get(paraName) + "");
		// Edit end KYEEAPPMAINTENANCE-45 liuxingping
		return _attrParams.get(paraName) + "";
	}

	/**
	 * 描述: 判断value值是否含有json字符串，集合类等,如果有，则存下value<br/>
	 * 创建人: 党智康 <br/>
	 * 任务单号：KYEEAPP-894 修改人：党智康 修改时间：2014年11月5日 下午20:23:35 <br/>
	 * 修改描述：未进行字符串校验 任务号：KYEEAPPTEST-725
	 * 
	 * @param value
	 * @return
	 * @since Ver 1.1
	 */
	private void getParameterValueKey(String paraName, String value) {
		if (!DotNetToJavaStringHelper.isNullOrEmpty(value)) {
			if (value.matches("[{][\\w\\W]*[}]"))// 判断参数值里面是否有json类的字符串等
			{
				// edit start KYEEAPP-1070 如果value是json字符串,则循环解析 houhy
				// 2014年12月11日 11:31:18
				String valueParam = ",";
				valueParam += getJsonStrAllParams(value);
				if (",".equals(valueParam))
					valueParam = "";
				// recoderParameter(paraName + "," + value);
				// recoderParameter(paraName + valueParam);
				// edit end KYEEAPP-1070 如果value是json字符串,则循环解析 houhy 2014年12月11日
				// 11:31:18

			} else {
				recoderParameter(paraName);
			}
		} else {
			recoderParameter(paraName);
		}
	}

	/**
	 * <pre>
	 * 任务:KYEEAPP-1070
	 * 描述:获取json字符串参数
	 * 作者:houhy 
	 * 时间:2014年12月11日上午11:41:54
	 * &#64;param jsonStr
	 * &#64;return
	 * returnType：String
	 * </pre>
	 */
	public String getJsonStrAllParams(String jsonStr) {

		String valueParam = "";
		ArrayList<String> al = null;

		if (jsonStr.matches("[{][\\w\\W]*[}]")) {
			try {

				al = new ArrayList<String>();

				JSONObject dataJson = JSONObject.fromObject(jsonStr);

				for (Iterator it = dataJson.keys(); it.hasNext();) {
					String str = it.next() + "";
					// valueParam += str + ",";
					al.add(str);
				}

				// 循环dataJson中的值是否还存在json
				for (Iterator it1 = dataJson.values().iterator(); it1.hasNext();) {
					String str = it1.next() + "";
					if (str.matches("[{][\\w\\W]*[}]")) {
						// valueParam += getJsonStrAllChildParams(str) + ",";//
						// 递归
						al.addAll(getJsonStrAllChildParams(str));
					}
				}

				if (al != null && al.size() > 0) {
					Collections.sort(al);
					for (String str : al) {
						valueParam += str + ",";
					}
				}

				if ("".equals(valueParam)) {
					valueParam = "";
				} else {
					valueParam = valueParam.substring(0, valueParam.lastIndexOf(","));
				}
				// System.out.println(dataJson.keys());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				HLogger.info("参数监控时，解析json出错:" + e);
			}
		}

		return valueParam;
	}

	/**
	 * <pre>
	 * 任务:KYEEAPP-1070
	 * 描述:获取json子字符串参数 
	 * 作者:houhy 
	 * 时间:2014年12月11日下午1:54:33
	 * &#64;param jsonStr
	 * &#64;return
	 * &#64;throws BaseBllException
	 * returnType：String
	 * </pre>
	 */
	public ArrayList<String> getJsonStrAllChildParams(String jsonStr) {

		// String valueParam = "";
		ArrayList<String> al = null;
		if (jsonStr.matches("[{][\\w\\W]*[}]")) {
			try {
				al = new ArrayList<String>();
				JSONObject dataJson = JSONObject.fromObject(jsonStr);

				for (Iterator it = dataJson.keys(); it.hasNext();) {
					String str = it.next() + "";
					// valueParam += str + ",";
					al.add(str);
				}

				// 循环dataJson中的值是否还存在json
				for (Iterator it1 = dataJson.values().iterator(); it1.hasNext();) {
					String str = it1.next() + "";
					if (str.matches("[{][\\w\\W]*[}]")) {
						// valueParam += getJsonStrAllChildParams(str) + ",";//
						// 递归
						al.addAll(getJsonStrAllChildParams(str));
					}
				}

				/*
				 * if (",".equals(valueParam)) { valueParam = ""; } else {
				 * valueParam = valueParam.substring(0,
				 * valueParam.lastIndexOf(",")); }
				 */
				// System.out.println(dataJson.keys());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				HLogger.info("参数监控时，解析json出错:" + e);
			}
		}

		// return valueParam;
		return al;
	}

	public static void main(String[] args) throws Exception {
		String str = "{'a':'1','b':{'b1':'2','b2':{'b11':'3'}},'c':'5','d':{'d1':'2','d2':{'d11':'3','d12':{'d22':'asda'}}}}";
		String jsonParam = new BaseBLL().getJsonStrAllParams(str);
		System.out.println(jsonParam);
	}

	/*
	 * comments:自定义参数 sjl modify 2013-11-3上午10:53:35
	 */
	public void setAttrParameter(String paraName, Object Value) {
		_attrParams.put(paraName, Value);
	}

	public void setAttrParams(Map<Object, Object> _attrParams) {
		this._attrParams = _attrParams;
	}

	public HttpContext getContext() {
		return context;
	}

	public void setContext(HttpContext context) {
		this.context = context;
	}

	/**
	 * 任务： 描述：统一在基类添加获取医院方法，此处理便于兼容hospitalId的两类写法 人员：施建龙
	 * 时间：2014年12月30日下午2:03:01
	 * 
	 * @throws BaseBllException
	 **/
	public String getParameterHospitalID() throws BaseBllException {
		/**
		 * 任务： 描述： 人员：施建龙 时间：2015年1月18日下午5:40:23
		 **/
		// if(_postParams==null){
		// return "-1";
		// }
		String hospitalID = null;
		// hospitalID=CommonUtil.getRequestMapParaValue(_postParams,
		// "hospitalID");
		// if(DotNetToJavaStringHelper.isNullOrEmpty(hospitalID)){
		// hospitalID = CommonUtil.getRequestMapParaValue(_postParams,
		// "HOSPITAL_ID");
		// }
		// if(DotNetToJavaStringHelper.isNullOrEmpty(hospitalID)){
		// hospitalID = CommonUtil.getRequestMapParaValue(_postParams,
		// "hospitalId");
		// }
		/**
		 * 任务： 描述：使用同一的工具类提供获取医院ID 人员：施建龙 时间：2015年1月19日下午1:44:54
		 **/
		if (context == null) {
			hospitalID = "-1";
		} else {
			hospitalID = HandlerUtil.getHospitalID(this.context.getRequest());
			if (DotNetToJavaStringHelper.isNullOrEmpty(hospitalID)) {
				throw new BaseBllException("获取医院失败，请先选择医院！");
			}
		}
		return hospitalID;
	}

	

}
