package com.BC.entertainmentgravitation.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.BC.androidtool.BaseActivity;
import com.BC.androidtool.BaseFragment;
import com.BC.androidtool.interfaces.FragmentSelectPicture;
import com.BC.entertainmentgravitation.R;
import com.BC.entertainmentgravitation.adapter.SectionsPagerAdapter;
import com.BC.entertainmentgravitation.fragment.GoodsListFragment;
import com.BC.entertainmentgravitation.fragment.OrdersListFragment;
import com.BC.entertainmentgravitation.fragment.ReleaseGoodsFragment;

public class GiftActivity extends BaseActivity implements FragmentSelectPicture {

	RadioGroup group;
	ViewPager pager;
	private SectionsPagerAdapter mAdapter;
	ArrayList<Fragment> views = new ArrayList<Fragment>();

	GoodsListFragment goodsListFragment;
	ReleaseGoodsFragment releaseGoodsFragment;
	OrdersListFragment ordersListFragment;

	BaseFragment p;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_li_ping);
		group = (RadioGroup) findViewById(R.id.radioGroup1);
		pager = (ViewPager) findViewById(R.id.billsViewPage);
		init();

		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.radio0:
					pager.setCurrentItem(0);
					break;
				// case R.id.radio1:
				// pager.setCurrentItem(1);
				// break;
				case R.id.radio2:
					pager.setCurrentItem(1);
					break;
				case R.id.radio3:
					pager.setCurrentItem(2);
					break;
				default:
					break;
				}
			}
		});
		pager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				switch (arg0) {
				case 0:
					group.check(R.id.radio0);
					break;
				// case 1:
				// group.check(R.id.radio1);
				// break;
				case 1:
					group.check(R.id.radio2);
					break;
				case 2:
					group.check(R.id.radio3);
					break;

				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void init() {
		// TODO Auto-generated method stub
		goodsListFragment = new GoodsListFragment();
		releaseGoodsFragment = new ReleaseGoodsFragment();
		ordersListFragment = new OrdersListFragment();

		releaseGoodsFragment.setFragmentSelectPicture(this);

		views.add(goodsListFragment);
		views.add(releaseGoodsFragment);
		views.add(ordersListFragment);
		mAdapter = new SectionsPagerAdapter(this.getSupportFragmentManager(),
				views);
		pager.setAdapter(mAdapter);
	}

	@Override
	public void requestSuccessful(String jsonString, int taskType) {
		// TODO Auto-generated method stub

	}

	@Override
	public void phoneImage(BaseFragment flag) {
		// TODO Auto-generated method stub
		p = flag;
		phoneImage();
	}

	@Override
	public void takePictureImage(BaseFragment flag) {
		// TODO Auto-generated method stub
		p = flag;
		takePictureImage();
	}

	@Override
	public void obtainImage(String imagePath) {
		// TODO Auto-generated method stub
		p.obtainImage(imagePath);
	}
}
