var actionUrl = "GuideAndSupplier/action/GuideAndSupplierLoginAction.jspx?op="; //访问的url
var loginId = "#loginCode"; //用户ID
var passId = "#pass"; //密码ID

$(document).ready(function() {
	superCss();	
	$('#loginCode').focus();
});

/**
 * 任务号：KYEEAPPMAINTENANCE-153
 * 描述：登陆函数
 * 作者：liuxingping
 * 时间：2015年4月2日上午10:45:30
 */
function login() {
	var role = urole.value;
	var login = $(loginId).val();
	var pass = $(passId).val();
	if (login == "") {
		$.messager.alert('登录提示', '用户名不能为空！', 'warning');
		return ;
	}
//	if (pass == "") {
//		$.messager.alert('登录提示', '密码不能为空！', 'warning');
//		return ;
//	}
	checkLogin(role,login,pass);
}

/**
 * 任务号：KYEEAPPMAINTENANCE-153
 * 描述：校验用户
 * 作者：liuxingping
 * 时间：2015年4月2日上午10:45:24
 * @param role
 * @param login
 * @param pass
 */
function checkLogin(role,login,pass) {
	changeUser(true);
	$.ajax({
		type : "POST",
		dataType : "json",
		url : actionUrl + "CheckLogin",
		data : {"role" : role, "login" : login, "pass" : pass},
		success : function(data) {
			if (data.success) {
				if (role == "MedicalGuide") {
					window.location.href = "guide.jsp";
				} else if (role == "Supplier") {
					window.location.href = "supplier.jsp";
				} else {
					changeUser(false);
				}
			} else {
				changeUser(false);
				$.messager.alert('登录提示', data.message, 'warning');
				return;
			}
		}, 
		error : function (data) {
			changeUser(false);
            ajaxError(data.message, "登录提示");
        }
	});
}

//响应回车事件
document.onkeydown = function (event) {
	var e = event || window.event || arguments.callee.caller.arguments[0]; //获取事件
	if(e && e.keyCode==13){ //如果是按回车键则执行登陆操作
		login();
	}
};


/**
 * 任务号：
 * 描述：禁用和启用编辑
 * 作者：liuxingping
 * 时间：2015年4月10日下午7:21:42
 */
function changeUser(boolean) {
	if (boolean) {
		$("#urole").attr({"disabled" : "disabled"});
		$("#loginCode").attr({"disabled" : "disabled"});
		$("#pass").attr({"disabled" : "disabled"});
	} else {
		$("#urole").removeAttr("disabled");
		$("#loginCode").removeAttr("disabled");
		$("#pass").removeAttr("disabled");
	}
}