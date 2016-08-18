package com.develop.model.util;

import java.text.DecimalFormat;

public class FormatterUtil {

	/**
	 * 指定长度补0
	 * 
	 * @param number
	 * @param length
	 * @return
	 */
	public static String formatNumber(long number, long length) {
		return String.format("%0" + length + "d", number);
	}

	/**
	 * 根据pattern格式化，0000
	 * 
	 * @param number
	 * @param pattern
	 * @return
	 */
	public static String formatNumber(long number, String pattern) {
		return new DecimalFormat(pattern).format(number);
	}

	public static String formatNumber(long number, long length, String supply) {
		StringBuffer builder = new StringBuffer();
		for (int i = 0; i < length - String.valueOf(number).length(); i++) {
			builder.append(supply);
		}
		builder.append(number);
		return builder.toString();
	}

}
