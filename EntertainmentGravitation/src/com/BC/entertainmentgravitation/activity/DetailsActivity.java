package com.BC.entertainmentgravitation.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.BC.androidtool.BaseActivity;
import com.BC.androidtool.BrowserAcitvity;
import com.BC.androidtool.HttpThread.WorkerManager;
import com.BC.androidtool.JSON.BaseEntity;
import com.BC.androidtool.view.CircularImage;
import com.BC.entertainmentgravitation.R;
import com.BC.entertainmentgravitation.HttpThread.InfoSource;
import com.BC.entertainmentgravitation.HttpThread.SimpleHttpTask;
import com.BC.entertainmentgravitation.adapter.PictureAdapter;
import com.BC.entertainmentgravitation.fragment.ConnectFragment;
import com.BC.entertainmentgravitation.fragment.PictureFragment;
import com.BC.entertainmentgravitation.json.response.album.Album;
import com.BC.entertainmentgravitation.json.response.album.Photo_images;
import com.BC.entertainmentgravitation.json.response.starInformation.StarInformation;
import com.BC.entertainmentgravitation.utl.ApplauseGiveConcern;
import com.BC.entertainmentgravitation.utl.HttpUtil;
import com.BC.entertainmentgravitation.utl.ToastUtil;
import com.BC.entertainmentgravitation.view.dialog.WarningDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class DetailsActivity extends BaseActivity implements OnClickListener {
	Album album;
	Button radio3;
	ImageButton Applause, BigBird, FocusOn;
	ImageButton previous, next;
	RadioGroup radioGroup1;
	CircularImage Head_portrait;
	ImageView details, vidoImage;
	ArrayList<String> showImage = new ArrayList<String>();
	int pageIndex = 1;
	int type = R.id.radio0;
	PictureAdapter adapter;
	String userID;
	ApplauseGiveConcern applauseGiveConcern;
	public StarInformation starInformation;

	// int p = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xiang_xi);
		radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
		Head_portrait = (CircularImage) findViewById(R.id.Head_portrait);
		radio3 = (Button) findViewById(R.id.radio3);
		previous = (ImageButton) findViewById(R.id.previous);
		next = (ImageButton) findViewById(R.id.next);
		details = (ImageView) findViewById(R.id.details);

		adapter = new PictureAdapter(showImage, this);
		Intent intent = getIntent();
		if (intent != null) {
			userID = intent.getStringExtra("userID");
			if (userID == null) {
				userID = MainActivity.starInformation.getStar_ID();
			}
		}
		radio3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(v.getContext(),
						JiaGeQuXianActivity.class);
				JiaGeQuXianActivity.starID = userID;
				startActivity(intent);
			}
		});
		findViewById(R.id.fenxiang).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				showShare();
				showShare(DetailsActivity.this);
			}
		});
		applauseGiveConcern = new ApplauseGiveConcern(this,
				MainActivity.starInformation.getStar_ID(), this,
				MainActivity.starInformation
						.getThe_current_hooted_thumb_up_prices(),
				MainActivity.starInformation.getStage_name());
		// previous.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// if (p == 0) {
		// if (pageIndex == 1) {
		// ToastUtil.show(v.getContext(), "已是第一张");
		// } else {
		// pageIndex--;
		// sendReqAlbum(pageIndex);
		// }
		// } else {
		// p--;
		// initPersonalInformation(p);
		// }
		// }
		// });
		// next.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// switch (type) {
		// case R.id.radio0:
		// if (album.getMore_pictures().size() - 1 == p) {
		// pageIndex++;
		// sendReqAlbum(pageIndex);
		// } else {
		// p++;
		// initPersonalInformation(p);
		// }
		// break;
		// case R.id.radio1:
		// if (album.getPhoto_images().size() - 1 == p) {
		// pageIndex++;
		// sendReqAlbum(pageIndex);
		// } else {
		// p++;
		// initPersonalInformation(p);
		// }
		// break;
		// case R.id.radio2:
		// if (album.getPhotographs().size() - 1 == p) {
		// pageIndex++;
		// sendReqAlbum(pageIndex);
		// } else {
		// p++;
		// initPersonalInformation(p);
		// }
		// break;
		//
		// }
		// }
		// });
		radioGroup1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				type = checkedId;
				sendReqAlbum(1);
				// p = 0;
				pageIndex = 1;
			}
		});
		init();
		details.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showImageDialog();
			}
		});
		sendReqStarInformation(userID);
	}

	private void showShare(Context context){
		   ShareSDK.initSDK(context, "10ee118b8af16");

		   OnekeyShare oks = new OnekeyShare(); 
		   //关闭sso授权
		   oks.disableSSOWhenAuthorize();
		   // 分享时Notification的图标和文字
		   oks.setTitle("看演员，去海绵娱直播APP!");
		   oks.setText("看演员，去海绵娱直播APP!" + "(" + MainActivity.user.getNickName() + "正在直播中)");
		   oks.setSite(getString(R.string.app_name));
		   //分享链接地址
		   oks.setUrl("http://shouji.baidu.com/soft/item?docid=9008168");
		   //logo地址
		   oks.setImageUrl("http://app.haimianyu.cn/DOWNLOAD/app_logo.png");
		   oks.show(context);
		}

	private void initImages(List<Photo_images> abums) {
		// TODO Auto-generated method stub
		if (pageIndex == 1) {
			showImage.clear();
			adapter.setImages(showImage);
		}
		for (int i = 0; i < abums.size(); i++) {
			showImage.add(abums.get(i).getPicture_address());
			adapter.setImages(showImage);
		}

	}

	private void showImageDialog() {
		// TODO Auto-generated method stub
		final PictureFragment fragment = new PictureFragment();
		fragment.setStyle(R.style.Dialog, DialogFragment.STYLE_NO_FRAME);
		fragment.show(getSupportFragmentManager(), "PictureDialog");
		fragment.setAdapter(adapter);
		fragment.setChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				if (showImage.size() - 1 == arg0) {
					pageIndex++;
					sendReqAlbum(pageIndex);
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
		// dialog.show();

	}

	private void showOutConnectDialog() {
		ConnectFragment connectFragment = new ConnectFragment();
		connectFragment.setStyle(R.style.Dialog, DialogFragment.STYLE_NO_FRAME);
		connectFragment.show(getSupportFragmentManager(), "dialog");
	}

	private void init() {
		// TODO Auto-generated method stub
		Applause = (ImageButton) findViewById(R.id.applause);
		BigBird = (ImageButton) findViewById(R.id.bigbird);
		FocusOn = (ImageButton) findViewById(R.id.FocusOn);

		Applause.setOnClickListener(this);
		BigBird.setOnClickListener(this);
		FocusOn.setOnClickListener(this);

		vidoImage = (ImageView) findViewById(R.id.vidoImage);

		findViewById(R.id.allConnect).setOnClickListener(this);
		findViewById(R.id.vidoConnect).setOnClickListener(this);
	}

	public void initPersonalInformation() {
		// TODO Auto-generated method stub
		if (album == null) {
			ToastUtil.show(this, "获取数据失败");
			return;
		}
		switch (type) {
		case R.id.radio0:
			if (album.getMore_pictures().size() <= 0) {
				ToastUtil.show(this, "没有更多了");
				return;
			} else {
				initImages(album.getMore_pictures());
			}
			if (pageIndex == 1) {
				Glide.with(this)
						.load(album.getMore_pictures().get(0)
								.getPicture_address()).centerCrop()
						.diskCacheStrategy(DiskCacheStrategy.ALL)
						.placeholder(R.drawable.home_image).into(details);
			}
			break;
		case R.id.radio1:
			if (album.getPhoto_images().size() <= 0) {
				ToastUtil.show(this, "没有更多了");
				return;
			} else {
				initImages(album.getPhoto_images());
			}
			if (pageIndex == 1) {
				Glide.with(this)
						.load(album.getPhoto_images().get(0)
								.getPicture_address()).centerCrop()
						.diskCacheStrategy(DiskCacheStrategy.ALL)
						.placeholder(R.drawable.home_image).into(details);
			}
			break;
		case R.id.radio2:
			if (album.getPhotographs().size() <= 0) {
				ToastUtil.show(this, "没有更多了");
				return;
			} else {
				initImages(album.getPhotographs());
			}
			if (pageIndex == 1) {
				Glide.with(this)
						.load(album.getPhotographs().get(0)
								.getPicture_address()).centerCrop()
						.diskCacheStrategy(DiskCacheStrategy.ALL)
						.placeholder(R.drawable.home_image).into(details);
			}
			break;

		}
	}

	/**
	 * 获取相册信息
	 */
	private void sendReqAlbum(int pageIndex) {
		HashMap<String, String> entity = new HashMap<String, String>();

		entity.put("clientID", userID);
		entity.put("The_page_number", "" + pageIndex);

		SimpleHttpTask httpTask = HttpUtil.packagingHttpTask(entity,
				InfoSource.photo_album_management);
		showProgressDialog("获取信息...");
		WorkerManager.addTask(httpTask, this);
	}

	/**
	 * 获取明星信息
	 */
	private void sendReqStarInformation(String starID) {
		HashMap<String, String> entity = new HashMap<String, String>();
		entity.put("clientID", MainActivity.user.getClientID());
		entity.put("Star_ID", starID);

		SimpleHttpTask httpTask = HttpUtil.packagingHttpTask(entity,
				InfoSource.star_information);
		showProgressDialog("获取明星基本信息...");
		WorkerManager.addTask(httpTask, this);
	}

	private void initContent() {
		// TODO Auto-generated method stub
		if (starInformation == null) {
			ToastUtil.show(this, "获取信息失败");
		}
		Glide.with(this).load(starInformation.getHead_portrait()).centerCrop()
				.diskCacheStrategy(DiskCacheStrategy.ALL)
				.placeholder(R.drawable.home_image).into(Head_portrait);
		if (starInformation.getVideo_link() != null) {
			Glide.with(this).load(starInformation.getVideo_link().getIcon())
					.centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)
					.placeholder(R.drawable.void_4).into(vidoImage);
		}
		setText(R.id.Stage_name, starInformation.getStage_name());
		setText(R.id.professional, starInformation.getProfessional());
		setText(R.id.The_constellation, starInformation.getThe_constellation());
		setText(R.id.height, starInformation.getHeight() + "cm | "
				+ starInformation.getWeight() + "kg");
		setText(R.id.gender, starInformation.getGender());
		setText(R.id.language, starInformation.getLanguage());
		setText(R.id.nationality, starInformation.getNationality());
		setText(R.id.region, starInformation.getRegion());
		setText(R.id.age, starInformation.getAge());
		setText(R.id.Agent_name, starInformation.getAgent_name());
		setText(R.id.The_phone, starInformation.getThe_phone());
		setText(R.id.QQ, starInformation.getQQ());
		setText(R.id.WeChat, starInformation.getWeChat());
		setText(R.id.email, starInformation.getEmail());
		setText(R.id.address, starInformation.getAddress());
		setText(R.id.Describe_the_text, starInformation.getDescribe_the_text());

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
				dialog.dismiss();
				Intent intent = new Intent(DetailsActivity.this,
						TopUpActivity.class);
				startActivity(intent);
			}
		});
		WarningDialog dialog = builder.create();

		dialog.show();

	}

	@Override
	public void requestFailed(int errcode, String message, int taskType) {
		// TODO Auto-generated method stub
		super.requestFailed(errcode, message, taskType);
		if (message.equals("娱币不足！")) {
			showWarningDialog("是否购买娱币", "您的娱币不足是否去购买");
		}
	}

	@Override
	public void requestSuccessful(String jsonString, int taskType) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		switch (taskType) {
		case InfoSource.photo_album_management:
			// if (MainActivity.user.getPermission().equals("2")) {
			BaseEntity<Album> baseEntity5 = gson.fromJson(jsonString,
					new TypeToken<BaseEntity<Album>>() {
					}.getType());
			album = baseEntity5.getData();
			if (album != null) {
				// p = 0;
				initPersonalInformation();
			} else {
				ToastUtil.show(this, "获取数据失败");
			}
			// } else {
			// BaseEntity<List<More_pictures>> baseEntity5 = gson.fromJson(
			// jsonString,
			// new TypeToken<BaseEntity<List<More_pictures>>>() {
			// }.getType());
			// Album album = new Album();
			// List<More_pictures> more_pictures = baseEntity5.getData();
			// album.setMore_pictures(more_pictures);
			// album.setPhoto_images(new ArrayList<Photo_images>());
			// album.setPhotographs(new ArrayList<Photographs>());
			// if (album != null) {
			// setAlbum(album);
			// initPersonalInformation();
			// } else {
			// ToastUtil.show(activity, "获取数据失败");
			// }
			// radio.setVisibility(View.GONE);
			// }

			break;
		case InfoSource.star_information:

			BaseEntity<StarInformation> baseEntity2 = gson.fromJson(jsonString,
					new TypeToken<BaseEntity<StarInformation>>() {
					}.getType());
			starInformation = baseEntity2.getData();
			if (starInformation != null) {
				initContent();
				sendReqAlbum(1);
				applauseGiveConcern = new ApplauseGiveConcern(
						this,
						starInformation.getStar_ID(),
						this,
						starInformation.getThe_current_hooted_thumb_up_prices(),
						starInformation.getStage_name());
			}

			break;
		case InfoSource.give_applause_booed:
			ToastUtil.show(this, "提交成功");
			switch (applauseGiveConcern.getType()) {
			case 1:
				applauseGiveConcern.showAnimationDialog(R.drawable.circle4,
						R.raw.applaud);
				break;
			case 2:
				applauseGiveConcern.showAnimationDialog(R.drawable.circle5,
						R.raw.give_back);
				break;
			default:
				applauseGiveConcern.showAnimationDialog(R.drawable.circle4,
						R.raw.applaud);
				break;
			}

			break;
		case InfoSource.and_attention:
			ToastUtil.show(this, "提交成功");
			applauseGiveConcern.showAnimationDialog(R.drawable.circle6,
					R.raw.concern);
			break;
		}
	}

	/**
	 * 加关注
	 */
	private void sendReqAndAttention() {
		if (MainActivity.user == null || MainActivity.starInformation == null) {
			ToastUtil.show(this, "抱歉，提交失败");
			return;
		}
		HashMap<String, String> entity = new HashMap<String, String>();

		entity.put("clientID", MainActivity.user.getClientID());
		entity.put("Star_ID", userID);

		SimpleHttpTask httpTask = HttpUtil.packagingHttpTask(entity,
				InfoSource.and_attention);
		showProgressDialog("正在提交...");
		WorkerManager.addTask(httpTask, this);
	}

	/**
	 * 鼓掌、喝倒彩
	 */
	private void sendReqGiveApplauseBooed(int type) {
		if (MainActivity.user == null || MainActivity.starInformation == null) {
			ToastUtil.show(this, "抱歉，提交失败");
			return;
		}
		HashMap<String, String> entity = new HashMap<String, String>();

		entity.put("clientID", MainActivity.user.getClientID());
		entity.put("Star_ID", userID);
		entity.put("Type", "" + type);

		SimpleHttpTask httpTask = HttpUtil.packagingHttpTask(entity,
				InfoSource.give_applause_booed);
		showProgressDialog("正在提交...");
		WorkerManager.addTask(httpTask, this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (applauseGiveConcern == null) {
			return;
		}
		switch (v.getId()) {
		/**
		 * 鼓掌
		 */
		case R.id.applause:
			applauseGiveConcern.showApplaudDialog(1);
			// applauseGiveConcern.showAnimationDialog(R.drawable.circle4,
			// R.raw.applaud);
			break;
		/**
		 * 倒彩
		 */
		case R.id.bigbird:
			applauseGiveConcern.showApplaudDialog(2);
			// applauseGiveConcern.showAnimationDialog(R.drawable.circle5,
			// R.raw.give_back);
			break;
		/**
		 * 关注
		 */
		case R.id.FocusOn:
			applauseGiveConcern.sendReqAndAttention();
			// applauseGiveConcern.showAnimationDialog(R.drawable.circle6,
			// R.raw.concern);
			break;
		case R.id.allConnect:
			showOutConnectDialog();
			break;
		case R.id.vidoConnect:
			if (MainActivity.starInformation.getVideo_link() != null) {
				Intent intent = new Intent(this, BrowserAcitvity.class);
				intent.putExtra("url", starInformation.getVideo_link()
						.getLink());
				startActivity(intent);
			} else {
				ToastUtil.show(this, "没有连接");
			}
			break;
		}
	}
}
