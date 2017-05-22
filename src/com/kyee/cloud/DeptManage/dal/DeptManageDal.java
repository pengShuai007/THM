package com.kyee.cloud.DeptManage.dal;

import java.util.ArrayList;
import java.util.List;

import model.base.ext.SYS_DEPT_INFO_EXT;
import APP.Comm.BLL.BaseBllException;
import APP.Comm.DataBase.Helper.AppDataTable;
import APP.Comm.DataBase.Helper.IDataBase;
import APP.Comm.DataBase.Parameter.AppDbParameter;
import APP.Comm.Util.DotNetToJavaStringHelper;
import APP.Comm.Util.HLogger;
import APP.Comm.View.GridRequestParameters;
import APP.Model.BaseEntity;

public class DeptManageDal {
	/**
	 * 查询编制部门数据 作者：刘勇 日期：2013年11月22日 14:25:40
	 * 修改者：杨乐 任务：HRPHRDEVJAVA-405 描述：增加部门类型 时间：2015年2月3日15:58
	 * @param hrpDb
	 *            数据库
	 * @return
	 */
//	public final java.util.ArrayList<BaseEntity> DoGetDeptTree(IDataBase hrpDb,
//			HrpUser hrpUser, boolean limit ,String deptStatus) throws BaseBllException {
//
//		java.util.List<AppDbParameter> parameters = new java.util.ArrayList<AppDbParameter>();
//		if (limit == true) {
//			StringBuilder strsql = new StringBuilder();
//			strsql.append("SELECT  t.*  FROM V_SYS_DEPT_DICT t "
//					+ "                                WHERE t.VISIBLE_INDICATOR = 1 ");
//			if (new HRP.HR.HRComm.CommSysParamCheck().CheckUserIsNotYXTADMIN(hrpUser.getLoginName())) {
//				// 加入用户权限控制: 角色权限和类型权限
//				strsql.append(" AND t.SERIAL_NO IN (SELECT DEPT_ID FROM V_SYS_USER_HD_PERM WHERE USER_ID=:USER_ID)");
//				parameters.add(new AppDbParameter("USER_ID", hrpUser
//						.getLoginName()));
//			}
//			strsql.append(" ORDER BY DEPT_CODE ASC");
//
//			return (java.util.ArrayList<BaseEntity>) hrpDb.QueryEntity(
//					SYS_DEPT_INFO_EXT.class, strsql + "", parameters)
//					.getEntityList();
//		} else {
////			String strSql = "SELECT  t.*  FROM V_SYS_DEPT_DICT t "
////					+ "                                WHERE  t.VISIBLE_INDICATOR = 1  ORDER BY DEPT_CODE ASC";
//			StringBuilder strSql = new StringBuilder();
//			strSql.append("SELECT  T.*  FROM V_SYS_DEPT_DICT T WHERE 1=1 ");
//			//显示正常
//			if("NORMAL".equals(deptStatus)){
//				strSql.append(" AND T.VISIBLE_INDICATOR = 1 ");
//			}
//			strSql.append(" ORDER BY DEPT_CODE ASC");
//			return (java.util.ArrayList<BaseEntity>) hrpDb.QueryEntity(
//					SYS_DEPT_INFO_EXT.class, strSql + "", null).getEntityList();
//		}
//	}

	/**
	 * 说明：修改 作者：刘勇 时间：2013年11月22日 16:18:07
	 * 
	 * @param enetiy
	 * @param hrpDb
	 * @return
	 */
	public final int DoUpdate(String beforeDeptCode, String deptCode, IDataBase baseDB)
			throws BaseBllException {
		HLogger.info("DeptManagerDal Function DeletePost Start!");
		List<AppDbParameter> paras = new ArrayList<AppDbParameter>();
		String sqlStr = " UPDATE SYS_DEPT_INFO SET DEPT_CODE = REPLACE(DEPT_CODE,:BEFORE_DEPT_CODE,:DEPT_CODE) "
				+ " WHERE DEPT_CODE LIKE :BEFORE_DEPT_CODES ";
		paras.add(new AppDbParameter("BEFORE_DEPT_CODE", beforeDeptCode+"-"));
		paras.add(new AppDbParameter("DEPT_CODE", deptCode+"-"));
		paras.add(new AppDbParameter("BEFORE_DEPT_CODES", beforeDeptCode+"-%"));
		int result = baseDB.Update(sqlStr, paras);
		HLogger.info("DeptManagerDal Function DeletePost End!");
		return result;
	}

