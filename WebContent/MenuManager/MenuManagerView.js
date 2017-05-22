/**
 * 描述：菜单管理，可以对菜单进行CRUD操作
 * 作者：李添
 * 时间：2016年7月25日11:45:24
 */
var actionUrl = "../MenuManager/action/MenuManagerAction.jspx?&op=";
//var addDialogId = "#addDialog";
var menuId = 0;
var menuName = undefined;//菜单名
var menuUrl = undefined;//菜单URL
var menuCode = undefined;//菜单代码
var CSMenuId = undefined;//父菜单ID(我们能得到的是String型的，到时候要转换为int型的)
var operator = undefined;//操作者
var operator_time = undefined;//操作时间
var modifier = undefined;//修改者
var modify_time = undefined;//修改时间
var menu_order = 0;//菜单排序

//dom准备就绪后执行
$(document).ready(function(){
	// 继承父级页面样式表
	superCss();
	initTreeGrid();//初始化菜单树
	initMenuTable();//初始化所有菜单的表
});
/**
 * 任务号：
 * 描述：初始化菜单树
 * 作者：李添
 * 时间：2016年7月28日13:14:48
 */
function initTreeGrid(){
	$.messager.progress({
		text:"正在加载，请稍等。。。"
	});
	
	$('#tt').tree({//菜单树,根据表sys_menu
		url : actionUrl + "getMenuTree",
		animate : true,//定义节点在展开或折叠的时候是否显示动画效果。
//		dnd : true,//定义是否启用拖拽功能
//		checkbox : true,//定义是否在每一个借点之前都显示复选框
		lines : true,//定义是否显示树控件上的虚线。
//		onClick : treeNodeOnClick,
		onLoadSuccess : function(node, data){//在数据加载成功以后触发
			$.messager.progress("close");
			if(data.length == 0){
				$('#tt').tree('isLeaf',node.target);//isLeaf是看这个节点是否是叶子节点
			}
			return true;
		},
		onLoadError:onLoadError,//在数据加载失败的时候触发，arguments参数和jQuery的$.ajax()函数里面的'error'回调函数的参数相同
		onContextMenu: function(e, node){//在右键点击节点的时候触发。
			e.preventDefault();// 查找节点
//			$('#tt').tree('select', node.target);
			menuId = node.id;//可以得到menuid
			menuName = node.text;
			menuCode = node.attributes.myHashMap.menuCode;
			menuUrl = node.attributes.myHashMap.menuUrl;
			CSMenuId = node.attributes.myHashMap.CSMenuId;
			operator = node.attributes.myHashMap.OPERATOR_NAME;
			operator_time = node.attributes.myHashMap.OPERATOR_TIME;
			modifier = node.attributes.myHashMap.MODIFIER;
			modify_time = node.attributes.myHashMap.MODIFY_TIME;
			menu_order = node.attributes.myHashMap.MENU_ORDER;
			/*
			 * 显示快捷菜单
			 */ 
//			if($('#tt').tree('isLeaf',node.target)){//是子节点就没有添加子菜单那一项功能
			if(menuUrl == null || menuUrl == ""){//如果url是null的话就可以添加子菜单
				$('#urlIsNull').menu('show', {
					left: e.pageX,
					top: e.pageY
				});
			}else{
				$('#urlIsNotNull').menu('show', {//如果url不是null的话就不可以添加子菜单
					left: e.pageX,
					top: e.pageY
				});
			}
		}
		
	});
}

/**
 * 任务号：
 * 描述：点击添加子菜单触发
 * 作者：李添
 * 时间：2016年7月28日13:17:02
 */
function append(){
	$("#addDialog").form('clear');
	$("#addDialog").dialog('open');
}

/**
 * 任务号：
 * 描述：确定添加子菜单触发的事件
 * 作者：李添
 * 时间：2016年7月28日13:18:23
 */
