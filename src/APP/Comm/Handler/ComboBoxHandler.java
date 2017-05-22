package APP.Comm.Handler;

import APP.Comm.BLL.BaseBllException;
import APP.Comm.DotNet.HttpContext;
import APP.Comm.Util.JsonUtil;
import APP.Comm.Util.OutJsonType;
import APP.Comm.View.ComboBox;
import APP.Comm.View.ComboBoxRequest;

/**
 * function: 通用的工具类,用于生成comboBox 作者：左仁帅 时间：2013年1月8日 10:38:37
 */
public class ComboBoxHandler {

	/**
	 * 说明:查询系统系统代码表 调用参数： key：视图V_SYS_CODE_ITEM的DICT_ID字段对应的值,必填 defaultId:默认
	 * id，可以和默认text相等， 选填 defaultText：默认显示，可以和默认id相等，选填
	 * 
	 * @param context
	 */
	public void DoGetSysCodeComboBox(HttpContext context)
			throws BaseBllException {
		ComboBoxRequest comboBoxRequest = new ComboBoxRequest();
		context.getHttpHandler().SetParamsToBLL(comboBoxRequest,context);
		@SuppressWarnings("unchecked")
		java.util.List<ComboBox> comboBoxs = (java.util.List<ComboBox>) context
				.getHttpHandler().BLLContainer.DoProcess(comboBoxRequest,
				"GetSysCodeComboBox");
		context.write(JsonUtil.listBeanToJsonString(0, true, "执行成功！",
				comboBoxs, OutJsonType.Combox));
	}

	/**
	 * 说明:说明:通用，获取comboBox 作者：左仁帅 时间：2013年1月8日 10:39:40 调用参数： table ：数据库表 必填
	 * id：关联的主键字段 id可以和text相等，必填 text：用于页面显示字段 id可以和text相等，必填
	 * where：执行查询的where条件，以‘AND’开头，格式为示例：AND ID=1 AND TEXT='2',选填
	 * sort：用于排序的字段，选填 order：排序的方式，取值范围：DESC|ASC ,选填 defaultId:默认
	 * id，可以和默认text相等， 选填 defaultText：默认显示，可以和默认id相等，选填
	 * 
	 * @param context
	 *            上下文
	 */
	public void DoGetComboBox(HttpContext context) throws BaseBllException {
		ComboBoxRequest comboBoxRequest = new ComboBoxRequest();
		context.getHttpHandler().SetParamsToBLL(comboBoxRequest,context);
		@SuppressWarnings("unchecked")
		java.util.List<ComboBox> comboBoxs = (java.util.List<ComboBox>) context
				.getHttpHandler().BLLContainer.DoProcess(comboBoxRequest,
				"GetComboBox");
		context.write(JsonUtil.listBeanToJsonString(0, true, "执行成功！",
				comboBoxs, OutJsonType.Combox));
	}
}