	/**
	 * 说明：新增编制部门信息 作者：刘勇 时间：2013年11月22日 14:23:04
	 * 
	 * @param entity
	 * @param hrpDb
	 * @return
	 */
	public final int DoAdd(SYS_DEPT_INFO_EXT drDeptExt, IDataBase dataBase)
			throws BaseBllException {
//		EntitySql entitySql = EntityUtil.CreateSaveEntitySql(drDeptExt);
//		DoUpdateChild(drDeptExt.getP_SERIAL_NO() + "", dataBase);
//		return dataBase.Save(entitySql.getSqlString(),
//				entitySql.getHrpDbParameters());
		int result = dataBase.Save(drDeptExt);
		return result;
	}

	/**
	 * 新增部门时将其父级部门是否为叶子节点更新为0 作者：刘勇 时间：2013年12月3日 14:31:00
	 * 
	 * @param dept_id
	 * @param dataBase
	 * @return
	 */
	public final Object DoUpdateChild(String dept_id, IDataBase dataBase)
			throws BaseBllException {
		String sql = "UPDATE SYS_DEPT_DICT SET IS_CHILD=0 WHERE SERIAL_NO='"
				+ dept_id + "'";
		dataBase.Update(sql + "", null);
		return 1;
	}

	/**
	 * 说明：分页查询 作者：刘勇 时间：2013年11月22日 16:17:23
	 * 任务：HRPHRDEVJAVA-213 
	 * 描述：修改SQL语句  关联HR_USERS根据USER_ID查询名字
	 * 修改人：张飞
	 * 时间：2014年9月16日11:58:38
	 * @param enetiy
	 * @param hrpDb
	 * @param gridRequestParameters
	 * @return
	 */
	public final AppDataTable DoQuery(IDataBase baseDb, String deptListCode,String codeOrName,
			GridRequestParameters gridRequestParameters) throws BaseBllException {
		java.util.List<AppDbParameter> appDbParameters = new java.util.ArrayList<AppDbParameter>();
		String sqlStr = " SELECT * FROM SYS_DEPT_INFO WHERE 1=1 ";
		if(!DotNetToJavaStringHelper.isNullOrEmpty(codeOrName)) {
			sqlStr += " AND (DEPT_LIST_CODE LIKE :DEPT_CODE_NAME OR DEPT_NAME LIKE :DEPT_CODE_NAME)";
			appDbParameters.add(new AppDbParameter("DEPT_CODE_NAME","%"+codeOrName+"%"));
		}
//		if(!DotNetToJavaStringHelper.isNullOrEmpty(deptListCode)) {
//			sqlStr += " AND DEPT_CODE LIKE :DEPT_LIST_CODE ";
//			sqlStr += " UNION SELECT * FROM SYS_DEPT_INFO "
//				   +  "WHERE DEPT_LIST_CODE = :DEPT_LIST_CODES ";
//			appDbParameters.add(new AppDbParameter("DEPT_LIST_CODE",deptListCode+"-%"));
//			appDbParameters.add(new AppDbParameter("DEPT_LIST_CODES",deptListCode));
//		}
		sqlStr += " ORDER BY DEPT_CODE ";
		AppDataTable result = baseDb.QueryByPage(sqlStr, "DEPT_ID", appDbParameters, gridRequestParameters);
		return result;
	}

	/**
	 * 根据部门全路径判断下级科室是否存在人员信息 作者：刘勇 时间：2013年12月3日 13:04:16
	 * 
	 * @param dept_id
	 * @param hrpDb
	 * @return
	 */

