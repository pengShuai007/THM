package com.kyee.cloud.MenuManager.dal;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import model.base.ext.SYS_MENU_EXT;
import APP.Comm.BLL.BaseBllException;
import APP.Comm.DataBase.Helper.AppDataTable;
import APP.Comm.DataBase.Helper.IDataBase;
import APP.Comm.DataBase.Parameter.AppDbParameter;
import APP.Comm.Util.HLogger;
import APP.Comm.View.GridRequestParameters;
import APP.Model.BaseEntity;

public class MenuManagerDal {

	/**
	 * 任务号： 描述：查找菜单管理根节点： sys_menu表中C_S_MENU_ID=0的节点 创建人：李添
	 * 时间：2016年7月26日11:32:02
	 * 
	 * @param baseDB
	 * @return
	 * @throws BaseBllException
	 */
	public List<BaseEntity> getRootTree(IDataBase baseDB)
			throws BaseBllException {
		HLogger.info("MenuManagerDal Function getRootTree Start!");
		String sqlStr = " SELECT MENU_NAME FROM sys_menu WHERE MENU_ID=0 ";
		List<BaseEntity> result = baseDB.QueryEntity(SYS_MENU_EXT.class,
				sqlStr, null).getEntityList();
		HLogger.info("MenuManagerDal Function getRootTree End!");
		return result;
	}

	/**
	 * 任务号： 描述：查找菜单树 作者：李添 时间：2016年7月28日11:35:37
	 * 
	 * @param baseDB
	 * @param menuId
	 * @return
	 * @throws BaseBllException
	 */
	public List<BaseEntity> getRootTree(IDataBase baseDB, int menuId)
			throws BaseBllException {
		HLogger.info("MenuManagerDal Function getRootTree Start!");
		List<AppDbParameter> paras = new ArrayList<AppDbParameter>();
		String sqlStr = " SELECT * FROM sys_menu WHERE 1=1 "
				+ "  AND C_S_MENU_ID = :C_S_MENU_ID ORDER BY MENU_ORDER ";
		paras.add(new AppDbParameter("C_S_MENU_ID", menuId));
		List<BaseEntity> result = baseDB.QueryEntity(SYS_MENU_EXT.class,
				sqlStr, paras).getEntityList();
		HLogger.info("MenuManagerDal Function getRootTree End!");
		return result;
	}

	/**
	 * 任务号： 描述：得到最大的id 作者：李添 时间：2016年7月28日13:58:38
	 * 
	 * @param baseDb
	 * @return
	 * @throws BaseBllException
	 */
	public int getMaxId(IDataBase baseDb) throws BaseBllException {
		HLogger.info("MenuManagerDal Function getMaxId Start!");
		String FindMaxIdSql = "SELECT MAX(MENU_ID) menuId FROM sys_menu";
		AppDataTable result = baseDb.Query(FindMaxIdSql, null);
		int maxId = result.getDataTable().getRows().get(0)
				.getIntColumn("menuId");
		HLogger.info("MenuManagerDal Function getMaxId End!");
		return maxId;
	}

