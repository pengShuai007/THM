package APP.Comm.DataBase.Helper;

import java.util.List;

import APP.Model.BaseEntity;
 /**
 *  
 * 修改人：  ypf <br/>  
 * 修改时间：2014年10月29日 11:28 <br/>  
 * 修改备注：修改文件编译时报编码错误的提示<br/>
 * 任务号：KYEEAPP-692
 * @创建人 ypf
 * @版本
 */
public class AppEntitys extends AppDataResult {
	private java.util.List<BaseEntity> privateEntityList;

	public final List<BaseEntity> getEntityList() {
		return privateEntityList;
	}

	public final void setEntityList(List<BaseEntity> value) {
		privateEntityList = value;
	}
}