package com.BC.entertainmentgravitation.view.graphs;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Histogram {
	ArrayList<Coordinate> coordinates;
	Context context;
	float histogramWidth = 40;
	int compare = 3;
	int[] colors = { 0x99fc3b9f, 0x40fc3b9f };
	int number = 0;

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

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
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

				paint.setColor(colors[i % 2]);

				float l = ((xMax - x0) / number);
				float lx = l * i;
				float bx = x0 + lx;

				float h = (y0 - yMax) / 7;
				for (int j = 0; j < 7; j++) {
					float hy = h * j;
					float by = hy + h;
					canvas.drawRect(bx + 1, yMax + hy + 1, bx + l - 1, yMax
							+ by - 1, paint);
				}

			}
		}
	}
}
