package com.BC.androidtool.HttpThread;

import com.BC.androidtool.HttpThread.InfoHandler.InfoReceiver;


/**
 * 
 * <p>线程管理类</p>
 * @author 吴旺春
 * @version 1.0.0
 * @modify
 * @see
 * @copyright Huamai GuangZhou  www.skyarm.com
 */
public class WorkerManager {
	private static int taskFlag = 0;
	private static WorkerGroup gWorkerGroup;
	public static synchronized void openWorkerManager(int workerNum) {
		if(gWorkerGroup==null){
			gWorkerGroup = new WorkerGroup(workerNum);
		}
	}
	
	public static void addTask(BaseTask aTask) {
		if(gWorkerGroup==null){
			openWorkerManager(3);
		}
		gWorkerGroup.addTask(aTask);
	}
	
	public static void removeTask(BaseTask task){
		if(gWorkerGroup==null){
			return;
		}
		gWorkerGroup.removeTask(task);
	}
	
	
	/**
	 * 添加任务
	 * 
	 * @param task
	 * @param receiver
	 * @return
	 */
	static synchronized public int addTask(HttpBaseTask task,
			InfoReceiver receiver) {
		InfoHandler handler = new InfoHandler(receiver);
		task.infoHandler = handler;
		WorkerManager.addTask(task);
		return taskFlag;
	}
}
