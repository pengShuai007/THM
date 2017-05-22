package com.kyee.cloud.systemParameter.bll;

import java.util.ArrayList;
import java.util.List;

import model.base.ext.SYS_PARAMETER_EXT;
import APP.Comm.BLL.BaseBLL;
import APP.Comm.BLL.BaseBllException;
import APP.Comm.DataBase.Helper.AppEntitys;
import APP.Comm.Util.DotNetToJavaStringHelper;
import APP.Comm.Util.HLogger;
import APP.Comm.Util.JsonUtil;
import APP.Model.BaseEntity;

import com.kyee.cloud.systemParameter.dal.SysParameterDal;
import com.util.SqlDateUtil;


/**
 * 
 * <pre>
 * 任务：KYEEAPP-1006
 * 描述：系统参数整理
 * 作者：何萌
 * 时间：2014年11月13日13:58:47
 * </pre>
 */
public class SysParameterBll extends BaseBLL {
	private SysParameterDal _sysParameterDal = new SysParameterDal();
	/** 
	 * <pre>
	 * 任务：KYEEAPP-1006
	 * 描述：参数更新
	 * 作者：何萌
	 * 时间：2014年11月13日14:09:34
	 * </pre>
	 */
	public final Object UpdateParam() throws BaseBllException {
		HLogger.info("SysParameterBll  Function UpdateParam begin!");
		HLogger.info("说明：参数更新");
		String sysParams = super.getParameterValue("obj");
		String[] objs = sysParams.split("[|]", -1);
		java.util.List<BaseEntity> sysParameterExtsList = new java.util.ArrayList<BaseEntity>();
		if (objs.length > 0) {
			String currentTime=SqlDateUtil.getSystemTime().toString();
			for (String obj : objs) {
				SYS_PARAMETER_EXT sysParameterExt = (SYS_PARAMETER_EXT) JsonUtil
						.jsonStringToObject(obj, SYS_PARAMETER_EXT.class);
				sysParameterExt.setUPDATER(super.getAttrParameterValue("updater"));
				sysParameterExt.setUPDATE_TIME(currentTime);
				sysParameterExtsList.add(sysParameterExt);
			}
		} else {
			throw new BaseBllException("页面传入修改数据不成功，请重试或联系调试人员进行调试！");
		}
		Object result = super.getBaseDB().BulkUpdate(sysParameterExtsList, 300);
		HLogger.info("SysParameterBll  Function UpdateParam end!");
		return result;
	}

	/** 
	 * <pre>
	 * 任务：KYEEAPP-1006
	 * 描述：获取Radio的值
	 * 作者：何萌
	 * 时间：2014年11月13日14:09:56
	 * </pre>
	 */
	public final Object GetRadioTableValue() throws BaseBllException {
		HLogger.info("SysParameterBll  Function GetRadioTableValue begin!");
		HLogger.info("说明：获取Radio的值");
		SYS_PARAMETER_EXT sysParameterExt = (SYS_PARAMETER_EXT) JsonUtil
				.jsonStringToObject(super.getParameterValue("obj"),
						SYS_PARAMETER_EXT.class);
		Object result = _sysParameterDal.GetRadioTableValue(sysParameterExt,
				super.getBaseDB());
		HLogger.info("SysParameterBll  Function GetRadioTableValue end!");
		return result;
	}
	
	/** 
	 * <pre>
	 * 任务：KYEEAPP-1006
	 * 描述：获取按系统排序的系统参数
	 * 作者：何萌
	 * 修改者：罗代华
	 * 时间：2014年11月17日16:52:52
	 * </pre>
	 */
	public final java.util.LinkedHashMap<String, java.util.List<SYS_PARAMETER_EXT>> GetAllSysParamOrderBySys()
			throws BaseBllException {
		HLogger.info("SysParameterBll  Function GetAllSysParamOrderBySys begin!");
		HLogger.info("说明：获取按系统排序的系统参数");
		java.util.LinkedHashMap<String, java.util.List<SYS_PARAMETER_EXT>> result = new java.util.LinkedHashMap<String, java.util.List<SYS_PARAMETER_EXT>>();		
		String currentUser = super.getAttrParameterValue("appUser");
		AppEntitys buinessEntitys = _sysParameterDal.queryBusinessCode(
				super.getBaseDB(),currentUser);
		if (buinessEntitys == null
				|| buinessEntitys.getEntityList().size() == 0) {
			return result;
		}
		AppEntitys entitys=null;
		if(!("admin".equals(currentUser))){
			entitys = _sysParameterDal.GetAllSysParamOrderBySys(
					super.getBaseDB(),true);
		}else{
			entitys = _sysParameterDal.GetAllSysParamOrderBySys(
					super.getBaseDB(),false);
		}
		for (BaseEntity o : buinessEntitys.getEntityList()) {
			SYS_PARAMETER_EXT buinessCodeEntity = (SYS_PARAMETER_EXT) o;
			String buinessCodeString = buinessCodeEntity.getBUSINESS_CODE();
			List<SYS_PARAMETER_EXT> tmpList = new ArrayList<SYS_PARAMETER_EXT>();
			for (BaseEntity obj : entitys.getEntityList()) {
				SYS_PARAMETER_EXT entity = (SYS_PARAMETER_EXT) obj;
				if (!DotNetToJavaStringHelper.isNullOrEmpty(entity
						.getBUSINESS_CODE())) {
					if (buinessCodeString.equals(entity.getBUSINESS_CODE())) {
						if("admin".equals(currentUser)) {
							entity.setPARA_TYPE("A");
						}
						tmpList.add(entity);
					}
				}
			}
			result.put(buinessCodeString, tmpList);
		}
		HLogger.info("SysParameterBll  Function GetAllSysParamOrderBySys end!");
		return result;
	}
}
