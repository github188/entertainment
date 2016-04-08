package com.BC.androidtool.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;

/**
 * json请求
 * 
 * @author shuzhi
 * 
 */
public class JsonRequest {

	/**
	 * 通过HashMap转换成Post参数
	 * 
	 * @author shuzhi
	 * 
	 */
	public static List<NameValuePair> requestForNameValuePair(
			HashMap<String, String> valueMap) {
		// TODO Auto-generated method stub
		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			for (Map.Entry<String, String> entry : valueMap.entrySet()) {
				params.add(new BasicNameValuePair(entry.getKey(), entry
						.getValue()));
			}
			return params;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	/**
	 * 根据Object转换json String
	 * 
	 * @return
	 */
	public static String getJSONObject(Object object) {

		String jsonSting = "";
		try {
			Gson gson = new Gson();
			jsonSting = gson.toJson(object);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonSting;
	}

	// /**
	// * 返回配置好的json对象
	// *
	// * @return
	// */
	// public static JSONObject getJSONObject() {
	//
	// try {
	// JSONObject obj = new JSONObject();
	// obj.put("pos", InfoSource.POS);
	// return obj;
	// } catch (JSONException e) {
	// e.printStackTrace();
	// }
	// return null;
	// }
}
