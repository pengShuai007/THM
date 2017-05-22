/**
 * 全局变量  comboBoxActionUrl已经在common中定义，直接调用即可 
 */
/************************************************************************/
/* 
 说明：部门管理JS
 作者：刘勇
 时间：2013年11月27日 18:53:08
 */
/** ********************************************************************* */

var controlID = "#tree";
var gridId = "#grid";
var winAddId = "#window_add";
var addFormId = "#addform";
var winEditId = "#window_edit";
var editFormId = "#editform";
var widAddDeptId = "#window_addDept";
var addDeptFormId = "#addDeptform";
var menuGridId = "#gridmm";
var action = "../DeptManage/action/DeptManageAction.jspx?op=";
var alertAdd = "新增提示";
var alertCancel = "撤销提示";
var alertEdit = "修改提示";
var i = 0;
var editDeptCode = "00";

// dom准备就绪后执行
$(document).ready(function() {
	// 继承父级页面样式表
	superCss();
	// 初始化页面
	initScrean();
	document.onkeyup = keyDownOnClick;
});

//键盘事件
function keyDownOnClick(e) {
	var currKey = 0, e = e || event;
	currKey = e.keyCode || e.which || e.charCode;
	if (currKey == 13) {// 回车
		onQuery();
	}
}


/**
 * 初始化页面(西部tree，中部grid)
 */
function initScrean() {
	// 初始化部门tree
//	initDeptTree();
	// 初始化Grid
	initDeptGrid();
	initDeptCombobox();
}

/**
 * 初始化部门tree
 * 任务：HRPHRDEVJAVA-213  描述：改用公共方法初始化部门树
 * 修改人：张飞   时间：2014年9月16日10:15:09
 * 任务号：HRPDRTESTJAVA-923
 * 修改：蔡丹丹
 * 描述：取消部门权限
 * 日期：2014年9月18日  15:28:12
 * 修改者：杨乐 时间：2015年2月3日15:06 任务：HRPHRDEVJAVA-405 描述：对部门属性进行过滤
 */
function initDeptTree() {
	$.messager.progress({
		text : "正在生成部门树。。."
	});
	$(controlID).tree({
		url : action + "GetDeptTree",
		animate : true,
		lines : true,
		onClick : function(node) {
			onQuery();
		},
		onLoadError : onLoadError,
		onLoadSuccess : function(node, data) {
			$.messager.progress("close");
		}
	});
}

/**
 * 初始化grid
 * 任务：HRPHRDEVJAVA-213  描述：增加queryParams属性
 * 修改人：张飞    时间：2014年9月16日10:18:21
 * 任务号：HRPDRTESTJAVA-923
 * 修改：蔡丹丹
 * 描述：是“撤销”状态置红
 * 日期：2014年9月19日  09:28:12
 */
function initDeptGrid() {
	$(gridId).datagrid({
		fit : true,
		nowrap : true,
		autoRowHeight : true,
		striped : true,
		collapsible : true,
		url : action + 'DoQuery',
		queryParams: {
			"DEPT_LIST_CODE" : "",
			"CODEORNAME" : ""
		},
		idField : 'DEPT_ID',
		columns : creatColums(),
		pagination : true,
		rownumbers : true,
		singleSelect : true,
		toolbar : "#tb",
		onLoadError : onLoadError,
		onLoadSuccess : function() {
			$(gridId).datagrid("clearSelections");
		}
	});
}

/**
 * 创建表头
 * 
 * @returns {Array}
 */
function creatColums() {
	var colums = [ [ {
		field : 'DEPT_CODE',
		title : '部门代码',
		width : 120,
		align : 'left',
		halign : 'center'
	}, {
		field : 'DEPT_NAME',
		title : '部门名称',
		width : 120,
		align : 'center'
	}, {
		field : 'VM_FLAG',
		title : '是否是虚拟部门',
		width : 100,
		align : 'center',
		formatter : function(value) {
			if(value == 1) {
				return '是';
			} else {
				return '否';
			}
		}
	}, {
		field : 'COMMENTS',
		title : '备注',
		width : 120,
		align : 'center'
	}, {
		field : 'OPERATOR_NAME',
		title : '操作人',
		width : 100,
		align : 'center'
	}, {
		field : 'OPERATOR_DATE',
		title : '操作时间',
		width : 120,
		align : 'center'
	} ] ];

	return colums;
}

