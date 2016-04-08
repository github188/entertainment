package com.BC.entertainmentgravitation.activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.BC.androidtool.BaseActivity;
import com.BC.androidtool.BaseFragment;
import com.BC.androidtool.HttpThread.WorkerManager;
import com.BC.androidtool.interfaces.FragmentSelectPicture;
import com.BC.entertainmentgravitation.R;
import com.BC.entertainmentgravitation.HttpThread.InfoSource;
import com.BC.entertainmentgravitation.HttpThread.SimpleHttpTask;
import com.BC.entertainmentgravitation.fragment.MyRightsFragment;
import com.BC.entertainmentgravitation.fragment.MyRightsStarFragment;
import com.BC.entertainmentgravitation.fragment.RightsFragment;
import com.BC.entertainmentgravitation.utl.BaiduMapUtil;
import com.BC.entertainmentgravitation.utl.HttpUtil;
import com.BC.entertainmentgravitation.utl.ToastUtil;
import com.BC.entertainmentgravitation.view.BaseSelcetWheel;
import com.BC.entertainmentgravitation.view.BaseSelectItem;
import com.BC.entertainmentgravitation.view.SelectWheelLinkage2;
import com.BC.entertainmentgravitation.view.WheelInterfasc;
import com.BC.entertainmentgravitation.view.dialog.AddGiftDialog;
import com.BC.entertainmentgravitation.view.dialog.WarningDialog;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult.AddressComponent;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class GiftActivity2 extends BaseActivity implements
		FragmentSelectPicture {

	public interface GiftChange {
		public void change();
	}

	RadioGroup group;

	RightsFragment rightsFragment;
	MyRightsFragment myRightsFragment;
	MyRightsStarFragment myRightsStarFragment;
	BaseSelectItem total, price;
	EditText describe, target;
	Button label;
	String labelString = "";
	String cityString = "";
	BaseSelectItem region;

	String[] num1 = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0" };
	String[] num2 = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
	String[] content1 = { "接戏", "接聊", "接玩" };
	List<String> selectContent = Arrays.asList(content1);

	String[] content2 = { "演电影", "演电视剧", "演网络剧", "演网络电影", "演话剧", "演歌剧", "T台走秀",
			"其他（在详细内容中描述）" };
	String[] content3 = { "剧组杂谈…", "怎样与导演沟通的很顺畅……", "演员之间是如何相处的更融洽",
			"怎样与制片人沟通？", "怎样与演员副导演拉关系？", "怎样与选择经纪人或经纪公司合作？", "如何与粉丝互动？",
			"怎样把握人物创作中的真实感……", "导演如何引导演员入戏……", "怎样搭建制作团队……", "怎样利用业余时间创造价值…",
			"怎样找到靠谱的投资人", "怎样找到优质影视剧项目", "其他（在详细内容中描述）" };
	String[] content4 = { "探我班…探班", "请我喝…喝茶", "约我唱…唱歌", "陪我游…旅游", "与我逛…逛街",
			"其他（在详细内容中描述）" };
	List<String> list1 = Arrays.asList(content2);
	List<String> list2 = Arrays.asList(content3);
	List<String> list3 = Arrays.asList(content4);
	List<List<String>> select2Content = new ArrayList<List<String>>();

	BaseFragment p;
	GiftChange change = new GiftChange() {

		@Override
		public void change() {
			// TODO Auto-generated method stub
			initContent();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_li_ping2);
		group = (RadioGroup) findViewById(R.id.radioGroup1);
		init();
		select2Content.add(list1);
		select2Content.add(list2);
		select2Content.add(list3);
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub

				switch (checkedId) {
				case R.id.radio0:
					showandhide();
					break;
				case R.id.radio1:
					showandhide();
					break;
				case R.id.radio3:
					showandhide();
					break;
				default:
					break;
				}
			}
		});
		if (MainActivity.user.getPermission().equals("2")) {
			findViewById(R.id.addGift).setOnClickListener(
					new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							showAddGiftDialog();
						}
					});
		} else {
			findViewById(R.id.addGift).setVisibility(View.GONE);
		}

	}

	private void init() {
		// TODO Auto-generated method stub
		rightsFragment = (RightsFragment) getSupportFragmentManager()
				.findFragmentById(R.id.fragment2);
		myRightsFragment = (MyRightsFragment) getSupportFragmentManager()
				.findFragmentById(R.id.fragment1);
		myRightsStarFragment = (MyRightsStarFragment) getSupportFragmentManager()
				.findFragmentById(R.id.fragment3);

		rightsFragment.setChange(change);
		myRightsFragment.setChange(change);
		if (!MainActivity.user.getPermission().equals("2")) {
			hideOne(R.id.fragment3);
			findViewById(R.id.radio3).setVisibility(View.GONE);
		}
		showandhide();
		//geoCode();
	}

	private void initContent() {
		rightsFragment.sendReqMessage(1);
		rightsFragment.pageIndex = 1;
		myRightsFragment.sendReqMessage(1);
		myRightsFragment.pageIndex = 1;
		myRightsStarFragment.sendReqMessage(1);
		myRightsStarFragment.pageIndex = 1;
	}

	/*private void geoCode() {
		if (BaiduMapUtil.getNowAddressComponent() != null) {
			AddressComponent addressComponent = BaiduMapUtil
					.getNowAddressComponent();
			cityString = addressComponent.province + "-"
					+ addressComponent.city;
		} else {
			showProgressDialog("正在获取位置");
			BaiduMapUtil.newInstance(this).setGetGeoCodeResultListener(
					new OnGetGeoCoderResultListener() {

						@Override
						public void onGetReverseGeoCodeResult(
								ReverseGeoCodeResult arg0) {
							// TODO Auto-generated method stub
							removeProgressDialog();
							AddressComponent addressComponent = arg0
									.getAddressDetail();
							BaiduMapUtil
									.setNowAddressComponent(addressComponent);
							cityString = addressComponent.province + "-"
									+ addressComponent.city;
						}

						@Override
						public void onGetGeoCodeResult(GeoCodeResult arg0) {
							// TODO Auto-generated method stub

						}
					});
			BaiduMapUtil.newInstance(this).shareAddr();
		}
	}*/

	private void hideOne(int id) {
		// TODO Auto-generated method stub
		FragmentTransaction fragmentTransaction = getSupportFragmentManager()
				.beginTransaction();
		android.support.v4.app.Fragment fragment = getSupportFragmentManager()
				.findFragmentById(id);
		fragmentTransaction.hide(fragment);
		fragmentTransaction.commit();
	}

	private void showandhide() {
		// TODO Auto-generated method stub
		FragmentTransaction fragmentTransaction = getSupportFragmentManager()
				.beginTransaction();
		int t = group.getCheckedRadioButtonId();
		switch (t) {
		case R.id.radio0:
			fragmentTransaction.hide(myRightsFragment);
			fragmentTransaction.hide(myRightsStarFragment);
			fragmentTransaction.show(rightsFragment);
			fragmentTransaction.commit();
			break;
		case R.id.radio1:
			fragmentTransaction.hide(rightsFragment);
			fragmentTransaction.hide(myRightsStarFragment);
			fragmentTransaction.show(myRightsFragment);
			fragmentTransaction.commit();
			break;
		case R.id.radio3:
			fragmentTransaction.hide(rightsFragment);
			fragmentTransaction.hide(myRightsFragment);
			fragmentTransaction.show(myRightsStarFragment);
			fragmentTransaction.commit();
			break;

		}
	}

	private void sendReq() {
		// TODO Auto-generated method stub
		if (MainActivity.user == null) {
			ToastUtil.show(this, "无法获取信息");
			return;
		}
		HashMap<String, String> entity = new HashMap<String, String>();

		entity.put("star_id", "" + MainActivity.user.getClientID());
		entity.put("total", "" + total.getContent());
		entity.put("price", "" + price.getContent());
		entity.put("label", "" + labelString);
		entity.put("describe", "" + describe.getText().toString());
		entity.put("target", "" + target.getText().toString());
		entity.put("region", "" + region.getContent());
		SimpleHttpTask httpTask = HttpUtil.packagingHttpTask(entity,
				InfoSource.make_equity_card);
		showProgressDialog("发布权益卡...");
		WorkerManager.addTask(httpTask, this);
	}

	AddGiftDialog dialogAddGiftDialog;

	private void showAddGiftDialog() {
		// TODO Auto-generated method stub
		if (MainActivity.user == null
				|| MainActivity.personalInformation == null) {
			ToastUtil.show(this, "无法获取信息");
			return;
		}
		final AddGiftDialog.Builder builder = new AddGiftDialog.Builder(this);
		builder.setNegativeButton(new android.content.DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		dialogAddGiftDialog = builder.create();
		region = (BaseSelectItem) builder.findViewById(R.id.region);
		region.setContent(cityString);
		ImageView star_Head_portrait = (ImageView) builder
				.findViewById(R.id.star_Head_portrait);
		TextView Stage_name = (TextView) builder.findViewById(R.id.Stage_name);
		TextView gender = (TextView) builder.findViewById(R.id.gender);
		TextView age = (TextView) builder.findViewById(R.id.age);
		TextView professional = (TextView) builder
				.findViewById(R.id.professional);

		total = (BaseSelectItem) builder.findViewById(R.id.total);
		total.setSelectContent(Arrays.asList(num2));
		total.setSelect2Content(Arrays.asList(num1));
		// total.setSelect3Content(Arrays.asList(num2));
		// total.setSelect4Content(Arrays.asList(num1));
		price = (BaseSelectItem) builder.findViewById(R.id.price);
		price.setSelectContent(Arrays.asList(num2));
		price.setSelect2Content(Arrays.asList(num2));
		price.setSelect3Content(Arrays.asList(num2));
		price.setSelect4Content(Arrays.asList(num1));
		describe = (EditText) builder.findViewById(R.id.describe);
		target = (EditText) builder.findViewById(R.id.target);
		label = (Button) builder.findViewById(R.id.label);

		label.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showLabelDialog();
			}
		});

		builder.findViewById(R.id.relest).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (describe.getText().toString().equals("")) {
							ToastUtil.show(v.getContext(), "请输入权益描述");
							describe.requestFocus();
							return;
						}
						if (region.getContent().equals("请点击选择")) {
							ToastUtil.show(v.getContext(), "请选择城市");
							return;
						}
						if (label.getText().toString().equals("点击选择权益标签")) {
							ToastUtil.show(v.getContext(), "请点击选择权益标签");
							return;
						}

						showWarningDialog("确认发布？", "请您确认权益卡内容，发布后将不能修改删除权益卡");
						// dialog.dismiss();
					}
				});

		Stage_name.setText(MainActivity.user.getNickName());
		gender.setText(MainActivity.personalInformation.getGender());
		age.setText(MainActivity.personalInformation.getAge());
		professional
				.setText(MainActivity.personalInformation.getProfessional());
		Glide.with(this)
				.load(MainActivity.personalInformation.getHead_portrait())
				.centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)
				.placeholder(R.drawable.home_image)
				.into(star_Head_portrait);
		// builder.findViewById(R.id.account);

		dialogAddGiftDialog.show();
	}

	private void showLabelDialog() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		final AlertDialog ad = builder.create();
		ad.show();
		Window window = ad.getWindow();

		int layoutId = R.layout.dialog_alert_select;
		// if (dialogContentId != 0 && dialogContentId ==
		// R.layout.item_dalog_grid) {
		// layoutId = R.layout.dialog_alert2;
		// }
		View view = LayoutInflater.from(this).inflate(layoutId, null);
		window.setContentView(view);
		window.findViewById(R.id.yes).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ad.dismiss();
			}
		});
		window.findViewById(R.id.no).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ad.dismiss();
			}
		});
		LinearLayout dialogContent = (LinearLayout) window
				.findViewById(R.id.selectContent);
		View showView = getLayoutInflater().inflate(
				R.layout.wheel_select_linkage2, null);
		LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		showView.setLayoutParams(layoutParams);

		TextView wheelTitle1 = (TextView) showView.findViewById(R.id.title1);
		TextView wheelTitle2 = (TextView) showView.findViewById(R.id.title2);

		wheelTitle1.setText("类型");
		wheelTitle2.setText("标签");

		dialogContent.addView(showView);
		View selectWheel = dialogContent.findViewById(R.id.selectWheel);
		if (selectWheel != null && selectWheel instanceof SelectWheelLinkage2) {
			((SelectWheelLinkage2) selectWheel).wheelInterfasc = new WheelInterfasc() {

				@Override
				public void selectValue(int selectID, String selectItem,
						boolean oneString) {
					// TODO Auto-generated method stub
					labelString = selectItem;
					label.setText(selectItem);
				}
			};
			((SelectWheelLinkage2) selectWheel).setContentList1(selectContent);
			((SelectWheelLinkage2) selectWheel).setContentList2(select2Content);
			((BaseSelcetWheel) selectWheel).getAll();

		}

	}

	private void showWarningDialog(String title, String message) {
		// TODO Auto-generated method stub
		final WarningDialog.Builder builder = new WarningDialog.Builder(this);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				sendReq();
				dialog.dismiss();
				dialogAddGiftDialog.dismiss();
			}
		});
		WarningDialog dialog = builder.create();

		dialog.show();

	}

	@Override
	public void requestSuccessful(String jsonString, int taskType) {
		// TODO Auto-generated method stub
		switch (taskType) {
		case InfoSource.make_equity_card:
			ToastUtil.show(this, "提交成功");
			initContent();
			break;
		}
	}

	@Override
	public void phoneImage(BaseFragment flag) {
		// TODO Auto-generated method stub
		p = flag;
		phoneImage();
	}

	@Override
	public void takePictureImage(BaseFragment flag) {
		// TODO Auto-generated method stub
		p = flag;
		takePictureImage();
	}

	@Override
	public void obtainImage(String imagePath) {
		// TODO Auto-generated method stub
		p.obtainImage(imagePath);
	}
}
