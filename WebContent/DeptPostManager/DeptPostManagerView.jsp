<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="java.io.File,
                 java.io.InputStream,
                 java.io.FileInputStream,
                 APP.*,APP.Comm.*"%>
<%-- <%@ include file="/Comm/taglibs.jsp"%> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>部门编制管理</title>
<link href="../Resources/Themes/default/easyui.css" rel="stylesheet"
	type="text/css" />
<link href="../Resources/Themes/icon.css" rel="stylesheet"
	type="text/css" />
<script src="../Resources/Scripts/plug-in/jquery-1.8.0.min.js"
	type="text/javascript"></script>
<script src="../Resources/Scripts/plug-in/jquery.easyui.min.js"
	type="text/javascript"></script>
<script src="../Resources/Scripts/plug-in/easyui-lang-zh_CN.js"
	type="text/javascript"></script>
<script src="../Resources/Scripts/common.js" type="text/javascript"></script>
<script src="../Resources/Scripts/iframe-common.js"
	type="text/javascript"></script>
<script src="DeptPostManagerView.js?time=<%=SystemResource.getNowDayTime()%>" type="text/javascript"></script>
</head>
<body class="easyui-layout" data-options="fit:true">
	<div id="tb" style="padding: 1px; height: auto">
		<div>
			部门: <input id="search_dept" type="text" style="width:100px" /> 岗位: <input
				id="search_post" type="text" style="width:100px" /> <a href="#"
				class="easyui-linkbutton" data-options="iconCls:'icon-search'"
				plain="false" onclick="onQuery();">查询</a>
		<!-- </div>
		<div> -->
			<a href="#" class="easyui-linkbutton" iconcls="icon-add"
				onclick="appendPost()" plain="false">添加</a> <a href="#"
				class="easyui-linkbutton" iconcls="icon-remove"
				onclick="removePost()" plain="flase">删除</a> <a href="#"
				class="easyui-linkbutton" iconcls="icon-edit" onclick="savePost()"
				plain="flase">保存</a><!-- <a href="#" class="easyui-linkbutton"
				iconcls="icon-tip" onclick="openPostMan()" plain="flase">维护岗位字典</a> --><a
				href="#" class="easyui-linkbutton" iconcls="icon-card"
				onclick="planPostUser()" plain="flase">编制岗位人员</a>
		</div>
	</div>
	<div id="tbName" style="padding: 1px; height: auto">
		<div>
			<input id="noname"  type="text"></input>
		</div>
	</div>
	<!-- 西部开始 -->
	<div data-options="region:'west',split:true" title="组织部门"
		style="width: 240px;">
		<ul id="tt" class="easyui-tree">
		</ul>
	</div>
	<!-- 西部结束 -->
	<!-- 中部开始 -->
	<div data-options="region:'center'" title="编制管理">
		<table id="dg">
		</table>
	</div>
	<div style="display: none">
		<!-- 岗位字典设置 -->
		<div id="postTypeWindow" class="easyui-window"
			data-options="closed:true,modal:true,title:'岗位字典管理'"
			style="width: 730px; height: 530px;">
			<table id="dgPostType">
			</table>
		</div>
	</div>
	<div style="display: none">
		<!-- 用户添加 -->
		<div id="postUserWindow" class="easyui-window"
			data-options="closed:true,modal:true,title:'岗位用户管理'"
			style="width: 730px; height: 530px;">
			<div style="width: 60%; height: 100%; float: left">
				<table id="dgPostUser">
				</table>
			</div>
			<div style="width: 40%; height: 100%; float: right">
				<table id="dgPostNoUser">
				</table>
			</div>
		</div>
	</div>
</body>
</html>