/**
 * 描述：查询
 * 修改：杨乐 任务：HRPHRDEVJAVA-311 描述：增加查询条件 时间：2014年11月24日10:36
 */
function onQuery() {
	var node = $(controlID).tree('getSelected');
	var codeOrName = $("#codeOrName").val();
	//获取部门类型的值
	var obj = undefined;
	if (node== "" || node==undefined || node==null) {
		 obj = {
			"DEPT_CODE" : "00",
			"CODEORNAME" : codeOrName
		};
	} else {
		 obj = {
			"DEPT_CODE" : node.attributes,
			"CODEORNAME" : codeOrName
		};
	}
	$(gridId).datagrid({
		queryParams : obj,
		pageNumber:1
	});
}

function initDeptTypeCombox(ID) {
	$("#" + ID).combobox({
		valueField : 'id',
		textField : 'text',
		required : false,
		width : 235,
		editable : false,
		url: comboBoxActionUrl + "getComboBox",
		onBeforeLoad : function(param) {
			param.table = "SYS_CODE_ITEM ";
			param.id = "ITEM_ID";
			param.text = "ITEM_NAME";
			//edit by 蔡丹丹 2014年12月23日  16:35:12 HRPDRTESTJAVA-1391 增加启用标志过滤条件
			param.where = " AND DICT_ID='8001' AND VALIDATE_FLAG = 1 "; // 科室类型
			param.sort = "ITEM_ORDER";
			param.order = "ASC";
		},
		onSelect : function() {
		},
		onLoadError : onLoadError, // iframe-common.js已定义
		onLoadSuccess : function(data) {
		}
	});

}

//初始化字典项目
function initSearchBoxByParam(controlId, dictId, widthValue) {
	$(controlId).combobox({
		width : widthValue,
		url : comboBoxActionUrl + "getComboBox",
		valueField : 'id',
		textField : 'text',
		onBeforeLoad : function(param) {
			param.table = "SYS_CODE_ITEM";
			param.id = "ITEM_ID";
			param.text = "ITEM_NAME";
			param.where = " AND DICT_ID = '" + dictId + "' AND VALIDATE_FLAG = 1 " ;
			param.sort = "ITEM_ORDER";
			param.order = "ASC";
			param.defaultId = "";
			param.defaultText = "全部";
		}
	});
}
// /////////////////////////////////新增/////////////////////////////
/**
 * 新增编制部门之前
 */
function openAdd() {
//	var node = $(controlID).tree('getSelected');
//	if (node == undefined) {
//		$.messager.alert("提示", "请选择编制部门（左侧列表）！", 'info');
//		return;
//	}
	$(winAddId).form('clear');
	// 上级部门层级代码
//	$("#add_DEPT_CODES").val(node.attributes);
	// 编制部门启用标记
	$("#add_VM_FLAG2").attr({"checked" : "checked"});
//	$('#btnsave').linkbutton('enable');
	$(winAddId).window('open');
}

/**
 * 新增
 * 任务号：HRPDRTESTJAVA-923
 * 修改：蔡丹丹
 * 日期:2014年9月19号  09:37:23
 * 描述：新增部门后刷新部门树
 */
function add() {
	var obj = checkInputAddUpdate("add");
	if (!this.success) {
		$.messager.alert(alertAdd, this.message, 'warning');
		return;
	} else {
		this.obj.DEPT_CODE = this.obj.DEPT_CODE + "-" + this.obj.DEPT_LIST_CODE;
		var data = {
			"obj" : jsonToString(obj)
		};
		$.messager.progress();
		$.ajax({
			type : "POST",
			url : action + "DoAdd",
			data : data,
			dataType : "json",
			success : function(data) {
				$.messager.progress('close');
				if (data.success) {
//					initDeptTree();
					$.messager.alert(alertAdd, data.message, 'info');
					$(winAddId).window('close');
					onQuery();
					initDeptCombobox();
				} else {
					$.messager.alert(alertAdd, data.message, 'error');
				}
			},
			error : function(data) {
				ajaxError(data, alertAdd);
			}
		});
	}
}

/**
 * 取消新增
 */
function cancelAdd() {
	$(winAddId).window('close');
	$(addform).form('clear');
}

/**
 * 撤销编制部门
 */
