package APP.Comm.Handler;

/** 
 当用户执行采集处理时，需要获取系统锁，每功能只能获取一个锁
 施建龙
 2013年6月6日14:20:58
 
*/
public class CollectDataLock
{
	//收入采集：INCOMES_LOCK
	//成本采集：COSTS_LOCK
	public String LOCK_KEY;
	public String sessionId;

	public String lockUserId;
	public String lockUserName;
	public java.util.Date lockTime = new java.util.Date(0);

	private boolean _status = false;


	protected final Object _syncMessage = new Object();


	public CollectDataLock(String lockKey)
	{
		LOCK_KEY = lockKey;
	}


	private boolean GetLockStatus()
	{
		return _status;
	}

	/** 
	 获取功能执行锁，如果获取成功，则返回true，否则返回false；
	 
	 @return 
	*/
	public final boolean RequestAppLock(String userId, String userName, String sessionId)
	{
		synchronized (_syncMessage)
		{
			if(_status)
			{
				return false;
			}
		}

		lockUserId = userId;
		lockUserName = userName;
		lockTime=new java.util.Date();
		this.sessionId = sessionId;
		_status = true;
		return _status;
	}


	public final void FreeAppLock()
	{
		_status = false;
	}


	public static void ClearAppLock(java.util.Map<String, CollectDataLock> LOCKS, String sessionId)
	{
//            IDictionary<string, CollectDataLock> LOCKS;
//
//            LOCKS = (IDictionary)Application["LOCKS"];

		CollectDataLock collectDataLock = null;

		java.util.Iterator ie = null;

		ie = LOCKS.values().iterator();

		while(ie.hasNext())
		{
			collectDataLock = (CollectDataLock)ie.next();

			if (sessionId.equals(collectDataLock.sessionId))
			{
				collectDataLock.FreeAppLock();
			}
		}


	}


}