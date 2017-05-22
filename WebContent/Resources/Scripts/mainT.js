/**
 * 全局参数
 */
var actionLoginUrl = "file/operator/action/OperatorLoginActionT.jspx?loc=t&op=";
//var actionLoginUrl = "http://172.16.2.151:8088/APP/operator/action/OperatorLoginActionC.jspx?loc=c&op=";
var actionSystemUIUrl = "RightsSystem/SystemUISeting/SystemUISetingHandler.jspx?op=";
// var budComUrl = "Budget/BudComm/BudCommHandler.jspx?op=";//暂时屏蔽
var assetComUrl = "ASSET/AssetComm/AssetCommHandler.jspx?op=";
var favoritePanelUrl = "DSS/FavoritePanel/FavoritePanelHandler.jspx?op=";
var loaded = false;
var isOverKeepSession = false;
var menuType = 0;
var clickType = false;
var sysType = "";
var goToHisId = "";
var mounseOut = false;
var ifMsg = false;
// var historyOperate = new Array();
// 创建系统菜单列表
var systemList;
var host = "Resources/Images/main/";
var host404 = "Resources/Images/Default/defaultSubsys.png";
var defaultImg = "Resources/Images/Default/";
// 后台已经把路径加上，前端不在处理
// 施建龙
// 2013年10月7日19:12:16
// var uploadImgPath = "Resources/Images/Upload/";
var uploadImgPath = "";
var p_operation_id = "";
var curNonde = "";
var TargetNode = "";

$(document).ready(function() {
	tabCloseEven();
	clockon();
	creatTreeMenu();
	showlogin();
});

function addTab(title, url) {
	if (parent.$('#tabs').tabs('exists', title)) {
		parent.$('#tabs').tabs('select', title); // 选中并刷新
		var currTab = parent.$('#tabs').tabs('getSelected');
		// var url = $(currTab.panel('options').content).attr('src');
		if (url != undefined && currTab.panel('options').title != '主页') {
			parent.$('#tabs').tabs('updateIframeTab', {
				which : "" + currTab.panel('options').title,
				iframe : {
					src : url
				}
			// options: {
			// content: createFrame(url)
			// }
			});
		}
	} else {
		// var content = createFrame(url);
		parent.$('#tabs').tabs('addIframeTab', {
			tab : {
				title : title,
				closable : true
			},
			iframe : {
				src : url
			}
		// title: title,
		// content: content,
		// closable: true
		});
	}
	tabClose();
}
// >>>>>>>>>>>>> Author：WXD Time：2013年12月12日 16:38:59<<<<<<<<<<<<<<<<<<//

function createFrame(url) {
	var iframe = document.createElement("iframe");
	iframe.src = url;
	iframe.scrolling = "auto";
	iframe.setAttribute("frameborder", "0");
	// iframe.style = "width:100%;height:100%;";

	// iframe.setAttribute("style", "width:100%;height:100%;");
	iframe.setAttribute("width", "100%");
	iframe.setAttribute("height", "100%");
	// if (iframe.attachEvent) {
	// iframe.attachEvent("onload", function () {

	// // alert("Local iframe is now loaded.");
	// // addLoadCover();
	// // closeCover();
	// });
	// } else {
	// iframe.onload = function () {
	// //alert("Local iframe is now loaded.");
	// //addLoadCover();
	// // closeCover();
	// };
	// }
	// var s = '<iframe scrolling="auto" frameborder="0" src="' + url
	// + '" style="width:100%;height:100%;"></iframe>';

	return iframe;
}

function tabClose() {
	/* 双击关闭TAB选项卡 */
	$(".tabs-inner").dblclick(function() {
		var subtitle = $(this).children(".tabs-closable").text();
		$('#tabs').tabs('close', subtitle);
	});
	/* 为选项卡绑定右键 */
	$(".tabs-inner").bind('contextmenu', function(e) {
		$('#mm').menu('show', {
			left : e.pageX,
			top : e.pageY
		});
		var subtitle = $(this).children(".tabs-closable").text();
		$('#mm').data("currtab", subtitle);
		$('#tabs').tabs('select', subtitle);
		return false;
	});
}

