package com.BC.androidtool.HttpThread;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import android.util.Log;

/**
 * 任务的实体类
 * 
 * @ClassName: BaseTask
 * @Description: TODO
 * @author dengxuxing
 * @date 2013-1-19 下午12:12:58
 * 
 */

public abstract class HttpBaseTask extends BaseTask {
	public static final String UTF8_ENCODING = "UTF-8";
	public InfoHandler infoHandler;
	protected int taskType = -1; // 任务类型
	protected String content; // 需要发送的报文
	protected String url; // url地址

	protected boolean isUsePostType = false;
	protected boolean isDefaultPostType = false;
	public static CookieStore cookieStore = null;

	// 存放数据的对象，需要返回数据时，都放在里面
	protected HashMap<String, Object> result = new HashMap<String, Object>();

	public HttpBaseTask(int taskType, String content, String url) {
		this.taskType = taskType;
		this.content = content;
		this.url = url;
		// if (content == null || content.length() == 0) {
		// isUsePostType = false;
		// }
	}

	// 设置请求是否使用post方式
	public void setRequestType(boolean isUsePostType) {
		this.isUsePostType = isUsePostType;
	}

	// 任务完成时，回调
	public void callBack() {
		if (infoHandler != null) {
			result.put("taskType", taskType);
			infoHandler.sendMessage(infoHandler.obtainMessage(
					InfoHandler.STATE_RESULT_RECEIVED, errorCode, 0, result));
		}
	}

