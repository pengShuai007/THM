package APP.Comm.DataBase.Helper;

import java.io.Serializable;
import java.sql.Blob;
import java.util.Date;
import java.util.List;
import java.util.Map;

import APP.Comm.BLL.BaseBllException;
import APP.Comm.DataBase.Parameter.AppDbParameter;
import APP.Comm.View.GridRequestParameters;
import APP.Model.BaseEntity;

//模块编号：<DBHelper>
//作用：<数据访问类接口>
//作者：张宝锋
//编写日期：<2012-12-27>
 /**
 *  
 * 修改人：  ypf <br/>  
 * 修改时间：2014年10月29日 11:28 <br/>  
 * 修改备注：修改文件编译时报编码错误的提示<br/>
 * 任务号：KYEEAPP-692
 * @创建人 ypf
 * @版本
 */
public interface IDataBase extends Serializable {
	/**
	 * 获取数据库类型
	 * 
	 * @return 数据库类型 //DatabaseType GetDatabaseType();
	 */

	/**
	 * 插入新数据 执行Insert操作时调用
	 * 
	 * @param sqlString
	 * @param AppDbParameters
	 * @return
	 * @throws BaseBllException
	 */
	int Save(String sqlString, List<AppDbParameter> AppDbParameters)
			throws BaseBllException;

	/**
	 * 更细数据 执行Update操作时调用
	 * 
	 * @param sqlString
	 * @param AppDbParameters
	 * @return
	 * @throws BaseBllException
	 */
	int Update(String sqlString, List<AppDbParameter> AppDbParameters)
			throws BaseBllException;

	/**
	 * 删除数据 执行Delete操作时调用
	 * 
	 * @param sqlString
	 * @param AppDbParameters
	 * @return
	 * @throws BaseBllException
	 */
	int Delete(String sqlString, List<AppDbParameter> AppDbParameters)
			throws BaseBllException;

	/**
	 * 执行存储过程时调用
	 * 
	 * @param sqlString
	 * @param AppDbParameters
	 * @return
	 * @throws BaseBllException
	 */
	Map<String, Object> ExecProcedure(String sqlString,
			List<AppDbParameter> AppDbParameters) throws BaseBllException;

	/**
	 * 获取某行一个字段的值，可以是任意类型 当需要获取某一个具体的字段值时调用 创建者：张宝锋 创建时间：2012-12-27
	 * 
	 * @param sqlString
	 *            要执行的Sql语句
	 * @param AppDbParameters
	 *            IList AppDbParameter>类型的参数列表
	 * @return object
	 * @throws BaseBllException
	 */
	Object QueryObject(String sqlString, List<AppDbParameter> AppDbParameters)
			throws BaseBllException;
	
	/**
	 * 任务号：
	 * 描述：获取Blob
	 * 作者：liuxingping
	 * 时间：2015年6月15日上午10:01:19
	 * @param sqlString
	 * @param AppDbParameters
	 * @return
	 * @throws BaseBllException
	 */
	Blob QueryBlob(String sqlString, List<AppDbParameter> AppDbParameters)
			throws BaseBllException;

	/**
	 * 执行一般查询，返回一个HrpDataTable类型数据集 创建者：张宝锋 创建时间：2012-12-27
	 * 
	 * @param sqlString
	 *            Sql语句
	 * @param AppDbParameters
	 *            IList AppDbParameter>类型参数列表
	 * @return HrpDataTable
	 * @throws BaseBllException
	 */
	AppDataTable Query(String sqlString, List<AppDbParameter> AppDbParameters)
			throws BaseBllException;

	/**
	 * 翻页查询用 返回一个HrpDataTable类型数据集 创建者：张宝锋 创建时间：2012-12-28/
	 * 
	 * @param sqlString
	 *            sqlString必须是普通查询语句，且不能出现Order By！返回要查询的数据集
	 * @param primaryKey
	 *            primaryKey必须是select count(*)
	 *            格式，并且这两个sql语句的where条件必须相同！返回满足查询条件的总记录行数
	 * @param AppDbParameters
	 *            IList AppDbParameter 类型的参数列表
	 * @param gridRequestParameters
	 *            GridRequestParameters类型的列表
	 * @return 返回HrpDataTable
	 * @throws BaseBllException
	 */
	AppDataTable QueryByPage(String sqlString, String primaryKey,
			List<AppDbParameter> AppDbParameters,
			GridRequestParameters gridRequestParameters)
			throws BaseBllException;

