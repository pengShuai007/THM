package com.kyee.cloud.DeptPostManager.bll;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.base.ext.SYS_DEPT_INFO_EXT;
import model.base.ext.SYS_DEPT_POST_INFO_EXT;
import model.base.ext.SYS_POST_DICT_EXT;
import model.base.ext.SYS_USER_EXT;
import APP.Comm.BLL.BaseBLL;
import APP.Comm.BLL.BaseBllException;
import APP.Comm.DataBase.Helper.AppDataTable;
import APP.Comm.DataBase.Helper.IDataBase;
import APP.Comm.Util.CommonUtil;
import APP.Comm.Util.DotNetToJavaStringHelper;
import APP.Comm.Util.HLogger;
import APP.Comm.Util.JsonUtil;
import APP.Comm.View.BaseTreeNode;
import APP.Model.BaseEntity;

import com.kyee.cloud.DeptPostManager.dal.DeptPostManagerDal;

public class DeptPostManagerBll extends BaseBLL {
	
	private DeptPostManagerDal deptPostManagerDal = new DeptPostManagerDal(); 
	// 查询科室岗位
	public Object queryDeptPost() throws BaseBllException {
//		String dgn = super.getParameterValue("DEPT_GLOBAL_NO");
		String deptCode = super.getParameterValue("DEPT_CODE");
		String postName = super.getParameterValue("POST_NAME");
		//任务号：HRPDRTESTJAVA-1023
	    //修改：李智博
	    //内容：添加模糊搜索
		String deptName = super.getParameterValue("DEPT_NAME");

		return deptPostManagerDal.queryDeptPost(super.getBaseDB(),deptCode,
				postName, deptName, super.getGridRequestParameters());
	}

	// 查询岗位字典
	public Object getPostDict() throws BaseBllException {
		// 任务优先级，到期时间暂不支持
		return deptPostManagerDal.getPostDict(super.getBaseDB(),
				super.getGridRequestParameters());
	}

	// 查询岗位配置用户
	public Object getPostUser() throws BaseBllException {
		String infoId = super.getParameterValue("INFO_ID");
		return deptPostManagerDal.getPostUser(super.getBaseDB(), infoId,
				super.getGridRequestParameters());
	}

	// 查询岗位未配置用户
	public Object getPostNoUser() throws BaseBllException {
		String infoId = super.getParameterValue("INFO_ID");
		String userName = super.getParameterValue("USER_NAME");
		return deptPostManagerDal.getPostNoUser(super.getBaseDB(), infoId,
				userName, super.getGridRequestParameters());
	}

