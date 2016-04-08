package com.BC.androidtool.HttpThread;


/**
 * 任务的实体类
* @ClassName: BaseTask 
* @Description: TODO
* @author dengxuxing
* @date 2013-1-19 下午12:12:58 
*
 */

public abstract class BaseTask implements  TaskInterface{
	protected String message;	//执行任务中产生的信息
	protected int errorCode=-1;  //错误码 ，0为正确
	protected boolean isCancel=false;
	
	public BaseTask() {
	}
	
	//任务是否取消
	public boolean isCancel() {
		
		return isCancel;
	}
	
	//取消任务
	public void cancelTask() {
		
		isCancel=true;
	}
	
}