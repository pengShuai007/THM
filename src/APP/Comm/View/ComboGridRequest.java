package APP.Comm.View;

import java.util.Map;

import APP.Comm.BLL.BaseBLL;
import APP.Comm.BLL.BaseBllException;
import APP.Comm.DataBase.DotNet.DataRow;
import APP.Comm.DataBase.Helper.AppDataTable;
import APP.Comm.Util.CommonUtil;
import APP.Comm.Util.DotNetToJavaStringHelper;
import APP.Comm.Util.RefObject;

public class ComboGridRequest extends BaseBLL {
	private String _table;
	private String _id;
	private String _text;
	private String _inputCode;
	private boolean _isDistinct = true;
	private String _defaultId;
	private String _defaultText;
	private String _where;
	private String _sort;
	private String _order;
	private boolean _idEqualsText;
	private String _maxRows = "A";
	private String _question;
	// Edit Add >>> zhangbf 2013-8-19 15:58:54
	private boolean _isUserDataPerm = false; // 是否使用用户数据权限校验，默认不使用

	/**
	 * 说明：通用获取ComboGrid 作者：左仁帅 时间：2013年2月27日 20:22:31
	 * 
	 * @return
	 * @throws BaseBllException
	 */
	public final java.util.List<ComboGrid> CommComboGrid()
			throws BaseBllException {
		Map<String, String[]> map = null;
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
		this._inputCode = CommonUtil.getRequestMapParaValue(map, "inputCode")
				+ "";
		if (CommonUtil.getRequestMapParaValue(map, "isDistinct") != null
				&& (CommonUtil.getRequestMapParaValue(map, "isDistinct") + "")
						.toLowerCase().equals("false")) {
			this._isDistinct = false;
		}
		// Edit Add >>> zhangbf 2013-8-19 16:01:05
		// 是否使用用户数据权限校验
		if (CommonUtil.getRequestMapParaValue(map, "isUserDataPerm") != null
				&& (CommonUtil.getRequestMapParaValue(map, "isUserDataPerm") + "")
						.toLowerCase().equals("true")) {
			this._isUserDataPerm = true;
		}
		// Edit Add <<< zhangbf 2013-8-19 16:01:05
		return this.CreateComboGrid(this.CreateNormalSql());
	}

	/**
	 * 说明:查询系统代码表 调用参数： key：视图V_SYS_CODE_ITEM的DICT_ID字段对应的值,必填 defaultId:默认
	 * id，可以和默认text相等， 选填 defaultText：默认显示，可以和默认id相等，选填
	 * isDistinct：是否去掉相同的数据，默认true sort order where maxRows
	 * 
	 * @throws BaseBllException
	 */
	public final java.util.List<ComboGrid> GetSysCode() throws BaseBllException {
		Map<String, String[]> map = null;
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
		this._where = DotNetToJavaStringHelper.isNullOrEmpty(map.get("where")
				+ "") ? " AND DICT_ID='" + key + "'" : map.get("where")
				+ " AND DICT_ID='" + key + "'";
		this._sort = CommonUtil.getRequestMapParaValue(map, "sort");
		this._order = CommonUtil.getRequestMapParaValue(map, "order");
		if (DotNetToJavaStringHelper.isNullOrEmpty(this._order)) {
			this._order = "ASC";
		}
		if (DotNetToJavaStringHelper.isNullOrEmpty(this._sort)) {
			this._sort = "ITEM_NAME";
		}
		this._inputCode = "";
		if (map.get("isDistinct") != null
				&& (map.get("isDistinct") + "").toLowerCase().equals("false")) {
			this._isDistinct = false;
		}
		return this.CreateComboGrid(this.CreateNormalSql());
	}

