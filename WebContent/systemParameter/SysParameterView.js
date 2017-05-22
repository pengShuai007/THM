/*****************系统参数配置********************/
var action = "../systemParameter/action/SysParameterActionC.jspx?loc=c&op=";
var comboBoxActionUrlNew = "/APP/APP/Comm/Handler/ComboBoxHandler.jspx?pubmodule=1&op=";
var textArray = new Array();
var textareaArray = new Array();
var comboArray = new Array();
var radioArray = new Array();
var checkArray = new Array();
var datetimeboxArray = new Array();
var dateboxArray=new Array();
var datemonthArray=new Array();
var htmlStr = "";
var tableId = "";
var radioOrCheck = "";
var errorName = "";
var allTabs;
var isLoadTabs = new Array();
var militaryIncomePercent = "";
var westernConvertPercent = "";
var chineseConvertPercent = "";
var curSys = "";
var curSelectedSys = ""; //当前选择的系统
var curSelectedSysId = ""; //当前选择的系统ID
var curTabTitle = ""; //当前选中的tab页标题即业务代码BUSINESS_CODE
var systemParam = ""; //获取页面传来的参数值
var starttime="";
var paramCount = 0;
//Edit Start KYEEAPP-1017 区分是云还是端发送的请求  hemeng 2014年11月20日16:04:34
var CurrentSource="C";
//Edit End KYEEAPP-1017 区分是云还是端发送的请求  hemeng 2014年11月20日16:04:34
//初始化页面
$(document).ready(function () {
    // 继承父级页面样式表           
    superCss();
    initTabs();
    //initSysListTree();
});

//初始化页面tabs
function initTabs() {
	$.messager.progress({ text: "正在加载，请稍候。。。" });
    $("#tt").tabs({
        tabPosition: 'left',
        fit: true
    });
    $.ajax({
        type: 'POST',
        url: action + 'GetAllSysParamOrderBySys',
        dataType: 'json',
        success: function (data) {
            if (data.success) {
                obj = data.data;
                createBusiness(obj);
                initZuJianValue();
                $.messager.progress('close');
            }
            else {
            	$.messager.progress('close');
                $.messager.alert('加载提示', '加载列表失败！' , 'warning');
            }
        },
        error: function (data) {
        	$.messager.progress('close');
            $.messager.alert('加载提示', '加载列表失败！' , 'warning');
        }
    });
}

//创建业务分类tab
function createBusiness(obj){
    allTabs = obj;
    var i = 0;
    for (var key in obj) {
        if (i == 0) {
            htmlStr = "<table id=\"" + key + "\">";
            creatContentHtmlStr(obj[key]);
            htmlStr = htmlStr + "</tr>";
            htmlStr = htmlStr + "</table>";
            isLoadTabs.push(key);
        }
        $("#tt").tabs('add', {
            title: key,
            content: htmlStr
        });
        htmlStr = "";
        i++;
        //初始化控件的值  
    }
    //    $('#tt').tabs('disableTab', '系统');
    $("#tt").tabs({ onSelect: function (title) {
        tableId = title;
        $.messager.progress({ text: "正在加载，请稍候。。。" });
        textArray = new Array();
        textareaArray = new Array();
        comboArray = new Array();
        radioArray = new Array();
        checkArray = new Array();
        htmlStr = "";
        radioOrCheck = "";
        errorName = "";
        militaryIncomePercent = "";
        var tempObj = allTabs[title];
        htmlStr = "<table id=\"" + title + "\">";
        creatContentHtmlStr(tempObj);
        htmlStr = htmlStr + "</tr>";
        htmlStr = htmlStr + "</table>";
        isLoadTabs.push(title);
        var tab = $('#tt').tabs('getSelected');
        $("#tt").tabs('update', {
            tab: tab,
            options: {
                title: title,
                content: htmlStr
            }
        });
        initZuJianValue();
        $.messager.progress('close');
    }
    });

}

