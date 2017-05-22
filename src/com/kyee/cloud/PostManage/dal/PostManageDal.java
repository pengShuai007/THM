package com.kyee.cloud.PostManage.dal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.base.ext.SYS_MENU_EXT;
import model.base.ext.SYS_USER_EXT;
import APP.Comm.BLL.BaseBllException;
import APP.Comm.DataBase.DotNet.DataTable;
import APP.Comm.DataBase.Helper.AppDataTable;
import APP.Comm.DataBase.Helper.IDataBase;
import APP.Comm.DataBase.Parameter.AppDbParameter;
import APP.Comm.Util.DotNetToJavaStringHelper;
import APP.Comm.Util.HLogger;
import APP.Comm.View.GridRequestParameters;
import APP.Model.BaseEntity;

public class PostManageDal {
	public  int initGrant(IDataBase baseDb)throws BaseBllException {
		HLogger.info("PostManageDal Function QueryPost Start!");
		List<AppDbParameter> param = new ArrayList<AppDbParameter>();
		String sqlStr = "DELETE FROM  sys_post_perm   WHERE menu_code NOT IN (SELECT menu_code FROM sys_menu)";
		return baseDb.Delete(sqlStr, null);
		//HLogger.info("PostManageDal Function QueryPost End!");
		//return result;
	}
	public  AppDataTable QueryPost(IDataBase baseDb, String postText, GridRequestParameters gridRequestParameters)throws BaseBllException {
		HLogger.info("PostManageDal Function QueryPost Start!");
		List<AppDbParameter> param = new ArrayList<AppDbParameter>();
		String sqlStr = " SELECT * FROM SYS_POST_DICT WHERE 1=1 ";
		if(!DotNetToJavaStringHelper.isNullOrEmpty(postText)) {
			sqlStr += "AND ( POST_CODE LIKE :POSTCODEORNAME OR POST_NAME LIKE :POSTCODEORNAME )";
			param.add(new AppDbParameter("POSTCODEORNAME","%"+postText+"%"));
		}
		sqlStr += " ORDER BY POST_CODE";
//		DataTable result = baseDb.Query(sqlStr, param).getDataTable();
		return baseDb.QueryByPage(sqlStr, "POST_ID", param, gridRequestParameters);
		//HLogger.info("PostManageDal Function QueryPost End!");
		//return result;
	}

    public List<BaseEntity> QueryMenuGrant(IDataBase baseDb) throws BaseBllException {
    	HLogger.info("PostManageDal Function QueryGrant Start!");
        String sqlStr = "SELECT * FROM SYS_MENU WHERE C_S_MENU_ID IS NOT NULL ";
        List<BaseEntity> result = baseDb.QueryEntity(SYS_MENU_EXT.class, sqlStr, null).getEntityList();
        HLogger.info("PostManageDal Function QueryGrant End!");
        return result;
    }
    
    public List<BaseEntity> QueryRootGrant(IDataBase baseDb) throws BaseBllException {
    	HLogger.info("PostManageDal Function QueryGrant Start!");
        String sqlStr = "SELECT * FROM SYS_MENU WHERE MENU_ID = 0 ";
        List<BaseEntity> result = baseDb.QueryEntity(SYS_MENU_EXT.class, sqlStr, null).getEntityList();
        HLogger.info("PostManageDal Function QueryGrant End!");
        return result;
    }
    
    public List<BaseEntity> QueryMenuByPostId(IDataBase baseDb, String postId) throws BaseBllException {
        String sqlStr = "SELECT MENU_ID,MENU_NAME FROM  SYS_MENU WHERE MENU_CODE "
        		+ "IN (SELECT MENU_CODE FROM SYS_POST_PERM  WHERE POST_ID = :POST_ID ) ";
        List<AppDbParameter> parameters = new ArrayList<AppDbParameter>();
        parameters.add(new AppDbParameter("POST_ID", postId));
        return baseDb.QueryEntity(SYS_MENU_EXT.class, sqlStr, parameters).getEntityList();
    }

	public int DeletePostGrant(IDataBase baseDB, String postId) throws BaseBllException {
    	HLogger.info("PostManageDal Function DeletePostGrant Start!");
    	List<AppDbParameter> parameter = new ArrayList<AppDbParameter>();
        String sqlStr = "DELETE FROM SYS_POST_PERM WHERE POST_ID = :POST_ID ";
        parameter.add(new AppDbParameter("POST_ID", postId));
        int result = baseDB.Delete(sqlStr, parameter);
        HLogger.info("PostManageDal Function DeletePostGrant End!");
        return result;
	}

	public int DeletePostUser(IDataBase baseDB, String postId) throws BaseBllException {
    	HLogger.info("PostManageDal Function DeletePostUser Start!");
    	List<AppDbParameter> parameter = new ArrayList<AppDbParameter>();
        String sqlStr = "DELETE FROM SYS_DEPT_POST_DETAIL WHERE INFO_ID IN"
        		+ " (SELECT INFO_ID FROM SYS_DEPT_POST_INFO WHERE POST_ID = :POST_ID ) ";
        parameter.add(new AppDbParameter("POST_ID", postId));
        int result = baseDB.Delete(sqlStr, parameter);
        HLogger.info("PostManageDal Function DeletePostUser End!");
        return result;
	}
	
	public int DeleteDeptPost(IDataBase baseDB, String postId) throws BaseBllException {
    	HLogger.info("PostManageDal Function DeleteDeptPost Start!");
    	List<AppDbParameter> parameter = new ArrayList<AppDbParameter>();
        String sqlStr = "DELETE FROM SYS_DEPT_POST_INFO WHERE POST_ID = :POST_ID ";
        parameter.add(new AppDbParameter("POST_ID", postId));
        int result = baseDB.Delete(sqlStr, parameter);
        HLogger.info("PostManageDal Function DeleteDeptPost End!");
        return result;
	}
	
	public int DeletePost(IDataBase baseDB, String postId) throws BaseBllException {
    	HLogger.info("PostManageDal Function DeleteDeptPost Start!");
    	List<AppDbParameter> parameter = new ArrayList<AppDbParameter>();
        String sqlStr = "DELETE FROM SYS_POST_DICT WHERE POST_ID = :POST_ID ";
        parameter.add(new AppDbParameter("POST_ID", postId));
        int result = baseDB.Delete(sqlStr, parameter);
        HLogger.info("PostManageDal Function DeleteDeptPost End!");
        return result;
	}

	public int SaveGrant(IDataBase baseDB, String postId, String menuCode,
			SYS_USER_EXT appuser) throws BaseBllException {
    	HLogger.info("PostManageDal Function SaveGrant Start!");
    	List<AppDbParameter> param = new ArrayList<AppDbParameter>();
    	String sqlStr = "INSERT INTO SYS_POST_PERM (POST_ID,MENU_CODE,OPERATOR,OPERATOR_NAME,OPERATOR_DATE) "
    			+ "VALUES(:POST_ID ,:MENU_CODE, :OPERATOR ,:OPERATOR_NAME ,:OPERATOR_DATE ) ";
        param.add(new AppDbParameter("POST_ID", postId));
        param.add(new AppDbParameter("MENU_CODE", menuCode));
        param.add(new AppDbParameter("OPERATOR", appuser.getUSER_CODE()));
        param.add(new AppDbParameter("OPERATOR_NAME", appuser.getUSER_NAME()));
        param.add(new AppDbParameter("OPERATOR_DATE", new Date()));
        int result = baseDB.Save(sqlStr, param);
        HLogger.info("PostManageDal Function SaveGrant End!");
        return result;
	}

}