	/**
	 * 说明:查询科室信息，视图V_DR_DEPT 调用参数： defaultId:默认 id，可以和默认text相等， 选填
	 * defaultText：默认显示，可以和默认id相等，选填 isDistinct：是否去掉相同的数据，默认true 作者：左仁帅
	 * 时间：2013年2月27日 20:33:43
	 * 
	 * @throws BaseBllException
	 */
	public final java.util.List<ComboGrid> GetDrDept() throws BaseBllException {
		Map<String, String[]> map = null;
		map = super.getPostParams();
		this._defaultId = CommonUtil.getRequestMapParaValue(map, "defaultId");
		this._defaultText = CommonUtil.getRequestMapParaValue(map,
				"defaultText");
		this._id = "DEPT_CODE";
		this._text = "DEPT_NAME";
		this._table = "V_DR_DEPT";
		this._where = CommonUtil.getRequestMapParaValue(map, "where");
		this._sort = CommonUtil.getRequestMapParaValue(map, "sort");
		this._order = CommonUtil.getRequestMapParaValue(map, "order");
		if (DotNetToJavaStringHelper.isNullOrEmpty(this._order)) {
			this._order = "DESC";
		}
		if (DotNetToJavaStringHelper.isNullOrEmpty(this._sort)) {
			this._sort = "DEPT_NAME";
		}
		this._inputCode = "INPUT_CODE";
		if (map.get("isDistinct") != null
				&& (map.get("isDistinct") + "").toLowerCase().equals("false")) {
			this._isDistinct = false;
		}
		return this.CreateComboGrid(this.CreateNormalSql());
	}

	/**
	 * 说明:核算单元,表DR_RECKON_UNIT 调用参数 type:
	 * 表DR_RECKON_UNIT的RECKON_UNIT_TYPE字段,取值范围为：D:科室| G:医疗组 |O:其它 |A:全部 ,必填
	 * defaultId:默认 id，可以和默认text相等， 选填 defaultText：默认显示，可以和默认id相等，选填 where order
	 * sort isDistinct 作者：左仁帅 时间：2013年1月15日 10:29:36
	 * 
	 * @return
	 * @throws BaseBllException
	 */
	public final java.util.List<ComboGrid> GetReckonUnit()
			throws BaseBllException {
		Map<String, String[]> map = null;
		map = super.getPostParams();
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
		this._where = CommonUtil.getRequestMapParaValue(map, "where") + "";
		if (!DotNetToJavaStringHelper.isNullOrEmpty(type)
				&& !type.toUpperCase().equals("A")) {
			this._where = DotNetToJavaStringHelper.isNullOrEmpty(map
					.get("where") + "") ? " AND RECKON_UNIT_TYPE='" + type
					+ "'" : map.get("where") + " AND RECKON_UNIT_TYPE='" + type
					+ "'";
		}
		if (DotNetToJavaStringHelper.isNullOrEmpty(this._order)) {
			this._order = "DESC";
		}
		if (DotNetToJavaStringHelper.isNullOrEmpty(this._sort)) {
			this._sort = "RECKON_UNIT_NAME";
		}
		this._inputCode = "INPUT_CODE";
		if (map.get("isDistinct") != null
				&& (map.get("isDistinct") + "").toLowerCase().equals("false")) {
			this._isDistinct = false;
		}
		return this.CreateComboGrid(this.CreateNormalSql());
	}

