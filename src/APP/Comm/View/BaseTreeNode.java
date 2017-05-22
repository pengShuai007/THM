package APP.Comm.View;
import java.io.Serializable;
/** 
 tree node view model
 作者：左仁帅
 编写时间：2012-12-28
 非统一规范说明：public字段全部为小写，是为了适应前端页面Tree插件。
 
*/
 /**
 *  
 * 修改人：  ypf <br/>  
 * 修改时间：2014年10月29日 11:28 <br/>  
 * 修改备注：修改文件编译时报编码错误的提示<br/>
 * 任务号：KYEEAPP-692
 * @创建人 ypf
 * @版本
 */
public class BaseTreeNode implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String _id;
	private String _text;
	private Object _attributes;
	private String _state;
	private String _iconCls;
	private boolean _checked=false;
	private String indeterminate;


	private BaseTreeNode[] _children;

	/** 
	 tree node 节点id 
	 
	*/
	public final String getid()
	{
		return _id;
	}
	public final void setid(String value)
	{
		_id = value;
	}
	/** 
	 tree node 页面显示的内容
	 
	*/
	public final String gettext()
	{
		return _text;
	}
	public final void settext(String value)
	{
		_text = value;
	}
	/** 
	 tree node 显示的状态 open|closed 
	 
	*/
	public final String getstate()
	{
		return _state;
	}
	public final void setstate(String value)
	{
		_state = value;
	}
	/** 
	 tree node 可额外携带的json数据属性
	 
	*/
	public final Object getattributes()
	{
		return _attributes;
	}
	public final void setattributes(Object value)
	{
		_attributes = value;
	}
	/** 
	 图标样式
	 
	*/
	public final String geticonCls()
	{
		return _iconCls;
	}
	public final void seticonCls(String value)
	{
		_iconCls = value;
	}
	/** 
	 是否被选择,默认值为false
	 
	*/
	public final boolean getChecked()
	{
		return _checked;
	}
	public final void setChecked(boolean value)
	{
		_checked = value;
	}
	/** 
	 是否有子节点被选中，默认为false
	 
	*/
	public final String getIndeterminate()
	{
		return indeterminate;
	}
	public final void setIndeterminate(String value)
	{
		indeterminate = value;
	}
	/** 
	 tree node 子级集合
	 
	*/
	public final BaseTreeNode[] getchildren()
	{
		return _children;
	}
	public final void setchildren(BaseTreeNode[] value)
	{
		_children = value;
	}
}