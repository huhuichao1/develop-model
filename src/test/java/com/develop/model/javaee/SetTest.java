package com.develop.model.javaee;

import java.util.TreeSet;

import com.develop.model.algorithms.topk.TopData;

/**
 * treeset  有序
 * @author huhuichao
 *
 */
public class SetTest {
	public static void main(String[] args) {
		TreeSet<TopData> ts = new TreeSet<TopData>();
		TopData t1 = new TopData("a", 99);
		TopData t2 = new TopData("b", 11);
		TopData t3 = new TopData("c", 12);
		TopData t4 = new TopData("d", 110);
		ts.add(t1);
		ts.add(t2);
		ts.add(t3);
		ts.add(t4);
		System.out.println(ts);
	}
}
