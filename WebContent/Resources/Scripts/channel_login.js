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
// 用户登录
function login() {
	$('#loginForm').form('submit', {
		url : actionLoginUrl + 'channel_login',
		onSubmit : function() {
			if ($('#chName').val() == '') {
				$.messager.alert('登录提示', '用户名不能为空！', 'warning');
				return false;
			}
			if ($('#password').val() == '') {
				$.messager.alert('登录提示', '密码不能为空！', 'warning');
				return false;
			}
			return true;
		},
		success : function(data) {
			var data = eval('(' + data + ')');
		//	var name=data.hasOwnProperty('OPERATOR_NAME');
			if (data.success) {
				var object = data.data;
				$('#logdialog').dialog('close');
				goToMain(object.PERMISSION);
			} else {
				$.messager.alert('登录提示', data.message, 'warning');
			}
		}
	});
}

//响应回车事件
document.onkeydown=function (event){
	var e=event || window.event || arguments.callee.caller.arguments[0];
	if(e && e.keyCode==13){
		//document.loginForm.login.onclick();
		//alert("键盘按下");
		login();
	}
};

function cancel(){
	window.opener=null;
	window.open('','_self');
	window.close();
}

function goToMain(flag) {
	if (flag == 1) {
//		window.location.href = "channel/channelUser.jsp";
		window.location.href = "channel/channelDetail.jsp";
	}else if (flag == 0) {
		window.location.href = "channel/countusers.jsp";
		
	}
}
 