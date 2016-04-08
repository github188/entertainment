package com.BC.entertainmentgravitation.wxapi.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.BC.entertainmentgravitation.wxapi.util.Constants;

public class WxOrder {
	
	private String appid = Constants.APP_ID;
	
	private String partnerid = Constants.MCH_ID;
	
	private String prepayid;
	
	private String wxPackage = Constants.PACKAGE;
	
	private String noncestr;
	
	private String sign;
	
//	private String timestamp = getFormatTimeStamp();
	private String timestamp;
	
	public String getAppid() {
		return appid;
	}

	public String getPartnerid() {
		return partnerid;
	}

	public String getPrepayid() {
		return prepayid;
	}

	public void setPrepayid(String prepayid) {
		this.prepayid = prepayid;
	}

	public String getWxPackage() {
		return wxPackage;
	}
	
	public String getNoncestr() {
		return noncestr;
	}

	public void setNoncestr(String noncestr) {
		this.noncestr = noncestr;
	}

	public String getTimestamp() {
		return timestamp;
	}
	
	public void setTimeStamp(String timestamp)
	{
		this.timestamp = timestamp;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	private String getFormatTimeStamp()
	{
	    Date date=new Date();  
	    SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMddHHmmss");  
	    String time=formatter.format(date);  
	    long seconds = 0;
		try {
			seconds = formatter.parse(time).getTime() / 1000;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//毫秒
	    return Long.toString(seconds);
	}
	
}