function cancelDeptById() {
	var selected = getSelected();
	
	if (selected != undefined && selected.length > 0) {
		//Edit Start By YangLe 任务：HRPHRDEVJAVA-285 时间：2014年11月4日16:18
		var isVisible = selected[0].VISIBLE_INDICATOR;
		if(isVisible != "1"){
			//Edit Start By YangLe 任务：HRPDRTESTJAVA-1194 描述：对消息信息进行更改 时间：2014年11月6日16:58
//			$.messager.alert("温馨提示", '只能对正常状态的信息进行恢复！', 'warning');
			$.messager.alert("温馨提示", '只能对正常状态的信息进行撤销！', 'warning');
			//Edit End By　YangLe 任务：HRPDRTESTJAVA-1194 描述：对消息信息进行更改 时间：2014年11月6日16:58 
			return;
		}
		var dept_id = "";
		dept_id = selected[0].SERIAL_NO;
		if(dept_id == "-100"){
			$.messager.alert("温馨提示", '该部门不能被撤销！', 'warning');
			return;
		}
		//Edit End By YangLe 任务：HRPHRDEVJAVA-285 时间：2014年11月4日16:18
		$.messager.confirm('撤销提示', "您确定要撤销选中数据吗？该操作将撤销所有下级部门！", function(r) {
			if (r) {
				var dept_code = "";
				dept_code = selected[0].DEPT_CODE;
				p_dept_id = selected[0].P_SERIAL_NO;
				var data = {
					"DEPT_CODE" : dept_code,
					"DEPT_ID" : dept_id,
					"P_DEPT_ID" : p_dept_id
				};
				$.messager.progress();
				$
						.ajax({
							type : "POST",
							url : action + "CancelDept",
							data : data,
							dataType : "json",
							success : function(data) {
								$.messager.progress('close');
								if (data.success) {
									$.messager.alert(alertCancel, data.message,
											'info');
									//任务：HRPHRDEVJAVA-213  描述：使用查询方法onQuery  修改人：张飞   时间：2014年9月16日13:03:25
									//initScrean();
									 onQuery();
								} else {
									$.messager.alert(alertCancel, data.message,
											'error');
								}
							},
							error : function(data) {
								ajaxError(data, alertCancel);
							}
						});
			}
		});
	} else {
		$.messager.alert(alertCancel, '请您至少选择一条数据进行撤销！', 'warning');
	}
}

/**
 * 任务：HRPHRDEVJAVA-285
 * 描述：恢复撤销
 * 时间：2014年11月4日16:32
 * 作者：杨乐
 */
function restoreDeptById(){
	var selected = getSelected();
	
	if (selected != undefined && selected.length > 0) {
		var isVisible = selected[0].VISIBLE_INDICATOR;
		if(isVisible != "0"){
			$.messager.alert("温馨提示", '只能对已撤销状态的信息进行恢复！', 'warning');
			return;
		}
		$.messager.confirm('撤销提示', "您确定要恢复选中数据吗？该操作将恢复所有下级部门！", function(r) {
			if (r) {
				var dept_code = "";
				var p_dept_id = "";
				dept_code = selected[0].DEPT_CODE;
				p_dept_id = selected[0].P_SERIAL_NO;
				var data = {
					"DEPT_CODE" : dept_code,
					"P_DEPT_ID" : p_dept_id
				};
				$.messager.progress();
				$
						.ajax({
							type : "POST",
							url : action + "restoreDept",
							data : data,
							dataType : "json",
							success : function(data) {
								$.messager.progress('close');
								if (data.success) {
									$.messager.alert("温馨提示", data.message,'info');
									 onQuery();
								} else {
									$.messager.alert("温馨提示", data.message,'error');
								}
							},
							error : function(data) {
								//Edit Start By YangLe 任务：HRPDRTESTJAVA-1192 描述：更改提示信息 时间：2014年11月7日17:03
//								ajaxError(data, "恢复撤销错误！");
								ajaxError(data, "提示");
								//Edit End By YangLe 任务：HRPDRTESTJAVA-1192 描述：更改提示信息 时间：2014年11月7日17:03
							}
						});
			}
		});
	} else {
		$.messager.alert(alertCancel, '请您选择一条数据进行恢复！', 'warning');
	}
}


