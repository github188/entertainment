package com.BC.androidtool.views;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.View;

public class MyDountChart extends View {
	float jibenAngle = 0f;
	float gangweiAngle = 0f;
	float xingweiAngle = 0f;
	float jiazhiAngle = 0f;
	float zhuyewuAngle = 0f;
	float nianzhongAngle = 0f;
	float jibenL = 0f;
	float gangweiL = 0f;
	float xingweiL = 0f;
	float jiazhiL = 0f;
	float zhuyewuL = 0f;
	float nianzhongL = 0f;
	float jibenX = 0f;
	float gangweiX = 0f;
	float xingweiX = 0f;
	float jiazhiX = 0f;
	float zhuyewuX = 0f;
	float nianzhongX = 0f;

	ArrayList<Float> money = new ArrayList<Float>();

	public ArrayList<Float> getMoney() {
		return money;
	}

	public void setMoney(ArrayList<Float> money) {
		this.money = money;
	}

	public MyDountChart(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	public MyDountChart(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public MyDountChart(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStyle(Style.STROKE);

		Paint paintText = new Paint();
		paintText.setColor(Color.WHITE);
		paintText.setTextSize(dip2px(getContext(), 12));

		int ringWidth = getWidth() > getHeight() ? getHeight() / 6
				: getWidth() / 6; // 圆环宽度

		RectF rect2;

		rect2 = new RectF(0 + ringWidth / 2 + (getWidth() - getHeight()) / 2,
				0 + ringWidth / 2, getHeight() - ringWidth / 2
						+ (getWidth() - getHeight()) / 2, getHeight()
						- ringWidth / 2);

		paint.setARGB(255, 127, 255, 212);
		paint.setStrokeWidth(ringWidth);

		paint.setColor(Color.parseColor("#d8d8d8"));
		canvas.drawArc(rect2, 180, 361, false, paint);

		float pd = 0;
		Path path;
		for (int i = 0; i < money.size(); i++) {
			float d = money.get(i) / 100 * 360;
			switch (i) {
			case 0:
				jibenL = d / 100;
				jibenX = d;
				paint.setColor(Color.parseColor("#f90732"));
				canvas.drawArc(rect2, pd, 0 + jibenAngle, false, paint);
				if (money.get(i) > 0) {
					// path = new Path();
					// path.addArc(rect2, pd + d / 2, d);
					// canvas.drawTextOnPath(money.get(i) + "%", path, 0, 20,
					// paintText);
				}
				break;
			case 1:
				gangweiL = d / 100;
				gangweiX = d;
				paint.setColor(Color.parseColor("#5dca0c"));
				canvas.drawArc(rect2, pd, 0 + gangweiAngle, false, paint);
				if (money.get(i) > 0) {
					// path = new Path();
					// path.addArc(rect2, pd + d / 2, d);
					// canvas.drawTextOnPath(money.get(i) + "%", path, 0, 20,
					// paintText);
				}
				break;
			case 2:
				xingweiL = d / 100;
				xingweiX = d;
				paint.setColor(Color.parseColor("#a6d2eb"));
				canvas.drawArc(rect2, pd, 0 + xingweiAngle, false, paint);
				if (money.get(i) > 0) {
					// path = new Path();
					// path.addArc(rect2, pd + d / 2, d);
					// canvas.drawTextOnPath(money.get(i) + "%", path, 0, 20,
					// paintText);
				}
				break;
			case 3:
				jiazhiL = d / 100;
				jiazhiX = d;
				paint.setColor(Color.parseColor("#10b3a2"));
				canvas.drawArc(rect2, pd, 0 + jiazhiAngle, false, paint);
				// canvas.drawArc(rect2, pd, d, false, paint);
				// if (money.get(i) > 0) {
				// // canvas.drawArc(rect2, pd, d, false, paint);
				// xcalc.CalcArcEndPointXY(cirX, cirY, ringWidth + 0
				// + ringWidth / 2 + (getWidth() - getHeight()) / 2,
				// pd + d / 2);
				// canvas.drawText(money.get(i) + "%", xcalc.getPosX(),
				// xcalc.getPosY(), paintText);
				// }
				if (money.get(i) > 0) {
					// path = new Path();
					// path.addArc(rect2, pd + d / 2, d);
					// canvas.drawTextOnPath(money.get(i) + "%", path, 0, 20,
					// paintText);
				}
				break;
			case 4:
				zhuyewuL = d / 100;
				zhuyewuX = d;
				paint.setColor(Color.parseColor("#edb92e"));
				canvas.drawArc(rect2, pd, 0 + zhuyewuAngle, false, paint);
				// canvas.drawArc(rect2, pd, d, false, paint);
				// if (money.get(i) > 0) {
				// // canvas.drawArc(rect2, pd, d, false, paint);
				// xcalc.CalcArcEndPointXY(cirX, cirY, ringWidth + 0
				// + ringWidth / 2 + (getWidth() - getHeight()) / 2,
				// pd + d / 2);
				// canvas.drawText(money.get(i) + "%", xcalc.getPosX(),
				// xcalc.getPosY(), paintText);
				// }
				if (money.get(i) > 0) {
					// path = new Path();
					// path.addArc(rect2, pd + d / 2, d);
					// canvas.drawTextOnPath(money.get(i) + "%", path, 0, 20,
					// paintText);
				}
				break;
			case 5:
				nianzhongL = d / 100;
				nianzhongX = d;
				paint.setColor(Color.parseColor("#e66c8c"));
				canvas.drawArc(rect2, pd, 0 + nianzhongAngle, false, paint);
				if (money.get(i) > 0) {
					// path = new Path();
					// path.addArc(rect2, pd + d / 2, d);
					// canvas.drawTextOnPath(money.get(i) + "%", path, 0, 20,
					// paintText);
				}
				break;
			}
			pd += d;
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
		jiazhiAngle += jiazhiL;
		zhuyewuAngle += zhuyewuL;
		nianzhongAngle += nianzhongL;
		postInvalidate();
	}

	private void cleanChart() {
		// TODO Auto-generated method stub
		jibenAngle = jibenX;
		gangweiAngle = gangweiX;
		xingweiAngle = xingweiX;
		jiazhiAngle = jiazhiX;
		zhuyewuAngle = zhuyewuX;
		nianzhongAngle = nianzhongX;
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
			jiazhiAngle = 0f;
			zhuyewuAngle = 0f;
			nianzhongAngle = 0f;
			jibenL = 0f;
			gangweiL = 0f;
			xingweiL = 0f;
			jiazhiL = 0f;
			zhuyewuL = 0f;
			nianzhongL = 0f;
			jibenX = 0f;
			gangweiX = 0f;
			xingweiX = 0f;
			jiazhiX = 0f;
			zhuyewuX = 0f;
			nianzhongX = 0f;
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
			MyDountChart.this.update();
		}
	}

	/* 根据手机的分辨率从 dp 的单位 转成为 px(像素) */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

}
