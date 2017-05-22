package APP.Model.Base;

public class SYS_EXCEPTION_PARA {
	private String hospitalId="";
	private String currentUser ="";
	private String userResource = "";
	private String ip = "";
	private String serverIp="";
	private String apkVersion="";
	private String tVersion="";
	private String url="";
	private String op ="";
	private String errorSource = "";
	private String requsetParameter="";
	private String errorSql="";
	private String errorMsgForMonitor="";
	private String iscloudorTerminal="";
	private Object in = null;
	private boolean isNetException = false;
	public void sethospitalId(String value)
	{
		this.hospitalId = value;
	}
	public String gethospitalId()
	{
		return this.hospitalId;
	}
	
	public void setcurrentUser(String value)
	{
		this.currentUser = value;
	}
	public String getcurrentUser()
	{
		return this.currentUser;
	}
	
	public void setuserResource(String value)
	{
		this.userResource = value;
	}
	public String getuserResource()
	{
		return this.userResource;
	}
	
	public void setip(String value)
	{
		this.ip = value;
	}
	public String getip()
	{
		return this.ip;
	}
	
	public void setserverIp(String value)
	{
		this.serverIp = value;
	}
	public String getserverIp()
	{
		return this.serverIp;
	}
	
	public void setapkVersion(String value)
	{
		this.apkVersion = value;
	}
	public String getapkVersion()
	{
		return this.apkVersion;
	}
	
	public void settVersion(String value)
	{
		this.tVersion = value;
	}
	public String gettVersion()
	{
		return this.tVersion;
	}
	
	public void seturl(String value)
	{
		this.url = value;
	}
	public String geturl()
	{
		return this.url;
	}
	
	public void setop(String value)
	{
		this.op = value;
	}
	public String getop()
	{
		return this.op;
	}
	
	public void seterrorSource(String value)
	{
		this.errorSource = value;
	}
	public String geterrorSource()
	{
		return this.errorSource;
	}
	
	public void setrequsetParameter(String value)
	{
		this.requsetParameter = value;
	}
	public String getrequsetParameter()
	{
		return this.requsetParameter;
	}
	
	public void seterrorSql(String value)
	{
		this.errorSql = value;
	}
	public String geterrorSql()
	{
		return this.errorSql;
	}
	
	public void seterrorMsgForMonitor(String value)
	{
		this.errorMsgForMonitor = value;
	}
	public String geterrorMsgForMonitor()
	{
		return this.errorMsgForMonitor;
	}
	
	public void setiscloudorTerminal(String value)
	{
		this.iscloudorTerminal = value;
	}
	public String getiscloudorTerminal()
	{
		return this.iscloudorTerminal;
	}
	
	public void setin(Object value)
	{
		this.in = value;
	}
	public Object getin()
	{
		return this.in;
	}
	public void setIsNetException(boolean value){
		isNetException = value;
	}
	public boolean getIsNetException(){
		return isNetException;
	}
}
