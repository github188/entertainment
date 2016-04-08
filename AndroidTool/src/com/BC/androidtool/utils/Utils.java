/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.BC.androidtool.utils;

/**
 *
 * @author Administrator
 */
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.view.View;

import com.BC.androidtool.R;

public class Utils {
	public static double KDoubleZero = 0.00009;

	/**
	 * 获取用户手机中是否安装有可以用于分享文字的的短信，QQ，微信，人人，腾讯微博，新浪微博，QQ空间等
	 * 有的软件则添加到Config类中作为临时变量储存
	 * 
	 */
	public static void getShareApps(Context context) {
		try {
			// if (Config.targetedShareNames == null) {
			// Config.targetedShareNames = new ArrayList<String>();
			// }
			// if (Config.targetedShareIntents == null) {
			// Config.targetedShareIntents = new ArrayList<Intent>();
			// }
			//
			// if (Config.targetedShareIntents.size() > 0
			// && Config.targetedShareIntents.size() ==
			// Config.targetedShareNames
			// .size()) {
			// return;
			// } else {
			// Config.targetedShareIntents.clear();
			// Config.targetedShareNames.clear();
			// }

			Intent it = new Intent(Intent.ACTION_SEND);
			it.setType("text/plain");
			PackageManager manager = context.getPackageManager();
			List<ResolveInfo> resInfo = manager.queryIntentActivities(it, 0);
			if (resInfo != null && !resInfo.isEmpty()) {
				for (ResolveInfo info : resInfo) {
					Intent targeted = new Intent(Intent.ACTION_SEND);
					targeted.setType("text/plain");
					ActivityInfo activityInfo = info.activityInfo;

					// if (activityInfo.packageName.equals("cn.cmcc.t")) {
					// targeted.setPackage(activityInfo.packageName);
					// Config.targetedShareIntents.add(targeted);
					// Config.targetedShareNames.add("移动微博");
					// }
					// if (activityInfo.packageName.equals("com.sina.weibo")) {
					// targeted.setPackage(activityInfo.packageName);
					// Config.targetedShareIntents.add(targeted);
					// Config.targetedShareNames.add("新浪微博");
					// }
					// if (activityInfo.packageName.equals("com.tencent.WBlog"))
					// {
					// targeted.setPackage(activityInfo.packageName);
					// Config.targetedShareIntents.add(targeted);
					// Config.targetedShareNames.add("腾讯微博");
					// }
					// if (activityInfo.packageName.equals("com.tencent.mm")) {
					// targeted.setPackage(activityInfo.packageName);
					// Config.targetedShareIntents.add(targeted);
					// Config.targetedShareNames.add("微信好友");
					// }
					// if
					// (activityInfo.packageName.equals("com.tencent.mobileqq"))
					// {
					// targeted.setPackage(activityInfo.packageName);
					// Config.targetedShareIntents.add(targeted);
					// Config.targetedShareNames.add("QQ好友");
					// }
					// if (activityInfo.packageName.equals("com.qzone")) {
					// targeted.setPackage(activityInfo.packageName);
					// Config.targetedShareIntents.add(targeted);
					// Config.targetedShareNames.add("QQ空间");
					// }
					// if
					// (activityInfo.packageName.equals("com.renren.mobile.android"))
					// {
					// targeted.setPackage(activityInfo.packageName);
					// Config.targetedShareIntents.add(targeted);
					// Config.targetedShareNames.add("人人");
					// }
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 安装软件，需要权限<uses-permission
	 * android:name="android.permission.INSTALL_PACKAGES" />
	 * 
	 * @Title: installApp
	 * @Description: TODO
	 * @param @param activity
	 * @param @param apkFilePath
	 * @return void
	 * @throws
	 */
	public static void installApp(Context activity, String apkFilePath) {
		if (activity == null || apkFilePath == null) {
			return;

		}
		// Uri uri = Uri.fromFile(new File("/sdcard/temp.apk")); //这里是APK路径
		try {
			Uri uri = Uri.fromFile(new File(apkFilePath)); // 这里是APK路径
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setDataAndType(uri,
					"application/vnd.android.package-archive");
			activity.startActivity(intent);
		} catch (Exception e) {
			
			e.printStackTrace();
		}

	}

	public static void callTelephone(String phoneNum, Context context) {
		Intent it = new Intent("android.intent.action.CALL", Uri.parse("tel:"
				+ phoneNum));
		context.startActivity(it);
	}

	public static void callTelephoneToDIAL(String phoneNum, Context context) {
		Intent it = new Intent("android.intent.action.DIAL", Uri.parse("tel:"
				+ phoneNum));
		context.startActivity(it);
	}

	public static void sendSms(String aPhoneNum, String aMsg, Context context) {
		try {
			Intent sendIntent = new Intent(Intent.ACTION_SENDTO,
					Uri.parse("smsto:" + aPhoneNum));
			// sendIntent.putExtra("address", aPhoneNum);
			sendIntent.putExtra("sms_body", aMsg);
			context.startActivity(sendIntent);
		} catch (Exception e) {
			
			Log.e("onFill", e.toString());
		}
	}

	public static void sendSmsUi(String aPhoneNum, String aMsg, Context activity) {
		// String smsUrl="sms://"+aPhoneNum;
		// Uri smsToUri = Uri.parse(smsUrl);
		Intent sendIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms://"));
		sendIntent.putExtra("address", aPhoneNum);
		sendIntent.putExtra("sms_body", aMsg);
		sendIntent.setType("vnd.android-dir/mms-sms");
		activity.startActivity(sendIntent);
	}

	public static boolean openBrowser(String url, Activity activity) {
		Uri uri = Uri.parse(url);
		Intent it = new Intent(Intent.ACTION_VIEW, uri);
		try {
			activity.startActivity(it);
			return true;
		} catch (RuntimeException e) {
			Log.e("onFling", e.toString());
		} catch (java.lang.Error e) {
			Log.e("onFling", e.toString());
		} catch (Exception e) {
			Log.e("onFling", e.toString());
		}
		return false;
	}

	public static Paint getFont(int aSize, int aStyle) {
		return new Paint();
	}

	public static void removeFile(String aFileName) {
		File file = new File(aFileName);
		file.delete();
	}

	public static void removeDir(String aDirPath) {

		File file = new File(aDirPath);
//		try {
////			FileUtils.deleteDirectory(file);
//		} catch (IOException e) {
//			
//			Log.e("onFill", e.toString());
//		}

	}

	public static boolean extractFileL(String aDstPath, String aSrcFile) {
		try {
			ZipInputStream in = new ZipInputStream(
					new FileInputStream(aSrcFile));
			ZipEntry entry = null;
			while ((entry = in.getNextEntry()) != null) {
				String entryName = entry.getName();
				if (entry.isDirectory()) {
					File file = new File(aDstPath + entryName);
					file.mkdirs();
				} else {
					String dstName = aDstPath + entryName;
					int pos = dstName.lastIndexOf("/");
					if (pos >= 0) {
						File file = new File(dstName.substring(0, pos));
						file.mkdirs();
					}
					FileOutputStream os = new FileOutputStream(aDstPath
							+ entryName);
					byte[] buf = new byte[1024];
					int len;
					while ((len = in.read(buf)) > 0) {
						os.write(buf, 0, len);
					}
					os.close();
					in.closeEntry();
				}
			}
			return true;
		} catch (IOException e) {
			Log.e("onFill", e.toString());
			return false;
		}
	}

	public static boolean openApp(String packageName, String entrance,
			Context context) {
		try {
			Intent intent = new Intent();
			intent.setComponent(new ComponentName(packageName, entrance));
			context.startActivity(intent);
			return true;
		} catch (RuntimeException e) {
			Log.e("onFling", e.toString());
		} catch (java.lang.Error e) {
			Log.e("onFling", e.toString());
		} catch (Exception e) {
			Log.e("onFling", e.toString());
		}
		return false;
	}

	public static boolean openApp(String appName, Context context) {
		try {
			Intent intent = new Intent(appName);
			context.startActivity(intent);
			return true;
		} catch (RuntimeException e) {
			Log.e("onFling", "OpenApp" + e.toString());
		} catch (java.lang.Error e) {
			Log.e("onFling", "OpenApp" + e.toString());
		} catch (Exception e) {
			Log.e("onFling", "OpenApp" + e.toString());
		}
		return false;
	}

	public static boolean isFileExists(String filePath) {
		boolean rs = false;
		try {
			File file = new File(filePath);
			rs = file.exists();
		} catch (Exception e) {
			
			Log.e("onFill", e.toString());
		}
		return rs;
	}

	/**
	 * Get Cell ID. Need permission :android.permission.ACCESS_COARSE_LOCATION
	 * android.permission.READ_PHONE_STATE
	 * 
	 * @return Cell ID
	 */
	public static int getCellID(Context con) {
		TelephonyManager tm = (TelephonyManager) con
				.getSystemService(Context.TELEPHONY_SERVICE);
		GsmCellLocation location = (GsmCellLocation) tm.getCellLocation();
		return location.getCid();
	}

	public static int getLacID(Context con) {
		TelephonyManager tm = (TelephonyManager) con
				.getSystemService(Context.TELEPHONY_SERVICE);
		GsmCellLocation gsm = (GsmCellLocation) tm.getCellLocation();
		return gsm.getLac();
	}

	/**
	 * Get Device Imei. Need permission
	 * :android.permission.ACCESS_COARSE_LOCATION
	 * android.permission.READ_PHONE_STATE
	 * 
	 * @return Imei
	 */
	public static String getImei(Context con) {
		TelephonyManager tm = (TelephonyManager) con
				.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getDeviceId();
	}

	/**
	 * Get Device Imsi. Need permission
	 * :android.permission.ACCESS_COARSE_LOCATION
	 * android.permission.READ_PHONE_STATE
	 * 
	 * @return Imsi
	 */
	public static String getImsi(Context con) {
		TelephonyManager tm = (TelephonyManager) con
				.getSystemService(Context.TELEPHONY_SERVICE);//
		return tm.getSubscriberId();
	}

	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static Bitmap createReflectedImage(Bitmap originalImage) {
		final int reflectionGap = 4;
		int width = originalImage.getWidth();
		int height = originalImage.getHeight();

		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);

		Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0,
				height / 2, width, height / 2, matrix, false);

		Bitmap bitmapWithReflection = Bitmap
				.createBitmap(width, (height + height / 3),
						android.graphics.Bitmap.Config.ARGB_8888);

		Canvas canvas = new Canvas(bitmapWithReflection);

		canvas.drawBitmap(originalImage, 0, 0, null);

		Paint deafaultPaint = new Paint();
		canvas.drawRect(0, height, width, height + reflectionGap, deafaultPaint);

		canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

		Paint paint = new Paint();
		LinearGradient shader = new LinearGradient(0,
				originalImage.getHeight(), 0, bitmapWithReflection.getHeight()
						+ reflectionGap, 0x70ffffff, 0x00ffffff, TileMode.CLAMP);

		paint.setShader(shader);

		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));

		canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
				+ reflectionGap, paint);
		return bitmapWithReflection;
	}

	/**
	 * 发送彩信
	 * 
	 */
	public static void sendMms(String phoneNum, String msgTitle,
			String msgBody, String filePath, Context context) {
		if (context == null) {
			return;
		}
		filePath = "file://" + filePath;
		//Log.d("ImgSend", filePath);
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra("address", phoneNum);// 接收者号码
		intent.putExtra("compose_mode", false);
		intent.putExtra("exit_on_sent", true);
		intent.putExtra("subject", msgTitle);// 标题
		intent.putExtra("sms_body", msgBody);// 彩信内容
		intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(filePath));// 添加附件

		intent.setClassName("com.android.mms",
				"com.android.mms.ui.ComposeMessageActivity");// 设置以彩信方式发送
		intent.setType("image/*");
		context.startActivity(intent);
	}

	public static double doubleE6(double d) {
		return d * 1000000;
	}

	public static Bitmap loadBitLimitWH(int width, int height, String filePath) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.outHeight = 200;
		options.outWidth = 200;
		options.inJustDecodeBounds = true;
		Bitmap bm = BitmapFactory.decodeFile(filePath, options); // 此时返回bm为空
		options.inJustDecodeBounds = false;
		int wScale = 1;
		if (width > 0) {
			int addOne = options.outWidth % width > 0 ? 1 : 0;
			wScale = options.outWidth / width + addOne;
		}
		int hScale = 1;
		if (height > 0) {
			int addOne = options.outWidth % width > 0 ? 1 : 0;
			hScale = options.outHeight / height + addOne;
		}
		int scale = Math.max(wScale, hScale);
		scale = scale > 0 ? scale : 1;
		options.inSampleSize = scale;
		bm = BitmapFactory.decodeFile(filePath, options);
		return bm;

	}

	public static Bitmap getImage(int height, String filePath, int inSampleSize) {
		Bitmap bm = null;
		try {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.outHeight = 200;
			options.outWidth = 200;
			options.inJustDecodeBounds = true;
			bm = BitmapFactory.decodeFile(filePath, options); // 此时返回bm为空
			options.inJustDecodeBounds = false;
			int scale = options.outHeight / height;
			scale = scale > 0 ? scale : 1;
			options.inSampleSize = scale;
			bm = BitmapFactory.decodeFile(filePath, options);
			return bm;
			/*
			 * BitmapFactory.Options options = new BitmapFactory.Options();
			 * options.inSampleSize = inSampleSize;
			 * 
			 * bm = BitmapFactory.decodeFile(filePath, options); // 此时返回bm为空
			 * 
			 * float scaleHeight = ((float) height) / bm.getHeight(); Matrix
			 * matrix = new Matrix(); matrix.postScale(scaleHeight,
			 * scaleHeight);
			 * 
			 * // 得到新的图片 bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
			 * bm.getHeight(),matrix,true);
			 */
		} catch (Exception e) {
			
			Log.e("onFill", e.toString());
		}
		return bm;

	}

	public static Bitmap getImage(String filePath) {
		Bitmap bm = null;
		try {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = 2;
			bm = BitmapFactory.decodeFile(filePath, options); // 此时返回bm为空
			Log.d("Bitmap", "" + bm.getHeight());
		} catch (Exception e) {
			
			Log.e("onFill", e.toString());
		}
		return bm;

	}

	public static Bitmap loadBitLimitWH(int width, int height, int resId,
			Context context) {

		Bitmap bm = null;
		try {
			bm = BitmapFactory.decodeResource(context.getResources(), resId);

			float scaleWidth = ((float) width) / bm.getWidth();

			float scaleHeight = ((float) height) / bm.getHeight();

			Matrix matrix = new Matrix();

			matrix.postScale(scaleWidth, scaleHeight);

			// 得到新的图片

			bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(),
					matrix,

					true);
		} catch (Exception e) {
			
			Log.e("onFill", e.toString());
		}
		return bm;

	}

	static public Bitmap creatBmpLimitWH(int width, int height, Bitmap bmp) {
		// 获得Bitmap的高和宽
		int bmpWidth = bmp.getWidth();
		int bmpHeight = bmp.getHeight();
		// 设置缩小比例
		float wScale = 1;
		if (width > 0) {
			wScale = width / (float) bmpWidth;
		}
		float hScale = 1;
		if (height > 0) {
			hScale = height / (float) bmpHeight;
		}
		// 计算出这次要缩小的比例
		float scale = Math.min(wScale, hScale);
		scale = scale > 1 ? 1 : scale;
		// 产生resize后的Bitmap对象
		Matrix matrix = new Matrix();
		matrix.postScale(scale, scale);
		Bitmap resizeBmp = null;
		try {
			resizeBmp = Bitmap.createBitmap(bmp, 0, 0, bmpWidth, bmpHeight,
					matrix, true);
		} catch (java.lang.Error e) {
			Log.e("onFling", "RuntimeException:" + e.toString());
		} catch (Exception e) {
			Log.e("onFling", "creatBMP:" + e.toString());
		}
		return resizeBmp;
	}

	/***
	 * 判断系统是否安装有指定的包（应用）
	 * 
	 * @param context
	 * @param packageName
	 *            包名
	 * @return
	 */
	static public boolean isInstall(Context context, String packageName) {

		boolean isInstall = false;
		if (context == null) {
			return isInstall;
		}
		PackageManager manager = context.getPackageManager();
		List<PackageInfo> packages = manager.getInstalledPackages(0);
		int count = packages.size();
		for (int i = 0; i < count; i++) {
			PackageInfo p = packages.get(i);
			if (p.packageName.equals(packageName)) {
				isInstall = true;
			}
		}
		return isInstall;
	}

	/**
	 * 通过应用的包名打开应用程序，打开的是默认的activity
	 * 
	 * @Title: OpenAppByPackageName
	 * @Description: TODO
	 * @param @param packageName
	 * @param @param context
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public static boolean openAppByPackageName(String packageName,
			Context context) {
		try {
			// packageName="com.uc.browser";
			Intent intent = context.getPackageManager()
					.getLaunchIntentForPackage(packageName);

			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
			return true;
		} catch (RuntimeException e) {
			Log.e("onFling", "OpenApp" + e.toString());
		} catch (java.lang.Error e) {
			Log.e("onFling", "OpenApp" + e.toString());
		} catch (Exception e) {
			Log.e("onFling", "OpenApp" + e.toString());
		}
		return false;
	}

	/**
	 * 
	 * @Title: getBitMap
	 * @Description: TODO
	 * @param @param context
	 * @param @param resId
	 * @param @return
	 * @return Bitmap
	 * @throws
	 */
	public static Bitmap getBitMap(Context context, int resId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		// 获取资源图片
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
	}

	public static BitmapDrawable getDrawable(Context context, int resId) {
		try {
			Bitmap bt = getBitMap(context, resId);

			return new BitmapDrawable(bt);
		} catch (Exception e) {
			
			Log.e("onFill", e.toString());
		}
		return null;
	}

	// 获得时间戳
	public static long GetTimeStamp() {
		return System.currentTimeMillis();
	}

	// 获得签名
	public static String CalculationSignature(long timeStamp, int allianceID,
			String secretKey, int sid, String requestType) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(timeStamp).append(allianceID);

		if (secretKey != null && !secretKey.equals("")) {
			String sectetKeyMD5 = MD5Encoding(secretKey).toUpperCase();
			stringBuilder.append(sectetKeyMD5);
		}

		stringBuilder.append(sid);

		if (requestType != null && !requestType.trim().equals("")) {
			stringBuilder.append(requestType);
		}

		String signature = MD5Encoding(stringBuilder.toString()).toUpperCase();

		return signature;
	}

	// MD5加密
	public static String MD5Encoding(String source) {
		MessageDigest mdInst;
		try {
			mdInst = MessageDigest.getInstance("MD5");
			byte[] input = source.getBytes();
			mdInst.update(input);
			byte[] output = mdInst.digest();

			int i = 0;

			StringBuffer buf = new StringBuffer("");

			for (int offset = 0; offset < output.length; offset++) {
				i = output[offset];

				if (i < 0) {
					i += 256;
				}

				if (i < 16) {
					buf.append("0");
				}

				buf.append(Integer.toHexString(i));
			}
			return buf.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}

	}

	// 如果s1小于s2,返回一个负数;如果s1大于s2，返回一个正数;如果他们相等，则返回0;
	public static int compare(String s1, String s2) {
		if (s1 == null && s2 == null) {
			return 0;
		} else if (s1 == null) {
			return -1;
		} else if (s2 == null) {
			return 1;
		}

		String[] arr1 = s1.split("[^a-zA-Z0-9]+"), arr2 = s2
				.split("[^a-zA-Z0-9]+");
		int i1, i2, i3;

		for (int ii = 0, max = Math.min(arr1.length, arr2.length); ii <= max; ii++) {
			if (ii == arr1.length)
				return ii == arr2.length ? 0 : -1;
			else if (ii == arr2.length)
				return 1;

			try {
				i1 = Integer.parseInt(arr1[ii]);
			} catch (Exception x) {
				i1 = Integer.MAX_VALUE;
			}

			try {
				i2 = Integer.parseInt(arr2[ii]);
			} catch (Exception x) {
				i2 = Integer.MAX_VALUE;
			}

			if (i1 != i2) {
				return i1 - i2;
			}

			i3 = arr1[ii].compareTo(arr2[ii]);

			if (i3 != 0)
				return i3;
		}
		return 0;
	}

	public static boolean copyFileFromAssets(String fromFileName,
			String toFilePath, Context activity) {
		try {

			InputStream input = activity.getResources().getAssets()
					.open(fromFileName);
			File fileOut = null;

			FileOutputStream out = null;

			fileOut = new File(toFilePath);
			if (!fileOut.exists()) {
				fileOut.createNewFile();
			}
			out = new FileOutputStream(fileOut);

			byte buf[] = new byte[1024];
			int c;
			while ((c = input.read(buf)) > 0) {
				out.write(buf, 0, c); // 将内容写到新文件当中

			}
			out.close();
			input.close();

			return true;

		} catch (Exception e) {
			
			Log.e("onFill", e.toString());
		}
		return false;

	}

	public static int getImageResId(String picName) {
		if (picName == null || picName.trim().equals("")) {
			return -1;
		}
		Class draw = R.drawable.class;
		try {
			java.lang.reflect.Field field = draw.getDeclaredField(picName);
			return field.getInt(picName);
		} catch (Exception e) {
			return -1;
		}
	}

	public static void saveBitmap(Bitmap bitmap, String filePath) {
		try {
			File file = new File(filePath);
			file.createNewFile();
			FileOutputStream out = new FileOutputStream(file);
			BufferedOutputStream bos = new BufferedOutputStream(out);
			bitmap.compress(Bitmap.CompressFormat.PNG, 80, bos);
			bos.flush();
			bos.close();
		} catch (Exception e) {
			
			Log.e("onFill", e.toString());
		}
	}

	// MD5加密
	public static String md5Encoding(String source) {
		MessageDigest mdInst;
		try {
			mdInst = MessageDigest.getInstance("MD5");
			byte[] input = source.getBytes();
			mdInst.update(input);
			byte[] output = mdInst.digest();

			int i = 0;

			StringBuffer buf = new StringBuffer("");

			for (int offset = 0; offset < output.length; offset++) {
				i = output[offset];

				if (i < 0) {
					i += 256;
				}

				if (i < 16) {
					buf.append("0");
				}

				buf.append(Integer.toHexString(i));
			}
			return buf.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}

	}

	public static void sendMail(String emailAddress, Context context) {
		try {

			String[] reciver = new String[] { emailAddress };

			Intent intent = new Intent(android.content.Intent.ACTION_SEND);
			intent.setType("plain/text");
			intent.putExtra(android.content.Intent.EXTRA_EMAIL, reciver);
			// Intent.createChooser(intent, "mail test")
			// context.startActivity(Intent.createChooser(intent, "Mail"));
			context.startActivity(intent);

		} catch (Exception e) {
			
			Log.e("onFill", e.toString());
		}
	}

	/**
	 * 
	 * 重新使用应用转为前台或打开，即原来是打开的，则显示原来的样子
	 * 
	 * @param packageName
	 * @param context
	 * @return
	 */
	public static boolean restartOrOpenAppByPackageName(String packageName,
			Context context) {
		try {
			Intent intent = context.getPackageManager()
					.getLaunchIntentForPackage(packageName);

			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
					| Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
			intent.addCategory(Intent.CATEGORY_LAUNCHER);
			// context.startActivity(intent);
			return true;
		} catch (RuntimeException e) {
			System.out.println("OpenApp==" + e.toString());
			Log.e("onFling", "OpenApp" + e.toString());
		} catch (java.lang.Error e) {
			System.out.println("OpenApqqqqp==" + e.toString());
			Log.e("onFling", "OpenApp" + e.toString());
		} catch (Exception e) {
			System.out.println("OpenAwwwwwpp==" + e.toString());
			Log.e("onFling", "OpenApp" + e.toString());
		}
		return false;
	}

	public static void changeImageToGray(View view) {
		try {
			ColorMatrix colorMatrix = new ColorMatrix();
			colorMatrix.setSaturation(0);
			view.getBackground().setFilterBitmap(false);
			ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(
					colorMatrix);
			view.getBackground().setColorFilter(colorMatrixFilter);

			/*
			 * Bitmap bit=getGrayBitmap(view.getBackground().g); Drawable d=new
			 * BitmapDrawable(bit); view.setBackgroundDrawable(d);
			 */
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	public static Bitmap getGrayBitmap(Bitmap bitmapSrc) {

		Bitmap buffer = Bitmap
				.createBitmap(bitmapSrc.getWidth(), bitmapSrc.getHeight(),
						android.graphics.Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(buffer);
		Paint paint = new Paint();
		ColorMatrix colorMatrix = new ColorMatrix();
		colorMatrix.setSaturation(0);
		ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(
				colorMatrix);
		paint.setColorFilter(colorMatrixFilter);

		canvas.drawBitmap(bitmapSrc, 0, 0, paint);
		canvas.save();
		return buffer;
	}

	/**
	 * 判断网络是否可用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetEnable(Context context) {
		try {
			ConnectivityManager connManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connManager.getActiveNetworkInfo() != null) {
				return connManager.getActiveNetworkInfo().isAvailable();
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}

		return false;

	}

	/**
	 * drawable转bitmap
	 * 
	 * @param drawable
	 * @return
	 */
	public static Bitmap drawableToBitmap(Drawable drawable) {

		Bitmap bitmap = Bitmap.createBitmap(

		drawable.getIntrinsicWidth(),

		drawable.getIntrinsicHeight(),

		drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888

		: Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		// canvas.setBitmap(bitmap);

		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight());

		drawable.draw(canvas);

		return small(bitmap);

	}

	private static Bitmap small(Bitmap bitmap) {
		Matrix matrix = new Matrix();
		matrix.postScale(0.4f, 0.4f); // 长和宽放大缩小的比例
		Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), matrix, true);
		return resizeBmp;
	}

	/**
	 * byte[]
	 * 
	 * @param bm
	 * @return
	 */
	public static byte[] Bitmap2Bytes(Bitmap bm) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);

		return baos.toByteArray();
	}

	/**
	 * 验证手机号码
	 * 
	 * @param mobiles
	 * @return [0-9]{5,9}
	 */
	public static boolean isMobileNO(String mobiles) {
		boolean flag = false;
		try {
			Pattern p = Pattern
					.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
			Matcher m = p.matcher(mobiles);
			flag = m.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 验证中国移动手机号码
	 * 
	 * @param mobiles
	 * @return [0-9]{5,9}
	 */
	public static boolean isMobileCM(String mobiles) {
		boolean flag = false;
		try {
			/**
			 * regid只能以139|138|137|136|135|134|
			 * 159|158|152|151|150|157|188|187|183|147|182开头11位'}
			 */
			Pattern p = Pattern.compile("^((134)|" + "(135)|" + "(136)|"
					+ "(137)|" + "(138)|" + "(139)|" + "(150)|" + "(151)|"
					+ "(152)|" + "(157)|" + "(158)|" + "(159)|" + "(182)|"
					+ "(183)|" + "(187)|" + "(188)|" + "(147)" + ")\\d{8}$");
			Matcher m = p.matcher(mobiles);
			flag = m.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 检测邮箱地址是否合法
	 * 
	 * @param email
	 * @return true合法 false不合法
	 */
	public static boolean isEmail(String email) {
		if (null == email || "".equals(email))
			return false;
		boolean flag = false;
		try {
			String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 过滤字符串中的特殊字符
	 * 
	 * @return time
	 */
	public static String stringFilter(String str, String filter) {
		String filterStr = null;
		Matcher m;
		try {
			Pattern p = Pattern.compile(filter);
			m = p.matcher(str);
			filterStr = m.replaceAll("");
		} catch (PatternSyntaxException e) {

		}
		return filterStr;
	}
}
