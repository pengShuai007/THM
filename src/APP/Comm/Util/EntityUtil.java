package APP.Comm.Util;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import APP.Comm.BLL.BaseBllException;
import APP.Comm.DataBase.DotNet.DataColumn;
import APP.Comm.DataBase.DotNet.DataRow;
import APP.Comm.DataBase.DotNet.DataTable;
import APP.Comm.DataBase.Helper.AbstractDataBase;
import APP.Comm.DataBase.Parameter.AppDbParameter;
import APP.Model.BaseEntity;

/**
 * 实体工具类，与实体相关的通用操作通过本类进行 创建者:施建龙 创建时间：2012-12-27
 */
public class EntityUtil {

	protected static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	protected static SimpleDateFormat sdfLong = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	/**
	 * 根据entity对象的UpdateAttributes获取到需要新增的列名 获取entity需要新增列的值 sql语句中只包含需要新增列
	 * 编写：施建龙 时间：2013年1月11日 11:49:14
	 * 
	 * @param entity
	 * @return
	 * @throws BaseBllException
	 */
	public static EntitySql CreateSaveEntitySql(BaseEntity entity)
			throws BaseBllException {
		EntitySql entitySql = null;
		StringBuilder sqlColumns = new StringBuilder();
		StringBuilder sqlColumnValues = new StringBuilder();

		sqlColumns.append(" INSERT INTO " + entity.GetTableName() + " ( ");
		sqlColumnValues.append(" VALUES (");
		java.util.List<String> updateAttributes = null;
		updateAttributes = entity.getUpdateAttributes();
		java.util.Map<String, EntityAttributeTypeValue> attributeValues = null;
		attributeValues = CreateAttributeValues(entity, updateAttributes);
		java.util.List<AppDbParameter> hrpDbParameters = new java.util.ArrayList<AppDbParameter>();
		AppDbParameter AppDbParameter = null;

		// typing in Java:
		for (Map.Entry<String, EntityAttributeTypeValue> entityAttributeTypeValue : attributeValues
				.entrySet()) {
			// 新增忽略VERSION字段
//			if (entityAttributeTypeValue.getKey().toUpperCase()
//					.equals("VERSION")) {
//				continue;
//			}
//			if (!entityAttributeTypeValue.getKey().equals(
//					entity.GetPrimaryKeyName())) {
				// 指定insert sql语句中的列名
				sqlColumns.append(entityAttributeTypeValue.getKey() + ", ");
				sqlColumnValues.append(":" + entityAttributeTypeValue.getKey()
						+ ", ");
				AppDbParameter = new AppDbParameter(
						entityAttributeTypeValue.getKey(),
						entityAttributeTypeValue.getValue().getValue())
						.AddColumnType(entityAttributeTypeValue.getValue()
								.getAttributeType());
				hrpDbParameters.add(AppDbParameter);
//			}
		}
		sqlColumns.delete(sqlColumns.length() - 2, sqlColumns.length()).append(
				") ");
		sqlColumnValues.delete(sqlColumnValues.length() - 2,
				sqlColumnValues.length()).append(") ");
		sqlColumns.append(sqlColumnValues);

		return new EntitySql(sqlColumns.toString(), hrpDbParameters);
	}

