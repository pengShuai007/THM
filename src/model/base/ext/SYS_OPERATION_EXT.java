package model.base.ext;
import model.base.SYS_OPERATION;
import APP.Model.BaseEntity;
 
     /// <summary>
     /// 模块：SYS_OPERATION 实体扩展类
     /// 功能：
     /// 创建者：sjl
     /// 创建时间：2013-10-06
     /// </summary>
     public  class SYS_OPERATION_EXT extends SYS_OPERATION
     { 
    /// <summary> 
    /// 覆盖GetId方法
    /// </summary> 
    @Override
    public  String GetId() 
    {
        return  getOPERATION_ID()+"";
    }
    
    private BaseEntity[] children;
    
    private String state="closed";
    /// <summary>
    /// 菜单节点是打开还是关闭
    /// </summary>
    
    /// <summary>
    /// 代表SYS_ROLE_PERM的 HAVE_PERM， 是否赋权， 0==false, 1==true
    /// </summary>
    public int HAVE_PERM;
    /// <summary>
    ///  代表SYS_ROLE_PERM的 ROLE_PERM_ID 主键
    /// </summary>
    public int ROLE_PERM_ID ;
	public BaseEntity[] getChildren() {
		return children;
	}
	public void setChildren(BaseEntity[] children) {
		this.children = children;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getHAVE_PERM() {
		return HAVE_PERM;
	}
	public void setHAVE_PERM(int hAVE_PERM) {
		HAVE_PERM = hAVE_PERM;
	}
	public int getROLE_PERM_ID() {
		return ROLE_PERM_ID;
	}
	public void setROLE_PERM_ID(int rOLE_PERM_ID) {
		ROLE_PERM_ID = rOLE_PERM_ID;
	}

	//Edit add start HRPCOMMDEVJAVA-195 liuxingping 增加设计文档连接
	private String DESIGN_DOC_URL;
	public String getDESIGN_DOC_URL() {
		return DESIGN_DOC_URL;
	}
	public void setDESIGN_DOC_URL(String dESIGN_DOC_URL) {
		DESIGN_DOC_URL = dESIGN_DOC_URL;
	}
	//Edit add end HRPCOMMDEVJAVA-195 liuxingping 增加设计文档连接
    
    
}
