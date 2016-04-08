package com.BC.androidtool.HttpThread;

import java.util.HashMap;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;


/**
 * 
 * <p> 消息传递者类，线程之间传递消息时需要用Handler对象作为传递的桥梁</p>
 * @author 吴旺春
 * @version 1.0.0
 * @modify
 * @see
 * @copyright Huamai GuangZhou  www.skyarm.com
 */
public class InfoHandler extends Handler{
	public interface InfoReceiver {
		void onInfoReceived(int errcode, HashMap<String, Object> items);

		void onNotifyText(String notify);
	}
	protected InfoReceiver receiver=null;
	public static final int STATE_RESULT_RECEIVED=1;
	public static final int STATE_INFO_NOTIFY=2;
	public InfoHandler(InfoReceiver receiver){
		super(Looper.getMainLooper());
		this.receiver=receiver;
	}
	/**
	 * 重写Handler的handleMessage函数，
	 * 本类的对象调用sendMessage函数时将被调用
	 * Message的类型这里有两种：state_result_received表示结果已经返回，
	 * 在线程中，线程做完事情后返回结果给接收者时使用；
	 * state_info_notify注意是在线程运行的过程中发某些通知消息给接收者
	 */
	@Override
	public void handleMessage(Message msg) {
		
		super.handleMessage(msg);
		if(receiver!=null){
			if(msg.what==STATE_RESULT_RECEIVED){
				receiver.onInfoReceived(msg.arg1,
						(HashMap<String,Object>)msg.obj);
			}else if(msg.what==STATE_INFO_NOTIFY){
				receiver.onNotifyText((String)msg.obj);
			}
			
		}
	}
	
}