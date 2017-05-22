package APP.Comm.DataBase.DotNet;

import java.sql.Blob;
import java.sql.Date;
import java.util.List;

import APP.Comm.BLL.BaseBllException;
import APP.Comm.Util.HLogger;

/**
 * comments:引用自网络
 * 
 * sjl modify 2013-10-5上午10:45:22
 */
public class DataRow {

	List<DataColumn> col;

	
	public DataColumn getColumnObject(String colName) {

		for (DataColumn c : col) {
			if (c.getKey().toUpperCase().equals(colName.toUpperCase())) {
//				try {
					return c;
//				} catch (Exception e) {
//					System.out.println("错误描述：" + e.toString());
//					HLogger.info("列:"+colName+",值为null");
//				}
			}
		}
		return null;
	}

	
	public Object getColumn(String colName) throws BaseBllException {
		for (DataColumn c : col) {
			if (c.getKey().toUpperCase().equals(colName.toUpperCase())) {
				try {
					return c.getValue();
				} catch (Exception e) {
//					System.out.println("错误描述：" + e.toString());
					HLogger.info("列:"+colName+",值为null");
//					HLogger.error(e);
					throw new BaseBllException(e);
				}
			}
		}
		return null;
	}

	
	public int getIntColumn(String colName) throws BaseBllException {
		for (DataColumn c : col) {
			if (c.getKey().toUpperCase().equals(colName.toUpperCase())) {
				try {
					return Integer.parseInt(c.getValue().toString());
				} catch (Exception e) {
//					System.out.println("错误描述：" + e.toString());
					HLogger.info("列:"+colName+",值为null");
					throw new BaseBllException(e);
				}
			}
		}
		return 0;
	}

	
	public String getStringColumn(String colName) throws BaseBllException {
		for (DataColumn c : col) {
			if (c.getKey().toUpperCase().equals(colName.toUpperCase())) {
				try {
					return (c.getValue()==null)?"":c.getValue().toString();
				} catch (Exception e) {
//					System.out.println("错误描述：" + e.toString());
					HLogger.info("列:"+colName+",值为null");
					throw new BaseBllException(e);
				}
			}
		}
		return null;
	}

	
	public String eval(String colName) throws BaseBllException {
		for (DataColumn c : col) {
			if (c.getKey().toUpperCase().equals(colName.toUpperCase())) {
				try {
					return c.getValue() + "";// 此方法将屏蔽错误！！！
				} catch (Exception e) {
//					System.out.println("错误描述：" + e.toString());
					HLogger.info("列:"+colName+",值为null");
					throw new BaseBllException(e);
				}
			}
		}
		return null;
	}

	
	public Date getDateColumn(String colName) throws BaseBllException {
		for (DataColumn c : col) {
			if (c.getKey().toUpperCase().equals(colName.toUpperCase())) {
				try {
					return Date.valueOf(c.getValue().toString());
				} catch (Exception e) {
//					System.out.println("错误描述：" + e.toString());
					HLogger.info("列:"+colName+",值为null");
					throw new BaseBllException(e);
				}
			}
		}
		return null;
	}

	
	public Blob getBlobColumn(String colName) throws BaseBllException {
		for (DataColumn c : col) {
			if (c.getKey().toUpperCase().equals(colName.toUpperCase())) {
				try {
					return (Blob) c.getValue();
				} catch (Exception e) {
//					System.out.println("错误描述：" + e.toString());
					HLogger.info("列:"+colName+",值为null");
					throw new BaseBllException(e);
				}
			}
		}
		return null;
	}

	
	public float getFloatColumn(String colName) throws BaseBllException {
		for (DataColumn c : col) {
			if (c.getKey().toUpperCase().equals(colName.toUpperCase())) {
				try {
					return Float.parseFloat(c.getValue().toString());
				} catch (Exception e) {
//					System.out.println("错误描述：" + e.toString());
					HLogger.info("列:"+colName+",值为null");
					throw new BaseBllException(e);
				}
			}
		}
		return 0;
	}

	public DataRow(List<DataColumn> c) {
		col = c;
	}

	public List<DataColumn> getCol() {
		return col;
	}

	public void setCol(List<DataColumn> col) {
		this.col = col;
	}

	/**
	 * comments:对列赋值
	 * 
	 * sjl modify 2013-10-11下午2:27:28
	 * @throws BaseBllException 
	 */
	public void setColumn(String colName,Object value) throws BaseBllException {
		for (DataColumn c : col) {
			if (c.getKey().toUpperCase().equals(colName.toUpperCase())) {
				try {
					c.setValue(value);
				} catch (Exception e) {
//					System.out.println("错误描述：" + e.toString());
//					HLogger.error(e);
					throw new BaseBllException(e);
				}
			}
		}
	}
	/*
	 * 时间：2016年7月12日14:24:37
	 * 作者：lichanjuan
	 * 描述：将行数据字段用逗号隔开，然后拼接，(注：字段值为为空或为字符串"null"，用0替换)
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()  {
		StringBuffer temp = new StringBuffer();
		for (DataColumn c : col) {
			//System.out.println(c.getValue());
			if("null".equals(c.getValue()) || c.getValue() == null || "".equals(c.getValue()))
			{
				temp.append("0,");
			}
			else
			{
				temp.append("'"+c.getValue()+"',");
			}
		}
		String result = temp.toString();
		return result.substring(0, result.length()-1);
	}
}
