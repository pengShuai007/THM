package APP.Comm.DataBase.Helper;
 /**
 *  
 * 修改人：  ypf <br/>  
 * 修改时间：2014年10月29日 11:28 <br/>  
 * 修改备注：修改文件编译时报编码错误的提示<br/>
 * 任务号：KYEEAPP-692
 * @创建人 ypf
 * @版本
 */
public class AppDataResult {
	private long privateCount;

	public final long getCount() {
		return privateCount;
	}

	public final void setCount(long value) {
		privateCount = value;
	}

	/**
	 * comments:为方便DotNet迁移，提供此方法
	 * 
	 * sjl modify 2013-10-13上午10:51:46
	 */
	public long size() {
		return privateCount;
	}

}