	public final int IsExistPerson(String dept_code, IDataBase hrpDb)
			throws BaseBllException {
		java.util.List<AppDbParameter> list = new java.util.ArrayList<AppDbParameter>();
		// 根据部门全路径判断下级科室是否存在人员信息
		String strSql = "SELECT COUNT(*) FROM HR_USERS t where t.DEPT_ID in (SELECT q.SERIAL_NO FROM V_SYS_DEPT_DICT q CONNECT BY q.P_SERIAL_NO = PRIOR q.SERIAL_NO START WITH  q.DEPT_CODE LIKE :DEPT_CODE) AND t.USER_STATUS IN ('D','S','A')";
		list.add(new AppDbParameter("DEPT_CODE", dept_code));
		Object i = hrpDb.QueryObject(strSql + "", list);
		return Integer.parseInt(i + "");
	}

	/**
	 * 说明：撤销编制部门 作者：刘勇 日期：2013年11月22日 14:21:26
	 * 
	 * @param ids
	 * @param hrpDb
	 * @return
	 */
	public final Object DoCancelDept(String dept_code, String p_dept_id,
			IDataBase dataBase) throws BaseBllException {
		java.util.List<AppDbParameter> list = new ArrayList<AppDbParameter>();
		// 参数转换
		String strSql = "UPDATE SYS_DEPT_DICT SET VISIBLE_INDICATOR = 0 where SERIAL_NO in "
				+ "(select q.SERIAL_NO from V_SYS_DEPT_DICT q CONNECT BY q.P_SERIAL_NO = PRIOR q.SERIAL_NO START WITH  q.DEPT_CODE = :DEPT_CODE)";
		list.add(new AppDbParameter("DEPT_CODE", dept_code));
		dataBase.Update(strSql + "", list);
		DoUpdateCancelChild(p_dept_id, dataBase);
		return 1;
	}

	/**
	 * 说明：撤销时更新是否为末级节点 作者：刘勇 时间：2013年11月22日 14:21:26
	 * 
	 * @param p_dept_id
	 * @param dataBase
	 * @return
	 */
	public final Object DoUpdateCancelChild(String p_dept_id, IDataBase dataBase)
			throws BaseBllException {
		String sql = "UPDATE SYS_DEPT_DICT T SET T.IS_CHILD=1 WHERE T.SERIAL_NO='"
				+ p_dept_id
				+ "' AND NOT EXISTS (SELECT Q.SERIAL_NO FROM V_SYS_DEPT_DICT Q WHERE Q.P_SERIAL_NO=T.SERIAL_NO AND Q.VISIBLE_INDICATOR=1)";
		dataBase.Update(sql + "", null);
		return 1;

	}

	/**
	 * <pre>
	 * 任务：HRPHRDEVJAVA-285
	 * 描述：恢复撤销部门信息
	 * 作者：杨乐
	 * 日期：2014年11月4日 下午6:31:44
	 * @param dept_code
	 * @param p_dept_id
	 * @param dataBase
	 * @return
	 * @throws BaseBllException
	 * returnType：Object
	 * </pre>
	 */
	public final int DoRestoreDept(String dept_code, String p_dept_id,IDataBase dataBase)
			throws BaseBllException {
		HLogger.info("DeptManageDal Function DoRestoreDept Start!");
		
		//Edit Start By YangLe 任务：HRPDRTESTJAVA-1192 描述：查询要恢复的父部门的状态 时间：2014年11月7日16:23
		int result = 0;
		int visibleType = QueryVisibleOfPdept(dataBase, p_dept_id);
		if(visibleType == 0){//父部门 未启用
			throw new BaseBllException("该部门的上级部门处于撤销状态，该部门不能恢复！");
		//Edit End By YangLe 任务：HRPDRTESTJAVA-1192 描述：查询要恢复的父部门的状态 时间：2014年11月7日16:23
		}else{
			java.util.List<AppDbParameter> list = new ArrayList<AppDbParameter>();
			// 参数转换
			String strSql = "UPDATE SYS_DEPT_DICT SET VISIBLE_INDICATOR = 1 where SERIAL_NO in "
					+ "(select q.SERIAL_NO from V_SYS_DEPT_DICT q CONNECT BY q.P_SERIAL_NO = PRIOR q.SERIAL_NO START WITH  q.DEPT_CODE = :DEPT_CODE)";
			list.add(new AppDbParameter("DEPT_CODE", dept_code));
			result = dataBase.Update(strSql + "", list);
			DoUpdateRetoreChild(p_dept_id, dataBase);
			HLogger.info("DeptManageDal Function DoRestoreDept End!");
		}
		return result;
	}
	
