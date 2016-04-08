package com.BC.entertainmentgravitation.IM;

import com.BC.entertainmentgravitation.HttpThread.DemoDataConstance;
import com.BC.entertainmentgravitation.HttpThread.SDKCoreHelper;
import com.BC.entertainmentgravitation.activity.MainActivity;
import com.yuntongxun.kitsdk.ECDeviceKit;

import android.content.Context;
import android.os.AsyncTask;

public class LoginIM extends AsyncTask {
	Context context;

	public LoginIM(Context context) {
		super();
		this.context = context;
	}

	@Override
	protected Object doInBackground(Object... params) {
		// TODO Auto-generated method stub
		login();
		return null;
	}

	private void login() {
		// TODO Auto-generated method stub
		// initIMSDK();
		if (MainActivity.user != null) {
			DemoDataConstance.USERID = MainActivity.user.getClientID();
			ECDeviceKit.init(DemoDataConstance.USERID,
					context.getApplicationContext(), SDKCoreHelper.getInstance());// 初始化kit
																					// sdk
			CustomUIAndActionManager.initCustomUI();
		}
		
	}
}
