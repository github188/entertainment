package com.BC.entertainmentgravitation.json.response.kLink;

import java.util.List;

public class KLink {
	private List<Point> point;

	private String max;
	private String min;
	private String difference;

	public String getDifference() {
		return difference;
	}

	public void setDifference(String difference) {
		this.difference = difference;
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public void setPoint(List<Point> point) {
		this.point = point;
	}

	public List<Point> getPoint() {
		return this.point;
	}

	public void setMax(String max) {
		this.max = max;
	}

	public String getMax() {
		return this.max;
	}
}
