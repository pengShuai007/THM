package com.kyee.cloud.MenuManager.action;

import java.util.List;

import APP.Comm.BLL.BaseBllException;
import APP.Comm.DataBase.Helper.AppDataTable;
import APP.Comm.DotNet.HttpContext;
import APP.Comm.Util.HLogger;
import APP.Comm.Util.JsonUtil;
import APP.Comm.Util.OutJsonType;
import APP.Comm.View.BaseTreeNode;

import com.kyee.cloud.MenuManager.bll.MenuManagerBll;

public class MenuManagerAction {

	private MenuManagerBll menuManagerBll = new MenuManagerBll();

	/**
	 * 任务号： 描述：查询菜单树 作者：李添 时间：2016年7月28日11:32:34
	 * 
	 * @throws BaseBllException
	 */
	@SuppressWarnings("unchecked")
	public void getMenuTree(HttpContext context) throws BaseBllException {
		HLogger.info("MenuManagerAction Function getMenuTree begin!");
		context.getHttpHandler().SetParamsToBLL(menuManagerBll, context);
		menuManagerBll.getBaseDB().NeedOpen(true);
		menuManagerBll.getBaseDB().NeedTransaction(false);
		List<BaseTreeNode> result = (List<BaseTreeNode>) context
				.getHttpHandler().BLLContainer.DoProcess(menuManagerBll,
				"getMenuManagerCodeTree");
		context.write(JsonUtil.listBeanToJsonString(
				Integer.parseInt(result.size() + ""), true, "执行成功", result,
				OutJsonType.Tree));
		HLogger.info("MenuManagerAction Function getMenuTree end!");
	}

	/**
	 * 任务号： 描述：添加菜单 作者：李添 时间：2016年7月28日11:49:28
	 * 
	 * @param context
	 * @throws BaseBllException
	 */
	public void addMenu(HttpContext context) throws BaseBllException {
		HLogger.info("MenuManagerAction Function addMenu begin!");
		HLogger.info("说明:添加菜单");
		context.getHttpHandler().SetParamsToBLL(menuManagerBll, context);
		menuManagerBll.getBaseDB().NeedOpen(true);
		menuManagerBll.getBaseDB().NeedTransaction(true);
		int result = (int) context.getHttpHandler().getBLLContainer()
				.DoProcess(menuManagerBll, "addMenu");
		context.write(result > 0 ? JsonUtil.successMessageJsonString("添加成功！",
				JsonUtil.objectToJsonString(result)) : JsonUtil
				.errorMessageJsonString("添加失败！"));
		HLogger.info("MenuManagerAction Function addMenu end!");
	}

	/**
	 * 任务号： 描述：查询能当父节点的节点 作者：李添 时间：2016年7月28日16:33:35
	 * 
	 * @param context
	 * @throws BaseBllException
	 */
	public void getisFather(HttpContext context) throws BaseBllException {
		HLogger.info("MenuManagerAction Function getisFather begin!");
		HLogger.info("说明:查询能当父节点的节点");
		context.getHttpHandler().SetParamsToBLL(menuManagerBll, context);
		menuManagerBll.getBaseDB().NeedOpen(true);
		menuManagerBll.getBaseDB().NeedTransaction(false);
		AppDataTable result = (AppDataTable) context.getHttpHandler().BLLContainer
				.DoProcess(menuManagerBll, "getisFather");
		context.write(result != null ? JsonUtil.dataTableToJsonString(
				Integer.parseInt(result.size() + ""), true, "查询能当父节点的节点成功",
				result.DataTable, OutJsonType.Combox) : JsonUtil
				.errorMessageJsonString("查询能当父节点的节点失败！"));
		HLogger.info("MenuManagerAction Function getisFather end!");
	}

	/**
	 * 任务号： 描述：根据menu_id查找menu_name 作者：李添 时间：2016年7月29日14:46:14
	 * 
	 * @param context
	 * @throws BaseBllException
	 */
	/*
	 * public void getFatherName(HttpContext context) throws BaseBllException {
	 * HLogger.info("MenuManagerAction Function getFatherName begin!");
	 * HLogger.info("说明:查询当前menu_id对应的menu_name");
	 * context.getHttpHandler().SetParamsToBLL(menuManagerBll, context);
	 * menuManagerBll.getBaseDB().NeedOpen(true);
	 * menuManagerBll.getBaseDB().NeedTransaction(true); AppDataTable result =
	 * (AppDataTable) context.getHttpHandler().BLLContainer
	 * .DoProcess(menuManagerBll, "getFatherName"); context.write(result != null
	 * ? JsonUtil.dataTableToJsonString( Integer.parseInt(result.size() + ""),
	 * true, "根据menu_id查询menu_name成功！", result.DataTable, OutJsonType.Combox) :
	 * JsonUtil.errorMessageJsonString("根据menu_id查询menu_name失败！"));
	 * HLogger.info("MenuManagerAction Function getFatherName end!"); }
	 */

