package com.kyee.cloud.SqlQuery.action;

import java.util.Map;

import APP.Comm.BLL.BaseBllException;
import APP.Comm.DataBase.Helper.AppDataTable;
import APP.Comm.DotNet.HttpContext;
import APP.Comm.Util.HLogger;
import APP.Comm.Util.JsonUtil;
import APP.Comm.Util.OutJsonType;

import com.kyee.cloud.SqlQuery.bll.SqlQueryBll;


public class SqlQueryAction
{
    SqlQueryBll sqlQueryBll=new SqlQueryBll();
    
    /**
     * 
     * 描述: 获取sql字段名<br/>
     * 创建人: shiqi <br/>
     * 创建时间：2015年5月4日16:06:46
     * 任务号：APPDAILYWORK-778
     *
     * @param context
     * @throws BaseBllException 
     * @since Ver 1.1
     */
    @SuppressWarnings("unchecked")
	public void querySqlClo(HttpContext context) throws BaseBllException{
        HLogger.info("SqlQueryAction Function querySQL begin!");
        context.getHttpHandler().SetParamsToBLL(sqlQueryBll, context);
        sqlQueryBll.getBaseDB().NeedOpen(true);
        sqlQueryBll.getBaseDB().NeedTransaction(false);
        Map<Integer, String> map= (Map<Integer, String>)context.getHttpHandler().BLLContainer.DoProcess(sqlQueryBll, "querySqlClo");
        HLogger.info("SqlQueryAction Function query SQL end!");
		if ("0".equals(map.get("key"))) {
			context.write(JsonUtil.errorMessageJsonString(map.get("value")));
		}
		if ("1".equals(map.get("key"))) {
			context.write(JsonUtil.errorMessageJsonString(map.get("value")));
		}
		if ("2".equals(map.get("key"))) {
			context.write(JsonUtil.successMessageJsonString("查询成功",map.get("value")));
		}

    }
    
    /**
     * 
     * 描述: 查询sql数据<br/>
     * 创建人: shiqi <br/>
     * 创建时间：2015年5月4日16:07:10
     * 任务号：APPDAILYWORK-778
     *
     * @param context
     * @throws BaseBllException 
     * @since Ver 1.1
     */
    public void querySqlData (HttpContext context) throws BaseBllException {
		context.getHttpHandler().SetParamsToBLL(sqlQueryBll, context);
    	sqlQueryBll.getBaseDB().NeedOpen(true);
    	sqlQueryBll.getBaseDB().NeedTransaction(false);
		AppDataTable result = (AppDataTable) context.getHttpHandler().BLLContainer
				.DoProcess(sqlQueryBll, "querySqlData");
		context.write(result != null ? JsonUtil.dataTableToJsonString(Integer.parseInt(result
				.size()+""), true, "sql数据查询成功", result.DataTable, OutJsonType.Grid)
				: JsonUtil.errorMessageJsonString("sql查询数据为空！"));
		}
}
