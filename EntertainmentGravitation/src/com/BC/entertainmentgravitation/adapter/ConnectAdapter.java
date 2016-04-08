package com.BC.entertainmentgravitation.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.BC.entertainmentgravitation.fragment.ConnectFragment;

public class ConnectAdapter extends FragmentPagerAdapter {

	public ConnectAdapter(FragmentManager fragmentManager) {
		super(fragmentManager);
	}

	@Override
	public Fragment getItem(int position) {
		return new ConnectFragment();
	}

	@Override
	public int getCount() {
		return 1;
	}

}
