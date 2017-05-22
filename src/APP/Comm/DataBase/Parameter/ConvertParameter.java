package APP.Comm.DataBase.Parameter;

import APP.Comm.DataBase.DotNet.DbType;
import APP.Comm.DataBase.DotNet.OracleParameter;
import APP.Comm.DataBase.DotNet.OracleType;
import APP.Comm.DataBase.DotNet.SqlServerParameter;

public class ConvertParameter {
	/**
	 * 将IList<AppDbParameter>类型转换成OracleParameter类型
	 * 
	 * @param cmdParameter
	 * @return //创建者：张宝锋 //创建时间：2012-12-27
	 */
	public static OracleParameter[] ConvertToDbParameter(
			java.util.List<AppDbParameter> cmdParameter) {
		AppDbParameter[] pars = new AppDbParameter[cmdParameter.size()];

		for (int i = 0; i < pars.length; i++) {
			pars[i] = cmdParameter.get(i);
		}

		return ConvertToOracleParameter(pars);
	}

	/**
	 * 将AppDbParameter类型参数转换成OracleParameter类型
	 * 
	 * @param AppDbParameters
	 *            自定义AppDbParameter类型
	 * @return 返回OracleParameter类型对象数组 //创建者：张宝锋 //创建时间：2012-12-27
	 */
	private static OracleParameter[] ConvertToOracleParameter(
			AppDbParameter[] AppDbParameters) {
		OracleParameter[] pars = new OracleParameter[AppDbParameters.length];
		for (int i = 0; i < AppDbParameters.length; i++) {
			OracleType parType = ConvertToOracleDbType(AppDbParameters[i]
					.getAppDbType());
			String parName = AppDbParameters[i].getColumnName().trim();
			int parSize = AppDbParameters[i].getsize();
			// 参数指定数据类型，同时指定长度
			if (AppDbParameters[i].getAppDbType() != AppDbType.AnsiString
					&& AppDbParameters[i].getsize() != 0) {
				// OracleType parType =
				// ConvertToOracleDbType(AppDbParameters[i].getHrpDbType());
				// int parSize = AppDbParameters[i].getsize();
				pars[i] = new OracleParameter(parName, parType, parSize);
				pars[i].setValue(AppDbParameters[i].getColumnValue());
			}
			// 参数指定数据类型，长度为0
			else if (AppDbParameters[i].getAppDbType() != AppDbType.AnsiString
					&& AppDbParameters[i].getsize() == 0) {
				// String parName = AppDbParameters[i].getColumnName().trim();
				// OracleType parType =
				// ConvertToOracleDbType(AppDbParameters[i].getHrpDbType());
				// int parSize = AppDbParameters[i].getsize();

				pars[i] = new OracleParameter(parName, parType);
				pars[i].setValue(AppDbParameters[i].getColumnValue());
			}else if(AppDbParameters[i].getInOrOut()==1){
				pars[i] = new OracleParameter(parName,parType,parSize,
						AppDbParameters[i].getColumnValue()+"",AppDbParameters[i].getInOrOut(),AppDbParameters[i].getReturnDataType());
				pars[i].setParType(parType);
			}
			// 参数的数据类型和长度都没有指定，只有参数名和参数值
			else // if (AppDbParameters[0].HrpDbType == HrpDbType.AnsiString &&
					// AppDbParameters[0].size == 0)
			{
				// String parName = AppDbParameters[i].getColumnName().trim();
				pars[i] = new OracleParameter(parName,
						AppDbParameters[i].getColumnValue());
				pars[i].setParType(parType);
			}
			
		}
		return pars;
	}

	/**
	 * 将AppDbParameter类型参数转换成SqlParameter类型
	 * 
	 * @param AppDbParameters
	 *            自定义AppDbParameter类型
	 * @return 返回SqlParameter类型对象数组 //创建者：张宝锋 //创建时间：2012-12-27
	 */
	private static SqlServerParameter[] ConvertToSqlParameter(
			AppDbParameter[] AppDbParameters) {
		SqlServerParameter[] pars = new SqlServerParameter[AppDbParameters.length];

		for (int i = 0; i < AppDbParameters.length; i++) {
			String parName = "@" + AppDbParameters[i].getColumnName().trim();

			OracleType parType = OracleType.VarChar;
			int parSize = AppDbParameters[i].getsize();
			String parValue = AppDbParameters[i].getColumnValue().toString();

			pars[i] = new SqlServerParameter(parName, parType, parSize,
					parValue);
		}

		return pars;
	}

	/**
	 * 数据类型转化，将DbType类型具体化为OracleType
	 * 
	 * @param dbType
	 *            DbType
	 * @return 返回OracleType //创建者：张宝锋 //创建时间：2012-12-27
	 */
	private static OracleType ConvertToOracleDbType(AppDbType dbType) {
		switch (dbType) {
		case BFile:
			return OracleType.BFile;
		case Blob:
			return OracleType.Blob;
		case Byte:
			return OracleType.Byte;
		case Char:
			return OracleType.Char;
		case Clob:
			return OracleType.Clob;
		case Cursor:
			return OracleType.Cursor;
		case DateTime:
			return OracleType.DateTime;
		case Double:
			return OracleType.Double;
		case Float:
			return OracleType.Float;
		case Int16:
			return OracleType.Int16;
		case Int32:
			return OracleType.Int32;
		case IntervalDayToSecond:
			return OracleType.IntervalDayToSecond;
		case IntervalYearToMonth:
			return OracleType.IntervalYearToMonth;
		case LongRaw:
			return OracleType.LongRaw;
		case LongVarChar:
			return OracleType.LongVarChar;
		case NChar:
			return OracleType.NChar;
		case NClob:
			return OracleType.NClob;
		case Number:
			return OracleType.Number;
		case NVarChar:
			return OracleType.NVarChar;
		case Raw:
			return OracleType.Raw;
		case RowId:
			return OracleType.RowId;
		case SByte:
			return OracleType.SByte;
		case Timestamp:
			return OracleType.Timestamp;
		case TimestampLocal:
			return OracleType.TimestampLocal;
		case TimestampWithTZ:
			return OracleType.TimestampWithTZ;
		case UInt16:
			return OracleType.UInt16;
		case UInt32:
			return OracleType.UInt32;
		case VarChar:
			return OracleType.VarChar;
		default:
			return OracleType.VarChar;
		}
	}

	/**
	 * 数据类型转化，将DbType类型具体化为SqlDbType
	 * 
	 * @param dbType
	 *            DbType
	 * @return 返回SqlDbType //创建者：张宝锋 //创建时间：2012-12-27
	 */
	private static OracleType ConvertToSqlDbType(DbType dbType) {
		switch (dbType) {
		case Decimal:
			return OracleType.Double;
		case String:
			return OracleType.VarChar;
		case Date:
			return OracleType.DateTime;
		case Binary:
			return OracleType.Blob;
		case Int32:
			return OracleType.Int32;
		default:
			return OracleType.VarChar;
		}
	}
}