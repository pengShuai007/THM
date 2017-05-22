package com.kyee.cloud.DeptPostManager.dal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.base.ext.SYS_DEPT_INFO_EXT;
import model.base.ext.SYS_DEPT_POST_INFO_EXT;
import model.base.ext.SYS_POST_DICT_EXT;
import model.base.ext.SYS_USER_EXT;
import APP.Comm.BLL.AppUser;
import APP.Comm.BLL.BaseBllException;
import APP.Comm.DataBase.Helper.AppDataTable;
import APP.Comm.DataBase.Helper.IDataBase;
import APP.Comm.DataBase.Parameter.AppDbParameter;
import APP.Comm.Util.CommonUtil;
import APP.Comm.Util.DotNetToJavaStringHelper;
import APP.Comm.Util.HLogger;
import APP.Comm.View.GridRequestParameters;
import APP.Model.BaseEntity;


public class DeptPostManagerDal {
	/**
	 * 说明：查询 作者：李智博
	 */
	public AppDataTable queryDeptPost(IDataBase hrpdb, String deptCode,
			String postName, String deptName,
			GridRequestParameters gridRequestParameters)
			throws BaseBllException {

		String sql = new String(" SELECT * FROM "
				+ " (SELECT T.INFO_ID, T.DEPT_ID,"
				+ " M.DEPT_NAME,T.POST_ID,D.POST_NAME, "
				+ " CONCAT(M.DEPT_CODE,'*',D.POST_CODE) DEPT_CODE, "
				+ " T.CREATOR,T.CREATE_TIME, T.CREATOR_NAME "
				+ " FROM SYS_DEPT_POST_INFO T "
				+ " LEFT JOIN SYS_DEPT_INFO M ON T.DEPT_ID = M.DEPT_ID "
				+ " LEFT JOIN SYS_POST_DICT D ON T.POST_ID = D.POST_ID "
				+ " ) DD WHERE 1 = 1 ");
		List<AppDbParameter> paras = new ArrayList<AppDbParameter>();

		// 拼接部门的 V_SYS_DEPT_INFO中的 DEPT_GLOBAL_NO
//		if (!DotNetToJavaStringHelper.isNullOrEmpty(dept_global_no)) {
//			sql += " AND M.DEPT_GLOBAL_NO LIKE :DEPT_GLOBAL_NO || '%' ";
//			paras.add(new AppDbParameter("DEPT_GLOBAL_NO", dept_global_no));
//		}
		//查找部门下所有的岗位
		if (!DotNetToJavaStringHelper.isNullOrEmpty(deptCode)) {
			sql += " AND DD.DEPT_CODE LIKE :DEPT_CODE ";
			paras.add(new AppDbParameter("DEPT_CODE", deptCode+"*%"));
		}

		//任务号：HRPDRTESTJAVA-1023
		//修改：李智博
		//内容：添加模糊搜索
		if (!DotNetToJavaStringHelper.isNullOrEmpty(postName)) {
			sql += " AND DD.POST_NAME LIKE '%" + postName + "%'";
		}
		if (!DotNetToJavaStringHelper.isNullOrEmpty(deptName)) {
			sql += " AND DD.DEPT_NAME LIKE '%" + deptName + "%'";
		}
		sql += " ORDER BY DEPT_CODE ";
		AppDataTable result = hrpdb.QueryByPage(sql, "INFO_ID", paras,
				gridRequestParameters);
		return result;
	}

	/**
	 * 说明：查询字典 作者：李智博
	 */
	public AppDataTable getPostDict(IDataBase hrpdb,
			GridRequestParameters gridRequestParameters)
			throws BaseBllException {
		// 同时需要流程ID
		String sql = new String("SELECT T.POST_ID, T.POST_CODE, T.POST_NAME,"
				+ " T.PUB_ATTR,  T.VALIDATE_FLAG, T.COMMENTS, T.CREATOR,"
				+ " T.CREATE_TIME, T.CREATOR_NAME " + " FROM SYS_POST_DICT T ");
		List<AppDbParameter> paras = new ArrayList<AppDbParameter>();

		AppDataTable result = hrpdb.QueryByPage(sql, "POST_ID", paras,
				gridRequestParameters);
		return result;
	}

