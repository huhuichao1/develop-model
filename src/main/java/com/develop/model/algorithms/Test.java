package com.develop.model.algorithms;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {

	/**
	 * Math.pow(0.85, Math.log1p(Math.max((double) (System.currentTimeMillis()-_doc.pubTime.value) / 86400000, 0)))
	 * @param ms
	 * @param str
	 */
	public static void testtime(Long ms,String str) {
		Double d = 0.85;
		double day = (System.currentTimeMillis() - ms) / 86400000d;
		System.out.println("当前时间："+str+" 的数据时间戳："+ms+" ====== "+Math.pow(d, Math.log1p(Math.max(day, 0))) + ", 距离当前："+  day + " 天" );
	};

	public static void testdistance() {
		Double d1 = 0.98d;
		System.out.println(Math.pow(d1, 0) + "           0");
		System.out.println(Math.pow(d1, 0.5) + "         0.5");
		System.out.println(Math.pow(d1, 3) + "        3");
		System.out.println(Math.pow(d1, 5) + "        5");
		System.out.println(Math.pow(d1, 10) + "       10");
		System.out.println(Math.pow(d1, 30) + "       30");
		System.out.println(Math.pow(d1, 50) + "       50");
		System.out.println(Math.pow(d1, 100) + "       100");
		System.out.println(Math.pow(d1, 2000d) + "       200");
	}



	public static void main(String[] args) throws ParseException {
		// int[] i={1,2,5,7,10,30,80,100,500,1000};
		// int[] i={-1,0,1,2,3,4,5,6,7,8,9,10,11};
		// for(int s:i){
		//
		// double brandHot = brandHot(s);
		// System.out.println(brandHot);
		// }
		System.out.println(convert(1472745600000l));
		System.out.println(convertTime("201609221240"));
		String s="as";
		String s1=s.replace("a", "1");
		System.out.println(s+","+s1);
//		testdistance();
	}

	
	public static void testTime(){
		String [] i={"201608221240","201608211240","20160820240","201608181240","201608151240","201608011240",
				"201607021240","201602221240","201508221240","201108221240"};
		for(String is:i){
			Long s;
			try {
				s = convertTime(is);
				testtime(s,is);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * 品牌热度
	 * 
	 * @param s
	 * @return
	 */
	public static double brandHot(double s) {
		double brandHot = 0.3;
		if (s < 10 && s > 1) {
			brandHot = Math.log10(s);
		} else if (s >= 10) {
			brandHot = 1;
		}
		;
		return brandHot;
	}
	public static String convert(long mill) {
		Date date = new Date(mill);
		String strs = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			strs = sdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strs;
	}
	
	/**
	 * 把时间转换成时间戳
	 * @param str
	 * @return
	 * @throws ParseException
	 */
	public static long convertTime(String str ) throws ParseException{
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmm");
		  
		  return sdf.parse(str).getTime();//毫秒
	}
}
