package com.kyee.cloud.patientManagement.dal;

import java.util.ArrayList;
import java.util.List;

import model.base.PATIENT;
import APP.Comm.BLL.BaseBllException;
import APP.Comm.DataBase.Helper.AppDataTable;
import APP.Comm.DataBase.Helper.IDataBase;
import APP.Comm.DataBase.Parameter.AppDbParameter;
import APP.Comm.Util.DotNetToJavaStringHelper;
import APP.Comm.View.GridRequestParameters;

public class PatientManagementDal {

	public AppDataTable queryPatientByCondition(IDataBase baseDb, String patientName,
			String status, String createTime,
			GridRequestParameters gridRequestParameters)
			throws BaseBllException {
		List<AppDbParameter> paramsList = new ArrayList<AppDbParameter>();
		String sql = "SELECT * FROM patient WHERE 1=1";
		if(!DotNetToJavaStringHelper.isNullOrEmpty(patientName)) {
            sql+=" AND PATIENT_NAME REGEXP :PATIENT_NAME";
            paramsList.add(new AppDbParameter("PATIENT_NAME", patientName));
        }
		if(!DotNetToJavaStringHelper.isNullOrEmpty(status)) {
            sql+=" AND STATUS =:STATUS";
            paramsList.add(new AppDbParameter("STATUS", status));
        }
		if(!DotNetToJavaStringHelper.isNullOrEmpty(createTime)) {
            sql+=" AND VISIT_DATE >= :CREATE_TIME";
            paramsList.add(new AppDbParameter("CREATE_TIME", createTime));
        }
		return baseDb.QueryByPage(sql, "ID", paramsList, gridRequestParameters);
	}
	
	public int savePatient(IDataBase baseDb,PATIENT patient) throws BaseBllException {
		return baseDb.Save(patient);
	}
	
	public int updatePatient(IDataBase baseDb,PATIENT patient) throws BaseBllException {
		List<AppDbParameter> paramsList = new ArrayList<AppDbParameter>();
		String sql =  " UPDATE                                  "
					+ "   patient                               "
					+ " SET                                     "
					+ "   PATIENT_NAME = :PATIENT_NAME,         "
					+ "   PATIENT_GENDER = :PATIENT_GENDER,     "
					+ "   PATIENT_AGE = :PATIENT_AGE,           "
					+ "   PATIENT_PHONE = :PATIENT_PHONE,       "
					+ "   REMARK = :REMARK                      "
					+ " WHERE ID = :ID                          ";
		paramsList.add(new AppDbParameter("PATIENT_NAME", patient.getPATIENT_NAME()));
		paramsList.add(new AppDbParameter("PATIENT_GENDER", patient.getPATIENT_GENDER()));
		paramsList.add(new AppDbParameter("PATIENT_AGE", patient.getPATIENT_AGE()));
		paramsList.add(new AppDbParameter("PATIENT_PHONE", patient.getPATIENT_PHONE()));
		paramsList.add(new AppDbParameter("REMARK", patient.getREMARK()));
		paramsList.add(new AppDbParameter("ID", patient.getID()));
		return baseDb.Update(sql, paramsList);
	}
	
	/**
	 * 
	* <pre>
	* 任务：
	* 描述：复诊
	* 作者：yangpeng 
	* 时间：2017年5月10日下午5:47:46
	* </pre>
	 */
	public int visit(IDataBase baseDb,String patient_id) throws BaseBllException{
		List<AppDbParameter> paramsList = new ArrayList<AppDbParameter>();
		String sql = "UPDATE patient SET STATUS = 0,IS_VISIT = 1 WHERE ID = :PATIENT_ID ";
		paramsList.add(new AppDbParameter("PATIENT_ID", patient_id));
		return baseDb.Update(sql, paramsList);
	}
}
