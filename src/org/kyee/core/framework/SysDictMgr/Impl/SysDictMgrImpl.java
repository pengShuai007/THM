package org.kyee.core.framework.SysDictMgr.Impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.kyee.core.framework.SysDictMgr.base.SYS_CODE_ITEM;
import org.kyee.core.framework.SysDictMgr.service.ISysDictMgr;

import APP.Comm.DataBase.Helper.AbstractDataBase;
import APP.Comm.DataBase.Helper.AppDataTable;
import APP.Comm.DataBase.Helper.DBFactory;
import APP.Comm.DataBase.Helper.IDataBase;
import APP.Comm.DataBase.Parameter.AppDbParameter;
import APP.Comm.Util.HLogger;
import APP.Model.BaseEntity;

/* 
 * 功能：系统字典管理 
 * 接口：系统字典对外查询接口等
 * */
public class SysDictMgrImpl implements ISysDictMgr{

	// 获得指定字典项值
	public String getSysDictItemValue(String dictId, String key) throws Exception {
		HLogger.info("SysDictMgrImpl Function getSysDictItemValue begin!");
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
			sqlStr.append(" SELECT ITEM_VALUE FROM SYS_CODE_ITEM ");
			sqlStr.append(" WHERE VALIDATE_FLAG = 1 AND DICT_ID = :DICT_ID ");
			sqlStr.append(" AND ITEM_CODE = :ITEM_CODE ");
			List<AppDbParameter> para = new ArrayList<AppDbParameter>();
			para.add(new AppDbParameter("DICT_ID",dictId));
			para.add(new AppDbParameter("ITEM_CODE",key));
			result = (String) baseDb.QueryObject(sqlStr.toString(), para);
		}catch (Exception e){
			HLogger.info("获取字典失败!");
			try {
				baseDbConnection.rollback();
			}catch (Exception e1){
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
		HLogger.info("SysDictMgrImpl Function getSysDictItemValue end!");
		return result;
	}

	// 获得指定字典
	public List<BaseEntity> getSysDictItem(String dictId) throws Exception {
		HLogger.info("SysDictMgrImpl Function getSysDictItem begin!");
		List<BaseEntity> result = new ArrayList<BaseEntity>();
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
			sqlStr.append(" SELECT * FROM SYS_CODE_ITEM ");
			sqlStr.append(" WHERE VALIDATE_FLAG = 1 AND  DICT_ID = :DICT_ID ");
			List<AppDbParameter> para = new ArrayList<AppDbParameter>();
			para.add(new AppDbParameter("DICT_ID",dictId));
			result =  baseDb.QueryEntity(SYS_CODE_ITEM.class, sqlStr.toString(), para).getEntityList();
		} catch (Exception e) {
			HLogger.info("获取字典失败!");
			try {
				baseDbConnection.rollback();
			}catch (Exception e1){
				HLogger.info(e1);
				throw new Exception(e1.getMessage());
			}
			throw new Exception(e.getMessage());
		} finally {
			try {
				baseDbConnection.commit();
				if (baseDB != null) {
					baseDB.Close();
				}
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		}
		HLogger.info("SysDictMgrImpl Function getSysDictItem end!");
		return result;
	}
	
	// 获得指定字典
	public AppDataTable getSysDictItems(String dictId) throws Exception {
		HLogger.info("SysDictMgrImpl Function getSysDictItems begin!");
		AppDataTable result = new AppDataTable();
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
			sqlStr.append(" SELECT * FROM SYS_CODE_ITEM ");
			sqlStr.append(" WHERE VALIDATE_FLAG = 1 AND DICT_ID = :DICT_ID ");
			List<AppDbParameter> para = new ArrayList<AppDbParameter>();
			para.add(new AppDbParameter("DICT_ID", dictId));
			result = baseDb.Query(sqlStr.toString(), para);
		} catch (Exception e) {
			HLogger.info("获取字典失败!");
			try {
				baseDbConnection.rollback();
			} catch (Exception e1) {
				HLogger.info(e1);
				throw new Exception(e1.getMessage());
			}
			throw new Exception(e.getMessage());
		} finally {
			try {
				baseDbConnection.commit();
				if (baseDB != null) {
					baseDB.Close();
				}
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		}
		HLogger.info("SysDictMgrImpl Function getSysDictItems end!");
		return result;
	}
}
