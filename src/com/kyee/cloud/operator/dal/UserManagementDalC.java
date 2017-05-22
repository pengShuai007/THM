
package com.kyee.cloud.operator.dal;

import java.util.ArrayList;
import java.util.List;

import APP.Comm.BLL.BaseBllException;
import APP.Comm.DataBase.Helper.AppDataTable;
import APP.Comm.DataBase.Helper.IDataBase;
import APP.Comm.DataBase.Parameter.AppDbParameter;
import APP.Comm.Util.DotNetToJavaStringHelper;
import APP.Comm.Util.HLogger;

/**
 * <pre>
 * 作者:刘健
 * 日期:2014年3月6日 下午4:59:26
 * 描述:用户忘记密码后修改密码
 * </pre>
 */
public class UserManagementDalC {
	
	
	/**
	 * 
	 * <pre>
	 * 作者:刘健
	 * 日期:2014年3月6日 下午5:02:05
	 * 描述:用户忘记密码后修改密码
	 * </pre>
	 */
	public int updatepwd(IDataBase appDb,String pwd,String usercode)throws BaseBllException{
		String sql="UPDATE C_ACCOUNT_USER SET PASSWORD=:PASSWORD WHERE USER_CODE=:USERCODE ";
		List<AppDbParameter> Parameters=new ArrayList<AppDbParameter>();
		Parameters.add(new AppDbParameter("PASSWORD",pwd));
		Parameters.add(new AppDbParameter("USERCODE",usercode));
		return appDb.Update(sql, Parameters);
	}
	
	
	public AppDataTable GetUserMenuPerm(IDataBase baseDB, String userCode) throws BaseBllException{
		HLogger.info("UserManagementBllC Function GetUserMenuPerm Start!");
		List<AppDbParameter> parameters=new ArrayList<AppDbParameter>();
		String sqlStr = " SELECT SPR.ROLE_ID FROM (SELECT SS.* FROM (SELECT DISTINCT SDI.DEPT_CODE "
				+ " FROM SYS_DEPT_POST_DETAIL SDPDS, SYS_USER SUS, SYS_DEPT_POST_INFO SDPIS, SYS_DEPT_INFO SDI "
				+ " WHERE SUS.USER_CODE = :USER_CODE AND SDPDS.USER_ID = SUS.USER_ID AND SDPDS.INFO_ID = SDPIS.INFO_ID "
				+ " AND SDPIS.DEPT_ID = SDI.DEPT_ID) TT ,SYS_DEPT_INFO SS "
				+ " WHERE TT.DEPT_CODE LIKE CONCAT(SS.DEPT_CODE,'%')) DD ,SYS_DEPT_POST_ROLE SPR "
				+ " WHERE DD.DEPT_ID = SPR.DEPT_ID AND SPR.DEPT_OR_POST = 0 "
				+ " UNION "
				+ " SELECT SDPR.ROLE_ID FROM (SELECT SDPI.* FROM SYS_DEPT_POST_DETAIL SDPD, SYS_USER SU, SYS_DEPT_POST_INFO SDPI "
				+ " WHERE SU.USER_ID = SDPD.USER_ID AND SDPD.INFO_ID = SDPI.INFO_ID AND SU.USER_CODE = :USER_CODE) S, SYS_DEPT_POST_ROLE SDPR "
				+ " WHERE S.DEPT_ID = SDPR.DEPT_ID AND S.POST_ID = SDPR.POST_ID ";
		parameters.add(new AppDbParameter("USER_CODE",userCode));
		AppDataTable result = baseDB.Query(sqlStr, parameters);
		HLogger.info("UserManagementBllC Function GetUserMenuPerm End!");
		return result;
	}

	public AppDataTable GetUserPost(IDataBase baseDB, String userCode) throws BaseBllException{
		HLogger.info("UserManagementBllC Function GetUserMenuPerm Start!");
		List<AppDbParameter> parameters=new ArrayList<AppDbParameter>();
		String sqlStr = " SELECT SU.USER_CODE,SU.USER_NAME,SDP.POST_ID,SPD.POST_NAME FROM SYS_USER SU "
				+ " INNER JOIN SYS_DEPT_POST_DETAIL SD ON SU.USER_ID = SD.USER_ID  "
				+ " INNER JOIN SYS_DEPT_POST_INFO SDP ON SD.INFO_ID = SDP.INFO_ID "
				+ " INNER JOIN SYS_POST_DICT SPD ON SDP.POST_ID = SPD.POST_ID "
				+ " AND SU.USER_CODE = :USER_CODE ";
		parameters.add(new AppDbParameter("USER_CODE",userCode));
		AppDataTable result = baseDB.Query(sqlStr, parameters);
		HLogger.info("UserManagementBllC Function GetUserMenuPerm End!");
		return result;
	}
	
