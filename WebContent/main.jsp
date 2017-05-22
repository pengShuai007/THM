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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>主页</title>
<link href="Resources/Themes/default/easyui.css" rel="stylesheet"
	type="text/css" />
<link href="Resources/Themes/icon.css" rel="stylesheet" type="text/css" />
<link href="Resources/Styles/main.css" rel="stylesheet" type="text/css" />
<link href="Resources/Styles/indexIniInfo.css" rel="stylesheet"
	type="text/css" />
<script src="Resources/Scripts/plug-in/jquery-1.8.0.min.js"
	type="text/javascript"></script>
<script src="Resources/Scripts/plug-in/jquery.easyui.min.js"
	type="text/javascript"></script>
<script src="Resources/Scripts/plug-in/easyui-lang-zh_CN.js"
	type="text/javascript"></script>
<script src="Resources/Scripts/common.js" type="text/javascript"></script>
<script src="Resources/Scripts/iframe-common.js" type="text/javascript"></script>
<script src="Resources/Scripts/main.js" type="text/javascript"></script>
<style type="text/css">
#loginUsers {
	position: relative;
	overflow: visible;
	font-family: '微软雅黑';
	color: RGB(255, 255, 255);
	margin-left: 0.5em;
	padding-top: 100px;
	font-size: 9pt;
	font-weight: 600;
}
#systemTime {
	z-index: 10;
	position: relative;
	overflow: visible;
	font-family: '微软雅黑';
	color: RGB(255, 255, 255);
	padding-top: 100px;
	font-size: 9pt;
	font-weight: 600;
	margin-left: 0.5em;
}

