package com.BC.entertainmentgravitation.wxapi.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class WxMessageReceiver {
	
	private BroadcastReceiver mMessageReceiver;
	
	public BroadcastReceiver GetBroadcastReceiver()
	{
		return mMessageReceiver;
	}
	
	public WxMessageReceiver(Context context)
	{

	}

}
