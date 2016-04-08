package com.BC.androidtool.views;

import java.util.HashMap;
import java.util.regex.Pattern;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.BC.androidtool.utils.Utils;

public class WebViewClientImpl extends WebViewClient {
	public interface WebViewClientImplLs {
		void WebViewClientImplLs();
	}

	WebViewClientImplLs clientImplLs;
	private Context activity;
	// private ProgressBar progressbar;
	// private View zoomInBtn;
	// private View zoomOutBtn;
	// private int progressValue = 0;
	// private Timer timer;
	public boolean isLoaded = false;

	public String mUrl;
	private Dialog mProgressDlg = null;
	private NextWeb nextWeb;
	private boolean isShowErrorPage = true;

	public WebViewClientImplLs getClientImplLs() {
		return clientImplLs;
	}

	public void setClientImplLs(WebViewClientImplLs clientImplLs) {
		this.clientImplLs = clientImplLs;
	}

	public interface NextWeb {
		public void nextWebUrl(String str);
	}

	public void setNextWeb(NextWeb nextWeb) {
		this.nextWeb = nextWeb;
	}

	public WebViewClientImpl(Context activity) {
		this.activity = activity;
		// timer = new Timer();
		// timer.scheduleAtFixedRate(new MyTimerTask(this), 1, 100);
	}

	protected void onChangeMyWebViewStatus() {
		if (activity == null)
			return;
		// else if (activity instanceof BrowserAcitvity) {
		// BaseWebView webView = ((BrowserAcitvity) activity).mWeb;
		// if (webView != null)
		// ((BrowserAcitvity) activity).onChangeMyWebViewStatus(webView);
		// }
	}

	@Override
	public void onReceivedError(WebView view, int errorCode,
			String description, String failingUrl) {
		// if(errorCode==WebViewClient.ERROR_HOST_LOOKUP){
		view.stopLoading();
		onPageFinished(view, failingUrl);
		if (this.isShowErrorPage) {
			view.loadUrl("file:///android_asset/error/index.html");
		}

		return;
		// }
	}

	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		try {
			if (url != null) {
				Log.d("onFling", url);
				mUrl = url;
				if (url.startsWith("tel:")) {
					Intent intent = new Intent("android.intent.action.DIAL",
							Uri.parse(url));
					activity.startActivity(intent);
					return true;
				}

				if (url.startsWith("wtai:")) {
					int position = 0;
					if (url.indexOf(";") > 0) {
						position = url.indexOf(";");
					} else if (url.indexOf("；") > 0) {
						position = url.indexOf("；");
					}
					if (position != 0) {
						url = url.substring(position + 1).trim();

						Utils.callTelephoneToDIAL(url, activity);
						return true;
					}
				}

				if (nextWeb != null) {
					nextWeb.nextWebUrl(url);
				}
				// NavigationBar nav=NavigationBar.getCurrentNav();
				// BrowserDelegate sd=new BrowserDelegate(nav.getSubArea(),nav);
				// HashMap<String,Object> params=new HashMap<String,Object>();
				// params.put(BrowserDelegate.URL_KEY, url);
				//
				// sd.open(params);
				String title = "";
				boolean isFind = false;

			}
			showCustomProgressDialog();
		} catch (Exception e) {

			Log.e("onFill", e.toString());
		}
		return super.shouldOverrideUrlLoading(view, url);

	}

	private HashMap<String, String> parserParam(String url) {
		int pos = url.indexOf('?');
		if (pos >= 0) {
			String params = url.substring(pos + 1);
			Pattern pattern = Pattern.compile("&");
			String[] strs = pattern.split(params);
			if (strs != null && strs.length > 0) {
				HashMap<String, String> ret = new HashMap<String, String>();
				for (String p : strs) {
					int index = p.indexOf('=');
					if (index > 0) {
						ret.put(p.substring(0, index), p.substring(index + 1));
					}
				}
				return ret;
			} else {
				return null;
			}
		} else {
			return null;
		}

	}

	void openVideo(WebView view, String url) {
		// Intent intent = new Intent(activity, VideoActivity.class);
		// intent.putExtra("VideoPath", url);
		// activity.startActivity(intent);

	}

	@Override
	public void onPageFinished(WebView view, String url) {

		super.onPageFinished(view, url);
		// if (mProgressDlg != null) {
		// mProgressDlg.dismiss();
		// mProgressDlg = null;
		// }
		// if (activity instanceof BrowserAcitvity) {
		// ((BrowserAcitvity) activity).load_is_done();
		// }
		if (clientImplLs != null) {
			clientImplLs.WebViewClientImplLs();
		}
		onChangeMyWebViewStatus();
		// //System.gc();
		// if (progressbar != null) {
		// progressbar.setVisibility(View.INVISIBLE);
		// }
		// setZoomBtnVisibility(View.VISIBLE);
	}

	// public ProgressBar getProgressBar() {
	// return progressbar;
	// }

	// public void setProgressBar(ProgressBar progress) {
	// this.progressbar = progress;
	// if(progressbar!=null)
	// this.progressbar.setMax(100);
	// }

	// @Override
	// public void OnTimerCall() {
	//
	// if (progressbar != null && progressbar.getVisibility() == View.VISIBLE) {
	// progressValue++;
	// progressValue = progressValue < 100 ? progressValue : 0;
	// progressbar.setProgress(progressValue);
	// }
	//
	// }

	// public void setZoomBtnVisibility(int invisible) {
	//
	// if(zoomInBtn!=null){
	// zoomInBtn.setVisibility(invisible);
	// }
	// if(zoomOutBtn!=null){
	// zoomOutBtn.setVisibility(invisible);
	// }
	// }
	// public View getZoomInBtn() {
	// return zoomInBtn;
	// }
	// public void setZoomInBtn(View zoomInBtn) {
	// this.zoomInBtn = zoomInBtn;
	// }
	// public View getZoomOutBtn() {
	// return zoomOutBtn;
	// }
	// public void setZoomOutBtn(View zoomOutBtn) {
	// this.zoomOutBtn = zoomOutBtn;
	// }

	public void showProgressDialog() {
		mProgressDlg = ProgressDialog.show(activity, null, "数据加载中...", true);
		mProgressDlg.setCancelable(true);
	}

	public void showCustomProgressDialog() {

		// if (activity instanceof BrowserAcitvity) {
		// ((BrowserAcitvity) activity).loading();
		// }
		// if (mProgressDlg != null) {
		// return;
		// }
		//
		// mProgressDlg = CustomProgressDialog.createDialog(activity);
		// ((CustomProgressDialog) mProgressDlg).setMessage("数据努力加载中...");
		// mProgressDlg.setCancelable(true);
		// mProgressDlg.show();
		// } catch (Exception e) {
		// Log.e("onFill",e.toString());
		// Toast.makeText(activity, "数据加载中。。。", Toast.LENGTH_SHORT).show();
		// }
	}

	public void setShowErrorPage(boolean isShowErrorPage) {
		this.isShowErrorPage = isShowErrorPage;
	}
}
