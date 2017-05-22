package APP.Comm.copmail.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 注意：tableHeaders跟tableValues中Map中的值数量一样
 * 即表头跟列子段保持一致
 * @author Administrator
 *
 */
public class MailTable {

	private String tableName;
	
	private List<TableHeader> tableHeaders = new ArrayList<TableHeader>();

	/**
	 * 注意：Map里面的key值要跟表头中的name对应
	 */
	private List<HashMap<String, String>> tableValues = new ArrayList<HashMap<String,String>>();

	public MailTable(){
		
	}
	public MailTable(String tableName, List<TableHeader> tableHeaders,
			List<HashMap<String, String>> tableValues) {
		super();
		this.tableName = tableName;
		this.tableHeaders = tableHeaders;
		this.tableValues = tableValues;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<TableHeader> getTableHeaders() {
		return tableHeaders;
	}

	public void setTableHeaders(List<TableHeader> tableHeaders) {
		this.tableHeaders = tableHeaders;
	}

	public List<HashMap<String, String>> getTableValues() {
		return tableValues;
	}

	public void setTableValues(List<HashMap<String, String>> tableValues) {
		this.tableValues = tableValues;
	}

	
}
