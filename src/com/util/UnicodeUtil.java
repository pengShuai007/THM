package com.util;

import APP.Comm.BLL.BaseBLL;
import APP.Comm.BLL.BaseBllException;
import APP.Comm.DataBase.DotNet.DataTable;
import APP.Comm.DataBase.Helper.IDataBase;

/**
 * <pre>
 * CopyRight (c) 2014-2015
 * 创建人:郭文奎
 * 日期:2014年11月5日 上午10:50:11
 * 描述:unicode编码、解码工具 KYEEAPP-974
 * </pre>
 */
public class UnicodeUtil extends BaseBLL{
	//unicode转汉字编码
	public static String decodeUnicode(final String dataStr) {                
        int start = 0;                  
        int end = 0;                
        final StringBuffer buffer = new StringBuffer();                  
        while (start > -1) {                     
            end = dataStr.indexOf("\\u", start + 2);                      
            String charStr = "";                      
            if (end == -1) {                          
                charStr = dataStr.substring(start + 2, dataStr.length());                     
                } else {                        
                    charStr = dataStr.substring(start + 2, end);                      
                    }                     
            char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串。                    
            buffer.append(new Character(letter).toString());                    
            start = end;                  
            }                  
        return buffer.toString();             
        } 
	//汉字转unicode:"中国"-->\u4e2d\u56fd
	public static String gbEncoding(final String gbString) {         
        char[] utfBytes = gbString.toCharArray();               
        String unicodeBytes = "";                
        for (int byteIndex = 0; byteIndex < utfBytes.length; byteIndex++) {  
            String hexB = Integer.toHexString(utfBytes[byteIndex]);                       
            if (hexB.length() <= 2) {                           
                hexB = "00" + hexB;                     
                }                       
            unicodeBytes = unicodeBytes + "\\u" + hexB;                   
            }          
        return unicodeBytes;       
      }  
	//转换数据库中unicode编码的数据 tableName：表名    name：数据库字段名 primaryKey:表的主键 <int>
	public  void dbUnicodeDecode(IDataBase appDB,String tableName,String name,String primaryKey) throws BaseBllException {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT "+primaryKey+","+name +" FROM "+tableName+" WHERE " +name+" like '%/u%' " );
		
		DataTable table = appDB.Query(sb.toString(), null).getDataTable();
		int rowCount=table.getRows().size();
		if(rowCount>0){
			//循环修改unicode编码
			for(int i=0;i<rowCount;i++){
				String updateName=table.getRow().get(i).getStringColumn(name);
				int key = table.getRow().get(i).getIntColumn(primaryKey);
				updateName = updateName.replace('/','\\');//转换"/"为"\"
				updateName=UnicodeUtil.decodeUnicode(updateName);//修改编码
				//修改数据库该条记录的编码值
				StringBuffer sql = new StringBuffer();
				sql.append(" UPDATE C_ACCOUNT_USERID_VS_IDNO SET "+name+"= "+updateName +" WHERE "+primaryKey+"="+key);
				int a =appDB.Update(sql.toString(), null);
				System.out.println("a:"+a);
			}
		}
	}
}
