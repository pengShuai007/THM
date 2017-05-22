package APP.Comm.View;

import java.io.Serializable;
import java.util.Hashtable;

/**
 * Hrp view 上下文，默认存取HttpHandler的Context.request.Params
 * 
 * 施建龙
 */
  /**
 *  
 * 修改人：  ypf <br/>  
 * 修改时间：2014年10月29日 11:28 <br/>  
 * 修改备注：修改文件编译时报编码错误的提示<br/>
 * 任务号：KYEEAPP-692
 * @创建人 ypf
 * @版本
 */
public class AppContext implements Serializable {
	private Hashtable privateParams;

	public final Hashtable getParams() {
		return privateParams;
	}

	public final void setParams(Hashtable value) {
		privateParams = value;
	}
}