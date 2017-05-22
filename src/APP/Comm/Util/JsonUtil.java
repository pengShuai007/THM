package APP.Comm.Util;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import APP.Comm.DataBase.DotNet.DataTable;
import APP.Comm.Util.Json.UtilDateDeserializer;
import APP.Comm.Util.Json.UtilDateSerializer;
import APP.Comm.View.GridRequestParameters;
import APP.Model.BaseEntity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

//using Util.JsonModel;
/**
 * comments:从DotNet迁移，使用Gson类库
 * 
 * sjl modify 2013-10-4下午1:07:49
 */
/**
 * Josn 工具类 author：左仁帅 creatTime：2012-12-20 13:54:34
 */
public class JsonUtil {

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#region Objcet to jsonString

	/**
	 * 
	 * 描述: Json字符串转成Map<String, Object>
	 * 创建人: ypf <br/>
     * 修改时间：2014-10-14 22:03:52
     * 任务号：KYEEAPP-863
	 * @param jsonString 传入的json字符串
	 * @return 
	 * @since Ver 1.1
	 */
		public static Map<String, Object> jsonToMap(String jsonString){
		    Type type = new TypeToken<Map<String, Object>>(){}.getType();
	        Map<String, Object> resultMap = new Gson().fromJson(jsonString, type);
	        return resultMap;
		}
	/**
	 * 通用：最终JSON输出格式 author：左仁帅 creatTime：2012-12-20 13:54:34
	 * 
	 * @param success
	 *            执行状态，成功与否
	 * @param message
	 *            执行后返回的消息
	 * @param dataJson
	 *            数据
	 * @return
	 */
	public static String outJson(boolean success, String message,
			String dataJson) {
		dataJson = DotNetToJavaStringHelper.isNullOrEmpty(dataJson) ? "null"
				: dataJson;
		String jsonString = "{\"success\":"
				+ (new Boolean(success)).toString().toLowerCase()
				+ ",\"message\":\"" + (message + "").trim() + "\",\"data\":"
				+ (dataJson + "").trim() + "}";
//		HLogger.info(jsonString);
		return jsonString;
	}
	
	/**
	 * 通用：最终JSON输出格式 author：郭著 creatTime：2014-12-17 13:54:34
	 * 
	 * @param success
	 *            执行状态，成功与否
	 * @param message
	 *            执行后返回的消息
	 * @param dataJson
	 *            数据
	 * @return
	 */
	public static String outJson(boolean success, String message,
			String dataJson, String resultCode) {
		dataJson = DotNetToJavaStringHelper.isNullOrEmpty(dataJson) ? "null"
				: dataJson;
		String jsonString = "{\"success\":"
				+ (new Boolean(success)).toString().toLowerCase()
				+ ",\"message\":\"" + (message + "").trim() + "\",\"resultCode\":\"" + (resultCode + "").trim() + "\",\"data\":"
				+ (dataJson + "").trim() + "}";
//		HLogger.info(jsonString);
		return jsonString;
	}

	public static String covertListBeanString(String jsonString) {
		// return jsonString.substring(jsonString.indexOf("["),
		// jsonString.lastIndexOf("]") + 1);
		if (jsonString.indexOf("[") != -1 && jsonString.lastIndexOf("]") != -1) {
			int start = jsonString.indexOf("[");
			int end = jsonString.lastIndexOf("]");
			return jsonString.substring(start, end + 1);
		} else {
			return "";
		}

	}

	/**
	 * 通用：返回给页面Grid的json 格式 author：左仁帅 creatTime：2012-12-20 13:54:34
	 * 
	 * @param total
	 * @param rows
	 * @return
	 */
	public static String outJson(int total, String rows) {

		String jsonString = "{\"total\":\"" + total + "\",\"rows\":" + rows
				+ "}";
//		HLogger.info(jsonString);
		return jsonString;
	}

	/**
	 * 说明：程序异常时候，输出的json数据格式 author：左仁帅 creatTime：2012-12-20 13:54:34
	 * 
	 * @param message
	 * @return
	 */
	public static String exceptionJson(String message) {
		// String jsonString ="{\"success\":false,\"message\":\"" +
		// (message+"").trim() + "\"}";
		String jsonString = "{'success':false,'message':'"
				+ (message + "").trim() + "'}";
		HLogger.info(jsonString);
		return jsonString;
	}

