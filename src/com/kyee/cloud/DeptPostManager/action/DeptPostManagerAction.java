package com.kyee.cloud.DeptPostManager.action;

import java.util.List;

import APP.Comm.BLL.BaseBllException;
import APP.Comm.DataBase.Helper.AppDataTable;
import APP.Comm.DotNet.HttpContext;
import APP.Comm.Util.HLogger;
import APP.Comm.Util.JsonUtil;
import APP.Comm.Util.OutJsonType;
import APP.Comm.View.BaseTreeNode;

import com.kyee.cloud.DeptPostManager.bll.DeptPostManagerBll;


public class DeptPostManagerAction {
	
	private DeptPostManagerBll deptPostManagerBll = new DeptPostManagerBll();
	/**
	 * 查询岗位列表 list
	 * 
	 * @param model
	 * @return
	 */
	public void queryDeptPost(HttpContext context) throws BaseBllException {
		HLogger.info("");
		context.getHttpHandler().SetParamsToBLL(deptPostManagerBll,context);
		deptPostManagerBll.getBaseDB().NeedOpen(true);
		deptPostManagerBll.getBaseDB().NeedTransaction(false);
		AppDataTable result = (AppDataTable) context.getHttpHandler().BLLContainer
				.DoProcess(deptPostManagerBll, "queryDeptPost");
		context.write(JsonUtil.dataTableToJsonString((int) result.size(), true,
				"查询成功", result.DataTable, OutJsonType.Grid));
	}
	
	/**
	 * 查询岗位用户
	 * 
	 * @param model
	 * @return
	 */
	public void getPostUser(HttpContext context) throws BaseBllException {
		DeptPostManagerBll deptPostManagerBll = new DeptPostManagerBll();
		context.getHttpHandler().SetParamsToBLL(deptPostManagerBll,context);
		deptPostManagerBll.getBaseDB().NeedOpen(true);
		deptPostManagerBll.getBaseDB().NeedTransaction(false);
		AppDataTable result = (AppDataTable) context.getHttpHandler().BLLContainer
				.DoProcess(deptPostManagerBll, "getPostUser");
		context.write(JsonUtil.dataTableToJsonString((int) result.size(), true,
				"查询成功", result.DataTable, OutJsonType.Grid));
	}
	
	/**
	 * 查询岗位可选用户
	 * 
	 * @param model
	 * @return
	 */
	public void getPostNoUser(HttpContext context) throws BaseBllException {
		DeptPostManagerBll deptPostManagerBll = new DeptPostManagerBll();

		context.getHttpHandler().SetParamsToBLL(deptPostManagerBll,context);
		deptPostManagerBll.getBaseDB().NeedOpen(true);
		deptPostManagerBll.getBaseDB().NeedTransaction(false);
		AppDataTable result = (AppDataTable) context.getHttpHandler().BLLContainer
				.DoProcess(deptPostManagerBll, "getPostNoUser");
		context.write(JsonUtil.dataTableToJsonString((int) result.size(), true,
				"查询成功", result.DataTable, OutJsonType.Grid));
	}

	/**
	 * 查询岗位
	 * 
	 * @param model
	 * @return
	 */
	public void getPostDict(HttpContext context) throws BaseBllException {

		context.getHttpHandler().SetParamsToBLL(deptPostManagerBll,context);
		deptPostManagerBll.getBaseDB().NeedOpen(true);
		deptPostManagerBll.getBaseDB().NeedTransaction(false);
		AppDataTable result = (AppDataTable) context.getHttpHandler().BLLContainer
				.DoProcess(deptPostManagerBll, "getPostDict");
		context.write(JsonUtil.dataTableToJsonString((int) result.size(), true,
				"查询成功", result.DataTable, OutJsonType.Grid));
	}

