package APP.Comm.Handler;

import APP.Comm.BLL.BaseBllException;
import APP.Comm.DotNet.HttpContext;
import APP.Comm.Util.JsonUtil;
import APP.Comm.Util.OutJsonType;
import APP.Comm.View.ComboGrid;
import APP.Comm.View.ComboGridRequest;

/**
 * ComboGridHandler 的摘要说明
 */
public class ComboGridHandler {
	@SuppressWarnings("unchecked")
	public void CommComboGrid(HttpContext context) throws BaseBllException {
		ComboGridRequest comboGridRequest = new ComboGridRequest();
		context.getHttpHandler().SetParamsToBLL(comboGridRequest,context);
		comboGridRequest.getBaseDB().NeedOpen(true);
		comboGridRequest.getBaseDB().NeedTransaction(false);
		java.util.List<ComboGrid> comboBoxs = null;
		comboBoxs = (java.util.List<ComboGrid>) context.getHttpHandler().BLLContainer
				.DoProcess(comboGridRequest, "CommComboGrid");
		context.write(JsonUtil.listBeanToJsonString(0, true, "执行成功！",
				comboBoxs, OutJsonType.Combox));
	}
}
