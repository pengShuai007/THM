package APP.Comm.View;

import java.util.Map;

import APP.Comm.BLL.BaseBLL;
import APP.Comm.BLL.BaseBllException;
import APP.Comm.Util.CommonUtil;
import APP.Comm.Util.DotNetToJavaStringHelper;

/**
 * function：公用的comboBox请求封装类 作者：左仁帅 时间：2013年1月8日 09:51:03
 */
public class ComboBoxRequest extends BaseBLL {
	/**
	 * id 和text 是否相等
	 */
	private boolean _idEqualsText = false;
	private String _id;
	private String _text;
	private String _defaultId;
	private String _defaultText;
	private String _order;
	private String _where;
	private String _table;
	private String _sort;
	private boolean _isShowInputCode = true;
	private String _inputCode;
	private boolean _isShowIdAndText = false;

	/**
	 * 说明:通用，获取comboBox 作者：左仁帅 时间：2013年1月8日 09:51:59 调用参数： table ：数据库表 必填
	 * id：关联的主键字段 id可以和text相等，必填 text：用于页面显示字段 id可以和text相等，必填
	 * where：执行查询的where条件，以‘AND’开头，格式为示例：AND ID=1 AND TEXT='2',选填
	 * sort：用于排序的字段，选填 order：排序的方式，取值范围：DESC|ASC ,选填 inputCode:快捷输入码,选填,
	 * 如果该选项未填,isShowInputCode选项将失效 isShowInputCode:是否显示快捷输入码，默认值为true
	 * isShowIdAndText：是否同时显示id和text,默认值值false defaultId:默认 id，可以和默认text相等， 选填
	 * defaultText：默认显示，可以和默认id相等，选填
	 * 
	 * @return IList comboBox
	 * @throws BaseBllException
	 */
	public final java.util.List<ComboBox> GetComboBox() throws BaseBllException {
		Map<String,String[]> map = null;
		map = super.getPostParams();
		this._table = CommonUtil.getRequestMapParaValue(map, "table");
		this._id = CommonUtil.getRequestMapParaValue(map, "id");
		this._text = CommonUtil.getRequestMapParaValue(map, "text");
		this._where = CommonUtil.getRequestMapParaValue(map, "where");
		this._sort = CommonUtil.getRequestMapParaValue(map, "sort");
		this._order = CommonUtil.getRequestMapParaValue(map, "order");
		this._defaultId = CommonUtil.getRequestMapParaValue(map, "defaultId");
		this._defaultText = CommonUtil.getRequestMapParaValue(map,
				"defaultText");
		this._inputCode = CommonUtil.getRequestMapParaValue(map, "inputCode");
		if (!DotNetToJavaStringHelper.isNullOrEmpty(map.get("isShowInputCode")
				+ "")
				&& (CommonUtil.getRequestMapParaValue(map, "isShowInputCode") + "")
						.toLowerCase().equals("false")) {
			this._isShowInputCode = false;
		}
		if (!DotNetToJavaStringHelper.isNullOrEmpty(map.get("isShowIdAndText")
				+ "")
				&& (CommonUtil.getRequestMapParaValue(map, "isShowIdAndText") + "")
						.toLowerCase().equals("true")) {
			this._isShowIdAndText = true;
		}
		return this.CreateComboBox(this.CreateNormalSql().toString());
	}

	/**
	 * 说明:查询系统系统代码表 调用参数： key：视图V_SYS_CODE_ITEM的DICT_ID字段对应的值,必填 defaultId:默认
	 * id，可以和默认text相等， 选填 defaultText：默认显示，可以和默认id相等，选填
	 * 
	 * @throws BaseBllException
	 */
	public final java.util.List<ComboBox> GetSysCodeComboBox()
			throws BaseBllException {
		Map<String,String[]> map = null;
		map = super.getPostParams();
		String key = CommonUtil.getRequestMapParaValue(map, "key");
		this._defaultId = CommonUtil.getRequestMapParaValue(map, "defaultId");
		this._defaultText = CommonUtil.getRequestMapParaValue(map,
				"defaultText");
		if (DotNetToJavaStringHelper.isNullOrEmpty(key)) {
			throw new BaseBllException("key参数为必填！");
		}
		this._id = "ITEM_CODE";
		this._text = "ITEM_NAME";
		this._table = "V_SYS_CODE_ITEM";
		this._where = "AND DICT_ID='" + key + "'";
		this._order = "ASC";
		this._sort = "ITEM_NAME";
		return this.CreateComboBox(this.CreateNormalSql().toString());
	}

