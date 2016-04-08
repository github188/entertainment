package com.BC.androidtool.views;

import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.BC.androidtool.view.CustomProgressDialog;

public class BaseLinearLayout extends LinearLayout {
	CustomProgressDialog progressDialog;
	public int mSession = 0;
	public int odSession = 0;
	public boolean canShow = false;
	public String stationName;
	public String stationCode;

	LayoutInflater inflater;

	public LayoutInflater getInflater() {
		return inflater;
	}

	public void setInflater(LayoutInflater inflater) {
		this.inflater = inflater;
	}

	public CustomProgressDialog getProgressDialog() {
		return progressDialog;
	}

	public void setProgressDialog(CustomProgressDialog progressDialog) {
		this.progressDialog = progressDialog;
	}

	public BaseLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public BaseLinearLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public void showProgressDialog(String title) {
		try {
			if (progressDialog != null) {
				return;
			}
			progressDialog = CustomProgressDialog.createDialog(this
					.getContext());
			progressDialog.setMessage(title);
			progressDialog.setCancelable(true);
			progressDialog.show();

			progressDialog
					.setOnCancelListener(new DialogInterface.OnCancelListener() {
						@Override
						public void onCancel(DialogInterface arg0) {
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

	public void init() {
		mSession = 0;
		odSession = 0;
	}

	public void toNext() {
		// TODO Auto-generated method stub
		if (mSession > 0) {
			odSession = mSession;
			mSession--;
			getData(mSession);
		} else if (mSession == 0) {
			odSession = mSession;
			getData(mSession);
		} else {

		}

		// showLayout
	}

	public void toPrew() {
		odSession = mSession;
		mSession++;
		getData(mSession);
	}

	public void showMe(boolean canShow) {
		if (!this.canShow) {
			return;
		}
	}

	public void getData(int session) {
		// TODO Auto-generated method stub

	}
}