	/**
	 * <pre>
	 * 任务：HRPDRTESTJAVA-1192
	 * 描述：查询要撤销的父节点所对应的部门的启用状态 1：启用 0：未启用
	 * 作者：杨乐
	 * 日期：2014年11月7日 下午4:16:28
	 * @param hrpDB
	 * @param p_dept_id
	 * @return
	 * @throws BaseBllException
	 * returnType：int
	 * </pre>
	 */
	public final int QueryVisibleOfPdept(IDataBase hrpDB,String p_dept_id)
		throws BaseBllException{
		int result = 0;
		String strSql = "SELECT V.VISIBLE_INDICATOR\n" +
						"  FROM V_SYS_DEPT_DICT V\n" + 
						" WHERE V.SERIAL_NO = "+ p_dept_id;
		result = Integer.parseInt(hrpDB.QueryObject(strSql, null)+"");
		return result;
	}
	
	
	/**
	 * <pre>
	 * 任务：HRPHRDEVJAVA-285
	 * 描述：更新恢复子节点
	 * 作者：杨乐
	 * 日期：2014年11月4日 下午6:31:58
	 * @param p_dept_id
	 * @param dataBase
	 * @return
	 * @throws BaseBllException
	 * returnType：Object
	 * </pre>
	 */
	public final Object DoUpdateRetoreChild(String p_dept_id, IDataBase dataBase)
			throws BaseBllException {
		HLogger.info("DeptManageDal Function DoUpdateRetoreChild Start!");
		HLogger.info("hrCommBll Function DoQuerySysParam End!");
		String sql = "UPDATE SYS_DEPT_DICT T SET T.IS_CHILD=0 WHERE T.SERIAL_NO='"
				+ p_dept_id
				+ "' AND NOT EXISTS (SELECT Q.SERIAL_NO FROM V_SYS_DEPT_DICT Q WHERE Q.P_SERIAL_NO=T.SERIAL_NO AND Q.VISIBLE_INDICATOR=1)";
		dataBase.Update(sql + "", null);
		HLogger.info("DeptManageDal Function DoUpdateRetoreChild End!");
		return 1;
	}
	
	
	/**
	 * 更新是否为末级节点 作者：刘勇 时间：2013年12月18日 13:43:47
	 * 
	 * @param dept_id
	 * @param dataBase
	 * @return
	 */
	public final int DoUpdateChild(IDataBase dataBase) throws BaseBllException {
		String sql = "UPDATE SYS_DEPT_DICT D SET IS_CHILD=(case when NOT EXISTS (SELECT 1 FROM V_SYS_DEPT_DICT T WHERE D.SERIAL_NO!=T.SERIAL_NO AND T.P_SERIAL_NO=D.SERIAL_NO AND T.VISIBLE_INDICATOR=1) AND d.IS_CHILD=0 theN 1 "
				+ "when EXISTS(SELECT 1 FROM V_SYS_DEPT_DICT t WHERE d.SERIAL_NO!=t.SERIAL_NO AND t.P_SERIAL_NO=d.SERIAL_NO AND T.VISIBLE_INDICATOR=1)  AND d.IS_CHILD=1 then "
				+ "\r\n" + "0" + "\r\n" + "else IS_CHILD END) " + "\r\n" + "";
		dataBase.Update(sql, null);
		return 1;
	}
	
