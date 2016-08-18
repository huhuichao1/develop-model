package com.develop.model.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import org.elasticsearch.common.lang3.ArrayUtils;

import com.develop.model.util.StringUtil;

/**
 * 数据类型工具类
 * 
 * @author huhuichao
 *
 */
public class DataTypeUtil {

	public static Integer getInteger(Object input) {
		return getInteger(input, null);
	}

	public static Integer getInteger(Object input, Integer defaultValue) {
		if (StringUtil.isEmpty(input)) {
			return defaultValue;
		}
		try {
			return Integer.valueOf(StringUtil.trim(input.toString()));
		} catch (Exception e) {
		}
		return defaultValue;
	}

	public static int[] getIntArray(String input, String regex) {
		if (StringUtil.isEmpty(input) || regex == null) {
			return null;
		}
		Integer[] rs = getIntegerArray(input, regex);
		return ArrayUtils.toPrimitive(rs);
	}

	public static Integer[] getIntegerArray(String input, String regex) {
		if (StringUtil.isEmpty(input) || regex == null) {
			return null;
		}
		Set<Integer> set = new LinkedHashSet<Integer>();
		String[] values = input.split(regex);
		for (String value : values) {
			Integer val = DataTypeUtil.getInteger(value);
			if (val == null || set.contains(val)) {
				continue;
			}
			set.add(val);
		}
		return set.toArray(new Integer[set.size()]);
	}

	public static Set<String> getStringSet(String input, String regex) {
		if (StringUtil.isEmpty(input) || regex == null) {
			return null;
		}
		Set<String> set = new LinkedHashSet<String>();
		String[] values = input.split(regex);
		for (String value : values) {
			if (StringUtil.isEmpty(value) || set.contains(value)) {
				continue;
			}
			set.add(value);
		}
		return set;
	}

	public static String[] getStringArray(String input, String regex) {
		if (StringUtil.isEmpty(input) || regex == null) {
			return null;
		}
		Set<String> set = new LinkedHashSet<String>();
		String[] values = input.split(regex);
		for (String value : values) {
			if (StringUtil.isEmpty(value) || set.contains(value)) {
				continue;
			}
			set.add(value);
		}
		return set.toArray(new String[set.size()]);
	}

	public static String getString(Object object) {
		return getString(object, null);
	}

	public static String getString(Object object, String defaultValue) {
		if (object == null) {
			return defaultValue;
		}
		try {
			return String.valueOf(object);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return defaultValue;
	}

	public static Long getLong(Object object) {
		return getLong(object, null);
	}

	public static Long getLong(Object object, Long defaultValue) {
		if (StringUtil.isEmpty(object)) {
			return defaultValue;
		}
		try {
			return Long.valueOf(StringUtil.trim(object.toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return defaultValue;
	}

	public static Float getFloat(String input) {
		if (StringUtil.isEmpty(input)) {
			return null;
		}
		try {
			return Float.valueOf(StringUtil.trim(input));
		} catch (Exception e) {
		}
		return null;
	}

	public static Double getDouble(String input) {
		if (StringUtil.isEmpty(input)) {
			return null;
		}
		try {
			return Double.valueOf(StringUtil.trim(input));
		} catch (Exception e) {
		}
		return null;
	}

	public static Double getDouble(Object input) {
		if (input == null) {
			return null;
		}
		return getDouble(input.toString());
	}

	public static Boolean getBoolean(String input) {
		if (StringUtil.isEmpty(input)) {
			return null;
		}
		try {
			return Boolean.valueOf(StringUtil.trim(input));
		} catch (Exception e) {
		}
		return null;
	}

	public static Date getDate(String input) {
		return getDate(input, "");
	}

	public static String format(Date date) {
		return format(date, null);
	}

	public static String format(Date date, DateFormat format) {
		if (date == null) {
			return null;
		}
		if (format == null) {
			format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		}
		try {
			return format.format(date);
		} catch (Exception e) {
		}
		return null;
	}

	public static Date getDate(String input, DateFormat format) {
		if (StringUtil.isEmpty(input)) {
			return null;
		}
		if (format == null) {
			format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		}
		try {
			return format.parse(StringUtil.trim(input));
		} catch (ParseException e) {
		}
		return null;
	}

	public static Date getDate(String input, String dateTimePattern) {
		if (StringUtil.isEmpty(input)) {
			return null;
		}
		DateFormat format = null;
		if (!StringUtil.isEmpty(dateTimePattern)) {
			format = new SimpleDateFormat(dateTimePattern);
		}
		return getDate(input, format);
	}

	public static Date getDate(String input, String[] dateTimePatterns) {
		if (StringUtil.isEmpty(input) || dateTimePatterns == null || dateTimePatterns.length == 0) {
			return null;
		}
		SimpleDateFormat format = new SimpleDateFormat();
		for (String dateTimePattern : dateTimePatterns) {
			if (StringUtil.isEmpty(dateTimePattern)) {
				continue;
			}
			format.applyPattern(dateTimePattern);
			try {
				return format.parse(StringUtil.trim(input));
			} catch (ParseException e) {
			}
		}
		return null;
	}

}
