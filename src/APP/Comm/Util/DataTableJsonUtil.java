package APP.Comm.Util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import APP.Comm.DataBase.DotNet.DataColumn;
import APP.Comm.DataBase.DotNet.DataRow;
import APP.Comm.DataBase.DotNet.DataTable;

/**
 * comments:�����DataTable���ͽ���ת�� ����Entity���͵������ʽ����ת��
 * 
 * sjl modify 2013-10-21����5:04:31
 */
public class DataTableJsonUtil {

	public static String dataTableToJson(DataTable dt) {
		String result = null;
		List<DataRow> drs = null;
		drs = dt.getRow();
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		for (DataRow dataRow : drs) {
			builder.append("{").append(transferDataColumns(dataRow.getCol()))
					.append("},");
		}
		if (builder.length() > 1) {
			// ɾ�����һ������
			builder.deleteCharAt(builder.length() - 1);
		}
		builder.append("]");
		result = builder.toString();
		result = result.replace('\n', ' ');
//		System.out.println(result);
		return result;
	}

	protected static String transferDataColumns(List<DataColumn> dcs) {
		String result = "";
		StringBuilder builder = new StringBuilder();
		for (DataColumn dataColumn : dcs) {
			builder.append("\"").append(dataColumn.getKey()).append("\"")
					.append(":").append(objectToJson(dataColumn.getValue()))
					.append(",");
		}
		if (builder.length() > 0) {
			builder.deleteCharAt(builder.length() - 1);
		}
		result = builder.toString();
		return result;
	}

	protected static String objectToJson(Object o) {
		// SimpleDateFormat sdf=null;
		// sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return objectToJson(o, "yyyy-MM-dd HH:mm:ss");
	}

	protected static String objectToJson(Object o, String dateFormat) {
		String result = null;
		SimpleDateFormat sdf = null;
		sdf = new SimpleDateFormat(dateFormat);
		// String classType=null;
		if (o != null) {
			// classType=o.getClass().getName();
			if (o instanceof String) {
				result = "\"" + string2Json(o) + "\"";
			} else if (o instanceof Date) {
				result = "\"" + sdf.format((Date) o) + "\"";
			} else if (o instanceof BigDecimal) {
				result = ((BigDecimal) o).doubleValue() + "";
			} else if (o instanceof Long) {
				result = ((Long) o).toString();
			} else if (o instanceof Integer) {
				result = ((Integer) o).toString();
			} else if (o instanceof Float) {
				result = ((Float) o).toString();
			} else if (o instanceof Double) {
				result = ((Double) o).toString();
			} else {
				result = string2Json(String.valueOf(o));
			}
		}
		return result;
	}



/**
 * 处理特殊字符
 * @param s
 * @return
 */
private static String string2Json(Object s) {
    StringBuffer sb = new StringBuffer();        
    for (int i=0; i<s.toString().length(); i++) {  
        char c = s.toString().charAt(i);    
         switch (c){  
         case '\"':        
             sb.append("\\\"");        
             break;        
         case '\\':        
             sb.append("\\\\");        
             break;        
         case '/':        
             sb.append("\\/");        
             break;        
         case '\b':        
             sb.append("\\b");        
             break;        
         case '\f':        
             sb.append("\\f");        
             break;        
         case '\n':        
             sb.append("\\n");        
             break;        
         case '\r':        
             sb.append("\\r");        
             break;        
         case '\t':        
             sb.append("\\t");        
             break;        
         default:        
             sb.append(c);     
         }  
     }
    return sb.toString();     
 } 

}