	// 创建树形结构
	public final java.util.List<BaseTreeNode> GetSysDeptCodeTree()
			throws BaseBllException {
//		List<BaseTreeNode> treeNodes = new java.util.ArrayList<BaseTreeNode>();
//		DeptPostManagerDal deptPostManagerDal = new DeptPostManagerDal();
//		// 建立某科室下 树形菜单
//		String deptCode = super.getParameterValue("DEPT__CODE");
//
//		// 获取科室列表
//		List<BaseEntity> depts = deptPostManagerDal.getDepts(getBaseDB(),
//				deptCode);
//		// 父节点 serial_no
//		final long P_SERIAL_NO;
//		if (depts.size() != 0) {
//			SYS_DEPT_INFO_EXT firstDept = (SYS_DEPT_INFO_EXT) depts.get(0);
//			P_SERIAL_NO = firstDept.getSERIAL_NO().longValue();
//		} else {
//			return treeNodes;
//		}
//		BaseTreeNode root = new BaseTreeNode();
//		// depts 原始科室记录
//		List<SYS_DEPT_INFO_EXT> entites = Linq4j
//				.asEnumerable(
//						Arrays.asList(depts.toArray(new SYS_DEPT_INFO_EXT[] {})))
//				.where(new Predicate1<SYS_DEPT_INFO_EXT>() {
//
//					@Override
//					public boolean apply(SYS_DEPT_INFO_EXT entity) {
//						// TODO Auto-generated method stub
//						return Long.parseLong(entity.getSERIAL_NO() + "") == P_SERIAL_NO;
//					}
//				}).toList();
//		if (entites.size() == 0) {
//			return treeNodes;
//		}
//		SYS_DEPT_INFO_EXT deptRoot = entites.get(0);
//
//		// 设置根节点（即全院或公司）
//		root.setid(deptRoot.getDEPT_GLOBAL_NO());
//		root.settext(deptRoot.getDEPT_NAME());
//		root.setstate("open");
//		// 根节点设置子节点
//		root.setchildren(this.findAllChildrenToTreeNode(
//				deptRoot.getSERIAL_NO(), depts).toArray(
//				new BaseTreeNode[] {}));
//		root.setattributes(deptRoot);
//
//		treeNodes.add(root);
//		return treeNodes;
		HLogger.info("DeptPostManagerBll Fuction GetSysDeptCodeTree Start!");
		
		List<BaseEntity> list = deptPostManagerDal.GetSysDeptTree(super.getBaseDB());
		List<BaseTreeNode> depttree = new ArrayList<BaseTreeNode>(); //构造的菜单树
//		BaseTreeNode root = new BaseTreeNode();
//		root.setid("0");
//		root.setChecked(false);
//		root.settext("部门");
//		root.setattributes("");
//		root.setstate("close");
		if(list != null && list.size() > 0) {
//			List<BaseTreeNode> depttree = new ArrayList<BaseTreeNode>(); //构造的菜单树
			for (BaseEntity be : list) {
				SYS_DEPT_INFO_EXT sysDeptInfo = (SYS_DEPT_INFO_EXT) be;
				BaseTreeNode node = new BaseTreeNode();
				node.setid(sysDeptInfo.getDEPT_ID()+"");
				node.settext(sysDeptInfo.getDEPT_NAME());
				List<BaseTreeNode> nds = converChildren(super.getBaseDB(),sysDeptInfo.getDEPT_CODE());
				if(nds.size() > 0) {
					node.setchildren(nds.toArray(new BaseTreeNode[] {}));
					node.setstate("open");
				}else {
					node.setstate("close");
				}
				node.setattributes(sysDeptInfo.getDEPT_CODE());
				depttree.add(node);
			}
//			root.setchildren(nodes.toArray(new BaseTreeNode[] {}));
//			root.setstate("open");
		}
		HLogger.info("DeptPostManagerBll Fuction GetSysDeptCodeTree End!");
		return depttree;
	}

	/**
	 * 任务号：
	 * 描述：
	 * 作者：liuxingping
	 * 时间：2015年3月17日下午2:49:28
	 * @param baseDB
	 * @param hospId
	 * @param listCode
	 * @return
	 * @throws BaseBllException
	 */
	private List<BaseTreeNode> converChildren(IDataBase baseDB, String deptCode) throws BaseBllException {
		
		List<BaseEntity> ens = deptPostManagerDal.GetSysDeptTree(super.getBaseDB(), deptCode);
		List<BaseTreeNode> nodes = new ArrayList<BaseTreeNode>();
		for (BaseEntity be : ens) {
			BaseTreeNode node = new BaseTreeNode();
			SYS_DEPT_INFO_EXT sysDeptInfo = (SYS_DEPT_INFO_EXT) be;
			node.setid(sysDeptInfo.getDEPT_ID()+"");
			node.settext(sysDeptInfo.getDEPT_NAME());
			List<BaseTreeNode> ns = converChildren(baseDB, sysDeptInfo.getDEPT_CODE());
			if (ns.size() > 0) {
				node.setchildren(ns.toArray(new BaseTreeNode[] {}));
				node.setstate("closed");
			} else {
				node.setstate("close");
			}
			node.setattributes(sysDeptInfo.getDEPT_CODE());
			nodes.add(node);
		}
		return nodes;
	}
	