//创建个业务分类tab对应的panel
function creatContentHtmlStr(obj) {
    for (var i = 0; i < obj.length; i++) {
        paramCount = i + 1;
        getHtmlStr(obj[i]);
    }
}
//edit add KYEEAPP-1018 s级参数不可修改，a级参数可以修改  罗代华 2014年11月17日16:53:58
function getHtmlStr(obj) {
    curTabTitle = obj.BUSINESS_CODE;
    if (paramCount % 3 == 1) {
//        if (obj.IS_DISPLAY == 1) {
    	htmlStr = htmlStr + "<tr id=\"t" + paramCount + "\">";
//        } else {
//            htmlStr = htmlStr + "<tr style=\"display:none;\" id=\"t" + paramCount + "\">";
//        }

        htmlStr = htmlStr + "<td id=\"edit_" + obj.SYS_PARA_ID + "_id\" style=\"display:none; \">" + obj.SYS_PARA_ID + "</td>";
        htmlStr = htmlStr + "<td style=\"width:100px;\">";
        htmlStr = htmlStr + "<a href='#' tooltip='" + (obj.PARA_DESC == null ? obj.PARA_NAME : obj.PARA_DESC) + "' class='easyui-tips'>" + obj.PARA_NAME + "</a>";
        htmlStr = htmlStr + "</td>";
        //初始化控件
        htmlStr = htmlStr + "<td style=\"width:180px;\">";
        //文本框形式
        if ($.trim(obj.CONTROL_TYPE.toUpperCase()) == $.trim("TEXT")) {
            if (obj.PARA_TYPE == 'S')
                htmlStr = htmlStr + "<input id=\"edit_" + obj.PARA_CODE + "_TEXT\" disabled = \"disabled\" class=\"easyui-validatebox\" type=\"text\" value=\"" + obj.PARA_VALUE + "\" />";
            else
                htmlStr = htmlStr + "<input id=\"edit_" + obj.PARA_CODE + "_TEXT\" class=\"easyui-validatebox\" type=\"text\" value=\"" + obj.PARA_VALUE + "\" />";
            textArray.push(obj);
        } else if ($.trim(obj.CONTROL_TYPE.toUpperCase()) == $.trim("MTEXT")) {
        	if (obj.PARA_TYPE == 'S')
                htmlStr = htmlStr + "<textarea id=\"edit_" + obj.PARA_CODE + "_TEXT\" disabled = \"disabled\" cols=\"19\" type=\"text\" rows=\"2\" ></textarea>";
            else
                htmlStr = htmlStr + "<textarea id=\"edit_" + obj.PARA_CODE + "_TEXT\" cols=\"19\" type=\"text\" rows=\"2\" ></textarea>";
            textArray.push(obj);
        } else if ($.trim(obj.CONTROL_TYPE.toUpperCase()) == $.trim("DROPDOWN")) {
        	if (obj.PARA_TYPE == 'S')
                htmlStr = htmlStr + "<input id=\"edit_" + $.trim(obj.PARA_CODE) + "_DROPDOWN\"disabled = \"disabled\" class=\"easyui-combobox\"  type=\"text\" />";
            else
                htmlStr = htmlStr + "<input id=\"edit_" + $.trim(obj.PARA_CODE) + "_DROPDOWN\" class=\"easyui-combobox\"  type=\"text\" />";
            comboArray.push(obj);
        } else if ($.trim(obj.CONTROL_TYPE.toUpperCase()) == $.trim("RADIO")) {
            radioOrCheck = "RADIO";
            GetRadioValueHtml(obj);
            radioArray.push(obj);
        } else if ($.trim(obj.CONTROL_TYPE.toUpperCase()) == $.trim("CHECKS")) {
            radioOrCheck = "CHECK";
            GetRadioValueHtml(obj);
            checkArray.push(obj);
        } else if ($.trim(obj.CONTROL_TYPE.toUpperCase()) == $.trim("BUTTON")) {
            htmlStr += " <a href=\"javascript:window.open('" + obj.CONTENT_HREF_LINK + "','" + obj.PARA_NAME + "')\" class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-search'\">点击查看</a>";
            checkArray.push(obj);
        } else if ($.trim(obj.CONTROL_TYPE.toUpperCase()) == $.trim("TIMEBOX")) {
        	if (obj.PARA_TYPE == 'S')
                htmlStr = htmlStr + "<input id=\"edit_" + $.trim(obj.PARA_CODE) + "_TIMEBOX\" disabled = \"disabled\" class=\"easyui-datetimebox\" style='width:150px;' />";
            else
                htmlStr = htmlStr + "<input id=\"edit_" + $.trim(obj.PARA_CODE) + "_TIMEBOX\" class=\"easyui-datetimebox\" />";
            datetimeboxArray.push(obj);
        } else if ($.trim(obj.CONTROL_TYPE.toUpperCase()) == $.trim("DATEBOX")) {
        	if (obj.PARA_TYPE == 'S')
                htmlStr = htmlStr + "<input id=\"edit_" + $.trim(obj.PARA_CODE) + "_DATEBOX\" disabled = \"disabled\" class=\"easyui-datebox\" style='width:150px;' />";
            else
                htmlStr = htmlStr + "<input id=\"edit_" + $.trim(obj.PARA_CODE) + "_DATEBOX\" class=\"easyui-datebox\" />";
            dateboxArray.push(obj);
        } else if ($.trim(obj.CONTROL_TYPE.toUpperCase()) == $.trim("DATEMONTH")) {//判断年月
        	if (obj.PARA_TYPE == 'S')
                htmlStr = htmlStr + "<input id=\"edit_" + $.trim(obj.PARA_CODE) + "_DATEMONTH\" disabled = \"disabled\" class=\"easyui-datebox\" style='width:150px;' data-options=\"formatter:dateFormatter\" />";
        	else
                htmlStr = htmlStr + "<input id=\"edit_" + $.trim(obj.PARA_CODE) + "_DATEMONTH\" class=\"easyui-datebox\" data-options=\"formatter:dateFormatter\" />";
        	datemonthArray.push(obj);
        }
        else {
            htmlStr = htmlStr + "<input id=\"edit_" + obj.PARA_CODE + "_TEXT\" class=\"easyui-validatebox\" type=\"text\" value=\"" + obj.PARA_VALUE + "\"   />";
        }
        htmlStr = htmlStr + "</td><td style=\"width:80px;\"></td>";
    }
    if (paramCount % 3 == 2) {
        htmlStr = htmlStr + "<td id=\"edit_" + obj.SYS_PARA_ID + "_id\" style=\"display:none; \">" + obj.SYS_PARA_ID + "</td>";
        htmlStr = htmlStr + "<td style=\"width:100px;\">";
        htmlStr = htmlStr + "<a href='#' tooltip='" + (obj.PARA_DESC == null ? obj.PARA_NAME : obj.PARA_DESC) + "' class='easyui-tips'>" + obj.PARA_NAME + "</a>";
        htmlStr = htmlStr + "</td>";
        htmlStr = htmlStr + "<td style=\"width:180px;\">";
        if ($.trim(obj.CONTROL_TYPE.toUpperCase()) == $.trim("TEXT")) {
            if (obj.PARA_TYPE == 'S')
                htmlStr = htmlStr + "<input id=\"edit_" + obj.PARA_CODE + "_TEXT\" disabled = \"disabled\" class=\"easyui-validatebox\" type=\"text\" value=\"" + obj.PARA_VALUE + "\" />";
            else
                htmlStr = htmlStr + "<input id=\"edit_" + obj.PARA_CODE + "_TEXT\" class=\"easyui-validatebox\" type=\"text\" value=\"" + obj.PARA_VALUE + "\" />";
            textArray.push(obj);
        } else if ($.trim(obj.CONTROL_TYPE.toUpperCase()) == $.trim("MTEXT")) {
        	if (obj.PARA_TYPE == 'S')
                htmlStr = htmlStr + "<textarea id=\"edit_" + obj.PARA_CODE + "_TEXT\" disabled = \"disabled\" cols=\"19\" type=\"text\" rows=\"2\" ></textarea>";
            else
                htmlStr = htmlStr + "<textarea id=\"edit_" + obj.PARA_CODE + "_TEXT\" cols=\"19\" type=\"text\" rows=\"2\" ></textarea>";
            textArray.push(obj);
        } else if ($.trim(obj.CONTROL_TYPE.toUpperCase()) == $.trim("DROPDOWN")) {
        	if (obj.PARA_TYPE == 'S')
                htmlStr = htmlStr + "<input id=\"edit_" + $.trim(obj.PARA_CODE) + "_DROPDOWN\"disabled = \"disabled\" class=\"easyui-combobox\"  type=\"text\" />";
            else
                htmlStr = htmlStr + "<input id=\"edit_" + $.trim(obj.PARA_CODE) + "_DROPDOWN\" class=\"easyui-combobox\"  type=\"text\" />";
            comboArray.push(obj);
        } else if ($.trim(obj.CONTROL_TYPE.toUpperCase()) == $.trim("RADIO")) {
            radioOrCheck = "RADIO";
            GetRadioValueHtml(obj);
            radioArray.push(obj);
        } else if ($.trim(obj.CONTROL_TYPE.toUpperCase()) == $.trim("CHECKS")) {
            radioOrCheck = "CHECK";
            GetRadioValueHtml(obj);
            checkArray.push(obj);
        } else if ($.trim(obj.CONTROL_TYPE.toUpperCase()) == $.trim("BUTTON")) {
            htmlStr += " <a href=\"javascript:window.open('" + obj.CONTENT_HREF_LINK + "','" + obj.PARA_NAME + "')\" class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-search'\">点击查看</a>";
            checkArray.push(obj);
        } else if ($.trim(obj.CONTROL_TYPE.toUpperCase()) == $.trim("TIMEBOX")) {
        	if (obj.PARA_TYPE == 'S')
                htmlStr = htmlStr + "<input id=\"edit_" + $.trim(obj.PARA_CODE) + "_TIMEBOX\" disabled = \"disabled\" class=\"easyui-datetimebox\" style='width:150px;' />";
            else
                htmlStr = htmlStr + "<input id=\"edit_" + $.trim(obj.PARA_CODE) + "_TIMEBOX\" class=\"easyui-datetimebox\" />";
            datetimeboxArray.push(obj);
        } else if ($.trim(obj.CONTROL_TYPE.toUpperCase()) == $.trim("DATEBOX")) {
        	if (obj.PARA_TYPE == 'S')
                htmlStr = htmlStr + "<input id=\"edit_" + $.trim(obj.PARA_CODE) + "_DATEBOX\" disabled = \"disabled\" class=\"easyui-datetimebox\" style='width:150px;' />";
            else
                htmlStr = htmlStr + "<input id=\"edit_" + $.trim(obj.PARA_CODE) + "_DATEBOX\" class=\"easyui-datetimebox\" />";
            dateboxArray.push(obj);
        } else if ($.trim(obj.CONTROL_TYPE.toUpperCase()) == $.trim("DATEMONTH")) {//判断年月
        	if (obj.PARA_TYPE == 'S')
                htmlStr = htmlStr + "<input id=\"edit_" + $.trim(obj.PARA_CODE) + "_DATEMONTH\" disabled = \"disabled\" class=\"easyui-datebox\" style='width:150px;' data-options=\"formatter:dateFormatter\" />";
        	else
                htmlStr = htmlStr + "<input id=\"edit_" + $.trim(obj.PARA_CODE) + "_DATEMONTH\" class=\"easyui-datebox\" data-options=\"formatter:dateFormatter\" />";
            datemonthArray.push(obj);
        }
        else {
            htmlStr = htmlStr + "<input id=\"edit_" + obj.PARA_CODE + "_TEXT\" class=\"easyui-validatebox\" type=\"text\" value=\"" + obj.PARA_VALUE + "\"   />";
        }
        htmlStr = htmlStr + "</td><td style=\"width:80px;\"></td>";
    }
    if (paramCount % 3 == 0) {
        htmlStr = htmlStr + "<td id=\"edit_" + obj.SYS_PARA_ID + "_id\" style=\"display:none; \">" + obj.SYS_PARA_ID + "</td>";
        htmlStr = htmlStr + "<td style=\"width:100px;\">";
        htmlStr = htmlStr + "<a href='#' tooltip='" + (obj.PARA_DESC == null ? obj.PARA_NAME : obj.PARA_DESC) + "' class='easyui-tips'>" + obj.PARA_NAME + "</a>";
        htmlStr = htmlStr + "</td>";
        htmlStr = htmlStr + "<td style=\"width:180px;\">";
        if ($.trim(obj.CONTROL_TYPE.toUpperCase()) == $.trim("TEXT")) {
            if (obj.PARA_TYPE == 'S')
                htmlStr = htmlStr + "<input id=\"edit_" + obj.PARA_CODE + "_TEXT\" disabled = \"disabled\" class=\"easyui-validatebox\" type=\"text\" value=\"" + obj.PARA_VALUE + "\" />";
            else
                htmlStr = htmlStr + "<input id=\"edit_" + obj.PARA_CODE + "_TEXT\" class=\"easyui-validatebox\" type=\"text\" value=\"" + obj.PARA_VALUE + "\" />";
            textArray.push(obj);
        } else if ($.trim(obj.CONTROL_TYPE.toUpperCase()) == $.trim("MTEXT")) {
        	if (obj.PARA_TYPE == 'S')
                htmlStr = htmlStr + "<textarea id=\"edit_" + obj.PARA_CODE + "_TEXT\" disabled = \"disabled\" cols=\"19\" type=\"text\" rows=\"2\" ></textarea>";
            else
                htmlStr = htmlStr + "<textarea id=\"edit_" + obj.PARA_CODE + "_TEXT\" cols=\"19\" type=\"text\" rows=\"2\" ></textarea>";
            textArray.push(obj);
        } else if ($.trim(obj.CONTROL_TYPE.toUpperCase()) == $.trim("DROPDOWN")) {
        	if (obj.PARA_TYPE == 'S')
                htmlStr = htmlStr + "<input id=\"edit_" + $.trim(obj.PARA_CODE) + "_DROPDOWN\"disabled = \"disabled\" class=\"easyui-combobox\"  type=\"text\" />";
            else
                htmlStr = htmlStr + "<input id=\"edit_" + $.trim(obj.PARA_CODE) + "_DROPDOWN\" class=\"easyui-combobox\"  type=\"text\" />";
            comboArray.push(obj);
        } else if ($.trim(obj.CONTROL_TYPE.toUpperCase()) == $.trim("RADIO")) {
            radioOrCheck = "RADIO";
            GetRadioValueHtml(obj);
            radioArray.push(obj);
        } else if ($.trim(obj.CONTROL_TYPE.toUpperCase()) == $.trim("CHECKS")) {
            radioOrCheck = "CHECK";
            GetRadioValueHtml(obj);
            checkArray.push(obj);
        } else if ($.trim(obj.CONTROL_TYPE.toUpperCase()) == $.trim("BUTTON")) {
            htmlStr += " <a href=\"javascript:window.open('" + obj.CONTENT_HREF_LINK + "','" + obj.PARA_NAME + "')\" class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-search'\">点击查看</a>";
            checkArray.push(obj);
        } else if ($.trim(obj.CONTROL_TYPE.toUpperCase()) == $.trim("TIMEBOX")) {
        	if (obj.PARA_TYPE == 'S')
                htmlStr = htmlStr + "<input id=\"edit_" + $.trim(obj.PARA_CODE) + "_TIMEBOX\" disabled = \"disabled\" class=\"easyui-datetimebox\" style='width:150px;' />";
            else
                htmlStr = htmlStr + "<input id=\"edit_" + $.trim(obj.PARA_CODE) + "_TIMEBOX\" class=\"easyui-datetimebox\" />";
            datetimeboxArray.push(obj);
        } else if ($.trim(obj.CONTROL_TYPE.toUpperCase()) == $.trim("DATEBOX")) {
        	if (obj.PARA_TYPE == 'S')
                htmlStr = htmlStr + "<input id=\"edit_" + $.trim(obj.PARA_CODE) + "_DATEBOX\" disabled = \"disabled\" class=\"easyui-datebox\" style='width:150px;' />";
            else
                htmlStr = htmlStr + "<input id=\"edit_" + $.trim(obj.PARA_CODE) + "_DATEBOX\" class=\"easyui-datebox\" />";
            dateboxArray.push(obj);
        } else if ($.trim(obj.CONTROL_TYPE.toUpperCase()) == $.trim("DATEMONTH")) {//判断年月
        	if (obj.PARA_TYPE == 'S')
                htmlStr = htmlStr + "<input id=\"edit_" + $.trim(obj.PARA_CODE) + "_DATEMONTH\" disabled = \"disabled\" class=\"easyui-datebox\" style='width:150px;' data-options=\"formatter:dateFormatter\" />";
        	else
                htmlStr = htmlStr + "<input id=\"edit_" + $.trim(obj.PARA_CODE) + "_DATEMONTH\" class=\"easyui-datebox\" data-options=\"formatter:dateFormatter\" />";
            datemonthArray.push(obj);
        }
        else {
            htmlStr = htmlStr + "<input id=\"edit_" + obj.PARA_CODE + "_TEXT\" class=\"easyui-validatebox\" type=\"text\" value=\"" + obj.PARA_VALUE + "\"   />";
        }        
        htmlStr = htmlStr + "</td>";
        htmlStr = htmlStr + "</tr>";
    }
}

