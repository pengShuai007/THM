/**
 * 文件名:HttpClientUtil.java
 * CopyRight (c) 2014-2015
 * 创建人:秦晓东
 * 日期:2014年2月18日
 * 描述:
 */
package APP.Comm.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import APP.Comm.BLL.BaseBLL;
import APP.Comm.BLL.BaseBllException;
import APP.Comm.Config.SystemParams;
import APP.Comm.DataBase.Helper.IDataBase;
import APP.Comm.DotNet.Application;
import APP.Model.Base.C_SYSTEM_HOSPITAL_EXT;

/**
 * @author Dike
 */
public class HttpClientUtil {

	// private static HandleTVersion handleTVersion = new HandleTVersion();

	/**
	 * 版本是否兼容标示
	 */
	private static final String ISVERSION_SAME = "isVersionSame";

	/**
	 * 方法版本key
	 */
	private static final String OP_VERSION = "opVersion";

	// private static HandleTVersionDal handleTVersionDal = new
	// HandleTVersionDal();
    private static Integer timeout=20000;
    private static boolean timeoutSetFlag=false;
	private static String HOSPITAL_ID = "";

	private static String OP_NAME = "";

	/**
	 * 转换inputstraem为string
	 * 
	 * @param in
	 * @return
	 */
	public static String inputStreamToString(InputStream in) {
		StringBuffer sb = new StringBuffer();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
			HLogger.Error(e);
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				HLogger.Error(e);
			}
		}
		String content = sb.toString();
		return content;
	}

	/**
	 * 描述: 原始post请求对外接口 创建人: 李智博
	 * 
	 * @param url
	 * @param method
	 * @param loc
	 * @param params
	 * @param hosId
	 * @return
	 * @throws Exception
	 */
	public static String origHttpPost(String url, String suffix, String method,
			Map<String, String> params) throws BaseBllException {
		//Add start huangpeng KYEEAPPMAINTENANCE-1162 2016年12月12日 20:26:34
		boolean isSuccess = false;
        String monitor_process_id = "";
		boolean isMonitorUser = false;
		String postParams = "";
		String loc = "";
		//Add end huangpeng KYEEAPPMAINTENANCE-1162 2016年12月12日 20:26:38
		String result = "";
		if (DotNetToJavaStringHelper.isNullOrEmpty(url)) {
			throw new BaseBllException("未配置URL连接");
		}

		// 拼接地址和方法
		url = url + (url.endsWith("/") ? suffix.substring(1) : suffix);
		if (method != null && !method.equals("")) {
			url = url + "?op=" + method;
		}

		// 处理访问参数
		if (params != null) {
			Iterator<Entry<String, String>> iter = params.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry<String, String> entry = (Map.Entry<String, String>) iter
						.next();
				url = url + "&" + entry.getKey() + "=" + entry.getValue();
				//Add start huangpeng KYEEAPPMAINTENANCE-1162 2016年12月12日 20:33:30
				if(entry.getKey().toLowerCase().equals("loc")){
					loc = entry.getValue();
				}
				//Add end huangpeng KYEEAPPMAINTENANCE-1162 2016年12月12日 20:33:34
			}
		}
		CloseableHttpClient httpClient = null;
		try {
			// 协议兼容
			if (url.toLowerCase().indexOf("https://") > -1) {
				httpClient = createCommonHttpsClient();
			} else if (url.toLowerCase().indexOf("http://") > -1) {
				httpClient = createHttpClient(url);
			} else {// 缺省使用http
				url = "http://" + url;
				httpClient = createHttpClient(url);
			}
			HLogger.info("请求url:"+url);
			//Add start huangpeng KYEEAPPMAINTENANCE-1162 2016年12月12日 20:15:17
			/*isMonitorUser = UserProcessMonitorUtil.getIsMonitorUser();
			if (isMonitorUser) {
				monitor_process_id = TimeStampRadomUtil.GetARadomValue();
			}*/
			//Add end huangpeng KYEEAPPMAINTENANCE-1162 2016年12月12日 20:15:21
			HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Access-Control-Allow-Origin", "*");
            httpPost.addHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, Accept,X-Requested-With");
            List<NameValuePair> nvpsd = new ArrayList<NameValuePair>();
            //Add start huangpeng KYEEAPPMAINTENANCE-1162 2016年12月13日 09:32:05
            if (params != null) {
				for (String key : params.keySet()) {
					nvpsd.add(new BasicNameValuePair(key, params.get(key)));
					// 做参数
					if ("t".equals(loc)) {
						recoderTParameter(url, method, key, params.get(key));
					}
				}
			}
            postParams = buildParamJsonStr(nvpsd);
            //Add end huangpeng KYEEAPPMAINTENANCE-1162 2016年12月13日 09:32:10
            // 键值对没有拼接
            httpPost.setEntity(new UrlEncodedFormEntity(nvpsd, Consts.UTF_8));
            
            
			RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
            requestConfigBuilder.setConnectionRequestTimeout(timeout)
            		.setSocketTimeout(timeout).setConnectTimeout(timeout);
	        
            RequestConfig requestConfig = ProxyClientUtil.setProxyConfig(requestConfigBuilder).build();
            httpPost.setConfig(requestConfig);

			CloseableHttpResponse response2 = httpClient.execute(httpPost);
			response2.addHeader("Access-Control-Allow-Methods",
					"POST, GET, OPTIONS");
			response2.addHeader("Access-Control-Allow-Headers",
					"Content-Type, Authorization, Accept,X-Requested-With");
			try {
				HttpEntity entity2 = response2.getEntity();
				if (response2.getStatusLine().getStatusCode() == 200) {
					result = inputStreamToString(entity2.getContent());
					//Add start huangpeng KYEEAPPMAINTENANCE-1162 2016年12月13日 10:25:29
					isSuccess = true;
					//Add end huangpeng KYEEAPPMAINTENANCE-1162 2016年12月13日 10:25:32
				} else {
					String content = inputStreamToString(response2.getEntity()
							.getContent());
					HLogger.error(content);
				}
			} catch (IOException e) {
				HLogger.Error("返回编码"+response2.getStatusLine().getStatusCode());
				HLogger.Error(e);
			} finally {
				try {
					response2.close();
				} catch (IOException e) {
					HLogger.Error(e);
				}
			}
		} catch (Exception e) {
			
			HLogger.Error(e);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				HLogger.Error(e);
			}
			//Add start huangpeng KYEEAPPMAINTENANCE-1162 2016年12月13日 10:20:36
			recordMonitor(url, method, loc, isSuccess, monitor_process_id,
					HOSPITAL_ID, null, result, postParams);
			//Add end huangpeng KYEEAPPMAINTENANCE-1162 2016年12月13日 10:20:40
		}
		return result;
	}
	/**
	 * 描述: 原始post请求对外接口 创建人: 荣昌
	 * 医院+接口无op，有token.增加只传入参数的接口，去除写死的op参数
	 * @param url
	 * @param method
	 * @param loc
	 * @param params
	 * @param hosId
	 * @return
	 * @throws Exception
	 */
	public static String origHttpPost(String url, String suffix,
			Map<String, String> params) throws BaseBllException {
		//1Add start huangpeng KYEEAPPMAINTENANCE-1162 2016年12月12日 20:26:34
		boolean isSuccess = false;
        String monitor_process_id = "";
		boolean isMonitorUser = false;
		String postParams = "";
		String loc = "";
		//Add end huangpeng KYEEAPPMAINTENANCE-1162 2016年12月12日 20:26:38
		String result = "";
		if (DotNetToJavaStringHelper.isNullOrEmpty(url)) {
			throw new BaseBllException("未配置URL连接");
		}

		// 拼接地址和方法
		url = url + (url.endsWith("/") ? suffix.substring(1) : suffix);
		

		// 处理访问参数
		if (params != null) {
			int sum=0;
			url = url + "?" ;
			Iterator<Entry<String, String>> iter = params.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry<String, String> entry = (Map.Entry<String, String>) iter
						.next();
				//2Add start huangpeng KYEEAPPMAINTENANCE-1162 2016年12月12日 20:33:30
				if(entry.getKey().toLowerCase().equals("loc")){
					loc = entry.getValue();
				}
				//Add end huangpeng KYEEAPPMAINTENANCE-1162 2016年12月12日 20:33:34
				if (sum != 0) {
					url = url + "&" + entry.getKey() + "=" + entry.getValue();
				} else {
					url = url + entry.getKey() + "=" + entry.getValue();
				}
				sum++;
			}
		}
		CloseableHttpClient httpClient = createHttpClient(url);
		try {
			if (url.indexOf("http://") > -1) {
				
			} else // 缺省使用http
			{
				url = "http://" + url;
			}
			//3Add start huangpeng KYEEAPPMAINTENANCE-1162 2016年12月12日 20:15:17
			/*isMonitorUser = UserProcessMonitorUtil.getIsMonitorUser();
			if (isMonitorUser) {
				monitor_process_id = TimeStampRadomUtil.GetARadomValue();
			}*/
			//Add end huangpeng KYEEAPPMAINTENANCE-1162 2016年12月12日 20:15:21
			HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Access-Control-Allow-Origin", "*");
            httpPost.addHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, Accept,X-Requested-With");
            List<NameValuePair> nvpsd = new ArrayList<NameValuePair>();
            //4Add start huangpeng KYEEAPPMAINTENANCE-1162 2016年12月13日 09:32:05
            if (params != null) {
				for (String key : params.keySet()) {
					nvpsd.add(new BasicNameValuePair(key, params.get(key)));
					// 做参数
					if ("t".equals(loc)) {
						recoderTParameter(url, "", key, params.get(key));
					}
				}
			}
            postParams = buildParamJsonStr(nvpsd);
            //Add end huangpeng KYEEAPPMAINTENANCE-1162 2016年12月13日 09:32:10
            // 键值对没有拼接
            httpPost.setEntity(new UrlEncodedFormEntity(nvpsd, Consts.UTF_8));
            
            
			RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
            requestConfigBuilder.setConnectionRequestTimeout(timeout)
            		.setSocketTimeout(timeout).setConnectTimeout(timeout);
	        
            RequestConfig requestConfig = ProxyClientUtil.setProxyConfig(requestConfigBuilder).build();
            httpPost.setConfig(requestConfig);
					
			CloseableHttpResponse response2 = httpClient.execute(httpPost);
			response2.addHeader("Access-Control-Allow-Methods",
					"POST, GET, OPTIONS");
			response2.addHeader("Access-Control-Allow-Headers",
					"Content-Type, Authorization, Accept,X-Requested-With");
			try {
				HttpEntity entity2 = response2.getEntity();
				if (response2.getStatusLine().getStatusCode() == 200) {
					result = inputStreamToString(entity2.getContent());
					//5Add start huangpeng KYEEAPPMAINTENANCE-1162 2016年12月13日 10:25:29
					isSuccess = true;
					//Add end huangpeng KYEEAPPMAINTENANCE-1162 2016年12月13日 10:25:32
				} else {
					String content = inputStreamToString(response2.getEntity()
							.getContent());
					HLogger.error(content);
				}
			} catch (IOException e) {
				HLogger.Error(e);
			} finally {
				try {
					response2.close();
				} catch (IOException e) {
					HLogger.Error(e);
				}
			}
		} catch (Exception e) {
			HLogger.Error(e);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				HLogger.Error(e);
			}
			//6Add start huangpeng KYEEAPPMAINTENANCE-1162 2016年12月13日 10:20:36
			recordMonitor(url, "", loc, isSuccess, monitor_process_id,
					HOSPITAL_ID, null, result, postParams);
			//Add end huangpeng KYEEAPPMAINTENANCE-1162 2016年12月13日 10:20:40
		}
		return result;
	}
	/**
	 * 描述: 原始post请求对外接口 创建人: 荣昌 当url长度过长时已放入参数的形式进行post请求
	 * 
	 * @param url
	 * @param method
	 * @param loc
	 * @param params
	 * @param hosId
	 * @return
	 * @throws Exception
	 */
	public static String HttpPost(String url, String suffix, String method,
			Map<String, String> params) throws BaseBllException {
		//Add start huangpeng KYEEAPPMAINTENANCE-1162 2016年12月12日 20:26:34
		boolean isSuccess = false;
        String monitor_process_id = "";
		boolean isMonitorUser = false;
		String postParams = "";
		String loc = "";
		//Add end huangpeng KYEEAPPMAINTENANCE-1162 2016年12月12日 20:26:38
		String result = "";
		if (DotNetToJavaStringHelper.isNullOrEmpty(url)) {
			throw new BaseBllException("未配置URL连接");
		}
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		// 拼接url、方法、参数
		if (url.startsWith("http:") || url.startsWith("https:")) {
			url = url + suffix + "?op=" + method;
		} else {
			url = "http://" + url + suffix + "?op=" + method;
		}

		Iterator<Entry<String, String>> iter = params.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) iter
					.next();
			//Add start huangpeng KYEEAPPMAINTENANCE-1162 2016年12月12日 20:33:30
			if(entry.getKey().toLowerCase().equals("loc")){
				loc = entry.getValue();
			}
			//Add end huangpeng KYEEAPPMAINTENANCE-1162 2016年12月12日 20:33:34
			nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
	    CloseableHttpClient httpClient = createHttpClient(url);
		try {
			//Add start huangpeng KYEEAPPMAINTENANCE-1162 2016年12月12日 20:15:17
			/*isMonitorUser = UserProcessMonitorUtil.getIsMonitorUser();
			if (isMonitorUser) {
				monitor_process_id = TimeStampRadomUtil.GetARadomValue();
			}*/
			//Add end huangpeng KYEEAPPMAINTENANCE-1162 2016年12月12日 20:15:21
			HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Access-Control-Allow-Origin", "*");
            httpPost.addHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, Accept,X-Requested-With");
     
            //Add start huangpeng KYEEAPPMAINTENANCE-1162 2016年12月13日 09:32:05
            postParams = buildParamJsonStr(nvps);
            //Add end huangpeng KYEEAPPMAINTENANCE-1162 2016年12月13日 09:32:10
            // 键值对没有拼接
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
            
            
			RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
            requestConfigBuilder.setConnectionRequestTimeout(timeout)
            		.setSocketTimeout(timeout).setConnectTimeout(timeout);
	        
            RequestConfig requestConfig = ProxyClientUtil.setProxyConfig(requestConfigBuilder).build();
            httpPost.setConfig(requestConfig);

			// 键值对没有拼接

			CloseableHttpResponse response2 = httpClient.execute(httpPost);
			response2.addHeader("Access-Control-Allow-Methods",
					"POST, GET, OPTIONS");
			response2.addHeader("Access-Control-Allow-Headers",
					"Content-Type, Authorization, Accept,X-Requested-With");
			try {
				HttpEntity entity2 = response2.getEntity();
				if (response2.getStatusLine().getStatusCode() == 200) {
					result = inputStreamToString(entity2.getContent());
					//Add start huangpeng KYEEAPPMAINTENANCE-1162 2016年12月13日 10:25:29
					isSuccess = true;
					//Add end huangpeng KYEEAPPMAINTENANCE-1162 2016年12月13日 10:25:32
				} else {
					String content = inputStreamToString(response2.getEntity()
							.getContent());
					HLogger.error(content);
				}
			} catch (IOException e) {
				HLogger.Error(e);
			} finally {
				try {
					response2.close();
				} catch (IOException e) {
					HLogger.Error(e);
				}
			}
		} catch (Exception e) {
			HLogger.Error(e);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				HLogger.Error(e);
			}
			//Add start huangpeng KYEEAPPMAINTENANCE-1162 2016年12月13日 10:20:36
			recordMonitor(url, method, loc, isSuccess, monitor_process_id,
					HOSPITAL_ID, null, result, postParams);
			//Add end huangpeng KYEEAPPMAINTENANCE-1162 2016年12月13日 10:20:40
		}
		return result;
	}

	/**
	 * 描述: 发送xml请求 创建人: 李智博
	 * 
	 * @param url
	 * @param xml格式
	 * @return
	 * @throws Exception
	 */
	public static String xmlHttpPost(String url, String xmlData)
			throws BaseBllException {
		// 创建httpClients，模拟发送http请求
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String result = "";
		if (DotNetToJavaStringHelper.isNullOrEmpty(url)) {
			throw new BaseBllException("未配置URL连接");
		}
		// 拼接url、方法、参数
		url = "http://" + url + "?postData=" + URLEncoder.encode(xmlData);
		try {
			HttpPost httpPost = new HttpPost(url);
			httpPost.addHeader("Access-Control-Allow-Origin", "*");
			httpPost.addHeader("Access-Control-Allow-Headers",
					"Content-Type, Authorization, Accept,X-Requested-With");
			httpPost.addHeader("Content-Type", "text/xml");

			// httpPost.setEntity(new StringEntity(nvps, Consts.UTF_8));
			CloseableHttpResponse response2 = httpClient.execute(httpPost);
			response2.addHeader("Access-Control-Allow-Methods",
					"POST, GET, OPTIONS");
			response2.addHeader("Access-Control-Allow-Headers",
					"Content-Type, Authorization, Accept,X-Requested-With");
			try {
				HttpEntity entity2 = response2.getEntity();
				if (response2.getStatusLine().getStatusCode() == 200) {
					result = inputStreamToString(entity2.getContent());
				} else {
					String content = inputStreamToString(response2.getEntity()
							.getContent());
					HLogger.error(content);
				}
			} catch (IOException e) {
				HLogger.Error(e);
			} finally {
				try {
					response2.close();
				} catch (IOException e) {
					HLogger.Error(e);
				}
			}
		} catch (Exception e) {
			HLogger.Error(e);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				HLogger.Error(e);
			}
		}
		return result;
	}

	/**
	 * 描述: 发送json请求 创建人: 荣昌
	 * 
	 * @param url
	 * @param 增加json格式的传入参数
	 * @return
	 * @throws BaseBllException 
	 * @throws Exception
	 */
	public String jsonHttpPost(String url, String json) throws BaseBllException {
		// Add start huangpeng KYEEAPPMAINTENANCE-1162 2016年12月12日 20:26:34
		boolean isSuccess = false;
		String monitor_process_id = "";
		boolean isMonitorUser = false;
		String postParams = "";
	/*	isMonitorUser = UserProcessMonitorUtil.getIsMonitorUser();
		if (isMonitorUser) {
			monitor_process_id = TimeStampRadomUtil.GetARadomValue();
		}*/
        postParams = json;
		// Add end huangpeng KYEEAPPMAINTENANCE-1162 2016年12月12日 20:26:38
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		StringEntity myEntity = new StringEntity(json,
				ContentType.APPLICATION_JSON);// 构造请求数据
		post.setEntity(myEntity);// 设置请求体
		String responseContent = null; // 响应内容
		CloseableHttpResponse response = null;
		try {
			response = client.execute(post);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				responseContent = EntityUtils.toString(entity, "UTF-8");
				isSuccess = true;
			}
		} catch (Exception e) {
			HLogger.Error(e);
		} finally {
			try {
				if (response != null)
					response.close();
			} catch (IOException e) {
				HLogger.Error(e);
			}
			//Add start huangpeng KYEEAPPMAINTENANCE-1162 2016年12月16日 10:20:36
			recordMonitor(url, "", "", isSuccess, monitor_process_id,
					HOSPITAL_ID, null, responseContent, postParams);
			//Add end huangpeng KYEEAPPMAINTENANCE-1162 2016年12月16日 10:20:40
		}
		return responseContent;

	}

	/**
	 * 描述: 封装后的post对外接口<br/>
	 * 创建人: ypf <br/>
	 * 
	 * @param url
	 * @param method
	 * @param loc
	 * @param params
	 * @param hosId
	 * @return
	 * @throws Exception
	 * @since Ver 1.1 修改人：幺鹏飞 修改时间：2014-10-14 10:56:52 任务号：KYEEAPP-893
	 */
	public static String post(String url, String method, String loc,
			Map<String, String> params, IDataBase appDb, int... hosId)
			throws BaseBllException {
		String returnRestult = null;
		return returnRestult;
	}

	/**
	 * @param url
	 *            请求的url,例如:usermanager/action/UserAction
	 * @param method
	 *            请求的方法名,例如:login
	 * @param loc
	 *            请求服务器定位,可选值:"c":clound(云服务器程序),"f":front(前置服务器程序),"t":terminal
	 *            (端服务器程序)
	 * @param params
	 *            请求方法所需参数列表,以Map方式存储
	 * @return 返回结果字符串
	 * @throws BaseBllException
	 *             <pre>
	 * Map&lt;String, String&gt; map = new HashMap&lt;String, String&gt;();
	 * map.put(&quot;username&quot;, &quot;test&quot;);
	 * map.put(&quot;password&quot;, &quot;123456&quot;);
	 * String result = HttpClientUtil.post(&quot;usermanager/action/UserAction&quot;, &quot;login&quot;,
	 * 		&quot;c&quot;, map);
	 * </pre>
	 */
	public static String post(String url, String method, String loc,
			Map<String, String> params, int... hosId) throws BaseBllException {
		boolean isSuccess = false;
        String monitor_process_id = "";
		boolean isMonitorUser = false;
		String postParams = "";
		
		getOpNameAndId(url, method, loc, hosId);

		C_SYSTEM_HOSPITAL_EXT hos = null;
		if ("t".equals(loc)) {
			Map<String, C_SYSTEM_HOSPITAL_EXT> hosMap = (Map<String, C_SYSTEM_HOSPITAL_EXT>) Application
					.get("hosMap");
			if (hosMap.get(hosId[0] + "") != null
					&& !(hosMap.get(hosId[0] + "").equals(""))) {
				hos = (C_SYSTEM_HOSPITAL_EXT) hosMap.get((hosId[0] + "")
						.toUpperCase());
			} else {
				throw new BaseBllException("医院ID为空！");
			}
			// hos = (C_SYSTEM_HOSPITAL_EXT) hosMap.get((hosId[0] + "")
			// .toUpperCase());
		} else if ("f".equals(loc)) {
			String front_add = params.get("FRONT_ADD");
			hos = new C_SYSTEM_HOSPITAL_EXT();
			hos.setFRONT_ADD(front_add);
		} else if ("c".equals(loc)) {
			hos = new C_SYSTEM_HOSPITAL_EXT();
			String hid = params.get("hospitalID");
			Long hospitalid = Long.parseLong(hid);
			hos.setHOSPITAL_ID(hospitalid);
		}
		String suffix = SystemParams.getParamaValue("SUFFIX").toLowerCase();
		String clouddrr = SystemParams.getParamaValue("SERVER_LOCATION_CLOUD");
		/* .toLowerCase(); */
		String result = "";
		if (!"c".equals(loc)) {
			if (DotNetToJavaStringHelper.isNullOrEmpty(suffix)) {
				throw new BaseBllException("未正确配置请求路径后缀名！");
//			}
//			if (hos == null || "".equals(hos.getFRONT_ADD())) {
//				throw new BaseBllException("未正确配置前置服务器地址！");
			} else if (hos == null || "".equals(hos.getTERMAIL_ADD())) {
				throw new BaseBllException("未正确配置端服务器地址！");
			}
		}
		String hosMod = hos.getPATTERN();
		if ("f".equals(loc)) {
			if ("1".equals(hosMod)) {
				url = "http://" + hos.getFRONT_ADD() + "/straight/" + url + "."
						+ suffix + "?op=" + method + "&loc=" + loc;
			} else if ("2".equals(hosMod)) {
				url = "http://" + hos.getFRONT_ADD() + "/file/" + url + "."
						+ suffix + "?op=" + method + "&loc=" + loc;
			} else if ("3".equals(hosMod)) {
				url = "http://" + hos.getFRONT_ADD() + "/brake/" + url + "."
						+ suffix + "?op=" + method + "&loc=" + loc;
			} else {
				url = "http://" + hos.getFRONT_ADD() + "/straight/" + url + "."
						+ suffix + "?op=" + method + "&loc=" + loc;
			}
		} else if ("t".equals(loc)) {
			if (hos.getTERMAIL_ADD().startsWith("https://")) {
				if ("1".equals(hosMod)) {
					url = hos.getTERMAIL_ADD() + "/straight/" + url + "."
							+ suffix + "?op=" + method + "&loc=" + loc;
				} else if ("2".equals(hosMod)) {
					url = hos.getTERMAIL_ADD() + "/file/" + url + "." + suffix
							+ "?op=" + method + "&loc=" + loc;
				} else if ("3".equals(hosMod)) {
					url = hos.getTERMAIL_ADD() + "/brake/" + url + "." + suffix
							+ "?op=" + method + "&loc=" + loc;
				} else {
					url = hos.getTERMAIL_ADD() + "/straight/" + url + "."
							+ suffix + "?op=" + method + "&loc=" + loc;
				}
			} else {
				if ("1".equals(hosMod)) {
					url = "http://" + hos.getTERMAIL_ADD() + "/straight/" + url
							+ "." + suffix + "?op=" + method + "&loc=" + loc;
				} else if ("2".equals(hosMod)) {
					url = "http://" + hos.getTERMAIL_ADD() + "/file/" + url
							+ "." + suffix + "?op=" + method + "&loc=" + loc;
				} else if ("3".equals(hosMod)) {
					url = "http://" + hos.getTERMAIL_ADD() + "/brake/" + url
							+ "." + suffix + "?op=" + method + "&loc=" + loc;
				} else {
					url = "http://" + hos.getTERMAIL_ADD() + "/straight/" + url
							+ "." + suffix + "?op=" + method + "&loc=" + loc;
				}
			}
		} else if ("c".equals(loc)) {
			url = clouddrr + "/" + url + "." + suffix + "?op=" + method
					+ "&loc=" + loc;
		} else {
			throw new BaseBllException("未能匹配正确的服务器！");
		}
		if (url.startsWith("https://")) {
			CloseableHttpClient httpClient = null;
			try {
				//Add start huangpeng KYEEAPPMAINTENANCE-1162 2016年12月12日 20:15:17
				/*isMonitorUser = UserProcessMonitorUtil.getIsMonitorUser();
				if (isMonitorUser) {
					monitor_process_id = TimeStampRadomUtil.GetARadomValue();
				}*/
				//Add end huangpeng KYEEAPPMAINTENANCE-1162 2016年12月12日 20:15:21
				HttpPost httpPost = new HttpPost(url);
				try {
					httpClient = SSLClientUtil.getTerminalHttpsClient();
				} catch (Exception e) {
					HLogger.error(e);
					throw new BaseBllException("加载SSL证书失败！");
				}
				httpPost.addHeader("Access-Control-Allow-Origin", "*");
				httpPost.addHeader("Access-Control-Allow-Headers",
						"Content-Type, Authorization, Accept,X-Requested-With");
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				if (params != null) {
					for (String key : params.keySet()) {
						nvps.add(new BasicNameValuePair(key, params.get(key)));
						// 做参数
						if ("t".equals(loc)) {
							recoderTParameter(url, method, key, params.get(key));
						}
					}
				}
				if ("t".equals(loc)) {
					nvps.add(new BasicNameValuePair("FRONT_ADD", hos
							.getFRONT_ADD()));
					/**
					 * 修改人：么鹏飞 修改时间：2014.11.14 09:08 任务号：KYEEAPP-988
					 * 云往端发的post请求中加入医院ID的参数，以便在端上拿到医院ID，对单端表中没医院ID历史数据进行处理，
					 * 业务也可拿到进行数据的操作，
					 */
					nvps.add(new BasicNameValuePair("HOSPITAL_ID", hos.GetId()));
				}
				//Add start KYEEAPPC-2908 用户操作记录 2015年8月6日16:29:15
	            postParams = buildParamJsonStr(nvps);
	            //Add end KYEEAPPC-2908 用户操作记录 2015年8月6日16:29:15
				httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
				CloseableHttpResponse response2 = httpClient.execute(httpPost);
				response2.addHeader("Access-Control-Allow-Methods",
						"POST, GET, OPTIONS");
				response2.addHeader("Access-Control-Allow-Headers",
						"Content-Type, Authorization, Accept,X-Requested-With");
				try {
					HttpEntity entity2 = response2.getEntity();
					if (response2.getStatusLine().getStatusCode() == 200) {
						result = inputStreamToString(entity2.getContent());
						isSuccess=true;
					} else {
						// Edit begin 党智康 2014年11月20日 10:17:07
						// 获取异常信息，并封装为异常对象
						// 任务：KYEEAPP-896
						String content = inputStreamToString(response2
								.getEntity().getContent());
						HLogger.error(content);
						throw new BaseBllException(ErrInfoUtil.doMapToJson(
								OP_NAME, getErrType(content), content, "3",
								HOSPITAL_ID));
					}
				} catch (IllegalStateException e) {
					HLogger.Error(e);
					throw new BaseBllException(ErrInfoUtil.doMapToJson(OP_NAME,
							e.getMessage(), ErrInfoUtil.getErrInfo(e), "3",
							HOSPITAL_ID));
				} catch (IOException e) {
					HLogger.Error(e);
					throw new BaseBllException(ErrInfoUtil.doMapToJson(OP_NAME,
							e.getMessage(), ErrInfoUtil.getErrInfo(e), "3",
							HOSPITAL_ID));
				} finally {
					try {
						response2.close();
					} catch (IOException e) {
						HLogger.Error(e);
						throw new BaseBllException(ErrInfoUtil.doMapToJson(
								OP_NAME, e.getMessage(),
								ErrInfoUtil.getErrInfo(e), "3", HOSPITAL_ID));
					}
					recordMonitor(url, method, loc, isSuccess, monitor_process_id,
							HOSPITAL_ID, null, result, postParams);
				}
			} catch (UnsupportedEncodingException e) {
				HLogger.Error(e);
				throw new BaseBllException(ErrInfoUtil.doMapToJson(OP_NAME,
						e.getMessage(), ErrInfoUtil.getErrInfo(e), "3",
						HOSPITAL_ID));
			} catch (ClientProtocolException e) {
				HLogger.Error(e);
				throw new BaseBllException(ErrInfoUtil.doMapToJson(OP_NAME,
						e.getMessage(), ErrInfoUtil.getErrInfo(e), "1",
						HOSPITAL_ID));
			} catch (IOException e) {
				HLogger.Error(e);
				throw new BaseBllException(ErrInfoUtil.doMapToJson(OP_NAME,
						e.getMessage(), ErrInfoUtil.getErrInfo(e), "2",
						HOSPITAL_ID));
			} finally {
				try {
					httpClient.close();
				} catch (IOException e) {
					HLogger.Error(e);
					throw new BaseBllException(ErrInfoUtil.doMapToJson(OP_NAME,
							e.getMessage(), ErrInfoUtil.getErrInfo(e), "3",
							HOSPITAL_ID));
					// Edit end 党智康 2014年11月20日 10:19:39
				}
				recordMonitor(url, method, loc, isSuccess, monitor_process_id,
						HOSPITAL_ID, null, result, postParams);
			}
		} else {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			try {
				//Add start huangpeng KYEEAPPMAINTENANCE-1162 2016年12月12日 20:15:17
				/*isMonitorUser = UserProcessMonitorUtil.getIsMonitorUser();
				if (isMonitorUser) {
					monitor_process_id = TimeStampRadomUtil.GetARadomValue();
				}*/
				//Add end huangpeng KYEEAPPMAINTENANCE-1162 2016年12月12日 20:15:21
				HttpPost httpPost = new HttpPost(url);
				httpPost.addHeader("Access-Control-Allow-Origin", "*");
				httpPost.addHeader("Access-Control-Allow-Headers",
						"Content-Type, Authorization, Accept,X-Requested-With");
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				if (params != null) {
					for (String key : params.keySet()) {
						nvps.add(new BasicNameValuePair(key, params.get(key)));
						// 做参数
						if ("t".equals(loc)) {
							recoderTParameter(url, method, key, params.get(key));
						}
					}
				}
				if ("t".equals(loc)) {
					nvps.add(new BasicNameValuePair("FRONT_ADD", hos
							.getFRONT_ADD()));
					/**
					 * 修改人：么鹏飞 修改时间：2014.11.14 09:08 任务号：KYEEAPP-988
					 * 云往端发的post请求中加入医院ID的参数，以便在端上拿到医院ID，对单端表中没医院ID历史数据进行处理，
					 * 业务也可拿到进行数据的操作，
					 */
					nvps.add(new BasicNameValuePair("HOSPITAL_ID", hos.GetId()));
				}
				//Add start KYEEAPPC-2908 用户操作记录 2015年8月6日16:29:15
	            postParams = buildParamJsonStr(nvps);
	            //Add end KYEEAPPC-2908 用户操作记录 2015年8月6日16:29:15
				httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
				CloseableHttpResponse response2 = httpClient.execute(httpPost);
				response2.addHeader("Access-Control-Allow-Methods",
						"POST, GET, OPTIONS");
				response2.addHeader("Access-Control-Allow-Headers",
						"Content-Type, Authorization, Accept,X-Requested-With");
				try {
					HttpEntity entity2 = response2.getEntity();
					if (response2.getStatusLine().getStatusCode() == 200) {
						result = inputStreamToString(entity2.getContent());
						isSuccess=true;
					} else {
						// Edit begin 党智康 2014年11月20日 10:17:07
						// 获取异常信息，并封装为异常对象
						// 任务：KYEEAPP-896
						String content = inputStreamToString(response2
								.getEntity().getContent());
						HLogger.error(content);
						throw new BaseBllException(ErrInfoUtil.doMapToJson(
								OP_NAME, getErrType(content), content, "3",
								HOSPITAL_ID));
					}
				} catch (IllegalStateException e) {
					HLogger.Error(e);
					throw new BaseBllException(ErrInfoUtil.doMapToJson(OP_NAME,
							e.getMessage(), ErrInfoUtil.getErrInfo(e), "3",
							HOSPITAL_ID));
				} catch (IOException e) {
					HLogger.Error(e);
					throw new BaseBllException(ErrInfoUtil.doMapToJson(OP_NAME,
							e.getMessage(), ErrInfoUtil.getErrInfo(e), "3",
							HOSPITAL_ID));
				} finally {
					try {
						response2.close();
					} catch (IOException e) {
						HLogger.Error(e);
						throw new BaseBllException(ErrInfoUtil.doMapToJson(
								OP_NAME, e.getMessage(),
								ErrInfoUtil.getErrInfo(e), "3", HOSPITAL_ID));
					}
					recordMonitor(url, method, loc, isSuccess, monitor_process_id,
							 HOSPITAL_ID, null, result, postParams);
				}
			} catch (UnsupportedEncodingException e) {
				HLogger.Error(e);
				throw new BaseBllException(ErrInfoUtil.doMapToJson(OP_NAME,
						e.getMessage(), ErrInfoUtil.getErrInfo(e), "3",
						HOSPITAL_ID));
			} catch (ClientProtocolException e) {
				HLogger.Error(e);
				throw new BaseBllException(ErrInfoUtil.doMapToJson(OP_NAME,
						e.getMessage(), ErrInfoUtil.getErrInfo(e), "1",
						HOSPITAL_ID));
			} catch (IOException e) {
				HLogger.Error(e);
				throw new BaseBllException(ErrInfoUtil.doMapToJson(OP_NAME,
						e.getMessage(), ErrInfoUtil.getErrInfo(e), "2",
						HOSPITAL_ID));
			} finally {
				try {
					httpClient.close();
				} catch (IOException e) {
					HLogger.Error(e);
					throw new BaseBllException(ErrInfoUtil.doMapToJson(OP_NAME,
							e.getMessage(), ErrInfoUtil.getErrInfo(e), "3",
							HOSPITAL_ID));
					// Edit end 党智康 2014年11月20日 10:19:39
				}
				recordMonitor(url, method, loc, isSuccess, monitor_process_id,
						HOSPITAL_ID, null, result, postParams);
			}
		}
		return result;
	}

	/**
	 * 描述: 获取接口信息和医院ID<br/>
	 * 创建人: 党智康 <br/>
	 * 创建时间: 2014年11月19日 10:48:41 任务：KYEEAPP-896
	 * 
	 * @param url
	 * @param method
	 * @param loc
	 * @param hosId
	 * @since Ver 1.1
	 */
	private static void getOpNameAndId(String url, String method, String loc,
			int... hosId) {
		if ("c".equals(loc)) {
			HOSPITAL_ID = "0";
		} else {
			HOSPITAL_ID = String.valueOf(hosId[0]);
		}
		OP_NAME = url + "." + method;
	}

	/**
	 * 描述: 取到num层的堆栈方法的全路径名称 创建人: ypf
	 * 
	 * @param num
	 * @return
	 * @since Ver 1.1
	 */
	private static String getBllName(int num) {
		Thread thread = Thread.currentThread();
		StackTraceElement[] nowStack = thread.getStackTrace();
		return nowStack[num].getClassName() + "."
				+ nowStack[num].getMethodName();
	}

	/**
	 * 描述: 记录云到端op参数信息的方法<br/>
	 * 创建人: ypf<br/>
	 * 
	 * @since Ver 1.1
	 */
	private static void recoderTParameter(String TopUrl, String TMethod,
			String parameterName, String parameterValue) {
		// 去缓存中取开关的值，在app到云时已对开关值做了处理，此处必定会拿到值
		String paramsOnOff = BaseBLL.paramsOnOff;
		// 当为1时为记录参数
		/**
		 * 描述：修改存储接口信息的bll层 修改人：党智康 修改时间：2014年11月18日 11:34 任务单号：KYEEAPP-1019
		 */
		if ("1".equals(paramsOnOff)) {
			String bllName = getBllName(5);
			// 对url进行处理，取到端上op的全路径
			String url = TopUrl.substring(TopUrl.indexOf("APP"),
					TopUrl.indexOf("jspx") - 1);
			try {
				// 判断value值是否含有json字符串，如果有，则记下它的key值。
				parameterName = new HttpClientUtil().getParameterValueKey(
						parameterName, parameterValue);
				// Edit begin 党智康 2014年11月25日 16:02:16
				// 记录接口参数列表信息中加入版本号
				// 任务：KYEEAPP-1019
				// RecordOpInfoBll recordOpInfoBll = new RecordOpInfoBll();
				// String version = recordOpInfoBll.getVersionInfo();
				String version = SystemParams
						.getParamaValue("WEB_BUILDER_VERSION_APP");
				// recordOpInfoBll.recoderOPInfo(bllName + "---->" + url + "."
				// + TMethod, parameterName, version);
				// Edit end
			} catch (BaseBllException e) {
				HLogger.Error(e);
				e.printStackTrace();
			}
		}
	}

	/**
	 * 描述: 判断value值是否含有json字符串，集合类等,如果有，则存下value<br/>
	 * 创建人: 党智康 <br/>
	 * 创建时间：2014年10月22日 上午10:53:44 <br/>
	 * 任务号：KYEEAPP-894 修改人：党智康 修改时间：2014年11月5日 下午20:23:35 <br/>
	 * 修改描述：未进行字符串校验 任务号：KYEEAPPTEST-725
	 * 
	 * @param value
	 * @return
	 * @since Ver 1.1
	 */
	private String getParameterValueKey(String paraName, String value)
			throws BaseBllException {
		// value中有可能会含有json格式的字符串
		if (!DotNetToJavaStringHelper.isNullOrEmpty(value)) {
			// value是json格式的
			// {"DEPT_CODE":"\u6cd5\u533b","LOC_INFO":"","MARK_DESC":"\u95eb\u4e13\u5bb6\u4e13\u5bb6\u53f7","CLINIC_TYPE":"\u4e13\u5bb6","DOCTOR_CODE":"5305","DOCTOR_NAME":"\u95eb\u4e13\u5bb6","DEPT_NAME":"\u6cd5\u533b","REG_DATE":"2014/12/10","CLINIC_DURATION":"\u4e0a\u5348","PHONE_NUMBER":"578","HID":"201404305305001","HID_FLAG":"1","AMOUNT_TEXT":"\uffe54","AMOUNT":"4.0","APPT_MADE_DATE":"2014/12/10","PATIENT_NAME":"\u4f53\u9a8c\u5c31\u8bca\u8005","ID_NO":"696165199101173045","PATIENT_ID":"25754627"}
			if (value.matches("[{][\\w\\W]*[}]"))// 判断参数值里面是否有json类的字符串等
			{
				// edit start KYEEAPP-1070 如果value是json字符串,则循环解析 houhy
				// 2014年12月11日 11:31:18
				String valueParam = ",";
				try {
					valueParam += getJsonStrAllParams(value);
					if (",".equals(valueParam))
						valueParam = "";
					// System.out.println(dataJson.keys());
				} catch (BaseBllException e) {
					HLogger.info("参数监控,解析json字符串异常:" + e);
				}
				// return paraName + "," + value;
				return paraName + valueParam;
				// edit end KYEEAPP-1070 如果value是json字符串,则循环解析 houhy 2014年12月11日
				// 11:31:18
			} else {
				return paraName;
			}
		} else {
			return paraName;
		}
	}

	/**
	 * <pre>
	 * 任务:KYEEAPP-1070
	 * 描述:获取json字符串参数
	 * 作者:houhy 
	 * 时间:2014年12月11日上午11:41:54
	 * @param jsonStr
	 * @return
	 * returnType：String
	 * </pre>
	 */
	public String getJsonStrAllParams(String jsonStr) throws BaseBllException {

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
					valueParam = valueParam.substring(0,
							valueParam.lastIndexOf(","));
				}
				// System.out.println(dataJson.keys());
			} catch (JSONException e) {
				throw new BaseBllException("参数监控时，解析json出错:" + e);
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
	 * @param jsonStr
	 * @return
	 * @throws BaseBllException
	 * returnType：String
	 * </pre>
	 */
	public ArrayList<String> getJsonStrAllChildParams(String jsonStr)
			throws BaseBllException {

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

			} catch (JSONException e) {
				throw new BaseBllException("参数监控时，解析json出错:" + e);
			}
		}

		return al;
	}

	/**
	 * 描述: 从响应信息中获得异常类型信息<br/>
	 * 创建人: 党智康 <br/>
	 * 任务：KYEEAPP-896
	 * 
	 * @param content
	 * @return
	 * @since Ver 1.1
	 */
	private static String getErrType(String content) {
		int start = content.indexOf("<h1>");
		int end = content.indexOf("</h1>");
		return content.substring(start + 18, end + 1);
	}

	/* 发送 https请求 */
	public static String origHttpsPost(String url, String suffix,
			String method, Map<String, String> params) throws BaseBllException {
		//1. Add start huangpeng KYEEAPPMAINTENANCE-1162 2016年12月12日 20:26:34
		boolean isSuccess = false;
		String monitor_process_id = "";
		boolean isMonitorUser = false;
		String postParams = "";
		String loc = "";
		// Add end huangpeng KYEEAPPMAINTENANCE-1162 2016年12月12日 20:26:38
		String result = "";
		if (DotNetToJavaStringHelper.isNullOrEmpty(url)) {
			throw new BaseBllException("未配置URL连接");
		}
		// 拼接url、方法、参数
		url = url + suffix + "?op=" + method;
		Iterator<Entry<String, String>> iter = params.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) iter
					.next();
			//2. Add start huangpeng KYEEAPPMAINTENANCE-1162 2016年12月12日 20:33:30
			if (entry.getKey().toLowerCase().equals("loc")) {
				loc = entry.getValue();
			}
			// Add end huangpeng KYEEAPPMAINTENANCE-1162 2016年12月12日 20:33:34
			url = url + "&" + entry.getKey() + "=" + entry.getValue();
		}
	    CloseableHttpClient httpClient = createHttpClient(url);
		try {
			//3. Add start huangpeng KYEEAPPMAINTENANCE-1162 2016年12月12日 20:15:17
			/*isMonitorUser = UserProcessMonitorUtil.getIsMonitorUser();
			if (isMonitorUser) {
				monitor_process_id = TimeStampRadomUtil.GetARadomValue();
			}*/
			//Add end huangpeng KYEEAPPMAINTENANCE-1162 2016年12月12日 20:15:21
			HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Access-Control-Allow-Origin", "*");
            httpPost.addHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, Accept,X-Requested-With");
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            //4. Add start huangpeng KYEEAPPMAINTENANCE-1162 2016年12月13日 09:32:05
            if (params != null) {
				for (String key : params.keySet()) {
					nvps.add(new BasicNameValuePair(key, params.get(key)));
					// 做参数
					if ("t".equals(loc)) {
						recoderTParameter(url, method, key, params.get(key));
					}
				}
			}
            postParams = buildParamJsonStr(nvps);
            //Add end huangpeng KYEEAPPMAINTENANCE-1162 2016年12月13日 09:32:10
            // 键值对没有拼接
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
            
            
			RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
            requestConfigBuilder.setConnectionRequestTimeout(timeout)
            		.setSocketTimeout(timeout).setConnectTimeout(timeout);
	        
            RequestConfig requestConfig = ProxyClientUtil.setProxyConfig(requestConfigBuilder).build();
            httpPost.setConfig(requestConfig);
		
			CloseableHttpResponse response2 = httpClient.execute(httpPost);
			response2.addHeader("Access-Control-Allow-Methods",
					"POST, GET, OPTIONS");
			response2.addHeader("Access-Control-Allow-Headers",
					"Content-Type, Authorization, Accept,X-Requested-With");
			try {
				HttpEntity entity2 = response2.getEntity();
				if (response2.getStatusLine().getStatusCode() == 200) {
					result = inputStreamToString(entity2.getContent());
					//5. Add start huangpeng KYEEAPPMAINTENANCE-1162 2016年12月13日 10:25:29
					isSuccess = true;
					//Add end huangpeng KYEEAPPMAINTENANCE-1162 2016年12月13日 10:25:32
				} else {
					String content = inputStreamToString(response2.getEntity()
							.getContent());
					HLogger.error(content);
				}
				/*
				 * } else if (response2.getStatusLine().getStatusCode()==500) {
				 * String content =
				 * inputStreamToString(response2.getEntity().getContent());
				 * throw new BaseBllException("网络异常！"); } else { String content
				 * = inputStreamToString(response2.getEntity().getContent());
				 * throw new BaseBllException(content); }
				 */
			} catch (IllegalStateException e) {
				HLogger.Error("返回编码"+response2.getStatusLine().getStatusCode());
				HLogger.Error(e);

			} catch (SocketTimeoutException e) {
				HLogger.Error("返回编码"+response2.getStatusLine().getStatusCode());
				HLogger.error("请求链接超时：" + e);

			} catch (ConnectException e) {
				HLogger.Error("返回编码"+response2.getStatusLine().getStatusCode());
				HLogger.error(e);

			} catch (IOException e) {
				HLogger.Error("返回编码"+response2.getStatusLine().getStatusCode());
				HLogger.Error(e);

			} finally {
				try {
					response2.close();
				} catch (IOException e) {
					HLogger.Error(e);

				}
			}
		} catch (Exception e) {
			HLogger.Error(e);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				HLogger.Error(e);

				// throw new BaseBllException(e);
				// Edit end 党智康 2014年11月20日 10:19:39
			}
			//6. Add start huangpeng KYEEAPPMAINTENANCE-1162 2016年12月13日 10:20:36
			recordMonitor(url, method, loc, isSuccess, monitor_process_id,
					HOSPITAL_ID, null, result, postParams);
			//Add end huangpeng KYEEAPPMAINTENANCE-1162 2016年12月13日 10:20:40
		}
		return result;
	}
	
	
	private static CloseableHttpClient createHttpClient(String url)
			throws BaseBllException {
		CloseableHttpClient httpClient = null;
		C_SYSTEM_HOSPITAL_EXT hospitalEntity = new C_SYSTEM_HOSPITAL_EXT();
    	hospitalEntity.setTERMAIL_ADD(url);
    	return createHttpClient("t", httpClient, hospitalEntity);
	}
	
	private static CloseableHttpClient createHttpClient(String loc,
			CloseableHttpClient httpClient, C_SYSTEM_HOSPITAL_EXT hos)
			throws BaseBllException {
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        if("t".equals(loc) && hos!=null && isHospitalUseHttps(hos)){
			try{
				SSLClientUtil.setSSLSocketBuild(httpClientBuilder);
			}catch (Exception e) {
				HLogger.error(e);
			}
		}else // 缺省使用http
		{
		}
        //设置代理
        ProxyClientUtil.setProxyClientBuild(httpClientBuilder);
        
        //KYEEAPPC-4591 设置socket.buffer为20K
        ConnectionConfig.Builder connConfigBuilder = ConnectionConfig.custom();
        connConfigBuilder.setBufferSize(20 * 1024);
        httpClientBuilder.setDefaultConnectionConfig(connConfigBuilder.build());
        
        //KYEEAPPC-4617 设置SoTimeout
        SocketConfig.Builder socketConfigBuilder = SocketConfig.custom();
        socketConfigBuilder.setSoTimeout(timeout);
        httpClientBuilder.setDefaultSocketConfig(socketConfigBuilder.build());
        
        httpClient = httpClientBuilder.build();
		return httpClient;
	}
	
	/**
	 * 任务:KYEEAPPC-3394
	 * 描述:判断医院是否使用https
	 * 作者:李君强
	 * 日期:2015年9月27日21:04:31
	 */
	public static boolean isHospitalUseHttps(C_SYSTEM_HOSPITAL_EXT hos){
		String tmpTerminalAdd = hos.getTERMAIL_ADD();
		if(tmpTerminalAdd!=null){
			return tmpTerminalAdd.startsWith("https://");
		}
		return false;
	}
	//监控rop服务器接口，跳过ssl
	public static String HttpsRopPost(String url) throws BaseBllException{
		HttpPost httpPost = null;
		String result = null;
		// 拼接url、方法、参数
		url = url+"/ROP/monitor";
		CloseableHttpClient httpClient = createHttpClient(url);
		try{
			httpPost = new HttpPost(url);
            httpPost.addHeader("Access-Control-Allow-Origin", "*");
            httpPost.addHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, Accept,X-Requested-With");
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            // 键值对没有拼接
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
            
            
			RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
            requestConfigBuilder.setConnectionRequestTimeout(timeout)
            		.setSocketTimeout(timeout).setConnectTimeout(timeout);
	        
            RequestConfig requestConfig = ProxyClientUtil.setProxyConfig(requestConfigBuilder).build();
            httpPost.setConfig(requestConfig);
			HttpResponse response = httpClient.execute(httpPost);
			if(response != null){
				HttpEntity resEntity = response.getEntity();
				if(resEntity != null){
					result = EntityUtils.toString(resEntity,Consts.UTF_8);
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return result;
	}
	//rop监控特殊，特殊方法
	public static String HttpRopPost(String url) throws BaseBllException{
		String result = null;
		// 拼接url、方法、参数
		url = "http://"+url+"/ROP/monitor";
		CloseableHttpClient httpClient = createHttpClient(url);
		try{
		HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Access-Control-Allow-Origin", "*");
        httpPost.addHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, Accept,X-Requested-With");
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        // 键值对没有拼接
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
        
        
		RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
        requestConfigBuilder.setConnectionRequestTimeout(timeout)
        		.setSocketTimeout(timeout).setConnectTimeout(timeout);
        
        RequestConfig requestConfig = ProxyClientUtil.setProxyConfig(requestConfigBuilder).build();
        httpPost.setConfig(requestConfig);

		// 键值对没有拼接

		CloseableHttpResponse response2 = httpClient.execute(httpPost);
		response2.addHeader("Access-Control-Allow-Methods",
				"POST, GET, OPTIONS");
		response2.addHeader("Access-Control-Allow-Headers",
				"Content-Type, Authorization, Accept,X-Requested-With");
		try {
			HttpEntity entity2 = response2.getEntity();
			if (response2.getStatusLine().getStatusCode() == 200) {
				result = inputStreamToString(entity2.getContent());
				
			} else {
				String content = inputStreamToString(response2.getEntity()
						.getContent());
				HLogger.error(content);
			}
		} catch (IOException e) {
			HLogger.Error("返回编码"+response2.getStatusLine().getStatusCode());
			HLogger.Error(e);
		} finally {
			try {
				response2.close();
			} catch (IOException e) {
				HLogger.Error(e);
			}
		}
	} catch (Exception e) {
		
		HLogger.Error(e);
	} finally {
		try {
			httpClient.close();
		} catch (IOException e) {
			HLogger.Error(e);
		}
	}
	return result;	
			
	}
	/**
	 * 描述: 原始post的https请求对外接口 创建人: 荣昌
	 * 医院+接口无op，有token.增加只传入参数的接口，去除写死的op参数
	 * @param url
	 * @param method
	 * @param loc
	 * @param params
	 * @param hosId
	 * @return
	 * @throws Exception
	 */
	public static String origHttpsPost(String url, String suffix, Map<String, String> params) throws BaseBllException {
		//1 Add start huangpeng KYEEAPPMAINTENANCE-1162 2016年12月12日 20:26:34
		boolean isSuccess = false;
		String monitor_process_id = "";
		boolean isMonitorUser = false;
		String postParams = "";
		String loc = "";
		// Add end huangpeng KYEEAPPMAINTENANCE-1162 2016年12月12日 20:26:38
		String result = "";
		if (DotNetToJavaStringHelper.isNullOrEmpty(url)) {
			throw new BaseBllException("未配置URL连接");
		}
		// 拼接url、方法、参数
		url = url + suffix;
		if (params != null) {
			url += "?";
			Iterator<Entry<String, String>> iter = params.entrySet().iterator();
			int sum = 0;
			while (iter.hasNext()) {
				Map.Entry<String, String> entry = (Map.Entry<String, String>) iter.next();
				//2 Add start huangpeng KYEEAPPMAINTENANCE-1162 2016年12月12日 20:33:30
				if (entry.getKey().toLowerCase().equals("loc")) {
					loc = entry.getValue();
				}
				// Add end huangpeng KYEEAPPMAINTENANCE-1162 2016年12月12日 20:33:34
				if (sum != 0) {
					url = url + "&" + entry.getKey() + "=" + entry.getValue();
				} else {
					url = url + entry.getKey() + "=" + entry.getValue();
				}
				sum++;
			}
		}
		CloseableHttpClient httpClient = createHttpClient(url);
		try {
			//3 Add start huangpeng KYEEAPPMAINTENANCE-1162 2016年12月12日 20:15:17
			/*isMonitorUser = UserProcessMonitorUtil.getIsMonitorUser();
			if (isMonitorUser) {
				monitor_process_id = TimeStampRadomUtil.GetARadomValue();
			}*/
			//Add end huangpeng KYEEAPPMAINTENANCE-1162 2016年12月12日 20:15:21
			HttpPost httpPost = new HttpPost(url);
			httpPost.addHeader("Access-Control-Allow-Origin", "*");
			httpPost.addHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, Accept,X-Requested-With");
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			//4 Add start huangpeng KYEEAPPMAINTENANCE-1162 2016年12月13日 09:32:05
            if (params != null) {
				for (String key : params.keySet()) {
					nvps.add(new BasicNameValuePair(key, params.get(key)));
					// 做参数
					if ("t".equals(loc)) {
						recoderTParameter(url, "", key, params.get(key));
					}
				}
			}
            postParams = buildParamJsonStr(nvps);
            //Add end huangpeng KYEEAPPMAINTENANCE-1162 2016年12月13日 09:32:10
			// 键值对没有拼接
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));

			RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
			requestConfigBuilder.setConnectionRequestTimeout(timeout).setSocketTimeout(timeout)
					.setConnectTimeout(timeout);

			RequestConfig requestConfig = ProxyClientUtil.setProxyConfig(requestConfigBuilder).build();
			httpPost.setConfig(requestConfig);
			CloseableHttpResponse response2 = httpClient.execute(httpPost);
			response2.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
			response2.addHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, Accept,X-Requested-With");
			try {
				HttpEntity entity2 = response2.getEntity();
				if (response2.getStatusLine().getStatusCode() == 200) {
					result = inputStreamToString(entity2.getContent());
					//5 Add start huangpeng KYEEAPPMAINTENANCE-1162 2016年12月13日 10:25:29
					isSuccess = true;
					//Add end huangpeng KYEEAPPMAINTENANCE-1162 2016年12月13日 10:25:32
				} else {
					String content = inputStreamToString(response2.getEntity().getContent());
					HLogger.error(content);
				}
				/*
				 * } else if (response2.getStatusLine().getStatusCode()==500) {
				 * String content =
				 * inputStreamToString(response2.getEntity().getContent());
				 * throw new BaseBllException("网络异常！"); } else { String content
				 * = inputStreamToString(response2.getEntity().getContent());
				 * throw new BaseBllException(content); }
				 */
			} catch (IllegalStateException e) {
				HLogger.Error(e);

			} catch (SocketTimeoutException e) {
				HLogger.error("请求链接超时：" + e);

			} catch (ConnectException e) {
				HLogger.error(e);

			} catch (IOException e) {
				HLogger.Error(e);

			} finally {
				try {
					response2.close();
				} catch (IOException e) {
					HLogger.Error(e);

				}
			}
		} catch (Exception e) {
			HLogger.Error(e);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				HLogger.Error(e);

				// throw new BaseBllException(e);
				// Edit end 党智康 2014年11月20日 10:19:39
			}
			//Add start huangpeng KYEEAPPMAINTENANCE-1162 2016年12月13日 10:20:36
			recordMonitor(url, "", loc, isSuccess, monitor_process_id,
					HOSPITAL_ID, null, result, postParams);
			//Add end huangpeng KYEEAPPMAINTENANCE-1162 2016年12月13日 10:20:40
		}
		return result;
	}
	public static String HttpsPost(String url, String suffix, String method,
			Map<String, String> params) throws BaseBllException {
		//1 Add start huangpeng KYEEAPPMAINTENANCE-1162 2016年12月12日 20:26:34
		boolean isSuccess = false;
        String monitor_process_id = "";
		boolean isMonitorUser = false;
		String postParams = "";
		String loc = "";
		//Add end huangpeng KYEEAPPMAINTENANCE-1162 2016年12月12日 20:26:38
		String result = "";
		if (DotNetToJavaStringHelper.isNullOrEmpty(url)) {
			throw new BaseBllException("未配置URL连接");
		}
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		// 拼接url、方法、参数
		url =  url + suffix + "?op=" + method;
		Iterator<Entry<String, String>> iter = params.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) iter
					.next();
			//2 Add start huangpeng KYEEAPPMAINTENANCE-1162 2016年12月12日 20:33:30
			if(entry.getKey().toLowerCase().equals("loc")){
				loc = entry.getValue();
			}
			//Add end huangpeng KYEEAPPMAINTENANCE-1162 2016年12月12日 20:33:34
			nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		CloseableHttpClient httpClient = createHttpClient(url);
		try {
			//3 Add start huangpeng KYEEAPPMAINTENANCE-1162 2016年12月12日 20:15:17
			/*isMonitorUser = UserProcessMonitorUtil.getIsMonitorUser();
			if (isMonitorUser) {
				monitor_process_id = TimeStampRadomUtil.GetARadomValue();
			}*/
			//Add end huangpeng KYEEAPPMAINTENANCE-1162 2016年12月12日 20:15:21
			HttpPost httpPost = new HttpPost(url);
	        httpPost.addHeader("Access-Control-Allow-Origin", "*");
	        httpPost.addHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, Accept,X-Requested-With");
	        //4 Add start huangpeng KYEEAPPMAINTENANCE-1162 2016年12月13日 09:32:05
            postParams = buildParamJsonStr(nvps);
            //Add end huangpeng KYEEAPPMAINTENANCE-1162 2016年12月13日 09:32:10
	        // 键值对没有拼接
	        httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
	        
	        
			RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
	        requestConfigBuilder.setConnectionRequestTimeout(timeout)
	        		.setSocketTimeout(timeout).setConnectTimeout(timeout);
	        
	        RequestConfig requestConfig = ProxyClientUtil.setProxyConfig(requestConfigBuilder).build();
	        httpPost.setConfig(requestConfig);

			CloseableHttpResponse response2 = httpClient.execute(httpPost);
			response2.addHeader("Access-Control-Allow-Methods",
					"POST, GET, OPTIONS");
			response2.addHeader("Access-Control-Allow-Headers",
					"Content-Type, Authorization, Accept,X-Requested-With");
			try {
				HttpEntity entity2 = response2.getEntity();
				if (response2.getStatusLine().getStatusCode() == 200) {
					result = inputStreamToString(entity2.getContent());
					//Add start huangpeng KYEEAPPMAINTENANCE-1162 2016年12月13日 10:25:29
					isSuccess = true;
					//Add end huangpeng KYEEAPPMAINTENANCE-1162 2016年12月13日 10:25:32
				} else {
					String content = inputStreamToString(response2.getEntity()
							.getContent());
					HLogger.error(content);
				}
				/*
				 * } else if (response2.getStatusLine().getStatusCode()==500) {
				 * String content =
				 * inputStreamToString(response2.getEntity().getContent());
				 * throw new BaseBllException("网络异常！"); } else { String content
				 * = inputStreamToString(response2.getEntity().getContent());
				 * throw new BaseBllException(content); }
				 */
			} catch (IllegalStateException e) {
				HLogger.Error(e);

			} catch (SocketTimeoutException e) {
				HLogger.error("请求链接超时：" + e);

			} catch (ConnectException e) {
				HLogger.error(e);

			} catch (IOException e) {
				HLogger.Error(e);

			} finally {
				try {
					response2.close();
				} catch (IOException e) {
					HLogger.Error(e);

				}
			}
		} catch (Exception e) {
			HLogger.Error(e);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				HLogger.Error(e);

				// throw new BaseBllException(e);
				// Edit end 党智康 2014年11月20日 10:19:39
			}
			//6 Add start huangpeng KYEEAPPMAINTENANCE-1162 2016年12月13日 10:20:36
			recordMonitor(url, method, loc, isSuccess, monitor_process_id,
					HOSPITAL_ID, null, result, postParams);
			//Add end huangpeng KYEEAPPMAINTENANCE-1162 2016年12月13日 10:20:40
		}
		return result;
	}
	/**
	 * <pre>
	 * 任务： KYEEAPPMAINTENANCE-1162
	 * 描述： 
	 * 作者：huangpeng 
	 * 时间：2016年12月12日下午8:05:14
	 * @param url
	 * @param method
	 * @param loc
	 * @param isSuccess
	 * @param monitor_process_id
	 * @param hospitalID
	 * @param tVersion
	 * @param result
	 * @param postParams
	 * @throws BaseBllException
	 * returnType：void
	 * </pre>
	 */
	private static void recordMonitor(String url, String method, String loc,
			boolean isSuccess, String monitor_process_id, String hospitalID,
			String tVersion, String result, String postParams)
			throws BaseBllException {
		monitor_process_id = "";
//		UserProcessMonitorUtil.recordProcessDetailInPost(url, method, loc, hospitalID, tVersion, postParams, result,monitor_process_id);
	}
	/**
	* <pre>
	* 任务:KYEEAPPC-2908
	* 描述:用户操作监控，记录Post请求时参数
	* 作者:李君强 2015年8月6日16:47:18
	*/
    private static String buildParamJsonStr(List<NameValuePair> nvps){
    	Map<String, String> paramMap = new HashMap<String, String>();
    	for(NameValuePair nvp : nvps){
    		paramMap.put(nvp.getName(), nvp.getValue());
    	}
    	return JsonUtil.getJsonGson().toJson(paramMap);
    };
	public static String httpPostWithJSON(String url, String json) throws BaseBllException {
		// 将JSON进行UTF-8编码,以便传输中文
		// Add start huangpeng KYEEAPPMAINTENANCE-1162 2016年12月12日 20:26:34
		boolean isSuccess = false;
		String monitor_process_id = "";
		boolean isMonitorUser = false;
		String postParams = "";
		// Add end huangpeng KYEEAPPMAINTENANCE-1162 2016年12月12日 20:26:38
		String result = null;
		DefaultHttpClient httpClient = new DefaultHttpClient();
		try {
			//Add start huangpeng KYEEAPPMAINTENANCE-1162 2016年12月12日 20:15:17
		/*	isMonitorUser = UserProcessMonitorUtil.getIsMonitorUser();
			if (isMonitorUser) {
				monitor_process_id = TimeStampRadomUtil.GetARadomValue();
			}*/
			//Add end huangpeng KYEEAPPMAINTENANCE-1162 2016年12月12日 20:15:21
			
			//String encoderJson = URLEncoder.encode(json, HTTP.UTF_8);
			HttpPost httpPost = new HttpPost(url);
			//httpPost.addHeader(HTTP.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString());
//			StringEntity se = new StringEntity(encoderJson);
			//se.setContentType(CONTENT_TYPE_TEXT_JSON);
			//se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString()));
//			httpPost.setEntity(se);
			
            //Add start huangpeng KYEEAPPMAINTENANCE-1162 2016年12月13日 09:32:05
            postParams = json;
            //Add end huangpeng KYEEAPPMAINTENANCE-1162 2016年12月13日 09:32:10
			
			httpPost.addHeader("Content-type","application/json; charset=utf-8");  
			httpPost.setHeader("Accept", "application/json");  
			httpPost.setEntity(new StringEntity(json, Charset.forName("UTF-8")));  

			CloseableHttpResponse response = httpClient.execute(httpPost);
			try {
				HttpEntity entity = response.getEntity();
				int statusCode = response.getStatusLine().getStatusCode();
				if (statusCode == 404) {
					throw new Exception("请求服务异常！");
				}
				// else if (statusCode == 500) {
				// throw new Exception("请求服务内部异常！");
				// }
				else {
					result = EntityUtils.toString(entity, "UTF-8");
					isSuccess = true;
				}
			} finally {
				if (response != null) {
					response.close();
				}
			}
		} catch (SocketTimeoutException e) {
			HLogger.error("请求链接超时：" + e);
		} catch (ConnectException e) {
			HLogger.error("远程链接失败：" + e);
		} catch (Exception e) {
			HLogger.error(e);
		} finally {
			if (httpClient != null) {
				httpClient.close();
			}
			//Add start huangpeng KYEEAPPMAINTENANCE-1162 2016年12月13日 10:20:36
			recordMonitor(url, "", "", isSuccess, monitor_process_id,
					HOSPITAL_ID, null, result, postParams);
			//Add end huangpeng KYEEAPPMAINTENANCE-1162 2016年12月13日 10:20:40
		}
		return result;
	}

	/**
	 * <pre>
	 * 任务： KYEEAPPMAINTENANCE-1268
	 * 描述： 构建HTTPS客户端CloseableHttpClient
	 * 作者：huangpeng 
	 * 时间：2016年12月29日下午2:16:30
	 * @return
	 * @throws BaseBllException
	 * returnType：CloseableHttpClient
	 * </pre>
	 */
	private static CloseableHttpClient createCommonHttpsClient() throws BaseBllException {
		CloseableHttpClient httpsClient = null;
		SSLContextBuilder builder = SSLContexts.custom();
		try {
			builder.loadTrustMaterial(null, new TrustStrategy() {
				@Override
				public boolean isTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
						throws java.security.cert.CertificateException {
					return true;
				}
			});
			javax.net.ssl.SSLContext sslContext = builder.build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, new X509HostnameVerifier() {
				@Override
				public boolean verify(String arg0, SSLSession arg1) {
					return true;
				}

				@Override
				public void verify(String arg0, SSLSocket arg1) throws IOException {
				}

				@Override
				public void verify(String arg0, java.security.cert.X509Certificate arg1) throws SSLException {
				}

				@Override
				public void verify(String arg0, String[] arg1, String[] arg2) throws SSLException {
				}
			});
			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
					.register("https", sslsf).build();
			PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
			httpsClient = HttpClients.custom().setConnectionManager(cm).build();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return httpsClient;
	}
}

