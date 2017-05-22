package APP.Comm.View;
public class ComboGrid
{
	private String _id;
	private String _text;
	private String _inputCodeUpper;
	private String _inputCodeLower;
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

	public final void setinputCodeUpper(String value)
	{
		this._inputCodeUpper = value;
	}
	public final String getinputCodeUpper()
	{
		return this._inputCodeUpper;
	}
	public final void setinputCodeLower(String value)
	{
		this._inputCodeLower = value;
	}
	public final String getinputCodeLower()
	{
		return this._inputCodeLower;
	}
}