package com.kyee.cloud.systemParameter.action;

import model.base.SYS_USER;
import model.base.ext.SYS_PARAMETER_EXT;
import APP.Comm.BLL.BaseBllException;
import APP.Comm.DataBase.Helper.AppDataTable;
import APP.Comm.DotNet.HttpContext;
import APP.Comm.Util.HLogger;
import APP.Comm.Util.JsonUtil;
import APP.Comm.Util.OutJsonType;

import com.kyee.cloud.systemParameter.bll.SysParameterBll;

/**
 * <pre>
 * 任务:KYEEAPP-1006
 * 描述:建立系统参数（借鉴HRP的系统参数）
 * 作者:何萌
 * 日期:2014年11月13日13:58:10
 * @param context
 * </pre>
 */
public class SysParameterActionC {
	/**
	 * <pre>
	 * 任务：KYEEAPP-1006
	 * 描述：说明：获取所有的系统参数数据
	 * 作者：何萌
	 * 时间：2014年11月13日13:58:47
	 * </pre>
	 */
	public void GetAllSysParamOrderBySys(HttpContext context)
			throws BaseBllException {
		HLogger.info("SysParameterActionC  Function GetAllSysParamOrderBySys begin!");
		HLogger.info("说明：获取所有的系统参数数据");
		SysParameterBll sysParameter = new SysParameterBll();
		context.getHttpHandler().SetParamsToBLL(sysParameter, context);
		sysParameter.getBaseDB().NeedOpen(true);
		sysParameter.getBaseDB().NeedTransaction(false);
		String currentUser = "";
		SYS_USER appuser = (SYS_USER) context.getSessionAttribute("appuser");
		currentUser = appuser.getUSER_CODE();
		sysParameter.getAttrParams().put("appUser", currentUser);
		// edit add end KYEEAPP-1018 获取当前用户 罗代华 2014年11月17日16:55:45
		@SuppressWarnings("unchecked")
		java.util.LinkedHashMap<String, java.util.List<SYS_PARAMETER_EXT>> reslut = (java.util.LinkedHashMap<String, java.util.List<SYS_PARAMETER_EXT>>) context
				.getHttpHandler().BLLContainer.DoProcess(sysParameter,
				"GetAllSysParamOrderBySys");
		HLogger.info("SysParameterActionC  Function GetAllSysParamOrderBySys end!");
		context.write(JsonUtil.successMessageJsonString("",
				JsonUtil.objectToJsonString(reslut)));

	}

	/**
	 * <pre>
	 * 任务：KYEEAPP-1006
	 * 描述：说明：更新参数
	 * 作者：何萌
	 * 时间：2014年11月13日13:58:47
	 * </pre>
	 */
	public final void UpdateParam(HttpContext context) throws BaseBllException {
		HLogger.info("SysParameterActionC  Function UpdateParam begin!");
		HLogger.info("说明：参数更新");
		SysParameterBll sysParameter = new SysParameterBll();
		context.getHttpHandler().SetParamsToBLL(sysParameter, context);
		sysParameter.getBaseDB().NeedOpen(true);
		sysParameter.getBaseDB().NeedTransaction(true);
		String currentUser = "";
		SYS_USER appuser = (SYS_USER) context.getSessionAttribute("appuser");
		currentUser = appuser.getUSER_CODE();
		sysParameter.getAttrParams().put("updater", currentUser);
		int res = Integer.parseInt(context.getHttpHandler().BLLContainer
				.DoProcess(sysParameter, "UpdateParam") + "");
		HLogger.info("SysParameterActionC  Function UpdateParam end!");
		context.write(res > 0 ? JsonUtil.successMessageJsonString("更新成功！")
				: JsonUtil.errorMessageJsonString("更新失败！"));
	}

	/**
	 * <pre>
	 * 任务：KYEEAPP-1006
	 * 描述：获取radio值
	 * 作者：何萌
	 * 时间：2014年11月13日13:58:47
	 * </pre>
	 */
	public final void GetRadioTableValue(HttpContext context)
			throws BaseBllException {
		HLogger.info("SysParameterActionC  Function GetRadioTableValue begin!");
		HLogger.info("说明：获取radio值");
		SysParameterBll sysParameter = new SysParameterBll();
		context.getHttpHandler().SetParamsToBLL(sysParameter, context);
		sysParameter.getBaseDB().NeedOpen(true);
		sysParameter.getBaseDB().NeedTransaction(false);
		AppDataTable result = (AppDataTable) context.getHttpHandler().BLLContainer
				.DoProcess(sysParameter, "GetRadioTableValue");
		HLogger.info("SysParameterActionC  Function GetRadioTableValue end!");
		context.write(JsonUtil.dataTableToJsonString(
				Integer.parseInt(result.size() + ""), true, "成功",
				result.DataTable, OutJsonType.Common));
	}

}
