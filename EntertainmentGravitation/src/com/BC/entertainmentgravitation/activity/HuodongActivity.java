package com.BC.entertainmentgravitation.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.BC.androidtool.BaseActivity;
import com.BC.androidtool.HttpThread.WorkerManager;
import com.BC.androidtool.JSON.BaseEntity;
import com.BC.androidtool.view.CirclePageIndicator;
import com.BC.entertainmentgravitation.R;
import com.BC.entertainmentgravitation.HttpThread.InfoSource;
import com.BC.entertainmentgravitation.HttpThread.SimpleHttpTask;
import com.BC.entertainmentgravitation.adapter.BannerAdapter;
import com.BC.entertainmentgravitation.json.response.AuthoritativeInformation.Activitys;
import com.BC.entertainmentgravitation.json.response.AuthoritativeInformation.AuthoritativeInformation;
import com.BC.entertainmentgravitation.utl.HttpUtil;
import com.BC.entertainmentgravitation.utl.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class HuodongActivity extends BaseActivity {
	private ViewPager banner;
	private CirclePageIndicator indicator;
	private BannerAdapter bannerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_huo_dong);
		banner = (ViewPager) findViewById(R.id.banner);
		indicator = (CirclePageIndicator) findViewById(R.id.indicator);

		bannerAdapter = new BannerAdapter(getSupportFragmentManager(),
				new ArrayList<Activitys>());
		banner.setAdapter(bannerAdapter);
		if (MainActivity.authoritativeInformation != null
				&& MainActivity.authoritativeInformation.getActivity() != null) {
			// ArrayList<Activitys> activitys = new ArrayList<Activitys>();
			// activitys.add(MainActivity.authoritativeInformation.getActivity()
			// .get(0));
			List<Activitys> activitys = MainActivity.authoritativeInformation
					.getActivity();
			initBanner(activitys);
		} else {
			sendReqActivities();
		}
	}

	/**
	 * 获取广告及公告信息
	 */
	private void sendReqActivities() {
		if (MainActivity.user == null) {
			ToastUtil.show(this, "无法获取");
			return;
		}
		HashMap<String, String> entity = new HashMap<String, String>();

		entity.put("clientID", MainActivity.user.getClientID());

		SimpleHttpTask httpTask = HttpUtil.packagingHttpTask(entity,
				InfoSource.activities);
		WorkerManager.addTask(httpTask, this);
	}

	private void initBanner(List<Activitys> result) {
		// TODO Auto-generated method stub

		bannerAdapter.add(result);
		indicator.setPadding((banner.getWidth() - indicator.getWidth()) / 2,
				indicator.getPaddingTop(), indicator.getPaddingRight(),
				indicator.getPaddingBottom());
		indicator.setInterval(5000);
		indicator.setViewPager(banner);
		indicator.startAutoPlay();
	}

	@Override
	public void requestSuccessful(String jsonString, int taskType) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		switch (taskType) {
		case InfoSource.activities:

			BaseEntity<AuthoritativeInformation> baseEntity3 = gson.fromJson(
					jsonString,
					new TypeToken<BaseEntity<AuthoritativeInformation>>() {
					}.getType());
			MainActivity.authoritativeInformation = baseEntity3.getData();
			if (MainActivity.authoritativeInformation != null
					&& MainActivity.authoritativeInformation.getActivity() != null) {
				// ArrayList<Activitys> activitys = new ArrayList<Activitys>();
				// activitys.add(MainActivity.authoritativeInformation
				// .getActivity());
				List<Activitys> activitys = MainActivity.authoritativeInformation
						.getActivity();
				initBanner(activitys);
			}

			break;
		}
	}
}