	/**
	 * 批量新增sql语句
	 * 
	 * @param entity
	 * @param columnNames
	 * @param includeName
	 *            是否包含列名
	 * @return
	 * @throws BaseBllException
	 */
	public static EntitySql CreateBuckInsertEntitySql(BaseEntity entity,
			String[] columnNames, int includeName) throws BaseBllException {
		StringBuilder sqlColumns = new StringBuilder();
		StringBuilder sqlColumnValues = new StringBuilder();

		sqlColumns.append(" INSERT INTO " + entity.GetTableName() + "  ");
		if (includeName > 0) {
			sqlColumns.append("(  ");
		}
		sqlColumnValues.append(" VALUES (");

		java.util.Map<String, EntityAttributeTypeValue> attributeValues = null;

		java.util.List<AppDbParameter> hrpDbParameters = new java.util.ArrayList<AppDbParameter>();
		AppDbParameter AppDbParameter = null;

		// typing in Java:
		for (String entityAttributeTypeValue : columnNames) {
			if (includeName > 0) {
				// 指定insert sql语句中的列名
				sqlColumns.append(entityAttributeTypeValue + ", ");
			}
			sqlColumnValues.append(":" + entityAttributeTypeValue + ", ");
			AppDbParameter = new AppDbParameter(entityAttributeTypeValue,
					entityAttributeTypeValue).AddColumnType(GetColumnType(
					entity, entityAttributeTypeValue));
			hrpDbParameters.add(AppDbParameter);
		}

		if (includeName > 0) {
			sqlColumns.delete(sqlColumns.length() - 2, sqlColumns.length())
					.append(") ");
		}

		sqlColumnValues.delete(sqlColumnValues.length() - 2,
				sqlColumnValues.length()).append(") ");
		sqlColumns.append(sqlColumnValues);

		return new EntitySql(sqlColumns.toString(), hrpDbParameters);
	}

	/**
	 * 不使用hrpDbParameters，直接拼装为可执行sql语句 施建龙 2013年2月8日7:53:19
	 * 
	 * @param entity
	 * @return
	 * @throws BaseBllException
	 */
	public static String CreateSaveEntitySqlString(AbstractDataBase dataBase,
			BaseEntity entity) throws BaseBllException {
		StringBuilder sqlColumns = new StringBuilder();
		StringBuilder sqlColumnValues = new StringBuilder();

		sqlColumns.append(" INSERT INTO " + entity.GetTableName() + " ( ");
		sqlColumnValues.append(" VALUES (");
		java.util.List<String> updateAttributes = null;
		updateAttributes = entity.getUpdateAttributes();

		java.util.Map<String, EntityAttributeTypeValue> attributeValues = null;
		attributeValues = CreateAttributeValues(entity, updateAttributes);

		java.util.List<AppDbParameter> hrpDbParameters = new java.util.ArrayList<AppDbParameter>();
		AppDbParameter AppDbParameter = null;

		// typing in Java:
		for (Map.Entry<String, EntityAttributeTypeValue> entityAttributeTypeValue : attributeValues
				.entrySet()) {
			// 新增忽略VERSION字段
			if (entityAttributeTypeValue.getKey().toUpperCase()
					.equals("VERSION")) {
				continue;
			}
			// 指定insert sql语句中的列名
			sqlColumns.append(entityAttributeTypeValue.getKey() + ", ");

			// 目前只对int,decimal,string,datetime几种类型进行处理
			sqlColumnValues.append(ConvertToString(dataBase,
					entityAttributeTypeValue.getValue()) + ", ");
		}

		sqlColumns.delete(sqlColumns.length() - 2, sqlColumns.length()).append(
				") ");
		sqlColumnValues.delete(sqlColumnValues.length() - 2,
				sqlColumnValues.length()).append(") ");

		sqlColumns.append(sqlColumnValues);

		return sqlColumns.toString();
	}

	/**
	 * 转换至数据库类型字符串 施建龙 2013年2月8日7:49:22
	 * 
	 * @param dataBase
	 * @param entityAttributeTypeValue
	 * @return
	 */
	public static String ConvertToString(AbstractDataBase dataBase,
			EntityAttributeTypeValue entityAttributeTypeValue) {
		if (entityAttributeTypeValue.getAttributeType().getName().toUpperCase()
				.indexOf("STRING") >= 0) {
			if (DotNetToJavaStringHelper.isNullOrEmpty(entityAttributeTypeValue
					.getValue() + "")) {
				return "''";
			} else {
				return "'" + entityAttributeTypeValue.getValue() + "'";
			}
		} else if (entityAttributeTypeValue.getAttributeType().getName()
				.toUpperCase().indexOf("INT") >= 0
				|| entityAttributeTypeValue.getAttributeType().getName()
						.toUpperCase().indexOf("LONG") >= 0) {
			return entityAttributeTypeValue.getValue() + "";
		} else if (entityAttributeTypeValue.getAttributeType().getName()
				.toUpperCase().indexOf("DECIMAL") >= 0) {
			return entityAttributeTypeValue.getValue() + "";
		} else if (entityAttributeTypeValue.getAttributeType().getName()
				.toUpperCase().indexOf("DATETIME") >= 0
				|| entityAttributeTypeValue.getAttributeType().getName()
						.toUpperCase().indexOf("TIMESTAMP") >= 0
				|| entityAttributeTypeValue.getAttributeType().getName()
						.toUpperCase().indexOf("DATE") >= 0) {
			if (entityAttributeTypeValue.getValue().toString().length() > 10) {
				return dataBase.StringToDateTime(CommonUtil
						.formatDateTime(entityAttributeTypeValue.getValue()));
			} else {
				return dataBase.StringToDate(CommonUtil
						.formatDate(entityAttributeTypeValue.getValue()));
			}
		}
		return "";
	}

