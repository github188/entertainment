package com.BC.androidtool.HttpThread;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.NameValuePair;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.BC.androidtool.config.Config;

public class BaseSimpleHttpTask extends HttpBaseTask {

	public int currencyKind = 0;
	public List<NameValuePair> params;

	public BaseSimpleHttpTask(int taskType, String content, String url) {
		super(taskType, content, url);
		// System.out.println(content);
		isUsePostType = true;
	}

	@Override
	public void dealwithData(InputStream in) {
		if (!validateInternet(Config.maincontext)) {
			result.put("message", "没有连接网络");
			result.put("errorCode", -2);
			errorCode = -2;
			return;
		}
		if (in == null) {
			result.put("message", "服务器返回错误");
			result.put("errorCode", -1);
			errorCode = -1;
			return;
		}
		String jsonString = readJsonData(in);
		if (jsonString == null) {
			result.put("message", "服务器返回参数错误");
			result.put("errorCode", -1);
			errorCode = -1;
			return;
		}
		// new JsonAnalytic().baseJsonStatus(jsonString, result);
		result.put("taskType", taskType);
		result.put("content", jsonString);
		

	}

	/**
	 * 读取数据
	 * 
	 * @param in
	 * @return
	 */
	public String readJsonData(InputStream in) {
		StringBuffer returnBuffer = new StringBuffer();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					in, "utf-8"));
			String line = "";
			while ((line = reader.readLine()) != null) {
				returnBuffer.append(line);
			}
			this.errorCode = 0;
			reader.close();
		} catch (Exception e) {
			this.errorCode = -1;
			e.printStackTrace();
		}
		Log.e("simplehttptask", "rep:" + returnBuffer.toString());
		return returnBuffer.toString();
	}

	public boolean validateInternet(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(context.CONNECTIVITY_SERVICE);
		if (manager == null) {
			return false;
		} else {
			NetworkInfo[] info = manager.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
