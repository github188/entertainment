package com.BC.entertainmentgravitation.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

class CheckView extends View {

	String tr = "无";

	public String getTr() {
		return tr;
	}

	public void setTr(String tr) {
		this.tr = tr;
	}

	public CheckView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void draw(Canvas canvas) {
		Paint paint = new Paint();
		canvas.drawColor(Color.WHITE);
		paint.setColor(Color.RED);
		paint.setTextSize(15);
		canvas.drawText(tr, 10, 30, paint);
		super.draw(canvas);
	}

}
