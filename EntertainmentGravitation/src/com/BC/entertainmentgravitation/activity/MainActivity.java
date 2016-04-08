package com.BC.entertainmentgravitation.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.BC.androidtool.BaseActivity;
import com.BC.androidtool.BrowserAcitvity;
import com.BC.androidtool.HttpThread.WorkerManager;
import com.BC.androidtool.JSON.BaseEntity;
import com.BC.androidtool.view.CircularImage;
import com.BC.entertainmentgravitation.R;
import com.BC.entertainmentgravitation.HttpThread.InfoSource;
import com.BC.entertainmentgravitation.HttpThread.SimpleHttpTask;
import com.BC.entertainmentgravitation.IM.LoginIM;
import com.BC.entertainmentgravitation.fragment.JiaGeQuXianFragment2;
import com.BC.entertainmentgravitation.json.Ranking;
import com.BC.entertainmentgravitation.json.Search;
import com.BC.entertainmentgravitation.json.User;
import com.BC.entertainmentgravitation.json.request.EditPersonal;
import com.BC.entertainmentgravitation.json.response.AuthoritativeInformation.AuthoritativeInformation;
import com.BC.entertainmentgravitation.json.response.starInformation.StarInformation;
import com.BC.entertainmentgravitation.utl.HttpUtil;
import com.BC.entertainmentgravitation.utl.ToastUtil;
import com.BC.entertainmentgravitation.utl.UpdataMainActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.yuntongxun.kitsdk.core.CCPAppManager;
import com.yuntongxun.kitsdk.core.ECKitConstant;
import com.yuntongxun.kitsdk.ui.ECChattingActivity;

