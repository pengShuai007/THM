package com.kyee.cloud.MenuManager.bll;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.base.ext.SYS_MENU_EXT;
import model.base.ext.SYS_USER_EXT;
import APP.Comm.BLL.BaseBLL;
import APP.Comm.BLL.BaseBllException;
import APP.Comm.DataBase.Helper.AppDataTable;
import APP.Comm.DataBase.Helper.IDataBase;
import APP.Comm.Util.HLogger;
import APP.Comm.View.BaseTreeNode;
import APP.Model.BaseEntity;

import com.fr.json.JSONObject;
import com.kyee.cloud.MenuManager.dal.MenuManagerDal;

public class MenuManagerBll extends BaseBLL {

	private MenuManagerDal menuManagerDal = new MenuManagerDal();

	/**
	 * 任务号： 描述：得到菜单树 作者：李添 时间：2016年7月28日11:33:12
	 * 
	 * @return
	 * @throws BaseBllException
	 */
	public final List<BaseTreeNode> getMenuManagerCodeTree()
			throws BaseBllException {
		HLogger.info("MenuManagerBll Function getMenuManagerCodeTree Start!");
		List<BaseEntity> list = menuManagerDal.getRootTree(super.getBaseDB());
		List<BaseTreeNode> rootTree = new ArrayList<BaseTreeNode>();// 构造的菜单树
		if (list != null && list.size() > 0) {
			for (BaseEntity be : list) {
				SYS_MENU_EXT sysMenuInfo = (SYS_MENU_EXT) be;
				BaseTreeNode node = new BaseTreeNode();
				node.setid(sysMenuInfo.getMENU_ID() + "");
				node.settext(sysMenuInfo.getMENU_NAME());

				Map<String, String> map = new java.util.HashMap<String, String>();
				map.put("menuUrl", sysMenuInfo.getMENU_URL());
				map.put("menuCode", sysMenuInfo.getMENU_CODE());
				map.put("CSMenuId", sysMenuInfo.getC_S_MENU_ID() + "");
				map.put("OPERATOR_NAME", sysMenuInfo.getOPERATOR_NAME());
				map.put("OPERATOR_TIME", sysMenuInfo.getOPERATOR_TIME() + "");
				map.put("MODIFIER", sysMenuInfo.getMODIFIER());
				map.put("MODIFY_TIME", sysMenuInfo.getMODIFY_TIME() + "");
				map.put("MENU_ORDER", sysMenuInfo.getMENU_ORDER() + "");

				JSONObject jsonMap = new JSONObject(map);
				node.setattributes(jsonMap);

				List<BaseTreeNode> nds = converChildren(super.getBaseDB(),
						sysMenuInfo.getMENU_ID());
				if (nds.size() > 0) {
					node.setchildren(nds.toArray(new BaseTreeNode[] {}));
					node.setstate("open");
				} else {
					node.setstate("close");
				}
				rootTree.add(node);
			}
		}
		HLogger.info("MenuManagerBll Function getMenuManagerCodeTree End!");
		return rootTree;
	}

	/**
	 * 任务号： 描述：遍历树 作者：李添 时间：2016年7月28日11:33:35
	 * 
	 * @param baseDB
	 * @param menuId
	 * @return
	 * @throws BaseBllException
	 */
	private List<BaseTreeNode> converChildren(IDataBase baseDB, int menuId)
			throws BaseBllException {
		HLogger.info("MenuManagerBll Function converChildren Start!");
		List<BaseEntity> ens = menuManagerDal.getRootTree(super.getBaseDB(),
				menuId);
		List<BaseTreeNode> nodes = new ArrayList<BaseTreeNode>();
		for (BaseEntity be : ens) {
			BaseTreeNode node = new BaseTreeNode();
			SYS_MENU_EXT sysMenuInfo = (SYS_MENU_EXT) be;
			node.setid(sysMenuInfo.getMENU_ID() + "");
			node.settext(sysMenuInfo.getMENU_NAME());

			Map<String, String> map = new java.util.HashMap<String, String>();
			map.put("menuUrl", sysMenuInfo.getMENU_URL());
			map.put("menuCode", sysMenuInfo.getMENU_CODE());
			map.put("CSMenuId", sysMenuInfo.getC_S_MENU_ID() + "");
			map.put("OPERATOR_NAME", sysMenuInfo.getOPERATOR_NAME());
			map.put("OPERATOR_TIME", sysMenuInfo.getOPERATOR_TIME() + "");
			map.put("MODIFIER", sysMenuInfo.getMODIFIER());
			map.put("MODIFY_TIME", sysMenuInfo.getMODIFY_TIME() + "");
			map.put("MENU_ORDER", sysMenuInfo.getMENU_ORDER() + "");

			JSONObject jsonMap = new JSONObject(map);
			node.setattributes(jsonMap);

			List<BaseTreeNode> ns = converChildren(baseDB,
					sysMenuInfo.getMENU_ID());
			if (ns.size() > 0) {
				node.setchildren(ns.toArray(new BaseTreeNode[] {}));
				node.setstate("closed");
			} else {
				node.setstate("close");
			}
			// node.setattributes(sysMenuInfo.getMENU_ID());
			nodes.add(node);
		}
		HLogger.info("MenuManagerBll Function converChildren End!");
		return nodes;
	}

