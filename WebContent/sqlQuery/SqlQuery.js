/**
 * 任务描述：sql查询 创建人：石起 创建时间：2015年5月5日13:02:49 任务号：APPDAILYWORK-778
 */

// 全局变量
var actionUrl = "../SqlQuery/action/SqlQueryAction.jspx?loc=c&op=";
var operator = "#operatorId";
/**
 * 页面加载
 */
$(document).ready(function() {
	superCss();
});

function onblurSQL() {
	var sql = $('#sql').val().trim();
	if (sql == '') {
		$('#sql').val('请输入sql查询语句');
	}
}

function onfocusSQL() {
	var sql = $('#sql').val().trim();
	if (sql == '请输入sql查询语句') {
		$('#sql').val('');
	}
}

// 查询列
function queryColName() {
	var colNames;
	var sql = $('#sql').val().trim();// +" LIMIT 0,1";//sql
	if (sql == '' || sql == null) {
		$.messager.alert('查询提示', '请输入sql查询语句！', 'info');
		return;
	}
	if (sql.substr(0, 6).toUpperCase() != "SELECT") {
		$.messager.alert('查询提示', '该功能只支持sql查询！', 'info');
		return;
	}
	$.messager.progress();
	$.ajax({
		type : 'POST',
		dataType : 'json',
		timeout : 300000,
		data : {
			'sql' : sql
		},
		url : actionUrl + 'querySqlClo',
		success : function(data) {
			if (!pageForward(data)) {
				return;
			}
			if (data.success) {
				colNames = data.data.rows;
				creatColumn(colNames);
			} else {
				$.messager.progress('close');
				$.messager.alert('查询提示', data.message, 'warning');
			}
		},
		error : function(data) {
			$.messager.progress('close');
			$.messager.alert('查询提示', '查询失败,请检查sql语句是否正确！', 'error');
		}
	});
}

// 创建列
function creatColumn(colNames) {
	var columns = new Array();
	for (var i = 0; i < colNames.length; i++) {
		var column = {};
		column["title"] = colNames[i];
		column["field"] = colNames[i];
		column["width"] = 100;
		// column["formatter"]=checkCol;
		columns.push(column);// 当需要formatter的时候自己添加就可以了,原理就是拼接字符串.
	}
	onQuery(columns);
}

// 根据用户名、用户姓名或手机号查询
function onQuery(columns) {
	// 获取查询参数
	var sql = $('#sql').val().trim();
	// 查询数据
	$(operator).datagrid({
		fit : true,
		striped : true,
		rownumbers : true,
		singleSelect : true,
		timeout : 300000,
		toolbar : '#tb',
		// 定义参数
		queryParams : {
			"sql" : sql
		},
		url : actionUrl + "querySqlData",
		pagination : true,
		pageNumber : 1,
		pageSize : 20,
		pageList : [ 20, 50, 100 ],
		columns : [ columns ],
		onLoadSuccess : function(data) {
			pageForward(data);
		}
	});
	$.messager.progress('close');
}
