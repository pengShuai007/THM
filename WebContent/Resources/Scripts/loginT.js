/**
 * 全局变量
 */
var actionLoginUrl = "file/operator/action/OperatorLoginActionT.jspx?loc=t&op=";
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
		url : actionLoginUrl + 'login',
		onSubmit : function() {
			if ($('#uname').val() == '') {
				$.messager.alert('登录提示', '用户名不能为空！', 'warning');
				return false;
			}
			if ($('#pass').val() == '') {
				$.messager.alert('登录提示', '密码不能为空！', 'warning');
				return false;
			}
			return true;
		},
		success : function(data) {
			var data = eval('(' + data + ')');
		//	var name=data.hasOwnProperty('OPERATOR_NAME');
			if (data.success) {
				saveUsersInfo($('#uname').val());
				var object = data.data;
				saveUsersInfo(object.OPERATOR_NAME);
//				$.messager.show({
//					title : '登录提示',
//					msg : data.message,
//					timeout : 3000,
//					showType : 'fade',
//					style : {
//						right : '',
//						top : '',
//						bottom : ''
//					}
//				});
				$('#logdialog').dialog('close');
				goToMain();
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

function goToMain() {
	// ?sys=" + sys + "&menuType=" + menuType + "&sysIni=1
	window.location.href = "mainT.jsp";
}
 