//全局变量
var actionUrl = "../PostManage/action/PostManageAction.jspx?op=";
var PostId = "#posts";
var PostText = "";
var selectIndex;
/**
 * 页面加载
 */
$(document).ready(function() {
	superCss();
	initPosts();
	westpanel();
	initGrant();
});
function  initGrant(){
	$.ajax({
		type : 'POST',
		dataType : 'json',
//		data : data,
		url : actionUrl + 'initGrant',
		success : function(data) {
			
			}
		});
}
// 创建列
function creatColumn() {
	var columns = [[{
		field : 'POST_ID',
		title : '岗位ID',
		width : 60,
		hidden : true
	}, {
		field : 'POST_CODE',
		title : '岗位代码',
		width : 60
	}, {
		field : 'POST_NAME',
		title : '岗位名称',
		width : 60
	}, {
		field : 'VALIDATE_FLAG',
		title : '是否启用',
		formatter : function(value) {
			if (value==1) {
				return '是';
			} else {
				return '否';
			}
		},
		width : 60
	}, {
		field : 'PUB_ATTR',
		title : '公共岗位',
		formatter : function(value) {
			if (value==1) {
				return '是';
			} else {
				return '否';
			}
		},
		width : 60
	}, {
		field : 'COMMENTS',
		title : '备注',
		width : 100
	}, {
		field : 'CREATOR',
		title : '创建者',
		width : 60
	}, {
		field : 'CREATOR_NAME',
		title : '创建者姓名',
		width : 60
	}, {
		field : 'CREATOR_TIME',
		title : '创建时间',
		width : 60
	}]];
	return columns;
}
// 初始加载角色管理信息
function initPosts(){
	$(PostId).datagrid({
		fit : true,
		striped : true,
		rownumbers : true,
		queryParams : {
			"PostText" : ''
		},
		singleSelect:true,
		frozenColumns : [ [ {
			field : 'ck',
			checkbox : true
		} ] ],
		toolbar:'#tb',
		url : actionUrl + "QueryPost",
		fitColumns : true,
		pagination : true,
		pageSize : 20,
		pageList : [20,50,100,200],
		columns : creatColumn(),
		onLoadSuccess:function(data){
			pageForward(data);
		}
	});
}

//根据岗位代码 或 岗位名称查询
function searchByPost(){
	var posttext = $('#search_posttext').val();
	$(PostId).datagrid({
		fit : true,
		striped : true,
		rownumbers : true,
		queryParams : {
			"PostText" : posttext
		},
		singleSelect:true,
		frozenColumns : [ [ {
			field : 'ck',
			checkbox : true
		} ] ],
		toolbar:'#tb',
		url : actionUrl + "QueryPost",
		fitColumns : true,
		pagination : true,
		pageSize : 20,
		pageList : [20,50,100,200],
		columns : creatColumn(),
		onLoadSuccess:function(data){
			pageForward(data);
		}
	});
}


