package com.BC.entertainmentgravitation.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.BC.androidtool.BaseActivity;
import com.BC.androidtool.BaseFragment;
import com.BC.androidtool.interfaces.FragmentSelectPicture;
import com.BC.entertainmentgravitation.R;
import com.BC.entertainmentgravitation.adapter.SectionsPagerAdapter;
import com.BC.entertainmentgravitation.fragment.CenterAddressUtl;
import com.BC.entertainmentgravitation.fragment.CenterAlbumUtl;
import com.BC.entertainmentgravitation.fragment.CenterBusinessUtl;
import com.BC.entertainmentgravitation.fragment.CenterConnectUtl;
import com.BC.entertainmentgravitation.fragment.CenterFeedbackUtl;
import com.BC.entertainmentgravitation.fragment.CenterUndergoUtl;
import com.BC.entertainmentgravitation.fragment.CenterUserUtl;

public class CenterActivity extends BaseActivity implements
		FragmentSelectPicture {
	RadioGroup group;
	ViewPager pager;
	private SectionsPagerAdapter mAdapter;

	// View user, album, undergo, connect, business, address;
	ArrayList<Fragment> views = new ArrayList<Fragment>();
	CenterUserUtl userUtl;
	CenterAlbumUtl albumUtl;
	CenterUndergoUtl undergoUtl;
	// CenterConnectUtl connectUtl;
	CenterBusinessUtl businessUtl;
	CenterAddressUtl addressUtl;
	CenterFeedbackUtl feedback;

	BaseFragment p;

	// MyPageAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ge_ren_dazhong);
		group = (RadioGroup) findViewById(R.id.radioGroup1);
		pager = (ViewPager) findViewById(R.id.billsViewPage);
		init();

		View radio2 = findViewById(R.id.radio2);
		View radio3 = findViewById(R.id.radio3);
		View radio4 = findViewById(R.id.radio4);
		View radio5 = findViewById(R.id.radio5);

		if (MainActivity.user.getPermission().equals("1")) {
			radio2.setVisibility(View.GONE);
			radio3.setVisibility(View.GONE);
			radio4.setVisibility(View.GONE);
		} else {
			radio5.setVisibility(View.GONE);
		}

		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub

				if (MainActivity.user.getPermission().equals("1")) {
					switch (checkedId) {
					case R.id.radio0:
						pager.setCurrentItem(0);
						break;
					case R.id.radio1:
						pager.setCurrentItem(1);
						break;
					case R.id.radio5:
						pager.setCurrentItem(2);
						break;
					case R.id.radio6:
						pager.setCurrentItem(3);
						break;

					default:
						break;
					}
				} else {
					switch (checkedId) {
					case R.id.radio0:
						pager.setCurrentItem(0);
						break;
					case R.id.radio1:
						pager.setCurrentItem(1);
						break;
					case R.id.radio2:
						pager.setCurrentItem(2);
						break;
					// case R.id.radio3:
					// pager.setCurrentItem(3);
					// break;
					case R.id.radio4:
						pager.setCurrentItem(3);
						break;
					case R.id.radio6:
						pager.setCurrentItem(4);
						break;

					default:
						break;
					}
				}

			}
		});
		pager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				if (MainActivity.user.getPermission().equals("1")) {
					switch (arg0) {
					case 0:
						group.check(R.id.radio0);
						break;
					case 1:
						group.check(R.id.radio1);
						break;
					case 2:
						group.check(R.id.radio5);
						break;
					case 3:
						group.check(R.id.radio6);
						break;
					}
				} else {
					switch (arg0) {
					case 0:
						group.check(R.id.radio0);
						break;
					case 1:
						group.check(R.id.radio1);
						break;
					case 2:
						group.check(R.id.radio2);
						break;
					// case 3:
					// group.check(R.id.radio3);
					// break;
					case 3:
						group.check(R.id.radio4);
						break;
					case 4:
						group.check(R.id.radio6);
						break;
					}
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

		if (MainActivity.user.getPermission().equals("1")) {
			userUtl = new CenterUserUtl();
			albumUtl = new CenterAlbumUtl();
			addressUtl = new CenterAddressUtl();
			feedback = new CenterFeedbackUtl();

			userUtl.setFragmentSelectPicture(this);
			albumUtl.setFragmentSelectPicture(this);

			views.add(userUtl);
			views.add(albumUtl);
			views.add(addressUtl);
			views.add(feedback);
			mAdapter = new SectionsPagerAdapter(
					this.getSupportFragmentManager(), views);
			pager.setAdapter(mAdapter);
		} else {
			userUtl = new CenterUserUtl();
			albumUtl = new CenterAlbumUtl();
			undergoUtl = new CenterUndergoUtl();
			// connectUtl = new CenterConnectUtl();
			businessUtl = new CenterBusinessUtl();
			feedback = new CenterFeedbackUtl();

			userUtl.setFragmentSelectPicture(this);
			albumUtl.setFragmentSelectPicture(this);
			// connectUtl.setFragmentSelectPicture(this);

			views.add(userUtl);
			views.add(albumUtl);
			views.add(undergoUtl);
			// views.add(connectUtl);
			views.add(businessUtl);
			views.add(feedback);
			mAdapter = new SectionsPagerAdapter(
					this.getSupportFragmentManager(), views);
			pager.setAdapter(mAdapter);
		}

	}

	@Override
	public void requestSuccessful(String jsonString, int taskType) {
		// TODO Auto-generated method stub

	}

	@Override
	public void obtainImage(String imagePath) {
		// TODO Auto-generated method stub
		// super.obtainImage(imagePath);
		if (p != null) {
			p.obtainImage(imagePath);
		}
	}

	@Override
	public void phoneImage(BaseFragment flag) {
		p = flag;
		phoneImage();
	}

	@Override
	public void takePictureImage(BaseFragment flag) {
		p = flag;
		takePictureImage();
	}
}