	/**
	 * 生成特定类型数组 施建龙 2013年2月15日13:41:48
	 * 
	 * @param type
	 * @param length
	 * @return
	 */
	public static Object CreateTypeArray(Class type, int length) {
		if (type.getName().toUpperCase().equals("STRING")) {
			return new String[length];
		} else if (type.getName().toUpperCase().indexOf("INT") >= 0) {
			return new int[length];
		} else if (type.getName().toUpperCase().indexOf("DECIMAL") >= 0) {
			return new java.math.BigDecimal[length];
		} else if (type.getName().toUpperCase().indexOf("DATETIME") >= 0) {
			return new java.util.Date[length];
		}
		return new String[length];
	}

	/**
	 * 根据entity对象的UpdateAttributes获取到需要更新的列名 获取entity需要新更新的值 sql语句中只包含需要更新列
	 * 编写：施建龙 时间：2013年1月11日 11:49:30
	 * 
	 * @param entity
	 * @return
	 * @throws BaseBllException
	 */
	public static EntitySql CreateUpdateEntitySql(BaseEntity entity)
			throws BaseBllException {
		StringBuilder sqlColumns = new StringBuilder();
		sqlColumns.append(" UPDATE  " + entity.GetTableName() + " SET ");

		java.util.List<String> updateAttributes = null;
		updateAttributes = entity.getUpdateAttributes();

		java.util.Map<String, EntityAttributeTypeValue> attributeValues = null;
		attributeValues = CreateAttributeValues(entity, updateAttributes);

		java.util.List<AppDbParameter> hrpDbParameters = new java.util.ArrayList<AppDbParameter>();
		AppDbParameter AppDbParameter = null;

		String versionWhere = "";

		// 在更新列表中发现Version字段标记
		// 如果entity标记为需要执行版本控制，但未发现客户端更新version字段，则表示程序员忽略该环节，直接报异常
//		boolean findVersionAttribute = false;

		// typing in Java:
		for (Map.Entry<String, EntityAttributeTypeValue> entityAttributeTypeValue : attributeValues
				.entrySet()) {
			// 修改需要考虑VERSION字段
//			if (entityAttributeTypeValue.getKey().toUpperCase()
//					.equals("VERSION")) {
//				if (entity.Versioned()) {
//					versionWhere = " AND VERSION= "
//							+ entityAttributeTypeValue.getValue().getValue();
//					sqlColumns.append(entityAttributeTypeValue.getKey()
//							+ "=VERSION +1, ");
//				} else {
//					continue;
//				}
//			} else {
//				findVersionAttribute = true;
				// 忽略PrimaryKey更新
				if (!(entity.GetPrimaryKeyName()
						.equalsIgnoreCase(entityAttributeTypeValue.getKey()))) {
					// 指定update sql语句中的列名和值参数
					sqlColumns.append(entityAttributeTypeValue.getKey() + "=:"
							+ entityAttributeTypeValue.getKey() + ", ");
				}
				AppDbParameter = new AppDbParameter(
						entityAttributeTypeValue.getKey(),
						entityAttributeTypeValue.getValue().getValue());
				hrpDbParameters.add(AppDbParameter);
			}
//		}

//		if (entity.Versioned() && !findVersionAttribute) {
//			throw new BaseBllException("当前更新实体:" + entity.GetTableName()
//					+ "需要完成更新冲突检测。");
//		}

		sqlColumns.delete(sqlColumns.length() - 2, sqlColumns.length());
		sqlColumns.append(CreateUpdateWhere(entity) + versionWhere);

		return new EntitySql(sqlColumns.toString(), hrpDbParameters);
	}