	/**
	 * <pre>
	 * 任务：HRPHRDEVJAVA-310
	 * 描述：查看是否启用父节点维护人员 1:启用 0:未启用
	 * 作者：杨乐
	 * 日期：2014年11月20日 下午2:31:21
	 * @param hrpDB
	 * @return
	 * @throws BaseBllException
	 * returnType：int
	 * </pre>
	 */
	public final int CheckIsFHaveUsers(IDataBase hrpDB) throws BaseBllException{
		HLogger.info("查询系统是否启用部门父节点维护人员开始！");
		int result = 0;
		String strSql = "SELECT S.PARA_VALUE\n" +
						"  FROM SYS_PARAMETER S\n" + 
		//Edit Start By YangLe 任务：HRPDRTESTJAVA-1354 描述：对查询条件进行改进 时间：2014年12月9日16:12
						//" WHERE S.SUB_SYSTEM = 'HR'\n" + 
						//"   AND S.PARA_CODE = 'HR_IS_FHAVE_USERS'";
						" WHERE S.PARA_CODE = 'HR_IS_FHAVE_USERS'";
		//Edit End By YangLe 任务：HRPDRTESTJAVA-1354 描述：对查询条件进行改进 时间：2014年12月9日16:12
		result = Integer.parseInt(hrpDB.QueryObject(strSql, null)+"");
		HLogger.info("查询系统是否启用部门父节点维护人员结束，查询结果="+result);
		return result;
	}
	
	/**
	 * <pre>
	 * 任务：HRPHRDEVJAVA-310
	 * 描述：查看该部门下是否包含人员
	 * 作者：杨乐
	 * 日期：2014年11月20日 下午5:58:23
	 * @param hrpDB
	 * @param serialNo
	 * @return
	 * @throws BaseBllException
	 * returnType：int
	 * </pre>
	 */
	public final int CheckIfExistHuman(IDataBase hrpDB,String serialNo) throws BaseBllException{
		HLogger.info("DeptManageDal Function CheckIfExistHuman Start!");
		int result = 0;
		String strSql = "SELECT COUNT(1) FROM HR_USERS U WHERE U.DEPT_ID = "+serialNo;
		result = Integer.parseInt(hrpDB.QueryObject(strSql, null)+"");
		HLogger.info("DeptManageDal Function CheckIfExistHuman End!");
		return result;
	}
	
	/**
	 * <pre>
	 * 任务：HRPHRDEVJAVA-310
	 * 描述：部门调动
	 * 作者：杨乐
	 * 日期：2014年11月20日 下午2:52:58
	 * @param hrpDB
	 * @param fromSerial
	 * @param toSerial
	 * @return
	 * @throws BaseBllException
	 * returnType：int
	 * </pre>
	 */
	public final int DoTransationDept(IDataBase hrpDB,String fromSerial,String toSerial)
		throws BaseBllException{
		int result = 0;
		HLogger.info("更新异动部门的信息开始！");
		//1.更新异动部门的父部门编号为目标部门的部门编号、异动部门的等级加1
		String strSql =  "UPDATE SYS_DEPT_DICT D\n" +
						"   SET D.P_SERIAL_NO = "+toSerial
						+", D.DEPT_LEVEL = D.DEPT_LEVEL + 1\n" + 
						" WHERE D.SERIAL_NO = "+fromSerial +" ";
		result += hrpDB.Update(strSql, null);
		HLogger.info("更新异动部门的信息结束，更新结果="+result);
		HLogger.info("更新异动部门的子部门信息开始！");
		//2.更新异动部门的子部门等级加1
		strSql = "UPDATE SYS_DEPT_DICT D\n" +
						"   SET D.DEPT_LEVEL = D.DEPT_LEVEL + 1\n" + 
						" WHERE D.P_SERIAL_NO = "+ fromSerial +" ";
		result += hrpDB.Update(strSql, null);
		HLogger.info("更新异动部门的子部门信息结束，更新结果="+result);
		HLogger.info("更新目标部门为非子节点开始！");
		//3.更新目标部门为非子节点
		strSql = "UPDATE SYS_DEPT_DICT D SET D.IS_CHILD = 0 WHERE D.SERIAL_NO = "+toSerial +" ";
		result += hrpDB.Update(strSql, null);
		HLogger.info("更新目标部门为非子节点结束，更新结果="+result);
		return result;
	}
	
