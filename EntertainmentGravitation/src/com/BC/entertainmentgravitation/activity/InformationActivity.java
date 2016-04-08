package com.BC.entertainmentgravitation.activity;

import java.io.File;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.BC.androidtool.BaseActivity;
import com.BC.androidtool.HttpThread.WorkerManager;
import com.BC.entertainmentgravitation.R;
import com.BC.entertainmentgravitation.HttpThread.InfoSource;
import com.BC.entertainmentgravitation.HttpThread.SimpleHttpTask;
import com.BC.entertainmentgravitation.fragment.MessageCommentFragment;
import com.BC.entertainmentgravitation.fragment.MessageListFragment;
import com.BC.entertainmentgravitation.utl.HttpUtil;
import com.BC.entertainmentgravitation.utl.ToastUtil;

public class InformationActivity extends BaseActivity {
	MessageListFragment listFragment;
	MessageCommentFragment commentFragment;
	LinearLayout release_message;
	View addImageDialog, button3;
	String addNewMessage;
	private Bitmap Head_portraitbmp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xin_xi);
		listFragment = (MessageListFragment) getSupportFragmentManager()
				.findFragmentById(R.id.fragment1);
		commentFragment = (MessageCommentFragment) getSupportFragmentManager()
				.findFragmentById(R.id.fragment2);
		listFragment.setOnSelectMessageItem(commentFragment);
		addImageDialog = findViewById(R.id.addImageDialog);
		release_message = (LinearLayout) findViewById(R.id.release_message);
		button3 = findViewById(R.id.button3);

		if (!MainActivity.user.getClientID().equals(
				MainActivity.starInformation.getStar_ID())) {
			release_message.setVisibility(View.GONE);
		}

		findViewById(R.id.addImage).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showAlertDialog(R.layout.dialog_alert3, R.id.button3,
						R.id.button1, R.id.button2);
			}
		});
		findViewById(R.id.negativeButton).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						addImageDialog.setVisibility(View.GONE);
					}
				});
		addImageDialog.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				addImageDialog.setVisibility(View.GONE);
			}
		});
		release_message.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				addImageDialog.setVisibility(View.VISIBLE);
			}
		});
		button3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				addImageDialog.setVisibility(View.GONE);
				addNewMessage = getTextViewContent(R.id.addNewMessage);
				if (!addNewMessage.equals("")) {
					sendReqSaveConnect();
				}
			}
		});

	}

	/**
	 * 保存外部链接信息
	 */
	private void sendReqSaveConnect() {
		if (MainActivity.user == null) {
			ToastUtil.show(this, "无法获取信息");
			return;
		}
		HashMap<String, String> entity = new HashMap<String, String>();

		entity.put("clientID", MainActivity.user.getClientID());
		entity.put("Message_ID", "");
		if (Head_portraitbmp != null) {
			entity.put("The_picture",
					Head_portraitbmp == null ? "" : "data:image/jpg;base64,"
							+ HttpUtil.getBtye64String(Head_portraitbmp));
		}

		entity.put("The_title", addNewMessage);
		entity.put("Type_(censored)_increasing", "");

		SimpleHttpTask httpTask = HttpUtil.packagingHttpTask(entity,
				InfoSource.edit_external_links);
		showProgressDialog("提交外部链接信息...");
		WorkerManager.addTask(httpTask, this);
	}

	@Override
	public void requestSuccessful(String jsonString, int taskType) {
		// TODO Auto-generated method stub

	}

	public void obtainImage(String imagePath) {
		File fe = new File(Environment.getExternalStorageDirectory(), imagePath);
		Head_portraitbmp = BitmapFactory.decodeFile(fe.getPath());
		ImageView imageView = (ImageView) findViewById(R.id.titleImage);
		imageView.setImageBitmap(Head_portraitbmp);
	}
}
