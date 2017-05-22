package model.base.ext;


import model.base.M_SYSTEM_EXCEPTION_RECORDS;

/**
*
*<pre>
* 任务: 
* 描述：记录系统异常表实体扩展类
* 作者：Administrator
* 时间：2016年12月08日 18:42:51
* 类名：M_SYSTEM_EXCEPTION_RECORDS_EXT
*
* <pre>
*/
public class M_SYSTEM_EXCEPTION_RECORDS_EXT extends M_SYSTEM_EXCEPTION_RECORDS {

	private static final long serialVersionUID = 1L;

	@Override
	public String GetId() {
		return getID() +"";
	}
}
