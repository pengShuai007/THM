package APP.Model;

public class GetTokenReturn {
	private String token; //Token
	private String resultState; //结果标识 0：失败，1：成功
	private String resultCode;//提示信息Code
	private String resultMsg;//提示信息
	/**
	 * 
	* <pre>
	* 任务:KYEEAPP-1155
	* 描述:设置Token
	* 作者:冯泽
	* 日期:2014年12月19日 下午6:24:47
	* @param value
	* </pre>
	 */
	public void  settoken(String value){
		this.token = value;
	}
	/**
	 * 
	* <pre>
	* 任务:KYEEAPP-1155
	* 描述:获取Token
	* 作者:冯泽
	* 日期:2014年12月19日 下午6:25:01
	* @return
	* </pre>
	 */
	public String gettoken()
	{
		return this.token;
	}
	/**
	 * 
	* <pre>
	* 任务:KYEEAPP-1155
	* 描述:设置结果标识 0：失败，1：成功
	* 作者:冯泽
	* 日期:2014年12月19日 下午6:25:04
	* @param value
	* </pre>
	 */
	public void setresultState(String value)
	{
		this.resultState = value;
	}
	/**
	 * 
	 * 
	* <pre>
	* 任务:KYEEAPP-1155
	* 描述:获取结果标识 0：失败，1：成功
	* 作者:冯泽
	* 日期:2014年12月19日 下午6:27:09
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
	* 描述:设置提示信息Code
	* 作者:冯泽
	* 日期:2014年12月19日 下午6:25:08
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
	* 描述:获取提示信息Code
	* 作者:冯泽
	* 日期:2014年12月19日 下午6:25:11
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
	* 日期:2014年12月19日 下午6:25:15
	* @param value
	* </pre>
	 */
	public void setresultMsg(String value)
	{
		this.resultMsg = value;
	}
	/**
	 * 
	 * 
	* <pre>
	* 任务:KYEEAPP-1155
	* 描述:获取提示信息
	* 作者:冯泽
	* 日期:2014年12月19日 下午6:25:19
	* @return
	* </pre>
	 */
	public String getresultMsg()
	{
		return this.resultMsg;
	}
}
