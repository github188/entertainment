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
import com.BC.entertainmentgravitation.json.request.EditPersonal;
import com.BC.entertainmentgravitation.utl.HttpUtil;
import com.BC.entertainmentgravitation.utl.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class CenterAddressUtl extends BaseFragment implements OnClickListener {
	Activity activity;
	View contentView;

	EditText The_recipient, Courier_contact_phone_number, Zip_code,
			Express_shipping_address;

	Button editButton, exitEditButton;
	boolean canEdit = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		activity = getActivity();
		contentView = inflater.inflate(R.layout.item_center_address, null);
		return contentView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		init();
		if (MainActivity.personalInformation != null) {
			initPersonalInformation();
		} else {
			sendReqUser();
		}
		super.onViewCreated(view, savedInstanceState);
	}

	private void init() {
		// TODO Auto-generated method stub
		The_recipient = (EditText) contentView.findViewById(R.id.The_recipient);
		Courier_contact_phone_number = (EditText) contentView
				.findViewById(R.id.Courier_contact_phone_number);
		Zip_code = (EditText) contentView.findViewById(R.id.Zip_code);
		Express_shipping_address = (EditText) contentView
				.findViewById(R.id.Express_shipping_address);
		editButton = (Button) contentView.findViewById(R.id.editButton);
		exitEditButton = (Button) contentView.findViewById(R.id.exitEditButton);

		editButton.setOnClickListener(this);
		exitEditButton.setOnClickListener(this);
		exitEditButton.setVisibility(View.GONE);
		canEdit(canEdit);
	}

	public void initPersonalInformation() {
		// TODO Auto-generated method stub
		if (MainActivity.personalInformation == null) {
			ToastUtil.show(activity, "获取数据失败");
			return;
		}
		The_recipient.setText(MainActivity.personalInformation
				.getThe_recipient());
		Courier_contact_phone_number.setText(MainActivity.personalInformation
				.getCourier_contact_phone_number());
		Zip_code.setText(MainActivity.personalInformation.getZip_code());
		Express_shipping_address.setText(MainActivity.personalInformation
				.getExpress_shipping_address());
	}

	public void canEdit(boolean b) {
		The_recipient.setEnabled(b);
		Courier_contact_phone_number.setEnabled(b);
		Zip_code.setEnabled(b);
		Express_shipping_address.setEnabled(b);
	}

	public void save() {
		// TODO Auto-generated method stub
		if (MainActivity.personalInformation == null) {
			MainActivity.personalInformation = new EditPersonal();
		}
		MainActivity.personalInformation.setThe_recipient(The_recipient
				.getText().toString());
		MainActivity.personalInformation
				.setCourier_contact_phone_number(Courier_contact_phone_number
						.getText().toString());
		MainActivity.personalInformation.setZip_code(Zip_code.getText()
				.toString());

		MainActivity.personalInformation
				.setExpress_shipping_address(Express_shipping_address.getText()
						.toString());
		sendReqSaveUser();
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
	 * 获取用户信息
	 */
	private void sendReqUser() {
		if (MainActivity.user == null) {
			ToastUtil.show(activity, "无法获取信息");
			return;
		}
		HashMap<String, String> entity = new HashMap<String, String>();

		entity.put("clientID", MainActivity.user.getClientID());

		SimpleHttpTask httpTask = HttpUtil.packagingHttpTask(entity,
				InfoSource.personal_information);
		showProgressDialog("获取用户基本信息...");
		WorkerManager.addTask(httpTask, this);
	}

	/**
	 * 提交用户信息
	 */
	private void sendReqSaveUser() {
		if (MainActivity.user == null
				|| MainActivity.personalInformation == null) {
			ToastUtil.show(activity, "无法获取信息");
			return;
		}
		// HashMap<String, String> entity = new HashMap<String, String>();

		EditPersonal entity = MainActivity.personalInformation;
		entity.setClientID(MainActivity.user.getClientID());

		entity.setThe_recipient(getTextViewContent(R.id.The_recipient)
				.toString());
		entity.setCourier_contact_phone_number(getTextViewContent(
				R.id.Courier_contact_phone_number).toString());
		entity.setZip_code(getTextViewContent(R.id.Zip_code).toString());

		entity.setExpress_shipping_address(getTextViewContent(
				R.id.Express_shipping_address).toString());

		SimpleHttpTask httpTask = HttpUtil.packagingHttpTask(entity,
				InfoSource.edit_personal_information);
		showProgressDialog("获取用户基本信息...");
		WorkerManager.addTask(httpTask, this);
	}

	@Override
	public void requestSuccessful(String jsonString, int taskType) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		switch (taskType) {
		case InfoSource.personal_information:
			BaseEntity<EditPersonal> baseEntity = gson.fromJson(jsonString,
					new TypeToken<BaseEntity<EditPersonal>>() {
					}.getType());
			MainActivity.personalInformation = baseEntity.getData();
			if (MainActivity.personalInformation != null) {
				initPersonalInformation();
			} else {
				ToastUtil.show(activity, "获取数据失败");
			}
			break;

		}
	}
}