	// 说明：批量提交
	public Object saveDeptPostInfo() throws BaseBllException {
		HLogger.info("DeptPostManagerBll Function saveDeptPostInfo begin");
		HLogger.info(" 取传入的obj对象的属性对应的值 ！");

		SYS_USER_EXT appuser = (SYS_USER_EXT) super.getContext().getSessionAttribute("appuser");
		String insertedStr = super.getParameterValue("inserted");
		String deletedStr = super.getParameterValue("deleted");
		String updatedStr = super.getParameterValue("updated");

		int success = 0;

		HLogger.info(" 分隔对象，利用jsonStringToObject做与jsonToString相反的操作 ！");
		for (int i = 0; i < insertedStr.split("[|]", -1).length; i++) {
			String obj = insertedStr.split("[|]", -1)[i];
			if (!DotNetToJavaStringHelper.isNullOrEmpty(obj)) {
				SYS_DEPT_POST_INFO_EXT entity = (SYS_DEPT_POST_INFO_EXT) JsonUtil
						.jsonStringToObject(obj, SYS_DEPT_POST_INFO_EXT.class);

//				entity.setINFO_ID(super.getBaseDB().GetNewID());
//				if (DotNetToJavaStringHelper.isNullOrEmpty((entity.getPOST_ID()+"")
//						.trim())) {
//					entity.setPOST_ID(null);
//				}
//				entity.setCREATOR(super.getAppUser().getLoginName());
//				entity.setCREATOR_NAME(super.getAppUser().getUserName());
				entity.setCREATOR(appuser.getUSER_CODE());
				entity.setCREATOR_NAME(appuser.getUSER_NAME());
				entity.setCREATE_TIME(CommonUtil.parse2SqlDate(CommonUtil
						.formatDate(new Date())));
				success += deptPostManagerDal.DoAdd(entity, super.getBaseDB());
			}
		}

		HLogger.info("删除，只处理主键 ！");
		for (int i = 0; i < deletedStr.split("[,]", -1).length; i++) {
			String obj = deletedStr.split("[,]", -1)[i]; // 主键字符
			if (!DotNetToJavaStringHelper.isNullOrEmpty(obj)) {
				success += deptPostManagerDal.DoDelete(super.getBaseDB(), obj);
			}
		}

		HLogger.info("更新 ！");
		for (int i = 0; i < updatedStr.split("[|]", -1).length; i++) {
			String obj = updatedStr.split("[|]", -1)[i];
			if (!DotNetToJavaStringHelper.isNullOrEmpty(obj)) {
				SYS_DEPT_POST_INFO_EXT entity = (SYS_DEPT_POST_INFO_EXT) JsonUtil
						.jsonStringToObject(obj, SYS_DEPT_POST_INFO_EXT.class);

				success += deptPostManagerDal
						.DoUpdate(entity, super.getBaseDB());
			}
		}
		HLogger.info("DeptPostManagerBll Function saveDeptPostInfo end !");
		return success;
	}

