package com.kyee.cloud.diagnosis.action;

import APP.Comm.BLL.BaseBllException;
import APP.Comm.DotNet.HttpContext;
import APP.Comm.Util.JsonUtil;

import com.kyee.cloud.diagnosis.bll.DiagnosisBll;

public class DiagnosisAction {

	private DiagnosisBll diagnosisBll = new DiagnosisBll();
	
	public void beginDiagnosis(HttpContext context) throws BaseBllException{
		context.getHttpHandler().SetParamsToBLL(diagnosisBll, context);
		diagnosisBll.getBaseDB().NeedOpen(true);
		diagnosisBll.getBaseDB().NeedTransaction(true);
		int result = (int) context.getHttpHandler().BLLContainer.DoProcess(
				diagnosisBll, "beginDiagnosis");
		context.write(result > 0 ? JsonUtil.successMessageJsonString("开始诊断",
				JsonUtil.objectToJsonString(result)) : JsonUtil
				.errorMessageJsonString("修改失败！"));
	}
}
