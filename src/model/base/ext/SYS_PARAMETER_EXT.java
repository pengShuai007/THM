package model.base.ext;
import model.base.SYS_PARAMETER;
 
     /// <summary>
     /// 模块：SYS_PARAMETER 实体扩展类
     /// 功能：
     /// 创建者：sjl
     /// 创建时间：2013-10-06
     /// </summary>
     @SuppressWarnings("serial")
	public  class SYS_PARAMETER_EXT extends SYS_PARAMETER{ 
    /// <summary> 
    /// 覆盖GetId方法
    /// </summary> 
         private String HIDDEN_SYS_ID = "";

         public String getHIDDEN_SYS_ID() {
             return HIDDEN_SYS_ID;
         }

         public void setHIDDEN_SYS_ID(String hIDDEN_SYS_ID) {
             HIDDEN_SYS_ID = hIDDEN_SYS_ID;
             this.AddUpdateAttribute("HIDDEN_SYS_ID");
         }
         @Override
         public  String GetId() {
             return  getSYS_PARA_ID()+"";
         }
}
