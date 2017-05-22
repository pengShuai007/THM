//全局变量
var actionUrl="../patientManagement/action/PatientManagementAction.jspx?op=";
var action = "../diagnosis/action/DiagnosisAction.java?op=";
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
		onDblClickRow : ondblclickrow,
		onLoadSuccess : function(data) {
			pageForward(data);
		}
	});
	$.messager.progress('close');
}

//双击打开固定页面（待修改，双击打开填写病例页面），参考扩展业务查询上线申请
function ondblclickrow(rowIndex, rowData) {
	var row = $(patientId).datagrid('getRows')[value];
	var patient_id = row.ID;
	var patient_name = row.PATIENT_NAME;
	var gender = row.PATIENT_GENDER;
	var age = row.PATIENT_AGE;
	localStorage.setItem(recordhospital_id, hospital_id);
	localStorage.setItem(recordaudit_id, audit_id);
	localStorage.setItem(recordstatus, statuss);
	window.parent.parent.addTab('医院功能清单','diagnosis/diagnosisBegin.jsp');
}

// 创建列
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

//修改之前
function diagnosis(){
	var row = $(patientId).datagrid('getSelected');
	if(row == undefined||row==null){
		$.messager.alert("编辑提示","请先选择患者再点击诊断按钮！","warning");
		return;
	}else{
		$.ajax({
			type : 'POST',
			dataType : 'json',
			data : {
				"PATIENT_ID":row.ID
			},
			url : action + 'beginDiagnosis',
			success : function(data) {
				if (!pageForward(data)) {
					return;
				}
				$.messager.progress('close');
				if (data.success) {
					$.messager.alert('诊断提示', data.message, 'info');
					onQuery(); 
				} else {
					$.messager.alert('诊断提示', '修改失败，请联系技术人员解决！', 'warning');
				}
			},
			error : function(data) {
				$.messager.progress('close');
				$.messager.alert('诊断提示', '修改失败，请联系技术人员解决！', 'error');
			}
		});
	}	
}

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