	/**
	 * 说明：执行错误返回的json author：左仁帅 creatTime：2012-12-20 13:54:34
	 * 
	 * @param message
	 * @return
	 */
	public static String errorMessageJsonString(String message) {
		return JsonUtil.outJson(false, message, "null");
	}

	/**
	 * 说明：执行错误返回的json author：左仁帅 creatTime：2012-12-20 13:54:34
	 * 
	 * @param message
	 * @param data
	 * @return
	 */
	public static String errorMessageJsonString(String message, String data) {

		return JsonUtil.outJson(false, message, data);
	}
	/**
	 * 说明：执行错误返回的json author：郭著creatTime：2014-12-18 13:54:34
	 * 
	 * @param message
	 * @param data
	 * @return
	 */
	public static String errorMessageJsonString(String message, String data, String resultCode) {
		
		return JsonUtil.outJson(false, message, data,resultCode);
	}

	/**
	 * 说明：执行成功返回的json author：左仁帅 creatTime：2012-12-20 13:54:34
	 * 
	 * @param message
	 * @return
	 */
	public static String successMessageJsonString(String message) {

		return JsonUtil.outJson(true, message, "null");
	}

	/**
	 * 说明：执行成功返回的json author：左仁帅 creatTime：2012-12-20 13:54:34
	 * 
	 * @param message
	 * @param data
	 *            json格式的数据，可使用JsonUtil.objectToJsonString() 方法把对象转换为json格式数据
	 * @return
	 */
	public static String successMessageJsonString(String message, String data) {

		return JsonUtil.outJson(true, message, data);
	}

	/**
	 * 说明：List集合类型的对象转换为json格式数据字符串 作者：左仁帅 creatTime：2012-12-20 13:54:34
	 * 
	 * @param total
	 * @param success
	 * @param message
	 * @param listObj
	 * @param jsonType
	 * @return
	 */
	public static String listBeanToJsonString(int total, boolean success,
			String message, Object listObj, OutJsonType jsonType) {
		// JsonConverter[] jsParams = new JsonConverter[1];
		// IsoDateTimeConverter d1 = new IsoDateTimeConverter();
		// d1.setDateTimeFormat("yyyy-MM-dd HH:mm:ss");
		// jsParams[0] = d1;
		switch (jsonType) {
		case Common:
			return JsonUtil.outJson(success, message,
					objectToJsonString(listObj));
		case Grid:
			return JsonUtil.outJson(total, objectToJsonString(listObj));
		case Combox:
			return objectToJsonString(listObj);
		case Tree:
			return objectToJsonString(listObj).replace("\"Checked\":",
					"\"checked\":");
		default:
			return "";

		}
	}

	/**
	 * 
	 * <pre>
	 * 作者:秦晓东
	 * 日期:2014年2月22日 下午3:44:35
	 * 描述:返回Gson对象
	 * </pre>
	 */
	public static Gson getJsonGson() {
		return new GsonBuilder()
				.registerTypeAdapter(java.util.Date.class,
						new UtilDateSerializer())
				.setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	}

	/**
	 * 
	 * <pre>
	 * 作者:秦晓东
	 * 日期:2014年2月22日 下午2:09:33
	 * 描述:json字符串转换为List对象
	 * </pre>
	 */
	public static List jsonStringToListBean(String jsonString, Class type) {
    	//update by jujin 2016年3月21日17:14:21 APPCOMMERCIALBUG-2225 科室同步失败
        Object[] objs = jsonStringToObjects(jsonString, type);
        List lst = new ArrayList();
        if (objs == null)
            return lst;
        List tmpLst = Arrays.asList(objs);
        if (tmpLst != null) {
        	for (int index = 0; index < tmpLst.size(); index++) {
        		lst.add(tmpLst.get(index));
        	}
        }
        return lst;
    }
	
	public static Object[] jsonStringToObjects(String jsonString,
            java.lang.Class type) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(java.util.Date.class,
                        new UtilDateDeserializer())
                .setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        JsonParser parser = new JsonParser();
        JsonElement el = parser.parse(jsonString);

        JsonArray array = null;
        if (el.isJsonArray()) {
            array = el.getAsJsonArray();
        }
     
