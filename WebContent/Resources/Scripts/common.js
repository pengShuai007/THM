/**
 * 全局变量
 */
var moduleNo = "";
var hostName = "";
var tusers;
var common_pathName = "http://" + window.location.host + "/";
var comboBoxActionUrl = hostName + "/Comm/ComboBoxHandler.jspx?op=";
var comboGridActionUrl = hostName + "/Comm/ComboGridHandler.jspx?op=";
var fineReportActionUrl = hostName + "/Comm/CommUtilHandler.jspx?op="; // lizhibo
var reportPrefix = ""; //报表前缀

// 报表操作，如获取配置的发布路径
var actionLoginUrl = hostName + "/RightsSystem/LoginHandler.jspx?op=login";
var acionFineReportUrl = "WebReport/ReportServer?op="; // http://localhost:8075/
/**
 * json to string
 */
function jsonToString(o) {
	// alert("字符串-------"+o);
	if (o == undefined) {
		return "";
	}
	var r = [];
	if (typeof o == "string")
		return "\""
				+ o.replace(/([\"\\])/g, "\\$1").replace(/(\n)/g, "\\n")
						.replace(/(\r)/g, "\\r").replace(/(\t)/g, "\\t") + "\"";
	if (typeof o == "object") {
		if (!o.sort) {
			for ( var i in o) {
				var jsonStr = jsonToString(o[i]);
				if (jsonStr != null && jsonStr != "" && jsonStr != undefined) {
					r.push("\"" + i + "\":" + jsonStr);
				}
			}
			if (!!document.all
					&& !/^\n?function\s*toString\(\)\s*\{\n?\s*\[native code\]\n?\s*\}\n?\s*$/
							.test(o.toString)) {
				r.push("toString:" + o.toString.toString());
			}
			r = "{" + r.join() + "}";
		} else {
			for (var i = 0; i < o.length; i++)
				r.push(fn.Obj2str(o[i]));
			r = "[" + r.join() + "]";
		}
		return r;
	}
	return o.toString().replace(/\"\:/g, '":""');
}

/**
 * paramToJson
 */
function paramToJson(paramStr) {
	var obj = {};
	paramStr = paramStr.replace("+", " ");
	var pairs = paramStr.split('&');
	$.each(pairs, function(i, pair) {
		var keyValue = pair.split('=');
		var name = keyValue[0];
		var value = pair.substring(pair.indexOf("=") + 1);
		obj[name] = value;
	});
	return obj;
};

/**
 * 显示普通窗口，普通窗口小于大窗体
 */

function openWin(url, winname) {
	var width = window.screen.width - 20;
	var heigh = window.screen.height - 20;
	window
			.open(
					url,
					window,
					"height="
							+ heigh
							+ ", width="
							+ width
							+ ", top=30, left=30, toolbar=no,menubar=no, scrollbars=yes, resizable=yes,location=no, status=no");
}

/**
 * 显示模态窗口
 */
function openWinDialog(url, width, height, winname) {
	var top = ((window.screen.height - height) / 2);
	var left = ((window.screen.width - width) / 2);
	var width = window.screen.width - 20;
	var height = window.screen.height - 20;
	window
			.showModalDialog(
					url,
					window,
					"dialogHeight:"
							+ height
							+ "px;dialogWidth:"
							+ width
							+ "px;top:"
							+ top
							+ ";left:"
							+ left
							+ ";toolbar:no;menubar:no;scrollbars:yes;resizable:yes;location:no;status:no");
}
/**
 * 显示模态窗口
 */
function openWinDialog(url, winname) {
	var width = window.screen.width - 20;
	var heigh = window.screen.height - 20;
	var top = ((window.screen.height - heigh) / 2);
	var left = ((window.screen.width - width) / 2);
	window
			.showModalDialog(
					url,
					window,
					"dialogHeight:"
							+ heigh
							+ "px;dialogWidth:"
							+ width
							+ "px;top:"
							+ top
							+ ";left:"
							+ left
							+ ";toolbar:no;menubar:no;scrollbars:yes;resizable:yes;location:no;status:no");
}

// 获取简拼方法
function getsimplepychar(text) {
	var returnChar = "";
	execScript("ascCode=Asc(\"" + text + "\")", "vbscript");
	var tmp = 65536 + ascCode;
	if (tmp >= 45217 && tmp <= 45252)
		returnChar = "A";
	else if (tmp >= 45253 && tmp <= 45760)
		returnChar = "B";
	else if (tmp >= 45761 && tmp <= 46317)
		returnChar = "C";
	else if (tmp >= 46318 && tmp <= 46825)
		returnChar = "D";
	else if (tmp >= 46826 && tmp <= 47009)
		returnChar = "E";
	else if (tmp >= 47010 && tmp <= 47296)
		returnChar = "F";
	else if (tmp >= 47297 && tmp <= 47613)
		returnChar = "G";
	else if (tmp >= 47614 && tmp <= 48118)
		returnChar = "H";
	else if (tmp >= 48119 && tmp <= 49061)
		returnChar = "J";
	else if (tmp >= 49062 && tmp <= 49323)
		returnChar = "K";
	else if (tmp >= 49324 && tmp <= 49895)
		returnChar = "L";
	else if (tmp >= 49896 && tmp <= 50370)
		returnChar = "M";
	else if (tmp >= 50371 && tmp <= 50613)
		returnChar = "N";
	else if (tmp >= 50614 && tmp <= 50621)
		returnChar = "O";
	else if (tmp >= 50622 && tmp <= 50905)
		returnChar = "P";
	else if (tmp >= 50906 && tmp <= 51386)
		returnChar = "Q";
	else if (tmp >= 51387 && tmp <= 51445)
		returnChar = "R";
	else if (tmp >= 51446 && tmp <= 52217)
		returnChar = "S";
	else if (tmp >= 52218 && tmp <= 52697)
		returnChar = "T";
	else if (tmp >= 52698 && tmp <= 52979)
		returnChar = "W";
	else if (tmp >= 52980 && tmp <= 53640)
		returnChar = "X";
	else if (tmp >= 53689 && tmp <= 54480)
		returnChar = "Y";
	else if (tmp >= 54481 && tmp <= 62289)
		returnChar = "Z";
	else
		// 如果不是中文，则不处理
		returnChar = text;
	return returnChar;
}

function showSmallLogin() {
	var smallLogin = $("#smallLogin");
	if (smallLogin.length == 0) {
		var loginHtml = '<div class="easyui-window" id="smallLogin"  style="padding:5px;"'
				+ ' data-options="modal:true,closed:true,title:\'用户登录\',collapsible:false,minimizable:false,maximizable:false,closable:false" > '
				+ '<div style="padding:5px 10px">  '
				+ '	用户: '
				+ '	<input id="smallLogin_userName" style="width:160px">    '
				+ '</div> '
				+ '<div style="padding:5px 10px">   '
				+ '	密码:'
				+ '	<input id="smallLogin_password" type="password" style="width:160px">  '
				+ '</div> '
				+ '<div style="padding:5px 10px;text-align:center"> '
				+ '	<a href="#" id="linkLogin" data-options="iconCls:\'icon-ok\'" class="easyui-linkbutton">登录</a>  '
				+ '	<a href="#" id="linkCancel" data-options="iconCls:\'icon-cancel\'"  class="easyui-linkbutton">取消</a>  '
				+ '</div> ' + '</div>';
		$(loginHtml).appendTo("body");
		// 
		//		
		// $("#smallLogin_userName").bind("keydown", function (e) {
		// var key = e.which;
		// if (key == 13) {
		// $("#smallLogin_password").focus();
		// }
		// });
		$("#smallLogin_userName").bind("keydown", function(e) {
			var key = e.which;
			if (key == 13) {
				var u_name = $("#smallLogin_userName").val();
				if (u_name != null || u_name != "") {
					if (u_name.length > 10) {
						// 文本框值大于10位默认是用保障卡登录，则直接触发登录事件
						$('#linkLogin').click();
					} else {
						$("#smallLogin_password").focus();
					}
				}
			}
		});
		$("#smallLogin_password").bind("keydown", function(e) {
			var key = e.which;
			if (key == 13) {
				$('#linkLogin').click();
			}
		});
		var users = getCookieUsersInfo();
		if (users != undefined && users != null) {
			$("#smallLogin_userName").val(users[0].os_username);
		}

		$('#linkLogin').linkbutton().bind("click", function() {
			/*
			 * 
			 * var userName = $("#smallLogin_userName").val(); var password =
			 * $("#smallLogin_password").val(); if (userName == "") {
			 * $.messager.alert("用户登录提示", "请输入用户名！", 'info'); return; } else if
			 * (password == "") { $.messager.alert("用户登录提示", "请输入密码！", 'info');
			 * return; } else { $.messager.progress({ text: "正在登录，请稍候..." });
			 * $.ajax({ type: "POST", url:
			 * '/RightsSystem/LoginHandler.ashx?op=login', data: { "obj":
			 * jsonToString({ "LoginName": userName, "PassWord": password }) },
			 * dataType: "json", success: function (data) {
			 * $.messager.progress('close'); if (data.success) {
			 * $.messager.confirm('用户登录提示', data.message, function (r) {
			 * //作者：秦晓东 //修复版本：V02C01B05 //任务号：HRPDRDEV-330
			 * //超时后，成功登陆跳转到可用系统选择列表页面 if (typeof (ready) == "function") {
			 * ready(); } else { // window.parent.location.href =
			 * "/systemList.html"; var href = window.location.href; if
			 * (href.substring(href.length - 1, href.length) == "#") href =
			 * href.substring(0, href.length - 1); window.location.href = href; }
			 * 
			 * $("#smallLogin_password").val("");
			 * $("#smallLogin").window('close'); }); } else {
			 * $.messager.alert("用户登录提示", data.message, 'error'); } }, error:
			 * function (data) { $.messager.progress('close'); var data =
			 * data.responseText; data = eval('(' + data + ')');
			 * $.messager.alert(title, data.message, 'error'); } }); }
			 * 
			 */
			// 为集成保障卡卡号登录，注释掉此代码，改为集成性登录
			// 侯昊鹰
			// 2013-11-19 13:00:40
			// doSmallFineReportLogin();
			DologinUserCheck();
		});
		$("#linkCancel").linkbutton().bind("click", function() {
			$("#smallLogin").window('close');
		});
		$("#smallLogin").window({
			modal : true,
			closed : true,
			title : '用户登录',
			collapsible : false,
			minimizable : false,
			maximizable : false,
			closable : false
		});
	}
	$(".messager-window").css({
		"display" : "none"
	});
	$(".window-shadow").css({
		"display" : "none"
	});
	$(".window-mask").css({
		"display" : "none"
	});
	$("#smallLogin").window('open');
}

// 登录检测
// 侯昊鹰
// 2013-11-19 12:47:17
function DologinUserCheck() {
	var u_name = $("#smallLogin_userName").val();
	var u_pass = $("#smallLogin_password").val();
	// $("#loginMsg").html("");
	var strMsg = "";
	var flag = true;
	if (u_name == null || u_name == "") {
		flag = false;
		strMsg = "用户名不能为空!";
	}
	if (!flag) {
		// $("#loginMsg").html(strMsg);
		// $("#doLogin").slideDown("slow");
		// setTimeout("$('#doLogin').slideUp('slow');", 5000);
		$.messager.alert("提示", "用户名不能为空!", "error");
		return;
	}
	var sysId = null;
	$.ajax({
		type : "POST",
		url : hostName + "/RightsSystem/LoginHandler.jspx?op=loginUserCheck",
		data : {
			"u_name" : u_name,
			"u_pass" : u_pass,
			"sysId" : sysId
		},
		dataType : "json",
		success : function(data) {
			if (!data.success) {
				$.messager.alert("提示", data.message, 'info');
				// $("#loginMsg").html("");
				// $("#loginMsg").html(data.message);
				// $("#doLogin").slideDown("slow");

				// setTimeout("$('#doLogin').slideUp('slow');", 5000);
				return;
			} else {
				if (data.data.USER_ID != "" && data.data.USER_PASS != "") {
					$("#smallLogin_userName").val("");
					$("#smallLogin_password").val("");
					$("#smallLogin_userName").val(data.data.USER_ID);
					$("#smallLogin_password").val(data.data.USER_PASS);
					// if (sysId != null)
					// yLogin = true; //设置是否独立登录
					// doFineReportLogin();
					doSmallFineReportLogin();
				}
			}
		},
		error : function(data) {
			ajaxError(data, "提示");
		}
	});
}

// 从cookie获取user信息
function getCookieUsersInfo() {
	var usersString = $.cookie("users");
	var us = undefined;
	if (usersString != null && usersString != "") {
		us = new Array();
		usersString = usersString.split("$");
		for (var i = 0; i < usersString.length; i++) {
			var temp = eval('(' + usersString[i] + ')');
			us.push(temp);
		}
	}
	return us;
}
// 从cookie获取user信息
function getCookie() {
	var usersString = $.cookie("users");
	var us = undefined;
	if (usersString != null && usersString != "") {
		us = new Array();
		usersString = usersString.split("$");
		for (var i = 0; i < usersString.length; i++) {
			// var temp = eval('(' + usersString[i] + ')');
			us.push(usersString[i]);
		}
	}
	return us;
}

// 保存用户信息60天
function saveUsersInfo(usersInfo) {
	$.cookie("users", usersInfo, {
		expires : 7
	});
}

// 主登录窗口是用，登录至报表服务器
// 施建龙
// 2013年5月30日8:41:01
function doFineReportLogin() {
	// 清除报表会话
	// var logOutScr=doLoginOutFineReport();

	var reportScr = $("#REPORT_URL")[0];

	if (reportScr.attachEvent) { // 判断是否为ie浏览器
		// reportScr.attachEvent("onload", function () { //如果为ie浏览器则页面加载完成后立即执行
		//           
		// });
		// 解决IE登陆死循环
		doFineReportLoginProcess();
	} else {
		reportScr.onload = function() { // 其他浏览器则重新加载onload事件
			doFineReportLoginProcess();
		};
	}
	var dt = new Date();
	reportScr.src = acionFineReportUrl + "auth_logout" + "&time="
			+ dt.toString(); // 将报表验证用户名密码的地址指向此iframe
	// reportScr.contentWindow.document.location.reload();
}

function doFineReportLoginProcess() {
	var username, password;
	username = $("#os_username").val().toUpperCase();
	password = $("#os_password").val();
	// alert("username-----" + username + "password----" + password);
	// var scr = document.createElement("iframe"); //创建iframe
	var reportScr = $("#REPORT_URL")[0];
	var dt = new Date();
	// 将报表验证用户名密码的地址指向此iframe

	if (reportScr.attachEvent) { // 判断是否为ie浏览器
		reportScr.attachEvent("onload", function() { // 如果为ie浏览器则页面加载完成后立即执行
			doLogin();
		});
	} else {
		reportScr.onload = function() { // 其他浏览器则重新加载onload事件
			doLogin();
		};
	}
	reportScr.src = acionFineReportUrl + "touchpf&__redirect__=false&username="
			+ username + "&password=" + password + "&time=" + dt.toString();
	// document.getElementsByTagName("head")[0].appendChild(scr);
	// //将iframe标签嵌入到head中
}

// session失效后的登录处理
// 施建龙
// 2013年7月12日11:41:41
function doSmallFineReportLogin() {
	// 清除报表会话
	// var reportScr=doLoginOutFineReport();

	var reportScr = parent.getReportIFrame();
	// alert("用户登陆提示信息----------"+reportScr);
	if (reportScr.attachEvent) { // 判断是否为ie浏览器
		// 修改ie8超时后登陆死机
		// reportScr.attachEvent("load", function () { //如果为ie浏览器则页面加载完成后立即执行
		// alert("用户登录------------");
		// doSmallFineReportLoginProcess();
		// });
		doSmallFineReportLoginProcess();
	} else {
		reportScr.onload = function() { // 其他浏览器则重新加载onload事件
			doSmallFineReportLoginProcess();
		};
	}
	var dt = new Date();
	reportScr.src = acionFineReportUrl + "auth_logout" + "&time="
			+ dt.toString(); // 将报表验证用户名密码的地址指向此iframe

}

// session失效后的登录处理
// 施建龙
// 2013年7月12日11:41:41
function doSmallFineReportLoginProcess() {

	var username, password;
	username = $("#smallLogin_userName").val().toUpperCase();
	password = $("#smallLogin_password").val();

	if (username == "") {
		$.messager.alert("用户登录提示", "请输入用户名！", 'info');
		return;
	} else if (password == "") {
		$.messager.alert("用户登录提示", "请输入密码！", 'info');
		return;
	}

	// alert("username-----" + username + "password----" + password);
	// var scr = document.createElement("iframe"); //创建iframe
	var reportScr = parent.getReportIFrame();
	var dt = new Date();
	// 将报表验证用户名密码的地址指向此iframe

	if (reportScr.attachEvent) { // 判断是否为ie浏览器
		reportScr.attachEvent("onload", function() { // 如果为ie浏览器则页面加载完成后立即执行
			loginProcess(username, password);
		});
	} else {
		reportScr.onload = function() { // 其他浏览器则重新加载onload事件
			loginProcess(username, password);
		};
	}
	reportScr.src = acionFineReportUrl + "touchpf&__redirect__=false&username="
			+ username + "&password=" + password + "&time=" + dt.toString();
	// document.getElementsByTagName("head")[0].appendChild(scr);
	// //将iframe标签嵌入到head中
}

// 临时登录请求方法简单封装
// 施建龙
// 2013年7月9日10:11:23
function loginProcess(username, password) {
	$.messager.progress({
		text : "正在登录，请稍候..."
	});
	$.ajax({
		type : "POST",
		url : hostName + '/RightsSystem/LoginHandler.jspx?op=login',
		data : {
			"obj" : jsonToString({
				"LoginName" : username,
				"PassWord" : password
			})
		},
		dataType : "json",
		success : function(data) {
			$.messager.progress('close');
			if (data.success) {
				message = data.message.substring(0, 5);
				// 切换用户后保存新的用户信息
				// 侯昊鹰
				// 2013年10月14日 10:12:50
				createUserInfo(username.toLowerCase());
				$.messager.confirm('用户登录提示', message, function(r) {
					// 注释解决session超时重新登陆时提示查询数据库错误
					// if (typeof (ready) == "function") {
					// // ready();
					// }
					// else {
					// window.parent.location.href = "/systemList.html";
					var href = window.location.href;
					if (href.substring(href.length - 1, href.length) == "#")
						href = href.substring(0, href.length - 1);
					window.location.href = href;
					// }

					$("#smallLogin_password").val("");
					$("#smallLogin").window('close');
				});
			} else {
				// $.messager.alert("用户登录提示", message, 'error');
				// 修改用户名密码错误提示
				// 侯昊鹰
				// 2013年10月12日 13:17:15
				$.messager.alert("用户登录提示", data.message, 'error');
			}
		},
		error : function(data) {
			$.messager.progress('close');
			var data = data.responseText;
			if (data == "") {
				$.messager.confirm('登录提示', "当前用户登陆已过期或session失效，请重新登陆！",
						function(r) {
							window.parent.location.href = "login.html";
						});
			} else {
				data = eval('(' + data + ')');
				$.messager.alert(title, data.message, 'error');
			}
		}
	});

}

// 保存登录用户信息到cookie中
// 侯昊鹰
// 2013年10月14日 10:12:20
function createUserInfo(userName) {
	// 重新排名和保存
	if (tusers != null && tusers != "undefined") {
		var tempUser = new Array();
		for (var i = 0; i < tusers.length; i++) {
			if (userName != tusers[i].userName) {
				tempUser.push(tusers[i]);
			}
		}
		// 不存在
		var userString = {
			"os_username" : userName
		};
		userString = jsonToString(userString);
		for (var i = 0; i < tempUser.length; i++) {
			userString += "$" + jsonToString(tempUser[i]);
		}
		saveUsersInfo(userString);
	} else {
		// 不存在
		var userString = {
			"os_username" : userName
		};
		userString = jsonToString(userString);
		saveUsersInfo(userString);
	}
}

// 废弃
// 施建龙
// 2013年7月12日11:41:05
function doFineReportSubmit(username, password) {
	var scr = document.createElement("iframe"); // 创建iframe
	// var scr = document.getElementById("F_REPORT");
	var dt = new Date();
	scr.src = acionFineReportUrl + "touchpf&__redirect__=false&username="
			+ username + "&password=" + password + "&time=" + dt.toString(); // 将报表验证用户名密码的地址指向此iframe

	document.getElementsByTagName("head")[0].appendChild(scr); // 将iframe标签嵌入到head中
}

// 不使用动态创建的IFrame，直接在页面中嵌入IFrame
// 施建龙
// 2013年7月12日11:39:52
function doLoginOutFineReport() {
	// var scr = document.createElement("iframe"); //创建iframe
	var scr = $("#REPORT_URL")[0];
	scr.src = acionFineReportUrl + "auth_logout"; // 将报表验证用户名密码的地址指向此iframe
	// document.getElementsByTagName("head")[0].appendChild(scr);

	return scr;

}

reportPrefix


// 查询报表路径
function getReportUrlPrefix(type) {
	var urlPrefix = '';
	// finereport相当于开启报表服务，占用独立的服务端口
	// var reportServer = 'http://localhost:8075/WebReport/ReportServer';
	// //可能会从webconfig或表中获取，主机名/端口
	var reportServer = '';
	// 拼接的公共前缀
	var paramPrefix = '?reportlet=';

	$.ajax({
		url : fineReportActionUrl + "GetReportUrl",
		type : "POST",
		async : false,
		data : "",
		dataType : 'json',
		success : function(data) {
			if (data.success) {
				reportServer = data.message;
				if (type == 'ACCT') {
					// 院级报表相对部署路径, 注意为部署服务的路径，而非VS工程路径
					urlPrefix = reportServer + paramPrefix + 'ACCT/'; // 即web-inf的路径,如WebReport\WEB-INF\reportlets\ACCT即
					// ACCT/
				} else if (type == 'BUD') {
					// 预算报表相对部署路径
					urlPrefix = reportServer + paramPrefix + 'BUD/';
				} else if (type == 'DR') {
					urlPrefix = reportServer + paramPrefix + 'DR/';
				} else if (type == 'DSS') {
					urlPrefix = reportServer + paramPrefix + 'DSS/';
				} else if (type == 'ASSET') {
					urlPrefix = reportServer + paramPrefix + 'ASSET/';
				} else if (type == 'HR') {
					urlPrefix = reportServer + paramPrefix + 'HR/';
				} else if (type == 'APP') {
					urlPrefix = reportServer + paramPrefix + ''; //app放在根目录
				} else {
					urlPrefix = reportServer + paramPrefix + '';
				}
			} else {
				urlPrefix = '';
			}
		},
		error : function(data) {
			ajaxError(data, '提示');
		}
	});
	return urlPrefix;
}

// 提取当前用户登录id，返回给页面调用
// zhangbf 2013-6-29 18:39:04
function getCurrentUserId() {
	var userId = '';

	$.ajax({
		url : fineReportActionUrl + "GetCurrentUserId",
		type : "POST",
		async : false,
		data : "",
		dataType : 'json',
		success : function(data) {
			if (data.success) {
				userId = data.data;
			} else {
				urlPrefix = '';
			}
		},
		error : function(data) {
			ajaxError(data, '提示');
		}
	});
	return urlPrefix;
}
// 保留4位小数
function priceFormatter(value, row, index) {
	value = (value == null || value == "null" || value == "0" || value == 0) ? "0.0000"
			: value;
	value = (Math.round(value * 100) / 100).toFixed(4);
	value = formatMoneyType(value);
	return '<div style="text-align:right;height:auto;">' + value + '</div>';
}
// 金额相关的样式,tofixed针对浮点数做截取
function amountFormatter(value, row, index) {
	value = (value == null || value == "null" || value == "0" || value == 0) ? "0.00"
			: value;
	value = (Math.round(value * 100) / 100).toFixed(2);
	value = formatMoneyType(value);
	return '<div style="text-align:right;height:auto;">' + value + '</div>';
}

// 金额 格式化 （千分符）
function formatMoneyType(data) {
	// EDIT BY JYB 处理金额为整数时js报错问题
	data = data.toString();
	if (data.indexOf('.') < 0) {
		data += '.00';
	}
	// EDIT END
	var j = 0;
	var str = data.split('.');
	var value = '';
	for (var i = str[0].length - 1; i >= 0; i--) {
		if (j >= 3) {
			value = str[0].substr(i, 1) + ',' + value;
			j = 0;
		} else {
			value = str[0].substr(i, 1) + value;
		}
		j++;
	}
	if (str[1] != undefined) {
		value = value + '.';
		j = 0;
		for (var i = 0; i < str[1].length; i++) {
			// if (j >= 3) {
			// value = value + ',' + str[1].substr(i, 1);
			// j = 0;
			// }
			// else {
			value = value + str[1].substr(i, 1);
			// }
			j++;
		}
	}

	// 如果整数位的个数刚好是3的倍数，则在负数时会出现这样的格式：-,999,999.99
	if (value.substring(0, 2) == "-,") {
		value = value.replace('-,', '-');
	}
	return value;
}

// 帆软 js解码
// 解决页面传参时，参数中存在中文时报表加载失败
function finalEncode(text) {
	if (text == null) {
		return "";
	}
	var newText = "";
	for (var i = 0; i < text.length; i++) {
		var code = text.charCodeAt(i);
		if (code >= 128 || code == 91 || code == 93) { // 91 is "[", 93 is "]".
			newText += "[" + code.toString(16) + "]";
		} else {
			newText += text.charAt(i);
		}
	}
	return newText;
}

function showBg(ct, content) {
	var bH = $("body#body").height();
	var bW = $("body#body").width() + 16;
	var objWH = getObjWh(ct);
	$("#fullbg").css({
		width : bW,
		height : bH,
		display : "block"
	});
	var tbT = objWH.split("|")[0] + "px";
	var tbL = objWH.split("|")[1] + "px";
	$("#" + ct).css({
		top : "180px",
		left : tbL,
		display : "block"
	});
	// $("#"+content).html("");
	$(window).scroll(function() {
		resetBg();
	});
	$(window).resize(function() {
		resetBg();
	});
}
function getObjWh(obj) {
	var st = document.documentElement.scrollTop; // 滚动条距顶部的距离
	var sl = document.documentElement.scrollLeft; // 滚动条距左边的距离
	var ch = document.documentElement.clientHeight; // 屏幕的高度
	var cw = document.documentElement.clientWidth; // 屏幕的宽度
	var objH = $("#" + obj).height(); // 浮动对象的高度
	var objW = $("#" + obj).width(); // 浮动对象的宽度
	var objT = Number(st) + (Number(ch) - Number(objH)) / 2;
	var objL = Number(sl) + (Number(cw) - Number(objW)) / 2;
	return objT + "|" + objL;
}
function resetBg() {
	var fullbg = $("#fullbg").css("display");
	if (fullbg == "block") {
		var bH2 = $("body").height();
		var bW2 = $("body").width() + 16;
		$("#fullbg").css({
			width : bW2,
			height : bH2
		});
		var objV = getObjWh("dialog");
		var tbT = objV.split("|")[0] + "px";
		var tbL = objV.split("|")[1] + "px";
		$("#dialog").css({
			top : tbT,
			left : tbL
		});
	}
}
function addLoadCover() {
	$("body").attr("id", "body");
	$("body#body")
			.append(
					"<div id='fullbg'></div><div id='dialog' style='text-align:center'><img src='Resources/Images/Default/loading.gif' >正在加载，请稍候...</div>");
	showBg('dialog', 'dialog');
}
// 关闭灰色JS遮罩层和操作窗口
function closeCover() {
	$("#fullbg").css("display", "none");
	$("#dialog").css("display", "none");
}

// 禁止浏览器内容复制的方法
function disableSelection(target) {
	if (typeof target.onselectstart != "undefined") { // IE route
		target.onselectstart = function() {
			return false;
		}
	} else if (typeof target.style.MozUserSelect != "undefined") { // Firefox
		// route
		target.style.MozUserSelect = "none";
	} else {
		target.onmousedown = function() {
			return false;
		}
	}
	// target.style.cursor = "default";
}

// =================================================EasyUI DateBox控件 缓存时间扩展
// 开始=================================================
// 描述：扩展EasyUI DateBox控件,用于缓存用户事件
// 作者：Qiangfang
// 时间：2013-10-14
var EasyUI_Extend_CACHE_NAME_FLAG_S = "cookie_s_date"; // 缓存名称标记 - 起始时间
var EasyUI_Extend_CACHE_NAME_FLAG_E = "cookie_e_date"; // 缓存名称标记 - 截止时间
var EasyUI_Extend_CACHE_NAME_FLAG = "cookie_date"; // 缓存名称标记 - 默认

var EasyUI_Extend_Cache_SDate_Is_Use = false; // 开始日期 标记
var EasyUI_Extend_Cache_EDate_Is_Use = false; // 截止日期 标记
var EasyUI_Extend_Cache_Is_Use = false; // 默认标记
var EasyUI_Extend_Cache_Other_Is_Use = false; // 其他标记
var EasyUI_Extend_Cache_Other_CacheName = ""; // 其他缓存名称
$
		.extend(
				$.fn.datebox.methods,
				{
					// -----------------------------启用缓存与否
					// 起始-----------------------------
					setStartDateCacheUse : function(jq) {
						$.fn.datebox.methods._setCacheUse(jq,
								EasyUI_Extend_CACHE_NAME_FLAG_S, true);
					},
					setEndDateCacheUse : function(jq) {
						$.fn.datebox.methods._setCacheUse(jq,
								EasyUI_Extend_CACHE_NAME_FLAG_E, true);
					},
					setDateCacheUse : function(jq) {
						$.fn.datebox.methods._setCacheUse(jq,
								EasyUI_Extend_CACHE_NAME_FLAG, true);
					},
					setOtherDateCacheUse : function(jq) {
						$.fn.datebox.methods._setCacheUse(jq,
								EasyUI_Extend_Cache_Other_CacheName, true);
					},
					_setCacheUse : function(jq, cacheName, isCache) {
						if (isCache == true) {
							if (cacheName == EasyUI_Extend_CACHE_NAME_FLAG_S)
								EasyUI_Extend_Cache_SDate_Is_Use = true;
							else if (cacheName == EasyUI_Extend_CACHE_NAME_FLAG_E)
								EasyUI_Extend_Cache_EDate_Is_Use = true;
							else if (cacheName == EasyUI_Extend_CACHE_NAME_FLAG)
								EasyUI_Extend_Cache_Is_Use = true;
							else {
								EasyUI_Extend_Cache_Other_Is_Use = true;
								EasyUI_Extend_Cache_Other_CacheName = true;
							}
						} else {
							EasyUI_Extend_Cache_SDate_Is_Use = false;
							EasyUI_Extend_Cache_EDate_Is_Use = false;
							EasyUI_Extend_Cache_Is_Use = false;
							EasyUI_Extend_Cache_Other_Is_Use = false;
						}
					},
					getCacheIsUse : function(jq, cacheName) {
						if ((EasyUI_Extend_Cache_SDate_Is_Use == true && cacheName == EasyUI_Extend_CACHE_NAME_FLAG_S)
								|| (EasyUI_Extend_Cache_EDate_Is_Use == true && cacheName == EasyUI_Extend_CACHE_NAME_FLAG_E)
								|| (EasyUI_Extend_Cache_Is_Use == true && cacheName == EasyUI_Extend_CACHE_NAME_FLAG)
								|| (EasyUI_Extend_Cache_SDate_Is_Use == true && cacheName == EasyUI_Extend_Cache_Other_CacheName)) {
							return true;
						}
						return false;
					},
					// -----------------------------启用缓存与否
					// 截止-----------------------------
					// -----------------------------获取缓存日期
					// 起始-----------------------------
					getLastSatrtDate : function(jq) {
						return $.fn.datebox.methods.setStartDate(jq,
								EasyUI_Extend_CACHE_NAME_FLAG_S);
					},
					getLastEndDate : function(jq) {
						return $.fn.datebox.methods.setEndDate(jq,
								EasyUI_Extend_CACHE_NAME_FLAG_E);
					},
					getLastDate : function(jq) {
						return $.fn.datebox.methods.setDate(jq,
								EasyUI_Extend_CACHE_NAME_FLAG);
					},
					getLastCacheDate : function(jq, cacheName) {
						// 获取当前日期
						var retVal = "";
						if ($.fn.datebox.methods.getCacheIsUse(jq, cacheName)) {
							var date = $.cookie(cacheName);
							if (date != null && date != ""
									&& date != "NaN-NaN-NaN"
									&& date != undefined) {
								retVal = date;
							}
						}
						return retVal;
					},
					// -----------------------------获取缓存日期
					// 截止-----------------------------
					// -----------------------------设置缓存日期
					// 起始-----------------------------
					setStartDate : function(jq, date) {
						$.fn.datebox.methods.setCacheDate(jq,
								EasyUI_Extend_CACHE_NAME_FLAG_S, date);
					},
					setEndDate : function(jq, date) {
						$.fn.datebox.methods.setCacheDate(jq,
								EasyUI_Extend_CACHE_NAME_FLAG_E, date);
					},
					setDate : function(jq, date) {
						$.fn.datebox.methods.setCacheDate(jq,
								EasyUI_Extend_CACHE_NAME_FLAG, date);
					},
					setCacheDate : function(jq, cacheName, date) {
						if (cacheName == NaN || cacheName == undefined
								|| cacheName == "" || cacheName == null)
							return;
						if (date == NaN || date == undefined || date == ""
								|| date == null)
							return;
						var arys = date.split('-');
						if (arys.length != 3
								|| (arys.length == 3 && arys[2] == ""))
							return;
						jq.each(function() {
							$.fn.datebox.methods._setCacheDate(this, cacheName,
									arys[0], arys[1], arys[2]);
						});
					},
					_setCacheDate : function(jq, cacheName, year, month, day) {
						var date = new Date(year, month - 1, day);
						var datebox = $.data(jq, "datebox");
						var opts = datebox.options;
						// var value = date.getTime();
						var text = opts.formatter(date);
						$(jq).combo("setValue", text).combo("setText", text);
						datebox.calendar.calendar("moveTo", date);
						if ($.fn.datebox.methods.getCacheIsUse(jq, cacheName)) {
							var str_date = year + "-" + month + "-" + day;
							if (str_date != "" && str_date != undefined
									&& str_date != null) {
								$.cookie(cacheName, str_date, {
									expires : 7,
									path : '/'
								});
							}
						}
						// alert("cookie_date:--------" +
						// $.cookie("cookie_date") + "-------" +
						// document.cookie);
					},
					// -----------------------------获取缓存日期
					// 截止-----------------------------
					// -----------------------------控件赋值
					// 起始-----------------------------
					getLastStartDateToCtrl : function(jq, _date) {
						var date = $.fn.datebox.methods.getLastCacheDate(jq,
								EasyUI_Extend_CACHE_NAME_FLAG_S);
						if (date != undefined && date != "" && date != null)
							$.fn.datebox.methods.setStartDate(jq, date);
						else
							$.fn.datebox.methods.setStartDate(jq, _date); // 用默认值
					},
					getLastEndDateToCtrl : function(jq, _date) {
						var date = $.fn.datebox.methods.getLastCacheDate(jq,
								EasyUI_Extend_CACHE_NAME_FLAG_E);
						if (date != undefined && date != "" && date != null)
							$.fn.datebox.methods.setEndDate(jq, date);
						else
							$.fn.datebox.methods.setEndDate(jq, _date); // 用默认值
					},
					getLastDateToCtrl : function(jq, _date) {
						var date = $.fn.datebox.methods.getLastCacheDate(jq,
								EasyUI_Extend_CACHE_NAME_FLAG);
						if (date != undefined && date != "" && date != null)
							$.fn.datebox.methods.setDate(jq, date);
						else
							$.fn.datebox.methods.setDate(jq, _date); // 用默认值
					},
					getLastOtherDateToCtrl : function(jq, _date) {
						$.fn.datebox.methods._getLastDateToCtrl(jq,
								EasyUI_Extend_Cache_Other_CacheName, _date);
					},
					_getLastDateToCtrl : function(jq, cacheName, _date) {
						// 获取当前日期
						var date = $.fn.datebox.methods.getLastCacheDate(jq,
								cacheName);
						if (date != undefined && date != "" && date != null)
							$.fn.datebox.methods.setDate(jq, date);
						else
							$.fn.datebox.methods.setDate(jq, _date); // 用默认值
					}
				// -----------------------------控件赋值
				// 截止-----------------------------
				});
// 设置日期控件启用Cookie缓存
function setCacheControl(controlID, sType, defaultDate) {
	var method_cache_use = "";
	var method_set_date = "";
	var method_date_to_ctrl = "";

	if (sType == "s") {
		method_cache_use = "setStartDateCacheUse";
		method_set_date = "setStartDate";
		method_date_to_ctrl = "getLastStartDateToCtrl";
	} else if (sType == "e") {
		method_cache_use = "setEndDateCacheUse";
		method_set_date = "setEndDate";
		method_date_to_ctrl = "getLastEndDateToCtrl";
	} else if (sType == "o") {
		alert("暂未启用其他缓存，为预留字段");
		return;
	} else {
		method_cache_use = "setDateCacheUse";
		method_set_date = "setDate";
		method_date_to_ctrl = "getLastDateToCtrl";
	}
	var date = new Date(defaultDate);
	if (date == NaN || date == undefined || date == "" || isNaN(date)) {
		date = "";
		var arys = defaultDate.split('-');
		if (arys.length == 3 && arys[2] != "")
			date = defaultDate;
	} else
		date = defaultDate;

	$("#" + controlID).datebox(method_cache_use, true); // 启用开始日期缓存
	$("#" + controlID).datebox(
			{
				// 日期改变 事件
				onChange : function(_date, old_date) {
					date = new Date(_date);
					if (date == NaN || date == undefined || date == ""
							|| isNaN(date)) {
						var arys = _date.split('-');
						if (arys.length == 3 && arys[2] != "") {
							date = new Date();
							date.setUTCFullYear(arys[0], arys[1] - 1, arys[2]);
							date.setUTCHours(0, 0, 0, 0);
						} else {
							return;
						}
					}
					var str_data = date.getFullYear() + "-"
							+ (date.getMonth() + 1) + "-" + date.getDate(); // 选中日期
					$("#" + controlID).datebox(method_set_date, str_data); // 将日期赋值给日期控件
					// alert($("#" + controlID).datebox("getValue"));
				}
			});
	$("#" + controlID).datebox(method_date_to_ctrl, date); // 设置开始日期缓存到当前控件,默认为空
}

function getCacheDate(sType) {
	var cacheName = "";
	if (sType == "s") {
		cacheName = EasyUI_Extend_CACHE_NAME_FLAG_S;
	} else if (sType == "e") {
		cacheName = EasyUI_Extend_CACHE_NAME_FLAG_E;
	} else if (sType == "o") {
		alert("暂未启用其他缓存，为预留字段");
		return;
	} else {
		cacheName = EasyUI_Extend_CACHE_NAME_FLAG;
	}
	if (cacheName != "") {
		return $.cookie(cacheName);
	}
	return "";
}
function NewDate(str) {
	str = str.split('-');
	var date = new Date();
	date.setUTCFullYear(str[0], str[1] - 1, str[2]);
	date.setUTCHours(0, 0, 0, 0);
	return date;
}
// =================================================EasyUI DateBox控件 缓存时间扩展
// 结束=================================================
// -----------------------------------------------将字符转化为ASC码开始------------------------------------------------------//

// Edit Add >>> zhangbf 2013-10-16 22:11:11
// 将传入的字符串转义成ASC码，并用传进来的字符作为分隔符
// 如传递参数'-'和‘abc’，则返回'97-98-99'
// 参数splitStr必须传，否则返回值将等于没有字符的asc码的加和，如abc 转化后= 97+98+99=294
function formatStrToAsc(splitStr, inputStr) {
	var returnStr = '';
	if (inputStr == undefined || inputStr == null || inputStr == '') {
		return returnStr;
	}

	for (var i = 0; i < inputStr.length; i++) {
		if (returnStr == '') {
			returnStr = getAsc(inputStr[i]);
		} else {
			returnStr += splitStr + getAsc(inputStr[i]);
		}
	}

	return returnStr;
}
// Edit Add <<< zhangbf 2013-10-16 22:11:19

function getAsc(str) {

	return str.charCodeAt(0);

}
// -----------------------------------------------将字符转化为ASC码结束------------------------------------------------------//
// -----------------------------------------------将ASC码转化为字符开始------------------------------------------------------//
function backAsc(str) {

	return String.fromCharCode(str);

}
// -----------------------------------------------将ASC码转化为字符开始------------------------------------------------------//

// Edit Add >>> zhangbf 2013-11-20 12:46:40
// 去掉左侧空格并返回
function ltrim(s) {
	if (s == undefined || s == "") {
		return s;
	}
	if (s.length > 0) {
		if (s.charAt(0) == " ")
			s = s.substring(1, s.length);
		if (s.charAt(0) == " ")
			return ltrim(s);
	}
	return s;
}

// 去掉右侧空格并返回
function rtrim(s) {
	if (s == undefined || s == "") {
		return s;
	}
	if (s.length > 0) {
		if (s.charAt(s.length - 1) == " ")
			s = s.substring(0, s.length - 1);
		if (s.charAt(s.length - 1) == " ")
			return rtrim(s);
	}
	return s;
}

// 去掉左侧和右侧空格并返回
function trim(s) {
	return ltrim(rtrim(s));
}
// Edit Add <<< zhangbf 2013-11-20 12:46:40

// Edit Add <<< qinxiaodong 2013-11-22 05:01:20

// 计算字符串长度，区分中英文，中文算2个字,英文一个
function getStrLength(str) {
	var cArr = str.match(/[^\x00-\xff]/ig);
	return str.length + (cArr == null ? 0 : cArr.length);
}
// Edit Add <<< qinxiaodong 2013-11-22 05:01:20

// session失效后页面跳转
function pageForward(data) {
	if (data.success == "timeout") {
		$.messager.progress('close');
		$.messager.alert('提示信息', data.message, 'info', function() {
			top.location.href = "../login.jsp";
		});
	} else {
		return true;
	}
}
