package com.kyee.cloud.patientManagement.action;

import APP.Comm.BLL.BaseBllException;
import APP.Comm.DataBase.Helper.AppDataTable;
import APP.Comm.DotNet.HttpContext;
import APP.Comm.Util.JsonUtil;
import APP.Comm.Util.OutJsonType;

import com.kyee.cloud.patientManagement.bll.PatientManagementBll;

public class PatientManagementAction {

	private PatientManagementBll patientMgtBll = new PatientManagementBll();
	
	public void queryPatientByCondition(HttpContext context) throws BaseBllException{
		context.getHttpHandler().SetParamsToBLL(patientMgtBll, context);
		patientMgtBll.getBaseDB().NeedOpen(true);
		patientMgtBll.getBaseDB().NeedTransaction(false);
		AppDataTable result = (AppDataTable) context.getHttpHandler().BLLContainer
				.DoProcess(patientMgtBll, "queryPatientByCondition");
		context.write(result != null ? JsonUtil.dataTableToJsonString(
				Integer.parseInt(result.size() + ""), true, "初始化查询成功",
				result.DataTable, OutJsonType.Grid) : JsonUtil
				.errorMessageJsonString("暂无患者信息"));
	}
	
	public void savePatient(HttpContext context) throws BaseBllException{
		context.getHttpHandler().SetParamsToBLL(patientMgtBll, context);
		patientMgtBll.getBaseDB().NeedOpen(true);
		patientMgtBll.getBaseDB().NeedTransaction(true);
		int result = (int) context.getHttpHandler().BLLContainer.DoProcess(
				patientMgtBll, "savePatient");
		context.write(result > 0 ? JsonUtil.successMessageJsonString("新增成功",
				JsonUtil.objectToJsonString(result)) : JsonUtil
				.errorMessageJsonString("新增失败！"));
	}
	
	public void updatePatient(HttpContext context) throws BaseBllException{
		context.getHttpHandler().SetParamsToBLL(patientMgtBll, context);
		patientMgtBll.getBaseDB().NeedOpen(true);
		patientMgtBll.getBaseDB().NeedTransaction(true);
		int result = (int) context.getHttpHandler().BLLContainer.DoProcess(
				patientMgtBll, "updatePatient");
		context.write(result > 0 ? JsonUtil.successMessageJsonString("修改成功",
				JsonUtil.objectToJsonString(result)) : JsonUtil
				.errorMessageJsonString("修改失败！"));
	}
	
	public void visit(HttpContext context) throws BaseBllException{
		context.getHttpHandler().SetParamsToBLL(patientMgtBll, context);
		patientMgtBll.getBaseDB().NeedOpen(true);
		patientMgtBll.getBaseDB().NeedTransaction(true);
		int result = (int) context.getHttpHandler().BLLContainer.DoProcess(
				patientMgtBll, "visit");
		context.write(result > 0 ? JsonUtil.successMessageJsonString("复诊成功，请耐心等待",
				JsonUtil.objectToJsonString(result)) : JsonUtil
				.errorMessageJsonString("复诊失败！"));
	}
}
