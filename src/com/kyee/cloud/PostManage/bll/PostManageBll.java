package com.kyee.cloud.PostManage.bll;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.base.ext.SYS_MENU_EXT;
import model.base.ext.SYS_POST_DICT_EXT;
import model.base.ext.SYS_USER_EXT;
import net.hydromatic.linq4j.Linq4j;
import net.hydromatic.linq4j.function.Predicate1;

import com.kyee.cloud.PostManage.dal.PostManageDal;

import APP.Comm.BLL.BaseBLL;
import APP.Comm.BLL.BaseBllException;
import APP.Comm.DataBase.DotNet.DataTable;
import APP.Comm.DataBase.Helper.AppDataTable;
import APP.Comm.Util.DotNetToJavaStringHelper;
import APP.Comm.Util.HLogger;
import APP.Comm.Util.JsonUtil;
import APP.Comm.View.BaseTreeNode;
import APP.Model.BaseEntity;

public class PostManageBll extends BaseBLL{

	private PostManageDal postManageDal = new PostManageDal();
	public int initGrant() throws BaseBllException{
		HLogger.info("PostManageBll Function initGrant Start!");
	
		return postManageDal.initGrant(super.getBaseDB());
		//HLogger.info("PostManageBll Function QueryPost End!");
	//	return result;
	}

	public AppDataTable QueryPost() throws BaseBllException{
		HLogger.info("PostManageBll Function QueryPost Start!");
		String postText = super.getParameterValue("PostText");
		return postManageDal.QueryPost(super.getBaseDB(),postText,super.getGridRequestParameters());
//		HLogger.info("PostManageBll Function QueryPost End!");
	//	return result;
	}

	
	
	public List<BaseTreeNode> QueryGrant() throws BaseBllException {		
		String postId = super.getParameterValue("postId"); //获取授权岗位id
		if(DotNetToJavaStringHelper.isNullOrEmpty(postId)){
			HLogger.error("传入空的岗位");
			return null;
		}
		List<BaseTreeNode> resultnodes = new ArrayList<BaseTreeNode>();
		List<BaseEntity> rootList = postManageDal.QueryRootGrant(super.getBaseDB()); //获取根节点
		List<BaseEntity> list = postManageDal.QueryMenuGrant(super.getBaseDB()); //获取当前用户下所有菜单
		List<BaseEntity> postlist = postManageDal.QueryMenuByPostId(super.getBaseDB(), postId); //获取赋权岗位的所有菜单
		if (rootList != null && rootList.size() > 0) {
			SYS_MENU_EXT rootSysMenu = (SYS_MENU_EXT) rootList.get(0);
			BaseTreeNode rootNode = new BaseTreeNode();
			rootNode.setid(rootSysMenu.getMENU_ID()+"");
			rootNode.settext(rootSysMenu.getMENU_NAME());
			List<BaseTreeNode> ns = converChildren(list, postlist, rootSysMenu.getMENU_ID());
			if (ns.size() > 0) {
				rootNode.setchildren(ns.toArray(new BaseTreeNode[] {}));
			}
			rootNode.setChecked(false);
			rootNode.setstate("open");
			rootNode.setattributes(rootSysMenu.getMENU_CODE());
			resultnodes.add(rootNode);
		}
		return resultnodes;
	}
	
	public List<BaseTreeNode> converChildren(List<BaseEntity> entities, List<BaseEntity> postlist,
			final int parentId) throws BaseBllException {
		List<BaseTreeNode> nodes = new ArrayList<BaseTreeNode>();
		List<BaseEntity> ens = Linq4j.asEnumerable(entities)
				.where(new Predicate1<BaseEntity>() {
					public boolean apply(BaseEntity entity) {
						return ((SYS_MENU_EXT) entity).getC_S_MENU_ID() == parentId;
					}
				}).toList();
		for (BaseEntity be : ens) {
			BaseTreeNode node = new BaseTreeNode();
			SYS_MENU_EXT cs = (SYS_MENU_EXT) be;
			node.setid(cs.getMENU_ID() + "");
			node.settext(cs.getMENU_NAME());
			List<BaseTreeNode> ns = converChildren(entities, postlist, cs.getMENU_ID());
			boolean fal=false;
			if (ns.size() > 0) {
				node.setchildren(ns.toArray(new BaseTreeNode[] {}));
				node.setstate("closed");
			} else {
				node.setstate("open");
				for(int i=0; i<postlist.size(); i++) {
					SYS_MENU_EXT li = (SYS_MENU_EXT) postlist.get(i);
						if(li.getMENU_ID() == cs.getMENU_ID()) {
							 fal=true;
						}
				}
			}		
			node.setChecked(fal);
			node.setattributes(cs.getMENU_CODE());
			nodes.add(node);
		}
		return nodes;
	}
	
	public int AddPost() throws BaseBllException {
		HLogger.info("PostManageBll Function AddPost Start!");
		String postInfo = super.getParameterValue("postInfo");
		SYS_USER_EXT appuser = (SYS_USER_EXT) super.getContext().getSessionAttribute("appuser");
		SYS_POST_DICT_EXT postData = (SYS_POST_DICT_EXT) JsonUtil.jsonStringToObject(postInfo, SYS_POST_DICT_EXT.class);
		postData.setCREATOR(appuser.getUSER_CODE());
		postData.setCREATOR_NAME(appuser.getUSER_NAME());
		postData.setCREATE_TIME(new Date());
		int result = super.getBaseDB().Save(postData);
		HLogger.info("PostManageBll Function AddPost End!");
		return result;
	}
	
	public int UpdatePost() throws BaseBllException {
		HLogger.info("PostManageBll Function UpdatePost Start!");
		String postInfo = super.getParameterValue("postInfo");
		SYS_POST_DICT_EXT postData = (SYS_POST_DICT_EXT) JsonUtil.jsonStringToObject(postInfo, SYS_POST_DICT_EXT.class);
		int result = super.getBaseDB().Update(postData);
		HLogger.info("PostManageBll Function UpdatePost End!");
		return result;
	}
	
	public int DeletePost() throws BaseBllException {
		HLogger.info("PostManageBll Function DeletePost Start!");
		String postId = super.getParameterValue("postId"); //获取删除的岗位ID
		int result = postManageDal.DeletePostGrant(super.getBaseDB(),postId);
		result += postManageDal.DeletePostUser(super.getBaseDB(),postId);
		result += postManageDal.DeleteDeptPost(super.getBaseDB(),postId);
		result += postManageDal.DeletePost(super.getBaseDB(),postId);
		HLogger.info("PostManageBll Function DeletePost End!");
		return result;
	}
	
	public int SaveGrant() throws BaseBllException {
		HLogger.info("PostManageBll Function SaveGrant Start!");
		String postId = super.getParameterValue("postId");
		String menuCode = super.getParameterValue("menuCode");
		SYS_USER_EXT appuser = (SYS_USER_EXT) super.getContext().getSessionAttribute("appuser");
		postManageDal.DeletePostGrant(super.getBaseDB(),postId); //根据岗位id删除岗位权限
        String[] menu = menuCode.split(",");
        int result = 0;
        for (int i = 0; i < menu.length; i++) {
        	result += postManageDal.SaveGrant(super.getBaseDB(),postId,menu[i],appuser);
        }
        return result;
	}
}