	/**
	 * 任务号： 描述：增加菜单 作者：李添 时间：2016年7月28日11:35:55
	 * 
	 * @param baseDb
	 * @param MENU_NAME
	 * @param MENU_URL
	 * @param MENU_CODE
	 * @param OPERATOR_NAME
	 * @param MENU_ID
	 *            父亲的id
	 * @return
	 * @throws BaseBllException
	 */
	public int addMenu(IDataBase baseDb, String currentUserName,
			String MENU_NAME, String MENU_URL, String MENU_CODE, int MENU_ID,
			int MENU_ORDER) throws BaseBllException {
		HLogger.info("MenuManagerDal Function addMenu begin!");
		List<AppDbParameter> parameters = new ArrayList<AppDbParameter>();
		parameters.add(new AppDbParameter("MENU_NAME", MENU_NAME));
		parameters.add(new AppDbParameter("MENU_URL", MENU_URL));
		parameters.add(new AppDbParameter("MENU_CODE", MENU_CODE));
		parameters.add(new AppDbParameter("MENU_ID", MENU_ID));
		parameters.add(new AppDbParameter("OPERATOR_NAME", currentUserName));
		parameters.add(new AppDbParameter("MENU_ORDER", MENU_ORDER));

		int id = getMaxId(baseDb) + 1;
		int result = 1;
		
		StringBuffer sqlUpdate = new StringBuffer("");
		String sql = null;
		
		sql = "SELECT COUNT(*) count1 FROM sys_menu WHERE C_S_MENU_ID = :MENU_ID";// 这个菜单有几个孩子
		int countChild = baseDb.Query(sql, parameters).getDataTable().getRows()
				.get(0).getIntColumn("count1");// 这个菜单有几个孩子
		if (countChild > 0) {
			sql = "SELECT MAX(MENU_ORDER) maxorder FROM sys_menu WHERE C_S_MENU_ID = :MENU_ID";// 这个父亲的孩子中menu_order的最大值
			int maxOrder = baseDb.Query(sql, parameters).getDataTable()
					.getRows().get(0).getIntColumn("maxorder");
			if (MENU_ORDER <= maxOrder) {
				int[][] array = whenEditMenuOrder(baseDb, MENU_ID, MENU_ORDER,
						maxOrder + 1);
				if(array[0][1] == MENU_ORDER){//如果不存在这个menu_order的话就直接添加，后边的不用往后移了
					/*sqlUpdate.append(" UPDATE sys_menu SET MENU_ORDER=MENU_ORDER+1 WHERE MENU_ID=");
					sqlUpdate.append(array[0][0]);
					result *= baseDb.Update(sqlUpdate.toString(), null);
					
					for (int i = 1; i < array.length; i++) {
						if(array[i][1] == array[i-1][1]){
							sql = " UPDATE sys_menu SET MENU_ORDER=MENU_ORDER+1 WHERE MENU_ID="
									+ array[i][0] + ";";
							result *= baseDb.Update(sql, null);
							array[i][1]++;
						}
					}*/
					sqlUpdate.append(" UPDATE sys_menu SET MENU_ORDER=MENU_ORDER+1 WHERE MENU_ID IN ( ");
					sqlUpdate.append(array[0][0]);
					array[0][1]++;
					for (int i = 1; i < array.length; i++) {
						if(array[i][1] == array[i-1][1]){
							sqlUpdate.append(",");
							sqlUpdate.append(array[i][0]);
							array[i][1]++;
						}
					}
					sqlUpdate.append(")");
					result *= baseDb.Update(sqlUpdate.toString(), null);
				}
			}
		}
		sql = "INSERT INTO sys_menu(MENU_ID,MENU_NAME,MENU_URL,MENU_CODE,OPERATOR_NAME,OPERATOR_TIME,MODIFIER,MODIFY_TIME,C_S_MENU_ID,MENU_ORDER) VALUES("
				+ id
				+ ",:MENU_NAME,:MENU_URL,:MENU_CODE,:OPERATOR_NAME,NOW(),null,null,:MENU_ID,:MENU_ORDER)";
		result *= baseDb.Save(sql, parameters);

		HLogger.info("MenuManagerDal Function addMenu end!");
		return result;
	}

	/**
	 * 任务号： 描述：获取所有可能当父亲的节点（url为空或""的都可以当父亲） 作者：李添 时间：2016年7月28日16:18:29
	 * 
	 * @param baseDb
	 * @return
	 * @throws BaseBllException
	 */
	public AppDataTable getisFather(IDataBase baseDb) throws BaseBllException {
		HLogger.info("MenuManagerDal Function getisFather begin!");
		String sqlquery = "SELECT MENU_ID,MENU_NAME FROM sys_menu WHERE MENU_URL = NULL OR MENU_URL = ''";
		HLogger.info("MenuManagerDal Function getisFather end!");
		return baseDb.Query(sqlquery, null);
	}