public class MainActivity extends BaseActivity implements OnClickListener,
		UpdataMainActivity {

	private View Center, SignIn, activitys, topUp, friends, information, level,
			ToApplyFor, gift, redEnvelope, LuckyDraw, toLevel, searchButton,
			account;
	private EditText searchEdit;
	public static User user;
	public static EditPersonal personalInformation;
	public static StarInformation starInformation;
	public static AuthoritativeInformation authoritativeInformation;

	public static final String PACKAGE_NAME = "com.BC.entertainmentgravitation";
	public static final String RAW_PATH = "/data"
			+ Environment.getDataDirectory().getAbsolutePath() + "/"
			+ PACKAGE_NAME;

	private TextView nickname, levelText, Entertainment_dollar, Stage_name,
			star_information, professional, prices;
	private int pageIndex = 1;
	private int selectIndex = 0;
	private CircularImage Head_portrait;
	private ImageView details, advertisement, star_Head_portrait;
	private ImageButton previous, next;

	private static Activity main;

	// private AnimationDrawable animationDrawable, animationDrawable2;

	ArrayList<Ranking> ranking = new ArrayList<Ranking>();
	ArrayList<Search> searchs;

	private JiaGeQuXianFragment2 jiaGeQuXianFragment;
	private boolean isExit = false;
	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			isExit = false;
		}

	};

	public static void broadcast(String message, String id) {
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) main
				.getSystemService(ns);
		// 定义通知栏展现的内容信息
		int icon = R.drawable.app_logo;
		CharSequence tickerText = "我的通知栏标题";
		long when = System.currentTimeMillis();
		Notification notification = new Notification(icon, tickerText, when);

		// 定义下拉通知栏时要展现的内容信息
		CharSequence contentTitle = "海绵娱";
		CharSequence contentText = message;
		Intent notificationIntent = new Intent(main, ECChattingActivity.class);
		notificationIntent.putExtra(ECKitConstant.KIT_CONVERSATION_TARGET, id);
		notificationIntent.putExtra(ECChattingActivity.CONTACT_USER, "海绵娱用户");
		PendingIntent contentIntent = PendingIntent.getActivity(main, 0,
				notificationIntent, 0);
		notification.setLatestEventInfo(main, contentTitle, contentText,
				contentIntent);

		// 用mNotificationManager的notify方法通知用户生成标题栏消息通知
		mNotificationManager.notify(1, notification);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//SDKInitializer.initialize(this.getApplicationContext());
		setContentView(R.layout.activity_main);
		main = this;
		nickname = (TextView) findViewById(R.id.nickname);
		levelText = (TextView) findViewById(R.id.levelText);
		Entertainment_dollar = (TextView) findViewById(R.id.Entertainment_dollar);
		Stage_name = (TextView) findViewById(R.id.Stage_name);
		star_information = (TextView) findViewById(R.id.star_information);
		professional = (TextView) findViewById(R.id.professional);
		prices = (TextView) findViewById(R.id.prices);
		searchEdit = (EditText) findViewById(R.id.searchEdit);
		Head_portrait = (CircularImage) findViewById(R.id.Head_portrait);
		star_Head_portrait = (ImageView) findViewById(R.id.star_Head_portrait);
		details = (ImageView) findViewById(R.id.details);
		advertisement = (ImageView) findViewById(R.id.advertisement);
		// imageView6 = (ImageView) findViewById(R.id.imageView6);
		previous = (ImageButton) findViewById(R.id.previous);
		next = (ImageButton) findViewById(R.id.next);

		jiaGeQuXianFragment = (JiaGeQuXianFragment2) getSupportFragmentManager()
				.findFragmentById(R.id.fragment1);

		Center = findViewById(R.id.Center);
		SignIn = findViewById(R.id.SignIn);
		activitys = findViewById(R.id.activitys);
		topUp = findViewById(R.id.topUp);
		friends = findViewById(R.id.friends);
		information = findViewById(R.id.information);
		level = findViewById(R.id.level);
		ToApplyFor = findViewById(R.id.ToApplyFor);
		gift = findViewById(R.id.gift);
		redEnvelope = findViewById(R.id.redEnvelope);
		LuckyDraw = findViewById(R.id.LuckyDraw);
		toLevel = findViewById(R.id.toLevel);
		searchButton = findViewById(R.id.searchButton);
		account = findViewById(R.id.account);

		Head_portrait.setImageResource(R.drawable.home_image);

		Center.setOnClickListener(this);
		SignIn.setOnClickListener(this);
		activitys.setOnClickListener(this);
		topUp.setOnClickListener(this);
		friends.setOnClickListener(this);
		information.setOnClickListener(this);
		level.setOnClickListener(this);
		ToApplyFor.setOnClickListener(this);
		gift.setOnClickListener(this);
		redEnvelope.setOnClickListener(this);
		LuckyDraw.setOnClickListener(this);
		searchButton.setOnClickListener(this);

		details.setOnClickListener(this);
		
		
		setImageListener();
		toLevel.setOnClickListener(this);
		previous.setOnClickListener(this);
		next.setOnClickListener(this);

		account.setOnClickListener(this);

		sendReqUser();
		sendReqConnect();
		sendReqActivities();
		initImageLoader();
		// AnimationDrawable animationDrawable = (AnimationDrawable) imageView6
		// .getDrawable();
		// animationDrawable.start();

		// animationDrawable = (AnimationDrawable) getResources().getDrawable(
		// R.drawable.circle2);
		// animationDrawable2 = (AnimationDrawable) getResources().getDrawable(
		// R.drawable.circle3);
		// imageView6.setImageDrawable(animationDrawable);
		// // next.setImageDrawable(animationDrawable2);
		// details.setBackground(animationDrawable2);
		// animationDrawable.start();
		// animationDrawable2.start();
		jiaGeQuXianFragment.setUpdataMainActivity(this);

		LoginIM loginIM = new LoginIM(getApplicationContext());
		loginIM.execute();

		/*BaiduMapUtil bdmu = BaiduMapUtil.newInstance(this);
		bdmu.setGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {

			@Override
			public void onGetReverseGeoCodeResult(ReverseGeoCodeResult arg0) {
				// TODO Auto-generated method stub
				BaiduMapUtil.newInstance(MainActivity.this)
						.setNowAddressComponent(arg0.getAddressDetail());
			}

			@Override
			public void onGetGeoCodeResult(GeoCodeResult arg0) {
				// TODO Auto-generated method stub

			}
		});
		bdmu.shareAddr();*/
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		sendReqUser();
		jiaGeQuXianFragment.sendReqKLineGraph();
		// sendReqConnect();
		// sendReqActivities();
	}

	/**
	 * 获取用户信息
	 */
	private void sendReqUser() {
		if (user == null) {
			ToastUtil.show(this, "登录已失效");
			finish();
			return;
		}
		HashMap<String, String> entity = new HashMap<String, String>();

		entity.put("clientID", user.getClientID());

		SimpleHttpTask httpTask = HttpUtil.packagingHttpTask(entity,
				InfoSource.personal_information);
		showProgressDialog("获取用户基本信息...");
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

	/**
	 * 获取广告及公告信息
	 */
	private void sendReqActivities() {
		HashMap<String, String> entity = new HashMap<String, String>();

		entity.put("clientID", user.getClientID());

		SimpleHttpTask httpTask = HttpUtil.packagingHttpTask(entity,
				InfoSource.activities);
		WorkerManager.addTask(httpTask, this);
	}

	/**
	 * 获取明星排行信息
	 */
	private void sendReqConnect() {
		if (MainActivity.user == null) {
			ToastUtil.show(this, "无法获取信息");
			return;
		}
		HashMap<String, String> entity = new HashMap<String, String>();

		entity.put("clientID", MainActivity.user.getClientID());
		entity.put("The_page_number", "" + pageIndex);
		// entity.put("The_page_number", "" + 1);
		entity.put("type", "1");

		SimpleHttpTask httpTask = HttpUtil.packagingHttpTask(entity,
				InfoSource.in_comparison_to_listApply_to_be_a_platform_star_);
		showProgressDialog("获取信息...");
		WorkerManager.addTask(httpTask, this);
	}

	/**
	 * 搜索
	 */
	private void sendReqSearch(String search) {
		if (MainActivity.user == null) {
			ToastUtil.show(this, "无法获取信息");
			return;
		}
		HashMap<String, String> entity = new HashMap<String, String>();

		entity.put("search", search);

		SimpleHttpTask httpTask = HttpUtil.packagingHttpTask(entity,
				InfoSource.search);
		showProgressDialog("获取信息...");
		WorkerManager.addTask(httpTask, this);
	}

	@Override
	public void requestSuccessful(String jsonString, int taskType) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		Log.i("test", "---jsonString---" + jsonString + "\n");
		Log.i("test", "---taskType---" + taskType + "\n");
		switch (taskType) {
		case InfoSource.personal_information:
			BaseEntity<EditPersonal> baseEntity = gson.fromJson(jsonString,
					new TypeToken<BaseEntity<EditPersonal>>() {
					}.getType());
			personalInformation = baseEntity.getData();
			if (personalInformation != null) {
				personalInformation.getBirthday();
				nickname.setText(personalInformation.getNickname());
				levelText.setText("Lv." + personalInformation.getLevel());
				// Entertainment_dollar.setText(personalInformation
				// .getEntertainment_dollar());
				UpdateTextTask task = new UpdateTextTask(this,
						Long.valueOf(personalInformation
								.getEntertainment_dollar()));
				task.execute();
				Glide.with(this).load(personalInformation.getHead_portrait())
						.centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)
						.placeholder(R.drawable.home_image).into(Head_portrait);

			}
			break;
		case InfoSource.star_information:

			BaseEntity<StarInformation> baseEntity2 = gson.fromJson(jsonString,
					new TypeToken<BaseEntity<StarInformation>>() {
					}.getType());
			starInformation = baseEntity2.getData();
			if (starInformation != null) {

				Glide.with(this).load(starInformation.getFirst_album())
						.centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)
						.placeholder(R.drawable.home_image).into(details);
				jiaGeQuXianFragment.initStarInformation();
			}

			break;
		case InfoSource.activities:
			BaseEntity<AuthoritativeInformation> baseEntity3 = gson.fromJson(
					jsonString,
					new TypeToken<BaseEntity<AuthoritativeInformation>>() {
					}.getType());
			authoritativeInformation = baseEntity3.getData();
			if (authoritativeInformation != null
					&& authoritativeInformation.getAdvertising() != null
					&& authoritativeInformation.getAdvertising().size() > 0) {
				Glide.with(this)
						.load(authoritativeInformation.getAdvertising().get(0)
								.getPicture_address()).centerCrop()
						.diskCacheStrategy(DiskCacheStrategy.ALL)
						.placeholder(R.drawable.home_ad).into(advertisement);
				advertisement.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(v.getContext(),
								BrowserAcitvity.class);
						intent.putExtra("url", authoritativeInformation
								.getAdvertising().get(0).getThe_link_address());
						startActivity(intent);
					}
				});
			}

			break;
		case InfoSource.in_comparison_to_listApply_to_be_a_platform_star_:
			BaseEntity<List<Ranking>> baseEntity4 = gson.fromJson(jsonString,
					new TypeToken<BaseEntity<List<Ranking>>>() {
					}.getType());
			ranking.addAll(baseEntity4.getData());
			if (ranking != null && baseEntity4.getData().size() > 0) {
				if (selectIndex != 0) {
					selectIndex++;
				}
				sendReqStarInformation(ranking.get(selectIndex).getStar_ID());
			} else {
				ToastUtil.show(this, "没有更多数据了");
			}
			break;
		case InfoSource.search:
			BaseEntity<ArrayList<Search>> baseEntity5 = gson.fromJson(
					jsonString, new TypeToken<BaseEntity<ArrayList<Search>>>() {
					}.getType());
			searchs = baseEntity5.getData();
			if (searchs != null && baseEntity5.getData().size() > 0) {
				if (selectIndex != 0) {
					selectIndex++;
				}
				sendReqStarInformation(searchs.get(selectIndex).getSearch());
			} else {
				searchs = null;
				ToastUtil.show(this, "没有搜索结果");
			}
			break;
		}
	}
	
	private float mPosX;
	private float mCurrentPosX;
	private boolean isSlipping = false;
	/**
	 * 图片滑动切换用户
	 */
	public void setImageListener() {
		details.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				switch (event.getAction()) {
				/**
				 * 按下
				 * */
				case MotionEvent.ACTION_DOWN:
					mPosX = event.getX();
					isSlipping = true;
					break;
				/**
				 * 移动
				 * */
				case MotionEvent.ACTION_MOVE:
					mCurrentPosX = event.getX();
					if(isSlipping){
						if (mCurrentPosX - mPosX > 10){
							isSlipping = false;
							nextOne();
						}
						else if (mPosX - mCurrentPosX > 10){
							isSlipping = false;
							lastOne();
						}
					}
				
					break;
				// 拿起
				case MotionEvent.ACTION_UP:
					if(isSlipping){
						Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
						startActivity(intent);
					}
					break;
				default:
					break;
				}
				return true;
			}
		});
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent;
		switch (v.getId()) {
		/**
		 * 个人中心
		 */
		case R.id.Center:
			intent = new Intent(this, CenterActivity.class);
			startActivity(intent);
			break;
		/**
		 * 签到
		 */
		case R.id.SignIn:
			intent = new Intent(this, SignInActivity.class);
			startActivity(intent);
			break;
		/**
		 * 充值
		 */
		case R.id.topUp:
			intent = new Intent(this, TopUpActivity.class);
			startActivity(intent);
			break;
		/**
		 * 活动
		 */
		case R.id.activitys:
			intent = new Intent(this, HuodongActivity.class);
			startActivity(intent);
			break;
		/**
		 * 好友
		 */
		case R.id.friends:
			intent = new Intent(this, FriendsActivity.class);
			startActivity(intent);
			break;
		/**
		 * 消息
		 */
		case R.id.information:
			intent = new Intent(this, InformationActivity.class);
			startActivity(intent);
			break;
		/**
		 * 点赞排行
		 */
		case R.id.level:
			intent = new Intent(this, LevelAcitivity.class);
			startActivity(intent);
			break;
		/**
		 * 申请
		 */
		case R.id.ToApplyFor:
			if (!user.getPermission().equals("2")) {
				intent = new Intent(this, ToApplyForActivity.class);
				startActivity(intent);
			} else {
				ToastUtil.show(this, "您已出道成为明星了");
			}
			break;
		/**
		 * 礼品
		 */
		case R.id.gift:
			// intent = new Intent(this, GiftActivity.class);
			// startActivity(intent);
			intent = new Intent(this, GiftActivity2.class);
			startActivity(intent);
			break;
		/**
		 * 红包
		 */
		case R.id.redEnvelope:
			intent = new Intent(this, RedEnvelopeActivity.class);
			startActivity(intent);
			break;
		/**
		 * 抽奖
		 */
		case R.id.LuckyDraw:
			intent = new Intent(this, LuckyDrawActivity.class);
			startActivity(intent);
			break;
		/**
		 * 上一个明星
		 */
		case R.id.previous:
			lastOne();
			break;
		/**
		 * 下一个明星
		 */
		case R.id.next:
			nextOne();
			break;
		/**
		 * 排行榜
		 */
		case R.id.toLevel:
			intent = new Intent(this, ToLevelActivity.class);
			startActivity(intent);
			break;
		/**
		 * 明星详情
		 */
		case R.id.details:
			intent = new Intent(this, DetailsActivity.class);
			startActivity(intent);
			break;
		/**
		 * 查询娱币交易信息
		 */
		case R.id.account:
			intent = new Intent(this, GoldActivity.class);
			startActivity(intent);
			break;
		/**
		 * 搜索
		 */
		case R.id.searchButton:
			String search = searchEdit.getText().toString();
			selectIndex = 0;
			if (search.equals("")) {
				searchs = null;
				sendReqConnect();
			} else {
				sendReqSearch(search);
			}
			break;

		default:
			break;
		}
	}
	
	/** 
	 * 首页展示,切换明星
	 * 上一个
	 ***/
	public void lastOne(){
		if (selectIndex > 0) {
			selectIndex--;
			if (searchs != null) {
				sendReqStarInformation(searchs.get(selectIndex).getSearch());
			} else {
				sendReqStarInformation(ranking.get(selectIndex)
						.getStar_ID());
			}
		} else {
			ToastUtil.show(this, "没有更多数据了");
		}
	}
	
	/** 
	 * 首页展示,切换明星
	 * 下一个
	 ***/
	public void nextOne(){
		if (searchs != null) {
			if (selectIndex < searchs.size() - 1) {
				selectIndex++;
				sendReqStarInformation(searchs.get(selectIndex).getSearch());
			} else {
				ToastUtil.show(this, "没有更多数据了");
			}
		} else {
			if (selectIndex < ranking.size() - 1) {
				selectIndex++;
				sendReqStarInformation(ranking.get(selectIndex)
						.getStar_ID());
			} else {
				pageIndex++;
				sendReqConnect();
			}
		}
	}

	@Override
	public void updataMainActivity() {
		// TODO Auto-generated method stub
		sendReqUser();
	}

	private void initImageLoader() {
		File cacheDir = StorageUtils.getOwnCacheDirectory(
				getApplicationContext(), "ECSDK_Demo/image");
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				this).threadPoolSize(1)
				// 线程池内加载的数量
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.memoryCache(new WeakMemoryCache())
				// .denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(CCPAppManager.md5FileNameGenerator)
				// 将保存的时候的URI名称用MD5 加密
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.diskCache(
						new UnlimitedDiscCache(cacheDir, null,
								CCPAppManager.md5FileNameGenerator))// 自定义缓存路径
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				// .writeDebugLogs() // Remove for release app
				.build();// 开始构建
		ImageLoader.getInstance().init(config);
	}

	public void exit() {
		if (!isExit) {
			isExit = true;
			Toast.makeText(getApplicationContext(), "再按一次退出登录",
					Toast.LENGTH_SHORT).show();
			mHandler.sendEmptyMessageDelayed(0, 2000);
		} else {
			finish();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exit();
			return false;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	class UpdateTextTask extends AsyncTask<Void, Integer, Integer> {
		private Context context;
		private long j;

		UpdateTextTask(Context context, long j) {
			this.context = context;
			this.j = j;
			// this.saveUtil = saveUtil;
		}

		/**
		 * 运行在UI线程中，在调用doInBackground()之前执行
		 */
		@Override
		protected void onPreExecute() {

		}

		/**
		 * 后台运行的方法，可以运行非UI线程，可以执行耗时的方法
		 */
		@Override
		protected Integer doInBackground(Void... params) {
			int i = 10;
			while (i > 1) {
				publishProgress(i);
				i--;
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
				}
			}
			return i;
		}

		/**
		 * 运行在ui线程中，在doInBackground()执行完毕后执行
		 */
		@Override
		protected void onPostExecute(Integer integer) {
			Entertainment_dollar.setText(j + "");
		}

		/**
		 * 在publishProgress()被调用以后执行，publishProgress()用于更新进度
		 */
		@Override
		protected void onProgressUpdate(Integer... values) {
			int v = values[0];
			Entertainment_dollar.setText((j / v) + "");

		}
	}
}
