<!-- 
	描述：菜单管理，对菜单进行CRUD操作
	创建人：李添
	创建时间：2016年7月25日11:46:44
	任务号：
 -->
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="java.io.File,
                 java.io.InputStream,
                 java.io.FileInputStream,
                 APP.*,APP.Comm.*"%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> -->
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>菜单管理</title>
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

<script
	src="MenuManagerView.js?time=<%=SystemResource.getNowDayTime()%>"
	type="text/javascript"></script>
</head>
<body class="easyui-layout" data-options="fit:true">

	<!-- 西部开始 -->
	<div data-options="region:'west',split:true" title="菜单管理"
		style="width: 240px;">
		<ul id="tt" class="easyui-tree">
		</ul>
		<!-- 右键菜单定义如下： -->
		<div id="urlIsNull" class="easyui-menu" style="width: 120px;">
			<div onclick="search()" data-options="iconCls:'icon-search'">查看菜单信息</div>
			<div onclick="append()" data-options="iconCls:'icon-add'">添加子菜单</div>
			<div onclick="edit()" data-options="iconCls:'icon-edit'">修改</div>
			<div onclick="del()" data-options="iconCls:'icon-remove'">移除</div>
		</div>
		<div id="urlIsNotNull" class="easyui-menu" style="width: 120px;">
			<div onclick="search()" data-options="iconCls:'icon-search'">查看菜单信息</div>
			<div onclick="edit()" data-options="iconCls:'icon-edit'">修改</div>
			<div onclick="del()" data-options="iconCls:'icon-remove'">移除</div>
		</div>
	</div>
	<!-- 西部结束 -->

	<!-- 中部开始 -->
	<div data-options="region:'center'" title="菜单展示">
		
		<table id="menulist"></table>
		
		<!-- 移除菜单前显示这个菜单都有谁在用 -->
		<div align="center" style="display: none;">
			<div id="showUser" class="easyui-dialog" 
				style="width: 520px; height: 420px; margin-top: 10px; margin-left: 5px; padding-top: 10px;"
				data-options="title:'使用本菜单的用户信息,请联系好用户是否确定删除？',closed:true,modal:true,resizable:true,cache:false,buttons:[{
				text:'确定删除',
				iconCls:'icon-ok',
				handler:function(){
					deleteMenu();
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function(){
					cancel();
				}
			
			}]">
				<table id="showUser1"></table>
			</div>
		
		</div>
		
		<!-- 查看菜单信息 -->
		<div align="center" style="display: none;">
			<div id="searchDialog" class="easyui-dialog" 
				style="width: 400px; height: 400px; margin-top: 10px; margin-left: 5px; padding-top: 10px;" 
				data-options="title:'菜单详情',closed:true,modal:true,resizable:true,cache:false,buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function(){
					cancel();
				}
				}]">
				<form class="searchform" id="searchform">
					<table align="center" id="searchTable" >
						<tr>
							<td>菜单Id:<br /><br /> </td>
							<td>
								<lable id="MENU_ID2" name="MENU_ID2"></lable>
								<br /><br />
							</td>
						</tr>
						<tr>
							<td>菜单名:<br /> <br /></td>
							<td>
								<lable id="MENU_NAME2" name="MENU_NAME2"></lable>
							<br /> <br /></td>
						</tr>
						<tr>
							<td>菜单URL:<br /> <br /></td>
							<td>
								<lable id="MENU_URL2" name="MENU_URL2"></lable>
							<br /> <br /></td>
						</tr>
						<tr>
							<td>菜单代码:<br /> <br /></td>
							<td>
								<lable id="MENU_CODE2" name="MENU_CODE2"></lable>
							<br /> <br /></td>
						</tr>
						<tr>
							<td>操作者:<br /> <br /></td>
							<td>
								<lable id="OPERATOR_NAME2" name="OPERATOR_NAME2"></lable>
							<br /> <br /></td>
						</tr>
						<tr>
							<td>操作时间:<br /> <br /></td>
							<td>
								<lable id="OPERATOR_TIME2" name="OPERATOR_TIME2"></lable>
							<br /> <br /></td>
						</tr>
						<tr>
							<td>修改者:<br /> <br /></td>
							<td>
								<lable id="MODIFIER2" name="MODIFIER2"></lable>
							<br /> <br /></td>
						</tr>
						<tr>
							<td>修改时间:<br /> <br /></td>
							<td>
								<lable id="MODIFY_TIME2" name="MODIFY_TIME2"></lable>
							<br /> <br /></td>
						</tr>
						<tr>
							<td>父菜单:<br /> <br /></td>
							<td>
								<lable id="C_S_MENU_ID2" name="C_S_MENU_ID2"></lable>
							<br /> <br /></td>
						</tr>
						<tr>
							<td>菜单排序:<br /> <br /></td>
							<td>
								<lable id="MENU_ORDER2" name="MENU_ORDER2"></lable>
							<br /> <br /></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		<!-- 查看菜单信息结束 -->

		<!-- 添加菜单 -->
		<div align="center" style="display: none;">
			<div id="addDialog" class="easyui-dialog"
				style="width: 500px; height: 300px; margin-top: 10px; margin-left: 5px; padding-top: 10px;"
				data-options="title:'新增菜单',closed:true,modal:true,resizable:true,cache:false,buttons:[{
		text:'保存',
		iconCls:'icon-ok',
		handler:function(){
			addsave();
		}
	},{
		text:'取消',
		iconCls:'icon-cancel',
		handler:function(){
			cancel();
		}
	}]">
				<form class="addform" id="addform">
					<table align="center">
						<tr>
							<td>菜单名:<br /> <br /></td>
							<td><input type="text" id="MENU_NAME" name="MENU_NAME"
								style="width: 300px" maxlength="20" /><br /> <br /></td>
						</tr>
						<tr>
							<td>菜单URL:<br /> <br /></td>
							<td><!-- <input type="url" id="MENU_URL" name="MENU_URL"
								style="width: 300px" maxlength="20" /> -->
								<textarea id="MENU_URL" name="MENU_URL" rows="3" cols="41"></textarea>
								<br /> <br /></td>
						</tr>
						<tr>
							<td>菜单代码(非空):<br /> <br /></td>
							<td><input type="text" id="MENU_CODE" name="MENU_CODE"
								style="width: 300px" maxlength="20" /><br /> <br /></td>
						</tr>
						<tr>
							<td>菜单排序:<br /> <br /></td>
							<td><input type="text" id="MENU_ORDER" name="MENU_ORDER"
								style="width: 300px" maxlength="20" /><br /> <br /></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		<!-- 添加菜单结束 -->

		<!-- 修改菜单 -->
		<div align="center" style="display: none;">
			<div id="editDialog" class="easyui-dialog"
				style="width: 500px; height: 300px; margin-top: 10px; margin-left: 5px; padding-top: 10px;"
				data-options="title:'修改菜单',closed:true,modal:true,resizable:true,cache:false,buttons:[{
		text:'保存',
		iconCls:'icon-ok',
		handler:function(){
			editSave();
		}
	},{
		text:'取消',
		iconCls:'icon-cancel',
		handler:function(){
			cancel();
		}
	}]">
				<form class="addform" id="addform">
					<table align="center">
						<tr>
							<td>菜单名:<br /> <br /></td>
							<td><input type="text" id="MENU_NAME1" name="MENU_NAME1"
								style="width: 300px" maxlength="20" /><br /> <br /></td>
						</tr>
						<tr>
							<td>菜单URL:<br /> <br /></td>
							<td>
								<textarea id="MENU_URL1" name="MENU_URL1" rows="3" cols="41"></textarea>
								<br /> <br /></td>
						</tr>
						<tr>
							<td>菜单代码:<br /> <br /></td>
							<td><input type="text" id="MENU_CODE1" name="MENU_CODE1"
								style="width: 300px" maxlength="20" /><br /> <br /></td>
						</tr>
						<tr>
							<td>父菜单:<br /> <br /></td>
							<td><input type="text" id="C_S_MENU_ID1" name="C_S_MENU_ID1"
								style="width: 300px" class="easyui-combobox"
								onkeydown="return banInputSapce(event);" /> <br /></td>
						</tr>
						<tr>
							<td>菜单排序:<br /> <br /></td>
							<td><input type="text" id="MENU_ORDER1" name="MENU_ORDER1"
								style="width: 300px" maxlength="20" /><br /> <br /></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		<!-- 修改菜单结束 -->
	</div>
	<!-- 中部结束 -->
</body>
</html>