	/**
	 * 任务号： 描述：修改菜单 作者：李添 时间：2016年7月29日16:22:19
	 * 
	 * @param baseDb
	 * @param currentUserName
	 * @param MENU_NAME
	 * @param MENU_URL
	 * @param MENU_CODE
	 * @param MENU_ID
	 * @param C_S_MENU_ID
	 * @param OPERATOR_NAME
	 * @param OPERATOR_TIME
	 * @return
	 * @throws BaseBllException
	 */
	public int editMenu(IDataBase baseDb, String currentUserName,
			String MENU_NAME, String MENU_URL, String MENU_CODE, int MENU_ID,
			int C_S_MENU_ID, String OPERATOR_NAME, Timestamp OPERATOR_TIME,
			int MENU_ORDER, int MENU_ORDER_OLD) throws BaseBllException {
		HLogger.info("MenuManagerDal Function editMenu begin!");

		List<AppDbParameter> parameters = new ArrayList<AppDbParameter>();
		parameters.add(new AppDbParameter("MENU_NAME", MENU_NAME));
		parameters.add(new AppDbParameter("MENU_URL", MENU_URL));
		parameters.add(new AppDbParameter("MENU_CODE", MENU_CODE));
		parameters.add(new AppDbParameter("MENU_ID", MENU_ID));
		parameters.add(new AppDbParameter("C_S_MENU_ID", C_S_MENU_ID));
		parameters.add(new AppDbParameter("OPERATOR_NAME", OPERATOR_NAME));
		parameters.add(new AppDbParameter("OPERATOR_TIME", OPERATOR_TIME));
		parameters.add(new AppDbParameter("MODIFIER", currentUserName));
		parameters.add(new AppDbParameter("MENU_ORDER", MENU_ORDER));

		int result = 1;
		String sql = "";
		StringBuffer sqlUpdate = new StringBuffer("");
		StringBuffer sqlUpdate1 = new StringBuffer("");
		
		if (MENU_ORDER == MENU_ORDER_OLD) {
			sql = " UPDATE sys_menu SET MENU_NAME=:MENU_NAME,MENU_URL=:MENU_URL,MENU_CODE=:MENU_CODE,OPERATOR_NAME=:OPERATOR_NAME,OPERATOR_TIME=:OPERATOR_TIME,MODIFIER=:MODIFIER,MODIFY_TIME=NOW(),C_S_MENU_ID=:C_S_MENU_ID WHERE MENU_ID=:MENU_ID ";
			result *= baseDb.Update(sql, parameters);
		} else {
			int[][] array = whenEditMenuOrder(baseDb, C_S_MENU_ID, MENU_ORDER,
					MENU_ORDER_OLD);
			if (MENU_ORDER < MENU_ORDER_OLD) {
				if(array[0][1] == MENU_ORDER){//如果这个位置空着就直接移了，如果不是就要移呢
					sqlUpdate.append(" UPDATE sys_menu SET MENU_ORDER=MENU_ORDER+1 WHERE MENU_ID IN ( ");
					sqlUpdate.append(array[0][0]);
					array[0][1]++;
					for (int i = 1; i < array.length; i++) {
						if(array[i][1] == array[i-1][1]){//移的时候也要判断这个位置是否空着，如果空着就不移了，否则就要移
							sqlUpdate.append(",");
							sqlUpdate.append(array[i][0]);
							array[i][1]++;
						}
					}
					sqlUpdate.append(")");
					result *= baseDb.Update(sqlUpdate.toString(),null);
				}
			} else {
				if(array[array.length-1][1] == MENU_ORDER){//如果这个位置空着，就直接移，如果不是就要移
					sqlUpdate1.append(" UPDATE sys_menu SET MENU_ORDER=MENU_ORDER-1 WHERE MENU_ID IN (");
					sqlUpdate1.append(array[0][0]);
					for (int i = 1; i < array.length; i++) {
						sqlUpdate1.append(",");
						sqlUpdate1.append(array[i][0]);
					}
					sqlUpdate1.append(")");
					result *= baseDb.Update(sqlUpdate1.toString(), null);
				}
			}
			sql = " UPDATE sys_menu SET MENU_NAME=:MENU_NAME,MENU_URL=:MENU_URL,MENU_CODE=:MENU_CODE,OPERATOR_NAME=:OPERATOR_NAME,OPERATOR_TIME=:OPERATOR_TIME,MODIFIER=:MODIFIER,MODIFY_TIME=NOW(),C_S_MENU_ID=:C_S_MENU_ID,MENU_ORDER=:MENU_ORDER WHERE MENU_ID=:MENU_ID ";
			result *= baseDb.Update(sql, parameters);
		}
		HLogger.info("MenuManagerDal Function editMenu end!");
		return result;
	}

