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
import com.BC.entertainmentgravitation.R;
import com.BC.entertainmentgravitation.HttpThread.InfoSource;
import com.BC.entertainmentgravitation.HttpThread.SimpleHttpTask;
import com.BC.entertainmentgravitation.activity.MainActivity;
import com.BC.entertainmentgravitation.json.BusinessInformation;
import com.BC.entertainmentgravitation.utl.HttpUtil;
import com.BC.entertainmentgravitation.utl.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class CenterBusinessUtl extends BaseFragment implements OnClickListener {
	Activity activity;
	View contentView;
	BusinessInformation businessInformation;
	EditText Agent_name, The_phone, QQ, WeChat, email, address;

	public BusinessInformation getBusinessInformation() {
		return businessInformation;
	}

	public void setBusinessInformation(BusinessInformation businessInformation) {
		this.businessInformation = businessInformation;
	}

	Button editButton, exitEditButton;
	boolean canEdit = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		activity = getActivity();
		contentView = inflater.inflate(R.layout.item_center_business, null);
		return contentView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		init();
		sendReqBusinessInformation();
		super.onViewCreated(view, savedInstanceState);
	}

	private void init() {
		// TODO Auto-generated method stub
		Agent_name = (EditText) contentView.findViewById(R.id.Agent_name);
		The_phone = (EditText) contentView.findViewById(R.id.The_phone);
		QQ = (EditText) contentView.findViewById(R.id.QQ);
		WeChat = (EditText) contentView.findViewById(R.id.WeChat);
		email = (EditText) contentView.findViewById(R.id.email);
		address = (EditText) contentView.findViewById(R.id.address);

		editButton = (Button) contentView.findViewById(R.id.editButton);
		exitEditButton = (Button) contentView.findViewById(R.id.exitEditButton);

		editButton.setOnClickListener(this);
		exitEditButton.setOnClickListener(this);
		exitEditButton.setVisibility(View.GONE);
		canEdit(canEdit);
	}

	public void initPersonalInformation() {
		// TODO Auto-generated method stub
		if (businessInformation == null) {
			// ToastUtil.show(activity, "获取数据失败");
			return;
		}
		Agent_name.setText(businessInformation.getAgent_name());
		The_phone.setText(businessInformation.getThe_phone());
		QQ.setText(businessInformation.getQQ());
		WeChat.setText(businessInformation.getWeChat());
		email.setText(businessInformation.getEmail());
		address.setText(businessInformation.getAddress());
	}

	public void canEdit(boolean b) {
		Agent_name.setEnabled(b);
		The_phone.setEnabled(b);
		QQ.setEnabled(b);
		WeChat.setEnabled(b);
		email.setEnabled(b);
		address.setEnabled(b);
	}

	public void save() {
		// TODO Auto-generated method stub
		if (businessInformation == null) {
			businessInformation = new BusinessInformation();
		}
		businessInformation.setAgent_name(Agent_name.getText().toString());
		businessInformation.setThe_phone(The_phone.getText().toString());
		businessInformation.setQQ(QQ.getText().toString());
		businessInformation.setWeChat(WeChat.getText().toString());
		businessInformation.setEmail(email.getText().toString());
		businessInformation.setAddress(address.getText().toString());
		sendReqSaveBusinessInformation();
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
	 * 获取商务信息
	 */
	private void sendReqBusinessInformation() {
		if (MainActivity.user == null) {
			ToastUtil.show(activity, "无法获取信息");
			return;
		}
		HashMap<String, String> entity = new HashMap<String, String>();

		entity.put("clientID", MainActivity.user.getClientID());

		SimpleHttpTask httpTask = HttpUtil.packagingHttpTask(entity,
				InfoSource.business_information);
		showProgressDialog("获取商务信息...");
		WorkerManager.addTask(httpTask, this);
	}

	/**
	 * 获取商务信息
	 */
	private void sendReqSaveBusinessInformation() {
		if (MainActivity.user == null) {
			ToastUtil.show(activity, "无法获取信息");
			return;
		}
		HashMap<String, String> entity = HttpUtil
				.object2HashMap(businessInformation);

		entity.put("clientID", MainActivity.user.getClientID());

		SimpleHttpTask httpTask = HttpUtil.packagingHttpTask(entity,
				InfoSource.edit_business_information);
		showProgressDialog("保存商务信息...");
		WorkerManager.addTask(httpTask, this);
	}

	@Override
	public void requestSuccessful(String jsonString, int taskType) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		switch (taskType) {
		case InfoSource.business_information:
			BaseEntity<BusinessInformation> baseEntity2 = gson.fromJson(
					jsonString,
					new TypeToken<BaseEntity<BusinessInformation>>() {
					}.getType());
			BusinessInformation businessInformation = baseEntity2.getData();
			if (businessInformation != null) {
				setBusinessInformation(businessInformation);
				initPersonalInformation();
			} else {
				ToastUtil.show(activity, "获取数据失败");
			}
			break;
		}
	}
}
