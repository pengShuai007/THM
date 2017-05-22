package model.base;
import java.util.*;
import java.math.*;

import APP.Model.BaseEntity;
 
     /// <summary>
     /// 模块：SYS_OPERATION 实体类
     /// 功能：
     /// 创建者：sjl
     /// 创建时间：2013-10-18
     /// </summary>
     public  class SYS_OPERATION extends BaseEntity
     { 
         public SYS_OPERATION()
         {}

       
         private long OPERATION_ID;
         private long P_OPERATION_ID;
         private String OPERATION_NO;
         private long CHECK_RIGHT;
         private String OPERATION_DESC;
         private long OPERATION_TYPE;
         private String HREFLINK;
         private long MENU_DISPLAY_ORDER;
         private String SEARCHKEY;
         private String OPERATION_NAME;
         private String SUB_SYSTEM;
         private String CREATOR;
         private Date CREATE_TIME;
         private String CREATOR_NAME;
         private String UPDATER;
         private Date UPDATE_TIME;
         private String UPDATER_NAME;
         private long OPERATION_RIGHT;
         private String ENABLE_RESOURCE_URL;
         private String DISABLE_RESOURCE_URL;
         private String L_ENABLE_RESOURCE_URL;
         private String L_DISABLE_RESOURCE_URL;
         private long MENU_TYPE;
         private String HELP_URL;
         private String CHECK_OPERTIME;
 		 private String OPERATION_LEVEL;
         private String VERSION_NO;
         
         public String getOPERATION_LEVEL() {
			return OPERATION_LEVEL;
		}
		public void setOPERATION_LEVEL(String oPERATION_LEVEL) {
			OPERATION_LEVEL = oPERATION_LEVEL;
			 this.AddUpdateAttribute("OPERATION_LEVEL");
		}
		public String getVERSION_NO() {
			return VERSION_NO;
		}
		public void setVERSION_NO(String vERSION_NO) {
			VERSION_NO = vERSION_NO;
			this.AddUpdateAttribute("VERSION_NO");
		}

     /** <summary>
     /// 操作ID
     /// </summary>*/
     public void setOPERATION_ID(long value )
         { 
             OPERATION_ID = value;
             this.AddUpdateAttribute("OPERATION_ID");
         }
     public long getOPERATION_ID()
         { 
             return OPERATION_ID;
         }
     /** <summary>
     /// 父操作ID
     /// </summary>*/
     public void setP_OPERATION_ID(long value )
         { 
             P_OPERATION_ID = value;
             this.AddUpdateAttribute("P_OPERATION_ID");
         }
     public long getP_OPERATION_ID()
         { 
             return P_OPERATION_ID;
         }
     /** <summary>
     /// 操作编号
     /// </summary>*/
     public void setOPERATION_NO(String value )
         { 
             OPERATION_NO = value;
             this.AddUpdateAttribute("OPERATION_NO");
         }
     public String getOPERATION_NO()
         { 
             return OPERATION_NO;
         }
     /** <summary>
     /// 是否校验
     /// </summary>*/
     public void setCHECK_RIGHT(long value )
         { 
             CHECK_RIGHT = value;
             this.AddUpdateAttribute("CHECK_RIGHT");
         }
     public long getCHECK_RIGHT()
         { 
             return CHECK_RIGHT;
         }
     /** <summary>
     /// 操作描述
     /// </summary>*/
     public void setOPERATION_DESC(String value )
         { 
             OPERATION_DESC = value;
             this.AddUpdateAttribute("OPERATION_DESC");
         }
     public String getOPERATION_DESC()
         { 
             return OPERATION_DESC;
         }
     /** <summary>
     /// 操作类型
     /// </summary>*/
     public void setOPERATION_TYPE(long value )
         { 
             OPERATION_TYPE = value;
             this.AddUpdateAttribute("OPERATION_TYPE");
         }
     public long getOPERATION_TYPE()
         { 
             return OPERATION_TYPE;
         }
     /** <summary>
     /// 操作链接
     /// </summary>*/
     public void setHREFLINK(String value )
         { 
             HREFLINK = value;
             this.AddUpdateAttribute("HREFLINK");
         }
     public String getHREFLINK()
         { 
             return HREFLINK;
         }
     /** <summary>
     /// 菜单显示顺序
     /// </summary>*/
     public void setMENU_DISPLAY_ORDER(long value )
         { 
             MENU_DISPLAY_ORDER = value;
             this.AddUpdateAttribute("MENU_DISPLAY_ORDER");
         }
     public long getMENU_DISPLAY_ORDER()
         { 
             return MENU_DISPLAY_ORDER;
         }
     /** <summary>
     /// SEARCHKEY
     /// </summary>*/
     public void setSEARCHKEY(String value )
         { 
             SEARCHKEY = value;
             this.AddUpdateAttribute("SEARCHKEY");
         }
     public String getSEARCHKEY()
         { 
             return SEARCHKEY;
         }
     /** <summary>
     /// OPERATION_NAME
     /// </summary>*/
     public void setOPERATION_NAME(String value )
         { 
             OPERATION_NAME = value;
             this.AddUpdateAttribute("OPERATION_NAME");
         }
     public String getOPERATION_NAME()
         { 
             return OPERATION_NAME;
         }
     /** <summary>
     /// 所属系统
     /// </summary>*/
     public void setSUB_SYSTEM(String value )
         { 
             SUB_SYSTEM = value;
             this.AddUpdateAttribute("SUB_SYSTEM");
         }
     public String getSUB_SYSTEM()
         { 
             return SUB_SYSTEM;
         }
     /** <summary>
     /// 创建者
     /// </summary>*/
     public void setCREATOR(String value )
         { 
             CREATOR = value;
             this.AddUpdateAttribute("CREATOR");
         }
     public String getCREATOR()
         { 
             return CREATOR;
         }
     /** <summary>
     /// 创建时间
     /// </summary>*/
     public void setCREATE_TIME(Date value )
         { 
             CREATE_TIME = value;
             this.AddUpdateAttribute("CREATE_TIME");
         }
     public Date getCREATE_TIME()
         { 
             return CREATE_TIME;
         }
     /** <summary>
     /// 创建者姓名
     /// </summary>*/
     public void setCREATOR_NAME(String value )
         { 
             CREATOR_NAME = value;
             this.AddUpdateAttribute("CREATOR_NAME");
         }
     public String getCREATOR_NAME()
         { 
             return CREATOR_NAME;
         }
     /** <summary>
     /// 更新者
     /// </summary>*/
     public void setUPDATER(String value )
         { 
             UPDATER = value;
             this.AddUpdateAttribute("UPDATER");
         }
     public String getUPDATER()
         { 
             return UPDATER;
         }
     /** <summary>
     /// 更新时间
     /// </summary>*/
     public void setUPDATE_TIME(Date value )
         { 
             UPDATE_TIME = value;
             this.AddUpdateAttribute("UPDATE_TIME");
         }
     public Date getUPDATE_TIME()
         { 
             return UPDATE_TIME;
         }
     /** <summary>
     /// 更新者姓名
     /// </summary>*/
     public void setUPDATER_NAME(String value )
         { 
             UPDATER_NAME = value;
             this.AddUpdateAttribute("UPDATER_NAME");
         }
     public String getUPDATER_NAME()
         { 
             return UPDATER_NAME;
         }
     /** <summary>
     /// OPERATION_RIGHT
     /// </summary>*/
     public void setOPERATION_RIGHT(long value )
         { 
             OPERATION_RIGHT = value;
             this.AddUpdateAttribute("OPERATION_RIGHT");
         }
     public long getOPERATION_RIGHT()
         { 
             return OPERATION_RIGHT;
         }
     /** <summary>
     /// 页面元素资源文件位置
     /// </summary>*/
     public void setENABLE_RESOURCE_URL(String value )
         { 
             ENABLE_RESOURCE_URL = value;
             this.AddUpdateAttribute("ENABLE_RESOURCE_URL");
         }
     public String getENABLE_RESOURCE_URL()
         { 
             return ENABLE_RESOURCE_URL;
         }
     /** <summary>
     /// 
     /// </summary>*/
     public void setDISABLE_RESOURCE_URL(String value )
         { 
             DISABLE_RESOURCE_URL = value;
             this.AddUpdateAttribute("DISABLE_RESOURCE_URL");
         }
     public String getDISABLE_RESOURCE_URL()
         { 
             return DISABLE_RESOURCE_URL;
         }
     /** <summary>
     /// 
     /// </summary>*/
     public void setL_ENABLE_RESOURCE_URL(String value )
         { 
             L_ENABLE_RESOURCE_URL = value;
             this.AddUpdateAttribute("L_ENABLE_RESOURCE_URL");
         }
     public String getL_ENABLE_RESOURCE_URL()
         { 
             return L_ENABLE_RESOURCE_URL;
         }
     /** <summary>
     /// 
     /// </summary>*/
     public void setL_DISABLE_RESOURCE_URL(String value )
         { 
             L_DISABLE_RESOURCE_URL = value;
             this.AddUpdateAttribute("L_DISABLE_RESOURCE_URL");
         }
     public String getL_DISABLE_RESOURCE_URL()
         { 
             return L_DISABLE_RESOURCE_URL;
         }
     /** <summary>
     /// 
     /// </summary>*/
     public void setMENU_TYPE(long value )
         { 
             MENU_TYPE = value;
             this.AddUpdateAttribute("MENU_TYPE");
         }
     public long getMENU_TYPE()
         { 
             return MENU_TYPE;
         }
     /** <summary>
     /// 
     /// </summary>*/
     public void setHELP_URL(String value )
         { 
             HELP_URL = value;
             this.AddUpdateAttribute("HELP_URL");
         }
     public String getHELP_URL()
         { 
             return HELP_URL;
         }
     /** <summary>
     /// 
     /// </summary>*/
     public void setCHECK_OPERTIME(String value )
         { 
             CHECK_OPERTIME = value;
             this.AddUpdateAttribute("CHECK_OPERTIME");
         }
     public String getCHECK_OPERTIME()
         { 
             return CHECK_OPERTIME;
         }
    /// <summary> 
    /// 获取主键字段
    /// </summary> 
    @Override
    public  String GetPrimaryKeyName() 
    {
        return "OPERATION_ID";
    }
}
