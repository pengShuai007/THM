package com.util;

public class StringUtil {

	/**
	 * 处理过长文本 作者：张勇 时间：二〇一四年四月二十三日 14:30:12
	 * 
	 * @param str
	 *            原文本
	 * @param limit
	 *            需要显示的字数
	 * @return 文本...
	 */
	public static String subTextByLimit(String str, int limit) {
		byte[] datas = str.getBytes();
		int english = 0;
		for (byte b : datas) {
			if (b > 0 && b < 127) {
				english++;
			}
		}
		int validLength = str.length() - english / 2;

		if (limit < validLength) {
			int endIndex = 0;
			int chinese = 0;
			for (int i = 0; i < datas.length; i++) {
				if (datas[i] < 0) {
					chinese++;
				}
				if (i + 1 == limit * 2) {
					if (chinese % 2 == 0) {
						endIndex = i + 1 - chinese / 2;
					} else {
						endIndex = i + 1 - (chinese + 1) / 2;
					}
					break;
				}
			}

			str = str.substring(0, endIndex) + "...";
		}
		return str;
	}

	/**
	 * 
	 * 描述: 判断字符串是否为 空 JIRA单号： KYEEAPP-947<br/>
	 * 创建人: tianlong <br/>
	 *
	 * @param str
	 * @return
	 * @since Ver 1.1
	 */
	public static boolean isStrBlank(String str) {
		return (null == str || "".equals(str.trim()));
	}
}
