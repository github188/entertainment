package com.BC.androidtool.utils;

import java.text.DecimalFormat;

public class StringUtil {
	/**
	 * 处理空字符串
	 * 
	 * @param str
	 * @return String
	 */
	public static String doEmpty(String str) {
		return doEmpty(str, "");
	}

	/**
	 * 处理空字符串
	 * 
	 * @param str
	 * @param defaultValue
	 * @return String
	 */
	public static String doEmpty(String str, String defaultValue) {
		if (str == null || str.equalsIgnoreCase("null")
				|| str.trim().equals("") || str.trim().equals("－请选择－")) {
			str = defaultValue;
		} else if (str.startsWith("null")) {
			str = str.substring(4, str.length());
		}
		return str.trim();
	}

	/**
	 * 请选择
	 */
	final static String PLEASE_SELECT = "请选择...";

	public static boolean notEmpty(Object o) {
		return o != null && !"".equals(o.toString().trim())
				&& !"null".equalsIgnoreCase(o.toString().trim())
				&& !"undefined".equalsIgnoreCase(o.toString().trim())
				&& !PLEASE_SELECT.equals(o.toString().trim());
	}

	public static boolean empty(Object o) {
		return o == null || "".equals(o.toString().trim())
				|| "null".equalsIgnoreCase(o.toString().trim())
				|| "undefined".equalsIgnoreCase(o.toString().trim())
				|| PLEASE_SELECT.equals(o.toString().trim());
	}

	public static boolean num(Object o) {
		int n = 0;
		try {
			n = Integer.parseInt(o.toString().trim());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		if (n > 0) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean decimal(Object o) {
		double n = 0;
		try {
			n = Double.parseDouble(o.toString().trim());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		if (n > 0.0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 给JID返回用户名
	 * 
	 * @param Jid
	 * @return
	 */
	public static String getUserNameByJid(String Jid) {
		if (empty(Jid)) {
			return null;
		}
		if (!Jid.contains("@")) {
			return Jid;
		}
		return Jid.split("@")[0];
	}

	/**
	 * 给用户名返回JID
	 * 
	 * @param jidFor
	 *            域名//如ahic.com.cn
	 * @param userName
	 * @return
	 */
	public static String getJidByName(String userName, String jidFor) {
		if (empty(jidFor) || empty(jidFor)) {
			return null;
		}
		return userName + "@" + jidFor;
	}

	/**
	 * 给用户名返回JID,使用默认域名ahic.com.cn
	 * 
	 * @param userName
	 * @return
	 */
	public static String getJidByName(String userName) {
		String jidFor = "ahic.com.cn";
		return getJidByName(userName, jidFor);
	}

	/**
	 * 根据给定的时间字符串，返回月 日 时 分 秒
	 * 
	 * @param allDate
	 *            like "yyyy-MM-dd hh:mm:ss SSS"
	 * @return
	 */
	public static String getMonthTomTime(String allDate) {
		return allDate.substring(5, 19);
	}

	/**
	 * 根据给定的时间字符串，返回月 日 时 分 月到分钟
	 * 
	 * @param allDate
	 *            like "yyyy-MM-dd hh:mm:ss SSS"
	 * @return
	 */
	public static String getMonthTime(String allDate) {
		return allDate.substring(5, 16);
	}

	public static String getCity(String s) {
		String[] strings = s.split("-");
		String city = strings[0];

		if (city.indexOf("市") <= 0 && strings.length > 1) {
			city = strings[1];
		}

		return city;
	}

	public static String getSounty(String s) {
		String[] strings = s.split("-");
		String sounty = s;
		if (strings.length > 1) {
			sounty = strings[strings.length - 2] + strings[strings.length - 1];
		}
		if (sounty.indexOf("市") == sounty.length() - 1) {
			sounty = "";
		}
		if (sounty.indexOf("市辖区") >= 0 && sounty.length() > 3) {
			sounty = sounty.substring(sounty.indexOf("市辖区") + 3);
		}
		if (sounty.indexOf("（") >= 0) {
			sounty = sounty.substring(0, sounty.indexOf("（"));
		}
		return sounty;
	}

	/**
	 * 百度地图距离转换
	 * 
	 * @param distance
	 * @return
	 */
	public static String distanceFormatter(int distance) {
		if (distance < 1000) {
			return distance + "米";
		} else if (distance % 1000 == 0) {
			return distance / 1000 + "公里";
		} else {
			DecimalFormat df = new DecimalFormat("0.0");
			int a1 = distance / 1000; // 十位

			double a2 = distance % 1000;
			double a3 = a2 / 1000; // 得到个位

			String result = df.format(a3);
			double total = Double.parseDouble(result) + a1;
			return total + "公里";
		}
	}

	/**
	 * 百度地图时间转换
	 * 
	 * @param minute
	 * @return
	 */
	public static String timeFormatter(int minute) {
		if (minute < 3600) {
			return minute / 60 + "分钟";
		} else if (minute % 3600 == 0) {
			return minute / 3600 + "小时";
		} else {
			int hour = minute / 3600;
			int minute1 = (minute % 3600) / 60;
			return hour + "小时" + minute1 + "分钟";
		}

	}
}
