package com.BC.androidtool;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.BC.androidtool.HttpThread.InfoHandler.InfoReceiver;
import com.BC.androidtool.interfaces.FragmentSelectPicture;
import com.BC.androidtool.view.CustomProgressDialog;

/**
 * 基础Fragment界面
 * 
 * @author shuzhi
 * 
 */
public abstract class BaseDialogFragment extends DialogFragment implements
		InfoReceiver {
	CustomProgressDialog progressDialog;
	protected View showView;
	protected String stationCode = "";
	protected String stationName = "";
	protected boolean isDeptName = false;

	Spinner spinner1;

	private FragmentSelectPicture fragmentSelectPicture;

	public FragmentSelectPicture getFragmentSelectPicture() {
		return fragmentSelectPicture;
	}

	public void setFragmentSelectPicture(
			FragmentSelectPicture fragmentSelectPicture) {
		this.fragmentSelectPicture = fragmentSelectPicture;
	}

	public CustomProgressDialog getProgressDialog() {
		return progressDialog;
	}

	public boolean isDeptName() {
		return isDeptName;
	}

	public void setDeptName(boolean isDeptName) {
		this.isDeptName = isDeptName;
	}

	public void setProgressDialog(CustomProgressDialog progressDialog) {
		this.progressDialog = progressDialog;
	}

	public Spinner getSpinner1() {
		return spinner1;
	}

	public void setSpinner1(Spinner spinner1) {
		this.spinner1 = spinner1;
	}

	public void showProgressDialog(String title) {
		try {
			if (progressDialog != null) {
				return;
			}
			progressDialog = CustomProgressDialog.createDialog(this
					.getActivity());
			progressDialog.setMessage(title);
			progressDialog.setCancelable(true);
			progressDialog.show();

			progressDialog
					.setOnCancelListener(new DialogInterface.OnCancelListener() {
						@Override
						public void onCancel(DialogInterface arg0) {
							if (progressDialog == null) {
								return;
							}
							progressDialog.dismiss();
							progressDialog = null;
						}
					});
		} catch (Exception e) {
			// Log.d("showProgressDialog", e.toString());
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

	public void titleColor(View v, int id) {
		// TODO Auto-generated method stub
		v.findViewById(R.id.relativeLayout1).setBackgroundResource(id);
	}

	public void getSpinner(String type) {

	}

	public void selectType() {
		// TODO Auto-generated method stub

	}

	public void onStopRequest() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onInfoReceived(int errcode, HashMap<String, Object> items) {
		// TODO Auto-generated method stub
		removeProgressDialog();
		onStopRequest();
		Log.d("shuzhi", "errcode = " + errcode);
		if (errcode == 0) {
			Log.d("shuzhi", items.toString());
			String jsonString = (String) items.get("content");
			if (jsonString != null) {
				JSONObject object;
				try {
					object = new JSONObject(jsonString);
					String msg = object.optString("msg");
					int errorCode = object.optInt("status", -1);
					int taskType = (Integer) items.get("taskType");
					if (errorCode == 0) {
						requestSuccessful(jsonString, taskType);
					} else {
						requestFailed(errcode, msg);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					requestFailed(-1, "解析出错");
					e.printStackTrace();
				}
			} else {
				requestFailed(-1, "返回参数有误");
			}
		}
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
	public void requestFailed(int errcode, String message) {
		removeProgressDialog();
		if (errcode == 0) {
			Toast.makeText(this.getActivity(), message, Toast.LENGTH_SHORT)
					.show();
		} else {
			Toast.makeText(this.getActivity(), message + "  错误码：" + errcode,
					Toast.LENGTH_SHORT).show();
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

	public void setText(int Id, String text) {
		TextView textView = (TextView) getView().findViewById(Id);
		textView.setText(text);
	}

	public String getTextViewContent(int id) {
		String s = new String();
		TextView t = (TextView) getView().findViewById(id);
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
