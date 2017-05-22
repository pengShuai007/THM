package APP.Comm.DotNet;

import java.io.Serializable;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import APP.Comm.BLL.BLLContainer;
import APP.Comm.DataBase.Helper.IDataBase;
import APP.Comm.Handler.BusinessHandler;
import APP.Comm.Util.HLogger;

/**
 * comments:模拟DotNet下的WEB请求的Context类
 * 
 * sjl modify 2013-10-11上午11:56:35
 */
public class HttpContext implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request = null;
	private HttpServletResponse response = null;
	private ServletContext servletContext = null;
	private HttpSession session = null;

	private BusinessHandler httpHandler = null;
	/**
	 * 修改人：幺鹏飞
     * 修改时间：2014-10-14 10:45:52
     * 任务号：KYEEAPP-893
	 * action在当前线程队栈中的层数，队栈层数从0开始
	 */
	private final static int ACTION_URL_INDEX = 3;
	// add start huangpeng KYEEAPPMAINTENANCE-1162 2016年12月15日 15:31:33
	private String result = null;
	private boolean resultSuccess = true;
	// add end huangpeng KYEEAPPMAINTENANCE-1162 2016年12月15日 15:31:37
	public HttpContext(HttpServletRequest request, HttpServletResponse response) {
		super();
		this.request = request;
		this.response = response;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * comments:通过response输出
	 * 
	 * sjl modify 2013-10-11下午12:29:45
	 * 修改人：幺鹏飞
	 * 修改时间：2014-10-14 10:45:52
	 * 任务号：KYEEAPP-893
	 */
	public void write(Object value) {
		try {
//		    String loc = request.getParameter("loc");
//		    if(loc == null || "".equals(loc)){
//		    	loc = "c";
//		    }
//		    if(!"t".equals(loc)){
//		        String version = request.getParameter("opVersion");
//	            String op = request.getParameter("op");
//		    	Adapter adapter = MainFactory.getAdapter(loc,getActionUrl(),op);
//		    	if(adapter != null && !"".equals(adapter)){
//			        value = adapter.cloud2App(op, value.toString(), version);
//			    }
//		    }
		    
		    this.response.getWriter().write(value + "");
		} catch (Exception e) {
			HLogger.error(e);
		}
	}
	
	/**
	 * 
	 * 描述: 得到action的全路径<br/>
	 * 创建人: ypf <br/>
	 * 修改人：幺鹏飞
     * 修改时间：2014-10-24 10:45:52
     * 任务号：KYEEAPP-893
	 * @return 
	 * @since Ver 1.1
	 */
	private String getActionUrl(){
	    Thread thread = Thread.currentThread();
        StackTraceElement[] nowStack = thread.getStackTrace();
        return nowStack[ACTION_URL_INDEX].getClassName();
	}

	public ServletContext getServletContext() {
		return servletContext;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

//	public IDataBase getAppDb() {
//		return this.getHttpHandler().getAppDb();
//	}

	public BLLContainer getBLLContainer() {
		return this.getHttpHandler().getBLLContainer();
	}

	public HttpSession getSession() {
		if (session == null) {
			session = this.request.getSession(true);
		}
		return session;
	}

	public BusinessHandler getHttpHandler() {
		return httpHandler;
	}

	public void setHttpHandler(BusinessHandler httpHandler) {
		this.httpHandler = httpHandler;
	}

	/**
	 * comments:
	 * 
	 * sjl modify 2013-11-2下午12:00:44
	 */
	public String getParameter(String paraName) {
		return getRequest().getParameter(paraName);
	}

	public Object getSessionAttribute(String name) {
		return getSession().getAttribute(name);
	}

	public void setModuleNo(String moduleNo) {
		getHttpHandler().setModuleNo(moduleNo);
	}

	public void setContentType(String contentType) {
		getResponse().setContentType("text/json");
	}

	// add start KYEEAPPMAINTENANCE-1162  增加监控success为false的请求 2016年12月12日 09:59:18
	public boolean isResultSuccess() {
		return resultSuccess;
	}

	public String getResult() {
		return result;
	}

	public void checkResult(String result) {
		if (result != null && result.startsWith("{\"success\":false")) {
			this.resultSuccess = false;
		}
		this.result = result;
	}
	// add end KYEEAPPMAINTENANCE-1162 增加监控success为false的请求 2016年12月12日 09:59:24
}