function closeAllTabs() {
	$('.tabs-inner span').each(function(i, n) {
		var t = $(n).text();
		if (t != '主页') {
			$('#tabs').tabs('close', t);
		}
	});
}
// 绑定右键菜单事件
function tabCloseEven() {
	// 刷新
	$('#mm-tabupdate').click(function() {
		var currTab = $('#tabs').tabs('getSelected');
		var url = $(currTab.panel('options').content).attr('src');
		if (url != undefined && currTab.panel('options').title != '主页') {
			$('#tabs').tabs('update', {
				tab : currTab,
				options : {
					content : createFrame(url)
				}
			});
		}
	});
	// 关闭当前
	$('#mm-tabclose').click(function() {
		var currtab_title = $('#mm').data("currtab");
		$('#tabs').tabs('close', currtab_title);
	});
	// 全部关闭
	$('#mm-tabcloseall').click(function() {
		// EDIT BY qinxiaodong START
		// 提取关闭全部tab方法，切换年度时调用
		closeAllTabs();
	});

	// 关闭除当前之外的TAB
	$('#mm-tabcloseother').click(function() {
		var prevall = $('.tabs-selected').prevAll();
		var nextall = $('.tabs-selected').nextAll();
		if (prevall.length > 0) {
			prevall.each(function(i, n) {
				var t = $('a:eq(0) span', $(n)).text();
				if (t != '主页') {
					$('#tabs').tabs('close', t);
				}
			});
		}
		if (nextall.length > 0) {
			nextall.each(function(i, n) {
				var t = $('a:eq(0) span', $(n)).text();
				if (t != '主页') {
					$('#tabs').tabs('close', t);
				}
			});
		}
		return false;
	});
	// 关闭当前右侧的TAB
	$('#mm-tabcloseright').click(function() {
		var nextall = $('.tabs-selected').nextAll();
		if (nextall.length == 0) {
			// msgShow('系统提示','后边没有啦~~','error');
			alert('后边没有啦~~');
			return false;
		}
		nextall.each(function(i, n) {
			var t = $('a:eq(0) span', $(n)).text();
			$('#tabs').tabs('close', t);
		});
		return false;
	});
	// 关闭当前左侧的TAB
	$('#mm-tabcloseleft').click(function() {
		var prevall = $('.tabs-selected').prevAll();
		if (prevall.length == 0) {
			alert('到头了，前边没有啦~~');
			return false;
		}
		prevall.each(function(i, n) {
			var t = $('a:eq(0) span', $(n)).text();
			$('#tabs').tabs('close', t);
		});
		return false;
	});

	// 退出
	$("#mm-exit").click(function() {
		$('#mm').menu('hide');
	});
}

/**
 * 本地时钟
 */
var count = 598;
function clockon() {
	var now = new Date();
	var year = now.getFullYear(); // getFullYear getYear
	var month = now.getMonth();
	var date = now.getDate();
	var day = now.getDay();
	var hour = now.getHours();
	var minu = now.getMinutes();
	var sec = now.getSeconds();
	var week;
	month = month + 1;
	if (month < 10)
		month = "0" + month;
	if (date < 10)
		date = "0" + date;
	if (hour < 10)
		hour = "0" + hour;
	if (minu < 10)
		minu = "0" + minu;
	if (sec < 10)
		sec = "0" + sec;
	var arr_week = new Array("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六");
	week = arr_week[day];
	var date = year + "/" + month + "/" + date + "";
	var time = hour + ":" + minu + ":" + sec + " " + week;
	$("#bgclock_date").html(date);
	$("#bgclock_time").html(time);
	$("#systemTime").html(date + "  " + time);
	if (sysType == "1001") {
		if (ifMsg) {
			if (count / 600 == 1) {
				show();
				count = 0;
			}
		}
		count++;
	}
	var timer = setTimeout("clockon()", 1000);

}

function creatTreeMenu() {
	$('#tt').tree({
		fit : true,
		 lines: true,
		url : actionLoginUrl + "queryMenus",
		id : "id",
		text : 'text',
		onClick : treeNodeOnClick,
		onLoadSuccess: function (node,data){
			if( data.success == "timeout"){
				$.messager.alert('登录超时',data.message,'info',function(){top.location.href="login.jsp";});
			}
		}
	});
}

/**
 * bind event to tree node
 */
