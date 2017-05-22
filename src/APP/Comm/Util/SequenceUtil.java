package APP.Comm.Util;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 任务：KYEEAPPC-4674
 * 描述：创建序列生成类
 * 作者：李君强  2015年12月22日21:06:19
 */
public class SequenceUtil {
	
	private static final Log log = LogFactory.getLog(SequenceUtil.class);
	
	//时间格式化
    private final static NumberFormat dateFormat = new DecimalFormat("00000000000000");
 
    //数字格式化
    private final static NumberFormat numberFormat = new DecimalFormat("0000");
    
	//格式化时位置
    private static final FieldPosition APPEND_POSITION = new FieldPosition(0);
    
    //序列后缀
    private static int currSubfix = 0;
 
    //序列后缀最大值
    private static final int maxSeq = 9999;
    
    //序列前缀
    private static String prefix = null;
    
    private static boolean initFlg = false;
    
    private static void init(){
    	if(!initFlg){
    		//Edit start fengze KYEEAPPC-5026 版本上线部署问题：修改序列号前缀的配置方式，
    		//从sys-config中移除放在另外一个单独的文件中 2016年1月25日 10:59:49
	        String prefix = "1";//ServerSystemParams.getParamaValue("SERVER_SEQ_PREFIX");
	        //Edit end fengze KYEEAPPC-5026 版本上线部署问题：修改序列号前缀的配置方式，
    		//从sys-config中移除放在另外一个单独的文件中 2016年1月25日 10:59:49
	        setPrefix(prefix);
	        initFlg = true;
    	}
    }
    /**
     * 设置系统序列前缀
     * @param prefix
     */
    private static void setPrefix(String prefix){
    	SequenceUtil.prefix = prefix;
    }
    
    /**
     * 获取系统序列值
     * 序列前缀 + 时间戳 + [0-9999]
     * @param prefix
     */
    public static String getSeqNo(){
    	init();
    	Calendar now = Calendar.getInstance();
        StringBuffer sb = new StringBuffer();
        if(prefix != null){
        	sb.append(prefix);
        }else{
        	log.warn("在配置文件中没有设置序列前缀！");
        }
        //拼入时间戳
        dateFormat.format(now.getTimeInMillis(), sb, APPEND_POSITION);
        //拼入后缀
        numberFormat.format(getSubfix(), sb, APPEND_POSITION);
        return sb.toString();
    }
    
    /**
     * 更新序列后缀
     * @return
     */
    public static synchronized int getSubfix(){
    	if(currSubfix == maxSeq){
    		currSubfix = 0;
    	}else{
    		currSubfix++;
    	}
    	return currSubfix;
    }
}