//初始化各控件，并赋值
function initZuJianValue() {
    //text类型赋值
    if (textArray.length > 0) {
        for (var i = 0; i < textArray.length; i++) {
            $("#edit_" + textArray[i].PARA_CODE + "_TEXT").val(textArray[i].PARA_VALUE);
        }
    }
    if (datetimeboxArray.length > 0) {
    	for (var i = 0; i < datetimeboxArray.length; i++) {
    		$("#edit_" + datetimeboxArray[i].PARA_CODE + "_TIMEBOX").datetimebox('setValue',datetimeboxArray[i].PARA_VALUE);
    	}
    }
    //edit start HRPCOMMDEVJAVA-165 添加系统参数对日期类型的支持 houhy 2014-8-12 19:39:19
    if(dateboxArray.length>0){
    	for (var i = 0; i < dateboxArray.length; i++) {
        	$("#edit_" + dateboxArray[i].PARA_CODE + "_DATEBOX").datebox('setValue',dateboxArray[i].PARA_VALUE);
        }
    }
    if(datemonthArray.length>0){
    	for (var i = 0; i < datemonthArray.length; i++) {
        	$("#edit_" + datemonthArray[i].PARA_CODE + "_DATEMONTH").datebox('setValue',datemonthArray[i].PARA_VALUE);
        }
    }
  //edit end HRPCOMMDEVJAVA-165 添加系统参数对日期类型的支持 houhy 2014-8-12 19:39:19
    //combobox类型赋值
    if (comboArray.length > 0) {
        for (var i = 0; i < comboArray.length; i++) {
            initCombobox(comboArray[i]);
            var dataArray = new Array();
            var tempValue = new Array();
            if (comboArray[i].PARA_VALUE == "" || comboArray[i].PARA_VALUE == null || $.trim(comboArray[i].PARA_VALUE.toString()) == "undefined") {
                tempValue = "";
            } else {
                if (comboArray[i].PARA_VALUE.indexOf(",") < 0) {
                    tempValue[0] = comboArray[i].PARA_VALUE;
                } else {
                    tempValue = comboArray[i].PARA_VALUE.split(',');
                }
            }
            if (comboArray[i].IF_MULTI_CHECK == 1) {
                for (var j = 0; j < tempValue.length; j++) {
                    dataArray.push(tempValue[j]);
                }
                $("#edit_" + $.trim(comboArray[i].PARA_CODE) + "_DROPDOWN").combobox('setValues', dataArray);
            } else {
                if (comboArray[i].PARA_VALUE == "" || comboArray[i].PARA_VALUE == null || $.trim(comboArray[i].PARA_VALUE.toString()) == "undefined") {
                    $("#edit_" + $.trim(comboArray[i].PARA_CODE) + "_DROPDOWN").combobox('setValue', "");
                } else {
                    $("#edit_" + $.trim(comboArray[i].PARA_CODE) + "_DROPDOWN").combobox('setValue', comboArray[i].PARA_VALUE);
                }
            }
        }
    }
    //radio类型赋值
    if (radioArray.length > 0) {
        for (var i = 0; i < radioArray.length; i++) {
            $("input[name='" + radioArray[i].PARA_CODE + "'][value=" + radioArray[i].PARA_VALUE + "]").attr({ 'checked': 'checked' });
        }
    }

    //checkbox类型赋值
    if (checkArray.length > 0) {
        for (var i = 0; i < checkArray.length; i++) {
            var r = document.getElementsByName(checkArray[i].PARA_CODE);
            var temValue = new Array();
            if (checkArray[i].PARA_VALUE == "" || checkArray[i].PARA_VALUE == null || checkArray[i].PARA_VALUE == undefined) {
                temValue[0] = "";
            } else {
                if (checkArray[i].PARA_VALUE.split(',').length < 0) {
                    temValue[0] = checkArray[i].PARA_VALUE;
                } else {
                    temValue = checkArray[i].PARA_VALUE.split(',');
                }
            }
            for (var j = 0; j < r.length; j++) {
                if (temValue.length < 0) {
                    r[j].value = "";
                    continue;
                } else {
                    for (var k = 0; k < temValue.length; k++) {
                        if (r[j].value == temValue[k]) {
                            r[j].checked = true;
                        }
                    }
                }
            }
        }
    }
}


