package com.BC.entertainmentgravitation.activity;

import java.util.HashMap;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.BC.androidtool.BaseLoginActivity;
import com.BC.androidtool.HttpThread.WorkerManager;
import com.BC.androidtool.JSON.BaseEntity;
import com.BC.entertainmentgravitation.R;
import com.BC.entertainmentgravitation.HttpThread.InfoSource;
import com.BC.entertainmentgravitation.HttpThread.SimpleHttpTask;
import com.BC.entertainmentgravitation.json.User;
import com.BC.entertainmentgravitation.utl.HttpUtil;
import com.BC.entertainmentgravitation.utl.ToastUtil;
import com.BC.entertainmentgravitation.utl.ValidateUtil;
import com.BC.entertainmentgravitation.view.dialog.ApplaudDialog;
import com.BC.entertainmentgravitation.view.dialog.WarningDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 登录、注册、修改密码
 * 
 * @author shuzhi
 * 
 */
public class LoginActivity extends BaseLoginActivity {

	private EditText phone, verify, passWord, passWordAg, shareCode;
	private Button verifyButton, yesButton, noButton;
	private TextView vtext, textView3;
	private View loginView, logonView, view2;
	private CheckBox checkBox1;
	private String verifyString = "";
	private boolean islogon = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		url = InfoSource.ADDRESS + "?_mod=user&_act=login";
		initLogin(R.id.loginButton, R.id.logonButton, R.id.retrievePasswordID,
				R.id.nameTextID, R.id.verificationodeEditID, R.id.boxID, url);
		loginView = findViewById(R.id.loginView);
		logonView = findViewById(R.id.logonView);

		phone = (EditText) findViewById(R.id.phone);
		verify = (EditText) findViewById(R.id.verify);
		passWord = (EditText) findViewById(R.id.passWord);
		passWordAg = (EditText) findViewById(R.id.passWordAg);
		shareCode = (EditText) findViewById(R.id.shareCode);

		vtext = (TextView) findViewById(R.id.vtext);
		textView3 = (TextView) findViewById(R.id.textView3);

		checkBox1 = (CheckBox) findViewById(R.id.checkBox1);

		verifyButton = (Button) findViewById(R.id.verifyButton);
		yesButton = (Button) findViewById(R.id.yes);
		noButton = (Button) findViewById(R.id.no);

		view2 = findViewById(R.id.view2);

		vtext.setVisibility(View.GONE);

		verifyButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sendReqVERIFY();
			}
		});
		yesButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (islogon) {
					sendReqREGISTER();
				} else {
					sendReqForgetPassword();
				}
			}
		});
		noButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				loginViewIn();
			}
		});
		textView3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showApplaudDialog();
			}
		});
		// logonView.setVisibility(View.GONE);
		loginViewIn();
	}

	private void loginViewIn() {
		// TODO Auto-generated method stub
		exchange(loginView, logonView);
	}

	private void loginViewOut() {
		// TODO Auto-generated method stub
		exchange(logonView, loginView);
	}

	private void exchange(final View v1, final View v2) {
		// TODO Auto-generated method stub
		v2.setVisibility(View.VISIBLE);
		Animation in = new AlphaAnimation(0f, 1f);
		in.setDuration(500);
		// in.setFillAfter(true);
		Animation out = new AlphaAnimation(1f, 0f);
		out.setDuration(500);
		// out.setFillAfter(true);

		in.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				v1.setVisibility(View.VISIBLE);
			}
		});
		out.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				v2.setVisibility(View.GONE);
			}
		});

		v1.startAnimation(in);
		v2.startAnimation(out);
	}

	@Override
	public void requestSuccessful(String jsonString, int taskType) {
		Log.i("test", "---tasktype---" + taskType + "\n");
		Log.i("test", "---jsonString---" + jsonString + "\n");
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		Intent intent;
		switch (taskType) {
		case -1:
			ToastUtil.show(this, "登录成功");
			BaseEntity<User> baseEntity = gson.fromJson(jsonString,
					new TypeToken<BaseEntity<User>>() {
					}.getType());

			MainActivity.user = baseEntity.getData();
			intent = new Intent(this, MainActivity.class);
			startActivity(intent);

			break;
		case InfoSource.VERIFY:
			ToastUtil.show(this, "获取成功");
			BaseEntity<String> baseEntity2 = gson.fromJson(jsonString,
					new TypeToken<BaseEntity<String>>() {
					}.getType());
			verifyString = baseEntity2.getData();
			vtext.setText(verifyString);
			break;
		case InfoSource.REGISTER:
			ToastUtil.show(this, "注册成功");
			// intent = new Intent(this, MainActivity.class);
			// startActivity(intent);
			loginViewIn();
			break;
		case InfoSource.forget_password:
			ToastUtil.show(this, "找回密码成功");
			// intent = new Intent(this, MainActivity.class);
			// startActivity(intent);
			loginViewIn();
			break;

		default:
			break;
		}
	}

	private void clearAll() {
		// TODO Auto-generated method stub
		phone.setText("");
		verify.setText("");
		passWord.setText("");
		passWordAg.setText("");
	}

	@Override
	public void retrievePassword() {
		// TODO Auto-generated method stub
		// ToastUtil.show(this, "忘记密码");
		// sendReqForgetPassword();
		islogon = false;
		shareCode.setVisibility(View.GONE);
		clearAll();
		loginViewOut();
		view2.setVisibility(View.GONE);
	}

	@Override
	public void register() {
		// TODO Auto-generated method stub
		// ToastUtil.show(this, "注册用户");
		islogon = true;
		shareCode.setVisibility(View.VISIBLE);
		clearAll();
		loginViewOut();
	}

	/**
	 * 获取验证码
	 */
	private void sendReqVERIFY() {
		if (phone.getText().toString().equals("")
				|| !ValidateUtil.isMobileNumber(phone)) {
			ToastUtil.show(this, "请输入手机号");
			return;
		}
		HashMap<String, String> entity = new HashMap<String, String>();

		entity.put("phone", phone.getText().toString());

		SimpleHttpTask httpTask = HttpUtil.packagingHttpTask(entity,
				InfoSource.VERIFY);
		showProgressDialog("获取验证码...");
		WorkerManager.addTask(httpTask, this);
	}

	/**
	 * 注册
	 */
	private void sendReqREGISTER() {
		if (!checkBox1.isChecked()) {
			ToastUtil.show(this, "请阅读用户协议");
			return;
		}
		if (ValidateUtil.isEmpty(phone, "手机号")
				|| ValidateUtil.isEmpty(passWord, "密码")
				|| ValidateUtil.isEmpty(passWordAg, "密码")
				|| ValidateUtil.isEmpty(verify, "验证码")) {
			return;
		}
		if (!passWord.getText().toString()
				.equals(passWordAg.getText().toString())) {
			ToastUtil.show(this, "两次密码不同");
			return;
		}
		if (!verify.getText().toString().equals(verifyString)) {
			ToastUtil.show(this, "验证码错误");
			return;
		}

		HashMap<String, String> entity = new HashMap<String, String>();

		entity.put("phone", phone.getText().toString());
		entity.put("passWord", passWord.getText().toString());
		entity.put("verify", verify.getText().toString());
		entity.put("shareCode", shareCode.getText().toString());

		SimpleHttpTask httpTask = HttpUtil.packagingHttpTask(entity,
				InfoSource.REGISTER);
		showProgressDialog("注册中...");
		WorkerManager.addTask(httpTask, this);
	}

	/**
	 * 忘记密码
	 */
	private void sendReqForgetPassword() {
		if (ValidateUtil.isEmpty(phone, "手机号")
				|| ValidateUtil.isEmpty(passWord, "密码")
				|| ValidateUtil.isEmpty(passWordAg, "密码")
				|| ValidateUtil.isEmpty(verify, "验证码")) {
			return;
		}
		if (!passWord.getText().toString()
				.equals(passWordAg.getText().toString())) {
			ToastUtil.show(this, "两次密码不同");
			return;
		}
		if (!verify.getText().toString().equals(verifyString)) {
			ToastUtil.show(this, "验证码错误");
			return;
		}
		HashMap<String, String> entity = new HashMap<String, String>();

		entity.put("phone", phone.getText().toString());
		entity.put("passWord", passWord.getText().toString());
		entity.put("verify", verify.getText().toString());

		SimpleHttpTask httpTask = HttpUtil.packagingHttpTask(entity,
				InfoSource.forget_password);
		showProgressDialog("忘记密码...");
		WorkerManager.addTask(httpTask, this);
	}

	private void showApplaudDialog() {
		// TODO Auto-generated method stub
		final WarningDialog.Builder builder = new WarningDialog.Builder(this);
		builder.setTitle("请阅读用户协议");
		View v = getLayoutInflater().inflate(R.layout.item_dialog_protocol,
				null);
		builder.setContentView(v);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				checkBox1.setChecked(true);
				dialog.dismiss();
				// 设置你的操作事项
			}
		});

		builder.setNegativeButton("取消",
				new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

		builder.create().show();
	}

}
