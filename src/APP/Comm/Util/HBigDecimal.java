package APP.Comm.Util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

public class HBigDecimal extends BigDecimal {

	public HBigDecimal(BigInteger unscaledVal, int scale, MathContext mc) {
		super(unscaledVal, scale, mc);
	}

	public HBigDecimal(BigInteger unscaledVal, int scale) {
		super(unscaledVal, scale);

	}

	public HBigDecimal(BigInteger val, MathContext mc) {
		super(val, mc);

	}

	public HBigDecimal(BigInteger val) {
		super(val);

	}

	public HBigDecimal(char[] in, int offset, int len, MathContext mc) {
		super(in, offset, len, mc);

	}

	public HBigDecimal(char[] in, int offset, int len) {
		super(in, offset, len);

	}

	public HBigDecimal(char[] in, MathContext mc) {
		super(in, mc);

	}

	public HBigDecimal(char[] in) {
		super(in);

	}

	public HBigDecimal(double val, MathContext mc) {
		super(val, mc);

	}

	public HBigDecimal(double val) {
		super(val);

	}

	public HBigDecimal(int val, MathContext mc) {
		super(val, mc);

	}

	public HBigDecimal(int val) {
		super(val);

	}

	public HBigDecimal(long val, MathContext mc) {
		super(val, mc);

	}

	public HBigDecimal(long val) {
		super(val);

	}

	public HBigDecimal(String val, MathContext mc) {
		super(val, mc);

	}

	public HBigDecimal(String val) {
		super(val);

	}

	/**
	 * comments:����dotnetǨ����java
	 * 
	 * sjl modify 2013-10-13����9:38:45
	 */
	public static BigDecimal Parse(Object in) {
		if (in == null || in.toString().trim().length() == 0
				|| in.toString().trim().equalsIgnoreCase("null")) {
			return BigDecimal.valueOf(0);
		}
		return new HBigDecimal(in.toString());
	}

}
