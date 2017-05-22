package com.util.OSSTools;

import java.io.IOException;
import java.io.InputStream;

import APP.Comm.BLL.BaseBllException;
import APP.Comm.DataBase.Helper.IDataBase;
import APP.Comm.Util.DotNetToJavaStringHelper;
import APP.Comm.Util.HLogger;

import com.aliyun.openservices.ClientException;
import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.OSSException;
import com.util.PubSystemParams;

public class OSSUtil {
	private static String OSSPASS = "1";
	/**
     * 任务号：KYEEAPP-949
     * 描述:OSS文件上传
     * 创建人:罗代华
     * 时间：2014年11月19日13:16:27
     * @param String filePath 云服务器上保存文件的绝对路径
     * @param IDataBase appDb
     * @throws BaseBllException 
	 * @throws IOException 
	 * @throws ClientException 
	 * @throws OSSException 
     */
    public static void OSSFileUpload(String filePath,
        IDataBase appDb,InputStream input) throws BaseBllException, OSSException, ClientException, IOException{
    	HLogger.info("OSSUtil function OSSFileUpload start!");
		HLogger.info("OSSFileUpload 文件上传");
    	if(!filePath.contains("Resources")){
    		throw new BaseBllException("文件上传路径错误");
    	}
    	String key=filePath.substring(filePath.indexOf("Resources")+10);//截取服务器的图片路径
        String ACCESS_ID=PubSystemParams.getParams("ACCESS_ID", appDb);//OSS登录名
        String ACCESS_KEY = PubSystemParams.getParams("ACCESS_KEY",appDb);//OSS登陆密码
        String ENDPOINT = PubSystemParams.getParams("ENDPOINT",appDb);//OSS服务器地址
        String BUCKETNAME = PubSystemParams.getParams("BUCKETNAME", appDb);//OSS Bucket名称
        String ROOTPATH = PubSystemParams.getParams("rootPath",appDb);//获取环境
        OSSClient client = new OSSClient(ENDPOINT,ACCESS_ID, ACCESS_KEY);//创建OSS客户端
        key=GetRootPath(ROOTPATH)+key;//得到OSS上的路径
        OSSObject.uploadImageFile(client, BUCKETNAME, key, input);  //上传图片资源
        HLogger.info("OSSUtil function OSSFileUpload end!");
		HLogger.info("OSSFileUpload 文件上传");
    }
    /**
     * 任务号：KYEEAPP-949
     * 描述:页面访问图片资源控制
     * 创建人:罗代华
     * 时间：2014年11月19日14:38:15
     * @param String localUrl 
     * 			服务器上的相对地址（从数据库中读取的地址）
     * @param String frontUrl 
     * 			当前环境允许访问的前端地址
     * @return String 
     * 			当前环境允许的访问路径
     * @throws BaseBllException 
     */
    public static String GetOSSPhotoUrl(String localUrl,String frontUrl) throws BaseBllException{
    	HLogger.info("OSSUtil function GetOSSPhotoUrl start!");
		HLogger.info("GetOSSPhotoUrl 页面访问图片资源控制");
    	
    	if(DotNetToJavaStringHelper.isNullOrEmpty(localUrl)){
    		HLogger.info("OSSUtil function GetOSSPhotoUrl end!");
			HLogger.info("GetOSSPhotoUrl 无图片返回为空");
    		return null;
    	}
    	String temp=localUrl.toLowerCase();
    	if(temp.startsWith("http://")){
    		HLogger.info("OSSUtil function GetOSSPhotoUrl end!");
			HLogger.info("GetOSSPhotoUrl 当前Url以http://开头");
    		return localUrl;//如果以http开头直接返回路径
    	}else if("1".equals(OSSPASS)){//
//    		edit add start KYEEAPPTEST-873 luodaihua 2014年12月11日21:27:06
    		localUrl = localUrl.replaceAll("\\\\", "/");
//    		edit add end KYEEAPPTEST-873 luodaihua 2014年12月11日21:27:22
    		String url=frontUrl+localUrl.substring(localUrl.indexOf("/")+1);//获取OSS访问路径
    		HLogger.info("OSSUtil function GetOSSPhotoUrl end!");
			HLogger.info("GetOSSPhotoUrl 返回OSS路径");
    		return url;
    	}else{
    		String url = frontUrl+localUrl;//获取访问路径
    		HLogger.info("OSSUtil function GetOSSPhotoUrl end!");
			HLogger.info("GetOSSPhotoUrl 返回服务器路径");
    		return url;
    	}    	
    }





