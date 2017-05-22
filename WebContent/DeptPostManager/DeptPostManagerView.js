var action = "../DeptPostManager/action/DeptPostManagerAction.jspx?&op=";
//var comboboxUrl = "../Comm/ComboBoxHandler.jspx?op=";
var editIndex = undefined;
var dictEditIndex = undefined; // 编辑字典的行

// dom准备就绪后执行
$(document).ready(function() {
	// 继承父级页面样式表
	superCss();
	initTreeGrid();
	initGrid();
	onQuery();
});

/**
 * 初始化部门tree
 */
function initTreeGrid() {
	$.messager.progress({
		text : "正在加载，请稍候。。。"
	});
	$('#tt').tree({
		// 业务科室树, 根据系统组 V_SYS_DEPT_INFO
		url : action + "getDeptTree",
		animate : true,
		lines : true,
		onClick : treeNodeOnClick,
		onLoadSuccess : function(node, data) {
			$.messager.progress("close");
			if (data.length == 0) {
				$('#tt').tree('isLeaf', node.target);
			}
			return true;
		},
		onLoadError : onLoadError
	});
}
/**
 * 初始化grid
 */
function initGrid() {
	$("#dg").datagrid({
		fit : true,
		nowrap : true,
		autoRowHeight : true,
		striped : true,
		collapsible : true,
//		sortName : 'POST_NAME',
//		sortOrder : 'ASC',
//		remoteSort : true,
		idField : 'INFO_ID',
		singleSelect : true,
		frozenColumns : [ [ {
			field : 'ck',
			checkbox : true
		} ] ],
		fitColums : true,
		columns : creatColums(),
		pagination : true,
		rownumbers : true,
		toolbar : '#tb',
		onDblClickRow : onClickRow,
		onLoadSuccess : onLoadSuccess,
		onLoadError : onLoadError
	});
}

// 创建表头
function creatColums() {
	var colums = [ [ {
		field : 'INFO_ID',
		title : '信息编号',
		hidden : true
	}, {
		field : 'DEPT_ID',
		title : '部门ID',
		hidden : true
	}, {
		field : 'DEPT_NAME',
		title : '部门名称',
		halign : "center",
		align : "left",
		width : 100
	}, {
		field : 'POST_ID',
		title : '岗位',
		width : 100,
		halign : "center",
		align : "left",
		formatter : function(value, row, index) {
			if (row.POST_NAME != undefined && row.POST_NAME != "") {
				return row.POST_NAME;
			}
		},
		editor : {
			type : 'combobox',
			options : {
				url : action + "initPostDict",
				valueField : 'POST_ID',
				textField : 'POST_NAME',
				required : true,
				editable : false
//				,
//				onBeforeLoad : function(param) {
//					param.table = 'SYS_POST_DICT';
//					param.id = 'POST_ID'; // 'TO_CHAR(FIELD_ID)';
//					param.text = 'POST_NAME';
//					param.where = ' AND VALIDATE_FLAG = 1 ';// 暂时不拼接
//				}
			}
		}
	}, {
		field : 'POST_NAME',
		title : '岗位名称',
		hidden : true
	}, {
		field : 'CREATOR',
		title : '创建者',
		width : 100,
		halign : "center",
		align : "left"
	}, {
		field : 'CREATE_TIME',
		title : '创建时间',
		width : 100,
		align : "center",
		formatter : function(value, row, index) {
			if (value != undefined && value != null && value != "") {
				return dateFormatterYMD(value);
			} else {
				return value;
			}
		}
	}, {
		field : 'CREATOR_NAME',
		title : '创建者姓名',
		width : 100,
		halign : "center",
		align : "left"
	} ] ];
	return colums;
}

// 结束当前正在编辑的行，添加新行到结尾
function appendPost() {
	if (endEditing()) {
		// 添加数据行到末尾(科室从左侧树带过来)，测试用
		var node = $('#tt').tree('getSelected');
		if (node == undefined || node == null) {
			$.messager.alert('新增提示',"请在左侧树选择需要增加岗位的部门！",'warning');
			return;
		}

		var obj = {
//			"DEPT_ID" : node.id.substr(node.id.lastIndexOf(":") + 1), // 部门ID
			"DEPT_ID" : node.id, // 部门ID
//			"DEPT_GLOBAL_NO" : node.id, // DEPT_GLOBAL_NO
			"DEPT_NAME" : node.text
		};

		$('#dg').datagrid('appendRow', obj);
		editIndex = $('#dg').datagrid('getRows').length - 1;
		$('#dg').datagrid('selectRow', editIndex).datagrid('beginEdit',
				editIndex);
	}
}

