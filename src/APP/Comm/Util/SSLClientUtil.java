package APP.Comm.Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import APP.Comm.SystemResource;

public class SSLClientUtil {
	
	private static boolean CLIENT_CERT_LOAD_FLG = false;
	private static String CLIENT_KEY_STORE_PASSWORD = "";
	private static String CLIENT_TRUST_KEY_STORE_PASSWORD = "";
	private static String CLIENT_KEY_STORE_PATH = "";
	private static String CLIENT_TRUST_KEY_STORE_PATH = "";
	private static SSLConnectionSocketFactory sslsf = null;
	/**
	 * 任务:KYEEAPPC-3394
	 * 描述:加载证书并返回客户端，供HttpClientUtil使用
	 * 作者:李君强
	 * 日期:2015年9月27日21:04:31
	 */
	public static CloseableHttpClient getTerminalHttpsClient() throws Exception{
		initClientCertInfo();
		KeyStore keyStore  = KeyStore.getInstance("PKCS12");
        KeyStore trustStore  = KeyStore.getInstance("JKS");
        
        FileInputStream keystream = new FileInputStream(new File(CLIENT_KEY_STORE_PATH));
        FileInputStream truststream = new FileInputStream(new File(CLIENT_TRUST_KEY_STORE_PATH));
        try {
        	keyStore.load(keystream, CLIENT_KEY_STORE_PASSWORD.toCharArray());
            trustStore.load(truststream, CLIENT_TRUST_KEY_STORE_PASSWORD.toCharArray());
        } finally {
        	keystream.close();
        	truststream.close();
        }

        //信任自签名证书，加载客户端证书
        SSLContext sslcontext = SSLContexts.custom()
        		.loadKeyMaterial(keyStore, CLIENT_KEY_STORE_PASSWORD.toCharArray())
                .loadTrustMaterial(trustStore, new TrustSelfSignedStrategy())
                .build();
        //允许TLS协议
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,new String[] { "TLSv1" },null,
                SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        //返回HTTPS客户端
        return HttpClients.custom().setSSLSocketFactory(sslsf).build();
	}
	public static void setSSLSocketBuild(HttpClientBuilder builder) throws Exception{
		initClientCertInfo();
		if(sslsf == null){
			KeyStore keyStore  = KeyStore.getInstance("PKCS12");
	        KeyStore trustStore  = KeyStore.getInstance("JKS");
	        
	        FileInputStream keystream = new FileInputStream(new File(CLIENT_KEY_STORE_PATH));
	        FileInputStream truststream = new FileInputStream(new File(CLIENT_TRUST_KEY_STORE_PATH));
	        try {
	        	keyStore.load(keystream, CLIENT_KEY_STORE_PASSWORD.toCharArray());
	            trustStore.load(truststream, CLIENT_TRUST_KEY_STORE_PASSWORD.toCharArray());
	        } finally {
	        	keystream.close();
	        	truststream.close();
	        }
	
	        //信任自签名证书，加载客户端证书
	        SSLContext sslcontext = SSLContexts.custom()
	        		.loadKeyMaterial(keyStore, CLIENT_KEY_STORE_PASSWORD.toCharArray())
	                .loadTrustMaterial(trustStore, new TrustSelfSignedStrategy())
	                .build();
	        //允许TLS协议
	        sslsf = new SSLConnectionSocketFactory(
	                sslcontext,new String[] { "TLSv1" },null,
	                SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		}
        //返回HTTPS客户端
        builder.setSSLSocketFactory(sslsf);
	}
	/**
	 * 任务:KYEEAPPC-3394
	 * 描述:初始化证书信息
	 * 作者:李君强
	 * 日期:2015年9月27日21:04:31
	 */
	private static void initClientCertInfo() throws IOException{
		if(!CLIENT_CERT_LOAD_FLG){
			String clientCertPath = SystemResource.getAppRealPath() + "WEB-INF" + File.separator + "clientcert" + File.separator;
			CLIENT_KEY_STORE_PATH = clientCertPath + "person-terminal-client.p12";
			CLIENT_TRUST_KEY_STORE_PATH = clientCertPath + "quyi-terminal-truststore.jks";
			CLIENT_KEY_STORE_PASSWORD = getFileString(clientCertPath + "person-terminal-client.pass");
			CLIENT_TRUST_KEY_STORE_PASSWORD = getFileString(clientCertPath + "quyi-terminal-truststore.pass");
		}
	}
	
	/**
	 * 任务:KYEEAPPC-3394
	 * 描述:从文件流中读取字符串
	 * 作者:李君强
	 * 日期:2015年9月27日21:04:31
	 */
	private static String inputStream2String(InputStream is) throws IOException{
	   BufferedReader in = new BufferedReader(new InputStreamReader(is));
	   StringBuffer buffer = new StringBuffer();
	   String line = "";
	   while ((line = in.readLine()) != null){
	     buffer.append(line);
	   }
	   return buffer.toString();
	}
	
	/**
	 * 任务:KYEEAPPC-3394
	 * 描述:从文件中读取字符串
	 * 作者:李君强
	 * 日期:2015年9月27日21:04:31
	 */
	private static String getFileString(String filePath) throws IOException{
		InputStream in = new FileInputStream(filePath);
		return inputStream2String(in);
	}
}