/* #publishTime,#systemTime {
	z-index: 10;
	position: absolute;
	left: 83%;
} */
</style>
</head>
<body id="layout" class="easyui-layout">
	<!-- 北部开始 -->
	<div data-options="region:'north'"
		style="overflow: hidden; padding: 1px; height: 45px;">

		<img id="SYS_TOP_LOGO_PATH" class="global logo"
			onerror="this.src='Resources/Images/Default/DefaultBackground.jpg'"
			src="Resources/Images/Default/DefaultBackground.jpg"
			style="z-index: 1; position: absolute; left: 0; top: 0; width: 100%;" />

		<div class="logo-hrp-inf"></div>
		<div class="logo">
			<div style="float: left; margin-top: 0.5em;">
				<img id="SYS_MAIN_LOGO_PATH"
					onerror="this.src='Resources/Images/main/qyy_logo.png'"
					src="Resources/Images/main/qyy_logo.png"
					style="float: left; margin-top: 0.5em;" />
			</div>
			<!---color:#003366;-->
			<div id="logo-hrp-text" class="logo-hrp-text"
				style="font-family: '微软雅黑'; color: RGB(255, 255, 255); margin-left: 0.5em; font-size: 18pt; font-weight: bold;">
				<span id="SYS_NAME">APP</span>
			</div>

			<div
				style="float: right; font-family: '微软雅黑'; color: White; font-weight: 600; font-size: 9pt; margin-left: 10px;">
				<table>
					<tr>
						<td align="left"></td>
						<td align="right"></td>
						<td id="finaName" style="text-align: left;" align="right"></td>
						<td id="fina_year" align="right" colspan="2" style=""></td>

					</tr>
					<tr>
						<td align="right"><a style="color: White;" id="help_doc_Id"
							href="#" title="帮助"> <img alt="帮助"
								src="Resources/Images/Default/ico_help.png" /></a> <input
							type="hidden" id="input_Help" value="" /></td>
						<td align="right"><a style="color: White;" id="edit_Pass"
							href="javascript:updatepw()" title="修改个人信息"> <img alt="修改密码"
								src="Resources/Themes/icons/pencil.png" /></a></td>
						<td align="right"><a style="color: White;"
							href="javascript:loginOut()" title="退出APP">退出</a></td>
						<td align="right"></td>
						<td align="right"></td>
						<td align="right"></td>
					</tr>
				</table>
				<table style="display: none">
					<tr>
						<td align="center" colspan="4"></td>
						<td><span>主题:</span></td>
						<td><select id="cb-theme"
							data-options="editable:false,onChange:onChangeTheme"
							style="width: 80px">
								<option value='default'>默认皮肤</option>
								<option value='gray'>灰色主题</option>
								<option value='metro'>透明主题</option>
								<option value='cupertino'>丘珀主题</option>
								<option value='dark-hive'>黑色主题</option>
								<option value='pepper-grinder'>雾蒙主题</option>
								<option value='sunny'>黄昏主题</option>
						</select></td>
						<td><a style="color: White;" href="javascript:loginOut()">退出</a>
						</td>
					</tr>
				</table>
			</div>
			<div
				style="font-family: '微软雅黑'; color: White; font-weight: 600; font-size: 9pt; padding-top: 8px; float: right; right: 30px;">
				<%-- <a href="javascript:showSmallLogin();"
						style="text-decoration: none;" title="切换用户">
						<div id="loginUsers"
							style="<%=((main.PAGE_USER_HIDDEN == "") ? "display:none;"
					: "display:inline;padding-top:30px;")%>">
							当前用户： <span id="loginUser"></span>
						</div>
					</a> --%>
				<!-- 	<a href="javascript:showlogin();">  -->

				<span id="systemTime"></span>&nbsp;&nbsp;当前用户:&nbsp;&nbsp;<span
					id="loginUser"></span>

				<!-- </a>  -->
			</div>
		</div>
	</div>
	<!-- 北部结束-->
	<!-- 南部开始 -->
	<!-- 南部结束-->
	<!-- 西部开始 -->
	<div data-options="region:'west',split:true" title="系统菜单"
		style="width: 200px;">
		<ul id="tt">
		</ul>
	</div>
	<!-- 西部结束 -->
	<!-- 中部开始 -->
	<div data-options="region:'center'" style="overflow: hidden">
		<div id="tabs" class="easyui-tabs" fit="true" border="false">
			<div title="主页">
				<div class="home-background">
					<asp:Image ID="SYS_DESKTOP_LOGO_PATH" alt="趣医APP"
						onerror="this.src='Resources/Images/Default/DefaultBackground.jpg'"
						runat="server"
						ImageUrl="Resources/Images/Default/DefaultBackground.jpg" />
					<img id="SYS_DESKTOP_LOGO_PATH"
						onerror="this.src='Resources/Images/Default/DefaultBackground.jpg'"
						src="Resources/Images/Default/DefaultBackground.jpg" />

				</div>
			</div>
		</div>
	</div>
	<!-- 中部结束-->
	<!-- tab 菜单开始 -->
	<div id="mm" class="easyui-menu" style="width: 120px;">
		<div id="mm-tabupdate">刷新</div>
		<div class="menu-sep"></div>
		<div id="mm-tabclose">关闭</div>
		<div id="mm-tabcloseother">关闭其他</div>
		<div id="mm-tabcloseall">关闭全部</div>
	</div>
	<!-- tab 菜单结束 -->
	<iframe id='REPORT_URL' src='' style='display: none;' />
	<div id="help_html_Id" style="width: 100%; height: auto;"
		class="easyui-window"
		data-options="title:'帮助文档',iconCls:'icon-save',closed:true,modal:true">
		<iframe src="" id="help_iframe_Id" style="width: 100%; height: 100%"></iframe>
	</div>
	<div id="update_password"
		style="width: 300px; height: 200px; overflow: hidden;"
		class="easyui-dialog"
		data-options="title:'个人信息管理',iconCls:'icon-save',width:300,height:300,closed:true,modal:true">
		<div id="panel" class="easyui-layout"
			style="width: 300px; height: 300px;">
			<div data-options="region:'center',title:'个人基本信息',split:false"
				style="width: 300px;">
				<table style="width: 100%; height: 90%;">
					<tbody>
						<tr>
							<td style="text-align: right;">当前密码:</td>
							<td><input type="password" id="update_Doing_pass"
								name="Doing_pass" /></td>
							<td></td>
						</tr>
						<tr>
							<td style="text-align: right;">新密码:</td>
							<td><input type="password" id="update_new_pass"
								name="new_pass" /></td>
						</tr>
						<tr>
							<td style="text-align: right;">确认密码:</td>
							<td><input type="password" id="update_confirm_pass"
								name="confirm_pass" /></td>
						</tr>
						<tr>
							<td style="text-align: right;">手机号码:</td>
							<td><input type="text" id="PHONE_NUM" name="PHONE_NUM" /></td>
						</tr>
						<tr>
							<td style="text-align: right;">邮箱地址:</td>
							<td><input type="text" id="EMAIL" name="EMAIL" /></td>
						</tr>
					</tbody>
					<tfoot>
						<tr align="center">
							<td colspan="2">
								<div style="float: center; margin: 0 -15px -5px 0">
									<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'"
										href="javascript:void(0)" onclick="javascript:update_Pass()">保存</a>
									<a class="easyui-linkbutton"
										data-options="iconCls:'icon-cancel'" href="javascript:void(0)"
										onclick="javascript:cancel_Update()">取消</a>
								</div>
							</td>
						</tr>
					</tfoot>
				</table>
			</div>
			<!-- <div data-options="region:'center',split:false"
				style="background: #eee; width: 400px;">
				<table id="SubEvent">
				</table>
			</div>
			<div data-options="region:'east',split:false" style="width: 400px;">
				<table id="UnSubEvent">
				</table>
			</div> -->

<!-- 			<div id="addDetectHospital" class="easyui-dialog"
				data-options="title:'请选择医院名称',closable:false,resizable:true,closed:true,modal:true,width:260,height:340,buttons:[{
		text:'确定',
		iconCls:'icon-ok',
		handler:function(){
			saveDetectHospital();
		}
	},{
		text:'取消',
		iconCls:'icon-cancel',
		handler:function(){
			cancelDetectHospital();
		}
	}]">
				<div style="padding-top: 10px; margin-left: 10px;">
					<div id="saveAll"></div>
					</br>
					<div>
						<label style="margin-left: 2px;">医院(医院按拼音排序，平台只显示前五家医院，最多订阅10家医院):</label>
						<select id="seacrh_hospitalId" class="easyui-combobox"
							data-options="multiple:true" style="width: 200px"></select>
					</div>
					<br />
					<div>
						<lable style="margin-left:2px;">订阅医院信息:</lable>
						<textarea id="save_hospital_info" name="save_hospital_info"
							readonly style="width: 200px; height: 100px"
							onkeydown="return banInputSapce(event);"></textarea>
					</div>
				</div>
			</div> -->
		</div>
	</div>
</body>
</html>