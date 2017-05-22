/**
 * 全局变量
 */
var actionLoginUrl = "operator/action/OperatorLoginActionC.jspx?loc=c&op=";
/**
 * 页面加载
 */
$(document).ready(function() {
	init();
});

function init() {
	login();
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
	var Data='6257368d4a8d397a60b3dba0f22cd89bd050e1834e217acf4bdc2570dfcb3cdf8e648e5f27bb4a9489bbc359be5efbd514bc78b57060493065ca3f7c8aafd9ed';
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
	// ?sys=" + sys + "&menuType=" + menuType + "&sysIni=1
	window.location.href = "main.jsp";
}

 