        if(array != null){
	        Iterator it = array.iterator();
	        Object[] obj = new Object[array.size()];
	        int i = 0;
	        while (it.hasNext()) {
	            JsonElement e = (JsonElement) it.next();
	            // JsonElement转换为JavaBean对象
	            obj[i] = gson.fromJson(e, type);
	            i++;
	        }
	        HLogger.debug("Json String to Object[]:" + jsonString);
	        return obj;
        }else{
        	return null;
        }
    }

	/**
	 * 说明： dataTable对象转换为json格式数据字符串 author：zuorenshuai creatTime：2012-12-20
	 * 13:54:34
	 * 
	 * @param total
	 * @param success
	 * @param message
	 * @param dt
	 * @param jsonType
	 * @return
	 */
	public static String dataTableToJsonString(int total, boolean success,
			String message, DataTable dt, OutJsonType jsonType) {
		// JsonConverter[] jsParams = new JsonConverter[1];
		// IsoDateTimeConverter d1 = new IsoDateTimeConverter();
		// d1.setDateTimeFormat("yyyy-MM-dd HH:mm:ss");
		// jsParams[0] = d1;
		/**
		 * comments:不提供方法重载，直接修改此方法，在转换为Json之前，先转换DataTabe为EntityList
		 * 
		 * sjl modify 2013-10-18上午9:03:28
		 */
		// ,Class rowClass
		// java.util.List<BaseEntity> rowList=null;
		// rowList=EntityUtil.CreateEntityList(dt, rowClass, true);

		switch (jsonType) {
		case Common:
			// return JsonUtil.outJson(success, message,
			// objectToJsonString(rowList));
			return JsonUtil.outJson(success, message,
					DataTableJsonUtil.dataTableToJson(dt));
		case Grid:
			// return JsonUtil.outJson(total,
			// objectToJsonString(rowList));
			return JsonUtil.outJson(total,
					DataTableJsonUtil.dataTableToJson(dt));
		case Combox:
			// return objectToJsonString(rowList);
			return DataTableJsonUtil.dataTableToJson(dt);
		case Tree:
			// return objectToJsonString(rowList).replace(
			// "\"Checked\":", "\"checked\":");
			return DataTableJsonUtil.dataTableToJson(dt).replace(
					"\"Checked\":", "\"checked\":");
		default:
			return "";

		}
	}

	/**
	 * 说明：任意对象转化为jsonString 作者：左仁帅 时间：2013年2月27日 13:43:07
	 * 
	 * @param obj
	 * @return
	 */
	public static String objectToJsonString(Object obj) {
		// JsonConverter[] jsParams = new JsonConverter[1];
		// IsoDateTimeConverter d1 = new IsoDateTimeConverter();
		// d1.setDateTimeFormat("yyyy-MM-dd HH:mm:ss");
		// jsParams[0] = d1;
		// return JsonConvert.SerializeObject(obj, jsParams);
		/**
		 *任务：
		 *描述：对于Entity类型，在转换为String的时候清除UpdateAttributes集合值
		 *人员：施建龙
		 *时间：2014年12月12日上午11:04:43
		 **/
		/**
		 *任务：
		 *描述：此方式不可行，通过底层json转换进行处理
		 *人员：施建龙
		 *时间：2014年12月12日下午3:01:11
		 **/
//		if(obj instanceof BaseEntity){
//			((BaseEntity)obj).ClearUpdateAttribute();
//		}
		
		Gson gson = new GsonBuilder()
				.registerTypeAdapter(java.util.Date.class,
						new UtilDateSerializer())
				.setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		String result = gson.toJson(obj);
		//HLogger.info("Json String From Object:" + result);
		HLogger.info("Json String From Object");
		return result;
	}

	/**
	 * 说明：任意对象转化为jsonString 作者：左仁帅 时间：2013年2月27日 13:43:07
	 * 
	 * @param obj
	 * @param dateTimeFormat
	 *            日期格式化的格式，例如yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String objectToJsonString(Object obj, String dateTimeFormat) {
		// JsonConverter[] jsParams = new JsonConverter[1];
		// IsoDateTimeConverter d1 = new IsoDateTimeConverter();
		// d1.setDateTimeFormat(dateTimeFormat);
		// jsParams[0] = d1;
		// return JsonConvert.SerializeObject(obj, jsParams);
		
		/**
		 *任务：
		 *描述：对于Entity类型，在转换为String的时候清除UpdateAttributes集合值
		 *人员：施建龙
		 *时间：2014年12月12日上午11:04:43
		 **/
		/**
		 *任务：
		 *描述：此方式不可行，通过底层json转换进行处理
		 *人员：施建龙
		 *时间：2014年12月12日下午3:01:11
		 **/
