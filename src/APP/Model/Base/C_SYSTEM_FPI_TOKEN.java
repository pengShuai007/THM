package APP.Model.Base;

import java.util.Date;

import APP.Model.BaseEntity;

public class C_SYSTEM_FPI_TOKEN extends BaseEntity{

	private long SYSTEM_FPI_TOKEN_ID;//外部平台令牌ID
	private long SYSTEM_FPI_ACCOUNT_ID;//外部平台帐号ID
	private Date TOKEN_DATE;//TOKEN日期
	private String TOKEN;//
	private Date TOKEN_END_TIME;//TOKEN截止时间
	private long TOKEN_STATE;//TOKEN状态
	private String COMMENTS;//说明
	private String CREATOR;//创建者
	private Date CREATE_TIME;//创建时间
	private String UPDATER;//更新者
	private Date UPDATE_TIME;//更新时间
	
	
	/**
	 * 
	 * 
	* <pre>
	* 任务:KYEEAPP-1155
	* 描述:设置外部平台令牌ID
	* 作者:冯泽
	* 日期:2014年12月18日 下午2:41:04
	* @param value
	* </pre>
	 */
	public void setSYSTEM_FPI_TOKEN_ID(long value)
	{
		this.SYSTEM_FPI_TOKEN_ID = value;
		this.AddUpdateAttribute("SYSTEM_FPI_TOKEN_ID");
	}
	/**
	 * 
	* <pre>
	* 任务:KYEEAPP-1155
	* 描述:获取外部平台令牌ID
	* 作者:冯泽
	* 日期:2014年12月18日 下午2:41:08
	* @return
	* </pre>
	 */
	public long getSYSTEM_FPI_TOKEN_ID()
	{
		return this.SYSTEM_FPI_TOKEN_ID;
	}
	/**
	 * 
	* <pre>
	* 任务:KYEEAPP-1155
	* 描述:设置外部平台帐号ID
	* 作者:冯泽
	* 日期:2014年12月18日 下午2:41:13
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
	 * 
	* <pre>
	* 任务:KYEEAPP-1155
	* 描述:获取外部平台帐号ID
	* 作者:冯泽
	* 日期:2014年12月18日 下午2:41:18
	* @return
	* </pre>
	 */
	public long getSYSTEM_FPI_ACCOUNT_ID()
	{
		return this.SYSTEM_FPI_ACCOUNT_ID;
	}
	/**
	 * 
	 * 
	* <pre>
	* 任务:KYEEAPP-1155
	* 描述:设置TOKEN日期
	* 作者:冯泽
	* 日期:2014年12月18日 下午2:41:22
	* @param value
	* </pre>
	 */
	public void setTOKEN_DATE(Date value)
	{
		this.TOKEN_DATE = value;
		this.AddUpdateAttribute("TOKEN_DATE");
	}
	/**
	 * 
	* <pre>
	* 任务:KYEEAPP-1155
	* 描述:获取TOKEN日期
	* 作者:冯泽
	* 日期:2014年12月18日 下午2:41:26
	* @return
	* </pre>
	 */
	public Date getTOKEN_DATE()
	{
		return this.TOKEN_DATE;
	}
	/**
	 * 
	* <pre>
	* 任务:KYEEAPP-1155
	* 描述:设置Token
	* 作者:冯泽
	* 日期:2014年12月18日 下午2:41:31
	* @param value
	* </pre>
	 */
	public void setTOKEN(String value)
	{
		this.TOKEN = value;
		this.AddUpdateAttribute("TOKEN");
	}
	/**
	 * 
	* <pre>
	* 任务:KYEEAPP-1155
	* 描述:获取Token
	* 作者:冯泽
	* 日期:2014年12月18日 下午2:41:35
	* @return
	* </pre>
	 */
	public String getTOKEN()
	{
		return this.TOKEN;
	}
	/**
	 * 
	* <pre>
	* 任务:KYEEAPP-1155
	* 描述:设置Token截止时间
	* 作者:冯泽
	* 日期:2014年12月18日 下午2:41:39
	* @param value
	* </pre>
	 */
	public void setTOKEN_END_TIME(Date value)
	{
		this.TOKEN_END_TIME = value;
		this.AddUpdateAttribute("TOKEN_END_TIME");
	}
	/**
	 * 
	* <pre>
	* 任务:KYEEAPP-1155
	* 描述:获取Token截止时间
	* 作者:冯泽
	* 日期:2014年12月18日 下午2:41:44
	* @return
	* </pre>
	 */
	public Date getTOKEN_END_TIME()
	{
		return this.TOKEN_END_TIME;
	}
	/**
	 * 
	* <pre>
	* 任务:KYEEAPP-1155
	* 描述:设置Token状态
	* 作者:冯泽
	* 日期:2014年12月18日 下午2:41:48
	* @param value
	* </pre>
	 */
	public void setTOKEN_STATE(long value)
	{
		this.TOKEN_STATE = value;
		this.AddUpdateAttribute("TOKEN_STATE");
	}
	/**
	 * 
	* <pre>
	* 任务:KYEEAPP-1155
	* 描述:获取Token状态
	* 作者:冯泽
	* 日期:2014年12月18日 下午2:41:52
	* @return
	* </pre>
	 */
	public long getTOKEN_STATE()
	{
		return this.TOKEN_STATE;
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
