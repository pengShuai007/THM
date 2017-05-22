package APP.Comm.Util;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;


import org.kyee.core.framework.SysParamMgr.Impl.SysParamMgrImpl;
import org.kyee.core.framework.SysParamMgr.service.ISysParamMgr;

import APP.Comm.BLL.BaseBllException;
import APP.Comm.Config.SystemParams;
/**
 * 任务:KYEEAPPC-3946
 * 描述:配置代理转发服务器
 * 李君强 2015年12月10日15:28:48
 * @author LiJunqiang
 */
public class ProxyClientUtil {
	//用于httpGet或httpPost的请求设置
	private static HttpHost proxyHost = null;
	//用于给HttpClientBuilder设置Provider
	private static CredentialsProvider credsProvider = null;
	//是否需要代理
	private static boolean needProxy = false;
	//代理主机
	private static String proxyHostName = null;
	//代理端口
	private static int proxyPort = 80;
	//代理用户
	private static String proxyUser = null;
	//代理用户密码
	private static String proxyPasswd = null;
	
	private static boolean initFlg = false;
	
	private static void init(){
		if(!initFlg){
			try {
				ISysParamMgr sysParamMgr = new SysParamMgrImpl();
		        String needProxySwitch = sysParamMgr.getSysParamValue("HTTP_PROXY_SWITCH");
		        needProxy = "Y".equalsIgnoreCase(needProxySwitch);
			} catch (Exception e) {
				needProxy = false;
				HLogger.error("判断是否使用http代理服务器时出错，请确认sys-config文件中的HTTP_PROXY_SWITCH属性");
			}
			if(needProxy){
				try {
					ISysParamMgr sysParamMgr = new SysParamMgrImpl();
					String proxyAddress = sysParamMgr.getSysParamValue("HTTP_PROXY_ADDRESS");
					init(proxyAddress);
				} catch (Exception e) {
					needProxy = false;
					proxyHost = null;
					credsProvider = null;
					HLogger.error("未能从系统参数中取到代理服务器(HTTP_PROXY_ADDRESS)的配置。");
				}
			}
			initFlg = true;
		}
	}
	
	/**
	 * 初始化代理服务器的配置信息
	 * @param confString format:[localhost;8088;username;password]
	 */
	private static void init(String confString){
		if(confString==null || confString.trim().isEmpty()){
			needProxy = false;
			return;
		}
		String[] conf = confString.split(";");
		if(conf != null && conf.length!=4){
			needProxy = false;
			return;
		}
		int proxyPort = 80;
		try{
			proxyPort = Integer.parseInt(conf[1]);
		}catch(Exception e){
			HLogger.error("int型转换失败："+conf[1]);
		}
		ProxyClientUtil.proxyHostName = conf[0];
		ProxyClientUtil.proxyPort = proxyPort;
		ProxyClientUtil.proxyUser = conf[2];
		ProxyClientUtil.proxyPasswd = conf[3];
		initProxyConf();
	}
	
	/**
	 * 初始化代理服务要使用的Provider和RequestConfig
	 */
	private static void initProxyConf(){
		credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope(proxyHostName, proxyPort),
                new UsernamePasswordCredentials(proxyUser, proxyPasswd));
        proxyHost = new HttpHost(proxyHostName, proxyPort);
	}
	
	/**
	 * 得到https请求时要使用的CloseableHttpClient
	 */
	public static RequestConfig.Builder setProxyConfig(RequestConfig.Builder reqConfBuilder){
		init();
		if(reqConfBuilder == null){
			reqConfBuilder = RequestConfig.custom();
		}
		if(needProxy && proxyHost!=null){
			reqConfBuilder.setProxy(proxyHost);
		}
		return reqConfBuilder;
	}
	
	/**
	 * 设置HttpClientBuilder的CredentialsProvider
	 */
	public static void setProxyClientBuild(HttpClientBuilder builder){
		if(needProxy && credsProvider!=null){
			builder.setDefaultCredentialsProvider(credsProvider);
		}
	}
}