function treeNodeOnClick(node) {

	var nodeData = $('#tt').tree('getData', node.target);
	if (nodeData.children.length == 0) {
		// addLoadCover();
		if (node.attributes != null)
			addTab(node.text, node.attributes);
		else
			addTab(node.text, "noBuy.html");
	} else {
//		getCanUseSysList(node.id);
//		$('#tabs').tabs('select', "主页");
//		// historyOperate.push(node.id);
//		if (node.state == "open") {
//			node.state = "closed";
//			$('#tt').tree('collapse', node.target);
//		} else {
//			$('#tt').tree('expand', node.target);
//		}
		// closeCover();
	}

}

/*
 * 退出
 */
function loginOut() {
	$.messager
			.confirm(
					'退出提示',
					'您确定要退出系统吗？',
					function(r) {
						if (r) {

							$.messager.progress({
								title : "退出提示",
								text : "正在退出,请稍候....",
								interval : 1000
							});
							$.ajax({
										type : "POST",
										url : actionLoginUrl + "loginOut",
										dataType : "json",
										success : function(data) {
											if (data=="1") {
												window.location.href = "loginT.jsp";
//												$.messager.progress({
//													title : "退出提示",
//													text : "退出成功！",
//													interval : 1000
//												});
//												// doLoginOutFineReport();
//												// window.location.href =
//												// "login.aspx";
//												// history.back();
//												// /退出时的登录页保持与登录时输入的地址一致
//												// /侯昊鹰
//												// /2013-11-07 12:13:45
//												var sysId;
//												var preUrl = document.referrer
//														.split("?"); // 取得Get参数
//												var aGET = new Array();
//												if (preUrl.length > 1) {
//													var aBuf = preUrl[1]
//															.split("&");
//													for (var i = 0, iLoop = aBuf.length; i < iLoop; i++) {
//														var aTmp = aBuf[i]
//																.split("="); // 分离key与Value
//														aGET[aTmp[0]] = aTmp[1];
//													}
//												}
//												sysId = aGET["sysId"];
//												if (sysId == undefined) {
//													// window.location.href =
//													// "login.aspx";
//													// HRPDRTEST-2060设置默认登录页
//													// 侯昊鹰
//													// 2013-12-13 11:19:10
//													//window.location.href = data.data;
//													window.location.href = "main.jsp";
//												} else {
//													//window.location.href = document.referrer;
//													window.location.href = "main.jsp";
//												}
//											} else {
//												$.messager.alert("退出提示",
//														data.message, 'error');
											}
										},
										error : function(data) {
											$.messager.progress('close');
											ajaxError(data, "退出提示");
										}
									});
						}
					});
}

