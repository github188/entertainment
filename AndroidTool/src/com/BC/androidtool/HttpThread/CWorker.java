/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.BC.androidtool.HttpThread;

/**
 * 
 * <p> 线程类</p>
 * @author 吴王春
 * @version 1.0.0
 * @modify
 * @see
 * @copyright Huamai GuangZhou  www.skyarm.com
 */
public class CWorker implements Runnable// extends Thread
{
	private Thread selfThread;
	private WorkerGroup workerManager;

	public CWorker(WorkerGroup workerManager) {
		selfThread = new Thread(this);
		this.workerManager = workerManager;
	}

	/**
	 * 开始执行线程
	 */
	public void startWork() {
		selfThread.start();
	}
	/**
	 * 线程执行主体
	 */
	@Override
	public void run() {
		boolean con = true;
		while (con) {
			BaseTask task = workerManager.getNextTask();
			if(task==null){
				con=false;
				break;
			}
			try {
				//如果任务取消，将不执行任务的主体工作
				if(task.isCancel()){
					continue;
				}
				task.call();
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
	}
}