	/**
	 * <pre>
	 * 任务：HRPHRDEVJAVA-310
	 * 描述：部门合并
	 * 作者：杨乐
	 * 日期：2014年11月21日 上午10:09:08
	 * @param hrpDB
	 * @param fromSerial
	 * @param toSerial
	 * @return
	 * @throws BaseBllException
	 * returnType：int
	 * </pre>
	 */
	public final int DoMergeDept(IDataBase hrpDB,String fromSerial,String toSerial)
		throws BaseBllException{
		HLogger.info("DeptManageDal Function DoMergeDept Start!");
		int result = 0;
		//1.更新异动部门的子部门的父部门代码为目标部门的部门代码
		String strSql = "UPDATE SYS_DEPT_DICT D SET D.P_SERIAL_NO = "+toSerial+" WHERE D.P_SERIAL_NO = "+fromSerial+" ";
		result += hrpDB.Update(strSql, null);
		HLogger.info("更新异动部门的子部门的父部门代码为目标部门的部门代码，更新结果="+result);
		if(result > 0){
			//2.更新目标部门为非子节点
			strSql = "UPDATE SYS_DEPT_DICT D SET D.IS_CHILD = 0 WHERE D.SERIAL_NO = "+toSerial +" ";
			result += hrpDB.Update(strSql, null);
			HLogger.info("更新目标部门为非子节点，更新结果="+result);
		}
		//3.删除异动部门
		strSql = "DELETE FROM SYS_DEPT_DICT D WHERE D.SERIAL_NO = "+fromSerial+" ";
		result += hrpDB.Update(strSql, null);
		HLogger.info("删除异动部门，删除结果="+result);
		HLogger.info("DeptManageDal Function DoMergeDept End!");
		return result;
	}

	public List<BaseEntity> GetDeptTree(IDataBase baseDB) throws BaseBllException {
		HLogger.info("DeptManagerDal Function GetSysDeptTree Start!");
		List<AppDbParameter> paras = new ArrayList<AppDbParameter>();
		String sqlStr = " SELECT * FROM SYS_DEPT_INFO WHERE 1=1 "
				+ " AND DEPT_LIST_CODE = '00' ";
		List<BaseEntity> result = baseDB.QueryEntity(SYS_DEPT_INFO_EXT.class, sqlStr, paras).getEntityList();
		HLogger.info("DeptManagerDal Function GetSysDeptTree End!");
		return result;
	}
	
	public List<BaseEntity> GetDeptTree(IDataBase baseDB, String deptListCode) throws BaseBllException {
		HLogger.info("DeptManagerDal Function GetSysDeptTree Start!");
		List<AppDbParameter> paras = new ArrayList<AppDbParameter>();
		String sqlStr = " SELECT * FROM SYS_DEPT_INFO WHERE 1=1 "
				+ " AND DEPT_LIST_CODE LIKE :DEPT_LIST_CODE "
				+ " AND DEPT_LIST_CODE NOT LIKE :DEPT_LIST_CODES "
				+ " ORDER BY DEPT_LIST_CODE ";
		paras.add(new AppDbParameter("DEPT_LIST_CODE", deptListCode+"-%"));
		paras.add(new AppDbParameter("DEPT_LIST_CODES", deptListCode+"-%"+"-%"));
		List<BaseEntity> result = baseDB.QueryEntity(SYS_DEPT_INFO_EXT.class, sqlStr, paras).getEntityList();
		HLogger.info("DeptManagerDal Function GetSysDeptTree End!");
		return result;
	}

