/**
 * 文件名:HttpRabbitMqclientUtil.java
 * 创建人:荣昌
 * 日期:2015年7月24日
 * 描述:调用RabbitMq接口方法
 */
package APP.Comm.Util;
import java.io.IOException;  
import java.util.ArrayList;  
import java.util.List;  


import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;  
import org.apache.commons.httpclient.Header;  
import org.apache.commons.httpclient.HttpClient;  
import org.apache.commons.httpclient.HttpException;  
import org.apache.commons.httpclient.HttpStatus;  
import org.apache.commons.httpclient.UsernamePasswordCredentials;  
import org.apache.commons.httpclient.auth.AuthScope;  
import org.apache.commons.httpclient.methods.GetMethod;  
import org.apache.commons.httpclient.params.HttpMethodParams; 
  
public class HttpRabbitMqclientUtil {  
	/**
	 * 验证的账号名
	 */
	private static final String USER = "Admin";

	/**
	 * 验证的密码名
	 */
	private static final String PSWD = "Admin123";
	/**
	 * 验证的IP地址
	 * http://115.28.141.185:15672/api/queues
	 */
	private static final String IP = "http://114.215.89.42:15672/api/";
	//private static final String IP = "http://115.28.141.185:15672/api/";
	
  
    public  String RabbitMqGetInfo(String url) throws HttpException, IOException {  
    	String responseBody="";
    	 HttpClient httpClient = new HttpClient();  
		//需要验证  
        UsernamePasswordCredentials creds = new UsernamePasswordCredentials(USER, PSWD);  

        httpClient.getState().setCredentials(AuthScope.ANY, creds); 
        
  
        //设置http头  
        List <Header> headers = new ArrayList <Header>();  
        headers.add(new Header("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)"));  
        httpClient.getHostConfiguration().getParams().setParameter("http.default-headers", headers);  
  
        GetMethod method = new GetMethod(IP+url);
        method.setDoAuthentication(true);
        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,  
                new DefaultHttpMethodRetryHandler(3, false));  
        try {  
        	   int statusCode = httpClient.executeMethod(method);
               if (statusCode != HttpStatus.SC_OK) {
                System.err.println("Method failed: "
                  + method.getStatusLine());
               }
               //读取内容 
               responseBody = method.getResponseBodyAsString();
               //处理内容
               System.out.println(new String(responseBody));
              } catch (HttpException e) {
               //发生致命的异常，可能是协议不对或者返回的内容有问题
               System.out.println("Please check your provided http address!");
               e.printStackTrace();
           
        }
        catch (IOException e) {
            //发生网络异常
            e.printStackTrace();
           } finally {  
            method.releaseConnection();  
        }
        return responseBody;
    }  
    
    	
    } 