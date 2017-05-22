package APP.Comm.DataBase.DotNet;
/**
 * comments:DotNet迁移至Java辅助类
 * 
 * sjl modify 2013-11-8下午2:30:35
 */
public class DataView {
	DataTable oldDataTable=null;
	DataTable currentDataTable=null;
	
	public DataView(DataTable dataTable) {
		super();
		this.oldDataTable = dataTable;
		this.currentDataTable=dataTable;
	}

	
	public void filterRow(String columnName,Object value){
		currentDataTable=new DataTable();
//		currentDataTable=null;
		
		if(value==null){
			return;
		}
//		
		if(oldDataTable!=null){
			for (DataRow row : oldDataTable.getRow()) {
				boolean find=false;
				for (DataColumn column : row.getCol()) {
					if(column.getKey().equalsIgnoreCase(columnName)){
						if(column.getValue()==null){
							continue;
						}else{
							if(column.getValue().equals(value)){
								find=true;
							}
						}
					}
				}
				if(find){
					currentDataTable.getRows().add(row);
				}
				
			}
		}
		
	}
	
	
	public DataTable ToTable(){
		return currentDataTable;
	}
	
	
}
