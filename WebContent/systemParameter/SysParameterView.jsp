<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="java.io.File,
                 java.io.InputStream,
                 java.io.FileInputStream,com.*,APP.Comm.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>系统参数</title>
    <link href="../Resources/Themes/default/easyui.css" rel="stylesheet"type="text/css" />
    <link href="../Resources/Themes/icon.css" rel="stylesheet"type="text/css" />
    <script src="../Resources/Scripts/plug-in/jquery-1.8.0.min.js"type="text/javascript"></script>
    <script src="../Resources/Scripts/plug-in/jquery.easyui.min-1.3.4.js"type="text/javascript"></script>
    <script src="../Resources/Scripts/plug-in/datagrid-detailview.js"type="text/javascript"></script>
    <script src="../Resources/Scripts/plug-in/easyui-lang-zh_CN.js"type="text/javascript"></script>
    <script src="../Resources/Scripts/common.js" type="text/javascript"></script>
    <script src="../Resources/Scripts/iframe-common.js"type="text/javascript"></script>
    <link href="../Resources/Styles/iframe-common.css" rel="stylesheet"type="text/css" />
    <script src="../Resources/Scripts/plug-in/jquery.form.js"type="text/javascript"></script>
    <script src="../Resources/Scripts/plug-in/jquery.tips.js"type="text/javascript"></script>
    <script src="SysParameterView.js?time=<%=SystemResource.getNowDayTime() %>" type="text/javascript"></script>   
    <style type="text/css">
        #tt a
        {
            color: #000;
            text-decoration: none;
        }
        #tt a:link
        {
            color: #000;
            text-decoration: none;
        }
        #tt a:hover
        {
            color: #000;
            text-decoration: none;
        }
        #tt a:active
        {
            color: #000;
            text-decoration: none;
        }
        #tt a:visited
        {
            color: #000;
            text-decoration: none;
        }
    </style>
</head>
<body id="layout" class="easyui-layout">
    <div data-options="region:'center',fit:true,border:0">
        <div id="elay" class="easyui-layout" data-options="fit:true">
          <!--   修改原因：KYEEAPP-1061 增加初始化系统菜单树
                                                   修改人：hemeng
                                                   修改时间：2014年11月26日22:33:25 -->
            <!--<div id="divSysList" data-options="region:'west',split:true,collapsed:false" style="width: 180px;">
                <ul id="ttSysList" class="easyui-tree">
                </ul>
            </div>-->
            <div id="divSysp" data-options="region:'center'">
                <table border="0" cellspacing="10" cellpadding="0" width="100%">
                    <tr>
                        <td align="right">
                            <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="onSaveParam();">
                                        保存</a>
                        </td>
                    </tr>
                </table>
                <div id="tt" class="easyui-tabs">
                </div>
            </div>
    </div>
    </div>
</body>
</html>
