package APP.Comm.DataBase.DotNet;

import java.util.ArrayList;
import java.util.List;

/**
 * comments:引用自网络
 * 
 * sjl modify 2013-10-5上午10:44:29
 */
public class DataTable {

	List<DataRow> row = new ArrayList<DataRow>();

	public List<String> Columns = new ArrayList<String>();

	public DataTable() {
	}

	public DataTable(List<DataRow> r) {
		row = r;
	}

	public List<DataRow> getRow() {
		if (row == null)
			row = new ArrayList<DataRow>();
		return row;
	}

	public void setRow(List<DataRow> row) {
		this.row = row;
	}

	/**
	 * comments:方便迁移
	 * 
	 * sjl modify 2013-10-11下午1:54:19
	 */
	public List<DataRow> getRows() {
		// if(row==null){
		// return 0;
		// }else{
		// return row.size();
		// }
		return getRow();
	}

	/**
	 * comments:方便从DotNet迁移
	 * 
	 * sjl modify 2013-11-26上午8:37:33
	 */
	public DataRow[] Select(String columnName, Object value) {

		ArrayList<DataRow> results = new ArrayList<DataRow>();

		if (value == null) {
			return null;
		}
		//
		for (DataRow row : getRow()) {
			boolean find = false;
			for (DataColumn column : row.getCol()) {
				if (column.getKey().equalsIgnoreCase(columnName)) {
					if (column.getValue() == null) {
						continue;
					} else {
						if (column.getValue().equals(value)) {
							find = true;
						}
					}
				}
			}
			if (find) {
				results.add(row);
			}

		}
		return results.toArray(new DataRow[] {});
	}

	/**
	 * comments:从DotNet迁移至Java时使用
	 * 
	 * sjl modify 2014年1月3日下午2:51:23
	 */
	public DataRow NewRow() {
		List<DataColumn> columns = new ArrayList<DataColumn>();
		for (String dataColumn : Columns) {
			columns.add(new DataColumn(dataColumn, null));
		}
		DataRow row = new DataRow(columns);
		return row;
	}

	public void addRow(DataRow row) {
		this.row.add(row);
	}

	public void merge(DataTable dt) {
		this.row.addAll(dt.getRow());
	}

}
