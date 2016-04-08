package com.BC.entertainmentgravitation.HttpThread;

import android.content.Context;
import android.content.Intent;

import com.yuntongxun.ecsdk.ECDevice;
import com.yuntongxun.ecsdk.ECDevice.ECConnectState;
import com.yuntongxun.ecsdk.ECError;
import com.yuntongxun.ecsdk.ECInitParams;
import com.yuntongxun.ecsdk.ECInitParams.LoginAuthType;
import com.yuntongxun.ecsdk.ECInitParams.LoginMode;
import com.yuntongxun.ecsdk.ECNotifyOptions;
import com.yuntongxun.kitsdk.ECDeviceKit;
import com.yuntongxun.kitsdk.beans.ECAuthParameters;
import com.yuntongxun.kitsdk.listener.OnConnectSDKListener;
import com.yuntongxun.kitsdk.listener.OnInitSDKListener;
import com.yuntongxun.kitsdk.listener.OnLogoutSDKListener;
import com.yuntongxun.kitsdk.utils.LogUtil;

public class SDKCoreHelper implements OnInitSDKListener, OnConnectSDKListener,
		OnLogoutSDKListener {

	private static final String TAG = "SDKCoreHelper";
	public static final String ACTION_LOGOUT = "com.yuntongxun.ECDemo_logout";
	public static final String ACTION_SDK_CONNECT = "com.yuntongxun.Intent_Action_SDK_CONNECT";
	public static final String ACTION_KICK_OFF = "com.yuntongxun.Intent_ACTION_KICK_OFF";
	private static SDKCoreHelper sInstance;
	private Context mContext;
	private ECDevice.ECConnectState mConnect = ECDevice.ECConnectState.CONNECT_FAILED;
	private ECInitParams mInitParams;
	private ECInitParams.LoginMode mMode = ECInitParams.LoginMode.FORCE_LOGIN;
	private boolean mKickOff = false;
	private ECNotifyOptions mOptions;

	private SDKCoreHelper() {

	}

	public static SDKCoreHelper getInstance() {
		if (sInstance == null) {
			sInstance = new SDKCoreHelper();
		}
		return sInstance;
	}

	public static boolean isKickOff() {
		return getInstance().mKickOff;
	}

	@Override
	public void onInitialized() {
		LogUtil.d(TAG, "ECSDK is ready");

		ECAuthParameters parameters = new ECAuthParameters();
		parameters.setUserId(DemoDataConstance.USERID);
		parameters.setAppKey(DemoDataConstance.APPKEY);
		parameters.setAppToken(DemoDataConstance.APPTOKEN);

		parameters.setLoginMode(LoginMode.AUTO);
		parameters.setLoginType(LoginAuthType.NORMAL_AUTH);

		ECDeviceKit.login(parameters, getInstance());

	}

	/**
	 * 当前SDK注册状态
	 * 
	 * @return
	 */
	public static ECDevice.ECConnectState getConnectState() {
		return getInstance().mConnect;
	}
	@Override
	public void onLogout() {
		getInstance().mConnect = ECDevice.ECConnectState.CONNECT_FAILED;
		if (mInitParams != null && mInitParams.getInitParams() != null) {
			mInitParams.getInitParams().clear();
		}
		mInitParams = null;
		mContext.sendBroadcast(new Intent(ACTION_LOGOUT));
	}

	@Override
	public void onError(Exception exception) {
		LogUtil.e(TAG,
				"ECSDK couldn't start: " + exception.getLocalizedMessage());
		ECDevice.unInitial();
	}

	@Override
	public void onConnect() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDisconnect(ECError error) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onConnectState(ECConnectState state, ECError error) {
		// TODO Auto-generated method stub

	}

}
