package APP.Comm.Handler;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.base.ext.SYS_USER_EXT;
import APP.Comm.BLL.BLLContainer;
import APP.Comm.BLL.BaseBLL;
import APP.Comm.BLL.BaseBllException;
import APP.Comm.Config.SystemParams;
import APP.Comm.DataBase.Helper.DBFactory;
import APP.Comm.DataBase.Helper.IDataBase;
import APP.Comm.DotNet.HttpContext;
import APP.Comm.Util.HLogger;
import APP.Comm.Util.JsonUtil;


/**
 * 系统Handler的基类 所有自定义Handler必须扩展自本类，同时通过覆盖ProcessHrpRequest接入系统
 * 用户扩展的ProcessHrpRequest可以不捕获异常，BaseHttpHandler会记录所有异常信息 施建龙 2012-12-27 模块号:无
 * 说明：系统中最上层的Hanler基类，所有自定义的.ashx都必须继承该类 Copyright: Copyright (c) 2012
 * ////Company: KYEE author: 宋建康 ////@version: 1.0
 */
/**
 * 说明：implements Filter 作者：施建龙 时间：2014年3月25日-下午1:26:27
 */
// abstract
public class BusinessHandler
{
    
    @SuppressWarnings("unused")
    private String moduleName;
    
    private List<String> noFilterList = new ArrayList<String>();
    
    /**
     * 请求的方法
     */
    public String op;
    
    /**
     * bll的委托执行对象
     */
    public BLLContainer BLLContainer;
    
    /**
     * sjl modify 2013-10-3下午5:55:58
     */
    private ServletContext servletContext = null;
    
    private HttpContext _context;
    
    private HttpServletRequest _request;
    
    private HttpServletResponse _response;
    
    @SuppressWarnings("unused")
    private HttpSession session = null;
    
    // Handler对应的模块编号
    // 每一个Handler对应一个确定ModuleNo，ModuleNo与权限设置的模块编号相同，系统通过ModuleNo校验当前用户是否有操作本模块权限
    private String privateModuleNo;
    
    // 自定义参数
    private java.util.Map<Object, Object> attrParams = new HashMap<Object, Object>();
    
    private IDataBase baseDb;
    
    /**
     * comments:从DotNet迁移而来，之前的前后台调用模式修改为后台之间的调用模式 使用此方法进行参数传递 sjl modify 2013-10-13下午12:57:16
     * 
     * @throws BaseBllException
     */
    public void initContext(HttpContext _context) throws BaseBllException
    {
        this._request = _context.getRequest();
        this._response = _context.getResponse();
        this.servletContext = _context.getServletContext();
        this._context = _context;
        initDb();
        this.BLLContainer = new BLLContainer(this.baseDb);
    }
    
    private void initDb() throws BaseBllException
    {
    	this.baseDb = DBFactory.CreateBaseDB();
    }
    
    public final String getModuleNo()
    {
        return privateModuleNo;
    }
    
    public final void setModuleNo(String value)
    {
        privateModuleNo = value;
    }
    
    /**
     * 说明：系统入口函数，接收前端请求的唯一入口，不得被覆盖或重写
     * 
     * @param context
     * @throws Throwable
     *             修改人：幺鹏飞 修改时间：2014-10-24 10:55:52 任务号：KYEEAPP-893
     */
    @SuppressWarnings(
    {
        "rawtypes", "unchecked"
    })
    public final void ProcessRequest() throws Exception{
    	// 是否访问成功，默认为失败
        boolean isSuccess = false;

        String url = "";
//        try
//        {
            //this.session = _request.getSession(true);
            this.op = _request.getParameter("op");
            if (this.op == null && _request.getParameter("reqReserved") != null)
            {
                this.op = _request.getParameter("reqReserved");
            }
            String loc = _request.getParameter("loc");
            url = _request.getRequestURI().replace("/COP", "");
            /**
			 *任务：
			 *描述：输出请求url，测试使用
			 *人员：施建龙
			 *时间：2014年11月23日下午4:19:36
			 **/
            HLogger.info("请求URL:"+url);
            String proName = "/" + SystemParams.getParamaValue("PROJECT_NAME");
            if (url.startsWith(proName))
            {
                url = url.substring(proName.length());
            }
            String clound = SystemParams.getParamaValue("PACKAGE_INFO_CLOUND");
            url = clound + url;
            /**
			 *任务：
			 *描述：替换双斜杠//为单斜杠/,此解决方法是为解决发送请求异常的临时解决方案
			 *人员：施建龙
			 *时间：2014年11月23日下午4:51:59
			 **/
            HLogger.info("请求URL2:"+url);
            String className = url.substring(0, url.lastIndexOf("."));
            className=className.replace("//", "/").replace("/", ".");
            if(className.startsWith("."))
                className=className.substring(1);
            Class cls = Class.forName(className);
            
			if (!className.endsWith("OperatorLoginActionC")
					&& !className.endsWith("OperatorLoginActionT")&& !className.endsWith("IPCCCallBackActionC")) {
				session = _request.getSession(true);
				if ("c".equals(loc)) {
					SYS_USER_EXT appOprater = (SYS_USER_EXT) session
							.getAttribute("appuser");
					if (appOprater == null) {
						throw new BaseBllException("您尚未登录或登录已过期，请重新登录！", null);
					}
				}
			}
            
            /**
			 *任务：
			 *描述：
			 *人员：施建龙
			 *时间：2014年11月20日上午12:19:03
			 **/
            HLogger.info("ClassName="+className);
            Method method = cls.getMethod(op, _context.getClass());
            // 调用工厂方法，通过已签名的串返回接口的适配类，如果为空走原有流程机制
                HLogger.info(cls.getName() + "的" + method.getName() + "方法开始执行!");
                Object temp=null;
                try{
                	temp=cls.newInstance();
                }catch(Exception e){
                	HLogger.error("ProcessRequest_className:"+className+":"+temp+e);
                }
                if(temp==null){
                	HLogger.error("ProcessRequest_className is null ");
                }
                HLogger.info("ProcessRequest method:"+method+"temp:"+temp+",_context:"+_context);
                method.invoke(temp, _context);
//                method.invoke(cls.newInstance(), _context);
                HLogger.info(cls.getName() + "的" + method.getName() + "方法执行结束!");
//            }
            isSuccess = true;
    }
    
