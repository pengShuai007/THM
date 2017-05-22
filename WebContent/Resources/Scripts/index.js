var actionLoginUrl = "RightsSystem/LoginHandler.jspx?op=";
var systemList;
var host = "Resources/Images/main/";
var host404 = "Resources/Images/404.jpg";
var defaultImg = "Resources/Images/Default/";
//后台已经把路径加上，前端不在处理
//施建龙
//2013年10月7日19:12:16
//var uploadImgPath = "Resources/Images/Upload/";
var uploadImgPath = "";
var menuType = 0;
//dom准备就绪后执行
$(document).ready(function () {
    var get = getURL();
    menuType = get["menuType"];

    if (menuType == 1) {
        $("#edit_Pass").css("display", "none");
    } else if (menuType == 2) {
        $("#edit_Pass").css("display", "block");
    } else {
        $("#edit_Pass").css("display", "none");
    }

    ready();
    clockon();
});

function getURL() {
    var aQuery = window.location.href.split("?"); //取得Get参数
    var aGET = new Array();
    if (aQuery.length > 1) {
        var aBuf = aQuery[1].split("&");
        for (var i = 0, iLoop = aBuf.length; i < iLoop; i++) {
            var aTmp = aBuf[i].split("="); //分离key与Value
            aGET[aTmp[0]] = aTmp[1];
        }
    }
    return aGET;
}

function open_Win(){
$("#update_password").window("open");
}

function ready() {
    getCanUseSysList();
}
//获取可用的系统列表
function getCanUseSysList() {
    $.ajax({
        type: "POST",
        url: actionLoginUrl + "getCanUseSys",
        dataType: "json",
        success: function (data) {
            if (data.success) {
                createSystemPng(data);
                getCurrentUser();
            }
            else {
                $.messager.alert("系统列表", data.message, 'error');
            }
        },
        error: function (data) {
            ajaxError(data, "系统列表");
        }
    });
}

//生成页面系统图片
function createSystemPng(canUseSys) {
    canUseSys = canUseSys.data;
    var pngDiv = $("#listSystem");
    for (var i = 0; i < canUseSys.length; i++) {
        var system = canUseSys[i];
        //使用静态内容
        //施建龙
        //2013年6月2日15:00:57
        //var src = actionLoginUrl + "outStreamImg&imgName=";
        var src = uploadImgPath;
        var title = "";
        var imgOnclick = "";
        var divClass = "";          
        var errorImgPath = "";       
//        if (system.HAVE_PERM == 1) {
            src += system.ENABLE_RESOURCE_URL;
            title = system.OPERATION_NAME;
            imgOnclick = "onclick=\"goToMain('" + system.OPERATION_ID + "')\"";
            divClass = "class=\"listSystem-canDiv\""+" style=\"align:center;margin:5px 10px\"";
            //if (system.SUB_SYSTEM == null)
                errorImgPath = host404;   
            //else
            //    errorImgPath = defaultImg + system.SUB_SYSTEM + "_01.png";
//        } else {
//            src += system.DISABLE_RESOURCE_URL;
//            title = "对不起,您无权访问" + system.OPERATION_NAME;
//            divClass = "class=\"listSystem-notCanDiv\"";
//            if (system.SUB_SYSTEM == null)
//                errorImgPath = host404;
//            else
//                errorImgPath = defaultImg + system.SUB_SYSTEM + "_02.png";
//        }
        var img = "<div " + divClass + " > "
        + "<img " + imgOnclick + "  onerror=\"this.src='" + errorImgPath + "';\"  src=\"" + src + "\" alt=\"" + title + "\" title=\"" + title + "\" />"
         + "<div class='sysName'><table><tr><td align=\"center\">" + title + "</td></tr></table></div>"
        + "</div>";
        $(img).appendTo(pngDiv);
    }
}

