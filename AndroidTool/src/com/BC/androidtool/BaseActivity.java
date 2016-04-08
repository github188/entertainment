package com.BC.androidtool;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.BC.androidtool.HttpThread.InfoHandler.InfoReceiver;
import com.BC.androidtool.config.Config;
import com.BC.androidtool.view.CustomProgressDialog;

/**
 * 基础界面类
 * 
 * @author shuzhi
 * 
 */
public abstract class BaseActivity extends FragmentActivity implements
		InfoReceiver {
	// protected ImageLoader imageLoader = ImageLoader.getInstance();
	private static final int NONE = 0;
	private static final int PHOTO_GRAPH = 1;// 拍照
	private static final int PHOTO_ZOOM = 2; // 相册
	private static final int PHOTO_RESOULT = 3;// 结果
	private boolean canCut = true;// 是否裁剪
	private static final String IMAGE_UNSPECIFIED = "image/*";
	private String IMAGE_FILE = "";
	private boolean exitCenter = false;

	public static ArrayList<Activity> activityList = new ArrayList<Activity>();
	private Activity activity;
	CustomProgressDialog progressDialog;
	public int count = 0;
	Calendar calendar;
	DateFormat format;

	public boolean isCanCut() {
		return canCut;
	}

	public void setCanCut(boolean canCut) {
		this.canCut = canCut;
	}

	public boolean isExitCenter() {
		return exitCenter;
	}

	public void setExitCenter(boolean exitCenter) {
		this.exitCenter = exitCenter;
	}

	public CustomProgressDialog getProgressDialog() {
		return progressDialog;
	}

	public void setProgressDialog(CustomProgressDialog progressDialog) {
		this.progressDialog = progressDialog;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		calendar = Calendar.getInstance(Locale.CHINA);
		format = new SimpleDateFormat("yyyyMMddHHmmsssss");
		try {
			activityList.add(this);
			activity = this;
			// Log.d("shuzhi", "config");
			if (!Config.hasloadConfig) {
				// Log.d("shuzhi", "loadConfig");
				File cache = this.getCacheDir();
				Config.setCachePath(cache.getPath());
				Config.setFileDir(getFilesDir().getPath());
				// Config.tf = Typeface.createFromAsset(getAssets(),
				// Config.fontPath);
				Config.maincontext = this;
				Config.loadConfig();
				// WorkerManager.OpenWorkerManager(2);
				// ImageWorkerGroup.OpenImageWorkerGroup(2);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (findViewById(R.id.nav_back_view) != null) {
			backButton();
		}
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}

	@Override
	protected void onDestroy() {
		try {
			activityList.remove(this);
			this.getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.onDestroy();
	}

	public void setTitleBackground(int id) {
		// TODO Auto-generated method stub
		findViewById(R.id.relativeLayout1).setBackgroundResource(id);
	}

	public void backToHome() {
		try {
			for (int i = 0; i < activityList.size(); i++) {
				Activity activity = activityList.get(i);
				activity.finish();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showProgressDialog(String title) {
		try {
			if (progressDialog != null) {
				return;
			}
			progressDialog = CustomProgressDialog.createDialog(this);
			progressDialog.setMessage(title);
			progressDialog.setCancelable(true);
			progressDialog.show();

			progressDialog
					.setOnCancelListener(new DialogInterface.OnCancelListener() {
						@Override
						public void onCancel(DialogInterface arg0) {
							if (progressDialog != null) {
								progressDialog.dismiss();
								progressDialog = null;
							}
						}
					});
		} catch (Exception e) {
			// Log.d("showProgressDialog", e.toString());
			e.printStackTrace();
		}
	}

	public void removeProgressDialog() {
		try {
			if (progressDialog != null) {
				progressDialog.dismiss();
				progressDialog = null;
			}
		} catch (Exception e) {

			// Log.d("removeProgressDialog", e.toString());
		}
	}

	/**
	 * 返回按钮
	 */
	public Button backButton() {
		Button button = (Button) findViewById(R.id.nav_back_view);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					doBack();
					activity.finish();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		return button;
	}

	public void doBack() {

	}

	/**
	 * 获得右上角按钮的方法
	 * 
	 * @return 返回右上角按钮对象
	 */
	public Button showLeftButton() {
		try {
			Button button = (Button) findViewById(R.id.buttonTypefaces1);
			button.setVisibility(View.VISIBLE);
			return button;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获得右上角按钮的方法
	 * 
	 * @return 返回右上角按钮对象
	 */
	public Button showRightButton() {
		try {
			Button button = (Button) findViewById(R.id.buttonTypefaces2);
			button.setVisibility(View.VISIBLE);
			return button;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 隐藏右上角按钮的方法
	 * 
	 * @return 返回右上角按钮对象
	 */
	public void goneRightButton() {
		try {
			Button button = (Button) findViewById(R.id.buttonTypefaces1);
			button.setVisibility(View.GONE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 隐藏左上角按钮的方法
	 * 
	 */
	public void goneLeftButton() {
		try {
			Button button = (Button) findViewById(R.id.buttonTypefaces2);
			button.setVisibility(View.GONE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获得下拉title钮的方法
	 * 
	 * @return 返回右上角按钮对象
	 */
	public Spinner showSpinnerTitle(String title) {
		try {
			LinearLayout title2 = (LinearLayout) findViewById(R.id.title2);
			Spinner spinner1 = (Spinner) this.findViewById(R.id.titleSpinner1);
			TextView titleView = (TextView) findViewById(R.id.titleName2);
			title2.setVisibility(View.VISIBLE);
			titleView.setText(title);
			return spinner1;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 隐藏下拉title钮的方法
	 * 
	 * @return 返回右上角按钮对象
	 */
	public void goneSpinnerTitle() {
		try {
			LinearLayout title2 = (LinearLayout) findViewById(R.id.title2);
			title2.setVisibility(View.GONE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设置标题，并将控件返回
	 * 
	 * @param title
	 *            标题名称
	 * @return
	 */
	public TextView showTitle(String title) {
		TextView titleView = (TextView) findViewById(R.id.titleName);
		titleView.setText(title);
		return titleView;
	}

	private void showExitAppAlert() {
		final Dialog dlg = new Dialog(this, R.style.MyDialogStyleBottom);
		dlg.show();
		Window window = dlg.getWindow();
		// *** 主要就是在这里实现这种效果的.
		// 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
		View exitLayout = window.findViewById(R.id.exitLayout);
		if (exitCenter) {
			window.setContentView(R.layout.show_exit_dialog2);
		} else {
			window.setContentView(R.layout.show_exit_dialog);
		}
		TextView msgTv = (TextView) window.findViewById(R.id.text);
		// 为确认按钮添加事件,执行退出应用操作
		window.findViewById(R.id.btn_ok).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						ExitApp(); // 退出应用...
						dlg.dismiss();
					}
				});

		// 关闭alert对话框架
		window.findViewById(R.id.btn_cancel).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						dlg.cancel();
					}
				});
	}

	public void ExitApp() {
		finish();
		System.exit(0);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && activityList.size() == 1) {
			showExitAppAlert();
		}

		return super.onKeyDown(keyCode, event);

	}

	public void onStopRequest() {

	}

	@Override
	public void onInfoReceived(int errcode, HashMap<String, Object> items) {
		// TODO Auto-generated method stub
		removeProgressDialog();
		Log.d("shuzhi", "errcode = " + errcode);
		if (errcode == 0) {
			Log.d("shuzhi", items.toString());
			String jsonString = (String) items.get("content");
			if (jsonString != null) {
				JSONObject object;
				try {
					object = new JSONObject(jsonString);
					String msg = object.optString("msg");
					int code = object.optInt("status", -1);
					int taskType = (Integer) items.get("taskType");
					if (code == 0) {
						requestSuccessful(jsonString, taskType);
					} else {
						requestFailed(code, msg, taskType);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					requestFailed(-1, "解析出错", -1);
					e.printStackTrace();
				}
			} else {
				requestFailed(-1, "返回参数有误", -1);
			}
		} else {
			if (items.get("message") == null) {
				items.put("message", "未知错误");
			}
			requestFailed(errcode, items.get("message").toString(), -1);
		}
		onStopRequest();
	}

	@Override
	public void onNotifyText(String notify) {
		// TODO Auto-generated method stub

	}

	/**
	 * 请求成功
	 */
	public abstract void requestSuccessful(String jsonString, int taskType);

	/**
	 * 请求提示
	 */
	public void requestFailed(int errcode, String message, int taskType) {
		removeProgressDialog();
		if (errcode == 0) {
			Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, message
			// + "  错误码：" + errcode
					, Toast.LENGTH_SHORT).show();
		}
	}

	public void phoneImage() {
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				IMAGE_UNSPECIFIED);
		Calendar newCalendar = Calendar.getInstance();
		IMAGE_FILE = format.format(newCalendar.getTime());
		startActivityForResult(intent, PHOTO_ZOOM);
	}

	public void takePictureImage() {
		// Calendar newCalendar = (Calendar) calendar.clone();
		Calendar newCalendar = Calendar.getInstance();
		IMAGE_FILE = format.format(newCalendar.getTime());
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT,
				Uri.fromFile(new File(
						Environment.getExternalStorageDirectory(), IMAGE_FILE
								+ ".jpg")));
		startActivityForResult(intent, PHOTO_GRAPH);
	}

	/**
	 * 显示一个自定义的选择图片对话框
	 * 
	 * @param layoutId
	 *            布局
	 * @param noId
	 *            取消按钮
	 * @param phoneImageId
	 *            相册选取
	 * @param takePictureImageId
	 *            拍照
	 */
	public void showAlertDialog(int layoutId, int noId, int phoneImageId,
			int takePictureImageId) {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		final AlertDialog ad = builder.create();
		ad.show();
		Window window = ad.getWindow();

		View view = LayoutInflater.from(this).inflate(layoutId, null);
		window.setContentView(view);
		window.findViewById(noId).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ad.dismiss();
			}
		});
		window.findViewById(phoneImageId).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						phoneImage();
						ad.dismiss();
					}
				});
		window.findViewById(takePictureImageId).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						takePictureImage();
						ad.dismiss();
					}
				});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == NONE)
			return;
		// 拍照
		if (requestCode == PHOTO_GRAPH) {
			// 设置文件保存路径
			if (canCut) {
				File picture = new File(
						Environment.getExternalStorageDirectory(), IMAGE_FILE
								+ ".jpg");
				startPhotoZoom(Uri.fromFile(picture));
			} else {
				File picture = new File(
						Environment.getExternalStorageDirectory(), IMAGE_FILE
								+ ".jpg");
				obtainImage(picture.getPath());
			}

		}
		if (data == null)
			return;

		// 读取相册缩放图片
		if (requestCode == PHOTO_ZOOM) {
			if (canCut) {
				startPhotoZoom(data.getData());
			} else {
				try {
					Uri originalUri = data.getData(); // 获得图片的uri
					String[] proj = { MediaStore.Images.Media.DATA };
					// 好像是android多媒体数据库的封装接口，具体的看Android文档
					Cursor cursor = managedQuery(originalUri, proj, null, null,
							null);
					// 按我个人理解 这个是获得用户选择的图片的索引值
					int column_index = cursor
							.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
					// 将光标移至开头 ，这个很重要，不小心很容易引起越界
					cursor.moveToFirst();
					// 最后根据索引值获取图片路径www.2cto.com
					String path = cursor.getString(column_index);
				} catch (Exception e) {
					Log.e("shuzhi", e.toString());
				}
			}
		}
		// 处理结果
		if (requestCode == PHOTO_RESOULT) {
			Bundle extras = data.getExtras();
			if (extras != null) {
				Bitmap photo = extras.getParcelable("data");
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				photo.compress(Bitmap.CompressFormat.JPEG, 75, stream);// (0-100)压缩文件
				// 此处可以把Bitmap保存到sd卡中，具体请看：http://www.cnblogs.com/linjiqin/archive/2011/12/28/2304940.html
				// imageView.setImageBitmap(photo); //把图片显示在ImageView控件上
				setImage(new File(Environment.getExternalStorageDirectory(),
						IMAGE_FILE + ".jpg"), photo);
			}

		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 收缩图片
	 * 
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 4);
		intent.putExtra("aspectY", 3);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 400);
		intent.putExtra("outputY", 300);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, PHOTO_RESOULT);
	}

	public void obtainImage(String imagePath) {

	}

	public void setImage(File path, Bitmap bmp) {
		try {
			if (!path.exists()) {
				path.createNewFile();
			}
			FileOutputStream iStream = new FileOutputStream(path);
			bmp.compress(CompressFormat.JPEG, 100, iStream);
			iStream.close();
			obtainImage(path.getName());
			iStream = null;
			path = null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Bitmap downloadImage(String imgURLStr) throws IOException {
		URL imgURL = new URL(imgURLStr);
		URLConnection conn = imgURL.openConnection();
		conn.connect();
		InputStream is = conn.getInputStream();
		BufferedInputStream bis = new BufferedInputStream(is);
		// 下载图片
		Bitmap bmp = BitmapFactory.decodeStream(bis);
		// 关闭Stream
		bis.close();
		is.close();
		imgURL = null;
		return bmp;
	}

	public void setText(int id, String text) {
		TextView t = (TextView) findViewById(id);
		t.setText(text);
	}

	public String getTextViewContent(int id) {
		String s = new String();
		TextView t = (TextView) findViewById(id);
		s = t.getText().toString();
		return s;
	}

	public void animationDrawable(final AnimationDrawable animationDrawable) {
		animationDrawable.start();

		int duration = 0;
		for (int i = 0; i < animationDrawable.getNumberOfFrames(); i++) {
			duration += animationDrawable.getDuration(i);
		}
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			public void run() {
				// 此处调用第二个动画播放方法
				animationDrawable.stop();
				animationDrawableOver();
			}
		}, duration);

	}

	public void animationDrawableOver() {

	}

}
