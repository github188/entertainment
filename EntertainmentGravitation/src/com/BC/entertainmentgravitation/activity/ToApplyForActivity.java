package com.BC.entertainmentgravitation.activity;

import java.util.HashMap;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;

import com.BC.androidtool.BaseActivity;
import com.BC.androidtool.HttpThread.WorkerManager;
import com.BC.entertainmentgravitation.R;
import com.BC.entertainmentgravitation.HttpThread.InfoSource;
import com.BC.entertainmentgravitation.HttpThread.SimpleHttpTask;
import com.BC.entertainmentgravitation.utl.HttpUtil;
import com.BC.entertainmentgravitation.utl.ToastUtil;
import com.BC.entertainmentgravitation.utl.ValidateUtil;

public class ToApplyForActivity extends BaseActivity {
	// EditText Starting_price;
	// clientID
	// Stage_name
	// professional
	// Starting_price
	// The_constellation
	// height
	// weight
	// gender
	// language
	// nationality
	// region
	// age

	Button button2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sheng_qing);
		button2 = (Button) findViewById(R.id.button2);
		// Starting_price = (EditText) findViewById(R.id.Starting_price);
		button2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sendReq();
			}
		});
		// Starting_price.setOnFocusChangeListener(new OnFocusChangeListener() {
		//
		// @Override
		// public void onFocusChange(View v, boolean hasFocus) {
		// // TODO Auto-generated method stub
		// if (hasFocus) {
		// Starting_price.setText("0");
		// ToastUtil.show(ToApplyForActivity.this,
		// "第一个为您鼓掌的用户将花费您输入的金额娱币，请注意此金额一旦审核通过后不能修改");
		// } else {
		// String str = Starting_price.getText().toString();
		// int p = Integer.valueOf(str);
		// if (p < 10) {
		// ToastUtil.show(ToApplyForActivity.this, "请输入大于10的数");
		// Starting_price.setText("1000");
		// }
		// }
		// }
		// });
		// Starting_price.addTextChangedListener(new TextWatcher() {
		//
		// @Override
		// public void onTextChanged(CharSequence s, int start, int before,
		// int count) {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// @Override
		// public void beforeTextChanged(CharSequence s, int start, int count,
		// int after) {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// @Override
		// public void afterTextChanged(Editable s) {
		// // TODO Auto-generated method stub
		// String str = s.toString();
		// if (str.length() < 1) {
		// ToastUtil.show(ToApplyForActivity.this, "请输入大于10的数");
		// Starting_price.setText("10");
		// } else {
		// int p = Integer.valueOf(str);
		// if (p > 1000) {
		// ToastUtil.show(ToApplyForActivity.this, "请输入小于1000的数");
		// Starting_price.setText("1000");
		// } else if (p < 10) {
		// ToastUtil.show(ToApplyForActivity.this, "请输入大于10的数");
		// }
		// }
		// }
		// });
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		if (MainActivity.personalInformation == null) {
			ToastUtil.show(this, "获取数据失败");
			return;
		}
		setText(R.id.Stage_name, MainActivity.personalInformation.getNickname());
		setText(R.id.professional,
				MainActivity.personalInformation.getProfessional());
		setText(R.id.Starting_price,
				MainActivity.personalInformation.getStarting_price());
		setText(R.id.The_constellation,
				MainActivity.personalInformation.getThe_constellation());
		setText(R.id.height, MainActivity.personalInformation.getHeight());
		setText(R.id.weight, MainActivity.personalInformation.getWeight());
		setText(R.id.gender, MainActivity.personalInformation.getGender());
		setText(R.id.language, MainActivity.personalInformation.getLanguage());
		setText(R.id.nationality,
				MainActivity.personalInformation.getNationality());
		setText(R.id.region, MainActivity.personalInformation.getRegion());
		setText(R.id.age, MainActivity.personalInformation.getAge());
	}

	private void sendReq() {
		// TODO Auto-generated method stub
		if (MainActivity.user == null) {
			ToastUtil.show(this, "无法获取信息");
			return;
		}
		// if (ValidateUtil.isEmpty(Starting_price, "掌声基础价")) {
		// return;
		// }
		// String str = Starting_price.getText().toString();
		// int p = Integer.valueOf(str);
		// if (p < 10 || p > 1000) {
		// ToastUtil.show(ToApplyForActivity.this, "请输入正确的掌声基础价");
		// Starting_price.setText("10");
		// return;
		// }
		HashMap<String, String> entity = new HashMap<String, String>();
		entity.put("clientID", "" + MainActivity.user.getClientID());
		entity.put("Stage_name", "" + getTextViewContent(R.id.Stage_name));
		entity.put("professional", "" + getTextViewContent(R.id.professional));
		// entity.put("Starting_price", ""
		// + getTextViewContent(R.id.Starting_price));
		entity.put("Starting_price", "" + 100);
		entity.put("The_constellation", ""
				+ getTextViewContent(R.id.The_constellation));
		entity.put("height", "" + getTextViewContent(R.id.height));
		entity.put("weight", "" + getTextViewContent(R.id.weight));
		entity.put("gender", "" + getTextViewContent(R.id.gender));
		entity.put("language", "" + getTextViewContent(R.id.language));
		entity.put("nationality", "" + getTextViewContent(R.id.nationality));
		entity.put("region", "" + getTextViewContent(R.id.region));
		entity.put("age", "" + getTextViewContent(R.id.age));
		entity.put("Whether_the_application_for_the_star", "1");

		SimpleHttpTask httpTask = HttpUtil.packagingHttpTask(entity,
				InfoSource.edit_personal_information);
		showProgressDialog("获取信息...");
		WorkerManager.addTask(httpTask, this);
	}

	@Override
	public void requestSuccessful(String jsonString, int taskType) {
		// TODO Auto-generated method stub
		switch (taskType) {
		case InfoSource.edit_personal_information:
			ToastUtil.show(this, "提交申请成功");
			finish();
			break;

		default:
			break;
		}
	}

}
