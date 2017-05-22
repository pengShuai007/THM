/**
 * 
 */
package com.util;

import java.util.ArrayList;
import java.util.List;

import model.base.ext.C_DEPT_DICT_EXT;
import net.hydromatic.linq4j.Linq4j;
import APP.Comm.BLL.BaseBllException;
import APP.Comm.DataBase.Helper.AppDataTable;
import APP.Comm.DataBase.Helper.IDataBase;
import APP.Comm.DataBase.Parameter.AppDbParameter;
import APP.Comm.Util.DotNetToJavaStringHelper;
import APP.Comm.Util.HLogger;
import APP.Comm.View.BaseTreeNode;
import APP.Model.BaseEntity;

/**
 * <pre>
	*
	* 任务:
	* 描述:省、市、医院构造树方法
	* 作者:罗代华
	* 日期:2015年3月3日13:49:02
	* 修改：张思源
	* 日期：2016年2月4日下午1:17:13
	* 修改原因：在医院树的最外层增加平台节点，同时扩展医院数的功能，在医院节点下增加子节点
	* 日期：2016年2月14日下午5:17:13
	* 修改原因：将静态常量优化为枚举类型，消除魔鬼数字
	
	*</pre>
 */
public class HosTreeTool {
	public enum END_NODE_TYPE{
		PLATFORM_AREA_HOSPITAT,//终节点是医院
		PLATFORM_AREA_HOSPITAT_DEPT,//终节点是科室
		PLATFORM_AREA_HOSPITAT_FLOOR_DEPT,//终节点是楼层科室
		PLATFORM_AREA_HOSPITAL_MULTIPLE_HEALTH;//终节点是多院区科室	
		public boolean isHospital(){
			//终节点是医院
			return this.equals(PLATFORM_AREA_HOSPITAT);
		}
		public boolean isDepartment(){
			//终节点是科室
			return this.equals(PLATFORM_AREA_HOSPITAT_DEPT);
		}
		public boolean isFloor(){
			//终节点是楼层
			return this.equals(PLATFORM_AREA_HOSPITAT_FLOOR_DEPT);
		}
		public boolean isMultipleHealth(){
			//终节点是多院区科室
			return this.equals(PLATFORM_AREA_HOSPITAL_MULTIPLE_HEALTH);
		}
	}	
	/**
	 * 
	* <pre>
	* 任务： APPCOMMERCIALBUG-2126
	* 描述：查询医院信息 
	* 作者：zhangsiyuan
	* 时间：2016年2月4日下午1:16:55
	* @param appDb
	* @return
	* @throws BaseBllException
	* returnType：List<BaseTreeNode>
	* </pre>
	*/
	public static List<BaseTreeNode> queryHos(IDataBase appDb) throws BaseBllException {
		
		return queryHos(appDb, END_NODE_TYPE.PLATFORM_AREA_HOSPITAT);
	}

	/**
	 * 
	 * <pre>
	 * CopyRight(c) 2014-2015
	 * 创建人：刘健
	 * 日期：2014年6月8日下午4:43:17
	 * 描述：查询医院信息
	 * 任务：APPCOMMERCIALBUG-2126   
	 * 修改：zhangsiyuan
	 * 时间：2016年2月4日下午1:06:39
	 * &#64;param appDb
	 * &#64;param lastNodeType
	 * &#64;return
	 * &#64;throws BaseBllException
	 * returnType：List<BaseTreeNode>
	 * </pre>
	 */

