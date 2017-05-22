package APP.Comm.Handler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import APP.Comm.BLL.BaseBllException;
import APP.Comm.DotNet.HttpContext;
import APP.Comm.Util.HLogger;

/**
 * comments:
 * 
 * sjl modify 2013-10-17上午5:29:51
 */
public abstract class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BaseServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HLogger.info(this.getServletName() + " doGet Start!");
		HttpContext httpHandler = initContext(request, response);
		try {
			httpHandler.getHttpHandler().ProcessRequest();
		} catch (Throwable e) {
			throw new ServletException(e.getMessage());
		}
		HLogger.info(this.getServletName() + " doGet End!");
	}

	/**
	 * comments:
	 * 
	 * sjl modify 2013-10-17下午2:36:50
	 */
	public HttpContext initContext(HttpServletRequest request,
			HttpServletResponse response) {
		// sjl
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException ex) {
			HLogger.error(ex);
		}
		response.setContentType("text/html;charset=UTF-8");
		//
		HttpContext context = new HttpContext(request, response);
		ServletContext servletContext = request.getServletContext();
		context.setServletContext(servletContext);
		BusinessHandler httpHandler = new BusinessHandler();
		context.setHttpHandler(httpHandler);
		String contextPath = request.getContextPath();
		try {
			httpHandler.initContext(context);
		} catch (BaseBllException e) {
			HLogger.error(e);
		}
		return context;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HLogger.info(this.getServletName() + " doPost Start!");
		HttpContext httpHandler = initContext(request, response);
		//Edit begin 党智康  2014年11月20日 10:17:07
        //无用代码，编译报错
        //任务：KYEEAPP-896
		try {
            httpHandler.getHttpHandler().ProcessRequest();
        } catch (Throwable e) {
            throw new ServletException(e.getMessage());
        }
		//Edit end 党智康  2014年11月20日 10:17:07
		HLogger.info(this.getServletName() + " doPost Ends!");
	}

	/**
	 * comments:
	 * 
	 * sjl modify 2013-10-17上午5:29:39
	 */
	public void ProcessHrpRequest(HttpContext context) throws Exception {
		throw new RuntimeException("必须实现方法：ProcessHrpRequest");
	}

	public boolean getHandlerExtend() {
		try {
			return getHandlerExtend(null);
		} catch (BaseBllException e) {
			e.printStackTrace();
			HLogger.Error(e);

		}
		return false;
	}

	/**
	 * comments:重载方法，目的为代码转换方便
	 * 
	 * sjl modify 2013-10-19下午12:15:05
	 */
	public boolean getHandlerExtend(HttpContext context)
			throws BaseBllException {
		return false;
	}

	public final void CheckIsOtherLogin(HttpContext context)
			throws BaseBllException {
		// context.getHttpHandler().CheckIsOtherLogin();
	}

	public void checkUser(HttpContext context) throws BaseBllException {
		// if (context.getHttpHandler().getHrpUser() == null) {
		// throw new BaseBllException("您尚未登录或登录已过期，请重新登录！");
		// }
	}

	public final void clearAppLock(HttpContext context) throws BaseBllException {
		// context.getHttpHandler().ClearAppLock();
	}

	public final void freeAppLock(HttpContext context, String lockKey)
			throws BaseBllException {
		// context.getHttpHandler().FreeAppLock(lockKey);
	}

	public final boolean requestAppLock(HttpContext context, String lockKey)
			throws BaseBllException {
		// return context.getHttpHandler().RequestAppLock(lockKey);
		return true;
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		ServletContext servletContext = null;
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
		
			HttpContext context = new HttpContext(request, response);
			String uri = request.getRequestURI();
			if (uri.indexOf("/login.jsp") > -1 || uri.indexOf("main.jsp") > -1
					|| uri.endsWith(".jspx")) {
				response.setHeader("Access-Control-Allow-Methods",
						"POST, GET");
				response.setHeader("Access-Control-Allow-Origin", "*");
				response.setHeader("Access-Control-Allow-Headers",
						"Content-Type, Authorization, Accept,X-Requested-With");
				servletContext = request.getServletContext();
				context.setServletContext(servletContext);
				response.setContentType("text/html;charset=UTF-8");
				if (request.getMethod().equals("OPTIONS")) {
					super.doOptions(request, response);
				}else{
					super.service(request, response);
				}
			} else {
				super.service(request, response);
			}
		} catch (Exception ex) {
			HLogger.error(ex);
		}
	}

}
