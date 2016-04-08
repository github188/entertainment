package com.BC.entertainmentgravitation.wxapi.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class WxPayTask extends AsyncTask<String, Void, String>{

	private Handler handler;
	private URL url;
	private String urlString = "http://182.92.179.220:8081/php/wxpay/wxpay.php";
	private Message message = new Message();
	public WxPayTask(Handler handler)
	{
		this.handler = handler;
		try {
			this.url = new URL(urlString);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}
	
	@Override
	protected String doInBackground(String... params) {
		String reault = "";
    	//获得请求体
        byte[] data = params[0].getBytes();
		
        try {
			HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
			//设置连接超时时间
			httpURLConnection.setConnectTimeout(3000);
			
			//打开输入流，以便从服务器获取数据
			httpURLConnection.setDoInput(true);
			
			//打开输出流，以便向服务器提交数据
			httpURLConnection.setDoOutput(true);
			
			//设置以Post方式提交数据
			httpURLConnection.setRequestMethod("POST");
			
			//使用Post方式不能使用缓存
			httpURLConnection.setUseCaches(false);
			
			//设置请求体的类型是文本类型
			httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			
			//设置请求体的长度
			httpURLConnection.setRequestProperty("Content-Length", String.valueOf(data.length));
			
			//获得输出流，向服务器写入数据
			OutputStream outputStream = httpURLConnection.getOutputStream();
			outputStream.write(data);
			
			int response = httpURLConnection.getResponseCode();            //获得服务器的响应码
			if(response == HttpURLConnection.HTTP_OK) {
			    InputStream inptStream = httpURLConnection.getInputStream();
			    reault =  dealResponseResult(inptStream);                     //处理服务器的响应结果
			}
		} catch (Exception e) {
			message.what = Constants.WX_EXCEPTION_ERROR;
			handler.sendMessage(message);
			e.printStackTrace();
		}
		return reault;
	}

	/*
     * Function  :   处理服务器的响应结果（将输入流转化成字符串）
     * Param     :   inputStream服务器的响应输入流
     */
    public String dealResponseResult(InputStream inputStream) {
        String resultData = null;      //存储处理结果
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        try {
            while((len = inputStream.read(data)) != -1) {
                byteArrayOutputStream.write(data, 0, len);
            }
        } catch (IOException e) {
        	message.what = Constants.WX_EXCEPTION_ERROR;
        	handler.sendMessage(message);
            e.printStackTrace();
            Log.i("WxPayTask","---------IOException------------>" + e.getMessage().toString() + "\n");
        }
        resultData = new String(byteArrayOutputStream.toByteArray());
        Log.i("WxPayTask", "-----------resultData---------->" + resultData + "\n");
        return resultData;
    }
    
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
    	message.what = Constants.WX_PREPAY_OK;
    	message.obj = result;
    	handler.sendMessage(message);
    	
	}


}