	/**
	 * 说明:科室核算单元对照 V_DR_DEPT_RECKON_UNIT 调用参数 defaultId:默认 id，可以和默认text相等， 选填
	 * defaultText：默认显示，可以和默认id相等，选填 isDistinct where order sort 作者：左仁帅
	 * 时间：2013年1月15日 10:29:36
	 * 
	 * @return
	 * @throws BaseBllException
	 */
	public final java.util.List<ComboGrid> GetDeptReckonUnit()
			throws BaseBllException {
		Map<String, String[]> map = null;
		map = super.getPostParams();
		this._defaultId = CommonUtil.getRequestMapParaValue(map, "defaultId");
		this._defaultText = CommonUtil.getRequestMapParaValue(map,
				"defaultText");
		this._id = "DEPT_CODE";
		this._text = "DEPT_NAME";
		this._table = "V_DR_DEPT_RECKON_UNIT";
		this._where = CommonUtil.getRequestMapParaValue(map, "where") + "";
		this._order = CommonUtil.getRequestMapParaValue(map, "order") + "";
		this._sort = CommonUtil.getRequestMapParaValue(map, "sort") + "";
		if (DotNetToJavaStringHelper.isNullOrEmpty(this._order)) {
			this._order = "DESC";
		}
		if (DotNetToJavaStringHelper.isNullOrEmpty(this._sort)) {
			this._sort = "DEPT_NAME";
		}
		this._inputCode = "INPUT_CODE";
		if (map.get("isDistinct") != null
				&& (map.get("isDistinct") + "").toLowerCase().equals("false")) {
			this._isDistinct = false;
		}
		return this.CreateComboGrid(this.CreateNormalSql());

	}

	/**
	 * 说明：根据sql创建ComboGrids 作者：左仁帅 时间：2013年2月27日 20:20:39
	 * 
	 * @param sql
	 * @return
	 * @throws BaseBllException
	 */
	private java.util.List<ComboGrid> CreateComboGrid(StringBuilder sql)
			throws BaseBllException {
		java.util.List<ComboGrid> ComboGrids = new java.util.ArrayList<ComboGrid>();
		this._idEqualsText = this._id.toLowerCase().equals(
				this._text.toLowerCase());
		ComboGrid ComboGrid = new ComboGrid();
		// 默认值)))
		if (DotNetToJavaStringHelper.isNullOrEmpty(this._defaultId)
				&& !DotNetToJavaStringHelper.isNullOrEmpty(this._defaultText)) {
			ComboGrid.setid("");
		} else if (!DotNetToJavaStringHelper.isNullOrEmpty(this._defaultId)
				&& !DotNetToJavaStringHelper.isNullOrEmpty(this._defaultText)) {
			ComboGrid.setid(this._defaultId);
		}
		ComboGrid.settext(this._defaultText);
		ComboGrids.add(ComboGrid);
		AppDataTable datetalbe = super.getBaseDB().Query(sql.toString(), null);
		if (datetalbe != null) {
			if (datetalbe.getDataTable() != null) {
				for (DataRow dr : datetalbe.getDataTable().getRow()) {
					ComboGrid comboGrid = new ComboGrid();
					String id = dr.getStringColumn(this._id) == null ? "" : dr
							.getStringColumn(this._id).toString();
					String text = dr.getStringColumn(this._text) == null ? ""
							: dr.getStringColumn(this._text).toString();
					String inputCode = "";
					if (!DotNetToJavaStringHelper
							.isNullOrEmpty(this._inputCode)) {
						inputCode = dr.getStringColumn(this._inputCode) == null ? ""
								: dr.getStringColumn(this._inputCode)
										.toString();
					}
					comboGrid.setid(id);
					comboGrid.settext(this._idEqualsText ? id : text);
					comboGrid.setinputCodeUpper(inputCode.toUpperCase());
					comboGrid.setinputCodeLower(inputCode.toLowerCase());
					ComboGrids.add(comboGrid);
				}
			}
		}
		return ComboGrids;
	}

