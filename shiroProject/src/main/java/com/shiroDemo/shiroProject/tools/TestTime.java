package com.shiroDemo.shiroProject.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.shiroDemo.shiroProject.exceptions.MyException;
import com.shiroDemo.shiroProject.util.DateUtil;

public class TestTime {

	public static final String YYYY = "yyyy";

	public static final String YYYYMM = "yyyy-MM";

	public static final String YYYYMMDD = "yyyy-MM-dd";

	public static final String YYYYMMDDHH = "yyyy-MM-dd HH";

	public static final String YYYYMMDDHHMM = "yyyy-MM-dd HH:mm";

	public static final String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";

	public static String formatDuring(long mss) {
		long days = mss / (1000 * 60 * 60 * 24);
		long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
		long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
		long seconds = (mss % (1000 * 60)) / 1000;
		return days + "days" + hours + "hours" + minutes + "minutes" + seconds + "seconds";
	}

	public static void main(String[] args) {
		System.out.println(DateUtil.dateDiff("2018-12-23 10:14:00", "2018-12-23 21:37:00", YYYYMMDDHHMMSS, "m"));
//		System.err.println(TestTime.dateDiff("2018-12-21 10:14:00", "2018-12-23 21:37:00", YYYYMMDDHHMMSS, "m"));
	}

	public void timeTest() {

		String startTime = "2018-12-21 10:14:00";
		String endTime = "2018-12-23 21:37:00";

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d1 = null;
		Date d2 = null;
		try {
			d1 = df.parse(startTime);
			d2 = df.parse(endTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println((((d2.getTime() - d1.getTime()) / (24 * 60 * 60 * 1000))) / 365 + "年");
		System.out.println((d2.getTime() - d1.getTime()) / (24 * 60 * 60 * 1000) + "天");

		System.out.println((((d2.getTime() - d1.getTime()) / (60 * 60 * 1000)) % 24) + "小时");
		System.out.println((((d2.getTime() - d1.getTime()) / (60 * 60 * 1000)) % 24) * 60
				+ (((d2.getTime() - d1.getTime()) / 1000) % 60) + "分钟");

		System.err.println((d2.getTime()-d1.getTime())/(1000*60));
	}

	/*
	 * 时间工具测试
	 */
	public static Long dateDiff(String startTime, String endTime, String format, String str) {
		// 按照传入的格式生成一个simpledateformate对象
		SimpleDateFormat sd = new SimpleDateFormat(format);
		long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
		long nh = 1000 * 60 * 60;// 一小时的毫秒数
		long nm = 1000 * 60;// 一分钟的毫秒数
		long ns = 1000;// 一秒钟的毫秒数
		long diff;
		long day = 0;
		long hour = 0;
		long min = 0;
		long sec = 0;
		// 最终动态返回参数
		long dynamicTime = 0;
		// 获得两个时间的毫秒时间差异
		try {
			System.out.println(sd.parse(endTime).getTime()+"-"+sd.parse(startTime).getTime());
			diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
			day = diff / nd;// 计算差多少天
			hour = diff % nd / nh + day * 24;// 计算差多少小时
			min = diff % nd % nh / nm + day * 24 * 60;// 计算差多少分钟
			sec = diff % nd % nh % nm / ns;// 计算差多少秒
			// 输出结果
			System.out.println(
					"时间相差：" + day + "天" + (hour - day * 24) + "小时" + (min - day * 24 * 60) + "分钟" + sec + "秒。");
			System.out.println("hour=" + hour + ",min=" + min + "(时间间隔：" + (hour * 60 + min) + ")");
			if (str.equalsIgnoreCase("d")) {
				System.err.println("间隔天数");
				dynamicTime = day;
				return dynamicTime;
			} else if (str.equalsIgnoreCase("h")) {
				System.err.println("间隔小时数");
				dynamicTime = hour;
				return dynamicTime;
			} else if (str.equalsIgnoreCase("m")) {
				System.err.println("间隔分钟数");
				dynamicTime = diff/(1000*60);
				return dynamicTime;
			} else{
				
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dynamicTime;

	}
}
