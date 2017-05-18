package com.develop.model.pvadder;

import java.util.LinkedList;
import java.util.List;

/**
 * 使用PvAdderUtils类提供的四种用法 实现灵活强大的PV飙升
 * 
 * @author 胡慧超
 */
public class PvAdder {

	// 启动线程数量
	private static final int threadNumber = 1;
	// 定义网页访问量的数量
	private static final int target = 1000;
	// 当错误数达到指定的数errorCount后线程会随机停止[0-errorCount)秒
	private static final int errorCount = 10;

	public static void main(String[] args) throws Exception {
		Thread t = new Thread() {
			@Override
			public void run() {
				List<String> blogIds = new LinkedList<String>();
				blogIds.add("69942920");
				PvAdderUtils.pvAddForCSDNBlogForSingleUserMultiPage("txj8859_1", blogIds, target, errorCount);
			}
		};
		t.setName("thread-" + t);
		t.start();
		
		Thread t2 = new Thread() {
			@Override
			public void run() {
				List<String> blogIds = new LinkedList<String>();
				blogIds.add("61192042");
				PvAdderUtils.pvAddForCSDNBlogForSingleUserMultiPage("txj8859_1", blogIds, target, errorCount);
			}
		};
		t2.setName("thread-" + t2);
		t2.start();
		
		Thread t3 = new Thread() {
			@Override
			public void run() {
				List<String> blogIds = new LinkedList<String>();
				blogIds.add("56840359");
				PvAdderUtils.pvAddForCSDNBlogForSingleUserMultiPage("txj8859_1", blogIds, target, errorCount);
			}
		};
		t3.setName("thread-" + t3);
		t3.start();

		Thread t1 = new Thread() {
			@Override
			public void run() {
				List<String> blogIds = new LinkedList<String>();
				blogIds.add("68059984");
				PvAdderUtils.pvAddForCSDNBlogForSingleUserMultiPage("hu948162999", blogIds, target, errorCount);
			}
		};
		t1.setName("thread-" + t1);
		t1.start();
	}
}