	/**
	 * 说明：查询岗位用户 作者：李智博
	 */
	public AppDataTable getPostUser(IDataBase hrpdb, String infoId,
			GridRequestParameters gridRequestParameters)
			throws BaseBllException {
		// 同时需要流程ID
		String sql = new String("SELECT M.DETAIL_ID,T.POST_ID,D.POST_NAME,"
				+ "M.USER_ID,N.USER_NAME,N.USER_CODE, M.CREATOR,M.CREATE_TIME,"
				+ " M.CREATOR_NAME "
				+ " FROM SYS_DEPT_POST_INFO  T,SYS_POST_DICT  D,"
				+ " SYS_DEPT_POST_DETAIL M, SYS_USER   N"
				+ " WHERE T.POST_ID = D.POST_ID "
				+ " AND T.INFO_ID = M.INFO_ID " + " AND M.USER_ID = N.USER_ID ");
		List<AppDbParameter> paras = new ArrayList<AppDbParameter>();
		if (!DotNetToJavaStringHelper.isNullOrEmpty(infoId)) {
			// 部门（岗位）
			sql += "AND T.INFO_ID = :INFO_ID";
			paras.add(new AppDbParameter("INFO_ID", infoId));
		}

		// 查询用户担任部门岗位，部门岗位等先不考虑
		sql += " ORDER BY N.USER_CODE ";
		AppDataTable result = hrpdb.QueryByPage(sql, "DETAIL_ID", paras,
				gridRequestParameters);
		return result;
	}

	/**
	 * 说明：查询岗位可选用户 作者：李智博
	 */
	public AppDataTable getPostNoUser(IDataBase hrpdb, String infoId,
			String userName, GridRequestParameters gridRequestParameters)
			throws BaseBllException {
		// 同时需要流程ID
		String sql = new String("SELECT T.USER_ID,T.USER_CODE,T.USER_NAME "
				+ " FROM SYS_USER T WHERE NOT EXISTS "
				+ " (SELECT 1 FROM SYS_DEPT_POST_DETAIL D "
				+ " WHERE T.USER_ID = D.USER_ID ");
		List<AppDbParameter> paras = new ArrayList<AppDbParameter>();
		if (!DotNetToJavaStringHelper.isNullOrEmpty(infoId)) {
			// 部门（岗位）
			sql += " AND D.INFO_ID = :INFO_ID";
			paras.add(new AppDbParameter("INFO_ID", infoId));
		}
		sql += " ) ";
		if (!DotNetToJavaStringHelper.isNullOrEmpty(userName)) {
			// 部门（岗位）
			sql += " AND (T.USER_ID LIKE '%" + userName + "%' OR ";
			sql += " T.USER_NAME LIKE '%" + userName + "%' )";
		}

		sql += " ORDER BY T.USER_CODE ";
		// 查询用户担任部门岗位，部门岗位等先不考虑
		AppDataTable result = hrpdb.QueryByPage(sql, "USER_ID", paras,
				gridRequestParameters);
		return result;
	}

	// 新增SYS_DEPT_POST_INFO_EXT
	public final int DoAdd(SYS_DEPT_POST_INFO_EXT entity, IDataBase baseDb)
			throws BaseBllException {
		HLogger.info("DeptPostManagerDal Function DoAdd begin !");
		HLogger.info("新增SYS_DEPT_POST_INFO_EXT !");
		int result = baseDb.Save(entity);
		HLogger.info("DeptPostManagerDal Function DoAdd end !");
		return result;
	}

	// 删除SYS_DEPT_POST_INFO_EXT
	public final int DoDelete(IDataBase hrpDb, String infoId)
			throws BaseBllException {
		HLogger.info("DeptPostManagerDal Function DoDelete begin !");
		HLogger.info("删除SYS_DEPT_POST_INFO_EXT !");
		String sql = " delete from SYS_DEPT_POST_INFO  where info_id =:INFO_ID";
		List<AppDbParameter> paras = new ArrayList<AppDbParameter>();
		paras.add(new AppDbParameter("INFO_ID", infoId));

		int result = hrpDb.Delete(sql, paras);

		HLogger.info("DeptPostManagerDal Function DoDelete end !");
		return result;
	}

