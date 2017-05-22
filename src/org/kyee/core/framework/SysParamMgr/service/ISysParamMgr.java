package org.kyee.core.framework.SysParamMgr.service;

import org.kyee.core.framework.SysParamMgr.base.*;

import APP.Comm.DataBase.Helper.IDataBase;
/* 功能：提供系统参数管理对外接口 */
public interface ISysParamMgr {
	//查询接口：获得指定参数值
	public String getSysParamValue(String key) throws Exception;
	
	public String getSysParamValues(String key) throws Exception;
}
