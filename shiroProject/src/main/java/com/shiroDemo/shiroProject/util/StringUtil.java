package com.shiroDemo.shiroProject.util;

import java.io.File;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 针对String字符串的验证以及操作.
 *
 * @author (作者) hechuanfei 2019-2-15 上午10:35:00
 * @version (版本) V1.0
 * @since (该版本支持的JDK版本) ： 1.8
 */
public class StringUtil {

	/**
	 * @param obj
	 * @return 如果obj为空或者为''返回true，否则返回false
	 * @Description 判断对象是否为空
	 * @author zengshan-c
	 * @date 2015年9月8日 上午10:37:44
	 */
	public static boolean isEmpty(Object obj) {
		if (obj == null) {
			return true;
		}
		// 字符串类型
		else if (obj instanceof String) {
			return obj.toString().trim().length() <= 0 ? true : false;
		}
		// list类型
		else if (obj instanceof List<?>) {
			return ((List<?>) obj).size() <= 0 ? true : false;
		}
		// 数组类型
		else if (obj.getClass().isArray()) {
			return Array.getLength(obj) <= 0 ? true : false;
		}
		// 集合类型
		else if (obj instanceof Collection) {
			return ((Collection<?>) obj).size() <= 0 ? true : false;
		}
		// map类型
		else if (obj instanceof Map) {
			return ((Map<?, ?>) obj).size() <= 0 ? true : false;
		}
		// 文件类型
		else if (obj instanceof File) {
			return ((File) obj).exists();
		}
		return false;
	}

	/**
	 * @param obj
	 * @return 如果obj不为空并且不为''返回true，否则返回false
	 * @Description 判断对象是否不为空
	 * @author mengLei
	 * @date 2015-2-4 上午11:58:51
	 */
	public static boolean isNotEmpty(Object obj) {
		return !isEmpty(obj);
	}

	/**
	 * @param obj
	 * @return 如果字符串为null、''或者"null"时返回true，否则返回false
	 * @Description 判断字符串是否为null或者''或者为"null"
	 * @author zengshan-c
	 * @date 2015年9月2日 下午5:20:37
	 */
	public static boolean isNullStr(String obj) {
		if (isEmpty(obj) || "null".equals(obj.trim())) {
			return true;
		}
		return false;
	}

	/**
	 * @param obj
	 * @return
	 * @Description 判断字符串是否不为空并且不为"null"
	 * @author zengshan-c
	 * @date 2015年9月8日 上午10:40:51
	 */
	public static boolean isNotStrNull(String obj) {
		if (isNotEmpty(obj) && !"null".equals(obj.trim())) {
			return true;
		}
		return false;
	}

	/**
	 * @param str
	 * @return
	 * @Description 首字母小写
	 * @author zengshan-c
	 * @date 2015年9月8日 上午10:53:58
	 */
	public static String firstLetter2Lower(String str) {
		if (isNotEmpty(str)) {
			return str.substring(0, 1).toLowerCase() + str.substring(1);
		}
		return str;
	}

	/**
	 * @param str
	 * @return
	 * @Description 首字母大写
	 * @author zengshan-c
	 * @date 2015年9月8日 上午10:53:58
	 */
	public static String firstLetter2UpperCase(String str) {
		if (isNotEmpty(str)) {
			return str.substring(0, 1).toUpperCase() + str.substring(1);
		}
		return str;
	}

	/**
	 * @param str
	 *            指定字符串
	 * @param length
	 *            自定的字符串长度
	 * @param symbol
	 *            补填的字符串
	 * @return
	 * @Description 自动在str后补充(length-str.length())个symbol<br>
	 *              比如：str="11" , length = 5 , symbol="0" , 最后返回 : 11000
	 * @author zengshan-c
	 * @date 2015年9月8日 上午10:57:03
	 */
	public static String appendCharacterForLast(String str, int length, String symbol) {
		int strLen = str.length();
		if (strLen < length) {
			while (strLen < length) {
				StringBuffer sb = new StringBuffer();
				sb.append(str).append(symbol);// 左补0
				str = sb.toString();
				strLen = str.length();
			}
		}
		return str;
	}

	/**
	 * @param isFormt
	 *            是否需要格式化。<br>
	 *            此处格式化指：是否将uuid中的"-"去掉（true:格式化,false:不需要格式化）
	 * @return
	 * @Description 获取uuid
	 * @author zengshan-c
	 * @date 2015年9月8日 上午11:22:57
	 */
	public static String getUUID(boolean isFormt) {
		String uuid = UUID.randomUUID().toString();
		if (isFormt) {
			uuid = uuid.replaceAll("-", "");
		}
		return uuid;
	}

