package com.kyee.cloud.DeptManage.action;

import APP.Comm.BLL.BaseBllException;
import APP.Comm.DataBase.Helper.AppDataTable;
import APP.Comm.DotNet.HttpContext;
import APP.Comm.Util.HLogger;
import APP.Comm.Util.JsonUtil;
import APP.Comm.Util.OutJsonType;
import APP.Comm.View.BaseTreeNode;

import com.kyee.cloud.DeptManage.bll.DeptManageBll;

/** 
 DeptManageAction 的摘要说明
*/
public class DeptManageAction
{
	public DeptManageBll deptManageBll = new DeptManageBll();
	/** 
	 说明：修改
	 作者：刘勇
	 时间：2013年11月27日 19:18:23
	 
	 @return 
	*/
	public void DoUpdate(HttpContext context) throws BaseBllException {
		
		context.getHttpHandler().SetParamsToBLL(deptManageBll,context);
		//需要打开数据库连接,如果不需要打开，就不比设置
		deptManageBll.getBaseDB().NeedOpen(true);
		//是否需要开启事务
		deptManageBll.getBaseDB().NeedTransaction(true);
		int result = Integer.parseInt(context.getHttpHandler().BLLContainer.DoProcess(deptManageBll,"DoUpdate")+"");
		context.write(result > 0 ? JsonUtil.successMessageJsonString("修改成功！") : JsonUtil.errorMessageJsonString("修改失败！"));
	}

	/** 
	 创建组织机构树时使用
	 作者：刘勇
	 日期：2013年11月27日 19:18:33
	 @param context
	*/
	public void GetDeptTree(HttpContext context) throws BaseBllException {
		context.getHttpHandler().SetParamsToBLL(deptManageBll,context);
		deptManageBll.getBaseDB().NeedOpen(true); //需要开启数据库
		deptManageBll.getBaseDB().NeedTransaction(false); //需要开启事务
		@SuppressWarnings("unchecked")
		java.util.List<BaseTreeNode> result = (java.util.List<BaseTreeNode>) context
				.getHttpHandler().BLLContainer.DoProcess(deptManageBll,"GetDeptTree");
		context.write(JsonUtil.listBeanToJsonString(0, true, "执行成功！", result, OutJsonType.Tree));
	}
	/** 
	 增加组织机构
	 作者：刘勇
	 日期：2013年11月27日 19:18:49
	 
	 @param context
	*/
	public void DoAdd(HttpContext context) throws BaseBllException {
		context.getHttpHandler().SetParamsToBLL(deptManageBll,context);
		//需要打开数据库连接,如果不需要打开，就不用设置
		deptManageBll.getBaseDB().NeedOpen(true);
		//是否需要开启事务
		deptManageBll.getBaseDB().NeedTransaction(true);
		int result = Integer.parseInt(context.getHttpHandler().BLLContainer.DoProcess(deptManageBll,"DoAdd")+"");
		context.write(result > 0 ? JsonUtil.successMessageJsonString("新增成功！") : JsonUtil.errorMessageJsonString("新增失败！"));
	}
	/** 
	 说明：查询组织架构
	 作者：刘勇
	 时间：2013-01-15 09:29:17:202
	 
	 @return 
	*/
	public void DoQuery(HttpContext context) throws BaseBllException {
		context.getHttpHandler().SetParamsToBLL(deptManageBll,context);
		//需要打开数据库连接,如果不需要打开，就不必设置
		deptManageBll.getBaseDB().NeedOpen(true);
		//是否需要开启事务
		deptManageBll.getBaseDB().NeedTransaction(false);
		AppDataTable hrpDataTable = (AppDataTable)context.getHttpHandler().BLLContainer.DoProcess(deptManageBll,"DoQuery");
		context.write(JsonUtil.dataTableToJsonString(Integer.parseInt(hrpDataTable.size()+""), true, "执行成功！", hrpDataTable.DataTable, OutJsonType.Grid));
	}

	/** 
	 说明：撤销编制部门
	 作者：刘勇
	 时间：2013年11月27日 19:20:29
	 
	 @param context
	*/
	public void CancelDept(HttpContext context) throws BaseBllException 
	{
		context.getHttpHandler().SetParamsToBLL(deptManageBll,context);
		//需要打开数据库连接,如果不需要打开，就不必设置
		deptManageBll.getBaseDB().NeedOpen(true);
		//是否需要开启事务
		deptManageBll.getBaseDB().NeedTransaction(true);
		int result = Integer.parseInt(context.getHttpHandler().BLLContainer.DoProcess(deptManageBll,"DoCancelDept")+"");
		if (result == 0)
		{
			context.write(JsonUtil.successMessageJsonString("撤销成功！"));
		}
		else
		{
			context.write(JsonUtil.successMessageJsonString("该部门下面有人员，不允许撤销！"));
		}
	}
	