	/**
	 * 说明:查询科室信息 调用参数： key：视图V_DR_DEPT的DEPT_NAME字段对应的值进行模糊查询,选填
	 * isShowInputCode:是否显示快捷输入码，默认值为true
	 * isShowIdAndText：是否同时显示id和text,默认值值false defaultId:默认 id，可以和默认text相等， 选填
	 * defaultText：默认显示，可以和默认id相等，选填 作者：左仁帅 时间：2013年1月15日 10:29:36
	 * 
	 * @throws BaseBllException
	 */
	public final java.util.List<ComboBox> GetDrDept() throws BaseBllException {
		Map<String,String[]> map = null;
		map = super.getPostParams();
		String key = CommonUtil.getRequestMapParaValue(map, "key");
		this._defaultId = CommonUtil.getRequestMapParaValue(map, "defaultId");
		this._defaultText = CommonUtil.getRequestMapParaValue(map,
				"defaultText");
		this._id = "DEPT_CODE";
		this._text = "DEPT_NAME";
		this._table = "V_DR_DEPT";
		this._where = "AND DEPT_NAME like'%"
				+ (DotNetToJavaStringHelper.isNullOrEmpty(key) ? "" : key)
				+ "%'";
		this._order = "DESC";
		this._sort = "DEPT_NAME";
		this._inputCode = "INPUT_CODE";
		if (!DotNetToJavaStringHelper.isNullOrEmpty(CommonUtil
				.getRequestMapParaValue(map, "isShowInputCode") + "")
				&& (CommonUtil.getRequestMapParaValue(map, "isShowInputCode") + "")
						.toLowerCase().equals("false")) {
			this._isShowInputCode = false;
		}
		if (!DotNetToJavaStringHelper.isNullOrEmpty(CommonUtil
				.getRequestMapParaValue(map, "isShowIdAndText") + "")
				&& (CommonUtil.getRequestMapParaValue(map, "isShowIdAndText") + "")
						.toLowerCase().equals("true")) {
			this._isShowIdAndText = true;
		}
		return this.CreateComboBox(this.CreateNormalSql().toString());
	}

	/**
	 * 说明:核算单元 调用参数 key： 表DR_RECKON_UNIT的DEPT_NAME字段对应的值进行模糊查询,选填 type:
	 * 表DR_RECKON_UNIT的RECKON_UNIT_TYPE字段,取值范围为：D:科室| G:医疗组 |O:其它 |A:全部 ,必填
	 * isShowInputCode:是否显示快捷输入码，默认值为true
	 * isShowIdAndText：是否同时显示id和text,默认值值fasle defaultId:默认 id，可以和默认text相等， 选填
	 * defaultText：默认显示，可以和默认id相等，选填 作者：左仁帅 时间：2013年1月15日 10:29:36
	 * 
	 * @return
	 * @throws BaseBllException
	 */
	public final java.util.List<ComboBox> GetReckonUnit()
			throws BaseBllException {
		Map<String,String[]> map = null;
		map = super.getPostParams();
		String key = CommonUtil.getRequestMapParaValue(map, "key");
		String type = CommonUtil.getRequestMapParaValue(map, "type");
		this._defaultId = CommonUtil.getRequestMapParaValue(map, "defaultId");
		this._defaultText = CommonUtil.getRequestMapParaValue(map,
				"defaultText");
		if (DotNetToJavaStringHelper.isNullOrEmpty(type)) {
			throw new BaseBllException("type参数为必填！");
		}
		this._id = "RECKON_UNIT_ID";
		this._text = "RECKON_UNIT_NAME";
		this._table = "DR_RECKON_UNIT";
		this._where = type.equals("A") ? " AND  RECKON_UNIT_NAME  like'%"
				+ (DotNetToJavaStringHelper.isNullOrEmpty(key) ? "" : key)
				+ "%'" : "AND RECKON_UNIT_TYPE=" + type
				+ " AND  RECKON_UNIT_NAME  like'%"
				+ (DotNetToJavaStringHelper.isNullOrEmpty(key) ? "" : key)
				+ "%'";
		this._order = "DESC";
		this._sort = "RECKON_UNIT_NAME";
		this._inputCode = "INPUT_CODE";
		if (!DotNetToJavaStringHelper.isNullOrEmpty(CommonUtil
				.getRequestMapParaValue(map, "isShowInputCode") + "")
				&& (CommonUtil.getRequestMapParaValue(map, "isShowInputCode") + "")
						.toLowerCase().equals("false")) {
			this._isShowInputCode = false;
		}
		if (!DotNetToJavaStringHelper.isNullOrEmpty(CommonUtil
				.getRequestMapParaValue(map, "isShowIdAndText") + "")
				&& (CommonUtil.getRequestMapParaValue(map, "isShowIdAndText") + "")
						.toLowerCase().equals("true")) {
			this._isShowIdAndText = true;
		}
		return this.CreateComboBox(this.CreateNormalSql().toString());
	}

