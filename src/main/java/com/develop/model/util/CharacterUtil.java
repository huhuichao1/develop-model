package com.develop.model.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

/**
 * 字符处理类
 * 
 * @author huhuichao
 *
 */
public class CharacterUtil {

	/**
	 * 全角转半角
	 * 
	 * 1.全角空格为12288，半角空格为32
	 * 
	 * 2.其他字符半角(33-126)与全角(65281-65374)的对应关系是：相差65248
	 * 
	 * @param c
	 * @return
	 */
	public static char full2half(char c) {
		if (c == 12288) {// 全角空格
			c = 32;
		} else if (c > 65280 && c < 65375) {// 其他全角字符
			c = (char) (c - 65248);
		}
		return c;
	}

	public static char toLowerCase(char ch) {
		if (ch >= 65 && ch <= 90) {// A-Z
			ch += 32;
		}
		return ch;
	}

	public static String getPinyin(String input) {
		if (StringUtil.isEmpty(input)) {
			return null;
		}
		try {
			StringBuffer builder = new StringBuffer();
			char[] chars = input.toCharArray();
			HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
			format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
			format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
			format.setVCharType(HanyuPinyinVCharType.WITH_V);
			for (char ch : chars) {
				ch = full2half(ch);// 全角转半角
				ch = toLowerCase(ch);// 字母转小写
				if ((ch >= 97 && ch <= 122) || (ch >= 48 && ch <= 57)) {// a-z || 0-9
					builder.append(ch);
				} else {
					String[] result = PinyinHelper.toHanyuPinyinStringArray(ch, format);
					if (result != null && result.length > 0) {
						builder.append(result[0]);
					}
				}
			}
			return builder.toString();
		} catch (Exception e) {
			LogUtil.ROOT.error("", e);
		}
		return null;
	}

	public static String getJianpin(String input) {
		if (StringUtil.isEmpty(input)) {
			return null;
		}
		try {
			StringBuffer builder = new StringBuffer();
			char[] chars = input.toCharArray();
			HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
			format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
			format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
			format.setVCharType(HanyuPinyinVCharType.WITH_V);
			for (char ch : chars) {
				ch = full2half(ch);// 全角转半角
				ch = toLowerCase(ch);// 字母转小写
				if ((ch >= 97 && ch <= 122) || (ch >= 48 && ch <= 57)) {// a-z || 0-9
					builder.append(ch);
				} else {
					String[] result = PinyinHelper.toHanyuPinyinStringArray(ch, format);
					if (result != null && result.length > 0) {
						String string = result[0];
						if (string != null && string.length() > 0) {
							builder.append(string.charAt(0));
						}
					}
				}
			}
			return builder.toString();
		} catch (Exception e) {
			LogUtil.ROOT.error("", e);
		}
		return null;
	}

	public static void main(String[] args) {
		// System.out.println(full2half('０'));
		// System.out.println(full2half('９'));
		// System.out.println(full2half('ａ'));
		// System.out.println(full2half('ｚ'));
		// System.out.println(full2half('Ａ'));
		// System.out.println(full2half('Ｚ'));
		// getPinyin("间");

		String input = "wObIdfas奔馳X5Ｚi:ｘ３";
		System.out.println(getPinyin(input));
		System.out.println(getJianpin(input));
		System.out.println(PinYinUtil.getFullSpell(input));
		System.out.println(PinYinUtil.getFirstSpell(input));

		long start = System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			// getPinyin(input);
			// PinYinUtil.getFullSpell(input);
			PinYinUtil.getFirstSpell(input);
		}
		System.out.println("=====" + (System.currentTimeMillis() - start));

		start = System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			// getPinyin(input);
			getJianpin(input);
			// PinYinUtil.getFullSpell(input);
			// PinYinUtil.getFirstSpell(input);
		}
		System.out.println("=====" + (System.currentTimeMillis() - start));

	}
}
