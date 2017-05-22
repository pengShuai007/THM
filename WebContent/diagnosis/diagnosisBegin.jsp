<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="java.io.File,
                 java.io.InputStream,
                 java.io.FileInputStream,com.*,APP.Comm.*"%>
<!DOCTYPE html PUBLIC "-//W3C//Dth HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dth">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<link href="../../Resources/Themes/default/easyui.css" rel="stylesheet" type="text/css" />
<link href="../../Resources/Themes/icon.css" rel="stylesheet" type="text/css" />
<script src="../../Resources/Scripts/plug-in/jquery-1.8.0.min.js" type="text/javascript"></script>
<script src="../../Resources/Scripts/plug-in/jquery.easyui.min.js" type="text/javascript"></script>
<script src="../../Resources/Scripts/plug-in/jquery.layout.js" type="text/javascript"></script>
<script src="../../Resources/Scripts/plug-in/jquery.layout.extend.js" type="text/javascript"></script>
<script src="../../Resources/Scripts/plug-in/datagrid-detailview.js" type="text/javascript"></script>
<script src="../../Resources/Scripts/plug-in/easyui-lang-zh_CN.js" type="text/javascript"></script>
<script src="../../Resources/Scripts/common.js" type="text/javascript"></script>
<script src="../../Resources/Scripts/iframe-common.js" type="text/javascript"></script>
<script src="APPInsuranceInfo.js" type="text/javascript"></script>
<title>诊断书</title>
</head>
<body  class="easyui-layout" >
	<div data-options="region:'center'" >
		<div id="tb" style="padding-left:5px;padding-top:1px;">
			<label>姓名：</label>
			<input id='patient_name' style="width: 120px">  
			<label>&nbsp;年龄：</label> 
			<input id='patient_age' style="width: 50px">
			<label>&nbsp;性别：</label> 
			<input id='patient_agent' style="width: 50px">    
        	<a href="#" class="easyui-linkbutton"
			data-options="iconCls:'icon-reload',plain:false" onclick="onSave();">保存</a>
        
			<a href="#" id="onExportID" class="easyui-linkbutton"
			data-options="iconCls:'icon-add',plain:false" onclick="onExport();">导出</a>
		
		</div>
		<div id="" style="padding-left:5px;padding-top:1px;">
			<label>诊断说明：</label><br/>
			<input class="easyui-textbox" data-options="multiline:true" style="width:300px;height:100px">
		</div>
	</div>
</body>
</html>