// 进入编辑
function onClickRow(rowIndex, rowData) {
	if (editIndex != rowIndex && typeof (rowIndex) != NaN) {
		if (endEditing()) {
			$("#dg").datagrid('selectRow', rowIndex).datagrid('beginEdit',
					rowIndex);
			editIndex = rowIndex;
		} else {
			$("#dg").datagrid('selectRow', rowIndex);
		}
	}
}
// 结束一行的编辑(只有岗位和人员可编辑，部门不能编辑)
function endEditing() {
	if (editIndex == undefined) {
		return true;
	}
	// 指定生效行数据
	if ($('#dg').datagrid('validateRow', editIndex)) {
		var ed = $('#dg').datagrid('getEditor', {
			index : editIndex,
			field : 'POST_ID'
		});
		if (ed.target != null) {
			var itemName = $(ed.target).combobox('getText');
			$('#dg').datagrid('getRows')[editIndex]['POST_NAME'] = itemName;
		}
		// 刷新并结束编辑一行
		$('#dg').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}

// 移去某行
function removePost() {
	if (editIndex == undefined) {
		return;
	}
	// 取消编辑状态并移除
	$('#dg').datagrid('cancelEdit', editIndex).datagrid('deleteRow', editIndex);
	editIndex = undefined;
}

// 保存修改
function savePost() {
	if (endEditing()) {
		// 准备提交,只封装 DEPT_ID ,POST_ID，USER_ID
		if ($('#dg').datagrid('getChanges').length) {
			// 分步提交修改的数据，通过主键的不重复，解决可能存在的操作次序
			var inserted = $('#dg').datagrid('getChanges', "inserted");
			var deleted = $('#dg').datagrid('getChanges', "deleted");
			var updated = $('#dg').datagrid('getChanges', "updated");

			// 类似JSONTOSTRING，将对象数据转化为字符串，分隔符自定义(对象之间使用|分隔)
			var result = checkdeptpost(inserted,updated);
			if (result != true) {
				$.messager.alert('保存提示',result,'warning');
				return;
			}
			var insertedString = "";
			for (var i = 0; i < inserted.length; i++) {
				if (i != 0) {
					insertedString += "|";
				}
				if (inserted[i].INFO_ID == undefined
						|| inserted[i].INFO_ID == null) {
					inserted[i].INFO_ID = "";
				}
				// 只打包需要的数据
				var package = {
					"DEPT_ID" : inserted[i].DEPT_ID,
					"POST_ID" : inserted[i].POST_ID,
//					"POST_NUM" : inserted[i].POST_NUM
				};
				insertedString += jsonToString(package);
			}
			// 删除只取ID
			var deletedString = "";
			for (var i = 0; i < deleted.length; i++) {
				if (i) {
					deletedString += ",";
				}
				deletedString += deleted[i].INFO_ID;
			}
			// 修改(科室和主键都不能动，只修改岗位和人员)
			var updatedString = "";
			for (var i = 0; i < updated.length; i++) {
				if (i) {
					updatedString += "|";
				}
				if (updated[i].POST_ID == undefined) {
					updated[i].POST_ID = "";
				}
				if (updated[i].POST_NUM == undefined) {
					updated[i].POST_NUM = "0";
				}
				// 只打包需要的数据
				var package = {
					"INFO_ID" : updated[i].INFO_ID,
					"POST_ID" : updated[i].POST_ID,
//					"POST_NUM" : updated[i].POST_NUM
				};
				updatedString += jsonToString(package);
			}

			// 传递json obj对象，对象的属性值用于识别
			var data = {
				"inserted" : insertedString,
				"deleted" : deletedString,
				"updated" : updatedString
			};

			$.messager.progress();
			$.ajax({
				type : "POST",
				url : action + "saveDeptPostInfo",
				data : data,
				dataType : "json",
				success : function(data) {
					$.messager.progress('close');
					if (data.success) {
						$.messager.alert("提示", "提交成功！");
					} else {
						$.messager.alert("提示", "提交失败！");
					}
//					onQuery();
					var node = $('#tt').tree('getSelected');
					treeNodeOnClick(node);
				},
				error : function(data) {
					ajaxError(data, "提交失败");
				}
			});
			$('#dg').datagrid('acceptChanges'); // 接受改变
			// 清除选中项
			$("#dg").datagrid("clearSelections");
		}
	}
}

// 查询按钮(部门岗位)
function onQuery() {
	editIndex = undefined;
	var obj = {
//		"DEPT_GLOBAL_NO" : "", // 部门代码
		"DEPT_CODE" : "", // 部门代码
		"POST_NAME" : $("#search_post").val(), // 岗位
		//任务号：HRPDRTESTJAVA-1023
		//修改：李智博
		//内容：添加模糊搜索
		"DEPT_NAME" : $("#search_dept").val()
	// 用户姓名
	};
	$("#dg").datagrid({
		url : action + 'queryDeptPost', // 查询部门岗位
		queryParams : obj
	});
	$("#dg").datagrid('clearSelections');
}
/**
 * 绑定部门树点击事件
 */
function treeNodeOnClick(node) {
	if (node.text != null) {
		var obj = {
//			"DEPT_GLOBAL_NO" : node.id, // 部门代码(目前使用原始代码)
			"DEPT_CODE" : node.attributes, // 部门代码(目前使用原始代码)
			"POST_NAME" : $("#search_post").val()
//			, // 岗位
//			"USER_NAME" : $("#search_user").val()
		// 用户姓名
		};
		$("#dg").datagrid({
			url : action + 'queryDeptPost', // 查询部门岗位
			queryParams : obj
		});
	}
}

// 岗位字典管理(窗口打开)
function openPostMan() {
	$('#postTypeWindow').window("open");
	initPostType();
}

// 查询岗位
function queryPostDict() {
	$("#dgPostType").datagrid({
		url : action + "getPostDict"
	});
}

// 编制岗位用户(窗口打开)
function planPostUser() {
	if ($('#dg').datagrid('getSelections').length != 1) {
		$.messager.alert("提示", "请选中岗位，并编制该岗位人员！", "info");
		return;
	}

	$('#postUserWindow').window("open");
	initPostUser();
}

// 岗位编制
function initPostType() {
	$("#dgPostType").datagrid({
		fit : true,
		nowrap : true,
		autoRowHeight : true,
		rownumbers : true,
		striped : true,
		singleSelect : true,
		collapsible : true,
		pagination : true,
		toolbar : toolBarPostType(),
		columns : creatPostDictColums(),
		url : action + "getPostDict",
		idField : 'POST_CODE',
		onDblClickRow : onClickPostDict,
		onLoadSuccess : onLoadSuccess,
		onLoadError : onLoadError
	});
}

// 岗位人员编辑
function initPostUser(value) {
	var obj = {
		"INFO_ID" : $('#dg').datagrid('getSelected').INFO_ID
	};
	initSearchBox();
	$("#dgPostUser").datagrid({
		fit : true,
		nowrap : true,
		autoRowHeight : true,
		rownumbers : true,
		striped : true,
		collapsible : true,
		pagination : true,
		title : "在编用户",
		columns : creatPostUserColums(),
		url : action + "getPostUser",
		queryParams : obj, // 不取人名
		idField : 'DETAIL_ID',
		onDblClickRow : cancelUserPost, // 取消用户
		onLoadSuccess : onLoadSuccess,
		onLoadError : onLoadError
	});
	$("#dgPostNoUser").datagrid({
		fit : true,
		nowrap : true,
		autoRowHeight : true,
		rownumbers : true,
		striped : true,
		collapsible : true,
		pagination : true,
		title : "可选用户",
		toolbar : "#tbName",
		columns : creatPostNoUserColums(),
		url : action + "getPostNoUser",
		queryParams : obj,
		idField : 'USER_ID',
		onDblClickRow : saveUserPost, // 保存岗位用户
		onLoadSuccess : onLoadSuccess,
		onLoadError : onLoadError
	});
}

// 岗位人员编辑
function queryPostUser() {
	var value = $('#noname').searchbox('getValue');
	var obj = {
		"INFO_ID" : $('#dg').datagrid('getSelected').INFO_ID,
		"USER_NAME" : (value == undefined) ? "" : value
	};
	$("#dgPostUser").datagrid({
		url : action + "getPostUser",
		queryParams : obj
	// 不取人名
	});
	$("#dgPostNoUser").datagrid({
		url : action + "getPostNoUser",
		queryParams : obj
	});
}
// 初始化搜索框
function initSearchBox() {
	$("#noname").searchbox({
		searcher : function(value, name) {
			queryPostUser();
		},
		prompt : '输入登陆名/用户名'
	});
}

// 取消用户岗位
function cancelUserPost(rowIndex, rowData) {
	$.messager.progress();
	$.ajax({
		type : "POST",
		url : action + "cancelUserPost",
		data : {
			"USER_ID" : rowData.USER_ID,
			"INFO_ID" : $('#dg').datagrid('getSelected').INFO_ID
		},
		dataType : "json",
		success : function(data) {
			$.messager.progress('close');
			queryPostUser();
		},
		error : function(data) {
			ajaxError(data, "提交失败");
		}
	});
}

// 保存用户岗位
function saveUserPost(rowIndex, rowData) {
	$.messager.progress();
	$.ajax({
		type : "POST",
		url : action + "saveUserPost",
		data : {
			"USER_ID" : rowData.USER_ID,
			"INFO_ID" : $('#dg').datagrid('getSelected').INFO_ID
		},
		dataType : "json",
		success : function(data) {
			$.messager.progress('close');
			queryPostUser();
		},
		error : function(data) {
			ajaxError(data, "提交失败");
		}
	});
}

// 创建表头
function creatPostDictColums() {
	var colums = [ [ {
		field : 'POST_ID',
		title : '岗位ID',
		hidden : true
	}, {
		field : 'POST_CODE',
		title : '岗位代码',
		halign : "center",
		align : "left",
		width : 100,
		editor : {
			type : 'text'
		}
	}, {
		field : 'POST_NAME',
		title : '岗位名称',
		halign : "center",
		align : "left",
		width : 100,
		editor : {
			type : 'text'
		}
	}, {
		field : 'VALIDATE_FLAG',
		title : '启用标志',
		width : 100,
		formatter : function(id) {
			if (id == '1') {
				return '启用';
			} else if (id == '0') {
				return '停用';
			}
		},
		editor : {
			type : 'combobox',
			options : {
				editable : false,
				valueField : 'id',
				textField : 'text',
				data : [ {
					id : '1',
					text : '启用'
				}, {
					id : '0',
					text : '停用'
				} ]
			}
		}
	}, {
		field : 'PUB_ATTR',
		title : '公共属性标志',
		width : 100,
		formatter : function(id) {
			if (id == '1') {
				return '公共';
			} else if (id == '0') {
				return '部门';
			}
		},
		editor : {
			type : 'combobox',
			options : {
				editable : false,
				valueField : 'id',
				textField : 'text',
				data : [ {
					id : '1',
					text : '公共'
				}, {
					id : '0',
					text : '部门'
				} ]
			}
		}
	}, {
		field : 'COMMENTS',
		title : '备注',
		width : 100,
		halign : "center",
		align : "left",
		editor : {
			type : 'text'
		}
	}, {
		field : 'CREATOR',
		title : '创建者',
		width : 100
	}, {
		field : 'CREATE_TIME',
		title : '创建时间',
		width : 100,
		formatter : function(value, row, index) {
			if (value != undefined && value != null && value != "") {
				return dateFormatterYMD(value);
			} else {
				return value;
			}
		}
	}, {
		field : 'CREATOR_NAME',
		title : '创建者姓名',
		width : 100
	} ] ];
	return colums;
}

// 创建表头
function creatPostUserColums() {
	var colums = [ [ {
		field : 'DETAIL_ID',
		title : '明细ID',
		hidden : true
	}, {
		field : 'POST_ID',
		title : '岗位ID',
		hidden : true
	}, {
		field : 'POST_NAME',
		title : '岗位名称',
		halign : "center",
		align : "left",
		width : 100
	}, {
		field : 'USER_ID',
		title : '岗位ID',
		hidden : true
	}, {
		field : 'USER_CODE',
		title : '登陆名',
		halign : "center",
		align : "left",
		width : 100
	}, {
		field : 'USER_NAME',
		title : '用户',
		halign : "center",
		align : "left",
		width : 100
	}, {
		field : 'CREATOR',
		title : '创建者',
		width : 100
	}, {
		field : 'CREATE_TIME',
		title : '创建时间',
		width : 100,
		formatter : function(value, row, index) {
			if (value != undefined && value != null && value != "") {
				return dateFormatterYMD(value);
			} else {
				return value;
			}
		}
	}, {
		field : 'CREATOR_NAME',
		title : '创建者姓名',
		width : 100
	} ] ];
	return colums;
}

// 创建表头
function creatPostNoUserColums() {
	var colums = [ [{
		field : 'USER_ID',
		title : '用户ID',
		halign : "center",
		align : "center",
		width : 60,
		hidden : true
	}, {
		field : 'USER_CODE',
		title : '登陆名',
		halign : "center",
		align : "left",
		width : 80
	}, {
		field : 'USER_NAME',
		title : '用户',
		halign : "center",
		align : "left",
		width : 80
	} ] ];
	return colums;
}

// 编辑行
function onClickPostDict(rowIndex, rowData) {
	if (dictEditIndex != rowIndex && typeof (rowIndex) != NaN) {
		if (endEditingPostDict()) {
			$("#dgPostType").datagrid('selectRow', rowIndex).datagrid(
					'beginEdit', rowIndex);
			dictEditIndex = rowIndex;
		} else {
			$("#dgPostType").datagrid('selectRow', rowIndex);
		}
	}
}

// 工具栏
function toolBarPostType() {
	var tooBar = [ {
		text : '新增',
		iconCls : 'icon-add',
		handler : function() {
			appendPostDict();
		}
	}, '-', {
		text : '删除',
		iconCls : 'icon-remove',
		handler : function() {
			removePostDict();
		}
	}, '-', {
		text : '保存',
		iconCls : 'icon-save',
		handler : function() {
			savePostDict();
		}
	} ];
	return tooBar;
}

// 结束当前正在编辑的行，添加新行到结尾
function appendPostDict() {
	if (endEditingPostDict()) {
		$('#dgPostType').datagrid('appendRow', {});
		dictEditIndex = $('#dgPostType').datagrid('getRows').length - 1;
		$('#dgPostType').datagrid('selectRow', dictEditIndex).datagrid(
				'beginEdit', dictEditIndex);
	}
}

// 结束一行的编辑(只有岗位和人员可编辑，部门不能编辑)
function endEditingPostDict() {
	if (dictEditIndex == undefined) {
		return true;
	}
	// 指定生效行数据
	if ($('#dgPostType').datagrid('validateRow', dictEditIndex)) {
		// 刷新并结束编辑一行
		$('#dgPostType').datagrid('endEdit', dictEditIndex);
		dictEditIndex = undefined;
		return true;
	} else {
		return false;
	}
}

// 移去某行
function removePostDict() {
	if (dictEditIndex == undefined) {
		return;
	}
	// 取消编辑状态并移除
	$('#dgPostType').datagrid('cancelEdit', editIndex).datagrid('deleteRow',
			dictEditIndex);
	dictEditIndex = undefined;
}

// 保存修改
function savePostDict() {
	if (endEditingPostDict()) {
		// 准备提交
		if ($('#dgPostType').datagrid('getChanges').length) {
			// 分步提交修改的数据，通过主键的不重复，解决可能存在的操作次序
			var inserted = $('#dgPostType').datagrid('getChanges', "inserted");
			var deleted = $('#dgPostType').datagrid('getChanges', "deleted");
			var updated = $('#dgPostType').datagrid('getChanges', "updated");
			var checkmsg = checkdata(inserted,deleted,updated);
			if (checkmsg != true) {
				$.messager.alert('保存提示',checkmsg,'warning');
				return;
			}
			// 类似JSONTOSTRING，将对象数据转化为字符串，分隔符自定义(对象之间使用|分隔)
			var insertedString = "";
			for (var i = 0; i < inserted.length; i++) {
				if (i != 0) {
					insertedString += "|";
				}
				if (inserted[i].INFO_ID == undefined
						|| inserted[i].INFO_ID == null) {
					inserted[i].INFO_ID = "";
				}
				// 只打包需要的数据
				var package = {
					"POST_CODE" : inserted[i].POST_CODE,
					"POST_NAME" : inserted[i].POST_NAME,
					"VALIDATE_FLAG" : inserted[i].VALIDATE_FLAG,
					"PUB_ATTR" : inserted[i].PUB_ATTR,
					"COMMENTS" : inserted[i].COMMENTS
				};
				insertedString += jsonToString(package);
			}
			// 删除只取ID
			var deletedString = "";
			for (var i = 0; i < deleted.length; i++) {
				if (i) {
					deletedString += ",";
				}
				deletedString += deleted[i].POST_ID;
			}
			// 修改
			var updatedString = "";
			for (var i = 0; i < updated.length; i++) {
				if (i) {
					updatedString += "|";
				}
				if (updated[i].POST_CODE == undefined) {
					updated[i].POST_CODE = "";
				}
				// 只打包需要的数据
				var package = {
					"POST_ID" : updated[i].POST_ID,
					"POST_CODE" : updated[i].POST_CODE,
					"POST_NAME" : updated[i].POST_NAME,
					"VALIDATE_FLAG" : updated[i].VALIDATE_FLAG,
					"PUB_ATTR" : updated[i].PUB_ATTR,
					"COMMENTS" : updated[i].COMMENTS
				};
				updatedString += jsonToString(package);
			}

			// 传递json obj对象，对象的属性值用于识别
			var data = {
				"inserted" : insertedString,
				"deleted" : deletedString,
				"updated" : updatedString
			};

			$.messager.progress();
			$.ajax({
				type : "POST",
				url : action + "savePostDict",
				data : data,
				dataType : "json",
				success : function(data) {
					$.messager.progress('close');
					if (data.success) {
						$.messager.alert("提示", "提交成功！",'info');
					} else {
						$.messager.alert("提示", "提交失败！",'info');
					}
					queryPostDict();
				},
				error : function(data) {
					ajaxError(data, "提交失败");
				}
			});
			$('#dgPostType').datagrid('acceptChanges'); // 接受改变
			// 清除选中项
			$("#dgPostType").datagrid("clearSelections");
		}
	} else {
		$.messager.alert("提示", "结束编辑失败！",'info');
	}
}

function checkdata(inserted,deleted,updated) {
	for (var i = 0; i < inserted.length; i++) {
		if (inserted[i].POST_CODE == null||inserted[i].POST_CODE == "") {
			return "岗位代码不能为空";
		}
		if (inserted[i].POST_NAME == null||inserted[i].POST_NAME == "") {
			return "岗位名称不能为空";
		}
		if (inserted[i].VALIDATE_FLAG == null) {
			return "岗位是否启用不能为空";
		}
		if (inserted[i].PUB_ATTR == null) {
			return "是否公共岗位用不能为空";
		}
	}

	// 修改
	for (var i = 0; i < updated.length; i++) {
		if (updated[i].POST_CODE == null||updated[i].POST_CODE == "") {
			return "岗位代码不能为空";
		}
		if (updated[i].POST_NAME == null||updated[i].POST_NAME == "") {
			return "岗位名称不能为空";
		}
	}
	return true;
}

function checkdeptpost(inserted,updated) {
	for (var i = 0; i < inserted.length; i++) {
		if (inserted[i].POST_ID == null||inserted[i].POST_ID == "") {
			return "岗位不能为空";
		}
	}
	for (var i = 0; i < updated.length; i++) {
		if (updated[i].POST_ID == null||updated[i].POST_ID == "") {
			return "岗位不能为空";
		}
	}
	return true;
}