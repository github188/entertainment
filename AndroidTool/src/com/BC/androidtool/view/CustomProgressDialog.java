package com.BC.androidtool.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.BC.androidtool.R;



public class CustomProgressDialog extends Dialog {
	public Context context = null;

	public CustomProgressDialog(Context context) {
		super(context);
	}

	public CustomProgressDialog(Context context, int theme) {
		super(context, theme);
	}

	public static CustomProgressDialog createDialog(Context con) {
		CustomProgressDialog customProgressDialog = new CustomProgressDialog(con,
				R.style.CustomProgressDialog);
		customProgressDialog.context=con;
		customProgressDialog.setContentView(R.layout.customprogressdialog);
		customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
		customProgressDialog.getWindow().getAttributes().height = android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
		customProgressDialog.getWindow().getAttributes().width = android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
		return customProgressDialog;
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		Animation animation = AnimationUtils.loadAnimation(context,
				R.anim.progress_round);
		ImageView imageView = (ImageView)findViewById(R.id.loadingImageView);
		imageView.startAnimation(animation);
		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				dismiss();
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

		});
	}

	/**
	 * 
	 * [Summary] setTitile 标题
	 * 
	 * @param strTitle
	 * @return
	 * 
	 */
	public CustomProgressDialog setTitile(String strTitle) {
		return this;
	}

	/**
	 * 
	 * [Summary] setMessage 提示内容
	 * 
	 * @param strMessage
	 * @return
	 * 
	 */
	public CustomProgressDialog setMessage(String strMessage) {
		
		TextView tvMsg = (TextView)findViewById(R.id.id_tv_loadingmsg);

		if (tvMsg != null) {
			tvMsg.setText(strMessage);
		}

		return this;
	}
}