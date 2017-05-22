package APP.Model.Base;
import java.util.Date;

import APP.Model.BaseEntity;
 
     /// <summary>
     /// 模块：SYS_DB_LOG 实体类
     /// 功能：
     /// 创建者：sjl
     /// 创建时间：2013-10-18
     /// </summary>
     public  class SYS_DB_LOG extends BaseEntity
     { 
         public SYS_DB_LOG()
         {}

       
         private String USER_ID;
         private String LOG_TYPE;
         private String LOG_CONTENT;
         private String CREATOR_NAME;
         private Date CREATE_TIME;
         private String DEPT_NAME;
         private String MODULE_NAME;
         private String SYSTEM_NAME;

     /** <summary>
     /// USER_ID
     /// </summary>*/
     public void setUSER_ID(String value )
         { 
             USER_ID = value;
             this.AddUpdateAttribute("USER_ID");
         }
     public String getUSER_ID()
         { 
             return USER_ID;
         }
     /** <summary>
     /// 日志类型
     /// </summary>*/
     public void setLOG_TYPE(String value )
         { 
             LOG_TYPE = value;
             this.AddUpdateAttribute("LOG_TYPE");
         }
     public String getLOG_TYPE()
         { 
             return LOG_TYPE;
         }
     /** <summary>
     /// 日志内容
     /// </summary>*/
     public void setLOG_CONTENT(String value )
         { 
             LOG_CONTENT = value;
             this.AddUpdateAttribute("LOG_CONTENT");
         }
     public String getLOG_CONTENT()
         { 
             return LOG_CONTENT;
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
     /// 科室名称
     /// </summary>*/
     public void setDEPT_NAME(String value )
         { 
             DEPT_NAME = value;
             this.AddUpdateAttribute("DEPT_NAME");
         }
     public String getDEPT_NAME()
         { 
             return DEPT_NAME;
         }
     /** <summary>
     /// 模块
     /// </summary>*/
     public void setMODULE_NAME(String value )
         { 
             MODULE_NAME = value;
             this.AddUpdateAttribute("MODULE_NAME");
         }
     public String getMODULE_NAME()
         { 
             return MODULE_NAME;
         }
     /** <summary>
     /// 系统名称
     /// </summary>*/
     public void setSYSTEM_NAME(String value )
         { 
             SYSTEM_NAME = value;
             this.AddUpdateAttribute("SYSTEM_NAME");
         }
     public String getSYSTEM_NAME()
         { 
             return SYSTEM_NAME;
         }
}