	/**
	 * @param str
	 *            : xx_xx
	 * @return
	 * @Description 首字母大写
	 * @author zengshan
	 * @date 2015年12月18日 下午5:09:30
	 */
	public static String initialtoUpper(String str) {
		if (null != str)
			return str.substring(0, 1).toUpperCase() + str.substring(1);
		else
			return str;
	}

	/**
	 * @param str
	 *            : xx_xx
	 * @return
	 * @Description 首字母大写
	 * @author zengshan
	 * @date 2015年12月18日 下午5:09:30
	 */
	public static String splitAndtoUpper(String str, String splitStr) {
		String[] temps = str.split(splitStr);
		str = temps[0];
		for (int i = 1; i < temps.length; i++) {
			str += initialtoUpper(temps[i]);
		}
		return str;
	}

	/**
	 * @param str
	 *            : xx_xx
	 * @return
	 * @Description 首字母大写
	 * @author zengshan
	 * @date 2015年12月18日 下午5:09:30
	 */
	public static String splitAndInitialtoUpper(String str, String splitStr) {
		str = splitAndtoUpper(str, splitStr);
		return initialtoUpper(str);
	}

	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	public static int getBlankNumber(String s, int index) {
		if (index < s.length()) {
			if (s.charAt(index) == ' ') {
				return getBlankNumber(s, index + 1) + 1;
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}

	public static String mergeBlank(String s) {
		int numberBlank = 0;
		String a1;// 字符串的第一部分
		String a2;// 字符串的第二部分
		for (int index = 0; index < s.length(); index++) {// 循环整个字符串，判断是否有连续空格
			numberBlank = getBlankNumber(s, index);
			if (numberBlank >= 2) {// 根据连续空格的个数以及当前的位置，截取字符串
				a1 = s.substring(0, index);
				a2 = s.substring(index + numberBlank - 1, s.length());
				s = a1 + a2;// 合并字符串
			}
		}
		return s;
	}

	public static String trim(String s) {
		if (s.charAt(0) == ' ') {
			s = s.substring(1, s.length());
		}
		if (s.charAt(s.length() - 1) == ' ') {
			s = s.substring(0, s.length() - 1);
		}
		return s;
	}

	public static String replaceBankByArray(String str) {
		StringBuilder sbl = new StringBuilder("");
		String text = str.replaceAll("\n", " ");
		text = text.replaceAll(" ", " ");
		String[] textArray = text.split(" ");
		for (String temp : textArray) {
			temp = (temp.trim()).trim();
			if (!temp.equals(" ") && !temp.equals(" ") && !temp.equals("") && temp != null) {
				sbl.append(temp.trim());
				sbl.append(" ");
			}
		}
		String string = (sbl.toString()).trim();
		Pattern p = Pattern.compile(" {2,}");// 去除多余空格
		Matcher m = p.matcher(string);
		String second = m.replaceAll(" ");
		return second;

	}

	public static boolean emailFormat(String email) {
		boolean tag = true;
		final String pattern1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		final Pattern pattern = Pattern.compile(pattern1);
		final Matcher mat = pattern.matcher(email);
		if (!mat.find()) {
			tag = false;
		}
		return tag;
	}

	public static void main(String[] args) {
		// System.out.println(StringUtil.replaceBlank("just do it!"));
		// String stringInfo = "Madam, I'm Adam";
		// System.out.println("待处理的字符串：" + stringInfo);
		//
		// Pattern p = Pattern.compile("[.,\"\\?!:']");// 增加对应的标点
		// Matcher m = p.matcher(stringInfo);
		//
		// String first = m.replaceAll(""); // 把英文标点符号替换成空，即去掉英文标点符号
		// System.out.println("去掉英文标点符号后的字符串：" + first);
		//
		// p = Pattern.compile(" {2,}");// 去除多余空格
		// m = p.matcher(first);
		// String second = m.replaceAll(" ");
		// System.out.println("去掉多余空格后的字符串" + second);// second为最终输出的字符串
		//
		// second = first.replace(" ", "");
		// System.out.println("去掉所有空格：" + second);
		System.out.println(emailFormat("123@qwq.com"));
	}

	public static Boolean isExcel(String fileName) {
		if (fileName != null && fileName != "") {
			// lastIndexOf如果没有搜索到则返回为-1
			if (fileName.lastIndexOf(".") != -1) {
				String fileType = (fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length())).toLowerCase();
				String[] suppotFile = new String[2];
				suppotFile[0] = "xlsx";
				suppotFile[1] = "xls";
				for (int i = 0; i < suppotFile.length; i++) {
					if (suppotFile[i].equals(fileType)) {
						return true;
					}
				}
				return false;
			} else {
				return false;
			}
		}
		return false;
	}
}
