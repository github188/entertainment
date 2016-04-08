package com.BC.entertainmentgravitation.wxapi.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;

import com.BC.entertainmentgravitation.wxapi.entity.WxOrder;
import com.BC.entertainmentgravitation.wxapi.entity.WxPrePayOrder;
import com.google.gson.Gson;

public class WxHttpUtil {
	
	private Handler mHandler;
	private URL url;
//	private String urlString = "http://182.92.179.220:8081/php/wxpay/wxpay.php";
	private String urlString = "http://182.92.179.220:8081/php/index.php?_mod=wxpay&_act=createorder";
	
	public WxHttpUtil(Handler mHandler)
	{
		this.mHandler = mHandler;
		try {
			this.url = new URL(urlString);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	public String SubmitPrePayData(String params) {
        
    	//获得请求体
        byte[] data = params.getBytes();
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
                return dealResponseResult(inptStream);                     //处理服务器的响应结果
            }
        } catch (IOException e) {
        	Message message = new Message();
        	message.what = Constants.WX_NETWORK_ERROR;
        	mHandler.sendMessage(message);
        	e.printStackTrace();
        }
        return "";
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
        	Message message = new Message();
        	message.what = Constants.WX_NETWORK_ERROR;
        	mHandler.sendMessage(message);
            e.printStackTrace();
            System.out.print("---------IOException------------>" + e.getMessage().toString() + "\n");
        }
        resultData = new String(byteArrayOutputStream.toByteArray());
        System.out.print("-----------resultData---------->" + resultData + "\n");
        return resultData;
    }

    public WxOrder GetWxOrder(String jsonString)
    {
    	WxOrder wxOrder = new WxOrder();
    	JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(jsonString).getJSONObject("data");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    	if(jsonObject != null)
    	{
			try {
				wxOrder.setPrepayid(jsonObject.getString("prepay_id"));
				wxOrder.setNoncestr(jsonObject.getString("nonce_str"));
				wxOrder.setSign(jsonObject.getString("sign"));
			} catch (JSONException e) {
	        	Message message = new Message();
	        	message.what = Constants.WX_EXCEPTION_ERROR;
	        	mHandler.sendMessage(message);
				e.printStackTrace();
			}
    	}
    	return wxOrder;
    }
}
