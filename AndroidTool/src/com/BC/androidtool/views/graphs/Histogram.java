package com.BC.androidtool.views.graphs;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.Log;

public class Histogram {
	ArrayList<Coordinate> coordinates;
	Context context;
	float histogramWidth = 40;
	int compare = 3;
	int[] colors = { 0xFF00FF00, 0xFFFF0000, 0xFF0de1a3 };

	public ArrayList<Coordinate> getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(ArrayList<Coordinate> coordinates) {
		this.coordinates = coordinates;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public float getHistogramWidth() {
		return histogramWidth;
	}

	public void setHistogramWidth(float histogramWidth) {
		this.histogramWidth = histogramWidth;
	}

	public int getCompare() {
		return compare;
	}

	public void setCompare(int compare) {
		this.compare = compare;
	}

	public int[] getColors() {
		return colors;
	}

	public void setColors(int[] colors) {
		this.colors = colors;
	}

	public Histogram(Context context) {
		coordinates = new ArrayList<Coordinate>();
		this.context = context;
	}

	public void addPoint(Integer x, Float y, String showValue) {
		// TODO Auto-generated method stub
		Coordinate coordinate = new Coordinate(x, y);
		coordinate.setShowValue(showValue);
		coordinates.add(coordinate);
	}

	class Coordinate {
		Integer x;
		Float y;
		String showValue;

		public Coordinate(Integer x, Float y) {
			super();
			this.x = x;
			this.y = y;
		}

		public String getShowValue() {
			return showValue;
		}

		public void setShowValue(String showVilue) {
			this.showValue = showVilue;
		}
	}

	/**
	 * 
	 * @param canvas
	 * @param paint
	 * @param x0
	 *            X轴原点
	 * @param xMax
	 *            X轴最大值
	 * @param y0
	 *            Y轴原点
	 * @param yMax
	 *            Y轴最大值
	 * @param xShowMax
	 *            X轴能显示实际值的最大值
	 * @param yShowMax
	 *            Y轴能显示实际值的最大值
	 */
	public void drawHistogram(Canvas canvas, Paint paint, float x0, float xMax,
			float y0, float yMax, float xShowMax, float yShowMax) {
		// TODO Auto-generated method stub
		if (coordinates != null && coordinates.size() > 0) {
			for (int i = 0; i < coordinates.size(); i++) {
				Coordinate b = coordinates.get(i);
				int c = i % compare - compare / 2;
				// int t = (b.x - 1) / (compare);
				int t = b.x;
				float scale = (xMax - x0) / xShowMax;
				// Log.d("shuzhi", "c = " + c);
				if (compare % 2 == 0 && c == 0) {
					c = 1;
				}
				histogramWidth = scale / 3;
				float lx = (t + 1) * scale + histogramWidth / 2 * c
						* (compare - 1);
				float bx = x0 + lx;
				float by = (b.y / yShowMax) * (y0 - yMax);
				if (by == 0.0f) {
					by = 1.0f;
				}
				int colorId = i % compare;
				if (i > 0 && coordinates.get(i - 1).y - b.y > 0) {
					colorId = 2;
				}
				LinearGradient lg = new LinearGradient(0, y0 - by, 0, y0,
						colors[colorId], colors[colorId] - 0xEF000000,
						Shader.TileMode.REPEAT);
				paint.setShader(lg);
				canvas.drawRect(bx - histogramWidth / 2, y0 - by, bx
						+ histogramWidth / 2, y0, paint);

				if (b.getShowValue() != null) {
					paint.reset();
					if ((i % compare) == 0) {
						paint.setTextAlign(Paint.Align.RIGHT);
					} else {
						paint.setTextAlign(Paint.Align.LEFT);
					}
					paint.setStrokeWidth(1);
					paint.setTextSize(histogramWidth * 2 / 5);
					paint.setColor(Color.WHITE);
					// float offSet = 0;
					// if (i > 0 && Math.abs(coordinates.get(i - 1).y - b.y) >=
					// 9) {
					// offSet = 20;
					// }
					float f = Float.valueOf(b.getShowValue());
					f = f / 10000;
					DecimalFormat decimalFormat = new DecimalFormat("0.0");// 构造方法的字符格式这里如果小数不足2位,会以0补足.
					String p = decimalFormat.format(f);
					canvas.drawText(p + "万", bx - histogramWidth / 2 * c, y0
							- by - paint.getFontMetrics().bottom - 5, paint);
				}
			}
		}
	}
}