	/**
	 * 描述：当改变菜单排序的时候，这个菜单的亲生兄弟姐妹中，这个菜单本来的menu_order和要变为的menu_order都会统一向前或向后移一个,
	 * 在这里只看menu_order != menu_order_old的情况, 这个方法中我们找到这些中间的menu_id 作者：李添
	 * 时间：2016年8月9日11:05:26
	 * 
	 * @param baseDb
	 * @param C_S_MENU_ID
	 * @param MENU_ORDER
	 * @return
	 * @throws BaseBllException
	 */
	public int[][] whenEditMenuOrder(IDataBase baseDb, int C_S_MENU_ID,
			int MENU_ORDER, int MENU_ORDER_OLD) throws BaseBllException {
		HLogger.info("MenuManagerDal Function whenEditMenuOrder begin!");
		List<AppDbParameter> parameters = new ArrayList<AppDbParameter>();
		parameters.add(new AppDbParameter("C_S_MENU_ID", C_S_MENU_ID));
		parameters.add(new AppDbParameter("MENU_ORDER", MENU_ORDER));
		parameters.add(new AppDbParameter("MENU_ORDER_OLD", MENU_ORDER_OLD));
		String sql = "";
		if(MENU_ORDER < MENU_ORDER_OLD){
			sql = "SELECT COUNT(*) count1 FROM sys_menu WHERE C_S_MENU_ID = :C_S_MENU_ID AND MENU_ORDER >= :MENU_ORDER AND MENU_ORDER < :MENU_ORDER_OLD";
		}else{
			sql = "SELECT COUNT(*) count1 FROM sys_menu WHERE C_S_MENU_ID = :C_S_MENU_ID AND MENU_ORDER > :MENU_ORDER_OLD AND MENU_ORDER <= :MENU_ORDER";
		}
		int len = baseDb.Query(sql, parameters).getDataTable().getRows().get(0).getIntColumn("count1");//中间总共有几个
		int[][] array = new int[len][2];// len行2列，这2列分别是menu_id和menu_order
		if (MENU_ORDER < MENU_ORDER_OLD) {
			sql = "SELECT MENU_ID,MENU_ORDER FROM sys_menu WHERE C_S_MENU_ID = :C_S_MENU_ID AND MENU_ORDER >= :MENU_ORDER AND MENU_ORDER < :MENU_ORDER_OLD ORDER BY MENU_ORDER ";
		} else {
			sql = "SELECT MENU_ID,MENU_ORDER FROM sys_menu WHERE C_S_MENU_ID = :C_S_MENU_ID AND MENU_ORDER > :MENU_ORDER_OLD AND MENU_ORDER <= :MENU_ORDER ORDER BY MENU_ORDER ";
		}
		AppDataTable result = baseDb.Query(sql, parameters);
		for (int i = 0; i < array.length; i++) {
			array[i][0] = result.getDataTable().getRows().get(i)
					.getIntColumn("MENU_ID");
			array[i][1] = result.getDataTable().getRows().get(i)
					.getIntColumn("MENU_ORDER");
		}
		HLogger.info("MenuManagerDal Function whenEditMenuOrder end!");
		return array;
	}

