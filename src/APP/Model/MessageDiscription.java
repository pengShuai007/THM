package APP.Model;

/**
 * 功能描述：执行错误返回信息的封装类<br/>
 * 修改人： 党智康 <br/>
 * 修改时间：2014年11月3日 上午10:12:28 <br/>
 * 修改备注：<br/>
 * 任务：KYEEAPP-896
 * @创建人 党智康
 * @版本
 */
public class MessageDiscription
{
    
    private String message;
    
    private String data;
    
    private String errInfo;
    
    private String errMessage;
    
    private String errInfoFirstLine;
    
    public String getErrInfoFirstLine()
    {
        return errInfoFirstLine;
    }

    
    public void setErrInfoFirstLine(String errInfoFirstLine)
    {
        this.errInfoFirstLine = errInfoFirstLine;
    }

    public String getErrInfo()
    {
        return errInfo;
    }
    
    public void setErrInfo(String errInfo)
    {
        this.errInfo = errInfo;
    }
    
    public MessageDiscription()
    {
    }
    
    public MessageDiscription(String message, String data)
    {
        this.message = message;
        this.data = data;
    }
    
    public MessageDiscription(String message, String data, String errInfo, String errMessage, String errInfoFirstLine)
    {
        this.message = message;
        this.data = data;
        this.errInfo = errInfo;
        this.errMessage = errMessage;
        this.errInfoFirstLine = errInfoFirstLine;
    }
    
    
    public String getErrMessage()
    {
        return errMessage;
    }

    
    public void setErrMessage(String errMessage)
    {
        this.errMessage = errMessage;
    }

    public String getMessage()
    {
        return message;
    }
    
    public void setMessage(String message)
    {
        this.message = message;
    }
    
    public String getData()
    {
        return data;
    }
    
    public void setData(String data)
    {
        this.data = data;
    }
}