    /**
     * 说明： 给BaseBLL变量赋值
     * 
     * @param baseBll
     *            业务层对象
     * @throws BaseBllException
     * 修改人：党智康
     * 修改时间：2014-10-24 10:55:52 
     * 任务号：KYEEAPP-894
     * 
     */
    public final void SetParamsToBLL(Object baseBll, HttpContext context) throws BaseBllException
    {
        //context参数作废
        BaseBLL baseBLL = (BaseBLL) baseBll;
        baseBLL.setPostParams(this._context.getRequest().getParameterMap());
        baseBLL.setAttrParams(attrParams);
        baseBLL.setGridRequestParameters(JsonUtil.GridRequestJsonToObject(this._context.getRequest().getParameterMap()));
        baseBLL.setBaseDB(this.baseDb);
        baseBLL.setContext(_context);
    }
    
    /**
     * comments:需要子类覆盖 sjl modify 2013-10-16下午10:49:51
     */
    public String getModuleName()
    {
        return null;
    }
    
    public void setModuleName(String moduleName)
    {
    }
    
    /**
     * comments:自定义参数 sjl modify 2013-11-3上午10:53:35
     */
    public void setAttrParameter(String paraName, Object Value)
    {
        attrParams.put(paraName, Value);
    }
    
    public BLLContainer getBLLContainer()
    {
        return BLLContainer;
    }
    
