package com.kyee.cloud.diagnosis.dal;

import java.util.ArrayList;
import java.util.List;

import APP.Comm.BLL.BaseBllException;
import APP.Comm.DataBase.Helper.IDataBase;
import APP.Comm.DataBase.Parameter.AppDbParameter;

public class DiagnosisDal {
	
	public int beginDiagnosis(IDataBase baseDb,String patientId) throws BaseBllException{
		List<AppDbParameter> paramsList = new ArrayList<AppDbParameter>();
		String sql = "UPDATE patient SET STATUS = 1 WHERE ID = :PATIENT_ID ";
		paramsList.add(new AppDbParameter("PATIENT_ID", patientId));
		return baseDb.Update(sql, paramsList);
	}

}
