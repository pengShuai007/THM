package com.kyee.cloud.patientManagement.bll;

import java.util.Date;

import model.base.PATIENT;

import com.kyee.cloud.patientManagement.dal.PatientManagementDal;

import APP.Comm.BLL.BaseBLL;
import APP.Comm.BLL.BaseBllException;
import APP.Comm.DataBase.Helper.AppDataTable;
import APP.Comm.Util.DotNetToJavaStringHelper;
import APP.Comm.Util.HLogger;
import APP.Comm.Util.JsonUtil;

public class PatientManagementBll extends BaseBLL{

	private PatientManagementDal patientMgtDal = new PatientManagementDal();
	
	public AppDataTable queryPatientByCondition() throws BaseBllException{
		String patientName = getParameterValue("PATIENT_NAME");
		String status = getParameterValue("STATUS");
		String createTime = getParameterValue("BEGIN_TIME");
		AppDataTable result = patientMgtDal.queryPatientByCondition(getBaseDB(),patientName,status,createTime,getGridRequestParameters());
		return result;
	}
	
	public int savePatient() throws BaseBllException{
		PATIENT patient = new PATIENT();
		String jsonStr = getParameterValue("postdata");
		if (DotNetToJavaStringHelper.isNullOrEmpty(jsonStr)) {
			HLogger.error("传入空的参数");
			return 0;
		}
		patient = (PATIENT) JsonUtil.jsonStringToObject(jsonStr, PATIENT.class);
		patient.setCREATETIME(new Date());
		int result = patientMgtDal.savePatient(getBaseDB(),patient);
		return result;
	}
	
	public int updatePatient() throws BaseBllException{
		PATIENT patient = new PATIENT();
		String jsonStr = getParameterValue("postdata");
		if (DotNetToJavaStringHelper.isNullOrEmpty(jsonStr)) {
			HLogger.error("传入空的参数");
			return 0;
		}
		patient = (PATIENT) JsonUtil.jsonStringToObject(jsonStr, PATIENT.class);
		int result = patientMgtDal.updatePatient(getBaseDB(),patient);
		return result;
	}
	
	public int visit() throws BaseBllException{
		String patient_id = getParameterValue("PATIENT_ID");
		int result = patientMgtDal.visit(getBaseDB(), patient_id);
		return result;
	}
}