	// 修改SYS_DEPT_POST_INFO_EXT
	public final int DoUpdate(SYS_DEPT_POST_INFO_EXT entity, IDataBase hrpDb)
			throws BaseBllException {
		HLogger.info("DeptPostManagerDal Function DoUpdate begin !");
		HLogger.info("修改SYS_DEPT_POST_INFO_EXT !");
		int result = hrpDb.Update(entity);
		HLogger.info("DeptPostManagerDal Function DoUpdate end !");
		return result;
	}

	// 新增SYS_POST_DICT
	public final int DoAddPostDict(SYS_POST_DICT_EXT entity, IDataBase hrpDb)
			throws BaseBllException {
		HLogger.info("DeptPostManagerDal Function DoAddPostDict begin !");
		HLogger.info("新增SYS_POST_DICT_EXT !");

		int result = hrpDb.Save(entity);
		HLogger.info("DeptPostManagerDal Function DoAddPostDict end !");
		return result;
	}

	// 删除SYS_DEPT_POST_INFO_EXT
	public final int DoDeletePostDict(IDataBase hrpDb, String postId)
			throws BaseBllException {
		HLogger.info("DeptPostManagerDal Function DoDeletePostDict begin !");
		HLogger.info("删除SYS_POST_DICT_EXT !");
		String sql = " delete from SYS_POST_DICT  where post_id =:POST_ID";
		List<AppDbParameter> paras = new ArrayList<AppDbParameter>();
		paras.add(new AppDbParameter("POST_ID", postId));

		int result = hrpDb.Delete(sql, paras);
		HLogger.info("DeptPostManagerDal Function DoDeletePostDict end !");
		return result;
	}

	// 修改SYS_POST_DICT_EXT
	public final int DoUpdatePostDict(SYS_POST_DICT_EXT entity, IDataBase hrpDb)
			throws BaseBllException {
		HLogger.info("DeptPostManagerDal Function DoUpdatePostDict begin !");
		HLogger.info("修改SYS_POST_DICT_EXT !");
		int result = hrpDb.Update(entity);
		HLogger.info("DeptPostManagerDal Function DoUpdatePostDict end !");
		return result;
	}

	// 获取科室
	public final List<BaseEntity> getDepts(IDataBase hrpDb, String deptCode)
			throws BaseBllException {
		HLogger.info("DeptPostManagerDal getDepts() Function deptCode :"
				+ deptCode);
		HLogger.info("input param  Function getDepts start!");
		List<AppDbParameter> AppDbParameters = new ArrayList<AppDbParameter>();
		StringBuilder strSql = new StringBuilder();
		strSql.append("SELECT T.SERIAL_NO, T.P_SERIAL_NO, T.DEPT_GLOBAL_NO, T.DEPT_NAME");
		strSql.append("	 FROM V_SYS_DEPT_INFO T ");
		// 某科室下的所有子集
		if (!DotNetToJavaStringHelper.isNullOrEmpty(deptCode)) {
			strSql.append(" WHERE DEPT_CODE LIKE :deptCode  OR DEPT_CODE = '0'");
			AppDbParameters.add(new AppDbParameter("deptCode", deptCode + "%"));
		}
		strSql.append("  ORDER BY T.DEPT_GLOBAL_NO ASC ");

		List<BaseEntity> result = hrpDb.QueryEntity(SYS_DEPT_INFO_EXT.class,
				strSql + "", null).getEntityList();
		HLogger.info("DeptPostManagerDal getDepts() Function getDepts end!");
		return result;
	}

	// 取消配置用户
	public final int cancelUserPost(IDataBase hrpDb, String infoId,
			String userId) throws BaseBllException {
		HLogger.info("DeptPostManagerDal Function cancelUserPost begin !");
		String sql = " delete from SYS_DEPT_POST_DETAIL "
				+ " where INFO_ID = :INFO_ID AND USER_ID =:USER_ID";
		List<AppDbParameter> paras = new ArrayList<AppDbParameter>();
		paras.add(new AppDbParameter("INFO_ID", infoId));
		paras.add(new AppDbParameter("USER_ID", userId));

		int result = hrpDb.Delete(sql, paras);

		HLogger.info("DeptPostManagerDal Function cancelUserPost end !");
		return result;
	}

