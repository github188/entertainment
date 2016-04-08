package com.BC.entertainmentgravitation.fragment;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.BC.androidtool.BaseFragment;
import com.BC.androidtool.HttpThread.WorkerManager;
import com.BC.androidtool.JSON.BaseEntity;
import com.BC.androidtool.view.CircularImage;
import com.BC.entertainmentgravitation.R;
import com.BC.entertainmentgravitation.HttpThread.InfoSource;
import com.BC.entertainmentgravitation.HttpThread.SimpleHttpTask;
import com.BC.entertainmentgravitation.activity.MainActivity;
import com.BC.entertainmentgravitation.activity.TopUpActivity;
import com.BC.entertainmentgravitation.json.request.EditPersonal;
import com.BC.entertainmentgravitation.utl.ConstantArrayListsUtl;
import com.BC.entertainmentgravitation.utl.HttpUtil;
import com.BC.entertainmentgravitation.utl.TimestampTool;
import com.BC.entertainmentgravitation.utl.ToastUtil;
import com.BC.entertainmentgravitation.view.BaseSelectItem;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class CenterUserUtl extends BaseFragment implements OnClickListener {
	Activity activity;
	View contentView;
	EditText nickname, In_the_mood,
	// gender, The_constellation,
	// national, height, weight,
			email, Mobile_phone;
	TextView level, copy;
	BaseSelectItem gender, professional, nationality, region, language,
			constellation, national, height, weight, birthday;

	List<String> genderList = Arrays.asList(ConstantArrayListsUtl.gender);

	List<String> professionalList = Arrays
			.asList(ConstantArrayListsUtl.professional);
	List<String> nationalityList = Arrays
			.asList(ConstantArrayListsUtl.nationality);
	List<String> languageList = Arrays.asList(ConstantArrayListsUtl.language);
	List<String> constellationList = Arrays
			.asList(ConstantArrayListsUtl.constellation);
	List<String> nationalList = Arrays.asList(ConstantArrayListsUtl.national);
	List<String> height1List = Arrays.asList(ConstantArrayListsUtl.height1);
	List<String> height2List = Arrays.asList(ConstantArrayListsUtl.height2);
	List<String> height3List = Arrays.asList(ConstantArrayListsUtl.height3);
	List<String> weight1List = Arrays.asList(ConstantArrayListsUtl.weight1);
	List<String> weight2List = Arrays.asList(ConstantArrayListsUtl.weight2);

	private CircularImage Head_portrait;
	private Bitmap Head_portraitbmp;
	private String headFile;

	Button editButton, exitEditButton;
	boolean canEdit = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		activity = getActivity();
		contentView = inflater.inflate(R.layout.item_center_user, null);
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

		gender = (BaseSelectItem) contentView.findViewById(R.id.gender);
		professional = (BaseSelectItem) contentView
				.findViewById(R.id.professional);
		nationality = (BaseSelectItem) contentView
				.findViewById(R.id.nationality);
		region = (BaseSelectItem) contentView.findViewById(R.id.region);
		language = (BaseSelectItem) contentView.findViewById(R.id.language);
		constellation = (BaseSelectItem) contentView
				.findViewById(R.id.constellation);
		national = (BaseSelectItem) contentView.findViewById(R.id.national);
		height = (BaseSelectItem) contentView.findViewById(R.id.height);
		weight = (BaseSelectItem) contentView.findViewById(R.id.weight);
		birthday = (BaseSelectItem) contentView.findViewById(R.id.birthday);

		gender.setSelectContent(genderList);
		professional.setSelectContent(professionalList);
		nationality.setSelectContent(nationalityList);
		language.setSelectContent(languageList);
		constellation.setSelectContent(constellationList);
		national.setSelectContent(nationalList);

		height.setSelectContent(height1List);
		height.setSelect2Content(height2List);
		height.setSelect3Content(height3List);
		height.setWheelTitle1String("米");
		height.setWheelTitle2String("X10厘米");
		height.setWheelTitle3String("厘米");

		weight.setSelectContent(weight1List);
		weight.setSelect2Content(weight2List);
		weight.setWheelTitle1String("X10公斤");
		weight.setWheelTitle2String("公斤");

		nickname = (EditText) contentView.findViewById(R.id.nickname);
		level = (TextView) contentView.findViewById(R.id.level);
		copy = (TextView)contentView.findViewById(R.id.copy);
		In_the_mood = (EditText) contentView.findViewById(R.id.In_the_mood);
		email = (EditText) contentView.findViewById(R.id.email);
		Mobile_phone = (EditText) contentView.findViewById(R.id.phone);
		editButton = (Button) contentView.findViewById(R.id.editButton);
		exitEditButton = (Button) contentView.findViewById(R.id.exitEditButton);
		Head_portrait = (CircularImage) contentView
				.findViewById(R.id.Head_portrait);
		
		Head_portrait.setOnClickListener(this);
		editButton.setOnClickListener(this);
		exitEditButton.setOnClickListener(this);
		copy.setOnClickListener(this);
		exitEditButton.setVisibility(View.GONE);
		canEdit(canEdit);
	}

	public void initPersonalInformation() {
		// TODO Auto-generated method stub
		if (MainActivity.personalInformation == null) {
			ToastUtil.show(activity, "获取数据失败");
			return;
		}
		nickname.setText(MainActivity.personalInformation.getNickname());
		In_the_mood.setText(MainActivity.personalInformation.getIn_the_mood());

		gender.setContent(MainActivity.personalInformation.getGender().equals(
				"男") ? "男" : "女");
		birthday.setContent(MainActivity.personalInformation.getBirthday());
		constellation.setContent(MainActivity.personalInformation
				.getThe_constellation());
		national.setContent(MainActivity.personalInformation.getNational());
		height.setContent(MainActivity.personalInformation.getHeight());
		weight.setContent(MainActivity.personalInformation.getWeight());
		region.setContent(MainActivity.personalInformation.getRegion());

		professional.setContent(MainActivity.personalInformation
				.getProfessional());
		nationality.setContent(MainActivity.personalInformation
				.getNationality());
		region.setContent(MainActivity.personalInformation.getRegion());
		language.setContent(MainActivity.personalInformation.getLanguage());

		email.setText(MainActivity.personalInformation.getEmail());
		Mobile_phone
				.setText(MainActivity.personalInformation.getMobile_phone());

		level.setText("Lv." + MainActivity.personalInformation.getLevel());

		Glide.with(activity)
				.load(MainActivity.personalInformation.getHead_portrait())
				.centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)
				.placeholder(R.drawable.home_image).into(Head_portrait);
	}

	public void canEdit(boolean b) {
		Head_portrait.setClickable(b);
		nickname.setEnabled(b);
		In_the_mood.setEnabled(b);
		// gender.setEnabled(b);
		// // birthday.setEnabled(b);
		// The_constellation.setEnabled(b);
		// national.setEnabled(b);
		// height.setEnabled(b);
		// weight.setEnabled(b);
		email.setEnabled(b);
		Mobile_phone.setEnabled(b);

		gender.setCanClick(b);
		professional.setCanClick(b);
		nationality.setCanClick(b);
		region.setCanClick(b);
		language.setCanClick(b);
		constellation.setCanClick(b);
		national.setCanClick(b);
		height.setCanClick(b);
		weight.setCanClick(b);
		birthday.setCanClick(b);
	}

	public void save() {
		// TODO Auto-generated method stub
		if (MainActivity.personalInformation == null) {
			MainActivity.personalInformation = new EditPersonal();
		}

		MainActivity.personalInformation.setNickname(nickname.getText()
				.toString());
		MainActivity.personalInformation.setIn_the_mood(In_the_mood.getText()
				.toString());
		MainActivity.personalInformation.setGender(gender.getContent().equals(
				"男") ? "男" : "女");
		MainActivity.personalInformation.setBirthday(birthday.getContent());
		MainActivity.personalInformation.setAge(""
				+ TimestampTool.dateDiffYear(birthday.getContent(),
						TimestampTool.getCurrentDate()));

		MainActivity.personalInformation.setProfessional(professional
				.getContent());
		MainActivity.personalInformation.setLanguage(language.getContent());
		MainActivity.personalInformation.setThe_constellation(constellation
				.getContent());
		MainActivity.personalInformation.setNational(national.getContent());
		MainActivity.personalInformation.setHeight(height.getContent());
		MainActivity.personalInformation.setWeight(weight.getContent());

		MainActivity.personalInformation.setLanguage(language.getContent());
		MainActivity.personalInformation.setNationality(nationality
				.getContent());
		MainActivity.personalInformation.setRegion(region.getContent());
		MainActivity.personalInformation.setEmail(email.getText().toString());
		MainActivity.personalInformation.setMobile_phone(Mobile_phone.getText()
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
			save();
			break;

		case R.id.Head_portrait:
			showAlertDialog(R.layout.dialog_alert3, R.id.button3, R.id.button1,
					R.id.button2, this);
			break;
		case R.id.copy:
			copy(MainActivity.user.getShareCode(), activity);
			Toast.makeText(activity, "已复制到剪切板，快去分享给好友吧",
					Toast.LENGTH_LONG).show();
//			showShare(activity);
			break;
		}

	}
	
	@SuppressWarnings("deprecation")
	public void copy(String content, Context context)
	{
		// 得到剪贴板管理器
		ClipboardManager cmb = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
		cmb.setText(content.trim());
	}
	
	private void showShare(Context context){
		   ShareSDK.initSDK(context, "10ee118b8af16");

		   OnekeyShare oks = new OnekeyShare(); 
		   //关闭sso授权
		   oks.disableSSOWhenAuthorize();
		   // 分享时Notification的图标和文字
		   oks.setTitle("分享");
		   oks.setText("我是分享文本");
		   oks.setUrl("http://sharesdk.cn");
		   oks.setSite(getString(R.string.app_name));
		   oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/05/21/oESpJ78_533x800.jpg"); 
		   // 启动分享GUI
		   oks.show(context);
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

		// if (headFile != null) {
		// FTPUtil ftpUtil = new FTPUtil(activity, headFile);
		// showProgressDialog("上传头像中");
		// ftpUtil.setFtpIsOK(new FTPIsOK() {
		//
		// @Override
		// public void ftpIsOK(String path) {
		// TODO Auto-generated method stub
		EditPersonal entity = MainActivity.personalInformation;
		// entity.setHead_portrait(headFile);
		entity.setClientID(MainActivity.user.getClientID());
		entity.setHead_portrait(Head_portraitbmp == null ? MainActivity.personalInformation
				.getHead_portrait() : ("data:image/jpg;base64," + HttpUtil
				.getBtye64String(Head_portraitbmp)));
		SimpleHttpTask httpTask = HttpUtil.packagingHttpTask(entity,
				InfoSource.edit_personal_information);
		showProgressDialog("提交基本信息...");
		WorkerManager.addTask(httpTask, CenterUserUtl.this);
		// }
		// });
		// }

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
		case InfoSource.edit_personal_information:
			ToastUtil.show(getActivity(), "提交成功");
			sendReqUser();
			break;
		}
	}

	@Override
	public void obtainImage(String imagePath) {
		// TODO Auto-generated method stub
		Glide.clear(Head_portrait);

		headFile = imagePath;

		File fe = new File(Environment.getExternalStorageDirectory(), imagePath);
		Head_portraitbmp = BitmapFactory.decodeFile(fe.getPath());
		Head_portrait.setImageBitmap(Head_portraitbmp);
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		ShareSDK.stopSDK();
	}
	
	
}
