package APP.Comm.Util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import APP.Comm.BLL.BaseBllException;

/**
 * 工具类，使用反射机制 2013年10月4日11:13:15
 * 
 * @author sjl
 * 
 */
public class ObjectFactory {
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	static SimpleDateFormat sdfLong = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	/**
	 * 利用reflection功能，根据传入的name，构造一个Object
	 * 
	 * @param name
	 * @return
	 */
	public static Object createObject(String name) {
		Object o = null;
		Class c = null;
		try {
			c = Class.forName(name);
			o = c.newInstance();
			// System.out.println(o.getClass().getName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			HLogger.error(e);
		}
		return o;
	}

	public static void setValue(Object object, String colName, Object value) {
		Object o = null;
		try {
			Class c = null;
			c = object.getClass();
			Method[] all;
			Method m;
			// all = c.getDeclaredMethods();
			all = c.getMethods();
			int i = 0;
			for (; i < all.length; i++) {
				m = all[i];
				String methodName;
				methodName = m.getName();

				if (methodName.toLowerCase().endsWith(colName.toLowerCase())
						&& methodName
								.toLowerCase()
								.substring(
										0,
										methodName.toLowerCase().indexOf(
												colName.toLowerCase()))
								.length() == 3) {
					// ok
					Object[] p1 = m.getParameterTypes();
					if (p1.length == 0) {
						continue;
					} else if (p1.length == 1) {
						// AirLogger.debug("colName:"+colName);
						if (value == null || ((Class) p1[0]).isInstance(value)) {
							m.invoke(object, new Object[] { value });
						} else {
							HLogger.debug("调用set方法出错，类型不匹配，需要:" + p1[0]
									+ "--提供：" + value.getClass());
						}
						return;
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			HLogger.error(e);
			// e.printStackTrace();
		}
	}

	public static void setValue(Object object, String colName, Object value,
			String colType) {
		Object o = null;
		try {
			Class c = null;
			c = object.getClass();
			Method[] all;
			Method m;
			// AirLogger.debug("getDeclaredMethods start:");
			// all = c.getDeclaredMethods();
			all = c.getMethods();
			// AirLogger.debug("getDeclaredMethods end:");
			int i = 0;
			for (; i < all.length; i++) {
				m = all[i];
				String methodName;
				methodName = m.getName();

				if (methodName.toLowerCase().endsWith(colName.toLowerCase())
						&& methodName
								.toLowerCase()
								.substring(
										0,
										methodName.toLowerCase().indexOf(
												colName.toLowerCase()))
								.length() == 3) {
					// ok
					Object[] p1 = m.getParameterTypes();

					if (p1.length == 0) {
						continue;
					} else if (p1.length == 1) {
						if (colType.equalsIgnoreCase("int")) {
							if (value == null) {
								m.invoke(object, new Object[] { null });
							} else {
								m.invoke(object, new Object[] { Integer
										.parseInt(value.toString()) });
							}
						} else if (colType.equalsIgnoreCase("long")) {
							if (value == null) {
								m.invoke(object, new Object[] { null });
							} else {
								m.invoke(object, new Object[] { Long
										.parseLong(value.toString()) });
							}
						} else {
							m.invoke(object, new Object[] { value });
						}
						return;
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			HLogger.error(e);
		}
	}

	public static Object getValue(Object object, String colName) {
		Object o = null;
		Class c = null;
		Method[] all;
		Method m;
		try {
			if (object == null) {
				return null;
			}
			// if(object instanceof BaseEntityViewMap){
			// return ((BaseEntityViewMap)object).get(colName);
			// }
			c = object.getClass();
			// all = c.getDeclaredMethods();
			all = c.getMethods();
			int i = 0;
			for (; i < all.length; i++) {
				m = all[i];
				String methodName;
				methodName = m.getName();

				if (methodName.toLowerCase().endsWith(colName.toLowerCase())
						&& (methodName
								.toLowerCase()
								.substring(
										0,
										methodName.toLowerCase().indexOf(
												colName.toLowerCase()))
								.length() == 3 || methodName
								.toLowerCase()
								.substring(
										0,
										methodName.toLowerCase().indexOf(
												colName.toLowerCase()))
								.length() == 2)) {
					// ok
					Object[] p1 = m.getParameterTypes();
					if (p1.length == 0) {
						o = m.invoke(object, new Object[] {});
						if (o instanceof Timestamp) {
							return formatTimeStamp(o);
						}
						if (o instanceof Date) {
							return formatDate(o);
						} else {
							return o;
						}

					} else if (p1.length == 1) {
						continue;
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			HLogger.error(e);
		}
		return o;
	}

	public static Object getValue(Object object, String colName,
			Object[] parameterValues) throws BaseBllException {
		Object o = null;
		try {
			Class c = null;
			c = object.getClass();
			Method[] all;
			Method m;
			// all = c.getDeclaredMethods();
			all = c.getMethods();
			int i = 0;
			for (; i < all.length; i++) {
				m = all[i];
				String methodName;
				methodName = m.getName();

				if (methodName.toLowerCase().endsWith(colName.toLowerCase())
						&& (methodName
								.toLowerCase()
								.substring(
										0,
										methodName.toLowerCase().indexOf(
												colName.toLowerCase()))
								.length() == 3 || methodName
								.toLowerCase()
								.substring(
										0,
										methodName.toLowerCase().indexOf(
												colName.toLowerCase()))
								.length() == 2)) {
					// ok
					Object[] p1 = m.getParameterTypes();
					if (p1.length == parameterValues.length) {
						o = m.invoke(object, parameterValues);
						if (o instanceof Date) {
							return formatDate(o);
						} else {
							return o;
						}

					} else if (p1.length == 1) {
						continue;
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			HLogger.error(e);
			throw new BaseBllException(e);
		}
		return o;
	}

	/**
	 * 为兼容系统设计
	 * 
	 * @param object
	 * @param colName
	 * @return
	 */
	public static Hashtable getHValue(Object object, int type) {
		Hashtable theReturn = new Hashtable();
		Object o = null;
		try {
			Class c = null;
			c = object.getClass();
			Method[] all;
			Method m;
			// all = c.getDeclaredMethods();
			all = c.getMethods();
			int i = 0;
			for (; i < all.length; i++) {
				m = all[i];
				String methodName;
				methodName = m.getName();

				if (methodName.startsWith("get")) {
					// ok
					Object[] p1 = m.getParameterTypes();
					if (p1.length == 0) {
						o = m.invoke(object, new Object[] {});
						// methodName.substring(3);
						if (o == null) {
							HLogger.debug(methodName.substring(3) + "null");
							theReturn.put(
									methodName.substring(3).toUpperCase(),
									new String[] { "" });
						} else {
							HLogger.debug(methodName.substring(3)
									+ o.toString());
							if (o instanceof Date) {
								theReturn.put(methodName.substring(3)
										.toUpperCase(),
										new String[] { formatDate(o) });
							} else {
								theReturn
										.put(methodName.substring(3)
												.toUpperCase(),
												new String[] { o + "" });
							}

						}
					} else if (p1.length == 1) {
						continue;
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			HLogger.Error(e);
		}
		return theReturn;
	}

	/**
	 * 
	 * @param source
	 * @param dest
	 */
	public static void dataTransfer(Object source, Object dest) {
		Hashtable theReturn = new Hashtable();
		Object o = null;
		try {
			Class c = null;
			c = source.getClass();
			Method[] all;
			Method m;
			Method setMethod;
			Class returnClass;
			// all = c.getDeclaredMethods();
			all = c.getMethods();
			int i = 0;
			for (; i < all.length; i++) {
				m = all[i];
				String methodName;
				methodName = m.getName();

				if (methodName.startsWith("get")) {
					// ok
					Object[] p1 = m.getParameterTypes();
					if (p1.length == 0) {
						returnClass = m.getReturnType();
						o = m.invoke(source, new Object[] {});
						try {
							setMethod = c.getDeclaredMethod(
									"set" + methodName.substring(3),
									new Class[] { returnClass });
							setMethod.invoke(dest, new Object[] { o });
						} catch (Exception e) {
							HLogger.error(e);
						}
						// methodName.substring(3);
						/*
						 * if (o == null) {
						 * theReturn.put(methodName.substring(3), new
						 * String[]{""}); } else {
						 * theReturn.put(methodName.substring(3), new String[] {
						 * o + "" }); }
						 */
					} else if (p1.length == 1) {
						continue;
					}
				} else if (methodName.startsWith("is")) {
					Object[] p1 = m.getParameterTypes();
					if (p1.length == 0) {
						returnClass = m.getReturnType();
						o = m.invoke(source, new Object[] {});
						try {
							setMethod = c.getDeclaredMethod(
									"set" + methodName.substring(2),
									new Class[] { returnClass });
							setMethod.invoke(dest, new Object[] { o });
						} catch (Exception e) {
							HLogger.error(e);
						}

					} else if (p1.length == 1) {
						continue;
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			HLogger.error(e);
		}
	}

	public static String formatDate(Object in) {
		String theReturn = "";
		theReturn = sdf.format(in);
		return theReturn;
	}

	public static String formatTimeStamp(Object in) {
		String theReturn = "";
		theReturn = sdfLong.format(in);
		return theReturn;
	}

	/**
	 * 执行无参方法 sjl
	 * 
	 * @param object
	 * @param methodName
	 * @throws BaseBllException
	 */
	public static Object execMethod(Object object, String methodName)
			throws BaseBllException {
		Object theReturn = null;
		Class c = null;
		Method m = null;
		Object[] p1 = null;
		try {
			c = object.getClass();
			m = c.getMethod(methodName, null);
			p1 = m.getParameterTypes();
			if (p1.length == 0) {
				theReturn = m.invoke(object, new Object[] {});
			} else if (p1.length == 1) {
				throw new BaseBllException("调用方法必须为无参方法，请检查传入的方法名称！");
			}
		} catch (Exception e) {
			if (e instanceof InvocationTargetException) {
				throw new BaseBllException(((InvocationTargetException) e));
			}
			if (e instanceof BaseBllException) {
				throw (BaseBllException) e;
			} else {
				throw new BaseBllException(e);
			}
		}
		return theReturn;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Object o = null;
		o = ObjectFactory.createObject("test.T_EO");
		ObjectFactory.setValue(o, "GCZLBH", "TTT-1");
		ObjectFactory.setValue(o, "GCZLBC", "01");
		ObjectFactory.setValue(o, "BIAOTI", "TEST");
		ObjectFactory.setValue(o, "JXBH2", "A320");
		ObjectFactory.setValue(o, "JXBH", "A320");
		Object o2 = null;
		o2 = ObjectFactory.createObject("test.T_EO");
		ObjectFactory.dataTransfer(o, o2);
		System.out.println(o2);

	}
}
