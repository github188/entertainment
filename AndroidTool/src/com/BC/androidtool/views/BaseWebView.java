package com.BC.androidtool.views;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions.Callback;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;

public class BaseWebView extends WebView implements Callback {
	WebViewClientImpl client;
	public boolean isProgressDialog = true;
	public String columnid = "";
	public boolean isNews = false;
	public boolean isLoaded = false;
	public boolean isToDownload = false;
	private Context mContext;

	// private Callback lCallback;// 地理位置回调
	private String lOrigin;// 地理位置请求来源

	public WebViewClientImpl getClient() {
		return client;
	}

	protected void onChangeMyWebViewStatus() {
		if (mContext == null)
			return;
		// else if(mContext instanceof BrowserAcitvity)
		// ((BrowserAcitvity) mContext).onChangeMyWebViewStatus(this);
	}

	public void setProgressDialog(boolean isProgressDialog) {
		this.isProgressDialog = isProgressDialog;
	}

	public BaseWebView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mContext = context;
		Init();
	}

	public BaseWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mContext = context;
		Init();
	}

	public BaseWebView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		// TODO Auto-generated constructor stub
		Init();
	}

	// @TargetApi(5)
	private void Init() {
		// AppInit.getAppinit().getConnectHelper().connect(Config.getConfig().getCmwapIap(),
		// Config.getConfig().KGprsConnectTimeout);
		// setBackgroundColor(0);//背景透明
		client = new WebViewClientImpl(this.getContext());
		setWebViewClient(client);
		// 设置纵向滚动条以叠加（浮动）的方式显示，不占用空间
		this.setVerticalScrollbarOverlay(true);
		// setVerticalScrollBarEnabled(false);
		this.setDownloadListener(new DownloadListener() {

			@Override
			public void onDownloadStart(String url, String userAgent,
					String contentDisposition, String mimetype,
					long contentLength) {
				Uri uri = Uri.parse(url);
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				getContext().startActivity(intent);
				// Log.e("downloadInfo---->",
				// "url:"+url+"\nuserAgent:"+userAgent+"\ncontentdisposition:"+contentDisposition
				// +"\nmime:"+ mimetype +"\ncontentlength:"+contentLength);
				// Intent intent = new
				// Intent(getContext(),DownLoadActivity.class);
				// intent.putExtra("path", url);
				// getContext().startActivity(intent);
			}

		});
		this.clearCache(false);
		WebSettings bs = getSettings();
		// bs.setDefaultTextEncodingName("UTF-8");
		bs.setBuiltInZoomControls(true);
		bs.setJavaScriptEnabled(true);

		bs.setSupportMultipleWindows(true);// 多点触摸
		bs.setSupportZoom(false);// 是否支持放大缩小
		// WebView.enablePlatformNotifications();// cmwap获取不到数据的解决方法
		this.setHorizontalScrollBarEnabled(false);

		/**
		 * 配置支持HTML5本地存储
		 */
		bs.setDatabaseEnabled(true);// 启用数据库
		String dir = mContext.getDir("database", Context.MODE_PRIVATE)
				.getPath();
		bs.setDatabasePath(dir);// 设置数据库路径
		bs.setDomStorageEnabled(true);// 使用localStorage则必须打开

		/**
		 * 配置支持构建html5离线应用
		 */
		bs.setAppCacheEnabled(true);// 开启应用程序缓存
		String dirCache = mContext.getDir("cache", Context.MODE_PRIVATE)
				.getPath();
		bs.setAppCachePath(dirCache);// 设置应用缓存的路径
		bs.setCacheMode(WebSettings.LOAD_DEFAULT); // 设置缓存的模式
		bs.setAppCacheMaxSize(1024 * 1024 * 8); // 设置应用缓存的最大尺寸

		/**
		 * 配置支持html5定位
		 */
		bs.setJavaScriptCanOpenWindowsAutomatically(true);
		bs.setGeolocationEnabled(true);
		// 设置定位的数据库路径
		bs.setGeolocationDatabasePath(dir);
		MyWebClient geo = new MyWebClient();
		this.setWebChromeClient(geo);

	}

	public void setShowErrorPage(boolean isShowErrorPage) {
		if (client != null) {
			client.setShowErrorPage(isShowErrorPage);
		}
	}

	@Override
	public void loadUrl(String url) {
		// TODO Auto-generated method stu
		if ("file:///android_asset/error/index.html".equalsIgnoreCase(url)) {
			goBack();
			return;
		}
		if (isNews) {
			if (!isLoaded) {
				isLoaded = true;
				url = getUrlWithExtra(url);
				Log.d("webviewURL", url);

				super.loadUrl(url);
				if (client != null) {
					// ProgressBar bar = client.getProgressBar();
					// if (bar != null) {
					// bar.setVisibility(View.VISIBLE);
					// }
					// client.setZoomBtnVisibility(View.INVISIBLE);
					if (isProgressDialog) {
						client.showCustomProgressDialog();
					}
				}
			}
			this.isLoaded = false;
			return;
		}
		url = getUrlWithExtra(url);
		super.loadUrl(url);
		if (client != null) {
			// ProgressBar bar = client.getProgressBar();
			// if (bar != null) {
			// bar.setVisibility(View.VISIBLE);
			// }
			// client.setZoomBtnVisibility(View.INVISIBLE);
			if (isProgressDialog) {
				client.showCustomProgressDialog();
			}
		}
		Log.d("webviewURL", url);

	}

	public String getUrlWithExtra(String url) {
		if (url == null || "".equals(url)) {
			return "";
		}

		if (url.endsWith(".html") || isToDownload) {
			return url;
		}

		if (url.indexOf("?") < 0) {
			url = url + "?";
		} else if (url.indexOf("&") > url.indexOf("?") || url.indexOf("&") < 0) {
			url = url + "&";
		}

		if (url.indexOf("token=") < 0) {
			// if (Config.token != null){// && Config.token.length() >= 2) {
			// url = url + "token=" + Config.token;
			// Log.e("Michael",url);
			// }
		}

		if (url.indexOf("pt=") < 0) {
			url = url + "&pt=pps";
		}

		if (url.indexOf("showinhwclient=") < 0) {
			url = url + "&showinhwclient=1";
		}

		url = url + "&ua=" + android.os.Build.MODEL.trim();
		return url;
	}

	public void showProgressDialog() {
		if (client != null) {
			client.showProgressDialog();
		}
	}

	public boolean isLoaded() {
		return client.isLoaded;
	}

	public String getLastUrl() {

		return client.mUrl;

	}

	@Override
	public void setVisibility(int visibility) {

		super.setVisibility(visibility);
		if (visibility == View.INVISIBLE && client != null) {
			// ProgressBar bar = client.getProgressBar();
			// if (bar != null) {
			// bar.setVisibility(View.INVISIBLE);
			// }

		}
	}

	@Override
	public void invoke(String arg0, boolean arg1, boolean arg2) {

	}

	class MyWebClient extends WebChromeClient {
		private Uri imageUri;

		// For Android > 4.1
		public void openFileChooser(ValueCallback<Uri> uploadMsg,
				String acceptType, String capture) {
			openFileChooser(uploadMsg);
		}

		// Andorid 3.0 +
		public void openFileChooser(ValueCallback<Uri> uploadMsg,
				String acceptType) {
			openFileChooser(uploadMsg);
		}

		// Android 3.0

		public void openFileChooser(ValueCallback<Uri> uploadMsg) {

			// if (mContext instanceof BrowserAcitvity ) {
			// try{
			// // File imageStorageDir = new File(((ColumnBrowserAcitvity)
			// mContext).getApplicationContext().getCacheDir().getPath());
			// File imageStorageDir = new
			// File(Environment.getExternalStorageDirectory().getPath()+"/skyarm/");
			// // Create the storage directory if it does not exist
			// if (!imageStorageDir.exists()) {
			// imageStorageDir.mkdirs();
			// }
			// File file = new File(imageStorageDir + File.separator + "IMG_"
			// + String.valueOf(System.currentTimeMillis()) + ".jpg");
			// imageUri = Uri.fromFile(file);
			// ((BrowserAcitvity) mContext).imageUri = imageUri;
			//
			// List<Intent> cameraIntents = new ArrayList<Intent>();
			// Intent captureIntent = new Intent(
			// android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			// PackageManager packageManager = ((BrowserAcitvity)
			// mContext).getPackageManager();
			// List<ResolveInfo> listCam = packageManager
			// .queryIntentActivities(captureIntent, 0);
			// for (ResolveInfo res : listCam) {
			// String packageName = res.activityInfo.packageName;
			// Intent i = new Intent(captureIntent);
			// i.setComponent(new ComponentName(res.activityInfo.packageName,
			// res.activityInfo.name));
			// i.setPackage(packageName);
			// i.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			//
			// cameraIntents.add(i);
			// }
			//
			// ((BrowserAcitvity) mContext).mUploadMessage = uploadMsg;
			// Intent i = new Intent(Intent.ACTION_GET_CONTENT);
			// i.addCategory(Intent.CATEGORY_OPENABLE);
			// i.setType("image/*");
			// Intent chooserIntent = Intent.createChooser(i, "选择图片");
			// chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,
			// cameraIntents.toArray(new Parcelable[] {}));
			// ((BrowserAcitvity)
			// mContext).startActivityForResult(chooserIntent,
			// BrowserAcitvity.FILECHOOSER_RESULTCODE);
			// }catch(Exception e){
			// e.printStackTrace();
			// }
			// }
		}

		// 扩充缓存的容量
		@Override
		public void onReachedMaxAppCacheSize(long spaceNeeded,
				long totalUsedQuota, WebStorage.QuotaUpdater quotaUpdater) {
			// quotaUpdater.updateQuota(spaceNeeded * 2);
		}

		// 扩充数据库的容量
		@Override
		public void onExceededDatabaseQuota(String url,
				String databaseIdentifier, long currentQuota,
				long estimatedSize, long totalUsedQuota,
				WebStorage.QuotaUpdater quotaUpdater) {
			// quotaUpdater.updateQuota(estimatedSize * 2);
		}

		// 配置权限
		@Override
		public void onGeolocationPermissionsShowPrompt(String origin,
				Callback callback) {
			try {
				// lCallback = callback;
				lOrigin = origin;
				Builder builder = new Builder(mContext);
				builder.setTitle("地理位置授权");
				builder.setMessage("允许" + origin + "访问您当前的地理位置信息？");
				builder.setPositiveButton(android.R.string.ok,
						new AlertDialog.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// lCallback.invoke(lOrigin, true, true);
							}
						});
				builder.setNegativeButton(android.R.string.cancel,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// lCallback.invoke(lOrigin, false, false);
							}
						});
				builder.setCancelable(false);
				builder.create();
				builder.show();

				super.onGeolocationPermissionsShowPrompt(origin, callback);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		// 处理javascript中的alert
		@Override
		public boolean onJsAlert(WebView view, String url, String message,
				final JsResult result) {
			// 构建一个Builder来显示网页中的对话框
			Builder builder = new Builder(mContext);
			builder.setTitle("温馨提示");
			builder.setMessage(message);
			builder.setPositiveButton(android.R.string.ok,
					new AlertDialog.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							result.confirm();
						}
					});
			builder.setCancelable(false);
			builder.create();
			builder.show();
			return true;
		};

		// 处理javascript中的confirm
		@Override
		public boolean onJsConfirm(WebView view, String url, String message,
				final JsResult result) {
			Builder builder = new Builder(mContext);
			builder.setTitle("温馨提示");
			builder.setMessage(message);
			builder.setPositiveButton(android.R.string.ok,
					new AlertDialog.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							result.confirm();
						}
					});
			builder.setNegativeButton(android.R.string.cancel,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							result.cancel();
						}
					});
			builder.setCancelable(false);
			builder.create();
			builder.show();
			return true;
		};

		// 设置网页加载的进度条
		@Override
		public void onProgressChanged(WebView view, int progress) {
			// if(mContext instanceof BrowserAcitvity){
			// ((BrowserAcitvity) mContext).progressbar(view,progress);
			// }
			super.onProgressChanged(view, progress);

		}

		// 设置应用程序的标题title
		@Override
		public void onReceivedTitle(WebView view, String title) {
			super.onReceivedTitle(view, title);
		}

		// 打印web调试信息
		@Override
		public void onConsoleMessage(String message, int lineNumber,
				String sourceID) {
			Log.d("webchrome_consoleMessage", message + " -- From line "
					+ lineNumber + " of " + sourceID);
		}

		// 获取到网址logo
		@Override
		public void onReceivedIcon(WebView view, Bitmap icon) {
			super.onReceivedIcon(view, icon);
		}
	}

}
