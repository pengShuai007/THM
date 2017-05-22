package APP.Comm.Util;

import java.net.ConnectException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.conn.HttpHostConnectException;
import APP.Comm.Util.JsonUtil;

/**
 * 任务：KYEEAPP-896
 * 功能描述：异常信息相关的工具类<br/>  
 * 修改人：  党智康 <br/>  
 * 修改时间：2014年10月30日 下午11:16:44 <br/>  
 * 修改备注：<br/>
 * @创建人 党智康
 * @版本
 */
public class ErrInfoUtil
{
    /**
     * 描述: 获得当前异常产生的时间<br/>
     * 创建人: 党智康 <br/>
     * 任务：KYEEAPP-896
     * @return
     * @since Ver 1.1
     */
    public static String getTime()
    {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return dateFormat.format(now);
    }
    /**
     * 描述: 获取异常信息<br/>
     * 创建人: 党智康 <br/>
     * 任务：KYEEAPP-896
     * @param e
     * @return
     * @since Ver 1.1
     */
    public static String getErrInfo(Throwable e)
    {
        StackTraceElement[] elements = e.getStackTrace();
        StringBuilder errInfo = new StringBuilder();
        for (StackTraceElement element : elements)
        {
            errInfo.append(element);
        }
        return errInfo.toString();
    }
    /**
     * 描述: 获取异常信息<br/>
     * 创建人: 党智康 <br/>
     * 任务：KYEEAPP-896
     * @param e
     * @return
     * @since Ver 1.1
     */
    public static String getErrInfoFirstLine(Throwable e)
    {
        StackTraceElement[] elements = e.getStackTrace();
        return elements[0].toString();
    }
    /**
     * 描述: 将需要记录的信息封装为一个jsonMap字符串<br/>
     * 创建人: 党智康 <br/>
     * 任务：KYEEAPP-896
     * @param opName
     * @param firstLine
     * @param errInfo
     * @param level
     * @param hospitalId
     * @return
     * @since Ver 1.1
     */
    public static String doMapToJson(String opName, String firstLine, String errInfo, String level, String hospitalId)
    {
        HashMap<String, String> record = new HashMap<String, String>();
        record.put("ERR_TIME", getTime());
        record.put("ERR_OP_NAME", opName);
        record.put("ERR_FIRST_LINE", firstLine);
        record.put("ERR_LEVEL", level);
        record.put("ERR_INFO", errInfo);
        record.put("HOSPITAL_ID", hospitalId);
        return JsonUtil.objectToJsonString(record);
    }
    
    /**
     * 描述: 将需要记录的信息封装为一个map集合<br/>
     * 创建人: 党智康 <br/>
     * 任务：KYEEAPP-896
     * @param opName
     * @param firstLine
     * @param errInfo
     * @param level
     * @param hospitalId
     * @return
     * @since Ver 1.1
     */
    public static Map<String, Object> doMap(String opName, String firstLine, String errInfo, String level, String hospitalId)
    {
        HashMap<String, Object> record = new HashMap<String, Object>();
        record.put("ERR_TIME", getTime());
        record.put("ERR_OP_NAME", opName);
        record.put("ERR_FIRST_LINE", firstLine);
        record.put("ERR_LEVEL", level);
        record.put("ERR_INFO", errInfo);
        record.put("HOSPITAL_ID", hospitalId);
        return record;
    }
    
    /**
     * 
     * 描述: 判断异常的级别<br/>
     * 创建人: 党智康 <br/>
     * 创建时间：2014年11月19日 20:38:14
     * 任务：KYEEAPP-896
     * @param e
     * @return 
     * @since Ver 1.1
     */
    public static String getExcepitonLevel(Throwable e)
    {
        if (e instanceof SQLException)
        {
            return "2";
        }
        else if (e instanceof HttpHostConnectException)
        {
            return "1";
        }
        else if (e instanceof ConnectException)
        {
            return "1";
        }
        else
        {
            return "3";
        }
    }
    
}