// /////////////////////////////////修改/////////////////////////////
/**
 * 修改之前，将选中行的数据，赋值给修改窗口。
 * 任务：HRPHRDEVJAVA-213  描述：修改修改校验
 * 修改人：张飞     时间：2014年9月16日10:58:51
 * 任务号：HRPDRTESTJAVA-923
 * 修改：蔡丹丹
 * 描述：修改判断条件
 * 日期：2014年9月18日  18:45:12
 */
function openUpdate() {
	$(winEditId).form("clear");
	// 得到修改列
	var node = $(gridId).datagrid('getSelected');
	if(node == null||node==''||node==undefined){
		$.messager.alert("修改提示", "请选择一条进行操作！", 'warning');
		return;
	}
	// 赋值给edit window
	$("#edit_DEPT_ID").val(node.DEPT_ID);
	// 上级部门
	var deptCode = node.DEPT_CODE;
	if(node.DEPT_CODE.lastIndexOf("-") > -1) {
		deptCode = node.DEPT_CODE.substr(0,node.DEPT_CODE.lastIndexOf("-"));
		$('#edit_DEPT_CODE').combobox({
			url : action + 'InitEditCombobox',
			valueField : 'DEPT_CODE',
			textField : 'DEPT_NAME',
			editable : false,
			onBeforeLoad : function(param) {
				param.deptCode = node.DEPT_CODE;
			}
		});
		$("#edit_DEPT_CODE").combobox({
			url : "",
			disabled : false
		});
	} else {
		deptCode = "";
		$("#edit_DEPT_CODE").combobox({
			url : "",
			disabled : true
		});
	}
	$("#edit_DEPT_CODE").combobox('setValue',deptCode);
	editDeptCode = node.DEPT_CODE;
	$("#edit_DEPT_LIST_CODE").val(node.DEPT_LIST_CODE);
	// 科室名称
	$("#edit_DEPT_NAME").val(node.DEPT_NAME);
	if (node.VM_FLAG ==1 ){
		$("#edit_VM_FLAG1").attr({"checked" : "checked"});
	} else {
		$("#edit_VM_FLAG2").attr({"checked" : "checked"});
	}
	//Edit Start By YangLe 任务：HRPHRDEVJAVA-402 描述：对备注赋值进行处理 时间：2015年1月29日18:10
	if(node.COMMENTS == null || node.COMMENTS == undefined){
		$("#edit_COMMENTS").val("");
	}else{
		$("#edit_COMMENTS").val(node.COMMENTS);
	}
	// 打开window
//	$('#btnupdate').linkbutton('enable');
	$(winEditId).window('open');
	 
}

/**
 * 修改
 * 任务号：HRPDRTESTJAVA-923
 * 修改：蔡丹丹
 * 描述：修改后刷新部门树
 * 日期：2014年9月19日  09:45:12
 */
function update() {
	var obj = checkInputAddUpdate("edit");
	if (!this.success) {
		$.messager.alert(alertEdit, this.message, 'info');
		return;
	}
	this.obj.DEPT_CODE = this.obj.DEPT_CODE + "-" + $("#edit_DEPT_LIST_CODE").val();
	var data = {
		"obj" : jsonToString(obj),
		"BEFORE_DEPT_CODE" : editDeptCode
	};
	$.messager.progress();
	$.ajax({
		type : "POST",
		url : action + "DoUpdate",
		data : data,
		dataType : "json",
		success : function(data) {
			$.messager.progress('close');
			if (data.success) {
//				initDeptTree();
				$.messager.alert(alertEdit, data.message, 'info');
				$(winEditId).window('close');
				onQuery();
			} else {
				$.messager.alert(alertEdit, data.message, 'error');
			}
		},
		error : function(data) {
			ajaxError(data, alertEdit);
		}
	});
}

/**
 * 取消修改
 */
function cancelUpdate() {
	$(winEditId).window('close');
	$(editFormId).form("clear");
}

/**
 * 修改校验
 * 任务：HRPHRDEVJAVA-213  
 * 描述：注掉电话号码验证
 * 修改人：张飞
 * 时间：2014年9月16日16:13:48
 */
