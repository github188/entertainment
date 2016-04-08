package com.BC.androidtool.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

public class MyVideoView extends VideoView {
	public MyVideoView(Context context) {
		// TODO Auto-generated constructor stub
		super(context);
	}

	public MyVideoView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyVideoView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub:
	}

	// 重點在此，override這個 function 才可以正常滿版!;
	// protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	// int width = getDefaultSize(0, widthMeasureSpec);
	// int height = getDefaultSize(0, heightMeasureSpec);
	// setMeasuredDimension(width, height);
	// }
}
