package APP.Model.Base;

import java.util.Date;

import APP.Model.BaseEntity;

public class C_SYSTEM_FPI_DEF extends BaseEntity{
	private long SYSTEM_FPI_DEF;//外部平台定义ID
	private String FPI_NO;//外部平台编号
	private String FPI_NAME;//外部平台名称
	private long TOKEN_NUMS;//TOKEN个数
	private String COMMENTS;//说明
	private String CREATOR;//创建者
	private Date CREATE_TIME;//创建时间
	private String UPDATER;//更新者
	private Date UPDATE_TIME;//更新时间
	
	/**
	 * 
	* <pre>
	* 任务:KYEEAPP-1155
	* 描述:设置外部平台定义ID
	* 作者:冯泽
	* 日期:2014年12月18日 下午2:12:14
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
	* 日期:2014年12月18日 下午2:13:03
	* @return
	* </pre>
	 */
	public long getSYSTEM_FPI_DEF()
	{
		return this.SYSTEM_FPI_DEF;
	}
	/**
	 * 
	* <pre>
	* 任务:KYEEAPP-1155
	* 描述:设置外部平台编号
	* 作者:冯泽
	* 日期:2014年12月18日 下午2:14:31
	* @param value
	* </pre>
	 */
	public void setFPI_NO(String value)
	{
		this.FPI_NO = value;
		this.AddUpdateAttribute("FPI_NO");
	}
	/**
	 * 
	 * 
	* <pre>
	* 任务:KYEEAPP-1155
	* 描述:获取外部平台编号
	* 作者:冯泽
	* 日期:2014年12月18日 下午2:14:51
	* @return
	* </pre>
	 */
	public String getFPI_NO()
	{
		return this.FPI_NO;
	}
	/**
	 * 
	 * 
	* <pre>
	* 任务:KYEEAPP-1155
	* 描述:设置外部平台名称
	* 作者:冯泽
	* 日期:2014年12月18日 下午2:16:15
	* @param value
	* </pre>
	 */
	public void setFPI_NAME(String value)
	{
		this.FPI_NAME = value;
		this.AddUpdateAttribute("FPI_NAME");
	}
	/**
	 * 
	* <pre>
	* 任务:KYEEAPP-1155
	* 描述:获取外部平台名称
	* 作者:冯泽
	* 日期:2014年12月18日 下午2:16:23
	* @return
	* </pre>
	 */
	public String getFPI_NAME()
	{
		return this.FPI_NAME;
	}
	/**
	 * 
	* <pre>
	* 任务:KYEEAPP-1155
	* 描述:设置Token个数
	* 作者:冯泽
	* 日期:2014年12月18日 下午2:17:52
	* @param value
	* </pre>
	 */
	public void setTOKEN_NUMS(long value)
	{
		this.TOKEN_NUMS = value;
		this.AddUpdateAttribute("TOKEN_NUMS");
	}
	/**
	 * 
	 * 
	* <pre>
	* 任务:KYEEAPP-1155
	* 描述:获取Token个数
	* 作者:冯泽
	* 日期:2014年12月18日 下午2:17:56
	* @return
	* </pre>
	 */
	public long getTOKEN_NUMS()
	{
		return this.TOKEN_NUMS;
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
