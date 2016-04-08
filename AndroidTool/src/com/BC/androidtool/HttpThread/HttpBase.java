package com.BC.androidtool.HttpThread;

import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;






public class HttpBase{
	// private HttpURLConnection mConnect;
	//如果是android系统必须设为true,ophone系统必须设置为false
	static int KHttpConnectTimeout = 80000;
	public static final int TIMEOUT_READ = 90000;
	protected HttpBase() {
		
	}


	public static InputStream getInputStream(String url) {
		InputStream input = null;
		try {
			HttpGet getRequest = new HttpGet(url);
			DefaultHttpClient client = new DefaultHttpClient();
			//设置超时时间
			client.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, 15000);
			client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 15000);
			HttpResponse response = client.execute(getRequest);
			HttpEntity entity = response.getEntity();
			input= entity.getContent();
		}
		catch (java.lang.Error e) {
			//Log.e("onFling","getInputStream"+e.toString());
		}
		catch (Exception e) {
			//Log.e("onFling","getInputStream"+e.toString());
		}
		return input;
	}

}
