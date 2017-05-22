package com.kyee.cloud.SqlQuery.dal;

import java.util.List;

import APP.Comm.BLL.BaseBllException;
import APP.Comm.DataBase.DotNet.DataColumn;
import APP.Comm.DataBase.DotNet.DataRow;
import APP.Comm.DataBase.Helper.AppDataTable;
import APP.Comm.DataBase.Helper.IDataBase;
import APP.Comm.Util.HLogger;
import APP.Comm.View.GridRequestParameters;

public class SqlQueryDal{

	/**
     * 
     * 描述: sql查询数据<br/>
     * 创建人: shiqi <br/>
     * 创建时间：2015年5月4日16:04:32
     * 任务号：APPDAILYWORK-778
     *
     * @param appDb
     * @param sql
     * @return
     * @throws BaseBllException 
     * @since Ver 1.1
     */
    public AppDataTable querySqlClo(IDataBase appRdDb,String sql)
            throws BaseBllException {
        HLogger.info("SqlQueryDal Function querySqlClo begin!");
        AppDataTable result=appRdDb.Query(sql, null);
        HLogger.info("SqlQueryDal Function querySqlClo end!");
        return result;
    }
    
    /**
     * 
     * 描述: sql查询数据<br/>
     * 创建人: shiqi <br/>
     * 创建时间：2015年5月5日17:01:12
     * 任务号：APPDAILYWORK-778
     *
     * @param appDb
     * @param sql
     * @param gridRequestParameters
     * @return
     * @throws BaseBllException 
     * @since Ver 1.1
     */
    public AppDataTable querySqlData(IDataBase appRdDb,String sql,GridRequestParameters gridRequestParameters)
            throws BaseBllException {
        HLogger.info("SqlQueryDal Function querySqlData begin!");
        
        AppDataTable queryDa=appRdDb.Query(sql, null);
        //分页查询
        AppDataTable queryData=appRdDb.QueryByPage(sql, "1", null, gridRequestParameters);
        HLogger.info("SqlQueryDal Function querySqlData："+queryDa.getDataTable().getRows().size()+queryData.getDataTable().getRows().size()+"  sql:"+sql);
        //遍历数据,处理转义字符
        List<DataRow> dtList=queryData.getDataTable().getRows();
        //遍历所查询的每行数据
        for(int n=0;n<dtList.size();n++){
            DataRow dr=dtList.get(n);
            List<DataColumn> dcList=dr.getCol();
            //遍历所查询行数据的每列
            for(int m=0;m<dcList.size();m++){
                DataColumn dc=dcList.get(m);
                //非空字符串，对转义字符转义
                if(dc.getValue()!=null){
                    String dcValue=dc.getValue().toString();
                    dc.setValue(dcValue);
                }
            }
        }
        HLogger.info("SqlQueryDal Function querySqlData end!");
        return queryData;
    }
    
}