//初始化combobox类型的参数
function initCombobox(obj) {
    var whereSen = "";
    var orderSen = "";
    var muti = false;
    if (obj.IF_MULTI_CHECK == 1) {
        muti = true;
    }

    if (obj.IF_HAVE_SOURCE == 2) {
        var item = obj.PARA_VALUES.split(',');
        var data = new Array();
        for (var i = 0; i < item.length; i++) {
            var temp = item[i].split(':');
            var tempJson = { "id": temp[0], "text": temp[1] };
            data.push(tempJson);
        }
        $("#edit_" + $.trim(obj.PARA_CODE) + "_DROPDOWN").combobox({
            url: "",
            valueField: 'id',
            textField: 'text',
            multiple: muti,
            data: data
        });
    } else {

        if (obj.FILTER != null && obj.FILTER != undefined && obj.FILTER != "") {
            if ($.trim(obj.FILTER).toUpperCase().indexOf("ORDER") == 0) {
                orderSen = obj.FILTER.split(' ')[2];
            } else {
                if ($.trim(obj.FILTER).toUpperCase().indexOf("AND") > 0) {
                    whereSen = " AND " + obj.FILTER;
                } else {
                	//Edit start KYEEAPP-1061 增加空格 hemeng 2014年11月28日10:46:45
                    whereSen = " "+ obj.FILTER;
                    //Edit end KYEEAPP-1061 增加空格 hemeng 2014年11月28日10:46:45
                }
            }
        } else {
            orderSen = obj.VALUE_COLUMN;
        }
        $("#edit_" + $.trim(obj.PARA_CODE) + "_DROPDOWN").combobox({
            url: comboBoxActionUrlNew + "DoGetComboBox",
            valueField: 'id',
            textField: 'text',
            multiple: muti,
            onBeforeLoad: function (param) {
                param.table = $.trim(obj.SOURCE_TABLE);
                param.id = $.trim(obj.VALUE_COLUMN);
                param.text = $.trim(obj.DISPLAY_COLUMN);
                if (param.id != param.text)
                    param.isShowIdAndText = true;
                param.where = whereSen;
                param.sort = orderSen;
                param.order = " ASC ";
            }
        });
    }
}
//并对内容进行校验66666666666666
//获取tab数据
function onSaveParam() {
    var table1 = document.getElementById(tableId);
    var rows = table1.rows;
    var paramStr = new Array();
    var paramValue = "";
    var obj = "";
    var data = "";
    for (var i = 0; i < rows.length; i++) {
        //var paramId = rows[i].cells[0].innerHTML;
        //获取ID列表
        var paramIdList = getParamIdList(rows[i]);
        //获取参数值所在列对应的html文本
        paramStr = getParamValueList(rows[i]);
        for (var strIndex = 0; strIndex < paramStr.length; strIndex++) {
            //获取具有参数值的列的id
            var tdID = getTdId(paramStr[strIndex]);
            //获取该列对应的控件的类型
            var idType = getIdType(tdID);
            var tempValue = new Array();
            if (idType == $.trim("TEXT")) {
                paramValue = "";
                paramValue = $("#" + tdID).val();
            } else if (idType == $.trim("DROPDOWN")) {
                paramValue = "";
                if (checkMuti(paramIdList[strIndex])) {
                    tempValue = $("#" + tdID).combobox('getValues');
                } else {
                    tempValue[0] = $("#" + tdID).combobox('getValue');
                }

                for (var valueIndex = 0; valueIndex < tempValue.length; valueIndex++) {
                    paramValue += tempValue[valueIndex] + ",";
                }
                if (tempValue.length > 0) {
                    paramValue = paramValue.substring(0, paramValue.length - 1);
                }
            } else if (idType == $.trim("RADIO")) {
                var tempName = getRadioName(paramStr[strIndex]);
                paramValue = "";
                paramValue = $("input[name='" + tempName + "']:checked").val();
            } else if (idType == $.trim("CHECKS")) {
                paramValue = "";
                var checkName = getRadioName(paramStr[strIndex]);
                var r = document.getElementsByName(checkName);
                for (var m = 0; m < r.length; m++) {
                    if (r[m].checked) {
                        paramValue = paramValue + r[m].value + ",";
                    }
                }
                paramValue = paramValue.substring(0, paramValue.length - 1);
            }
            //edit start HRPCOMMDEVJAVA-165 添加对日期类型控件的取值 houhy 2014-8-12 18:21:34
            else if(idType==$.trim("DATEBOX")||idType==$.trim("DATEMONTH")){
            	paramValue = "";
                paramValue = $("#" + tdID).datebox('getValue');
            } else if (idType==$.trim("TIMEBOX")) {
            	paramValue = "";
                paramValue = $("#" + tdID).datetimebox('getValue');
            }
          //edit end HRPCOMMDEVJAVA-165 添加对日期类型控件的取值 houhy 2014-8-12 18:21:34
            //拼装json格式的更新字符串，后台有解析
            obj = { "SYS_PARA_ID": paramIdList[strIndex], "PARA_VALUE": paramValue };
          //edit chaoouyang 2014-7-7 13:39:09
          //HRPDRTESTJAVA-565 判断日期格式是否YYYY-MM-DD
            if ((obj.SYS_PARA_ID == starttime) && (!IsDate(paramValue))) {
            	 $.messager.alert('保存提示', "请输入正确格式的日期，如‘YYYY-MM-DD’格式的记账时间！", 'info');
        		return;
        	}
         //edit chaoouyang 2014-7-7 13:39:09
            data += jsonToString(obj) + "|";
        }
    }
    data = { "obj": data.substring(0, data.length - 1),
    		 "PARA_SOURCE":CurrentSource};
    $.messager.progress();
    $.ajax({
        type: 'POST',
        url: action + 'UpdateParam',
        data: data,
        dataType: 'json',
        success: function (data) {
            $.messager.progress('close');
            if (data.success) {
            	$.messager.alert('保存提示', "保存成功！", 'info');               
            } else {
                $.messager.alert('保存提示', data.message, 'error');
            }
        },
        error: function (data) {
            $.messager.progress('close');
            ajaxError(data, "保存失败");
        }
    });
}

