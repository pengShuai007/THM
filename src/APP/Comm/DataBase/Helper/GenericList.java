package APP.Comm.DataBase.Helper;

import APP.Comm.BLL.BaseBllException;
import APP.Comm.DataBase.DotNet.DataTable;
import APP.Comm.Util.*;

//模块编号：<模块编号，可以引用系统设计中的模块编号>
//作用：<使用反射机制，创建并返回一个新的对象>
//作者：张宝锋
//编写日期：<2012-12-25>
 /**
 *  
 * 修改人：  ypf <br/>  
 * 修改时间：2014年10月29日 11:28 <br/>  
 * 修改备注：修改文件编译时报编码错误的提示<br/>
 * 任务号：KYEEAPP-692
 * @创建人 ypf
 * @版本
 */
public class GenericList<T> extends java.util.ArrayList<T>
{
	/** 
	 反射并返回一个新的对象实例
	 
	 @param dt 数据集合
	 @param type 实例类型
	//创建者：张宝锋
	//创建时间：2012-12-27
	//添加参数bool readOnly=true
	//施建龙
	//2013年3月4日11:38:38
	 * @throws BaseBllException 
	*/
	public GenericList(DataTable dt, java.lang.Class type, boolean readOnly) throws BaseBllException
	{

		this.addAll((java.util.List<T>)EntityUtil.CreateEntityList(dt, type, readOnly));
		/**反射方法移动到外部工具类
		 施建龙
		 2013年1月9日 07:48:41
		*/


	}

	/** 
	 反射并返回一个新的对象实例
	 
	 @param dt 数据集合
	 @param f 实例名称
	//创建者：张宝锋
	//创建时间：2012-12-27
	 * @throws ClassNotFoundException 
	 * @throws BaseBllException 
	*/
	public GenericList(DataTable dt, String entityName) throws ClassNotFoundException, BaseBllException
	{
		java.lang.Class type = java.lang.Class.forName(entityName); //获取指定名称的类型

		/**反射方法移动到外部工具类
		 施建龙
		 2013年1月9日 07:48:41
		*/
		this.addAll((java.util.List<T>)EntityUtil.CreateEntityList(dt, type,true));

	}
}