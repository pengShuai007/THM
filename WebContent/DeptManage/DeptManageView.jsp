<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="java.io.File,
                 java.io.InputStream,
                 java.io.FileInputStream,
                 APP.*,APP.Comm.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>部门信息管理</title>
<link href="../Resources/Themes/default/easyui.css" rel="stylesheet"
	type="text/css" />
<link href="../Resources/Themes/icon.css" rel="stylesheet"
	type="text/css" />
<link href="../Resources/Styles/iframe-common.css" rel="stylesheet"
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
<script src="DeptManageView.js?time=<%=SystemResource.getNowDayTime()%>" type="text/javascript"></script>
</head>
<body class="easyui-layout">
	<!-- 中部开始 -->
	<div data-options="region:'center'" title="编制部门信息" overflow: auto>
		<table id="grid">
		</table>
		<div id="tb" style="padding: 0px; height: auto">
				<table>
					<tr>
						<td>
							&ensp;本级代码(名称)：<input type="text" id="codeOrName" style="width: 80px" />
							<!-- <input type="checkbox" id="btnCancel" name="status" value="CANCEL" onclick="onQuery()" /> -->
							<!-- <label for="btnCancel" id="cancelLabel">撤销</label> -->
							<a href="#" class="easyui-linkbutton" iconcls="icon-search" plain="false" onclick="onQuery()"> 查询</a>
<!-- 						</td>
					</tr>
					<tr>
						<td> -->
							<a href="#" class="easyui-linkbutton" iconcls="icon-add" plain="false" onclick="openAdd()"> 新增</a>
							<!-- <a href="#" class="easyui-linkbutton" iconcls="icon-redo" plain="false" onclick="cancelDeptById()"> 撤销</a> -->
							<!-- <a href="#" class="easyui-linkbutton" iconcls="icon-undo" plain="false" onclick="restoreDeptById()"> 恢复</a>  -->
							<a href="#" class="easyui-linkbutton" iconcls="icon-edit" plain="false" onclick="openUpdate()"> 修改</a>
							<a href="#" class="easyui-linkbutton" iconcls="icon-edit" plain="false" onclick="openDelete()"> 删除</a>
							<!-- <a href="#" class="easyui-linkbutton" iconcls="icon-redo" plain="false" onclick="onDeptRemove()"> 部门调动</a>
							<a href="#" class="easyui-linkbutton" iconcls="icon-reload" plain="false" onclick="onDeptMerge()"> 部门合并</a> -->
						</td>
					</tr>
				</table>
			</div>
			<!-- 中部结束-->
			<div class="menu-sep">
				<!-- TreeMenu End -->
				<!-- 非grid部分开始 -->
				<div style="display: none">
					<div id="window_add" class="easyui-window"
						data-options="title:'新增编制部门信息',iconCls:'icon-add',closed:true,modal:true,minimizable:false,maximizable:false,collapsible:false"
						style="width: 300px; height: 250px; padding: 3px;" align="center">
						<form class="addform" id="addform">
							<table>
								<tbody>
									<tr >
										<td>上级部门：</td>
										<td><select class="easyui-combobox" id="add_DEPT_CODE"
											name="DEPT_CODE" style="width: 134px"></select></td>
									</tr>
									<tr></tr>
									<tr>
										<td>部门代码：</td>
										<td><input type="text" id="add_DEPT_LIST_CODE"
											name="DEPT_LIST_CODE" style="width: 130px" maxlength="20" /></td>
									</tr>
									<tr></tr>
									<tr>
										<td>部门名称：</td>
										<td><input type="text" id="add_DEPT_NAME"
											name="DEPT_NAME" style="width: 130px" maxlength="25" /></td>
									</tr>
									<tr></tr>
									<tr>
										<td>虚拟部门：</td>
										<!-- <td><input id="add_VM_FLAG" name="VM_FLAG"
											style="width: 230px" /></td> -->
										<td><input type="radio" id="add_VM_FLAG1"
											name="VM_FLAG" value="1" />是 <input
											type="radio" id="add_VM_FLAG2"
											name="VM_FLAG" value="0" checked="checked" />否</td>
									</tr>
