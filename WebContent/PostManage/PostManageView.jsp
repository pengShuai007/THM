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
<script src="PostManageView.js?time=<%=SystemResource.getNowDayTime()%>" type="text/javascript"></script>
<title>岗位管理</title>
</head>
<body class="easyui-layout" id="cc">
	<div data-options="region:'center'">
		<div id="tb" align="left">
			<label>请输入要查询的岗位代码(名称)</label>
			<input type="text" id="search_posttext" name="postName" style="width:100px"/>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false" onclick="onQuery()">查询</a>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:false" onclick="add()">增加</a>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:false" onclick="update()">修改</a> 
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:false" onclick="del()">删除</a>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:false" onclick="grant()">授权</a>
		</div>
		<table id="posts"></table>
		<!-- 新增页面 -->
		<div id="addDialog" class="easyui-dialog" data-options="title:'新增岗位信息',closable:false,closed:true,modal:true,width:270,height:260,buttons:[{
			text:'保存',
			iconCls:'icon-ok',
			handler:function(){
			savecheck();
			}
		},{
			text:'取消',
			iconCls:'icon-cancel',
			handler:function(){
			cancel();
			}
		}]">
			<div style="padding-top:5px;margin-left:30px;">
				<table cellspacing="5px;">
					<tr>
						<td><label>岗位代码</label></td>
						<td><input type="text" style="width:120px;"  id="addPostCode" name="PostCode" onkeydown="return banInputSapce(event);"/></td>
					</tr>
					<tr>
						<td><label>岗位名称</label></td>
						<td><input type="text" style="width:120px;" id="addPostName" name="PostName" onkeydown="return banInputSapce(event);"/></td>
					</tr>
					<tr>
						<td><label>是否启用</label></td>
						<td><input style="margin-left:10px" type="radio" id="addValidateFlag1" name="ValidateFlag" value="0" />否
							<input style="margin-left:10px" type="radio" id="addValidateFlag2" name="ValidateFlag" value="1" checked="checked" />是
						</td>
					</tr>
					<tr>
						<td><label>公共岗位</label></td>
						<td><input style="margin-left:10px" type="radio" id="addPubAttr1" name="PubAttr" value="0" checked="checked" />否
							<input style="margin-left:10px" type="radio" id="addPubAttr2" name="PubAttr" value="1" />是
						</td>
					</tr>
					<tr>
						<td><label>备注</label></td>
						<td><textarea id="addComments" rows="3" cols="14" style="resize:none;"></textarea></td>
					</tr>
				</table>
			</div>
		</div>
		<!-- 修改页面 -->
		<div id="updateDialog" class="easyui-dialog" data-options="title:'修改角色信息',closable:false,closed:true,modal:true,width:270,height:260,buttons:[{
			text:'保存',
			iconCls:'icon-ok',
			handler:function(){
			updatesave();
			}
		},{
			text:'取消',
			iconCls:'icon-cancel',
			handler:function(){
			cancel();
			}
		}]">
			<div style="padding-top:5px;margin-left:30px;">
				<table cellspacing="5px;">
					<tr>
						<td><label>岗位代码</label></td>
						<td><input type="text" style="width:120px;"  id="editPostCode" name="PostCode" disabled="disabled"/></td>
					</tr>
					<tr>
						<td><label>岗位名称</label></td>
						<td><input type="text" style="width:120px;" id="editPostName" name="PostName" onkeydown="return banInputSapce(event);"/></td>
					</tr>
					<tr>
						<td><label>是否启用</label></td>
						<td><input style="margin-left:10px" type="radio" id="editValidateFlag1" name="ValidateFlags" value="0" />否
							<input style="margin-left:10px" type="radio" id="editValidateFlag2" name="ValidateFlags" value="1" checked="checked" />是
						</td>
					</tr>
					<tr>
						<td><label>公共岗位</label></td>
						<td><input style="margin-left:10px" type="radio" id="editPubAttr1" name="PubAttrs" value="0" checked="checked" />否
							<input style="margin-left:10px" type="radio" id="editPubAttr2" name="PubAttrs" value="1" />是
						</td>
					</tr>
					<tr>
						<td><label>备注</label></td>
						<td><textarea id="editComments" rows="3" cols="14" style="resize:none;"></textarea></td>
					</tr>
				</table>
			</div>
		</div>		
	</div>
	<div id="east" data-options="region:'east',split:true,collapsed:true,title:'岗位授权'" style="width:200px;padding:10px;">
		 <div>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:false" onclick="grantsave();">保存</a>
		 </div>		 
		 <ul id="tt" class="easyui-tree" checkbox="true"></ul>
	</div>
</body>
</html>