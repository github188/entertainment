package com.BC.entertainmentgravitation.IM;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.BC.entertainmentgravitation.R;
import com.BC.entertainmentgravitation.activity.MainActivity;
import com.yuntongxun.kitsdk.core.ECKitConstant;
import com.yuntongxun.kitsdk.ui.ECChattingActivity;

public class IMBroadcastReceiver extends BroadcastReceiver {

	@SuppressWarnings("deprecation")
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String message = intent.getStringExtra("message");
		String id = intent.getStringExtra("id");
		MainActivity.broadcast(message, id);
		// String ns = Context.NOTIFICATION_SERVICE;
		// NotificationManager mNotificationManager = (NotificationManager)
		// context
		// .getSystemService(ns);
		// // 定义通知栏展现的内容信息
		// int icon = R.drawable.app_logo;
		// CharSequence tickerText = "我的通知栏标题";
		// long when = System.currentTimeMillis();
		// Notification notification = new Notification(icon, tickerText, when);
		//
		// // 定义下拉通知栏时要展现的内容信息
		// CharSequence contentTitle = "海绵娱";
		// CharSequence contentText = message;
		// Intent notificationIntent = new Intent(context,
		// ECChattingActivity.class);
		// notificationIntent.putExtra(ECKitConstant.KIT_CONVERSATION_TARGET,
		// id);
		// notificationIntent.putExtra(ECChattingActivity.CONTACT_USER,
		// "海绵娱用户");
		// PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
		// notificationIntent, 0);
		// notification.setLatestEventInfo(context, contentTitle, contentText,
		// contentIntent);
		//
		// // 用mNotificationManager的notify方法通知用户生成标题栏消息通知
		// mNotificationManager.notify(1, notification);
	}

}
