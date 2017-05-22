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
	<script src="operatorManagement.js?time=<%=SystemResource.getNowDayTime()%>" type="text/javascript"></script>
<title>用户管理</title>
<!-- <style>
.tabs {
	overflow: scroll;
	overflow-x: hidden;
}
</style> -->
</head>
<body class="easyui-layout">
	<div data-options="region:'center'">
		<div id="tb" align="left">
			<label>登陆名</label>
			<input type="text" id="search_code" name="operatorCode" style="width:80px"/>
			<label>用户名称</label>
			<input type="text" id="search_names" name="operatorName" style="width:80px"/>
			<label>岗位</label>
		    <!-- <input type="text" id="search_role" name="operatorRole" style="width:100px"/> -->
		    <select class="easyui-combobox" id="search_post" name="operatorPost" style="width:100px"></select>

			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false" onclick="onQuery();">查询</a>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:false" onclick="add();">增加</a>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:false" onclick="update();">修改</a>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:false" onclick="del();">删除</a>
			<a href="#" class="easyui-linkbutton" title="重置密码为a1b2c3//" data-options="iconCls:'icon-remove',plain:false" onclick="resetPassWord();">重置用户密码</a>
		</div>
		<table id="operatorId"></table>
	</div>
	<!-- 增加用户 -->
	<div id="addDialog" class="easyui-dialog"
		data-options="title:'新增用户',closable:false,closed:true,modal:true,width:340,height:340,buttons:[{
		text:'保存',
		iconCls:'icon-ok',
		handler:function(){
		save();
		}
	},{
		text:'取消',
		iconCls:'icon-cancel',
		handler:function(){
		cancel();
		}
	}]">
		<div style="padding-top: 20px; margin-left: 10px;">
			<!--<div>
				<label>医院名称&nbsp;&nbsp;&nbsp;&nbsp;</label>
				<input id="hospitalId" name="hospitalName" class="easyui-combobox" style="width:150px" onkeydown="return banInputSapce(event);"/>
			</div>-->
			<div>
				<label>登陆名&nbsp;&nbsp;&nbsp;</label>
				<input type="text" id="usercodeId" name="usercodeName" style="width:145px" onkeydown="return banInputSapce(event);"/>
			</div>
			<br/>
			<div>
				<label>用户名称&nbsp;&nbsp;</label>
				<input type="text" id="operatorName" name="operatorName" style="width: 145px" onkeydown="return banInputSapce(event);"/>
			</div>
			<br/>
			<div>
				<label>输入密码&nbsp;&nbsp;</label>
				<input type="password" id="pwd" name="passWord" style="width: 145px" onkeydown="return banInputSapce(event);"/>
			</div>
			<br/>
			<div>
				<label>请再输入密码</label>
				<input type="password" id="newpwd" name="passWord" style="width: 145px" onkeydown="return banInputSapce(event);"/>
			</div>
			<br/>
	 		<div>
				<label>输入手机号码</label>
				<input type="text" id="PHONE_NUM" name="PHONE_NUM" style="width: 145px" onkeydown="return banInputSapce(event);"/>
			</div>
			<br/>
			<div>
				<label>输入邮箱&nbsp;&nbsp;</label>
				<input type="text" id="EMAIL" name="EMAIL" style="width: 145px" onkeydown="return banInputSapce(event);"/>
			</div>
			<br/> 
			<!-- <div>
				<label>输入工号&nbsp;&nbsp;</label>
				<input type="text" id="JOB_ID" name="JOB_ID" style="width: 145px" onkeydown="return banInputSapce(event);"/>
			</div> 
			<br/> -->
			<div>
				<label>是否保护&nbsp;&nbsp;</label>
				<!-- <input type="text" id="isProtected" name="isProtected" style="width: 145px" onkeydown="return banInputSapce(event);"/> -->
				<select id="isProtected" class="easyui-combobox" name="isProtected" style="width: 150px" data-options="editable:false,required:true,width:150">
				<option value="1">保护</option>
				<option value="0">不保护</option>
			    </select>
			</div>
			<!-- <div style="padding-top:2px;">
				<label>选择角色&nbsp;&nbsp;&nbsp;&nbsp;</label>
				<input id="role" name="roleName" class="easyui-combobox" style="width: 150px" />
			</div> -->
		</div>
	</div>
	<!-- 修改用户 -->
	<div id="updateDialog" class="easyui-dialog" data-options="title:'修改用户',closable:false,closed:true,modal:true,width:340,height:300,buttons:[{
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
		<div style="padding-top: 20px; margin-left: 10px;">
			<div>
				<input type="hidden" id="operatorId" name="operatorId" />
				<!-- <input type="hidden" id="hospitalId" name="hospitalId"/> -->
			</div>
			<div>
				<label>登陆名&nbsp;&nbsp;</label>
				<input type="text" id="usercodeIdp" name="usercodeNamep" style="width:150px" onkeydown="return banInputSapce(event);"/>
			</div>
			<br/>
			<div>
				<label>用户名称&nbsp;&nbsp;</label>
				<input type="text" id="operatorNamep" name="operatorName" style="width: 150px" onkeydown="return banInputSapce(event);"/><br>
			</div>
			<br/>
			<div>
				<label>输入手机号码</label>
				<input type="text" class="easyui-validatebox" id="PHONE_NUM1" name="PHONE_NUM1" style="width: 145px"  data-options="validType:'phoneRex'" onkeydown="return banInputSapce(event);"/>
			</div>
			<br/>
			<div>
				<label>输入邮箱&nbsp;&nbsp;</label>
				<input type="text" id="EMAIL1" name="EMAIL1" style="width: 145px" onkeydown="return banInputSapce(event);"/>
			</div>
			<br/>
			<!-- <div>
				<label>输入工号&nbsp;&nbsp;</label>
				<input type="text" id="JOB_ID1" name="JOB_ID1" style="width: 145px" onkeydown="return banInputSapce(event);"/>
			</div> 
			<br/>-->
			<div>
				<label>是否保护&nbsp;&nbsp;</label>
				<!-- <input type="text" id="isProtected1" name="isProtected1" style="width: 145px" onkeydown="return banInputSapce(event);"/> -->
				<select id="isProtected1" class="easyui-combobox" name="isProtected1" style="width: 150px" data-options="editable:false,required:true,width:150">
				<option value="1">保护</option>
				<option value="0">不保护</option>
			    </select>
			</div>
			<br/>
			<!-- <div style="padding-top:2px;">
				<label>选择角色&nbsp;&nbsp;</label>
				<input id="rolep" name="roleName" class="easyui-combobox" style="width: 154px" />
			</div> -->
		</div>
	</div>	
	<!-- 查看权限界面 -->
	<div style="display: none">
		<div id="showUserAllPerm" class="easyui-window"
			data-options="title:'用户权限',iconCls:'icon-search',maximizable:false,minimizable:false,collapsible:false,closable:true,resizable:false,closed:true,modal:true"
			style="width: 600px; height: 400px; padding: 5px;">
			<div id="scanPermTabs" class="easyui-tabs"
				data-options="fit:true,onSelect:tabOnWindowSelect,tabPosition:'left' ">
				<div title="模块权限">
					<ul id="UserOperation" class="easyui-tree">
					</ul>
				</div>
				<div title="岗位权限">
					<ul id="UserPost" class="easyui-tree">
					</ul>
				</div>
				<div title="系统订阅">
					<table id="SuEvent"  >
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>