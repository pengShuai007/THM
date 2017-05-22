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
	<script src="patientManagement.js?time=<%=SystemResource.getNowDayTime()%>" type="text/javascript"></script>
<title>患者管理</title>

</head>
<body class="easyui-layout">
	<div data-options="region:'center'">
		<div id="tb" align="left">
			<label>&nbsp;&nbsp;姓名&nbsp;</label>
			<input type="text" id="patientName" name="patientName" style="width:80px"/>
			<label>&nbsp;&nbsp;状态&nbsp;</label>
		    <select class="easyui-combobox" id="status" name="status" style="width:100px">
		    	<option value="0">等待</option>
		    	<option value="1">治疗中</option>
		    	<option value="2">完成</option>
		    </select>
		    <label>&nbsp;&nbsp;就诊日期&nbsp;</label>
			<input id="beginTime" class="easyui-datebox" style="width:130px"  />
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false" onclick="onQuery();">查询</a>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:false" onclick="add();">增加</a>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:false" onclick="visit();">复诊</a>
		</div>
		<table id="patientId"></table>
	</div>
	<!-- 增加用户 -->
	<div id="addDialog" class="easyui-dialog"
		data-options="title:'新增患者',closable:false,closed:true,modal:true,width:320,height:280,buttons:[{
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
			<div>
				<label>姓名&nbsp;&nbsp;&nbsp;</label>
				<input type="text" id="userName" name="userName" style="width:145px" onkeydown="return banInputSapce(event);"/>
			</div>
			<br/>
			<div>
				<label>性别&nbsp;&nbsp;&nbsp;</label>
				<select id="gender" name="gender" class="easyui-combobox" style="width: 150px" data-options="editable:false,required:true,width:150">
					<option value="1">男</option>
					<option value="0">女</option>
			    </select>
			</div>
			<br/>
			<div>
				<label>年龄&nbsp;&nbsp;&nbsp;</label>
				<input type="text" id="patientAge" name="patientAge" style="width: 145px" onkeydown="return banInputSapce(event);"/>
			</div>
			<br/>
			<div>
				<label>电话&nbsp;&nbsp;&nbsp;</label>
				<input type="text" id="phone" name="phone" style="width: 145px" onkeydown="return banInputSapce(event);"/>
			</div>
			<br/>
			<div>
				<label>就诊日期&nbsp;&nbsp;&nbsp;</label>
				<input id="visit_date" class="easyui-datebox" style="width: 145px" onkeydown="return banInputSapce(event);"/>
			</div>
			<br/>
	 		<div>
				<label>备注&nbsp;&nbsp;&nbsp;</label>
				<input type="text" id="remark" name="remark" style="width: 145px;" onkeydown="return banInputSapce(event);"/>
			</div>
		</div>
	</div>
	<!-- 修改用户 -->
	<!-- <div id="updateDialog" class="easyui-dialog" data-options="title:'修改用户',closable:false,closed:true,modal:true,width:320,height:280,buttons:[{
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
				<input type="hidden" id="id_update" name="id_update" style="width:145px"/>
			</div>
			<div>
				<label>姓名&nbsp;&nbsp;&nbsp;</label>
				<input type="text" id="name_update" name="name_update" style="width:145px" onkeydown="return banInputSapce(event);"/>
			</div>
			<br/>
			<div>
				<label>性别&nbsp;&nbsp;&nbsp;</label>
				<select id="gender_update" name="gender_update" class="easyui-combobox" style="width: 150px" data-options="editable:false,required:true,width:150">
					<option value="1">男</option>
					<option value="0">女</option>
			    </select>
			</div>
			<br/>
			<div>
				<label>年龄&nbsp;&nbsp;&nbsp;</label>
				<input type="text" id="age_update" name="age_update" style="width: 145px" onkeydown="return banInputSapce(event);"/>
			</div>
			<br/>
			<div>
				<label>电话&nbsp;&nbsp;&nbsp;</label>
				<input type="text" id="phone_update" name="phone_update" style="width: 145px" onkeydown="return banInputSapce(event);"/>
			</div>
			<br/>
	 		<div>
				<label>备注&nbsp;&nbsp;&nbsp;</label>
				<input type="text" id="remark_update" name="remark_update" style="width: 145px;" onkeydown="return banInputSapce(event);"/>
			</div>
		</div>
	</div>	 -->
</body>
</html>