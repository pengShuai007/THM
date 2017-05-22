package APP.Comm.Util;

import java.util.concurrent.ExecutorService;

/**
 * 任务：KYEEAPPC-2930
 * 描述：单独线程异步操作
 * 作者：李君强	时间：2015年8月5日18:41:37
 */
public class ThreadPoolUtil {
	//用户访问记录发布消息线程池
	private static ExecutorService userRecordThreadPool = null;
	private static ExecutorService userMonitorthreadPool = null;

	public static void setUserRecordThreadPool(
			ExecutorService threadPool) {
		ThreadPoolUtil.userRecordThreadPool = threadPool;
	}
	
	public static void setUserMonitorThreadPool(
			ExecutorService threadPool) {
		ThreadPoolUtil.userMonitorthreadPool = threadPool;
	}
	
	//用户访问记录发布消息处理执行
	public static void userRecordExec(Runnable command){
		if(isExecutorAlive(userRecordThreadPool)){
			userRecordThreadPool.execute(command);
		}
	}
	
	//用户操作监控处理执行
	public static void userMonitorExec(Runnable command){
		if(isExecutorAlive(userMonitorthreadPool)){
			userMonitorthreadPool.execute(command);
		}
	}
	
	private static boolean isExecutorAlive(ExecutorService executorService){
		return executorService!=null && !executorService.isShutdown();
	}

	/**
	 * 关闭所有线程池执行者
	 */
	public static void shutdownAllThreadPool() {
		if (userRecordThreadPool != null) {
			userRecordThreadPool.shutdown();
		}
	}

}