	/**
	 * 任务号： 描述：修改菜单 作者：李添 时间：2016年7月29日16:24:12
	 * 
	 * @param context
	 * @throws BaseBllException
	 */
	public void editMenu(HttpContext context) throws BaseBllException {
		HLogger.info("MenuManagerAction Function editMenu begin!");
		HLogger.info("说明:修改菜单");
		context.getHttpHandler().SetParamsToBLL(menuManagerBll, context);
		menuManagerBll.getBaseDB().NeedOpen(true);
		menuManagerBll.getBaseDB().NeedTransaction(true);
		int result = (int) context.getHttpHandler().getBLLContainer()
				.DoProcess(menuManagerBll, "editMenu");
		context.write(result > 0 ? JsonUtil.successMessageJsonString("修改成功！",
				JsonUtil.objectToJsonString(result)) : JsonUtil
				.errorMessageJsonString("修改失败！"));
		HLogger.info("MenuManagerAction Function editMenu end!");
	}

	/**
	 * 描述：删除菜单 作者：李添 时间：2016年7月29日17:24:22
	 * 
	 * @param context
	 * @throws BaseBllException
	 */
	public void removeMenu(HttpContext context) throws BaseBllException {
		HLogger.info("MenuManagerAction Function removeMenu begin!");
		HLogger.info("说明:删除菜单");
		context.getHttpHandler().SetParamsToBLL(menuManagerBll, context);
		menuManagerBll.getBaseDB().NeedOpen(true);
		menuManagerBll.getBaseDB().NeedTransaction(true);
		int result = (int) context.getHttpHandler().getBLLContainer()
				.DoProcess(menuManagerBll, "removeMenu");
		context.write(result > 0 ? JsonUtil.successMessageJsonString("删除成功",
				JsonUtil.objectToJsonString(result)) : JsonUtil
				.errorMessageJsonString("删除失败"));
		HLogger.info("MenuManagerAction Function removeMenu end!");
	}

	/**
	 * 描述：查找所有菜单 作者：李添 时间：2016年7月29日19:47:57
	 * 
	 * @param context
	 * @throws BaseBllException
	 */
	public void querySysMenu(HttpContext context) throws BaseBllException {
		HLogger.info("MenuManagerAction Function querySysMenu begin!");
		HLogger.info("说明:初始化菜单信息");
		context.getHttpHandler().SetParamsToBLL(menuManagerBll, context);
		menuManagerBll.getBaseDB().NeedOpen(true);
		menuManagerBll.getBaseDB().NeedTransaction(false);
		AppDataTable result = (AppDataTable) context.getHttpHandler().BLLContainer
				.DoProcess(menuManagerBll, "querySysMenu");
		context.write(result != null ? JsonUtil.dataTableToJsonString(
				Integer.parseInt(result.size() + ""), true, "初始化查询成功",
				result.DataTable, OutJsonType.Grid) : JsonUtil
				.errorMessageJsonString("初始化查询失败"));
		HLogger.info("MenuManagerAction Function querySysMenu end!");
	}

	/**
	 * 描述：查找有操作这个菜单的所有的用户信息
	 * @param context
	 * @throws BaseBllException
	 */
	public void findUserInfo(HttpContext context) throws BaseBllException {
		HLogger.info("MenuManagerAction Function querySysMenu begin!");
		HLogger.info("说明:初始化菜单信息");
		context.getHttpHandler().SetParamsToBLL(menuManagerBll, context);
		menuManagerBll.getBaseDB().NeedOpen(true);
		menuManagerBll.getBaseDB().NeedTransaction(false);
		AppDataTable result = (AppDataTable) context.getHttpHandler().BLLContainer
				.DoProcess(menuManagerBll, "findUserInfo");
		context.write(result != null ? JsonUtil.dataTableToJsonString(
				Integer.parseInt(result.size() + ""), true, "初始化查询成功！",
				result.DataTable, OutJsonType.Grid) : JsonUtil
				.errorMessageJsonString("初始化查询失败！"));
		HLogger.info("MenuManagerAction Function querySysMenu end!");
	}
}
