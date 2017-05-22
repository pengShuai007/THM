package APP.Comm.BLL;

/** 
 functions：用于抛出记录插入日志到数据库的时候，产生的异常
 
*/
public class LogException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 
	 构造方法
	 
	 @param message 业务异常消息
	 @param innerException 上级异常对象
	*/
	public LogException(String message, Exception innerException)
	{
		super(message, innerException);
	}
}