package APP.Model.Base;
import java.util.Date;

import APP.Model.BaseEntity;
 
     /// <summary>
     /// 模块：SYS_MODULE_USE_HIS 实体类
     /// 功能：
     /// 创建者：sjl
     /// 创建时间：2013-10-18
     /// </summary>
     public  class SYS_MODULE_USE_HIS extends BaseEntity
     { 
         public SYS_MODULE_USE_HIS()
         {}

       
         private long MODULE_USE_HIS_ID;
         private long OPERATION_ID;
         private String MODULE_NAME;
         private Date CREATE_TIME;
         private String CREATOR;
         private String CREATOR_NAME;

     /** <summary>
     /// MODULE_USE_HIS_ID
     /// </summary>*/
     public void setMODULE_USE_HIS_ID(long value )
         { 
             MODULE_USE_HIS_ID = value;
             this.AddUpdateAttribute("MODULE_USE_HIS_ID");
         }
     public long getMODULE_USE_HIS_ID()
         { 
             return MODULE_USE_HIS_ID;
         }
     /** <summary>
     /// 
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
     /// 模块名称
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
     /// 创建者名称
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
    /// <summary> 
    /// 获取主键字段
    /// </summary> 
    @Override
    public  String GetPrimaryKeyName() 
    {
        return "MODULE_USE_HIS_ID";
    }
}