<!-- 									<tr style="display: none">
										<td>启用标记：</td>
										<td><input type="radio" id="add_VISIBLE_INDICATOR2"
											name="VISIBLE_INDICATOR" value="1" checked="true" />启用 <input
											type="radio" id="add_VISIBLE_INDICATOR1"
											name="VISIBLE_INDICATOR" value="0" />未启用</td>
									</tr> -->
									<tr></tr>
									<tr>
										<td>备注：</td>
										<td><textarea id="add_COMMENTS" name="COMMENTS"
												class="easyui-validatebox" style="resize: none" maxlength="50"
												data-options="validType:true" cols="16" rows="3"></textarea>
										</td>
									</tr>
								</tbody>
								<tfoot>
									<tr align="center">
										<td colspan="2">
											<div style="float: none; margin: 0 15px 5px 0c">
												<a class="easyui-linkbutton" id="btnsave"
													data-options="iconCls:'icon-ok'" href="javascript:void(0)"
													onclick="javascript:add()">保存</a> <a
													class="easyui-linkbutton"
													data-options="iconCls:'icon-cancel'"
													href="javascript:void(0)" onclick="javascript:cancelAdd()">取消</a>
											</div>
										</td>
									</tr>
								</tfoot>
							</table>
						</form>
					</div>
				</div>
				<div id="window_edit" class="easyui-window"
					data-options="title:'修改编制部门',iconCls:'icon-edit',closed:true,modal:true,minimizable:false,maximizable:false,collapsible:false"
					style="width: 300px; height: 250px; padding: 3px;" align="center">
					<form class="editform" id="editform">
						<input type="hidden" id="edit_DEPT_ID" name="DEPT_ID" />
						<table>
							<tbody>
								<tr >
									<td>上级部门：</td>
									<td><select class="easyui-combobox" id="edit_DEPT_CODE"
											name="DEPT_CODE" style="width: 134px"></select></td>
								</tr>
								<tr></tr>
								<tr>
									<td>部门代码：</td>
									<td><input type="text" id="edit_DEPT_LIST_CODE"
										name="DEPT_LIST_CODE" disabled="disabled" style="width: 130px" /></td>
								</tr>
								<tr></tr>
								<tr>
									<td>部门名称：</td>
									<td><input type="text" id="edit_DEPT_NAME"
										name="DEPT_NAME" style="width: 130px" maxlength="25"  /></td>
								</tr>
								<tr></tr>
								<!-- <tr>
									<td>部门类型：</td>
									<td><input id="edit_DEPT_TYPE" name="DEPT_TYPE"
										style="width: 235px" /></td>
								</tr> -->
								<tr>
								<td>虚拟部门：</td>
								<!-- <td><input id="add_VM_FLAG" name="VM_FLAG"
											style="width: 230px" /></td> -->
								<td><input type="radio" id="edit_VM_FLAG1" name="VM_FLAG"
									value="1" />是 <input type="radio" id="edit_VM_FLAG2"
									name="VM_FLAG" value="0" />否</td>
								</tr>
	<!-- 							<tr>
										<td>启用标记：</td>
										<td><input type="radio" id="add_VISIBLE_INDICATOR2"
											name="VISIBLE_INDICATOR" value="1" />启用 <input
											type="radio" id="add_VISIBLE_INDICATOR1"
											name="VISIBLE_INDICATOR" value="0" />未启用</td>
								</tr> -->
								<tr>
									<td>备注：</td>
									<td><textarea id="edit_COMMENTS" name="COMMENTS"
											class="easyui-validatebox" style="resize: none" maxlength="50" 
											data-options="validType:true" cols="16" rows="3"></textarea>
									</td>
								</tr>
							</tbody>
							<tfoot>
								<tr align="center">
									<td colspan="2">
										<div style="float: none; margin: 0 -15px -5px 0">
											<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'"
												href="javascript:void(0)" onclick="javascript:update()">保存</a>
											<a class="easyui-linkbutton"
												data-options="iconCls:'icon-cancel'"
												href="javascript:void(0)"
												onclick="javascript:cancelUpdate()">取消</a>
										</div>
									</td>
								</tr>
							</tfoot>
						</table>
					</form>
				</div>
			</div>
		</div>
	
	<div style="display: none">
		<!-- 部门调动开始 -->
		<div id="deptRemove_window" class="easyui-window"
			data-options="title:'部门调动',iconCls:'icon-redo',maximizable:false,minimizable:false,collapsible:false,closable:true,
			resizable:false,modal:true,closed:true" style="width: 480px; height: 290px; padding: 5px;">
		<form >
			<table>
				<tbody>
					<tr>
						<td >
							<label style="width:89px">异动部门(A)：</label><input type="text" id="deptFrom" class='easyui-combobox' style="width: 360px" />
						</td>
					</tr>
					<tr>
						<td style="text-align: center; padding: 10px 0px ">
							⇓ ⇓ ⇓ 调动到 ⇓ ⇓ ⇓
						</td>
					</tr>			
					<tr>
						<td>
							<label style="width:89px">目标部门(B)：</label><input type="text" id="deptTarget" class='easyui-combobox' style="width: 360px" />
						</td>
					</tr>
					<tr>
						<td>
							<div style="height:80px;width:430px;padding: 10px 0px">
								说明：
			                    <ul>
			                        <li>1、部门调动：指把一个部门（A）异动为另一个部门（B）的子部门；</li>
			                        <li>2、部门合并：指把一个部门（A）的子部门异动为另一个部门（B）的子部门，并删除该部门（A）。</li>
			                    </ul>
							</div>
						</td>
					</tr>
				</tbody>
				<tfoot>
					<tr align="center">
						<td colspan="1">
							<div style="float: none; margin: 0 5px -5px 0">
								<input type="hidden" id="removeAction" />
								<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="javascript:doAction()">提交</a>
								<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:cancelRemove()">取消</a>
							</div>
						</td>
					</tr>
				</tfoot>
			</table>
		</form>
		</div>
		<!-- 部门调动结束 -->
	</div>
	
</body>
</html>
