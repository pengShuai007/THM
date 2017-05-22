
package com.kyee.cloud.operator.action;

import java.util.Map;

import com.kyee.cloud.operator.bll.UserManagementBllC;

import APP.Comm.BLL.BaseBllException;
import APP.Comm.DataBase.Helper.AppDataTable;
import APP.Comm.DotNet.HttpContext;
import APP.Comm.Util.HLogger;
import APP.Comm.Util.JsonUtil;
import APP.Comm.Util.OutJsonType;

/**
 * <pre>
 * 作者:刘健
 * 日期:2014年3月6日 下午4:58:13
 * 描述:用户忘记密码后修改密码
 * </pre>
 */
public class UserManagementActionC {
	
	private UserManagementBllC userBll=new UserManagementBllC(); 
	/**
	 * 
	 * <pre>
	 * 作者:刘健
	 * 日期:2014年3月10日 下午3:27:02
	 * 描述:检查用户名是否存在
	 * </pre>
	 */
//	public void checkuser(HttpContext context) throws BaseBllException {
//		context.getHttpHandler().SetParamsToBLL(userBll,context);
//		userBll.getAppDB().NeedOpen(true);
//		userBll.getAppDB().NeedTransaction(false);
//		boolean result = (Boolean) context.getHttpHandler().BLLContainer
//				.DoProcess(userBll, "checkuser");
//		context.write(result ? JsonUtil.successMessageJsonString("用户名正确！")
//				: JsonUtil.errorMessageJsonString("用户名不正确！"));
//	}
	/**
	 * 
	 * <pre>
	 * 作者:刘健
	 * 日期:2014年3月6日 下午5:25:29
	 * 描述:用户忘记密码后修改密码
	 * </pre>
	 */
//	public void updatepwd(HttpContext context)throws BaseBllException{
//		context.getHttpHandler().SetParamsToBLL(userBll,context);
//		userBll.getAppDB().NeedOpen(true);
//		userBll.getAppDB().NeedTransaction(true);
//		int result = (int) context.getHttpHandler().BLLContainer.DoProcess(
//				userBll, "updatepwd");
//		context.write(result>0 ? JsonUtil.successMessageJsonString(
//				"修改成功！", JsonUtil.objectToJsonString(result)) : JsonUtil
//				.errorMessageJsonString("修改失败！"));
//	}
	
//	public void GetUserMenuPerm(HttpContext context) throws BaseBllException {
//		HLogger.info("UserManagementActionC Function GetUserMenuPerm Start!");
//		context.getHttpHandler().SetParamsToBLL(userBll, context);
//		userBll.getAppDB().NeedOpen(true);
//		userBll.getAppDB().NeedTransaction(false);
//		AppDataTable result = (AppDataTable) context.getHttpHandler().BLLContainer
//				.DoProcess(userBll, "GetUserMenuPerm");
//		HLogger.info("UserManagementActionC Function GetUserMenuPerm End!");
//		context.write(result != null ? JsonUtil.dataTableToJsonString(
//				result.DataTable.getRows().size(), true, "查询成功",
//				result.DataTable, OutJsonType.Common) : JsonUtil
//				.errorMessageJsonString("查询失败"));
//	}
	
	/**
	 * 任务号：
	 * 描述：
	 * 作者：liuxingping
	 * 时间；2015年3月24日下午6:08:16
	 * @param context
	 * @throws BaseBllException
	 */
	@SuppressWarnings("unchecked")
	public void getUserHosp(HttpContext context) throws BaseBllException {
		HLogger.info("UserManagementActionC Function GetUserMenuPerm Start!");
		context.getHttpHandler().SetParamsToBLL(userBll, context);
		userBll.getBaseDB().NeedOpen(true);
		userBll.getBaseDB().NeedTransaction(false);
		Map<String,String> result = (Map<String,String>) context.getHttpHandler().BLLContainer
				.DoProcess(userBll, "getUserHosp");
		HLogger.info("UserManagementActionC Function GetUserMenuPerm End!");
//		context.write(result != null ? JsonUtil.dataTableToJsonString(
//				result.DataTable.getRows().size(), true, "查询成功",
//				result.DataTable, OutJsonType.Common) : JsonUtil
//				.errorMessageJsonString("查询失败"));
		context.write(JsonUtil.objectToJsonString(result));
	}
	
