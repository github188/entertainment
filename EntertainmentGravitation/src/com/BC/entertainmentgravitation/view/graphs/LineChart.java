package com.BC.entertainmentgravitation.view.graphs;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class LineChart {

	ArrayList<Coordinate> coordinates;
	boolean rectFlag = false;
	boolean circleFlag = false;
	Bitmap bitmapFlag;
	int num = 24;
	int ynum = 7;

	public int getYnum() {
		return ynum;
	}

	public void setYnum(int ynum) {
		this.ynum = ynum;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public ArrayList<Coordinate> getCoordinates() {
		return coordinates;
	}

	public boolean isRectFlag() {
		return rectFlag;
	}

	public void setRectFlag(boolean rectFlag) {
		this.rectFlag = rectFlag;
	}

	public boolean isCircleFlag() {
		return circleFlag;
	}

	public void setCircleFlag(boolean circleFlag) {
		this.circleFlag = circleFlag;
	}

	public Bitmap getBitmapFlag() {
		return bitmapFlag;
	}

	public void setBitmapFlag(Bitmap bitmapFlag) {
		this.bitmapFlag = bitmapFlag;
	}

	public LineChart() {
		coordinates = new ArrayList<Coordinate>();
	}

	public void addPoint(float x, float y, String showValue) {
		// TODO Auto-generated method stub
		Coordinate coordinate = new Coordinate(x, y);
		coordinate.setShowValue(showValue);
		coordinates.add(coordinate);
	}

	class Coordinate {
		float x;
		float y;
		String showValue;

		public Coordinate(float x, float y) {
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

	public void drawLineChart(Canvas canvas, Paint paint, float x0, float xMax,
			float y0, float yMax, float xShowMax, float yShowMax) {
		// TODO Auto-generated method stub
		if (coordinates != null && coordinates.size() > 0) {
			for (int i = 0; i < coordinates.size() - 1; i++) {
				Coordinate b = coordinates.get(i);
				Coordinate o = coordinates.get(i + 1);
				float l = ((xMax - x0) / num);
				float lx = l * i;
				float bx = x0 + lx;
				// float bx = x0 + (b.x / xShowMax) * (xMax - x0);
				// float by = y0 - (b.y / yShowMax) * (y0 - yMax) - yMax;
				float by = (1 - b.y / yShowMax) * (y0 - yMax) + yMax;
				float lox = l * (i + 1);
				float ox = x0 + lox;

				// float ox = x0 + (o.x / xShowMax) * (xMax - x0);
//				float oy = y0 - (o.y / yShowMax) * (y0 - yMax) - yMax;
				float oy = (1 - o.y / yShowMax) * (y0 - yMax) + yMax;

				paint.reset();
				paint.setStyle(Paint.Style.STROKE);
				paint.setAntiAlias(true);
				paint.setStrokeWidth(2);
				paint.setShadowLayer(15, 0, 0, Color.WHITE);
				paint.setColor(Color.WHITE);
				// Path path = new Path();
				// // path.moveTo(bx, by);
				// // path.quadTo(bx + (ox - bx) / 2, by + (oy - by) / 2, ox,
				// oy);
				// path.moveTo(bx, by);
				// float quadX = bx + (ox - bx) / 2;
				// float quadY = by + (oy - by) / 2;
				// if (i % 2 == 0) {
				// if (by - oy != 0) {
				// quadX += 60;
				// quadY -= 60;
				// }
				// } else {
				// if (by - oy != 0) {
				// quadX -= 60;
				// quadY += 60;
				// }
				// }
				// path.quadTo(quadX, quadY, ox, oy);

				// canvas.drawPath(path, paint);
				canvas.drawLine(bx, by, ox, oy, paint);
				// canvas.drawLine(ox, oy, ox, y0, paint);
				// canvas.drawLine(bx, by, ox, oy, paint);
				paint.setStyle(Paint.Style.FILL);
				paint.setStrokeWidth(3);
				if (rectFlag) {
					canvas.drawRect(bx - 8f, by - 8f, bx + 8f, by + 8f, paint);
					canvas.drawRect(ox - 8f, oy - 8f, ox + 8f, oy + 8f, paint);
				} else if (circleFlag) {
					canvas.drawCircle(bx, by, 8, paint);
					canvas.drawCircle(ox, oy, 8, paint);
				} else if (bitmapFlag != null) {
					bitmapFlag = Bitmap.createScaledBitmap(bitmapFlag, 8, 8,
							true);
					canvas.drawBitmap(bitmapFlag, bx - bitmapFlag.getWidth()
							/ 2, by - bitmapFlag.getWidth() / 2, paint);
					canvas.drawBitmap(bitmapFlag, ox - bitmapFlag.getWidth()
							/ 2, oy - bitmapFlag.getWidth() / 2, paint);
				}

				// if (b.getShowValue() != null && i == 0) {
				// paint.reset();
				// paint.setTextAlign(Paint.Align.CENTER);
				// paint.setStrokeWidth(1);
				// paint.setTextSize(33);
				// paint.setColor(Color.WHITE);
				//
				// canvas.drawText(b.getShowValue(), bx,
				// by - paint.getFontMetrics().bottom - 20, paint);
				// }
				// if (o.getShowValue() != null) {
				// paint.reset();
				// paint.setTextAlign(Paint.Align.CENTER);
				// paint.setStrokeWidth(1);
				// paint.setTextSize(33);
				// paint.setColor(Color.WHITE);
				//
				// canvas.drawText(o.getShowValue(), ox,
				// oy - paint.getFontMetrics().bottom - 20, paint);
				// }

			}
		}
	}
}
