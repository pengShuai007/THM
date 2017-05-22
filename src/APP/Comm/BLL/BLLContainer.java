package APP.Comm.BLL;

import java.sql.Connection;
import java.sql.SQLException;

import APP.Comm.DataBase.Helper.AbstractDataBase;
import APP.Comm.DataBase.Helper.IDataBase;
import APP.Comm.Util.HLogger;
import APP.Comm.Util.ObjectFactory;

/**
 * 业务处理基类 在Hanler层调用BLL层时，严禁直接调用，必须通过本业务处理基类调用，以便统一控制数据库状态和事务 作者：宋建康
 * 创建时间：2012.12.26
 */
public class BLLContainer {
	public LogException LogException; 
	public RuntimeException sysException;
	public String moduleName;
	public IDataBase BaseDB;
	
	public BLLContainer() {
	}

	public BLLContainer(IDataBase baseDb) {
		this.BaseDB = baseDb;
	}

	/**
	 * 说明：最终委托方法
	 * 
	 * @param BllProcess
	 * @return
	 */
	public final Object DoProcess(Object owner, String method)
			throws BaseBllException {
	    HLogger.info("1111111111111111111+begin>>>"+owner);
        HLogger.info("1111111111111111111+begin+method>>>"+method);
		// 定义返回参数
		Object result;
		// 定义运维数据库连接
		Connection baseDbConnection = null;
		AbstractDataBase baseDB = (AbstractDataBase) BaseDB;
		
		try {
			// /#region 打开数据库连接
			// 打开数据源
			//Edit start KYEEAPPMAINTENANCE-45  运维数据库独立 liuxingping 2015年1月16日11:48:45
			if(baseDB != null) {
				if(baseDB.NeedOpen()) {
					baseDB.OpenBase();
					baseDbConnection = baseDB.getConnection();
					if(baseDB.NeedTransaction()) {
						baseDbConnection.setAutoCommit(false);
					}else {
						baseDbConnection.setAutoCommit(true);
					}
				}
			}
			
			((BaseBLL) owner).setBllContainer(this);
			// 执行委托函数
			
			HLogger.info("1111111111111111111-->"+owner);
			HLogger.info("1111111111111111111method-->"+method);
			result = bllProcess(owner, method);
			
			// /#region 提交事务
			if (baseDB != null && baseDB.NeedTransaction()) {
				baseDbConnection.commit();
			}
			// /#endregion
			// 返回委托函数处理结果);
			return result;
		} catch (Exception e) {
			// /#region 回滚事务
			try {
				if (baseDB != null && baseDB.NeedTransaction()) {
					baseDB.getConnection().rollback();
				}
			} catch (SQLException sqle) {
				//HLogger.error(sqle);
				throw new BaseBllException(e.getMessage() + "\n" + sqle.getMessage());
			}
			// 记录错误信息至日志文件,误删此段补充代码
			// 施建龙
			// 2013年3月16日12:19:40
			if (!(e instanceof BaseBllException)) {
				throw new BaseBllException(e);
			}
			// Edit Add <<< zhangbf 2013-10-8 13:58:27
			// /#endregion
			// /#region 数据库异常处理
			// 说明：数据库异常处理，目前只针对外键异常进行处理
			// 作者：张宝锋
			// 创建日期：2013-03-11
			// /#endregion
			throw new BaseBllException(e);
		} finally {
			// 关闭数据库
			if (baseDB != null) {
				baseDB.Close();
			}
			// /#region 未打开事务预警
			if (baseDB != null && baseDB.getIfUpdated()
					&& !baseDB.NeedTransaction()) {
				HLogger.Warn("执行BLL层的" + method + "方法进行了APP数据库数据物理操作，但未开启事务！");
			}
		}
	}

	/**
	 * 通过反射机制调用方法 sjl modify 2013-10-4上午10:08:22
	 * 
	 * @throws BaseBllException
	 */
	private Object bllProcess(Object object, String methodName)
			throws BaseBllException {
		return ObjectFactory.execMethod(object, methodName);
	}

}