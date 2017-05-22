package APP.Comm.copmail.htmmail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import APP.Comm.Util.DotNetToJavaStringHelper;
import APP.Comm.copmail.model.MailTable;
import APP.Comm.copmail.model.TableHeader;

public class MailFormatter {

	public static String mailFormat(MailTable mailTable){
		String htmlStr = "";
		String tabName = mailTable.getTableName();
		List<TableHeader> tableHeaders = mailTable.getTableHeaders();
		List<HashMap<String, String>> tableValues = mailTable.getTableValues();
		
		htmlStr +=" <div align=\"center\"> "
				+ " <h3>" + tabName + "</h3>"
				+ " <table border=\"1\" cellspacing=\"0\" >" ;
		
		//拼接表头
		htmlStr += " <tr>";
		for(int i=0;i < tableHeaders.size();i++){
			htmlStr +=" <td ";
			if(!DotNetToJavaStringHelper.isNullOrEmpty(tableHeaders.get(i).getBackGroudColor())){
				htmlStr += " bgcolor=\""+tableHeaders.get(i).getBackGroudColor()+ "\" ";	
			}
			htmlStr += ">" + tableHeaders.get(i).getName() + "</td>";
		}
		htmlStr += "</tr>";

		for(Map<String,String> tableValue : tableValues){
			htmlStr += "<tr>";
			for(int i=0;i < tableHeaders.size();i++){
				String key = tableHeaders.get(i).getName();
				String value = tableValue.get(key);
				htmlStr += " <td>" + value + "</td>";
			}
			htmlStr += "</tr>";
		}

		htmlStr += "</table> </div>";
		return htmlStr;
	}
}
