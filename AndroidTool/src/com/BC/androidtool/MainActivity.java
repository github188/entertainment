package com.BC.androidtool;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

	TextView tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv = (TextView) findViewById(R.id.tv);
		// loging();

	}

	// public void loging() {
	// String name = "zzzxzj";
	// HashMap<String, String> value = new HashMap<String, String>();
	// value.put("j_username", name + "#####ssbx#####undefined#####0");
	// value.put("j_password", "123456");
	// List<NameValuePair> params = JsonRequest.requestForNameValuePair(value);
	// SimpleHttpTask httpTask = new SimpleHttpTask(InfoSource.LOGIN_TYPE,
	// null, null);
	// httpTask.params = params;
	// httpTask.setRequestType(false);
	// WorkerManager.addTask(httpTask, new InfoReceiver() {
	//
	// @Override
	// public void onNotifyText(String notify) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void onInfoReceived(int errcode,
	// HashMap<String, Object> items) {
	// // TODO Auto-generated method stub
	//
	// if (errcode == 0) {
	// Log.d("errcode", "errcode = " + errcode);
	// lodData();
	// }
	//
	// }
	// });
	// }
	//
	// private void lodData() {
	// HashMap<String, String> value = new HashMap<String, String>();
	// value.put("j_username", "zzzxzj");
	// value.put("pos", InfoSource.POS + "");
	// value.put("session", "2015");
	// value.put("stationName", "");
	// value.put("stationCode", "");
	// List<NameValuePair> params = JsonRequest.requestForNameValuePair(value);
	// SimpleHttpTask httpTask = new SimpleHttpTask(InfoSource.EXPENSECONTROL,
	// null, null);
	// httpTask.params = params;
	// httpTask.setRequestType(false);
	// WorkerManager.addTask(httpTask, new InfoReceiver() {
	//
	// @Override
	// public void onNotifyText(String notify) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void onInfoReceived(int errcode,
	// HashMap<String, Object> items) {
	// // TODO Auto-generated method stub
	// if (errcode == 0) {
	// Log.d("errcode", "errcode = " + errcode);
	// Log.d("shuzhi", "json = " + items.get("content"));
	// if (items.get("content") != null
	// && !"".equals(items.get("content"))) {
	// BaseEntity<ArrayList<Data>> entity = BaseEntity.parse(
	// items.get("content").toString(),
	// new TypeToken<BaseEntity<ArrayList<Data>>>() {
	// }.getType());
	//
	// Log.d("shuzhi", "entity = " + entity.getMessage());
	//
	// }
	// }
	// }
	// });
	// }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