	/**
	 * 作用：返回一个具体的实体对象列表 创建者：张宝锋 创建时间：2012-12-27
	 * 
	 * @param baseEntity
	 *            BaseEntity对象类型,Model实体类型
	 * @param sqlString
	 *            Sql查询语句
	 * @param AppDbParameters
	 *            IList AppDbParameter>类型的参数列表
	 * @return 返回AppEntitys
	 * @throws BaseBllException
	 */
	AppEntitys QueryEntity(java.lang.Class entityType, String sqlString,
			List<AppDbParameter> AppDbParameters) throws BaseBllException;

	AppEntitys QueryEntityWithoutLog(java.lang.Class entityType,
			String sqlString, List<AppDbParameter> AppDbParameters)
			throws BaseBllException;

	/**
	 * 翻页查询用，返回一个具体的实体对象列表 创建者：张宝锋 创建时间：2012-12-28
	 * 
	 * @param baseEntity
	 *            BaseEntity对象类型,Model实体类型
	 * @param sqlString
	 *            Sql查询语句
	 * @param AppDbParameters
	 *            IList AppDbParameter>类型的参数列表
	 * @param gridRequestParameters
	 *            GridRequestParameters类型参数列表
	 * @return
	 * @throws BaseBllException
	 */
	AppEntitys QueryEntityByPage(java.lang.Class entityType, String sqlString,
			String primaryKey, List<AppDbParameter> AppDbParameters,
			GridRequestParameters gridRequestParameters)
			throws BaseBllException;

	// 添加参数bool readOnly
	// 只读属性为false时，返回的实体可以直接调用实体更新方法，否则不能调用实体更新方法
	// 施建龙
	// 2013年3月4日11:38:38
	AppEntitys QueryEntityByPage(java.lang.Class entityType, String sqlString,
			String primaryKey, List<AppDbParameter> AppDbParameters,
			GridRequestParameters gridRequestParameters, boolean readOnly)
			throws BaseBllException;

	AppEntitys QueryEntity(java.lang.Class entityType, String sqlString,
			List<AppDbParameter> AppDbParameters, boolean readOnly)
			throws BaseBllException;

	// /#region 保存实体至数据库 施建龙 2012-12-28

	// 保存实体至数据库
	// 施建龙 2012-12-28
	int Save(BaseEntity entity) throws BaseBllException;

	int Update(BaseEntity entity) throws BaseBllException;

	int Delete(BaseEntity entity) throws BaseBllException;

	// 刷新entity数据
	BaseEntity Refresh(BaseEntity entity) throws BaseBllException;

	int Save(List<BaseEntity> entitys) throws BaseBllException;

	int Update(List<BaseEntity> entitys) throws BaseBllException;

	int Delete(List<BaseEntity> entitys) throws BaseBllException;

	// 直接构造sql语句，不适用数据库参数，拼装sql语句的行数用rows设定
	// sjl
	int BulkInsert(List<BaseEntity> entitys, int rows) throws BaseBllException;

	int BulkUpdate(List<BaseEntity> entitys, int rows) throws BaseBllException;

	/**
	 * 说明：设置是否需要启动事务控制。对于纯查询的的操作如报表统计等，不需要开启事务 创建者：宋建康 创建时间：2013-01-03
	 */
	void NeedTransaction(boolean needTransaction);

	boolean NeedTransaction();

	/**
	 * 是否需要打开数据库连接
	 * 
	 * @param need
	 * @return
	 */
	void NeedOpen(boolean need);

	boolean NeedOpen();

	/**
	 * 获取数据库服务器时间
	 * 
	 * @return
	 * @throws BaseBllException
	 */
	Date ServerDate() throws BaseBllException;

	/**
	 * 获取数据库最新ID
	 * 
	 * @return
	 * @throws BaseBllException
	 */
	long GetNewID() throws BaseBllException;

	long GetNewID(String seqName) throws BaseBllException;

	/**
	 * 检查一个表中某个字段内容是否已经存在 若存在，返回true；否则返回false 创建者：张宝锋 创建时间：2013-01-07
	 * 
	 * @param tableName
	 *            要检查的表名
	 * @param columnName
	 *            要检查的字段名
	 * @param columnValue
	 *            要检查的字段值
	 * @return
	 * @throws BaseBllException
	 */
	boolean CheckExists(String tableName, String columnName, String columnValue)
			throws BaseBllException;

	/**
	 * 检查一个表中某个字段内容是否已经存在 若存在，返回true；否则返回false 创建者：张宝锋 创建时间：2013-01-07
	 * 
	 * @param tableName
	 *            要检查的表名
	 * @param columnName
	 *            要检查的字段名
	 * @param dictionary
	 *            要检查的字段键、值字典
	 * @return
	 * @throws BaseBllException
	 */
	boolean CheckExists(String tableName, String columnName,
			java.util.Map<String, String> dictionary) throws BaseBllException;

