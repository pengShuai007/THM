package com.kyee.cloud.PostManage.action;

import java.util.List;

import model.base.ext.SYS_USER_EXT;
import APP.Comm.BLL.BaseBllException;
import APP.Comm.DataBase.DotNet.DataTable;
import APP.Comm.DataBase.Helper.AppDataTable;
import APP.Comm.DotNet.HttpContext;
import APP.Comm.Util.HLogger;
import APP.Comm.Util.JsonUtil;
import APP.Comm.Util.OutJsonType;
import APP.Comm.View.BaseTreeNode;

import com.kyee.cloud.PostManage.bll.PostManageBll;

public class PostManageAction {
	
	private PostManageBll postManageBll = new PostManageBll();
	public void initGrant(HttpContext context)throws BaseBllException{
		HLogger.info("PostManageAction Function initGrant Start!");
		context.getHttpHandler().SetParamsToBLL(postManageBll,context);
		postManageBll.getBaseDB().NeedOpen(true);
		postManageBll.getBaseDB().NeedTransaction(false);
		int result = (int) context.getHttpHandler().BLLContainer
				.DoProcess(postManageBll, "initGrant");
		HLogger.info("PostManageAction Function initGrant End!");
		context.write(result >0 ? JsonUtil.errorMessageJsonString("删除成功"): JsonUtil.errorMessageJsonString("删除失败"));
	}

	public void QueryPost(HttpContext context)throws BaseBllException{
		HLogger.info("PostManageAction Function QueryPost Start!");
		context.getHttpHandler().SetParamsToBLL(postManageBll,context);
		postManageBll.getBaseDB().NeedOpen(true);
		postManageBll.getBaseDB().NeedTransaction(false);
		AppDataTable result = (AppDataTable) context.getHttpHandler().BLLContainer
				.DoProcess(postManageBll, "QueryPost");
		HLogger.info("PostManageAction Function QueryPost End!");
		context.write(result != null ? JsonUtil.dataTableToJsonString(Integer.parseInt(result
				.size()+""), true, "查询成功", result.DataTable, OutJsonType.Grid)
				: JsonUtil.errorMessageJsonString("查询失败"));
	}

	
	@SuppressWarnings("unchecked")
	public void QueryGrant(HttpContext context) throws BaseBllException{
		HLogger.info("PostManageAction Function QueryGrant Start!");
		context.getHttpHandler().SetParamsToBLL(postManageBll,context);
		postManageBll.getBaseDB().NeedOpen(true);
		postManageBll.getBaseDB().NeedTransaction(false);
		List<BaseTreeNode> result = (List<BaseTreeNode>) context
				.getHttpHandler().BLLContainer
				.DoProcess(postManageBll, "QueryGrant");
		HLogger.info("PostManageAction Function QueryGrant Start!");
		context.write(result != null && result.size() > 0 ? JsonUtil
				.listBeanToJsonString(result.size(), true, "查询成功",
						result,OutJsonType.Tree) : JsonUtil
				.errorMessageJsonString("查询失败！"));
	}
	
	public void AddPost(HttpContext context) throws BaseBllException {
		HLogger.info("PostManageAction Function AddPost Start!");
		context.getHttpHandler().SetParamsToBLL(postManageBll,context);
		postManageBll.getBaseDB().NeedOpen(true);
		postManageBll.getBaseDB().NeedTransaction(true);
		int result = (int) context.getHttpHandler().BLLContainer.DoProcess(
				postManageBll, "AddPost");
		HLogger.info("PostManageAction Function AddPost End!");
		context.write(result > 0 ? JsonUtil.successMessageJsonString("新增成功！") :
			JsonUtil.errorMessageJsonString("新增失败！"));
	}
	
	public void UpdatePost(HttpContext context) throws BaseBllException {
		HLogger.info("PostManageAction Function UpdatePost Start!");
		context.getHttpHandler().SetParamsToBLL(postManageBll,context);
		postManageBll.getBaseDB().NeedOpen(true);
		postManageBll.getBaseDB().NeedTransaction(true);
		int result = (int) context.getHttpHandler().BLLContainer.DoProcess(
				postManageBll, "UpdatePost");
		HLogger.info("PostManageAction Function UpdatePost End!");
		context.write(result > 0 ? JsonUtil.successMessageJsonString("修改成功！") :
			JsonUtil.errorMessageJsonString("修改失败！"));
	}
	
	public void DeletePost(HttpContext context) throws BaseBllException {
		HLogger.info("PostManageAction Function DeletePost Start!");
		context.getHttpHandler().SetParamsToBLL(postManageBll,context);
		postManageBll.getBaseDB().NeedOpen(true);
		postManageBll.getBaseDB().NeedTransaction(true);
		int result = (int) context.getHttpHandler().BLLContainer.DoProcess(
				postManageBll, "DeletePost");
		HLogger.info("PostManageAction Function DeletePost End!");
		context.write(result > 0 ? JsonUtil.successMessageJsonString("删除成功！") :
			JsonUtil.errorMessageJsonString("删除失败！"));
	}
	
	public void SaveGrant(HttpContext context) throws BaseBllException {
		HLogger.info("PostManageAction Function SaveGrant Start!");
		context.getHttpHandler().SetParamsToBLL(postManageBll,context);
		postManageBll.getBaseDB().NeedOpen(true);
		postManageBll.getBaseDB().NeedTransaction(true);
		int result = (int) context.getHttpHandler().BLLContainer.DoProcess(
				postManageBll, "SaveGrant");
		HLogger.info("PostManageAction Function SaveGrant End!");
		context.write(result > 0 ? JsonUtil.successMessageJsonString("赋权成功！") :
			JsonUtil.errorMessageJsonString("赋权失败！"));
	}
}