	public int DeleteUser(IDataBase baseDB, String deptCode, String deptId) throws BaseBllException{
		HLogger.info("DeptManagerDal Function DeleteUser Start!");
		List<AppDbParameter> paras = new ArrayList<AppDbParameter>();
		String sqlStr = " DELETE FROM SYS_DEPT_POST_DETAIL "
				+ " WHERE INFO_ID IN "
				+ " (SELECT INFO_ID FROM SYS_DEPT_POST_INFO "
				+ " WHERE DEPT_ID IN "
				+ " (SELECT DEPT_ID FROM SYS_DEPT_INFO  "
				+ " WHERE ( DEPT_CODE = :DEPT_CODE "
				+ " OR DEPT_CODE LIKE :DEPT_CODES)))";
		paras.add(new AppDbParameter("DEPT_CODE", deptCode));
		paras.add(new AppDbParameter("DEPT_CODES", deptCode+"-%"));
		int result = baseDB.Delete(sqlStr, paras);
		HLogger.info("DeptManagerDal Function DeleteUser End!");
		return result;
	}

	public int DeletePost(IDataBase baseDB, String deptCode, String deptId) throws BaseBllException{
		HLogger.info("DeptManagerDal Function DeletePost Start!");
		List<AppDbParameter> paras = new ArrayList<AppDbParameter>();
		String sqlStr = " DELETE FROM SYS_DEPT_POST_INFO WHERE DEPT_ID IN  "
				+ " (SELECT DEPT_ID FROM SYS_DEPT_INFO  "
				+ " WHERE ( DEPT_CODE = :DEPT_CODE "
				+ " OR DEPT_CODE LIKE :DEPT_CODES))";
		paras.add(new AppDbParameter("DEPT_CODE", deptCode));
		paras.add(new AppDbParameter("DEPT_CODES", deptCode+"-%"));
		int result = baseDB.Delete(sqlStr, paras);
		HLogger.info("DeptManagerDal Function DeletePost End!");
		return result;
	}

	public int DeleteDept(IDataBase baseDB, String deptCode, String deptId) throws BaseBllException{
		HLogger.info("DeptManagerDal Function DeleteDept Start!");
		List<AppDbParameter> paras = new ArrayList<AppDbParameter>();
		String sqlStr = " DELETE FROM SYS_DEPT_INFO "
				+ " WHERE ( DEPT_CODE = :DEPT_CODE "
				+ " OR DEPT_CODE LIKE :DEPT_CODES)"
				+ " AND DEPT_CODE <> '00' ";
		paras.add(new AppDbParameter("DEPT_CODE", deptCode));
		paras.add(new AppDbParameter("DEPT_CODES", deptCode+"-%"));
		int result = baseDB.Delete(sqlStr, paras);
		HLogger.info("DeptManagerDal Function DeleteDept End!");
		return result;
	}

	/**
	 * 任务号：
	 * 描述：
	 * 作者：liuxingping
	 * 时间：2015年3月19日下午5:30:09
	 * @param baseDB
	 * @return
	 * @throws BaseBllException
	 */
	public AppDataTable GetDeptInfo(IDataBase baseDB) throws BaseBllException{
		HLogger.info("DeptManagerDal Function GetDeptInfo Start!");
		String sqlStr = " SELECT DEPT_CODE,DEPT_NAME FROM SYS_DEPT_INFO ";
		AppDataTable result = baseDB.Query(sqlStr, null);
		HLogger.info("DeptManagerDal Function GetDeptInfo End!");
		return result;
	}
	
	public AppDataTable GetDeptInfo(IDataBase baseDB,String deptCode) throws BaseBllException{
		HLogger.info("DeptManagerDal Function GetDeptInfo Start!");
		List<AppDbParameter> paras = new ArrayList<AppDbParameter>();
		String sqlStr = " SELECT DEPT_CODE,DEPT_NAME FROM SYS_DEPT_INFO WHERE 1=1 "
				+ " AND DEPT_CODE NOT LIKE :DEPT_CODES "
				+ " AND DEPT_CODE <> :DEPT_CODE ";
		paras.add(new AppDbParameter("DEPT_CODE", deptCode));
		paras.add(new AppDbParameter("DEPT_CODES", deptCode+"-%"));
		AppDataTable result = baseDB.Query(sqlStr, paras);
		HLogger.info("DeptManagerDal Function GetDeptInfo End!");
		return result;
	}
}
