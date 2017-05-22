/**
 * 全局变量
 */
var actionLoginUrl = "operator/action/OperatorLoginActionC.jspx?loc=c&op=";
/**
 * 页面加载
 */
$(document).ready(function() {
	superCss();	
	init();
	$('#uname').focus();
});

function init() {
	$('#logdialog').dialog('open');
}

/**
* <pre>
* 任务:KYEEAPPMAINTENANCE-50
* 描述:用户登录
* 作者:罗京
* 日期:2015年2月3日 下午5:20:31
* @returns {Boolean}
* </pre>
*/
function login() {
	if ($('#uname').val() == '') {
		$.messager.alert('登录提示', '用户名不能为空！', 'warning');
		return false;
	}
	if ($('#pass').val() == '') {
		$.messager.alert('登录提示', '密码不能为空！', 'warning');
		return false;
	}
	/*
	//增加判断密码强度，不符合规则，不让登录
	 if(checkPwd($('#pass').val())== false){
		$.messager.alert('登录提示', '要求登录密码必须由 8-16位字母、数字、特殊字符!@#$^&*()_?/组成！请联系管理员重置密码后重新登录！', 'warning');
		return false;
	}*/
	userLogin();
}

/**
* 握手校验用户登录
* 作者:罗京
* 日期:2015年2月2日 下午8:14:40
* </pre>
*/
function userLogin(){
	var uname = $('#uname').val();
	var pass = $('#pass').val();

	//RSA加密
	var Data=getRsaResult(uname + "_$$@@_"+ pass);
	$.ajax({
		type : "POST",
		dataType : "json",
		url : actionLoginUrl + "checkUserLogin",
		data:{
			"Data":Data,
		},
		success : function(data) {
			if (data.success) {
				saveUsersInfo(data.data);	// 保存cookie			
				goToMain();
			} else {
				$.messager.alert('登录提示', data.message, 'warning');
			}
		},
		error: function (data) {
            ajaxError(data.message, "登录提示");
        }
	});
}

//响应回车事件
document.onkeydown=function (event){
	var e=event || window.event || arguments.callee.caller.arguments[0];
	if(e && e.keyCode==13){
		login();
	}
};

function cancel(){
	window.opener=null;
	window.open('','_self');
	window.close();
}

function goToMain() {
	window.location.href = "main.jsp";
}

// 校验口令强度
function checkPwd(obj){
	var reg=/(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$^&*()_?\/])[a-zA-Z\d!@#$^&*()_?\/]{8,16}/i;
	return reg.test(obj);
}
 