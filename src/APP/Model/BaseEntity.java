package APP.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 所有Entity的祖先 施建龙 2012-12-21
 */
public class BaseEntity implements Serializable {
	/**
	 * 每条数据记录的唯一编号 Ext包中对应的Entity需要覆盖本方法 施建龙 2012-12-21
	 * 
	 * @return
	 */
	public String GetId() {
		return null;
	}

	/**
	 * 组合主键多列中间使用逗号(,)分隔
	 * 
	 * @return
	 */
	public String GetPrimaryKeyName() {
		return "";
	}

	/**
	 * 是否需要在更新过程中使用防冲突版本控制 如果需要做版本控制，则表中必须存在列名为VERSION的字段
	 * 在Entity扩展类中覆盖此方法，标识是否需要冲突监测
	 * 
	 * @return
	 */
	public boolean Versioned() {
		return false;
	}

	/**
	 * 标识实体是否为只读，例如：从数据库select数据，此时不需要记录UpdateAttribute，则可设置entity的ReadOnly=true
	 * ； 编写：施建龙 时间：2013年1月11日 10:15:36
	 */
	private boolean privateReadOnly;

	public boolean getReadOnly() {
		return privateReadOnly;
	}

	public void setReadOnly(boolean value) {
		privateReadOnly = value;
	}

	/**
	 * 数据库可更新列标记，值为1时表示本次需要新增或者更新
	 */
	private java.util.List<String> _updateAttributes = new java.util.ArrayList<String>();

	private java.util.HashMap<String, String> _columnDic = new java.util.HashMap<String, String>();

	public final java.util.List<String> getUpdateAttributes() {
		return _updateAttributes;
	}

	/**
	 * IDataBase的update或者insert方法成功之后执行此方法，对更新列进行复位
	 */
	public final void ClearUpdateAttribute() {
		_updateAttributes.clear();
		/**
		 *任务：
		 *描述：
		 *人员：施建龙
		 *时间：2014年12月12日上午11:31:30
		 **/
		_columnDic.clear();
	}

	/**
	 * 预留属性 标记主键是自动生成还是手工指定 编写者：施建龙 编写时间：2013年1月11日 09:58:30
	 */
	private PrimaryKeyType privatePrimaryKeyType = PrimaryKeyType.forValue(0);

	public PrimaryKeyType getPrimaryKeyType() {
		return privatePrimaryKeyType;
	}

	public void setPrimaryKeyType(PrimaryKeyType value) {
		privatePrimaryKeyType = value;
	}

	/**
	 * 设定需要新增或者更新的列
	 * 
	 * @param columnName
	 */
	public final void AddUpdateAttribute(String columnName) {
		if (!getReadOnly()) {
			if (!_columnDic.containsKey(columnName)) {
				_updateAttributes.add(columnName);
				_columnDic.put(columnName, "");
			}
		}
	}

	/**
	 * 获取到entity对应的TableName
	 */
	public final String GetTableName() {
		String typeName = this.getClass().toString();
		int index = 0;

		if (typeName.endsWith("_EXT")) {
			typeName = typeName.substring(0, typeName.length() - 4);
		}

		if ((index = typeName.lastIndexOf(".")) > 0) {
			return typeName.substring(index + 1);
		} else {
			return typeName;
		}
	}

	private List<Object> reportChildList = new ArrayList<Object>();
	
	public List<Object> getREPORTCHILDLIST(){
		return reportChildList;
	}
	
	public void setREPORTCHILDLIST(List<Object> reportChildList) {
		this.reportChildList = reportChildList;
	}
	
}