// 取消
function cancel() {
	$('#addDialog').dialog('close');
	$('#updateDialog').dialog('close');
}
// 根据角色名称查询角色信息
function onQuery() {
	var posttext = $('#search_posttext').val();
	$('#cc').layout("collapse", "east");
//	PostText = posttext.replace(/\s+/g, "");
	PostText = posttext.replace(/\s/g, "");
//	initPosts();
	if (posttext == "") {
//		initRoles();
		initPosts();
		return;
	} else {
		searchByPost(posttext);
	}

}
// 新增角色
function add() {
	$('#addPostCode').val('');
	$('#addPostName').val('');
	$("#addValidateFlag2").attr({"checked" : "checked"});
	$("#addPubAttr1").attr({"checked" : "checked"});
	$('#addComments').val('');
	$('#addDialog').dialog('open');
}
// 修改角色
function update() {
	var row = $(PostId).datagrid('getSelected');
	if (row == undefined || row == null) {
		$.messager.alert("编辑提示", "请选择行进行编辑！", "warning");
		return;
	} else {
		$('#editPostCode').val(row.POST_CODE);
		$('#editPostName').val(row.POST_NAME);
		if (row.VALIDATE_FLAG == 1) {
			$("#editValidateFlag2").attr({"checked" : "checked"});
		} else {
			$("#editValidateFlag1").attr({"checked" : "checked"});
		}
		if (row.PUB_ATTR == 1) {
			$("#editPubAttr2").attr({"checked" : "checked"});
		} else {
			$("#editPubAttr1").attr({"checked" : "checked"});
		}
		$('#editComments').val(row.COMMENTS);
	}
	$('#updateDialog').dialog('open');
}
// 删除角色
function del() {
	var row = $(PostId).datagrid('getSelected');
	if (row == undefined || row == null) {
		$.messager.alert("删除提示", "请选择行进行删除！", "warning");
		return;
	} else {
		$.messager.confirm('删除提示', '确定删除该岗位吗？', function(r) {
			if (r) {
				$.messager.progress();
				var postId = row.POST_ID;
				var data = {
					"postId" : postId
				};
				$.ajax({
					type : 'POST',
					dataType : 'json',
					data : data,
					url : actionUrl + 'DeletePost',
					success : function(data) {
						if (!pageForward(data)) {
							return;
						}
						$.messager.progress('close');
						$(PostId).datagrid('reload', {});
						$('#cc').layout("collapse", "east");
						if (data.success) {
							$.messager.alert('删除提示', data.message, 'info');
						} else {
							$.messager.alert('删除提示', '删除失败！', 'warning');
						}
					},
					error : function(data) {
						$.messager.progress('close');
						$.messager.alert('删除提示', '删除失败！', 'error');
					}
				});
			}
		});
	}
}
// 校验新增角色输入是否为空
function savecheck() {
	var addmsg = addCheck();
	if (addmsg != true) {
		$.messager.alert('新增岗位',addmsg,'warning');
		return;
	}
	saveadd();
}
// 新增角色
function saveadd() {
	$.messager.progress();
	var postCode = $('#addPostCode').val();
	var postName = $('#addPostName').val();
	var validateFlag = $("input[name='ValidateFlag']:checked").val();
	var pubAttr = $("input[name='PubAttr']:checked").val();
	var comments = $('#addComments').val();
	var postInfo = {
		'POST_CODE' : postCode,
		'POST_NAME' : postName,
		'VALIDATE_FLAG' : validateFlag,
		'PUB_ATTR' : pubAttr,
		'COMMENTS' : comments
	};
	var data = {"postInfo":jsonToString(postInfo)};
	$.ajax({
		type : 'POST',
		dataType : 'json',
		data : data,
		url : actionUrl + 'AddPost',
		success : function(data) {
			if (!pageForward(data)) {
				return;
			}
			$.messager.progress('close');
			$('#addDialog').dialog('close');
			$(PostId).datagrid('reload', {});
			$('#cc').layout("collapse", "east");
			if (data.success) {
				$.messager.alert('新增提示', data.message, 'info');
			} else {
				$.messager.alert('新增提示', '新增失败！', 'warning');
			}
		},
		error : function(data) {
			$.messager.progress('close');
			$.messager.alert('新增提示', '新增失败！', 'error');
		}
	});
}

