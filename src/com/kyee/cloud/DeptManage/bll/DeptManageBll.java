package com.kyee.cloud.DeptManage.bll;

import java.util.ArrayList;
import java.util.List;

import model.base.ext.SYS_DEPT_INFO_EXT;
import model.base.ext.SYS_USER_EXT;
import APP.Comm.BLL.BaseBLL;
import APP.Comm.BLL.BaseBllException;
import APP.Comm.DataBase.Helper.AppDataTable;
import APP.Comm.DataBase.Helper.IDataBase;
import APP.Comm.Util.DotNetToJavaStringHelper;
import APP.Comm.Util.HLogger;
import APP.Comm.Util.JsonUtil;
import APP.Comm.View.BaseTreeNode;
import APP.Model.BaseEntity;

import com.kyee.cloud.DeptManage.dal.DeptManageDal;


public class DeptManageBll extends BaseBLL {
	private DeptManageDal deptManageDal = new DeptManageDal();

	// 创建树形结构
	public final java.util.List<BaseTreeNode> GetDeptTree()
			throws BaseBllException {
		HLogger.info("DeptPostManagerBll Fuction GetDeptTree Start!");
		List<BaseEntity> list = deptManageDal.GetDeptTree(super.getBaseDB());
		List<BaseTreeNode> depttree = new ArrayList<BaseTreeNode>(); //构造的菜单树
		if(list != null && list.size() > 0) {
			for (BaseEntity be : list) {
				SYS_DEPT_INFO_EXT sysDeptInfo = (SYS_DEPT_INFO_EXT) be;
				BaseTreeNode node = new BaseTreeNode();
				node.setid(sysDeptInfo.getDEPT_ID()+"");
				node.settext(sysDeptInfo.getDEPT_NAME());
				List<BaseTreeNode> nds = converChildren(super.getBaseDB(),sysDeptInfo.getDEPT_LIST_CODE());
				if(nds.size() > 0) {
					node.setchildren(nds.toArray(new BaseTreeNode[] {}));
					node.setstate("open");
				}else {
					node.setstate("close");
				}
				node.setattributes(sysDeptInfo.getDEPT_LIST_CODE());
				depttree.add(node);
			}
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
	private List<BaseTreeNode> converChildren(IDataBase baseDB, String deptListCode) throws BaseBllException {
		
		List<BaseEntity> ens = deptManageDal.GetDeptTree(super.getBaseDB(),deptListCode);
		List<BaseTreeNode> nodes = new ArrayList<BaseTreeNode>();
		for (BaseEntity be : ens) {
			BaseTreeNode node = new BaseTreeNode();
			SYS_DEPT_INFO_EXT sysDeptInfo = (SYS_DEPT_INFO_EXT) be;
			node.setid(sysDeptInfo.getDEPT_ID()+"");
			node.settext(sysDeptInfo.getDEPT_NAME());
			List<BaseTreeNode> ns = converChildren(baseDB, sysDeptInfo.getDEPT_LIST_CODE());
			if (ns.size() > 0) {
				node.setchildren(ns.toArray(new BaseTreeNode[] {}));
				node.setstate("closed");
			} else {
				node.setstate("close");
			}
			node.setattributes(sysDeptInfo.getDEPT_LIST_CODE());
			nodes.add(node);
		}
		return nodes;
	}
	
	/**
	 * 说明：修改 作者：刘勇 时间：2013年11月22日 16:05:58
	 * 
	 * @return
	 */
	public final Object DoUpdate() throws BaseBllException {
		SYS_USER_EXT appuser = (SYS_USER_EXT) super.getContext().getSessionAttribute("appuser");
		String obj = super.getParameterValue("obj");
		String beforeDeptCode = super.getParameterValue("BEFORE_DEPT_CODE");
		if (DotNetToJavaStringHelper.isNullOrEmpty(obj)) {
			throw new BaseBllException("obj不能为空！");
		}
		SYS_DEPT_INFO_EXT entity = (SYS_DEPT_INFO_EXT) JsonUtil
				.jsonStringToObject(obj, SYS_DEPT_INFO_EXT.class);
		entity.setOPERATOR(appuser.getUSER_CODE());
		entity.setOPERATOR_NAME(appuser.getUSER_NAME());
		int result = super.getBaseDB().Update(entity);
		if (!entity.getDEPT_CODE().equals(beforeDeptCode)) {
			result += deptManageDal.DoUpdate(beforeDeptCode, entity.getDEPT_CODE(), super.getBaseDB());
		}
		return result;
	}

	/**
	 * 说明：查询编制部门树 作者：刘勇 时间：2013年11月22日 16:06:24
	 */
//	public final java.util.List<BaseTreeNode> GetDeptTree()
//			throws BaseBllException {
//		List<BaseTreeNode> resultNodes = new ArrayList<BaseTreeNode>();
//		List<BaseEntity> units = this.deptManageDal
//				.DoGetDeptTree(super.getBaseDB(), super.getHrpUser(), limit,deptStatus);
//		if (units == null || units.isEmpty()) {
//			return resultNodes;
//		}
//		BaseTreeNode root = new BaseTreeNode();
//		java.util.List<BaseEntity> entites = Linq4j.asEnumerable(units)
//				.where(new Predicate1<BaseEntity>() {
//					@Override
//					public boolean apply(BaseEntity entity) {
//						return -100 == ((SYS_DEPT_INFO_EXT) (entity))
//								.getSERIAL_NO().intValue();
//					}
//				}).toList();
//		if (entites.size() == 0) {
//			return resultNodes;
//		}
//		SYS_DEPT_INFO_EXT drDept = (SYS_DEPT_INFO_EXT) entites.get(0);
//		root.setid(drDept.getDEPT_CODE());
//		root.settext(root.getid() + '-' + drDept.getDEPT_NAME());
//		root.setchildren(this.DrDeptToTreeNode(drDept.getSERIAL_NO(), units)
//				.toArray(new BaseTreeNode[] {}));
//		root.setattributes(drDept);
//		resultNodes.add(root);
//		return resultNodes;
//	}

	/**
	 * 说明：新增编制部门 作者：刘勇 时间：2013年11月22日 16:06:48
	 * 
	 * @return
	 */
	public final Object DoAdd() throws BaseBllException {
		String obj = super.getParameterValue("obj");
		SYS_USER_EXT appUser = (SYS_USER_EXT) super.getContext().getSessionAttribute("appuser");
		if (DotNetToJavaStringHelper.isNullOrEmpty(obj)) {
			throw new BaseBllException("obj不能为空！");
		}
		SYS_DEPT_INFO_EXT entity = (SYS_DEPT_INFO_EXT) JsonUtil
				.jsonStringToObject(obj, SYS_DEPT_INFO_EXT.class);
		String deptCode = entity.getDEPT_CODE();
		if(deptCode.startsWith("-")){
			deptCode = deptCode.substring(1);
		}
		// 检查用户输入的编制部门代码是否已经存在
		CheckExists(deptCode);
		entity.setOPERATOR(appUser.getUSER_CODE());
		entity.setOPERATOR_NAME(appUser.getUSER_NAME());
		return this.deptManageDal.DoAdd(entity, super.getBaseDB());
	}

	/**
	 * 说明：如果编制部门代码存在，则不允许添加 作者：刘勇 日期：2013-6-24 21:01:51
	 * 
	 * @param id
	 */
	private void CheckExists(String id) throws BaseBllException {
		boolean ret = false;
		ret = super.getBaseDB().CheckExists("SYS_DEPT_INFO", "DEPT_CODE",
				id.toUpperCase());
		if (ret) {
			throw new BaseBllException("代码为：" + id + " 的编制部门已经存在！请重新输入编制部门代码！");
		}
	}

	/**
	 * 说明：查询 作者：刘勇 时间：2013年11月22日 16:07:17
	 * 
	 * @return
	 */
	public final AppDataTable DoQuery() throws BaseBllException {
		String deptListCode = super.getParameterValue("DEPT_LIST_CODE");
		String codeOrName = super.getParameterValue("CODEORNAME");
		AppDataTable result = deptManageDal.DoQuery(super.getBaseDB(),deptListCode,codeOrName,
				super.getGridRequestParameters());
		return result;
	}

	/**
	 * 撤销编制部门信息 作者：刘勇 时间：2013年12月3日 15:07:33
	 * 
	 * @return
	 */
	public final Object DoCancelDept() throws BaseBllException {
		String dept_code = super.getParameterValue("DEPT_CODE");
//		String dept_id = super.getParameterValue("DEPT_ID");
		String p_dept_id = super.getParameterValue("P_DEPT_ID");
		if (DotNetToJavaStringHelper.isNullOrEmpty(dept_code)) {
			throw new BaseBllException("未接收到页面传入的删除数据信息！");
		}

		int i = deptManageDal.IsExistPerson(dept_code, super.getBaseDB());
		if (i == 0) {
			deptManageDal.DoCancelDept(dept_code, p_dept_id, super.getBaseDB());
			return 0;
		} else {
			throw new BaseBllException("该编制部门或下级部门下面存在人员，不允许撤销该部门！");
		}

	}
	
	/**
	 * <pre>
	 * 任务：HRPHRDEVJAVA-285
	 * 描述：恢复撤销的部门
	 * 作者：杨乐
	 * 日期：2014年11月4日 下午5:16:43
	 * @return
	 * @throws BaseBllException
	 * returnType：Object
	 * </pre>
	 */
	public final int RestoreDept() throws BaseBllException {
		HLogger.info("DeptManageBll Function RestoreDept Start!");
		int result = 0;
		String dept_code = super.getParameterValue("DEPT_CODE");
		String p_dept_id = super.getParameterValue("P_DEPT_ID");
		//Edit Start By YangLe 任务：HRPDRTESTJAVA-1192 描述：更改返回值 时间：2014年11月6日17:49
		result = deptManageDal.DoRestoreDept(dept_code, p_dept_id, super.getBaseDB());
		//Edit End By YangLe 任务：HRPDRTESTJAVA-1192 描述：更改返回值 时间：2014年11月6日17:49
		HLogger.info("DeptManageBll Function RestoreDept End!");
		return result;
	}
	
	/**
	 * <pre>
	 * 任务：HRPHRDEVJAVA-310
	 * 描述：部门调动
	 * 作者：杨乐
	 * 日期：2014年11月20日 上午11:49:36
	 * @return
	 * @throws BaseBllException
	 * returnType：int
	 * </pre>
	 */
	public final int DoTransationDept() throws BaseBllException{
		HLogger.info("DeptManageBll Function DoTransationDept Start!");
		int result = 0;
		//接收参数
		String fromSerialNo = super.getParameterValue("FROMSERIALNO");
		String toSerialNo = super.getParameterValue("TOSERIALNO");
		result = deptManageDal.DoTransationDept(super.getBaseDB(), fromSerialNo, toSerialNo);
		HLogger.info("DeptManageBll Function DoTransationDept End,The result is "+result);
		return result;
	}
	
	/**
	 * <pre>
	 * 任务：HRPHRDEVJAVA-310
	 * 描述：部门合并
	 * 作者：杨乐
	 * 日期：2014年11月20日 下午5:48:46
	 * @return
	 * @throws BaseBllException
	 * returnType：int
	 * </pre>
	 */
	public final int DoMergeDept() throws BaseBllException{
		HLogger.info("DeptManageBll Function DoMergeDept Start!");
		int result = 0;
		//接收参数
		String fromSerialNo = super.getParameterValue("FROMSERIALNO");
		String toSerialNo = super.getParameterValue("TOSERIALNO");
		//1.判断系统是否启用父节点维护人员 1:启用 0:未启用
		int checkResult = deptManageDal.CheckIsFHaveUsers(super.getBaseDB());
		//2.若启用，则判断该异动部门下是否包含有人员信息
		if(1 == checkResult){
			result += deptManageDal.CheckIfExistHuman(super.getBaseDB(), fromSerialNo);
			if(result > 0){
				throw new BaseBllException("异动部门中包含人员信息，不能合并！");
			}
		}
		//3.部门合并
		result = deptManageDal.DoMergeDept(super.getBaseDB(), fromSerialNo, toSerialNo);
		HLogger.info("DeptManageBll Function DoMergeDept Start!");
		return result;
	}

	public int DoDelete() throws BaseBllException {
		HLogger.info("DeptManageBll Function DoDelete Start");
		String deptId = super.getParameterValue("DEPT_ID");
		String deptCode = super.getParameterValue("DEPT_CODE");
		int result = 0;
		result += deptManageDal.DeleteUser(super.getBaseDB(),deptCode,deptId);
		result += deptManageDal.DeletePost(super.getBaseDB(),deptCode,deptId);
		result += deptManageDal.DeleteDept(super.getBaseDB(),deptCode,deptId);
		HLogger.info("DeptManageBll Function DoDelete End");
		return result;
	}
	
	public AppDataTable InitDeptCombobox() throws BaseBllException {
		HLogger.info("DeptManageBll Function InitDeptCombobox Start");
		AppDataTable result = deptManageDal.GetDeptInfo(super.getBaseDB());
		HLogger.info("DeptManageBll Function InitDeptCombobox End");
		return result;
	}
	
	public AppDataTable InitEditCombobox() throws BaseBllException {
		HLogger.info("DeptManageBll Function InitDeptCombobox Start");
		String deptCode = super.getParameterValue("deptCode");
		AppDataTable result = deptManageDal.GetDeptInfo(super.getBaseDB(),deptCode);
		HLogger.info("DeptManageBll Function InitDeptCombobox End");
		return result;
	}
}