	public void queryProvince(HttpContext context) throws BaseBllException {
        HLogger.info("UserManagementActionC Function queryProvince begin!");
        context.getHttpHandler().SetParamsToBLL(userBll, context);
        userBll.getBaseDB().NeedOpen(true); 
        userBll.getBaseDB().NeedTransaction(false);
        AppDataTable result = (AppDataTable) context.getHttpHandler().BLLContainer
                .DoProcess(userBll, "queryProvince");
        HLogger.info("UserManagementActionC Function queryProvince end!");
        context.write(result!= null ? JsonUtil.dataTableToJsonString(result.getDataTable().getRows().size(), true, "导医服务省份查询成功",
                result.getDataTable(), OutJsonType.Combox) : JsonUtil.errorMessageJsonString("导医服务省份查询失败"));
    }
    
    /**
     * 
     * 描述: 导医服务查询城市<br/>
     * 创建人: shiqi <br/>
     * 创建时间：2015年3月27日13:29:12
     * 任务号：KYEEAPPMAINTENANCE-151
     *
     * @param context
     * @throws BaseBllException 
     * @since Ver 1.1
     */
    public void queryCity(HttpContext context) throws BaseBllException {
        HLogger.info("UserManagementActionC Function queryCity begin!");
        context.getHttpHandler().SetParamsToBLL(userBll, context);
        userBll.getBaseDB().NeedOpen(true); 
        userBll.getBaseDB().NeedTransaction(false);
        AppDataTable result = (AppDataTable) context.getHttpHandler().BLLContainer
                .DoProcess(userBll, "queryCity");
        HLogger.info("UserManagementActionC Function queryCity end!");
        context.write(result!= null ? JsonUtil.dataTableToJsonString(result.getDataTable().getRows().size(), true, "导医服务城市查询成功",
                result.getDataTable(), OutJsonType.Combox) : JsonUtil.errorMessageJsonString("导医服务城市查询失败"));
    }
    
    /**
     * 
     * 描述: 导医服务查询医院<br/>
     * 创建人: shiqi <br/>
     * 创建时间：2015年3月27日13:29:12
     * 任务号：KYEEAPPMAINTENANCE-151
     *
     * @param context
     * @throws BaseBllException 
     * @since Ver 1.1
     */
    public void queryHospital(HttpContext context) throws BaseBllException {
        HLogger.info("UserManagementActionC Function queryHospital begin!");
        context.getHttpHandler().SetParamsToBLL(userBll, context);
        userBll.getBaseDB().NeedOpen(true); 
        userBll.getBaseDB().NeedTransaction(false);
        AppDataTable result = (AppDataTable) context.getHttpHandler().BLLContainer
                .DoProcess(userBll, "queryHospital");
        HLogger.info("UserManagementActionC Function queryHospital end!");
        context.write(result!= null ? JsonUtil.dataTableToJsonString(result.getDataTable().getRows().size(), true, "导医服务医院查询成功",
                result.getDataTable(), OutJsonType.Combox) : JsonUtil.errorMessageJsonString("导医服务医院查询失败"));
    }
    
    
    /**
     * 任务号：
     * 描述：初始化供应商combobox
     * 作者：liuxingping
     * 时间：2015年5月25日下午3:51:32
     * @param context
     * @throws BaseBllException
     */
    public void initSupplier(HttpContext context) throws BaseBllException {
        HLogger.info("UserManagementActionC Function initSupplier begin!");
        context.getHttpHandler().SetParamsToBLL(userBll, context);
        AppDataTable result = (AppDataTable) context.getHttpHandler().BLLContainer
                .DoProcess(userBll, "initSupplier");
        HLogger.info("UserManagementActionC Function initSupplier end!");
        context.write(result!= null ? JsonUtil.dataTableToJsonString(result.getDataTable().getRows().size(), true, "导医服务城市查询成功",
                result.getDataTable(), OutJsonType.Combox) : JsonUtil.errorMessageJsonString("导医服务城市查询失败"));
    }
}
