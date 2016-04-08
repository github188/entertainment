package com.BC.androidtool.HttpThread;

import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;

/**
 * 
 * <p>图片提供者接口 </p>
 * @author 吴旺春、邓旭兴
 * @version 1.00
 * @modify
 * @see
 * @copyright Huamai GuangZhou  www.skyarm.com
 */
public interface ImageProvider {
	public int showImage(String url,View imgView ,int inSampleSize);
	public int showImage(String url,View imgView,BitmapDrawable defaultDrawable,int inSampleSize);
	public int showImage(String url,ImageView imgView,BitmapDrawable defaultDrawable,int inSampleSize);
	public int showImage(String url,View imgView,int  defaultResId,int inSampleSize);
//	public int ImageShow(String url,View imgView,BaseAdapter adapter,int inSampleSize);
}
