package APP.Comm.DataBase.DotNet;

public class SqlServerParameter extends OracleParameter {

	public SqlServerParameter(String parName, OracleType parType, int parSize,
			String value) {
		super(parName, parType, parSize, value);
	}

	public SqlServerParameter(String parName, OracleType parType, int parSize) {
		super(parName, parType, parSize);

	}

	public SqlServerParameter(String parName, OracleType parType) {
		super(parName, parType);

	}

	public SqlServerParameter(String parName, Object value) {
		super(parName, value);

	}

}