	public static List<BaseTreeNode> queryHos(IDataBase appDb,END_NODE_TYPE lastNodeType) throws BaseBllException {
		HLogger.info("HosTreeTool function queryHos start!");
		long count = 0;
		//edit begin  任务号:APPCOMMERCIALBUG-2126  任务描述:增加医院平台节点  修改人：zhangsiyuan  时间：2016年2月2日下午4:16:17
		List<BaseTreeNode> nodes = new ArrayList<BaseTreeNode>();
		AppDataTable appDataTable = queryproAndHos(appDb);
		count = appDataTable.getCount();
		//取出平台节点，根节点
		String hospitalPlatformIdTemp = "";
		String hospitalPlatformId = "";
		String hospitalPlatformName = "";
		for(int i = 0; i < count; i++)
		{
			hospitalPlatformId = appDataTable.DataTable.getRow().get(i).getColumn("PLATFORM")+"";//获取医院平台ID
			hospitalPlatformName = appDataTable.DataTable.getRow().get(i).getColumn("PLATFORM_NAME")+"";//获取医院平台名成
			if(!hospitalPlatformIdTemp.equals(hospitalPlatformId)){
				BaseTreeNode node = new BaseTreeNode();
				node.setid(hospitalPlatformId);//设置节点ID
				node.settext(hospitalPlatformName);//设置节点显示名
				List<BaseTreeNode> subNode = getProvinceChildrenTreeNode(hospitalPlatformId,appDataTable,i,lastNodeType);//子节点
			    node.setchildren((BaseTreeNode[]) subNode.toArray(new BaseTreeNode[0]));
			    node.setstate("closed");//子节点关闭
			    node.setattributes("00");
			    nodes.add(node);//添加节点
			    hospitalPlatformIdTemp = hospitalPlatformId;
			}
		}
		//edit end  任务号:APPCOMMERCIALBUG-2126  任务描述:增加医院平台节点  修改人：zhangsiyuan  时间：2016年2月2日下午4:16:24
		/*String provinceIdtemp = "";
		String provinceId = "";
		String provinceName = "";
		List<BaseTreeNode> nodes = new ArrayList<BaseTreeNode>();
		AppDataTable appDataTable = queryproAndHos(appDb);
		count = appDataTable.getCount();
		for(int i = 0;i<count;i++)
		{
			provinceId = appDataTable.DataTable.getRow().get(i).getColumn("PROVINCE_ID")+"";
			provinceName  = appDataTable.DataTable.getRow().get(i).getColumn("PROVINCE_NAME")+"";
			if(!provinceIdtemp.equals(provinceId))
			{
				BaseTreeNode node = new BaseTreeNode();
				node.setid(provinceId);
				node.settext(provinceName);
				List<BaseTreeNode> ns = getCityChildrenTreeNode(provinceId,appDataTable,i);
				node.setchildren((BaseTreeNode[]) ns
						.toArray(new BaseTreeNode[0]));
				node.setstate("closed");
				nodes.add(node);
				provinceIdtemp = provinceId;
			}
		}
		*/
		HLogger.info("HosTreeTool function queryHos end!");
		return nodes;
	}
	/**
	 * 
	* <pre>
	* 任务:KYEEAPPTEST-1143
	* 描述:优化树形组件
	* 作者:冯泽
	* 日期:2015年2月12日 下午7:46:05
	* @param provinceId
	* @param appDataTable
	* @param start
	* @return
	* @throws BaseBllException
	* </pre>
	 */
	private static AppDataTable queryproAndHos(IDataBase appDb) throws BaseBllException {
		HLogger.info("HosTreeTool function queryproAndHos start!");
		AppDataTable appDataTable = null;
		String sql = " SELECT "
				+"\r\n"
				//edit begin  任务号APPCOMMERCIALBUG-2126:任务描述：新增医院平台节点  修改人：zhangsiyuan  时间：2016年2月2日下午4:53:20
				+ " CSH.PLATFORM, "
				+"\r\n"
				+ " CSCI.ITEM_NAME PLATFORM_NAME, "
				+"\r\n" 
				//edit end  任务号APPCOMMERCIALBUG-2126:任务描述：新增医院平台节点  修改人：zhangsiyuan  时间：2016年2月2日下午4:53:40		
				+ " CSP.PROVINCE_ID,"
				+"\r\n"
				+ " CSP.PROVINCE_NAME, "
				+"\r\n"
				+" CSC.CITY_ID, "
				+ "\r\n"
				+ " CSC.CITY_NAME, "
				+"\r\n"
				+ " CSH.HOSPITAL_ID, "
				+"\r\n"
				+ " CSH.HOSPITAL_NAME "
				+"\r\n"
				+ " FROM "
				+"\r\n"
				+ " C_SYSTEM_HOSPITAL CSH, "
				+"\r\n"
				+ " C_SYSTEM_PROVINCE CSP, "
				+"\r\n"
				+ " C_SYSTEM_CITY CSC,  "
				+"\r\n"
				//edit begin  任务号APPCOMMERCIALBUG-2126:任务描述：新增医院平台节点  修改人：zhangsiyuan  时间：2016年2月2日下午4:53:20
				+ "C_SYS_CODE_ITEM CSCI "
				+"\r\n"
				//edit end  任务号APPCOMMERCIALBUG-2126:任务描述：新增医院平台节点  修改人：zhangsiyuan  时间：2016年2月2日下午4:53:20
				+ " WHERE CSP.PROVINCE_FLG = '1'  "
				+"\r\n"
				+ " AND (CSH.STATUS = '1'  "
				+"\r\n"
				+ " OR CSH.STATUS = '2')  "
				+"\r\n"
				+ " AND CSH.CITY_ID = CSC.CITY_ID  "
				+"\r\n"
				+ " AND CSC.PROVINCE_ID = CSP.PROVINCE_ID  "
				+"\r\n"
				//edit begin  任务号APPCOMMERCIALBUG-2126:任务描述：新增医院平台节点   修改人：zhangsiyuan  时间：2016年2月2日下午4:53:20
				+ " AND CSCI.ITEM_VALUE = CSH.PLATFORM  "
				+"\r\n"
				+ " AND CSCI.DICT_ID = '6501'  "
				+"\r\n"
				//edit end  任务号APPCOMMERCIALBUG-2126:任务描述：新增医院平台节点   修改人：zhangsiyuan  时间：2016年2月2日下午4:53:20
				+ " ORDER BY CSH.PLATFORM,CSP.PROVINCE_ID,CSC.CITY_ID,CSH.HOSPITAL_ID "
				+"\r\n"
				+ "      "; 
		appDataTable = appDb.Query(sql, null);
		HLogger.info("HosTreeTool function queryproAndHos start!");
		return appDataTable;
	}
	/**
	 * 
	* <pre>
	* 任务:KYEEAPPTEST-1143
	* 描述:优化树形组件
	* 作者:冯泽
	* 日期:2015年2月12日 下午7:46:05
	* 新增医院平台参数  zhangsiyuan
	* @param hospitalPlatformId 
	* @param provinceId
	* @param appDataTable
	* @param start
	* @return
	* @throws BaseBllException
	* </pre>
	 */
	private static List<BaseTreeNode> getCityChildrenTreeNode(String hospitalPlatformId,String provinceId,
			AppDataTable appDataTable,int start,END_NODE_TYPE lastNodeType) throws BaseBllException
	{
		HLogger.info("HosTreeTool function getCityChildrenTreeNode start!");
		List<BaseTreeNode> nodes = new ArrayList<BaseTreeNode>();
		long count = appDataTable.getCount();
		String cityId = "";
		String cityName = "";
		String cityIdTemp = "";
		String provinceIdTemp="";
		String hospitalPlatformIdTemp = "";//当前正在添加的节点的医院平台ID
		for(int i = start;i<count;i++)
		{
			hospitalPlatformIdTemp = appDataTable.DataTable.getRow().get(i).getColumn("PLATFORM")+"";
			provinceIdTemp = appDataTable.DataTable.getRow().get(i).getColumn("PROVINCE_ID")+"";
			cityId = appDataTable.DataTable.getRow().get(i).getColumn("CITY_ID")+"";
			cityName = appDataTable.DataTable.getRow().get(i).getColumn("CITY_NAME")+"";
			if(hospitalPlatformIdTemp.equals(hospitalPlatformId) && provinceId.equals(provinceIdTemp) && !cityId.equals(cityIdTemp))
			{
				BaseTreeNode node = new BaseTreeNode();
				node.setid(cityId);
				node.settext(cityName);
				List<BaseTreeNode> ns = getHospitalChildrenTreeNode(hospitalPlatformId,cityId,appDataTable,i,lastNodeType);
				node.setchildren((BaseTreeNode[]) ns
						.toArray(new BaseTreeNode[0]));
				node.setstate("closed");
				node.setattributes("02");
				nodes.add(node);
				cityIdTemp = cityId;
			}	
		}
		HLogger.info("HosTreeTool function getCityChildrenTreeNode end!");
		return nodes;
	}
	/**
	 * 
	* <pre>
	* 任务:KYEEAPPTEST-1143
	* 描述:优化树形组件
	* 作者:冯泽
	* 日期:2015年2月12日 下午7:46:05
	* 新增医院平台参数  zhangsiyuan
	* @param hospitalPlatformId 
	* @param provinceId
	* @param appDataTable
	* @param start
	* @return
	* @throws BaseBllException
	* </pre>
	 */
	private static List<BaseTreeNode> getHospitalChildrenTreeNode(String hospitalPlatformId,String cityId,AppDataTable appDataTable,
			int start,END_NODE_TYPE lastNodeType) throws BaseBllException
	{
		HLogger.info("HosTreeTool function getHospitalChildrenTreeNode start!");
		List<BaseTreeNode> nodes = new ArrayList<BaseTreeNode>();
		long count = appDataTable.getCount();
		String cityIdTemp = "";
		String hospitalId = "";
		String hospitalName = "";
		String hospitalPlatformIdTemp = "";//当前正在添加的节点的医院平台ID
		for(int i = start;i<count;i++)
		{
			hospitalPlatformIdTemp = appDataTable.DataTable.getRow().get(i).getColumn("PLATFORM")+"";
			cityIdTemp = appDataTable.DataTable.getRow().get(i).getColumn("CITY_ID")+"";
			if(hospitalPlatformIdTemp.equals(hospitalPlatformId) && cityId.equals(cityIdTemp))
			{
				hospitalId = appDataTable.DataTable.getRow().get(i).getColumn("HOSPITAL_ID")+"";
				hospitalName = appDataTable.DataTable.getRow().get(i).getColumn("HOSPITAL_NAME")+"";
				BaseTreeNode node = new BaseTreeNode();
				node.setid(hospitalId);
				node.settext(hospitalName);
				//edit begin  任务号:APPCOMMERCIALBUG-2126 任务描述：在医院树形结构最外层添加医院平台节点  修改人：zhangsiyuan  时间：2016年2月14日下午5:11:19
				//优化为为枚举类型判断终节点类型
				if(lastNodeType.isHospital()){
					//终节点是医院
    				node.setstate("open"); 
				}else{
					//在医院下层还有子节点
					node.setstate("closed");
				}
				//edit begin  任务号:APPCOMMERCIALBUG-2126 任务描述：在医院树形结构最外层添加医院平台节点  修改人：zhangsiyuan  时间：2016年2月14日下午5:11:19
                node.setattributes("03");
                nodes.add(node);
			}	
		}
		HLogger.info("HosTreeTool function getHospitalChildrenTreeNode end!");
		return nodes;
	}
	/**
	* <pre>
	* 任务：APPCOMMERCIALBUG-2126 
	* 描述： 在医院树形结构最外层添加医院平台节点
	* 作者：zhangsiyuan
	* 时间：2016年2月2日下午2:34:49
	* @param hospitalPlatformId
	* @param appDataTable
	* @param start
	* @param appDb
	* @param lastNodeType 
	* @return
	* @throws BaseBllException
	* returnType：List<BaseTreeNode>
	* </pre>
	*/
	private static List<BaseTreeNode> getProvinceChildrenTreeNode(String hospitalPlatformId,AppDataTable appDataTable,
			int start,END_NODE_TYPE lastNodeType) throws BaseBllException
	{
		HLogger.info("HosTreeTool function getProvinceChildrenTreeNode start!");
		List<BaseTreeNode> nodes = new ArrayList<BaseTreeNode>();
		long count = appDataTable.getCount();//数据行总数
		String provinceId = "";//当前每个节点的省ID
		String provinceIdTemp = "";//当前正在添加的省节点ID
		String provinceName = "";//当前每个节点的省名称
		String hospitalPlatformIdTemp = "";//当前正在添加的节点的医院平台ID
		for(int i = start;i<count;i++)
		{
			hospitalPlatformIdTemp = appDataTable.DataTable.getRow().get(i).getColumn("PLATFORM")+"";//当前数据的医院平台ID
			provinceId = appDataTable.DataTable.getRow().get(i).getColumn("PROVINCE_ID")+"";
			provinceName = appDataTable.DataTable.getRow().get(i).getColumn("PROVINCE_NAME")+"";
			if(hospitalPlatformId.equals(hospitalPlatformIdTemp) && !provinceId.equals(provinceIdTemp))
			{
				BaseTreeNode node = new BaseTreeNode();
				node.setid(provinceId);//设置节点ID
				node.settext(provinceName);//设置节点名称
				//由于一个省市有可能对应多个平台，因此下个子节点应该判断医院平台是否一致
				List<BaseTreeNode> subNode = getCityChildrenTreeNode(hospitalPlatformId,provinceId,appDataTable,i,lastNodeType);
				node.setchildren((BaseTreeNode[]) subNode
						.toArray(new BaseTreeNode[0]));//设置子节点
				node.setstate("closed");//节点闭合
				node.setattributes("01");
				nodes.add(node);//添加当前结点
				provinceIdTemp = provinceId;//保存当前已经添加的省份ID，防止重复添加
			}	
		}
		HLogger.info("HosTreeTool function getProvinceChildrenTreeNode start!");
		return nodes;		
	}
	/**
	* <pre>
	* 任务： APPCOMMERCIALBUG-2126 
	* 描述： 添加科室节点
	* 作者：zhangsiyuan
	* 时间：2016年2月4日上午11:31:06
	* @param id
	* @return
	* @throws BaseBllException
	* returnType：List<BaseTreeNode>
	* </pre>
	*/
	public static List<BaseTreeNode> getDepartmentChildrenTreeNode(int hospitalId,IDataBase appDb) throws BaseBllException {
		HLogger.info("HosTreeTool function getDepartmentChildrenTreeNode start!");
		List<BaseEntity> deptlist = queryDeptInfobyHospitalId(appDb, hospitalId);
		List<BaseTreeNode> nodes = new ArrayList<BaseTreeNode>();
		List<BaseEntity> ens = Linq4j.asEnumerable(deptlist).toList();
		for (BaseEntity be : ens) {
			BaseTreeNode node = new BaseTreeNode();
			C_DEPT_DICT_EXT cs = (C_DEPT_DICT_EXT) be;
			node.setid(cs.getDEPT_CODE() + "");
			node.settext(cs.getDEPT_NAME());
			node.setstate("open");
			nodes.add(node);
		}
		HLogger.info("HosTreeTool function getDepartmentChildrenTreeNode end!");
		return nodes;
	}

    /**
    * <pre>
    * 任务： APPCOMMERCIALBUG-2126 
    * 描述： 通过医院ID查询科室信息
    * 作者：zhangsiyuan
    * 时间：2016年2月4日上午11:21:44
    * @param appDb
    * @param hospitalId
    * @return
    * @throws BaseBllException
    * returnType：List<BaseEntity>
    * </pre>
    */
    public static List<BaseEntity> queryDeptInfobyHospitalId(IDataBase appDb,int hospitalId)throws BaseBllException{
		HLogger.info("HosTreeTool function queryDeptInfobyHospitalId start!");
    	StringBuffer sql=new StringBuffer();
        sql.append(" SELECT DEPT_CODE,DEPT_NAME FROM C_DEPT_DICT WHERE HOSPITAL_ID=:HOSPITALID ");
        List<AppDbParameter> parameter=new ArrayList<AppDbParameter>();
        parameter.add(new AppDbParameter("HOSPITALID",hospitalId));
        HLogger.info("HosTreeTool function queryDeptInfobyHospitalId end!");
        return appDb.QueryEntity(C_DEPT_DICT_EXT.class, sql.toString(), parameter).getEntityList();
    }
}

