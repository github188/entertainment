package com.BC.androidtool.HttpThread;

import java.util.Vector;
/**
 * 
 * <p>线程池类 </p>
 * @author 吴王春
 * @version 1.0.0
 * @modify
 * @see
 * @copyright Huamai GuangZhou  www.skyarm.com
 */
public class WorkerGroup {
	private Vector<BaseTask> tasks = new Vector<BaseTask>();
	private int workCount;
	private int currentWorkNum=0;

	public WorkerGroup(int workNum) {
		workCount=workNum;
	}
	/**
	 * 添加需要执行的任务
	 * @param aTask	任务的对象
	 */
	public void addTask(BaseTask aTask) {
		synchronized (tasks) {
			tasks.add(aTask);
			if(currentWorkNum<workCount) {
				currentWorkNum++;
				CWorker worker=new CWorker(this);
				worker.startWork();
			} 
		}
	}
	/**
	 * 获取任务，如果任务列表不为空，返回任务列表中的第一个任务
	 * @return
	 */
	public BaseTask getNextTask(){
		synchronized (tasks) {
			if(tasks.isEmpty()){
				currentWorkNum--;
				currentWorkNum=currentWorkNum<0?0:currentWorkNum;
				return null;
			}else{
				BaseTask task=tasks.elementAt(0);
				tasks.remove(0);
				return task;
			}
		}
	}
	public void removeTask(BaseTask task){
		synchronized (tasks) {
			tasks.remove(task);
		}
	}

}