	// 获取科室树
	public void getDeptTree(HttpContext context) throws BaseBllException {
		context.getHttpHandler().SetParamsToBLL(deptPostManagerBll,context);
		deptPostManagerBll.getBaseDB().NeedOpen(true);
		deptPostManagerBll.getBaseDB().NeedTransaction(false);
		List<BaseTreeNode> result = result = (List<BaseTreeNode>) context
				.getHttpHandler().BLLContainer.DoProcess(deptPostManagerBll,
				"GetSysDeptCodeTree");
		context.write(JsonUtil.listBeanToJsonString(
				Integer.parseInt(result.size() + ""), true, "执行成功", result,
				OutJsonType.Tree));
	}

	// 批量提交保存
	public void saveDeptPostInfo(HttpContext context) throws BaseBllException {
		context.getHttpHandler().SetParamsToBLL(deptPostManagerBll,context);
		// 需要打开数据库连接,如果不需要打开，就不比设置
		deptPostManagerBll.getBaseDB().NeedOpen(true);
		// 是否需要开启事务
		deptPostManagerBll.getBaseDB().NeedTransaction(true);
		int result = Integer.parseInt(context.getHttpHandler().BLLContainer
				.DoProcess(deptPostManagerBll, "saveDeptPostInfo").toString());
		context.write(result > 0 ? JsonUtil.successMessageJsonString("提交成功！")
				: JsonUtil.errorMessageJsonString("提交失败！"));
	}

	// 批量提交保存
	public void savePostDict(HttpContext context) throws BaseBllException {
		context.getHttpHandler().SetParamsToBLL(deptPostManagerBll,context);
		// 需要打开数据库连接,如果不需要打开，就不比设置
		deptPostManagerBll.getBaseDB().NeedOpen(true);
		// 是否需要开启事务
		deptPostManagerBll.getBaseDB().NeedTransaction(true);
		int result = Integer.parseInt(context.getHttpHandler().BLLContainer
				.DoProcess(deptPostManagerBll, "savePostDict").toString());
		context.write(result > 0 ? JsonUtil.successMessageJsonString("提交成功！")
				: JsonUtil.errorMessageJsonString("提交失败！"));
	}

	// 批量提交保存
	public void saveUserPost(HttpContext context) throws BaseBllException {
		context.getHttpHandler().SetParamsToBLL(deptPostManagerBll,context);
		// 需要打开数据库连接,如果不需要打开，就不比设置
		deptPostManagerBll.getBaseDB().NeedOpen(true);
		// 是否需要开启事务
		deptPostManagerBll.getBaseDB().NeedTransaction(true);
		int result = Integer.parseInt(context.getHttpHandler().BLLContainer
				.DoProcess(deptPostManagerBll, "saveUserPost").toString());
		context.write(result > 0 ? JsonUtil.successMessageJsonString("提交成功！")
				: JsonUtil.errorMessageJsonString("提交失败！"));
	}

	// 批量提交保存
	public void cancelUserPost(HttpContext context) throws BaseBllException {
		context.getHttpHandler().SetParamsToBLL(deptPostManagerBll,context);
		// 需要打开数据库连接,如果不需要打开，就不比设置
		deptPostManagerBll.getBaseDB().NeedOpen(true);
		// 是否需要开启事务
		deptPostManagerBll.getBaseDB().NeedTransaction(true);
		int result = Integer.parseInt(context.getHttpHandler().BLLContainer
				.DoProcess(deptPostManagerBll, "cancelUserPost").toString());
		context.write(result > 0 ? JsonUtil.successMessageJsonString("提交成功！")
				: JsonUtil.errorMessageJsonString("提交失败！"));
	}
	
	public void initPostDict(HttpContext context) throws BaseBllException {
		context.getHttpHandler().SetParamsToBLL(deptPostManagerBll,context);
		deptPostManagerBll.getBaseDB().NeedOpen(true);
		deptPostManagerBll.getBaseDB().NeedTransaction(false);
		AppDataTable result = (AppDataTable) context.getHttpHandler().BLLContainer
				.DoProcess(deptPostManagerBll, "initPostDict");
		context.write(JsonUtil.dataTableToJsonString((int) result.size(), true,
				"查询成功", result.DataTable, OutJsonType.Combox));
	}
}
