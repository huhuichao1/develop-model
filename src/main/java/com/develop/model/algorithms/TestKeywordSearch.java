package com.develop.model.algorithms;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestKeywordSearch {

	/**
	 * Math.pow(0.85, Math.log1p(Math.max((double) (System.currentTimeMillis()-_doc.pubTime.value) / 86400000, 0)))
	 * @param ms
	 * @param str
	 * 
	 * X７=0.85ln{1+（t1-t2）/86400000}
	 * X７——发布时间因子
	 * t1——当前时间
	 * t1——发布时间
	 * 86400000——24h*60分*60秒*1000毫秒
	 * 这里采用ln函数，1+能保证ln的取值为正数，当（t1-t2）/86400000较小时，ln函数会出现较大差异，当（t1-t2）/86400000较大时ln函数会出现较少差异，用ln函数充当对数函数的的x，能保证数据分布较为集中且较大。0.85可以调整，主要可以影响数据分布。
	 */
	public static void testtime(Long ms,String str) {
		Double d = 0.85;
		double day = (System.currentTimeMillis() - ms) / 86400000d;
		System.out.println("当前时间："+str+" 的数据时间戳："+ms+" ====== "+Math.pow(d, Math.log1p(Math.max(day, 0))) + ", 距离当前："+  day + " 天" );
	};

	/**
	 * X2=0.98K
	 * 注释：
	 * X2——距离因子
	 * 0.98——底数
	 *	K——distance距离
	 *	这里的底数0.98是人为定的，主要考虑到数据分布，可以根据数据分布和排序处理改变数值。
	 * 这里采用指数函数，主要是在k较小时，分数也能明显的区分，而k很大时，分数的区别也较小，这也是符合用户逻辑的，对于用户来说2公里和10公里区别较大，对用户的决策有较大影响。而300公里和330公里对于用户来说，其实区别是不大的。
	 */
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
	
	/**
	 * 计算对数
	 * @param value
	 * @param base
	 * @return
	 */
	public static  double log(double value, double base) {
	    return Math.log(value) / Math.log(base);
	 }
	
	/**
	 *  反正切函数*2/pi  归一
	 * @param value
	 * @return
	 */
	public static	double	arctan(double value){
		return Math.atan(value)*2/Math.PI;
	}

	/**
	 * 计算折扣力度，p1 原价，p2 现价
	 * @param p1
	 * @param p2
	 * @return
	 */
	public static double discountScore(double p1,double p2){
		return (p1-p2)/p1;
	}
	public static void main(String[] args) throws ParseException {
		// int[] i={1,2,5,7,10,30,80,100,500,1000};
		// int[] i={-1,0,1,2,3,4,5,6,7,8,9,10,11};
		// for(int s:i){
		//
		// double brandHot = brandHot(s);
		// System.out.println(brandHot);
		// }
//		System.out.println(convert(1472745600000l));
//		System.out.println(convertTime("201609221240"));
//		String s="as";
//		String s1=s.replace("a", "1");
//		System.out.println(s+","+s1);
//		testdistance();
//		System.out.println(log(1024, 2));
//		System.out.println(Math.log10(1000000000000000d));
//		System.out.println(arctan(1));
		System.out.println(discountScore(10,1));
	}
}
