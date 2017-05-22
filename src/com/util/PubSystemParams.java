package com.util;


import java.util.ArrayList;
import java.util.List;

import model.base.ext.SYS_PARAMETER_EXT;
import APP.Comm.BLL.BaseBllException;
import APP.Comm.DataBase.Helper.IDataBase;
import APP.Comm.DataBase.Parameter.AppDbParameter;
import APP.Comm.Util.HLogger;
import APP.Model.BaseEntity;

public class PubSystemParams {

	
    
    /**
	 * <pre>
	 * CopyRight(c) 2014-2015
	 * 创建人：罗代华
	 * 日期：2014年11月14日10:24:36
	 * 描述：根据参数名称查询
	 * 任务号：KYEEAPP-895
	 * @param paraCode
	 * @param AppDB
	 * @return 系统参数值
	 * @throws BaseBllException
	 */
	public static String getParams(String paraCode,IDataBase AppRdDB) throws BaseBllException{
	   HLogger.info("PubSystemParams  Function getParams begin!");
       HLogger.info("说明：传入系統参数code，获取该参数的值");
	   StringBuffer sql=new StringBuffer();
       sql.append("SELECT PARA_CODE,PARA_VALUE FROM SYS_PARAMETER WHERE PARA_CODE=:PARA_CODE AND PARA_TYPE ='S'");
       List<AppDbParameter> para = new ArrayList<AppDbParameter>();
       para.add(new AppDbParameter("PARA_CODE", paraCode));
       List<BaseEntity> paralist = AppRdDB.QueryEntity(SYS_PARAMETER_EXT.class, sql.toString(), para).getEntityList();           
       HLogger.info("PubSystemParams  Function getParams end!");
       if (paralist.size() == 0){
    	   HLogger.info("取出系统参数为空，请检查传入参数是否正确！");
           return null; 
       }else{
           SYS_PARAMETER_EXT csc = (SYS_PARAMETER_EXT) paralist.get(0);
           String   value = csc.getPARA_VALUE();
           return value;
       }
   } 
}