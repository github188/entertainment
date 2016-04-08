package com.BC.entertainmentgravitation.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

import com.BC.androidtool.BaseDialogFragment;
import com.BC.entertainmentgravitation.R;
import com.BC.entertainmentgravitation.adapter.PictureAdapter;

public class PictureFragment extends BaseDialogFragment {
	Activity activity;
	View contentView;// editConnect
	OnPageChangeListener changeListener;
	PictureAdapter adapter;

	public OnPageChangeListener getChangeListener() {
		return changeListener;
	}

	public void setChangeListener(OnPageChangeListener changeListener) {
		this.changeListener = changeListener;
	}

	public PictureAdapter getAdapter() {
		return adapter;
	}

	public void setAdapter(PictureAdapter adapter) {
		this.adapter = adapter;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		activity = getActivity();
		contentView = inflater.inflate(R.layout.dialog_picture, container,
				false);
		return contentView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		init();
		super.onViewCreated(view, savedInstanceState);
	}

	private void init() {
		// TODO Auto-generated method stub
		contentView.findViewById(R.id.negativeButton).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dismiss();
					}
				});
		ViewPager pager = (ViewPager) contentView.findViewById(R.id.picture);
		if (adapter != null) {
			pager.setAdapter(adapter);
		}
		if (changeListener != null) {
			pager.setOnPageChangeListener(changeListener);
		}
	}

	@Override
	public void requestSuccessful(String jsonString, int taskType) {
		// TODO Auto-generated method stub

	}

}