	/**
	 * <pre>
	 * 任务：HRPHRDEVJAVA-285
	 * 描述：恢复撤销的部门
	 * 作者：杨乐
	 * 日期：2014年11月4日 下午4:41:01
	 * @param context
	 * @throws BaseBllException
	 * returnType：void
	 * </pre>
	 */
	public void RestoreDept(HttpContext context) throws BaseBllException{
		HLogger.info("DeptManageAction Function RestoreDept Start!");
		context.getHttpHandler().SetParamsToBLL(deptManageBll,context);
		//是否需要打开数据库
		deptManageBll.getBaseDB().NeedOpen(true);
		//是否需要开启事务
		deptManageBll.getBaseDB().NeedTransaction(true);
		int result = Integer.parseInt(context.getHttpHandler().BLLContainer.DoProcess(deptManageBll,"RestoreDept")+"");
		//Edit Start By YangLe 任务：HRPDRTESTJAVA-1192 描述：更改判断 时间：2014年11月7日16:49
//		if (result == 0)
		if (result > 0)
		{
		//Edit End By YangLe 任务：HRPDRTESTJAVA-1192 描述：更改判断 时间：2014年11月7日16:49
			context.write(JsonUtil.successMessageJsonString("恢复成功！"));
		}
		else
		{
			context.write(JsonUtil.errorMessageJsonString("恢复过程中出错！"));
		}
		HLogger.info("DeptManageAction Function RestoreDept End!");
	}
	
	/**
	 * <pre>
	 * 任务：HRPHRDEVJAVA-310
	 * 描述：部门调动
	 * 作者：杨乐
	 * 日期：2014年11月20日 上午11:43:25
	 * @param context
	 * @throws BaseBllException
	 * returnType：void
	 * </pre>
	 */
	public void DoTransationDept(HttpContext context) throws BaseBllException{
		HLogger.info("DeptManageAction Function DoTransationDept Start!");
		context.getHttpHandler().SetParamsToBLL(deptManageBll,context);
		//是否需要打开数据库
		deptManageBll.getBaseDB().NeedOpen(true);
		//是否需要开启事务
		deptManageBll.getBaseDB().NeedTransaction(true);
		int result = Integer.parseInt(context.getHttpHandler().BLLContainer.DoProcess(deptManageBll,"DoTransationDept")+"");
		context.write(result > 0 ? JsonUtil.successMessageJsonString("部门调动成功！") : JsonUtil.errorMessageJsonString("部门调动失败！"));
		HLogger.info("DeptManageAction Function DoTransationDept End!");
	}
	
	/**
	 * <pre>
	 * 任务：HRPHRDEVJAVA-310
	 * 描述：部门合并
	 * 作者：杨乐
	 * 日期：2014年11月20日 下午5:44:03
	 * @param context
	 * @throws BaseBllException
	 * returnType：void
	 * </pre>
	 */
	public void DoMergeDept(HttpContext context) throws BaseBllException {
		HLogger.info("DeptManageAction Function DoMergeDept Start!");
		context.getHttpHandler().SetParamsToBLL(deptManageBll,context);
		//是否需要打开数据库
		deptManageBll.getBaseDB().NeedOpen(true);
		//是否需要开启事务
		deptManageBll.getBaseDB().NeedTransaction(true);
		int result = Integer.parseInt(context.getHttpHandler().BLLContainer.DoProcess(deptManageBll,"DoMergeDept")+"");
		context.write(result > 0 ? JsonUtil.successMessageJsonString("部门合并成功！") : JsonUtil.errorMessageJsonString("部门合并失败！"));
		HLogger.info("DeptManageAction Function DoMergeDept End!");
	}
	
	public void DoDelete(HttpContext context) throws BaseBllException {
		HLogger.info("DeptManageAction Function DoDelete Start!");
		context.getHttpHandler().SetParamsToBLL(deptManageBll,context);
		//是否需要打开数据库
		deptManageBll.getBaseDB().NeedOpen(true);
		//是否需要开启事务
		deptManageBll.getBaseDB().NeedTransaction(true);
		int result = Integer.parseInt(context.getHttpHandler().BLLContainer.DoProcess(deptManageBll,"DoDelete")+"");
		context.write(result > 0 ? JsonUtil.successMessageJsonString("删除成功！") : JsonUtil.errorMessageJsonString("删除失败！"));
		HLogger.info("DeptManageAction Function DoDelete End!");
	}
	
	public void InitDeptCombobox(HttpContext context) throws BaseBllException {
		HLogger.info("DeptManageAction Function InitDeptCombobox Start!");
		context.getHttpHandler().SetParamsToBLL(deptManageBll, context);
		deptManageBll.getBaseDB().NeedOpen(true);// 需要开启数据库
		deptManageBll.getBaseDB().NeedTransaction(false);// 不需开启事务
		AppDataTable result = (AppDataTable) context.getHttpHandler().BLLContainer
				.DoProcess(deptManageBll, "InitDeptCombobox");
		HLogger.info("DeptManageAction Function InitDeptCombobox End!");
		context.write(result != null ? JsonUtil.dataTableToJsonString(result
				.getDataTable().getRows().size(), true, "查询成功",
				result.getDataTable(), OutJsonType.Combox) : JsonUtil
				.errorMessageJsonString("查询失败"));
	}
	
	public void InitEditCombobox(HttpContext context) throws BaseBllException {
		HLogger.info("DeptManageAction Function InitEditCombobox Start!");
		context.getHttpHandler().SetParamsToBLL(deptManageBll, context);
		deptManageBll.getBaseDB().NeedOpen(true);// 需要开启数据库
		deptManageBll.getBaseDB().NeedTransaction(false);// 不需开启事务
		AppDataTable result = (AppDataTable) context.getHttpHandler().BLLContainer
				.DoProcess(deptManageBll, "InitEditCombobox");
		HLogger.info("DeptManageAction Function InitEditCombobox End!");
		context.write(result != null ? JsonUtil.dataTableToJsonString(result
				.getDataTable().getRows().size(), true, "查询成功",
				result.getDataTable(), OutJsonType.Combox) : JsonUtil
				.errorMessageJsonString("查询失败"));
	}
}