//获取td id
function getTdId(tdStr) {


    // 调用示例
    var myos = appInfo();
    // 如果当前浏览器是IE，弹出浏览器版本,否则弹出当前浏览器名称和版本
    if (myos.msie) {
        if (myos.version < 9.0) {
            var idIndex = tdStr.indexOf("edit");
            var temStr = tdStr.substring(idIndex, tdStr.length - idIndex);
            var resultStr = temStr.split(' ');
            if (resultStr[0].indexOf('\"') > 0) {
                resultStr[0] = resultStr[0].substring(0, resultStr.indexOf('\"'));
            }

            return resultStr[0];
        } else {
            var idIndex = tdStr.indexOf("id");
            var tempStr = tdStr.substring(idIndex, tdStr.length - idIndex);
            var resultStr = tempStr.split('"');
            return resultStr[1];
        }
    } else {
        var idIndex = tdStr.indexOf("id");
        var tempStr = tdStr.substring(idIndex, tdStr.length - idIndex);
        var resultStr = tempStr.split('"');
        return resultStr[1];
    }
}

//获取控件类型
function getIdType(idStr) {
    return $.trim(idStr.substring(idStr.lastIndexOf("_") + 1, idStr.length));
}

//获取一行的所有参数ID
function getParamIdList(row) {
    var paramIdArray = new Array();

    // 调用示例
    var myos = appInfo();
    // 如果当前浏览器是IE，弹出浏览器版本,否则弹出当前浏览器名称和版本
    if (myos.msie) {
        if (myos.version < 9.0) {
            var tdChild = row.childNodes;
            if (tdChild.length > 8) {
                paramIdArray.push(tdChild[0].innerHTML);
                paramIdArray.push(tdChild[4].innerHTML);
                paramIdArray.push(tdChild[8].innerHTML);
            } else if (tdChild.length > 4) {
                paramIdArray.push(tdChild[0].innerHTML);
                paramIdArray.push(tdChild[4].innerHTML);
            }else {
                paramIdArray.push(tdChild[0].innerHTML);
            }
        } else {
            if (row.childElementCount > 8) {
                paramIdArray.push(row.cells[0].innerHTML);
                paramIdArray.push(row.cells[4].innerHTML);
                paramIdArray.push(row.cells[8].innerHTML);
            }else if (row.childElementCount > 4) {
                paramIdArray.push(row.cells[0].innerHTML);
                paramIdArray.push(row.cells[4].innerHTML);
            }else {
                paramIdArray.push(row.cells[0].innerHTML);
            }
        }
    } else {
    	if (row.childElementCount > 8) {
            paramIdArray.push(row.cells[0].innerHTML);
            paramIdArray.push(row.cells[4].innerHTML);
            paramIdArray.push(row.cells[8].innerHTML);
        }else if (row.childElementCount > 4) {
            paramIdArray.push(row.cells[0].innerHTML);
            paramIdArray.push(row.cells[4].innerHTML);
        }
        else {
            paramIdArray.push(row.cells[0].innerHTML);
        }
    }
    return paramIdArray;
}

