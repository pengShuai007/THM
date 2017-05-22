package APP.Comm.Util;

import APP.Comm.View.*;

//using Util.JsonModel;


/** 
 分页工具类
 
*/
public class GridPagingUtil
{
	/** 
	 oracle 分页方法
	 
	 @param baseSql 自己的sql查询语句
	 @param gridReq 分页参数对象
	 @return 
	*/
	public static String oracleGridPaging(String baseSql, GridRequestParameters gridReq)
	{
		StringBuilder outSql = new StringBuilder("SELECT * FROM ( SELECT ROWNUM R , T2.* FROM (" + baseSql+" ");
		outSql.append(") T2 ) T WHERE T.R>" + (gridReq.getPage() - 1) * gridReq.getRows() + " AND T.R<=" + gridReq.getPage() * gridReq.getRows() + " ORDER BY "+gridReq.getSort()+" "+gridReq.getOrder()+" ");
		return outSql.toString();
		
	}

}