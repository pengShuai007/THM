package APP.Comm.DataBase.Helper;

import APP.Comm.DataBase.DotNet.DataTable;
 /**
 *  
 * 修改人：  ypf <br/>  
 * 修改时间：2014年10月29日 11:28 <br/>  
 * 修改备注：修改文件编译时报编码错误的提示<br/>
 * 任务号：KYEEAPP-692
 * @创建人 ypf
 * @版本
 */
public class AppDataTable extends AppDataResult {

	public AppDataTable() {
		this.setDataTable(new DataTable());
	}

	/**
	 * comments:便于平台迁移，修改为public修饰符
	 * 
	 * sjl modify 2013-10-11下午1:59:54
	 */
	public DataTable DataTable;

	public final DataTable getDataTable() {
		return DataTable;
	}

	public final void setDataTable(DataTable value) {
		DataTable = value;
	}

	
}