	/**
	 * 施建龙 2013年2月8日10:46:16
	 * 
	 * @param dataBase
	 * @param entity
	 * @return
	 * @throws BaseBllException
	 */
	public static String CreateUpdateEntitySqlString(AbstractDataBase dataBase,
			BaseEntity entity) throws BaseBllException {
		StringBuilder sqlColumns = new StringBuilder();
		sqlColumns.append(" UPDATE  " + entity.GetTableName() + " SET  ");
		java.util.List<String> updateAttributes = null;
		updateAttributes = entity.getUpdateAttributes();
		java.util.Map<String, EntityAttributeTypeValue> attributeValues = null;
		attributeValues = CreateAttributeValues(entity, updateAttributes);

		if (attributeValues.isEmpty()) {
			return "";
		}

		// 在更新列表中发现Version字段标记
		// 如果entity标记为需要执行版本控制，但未发现客户端更新version字段，则表示程序员忽略该环节，直接报异常
		boolean findVersionAttribute = false;

		// typing in Java:
		for (Map.Entry<String, EntityAttributeTypeValue> entityAttributeTypeValue : attributeValues
				.entrySet()) {
			{
				findVersionAttribute = true;
				// 忽略PrimaryKey更新
				if (!entity.GetPrimaryKeyName().contains(
						entityAttributeTypeValue.getKey())) {
					// 指定update sql语句中的列名和值参数
					sqlColumns
							.append(entityAttributeTypeValue.getKey()
									+ "= "
									+ ConvertToString(dataBase,
											entityAttributeTypeValue.getValue())
									+ ", ");

				}
			}
		}
		sqlColumns.delete(sqlColumns.length() - 2, sqlColumns.length());
		sqlColumns.append(CreateUpdateSqlStringWhere(dataBase, entity));

		return sqlColumns.toString();
	}

	/**
	 * 构造带有entity对应table主键的where条件 页面更新中禁止更新主键列 编写：施建龙 时间：2013年1月11日 11:58:26
	 * 
	 * @param entity
	 * @return
	 */
	public static String CreateUpdateWhere(BaseEntity entity) {
		String primaryKeyName = entity.GetPrimaryKeyName();
		StringBuilder result = new StringBuilder(" WHERE ");
		String[] temp = primaryKeyName.split("[,]", -1);

		// typing in Java:
		for (String s : temp) {
			result.append(s + " =:" + s + " AND ");
		}
		if (result.length() < 10) {
			throw new RuntimeException("entity:" + entity.GetTableName()
					+ " 未设定主键！");
		}

		return result.delete(result.length() - 4, result.length()).toString();
	}

	/**
	 * 构造带有entity对应table主键的where条件,不使用dbparameter 施建龙 2013年2月15日11:12:27
	 * 
	 * @param entity
	 * @return
	 * @throws BaseBllException
	 */
	public static String CreateUpdateSqlStringWhere(AbstractDataBase dataBase,
			BaseEntity entity) throws BaseBllException {
		String primaryKeyName = entity.GetPrimaryKeyName();
		StringBuilder result = new StringBuilder(" WHERE ");
		String[] temp = primaryKeyName.split("[,]", -1);

		// typing in Java:
		for (String s : temp) {
			EntityAttributeTypeValue et = new EntityAttributeTypeValue(
					GetColumnType(entity, s), GetColumnValue(entity, s));
			result.append(s + " =" + ConvertToString(dataBase, et) + " AND ");
		}
		if (result.length() < 10) {
			throw new RuntimeException("entity:" + entity.GetTableName()
					+ " 未设定主键！");
		}

		return result.delete(result.length() - 4, result.length()).toString();
	}

