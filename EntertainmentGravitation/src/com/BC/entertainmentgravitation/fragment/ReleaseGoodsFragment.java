package com.BC.entertainmentgravitation.fragment;

import java.io.File;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.BC.androidtool.BaseFragment;
import com.BC.androidtool.HttpThread.WorkerManager;
import com.BC.entertainmentgravitation.R;
import com.BC.entertainmentgravitation.HttpThread.InfoSource;
import com.BC.entertainmentgravitation.HttpThread.SimpleHttpTask;
import com.BC.entertainmentgravitation.activity.MainActivity;
import com.BC.entertainmentgravitation.utl.HttpUtil;
import com.BC.entertainmentgravitation.utl.ToastUtil;
import com.bumptech.glide.Glide;

public class ReleaseGoodsFragment extends BaseFragment implements
		OnItemClickListener {

	View contentView;
	Button editButton;

	Bitmap bitmap;
	ImageView imageView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		contentView = inflater.inflate(R.layout.fragment_release_goods_list,
				null);
		return contentView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		init();
		// sendReqMessage(1);
		super.onViewCreated(view, savedInstanceState);
	}

	private void init() {
		// TODO Auto-generated method stub
		imageView = (ImageView) contentView.findViewById(R.id.The_picture);
		editButton = (Button) contentView.findViewById(R.id.editButton);
		editButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sendReqMessage();
			}
		});
		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showAlertDialog(R.layout.dialog_alert3, R.id.button3,
						R.id.button1, R.id.button2, ReleaseGoodsFragment.this);
			}
		});
	}

	@Override
	public void onInfoReceived(int errcode, HashMap<String, Object> items) {
		// TODO Auto-generated method stub
		super.onInfoReceived(errcode, items);
	}

	private void sendReqMessage() {
		// TODO Auto-generated method stub
		if (MainActivity.user == null) {
			ToastUtil.show(getActivity(), "无法获取信息");
			return;
		}
		HashMap<String, String> entity = new HashMap<String, String>();

		entity.put("clientID", "" + MainActivity.user.getClientID());
		if (bitmap != null) {
			entity.put("The_picture",
					"data:image/jpg;base64," + HttpUtil.getBtye64String(bitmap));
		}
		entity.put("describe", getTextViewContent(R.id.describe));
		entity.put("The_number_of", getTextViewContent(R.id.The_number_of));
		entity.put("Release_people", MainActivity.user.getNickName());
		entity.put("entertainment_coins",
				getTextViewContent(R.id.entertainment_coins));
		entity.put("Ways_to_convert", "" + 1);
		entity.put("type", "" + 1);
		entity.put("Under_the_frame_of_time",
				getTextViewContent(R.id.Under_the_frame_of_time));
		getText(R.id.describe);

		SimpleHttpTask httpTask = HttpUtil.packagingHttpTask(entity,
				InfoSource.release_goods);
		showProgressDialog("发布信息...");
		WorkerManager.addTask(httpTask, this);
	}

	@Override
	public void requestSuccessful(String jsonString, int taskType) {
		// TODO Auto-generated method stub
		switch (taskType) {
		case InfoSource.release_goods:
			ToastUtil.show(getActivity(), "发布成功");
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
	}

	@Override
	public void obtainImage(String imagePath) {
		// TODO Auto-generated method stub
		Glide.clear(imageView);
		File fe = new File(Environment.getExternalStorageDirectory(), imagePath);
		bitmap = BitmapFactory.decodeFile(fe.getPath());
		imageView.setImageBitmap(bitmap);
	}
}
