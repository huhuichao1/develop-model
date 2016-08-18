package com.develop.model.javase.object.collection;


/**
 * java.lang.String.compareTo() 方法比较两个字符串的字典。比较是基于字符串中的每个字符的Unicode值。此String对象表示的字符序列的参数字符串表示的字符序列进行比较字典.
其结果是负的整数，如果此String对象字典前面的参数字符串
其结果是一个正整数，如果此String对象字典如下的参数字符串
结果是零，如果两个字符串相等，CompareTo返回0时，equal（Object）方法将返回true。
 * @author hhc
 *
 */
public class CompareToString {

	public static void main(String[] args) {
		// num1<0,表示要比较的字符串,在当前字符串的后面
		int num1 = "AAA".compareTo("B");
		System.out.println(num1);
		// num2>0, 表示要比较的字符串,在当前字符串的前面
		int num2 = "ccc".compareTo("aa");
		System.out.println(num2);
		// num3 ==0 ,表示相等(两个字符串内容全部相等)
		// 字符串比较大小的时候,首先是比较首字母的(int)char大小
		// 如果首字母一样的,比较第2位字母的大小,还一样,第3位...
		// 以此类推,笔试中常考
		int num3 = "ddd".compareTo("ddd");
		System.out.println(num3);

	}

}
