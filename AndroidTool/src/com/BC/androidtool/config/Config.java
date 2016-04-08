package com.BC.androidtool.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.http.client.CookieStore;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

public class Config {
	public static String UTF_8_TYPE = "text/html;charset=UTF-8";
	public static final String KPassword = "password";
	public static final String KPhoneNum = "phonenum";
	
	public static Context maincontext;

	private static String cachePath = "";

	public static boolean hasloadConfig = false;
	public static CookieStore cookieStore = null;

	private static String fileDir = "";
	private static final String cfgname = "/sales.cfg";

	private static String password = "";
	private static String phoneNum = "";
	private static boolean isSavePsw = false;
	private static boolean isLogin = false;
	private static int state = 0;
	
	public static int getState() {
		return state;
	}

	public static void setState(int state) {
		Config.state = state;
	}

	public static boolean isLogin() {
		return isLogin;
	}

	public static void setLogin(boolean isLogin) {
		Config.isLogin = isLogin;
	}

	// Font path
	public static String fontPath = "fonts/lishu.ttf";
	// Loading Font Face
	public static Typeface tf = null;

	public static void setFileDir(String path) {

		fileDir = path;
	}

	public static void setCachePath(String path) {

		cachePath = path;
	}

	public static String getCachePath() {

		return cachePath;
	}

	public static String getFileDir() {

		return fileDir;
	}

	public static boolean isSavePsw() {
		return isSavePsw;
	}

	public static void setSavePsw(boolean isSavePsw) {
		Config.isSavePsw = isSavePsw;
		Config.saveConfig();
	}

	static public String getPhoneNum() {
		return phoneNum;
	}

	static public void setPhoneNum(String phoneNo) {
		phoneNum = phoneNo;
	}

	static public void setPassword(String passwordString) {
		password = passwordString;
	}

	public static void clearPassword() {
		password = "";
		saveConfig();
	}

	static public void saveUser(String phone, String pwd) {
		if (phone == null || pwd == null) {
			return;
		}
		phoneNum = phone;
		password = pwd;
		Config.saveConfig();
	}

	static public String getPassword() {
		return password;
	}

	/**
	 * 保存程序的配置
	 */
	public static void saveConfig() {
		Properties properties = new Properties();
		properties.setProperty(Config.KPhoneNum,
				String.valueOf(Config.phoneNum));
		properties.setProperty(Config.KPassword,
				String.valueOf(Config.password));
		properties.setProperty("cookieStore",
				String.valueOf(Config.cookieStore));
		properties.setProperty("isSavePsw", String.valueOf(Config.isSavePsw));
		FileOutputStream stream = null;
		String filename = Config.fileDir + Config.cfgname;
		try {

			File f = new File(filename);
			if (!f.exists()) {
				File dir = new File(Config.fileDir);
				dir.mkdirs();
				f.createNewFile();
			}
			stream = new FileOutputStream(f);
			properties.store(stream, "");
		} catch (FileNotFoundException e) {
			Log.e("onFling", "saveConfig" + e.toString());
		} catch (Exception e) {
			Log.e("onFling", "saveConfig" + e.toString());
		} finally {
			if (stream != null) {
				try {
					stream.close();
					stream = null;
				} catch (IOException e) {
					Log.e("onFling", "saveConfig" + e.toString());
				}
			}
		}
	}

	/**
	 * 加载配置文件
	 */
	public static void loadConfig() {
		hasloadConfig = true;
		FileInputStream stream = null;
		String filename = Config.fileDir + Config.cfgname;
		try {
			File file = new File(filename);
			if (!file.exists()) {
				file.createNewFile();
			}

			stream = new FileInputStream(filename);
			Properties properties = new Properties();
			properties.load(stream);
			Config.password = properties.getProperty(KPassword, "");
			Config.phoneNum = properties.getProperty(KPhoneNum, "");
			String w = properties.getProperty("isSavePsw", "false");
			Config.isSavePsw = Boolean.valueOf(w).booleanValue();
			// Config.cookieStore = (CookieStore) properties.get("cookieStore");
		} catch (FileNotFoundException e) {
			Log.e("onFling", e.toString());
		} catch (IOException e) {
			Log.e("onFling", e.toString());
		} finally {
			if (stream != null) {
				try {
					stream.close();
					stream = null;
				} catch (IOException e) {
					Log.e("onFling", e.getMessage());
				}
			}
		}

	}

}
