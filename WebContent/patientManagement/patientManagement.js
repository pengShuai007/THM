//全局变量
var actionUrl="../patientManagement/action/PatientManagementAction.jspx?op=";
var patientId="#patientId";

/**
 * 页面加载
 */
$(document).ready(function(){
	superCss();
	var date = new Date();
	$('#beginTime').datebox('setValue',formatterDate(date));
	init();
});

//时间类型转换
function formatterDate(date) {
	var day = date.getDate() > 9 ? date.getDate() : "0" + date.getDate();
	var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0"
			+ (date.getMonth() + 1);
	return date.getFullYear() + '-' + month + '-' + day;
};

function init(){
	onQuery();
}

//根据用户名查询
function onQuery() {
	$.messager.progress();
	var name = $('#patientName').val();
	var status = $('#status').combobox('getValue');
	var beginTime = $('#beginTime').datebox('getValue');
	$(patientId).datagrid({
		fit : true,
		striped : true,
		rownumbers : true,
		singleSelect : true,
		queryParams : {
			"PATIENT_NAME" : name,
			"STATUS" : status,
			"BEGIN_TIME" : beginTime
		},
		toolbar:'#tb',
		url : actionUrl + "queryPatientByCondition",
		columns : creatColumn(),
		fitColumns : true,
		pagination : true,
		pageSize : 20,
		pageList : [20,50,100,200],
		onLoadSuccess : function(data) {
			pageForward(data);
		}
	});
	$.messager.progress('close');
}

//创建列
function creatColumn(){
	var columns=[[/*{
		field:'ID',
		title:'编号',
		width:60
	},*/{
		field:'PATIENT_NAME',
		title:'姓名',
		width:60
	},{
		field:'PATIENT_GENDER',
		title:'性别',
		formatter : function(value,row,index) {
			if(value == 0){
				return '女';
			}else{
				return '男';
			}
		},
		width:60
	},{
		field:'PATIENT_AGE',
		title:'年龄',
		width:60
	},{
		field:'PATIENT_PHONE',
		title:'电话',
		width:60
	},{
		field:'STATUS',
		title:'状态',
		formatter : function(value,row,index) {
			if(value == 0){
				return '等待';
			}else if(value == 1){
				return '治疗中';
			}else{
				return '完成';
			}
		},
		width:60
	},{
		field:'IS_VISIT',
		title:'是否复诊',
		formatter : function(value,row,index) {
			if(value == 0){
				return '初诊';
			}else{
				return '复诊';
			}
		},
		width:60
	},{
		field:'VISIT_DATE',
		title:'就诊日期',
		formatter : function(value,row,index) {
			return value.substring(0,10);
		},
		width:60
	},
	{
		field : 'REMARK',
		title : '备注',
		width:60
	},
	{
		field : 'CREATETIME',
		title : '创建时间',
		width:60
	}]];
	return columns;
}

//增加之前
function add(){
	clear();
	$('#addDialog').dialog('open');
}

function clear(){
	$('#userName').val('');
	$('#patientAge').val('');
	$('#phone').val('');
	$('#remark').val('');
	$('input').css("background-color", "");
}
//保存新增
function save(){
	if(check()==false){
		return;
	}
	$.messager.progress();
	var userName=$('#userName').val();
	var gender=$('#gender').combogrid("getValue");
	var patientAge=$('#patientAge').val();
	var phone=$('#phone').val();
	var visit_date = $('#visit_date').datebox('getValue');
	var remark=$('#remark').val();
	var data = {
			'PATIENT_NAME':userName,
			'PATIENT_GENDER':gender,
			"PATIENT_AGE":patientAge,
			"PATIENT_PHONE":phone,
			"VISIT_DATE":visit_date,
			"REMARK":remark
	};
	var result={
			"postdata":jsonToString(data)
	};
	$.ajax({
		type : 'POST',
		dataType : 'json',
		data : result,
		url : actionUrl + 'savePatient',
		success : function(data) {
			if (!pageForward(data)) {
				return;
			}
			$.messager.progress('close');
			$('#addDialog').dialog('close');
			if (data.success) {
				$.messager.alert('新增提示', data.message, 'info');
				onQuery(); 
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

//复诊
function visit(){
	var row = $(patientId).datagrid('getSelected');
	if(row == undefined||row==null){
		$.messager.alert("编辑提示","请选择行进行编辑！","warning");
		return;
	}else{
		var id = row.ID;
		$.ajax({
			type : 'POST',
			dataType : 'json',
			data : {
				'PATIENT_ID':id
			},
			url : actionUrl + 'visit',
			success : function(data) {
				$.messager.progress('close');
				if (data.success) {
					$.messager.alert('复诊提示', data.message, 'info');
					onQuery(); 
				} else {
					$.messager.alert('复诊提示', '复诊失败！', 'warning');
				}
			},
			error : function(data) {
				$.messager.progress('close');
				$.messager.alert('复诊提示', '复诊失败！', 'error');
			}
		});
	}	
}
//修改用户
/*function updatesave(){
	var id = $('#id_update').val();
	var userName=$('#name_update').val();
	var gender=$('#gender_update').combogrid("getValue");
	var patientAge=$('#age_update').val();
	var phone=$('#phone_update').val();
	var remark=$('#remark_update').val();
	var data = {
			'ID':id,
			'PATIENT_NAME':userName,
			'PATIENT_GENDER':gender,
			"PATIENT_AGE":patientAge,
			"PATIENT_PHONE":phone,
			"REMARK":remark
	};
	var result={
			"postdata":jsonToString(data)
	};
	$.ajax({
		type : 'POST',
		dataType : 'json',
		data : result,
		url : actionUrl + 'updatePatient',
		success : function(data) {
			if (!pageForward(data)) {
				return;
			}
			$.messager.progress('close');
			$('#updateDialog').dialog('close');
			if (data.success) {
				$.messager.alert('修改提示', data.message, 'info');
				onQuery(); 
			} else {
				$.messager.alert('修改提示', '修改失败！', 'warning');
			}
		},
		error : function(data) {
			$.messager.progress('close');
			$.messager.alert('修改提示', '修改失败！', 'error');
		}
	});
}*/

//取消
function cancel(){
	$('#addDialog').dialog('close');
	$('#updateDialog').dialog('close');
}

//新增操作，必须项目check
function check(){
	var message='';
	var userName = $('#userName').val();
	if(userName==''){
		$('#userName').css("background-color", "#FFE4E1");
		message=message + '<div style="margin-left: 50px;"><font color="red">用户姓名是必须输入项目！</font><br></div>';
	}else{
		$('#userName').css("background-color", "");
	}
	var PHONE_NUM = $('#phone').val();
	if(!regIsPhoneNum(Number(PHONE_NUM))){
		$('#phone').css("background-color", "#FFE4E1");
		message=message + '<div style="margin-left: 50px;"><font color="red">请输入正确的手机号码！</font><br></div>';
	}else{
		$('#phone').css("background-color", "");
	}
	if (message==''){
		return true;
	} else {
		$.messager.alert('提示信息', message, 'info');
		return false;
	}
}

//校验是否为电话号码
function regIsPhoneNum(Data) {
	var reg = new RegExp("^[1][3-8]+\\d{9}");
	return reg.test(Data);
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