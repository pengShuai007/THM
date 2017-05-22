//全局变量
var actionUrl="../operator/action/OperatorLoginActionC.jspx?op=";
var action = "../systemLogMonitor/action/SystemLogMonitorActionC.jspx?op=";
var operator="#operatorId";
var SelectUserCode = "";
/**
 * 页面加载
 */
$(document).ready(function(){
	superCss();
	initconfig();
	initCombobox();
	
});

//创建列
function creatColumn(){
	var columns=[[{
		field:'USER_CODE',
		title:'登陆名',
		width:60
	},{
		field:'USER_NAME',
		title:'用户名称',
		width:60
	},{
		field:'PHONE_NUM',
		title:'手机号码',
		width:60
	},{
		field:'EMAIL',
		title:'邮箱',
		width:60
	}/*,{
		field:'JOB_ID',
		title:'工号',
		width:60
	}*/,{
		field:'IS_PROTECTED',
		title:'是否保护',
		width:60
	},
	{
		field : 'USER_ID',
		title : '用户权限',
		formatter : function(value,row,index) {
			return '<a href="#" onclick="queryAllGrant(\'' +row.USER_CODE+ '\')">查看权限</a>';
		},
		width:60
	}]];
	return columns;
}
//初始化加载
function initconfig(){
	$(operator).datagrid({
		fit : true,
		striped : true,
		rownumbers : true,
		singleSelect:true,
		frozenColumns : [ [ {
			field : 'ck',
			checkbox : true
		} ] ],
		toolbar:'#tb',
		url : actionUrl + "queryoperator",
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
//依据用户名查询
function onQuery(){
	//检查参数是否为空
	var nme=$('#search_names').val();
	var code=$('#search_code').val();
//	var role=$('#search_role').val();
	var postId =$('#search_post').combobox('getValue');
	code = code.replace(/\s+/g,"");
	nme=nme.replace(/\s+/g,"");
	postId = postId.replace(/\s+/g,"");
	if(nme==""&&code==""&&postId==""){
		initconfig();
		return;
	}else{
		surch();
	}	
}
//根据用户名查询
function surch(){
	$.messager.progress();
	var code=$('#search_code').val();
	var nme=$('#search_names').val();
//	var role=$('#search_role').val();
	var postId=$('#search_post').combobox('getValue');
	//var role=$('#search_role').combobox('getvalue');
	$(operator).datagrid({
		fit : true,
		striped : true,
		rownumbers : true,
		singleSelect:true,
		queryParams : {
//			"OPERATOR_NAME" : nme
			"POST_ID" :postId,
			"USER_CODE" : code,
			"USER_NAME" : nme
		},
		url : actionUrl + "queryOperatorByname",
		fitColumns : true,
		columns : creatColumn(),
		onLoadSuccess:function(data){pageForward(data);}
	});
	$.messager.progress('close');
}

//修改之前
function update(){
	var row = $(operator).datagrid('getSelected');
	if(row == undefined||row==null){
		$.messager.alert("编辑提示","请选择行进行编辑！","warning");
		return;
	}else{
		$('#operatorId').val(row.USER_ID);
		$('#usercodeIdp').val(row.USER_CODE);
		$('#operatorNamep').val(row.USER_NAME);
		$('#PHONE_NUM1').val(row.PHONE_NUM);
		$('#EMAIL1').val(row.EMAIL);
		//$('#JOB_ID1').val(row.JOB_ID);
		$('#isProtected1').combobox('setValue',row.IS_PROTECTED);
	}	
	$('input').css("background-color", "");
	$('#updateDialog').dialog('open');
}
//增加之前
function add(){
	clear();
	$('#addDialog').dialog('open');
}

function clear(){
	$('#usercodeName').attr('value','');
	$('#operatorName').attr('value','');
	$('#pwd').attr('value','');
	$('#newpwd').attr('value','');
	$('#PHONE_NUM').attr('value','');
	$('#EMAIL').attr('value','');
	//$('#JOB_ID').attr('value','');
	$('#isProtected').attr('value','1');
	$('input').css("background-color", "");
}
//保存新增
function save(){
	if(addCheckUsercode()==false){
		return;
	}
	//Edit start liuxingping 
	if(addCheck()==false){
		return;
	}
	$.messager.progress();
	var usercCode=$('#usercodeId').val();
	var operatorName=$('#operatorName').val();
	var oldpwd=$('#pwd').val();
	var pwd=$('#newpwd').val();
	var PHONE_NUM=$('#PHONE_NUM').val();
	var EMAIL=$('#EMAIL').val();
	//var JOB_ID=$('#JOB_ID').val();
	var isProtected=$('#isProtected').combobox('getValue');
	if(oldpwd==pwd){
		var data = {'USER_NAME':operatorName,
					'PASS_WORD':pwd,
					"USER_CODE":usercCode,
					"PHONE_NUM":PHONE_NUM,
					"EMAIL":EMAIL,
					//"JOB_ID":JOB_ID,
					"IS_PROTECTED":isProtected};
		var result={"postdata":jsonToString(data)};
		$.ajax({
			type : 'POST',
			dataType : 'json',
			data : result,
			url : actionUrl + 'saveoperator',
			success : function(data) {
				if (!pageForward(data)) {
					return;
				}
				$.messager.progress('close');
				$('#addDialog').dialog('close');
				$(operator).datagrid('reload', {});
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
	else{
		$.messager.alert('检查提示','两次输入的密码不同，请重新输入！','warning');
		$.messager.progress('close');
		return ;
	}
	//Edit end liuxingping 
}
//修改用户
function updatesave(){
	if(updateCheck()==false){
		return;
	}
	$.messager.progress();
//	var roleId = $('#rolep').combobox('getValue');
	var operatorCode=$('#usercodeIdp').val();
	var operatorName=$('#operatorNamep').val();
	var operatorId=$('#operatorId').val();
	var PHONE_NUM=$('#PHONE_NUM1').val();
	var EMAIL=$('#EMAIL1').val();
	//var JOB_ID=$('#JOB_ID1').val();
	var isProtected=$('#isProtected1').combobox('getValue');
	var data = {
		'USER_NAME' : operatorName,
		'USER_ID' : operatorId,
		'USER_CODE' : operatorCode,
		'PHONE_NUM' : PHONE_NUM,
		'EMAIL' : EMAIL,
		//'JOB_ID' : JOB_ID,
		'IS_PROTECTED' : isProtected
		};
	var result={"postdata":jsonToString(data)};
	$.ajax({
		type : 'POST',
		dataType : 'json',
		data : result,
		url : actionUrl + 'changeOperator',
		success : function(data) {
			if (!pageForward(data)) {
				return;
			}
			$.messager.progress('close');
			$('#updateDialog').dialog('close');
			$(operator).datagrid('reload', {});
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
//删除用户
function del() {
	var row = $(operator).datagrid('getSelected');
	if (row == undefined || row == null) {
		$.messager.alert("编辑提示", "请选择行进行编辑！", "warning");
		return;
	} else {
		$.messager.confirm('删除提示', '确定删除该条记录吗？', function(r) {
			if (r) {
				$.messager.progress();
				var operatorId = row.USER_ID;
				var data = {
					"USER_ID" : operatorId
				};
				$.ajax({
					type : 'POST',
					dataType : 'json',
					data : data,
					url : actionUrl + 'deleteOperator',
					success : function(data) {
						if (!pageForward(data)) {
							return;
						}
						$.messager.progress('close');
						$(operator).datagrid('reload', {});
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
//取消
function cancel(){
	$('#addDialog').dialog('close');
	$('#updateDialog').dialog('close');
}

//校验是否为电话号码
function regIsPhoneNum(Data) {
	var reg = new RegExp("^[1][3-8]+\\d{9}");
	return reg.test(Data);
}

//校验是否为邮箱
function regIsEmail(Data) {
	var reg = new RegExp("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$");
	return reg.test(Data);
}
function regIsJobId(Data) {
	var reg = new RegExp("^\\d{5}$");
	return reg.test(Data);
}
function regIsProtected(Data) {
	var reg = new RegExp("^[0|1]$");
	return reg.test(Data);
}

/**
 * 描述：新增操作前先判断这个用户名是否存在
 */
function addCheckUsercode(){
	var result = '';
	var userCode=$('#usercodeId').val();
	var operatorCode = $('#usercodeId').val();
	var data={"USER_CODE":operatorCode};
	$.ajax({
		type : 'POST',
		dataType : 'json',
		async : false,
		data : data,
		url : actionUrl + 'checkUserCode',
		success : function(data) {
			if(data.data > 0){//表示该用户已存在
				$('#usercodeId').css("background-color", "#FFE4E1");
				$.messager.alert('提示信息', "对不起，该用户已存在！", 'info');
				result = false;
			}else{
				$('#usercodeId').css("background-color", "");
				result = true;
			}
		}
	});
	return result;
}

//新增操作，必须项目check
function addCheck(){
	var message='';
	var operatorCode = $('#usercodeId').val();
	if(operatorCode==''){
		$('#usercodeId').css("background-color", "#FFE4E1");
		message=message + '<div style="margin-left: 50px;"><font color="red">用户代码是必须输入项目！</font><br></div>';
	}else{
		$('#usercodeId').css("background-color", "");
	}
	
	var operatorName = $('#operatorName').val();
	if(operatorName==''){
		$('#operatorName').css("background-color", "#FFE4E1");
		message=message + '<div style="margin-left: 50px;"><font color="red">用户名称是必须输入项目！</font><br></div>';
	}else{
		$('#operatorName').css("background-color", "");
	}
	var pwd = $('#pwd').val();
	if(checkPwd(pwd)== false){
		$('#pwd').css("background-color", "#FFE4E1");
		message=message + '<div style="margin-left: 50px;"><font color="red">新密码必须由 8-16位字母、数字、特殊符号+-_/组成！</font><br></div>';
	}
	if(pwd==''){
		$('#pwd').css("background-color", "#FFE4E1");
		message=message + '<div style="margin-left: 50px;"><font color="red">用户密码是必须输入项目！</font><br></div>';
	}else{
		newpwd = $('#newpwd').val();
		if(pwd!=newpwd){
			$('#newpwd').css("background-color", "#FFE4E1");
			message=message + '<div style="margin-left: 50px;"><font color="red">两次输入的密码不一致，请重新输入！</font><br></div>';
			$('#pwd').css("background-color", "#FFE4E1");
			$('#newpwd').css("background-color","#FFE4E1");
		}else{
			$('#pwd').css("background-color", "");
			$('#newpwd').css("background-color","");
		}	
	}
	var PHONE_NUM = $('#PHONE_NUM').val();
	if(!regIsPhoneNum(Number(PHONE_NUM))){
		$('#PHONE_NUM').css("background-color", "#FFE4E1");
		message=message + '<div style="margin-left: 50px;"><font color="red">请输入正确的手机号码！</font><br></div>';
	}else{
		$('#PHONE_NUM').css("background-color", "");
	}
	var EMAIL = $('#EMAIL').val();
	if(!regIsEmail(EMAIL)){
		$('#EMAIL').css("background-color", "#FFE4E1");
		message=message + '<div style="margin-left: 50px;"><font color="red">请输入正确的邮箱格式！</font><br></div>';
	}else{
		$('#EMAIL').css("background-color", "");
	}
/*	var JOB_ID = $('#JOB_ID').val();
	if(!regIsJobId(JOB_ID)){
		$('#JOB_ID').css("background-color", "#FFE4E1");
		message=message + '<div style="margin-left: 50px;"><font color="red">请输入正确的工号格式！</font><br></div>';
	}else{
		$('#JOB_ID').css("background-color", "");
	}*/
	var isProtected = $('#isProtected').val();
	if(!regIsProtected(isProtected)){
		$('#isProtected').css("background-color", "#FFE4E1");
		message=message + '<div style="margin-left: 50px;"><font color="red">请输入正确的保护格式！</font><br></div>';
	}else{
		$('#isProtected').css("background-color", "");
	}
	if (message==''){
		return true;
	} else {
		$.messager.alert('提示信息', message, 'info');
		return false;
	}
}
//修改操作，必须项目check
function updateCheck(){
	var message='';
	var operatorCode = $('#usercodeIdp').val();
	if(operatorCode==''){
		$('#usercodeIdp').css("background-color", "#FFE4E1");
		message=message + '<div style="margin-left: 50px;"><font color="red">用户代码是必须输入项目！</font><br></div>';
	}else{
		$('#usercodeIdp').css("background-color", "");
	}
	var operatorName = $('#operatorNamep').val();
	if(operatorName==''){
		$('#operatorNamep').css("background-color", "#FFE4E1");
		message=message + '<div style="margin-left: 50px;"><font color="red">用户名称是必须输入项目！</font><br></div>';
	}else{
		$('#operatorNamep').css("background-color", "");
	}
	var PHONE_NUM = $('#PHONE_NUM1').val();
	if(!regIsPhoneNum(Number(PHONE_NUM))){
		$('#PHONE_NUM').css("background-color", "#FFE4E1");
		message=message + '<div style="margin-left: 50px;"><font color="red">请输入正确的手机号码！</font><br></div>';
	}else{
		$('#PHONE_NUM').css("background-color", "");
	}
	var EMAIL = $('#EMAIL1').val();
	if(!regIsEmail(EMAIL)){
		$('#EMAIL').css("background-color", "#FFE4E1");
		message=message + '<div style="margin-left: 50px;"><font color="red">请输入正确的邮箱格式！</font><br></div>';
	}else{
		$('#EMAIL').css("background-color", "");
	}
	/*var JOB_ID = $('#JOB_ID1').val();
	if(!regIsJobId(JOB_ID)){
		$('#JOB_ID').css("background-color", "#FFE4E1");
		message=message + '<div style="margin-left: 50px;"><font color="red">请输入正确的工号格式！</font><br></div>';
	}else{
		$('#JOB_ID').css("background-color", "");
	}*/
	var isProtected = $('#isProtected1').val();
	if(!regIsProtected(isProtected)){
		$('#isProtected1').css("background-color", "#FFE4E1");
		message=message + '<div style="margin-left: 50px;"><font color="red">请输入正确的保护格式！</font><br></div>';
	}else{
		$('#isProtected1').css("background-color", "");
	}
	if (message==''){
		return true;
	} else {
		$.messager.alert('提示信息', message, 'info');
		return false;
	}
}
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
	if (keynum== 32) {
		return false;
	}
	return true;
}

function initCombobox() {
	$('#search_post').combobox({
		url : actionUrl + "QueryPost",
		valueField : "POST_ID",
		textField : "POST_NAME",
		editable : true
	});
}

function queryAllGrant(value) {
	SelectUserCode = value;
	$('#showUserAllPerm').window('open');
	$('#scanPermTabs').tabs('select', 0);
	//SubEventSelect(value);
}

function tabOnWindowSelect() {
	var tab = $('#scanPermTabs').tabs('getSelected');
	var index = $('#scanPermTabs').tabs('getTabIndex', tab);//获取指定选项卡面板的索引
	if (index == 0)
		initOperationPerm();//模块权限
	else if (index == 1)
		initUserPost();//岗位权限
	else if (index == 2)
		SubEventSelect();//系统订阅
}

function initOperationPerm() {//模块权限
	$.messager.progress();
	$('#UserOperation').tree({
		lines : true,//定义是否显示树控件上的虚线
		url : actionUrl + "QueryOperation",
		onBeforeLoad : function(node, param) {//在请求加载远程数据之前触发，返回false可以取消加载操作
			param.UserCode = SelectUserCode;
		},
		onLoadSuccess : function(node, data) {//在数据加载成功以后触发
			$.messager.progress('close');
		},
		onLoadError : function(data) {//在数据加载失败的时候触发，data参数和jQuery的$.ajax()函数里面的'error'回调函数的参数相同
			$.messager.progress('close');
			$.messager.alert('查询提示','查询失败','error');
		}
	});
}

function initUserPost() {//岗位权限
	$.messager.progress();
	$('#UserPost').tree({
		lines : true,
		url : actionUrl + "QueryUserPost",
		onBeforeLoad : function(node, param) {
			param.UserCode = SelectUserCode;
		},
		onLoadSuccess : function(node, data) {
			$.messager.progress('close');
		},
		onLoadError : function(data) {
			$.messager.progress('close');
			$.messager.alert('查询提示','查询失败','error');
		}
	});
}

function resetPassWord() {
	var row = $(operator).datagrid('getSelected');
	if (row == undefined||row==null) {
		$.messager.alert("重置密码提示","请选择用户进行重置密码！","warning");
		return;
	} else {
		$.messager.progress();
		var userCode = row.USER_CODE;
		$.ajax({
			type : "POST",
			url : actionUrl + "ResetPassWord",
			dataType : "json",
			data : {"userCode" : userCode},
			success : function(data) {
				$.messager.progress('close');
				if(data.success) {
					$.messager.alert('重置密码提示','重置成功','info');
				} else {
					$.messager.alert('重置密码提示','重置失败','error');
				}
			},
			error : function(data) {
				$.messager.progress('close');
				$.messager.alert('重置密码提示','重置失败','error');
			}
		});
	}
}
function SubEventSelect() {//系统订阅
	 var obj = {
			'USER_CODE':SelectUserCode
	};
	$("#SuEvent").datagrid({
		fit : true,
		nowrap : true,
		autoRowHeight : true,
		rownumbers : true,
		singleSelect:true,
		//striped : true,
		title : "订阅的系统事件",
		columns : creatEventColums(),
		queryParams : obj,
		url : action + "getSubEvent",
	});
	
}
function creatEventColums() {
	var colums = [ [ {
		field : 'EVENT_TYPE',
		title : '系统类别',
		formatter: function(value){
			if(value = 's')
			{
				return '系统';
			}
			else{
				return '应用';
			}
		},
		width : 80,
	},{
		field : 'EVENT_NAME',
		title : '系统事件名称',
		width : 300
	} ,
	{
		field : 'DICT_ID',
		title : '系统事件ID',
		width : 300,
		hidden: true
	}] ];
	return colums;
}

/**
 * 
* <pre>
* 任务:
* 描述:校验口令强度
* 作者:jujin
* 日期:2015年8月04日下午5:02:04
* @param obj
* @returns {Boolean}
* </pre>
 */
function checkPwd(obj){
	debugger;
	var reg = /^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[_+-/]).{8,16}$/;
	var flag = reg.test(obj);
	if(flag == false){  
		$.messager.alert("提示","新密码必须由 8-16位字母、数字、特殊符号_+-/组成.","warning");      
		return false;
	}
}