function creatAccordionM0(id, data) {
	for (var i = 0; i < data.length; i++) {
		$('#' + id).accordion(
				'add',
				{
					title : data[i].text,
					content : "<div  id='m" + i.toString()
							+ "' class='easyui-accordion'></div>",
					style : {
						'padding' : '1px 1px 3px 3px'
					}
				});
		// while ($("#m" + i.toString()).length == 0) {
		// window.setTimeout("clockon()", 1000); }
		creatAccordion("m" + i.toString(), data[i].children);

	}
}
function creatAccordion(id, data) {
	var isHaveChildren = true;
	var array = new Array();
	for (var i = 0; i < data.length; i++) {
		if (data[i].children != null) {
			$('#' + id.toString()).accordion('add', {
				id : 'a' + data[i].id.toString(),
				title : data[i].text,
				content : "<ul id='t" + data[i].id.toString() + "'></ul>",
				selected : true,
				style : {
					'padding' : '1px 1px 1px 5px'
				},
				border : false
			});
			// while ($('#t' + data[i].id.toString()).length == 0) {
			// window.setTimeout("clockon()", 1000); }
			$('#t' + data[i].id.toString()).tree({
				data : data[i].children,
				onClick : treeNodeOnClick
			});
			$('#t' + data[i].id.toString()).tree("expandAll");
		} else {
			isHaveChildren = false;
			array.push(data[i]);
		}
	}
	if (!isHaveChildren) {
		$("#" + id).html("<ul id='" + id + "t'><ul>");
		$("#" + id + 't').tree({
			data : array,
			onClick : treeNodeOnClick
		});
	}
}
//当前用户修改密码
function updatepw(){
	$('#update_Doing_pass').attr("value","");
	$('#update_new_pass').attr("value","");
	$('#update_confirm_pass').attr("value","");
	$('#update_new_pass').css("background-color", "");
	$('#update_confirm_pass').css("background-color", "");
	$('#update_Doing_pass').css("background-color", "");
	$('#update_password').dialog('open');
}
function update_Pass(){
	if(updateCheck()==false){
		return;
	}
	$.messager.progress();
	var nowpass= $('#update_Doing_pass').val();
	var newpassold=$('#update_new_pass').val();
	var newpass=$('#update_confirm_pass').val();
	var username=undefined;
	username=new Array();
	username=getCookie();
	var operatorName=username[0];
	if(newpassold==newpass){
		var data = {'OLDPASS_WORD':nowpass,'OPERATOR_NAME':operatorName,'PASS_WORD':newpass};
		var result={"postdata":jsonToString(data)};
		$.ajax({
		type : 'POST',
		dataType : 'json',
		data : result,
		url : actionLoginUrl + 'updatepwd',
		success : function(data) {
		  if( data.success == "timeout"){
			  $.messager.progress('close');
			  $.messager.alert('修改提示',data.message,'info',function(){top.location.href="login.jsp";});
			  return;
		  }
		  $.messager.progress('close');
		  $('#update_password').dialog('close');
		  if (data.success) {
		    $.messager.alert('修改提示', data.message, 'info');
		  }
		  else{
		       $.messager.alert('修改提示', '修改失败！', 'warning');
		   }
		  },
		    error : function(data) {
		         $.messager.progress('close');
		    	 $.messager.alert('修改提示', '修改失败！', 'error');
		    }
		  });
	}
	else{
		$.messager.alert('检查提示','两次输入的密码不同，请重新输入！','warning');
		$.messager.progress('close');
		return ;
	}
}
function updateCheck(){
	var message='';
	var nowPwd = $('#update_Doing_pass').val();
	var newPwd = $('#update_new_pass').val();
	var confirmPwd = $('#update_confirm_pass').val();
	if(nowPwd==''){
		$('#update_Doing_pass').css("background-color", "#FFE4E1");
		message=message + '<div style="margin-left: 50px;"><font color="red">当前密码不能为空！</font><br></div>';
	}else{
		$('#update_Doing_pass').css("background-color", "");
	}
	if(newPwd==''){
		$('#update_new_pass').css("background-color", "#FFE4E1");
		message=message + '<div style="margin-left: 50px;"><font color="red">新密码不能为空！</font><br></div>';
	}else{
		if(confirmPwd==''){
			$('#update_confirm_pass').css("background-color", "#FFE4E1");
			message=message + '<div style="margin-left: 50px;"><font color="red">确认密码不能为空！</font><br></div>';
		}else{
			if(newPwd!=confirmPwd){
				$('#update_new_pass').css("background-color", "#FFE4E1");
				$('#update_confirm_pass').css("background-color", "#FFE4E1");
				message=message + '<div style="margin-left: 50px;"><font color="red">两次输入的密码不同，请重新输入！</font><br></div>';
			} else {
				$('#update_new_pass').css("background-color", "");
				$('#update_confirm_pass').css("background-color", "");
			}
		}
	}

	if (message==''){
		return true;
	} else {
		$.messager.alert('提示信息', message, 'info');
		return false;
	}
}
function cancel_Update(){
	$('#update_password').dialog('close');
	//$(hospitalsId).datagrid('reload', {});
}
//显示当前登录用户
function showlogin(){
	var username=undefined;
	username=new Array();
	//username=getCookieUsersInfo();
	username=getCookie();
	$('#loginUser').html(username[0]);
}
// 当前登录用户信息
function getCurrentUser() {
	$.ajax({
		type : "POST",
		url : actionLoginUrl + "getCurrentUser",
		dataType : "json",
		success : function(data) {
			if (data.success) {
				$("#currentUser").html(data.data.UserName);
				$("#loginUser").html(data.data.UserName);
			} else {

			}
		},
		error : function(data) {
			$.messager.progress('close');
			data = data.responseText;
			data = eval('(' + data + ')');
			$.messager.alert("获取当前用户提示", data.message, 'error');
		}
	});
}

/**
 * @author {CaoGuangHui}
 */
