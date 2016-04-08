package com.BC.entertainmentgravitation.utl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.util.Base64;

import com.BC.androidtool.JSON.JsonRequest;
import com.BC.entertainmentgravitation.HttpThread.SimpleHttpTask;

public class HttpUtil {
	public static SimpleHttpTask packagingHttpTask(
			HashMap<String, String> value, int taskType, boolean isUsePostType) {
		List<NameValuePair> params = JsonRequest.requestForNameValuePair(value);
		SimpleHttpTask httpTask = new SimpleHttpTask(taskType, null, null);
		httpTask.params = params;
		httpTask.setRequestType(isUsePostType);
		return httpTask;
	}
	
	public static SimpleHttpTask packagingHttpTask(
			HashMap<String, String> value, int taskType) {
		List<NameValuePair> params = JsonRequest.requestForNameValuePair(value);
		SimpleHttpTask httpTask = new SimpleHttpTask(taskType, null, null);
		httpTask.params = params;
		httpTask.setRequestType(false);
		return httpTask;
	}

	public static SimpleHttpTask packagingHttpTask2(
			ArrayList<NameValuePair> arrayList, int taskType) {
		List<NameValuePair> params = arrayList;
		SimpleHttpTask httpTask = new SimpleHttpTask(taskType, null, null);
		httpTask.params = params;
		httpTask.setRequestType(false);
		return httpTask;
	}

	public static SimpleHttpTask packagingHttpTask(Object value, int taskType) {
		HashMap<String, String> hashMap = object2HashMap(value);
		return packagingHttpTask(hashMap, taskType);
	}

	public static HashMap<String, String> object2HashMap(Object value) {
		HashMap<String, String> hashMap = new HashMap<String, String>();
		String jsonString = JsonRequest.getJSONObject(value);
		JSONObject object;
		try {
			object = new JSONObject(jsonString);
			hashMap = JsonObject2HashMap(object);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hashMap;
	}

	public static HashMap<String, String> JsonObject2HashMap(JSONObject jo) {
		HashMap<String, String> hashMap = new HashMap<String, String>();
		for (Iterator<String> keys = jo.keys(); keys.hasNext();) {
			try {
				String key1 = keys.next();
				// System.out.println("key1---" + key1 + "------" + jo.get(key1)
				// + (jo.get(key1) instanceof JSONObject) + jo.get(key1)
				// + (jo.get(key1) instanceof JSONArray));
				hashMap.put(key1, jo.getString(key1));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return hashMap;
	}

	public static String getBtye64String(Bitmap bitmapOrg) {
		ByteArrayOutputStream bao = new ByteArrayOutputStream();

		bitmapOrg.compress(Bitmap.CompressFormat.PNG, 90, bao);

		byte[] ba = bao.toByteArray();

		String ba1 = Base64.encodeToString(ba, Base64.NO_WRAP);
		return ba1;
	}

	public static String getBtye64String(File bitmapOrg) {
		FileInputStream inputFile = null;
		String ba1 = "";
		try {
			inputFile = new FileInputStream(bitmapOrg);

			byte[] buffer = new byte[(int) bitmapOrg.length()];
			inputFile.read(buffer);
			inputFile.close();
			ba1 = Base64.encodeToString(buffer, Base64.NO_WRAP);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if (inputFile != null) {
				try {
					inputFile.close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

		return ba1;
	}

}
