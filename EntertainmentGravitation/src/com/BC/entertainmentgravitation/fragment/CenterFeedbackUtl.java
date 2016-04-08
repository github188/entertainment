package com.BC.entertainmentgravitation.fragment;

import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.BC.androidtool.BaseFragment;
import com.BC.androidtool.HttpThread.WorkerManager;
import com.BC.androidtool.JSON.BaseEntity;
import com.BC.androidtool.utils.ValidateUtil;
import com.BC.entertainmentgravitation.R;
import com.BC.entertainmentgravitation.HttpThread.InfoSource;
import com.BC.entertainmentgravitation.HttpThread.SimpleHttpTask;
import com.BC.entertainmentgravitation.activity.MainActivity;
import com.BC.entertainmentgravitation.json.Undergo;
import com.BC.entertainmentgravitation.utl.HttpUtil;
import com.BC.entertainmentgravitation.utl.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 意见反馈
 * 
 * @author shuzhi
 * 
 */
public class CenterFeedbackUtl extends BaseFragment implements OnClickListener {
	Activity activity;
	View contentView;

	String undergo;
	EditText Describe_the_text;

	Button editButton, exitEditButton;
	boolean canEdit = false;

	public String getUndergo() {
		return undergo;
	}

	public void setUndergo(String undergo) {
		this.undergo = undergo;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		activity = getActivity();
		contentView = inflater.inflate(R.layout.item_center_feed_back, null);
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
		Describe_the_text = (EditText) contentView
				.findViewById(R.id.Describe_the_text);
		editButton = (Button) contentView.findViewById(R.id.editButton);
		exitEditButton = (Button) contentView.findViewById(R.id.exitEditButton);

		editButton.setOnClickListener(this);
		exitEditButton.setOnClickListener(this);
		exitEditButton.setVisibility(View.GONE);
		canEdit(canEdit);
	}

	public void canEdit(boolean b) {
		Describe_the_text.setEnabled(b);
	}

	public void save() {
		// TODO Auto-generated method stub
		sendReqSaveUndergo();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.editButton:
			canEdit = !canEdit;
			canEdit(canEdit);

			if (!canEdit) {
				editButton.setText("更改");
				save();
				exitEditButton.setVisibility(View.GONE);
			} else {
				editButton.setText("确定");
				exitEditButton.setVisibility(View.VISIBLE);
			}
			break;

		case R.id.exitEditButton:
			canEdit = false;
			canEdit(canEdit);
			editButton.setText("更改");
			exitEditButton.setVisibility(View.GONE);
			break;
		}

	}

	/**
	 * 保存演艺经历信息
	 */
	private void sendReqSaveUndergo() {
		if (MainActivity.user == null) {
			ToastUtil.show(activity, "无法获取信息");
			return;
		}
		if (Describe_the_text.getText().equals("")) {
			ToastUtil.show(activity, "反馈信息还是填一下吧。");
			return;
		}
		HashMap<String, String> entity = new HashMap<String, String>();

		entity.put("clientID", MainActivity.user.getClientID());
		entity.put("feed_balk", getTextViewContent(R.id.Describe_the_text));

		SimpleHttpTask httpTask = HttpUtil.packagingHttpTask(entity,
				InfoSource.feedback);
		showProgressDialog("获取信息...");
		WorkerManager.addTask(httpTask, this);

	}

	@Override
	public void requestSuccessful(String jsonString, int taskType) {
		// TODO Auto-generated method stub
		switch (taskType) {
		case InfoSource.feedback:
			ToastUtil.show(activity, "提交成功");
			Describe_the_text.setText("");
			break;
		}
	}
}
