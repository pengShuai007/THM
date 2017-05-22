package com.util.callEtlTools;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.pentaho.di.core.exception.KettleException;

import APP.Comm.Util.HLogger;

/**
 * 功能：初始化KETTLE组件，和加载直连KTR路径
 * 类名：InitCallEtlServlet   
 * 时间：2014年12月04日 下午15:22:17 <br/> 
 * @author wpk
 */
public class InitCallEtlServlet extends HttpServlet implements javax.servlet.Servlet
{

	
	public InitCallEtlServlet() {
		super();
	}
	
	/**
	 *  servlet初始化时加载KETTLE组件，和加载直连KTR路径
	 */
	@Override
	public void init() throws ServletException
	{
		try 
		{
			HLogger.info(" begin Load KTR PATH ...... 开始时间：" + System.currentTimeMillis());
			KtrParams.loadKtrPath(this.getServletContext().getRealPath("/"));
			HLogger.info(" End Load KTR PATH ...... 结束时间： " + System.currentTimeMillis());

			HLogger.info(" BEGIN KETTLE INTI ......  初始化ETL组件, 开始时间：" + System.currentTimeMillis());
			CallEtlTool.initKettle();
			HLogger.info(" END KETTLE INTI ......  ETL初始化成功, 结束时间：" + System.currentTimeMillis());
		} 
		catch (KettleException e)
		{
			HLogger.error(" fail KETTLE INTI ......  ETL初始化失败" + System.currentTimeMillis());
			e.printStackTrace();
			throw new ServletException(e);
		}
		super.init();
	}
	
}
