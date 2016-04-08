package com.BC.androidtool.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

import com.BC.androidtool.config.Config;

public class Button_Typefaces extends Button {
	private int tf;
	// 添加字体类型
	private static final int LISHU = 4;

	public Button_Typefaces(Context context) {
		super(context);
	}

	public Button_Typefaces(Context context, AttributeSet attrs) {
		super(context, attrs);
		// init(context, attrs);
	}

	@Override
	public void setTypeface(Typeface tf) {
		// TODO Auto-generated method stub
		if (Config.tf != null) {
			super.setTypeface(Config.tf);
		} else {
			super.setTypeface(tf);
		}
	}
	// private void init(Context context, AttributeSet attrs) {
	// if (attrs != null) {
	// TypedArray a = context.obtainStyledAttributes(attrs,
	// R.styleable.TextView_Typefaces);
	// tf = a.getInt(R.styleable.TextView_Typefaces_tf, tf);
	// switch (tf) {
	// case LISHU:
	// Typeface mtf = Typeface.createFromAsset(context.getAssets(),
	// "fonts/stliti.TTF");
	// // Typeface mtf =
	// // Typeface.createFromAsset(context.getAssets(),"fonts/lishu.ttf");
	// super.setTypeface(mtf);
	// break;
	// default:
	// break;
	// }
	// }
	// }

}
