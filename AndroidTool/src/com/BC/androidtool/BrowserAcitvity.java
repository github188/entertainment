package com.BC.androidtool;

import org.apache.http.util.EncodingUtils;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.BC.androidtool.view.CustomProgressDialog;
import com.BC.androidtool.views.BaseWebView;

/**
 * 浏览器界面
 * 
 * @author shuzhi
 * 
 */
public class BrowserAcitvity extends BaseActivity {

	public ValueCallback<Uri> mUploadMessage;
	public final static int FILECHOOSER_RESULTCODE = 1;
	public Uri imageUri;

	public View stop, refresh;
	LinearLayout firstBar, secondBar;
	public BaseWebView mWeb;
	private ProgressBar progressbar;
	String mNodeId;
	int mIsFaverite = 0;
	private CustomProgressDialog mProgressDlg = null;

	boolean isToDownload = false;
	String share = "";
	String nodename = "";
	String url = "";
	String post = "";
	CharSequence[] items = { "移动微博", "短信" };
	String[] sharetype = { "yidongweibo", "duanxin" };
	BrowserAcitvity content;

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			/*
			 * if (msg.what >= 100) { progressbar.setVisibility(View.GONE); }
			 * progressbar.setProgress(msg.what);
			 */
			if (msg.what == 0) {
				loadurl(mWeb, url);
			}
			if (msg.what == 1) {
				loadurl(mWeb, url, post);
			}
			super.handleMessage(msg);
		}
	};

	public void loadurl(final WebView view, final String url) {
		view.loadUrl(url);// 载入网页
	}

	public void loadurl(final WebView view, final String url, final String post) {
		Log.d("shuzhi", "post = " + EncodingUtils.getBytes(post, "BASE64"));
		view.postUrl(url, EncodingUtils.getBytes(post, "BASE64"));// 载入网页
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		this.setContentView(R.layout.item_page_web);

		content = this;
		Intent intent = getIntent();
		url = intent.getStringExtra("url");
		isToDownload = intent.getBooleanExtra("isToDownload", false);

		post = intent.getStringExtra("post");
//		Log.d("shuzhi", post);
		// secondBar = (LinearLayout) findViewById(R.id.secondBar);

		if (post == null) {
			handler.sendEmptyMessage(0);
		} else {
			handler.sendEmptyMessage(1);
		}

		// addBackListener();
		// this.findViewById(R.id.pre_button).setOnClickListener(
		// new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// if (mWeb.canGoBack()) {
		// mWeb.goBack();
		// onChangeMyWebViewStatus(mWeb);
		// } else {
		// content.finish();
		// }
		// }
		// });
		// this.findViewById(R.id.next_button).setOnClickListener(
		// new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// mWeb.goForward();
		// onChangeMyWebViewStatus(mWeb);
		// }
		// });
		// refresh = this.findViewById(R.id.refresh_button);
		// refresh.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// String lastUrl = mWeb.getLastUrl();
		// if (lastUrl == null) {
		// lastUrl = url;
		// }
		// mWeb.scrollTo(0, 0);
		// mWeb.loadUrl(lastUrl);
		// onChangeMyWebViewStatus(mWeb);
		//
		// stop.setVisibility(View.VISIBLE);
		// refresh.setVisibility(View.GONE);
		// }
		// });
		// stop = this.findViewById(R.id.stop_button);
		// stop.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// mWeb.stopLoading();
		// refresh.setVisibility(View.VISIBLE);
		// stop.setVisibility(View.GONE);
		// }
		// });

		mWeb = (BaseWebView) this.findViewById(R.id.webView1);
		mWeb.requestFocus(View.FOCUS_DOWN);
		// mWeb.getSettings().setJavaScriptEnabled(true);// 可用JS
		mWeb.setScrollBarStyle(0);// 滚动条风格，为0就是不给滚动条留空间，滚动条覆盖在网页上
		// mWeb.setWebViewClient(new WebViewClient() {
		// public boolean shouldOverrideUrlLoading(final WebView view,
		// final String url) {
		// loadurl(view, url);// 载入网页
		// return true;
		// }// 重写点击动作,用webview载入
		//
		// });
		// mWeb.setWebChromeClient(new WebChromeClient() {
		//
		// @Override
		// public void onProgressChanged(WebView view, int progress) {
		// progressbar(view,progress);
		// super.onProgressChanged(view, progress);
		//
		// }
		//
		// });
		mWeb.isHardwareAccelerated();
		Log.d("shuzhi", "mweb = " + mWeb.isHardwareAccelerated());
		if (url != null) {

			// WebView.enablePlatformNotifications();// cmwap~�G����HA(�Pk� �
			mWeb.requestFocus();
			// if (title.equals("我的收藏")) {
			// String url1 = url + "?pt=pps&token=&ua="
			// + android.os.Build.MODEL.trim();
			// mWeb.loadUrl(url1);
			// } else if (url != null) {
			// mWeb.loadUrl(url);
			// }

		}
		onChangeMyWebViewStatus(mWeb);
	}

	public void onChangeMyWebViewStatus(BaseWebView web) {
		// if (web.canGoBack()) {
		// this.findViewById(R.id.pre_button).setBackgroundResource(
		// R.drawable.pre_button_real_new);
		// } else {
		// this.findViewById(R.id.pre_button).setBackgroundResource(
		// R.drawable.left_c);
		//
		// }
		// if (web.canGoForward()) {
		// this.findViewById(R.id.next_button).setBackgroundResource(
		// R.drawable.next_button_real_new);
		// } else {
		// this.findViewById(R.id.next_button).setBackgroundResource(
		// R.drawable.right_c);
		// }
		// progressbar.setProgress(0);
	}

	public void progressbar(WebView view, int progress) {
		if (progress != 100) {
			refresh.setVisibility(View.GONE);
			stop.setVisibility(View.VISIBLE);
			progressbar.setVisibility(View.VISIBLE);
			progressbar.setProgress(progress);
		} else {
			refresh.setVisibility(View.VISIBLE);
			stop.setVisibility(View.GONE);
			progressbar.setVisibility(View.GONE);
			progressbar.setProgress(0);
		}
	}

	public void loading() {
		firstBar.setVisibility(View.VISIBLE);
		stop.setVisibility(View.VISIBLE);
		refresh.setVisibility(View.GONE);
		// secondBar.setVisibility(View.VISIBLE);
		if (progressbar != null) {
			progressbar.setVisibility(View.VISIBLE);
		}
	}

	public void load_is_done() {
		refresh.setVisibility(View.VISIBLE);
		stop.setVisibility(View.GONE);
		// secondBar.setVisibility(View.INVISIBLE);
		firstBar.setVisibility(View.VISIBLE);
		// handler.sendEmptyMessage(100);
		if (progressbar != null) {
			progressbar.setVisibility(View.GONE);
		}
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		// System.gc();
	}

	@Override
	protected void onDestroy() {
		try {

			mWeb.stopLoading();
			mWeb.destroy();

		} catch (Exception e) {

			e.printStackTrace();
		}
		super.onDestroy();
	}

	public void showProgressDialog(int type) {
		try {
			if (mProgressDlg != null) {
				return;
			}
			mProgressDlg = CustomProgressDialog.createDialog(this);
			mProgressDlg.setMessage("请等待...");
			mProgressDlg.show();
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(this, "请稍后", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onConfigurationChanged(Configuration config) {
		super.onConfigurationChanged(config);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		if (requestCode == FILECHOOSER_RESULTCODE) {
			if (null == mUploadMessage) {
				return;
			}
			// if (intent == null && imageUri != null
			// && !"".equals(imageUri.toString())) {
			// mUploadMessage.onReceiveValue(imageUri);
			// mUploadMessage = null;
			// } else {
			// Uri result = intent == null || resultCode != RESULT_OK ? null
			// : intent.getData();
			// mUploadMessage.onReceiveValue(result);
			// mUploadMessage = null;
			// }
		}
	}

	@Override
	public void requestSuccessful(String jsonString, int taskType) {
		// TODO Auto-generated method stub

	}

}