// 根据查询条件重新加载页面
function surchConfig(rolename) {
	$(PostId).datagrid({
		fit : true,
		striped : true,
		rownumbers : true,
		queryParams : {
			"roleName" : rolename
		},
		url : actionUrl + "queryByRoleName",
		fitColumns : true,
		columns : creatColumn(),
		onLoadSuccess : function(data) {
			pageForward(data);
		}
	});
}
// 修改角色
function updatesave() {
	var upmsg = updateCheck();
	if (upmsg != true) {
		$.messager.alert('修改提示',upmsg,'warning');
		return;
	}
	$.messager.progress();
	var postId = $(PostId).datagrid('getSelected').POST_ID;
	var postCode = $('#editPostCode').val();
	var postName = $('#editPostName').val();
	var validateFlag = $("input[name='ValidateFlags']:checked").val();
	var pubAttr = $("input[name='PubAttrs']:checked").val();
	var comments = $('#editComments').val();
	var postInfo = {
		'POST_ID' : postId,
		'POST_CODE' : postCode,
		'POST_NAME' : postName,
		'VALIDATE_FLAG' : validateFlag,
		'PUB_ATTR' : pubAttr,
		'COMMENTS' : comments
	};
	var data = {"postInfo":jsonToString(postInfo)};
	$.ajax({
		type : 'POST',
		dataType : 'json',
		data : data,
		url : actionUrl + 'UpdatePost',
		success : function(data) {
			if (!pageForward(data)) {
				return;
			}
			$.messager.progress('close');
			$('#updateDialog').dialog('close');
			$(PostId).datagrid('reload', {});
			$('#cc').layout("collapse", "east");
			if (data.success) {
				$.messager.alert('修改提示', data.message, 'info');
			} else {
				$.messager.alert('修改提示', '修改失败！', 'warning');
			}
		},
		error : function(data) {
			$.messager.progress('close');
			$.messager.alert('修改提示', '修改失败！', 'error');
		}
	});
}
// 授权
function grant() {
	var row = $(PostId).datagrid('getSelected');
	if (row == undefined || row == null) {
		$.messager.alert("授权提示", "请选择岗位进行授权！", "warning");
		return ;
	} else {
		$('#cc').layout('expand', 'east');
	}

}
// 保存授权
function grantsave() {
	$.messager.progress();
	var row = $(PostId).datagrid('getSelected');
	var postId = row.POST_ID;
	var cnode = GetNode();
	var data = {
		"postId" : postId,
		"menuCode" : cnode
	};
	$.ajax({
		type : 'POST',
		dataType : 'json',
		data : data,
		url : actionUrl + 'SaveGrant',
		success : function(data) {
			if (!pageForward(data)) {
				return;
			}
			$.messager.progress('close');
			if (data.success) {
				$.messager.alert('赋权提示', data.message, 'info');
			} else {
				$.messager.alert('赋权提示', '赋权失败！', 'warning');
			}
		},
		error : function(data) {
			$.messager.progress('close');
			$.messager.alert('赋权提示', '赋权失败！', 'error');
		}
	});

}
// 找到选中的节点
function GetNode() {
	var nodes = "";
	var node = $('#tt').tree('getChecked');
	for (var i = 0; i < node.length; i++) {
		if (nodes.indexOf(node[i].attributes + ',') == -1) {
			if (i == 0) {
				nodes += ',' + node[i].attributes + ',';
			} else {
				nodes += node[i].attributes + ',';
			}

		}
		nodes = getAllParentCode(node[i], nodes);
	}
	return nodes = nodes.substring(1, nodes.length);
}
/**
 * 任务： QUYIYUANSHANGYONGXIANCHANG-119 描述： 拿到所有的节点 作者：党智康 时间：2014年12月2日下午9:04:43
 * 
 * @param node
 * @param nodes
 * @returns
 */
function getAllParentCode(node, nodes) {
	if (node.attributes == 'root') {
		return nodes;
	}
	var pnode = $('#tt').tree('getParent', node.target);
	if (node.attributes == 'root') {
		return nodes;
	} else {
		if (nodes.indexOf(',' + pnode.attributes + ',') == -1) {
			nodes += pnode.attributes + ',';
		}
		nodes = getAllParentCode(pnode, nodes);
		return nodes;
	}
}
// 新增操作，必须项目check
function addCheck() {
	var message = '';
	var postCode = $('#addPostCode').val();
	var postName = $('#addPostName').val();
	if ( postCode == "") {
		message = "岗位代码不能为空！";
		return message;
	} else if(postName == "") {
		message = "岗位名称不能为空！";
		return message;
	}
	return true;
}
// 修改操作，必须项目check
function updateCheck() {
	var message = '';
	var postCode = $('#editPostCode').val();
	var postName = $('#editPostName').val();
	if ( postCode == "") {
		message = "岗位代码不能为空！";
		return message;
	} else if(postName == "") {
		message = "岗位名称不能为空！";
		return message;
	}
	return true;
}
// 不能输入空格
function banInputSapce(e) {
	var keynum;
	if (window.event) { // IE
		keynum = e.keyCode;
	} else if (e.which) { // Netscape/Firefox/Opera
		keynum = e.which;
	}
	if (keynum == 32) {
		return false;
	}
	return true;
}


/**
 * 任务号：KYEEAPPMAINTENANCE-45
 * 描述：
 * 作者：liuxingping
 * 时间：2015年1月17日下午6:32:56
 */
function westpanel(){
	$('#east').panel({
		"onBeforeExpand" : function() {
			var row = $(PostId).datagrid('getSelected');
			if (row == undefined || row == null) {
				$.messager.alert("授权提示", "请选择岗位进行授权！", "warning");
				return false;
			} else {
				var postId = row.POST_ID;
				$.messager.progress();
				$('#tt').tree({
					cascadeCheck : true,
					lines : true,
					url : actionUrl + "QueryGrant",
					onBeforeLoad : function(node, param) {
						param.postId = postId;
					},
					onLoadSuccess : function(node, data) {
						$.messager.progress('close');
					},
					onLoadError : function(data) {
						$.messager.progress('close');
						$.messager.alert('授权提示',data.message,'warning');
					}
				});
				return true;
			}
		}
	});
}