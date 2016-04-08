package com.BC.entertainmentgravitation.activity;

import android.os.Bundle;

import com.BC.androidtool.BaseActivity;
import com.BC.entertainmentgravitation.R;

/**
 * 欢迎页
 * 
 * @author shuzhi
 * 
 */
public class WelcomActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
	}

	@Override
	public void requestSuccessful(String jsonString, int taskType) {
		// TODO Auto-generated method stub
		
	}

}