function addsave(){
	var menuName = $("#MENU_NAME").val();//菜单名
	var menuURL = $("#MENU_URL").val();//菜单URL
	var menuCode = $("#MENU_CODE").val();//菜单代码
	var menu_order = $("#MENU_ORDER").val();//菜单排序
	var data = {
			'MENU_NAME':menuName,
			'MENU_URL':menuURL,
			'MENU_CODE':menuCode,
			'MENU_ID':menuId,
			'MENU_ORDER':menu_order
	};
	$.ajax({
		type : 'POST',
		dataType : 'json',
		data : data,
		url : actionUrl + 'addMenu',
		success : function(){
			$.messager.progress('close');
			$('#addDialog').dialog('close');
			$.messager.alert("提示", "新增信息成功");
			initTreeGrid();
			initMenuTable();
			
		},
		error : function() {
			$.messager.progress('close');
			$.messager.alert('新增提示', '添加失败！', 'error');
		}
	});
}

/**
 * 任务号：
 * 描述：点击取消,关闭窗口
 */
function cancel(){
	$("#addDialog").dialog('close');
	$("#editDialog").dialog('close');
	$("#searchDialog").dialog('close');
	$("#showUser").dialog('close');
}

/**
 * 任务号：
 * 描述：修改之前发生的事情
 * 作者：李添
 * 时间：2016年7月28日16:04:36
 */
function edit(){
	$("#MENU_NAME1").val(menuName);
	$("#MENU_URL1").val(menuUrl);
	$("#MENU_CODE1").val(menuCode);
	$("#MENU_ORDER1").val(menu_order);
	var data = {
			'CSMenuId':CSMenuId
	};
	$("#C_S_MENU_ID1").combobox({//获取所有能当父亲的节点菜单,可修改一个作为自己的父亲
		url : actionUrl + "getisFather",
		valueField : 'MENU_ID',
		textField : 'MENU_NAME',
		value :data.CSMenuId,
		editable : false
	});
	$('input').css("background-color", "");
	$('#editDialog').dialog('open');
}

function editSave(){
	menuName = $("#MENU_NAME1").val();//菜单名
	menuURL = $("#MENU_URL1").val();//菜单URL
	menuCode = $("#MENU_CODE1").val();//菜单代码
	CSMenuId = $("#C_S_MENU_ID1").combobox('getValue');//父菜单
	var menu_order_old = menu_order;//记住改之前的menu_order_old
	menu_order = $("#MENU_ORDER1").val();//菜单排序
	var data = {
			'MENU_NAME':menuName,
			'MENU_URL':menuURL,
			'MENU_CODE':menuCode,
			'MENU_ID':menuId,
			'C_S_MENU_ID':CSMenuId,
			'OPERATOR_NAME':operator,
			'OPERATOR_TIME':operator_time,
			'MENU_ORDER':menu_order,
			'MENU_ORDER_OLD':menu_order_old
	};
	$.ajax({
		type : 'POST',
		dataType : 'json',
		data : data,
		url : actionUrl + 'editMenu',
		success : function(){
			$.messager.progress('close');
			$('#editDialog').dialog('close');
			$.messager.alert("提示", "修改信息成功");
			initTreeGrid();
			initMenuTable();
		},
		error : function() {
			$.messager.progress('close');
			$.messager.alert('提示', '修改信息失败！', 'error');
		}
	});
}

/**
 * 描述：删除菜单
 * 作者：李添
 * 时间：2016年7月29日17:27:19
 */
/**
 * 描述：点击删除弹出一个框框，上面显示对这个菜单有权限的人及其电话号码
 */
function delShowColumn(){
	var columns = [ [ {
		field : 'USER_ID',
		title : '用户id',
		width : 20
	},
	{
		field : 'USER_NAME',
		title : '用户名',
		width : 60
	},{
		field : 'PHONE_NUM',
		title : '联系方式',
		width : 80
	}]];
	return columns;
}
/**
 * 描述：点击删除按钮，首先把对这个菜单有权限的人的信息显示出来，(待要删除者联系确认好了之后再决定是否删除)
 * 作者：李添
 * 时间：2016年8月3日14:26:28
 */
