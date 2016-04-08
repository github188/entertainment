package com.BC.androidtool.HttpThread;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.BC.androidtool.R;
import com.BC.androidtool.HttpThread.DownloadFile.DownloadListener;
import com.BC.androidtool.config.Config;
import com.BC.androidtool.utils.Utils;

/**
 * 
 * <p>
 * 下载图片管理类
 * </p>
 * 
 * @author 吴王春
 * @version 1.0.0
 * @modify
 * @see
 * @copyright Huamai GuangZhou www.skyarm.com
 */
public class AsynImageManager implements ImageProvider,
		DownloadFile.DownloadListener {
	public interface DownloadFinish {
		/**
		 * 在图片下载完成后，图片还未添加给View时调用该接口
		 * 
		 * @param v
		 * @param b
		 */
		public void onFinish(View v, BitmapDrawable b);
	}

	public DownloadFinish downloadFinish;
	private static AsynImageManager gAsynImageManager;
	public HashMap<View, Integer> inSampleSizeMap = new HashMap<View, Integer>();
	protected HashMap<String, ArrayList<View>> downloadTasks = new HashMap<String, ArrayList<View>>();

	private static WorkerGroup gImageWorkerGroup;

	public static void addTask(BaseTask aTask) {
		if (gImageWorkerGroup == null) {
			synchronized (AsynImageManager.class) {
				if (gImageWorkerGroup == null) {
					gImageWorkerGroup = new WorkerGroup(3);
				}
			}
		}
		gImageWorkerGroup.addTask(aTask);
	}

	public AsynImageManager() {

	}

	public void setDownloadFinish(DownloadFinish downloadFinish) {
		this.downloadFinish = downloadFinish;
	}

	public static AsynImageManager getInstance() {
		if (gAsynImageManager == null) {
			synchronized (AsynImageManager.class) {
				if (gAsynImageManager == null) {
					gAsynImageManager = new AsynImageManager();
				}
			}
		}
		return gAsynImageManager;
	}

	@Override
	public int showImage(String url, View imgView, int inSampleSize) {

		return showImage(url, imgView, null, inSampleSize);

	}

	/**
	 * 将图片设置为圆角
	 */
	public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), android.graphics.Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

	@Override
	public void onFinished(int errcode, int flag, DownloadFile df) {

		try {
			View view;
			ArrayList<View> array = downloadTasks.get(df.getUrl());
			if (array != null) {
				downloadTasks.remove(df.getUrl());
				String url;
				for (int i = 0; i < array.size(); i++) {
					view = array.get(i);
					if (errcode != 0) {
						inSampleSizeMap.remove(view);
						continue;
					}
					url = (String) view.getTag(R.id.image_url_key);
					if (!df.getUrl().equals(url)) {
						inSampleSizeMap.remove(view);
						continue;
					}
					try {

						Integer inSampleSize = inSampleSizeMap.get(view);
						if (inSampleSize == null) {
							inSampleSize = new Integer(1);
						}
						inSampleSizeMap.remove(view);
						Bitmap img = ((ImageDownloader) df)
								.getImage(inSampleSize);
						if (img != null && img.getHeight() > 0
								&& img.getWidth() > 0) {
							BitmapDrawable d = new BitmapDrawable(img);
							if (d != null) {
								if (downloadFinish != null) {
									downloadFinish.onFinish(view, d);
								} else {
									view.setBackgroundDrawable(d);
								}
							}
						}

					} catch (Exception e) {

						e.printStackTrace();
					}
				}
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public String getDstPath(String url) {
		String dst = Config.getCachePath();
		int dstlen = 0;
		int pos = 0;
		String filename = "";
		while (dstlen < 45 && pos < url.length() - 1) {
			pos++;
			char b = url.charAt(url.length() - pos);
			if (mid(b, '0', '9') || mid(b, 'a', 'z') || mid(b, 'A', 'Z')) {
				filename += b;
				dstlen++;
			}

		}
		return dst + "/sa" + filename.toString();
	}

	/*
	 * public String getDstPath(String url) { String dst =
	 * Config.getCachePath(); String filename=Utils.md5Encoding(url); return dst
	 * + "/sa" + filename; }
	 */

	private boolean mid(char a, char min, char max) {
		if (min <= a && a <= max) {
			return true;
		} else {
			return false;
		}
	}

	protected int startDownload(String url, String dstPath,
			DownloadListener listener, Looper looper) {
		ImageDownloader df = new ImageDownloader(url, dstPath, listener, looper);
		AsynImageManager.addTask(df);
		return df.getFlag();
	}

	/**
	 * 显示图片
	 * 
	 * @param url
	 *            图片的地址
	 * @param imgView
	 *            使用该图片作为背景的View
	 * @param defaultDrawable
	 *            默认背景
	 * @param inSampleSize
	 *            缩放比例 1是不进行任何改变，占用内存比较大，越多占用内存越小
	 */
	@Override
	public int showImage(String url, View imgView,
			BitmapDrawable defaultDrawable, int inSampleSize) {
		// 将view的下载地址保存是Tag里
		imgView.setTag(R.id.image_url_key, url);
		if (url == null || "".equals(url)) {
			if (defaultDrawable != null) {
				imgView.setBackgroundDrawable(defaultDrawable);
			}
			return 0;
		}

		if (url != null && !"".equals(url)) {
			if (Utils.isFileExists(getDstPath(url))) {
				BitmapFactory.Options options = null;
				try {
					options = new BitmapFactory.Options();
					options.inSampleSize = inSampleSize;
					Bitmap bitmap = BitmapFactory.decodeFile(getDstPath(url),
							options);
					if (bitmap != null && bitmap.getHeight() > 0
							&& bitmap.getWidth() > 0) {
						imgView.setBackgroundDrawable(new BitmapDrawable(bitmap));
						return 1;
					}
					bitmap.recycle();
				} catch (Exception e) {
					Log.e("onFling", "ImageShowOnlyImageView" + e.toString());
				}

			}
		}

		if (defaultDrawable != null) {
			imgView.setBackgroundDrawable(defaultDrawable);
		}

		ArrayList<View> array = downloadTasks.get(url);
		if (array != null) {
			array.remove(imgView);
			array.add(imgView);
			inSampleSizeMap.put(imgView, inSampleSize);
			return 0;
		} else {
			array = new ArrayList<View>();
			array.add(imgView);
			downloadTasks.put(url, array);
			inSampleSizeMap.put(imgView, inSampleSize);
		}
		startDownload(url, getDstPath(url), this, Looper.getMainLooper());

		return 0;

	}

	@Override
	public int showImage(String url, View imgView, int defaultResId,
			int inSampleSize) {
		// 将view的下载地址保存是Tag里
		imgView.setTag(R.id.image_url_key, url);
		if (url == null || "".equals(url)) {

			imgView.setBackgroundResource(defaultResId);

			return 0;
		}

		if (url != null && !"".equals(url)) {
			if (Utils.isFileExists(getDstPath(url))) {
				BitmapFactory.Options options = null;
				try {
					options = new BitmapFactory.Options();
					options.inSampleSize = inSampleSize;
					Bitmap bitmap = BitmapFactory.decodeFile(getDstPath(url),
							options);
					if (bitmap != null && bitmap.getHeight() > 0
							&& bitmap.getWidth() > 0) {
						imgView.setBackgroundDrawable(new BitmapDrawable(bitmap));
						return 1;
					}
				} catch (Exception e) {
					Log.e("onFling", "ImageShowOnlyImageView" + e.toString());
				}

			}
		}

		imgView.setBackgroundResource(defaultResId);

		ArrayList<View> array = downloadTasks.get(url);
		if (array != null) {
			array.remove(imgView);
			array.add(imgView);
			inSampleSizeMap.put(imgView, inSampleSize);
			return 0;
		} else {
			array = new ArrayList<View>();
			array.add(imgView);
			downloadTasks.put(url, array);
			inSampleSizeMap.put(imgView, inSampleSize);
		}
		startDownload(url, getDstPath(url), this, Looper.getMainLooper());

		return 0;
	}

	public void onDestroy() {
		try {
			inSampleSizeMap.clear();
			downloadTasks.clear();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 当不需要接收下载返回事件时用，为了释放资源最好在Activity的onDestroy()函数中调用一下
	 */
	public void removeAllTask() {
		try {
			downloadTasks.clear();
			inSampleSizeMap.clear();
		} catch (Exception e) {

		}
	}

	public static void removeAllFiles() {
		try {
			String dst = Config.getCachePath();
			File f = new File(dst);
			File[] files = f.listFiles();
			if (files != null && files.length > 0) {
				for (int i = 0; i < files.length; ++i) {
					files[i].delete();
				}
			}
		} catch (Exception e) {

		}
	}

	@Override
	public int showImage(String url, ImageView imgView,
			BitmapDrawable defaultDrawable, int inSampleSize) {
		// TODO Auto-generated method stub

		imgView.setTag(R.id.image_url_key, url);
		if (url == null || "".equals(url)) {
			if (defaultDrawable != null) {
				imgView.setImageBitmap(defaultDrawable.getBitmap());
			}
			return 0;
		}

		if (url != null && !"".equals(url)) {
			if (Utils.isFileExists(getDstPath(url))) {
				BitmapFactory.Options options = null;
				try {
					options = new BitmapFactory.Options();
					options.inSampleSize = inSampleSize;
					Bitmap bitmap = BitmapFactory.decodeFile(getDstPath(url),
							options);
					if (bitmap != null && bitmap.getHeight() > 0
							&& bitmap.getWidth() > 0) {
						imgView.setImageBitmap(bitmap);
						return 1;
					}
				} catch (Exception e) {
					Log.e("onFling", "ImageShowOnlyImageView" + e.toString());
				}

			}
		}

		if (defaultDrawable != null) {
			imgView.setImageBitmap(defaultDrawable.getBitmap());
		}

		ArrayList<View> array = downloadTasks.get(url);
		if (array != null) {
			array.remove(imgView);
			array.add(imgView);
			inSampleSizeMap.put(imgView, inSampleSize);
			return 0;
		} else {
			array = new ArrayList<View>();
			array.add(imgView);
			downloadTasks.put(url, array);
			inSampleSizeMap.put(imgView, inSampleSize);
		}
		startDownload(url, getDstPath(url), this, Looper.getMainLooper());

		return 0;
	}

}
