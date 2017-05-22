package com.util.callEtlTools;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;

import APP.Comm.BLL.BaseBllException;
import APP.Comm.Util.HLogger;
import APP.Comm.Util.JsonUtil;

/**
 * 功能：KETTLE调用KTR文件的工具 
 * 类名：单例类
 * 时间：2014年12月04日 下午19:48:17 <br/> 
 * @author 王攀科
 */

public class CallEtlTool 
{
	private static CallEtlTool callEtlTool = null;
	private static final String[] ERROR_MSGS = { "SUCCESS", "KTR路径为空!", "KTR执行异常!", "调用KETTLE组件出错!" };
	public static final String RESULT_LIST = "RESULT_LIST";
	public static final String ERROR_CODE = "ERROR_CODE";
	public static final String ERROR_MSG = "ERROR_MSG";

	//初始化加载CallEtlTool实例
	static
	{
		callEtlTool = new CallEtlTool();
	}
	
	private CallEtlTool() {}
	
	/**
	 * <pre>
	 * 创建人：王攀科
	 * 日期：2014年12月4日 下午8:10:08
	 * 描述：返回CallEtlTool实例
	 * <pre>
	 */
	public static CallEtlTool getInstance()
	{
		return callEtlTool;
	}

	/**
	 * <pre>
	 * 创建人：王攀科
	 * 日期：2014年12月4日 下午8:09:02
	 * 描述：初始化KETTLE;
	 * <pre>
	 */
	public static void initKettle() throws KettleException
    {
    	if(!KettleEnvironment.isInitialized())
    	{
    		KettleEnvironment.init();
    	}
    }
	
	/**
	 * <pre>
	 * 创建人：王攀科
	 * 日期：2014年12月4日 下午8:24:56
	 * 描述：Java调用ETL方法， req ETL输入参数，fname KTR文件路径
	 * 返回参数：MAP包含三个返回值 RESULT_LIST, ERROR_CODE, ERROR_MSG;
	 * 		RESULT_LIST ETL返回的JSON字符串;
	 * 		ERROR_CODE 0 为KTR执行成功 ，其余为失败;
	 *		ERROR_MSG 失败原因;
	 * <pre>
	 */
	public Map<String, String> callKettle(Map<String, String> req, String fname) throws BaseBllException
	{
		int errorCode = 0;
		String errorMsg = ERROR_MSGS[errorCode];
    	Map<String, String> result = new HashMap<String, String>();
    	
    	if(isEmpty(fname))
    	{
    		errorCode = 1;
			errorMsg = ERROR_MSGS[errorCode];
        	result.put(RESULT_LIST, "");
    	}
    	else
    	{
    		fname = KtrParams.getRootPath() + fname;
    		if (! new File(fname).exists())
    		{
            	HLogger.info("此" + fname + "文件不存在");
    			errorCode = 4;
    			errorMsg = fname + "ktr 不存在";
    		}
    		else
    		{
    			try 
        		{
                	HLogger.info("begin JAVA 开始调用ETL。。。。。。");

    				initKettle();
    	    		TransMeta tranMeta = new TransMeta(fname);		
    	        	Trans tran = new Trans(tranMeta);
    	        	
    	        	tran.setVariable("errorMsg", "");
    	        	tran.setVariable("resultList", "");
    	        	tran.prepareExecution(new String[]{JsonUtil.objectToJsonString(req)});
    	        	tran.startThreads();
    	        	tran.waitUntilFinished();
    	        	
    	        	if(tran.getErrors() > 0)
    	        	{
    	        		errorCode = 2;
    	    			errorMsg = ERROR_MSGS[errorCode];
    	        	}
    	        	else if(!"SUCCESS".equals(tran.getVariable("errorMsg")))
    	        	{	
    	        		errorCode = 4;
    	        		errorMsg = tran.getVariable("errorMsg");
    	        	}
    	        	result.put(RESULT_LIST, tran.getVariable("resultList"));
    	        	
                	HLogger.info("end JAVA 调用ETL结束。。。。。。执行结果：  " + result.get(RESULT_LIST));
    	    	} 
        		catch (KettleException e) 
        		{
        			errorCode = 3;
        			errorMsg = ERROR_MSGS[errorCode];
                	result.put(RESULT_LIST, "");
    				e.printStackTrace();
    				throw new BaseBllException(e);
    			}
    		}
    	}
    	
    	result.put(ERROR_CODE, errorCode + "");
    	result.put(ERROR_MSG, errorMsg);

    	return result;
	}
	
	/**
	 * <pre>
	 * 创建人：王攀科
	 * 日期：2014年12月4日 下午8:24:56
	 * 描述：Java调用ETL方法， reqJson ETL输入参数，fname KTR文件路径
	 * 返回参数：MAP包含三个返回值 RESULT_LIST, ERROR_CODE, ERROR_MSG;
	 * 		RESULT_LIST ETL返回的JSON字符串;
	 * 		ERROR_CODE 0 为KTR执行成功 ，其余为失败;
	 *		ERROR_MSG 失败原因;
	 * <pre>
	 */
	public Map<String, String> callKettle(String reqJson, String fname) throws BaseBllException
	{
		int errorCode = 0;
		String errorMsg = ERROR_MSGS[errorCode];
    	Map<String, String> result = new HashMap<String, String>();
    	
    	if(isEmpty(fname))
    	{
    		errorCode = 1;
			errorMsg = ERROR_MSGS[errorCode];
        	result.put(RESULT_LIST, "");
    	}
    	else
    	{
    		fname = KtrParams.getRootPath() + fname;
    		if (! new File(fname).exists())
    		{
            	HLogger.info("此" + fname + "文件不存在");
    			errorCode = 4;
    			errorMsg = fname + "ktr 不存在";
    		}
    		else
    		{
    			try 
        		{
                	HLogger.info("begin JAVA 开始调用ETL。。。。。。");

    				initKettle();
    	    		TransMeta tranMeta = new TransMeta(fname);		
    	        	Trans tran = new Trans(tranMeta);
    	        	
    	        	tran.setVariable("errorMsg", "");
    	        	tran.setVariable("resultList", "");
    	        	tran.prepareExecution(new String[]{reqJson});
    	        	tran.startThreads();
    	        	tran.waitUntilFinished();
    	        	
    	        	if(tran.getErrors() > 0)
    	        	{
    	        		errorCode = 2;
    	    			errorMsg = ERROR_MSGS[errorCode];
    	        	}
    	        	else if(!"SUCCESS".equals(tran.getVariable("errorMsg")))
    	        	{	
    	        		errorCode = 4;
    	        		errorMsg = tran.getVariable("errorMsg");
    	        	}
    	        	result.put(RESULT_LIST, tran.getVariable("resultList"));
    	        	
                	HLogger.info("end JAVA 调用ETL结束。。。。。。");
    	    	} 
        		catch (KettleException e) 
        		{
        			errorCode = 3;
        			errorMsg = ERROR_MSGS[errorCode];
                	result.put(RESULT_LIST, "");
    				e.printStackTrace();
    				throw new BaseBllException(e);
    			}
    		}
    	}
    	
    	result.put(ERROR_CODE, errorCode + "");
    	result.put(ERROR_MSG, errorMsg);

    	return result;
	}
	
	/**
	 * <pre>
	 * 创建人：王攀科
	 * 日期：2014年12月4日 下午7:18:43
	 * 描述：判断字符串是否为空
	 * <pre>
	 */
	private boolean isEmpty(String str)
	{
		return null == str || "".equals(str);
	}
	
}