	// 说明：批量提交
	public Object savePostDict() throws BaseBllException {
		HLogger.info("DeptPostManagerBll Function savePostDict begin");
		HLogger.info(" 取传入的obj对象的属性对应的值 ！");
		
		SYS_USER_EXT appuser = (SYS_USER_EXT) super.getContext().getSessionAttribute("appuser");
		String insertedStr = super.getParameterValue("inserted");
		String deletedStr = super.getParameterValue("deleted");
		String updatedStr = super.getParameterValue("updated");

		int success = 0;

		HLogger.info(" 分隔对象，利用jsonStringToObject做与jsonToString相反的操作 ！");
		for (int i = 0; i < insertedStr.split("[|]", -1).length; i++) {
			String obj = insertedStr.split("[|]", -1)[i];
			if (!DotNetToJavaStringHelper.isNullOrEmpty(obj)) {
				SYS_POST_DICT_EXT entity = (SYS_POST_DICT_EXT) JsonUtil
						.jsonStringToObject(obj, SYS_POST_DICT_EXT.class);

//				entity.setPOST_ID(super.getBaseDB().GetNewID());
				if (DotNetToJavaStringHelper.isNullOrEmpty(entity
						.getPOST_CODE().trim())) {
					entity.setPOST_CODE(null);
				}
//				entity.setCREATOR(super.getAppUser().getLoginName());
//				entity.setCREATOR_NAME(super.getAppUser().getUserName());
				entity.setCREATOR(appuser.getUSER_CODE());
				entity.setCREATOR_NAME(appuser.getUSER_NAME());
				entity.setCREATE_TIME(CommonUtil.parse2SqlDate(CommonUtil
						.formatDate(new Date())));
				success += deptPostManagerDal.DoAddPostDict(entity,
						super.getBaseDB());
			}
		}

		HLogger.info("删除，只处理主键 ！");
		for (int i = 0; i < deletedStr.split("[,]", -1).length; i++) {
			String obj = deletedStr.split("[,]", -1)[i]; // 主键字符
			if (!DotNetToJavaStringHelper.isNullOrEmpty(obj)) {
				success += deptPostManagerDal.DoDeletePostDict(
						super.getBaseDB(), obj);
			}
		}

		HLogger.info("更新 ！");
		for (int i = 0; i < updatedStr.split("[|]", -1).length; i++) {
			String obj = updatedStr.split("[|]", -1)[i];
			if (!DotNetToJavaStringHelper.isNullOrEmpty(obj)) {
				SYS_POST_DICT_EXT entity = (SYS_POST_DICT_EXT) JsonUtil
						.jsonStringToObject(obj, SYS_POST_DICT_EXT.class);

				success += deptPostManagerDal.DoUpdatePostDict(entity,
						super.getBaseDB());
			}
		}
		HLogger.info("DeptPostManagerBll Function savePostDict end !");
		return success;
	}

//	// 描述：查找某个节点下的所有叶子节点
//	private java.util.List<BaseTreeNode> findAllChildrenToTreeNode(
//			final java.math.BigDecimal parentId,
//			java.util.List<BaseEntity> units) throws BaseBllException {
//		java.util.List<BaseTreeNode> tempNodes = new java.util.ArrayList<BaseTreeNode>();
//		List<SYS_DEPT_INFO_EXT> entites = Linq4j
//				.asEnumerable(
//						Arrays.asList(units.toArray(new SYS_DEPT_INFO_EXT[] {})))
//				.where(new Predicate1<SYS_DEPT_INFO_EXT>() {
//					public boolean apply(SYS_DEPT_INFO_EXT entity) {
//						return Long.parseLong(parentId + "") == Long
//								.parseLong(entity.getP_SERIAL_NO() + "");
//					}
//				}).toList();
//		for (SYS_DEPT_INFO_EXT entity : entites) {
//
//			BaseTreeNode node = new BaseTreeNode();
//			node.setid(entity.getDEPT_GLOBAL_NO());
//			node.settext(entity.getDEPT_NAME());
//
//			java.util.List<BaseTreeNode> childrenNodes = this
//					.findAllChildrenToTreeNode(entity.getSERIAL_NO(), units);
//			if (childrenNodes == null || childrenNodes.isEmpty()) {
//				node.setstate("open");
//			} else {
//				node.setchildren(childrenNodes.toArray(new BaseTreeNode[] {}));
//				node.setstate("closed");
//			}
//			node.setattributes(entity);
//			tempNodes.add(node);
//		}
//		return tempNodes;
//	}

	public Object cancelUserPost() throws BaseBllException {
		DeptPostManagerDal deptPostManagerDal = new DeptPostManagerDal();

		String infoId = super.getParameterValue("INFO_ID");
		String userId = super.getParameterValue("USER_ID");
		return deptPostManagerDal.cancelUserPost(super.getBaseDB(), infoId,
				userId);
	}

	public Object saveUserPost() throws BaseBllException {
		DeptPostManagerDal deptPostManagerDal = new DeptPostManagerDal();
		SYS_USER_EXT appuser = (SYS_USER_EXT) super.getContext().getSessionAttribute("appuser");
		String infoId = super.getParameterValue("INFO_ID");
		String userId = super.getParameterValue("USER_ID");
		return deptPostManagerDal.saveUserPost(super.getBaseDB(),
				appuser, infoId, userId);
	}
	
	public AppDataTable initPostDict() throws BaseBllException {
		HLogger.info("DeptPostManagerBll Function initPostDict start !");
		AppDataTable result = deptPostManagerDal.initPostDict(super.getBaseDB());
		HLogger.info("DeptPostManagerBll Function initPostDict end !");
		return result;
	}
}