function del(){
	$("#showUser").dialog('open');
	var data1 = {
			'MENU_CODE' : menuCode
		};
	$("#showUser1").datagrid({
		type : 'POST',
		dataType : 'json',
		fit : true,
		striped : false,
		singleSelect : true,
		fitColumns : true,
		pagination : true,
		pageSize : 10,
		scrollbarSize : 10,
		pageList : [ 10, 20, 100, 100 ],
		queryParams : data1,
		url : actionUrl + "findUserInfo",
		columns : delShowColumn(),
		success : function(data1) {
			$.messager.alert("提示", "加载列表成功");
		}  
	});
}
/**
 * 描述：要删除的人点击确认删除的时候，才删除这个菜单
 * 作者：李添
 * 时间：2016年8月3日14:27:36
 */
function deleteMenu(){
	$.messager.progress();
	var data = {
		'MENU_ID' : menuId,
		'C_S_MENU_ID' : CSMenuId,
		'MENU_ORDER' : menu_order
	};
	$.ajax({
		type : 'POST',
		dataType : 'json',
		data : data,
		url : actionUrl + 'removeMenu',
		success : function(data) {
			$.messager.progress('close');
			$.messager.alert("提示", "删除信息成功");
			$("#showUser").dialog('close');
			initTreeGrid();
			initMenuTable();
		},
		error : function(data) {
			$.messager.progress('close');
			$.messager.alert('删除提示', '删除失败！', 'error');
		}
	});
}

/**
 * 描述：查看菜单信息
 * 作者：李添
 * 时间：2016年7月29日18:33:53
 */
function search(){
	$("#MENU_ID2").text(menuId);
	$("#MENU_NAME2").text(menuName);
	$("#MENU_URL2").text(menuUrl);
	$("#MENU_CODE2").text(menuCode);
	$("#OPERATOR_NAME2").text(operator);
	$("#OPERATOR_TIME2").text(operator_time);
	$("#MODIFIER2").text(modifier);
	$("#MODIFY_TIME2").text(modify_time);
	$("#C_S_MENU_ID2").text(CSMenuId);
	$("#MENU_ORDER2").text(menu_order);
	
	$("#searchDialog").dialog('open');
}

/**
 * 任务：
 * 描述：清除输入框
 * 作者：李添
 * 时间：2016年7月28日16:40:03
 */
function clear() {
	$('#MENU_NAME').attr('value','');
	$('#MENU_URL').attr('value', '');
	$('#MENU_CODE').attr('value', '');
	$('#C_S_MENU_ID').attr('value', '');
	$('input').css("background-color", "");
}

/**
 * 这个方法是抄杨博申的SmsManager.js里的
 * @param e
 * @returns {Boolean}
 */
//不能输入空格
function banInputSapce(e) {
	var keynum;
	if (window.event) // IE
	{
		keynum = e.keyCode;
	} else if (e.which) // Netscape/Firefox/Opera
	{
		keynum = e.which;
	}
	if (keynum == 32) {
		return false;
	}
	return true;
}

function creatColumn() {
	var columns = [ [ {
		field : 'MENU_ID',
		title : '菜单id',
		width : 20
	},
	{
		field : 'MENU_NAME',
		title : '菜单名',
		width : 40
	},{
		field : 'MENU_URL',
		title : '菜单URL',
		width : 120
	}, 
	{
		field : 'MENU_CODE',
		title : '菜单代码',
		width : 40
	}, {
		field : 'OPERATOR_NAME',
		title : '操作者',
		width : 30
	}, {
		field : 'OPERATOR_TIME',
		title : '操作时间',
		width : 60
	}, {
		field : 'MODIFIER',
		title : '修改者',
		width : 30
	}, 
	{
		field : 'MODIFY_TIME',
		title : '修改时间',
		width : 60
	},{
		field : 'C_S_MENU_ID',
		title : '父菜单id',
		width : 20
	}, {
		field : 'MENU_ORDER',
		title : '菜单排序',
		width : 30
	} ] ];
	return columns;
}

function initMenuTable(){
	$("#menulist").datagrid({
		fit : true,
		striped : false,
		singleSelect : true,
		fitColumns : true,
		pagination : true,
		pageSize : 20,
		scrollbarSize : 20,
		pageList : [ 20, 50, 100, 200 ],
		url : actionUrl + "querySysMenu",
		columns : creatColumn(),
		success : function(json) {
			$.messager.alert("提示", "加载列表成功");
		}
	});
}