	/**
	 * 检查一个表中某个字段内容是否已经存在 若存在，返回true；否则返回false 创建者：张宝锋 创建时间：2013-03-12
	 * 
	 * @param tableName
	 *            要检查的表名
	 * @param dictionary
	 *            要检查的字段键、值字典
	 * @return
	 * @throws BaseBllException
	 */
	boolean CheckExists(String tableName,
			java.util.Map<String, String> dictionary) throws BaseBllException;

	/**
	 * 检查一个表中某个记录是否已经存在,按原始数据校验，不对字段值进行大小写转换和Trim()处理 若存在，返回true；否则返回false
	 * 创建者：张宝锋 创建时间：2013-09-05
	 * 
	 * @param tableName
	 *            要检查的表名
	 * @param dictionary
	 *            要检查的字段键、值字典
	 * @return 存在返回True，否则返回False
	 * @throws BaseBllException
	 */
	boolean CheckExistsOriginal(String tableName,
			java.util.Map<String, String> dictionary) throws BaseBllException;

	/**
	 * 测试数据库连接是否正确 true：正确 false：错误 施建龙 2013年2月22日16:14:16
	 * 
	 * @return
	 * @throws BaseBllException
	 */
	boolean TestConnection() throws BaseBllException;

	/**
	 * 根据外键名称找到 相应的源表的中文名称
	 * 
	 * @throws BaseBllException
	 */
	String GetForeignTableName(String foreignKey) throws BaseBllException;

	/**
	 * 说明：获取数据权限之 核算单元权限 (key:RECKON_UNIT_CODE),只对一般用户进行数据权限校验 作者：左仁帅
	 * 时间：2013年3月24日 16:46:15
	 * 
	 * @param key
	 *            比如：t0.RECKON_UNIT_CODE
	 * @return AND EXISTS (SELECT a.RECKON_UNIT_CODE FROM (SELECT
	 *         unit.RECKON_UNIT_CODE,per.USER_ID FROM DR_RECKON_UNIT unit LEFT
	 *         JOIN SYS_USER_RECKON_PERM per ON
	 *         per.RECKON_UNIT_ID=unit.RECKON_UNIT_ID WHERE
	 *         per.RECKON_UNIT_ID=unit.RECKON_UNIT_ID AND per.PERM_FLAG=1 ) a
	 *         WHERE a.USER_ID='" + base.HrpUser.LoginName +
	 *         "' AND RECKON_ITEM_CODE ="+key+" )
	 */
	String GetDataAccessReckonUnit(String key);

	/**
	 * 说明：获取数据权限之 核算项目权限 (key:RECKON_ITEM_CODE),只对一般用户进行数据权限校验 作者：左仁帅
	 * 时间：2013年3月24日 16:46:15
	 * 
	 * @param key
	 *            比如：t0.RECKON_ITEM_CODE
	 * @return AND EXISTS ( SELECT RECKON_ITEM_CODE FROM SYS_RECKON_ITEM_PERM
	 *         WHERE PERM_FLAG=1 AND USER_ID='" + base.HrpUser.LoginName +
	 *         "' AND RECKON_ITEM_CODE ="+key+" )
	 */
	String GetDataAccessReckonItem(String key);

	/**
	 * 说明：获取数据权限之 成本项目权限 (key:COST_ITEM_NO) ,只对一般用户进行数据权限校验 作者：左仁帅 时间：2013年3月24日
	 * 16:46:15
	 * 
	 * @param key
	 *            比如：t0.COST_ITEM_NO
	 * @return AND EXISTS ( SELECT COST_ITEM_NO FROM SYS_COST_ITEM_PERM WHERE
	 *         PERM_FLAG=1 AND USER_ID='" + base.HrpUser.LoginName +
	 *         "' AND COST_ITEM_NO="+key+" )
	 */
	String GetDataAccessCostItem(String key);

	/**
	 * comments:
	 * 
	 * sjl modify 2013-10-15上午11:23:39
	 */
	String getCharSet();

	void setCharSet(String charSet);

	/**
	 * 获取当前DataBase的No 施建龙 2013年10月29日14:42:53
	 * 
	 * @return
	 */
	String GetDatabaseName();

	/**
	 * 说明：获取存储过程的out类型返回 作者：侯昊鹰 时间：2013年10月25日 15:35:10
	 * 
	 * @param sqlString
	 * @param AppDbParameters
	 * @return
	 */
	java.util.Map<String, Object> ExecResultProcedure(String sqlString,
			List<AppDbParameter> AppDbParameters) throws BaseBllException;

	void failure() throws BaseBllException;

	void rollback() throws BaseBllException;
	void commit() throws BaseBllException;
}