	/**
	 * 任务号： 描述：删除菜单 作者：李添 时间：2016年7月29日17:18:06
	 * 
	 * @param baseDb
	 * @param MENU_ID
	 * @return
	 * @throws BaseBllException
	 */
	public int removeMenu(IDataBase baseDb, int MENU_ID, int C_S_MENU_ID,
			int MENU_ORDER) throws BaseBllException {
		HLogger.info("MenuManagerDal Function removeMenu begin!");
		List<AppDbParameter> parameters = new ArrayList<AppDbParameter>();
		parameters.add(new AppDbParameter("MENU_ID", MENU_ID));
		parameters.add(new AppDbParameter("C_S_MENU_ID", C_S_MENU_ID));
		parameters.add(new AppDbParameter("MENU_ORDER", MENU_ORDER));
		String sql = null;
		StringBuffer sqlDel = new StringBuffer();
		int result = 1;
		sql = "SELECT COUNT(*) count1 FROM sys_menu WHERE C_S_MENU_ID = :MENU_ID";// 这个菜单有几个孩子
		int countChild = baseDb.Query(sql, parameters).getDataTable().getRows()
				.get(0).getIntColumn("count1");// 这个菜单有几个孩子
		if (countChild > 0) {
			sql = "DELETE FROM sys_menu WHERE C_S_MENU_ID=:MENU_ID ";
			result *= baseDb.Delete(sql, parameters);
		}
		sql = "SELECT MAX(MENU_ORDER) maxOrder FROM sys_menu WHERE C_S_MENU_ID = :C_S_MENU_ID";// 这个父亲的孩子中menu_order的最大值
		int maxMenuOrder = baseDb.Query(sql, parameters).getDataTable()
				.getRows().get(0).getIntColumn("maxOrder");// 这个父亲的孩子中menu_order的最大值

		sql = " DELETE FROM sys_menu WHERE MENU_ID=:MENU_ID ";
		result *= baseDb.Delete(sql, parameters);
		if (MENU_ORDER < maxMenuOrder) {// 删除的时候这个菜单同级的后面的菜单的menu_order统一向前移一个
			int array[][] = whenEditMenuOrder(baseDb, C_S_MENU_ID, maxMenuOrder,
					MENU_ORDER);
			sqlDel.append(" UPDATE sys_menu SET MENU_ORDER=MENU_ORDER-1 WHERE MENU_ID IN (");
			sqlDel.append(array[0][0]);
			for (int i = 1; i < array.length; i++) {
				sqlDel.append(",");
				sqlDel.append(array[i][0]);
			}
			sqlDel.append(")");
			result *= baseDb.Update(sqlDel.toString(),null);
			
		}

		HLogger.info("MenuManagerDal Function removeMenu end!");
		return result;
	}

	/**
	 * 描述：查询所有菜单 作者：李添 时间：2016年7月29日19:45:45
	 * 
	 * @param baseDb
	 * @return
	 * @throws BaseBllException
	 */
	public AppDataTable querySysMenu(IDataBase baseDb,
			GridRequestParameters gridRequestParameters)
			throws BaseBllException {
		HLogger.info("MenuManagerDal Function querySysMenu begin!");
		List<AppDbParameter> parameters = new ArrayList<AppDbParameter>();
		String sql = "SELECT MENU_ID,MENU_NAME,MENU_URL,MENU_CODE,OPERATOR_NAME,OPERATOR_TIME,MODIFIER,MODIFY_TIME,C_S_MENU_ID,MENU_ORDER FROM sys_menu  ";
		HLogger.info("MenuManagerDal Function querySysMenu end!");
		// baseDb.QueryByPage(sqlStr, "POST_ID", param, gridRequestParameters);
		return baseDb.QueryByPage(sql, "MENU_ID", parameters,
				gridRequestParameters);
	}
	/*public AppDataTable queryNodeSysMenu(IDataBase baseDb,GridRequestParameters gridRequestParameters){
		HLogger.info("MenuManagerDal Function queryNodeSysMenu begin!");
		List<AppDbParameter> para = new ArrayList<AppDbParameter>();
		String sql = 
		HLogger.info("MenuManagerDal Function queryNodeSysMenu end!");
	}*/
	

	/**
	 * 描述：找到有这个菜单权限的人 作者：李添
	 * 
	 * @param baseDb
	 * @param menuCode
	 * @param gridRequestParameters
	 * @return
	 * @throws BaseBllException
	 */
	public AppDataTable findUserInfo(IDataBase baseDb, String menuCode,
			GridRequestParameters gridRequestParameters)
			throws BaseBllException {
		HLogger.info("MenuManagerDal Function findUserInfo begin!");
		List<AppDbParameter> parameters = new ArrayList<AppDbParameter>();
		parameters.add(new AppDbParameter("MENU_CODE", menuCode));
		String sql = "SELECT USER_ID,USER_NAME,PHONE_NUM FROM sys_user WHERE user_id IN "
				+ "(SELECT user_id FROM sys_dept_post_detail WHERE info_id IN "
				+ "(SELECT info_id FROM sys_dept_post_info WHERE POST_ID IN "
				+ "(SELECT POST_ID FROM sys_post_perm WHERE MENU_CODE=:MENU_CODE)))";
		HLogger.info("MenuManagerDal Function findUserInfo end!");
		return baseDb.QueryByPage(sql, "USER_ID", parameters,
				gridRequestParameters);
	}

}
