package com.BC.androidtool.HttpThread;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Looper;
import android.util.Log;

/**
 * 
 * <p>
 * 下载图片类
 * </p>
 * 
 * @author 吴旺春
 * @version 1.0.0
 * @modify
 * @see
 * @copyright Huamai GuangZhou www.skyarm.com
 */
public class ImageDownloader extends DownloadFile {
	private Bitmap imgBitmap;

	public ImageDownloader(String url, String dstPath,
			DownloadListener listener, Looper looper) {
		super(url, dstPath, listener, looper);
	}

	
	/**
	 * @return the image
	 */
	public Bitmap getImage(int inSampleSize) {
		if ( imgBitmap == null) {
			BitmapFactory.Options options = null;
			try {
				options = new BitmapFactory.Options();
				options.inSampleSize = inSampleSize;
				imgBitmap = BitmapFactory.decodeFile(getDstPath(), options);
			} catch (RuntimeException e) {
				Log.e("onFling", "AsynImageDown:DownloadFinish" + e.toString());
			} catch (java.lang.Error e) {
				Log.e("onFling", "AsynImageDown:DownloadFinish" + e.toString());
			} catch (Exception e) {
				Log.e("onFling", "AsynImageDown:DownloadFinish" + e.toString());
			} finally {
				if (options != null)
					options = null;
			}
		}
		return imgBitmap;
	}

}
