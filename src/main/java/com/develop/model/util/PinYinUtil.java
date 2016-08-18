package com.develop.model.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

/**
 * 
 * @author herry
 *
 */
public class PinYinUtil {

	/**
	 * 获取汉字串拼音首字母，英文字符不变，如果是多音字只取其中一个
	 * 
	 * @param chinese
	 *            汉字串
	 * @return 汉语拼音首字母
	 */
	public static String getFirstSpell(String chinese) {
		if (chinese == null) {
			return "";
		}
		StringBuffer pybf = new StringBuffer();
		char[] arr = chinese.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] > 128) {
				try {
					String[] result = PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat);
					if (result != null && result.length > 0) {
						pybf.append(result[0].charAt(0));
					} else {
						pybf.append(arr[i]);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				pybf.append(arr[i]);
			}
		}
		return pybf.toString().replaceAll("\\W", "").trim();
	}

	/**
	 * 获取汉字串拼音，英文字符不变，如果是多音字只取其中一个
	 * 
	 * @param chinese
	 *            汉字串
	 * @return 汉语拼音
	 */
	public static String getFullSpell(String chinese) {
		if (chinese == null) {
			return "";
		}
		StringBuffer pybf = new StringBuffer();
		char[] arr = chinese.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] > 128) {
				try {
					String[] result = PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat);
					if (result != null && result.length > 0) {
						pybf.append(result[0]);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				pybf.append(arr[i]);
			}
		}
		return pybf.toString();
	}

	/**
	 * 获取 汉字串的简拼，多音字返回多个，英文字符不变
	 * 
	 * @param chinese
	 *            汉字串
	 * @return 汉语简拼
	 */
	public static List<String> getAllFristSpell(String chinese) {
		List<String> list = new ArrayList<String>();
		if (chinese == null) {
			return list;
		}
		char[] arr = chinese.toCharArray();
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] > 128) {
				try {
					Set<String> set = getAllPinyin(arr[i], false);
					list = meger(list, set);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				Set<String> set = new HashSet<String>();
				set.add(Character.toString(arr[i]));
				list = meger(list, set);
			}
		}
		return list;
	}

	/**
	 * 获取 汉字串的拼音，多音字返回多个，英文字符不变
	 * 
	 * @param chinese
	 *            汉字串
	 * @return 汉语拼音
	 */
	public static List<String> getAllFullSpell(String chinese) {
		List<String> list = new ArrayList<String>();
		if (chinese == null) {
			return list;
		}
		char[] arr = chinese.toCharArray();
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] > 128) {
				try {
					Set<String> set = getAllPinyin(arr[i], true);
					list = meger(list, set);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				Set<String> set = new HashSet<String>();
				set.add(Character.toString(arr[i]));
				list = meger(list, set);
			}
		}
		return list;
	}

	/**
	 * 把set里面的数据合并到list中
	 * 
	 * @param list
	 * @param set
	 */
	private static List<String> meger(List<String> list, Set<String> set) {
		if (set == null || set.size() == 0) {
			return list;
		}
		List<String> result = new ArrayList<String>();
		if (list.size() == 0) {
			for (String str : set) {
				result.add(str);
			}
		} else {
			for (String str1 : list) {
				for (String str2 : set) {
					StringBuffer sb = new StringBuffer(str1);
					sb.append(str2);
					result.add(sb.toString());
				}
			}
		}
		return result;
	}

	/**
	 * 获得字符 的所有拼音
	 * 
	 * @param c
	 * @param flag
	 *            ，标记是全拼还是简拼，true是全拼
	 * @return
	 */
	private static Set<String> getAllPinyin(char c, boolean flag) {
		Set<String> set = new HashSet<String>();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
		try {
			String[] result = PinyinHelper.toHanyuPinyinStringArray(c, defaultFormat);
			if (result != null && result.length > 0) {
				for (int i = 0; i < result.length; i++) {
					if (flag) {
						set.add(result[i]);
					} else {
						set.add(Character.toString(result[i].charAt(0)));
					}

				}
			} else {
				set.add(Character.toString(c));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return set;
	}

	public static void main(String[] args) {
		// for (int i = -258; i < 300; i++) {
		// System.out.println(i + "." + (char) i);
		// }
		// System.out.println((int) '　');

		System.out.println(getFullSpell("a@好8"));

		System.out.println((char) 7656);

		System.out.println("打发a1".replaceAll("\\W", ""));
	}
}