function checkInputAddUpdate(op) {
	if (op == "add") {
		this.obj = paramToJson(decodeURIComponent($(addFormId).serialize()));
	} else {
		this.obj = paramToJson(decodeURIComponent($(editFormId).serialize()));
	}
	this.success = true;
	this.message = "";
	/*if (obj.DEPT_CODE == "") {
		this.message = "上级部门不能为空！";
		this.success = false;
	} else */if (obj.DEPT_LIST_CODE == "") {
		this.message = "本级部门代码不能为空！";
		this.success = false;
	} else if (obj.DEPT_NAME == "") {
		this.message = "部门名称不能为空！";
		this.success = false;
	}
	return this.obj;
}
/**
 * 得到被选择的行
 */
function getSelected() {
	return $(gridId).datagrid('getSelections');
}


/**
 * 任务号：HRPHRDEVJAVA-161
 * 作者：蔡丹丹
 * 时间：2014年8月30号  16:54:03
 * 描述： 双击弹出修改窗口
 */
function openEdit(rowIndex, rowData) {
	$(gridId).datagrid('clearChecked');
	$(gridId).datagrid('checkRow', rowIndex);
	if (rowData == null) {
		$.messager.alert('温馨提示', "请先选择需要操作的数据", 'info');
		return;
	}
	var id = rowData["DEPT_GLOBAL_NO"];
	if (id == null || id == "" || id == undefined) {
		$.messager.alert('温馨提示', "数据有误,请重新选择", 'info');
		return;
	}
	openUpdate();
}
/**
 * 任务：HRPHRDEVJAVA-213   描述：初始化部门负责人
 * 作者：张飞     时间：2014年9月16日11:06:19
 * 修改：杨乐 任务：HRPDRTESTJAVA-1477 描述：修改控件显示宽度 时间：2015年1月19日10:21
 */
function initCombobxDeptPerson(controlId,deptGlobalNo){
	var tableStr = "( SELECT U.USER_ID, U.USER_CODE, U.USER_NAME, U.INPUT_CODE,D.DEPT_GLOBAL_NO, "
			+ " D.DEPT_NAME, U.USER_STATUS,U.PHONE_NO"
			+ " FROM HR_USERS U, V_SYS_DEPT_DICT D WHERE U.DEPT_ID = D.SERIAL_NO ) ";
	var comboWhereStr = " AND USER_STATUS='A' AND  REPLACE(DEPT_GLOBAL_NO,chr(58),';') LIKE '"+deptGlobalNo.replace(/:/g,";")+"'||'%'";

	$(controlId).combogrid({
		url : comboGridActionUrl + "CommComboGridCols",
		panelWidth : 280,
		panelHeight : 240,
		mode : 'remote',
		idField : 'USER_CODE',
		textField : 'USER_NAME',
		pagination : true,// 分页码
		fitColumns:true,
		rownumbers : true,// 行号0
		onBeforeLoad : function(param) {
			param.table = tableStr;
			param.id = "USER_CODE";
			param.text = "USER_NAME";
			param.where = comboWhereStr;
			param.sort = "USER_CODE";
			param.order = "ASC";
			param.inputCode = "INPUT_CODE";
			param.isShowInputCode = "true";
			param.isDistinct = "false";
			param.ajaxColName = "USER_CODE";// 分页
		},
		onSelect : function(rowIndex, rowData) {
			 $("#edit_DEPT_TEL").val(rowData['PHONE_NO']);
		},
		columns : [ [ {
			field : 'USER_ID',
			title : '人员ID',
			width : 140,
			hidden : true,
			halign : 'center'
		}, {
			field : 'USER_CODE',
			title : '工号',
			width : 140,
			halign : 'center'
		}, {
			field : 'USER_NAME',
			title : '姓名',
			width : 120,
			halign : 'center'
		}, {
			field : 'inputCodeUpper',
			title : '输入码大写',
			hidden : true
		}, {
			field : 'inputCodeLower',
			title : '输入码小写',
			hidden : true
		} ] ]
	});
}

/**
 * 任务：HRPHRDEVJAVA-310
 * 描述：部门调动
 * 作者：杨乐
 * 时间：2014年11月19日10:43
 */
function onDeptRemove(){
	//初始化异动部门(A)
	initDeptComboTree("#deptFrom",true);
	//初始化异动部门(B)
	initDeptComboTree("#deptTarget",true);
	//设置标题
	$("#deptRemove_window").window({title: '部门调动'});
	//给隐藏控件赋值
	$("#removeAction").val("Transacion");
	$("#deptRemove_window").window('open');
}

