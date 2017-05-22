package com.kyee.cloud.SqlQuery.bll;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import APP.Comm.BLL.BaseBLL;
import APP.Comm.BLL.BaseBllException;
import APP.Comm.DataBase.DotNet.DataTable;
import APP.Comm.DataBase.Helper.AppDataTable;
import APP.Comm.Util.HLogger;
import APP.Comm.Util.JsonUtil;
import APP.Comm.Util.OutJsonType;

import com.kyee.cloud.SqlQuery.dal.SqlQueryDal;

public class SqlQueryBll extends BaseBLL {
	SqlQueryDal sqlQueryDal = new SqlQueryDal();

	/**
	 * 
	 * 描述: 查询sql字段名<br/>
	 * 创建人: shiqi <br/>
	 * 创建时间：2015年5月4日16:05:01 任务号：APPDAILYWORK-778
	 *
	 * @return
	 * @throws BaseBllException
	 * @since Ver 1.1
	 */
	public Map<String, String> querySqlClo() throws BaseBllException {
		HLogger.info("SqlQueryBll Function querySqlClo start!");
		String sql = getParameterValue("sql");// sql查询语句
		if (sql.endsWith(";")) {
			sql = sql.substring(0, sql.length() - 1) + " LIMIT 0,100";
		} else {
			sql += " LIMIT 0,100";
		}
		Map<String, String> map = new HashMap<String, String>();
		String jsonStr;
		DataTable result = null;
		result = sqlQueryDal.querySqlClo(super.getBaseDB(), sql).getDataTable();
		// 获取所查询sql语句的字段名
		List<String> resultList = result.Columns;
		// 判断所获取字段名是否为空
		if (resultList.size() == 0) {
			jsonStr = "";
			map.put("key", "1");
			map.put("value", "SQL查询失败！");
		} else {
			jsonStr = JsonUtil.listBeanToJsonString(resultList.size(), true,
					"sql查询字段", resultList, OutJsonType.Grid);
			map.put("key", "2");
			map.put("value", jsonStr);
		}
		HLogger.info("SqlQueryBll Function querySqlClo end!");
		return map;
	}

	/**
	 * 
	 * 描述: sql查询数据<br/>
	 * 创建人: shiqi <br/>
	 * 创建时间：2015年5月4日16:05:55 任务号：APPDAILYWORK-778
	 *
	 * @return
	 * @throws BaseBllException
	 * @since Ver 1.1
	 */
	public AppDataTable querySqlData() throws BaseBllException {
		HLogger.info("SqlQueryBll Function querySqlData start!");
		String sql = getParameterValue("sql");// sql查询语句
		if (sql.endsWith(";")) {
			sql = sql.substring(0, sql.length() - 1) + " LIMIT 0,100";
		} else {
			sql += " LIMIT 0,100";
		}
		AppDataTable result = null;
		result = sqlQueryDal.querySqlData(super.getBaseDB(), sql,
				super.getGridRequestParameters());
		HLogger.info("SqlQueryBll Function querySqlData end!");
		return result;
	}
}
