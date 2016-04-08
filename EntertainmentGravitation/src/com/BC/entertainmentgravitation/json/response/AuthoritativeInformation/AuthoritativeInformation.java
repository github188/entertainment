package com.BC.entertainmentgravitation.json.response.AuthoritativeInformation;

import java.util.List;

public class AuthoritativeInformation {
	private List<Activitys> activity;

	private List<Advertising> advertising;

	public void setActivity(List<Activitys> activity) {
		this.activity = activity;
	}

	public List<Activitys> getActivity() {
		return this.activity;
	}

	public void setAdvertising(List<Advertising> advertising) {
		this.advertising = advertising;
	}

	public List<Advertising> getAdvertising() {
		return this.advertising;
	}

}