$
		.extend(
				$.fn.tabs.methods,
				{
					/**
					 * 加载iframe内容
					 * 
					 * @param {jq
					 *            Object} jq [description]
					 * @param {Object}
					 *            params
					 *            params.which:tab的标题或者index;params.iframe:iframe的相关参数
					 * @return {jq Object} [description]
					 */
					loadTabIframe : function(jq, params) {
						return jq
								.each(function() {
									var $tab = $(this).tabs('getTab',
											params.which);
									if ($tab == null)
										return;
									var $tabBody = $tab.panel('body');
									// 销毁已有的iframe
									var $frame = $('iframe', $tabBody);
									if ($frame.length > 0) {
										try {// 跨域会拒绝访问，这里处理掉该异常
											$frame[0].contentWindow.document
													.write('');
											$frame[0].contentWindow.close();
										} catch (e) {
											// Do nothing
										}
										$frame.remove();
										if ($.browser.msie) {
											CollectGarbage();
										}
									}
									$tabBody.html('');
									$tabBody.css({
										'overflow' : 'hidden',
										'position' : 'relative'
									});
									var $mask = $(
											'<div style="position:absolute;z-index:2;width:100%;height:100%;background:#ccc;opacity:0.3;filter:alpha(opacity=30);"><div>')
											.appendTo($tabBody);
									var $maskMessage = $(
											'<div class="mask-message" style="z-index:3;width:auto;height:16px;line-height:16px;position:absolute;top:50%;left:50%;margin-top:-20px;margin-left:-92px;border:2px solid #ccc;padding: 12px 5px 10px 30px;background: #ffffff url(\''
													+ hostName
													+ '/Resources/Themes/default/images/loading.gif\') no-repeat scroll 5px center;">'
													+ (params.iframe.message || '正在加载中，请稍等。。。')
													+ '</div>').appendTo(
											$tabBody);
									var $containterMask = $(
											'<div style="position:absolute;width:100%;height:100%;z-index:1;background:#fff;"></div>')
											.appendTo($tabBody);
									var $containter = $(
											'<div style="position:absolute;width:100%;height:100%;z-index:0;"></div>')
											.appendTo($tabBody);
									var iframe = document
											.createElement("iframe");
									iframe.src = params.iframe.src;
									iframe.frameBorder = params.iframe.frameBorder || 0;
									iframe.height = params.iframe.height
											|| '100%';
									iframe.width = params.iframe.width
											|| '100%';
									if (iframe.attachEvent) {
										iframe
												.attachEvent(
														"onload",
														function() {
															$(
																	[
																			$mask[0],
																			$maskMessage[0] ])
																	.fadeOut(
																			params.iframe.delay
																					|| 'slow',
																			function() {
																				$(
																						this)
																						.remove();
																				if ($(
																						this)
																						.hasClass(
																								'mask-message')) {
																					$containterMask
																							.fadeOut(
																									params.iframe.delay
																											|| 'slow',
																									function() {
																										$(
																												this)
																												.remove();
																									});
																				}
																			});
														});
									} else {
										iframe.onload = function() {
											$([ $mask[0], $maskMessage[0] ])
													.fadeOut(
															params.iframe.delay
																	|| 'slow',
															function() {
																$(this)
																		.remove();
																if ($(this)
																		.hasClass(
																				'mask-message')) {
																	$containterMask
																			.fadeOut(
																					params.iframe.delay
																							|| 'slow',
																					function() {
																						$(
																								this)
																								.remove();
																					});
																}
															});
										};
									}
									$containter[0].appendChild(iframe);
								});
					},
					/**
					 * 增加iframe模式的标签页
					 * 
					 * @param {[type]}
					 *            jq [description]
					 * @param {[type]}
					 *            params [description]
					 */
					addIframeTab : function(jq, params) {
						return jq.each(function() {
							if (params.tab.href) {
								delete params.tab.href;
							}
							$(this).tabs('add', params.tab);
							$(this).tabs('loadTabIframe', {
								'which' : params.tab.title,
								'iframe' : params.iframe
							});
						});
					},
					/**
					 * 更新tab的iframe内容
					 * 
					 * @param {jq
					 *            Object} jq [description]
					 * @param {Object}
					 *            params [description]
					 * @return {jq Object} [description]
					 */
					updateIframeTab : function(jq, params) {
						return jq
								.each(function() {
									params.iframe = params.iframe || {};
									if (!params.iframe.src) {
										var $tab = $(this).tabs('getTab',
												params.which);
										if ($tab == null)
											return;
										var $tabBody = $tab.panel('body');
										var $iframe = $tabBody.find('iframe');
										if ($iframe.length === 0)
											return;
										$.extend(params.iframe, {
											'src' : $iframe.attr('src')
										});
									}
									$(this).tabs('loadTabIframe', params);
								});
					}
				});
