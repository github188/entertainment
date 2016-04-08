package com.BC.androidtool.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class MyDountChartFill extends View {
	float jibenAngle = 0f;
	float gangweiAngle = 0f;
	float xingweiAngle = 0f;
	float jibenL = 0f;
	float gangweiL = 0f;
	float xingweiL = 0f;
	float jibenX = 0f;
	float gangweiX = 0f;
	float xingweiX = 0f;

	public int red = 0;
	public int yellow = 0;
	public int green = 0;

	public MyDountChartFill(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	public MyDountChartFill(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public MyDountChartFill(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStyle(Style.FILL);

		Paint paintText = new Paint();
		paintText.setColor(Color.BLACK);
		paintText.setTextSize(dip2px(getContext(), 18));
		paintText.setStrokeWidth(8);

		// int ringWidth = getWidth() > getHeight() ? getHeight() / 6
		// : getWidth() / 6; // 圆环宽度

		RectF rect2;

		rect2 = new RectF(0 + (getWidth() - getHeight()) / 2, 0, getHeight()
				+ (getWidth() - getHeight()) / 2, getHeight());

		paint.setARGB(255, 127, 255, 212);
		// paint.setStrokeWidth(ringWidth);

		paint.setColor(Color.parseColor("#d8d8d8"));
		canvas.drawArc(rect2, 180, 361, false, paint);

		float pd = 0;
		float sum = red + yellow + green;
		Path path;
		float d = red / sum * 360;
		jibenL = d / 100;
		jibenX = d;
		paint.setColor(Color.RED);
		canvas.drawArc(rect2, pd, 0 + jibenAngle, true, paint);
		if (red > 0) {
			path = new Path();
			path.addArc(rect2, pd + d / 2, d);
			canvas.drawTextOnPath(red + "", path, 0, dip2px(getContext(), 30),
					paintText);
		}
		pd += d;

		d = yellow / sum * 360;
		gangweiL = d / 100;
		gangweiX = d;
		paint.setColor(Color.YELLOW);
		canvas.drawArc(rect2, pd, 0 + gangweiAngle, true, paint);
		if (yellow > 0) {
			path = new Path();
			path.addArc(rect2, pd + d / 2, d);
			canvas.drawTextOnPath(yellow + "", path, 0,
					dip2px(getContext(), 30), paintText);
		}
		pd += d;

		d = green / sum * 360;
		xingweiL = d / 100;
		xingweiX = d;
		paint.setColor(Color.GREEN);
		canvas.drawArc(rect2, pd, 0 + xingweiAngle, true, paint);
		if (green > 0) {
			path = new Path();
			path.addArc(rect2, pd + d / 2, d);
			canvas.drawTextOnPath(green + "", path, 0,
					dip2px(getContext(), 30), paintText);
		}
	}

	public void starUpate() {
		UpdateTextTask updateTextTask = new UpdateTextTask(this.getContext());
		updateTextTask.execute();
	}

	public void update() {
		jibenAngle += jibenL;
		gangweiAngle += gangweiL;
		xingweiAngle += xingweiL;
		if (jibenAngle > jibenX) {
			jibenAngle = jibenX;
		}
		if (gangweiAngle > gangweiX) {
			gangweiAngle = gangweiX;
		}
		if (xingweiAngle > xingweiX) {
			xingweiAngle = xingweiX;
		}
		// Log.d("shuzhi", "jibenAngle = " + jibenAngle);
		// Log.d("shuzhi", "gangweiAngle = " + gangweiAngle);
		// Log.d("shuzhi", "xingweiAngle = " + xingweiAngle);
		postInvalidate();
	}

	private void cleanChart() {
		// TODO Auto-generated method stub
		jibenAngle = jibenX;
		gangweiAngle = gangweiX;
		xingweiAngle = xingweiX;
		postInvalidate();
	}

	class UpdateTextTask extends AsyncTask<Void, Integer, Integer> {
		private Context context;

		UpdateTextTask(Context context) {
			this.context = context;
		}

		/**
		 * 运行在UI线程中，在调用doInBackground()之前执行
		 */
		@Override
		protected void onPreExecute() {
			jibenAngle = 0f;
			gangweiAngle = 0f;
			xingweiAngle = 0f;
			jibenL = 0f;
			gangweiL = 0f;
			xingweiL = 0f;
			jibenX = 0f;
			gangweiX = 0f;
			xingweiX = 0f;
		}

		@Override
		protected Integer doInBackground(Void... params) {
			int i = 0;
			while (i < 100) {
				i++;
				publishProgress(i);
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Integer integer) {
			// Toast.makeText(context, "执行完毕", Toast.LENGTH_SHORT).show();
			cleanChart();
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			MyDountChartFill.this.update();
		}
	}

	/* 根据手机的分辨率从 dp 的单位 转成为 px(像素) */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

}