    public void setBLLContainer(BLLContainer bLLContainer)
    {
        BLLContainer = bLLContainer;
    }
    
//    public IDataBase getAppDb()
//    {
//        return appDb;
//    }
//    
    /**
     * 记录用户访问
     * 描述: TODO(这里用一句话描述这个方法的作用)<br/>
     * 创建人: Administrator <br/>
     * 
     * @since Ver 1.1
     */
//    private void recordUserVisit(HttpServletRequest _request,boolean isSuccess,String url)
//    {
//    	HLogger.info("BusinessHandler function recordUserVisit begin!");
//    	HLogger.info("说明:记录用户访问记录!");
//        // 获得访问的用户ID
//        String userID = _request.getParameter("USER_ID");
//        if (DotNetToJavaStringHelper.isNullOrEmpty(userID))
//        {
//            userID = _request.getParameter("userId");
//            
//            if (DotNetToJavaStringHelper.isNullOrEmpty(userID))
//            {
//                userID = "-1";
//            }
//        }
//        
//        // 获得访问的医院ID
//        String hospitalID = _request.getParameter("hospitalID");
//        if (DotNetToJavaStringHelper.isNullOrEmpty(hospitalID))
//        {
//            hospitalID = _request.getParameter("HOSPITAL_ID");
//            
//            if (DotNetToJavaStringHelper.isNullOrEmpty(hospitalID))
//            {
//                hospitalID = "-1";
//            }
//        }
//        
//        // 获取访问者的IP
//        String ip =_request.getRemoteAddr();
//        //edit start KYEEAPP-1099 获取当前服务器IP和op版本号 houhy 2014年12月9日 14:10:48
//        String server_ip=_request.getLocalAddr();//获取当前服务器的IP
//        //String version=_request.getParameter("opVersion");//获取当前op版本
//        String version=SystemParams.getParamaValue("WEB_BUILDER_VERSION_APP");//获取当前op版本
//        
//        String sqlStr = "insert into C_USER_VISIT_RECORDS (USER_ID,HOSPITAL_ID,VISIT_OP,IS_SUCCESS,IP,SERVER_IP,VERSION) "
//        		+ "values(:USER_ID,:HOSPITAL_ID,:VISIT_OP,:IS_SUCCESS,:IP,:SERVER_IP,:VERSION)";
//        //edit end KYEEAPP-1099 获取当前服务器IP和op版本号 houhy 2014年12月9日 14:10:48
//        List<AppDbParameter> paramList = new ArrayList<AppDbParameter>();
//        paramList.add(new AppDbParameter("USER_ID",Integer.valueOf(userID)));
//        paramList.add(new AppDbParameter("HOSPITAL_ID",Integer.valueOf(hospitalID)));
//        paramList.add(new AppDbParameter("VISIT_OP",url+"/"+op));
//        paramList.add(new AppDbParameter("IS_SUCCESS",(isSuccess == true ? 1 : 0)));
//        paramList.add(new AppDbParameter("IP",ip));
//        //edit start KYEEAPP-1099 获取当前服务器IP和op版本号 houhy 2014年12月9日 14:10:48
//        paramList.add(new AppDbParameter("SERVER_IP",server_ip));
//        paramList.add(new AppDbParameter("VERSION",version));
//        //edit end KYEEAPP-1099 获取当前服务器IP和op版本号 houhy 2014年12月9日 14:10:48
//        
//        try
//        {
//            ((AbstractDataBase)appDb).Open();
//            appDb.Save(sqlStr, paramList);            
//        }
//        catch (BaseBllException e)
//        {
//            HLogger.Info("写入用户访记录失败："+paramList+"异常信息:"+e);
//        }
//        finally
//        {
//            try
//            {
//                ((AbstractDataBase)appDb).Close();
//                HLogger.info("BusinessHandler function recordUserVisit end!");
//            }
//            catch (BaseBllException e)
//            {                
//                HLogger.error("BusinessHandler关闭记录用户appDb异常"+e);
//            }
//        }
//    }
    
    /**
     * <pre>
     * 任务： KYEEAPP-1099
     * 描述： 获取本地IP
     * 作者：houhy 
     * 时间：2014年12月9日下午2:28:36
     * @return
     * returnType：String
     * </pre>
     */
    private String getLocalIP(){
    	InetAddress addr=null;
    	try{
    		addr=InetAddress.getLocalHost();
    	}catch(UnknownHostException ex){
    		ex.printStackTrace();
    	}
    	byte[] ipAddr = addr.getAddress();     
        String ipAddrStr = "";     
        for (int i = 0; i < ipAddr.length; i++) {     
            if (i > 0) {     
                ipAddrStr += ".";     
            }     
            ipAddrStr += ipAddr[i] & 0xFF;     
        }     
        return ipAddrStr; 
    }
    
    
    //	@Override
    //	public void destroy() {
    //	}
    public static void main(String[] args) throws UnsupportedEncodingException
    {
        System.out.println(URLEncoder.encode("陈伯鹏", "utf-8"));
    }
    //
    //	@Override
    //	public void doFilter(ServletRequest request, ServletResponse response,
    //			FilterChain chain) throws IOException, ServletException {
    //		HttpServletRequest _request = (HttpServletRequest) request;
    //		HttpServletResponse _response = (HttpServletResponse) response;
    //		ServletContext servletContext = null;
    //		try {
    //			_request.setCharacterEncoding("UTF-8");
    //			_response.setCharacterEncoding("UTF-8");
    //		} catch (UnsupportedEncodingException ex) {
    //			HLogger.error(ex);
    //		}
    //		HttpContext _context = new HttpContext(_request, _response);
    //		String uri = _request.getRequestURI();
    //		if (uri.indexOf("/login.jsp") > -1 || uri.indexOf("main.jsp") > -1
    //				|| uri.endsWith(".jspx")) {
    //			_response.setHeader("Access-Control-Allow-Methods",
    //					"POST, GET");
    //			_response.setHeader("Access-Control-Allow-Origin", "*");
    //			_response.setHeader("Access-Control-Allow-Headers",
    //					"Content-Type, Authorization, Accept,X-Requested-With");
    //			servletContext = request.getServletContext();
    //			_context.setServletContext(this.servletContext);
    //			response.setContentType("text/html;charset=UTF-8");
    //			if (!_request.getMethod().equals("OPTIONS")) {
    //				ProcessRequest(_context);
    //			} else {
    //				return;
    //			}
    //		} else {
    //			chain.doFilter(request, response);
    //			return;
    //		}
    //	}
    //	@Override
    //	public void init(FilterConfig chain) throws ServletException {
    //	}
}