	@Override
	public void call() {

		InputStream in = null;
		// dealwithData(in);
		// callBack();
		try {
			in = getData();
			if (isCancel()) {
				return;
			}
			if (in != null) {
				dealwithData(in);
				in.close();
				in = null;
			} else {
				result.put("message", message);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("onFill", e.toString());

		}
		try {
			if (in != null) {
				in.close();
			}
			callBack();
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	/**
	 * 获取远程数据，如果需要可以子类中可以重写这个方法进行一些额外的处理 拼接url
	 * 
	 * @return
	 */
	public InputStream getData() {
		return getInputStream(url, null);
	}

	/**
	 * 处理远程请求返回的数据
	 * 
	 * @param in
	 */
	public abstract void dealwithData(InputStream in);

	public InputStream getInputStream(String url, List<NameValuePair> params) {
		// System.out.println("url==" + url);
		InputStream in = null;
		try {
			if (isUsePostType) {
				in = getInputStreamWithPostType4WebService(url);
				// getInputStreamWithPostType4WebService(url);
				// in = getInputStreamWithPostType(url);
			} else {

				if (params != null) {
					in = getInputStreamWithPostType(params, url);
				} else {
					in = getInputStreamWithGetType(url);
				}
			}
		} catch (Exception e) {
			message = e.toString();
			Log.e("onFling", "out.close:" + e.toString());
			e.printStackTrace();
		}
		return in;
	}

	private InputStream getInputStreamWithPostType(List<NameValuePair> params,
			String url) {
		// TODO Auto-generated method stub
		InputStream in = null;
		Log.e("httptask", "url:" + url + "\nreq:" + params);
		try {
			DefaultHttpClient client = new DefaultHttpClient();
			// 设置超时时间
			client.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, 50000);
			client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
					50000);
			HttpPost method = new HttpPost(url);
			if (cookieStore != null) {
				client.setCookieStore(cookieStore);
			}
			method.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			Log.e("httptask", "method:" + method.getMethod());
			HttpResponse response = client.execute(method);
			cookieStore = client.getCookieStore();
			Log.e("shuzhi", "cookieStore:" + cookieStore);
			in = response.getEntity().getContent();

		} catch (Exception e) {
			message = "网络异常.";
			// message= e.toString();
			Log.e("onFling", "out.close:" + e.toString());
			e.printStackTrace();
		}
		return in;
	}

	public InputStream getInputStreamWithPostType(String url) {
		InputStream in = null;
		try {
			// Log.e("httptask", "url:" + url + "\nreq:" + content);
			DefaultHttpClient client = new DefaultHttpClient();
			// 设置超时时间
			client.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, 50000);
			client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
					50000);

			HttpPost method = new HttpPost(url);

			StringEntity se;
			if (content != null) {
				se = new StringEntity(content, HTTP.UTF_8);
				method.setEntity(se);
			}

			HttpResponse response = client.execute(method);
			in = response.getEntity().getContent();

			Log.e("httptask", "url:" + url + "\nreq:" + content);
		} catch (Exception e) {
			message = "网络异常.";
			// message= e.toString();
			Log.e("onFling", "out.close:" + e.toString());
			e.printStackTrace();
		}
		return in;
	}

	// public String getInputStreamWithPostType4WebService(String url) {
	// try {
	// Log.e("httptask", "url:" + url + "\nreq:" + content);
	// final String SERVER_URL = url; // 带参数的WebMethod
	// HttpPost request = new HttpPost(SERVER_URL); // 根据内容来源地址创建一个Http请求
	// request.addHeader("Content-Type", "application/json; charset=utf-8");//
	// 必须要添加该Http头才能调用WebMethod时返回JSON数据
	// HttpEntity bodyEntity = new StringEntity(content, "utf8");//
	// 参数必须也得是JSON数据格式的字符串才能传递到服务器端，否则会出现"{'Message':'xxx是无效的JSON基元'}"的错误
	// request.setEntity(bodyEntity);
	//
	// HttpParams httpParameters = new BasicHttpParams();
	// // Set the timeout in milliseconds until a connection is
	// // established.
	// // The default value is zero, that means the timeout is not used.
	// int timeoutConnection = 3 * 1000;
	// HttpConnectionParams.setConnectionTimeout(httpParameters,
	// timeoutConnection);
	// // Set the default socket timeout (SO_TIMEOUT)
	// // in milliseconds which is the timeout for waiting for data.
	// int timeoutSocket = 5 * 1000;
	// HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
	// Log.e("httptask", "start");
	// DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
	// HttpResponse httpResponse = httpClient.execute(request); // 发送请求并获取反馈
	// Log.e("httptask", "end");
	// Log.e("httptask", "httpResponse.getStatusLine().getStatusCode() = "
	// + httpResponse.getStatusLine().getStatusCode());
	// // 解析返回的内容
	// if (httpResponse.getStatusLine().getStatusCode() == 200) //
	// StatusCode为200表示与服务端连接成功
	// {
	// StringBuilder builder = new StringBuilder();
	// BufferedReader bufferedReader2 = new BufferedReader(
	// new InputStreamReader(httpResponse.getEntity()
	// .getContent()));
	// for (String s = bufferedReader2.readLine(); s != null; s =
	// bufferedReader2
	// .readLine()) {
	// builder.append(s);
	// }
	//
	// String resultString = builder.toString();
	// Log.d("shuzhi", resultString);
	// return resultString;
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// return "";
	// }
	// return "";
	// }

	public InputStream getInputStreamWithPostType4WebService(String url) {
		InputStream in = null;
		try {
			Log.e("httptask", "url:" + url + "\nreq:" + content);
			DefaultHttpClient client = new DefaultHttpClient();
			// 设置超时时间
			client.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, 50000);
			client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
					50000);

			HttpPost method = new HttpPost(url);
			method.addHeader("Content-Type", "application/json; charset=utf-8");
			StringEntity se;
			if (content != null) {
				se = new StringEntity(content, HTTP.UTF_8);
				method.setEntity(se);
			}
			HttpResponse response = client.execute(method);
			in = response.getEntity().getContent();
			Log.e("httptask", "httpResponse.getStatusLine().getStatusCode() = "
					+ response.getStatusLine().getStatusCode());
			Log.e("httptask", "url:" + url + "\nreq:" + content);
		} catch (Exception e) {
			message = "网络异常.";
			// message= e.toString();
			Log.e("onFling", "out.close:" + e.toString());
			e.printStackTrace();
		}
		return in;
	}

	public InputStream getInputStreamWithGetType(String url) {
		try {
			HttpGet getRequest = new HttpGet(url);
			DefaultHttpClient client = new DefaultHttpClient();
			// 设置超时时间
			client.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, 15000);
			client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
					15000);
			HttpResponse response = client.execute(getRequest);
			return response.getEntity().getContent();
			// }
		} catch (Exception e) {
			message = e.toString();
			e.printStackTrace();
			Log.e("onFill", e.toString());
		}
		return null;
	}

}