/**
 * 任务：HRPHRDEVJAVA-310
 * 描述：部门合并
 * 作者：杨乐
 * 时间：2014年11月19日11:10
 */
function onDeptMerge(){
	//初始化异动部门(A)
	initDeptComboTree("#deptFrom",true);
	//初始化异动部门(B)
	initDeptComboTree("#deptTarget",true);
	//设置标题
	$("#deptRemove_window").window({title: '部门合并'});
	//给隐藏控件赋值
	$("#removeAction").val("Merge");
	$("#deptRemove_window").window('open');
}

/**
 * 任务：HRPHRDEVJAVA-310
 * 描述：关闭部门异动窗口
 */
function cancelRemove(){
	$("#deptRemove_window").window('close');
}

//点击提交按钮
function doAction(){
	//获取隐藏控件的值
	var active = $("#removeAction").val();
	if(active == "Transacion"){//部门调动
		commitRemove();
	}else if(active == "Merge"){//部门合并
		commitMerge();
	}
}

/**
 * 任务：HRPHRDEVJAVA-310
 * 描述：部门异动 提交
 */
function commitRemove(){
	//获取异动部门的信息
	var fromDT = $('#deptFrom').combotree('tree');
	var fromSelected = fromDT.tree('getSelected');
	if(fromSelected == null || fromSelected == ""){
		$.messager.alert('温馨提示', "无法异动，请选择异动部门！", 'warning');
		return;
	}
	var fromGlobalNo = fromSelected.attributes.dept_global_no;
	var fromPSerialNo = fromSelected.attributes.p_serial_no;
	var fromSerialNo = fromSelected.id;
	
	//获取目标部门的信息
	var targetDT = $('#deptTarget').combotree('tree');
	var targetSelected = targetDT.tree('getSelected');
	if(targetSelected == null || targetSelected == ""){
		$.messager.alert('温馨提示', "无法异动，请选择目标部门！", 'warning');
		return;
	}
	var toGlobalNo = targetSelected.attributes.dept_global_no;
	var toPSerialNo = targetSelected.attributes.p_serial_no;
	var toSerialNo = targetSelected.id;
	
	//对异动部门和目标部门进行判断
	if(fromSerialNo == toSerialNo){
		$.messager.alert('温馨提示', "异动部门与目标部门一致，无法异动！", 'warning');
		return;
	}
	//判断异动部门的父部门是否是目标部门
	if(fromPSerialNo == toSerialNo){
		$.messager.alert('温馨提示', "目标部门是异动部门的上级部门，无法异动！", 'warning');
		return;
	}
	//判断异动部门是否是根节点
	if(fromSerialNo == -100){
		$.messager.alert('温馨提示', "根节点部门无法异动！", 'warning');
		return;
	}
	//判断异动部门是否是目标部门的上级部门
	if(toGlobalNo.indexOf(fromGlobalNo) > -1){
		$.messager.alert('温馨提示', "异动部门是目标部门的上级部门，无法异动！", 'warning');
		return;
	}
	
	var obj = {
			"FROMSERIALNO" : fromSerialNo,
			"TOSERIALNO" : toSerialNo 
			};
		$.messager.progress({
			"text" : "正在调动，请稍等。。。"
		});
		$.ajax({
			type : "POST",
			url : action + "doTransationDept",
			data : obj,
			async : false,
			dataType : "json",
			success : function(data) {
				$.messager.progress('close');
				$.messager.alert("温馨提示", data.message, 'info');
				cancelRemove();//关闭异动窗口
				initScrean();//初始化页面
			},
			error : function(data) {
				ajaxError(data, "错误提示");
			}
		});
}

/**
 * 任务:HRPHRDEVJAVA-310
 * 描述：部门合并 提交
 * 时间：2014年11月20日17:33
 */
