<!-- 
任务描述：sql查询
创建人：石起
创建时间：2015年5月5日12:59:20
任务号：APPDAILYWORK-778
 -->
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
<script src="SqlQuery.js?time=<%=SystemResource.getNowDayTime()%>"
	type="text/javascript"></script>
<title>sql查询</title>
</head>
<body class="easyui-layout">
	<div data-options="region:'center'"
		style="background-color: rgb(231, 235, 241);">
		<div id="tb" align="left" style="margin-bottom: 3px;">
			<label>数据库：</label> 
			<label id = ""></label>
			<select id="search_database"
				class="easyui-combobox" name="search_questionType"
				style="width: 300px"
				data-options="editable:false,required:true,width:150">
				<option value="1">正式环境数据库</option>
			</select> 
			<a href="#" class="easyui-linkbutton"
				data-options="iconCls:'icon-search',plain:false"
				onclick="queryColName();">查询</a>
			<div style="color: red; line-height: 30px;" align="center">查询提示：该功能只支持单语句查询,其他概不支持,最多只能查询100条数据.由于已限制了查询条数,在SQL语句中,请勿再添加LIMIT,否则会导致SQL出错！
			</div>
			<div>
				<textarea id="sql"
					style="overflow: auto; width: 100%; height: 170px; border: 1px solid grey; resize: none;"
					onblur="onblurSQL();" onfocus="onfocusSQL();">请输入sql查询语句</textarea>
			</div>
		</div>
		<table id="operatorId" border="1"></table>
	</div>
</body>
</html>