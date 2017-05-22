package org.kyee.core.framework.SysParamMgr.Impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.kyee.core.framework.SysParamMgr.service.ISysParamMgr;

import APP.Comm.DataBase.Helper.AbstractDataBase;
import APP.Comm.DataBase.Helper.DBFactory;
import APP.Comm.DataBase.Helper.IDataBase;
import APP.Comm.DataBase.Parameter.AppDbParameter;
import APP.Comm.Util.HLogger;

/*系统参数管理：实现参数管理接口 */
public class SysParamMgrImpl implements ISysParamMgr {
	
	// 接口：获得系统参数值
	public String getSysParamValue(String paramkey) throws Exception {
		HLogger.info("SysParamMgrImpl Function getSysParamValue begin!");
		String result = null;
		IDataBase baseDb = null;
		AbstractDataBase baseDB = null;
		Connection baseDbConnection = null;
		try {
			baseDb = DBFactory.CreateBaseDB();
			baseDB = (AbstractDataBase) baseDb;
			baseDB.OpenBase();
			baseDbConnection = baseDB.getConnection();
			baseDbConnection.setAutoCommit(true);
			StringBuffer sqlStr = new StringBuffer();
			sqlStr.append(" SELECT PARA_VALUE FROM SYS_PARAMETER ");
			sqlStr.append(" WHERE PARA_CODE = :PARA_CODE ");
			List<AppDbParameter> para = new ArrayList<AppDbParameter>();
			para.add(new AppDbParameter("PARA_CODE",paramkey));
			result = (String) baseDb.QueryObject(sqlStr.toString(), para);
		}catch (Exception e){
			HLogger.info("获取参数失败!");
			try {
				baseDbConnection.rollback();
			} catch (Exception e1) {
				HLogger.info(e1);
				throw new Exception(e1.getMessage());
			}
			throw new Exception(e.getMessage());
		}finally{
			try {
				baseDbConnection.commit();
				if (baseDB != null) {
					baseDB.Close();
				}
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		}
		HLogger.info("SysParamMgrImpl Function getSysParamValue end!");
		return result;
	}
	
	// 接口：获得每日走势中第二个时间参数：上次执行时间。精确到时分秒。
	public String getSysParamValues(String paramkey) throws Exception {
		HLogger.info("SysParamMgrImpl Function getSysParamValue begin!");
		String result = null;
		IDataBase baseDb = null;
		AbstractDataBase baseDB = null;
		Connection baseDbConnection = null;
		try {
			baseDb = DBFactory.CreateBaseDB();
			baseDB = (AbstractDataBase) baseDb;
			baseDB.OpenBase();
			baseDbConnection = baseDB.getConnection();
			baseDbConnection.setAutoCommit(true);
			StringBuffer sqlStr = new StringBuffer();
			sqlStr.append(" SELECT PARA_VALUES FROM SYS_PARAMETER ");
			sqlStr.append(" WHERE PARA_CODE = :PARA_CODE ");
			List<AppDbParameter> para = new ArrayList<AppDbParameter>();
			para.add(new AppDbParameter("PARA_CODE",paramkey));
			result = (String) baseDb.QueryObject(sqlStr.toString(), para);
		}catch (Exception e){
			HLogger.info("获取参数失败!");
			try {
				baseDbConnection.rollback();
			} catch (Exception e1) {
				HLogger.info(e1);
				throw new Exception(e1.getMessage());
			}
			throw new Exception(e.getMessage());
		}finally{
			try {
				baseDbConnection.commit();
				if (baseDB != null) {
					baseDB.Close();
				}
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		}
		HLogger.info("SysParamMgrImpl Function getSysParamValue end!");
		return result;
	}
}
