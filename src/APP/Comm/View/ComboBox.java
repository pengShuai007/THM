package APP.Comm.View;

/** 
 页面ComboBox 对应的数据模型类
 作者：左仁帅
 时间：2013年1月7日 09:36:46
 
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
public class ComboBox
{
	private String _id;
	private String _text;
	private boolean _selected;
	/** 
	 主键id
	 
	*/
	public final void setid(String value)
	{
		this._id = value;
	}
	public final String getid()
	{
		return this._id;
	}
	/** 
	 显示的内容
	 
	*/
	public final void settext(String value)
	{
		this._text = value;
	}
	public final String gettext()
	{
		return this._text;
	}
	/** 
	 是否被选中
	 
	*/
	public final void setselected(boolean value)
	{
		this._selected = value;
	}
	public final boolean getselected()
	{
		return this._selected;
	}
}