function commitMerge(){
	//获取异动部门的信息
	var fromDT = $('#deptFrom').combotree('tree');
	var fromSelected = fromDT.tree('getSelected');
	if(fromSelected == null || fromSelected == ""){
		$.messager.alert('温馨提示', "无法异动，请选择异动部门！", 'warning');
		return;
	}
	var fromGlobalNo = fromSelected.attributes.dept_global_no;
	var fromPSerialNo = fromSelected.attributes.p_serial_no;
	var fromSerialNo = fromSelected.id;
	
	//获取目标部门的信息
	var targetDT = $('#deptTarget').combotree('tree');
	var targetSelected = targetDT.tree('getSelected');
	if(targetSelected == null || targetSelected == ""){
		$.messager.alert('温馨提示', "无法异动，请选择目标部门！", 'warning');
		return;
	}
	var toGlobalNo = targetSelected.attributes.dept_global_no;
	var toPSerialNo = targetSelected.attributes.p_serial_no;
	var toSerialNo = targetSelected.id;
	
	//对异动部门和目标部门进行判断
	if(fromSerialNo == toSerialNo){
		$.messager.alert('温馨提示', "异动部门与目标部门一致，无法异动！", 'warning');
		return;
	}
	//判断异动部门是否是根节点
	if(fromSerialNo == -100){
		$.messager.alert('温馨提示', "根节点部门无法异动！", 'warning');
		return;
	}
	//判断异动部门是否是目标部门的上级部门
	if(toGlobalNo.indexOf(fromGlobalNo) > -1){
		$.messager.alert('温馨提示', "异动部门是目标部门的上级部门，无法异动！", 'warning');
		return;
	}
	var obj = {
				"FROMSERIALNO" : fromSerialNo,
				"TOSERIALNO" : toSerialNo 
			  };
		$.messager.progress({
			"text" : "正在合并，请稍等。。。"
		});
		$.ajax({
			type : "POST",
			url : action + "doMergeDept",
			data : obj,
			async : false,
			dataType : "json",
			success : function(data) {
				$.messager.progress('close');
				$.messager.alert("温馨提示", data.message, 'info');
				cancelRemove();//关闭异动窗口
				initScrean();//初始化页面
			},
			error : function(data) {
				ajaxError(data, "错误提示");
			}
		});
}

/**
 * 任务：HRPHRDEVJAVA-310
 * 描述：初始化ComboTree,controlId为控件ID;limit 为权限控制请求,为true表示需要加权限
 * 作者：杨乐
 * 时间：2014年11月21日11:06
 */
function initDeptComboTree(controlId, limit) {
	$.messager.progress({
		"text" : "正在生成部门树。。。"
	});
	$(controlId).combotree({
		url : hrCommAction + "GetDeptTree&limit=" + limit,
		animate : true,
		panelWidth : 360,
		panelHeight : 230,
		lines : true,
		checkbox : false,
		onLoadSuccess : function(node, data) {
			$.messager.progress("close");
		},
		onLoadError : function(data) {
			$.messager.progress("close");
			data = data.responseText;
			data = eval('(' + data + ')');
			$.messager.alert("加载提示", data.message, 'info');
		}
	});
}


function openDelete() {
	var selected = $("#grid").datagrid('getSelections');
	if(selected !=null && selected.length == 1) {
		$.messager.confirm('撤销提示', "您确定要删除选中数据吗？该操作将删除所有下级部门和相关信息！", function(r) {
			if (r) {
				var deptCode = selected[0].DEPT_CODE;
				var deptId = selected[0].DEPT_ID;
				var data = {
					"DEPT_CODE" : deptCode,
					"DEPT_ID" : deptId
				};
				$.messager.progress();
						$.ajax({
							type : "POST",
							url : action + "DoDelete",
							data : data,
							dataType : "json",
							success : function(data) {
								$.messager.progress('close');
								if (data.success) {
									$.messager.alert("温馨提示", data.message,
											'info');
									onQuery();
									initDeptCombobox();
								} else {
									$.messager.alert("温馨提示", data.message,
											'error');
								}
							},
							error : function(data) {
								ajaxError(data, "提示");
							}
						});
			}
		});
	} else {
		$.messager.alert('删除提示','请选择一行数据删除','warning');
		return;
	}
}

function initDeptCombobox() {
	$('#add_DEPT_CODE').combobox({
		url : action+'InitDeptCombobox',
		valueField : 'DEPT_CODE',
		textField : 'DEPT_NAME',
		editable : false
	});
//	$('#edit_DEPT_CODE').combobox({
//		url : action+'InitDeptCombobox',
//		valueField : 'DEPT_CODE',
//		textField : 'DEPT_NAME',
//		editable : false
//	});  
}