	/**
	 * 说明：根据请求参数，拼装sql 作者：左仁帅 时间：2013年2月27日 20:21:00
	 * 
	 * @return
	 * @throws BaseBllException
	 */
	private StringBuilder CreateNormalSql() throws BaseBllException {
		Map<String, String[]> map = null;
		map = super.getPostParams();
		if (_table == null || (new String("")).equals(_table)) {
			throw new BaseBllException("table参数错误！");
		} else if (this._id == null || (new String("")).equals(this._id)) {
			throw new BaseBllException("id参数错误！");
		} else if (this._text == null || (new String("")).equals(this._text)) {
			throw new BaseBllException("text参数错误！");
		}
		this._question = CommonUtil.getRequestMapParaValue(map, "q") == null ? ""
				: (CommonUtil.getRequestMapParaValue(map, "q") + "").trim();
		this._maxRows = CommonUtil.getRequestMapParaValue(map, "maxRows");
		StringBuilder sql = new StringBuilder(
				"SELECT * FROM ( SELECT ROWNUM R , T2.* FROM (SELECT ");
		// distince
		if (this._isDistinct) {
			sql.append("DISTINCT ");
		}
		// id和text是否相等，相等的话就只根据id进行查询
		if (this._id.toLowerCase().equals(this._text.toLowerCase())) {
			sql.append(this._id + " ");
		} else {
			sql.append(this._id + "," + this._text + " ");
		}
		// Edit Add >>> zhangbf 2013-09-23 15:13:08
		// 排序字段
		/**
		 * comments:id和text都需要判断是否和sort字段匹配
		 * 
		 * sjl modify 2013-11-3上午10:34:51
		 */
		if (!DotNetToJavaStringHelper.isNullOrEmpty(_sort)
				&& !(_id.equalsIgnoreCase(_sort) || _text
						.equalsIgnoreCase(_sort))) {
			sql.append(", " + _sort.trim() + " ");
		}
		// Edit Add <<< zhangbf 2013-09-23 15:13:08
		// 是否有输入码
		if (!DotNetToJavaStringHelper.isNullOrEmpty(this._inputCode)) {
			sql.append("," + this._inputCode + " ");
		}
		// table
		sql.append(" FROM " + _table + " WHERE 1=1 AND ");
		// Edit Add >>> zhangbf 2013-8-19 16:03:18
		if (_isUserDataPerm) {
			String userId = "guest";
			sql.append(" USER_ID = '" + userId + "' AND ");
		}
		sql.append(" ( ");
		// Edit Add <<< zhangbf 2013-8-19 16:03:30
		if (this._id.toLowerCase().equals(this._text.toLowerCase())) {
			sql.append(" " + this._id + " LIKE '%"
					+ this._question.toLowerCase() + "%' OR " + this._id
					+ " LIKE '%" + this._question.toUpperCase() + "%' ");
		} else {
			sql.append(" " + this._id + " LIKE '%"
					+ this._question.toLowerCase() + "%' OR  " + this._text
					+ " LIKE '%" + this._question.toLowerCase() + "%' OR  "
					+ this._id + " LIKE '%" + this._question.toUpperCase()
					+ "%' OR  " + this._text + " LIKE '%"
					+ this._question.toUpperCase() + "%' ");
		}
		// 是否有输入码
		if (!DotNetToJavaStringHelper.isNullOrEmpty(this._inputCode)) {
			sql.append(" OR " + this._inputCode + " LIKE '%"
					+ this._question.toUpperCase() + "%'  OR "
					+ this._inputCode + " LIKE '%"
					+ this._question.toLowerCase() + "%' ");
		}
		// 查询条件
		if (!DotNetToJavaStringHelper.isNullOrEmpty(_where)) {
			sql.append(" ) " + _where + " )T2 ) T ");
		} else {
			sql.append(" )  )T2 ) T ");
		}
		int maxRows = 0;
		RefObject<Integer> tempRef_maxRows = new RefObject<Integer>(maxRows);
		boolean tempVar = !DotNetToJavaStringHelper
				.isNullOrEmpty(this._maxRows)
				&& Integer.parseInt(this._maxRows) > 0;
		// if(tempVar) {
		// maxRows = tempRef_maxRows.argvalue;
		// }
		if (tempVar) {
			sql.append(" WHERE T.R<" + maxRows);
		}
		// 排序))
		if (!DotNetToJavaStringHelper.isNullOrEmpty(_sort)) {
			sql.append(" ORDER BY T." + _sort.trim() + " ");
			// 排序方式
			if (!DotNetToJavaStringHelper.isNullOrEmpty(_order)) {
				sql.append(_order + " ");
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