	/**
	 * 任务号： 描述：添加菜单 作者：李添 时间：2016年7月28日11:33:54
	 * 
	 * @return
	 * @throws BaseBllException
	 */
	public int addMenu() throws BaseBllException {
		HLogger.info("MenuManagerBll Function addMenu Start!");
		SYS_USER_EXT appuser = (SYS_USER_EXT) context
				.getSessionAttribute("appuser");// 获取当前用户
		String currentUserName = appuser.getUSER_CODE();// 得到当前用户的名字

		String MENU_NAME = getParameterValue("MENU_NAME");
		String MENU_URL = getParameterValue("MENU_URL");
		String MENU_CODE = getParameterValue("MENU_CODE");
		int MENU_ID = Integer.parseInt(getParameterValue("MENU_ID"));
		int  MENU_ORDER = Integer.parseInt(getParameterValue("MENU_ORDER"));
		int result = menuManagerDal.addMenu(super.getBaseDB(), currentUserName,
				MENU_NAME, MENU_URL, MENU_CODE, MENU_ID,MENU_ORDER);
		HLogger.info("MenuManagerBll Function addMenu End!");
		return result;
	}

	/**
	 * 任务号： 描述：找到所有能当父亲的节点菜单 作者：李添 时间：2016年7月28日16:22:23
	 * 
	 * @return
	 * @throws BaseBllException
	 */
	public AppDataTable getisFather() throws BaseBllException {
		HLogger.info("MenuManagerBll Function getisFather Begin!");
		HLogger.info("MenuManagerBll Function getisFather End!");
		return menuManagerDal.getisFather(getBaseDB());
	}

	/**
	 * 任务号： 描述：根据menu_id找到menu_name 作者：李添 时间：2016年7月29日14:40:06
	 * 
	 * @return
	 * @throws BaseBllException
	 */
	/*
	 * public AppDataTable getFatherName() throws BaseBllException{
	 * HLogger.info("MenuManagerBll Function getFatherName Begin!"); int menuId
	 * = Integer.parseInt(getParameterValue("MENU_ID"));
	 * HLogger.info("MenuManagerBll Function getFatherName End!"); return
	 * menuManagerDal.getFatherName(super.getBaseDB(), menuId); }
	 */

	/**
	 * 任务号： 描述：修改菜单 作者：李添 时间：2016年7月29日16:21:43
	 * 
	 * @return
	 * @throws BaseBllException
	 */
	public int editMenu() throws BaseBllException {
		HLogger.info("MenuManagerBll Function editMenu Begin!");
		SYS_USER_EXT appuser = (SYS_USER_EXT) context
				.getSessionAttribute("appuser");// 获取当前用户
		String currentUserName = appuser.getUSER_CODE();// 得到当前用户的名字
		String MENU_NAME = getParameterValue("MENU_NAME");
		String MENU_URL = getParameterValue("MENU_URL");
		String MENU_CODE = getParameterValue("MENU_CODE");
		int MENU_ID = Integer.parseInt(getParameterValue("MENU_ID"));
		String OPERATOR_NAME = getParameterValue("OPERATOR_NAME");
		String operatorTime = getParameterValue("OPERATOR_TIME");
		int MENU_ORDER = Integer.parseInt(getParameterValue("MENU_ORDER"));
		int menu_order_old = Integer.parseInt(getParameterValue("MENU_ORDER_OLD"));
		// Timestamp OPERATOR_TIME =
		// Timestamp.valueOf(operatorTime);//转换时间字符串为Timestamp型
		int C_S_MENU_ID = Integer.parseInt(getParameterValue("C_S_MENU_ID"));
		int result = menuManagerDal.editMenu(super.getBaseDB(),
				currentUserName, MENU_NAME, MENU_URL, MENU_CODE, MENU_ID,
				C_S_MENU_ID, OPERATOR_NAME, Timestamp.valueOf(operatorTime),
				MENU_ORDER, menu_order_old);
		HLogger.info("MenuManagerBll Function editMenu End!");
		return result;
	}

	/**
	 * 任务号： 描述：删除菜单 作者：李添 时间：2016年7月29日17:20:29
	 * 
	 * @return
	 * @throws BaseBllException
	 */
	public int removeMenu() throws BaseBllException {
		HLogger.info("MenuManagerBll Function removeMenu Begin!");
		int MENU_ID = Integer.parseInt(getParameterValue("MENU_ID"));
		int C_S_MENU_ID = Integer.parseInt(getParameterValue("C_S_MENU_ID"));
		int MENU_ORDER = Integer.parseInt(getParameterValue("MENU_ORDER"));
		int result = menuManagerDal.removeMenu(super.getBaseDB(), MENU_ID,C_S_MENU_ID,MENU_ORDER);
		HLogger.info("MenuManagerBll Function removeMenu end!");
		return result;
	}

	/**
	 * 描述：查询所有菜单 作者：李添 时间：2016年7月29日19:47:57
	 * 
	 * @return
	 * @throws BaseBllException
	 */
	public AppDataTable querySysMenu() throws BaseBllException {
		HLogger.info("MenuManagerBll Function querySysMenu Begin!");
		AppDataTable result = menuManagerDal.querySysMenu(super.getBaseDB(),
				super.getGridRequestParameters());
		HLogger.info("MenuManagerBll Function querySysMenu End!");
		return result;
	}

	/**
	 * 描述：找到有这个菜单权限的人 作者：李添 时间：2016年8月3日11:07:49
	 * 
	 * @return
	 * @throws BaseBllException
	 */
	public AppDataTable findUserInfo() throws BaseBllException {
		HLogger.info("MenuManagerBll Function findUserInfo Begin!");
		String menuCode = getParameterValue("MENU_CODE");
		AppDataTable result = menuManagerDal.findUserInfo(super.getBaseDB(),
				menuCode, super.getGridRequestParameters());
		HLogger.info("MenuManagerBll Function findUserInfo End!");
		return result;
	}
	
}