	/**
	 * 创建刷新entity的带参sql
	 * 
	 * @param entity
	 * @return
	 * @throws BaseBllException
	 */
	public static EntitySql CreateSelectEntitySql(BaseEntity entity)
			throws BaseBllException {
		EntitySql entitySql = null;
		StringBuilder sqlColumns = new StringBuilder();
		sqlColumns.append(" SELECT * FROM  " + entity.GetTableName());

		java.util.List<AppDbParameter> hrpDbParameters = CreatePrimaryKeyParameters(entity);
		sqlColumns.append(CreateUpdateWhere(entity));

		return new EntitySql(sqlColumns.toString(), hrpDbParameters);
	}

	/**
	 * 创建主键Where查询条件参数 编写：施建龙 时间：2013年1月11日 14:32:53
	 * 
	 * @param entity
	 * @param primaryKeyName
	 * @return
	 * @throws BaseBllException
	 */
	public static java.util.List<AppDbParameter> CreatePrimaryKeyParameters(
			BaseEntity entity) throws BaseBllException {
		java.util.List<AppDbParameter> parameters = new java.util.ArrayList<AppDbParameter>();
		String primaryKeyName = entity.GetPrimaryKeyName();
		String[] temp = primaryKeyName.split("[,]", -1);

		// typing in Java:
		for (String s : temp) {
			AppDbParameter AppDbParameter = null;
			AppDbParameter = new AppDbParameter(s, GetColumnValue(entity,
					s.toString()));
			parameters.add(AppDbParameter);
		}

		return parameters;
	}

	/**
	 * 返回entity对象中可更新列的key，value集合
	 * 
	 * @param entity
	 * @param updateAttributes
	 * @return
	 * @throws BaseBllException
	 */
	private static java.util.Map<String, EntityAttributeTypeValue> CreateAttributeValues(
			BaseEntity entity, java.util.List<String> updateAttributes)
			throws BaseBllException {
		Class type = entity.getClass();

		String propertyName = null;

		java.util.Map<String, EntityAttributeTypeValue> attributeValues = new java.util.HashMap<String, EntityAttributeTypeValue>();

		Method methodInfo = null;

		Object value = null;
		EntityAttributeTypeValue attributeTypeValue = null;

		// typing in Java:
		HashMap<String, Method> methods = getGetMethods(type);
		for (String updateAttribute : updateAttributes) {
			try {
				// propertyInfo = type.getField(updateAttribute.toString());
				methodInfo = methods.get(updateAttribute.toString());
				/**
				 *任务：
				 *描述：针对实体中没有的方法只输出日志，不抛出异常
				 *人员：施建龙
				 *时间：2014年12月12日下午2:32:12
				 **/
				if(methodInfo==null){
					HLogger.Warn("实体更新警告:ClassType:"+type+"methodInfo:"+methodInfo+",updateAttribute:"+updateAttribute);
					continue;
				}
				value = methodInfo.invoke(entity, new Object[0]);
			} catch (Exception e) {
				// HLogger.Error(e);
				throw new BaseBllException(e);
			}
			attributeTypeValue = new EntityAttributeTypeValue(
					methodInfo.getReturnType(), value);
			attributeValues.put(updateAttribute, attributeTypeValue);
		}

		return attributeValues;
	}

	/**
	 * 获取entity中指定列的值 编写：施建龙 时间：2013年1月11日 11:57:42
	 * 
	 * @param entity
	 * @param columnName
	 * @return
	 * @throws BaseBllException
	 */
	public static Object GetColumnValue(BaseEntity entity, String columnName)
			throws BaseBllException {
		Class type = entity.getClass();
		Object value = null;
		Method methodInfo = null;
		HashMap<String, Method> methods = getGetMethods(type);
		try {
			// propertyInfo = type.getField(columnName);
			methodInfo = methods.get(columnName);
			// value = propertyInfo.get(entity);
			value = methodInfo.invoke(entity, new Object[0]);
		} catch (Exception e) {
			// HLogger.Error(e);
			throw new BaseBllException(e);
		}
		return value;
	}