	/**
	 * 任务号：
	 * 描述：获取用户
	 * 作者：liuxingping
	 * 时间：2015年5月14日上午9:28:32
	 * @param baseDB
	 * @param userCode
	 * @return
	 * @throws BaseBllException
	 */
	public AppDataTable gerUserAreaIds(IDataBase baseDB, String userCode) throws BaseBllException{
		HLogger.info("UserManagementBllC Function GetUserMenuPerm Start!");
		userCode = "%,"+userCode+",%";
		List<AppDbParameter> parameters=new ArrayList<AppDbParameter>();
		String sqlStr = " SELECT AREA_ID FROM AREA_INFO WHERE CONCAT(',',SUPER_CODES,',') LIKE :SUPER_CODES ";
		parameters.add(new AppDbParameter("SUPER_CODES",userCode));
		AppDataTable result = baseDB.Query(sqlStr, parameters);
		HLogger.info("UserManagementBllC Function GetUserMenuPerm End!");
		return result;
	}
	
	/**
	 * 任务号：
	 * 描述：通过区域ID获取所有子节点加本身
	 * 作者：liuxingping
	 * 时间：2015年5月14日上午11:26:32
	 * @param baseDB
	 * @param areaId
	 * @return
	 * @throws BaseBllException
	 */
	public String getChildAreaById(IDataBase baseDB, String areaId) throws BaseBllException {
		HLogger.info("UserManagementBllC Function GetUserMenuPerm Start!");
		String sqlStr = " SELECT getAreaChildLst("+areaId+") ";
		String result = (String) baseDB.QueryObject(sqlStr, null);
		HLogger.info("UserManagementBllC Function GetUserMenuPerm End!");
		return result;
	}
	/**
	 * 任务号：
	 * 描述：通过区域ID获取可查看医院
	 * 作者：liuxingping
	 * 时间：2015年5月14日上午11:27:02
	 * @param baseDB
	 * @param areaIds
	 * @return
	 * @throws BaseBllException
	 */
	public String gerUserHospIds(IDataBase baseDB, String areaIds) throws BaseBllException {
		HLogger.info("UserManagementBllC Function GetUserMenuPerm Start!");
		String sqlStr = " SELECT GROUP_CONCAT(C.HOSPITAL_ID) FROM "
				+ " (SELECT DISTINCT HOSPITAL_ID FROM AREA_HOSP_DETAIL WHERE AREA_ID IN ("+areaIds+")) C  ";
		String result = (String) baseDB.QueryObject(sqlStr, null);
		HLogger.info("UserManagementBllC Function GetUserMenuPerm End!");
		return result;
	}
	
	/**
	 * 任务号：
	 * 描述：
	 * 作者：liuxingping
	 * 时间：2015年5月14日下午2:04:03
	 * @param appDb
	 * @return
	 * @throws BaseBllException
	 */
	public AppDataTable queryProvince(IDataBase appRdDb) throws BaseBllException {
		HLogger.info("MedicalGuideDal Function queryProvince begin!");
		String sql = "SELECT PROVINCE_ID,PROVINCE_NAME FROM C_SYSTEM_PROVINCE";
		AppDataTable result = appRdDb.Query(sql, null);
		HLogger.info("MedicalGuideDal Function queryProvince end!");
		return result;
	}
	
	/**
	 * 任务号：
	 * 描述：
	 * 作者：liuxingping
	 * 时间：2015年5月14日下午2:29:17
	 * @param appDb
	 * @param hospIds
	 * @return
	 * @throws BaseBllException
	 */
	public AppDataTable queryProvince(IDataBase appRdDb,String hospIds) throws BaseBllException {
		HLogger.info("MedicalGuideDal Function queryProvince begin!");
		String sql = " SELECT DISTINCT CSP.PROVINCE_ID,CSP.PROVINCE_NAME FROM c_system_hospital CSH "
				+ " INNER JOIN c_system_city CSC ON CSH.CITY_ID = CSC.CITY_ID INNER JOIN c_system_province CSP "
				+ " ON CSC.PROVINCE_ID = CSP.PROVINCE_ID AND CSH.STATUS = 1 AND CSH.HOSPITAL_ID IN ("+hospIds+") ";
		AppDataTable result = appRdDb.Query(sql, null);
		HLogger.info("MedicalGuideDal Function queryProvince end!");
		return result;
	}

	/**
	 * 任务号：
	 * 描述：
	 * 作者：liuxingping
	 * 时间：2015年5月14日下午2:04:07
	 * @param appDb
	 * @param provinceID
	 * @return
	 * @throws BaseBllException
	 */
	public AppDataTable queryCity(IDataBase appRdDb, String provinceID)
			throws BaseBllException {
		HLogger.info("MedicalGuideDal Function queryCity begin!");
		String sql = "SELECT CITY_ID,CITY_NAME FROM C_SYSTEM_CITY WHERE 1=1";
		// 判断省份ID是否为空，依据省份ID条件查询
		if (!DotNetToJavaStringHelper.isNullOrEmpty(provinceID)) {
			sql += " AND PROVINCE_ID IN (" + provinceID + ")";
		}
		AppDataTable result = appRdDb.Query(sql, null);
		HLogger.info("MedicalGuideDal Function queryCity end!");
		return result;
	}

