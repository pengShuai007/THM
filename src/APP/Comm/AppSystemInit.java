package APP.Comm;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.PropertyConfigurator;

import APP.Comm.BLL.BaseBllException;
import APP.Comm.Config.Parser.SysConfigParse;
import APP.Comm.DataBase.Helper.DBUtil;
import APP.Comm.DotNet.Application;
import APP.Comm.Util.HLogger;

/**
 * 功能：继承HttpServlet（已经使用http协议规范实现Servlet接口），如 1 init方法,负责初始化Servlet对象 2
 * service方法,负责相应客户的请求, 如实现doDelete(),doGet(),doOptions(), doPost(),
 * doPut()和doTrace()的部分 3 destory方法
 * 
 * 作者：李智博
 */
public class AppSystemInit extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 类初始化
	public AppSystemInit() {
		super();
	}

	// 重新覆盖get、post方法
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

	// 重新覆盖init方法
	public void init() throws ServletException {
		HLogger.info("AppSystem Init Start!");
		super.init();
		// 获取项目路径
		String appBase = getServletContext().getContextPath();
		appBase = appBase.endsWith(File.separator) ? appBase : appBase
				+ File.separator;
		SystemResource.AppPath = appBase;
		SystemResource.setAppRealPath(getServletContext().getRealPath("/")); //真实路径
		
		// 初始化程序
		initApplication();  //servlet的 servletContext赋值给Application的serverlet
		String hrpHome = getInitParameter("APP_HOME"); //读取启动参数，放入程序属性
		setApplicationAttribute("APP_HOME", hrpHome);
		
		HLogger.info("AppSystem init set param!");
		// 系统配置参数sysParams放入Application中
		@SuppressWarnings("rawtypes")
		HashMap sysParams = (new SysConfigParse()).parse();
		setApplicationAttribute("HRP_PARAMS", sysParams);
		String file = null;
		file = hrpHome + File.separatorChar + "config" + File.separatorChar
				+ sysParams.get("LOGCONFIGFILE");
		PropertyConfigurator.configure(file);
		
		// 打开数据源
		try {
			DBUtil.getInstance().openDatasource(
					hrpHome + File.separatorChar + "config"
							+ File.separatorChar
							+ sysParams.get("DATASOURCECONFIGFILE"));
		} catch (BaseBllException e) {
			HLogger.error(e);
		}
		
		HLogger.info("AppSystem Init end! !");
	}

	/**
	 * 初始化
	 */
	public void initApplication() {
		Application.servletContext = this.getServletContext();
	}

	/**
	 * 设定全局属性 施建龙 2013年10月11日9:31:07
	 */
	public void setApplicationAttribute(String name, Object value) {
		this.getServletContext().setAttribute(name, value);
	}

	public Object getApplicationAttribute(String name) {
		return this.getServletContext().getAttribute(name);
	}
}