    /**
     * <pre>
     * 任务号：KYEEAPP-949
     * 作者:罗代华
     * 日期:2014年11月19日 下午8:44:40
     * 描述:依据环境获取OSS根目录
     * </pre>
     */
    public static String GetRootPath(String rootPath) throws BaseBllException{
    	switch(rootPath){
        	case "DEVELOP":                         //判断请求端是否为开发库
            	return "DevelopLibrary/";
        	case "TEST":                            //判断是否为测试库
            	return  "TestLibrary/";
        	case "RELEASE":                        //判断是否为发布库
            	return  "ReleaseLibrary/";
        	case "TESTCLOUD":                       //判断是否为测试云
            	return  "TestCloud/";
        	case "OFFICIAL":                        //判断是否为正式云
        		return  "Official/";
        	default:                                //其他
        		throw new BaseBllException("OSSUtil GetRootPath参数值配置错误");
    	}
	}
    /**
     * 任务号：KYEEAPP-949
     * 描述:页面访问图片资源控制
     * 创建人:罗代华
     * 时间：2014年11月24日17:31:46
     * @param String url 服务器上的相对地址（从数据库中读取的地址）
     * @return String 经过滤后的服务器上的相对路径
     * @throws BaseBllException 
     */
    public static String GetCloudPhotoUrl(String url) throws BaseBllException{
    	if(DotNetToJavaStringHelper.isNullOrEmpty(url)){
    		HLogger.info("OSSUtil  function GetCloudPhotoUrl无图片路径");
    		return null;
    	}
    	String tempUrl=url.toLowerCase();
    	if(tempUrl.startsWith("http://")){
    		HLogger.info("OSSUtil  function GetCloudPhotoUrl 当前路径以http开头，默认可以直接进行访问");
    		return url;
    	}else if(url.startsWith("Resources/")){
    		HLogger.info("OSSUtil  function GetCloudPhotoUrl 当前路径为服务器上的相对路径");
    		return url;
    	}else 
    		throw new BaseBllException("OSSUtil  function GetCloudPhotoUrl 当前图片路径为非法路径");  	
    }
    
    
    /**
     * 任务号：KYEEAPP-949
     * 描述:页面访问图片资源控制
     * 创建人:罗代华
     * 时间：2014年11月24日17:31:46
     * @param IDataBase appDb 当前环境的DB
     * @return 返回当前环境的前半部分的URL（不包括数据库中的相对路径）
     * @throws BaseBllException 
     */  
    public static String GetFrontUrl(IDataBase appDb) throws BaseBllException{
    	HLogger.info("OSSUtil function GetFrontUrl start!");
		HLogger.info("GetFrontUrl 获取当前环境的前半部分的URL");
    	OSSPASS = PubSystemParams.getParams("OSSPASS", appDb);//获取OSS是否启用标志
    	if("0".equals(OSSPASS)){
    		String LocalPort = PubSystemParams.getParams("LOCALPORT",appDb);//获取服务器IP地址和端口号
    		String url = LocalPort+"/APP/";//获取访问路径
    		HLogger.info("OSSUtil function GetFrontUrl end!");
    		HLogger.info("GetFrontUrl OSS关闭，访问服务器路径");
    		return url;
    	}else{
    		String OSSURL = PubSystemParams.getParams("ENDPOINT", appDb);//获取OSS 服务器地址
    		String path = PubSystemParams.getParams("rootPath",appDb);//获取环境
    		String BUCKETNAME = PubSystemParams.getParams("BUCKETNAME", appDb); //获取bucket名称
    		String rootPath = GetRootPath(path);//获取OSS根目录
    		String tempUrl="http://"+BUCKETNAME+"."+OSSURL.substring(7)+"/"+rootPath;
    		HLogger.info("OSSUtil function GetFrontUrl end!");
    		HLogger.info("GetFrontUrl OSS开启，访问OSS路径");
    		return tempUrl;
    	}    	
    }   
}