	// 岗位配置用户
	public final int saveUserPost(IDataBase hrpDb, SYS_USER_EXT appUser,
			String infoId, String userId) throws BaseBllException {
		HLogger.info("DeptPostManagerDal Function saveUserPost begin !");
		List<AppDbParameter> paras = new ArrayList<AppDbParameter>();

		String sql = " INSERT INTO SYS_DEPT_POST_DETAIL "
				+ " (INFO_ID, USER_ID, CREATOR, CREATE_TIME, CREATOR_NAME) "
				+ " VALUES "
				+ " (:INFO_ID, :USER_ID, :CREATOR, :CREATE_TIME, :CREATOR_NAME)";
		paras.add(new AppDbParameter("INFO_ID", infoId));
		paras.add(new AppDbParameter("USER_ID", userId));

		paras.add(new AppDbParameter("CREATOR", appUser.getUSER_CODE()));
		paras.add(new AppDbParameter("CREATOR_NAME", appUser.getUSER_NAME()));
		paras.add(new AppDbParameter("CREATE_TIME", CommonUtil
				.parse2SqlDate(CommonUtil.formatDate(new Date()))));

		int result = hrpDb.Save(sql, paras);
		HLogger.info("DeptPostManagerDal Function saveUserPost end !");
		return result;
	}

	/**
	 * 任务号：
	 * 描述：
	 * 作者：liuxingping
	 * 时间：2015年3月17日下午3:15:01
	 * @param baseDB
	 * @return
	 * @throws BaseBllException
	 */
	public List<BaseEntity> GetSysDeptTree(IDataBase baseDB) throws BaseBllException {
		HLogger.info("DeptPostManagerDal Function GetSysDeptTree Start!");
		String sqlStr = " SELECT * FROM SYS_DEPT_INFO WHERE DEPT_CODE = '00' ";
		List<BaseEntity> result = baseDB.QueryEntity(SYS_DEPT_INFO_EXT.class, sqlStr, null).getEntityList();
		HLogger.info("DeptPostManagerDal Function GetSysDeptTree End!");
		return result;
	}
	
	/**
	 * 任务号：
	 * 描述：
	 * 作者：liuxingping
	 * 时间：2015年3月17日下午4:09:37
	 * @param baseDB
	 * @param deptListCode
	 * @return
	 * @throws BaseBllException
	 */
	public List<BaseEntity> GetSysDeptTree(IDataBase baseDB, String deptCode) throws BaseBllException {
		HLogger.info("DeptPostManagerDal Function GetSysDeptTree Start!");
		List<AppDbParameter> paras = new ArrayList<AppDbParameter>();
		String sqlStr = " SELECT * FROM SYS_DEPT_INFO WHERE 1=1 "
				+ " AND DEPT_CODE LIKE :DEPT_CODE "
				+ " AND DEPT_CODE NOT LIKE :DEPT_CODES "
				+ " ORDER BY DEPT_CODE ";
		paras.add(new AppDbParameter("DEPT_CODE", deptCode+"-%"));
		paras.add(new AppDbParameter("DEPT_CODES", deptCode+"-%"+"-%"));
		List<BaseEntity> result = baseDB.QueryEntity(SYS_DEPT_INFO_EXT.class, sqlStr, paras).getEntityList();
		HLogger.info("DeptPostManagerDal Function GetSysDeptTree End!");
		return result;
	}

	public AppDataTable initPostDict(IDataBase baseDB) throws BaseBllException {
		HLogger.info("DeptPostManagerDal Function GetSysDeptTree Start!");

		String sqlStr = " SELECT * FROM SYS_POST_DICT WHERE VALIDATE_FLAG=1 ";
		AppDataTable result = baseDB.Query(sqlStr, null);
		HLogger.info("DeptPostManagerDal Function GetSysDeptTree End!");
		return result;
	}
}