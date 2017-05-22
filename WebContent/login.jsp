<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>运维管理系统</title>
<link href="Resources/Themes/default/easyui.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="Resources/Styles/layout/batch.css" media="all" />
<link href="Resources/Themes/default/easyui.css" rel="stylesheet" type="text/css" />
<link href="Resources/Themes/icon.css" rel="stylesheet" type="text/css" />
<link href="Resources/Styles/indexIniInfo.css" rel="stylesheet" type="text/css" />
<script src="Resources/Scripts/plug-in/jquery-1.8.0.min.js" type="text/javascript"></script>
<script src="Resources/Scripts/plug-in/jquery.easyui.min.js" type="text/javascript"></script>
<script src="Resources/Scripts/plug-in/easyui-lang-zh_CN.js" type="text/javascript"></script>
<script src="Resources/Scripts/common.js" type="text/javascript"></script>
<script src="Resources/Scripts/iframe-common.js" type="text/javascript"></script>
<script src="Resources/Scripts/login.js" type="text/javascript"></script>
<script src="Resources/Scripts/Barrett.js" type="text/javascript"></script>
<script src="Resources/Scripts/BigInt.js" type="text/javascript"></script>
<script src="Resources/Scripts/des_pack.js" type="text/javascript"></script>
<script src="Resources/Scripts/RSA.js" type="text/javascript"></script>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	top: 0px;
	left: 0px;
	width: 100%;
	height: 100%;
}

.copyrightCss {
	/*color: #FFFFFF;*/
	color: #000;
	font-family: "微软雅黑";
}

.mainCss {
	color: #FFFFFF;
	font-family: "微软雅黑";
	width: 100%;
	height: 600px;
	top: 0px;
	left: 0px;
	bottom: 0px;
}

#Layer1 {
	position: absolute;
	width: 200px;
	height: 115px;
	z-index: 11;
}

body.login #main {
	position: absolute;
	overflow: hidden;
	padding: 0 0 0;
	width: 1000px;
	margin-left: -550px;
	top: 20%;
	left: 50%;
	right: 50%;
}

.login-doLogin {
	font-size: 24px;
	display: none;
	/*background-color:#666666;*/
	z-index: 10000;
	position: absolute;
	left: 30%;
	right: 30%;
	top: 70%;
	/*width:524px;
     height:100px;*/
	/* filter:alpha(opacity=50); */
	/* IE */
	/*  -moz-opacity:0.5; */
	/* Moz + FF */
	/*  opacity: 0.5; */
}

.login-doLogin div {
	position: absolute;
	top: 35%;
	width: 524px;
	color: #FFFFFF;
	text-align: center;
}

-->
#login-container {
	filter: alpha(Opacity = 80);
	-moz-opacity: 0.5;
	opacity: 1;
	z-index: 100;
}

#tbNJContent td {
	text-align: justify;
	text-justify: distribute;
	color: #4D4E52;
}
</style>
</head>
<body id="com-kyee" class="theme-default login"
	style="height: 100%; width: 100%;">
	<img ID="BackgroundImage"
		class="body-background"
		Style="top: 0; left: 0; width: 100%; height: 100%; margin: 0px; position: absolute;"
		src="" />
	<div id="main"style="background-image: url(Resources/Images/Default/login.png);height: 350px;">
		<div align="right"style="text-align: right; margin: 0px 0px 0px 0px;">
			<div id="title-heading" class="pagetitle"
				style="width: 100%; text-align: right;">
				<table border="0" cellpadding="0" cellspacing="0" align="center"
					style="width: 100%; margin-top: 50px; margin-left: 470px;">
					<tr>
						<td style="width: 68%; text-align: left;"><a href=""
							style="font-family: '微软雅黑'; color: #74483; margin-left: 0.8em; text-decoration: none; line-height: 32px; display: inline-block;">
								<span ID="SYS_NAME" style="font-size: 20pt; font-weight: bold">运维管理系统</span>
						</a></td>
					</tr>
					<tr>
						<td style="width: 68%; text-align: left;"><a href=""
							style="color: white; margin-left: 0.8em; text-decoration: none; line-height: 40px; display: inline-block;">
								<span ID="SYS_NAME" style="font-size: 16pt; font-weight: bold">登录</span>
						</a></td>
					</tr>
				</table>
			</div>
		</div>
		<div id="login-container"
			style="width: 100%;margin-left: 350px;">
			<form id="loginForm" method="post" action=""
				class="aui login-form-container">
				<fieldset class="compact-form-fields" id="nLogin"
					style="display: block; top: 10px; left: 0px;">
					<legend class="assistive">
						<span>Log in to APP</span>
					</legend>
					<table border="0" cellpadding="0" cellspacing="0">
						<tr style="height: 50px;">
							<td>
								<div class="field-group">
									<label for="uname" style="font-family: '微软雅黑'; color: #003366"> 用&nbsp;&nbsp;&nbsp;户</label>
									<input id="uname" name="operatorName" type="text" />
								</div>
								<div class="field-group">
									<label for="pass" style="font-family: '微软雅黑'; color: #003366"> 密&nbsp;&nbsp;&nbsp;码 </label>
									<input id="pass" name="passWord" type="password" />
								</div>
							</td>
						</tr>
						<tr style="height: 110px;">
							<td style="text-align: right;">
							<button type="button" style="width:100px;height:30px" onClick="login()">登&nbsp;&nbsp;录</button>
							</td>
						</tr>
					</table>
				</fieldset>
			</form>
		</div>
	</div>
</body>
</html>