	/**
	 * 获取entity中指定列的语言类型 编写：施建龙 时间：2013年1月11日 11:57:42
	 * 
	 * @param entity
	 * @param columnName
	 * @return
	 * @throws BaseBllException
	 */
	public static Class GetColumnType(BaseEntity entity, String columnName)
			throws BaseBllException {
		Class type = entity.getClass();
		Method methodInfo = null;
		HashMap<String, Method> fields = getGetMethods(type);
		try {
			methodInfo = fields.get(columnName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// HLogger.Error(e);
			throw new BaseBllException(e);
		}
		Class value = methodInfo.getReturnType();
		return value;
	}

	/**
	 * 反射并返回一个新的对象实例
	 * 
	 * @param dt
	 *            数据集合
	 * @param type
	 *            实例类型 创建者：张宝锋 创建时间：2012-12-27
	 * 
	 *            Log编号：1 来源于GenericList类的构造方法； 修改为外部工具类
	 * @param dt
	 *            数据集合
	 * @param type
	 *            实例类型 修改者：施建龙 修改时间：2013-01-05
	 * 
	 *            //添加参数bool readOnly=true //施建龙 //2013年3月4日11:38:38
	 * @throws BaseBllException
	 */
	// dt, Type type,bool readOnly=true)
	public static java.util.List<BaseEntity> CreateEntityList(DataTable dt,
			Class type, boolean readOnly) throws BaseBllException {
		HLogger.info("DataTable to Entity Start! record:" + dt.getRows().size());
		java.util.List<BaseEntity> results = new java.util.ArrayList<BaseEntity>();

		Object obj = null; // 创建指定类型实例
		/**
		 * comments:用方法的方式替换属性的方式，因为对于java平台，没有一个
		 * 简单的方法可以获取当前类和父类的所有属性，包括private属性
		 * 
		 * sjl modify 2013-10-14下午6:50:37
		 */
		// Method[] methods = type.getMethods(); // 获取指定对象的所有公共属性

		Map<String, Method> methods = getSetMethods(type);
		Method m = null;
		for (DataRow dr : dt.getRow()) {
			try {
				obj = type.newInstance();
			} catch (Exception e) {

				// e.printStackTrace();
				HLogger.Error(e);
			}
			if (readOnly) {
				// 设定实体为只读
				((BaseEntity) obj).setReadOnly(true);
			} else {
				((BaseEntity) obj).setReadOnly(false);
			}
			for (DataColumn dc : dr.getCol()) {
				setObjectPropertyValue(obj, methods, dc);
			}

			results.add((BaseEntity) obj); // 将对象填充到list集合
		}
		HLogger.info("DataTable to Entity End!");
		return results;
	}

	/**
	 * comments:
	 * 
	 * sjl modify 2013-10-15上午9:42:19
	 * 
	 * @throws BaseBllException
	 */
	private static void setObjectPropertyValue(Object obj,
			Map<String, Method> methods, DataColumn dc) throws BaseBllException {
		Method m;
		String colName = dc.getKey().toLowerCase().trim();
		Object value = null;
		value = dc.getValue();

		fillObjectPropertyValue(methods, obj, colName, value);
		// return i;
	}

	/**
	 * comments:对没有找到匹配方法的实体属性，需要抛出异常
	 * 
	 * sjl modify 2013-10-20下午12:20:21
	 * 
	 * @throws BaseBllException
	 */
	private static void fillObjectPropertyValue(Map<String, Method> methods,
			Object obj, String colName, Object value) throws BaseBllException {
		// Method m;
		// int i = 0;
		// for (; i < methods.length; i++) {
		if (value == null)
			return;
		String valueClassName = null;
		valueClassName = value.getClass().getName();
		boolean find = false;
		for (Method m : methods.values()) {
			// m = methods[i];
			String methodName;
			methodName = m.getName();// .toLowerCase()
			if (methodName.length() < 3) {
				continue;
			}
			// 移去set字符串
			methodName = methodName.substring(3);

			if (methodName.equalsIgnoreCase(colName)) {
				// ok
				Class[] p1 = m.getParameterTypes();
				// Class r1 = m.getReturnType();

				if (p1.length == 1) {
					if (p1[0].getName().contains("Decimal")) {
						if (valueClassName.contains("Long")) {
							value = new BigDecimal((Long) value);
						} else if (valueClassName.contains("Integer")) {
							value = new BigDecimal((Integer) value);
						}
					} else if (p1[0].getName().contains("Long")) {
						if (valueClassName.contains("Integer")) {
							value = new Long((Integer) value);
						}
					} else if (p1[0].getName().contains("Integer")) {
						if (valueClassName.contains("Long")) {
							continue;
						}
					} else if (!p1[0].getName().equals(valueClassName)) {
						// continue;
					}
					find = true;
					try {
						m.invoke(obj, new Object[] { value });
					} catch (Exception e) {

						// e.printStackTrace();
						HLogger.error(m.toString() + "colName:" + colName
								+ ",value:" + value);
						HLogger.error(e);
					}
				} else if (p1.length > 1) {
					continue;
				}
			}
		}
		// if(!find){
		// throw new BaseBllException("colName:" + colName
		// + ",value:" + value+",未能在实体中找到对应的set方法！");
		// }
	}

	/**
	 * 测试使用字符转换 编写:施建龙
	 * 
	 * @param inString
	 * @return
	 */
	public static String AsciiToGbk(String inString) {
		String result;

		byte[] sourcebytes;

		// sourcebytes = System.Text.Encoding.UTF7.GetBytes(inString);

		//
		// byte[] tempbytes = System.Text.Encoding.Convert(Encoding.Unicode,
		// Encoding.ASCII, sourcebytes);
		//
		// byte[] gbkbytes = System.Text.Encoding.Convert(Encoding.ASCII,
		// Encoding.BigEndianUnicode, tempbytes);
		// result = System.Text.Encoding.BigEndianUnicode.GetString(gbkbytes);
		result = inString;
		return result;
	}

	/**
	 * 构造IN操作Where条件
	 * 
	 * @param parms
	 *            转换为HrpDbParameter的参数集合
	 * @param formulaNameSet
	 *            页面传入的参数值
	 * @param sql
	 *            初始sql
	 * @param columnName
	 *            数据库字段名
	 * @return 修改为IN操作方式的sql语句
	 */
	public static String BuilderInOperaterCondition(
			java.util.List<AppDbParameter> parms, String[] formulaNameSet,
			StringBuilder sql, String columnName) {
		int index = 0;

		StringBuilder tempWhere = new StringBuilder();

		// typing in Java:
		for (String s : formulaNameSet) {
			AppDbParameter param = new AppDbParameter(columnName + index, s);
			parms.add(param);

			tempWhere.append(":" + columnName + index + ",");
			index++;
		}

		if (index > 0) {
			tempWhere.deleteCharAt(tempWhere.length() - 1);
			sql.append(" AND " + columnName + " IN (" + tempWhere + " ) ");
		}

		return sql.toString();
	}

	/**
	 * comments:获取所有祖先类的Method 为方便entity处理，统一转换为大写 sjl modify
	 * 2013-10-16下午7:39:55
	 */
	public static HashMap<String, Method> getSetMethods(Class type) {
		HashMap<String, Method> methods = new HashMap<String, Method>();
		Class superClass = null;
		// if(type.getCanonicalName().endsWith(".Object")){
		// return methods;
		// }
		Method[] currentMethods = type.getMethods();
		//
		String methodName = null;
		for (Method method : currentMethods) {
			methodName = method.getName();
			if (methodName.toUpperCase().startsWith("SET")) {
				methodName = methodName.substring(3).toUpperCase();
			}
			methods.put(methodName, method);
		}
		// superClass=type.getSuperclass();
		// fields.putAll(getFields(superClass));
		return methods;
	}

	/**
	 * comments:
	 * 
	 * sjl modify 2013-10-16下午9:03:49
	 */
	public static HashMap<String, Method> getGetMethods(Class type) {
		HashMap<String, Method> methods = new HashMap<String, Method>();
		Class superClass = null;
		// if(type.getCanonicalName().endsWith(".Object")){
		// return methods;
		// }
		Method[] currentMethods = type.getMethods();
		//
		String methodName = null;
		for (Method method : currentMethods) {
			methodName = method.getName();
			if (methodName.toUpperCase().startsWith("GET")) {
				methodName = methodName.substring(3).toUpperCase();
			}
			methods.put(methodName, method);
		}
		// superClass=type.getSuperclass();
		// fields.putAll(getFields(superClass));
		return methods;
	}

}