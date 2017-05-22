package APP.Comm.View;

public class GridRequestParameters
{
	/** 
	 page默认值
	 
	*/
	public final int DEFAULT_PAGE = 1;
	/** 
	 rows默认值
	 
	*/
	public final int DEFAULT_ROWS = 10;
	private String _order;
	private int _page;
	private int _rows;
	private String _sort;
	/** 
	 order的的范围为 desc、asc。
	 
	*/
	public final String getOrder()
	{
		return _order;
	}
	public final void setOrder(String value)
	{
		_order = value;
	}
	/** 
	 page代表分页查询要查询第几页。
	 
	*/
	public final int getPage()
	{
		return _page;
	}
	public final void setPage(int value)
	{
		_page = value;
	}

	/** 
	 rows代表分页查询每页的数据条数。
	 
	*/
	public final int getRows()
	{
		return _rows;
	}
	public final void setRows(int value)
	{
		_rows = value;
	}

	/** 
	 sort代表执行查询按照哪个字段排序。
	 
	*/
	public final String getSort()
	{
		return _sort;
	}
	public final void setSort(String value)
	{
		_sort = value;
	}
}