	public AppDataTable queryCity(IDataBase appRdDb, String provinceID, String hospIds)
			throws BaseBllException {
		HLogger.info("MedicalGuideDal Function queryCity begin!");
//		String sql = "SELECT CITY_ID,CITY_NAME FROM C_SYSTEM_CITY WHERE 1=1";
		String sql = " SELECT DISTINCT CSC.CITY_ID,CSC.CITY_NAME FROM c_system_hospital CSH "
				+ " INNER JOIN c_system_city CSC ON CSH.CITY_ID = CSC.CITY_ID INNER JOIN c_system_province CSP "
				+ " ON CSC.PROVINCE_ID = CSP.PROVINCE_ID AND CSH.STATUS = 1 AND CSH.HOSPITAL_ID IN ("+hospIds+") ";
		// 判断省份ID是否为空，依据省份ID条件查询
		if (!DotNetToJavaStringHelper.isNullOrEmpty(provinceID)) {
			sql += " AND CSP.PROVINCE_ID IN (" + provinceID + ")";
		}
		AppDataTable result = appRdDb.Query(sql, null);
		HLogger.info("MedicalGuideDal Function queryCity end!");
		return result;
	}
	/**
	 * 任务号：
	 * 描述：
	 * 作者：liuxingping
	 * 时间：2015年5月14日下午2:04:10
	 * @param baseDb
	 * @param provinceName
	 * @param cityName
	 * @return
	 * @throws BaseBllException
	 */
	public AppDataTable queryHospital(IDataBase baseDb, String provinceName,
			String cityName) throws BaseBllException {
		HLogger.info("MedicalGuideDal Function queryHospital begin!");
		String sql = "SELECT H.HOSPITAL_ID,H.HOSPITAL_NAME,H.PLATFORM FROM HOSP_BASIC_INFO H WHERE MEDICAL_GUIDE_STATUS=1 ";
		// 判断省份名称是否为空，依据省份名称条件查询
		if (!DotNetToJavaStringHelper.isNullOrEmpty(provinceName)) {
			sql += " AND H.PROVINCE_NAME IN (" + "'" + provinceName + "'" + ")";
		}
		// 判断城市名称是否为空，依据城市名称条件查询
		if (!DotNetToJavaStringHelper.isNullOrEmpty(cityName)) {
			sql += " AND H.CITY_NAME IN (" + "'" + cityName + "'" + ")";
		}
		AppDataTable result = baseDb.Query(sql, null);
		HLogger.info("MedicalGuideDal Function queryHospital end!");
		return result;
	}
	
	public AppDataTable queryHospital(IDataBase baseDb, String provinceName,
			String cityName, String hospIds) throws BaseBllException {
		HLogger.info("MedicalGuideDal Function queryHospital begin!");
		String sql = "SELECT H.HOSPITAL_ID,H.HOSPITAL_NAME,H.PLATFORM FROM HOSP_BASIC_INFO H WHERE MEDICAL_GUIDE_STATUS=1 "
				+ " AND HOSPITAL_ID IN ("+hospIds+")";
		// 判断省份名称是否为空，依据省份名称条件查询
		if (!DotNetToJavaStringHelper.isNullOrEmpty(provinceName)) {
			sql += " AND H.PROVINCE_NAME IN (" + "'" + provinceName + "'" + ")";
		}
		// 判断城市名称是否为空，依据城市名称条件查询
		if (!DotNetToJavaStringHelper.isNullOrEmpty(cityName)) {
			sql += " AND H.CITY_NAME IN (" + "'" + cityName + "'" + ")";
		}
		AppDataTable result = baseDb.Query(sql, null);
		HLogger.info("MedicalGuideDal Function queryHospital end!");
		return result;
	}
	
	/**
	 * 任务号：
	 * 描述：在正式云查询供应商
	 * 作者：liuxingping
	 * 时间：2015年5月25日下午3:56:05
	 * @param appRdDb
	 * @param provinceID
	 * @param hospIds
	 * @return
	 * @throws BaseBllException
	 */
	public AppDataTable initSupplier(IDataBase appRdDb)
			throws BaseBllException {
		HLogger.info("MedicalGuideDal Function queryCity begin!");
		String sql = " SELECT SUPPLIER_ID,SUPPLIER_NAME FROM C_MEDICAL_GUIDE_SUPPLIER ";
		AppDataTable result = appRdDb.Query(sql, null);
		HLogger.info("MedicalGuideDal Function queryCity end!");
		return result;
	}
}
