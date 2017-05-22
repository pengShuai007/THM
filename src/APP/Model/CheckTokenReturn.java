package APP.Model;

public class CheckTokenReturn {
	private String resultState;//结果标识 0：失败，1：成功
	private String resultCode;//提示信息Code
	private String resultMsg;//提示信息来源
	private String resultResource;//来源
	/**
	 * 
	* <pre>
	* 任务:KYEEAPP-1155
	* 描述:设置结果标识
	* 作者:冯泽
	* 日期:2014年12月19日 下午4:47:50
	* @param value
	* </pre>
	 */
	public void setresultState(String value)
	{
		this.resultState = value;
	}
	/**
	 * 
	* <pre>
	* 任务:KYEEAPP-1155
	* 描述:获取结果标识
	* 作者:冯泽
	* 日期:2014年12月19日 下午4:48:11
	* @return
	* </pre>
	 */
	public String getresultState()
	{
		return this.resultState;
	}
	/**
	 * 
	* <pre>
	* 任务:KYEEAPP-1155
	* 描述:设置提示信息编码
	* 作者:冯泽
	* 日期:2014年12月19日 下午4:48:16
	* @param value
	* </pre>
	 */
	public void setresultCode(String value)
	{
		this.resultCode = value;
	}
	/**
	 * 
	* <pre>
	* 任务:KYEEAPP-1155
	* 描述:获取提信息编码
	* 作者:冯泽
	* 日期:2014年12月19日 下午4:50:23
	* @return
	* </pre>
	 */
	public String getresultCode()
	{
		return this.resultCode;
	}
	/**
	 * 
	* <pre>
	* 任务:KYEEAPP-1155
	* 描述:设置提示信息
	* 作者:冯泽
	* 日期:2014年12月19日 下午4:48:20
	* @param value
	* </pre>
	 */
	public void setresultMsg(String value)
	{
		this.resultMsg = value;
	}
	/**
	 * 
	* <pre>
	* 任务:
	* 描述:获取提示信息
	* 作者:冯泽
	* 日期:2014年12月19日 下午4:50:59
	* @return
	* </pre>
	 */
	public String getresultMsg()
	{
		return this.resultMsg;
	}
	/**
	 * 
	* <pre>
	* 任务:KYEEAPP-1155
	* 描述:设置信息来源，外部平台定义ID
	* 作者:冯泽
	* 日期:2014年12月19日 下午4:48:24
	* @param value
	* </pre>
	 */
	public void setresultResource(String value)
	{
		this.resultResource = value;
	}
	/**
	 * 
	* <pre>
	* 任务:KYEEAPP-1155
	* 描述:获取来源信息，外部平台定义ID
	* 作者:冯泽
	* 日期:2014年12月19日 下午4:48:28
	* @return
	* </pre>
	 */
	public String getresultResource()
	{
		return this.resultResource;
	}
}
