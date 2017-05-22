/**
 * 
 */
package APP.Comm.Util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * @author Dike
 * 
 */
public class VistitWebServiceUtil {
	/**
	 * 
	 * @param cls
	 *            WebService接口的类型
	 * @param wsdlUrl
	 *            WebService接口的访问路径,建议首先在浏览器中访问,如果能正常获得XML文件结构,就能正常调用
	 * @return 返回Object对象,需要强制转换为接口类型的对象
	 * 
	 *         <pre>
	 *           调用示例： 接口类：(可以从WebService服务端拷贝)
	 * 	  {@code   @WebService}(targetNamespace = "http://inface.webservice.kyee.com/") 
	 *           public interface HelloWorld{
	 *           	   String sayHello({@code @WebParam}(name = "username")String username); 
	 *           }
	 *           调用方法片段:
	 *           String wsdUrl = SystemParams.getParamaWSDLValue("helloWorld");
	 *           HelloWorld hw = VistitWebServiceUtil.visitWebService(HelloWorld.class,wsdlUrl);
	 *           String result = hw.sayHello("Johon");<br>
	 *           结果： {@code Hello,Johon!}
	 * </pre>
	 */
	public static Object visitWebService(Class cls, String wsdlUrl) {
		JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
		factoryBean.setServiceClass(cls);
		factoryBean.setAddress(wsdlUrl);
		Object object = factoryBean.create();
		return object;
	}

	/**
	 * http方式访问webservice
	 * @param url webservice路径
	 * @param method webservice接口名
	 * @param params webservice接口参数
	 * @param namespace webservice接口的targetNamespace
	 * @return 字符串
	 */
	public static String visitWebService(String url, String method,
			Map<String, String> params, String namespace) {
		String result = "";
		CloseableHttpClient httpClient = HttpClients.createDefault();

		StringBuilder soapRequestData = new StringBuilder();

		soapRequestData.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		soapRequestData
				.append("<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
		soapRequestData
				.append(" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"");
		soapRequestData
				.append(" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">");
		soapRequestData.append(" <soap12:Body>");
		soapRequestData.append(" <");
		soapRequestData.append(method);
		soapRequestData.append("	");
		soapRequestData.append("	xmlns=\"");
		soapRequestData.append(namespace);
		soapRequestData.append("\">");
		if (params.keySet().size() > 0) {
			for (String key : params.keySet()) {
				soapRequestData.append("<");
				soapRequestData.append(key);
				soapRequestData.append(">");
				soapRequestData.append(params.get(key));
				soapRequestData.append("</");
				soapRequestData.append(key);
				soapRequestData.append(">");
			}
		}
		soapRequestData.append("</");
		soapRequestData.append(method);
		soapRequestData.append("</soap12:Body></soap12:Envelope>");
		try {
			HttpPost httpPost = new HttpPost(url);
			httpPost.setHeader("Content-Type",
					"application/soap+xml; charset=utf-8");
			HttpEntity he = new StringEntity(soapRequestData.toString());
			httpPost.setEntity(he);
			CloseableHttpResponse response2 = httpClient.execute(httpPost);
			try {
				// System.out.println(response2.getStatusLine());
				HttpEntity entity2 = response2.getEntity();
				// do something useful with the response body
				// and ensure it is fully consumed
				// EntityUtils.consume(entity2);
				if (response2.getStatusLine().getStatusCode() == 200) {
					result = HttpClientUtil.inputStreamToString(entity2
							.getContent());
				} else {
					result = "未能正确获取结果,请检查参数等是否正确。";
				}
			} catch (IllegalStateException e) {
				e.printStackTrace();
				HLogger.Error(e);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				HLogger.Error(e);
			} finally {
				try {
					response2.close();
				} catch (IOException e) {
					e.printStackTrace();
					HLogger.Error(e);
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			HLogger.Error(e);
		} catch (ClientProtocolException e1) {
			e1.printStackTrace();
			HLogger.Error(e1);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			HLogger.Error(e1);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
				HLogger.Error(e);
			}
		}
		return result;
	}
}