/*
* 退出
*/
function loginOut() {
    $.messager.confirm('退出提示', '您确定要退出系统吗？', function (r) {
        if (r) {

            $.messager.progress({
                title: "退出提示",
                text: "正在退出,请稍候....",
                interval: 1000
            });
            $.ajax({
                type: "POST",
                url: actionLoginUrl + "loginOut",
                dataType: "json",
                success: function (data) {
                    if (data.success) {
                        $.messager.progress({
                            title: "退出提示",
                            text: "退出成功！",
                            interval: 1000
                        });
                        //doLoginOutFineReport();
                        window.location.href = "login.jsp";
                    }
                    else {
                        $.messager.alert("退出提示", data.message, 'error');
                    }
                },
                error: function (data) {
                    $.messager.progress('close');
                    ajaxError(data, "退出提示");
                }
            });
        }
    });
}

/**
* 本地时钟
*/
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
        month = month;
    if (date < 10)
        date = date;
    if (hour < 10)
        hour = "0" + hour;
    if (minu < 10)
        minu = "0" + minu;
    if (sec < 10)
        sec = "0" + sec;
    var arr_week = new Array("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六");
    week = arr_week[day];
    var date = year + "年" + month + "月" + date + "日";
    var time = hour + ":" + minu + ":" + sec;  //+ week;
    $("#bgclock").html(date + " " + time);
    $("#bgclock_time").html(time);
    var timer = setTimeout("clockon()", 1000);
}
//当前登录用户信息
function getCurrentUser() {
    $.ajax({
        type: "POST",
        url: actionLoginUrl + "getCurrentUser",
        dataType: "json",
        success: function (data) {
            if (data.success) {
                //                $("#currentUser").html("当前用户:" + data.data.UserName)
                //                $("#loginUser").html("当前用户:" + data.data.UserName)
                $("#currentUser").html(data.data.UserName);
                $("#loginUser").html(data.data.UserName);
            }
            else {

            }
        },
        error: function (data) {
            ajaxError(data, "当前用户");
        }
    });
}


function goToMain(sys) {
    window.location.href = "main.jsp?sys=" + sys + "&menuType=" + menuType;
}

//获取当前窗口的报表链接IFrame
//施建龙
//2013年7月12日11:39:15
function getReportIFrame(){
	var reportScr =$("#REPORT_URL")[0];
	return reportScr;
}

function update_Pass() {
    var pass = $("#update_Doing_pass").val();
    var newpass = $("#update_new_pass").val();
    var conformpass = $("#update_confirm_pass").val();

    if (pass == "" || pass == undefined) {
        $.messager.alert('新增提示', "当前密码不能为空!", 'info');
        return false;
    } else if (newpass == "" || newpass == undefined) {
        $.messager.alert('新增提示', "新密码不能为空!", 'info');
        return false;
    } else if (conformpass == "" || conformpass == undefined) {
        $.messager.alert('新增提示', "确认密码不能为空!", 'info');
        return false;
    } else if ($.trim(newpass) != $.trim(conformpass)) {
        $.messager.alert('新增提示', "两次输入的密码不一致!", 'info');
        return false;
    }

    var obj = {
        "pass": pass,
        "newpass": newpass,
        "conformpass":conformpass
    };

    $.messager.progress();
    $.ajax({
        type: "POST",
        url: actionLoginUrl + "Update_pass",
        data: obj,
        dataType: "json",
        success: function (data) {
            $.messager.progress('close');
            if (data.success) {
                $.messager.alert("修改密码提示", data.message, 'info');
                $("#update_password").window("close");
            }
            else {
                $.messager.alert("修改密码提示", data.message, 'error');
                $("#update_password").window("close");
            }
        },
        error: function (data) {
            $.messager.alert("修改密码提示", data.message, 'error');
            $("#update_password").window("close");
        }
    });
}

function cancel_Update() {
    $("#update_password").window("close");
}

function onUserPersonalSet() {
    $("#window_USER_Personal_Frame").attr("src", hostName+"/RightsSystem/UserPersonalSet/UserPersonalSetView.htm");
    $("#window_USER_Personal").window('open');
}