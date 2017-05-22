<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="java.io.File,
                 java.io.InputStream,
                 java.io.FileInputStream,com.*,APP.Comm.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="../Resources/Themes/default/easyui.css" rel="stylesheet"
	type="text/css" />
<link href="../Resources/Themes/icon.css" rel="stylesheet"
	type="text/css" />
<script src="../Resources/Scripts/plug-in/jquery-1.8.0.min.js"
	type="text/javascript"></script>
<script src="../Resources/Scripts/plug-in/jquery.easyui.min.js"
	type="text/javascript"></script>
<script src="../Resources/Scripts/plug-in/datagrid-detailview.js"
	type="text/javascript"></script>
<script src="../Resources/Scripts/plug-in/easyui-lang-zh_CN.js"
	type="text/javascript"></script>
<script src="../Resources/Scripts/common.js" type="text/javascript"></script>
<script src="../Resources/Scripts/iframe-common.js"
	type="text/javascript"></script>
	<script src="diagnosis.js?time=<%=SystemResource.getNowDayTime()%>" type="text/javascript"></script>
<title>医生诊断</title>
</head>
<body class="easyui-layout">
	<div data-options="region:'center'">
		<div id="tb" align="left">
			<label>&nbsp;&nbsp;姓名&nbsp;</label>
			<input type="text" id="patientName" name="patientName" style="width:80px"/>
			<label>&nbsp;&nbsp;状态&nbsp;</label>
		    <label>&nbsp;&nbsp;就诊日期&nbsp;</label>
			<input id="beginTime" class="easyui-datebox" style="width:130px"  />
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false" onclick="onQuery();">查询</a>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:false" onclick="diagnosis();">诊断</a>
		</div>
		<table id="patientId"></table>
	</div>
</body>
</html>