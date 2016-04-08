package com.BC.entertainmentgravitation.activity;

import android.os.Bundle;

import com.BC.androidtool.BaseActivity;
import com.BC.entertainmentgravitation.R;
import com.BC.entertainmentgravitation.fragment.JiaGeQuXianFragment;

public class JiaGeQuXianActivity extends BaseActivity {
	JiaGeQuXianFragment jiaGeQuXianFragment;
	public static String starID;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jia_ge_qu_xian);
		jiaGeQuXianFragment = (JiaGeQuXianFragment) getSupportFragmentManager()
				.findFragmentById(R.id.fragment1);
		jiaGeQuXianFragment.showStarInformation(starID);
	}

	@Override
	public void requestSuccessful(String jsonString, int taskType) {
		// TODO Auto-generated method stub

	}

}
