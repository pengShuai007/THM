package APP.Model.Base;

import java.util.Date;

import APP.Model.BaseEntity;

public class C_SYSTEM_FPI_HOSPITAL extends BaseEntity{

	
	private long SYSTEM_FPI_HOSPITAL; //外部平台医院ID
	private long HOSPITAL_ID; //医院ID
	private long SYSTEM_FPI_DEF; //外部平台定义ID
	private String COMMENTS;//说明
	private String CREATOR;//创建者
	private Date CREATE_TIME;//创建时间
	private String UPDATER;//更新者
	private Date UPDATE_TIME;//更新时间
	
	
	/**
	 * 
	* <pre>
	* 任务:KYEEAPP-1155
	* 描述:设置外部平台医院ID
	* 作者:冯泽
	* 日期:2014年12月18日 下午2:28:26
	* @param value
	* </pre>
	 */
	public void setSYSTEM_FPI_HOSPITAL(long value)
	{
		this.SYSTEM_FPI_HOSPITAL = value;
		this.AddUpdateAttribute("SYSTEM_FPI_HOSPITAL");
	}
	/**
	 * 
	* <pre>
	* 任务:KYEEAPP-1155
	* 描述:获取外部平台医院ID
	* 作者:冯泽
	* 日期:2014年12月18日 下午2:28:33
	* @return
	* </pre>
	 */
	public long getSYSTEM_FPI_HOSPITAL()
	{
		return this.SYSTEM_FPI_HOSPITAL;
	}
	
	/**
	 * 
	* <pre>
	* 任务:KYEEAPP-1155
	* 描述:设置医院ID
	* 作者:冯泽
	* 日期:2014年12月18日 下午2:28:37
	* @param value
	* </pre>
	 */
	public void setHOSPITAL_ID(long value)
	{
		this.HOSPITAL_ID = value;
		this.AddUpdateAttribute("HOSPITAL_ID");
	}
	/**
	 * 
	* <pre>
	* 任务:KYEEAPP-1155
	* 描述:获取医院ID
	* 作者:冯泽
	* 日期:2014年12月18日 下午2:28:41
	* @return
	* </pre>
	 */
	public long getHOSPITAL_ID()
	{
		return this.HOSPITAL_ID;
	}
	/**
	 * 
	 * 
	* <pre>
	* 任务:KYEEAPP-1155
	* 描述:设置外部平台定义ID
	* 作者:冯泽
	* 日期:2014年12月18日 下午2:28:45
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
	 * 
	* <pre>
	* 任务:KYEEAPP-1155
	* 描述:获取外部平台定义ID
	* 作者:冯泽
	* 日期:2014年12月18日 下午2:28:49
	* @return
	* </pre>
	 */
	public long getSYSTEM_FPI_DEF()
	{
		return this.SYSTEM_FPI_DEF;
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