//获取参数值所在列对应的html文本
function getParamValueList(row) {
    var paramStrArray = new Array();

    var myos = appInfo();
    // 如果当前浏览器是IE，弹出浏览器版本,否则弹出当前浏览器名称和版本
    if (myos.msie) {
        if (myos.version < 9.0) {
            var tdChild = row.childNodes;
            if (tdChild.length > 8) {
                paramStrArray.push(tdChild[2].innerHTML);
                paramStrArray.push(tdChild[6].innerHTML);
                paramStrArray.push(tdChild[10].innerHTML);
            }else if (tdChild.length > 4) {
                paramStrArray.push(tdChild[2].innerHTML);
                paramStrArray.push(tdChild[6].innerHTML);
            } else {
                paramStrArray.push(tdChild[2].innerHTML);
            }
        } else {
        	if (row.childElementCount > 8) {
                paramStrArray.push(row.cells[2].innerHTML);
                paramStrArray.push(row.cells[6].innerHTML);
                paramStrArray.push(row.cells[10].innerHTML);
            }else if (row.childElementCount > 4) {
                paramStrArray.push(row.cells[2].innerHTML);
                paramStrArray.push(row.cells[6].innerHTML);
            }
            else {
                paramStrArray.push(row.cells[2].innerHTML);
            }
        }
    } else {
    	if (row.childElementCount > 8) {
            paramStrArray.push(row.cells[2].innerHTML);
            paramStrArray.push(row.cells[6].innerHTML);
            paramStrArray.push(row.cells[10].innerHTML);
        }else if (row.childElementCount > 4) {
            paramStrArray.push(row.cells[2].innerHTML);
            paramStrArray.push(row.cells[6].innerHTML);
        }
        else {
            paramStrArray.push(row.cells[2].innerHTML);
        }
    }

    return paramStrArray;
}
//获取创建radio的html
function GetRadioValueHtml(obj) {

    if (obj.IF_HAVE_SOURCE == '1') {
        if (obj.SOURCE_TABLE == null || obj.VALUE_COLUMN == null || obj.DISPLAY_COLUMN == null) {
            errorName += obj.PARA_NAME + ",";
            if (obj.PARA_TYPE == 'S')
                htmlStr = htmlStr + "<input id=\"edit_" + obj.PARA_CODE + "_TEXT\" disabled = \"disabled\" class=\"easyui-validatebox\" type=\"text\" value=\"" + obj.PARA_VALUE + "\"/>";
            else
                htmlStr = htmlStr + "<input id=\"edit_" + obj.PARA_CODE + "_TEXT\" class=\"easyui-validatebox\" type=\"text\" value=\"" + obj.PARA_VALUE + "\"/>";
            return;
        } else if (obj.FILTER == null || obj.FILTER == undefined || obj.FILTER == "null" || obj.FILTER == "") {
            obj.FILTER = "";
        }

        $.ajax({
            url: action + "GetRadioTableValue",
            type: 'POST',
            data: { "obj": jsonToString(obj) },
            dataType: 'json',
            async: false,
            success: function (data) {
                if (data.success) {
                    var retData = data.data;
                    for (var dataIndex = 0; dataIndex < retData.length; dataIndex++) {
                        if (radioOrCheck == "RADIO") {
                        	if (obj.PARA_TYPE == 'S')
                                htmlStr = htmlStr + "<input id=\"edit_" + obj.PARA_CODE + "_" + dataIndex + "_RADIO\" disabled = \"disabled\" type=\"radio\" name=\"" + obj.PARA_CODE + "\" value=\"" + retData[dataIndex].VALUEfIELD + "\"/>" + retData[dataIndex].TEXTFIELD;
                            else
                                htmlStr = htmlStr + "<input id=\"edit_" + obj.PARA_CODE + "_" + dataIndex + "_RADIO\" type=\"radio\" name=\"" + obj.PARA_CODE + "\" value=\"" + retData[dataIndex].VALUEfIELD + "\"/>" + retData[dataIndex].TEXTFIELD;
                        } else if (radioOrCheck == "CHECK") {
                        	if (obj.PARA_TYPE == 'S')
                                htmlStr = htmlStr + "<input id=\"edit_" + obj.PARA_CODE + "_" + dataIndex + "_CHECKS\" disabled = \"disabled\" type=\"checkbox\" name=\"" + obj.PARA_CODE + "\" value=\"" + retData[dataIndex].VALUEfIELD + "\"/>" + retData[dataIndex].TEXTFIELD;
                            else
                                htmlStr = htmlStr + "<input id=\"edit_" + obj.PARA_CODE + "_" + dataIndex + "_CHECKS\" type=\"checkbox\" name=\"" + obj.PARA_CODE + "\" value=\"" + retData[dataIndex].VALUEfIELD + "\"/>" + retData[dataIndex].TEXTFIELD;
                        }
                    }
                }
                else {
                	if (obj.PARA_TYPE == 'S')
                        htmlStr = htmlStr + "<input id=\"edit_" + obj.PARA_CODE + "_TEXT\" disabled = \"disabled\" class=\"easyui-validatebox\" type=\"text\" value=\"" + obj.PARA_VALUE + "\"/>";
                    else
                        htmlStr = htmlStr + "<input id=\"edit_" + obj.PARA_CODE + "_TEXT\" class=\"easyui-validatebox\" type=\"text\" value=\"" + obj.PARA_VALUE + "\"/>";
                }
            },
            error: function () {
            	if (obj.PARA_TYPE == 'S')
                    htmlStr = htmlStr + "<input id=\"edit_" + obj.PARA_CODE + "_TEXT\" disabled = \"disabled\" class=\"easyui-validatebox\" type=\"text\" value=\"" + obj.PARA_VALUE + "\"/>";
                else
                    htmlStr = htmlStr + "<input id=\"edit_" + obj.PARA_CODE + "_TEXT\" class=\"easyui-validatebox\" type=\"text\" value=\"" + obj.PARA_VALUE + "\"/>";
            }
        });
    } else if (obj.IF_HAVE_SOURCE == '2') {
        var tempArray = obj.PARA_VALUES.split(',');
        if (tempArray.length > 0) {
            for (var radioIndex = 0; radioIndex < tempArray.length; radioIndex++) {
                var valueTemp = tempArray[radioIndex].split(':');
                if (radioOrCheck == "RADIO") {
                    if (radioIndex == 0) {
                    	if (obj.PARA_TYPE == 'S')
                            htmlStr = htmlStr + "<input id=\"edit_" + obj.PARA_CODE + "_" + radioIndex + "_RADIO\" disabled = \"disabled\" checked=\"true\" type=\"radio\" name=\"" + obj.PARA_CODE + "\" value=\"" + valueTemp[0] + "\"/>" + valueTemp[1];
                        else
                            htmlStr = htmlStr + "<input id=\"edit_" + obj.PARA_CODE + "_" + radioIndex + "_RADIO\" checked=\"true\" type=\"radio\" name=\"" + obj.PARA_CODE + "\" value=\"" + valueTemp[0] + "\"/>" + valueTemp[1];
                    } else {
                    	if (obj.PARA_TYPE == 'S')
                            htmlStr = htmlStr + "<input id=\"edit_" + obj.PARA_CODE + "_" + radioIndex + "_RADIO\" type=\"radio\" name=\"" + obj.PARA_CODE + "\" disabled = \"disabled\" value=\"" + valueTemp[0] + "\"/>" + valueTemp[1];
                        else
                            htmlStr = htmlStr + "<input id=\"edit_" + obj.PARA_CODE + "_" + radioIndex + "_RADIO\" type=\"radio\" name=\"" + obj.PARA_CODE + "\" value=\"" + valueTemp[0] + "\"/>" + valueTemp[1];
                    }
                } else if (radioOrCheck == "CHECK") {
                	if (obj.PARA_TYPE == 'S')
                        htmlStr = htmlStr + "<input id=\"edit_" + obj.PARA_CODE + "_" + radioIndex + "_CHECKS\" disabled = \"disabled\" type=\"checkbox\" name=\"" + obj.PARA_CODE + "\" value=\"" + valueTemp[0] + "\"/>" + valueTemp[1];
                    else
                        htmlStr = htmlStr + "<input id=\"edit_" + obj.PARA_CODE + "_" + radioIndex + "_CHECKS\" type=\"checkbox\" name=\"" + obj.PARA_CODE + "\" value=\"" + valueTemp[0] + "\"/>" + valueTemp[1];
                }
            }
        }
    } else {
    	if (obj.PARA_TYPE == 'S')
            htmlStr = htmlStr + "<input id=\"edit_" + obj.PARA_CODE + "_TEXT\" class=\"easyui-validatebox\" disabled = \"disabled\" type=\"text\" value=\"" + obj.PARA_VALUE + "\"/>";
        else
            htmlStr = htmlStr + "<input id=\"edit_" + obj.PARA_CODE + "_TEXT\" class=\"easyui-validatebox\" type=\"text\" value=\"" + obj.PARA_VALUE + "\"/>";
    }
}

