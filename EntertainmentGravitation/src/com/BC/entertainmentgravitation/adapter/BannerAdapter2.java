package com.BC.entertainmentgravitation.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.BC.entertainmentgravitation.fragment.BannerFragment;

public class BannerAdapter2 extends FragmentPagerAdapter {

	private List<Integer> banners;

	public BannerAdapter2(FragmentManager fm, List<Integer> ids) {
		super(fm);
		this.banners = ids;
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

	// @Override
	// public CharSequence getPageTitle(int position) {
	// return banners.get(position).getTitle();
	// }

	public void add(List<Integer> list) {
		if (list == null || list.size() == 0)
			return;
		banners.addAll(list);
		notifyDataSetChanged();
	}

}
