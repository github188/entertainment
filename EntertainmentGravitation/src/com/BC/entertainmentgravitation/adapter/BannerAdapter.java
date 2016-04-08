package com.BC.entertainmentgravitation.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.BC.entertainmentgravitation.fragment.BannerFragment;
import com.BC.entertainmentgravitation.json.response.AuthoritativeInformation.Activitys;

public class BannerAdapter extends FragmentPagerAdapter {

	private List<Activitys> banners;

	public BannerAdapter(FragmentManager fragmentManager, ArrayList<Activitys> arrayList) {
		super(fragmentManager);
		this.banners = arrayList;
	}

	@Override
	public Fragment getItem(int position) {
		return BannerFragment
				.newInstance(banners.get(position % banners.size()));
	}

	@Override
	public int getCount() {
		return banners.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return banners.get(position).getThe_link_address();
	}

	public void add(List<Activitys> list) {
		if (list == null || list.size() == 0)
			return;
		banners.addAll(list);
		notifyDataSetChanged();
	}

}