//获取radio控件对应的name
function getRadioName(paramStr) {
    var nameIndex = paramStr.indexOf("name");
    var tempStr = paramStr.substring(nameIndex, paramStr.length - nameIndex);
    var resultStr = tempStr.split('"');
    var myos = appInfo();
    // 如果当前浏览器是IE，弹出浏览器版本,否则弹出当前浏览器名称和版本
    if (myos.msie) {
        if (myos.version < 9) {
            var valueArray = resultStr[0].split('=');
            return valueArray[1].split('>')[0];
        } else {
            return resultStr[1];
        }
    } else {
        return resultStr[1];
    }
}
//判断是combobox否是可以多选
function checkMuti(sysId) {
    var result = false;
    for (var i = 0; i < comboArray.length; i++) {
        if (sysId == comboArray[i].SYS_PARA_ID) {
            if (comboArray[i].IF_MULTI_CHECK == 1) {
                result = true;
                break;
            }
        }
    }

    return result;
}
//获取系统参数
function processUrlParam(paramStr) {
    var paramCount = paramStr.substring(1, paramStr.length).split('&');
    systemParam = paramCount[0].split('=')[1];
}
/**
判断输入框中输入的日期格式为yyyy-mm-dd和正确的日期
*/
function IsDate(mystring) {
 var reg = /^(\d{4})-(\d{2})-(\d{2})$/;
 var str = mystring;
 if (!reg.test(str)){
  return false;
  }
  return true;
}

//add edit end 2014-7-7 15:39:44
/**
 * 日期格式化
 */
function dateFormatter(date){
	var month = date.getMonth() + 1;
	if(month < 10){
		month = '0' + month;
	}
	return date.getFullYear() + "-" + month;
}
// 获取浏览器名称及版本信息
function appInfo() {
    var browser = {
        msie: false, firefox: false, opera: false, safari: false,
        chrome: false, netscape: false, appname: 'unknown', version: 0
    },
        userAgent = window.navigator.userAgent.toLowerCase();
    if (/(msie|firefox|opera|chrome|netscape)\D+(\d[\d.]*)/.test(userAgent)) {
        browser[RegExp.$1] = true;
        browser.appname = RegExp.$1;
        browser.version = RegExp.$2;
    } else if (/version\D+(\d[\d.]*).*safari/.test(userAgent)) { // safari
        browser.safari = true;
        browser.appname = 'safari';
        browser.version = RegExp.$2;
    }
    return browser;
}