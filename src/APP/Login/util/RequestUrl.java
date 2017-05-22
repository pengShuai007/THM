package APP.Login.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

public class RequestUrl {
	private static final Logger LOG = LoggerFactory.getLogger(RequestUrl.class);

	public static JSONObject excuteRequestUrl(String url) {
		JSONObject json = connectionUrl(url);
//		JSONObject json = httpPost(url);
//		JSONObject json = null;
//		try {
//			json = http(url);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return json;
	}

	private static JSONObject connectionUrl(String url){
		JSONObject json = null;
		URL getUrl = null;
		try {
			getUrl = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
			connection.connect();
			long start = System.currentTimeMillis();
			InputStream in = connection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
			long end = System.currentTimeMillis();
			String lines;
			StringBuffer resultStr = new StringBuffer();
			while(true){
				if(in.available()>0){
					if((lines = reader.readLine()) != null) {
						resultStr.append(lines);
					}else{
						break;
					}
				}else if(in.available()==0 && !resultStr.toString().contains("}")){
                    System.out.println("与服务器的连接已中断") ;
                    break ;
                }else{
                	break;
                }
			}
			reader.close();
			connection.disconnect();
			json = JSONObject.fromObject(resultStr.toString());
			json.put("RESPOND_TIME", end - start);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return json;
	}
	
	private static JSONObject httpPost(String url){
		String result = "";
		 JSONObject json = null;
		 CloseableHttpClient httpClient = HttpClients.createDefault();
		 HttpPost httpPost = new HttpPost(url);
		 httpPost.addHeader("Access-Control-Allow-Origin", "*");
		 httpPost.addHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, Accept,X-Requested-With");
		 List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		 // 键值对没有拼接
//		 for(String key : paramsMap.keySet()){
//	        	nvps.add(new BasicNameValuePair(key, paramsMap.get(key)));
//	     }
		 httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		 CloseableHttpResponse response2;
		 try {
		 long start = System.currentTimeMillis();
		 response2 = httpClient.execute(httpPost);
		 long end = System.currentTimeMillis();
		 response2.addHeader("Access-Control-Allow-Methods", "POST,GET, OPTIONS");
		 response2.addHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, Accept,X-Requested-With");
		 HttpEntity entity2 = response2.getEntity();
		 if (response2.getStatusLine().getStatusCode() == 200) {
			 result = inputStreamToString(entity2.getContent());
			 long time = end-start;
			 if(result.contains("{") && result.contains("}")){
				 json = JSONObject.fromObject(result);
				 if(json != null){
					 json.put("RESPOND_TIME", time);
				 }
			 }
		 } else {
			 String content = inputStreamToString(response2.getEntity().getContent());
			 LOG.info(content);
		 }
		 } catch (IOException e) {
		 e.printStackTrace();
		 }
		 return json;
	}
	
	private static JSONObject http(String url) throws ClientProtocolException, IOException{
		LOG.info("使用HttpPost请求~~~~~~~~~~~~~~~~~~~~~~~");
/**/	JSONObject json = null;
		String result = "";
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpClient httpClient = HttpClients.createDefault();
        httpPost.addHeader("Access-Control-Allow-Origin", "*");
        httpPost.addHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, Accept,X-Requested-With");
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        // 键值对没有拼接
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
        CloseableHttpResponse response2 = httpClient.execute(httpPost);
        response2.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        response2.addHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, Accept,X-Requested-With");
        try
        {
            HttpEntity entity2 = response2.getEntity();
            if (response2.getStatusLine().getStatusCode() == 200)
            {
                result = inputStreamToString(entity2.getContent());
/**/            json = JSONObject.fromObject(result);
            }
            else
            {
                String content = inputStreamToString(response2.getEntity().getContent());
                LOG.error(content);
            }
        }
        catch (IOException e)
        {
        	LOG.error(e.getMessage());
        }
        finally
        {
            try
            {
                response2.close();
            }
            catch (IOException e)
            {
            	LOG.error(e.getMessage());
            }
        }
        return json;
	}

	private static String inputStreamToString(InputStream in) {
		StringBuffer sb = new StringBuffer();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			String line = null;
			while((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		String content = sb.toString();
		return content;
	}

	public static String buildUrl(String url1,String url2,Map<String,String> map){
		String urlTemp = url2;
		Iterator<String> it = map.keySet().iterator(); 
        while(it.hasNext()){  
             String key=it.next().toString();     
             String value=(String) map.get(key);     
             String temp = "&"+key+"="+value;
             urlTemp += temp;
        }  
		return "http://" + SystemUtil.getURL().get("serverLocation") + url1 + urlTemp;
	}
	
	public static String buildCheckUrl(String url1,String url2,Map<String,String> map){
		String urlTemp = url2;
		Iterator<String> it = map.keySet().iterator(); 
        while(it.hasNext()){  
             String key=it.next().toString(); 
             if(!key.equals("TOKEN")){
            	 String value=(String) map.get(key);     
            	 String temp = "&"+key+"="+value;
            	 urlTemp += temp;
             }
        }
        String[] arrayUrl = urlTemp.split("&");
        List<String> list = new ArrayList<String>(Arrays.asList(arrayUrl));
        Collections.sort(list);
        String newUrl = "";
        for(int i=0;i<list.size();i++){
        	newUrl += list.get(i);
        	if(i<list.size()-1){
        		newUrl += "&";
        	}
        }
        String md5_temp = MD5.string2MD5(newUrl);
        String QY_CHECK_SUFFIX = MD5.string2MD5(md5_temp+"@@"+map.get("TOKEN"));
		return "http://" + SystemUtil.getURL().get("serverLocation") + url1 + newUrl + "&QY_CHECK_SUFFIX="+QY_CHECK_SUFFIX;
	}
}
