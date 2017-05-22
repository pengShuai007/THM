package APP.Model.Base;

import java.util.Date;

import APP.Model.BaseEntity;

public class C_SYSTEM_FPI_ACCOUNT extends BaseEntity{
	private  long	SYSTEM_FPI_ACCOUNT_ID; //外部平台账号ID
	private  long SYSTEM_FPI_DEF;//外部平台定义ID
	private String FPI_ACCOUNT;//平台账号
	private String FPI_PASSWORD;//平台密码
	private long FPI_ACCOUNT_STATE;//账号状态
	private String COMMENTS;//说明
	private String CREATOR;//创建者
	private Date CREATE_TIME;//创建时间
	private String UPDATER;//更新者
	private Date UPDATE_TIME;//更新时间
	
	/**
	 * 
	* <pre>
	* 任务:KYEEAPP-1155
	* 描述:设置外部平台账号ID
	* 作者:冯泽
	* 日期:2014年12月18日 下午1:52:17
	* @param value
	* </pre>
	 */
	public void setSYSTEM_FPI_ACCOUNT_ID(long value)
	{
		this.SYSTEM_FPI_ACCOUNT_ID = value;
		this.AddUpdateAttribute("SYSTEM_FPI_ACCOUNT_ID");
	}
	/**
	 * 
	* <pre>
	* 任务:KYEEAPP-1155
	* 描述:获取外部平台账号ID
	* 作者:冯泽
	* 日期:2014年12月18日 下午1:52:52
	* @return
	* </pre>
	 */
	public long getSYSTEM_FPI_ACCOUNT_ID()
	{
		return this.SYSTEM_FPI_ACCOUNT_ID;
	}
	
	/**
	 * 
	* <pre>
	* 任务:KYEEAPP-1155
	* 描述:设置外部平台定义ID
	* 作者:冯泽
	* 日期:2014年12月18日 下午1:53:08
	* @param value
	* </pre>
	 */
	public void setSYSTEM_FPI_DEF(long value)
	{
		this.SYSTEM_FPI_DEF = value;
		this.AddUpdateAttribute("SYSTEM_FPI_DEF");
	}
	/**
	 * 
	* <pre>
	* 任务:KYEEAPP-1155
	* 描述:获取外部平台定义ID
	* 作者:冯泽
	* 日期:2014年12月18日 下午1:53:15
	* @return
	* </pre>
	 */
	public long getSYSTEM_FPI_DEF()
	{
		return this.SYSTEM_FPI_DEF;
	}
	
	/**
	 * 
	 * 
	* <pre>
	* 任务:KYEEAPP-1155
	* 描述:设置账号
	* 作者:冯泽
	* 日期:2014年12月18日 下午1:53:19
	* @param value
	* </pre>
	 */
	public void setFPI_ACCOUNT(String value)
	{
		this.FPI_ACCOUNT = value;
		this.AddUpdateAttribute("FPI_ACCOUNT");
	}
	/**
	 * 
	* <pre>
	* 任务:KYEEAPP-1155
	* 描述:获取账号
	* 作者:冯泽
	* 日期:2014年12月18日 下午1:53:25
	* @return
	* </pre>
	 */
	public String getFPI_ACCOUNT()
	{
		return this.FPI_ACCOUNT;
	}
	/**
	 * 
	* <pre>
	* 任务:KYEEAPP-1155
	* 描述:设置密码
	* 作者:冯泽
	* 日期:2014年12月18日 下午1:53:29
	* @param value
	* </pre>
	 */
	public void setFPI_PASSWORD(String value)
	{
		this.FPI_PASSWORD = value;
		this.AddUpdateAttribute("FPI_PASSWORD");
	}
	
	/**
	 * 
	 * 
	* <pre>
	* 任务:KYEEAPP-1155
	* 描述:获取密码
	* 作者:冯泽
	* 日期:2014年12月18日 下午1:53:38
	* @return
	* </pre>
	 */
	public String getFPI_PASSWORD()
	{
		return this.FPI_PASSWORD;
	}
	
