package com.BC.androidtool.HttpThread;

import java.util.HashMap;

import com.BC.androidtool.HttpThread.InfoHandler.InfoReceiver;

public class BaseInfoSource {


	public interface ItemInfoReceiver {
		void onItemInfoReceived(int errcode, HashMap<String, Object> items,
				int flag);

		void onNotifyText(String notify);
	}

	/**
	 * 添加任务
	 * 
	 * @param task
	 * @param receiver
	 * @return
	 */
	static synchronized public void addTask(HttpBaseTask task,
			InfoReceiver receiver) {
		InfoHandler handler = new InfoHandler(receiver);
		task.infoHandler = handler;
		WorkerManager.addTask(task);
	}

}
