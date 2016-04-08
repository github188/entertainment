package com.BC.entertainmentgravitation.wxapi.util;

/**
 * 调起微信支付中定义的常量
 * 
 * https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_12&index=2
 * @author zhongwen 2016/3/23
 *
 */
public class Constants {
	//URL
	public static final String URLSTRING = "http://app.haimianyu.cn/wxpay/wxpay.php";
	
	public static final String URLQUERY = "http://app.haimianyu.cn/index.php?_mod=wxpay&_act=query";
	
	//应用ID
	public static final String APP_ID = "wx2044b77eca1acb02";
	
	//商户号
	public static final String MCH_ID = "1321312601";
	
	//商品或支付单简要描述
    public static final String BODY = "海绵娱娱币";
    
    //扩展字段 时间戳，请见接口规则-参数规定
    public static final String PACKAGE = "Sign=WXPay";
    
    //预支付网络异常
    public static final int WX_NETWORK_ERROR = 0x00;
    
    //预支付异常
    public static final int WX_EXCEPTION_ERROR = 0x06;
    
    //获取订单中
    public static final int WX_GETTING_ORDER = 0x02;
    
    //正常调起微信支付
    public static final int WX_CALL_BACK_OK = 0x03;
    
    //服务器请求错误
    public static final int WX_REQUEST_ERROR = 0x04;
    
    //请求预支付服务器正常
    public static final int WX_PREPAY_OK = 0x05;
    
    //微信支付
    public static final int PAY_WX =0x11;
    
    //支付宝支付
    public static final int PAY_ALI =0x12;
}