	/**
	 * 
	* <pre>
	* 任务:KYEEAPP-1155
	* 描述:设置账号状态
	* 作者:冯泽
	* 日期:2014年12月18日 下午1:53:42
	* @param value
	* </pre>
	 */
	public void setFPI_ACCOUNT_STATE(long value)
	{
		this.FPI_ACCOUNT_STATE = value;
		this.AddUpdateAttribute("FPI_ACCOUNT_STATE");
	}
	
	/**
	 * 
	 * 
	* <pre>
	* 任务:KYEEAPP-1155
	* 描述:获取账号状态
	* 作者:冯泽
	* 日期:2014年12月18日 下午1:53:47
	* @return
	* </pre>
	 */
	public long getFPI_ACCOUNT_STATE()
	{
		return this.FPI_ACCOUNT_STATE;
	}
	
	/***
	 * 
	* <pre>
	* 任务:KYEEAPP-1155
	* 描述:设置说明
	* 作者:冯泽
	* 日期:2014年12月18日 下午1:53:51
	* @param value
	* </pre>
	 */
	public void setCOMMENTS(String value)
	{
		this.COMMENTS = value;
		this.AddUpdateAttribute("COMMENTS");
	}
	
	/**
	 * 
	* <pre>
	* 任务:KYEEAPP-1155
	* 描述:获取说明
	* 作者:冯泽
	* 日期:2014年12月18日 下午1:53:55
	* @return
	* </pre>
	 */
	public String getCOMMENTS()
	{
		return this.COMMENTS;
	}
	
	/**
	 * 
	* <pre>
	* 任务:KYEEAPP-1155
	* 描述:设置创建者
	* 作者:冯泽
	* 日期:2014年12月18日 下午1:54:01
	* @param value
	* </pre>
	 */
	public void setCREATOR(String value)
	{
		this.CREATOR = value;
		this.AddUpdateAttribute("CREATOR");
	}
	/**
	 * 
	 * 
	* <pre>
	* 任务:KYEEAPP-1155
	* 描述:获取创建者
	* 作者:冯泽
	* 日期:2014年12月18日 下午1:54:07
	* @return
	* </pre>
	 */
	public String getCREATOR()
	{
		return this.CREATOR;
	}
	
	
	/**
	 * 
	* <pre>
	* 任务:KYEEAPP-1155
	* 描述:设置创建时间
	* 作者:冯泽
	* 日期:2014年12月18日 下午1:54:11
	* @param value
	* </pre>
	 */
	public void setCREATE_TIME(Date value)
	{
		this.CREATE_TIME = value;
		this.AddUpdateAttribute("CREATE_TIME");
	}
	
	/***
	 * 
	 * 
	* <pre>
	* 任务:KYEEAPP-1155
	* 描述:获取创建时间
	* 作者:冯泽
	* 日期:2014年12月18日 下午1:54:15
	* @return
	* </pre>
	 */
	public Date getCREATE_TIME()
	{
		return this.CREATE_TIME;
	}
	
	/**
	 * 
	* <pre>
	* 任务:KYEEAPP-1155
	* 描述:设置更新者
	* 作者:冯泽
	* 日期:2014年12月18日 下午1:54:19
	* @param value
	* </pre>
	 */
	public void setUPDATER(String value)
	{
		this.UPDATER = value;
		this.AddUpdateAttribute("UPDATER");
	}
	
	/**
	 * 
	* <pre>
	* 任务:KYEEAPP-1155
	* 描述:获取更新者
	* 作者:冯泽
	* 日期:2014年12月18日 下午1:54:24
	* @return
	* </pre>
	 */
	public String getUPDATER()
	{
		return this.UPDATER;
	}
	
	/**
	 * 
	 * 
	* <pre>
	* 任务:KYEEAPP-1155
	* 描述:设置更新时间
	* 作者:冯泽
	* 日期:2014年12月18日 下午1:54:29
	* @param value
	* </pre>
	 */
	public void setUPDATE_TIME(Date value)
	{
		this.UPDATE_TIME = value;
		this.AddUpdateAttribute("UPDATE_TIME");
	}
	
	/**
	 * 
	* <pre>
	* 任务:KYEEAPP-1155
	* 描述:获取更新时间
	* 作者:冯泽
	* 日期:2014年12月18日 下午1:54:34
	* @return
	* </pre>
	 */
	public Date getUPDATE_TIME()
	{
		return this.UPDATE_TIME;
	}
}

