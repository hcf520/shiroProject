package com.shiroDemo.shiroProject.tools;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestUtil {

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

	public static void main(String args[]) {
	String[] arr = {"aa","bb","cc","啊看见啊等会看见啊贷记卡山东快书打开了"};
	String[] arr1 = new String[4];

	 for (int i = 0; i < arr.length; i++) {
		 arr1[i] = arr[i];
		 System.out.println(arr1[i]);
		 
     }
	System.out.println(arr1.length);
	}
}