	/**
	 * 说明:科室核算单元对照 调用参数 key：视图V_DR_DEPT_RECKON_UNIT的DEPT_NAME字段对应的值进行模糊查询,选填
	 * isShowInputCode:是否显示快捷输入码，默认值为true isShowIdAndText：是否同时显示id和text,默认值值true
	 * defaultId:默认 id，可以和默认text相等， 选填 defaultText：默认显示，可以和默认id相等，选填 作者：左仁帅
	 * 时间：2013年1月15日 10:29:36
	 * 
	 * @return
	 * @throws BaseBllException
	 */
	public final java.util.List<ComboBox> GetDeptReckonUnit()
			throws BaseBllException {
		Map<String,String[]> map = null;
		map = super.getPostParams();
		String key = CommonUtil.getRequestMapParaValue(map, "key");
		this._defaultId = CommonUtil.getRequestMapParaValue(map, "defaultId");
		this._defaultText = CommonUtil.getRequestMapParaValue(map,
				"defaultText");
		this._id = "DEPT_CODE";
		this._text = "DEPT_NAME";
		this._table = "V_DR_DEPT_RECKON_UNIT";
		this._where = "AND DEPT_NAME like'%"
				+ (DotNetToJavaStringHelper.isNullOrEmpty(key) ? "" : key)
				+ "%'";
		this._order = "DESC";
		this._sort = "DEPT_NAME";
		this._inputCode = "INPUT_CODE";
		if (!DotNetToJavaStringHelper.isNullOrEmpty(CommonUtil
				.getRequestMapParaValue(map, "isShowInputCode") + "")
				&& (CommonUtil.getRequestMapParaValue(map, "isShowInputCode") + "")
						.toLowerCase().equals("false")) {
			this._isShowInputCode = false;
		}
		if (!DotNetToJavaStringHelper.isNullOrEmpty(CommonUtil
				.getRequestMapParaValue(map, "isShowIdAndText") + "")
				&& (CommonUtil.getRequestMapParaValue(map, "isShowIdAndText") + "")
						.toLowerCase().equals("true")) {
			this._isShowIdAndText = true;
		}
		return this.CreateComboBox(this.CreateNormalSql().toString());

	}

	/**
	 * 说明：组装ComboBox 作者：左仁帅 时间：2013年1月8日 09:51:59 修改 1 ：左仁帅 2013年1月14日 17:08:06
	 * 修改内容：默认值
	 * 
	 * @param sql
	 * @return
	 * @throws BaseBllException
	 */
	private java.util.List<ComboBox> CreateComboBox(String sql)
			throws BaseBllException {
		java.util.List<ComboBox> comboBoxs = new java.util.ArrayList<ComboBox>();
		// 默认值
		if (DotNetToJavaStringHelper.isNullOrEmpty(this._defaultId)
				&& !DotNetToJavaStringHelper.isNullOrEmpty(this._defaultText)) {
			ComboBox comboBox = new ComboBox();
			comboBox.setid("");
			comboBox.settext(this._defaultText);
			comboBox.setselected(true);
			comboBoxs.add(comboBox);
		} else if (!DotNetToJavaStringHelper.isNullOrEmpty(this._defaultId)
				&& !DotNetToJavaStringHelper.isNullOrEmpty(this._defaultText)) {
			ComboBox comboBox = new ComboBox();
			comboBox.setid(this._defaultId);
			comboBox.settext(this._defaultText);
			comboBox.setselected(true);
			comboBoxs.add(comboBox);
		}
		return comboBoxs;
	}

	/**
	 * 根据请求参数，拼装sql
	 * 
	 * @return
	 * @throws BaseBllException
	 */
	private StringBuilder CreateNormalSql() throws BaseBllException {

		if (_table == null || (new String("")).equals(_table)) {
			throw new BaseBllException("table参数错误！");
		} else if (this._id == null || (new String("")).equals(this._id)) {
			throw new BaseBllException("id参数错误！");
		} else if (this._text == null || (new String("")).equals(this._text)) {
			throw new BaseBllException("text参数错误！");
		}
		StringBuilder sql = null;
		// id 和 text 是否相等
		if (this._id.equals(this._text)) {
			this._idEqualsText = true;
			sql = new StringBuilder("SELECT  DISTINCT    " + this._id);
		} else {
			this._idEqualsText = false;
			sql = new StringBuilder("SELECT  DISTINCT   " + this._id + ","
					+ this._text);
		}
		if (!DotNetToJavaStringHelper.isNullOrEmpty(this._inputCode)
				&& this._isShowInputCode) {
			sql.append("," + this._inputCode + " FROM " + _table + " WHERE 1=1");
		} else {
			sql.append(" FROM " + _table + " WHERE 1=1");
		}
		if (_where != null && !(new String("")).equals(_where)) {
			sql.append(_where);
		}
		if (_sort != null && !(new String("")).equals(_sort)) {
			sql.append(" ORDER BY " + _sort);
			if (_order != null && !(new String("")).equals(_order)) {
				sql.append(" " + _order + " ");
			} else {
				sql.append(" DESC ");
			}
		}
		String checkSql = sql.toString().toUpperCase();
		if (checkSql.indexOf("INSERT") > 0 || checkSql.indexOf("DELETE") > 0
				|| checkSql.indexOf("UPDATE") > 0) {
			throw new BaseBllException("警告，您在进行非法sql注入活动！");
		}
		return sql;
	}

}