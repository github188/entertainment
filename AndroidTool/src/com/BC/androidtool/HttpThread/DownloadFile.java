package com.BC.androidtool.HttpThread;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.BC.androidtool.utils.Utils;

/**
 * 
 * <p>下载文件类</p>
 * @author 吴王春
 * @version 1.0.0
 * @modify
 * @see
 * @copyright Huamai GuangZhou  www.skyarm.com
 */
public class DownloadFile extends BaseTask{
	private DownloadFileNotifyHandler handler;
	private String url;
	private String dstPath;
	private int flag;
	private static int gFlag = 1;
	private boolean isReplace = true;
	public DownloadFile(String url, String dstPath,DownloadListener listener,Looper looper) {
		this.dstPath = dstPath;
		this.url = url;
		synchronized (DownloadFile.class) {
			this.flag = gFlag++;
		}
		init(listener,looper);
	}

	public interface DownloadListener {
		void onFinished(int errcode, int flag,DownloadFile df);
	}
	public class DownloadFileNotifyHandler extends Handler{
		private DownloadListener mListener;
		public DownloadFileNotifyHandler(Looper looper,DownloadListener listener){
			super(looper);
			this.mListener = listener;
		}
		@Override
		public void handleMessage(Message msg) {
			
			super.handleMessage(msg);
			if(mListener!=null){
				mListener.onFinished(msg.arg1, msg.arg2, (DownloadFile) msg.obj);
			}
		}
	}
	private void init(DownloadListener listener,Looper looper){
		if(listener!=null){
			this.handler=new DownloadFileNotifyHandler(looper,listener);
		}
	}

	@Override
	public void call() {
		
		try {
			int errcode = downloadFile(url, dstPath);
			downloadFinish(errcode);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	protected void downloadFinish(int errcode) {
		
	
		if(handler!=null){
			handler.sendMessage(handler.obtainMessage(0, errcode, getFlag(), this));
		}
	}
	public boolean isReplace() {
		return isReplace;
	}

	public int downloadFile(String url, String filePath) {
		if (Utils.isFileExists(filePath)) {
			try {
				if (!isReplace()) {
					return 0;
				}
				File file = new File(filePath);
				file.delete();
				file = new File(filePath + "temp");
				file.delete();
			} catch (Exception e) {
				
				e.printStackTrace();
			} 
		}
		int errcode = -1;
		InputStream in = null;
		File fileOut = null;
		FileOutputStream out = null;
		try {
			in = HttpBase.getInputStream(url);
			fileOut = new File(filePath + "temp");
			out = new FileOutputStream(fileOut);
			byte[] bytes = new byte[1024];
			int c;
			while ((c = in.read(bytes)) != -1) {
				out.write(bytes, 0, c);
			}
			fileOut.renameTo(new File(filePath));
			errcode = 0;
		} catch (RuntimeException e) {
			Log.e("onFling", "DownloadFileBase:downloadFile" + e.toString());
			errcode = -1;
		} catch (java.lang.Error e) {
			Log.e("onFling", "DownloadFileBase:downloadFile" + e.toString());
			errcode = -1;
		} catch (Exception e) {
			Log.e("onFling", "DownloadFileBase:downloadFile" + e.toString());
			errcode = -1;
		} finally {
			try {
				if (fileOut != null)
					fileOut = null;
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (Exception e) {
				errcode = -1;
				e.printStackTrace();
			}
		}
		return errcode;
	}

	/**
	 * @return the srcPath
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @return the dstPath
	 */
	public String getDstPath() {
		return dstPath;
	}
	public int getFlag() {
		return flag;
	}

	@Override
	public boolean isCancel() {
		
		return false;
	}

	@Override
	public void cancelTask() {
		
		
	}

}
