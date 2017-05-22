package APP.Comm.Util;

import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.kyee.core.framework.SysParamMgr.Impl.SysParamMgrImpl;
import org.kyee.core.framework.SysParamMgr.service.ISysParamMgr;

import APP.Comm.DataBase.Helper.AbstractDataBase;
import APP.Comm.DataBase.Helper.AppDataTable;
import APP.Comm.DataBase.Helper.DBFactory;
import APP.Comm.DataBase.Helper.IDataBase;





public class Token{
    
    private static String token=null;//token
    private static String time=null;//获取token的时间
    
    Logger logger = Logger.getLogger(getClass());
    
    //该类只有一个实例
    private Token(){};
    
    private volatile static Token tokenClass;  
    //这个类必须向整个系统提供这个实例对象
    public static Token getTokenClass() {  
        if (tokenClass == null) {  
            synchronized (Token.class) {  
                if (tokenClass == null) {  
                    tokenClass = new Token();  
                }  
            }  
        }  
        return tokenClass;  
    }  

  //通过连接获取token，并赋值给实例
    public static String setToken(){
    	
    	String jsonResult="";
		try {
			 String tokenUser= "";//获取token的账户
		        String tokenPwd="";//获取token的密码
		        String interfaceAddr="";//接口地址}
			AppDataTable tokeninfo=getTokenInfo();
			if(tokeninfo.size()>0){
				tokenUser= tokeninfo.DataTable.getRows().get(0).getStringColumn("INTE_USER");//获取token的账户
				tokenPwd= tokeninfo.DataTable.getRows().get(0).getStringColumn("INTE_UPSWD");//获取token的密码
				interfaceAddr= tokeninfo.DataTable.getRows().get(0).getStringColumn("INTE_URL");//接口地址}
			}
    
		
        interfaceAddr=interfaceAddr+"/access/token/get?userName="+tokenUser+"&password="+tokenPwd;
       
        if (interfaceAddr.startsWith("https")) {
        	jsonResult = HttpClientUtil
					.origHttpsPost(
							interfaceAddr,
							"",
							 null);
		} else {
			jsonResult = HttpClientUtil.origHttpPost(
							interfaceAddr,
							"",
							 null);
		}
      
          
        HLogger.error("token获取url！"+interfaceAddr );
        HLogger.error("token返回数据！"+jsonResult );
        Map<String, Object> map=json2Map(jsonResult);
        
        if((boolean)map.get("success")==true){
            String data=map.get("data")+"";//获取返回的token数据
            Map<String, Object> tokenMap=json2Map(data);
            String token1=tokenMap.get("token").toString();
            String time1=map.get("time")+"";//获取返回的时间数据
            
            token=token1;
            time=time1;
            tokenClass.logger.info("新生成token的时间:"+time);
            return token;
        }else{
            tokenClass.logger.error("token获取失败！");
            return null;
        }
        } catch (Exception e) {
			
			  tokenClass.logger.error(jsonResult+"链接获取token出错",e);
	            return null;
		}
    }
    
    //校验当前token是否失效
    public static boolean comparedate(String date){
        SimpleDateFormat sformat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        Date preDate = null;//获取token的日期
        try{
            preDate = sformat.parse(date);
        }catch (ParseException e){
            tokenClass.logger.error("每日趋势时间转换失败!");
            return false;
        }
        Date nowDate=new Date();
        long lpreDate=preDate.getTime()+1000*60*60*7;//上次获取token时间加上7个小时的毫秒数
        long lnowDate=nowDate.getTime();//当前时间的毫秒数
        if(lpreDate<=lnowDate){//当前token使用时长已经超过七个小时,失效了
            return false;
        }else{//当前token使用时长尚未超过七个小时,未失效
            return true;
        } 
    }
    
    //校验并获取token
    public String checkToken(){
        if(time==null){
            return Token.setToken();
        }else{
            boolean flag=Token.comparedate(time);
            
            if(flag==true){
                tokenClass.logger.info("未失效token的时间:"+time);
                return token;
            }else{
                return Token.setToken();
            } 
        }
    }
    public static Map<String, Object> json2Map(String jsonStr){
        Map<String, Object> map = new HashMap<String, Object>();
        //最外层解析
        JSONObject json = JSONObject.fromObject(jsonStr);
        for(Object k : json.keySet()){
          Object v = json.get(k); 
          //如果内层还是数组的话，继续解析
          if(v instanceof JSONArray){
            List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
            Iterator<JSONObject> it = ((JSONArray)v).iterator();
            while(it.hasNext()){
              JSONObject json2 = it.next();
              list.add(json2Map(json2.toString()));
            }
            map.put(k.toString(), list);
          } else {
            map.put(k.toString(), v);
          }
        }
        return map;
      }
   
    
    public static  AppDataTable getTokenInfo(){
		IDataBase baseDb = null;
		AbstractDataBase baseDB = null;
		Connection baseDbConnection = null;
		AppDataTable  result=null;
		try {
			baseDb = DBFactory.CreateBaseDB();
			baseDB = (AbstractDataBase) baseDb;
			baseDB.OpenBase();
			baseDbConnection = baseDB.getConnection();
			baseDbConnection.setAutoCommit(false);
			String sql="SELECT * FROM m_inte_account_info WHERE  INTE_CODE='HPLUS' AND  IS_DEL='1'";
			result=baseDb.Query(sql, null);
			if (baseDbConnection!= null) {
				
				baseDbConnection.commit();
			}
			
			System.out.println("mysql: "+sql);
		
		} catch (Exception e1) {
			HLogger.error("搜索信息失败:" + e1.getMessage());
		}finally {
			try {
				if (baseDB != null) {
					
					baseDB.Close();
				}
				
			} catch(Exception e) {
				HLogger.error(e.getMessage());
			}
		}
				return result;	
	}
}