//		if(obj instanceof BaseEntity){
//			((BaseEntity)obj).ClearUpdateAttribute();
//		}
		
		SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);

		Gson gson = new GsonBuilder()
				.registerTypeAdapter(java.util.Date.class,
						new UtilDateSerializer()).setDateFormat(dateTimeFormat)
				.create();
		String result = gson.toJson(obj);
		HLogger.info("Json String From Object" );
		return result;
	}

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#endregion

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#region jsonString to object (json 字符串转化成object对象)

	/**
	 * 说明：json 字符串转化成object对象 author：zuorenshuai creatTime：2012-12-20 14:02:59
	 * 
	 * @param jsonString
	 * @param type
	 * @return
	 */
	public static Object jsonStringToObject(String jsonString,
			java.lang.Class type) {

		// return JsonConvert.DeserializeObject(jsonString, type);
		Gson gson = new GsonBuilder()
				.registerTypeAdapter(java.util.Date.class,
						new UtilDateDeserializer())
				.setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		// Gson gson = new GsonBuilder().create();
		//HLogger.info("Json String to Object:" + jsonString);
		HLogger.info("Json String to Object");
		return gson.fromJson(jsonString, type);
	}

	/**
	 * 说明：json 字符串转化成object对象(能转换JavaScriptDateTime) author：zuorenshuai
	 * creatTime：2012-12-20 14:03:04
	 * 
	 * @param json
	 * @param type
	 * @return
	 */
	public static Object JsonStringToObjectByConverter(String json,
			java.lang.Class type) {
		// JsonConverter[] jsParams = new JsonConverter[2];
		// jsParams[0] = new DataTableConverter();
		// jsParams[1] = new JavaScriptDateTimeConverter();
		// return JsonConvert.DeserializeObject(json, type, jsParams);

		Gson gson = new GsonBuilder()
				.registerTypeAdapter(java.util.Date.class,
						new UtilDateDeserializer())
				.setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		HLogger.info("Json String to Object:" + json);
		return gson.fromJson(json, type);

	}

	/**
	 * 说明： json 字符串转化成object对象(格式化时间：yyyy-MM-dd HH:mm:ss:fff) author：zuorenshuai
	 * creatTime：2012-12-20 14:04:05
	 * 
	 * @param json
	 * @param type
	 * @return
	 */
	public static Object JsonStringToObjectDF(String json,
			java.lang.Class type, String dateTimeFormat) {
		// JsonConverter[] jsParams = new JsonConverter[1];
		// IsoDateTimeConverter d1 = new IsoDateTimeConverter();
		// d1.setDateTimeFormat("yyyy-MM-dd HH:mm:ss:fff");
		// jsParams[0] = d1;
		// return JsonConvert.DeserializeObject(json, type, jsParams);
		Gson gson = new GsonBuilder()
				.registerTypeAdapter(java.util.Date.class,
						new UtilDateDeserializer())
				.setDateFormat(dateTimeFormat).create();
		HLogger.info("Json String:" + json);
		return gson.fromJson(json, type);
	}

	/**
	 * 说明：将grid请求参数封装成对象 author：zuorenshuai creatTime：2012-12-20 14:04:05
	 * 
	 * @param requersParams
	 *            context.Request.Params
	 * @return
	 */
	public static GridRequestParameters GridRequestJsonToObject(
			Map<String, String[]> requersParams) {
		GridRequestParameters gridObject = new GridRequestParameters();
		gridObject.setOrder(requersParams.get("order") != null ? requersParams
				.get("order")[0] : "");
		//
		String page = null;
		page = requersParams.get("page") != null ? requersParams.get("page")[0]
				: "";
		gridObject.setPage(Integer.parseInt((page == null || (new String(""))
				.equals(page)) ? gridObject.DEFAULT_PAGE + "" : page));
		//
		String rows = null;
		rows = requersParams.get("rows") != null ? requersParams.get("rows")[0]
				: "";

		gridObject.setRows(Integer.parseInt((rows == null || (new String(""))
				.equals(rows)) ? gridObject.DEFAULT_ROWS + "" : rows));
		//
		String sort = null;
		sort = requersParams.get("sort") != null ? requersParams.get("sort")[0]
				: "";

		gridObject.setSort(sort);
		return gridObject;

	}
	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#endregion
	/**
     * Json提示信息类型
     * 
     * @author houhy
     * 
     */
    public enum JsonAlertType {
        /*
         * 不提示
         */
        NO,
        /*
         * 提示信息
         */
        ALERT,
        /*
         * 提示并确认信息
         */
        CONFIRM
    }
}