package APP.Model.Base;
import java.math.BigDecimal;
import java.util.Date;

import APP.Model.BaseEntity;
 
     /// <summary>
     /// 模块：DR_DS_CONFIG 实体类
     /// 功能：
     /// 创建者：sjl
     /// 创建时间：2013-11-03
     /// </summary>
     public  class DR_DS_CONFIG extends BaseEntity
     { 
         public DR_DS_CONFIG()
         {}

       
         private Long DS_CONFIG_ID;
         private String SYSTEM_CODE;
         private String SYSTEM_NAME;
         private String CONNECCT_STRING;
         private String SERVER_ADDRESS;
         private String CONNECT_USER;
         private String COMMECT_PASSWORD;
         private String DATABASE_NAME;
         private String DATABASE_TYPE;
         private String COMMENTS;
         private String CREATOR;
         private Date CREATE_TIME;
         private String UPDATER;
         private Date UPDATE_TIME;
         private String CREATOR_NAME;
         private String UPDATER_NAME;
         private String DEPT_TABLE;
         private String DEPT_CODE_COLUMN;
         private String DEPT_NAME_COLUMN;
         private String DEPT_SEPARATOR;
         private String PORT;

     /** <summary>
     /// DS_CONFIG_ID
     /// </summary>*/
     public void setDS_CONFIG_ID(Long value )
         { 
             DS_CONFIG_ID = value;
             this.AddUpdateAttribute("DS_CONFIG_ID");
         }
     public Long getDS_CONFIG_ID()
         { 
             return DS_CONFIG_ID;
         }
     /** <summary>
     /// DS_CONFIG_ID
     /// </summary>*/
     public void setDS_CONFIG_ID(BigDecimal value )
         { 
         if(value==null){
             DS_CONFIG_ID = null;
         }
         else{
             DS_CONFIG_ID = value.longValue();
         }
             this.AddUpdateAttribute("DS_CONFIG_ID");
         }
     /** <summary>
     /// 系统代码
     /// </summary>*/
     public void setSYSTEM_CODE(String value )
         { 
             SYSTEM_CODE = value;
             this.AddUpdateAttribute("SYSTEM_CODE");
         }
     public String getSYSTEM_CODE()
         { 
             return SYSTEM_CODE;
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
     /** <summary>
     /// 连接参数
     /// </summary>*/
     public void setCONNECCT_STRING(String value )
         { 
             CONNECCT_STRING = value;
             this.AddUpdateAttribute("CONNECCT_STRING");
         }
     public String getCONNECCT_STRING()
         { 
             return CONNECCT_STRING;
         }
     /** <summary>
     /// 服务器地址
     /// </summary>*/
     public void setSERVER_ADDRESS(String value )
         { 
             SERVER_ADDRESS = value;
             this.AddUpdateAttribute("SERVER_ADDRESS");
         }
     public String getSERVER_ADDRESS()
         { 
             return SERVER_ADDRESS;
         }
     /** <summary>
     /// 连接用户
     /// </summary>*/
     public void setCONNECT_USER(String value )
         { 
             CONNECT_USER = value;
             this.AddUpdateAttribute("CONNECT_USER");
         }
     public String getCONNECT_USER()
         { 
             return CONNECT_USER;
         }
     /** <summary>
     /// 连接密码
     /// </summary>*/
     public void setCOMMECT_PASSWORD(String value )
         { 
             COMMECT_PASSWORD = value;
             this.AddUpdateAttribute("COMMECT_PASSWORD");
         }
     public String getCOMMECT_PASSWORD()
         { 
             return COMMECT_PASSWORD;
         }
     /** <summary>
     /// 数据库名称
     /// </summary>*/
     public void setDATABASE_NAME(String value )
         { 
             DATABASE_NAME = value;
             this.AddUpdateAttribute("DATABASE_NAME");
         }
     public String getDATABASE_NAME()
         { 
             return DATABASE_NAME;
         }
     /** <summary>
     /// 数据库类型
     /// </summary>*/
     public void setDATABASE_TYPE(String value )
         { 
             DATABASE_TYPE = value;
             this.AddUpdateAttribute("DATABASE_TYPE");
         }
     public String getDATABASE_TYPE()
         { 
             return DATABASE_TYPE;
         }
     /** <summary>
     /// 备注
     /// </summary>*/
     public void setCOMMENTS(String value )
         { 
             COMMENTS = value;
             this.AddUpdateAttribute("COMMENTS");
         }
     public String getCOMMENTS()
         { 
             return COMMENTS;
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
     /// 科室表名称
     /// </summary>*/
     public void setDEPT_TABLE(String value )
         { 
             DEPT_TABLE = value;
             this.AddUpdateAttribute("DEPT_TABLE");
         }
     public String getDEPT_TABLE()
         { 
             return DEPT_TABLE;
         }
     /** <summary>
     /// 科室代码字段
     /// </summary>*/
     public void setDEPT_CODE_COLUMN(String value )
         { 
             DEPT_CODE_COLUMN = value;
             this.AddUpdateAttribute("DEPT_CODE_COLUMN");
         }
     public String getDEPT_CODE_COLUMN()
         { 
             return DEPT_CODE_COLUMN;
         }
     /** <summary>
     /// 科室名称字段
     /// </summary>*/
     public void setDEPT_NAME_COLUMN(String value )
         { 
             DEPT_NAME_COLUMN = value;
             this.AddUpdateAttribute("DEPT_NAME_COLUMN");
         }
     public String getDEPT_NAME_COLUMN()
         { 
             return DEPT_NAME_COLUMN;
         }
     /** <summary>
     /// 科室上下级分隔符
     /// </summary>*/
     public void setDEPT_SEPARATOR(String value )
         { 
             DEPT_SEPARATOR = value;
             this.AddUpdateAttribute("DEPT_SEPARATOR");
         }
     public String getDEPT_SEPARATOR()
         { 
             return DEPT_SEPARATOR;
         }
     /** <summary>
     /// PORT
     /// </summary>*/
     public void setPORT(String value )
         { 
             PORT = value;
             this.AddUpdateAttribute("PORT");
         }
     public String getPORT()
         { 
             return PORT;
         }
    /// <summary> 
    /// 获取主键字段
    /// </summary> 
    @Override
    public  String GetPrimaryKeyName() 
    {
        return "DS_CONFIG_ID";
    }
}
