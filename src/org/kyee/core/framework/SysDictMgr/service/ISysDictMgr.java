package org.kyee.core.framework.SysDictMgr.service;

import java.util.List;

import APP.Comm.DataBase.Helper.AppDataTable;
import APP.Model.BaseEntity;

/* 功能：提供系统字典管理对外接口 */
public interface ISysDictMgr {
	// 查询接口：获得指定字典项值
	public String getSysDictItemValue(String dictId, String key)
			throws Exception;

	// 查询字典接口：获取指定的字典 返回实体列
//	public List<BaseEntity> getSysDictItem(String dictId, String key)
//			throws Exception;

	// 查询字典接口：获取指定的字典 返回实体列
	public List<BaseEntity> getSysDictItem(String dictId) throws Exception;

	// 查询字典接口：获取指定的字典 返回DataTable
	public AppDataTable getSysDictItems(String dictId) throws Exception;
}
