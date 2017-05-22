function updateInputClss() {
	var its = $(":text");
	var ars = $("textarea");
	ars.addClass("area-cls");
	its.addClass('input-cls');
}
/**
 * 当前页面继承父级页面的css样式表， 父级如果没有选择其他的主题，则为默认主题样式
 * 
 */
function superCss() {
	var defaultCss = hostName + "/Resources/Themes/default/easyui.css";

	// var locationPath = "http://" + window.location.host + "/"
	+window.location.pathname.split("/")[1] + "/";
	var link = '<link href="' + defaultCss
			+ '" type="text/css" rel="Stylesheet" class="ui-theme" />';
	$("head").append($(link));
	inputBindKeyEnter();
}

/**
 * 加载成功后，清空选中行状态。
 */
function onLoadSuccess(row, data) {
	$.messager.progress("close");
	if (typeof (clearSelected) == "function")
		clearSelected();
}
/**
 * 加载失败显示
 */
function onLoadError(data) {
	$.messager.progress("close");
	debugger;
	onLoadError(data, "查询提示");
}
/**
 * 加载失败显示
 */
function onLoadError(data, title) {
	title = title == "parsererror" ? "查询提示" : title;
	$.messager.progress("close");
	var d = data.responseText;
	var json = eval('(' + d + ')');
	if (json.message != null && json.message != "undefined") {
		if (json.message.indexOf("登录") > 0)
			$.messager.confirm('登录提示', json.message, function(r) {
				showSmallLogin();
			});
		else
			$.messager.alert(title, json.message, 'error');
	} else {
		$.messager.alert(title, '访问失败！', 'error');
	}

}
/**
 * ajax请求后异常错误
 */
function ajaxError(data, title) {
	title = (title == "parsererror" ? "查询提示" : title);
	$.messager.progress('close');
	data = data.responseText;
	data = eval('(' + data + ')');
	if (data.message != null && data.message != "undefined") {
		if (data.message.indexOf("请重新登录") > 0)
			$.messager.confirm('登录提示', data.message, function(r) {
				showSmallLogin();
			});
		else
			$.messager.alert(title, data.message, 'error');
	} else {
		$.messager.alert(title, "访问服务器失败！", 'error');
	}
}

// 继承esayui的editors，扩展type=combogrid
$
		.extend(
				$.fn.datagrid.defaults.editors,
				{
					combogrid : {
						init : function(container, options) {
							var input = $(
									'<input type="text" class="datagrid-editable-input">')
									.appendTo(container);
							input.combogrid(options);
							return input;

						},
						getValue : function(target) {
							return $(target).combogrid('getValue');
						},
						setValue : function(target, value) {
							$(target).combogrid('setValue', value);
						},
						resize : function(target, width) {
							var input = $(target);
							if ($.boxModel == true) {
								input.width(width
										- (input.outerWidth() - input.width()));
							} else {
								input.width(width);
							}
						}
					}
				});
// select combox
$.extend($.fn.datagrid.defaults.editors, {
	select : {
		init : function(container, options) {
			var input = $('<select  class="easyui-combobox">').appendTo(
					container);
			return input.combobox(options);
		},
		destroy : function(target) {
			$(target).combobox('destroy');
		},
		getValue : function(target) {
			return $(target).combobox('getValue');
		},
		setValue : function(target, value) {
			$(target).combobox('setValue', value);
		},
		resize : function(target, width) {
			$(target).combobox('resize', width);
		}
	}
});

/**
 * 将Date对象 或string(标准格式yyyy-MM-dd hh:mm:ss) 格式化为yyyy-MM-dd
 */
function dateFormatterYMD(pdate) {
	var cdate;
	if (typeof (pdate) == "string") {
		// 浏览器兼容性处理，只处理年月日
		// 施建龙
		// 2013年6月6日12:04:13
		// date = new Date(date);
		cdate = new Date();
		// EDIT BY 贾渊博 HRPCOMMERCIALBUG-127
		// pdate = pdate.replace("/", "-");
		while (pdate.indexOf("/") > 0) {
			pdate = pdate.replace("/", "-");
		}
		// EDIT END
		pdate = pdate.substr(0, pdate.indexOf(" "));
		str = pdate.split('-');
		cdate.setUTCFullYear(str[0], str[1] - 1, str[2]);

	} else {
		cdate = pdate;
	}
	var y = cdate.getFullYear();
	var m = cdate.getMonth() + 1;
	var d = cdate.getDate();
	return y + '-' + (m < 10 ? ('0' + m) : m) + '-' + (d < 10 ? ('0' + d) : d);
}

/**
 * 将Date对象 或string(标准格式yyyy-MM-dd hh:mm:ss) 格式化为yyyy-MM
 */
function dateFormatterYM(pdate) {
	var cdate;
	if (typeof (pdate) == "string") {
		// 浏览器兼容性处理，只处理年月日
		// 施建龙
		// 2013年6月6日12:04:13
		// date = new Date(date);
		cdate = new Date();
		// EDIT BY 贾渊博 HRPCOMMERCIALBUG-127
		// pdate = pdate.replace("/", "-");
		while (pdate.indexOf("/") > 0) {
			pdate = pdate.replace("/", "-");
		}
		// EDIT END
		pdate = pdate.substr(0, pdate.indexOf(" "));
		str = pdate.split('-');
		cdate.setUTCFullYear(str[0], str[1] - 1, str[2]);
	} else {
		cdate = pdate;
	}
	var y = cdate.getFullYear();
	var m = cdate.getMonth() + 1;
	return y + '-' + (m < 10 ? ('0' + m) : m);
}

/**
 * input标签id以“search_”开始的标签，都绑定了回车事件，执行onQuery()
 */
function inputBindKeyEnter() {
	$("input[id^='search_']").each(function(i) {
		$(this).bind("keydown", function(e) {
			var key = e.which;
			if (key == 13) {
				if (typeof (onQuery) == "function")
					onQuery();
			}
		});
	});
}
// 只读编辑器，主要用于datagrid需要展示，但不可编辑的内容
$.extend($.fn.datagrid.defaults.editors, {
	readonlyText : {
		init : function(container, options) {
			var input = $('<input type="text" readonly = "readonly">')
					.appendTo(container);
			return input;
		},
		getValue : function(target) {
			return $(target).val();
		},
		setValue : function(target, value) {
			$(target).val(value);
		},
		resize : function(target, width) {
			var input = $(target);
			if ($.boxModel == true) {
				input.width(width - (input.outerWidth() - input.width()));
			} else {
				input.width(width);
			}
		}
	}
});