/**
 * 全局变量
 */
// grid id
var gridId = "#gd";
var winAddId = "#window_add";
var addFormId = "#addform";
var winEditId = "#window_edit";
var editFormId = "#editform";
var action = "UserLogHisHandler.jspx?op=";
var selectedRow;
var isExpandAll = false;
// dom准备就绪后执行
$(document).ready(function() {
	initGrid();
	// alert("KO")
});

/**
 * 初始化grid
 */
function initGrid() {
	$(gridId).datagrid({
		fit : true,
		nowrap : true,
		autoRowHeight : true,
		striped : true,
		collapsible : true,
		// url: action + "getUserLoginList",
		sortName : 'USER_ID',
		sortOrder : 'DESC',
		remoteSort : true,
		idField : 'USER_ID',
		singleSelect : true,
		columns : creatColums(),
		pagination : true,
		rownumbers : true,
		onLoadError : onLoadError, // iframe-common.js已定义
		onLoadSuccess : onLoadSuccess
	// iframe-common.js已定义
	});
}

/**
 * 创建表头
 * 
 * @returns {Array}
 */
function creatColums() {
	var colums = [ [ {
		field : 'FileName',
		title : '文件名',
		width : 350,
		sortable : true
	}, {
		field : 'File_List',
		title : '文件操作',
		width : 350,
		sortable : true
	} ] ];
	return colums;
}