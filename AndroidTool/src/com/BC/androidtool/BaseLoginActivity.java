package com.BC.androidtool;

import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.BC.androidtool.HttpThread.InfoSource;
import com.BC.androidtool.HttpThread.SimpleHttpTask;
import com.BC.androidtool.HttpThread.WorkerManager;
import com.BC.androidtool.JSON.JsonRequest;
import com.BC.androidtool.config.Config;
import com.BC.androidtool.utils.Md5;
import com.BC.androidtool.utils.ValidateUtil;

public abstract class BaseLoginActivity extends BaseActivity {
	public Button loginButton, registerButton, retrievePassword;
	public EditText nameText, verificationodeEdit;
	public CheckBox box;

	public String url = "";

	public boolean canMD5 = false;

	/**
	 * 初始化基本登录功能
	 * 
	 * @param loginButtonID
	 *            登录按钮ID
	 * @param registerButtonID
	 *            注册按钮
	 * @param retrievePasswordID
	 *            记住密码
	 * @param nameTextID
	 *            用户名
	 * @param verificationodeEditID
	 *            密码
	 * @param boxID
	 *            记住密码单选框
	 */
	public void initLogin(int loginButtonID, int registerButtonID,
			int retrievePasswordID, int nameTextID, int verificationodeEditID,
			int boxID, String url) {
		this.url = url;
		box = (CheckBox) findViewById(boxID);
		loginButton = (Button) findViewById(loginButtonID);
		registerButton = (Button) findViewById(registerButtonID);
		retrievePassword = (Button) findViewById(retrievePasswordID);
		nameText = (EditText) findViewById(nameTextID);
		verificationodeEdit = (EditText) findViewById(verificationodeEditID);

		if (Config.getPhoneNum() != null) {
			nameText.setText(Config.getPhoneNum());
		}
		if (Config.getPassword() != null) {
			verificationodeEdit.setText(Config.getPassword());
		}

		loginButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// && validateInternet()
				if (checkData()) {
					showProgressDialog("正在为您登陆");
					loging();

				}
			}
		});
		registerButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				register();
			}
		});
		retrievePassword.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				retrievePassword();
			}
		});
	}

	/**
	 * 忘记密码
	 */
	public abstract void retrievePassword();

	/**
	 * 注册
	 */
	public abstract void register();

	public void loging() {
		final String name = nameText.getText().toString();
		Config.setPhoneNum(name);
		Config.saveConfig();
		final String psw = verificationodeEdit.getText().toString();
		String pswMD5 = null;
		if (canMD5) {
			pswMD5 = Md5.getInstance().getMd5(psw);
		}

		HashMap<String, String> value = new HashMap<String, String>();
		value.put("name", name);

		if (pswMD5 != null && !pswMD5.equals("")) {
			value.put("passWord", pswMD5);
		} else {
			value.put("passWord", psw);
		}
		value.put("pos", InfoSource.POS + "");

		SimpleHttpTask httpTask = packagingHttpTask(value,
				InfoSource.LOGIN_TYPE);
		httpTask.setUrl(url);
		showProgressDialog("正在为您登录中...");
		WorkerManager.addTask(httpTask, this);

		if (box.isChecked()) {
			Config.setPassword(psw);
			Config.saveConfig();
		}
	}

	public static SimpleHttpTask packagingHttpTask(
			HashMap<String, String> value, int taskType) {
		List<NameValuePair> params = JsonRequest.requestForNameValuePair(value);
		SimpleHttpTask httpTask = new SimpleHttpTask(taskType, null, null);
		httpTask.params = params;
		httpTask.setRequestType(false);
		return httpTask;
	}

	private boolean checkData() {
		boolean checked = false;
		checked = (!ValidateUtil.isEmpty(nameText, "登录名") && !ValidateUtil
				.isEmpty(verificationodeEdit, "密码"));
		return checked;
	}

}
