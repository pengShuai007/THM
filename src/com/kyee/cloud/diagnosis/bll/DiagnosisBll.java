package com.kyee.cloud.diagnosis.bll;

import com.kyee.cloud.diagnosis.dal.DiagnosisDal;

import APP.Comm.BLL.BaseBLL;
import APP.Comm.BLL.BaseBllException;

public class DiagnosisBll extends BaseBLL {

	private DiagnosisDal diagnosisDal = new DiagnosisDal();
	
	public int beginDiagnosis() throws BaseBllException{
		String patient_id = getParameterValue("PATIENT_ID");
		int result = diagnosisDal.beginDiagnosis(getBaseDB(), patient_id);
		return result;
	}
}
