package com.BC.androidtool.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.BC.androidtool.config.Config;

public class Textview_Typefaces extends TextView {

	// 系统自带字体
	// private static final int SANS = 1;
	// private static final int SERIF = 2;
	// private static final int MONOSPACE = 3;

	private int tf;

	// 添加字体类型
	private static final int LISHU = 4;

	public Textview_Typefaces(Context context) {
		super(context);
	}

	public Textview_Typefaces(Context context, AttributeSet attrs) {
		super(context, attrs);
		// init(context, attrs);
	}

	@Override
	public void setTypeface(Typeface tf) {
		// TODO Auto-generated method stub
		Log.d("shuzhi", "tf = " + Config.tf);
		if (Config.tf != null) {
			super.setTypeface(Config.tf);
		} else {
			super.setTypeface(tf);
		}
	}
	// private void init(Context context, AttributeSet attrs) {
	// // if (attrs != null) {
	// // TypedArray a =
	// // context.obtainStyledAttributes(attrs,R.styleable.TextView_Typefaces);
	// // tf = a.getInt(R.styleable.TextView_Typefaces_tf, tf);
	// // switch (tf) {
	// // case LISHU:
	// // Typeface mtf =
	// // Typeface.createFromAsset(context.getAssets(),"fonts/stliti.TTF");
	// // // Typeface mtf =
	// // Typeface.createFromAsset(context.getAssets(),"fonts/lishu.ttf");
	// // super.setTypeface(mtf);
	// // break;
	// // default:
	// // break;
	// // }
	// // }
	// }

}
