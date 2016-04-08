package com.BC.entertainmentgravitation.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.BC.androidtool.BaseActivity;
import com.BC.androidtool.HttpThread.WorkerManager;
import com.BC.androidtool.JSON.BaseEntity;
import com.BC.androidtool.views.RadioGroupLayout;
import com.BC.entertainmentgravitation.R;
import com.BC.entertainmentgravitation.HttpThread.InfoSource;
import com.BC.entertainmentgravitation.HttpThread.SimpleHttpTask;
import com.BC.entertainmentgravitation.json.The_prize;
import com.BC.entertainmentgravitation.json.The_prize_list;
import com.BC.entertainmentgravitation.utl.HttpUtil;
import com.BC.entertainmentgravitation.utl.ToastUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class LuckyDrawActivity extends BaseActivity {
	boolean isRun = false;
	Button runButton;
	RadioGroupLayout groupLayout;

	List<The_prize_list> The_prize_list;
	The_prize_list prize;
	int j = 0;

	ArrayList<RadioButton> radioButtons = new ArrayList<RadioButton>();
	RadioButton radioButton1;
	RadioButton radioButton2;
	RadioButton radioButton3;
	RadioButton radioButton4;
	RadioButton radioButton5;
	RadioButton radioButton7;
	RadioButton radioButton9;
	RadioButton radioButton14;
	RadioButton radioButton13;
	RadioButton radioButton12;
	RadioButton radioButton11;
	RadioButton radioButton10;
	RadioButton radioButton8;
	RadioButton radioButton6;
	ArrayList<ImageView> imageViews = new ArrayList<ImageView>();
	ImageView imageView1;
	ImageView imageView2;
	ImageView imageView3;
	ImageView imageView4;
	ImageView imageView5;
	ImageView imageView7;
	ImageView imageView9;
	ImageView imageView14;
	ImageView imageView13;
	ImageView imageView12;
	ImageView imageView11;
	ImageView imageView10;
	ImageView imageView8;
	ImageView imageView6;

	private boolean canSin = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chou_jiang);
		runButton = (Button) findViewById(R.id.runButton);
		groupLayout = (RadioGroupLayout) findViewById(R.id.RadioGroup01);
		groupLayout.setClickable(false);
		runButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!canSin) {
					ToastUtil.show(v.getContext(), "抱歉您已无抽奖次数，请明天再试");
					return;
				}
				if (!isRun) {
					if (j != 0) {
						update();
					}
				}
			}
		});
		radioButton1 = (RadioButton) findViewById(R.id.radioButton1);
		radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
		radioButton3 = (RadioButton) findViewById(R.id.radioButton3);
		radioButton4 = (RadioButton) findViewById(R.id.radioButton4);
		radioButton5 = (RadioButton) findViewById(R.id.radioButton5);
		radioButton7 = (RadioButton) findViewById(R.id.radioButton7);
		radioButton9 = (RadioButton) findViewById(R.id.radioButton9);
		radioButton14 = (RadioButton) findViewById(R.id.radioButton14);
		radioButton13 = (RadioButton) findViewById(R.id.radioButton13);
		radioButton12 = (RadioButton) findViewById(R.id.radioButton12);
		radioButton11 = (RadioButton) findViewById(R.id.radioButton11);
		radioButton10 = (RadioButton) findViewById(R.id.radioButton10);
		radioButton8 = (RadioButton) findViewById(R.id.radioButton8);
		radioButton6 = (RadioButton) findViewById(R.id.radioButton6);

		radioButtons.add(radioButton1);
		radioButtons.add(radioButton2);
		radioButtons.add(radioButton3);
		radioButtons.add(radioButton4);
		radioButtons.add(radioButton5);
		radioButtons.add(radioButton7);
		radioButtons.add(radioButton9);
		radioButtons.add(radioButton14);
		radioButtons.add(radioButton13);
		radioButtons.add(radioButton12);
		radioButtons.add(radioButton11);
		radioButtons.add(radioButton10);
		radioButtons.add(radioButton8);
		radioButtons.add(radioButton6);
		imageView1 = (ImageView) findViewById(R.id.imageView1);
		imageView2 = (ImageView) findViewById(R.id.imageView2);
		imageView3 = (ImageView) findViewById(R.id.imageView3);
		imageView4 = (ImageView) findViewById(R.id.imageView4);
		imageView5 = (ImageView) findViewById(R.id.imageView5);
		imageView7 = (ImageView) findViewById(R.id.imageView7);
		imageView9 = (ImageView) findViewById(R.id.imageView9);
		imageView14 = (ImageView) findViewById(R.id.imageView14);
		imageView13 = (ImageView) findViewById(R.id.imageView13);
		imageView12 = (ImageView) findViewById(R.id.imageView12);
		imageView11 = (ImageView) findViewById(R.id.imageView11);
		imageView10 = (ImageView) findViewById(R.id.imageView10);
		imageView8 = (ImageView) findViewById(R.id.imageView8);
		imageView6 = (ImageView) findViewById(R.id.imageView6);

		imageViews.add(imageView1);
		imageViews.add(imageView2);
		imageViews.add(imageView3);
		imageViews.add(imageView4);
		imageViews.add(imageView5);
		imageViews.add(imageView7);
		imageViews.add(imageView9);
		imageViews.add(imageView14);
		imageViews.add(imageView13);
		imageViews.add(imageView12);
		imageViews.add(imageView11);
		imageViews.add(imageView10);
		imageViews.add(imageView8);
		imageViews.add(imageView6);

		sendReqThePrizeList();
	}

	/**
	 * 获取列表
	 */
	private void sendReqThePrizeList() {
		if (MainActivity.user == null) {
			ToastUtil.show(this, "无法获取信息");
			return;
		}
		HashMap<String, String> entity = new HashMap<String, String>();

		entity.put("clientID", MainActivity.user.getClientID());

		SimpleHttpTask httpTask = HttpUtil.packagingHttpTask(entity,
				InfoSource.the_prize_list);
		showProgressDialog("获取数据...");
		WorkerManager.addTask(httpTask, this);
	}

	/**
	 * 提交结果
	 */
	private void sendReqLotteryResultsSubmitted(String The_prize_ID) {
		if (MainActivity.user == null) {
			ToastUtil.show(this, "无法获取信息");
			return;
		}
		HashMap<String, String> entity = new HashMap<String, String>();

		entity.put("clientID", MainActivity.user.getClientID());
		entity.put("The_prize_ID", The_prize_ID);

		SimpleHttpTask httpTask = HttpUtil.packagingHttpTask(entity,
				InfoSource.lottery_results_submitted);
		showProgressDialog("获取数据...");
		WorkerManager.addTask(httpTask, this);
	}

	@Override
	public void requestSuccessful(String jsonString, int taskType) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		switch (taskType) {
		case InfoSource.the_prize_list:
			BaseEntity<The_prize> baseEntity5 = gson.fromJson(jsonString,
					new TypeToken<BaseEntity<The_prize>>() {
					}.getType());
			int can = baseEntity5.getData().getCanSin();

			if (can == 0) {
				canSin = true;
			} else {
				canSin = false;
			}

			The_prize_list = baseEntity5.getData().getArray();
			if (The_prize_list != null && The_prize_list.size() > 5) {

				double d = Math.random();

				if (d > 0.05) {
					Random rand = new Random();
					int t = rand.nextInt(4);
					j = (t * 2) + 3 * 14;
					prize = The_prize_list.get(0);
				} else if (d > 0.02) {
					Random rand = new Random();
					int t = rand.nextInt(4);
					j = (13 - t * 2) + 3 * 14;
					prize = The_prize_list.get(1);
				} else if (d > 0.01) {
					j = 10 + 3 * 14;
					prize = The_prize_list.get(2);
				} else if (d > 0.01) {
					Random rand = new Random();
					int t = rand.nextInt(4);
					j = 3 + 3 * 14;
					prize = The_prize_list.get(3);
				} else if (d == 0.00001) {
					j = (14 - 2) + 3 * 14;
					prize = The_prize_list.get(5);
				} else {
					Random rand = new Random();
					j = 1 + 3 * 14;
					prize = The_prize_list.get(4);
				}
				 Log.e("-----shuzhi", "-----shu1zhi" + j);
				 Log.e("-----shuzhi", "-----shu2zhi" + d);
				 Log.e("-----shuzhi", "-----shu3zhi" + prize);
				setContent(radioButtons.get(0), imageViews.get(0),
						The_prize_list.get(0));
				setContent(radioButtons.get(1), imageViews.get(1),
						The_prize_list.get(4));
				setContent(radioButtons.get(2), imageViews.get(2),
						The_prize_list.get(0));
				setContent(radioButtons.get(3), imageViews.get(3),
						The_prize_list.get(3));
				setContent(radioButtons.get(4), imageViews.get(4),
						The_prize_list.get(0));
				setContent(radioButtons.get(5), imageViews.get(5),
						The_prize_list.get(2));
				setContent(radioButtons.get(6), imageViews.get(6),
						The_prize_list.get(0));
				setContent(radioButtons.get(7), imageViews.get(7),
						The_prize_list.get(1));
				setContent(radioButtons.get(8), imageViews.get(8),
						The_prize_list.get(3));
				setContent(radioButtons.get(9), imageViews.get(9),
						The_prize_list.get(1));
				setContent(radioButtons.get(10), imageViews.get(10),
						The_prize_list.get(2));
				setContent(radioButtons.get(11), imageViews.get(11),
						The_prize_list.get(1));
				setContent(radioButtons.get(12), imageViews.get(12),
						The_prize_list.get(5));
				setContent(radioButtons.get(13), imageViews.get(13),
						The_prize_list.get(1));
			}
			break;
		case InfoSource.lottery_results_submitted:
			ToastUtil.show(this, "获奖数据保存成功");
			sendReqThePrizeList();
			break;

		}
	}

	private void setContent(RadioButton radioButton, ImageView imageView,
			The_prize_list prize) {
		// TODO Auto-generated method stub
		radioButton.setText(prize.getThe_prize_described());
		// radioButton.
		Glide.with(this).load(prize.getThe_prize_images()).centerCrop()
				.diskCacheStrategy(DiskCacheStrategy.ALL)
				.placeholder(R.drawable.home_image).into(imageView);
	}

	private void update() {
		UpdateTextTask updateTextTask = new UpdateTextTask(this);
		updateTextTask.execute();
		isRun = true;
	}

	class UpdateTextTask extends AsyncTask<Void, Integer, Integer> {
		private Context context;

		UpdateTextTask(Context context) {
			this.context = context;
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
			int i = 0;
			// Random rand = new Random();
			// 生成20-30之间的
			// int j = 50 + rand.nextInt(50);
			while (i <= j) {
				publishProgress(i);
				i++;
				try {
					Thread.sleep(1 * i);
				} catch (InterruptedException e) {
				}
			}
			int v = i;
			int k = v % 14;
			return k;
		}

		/**
		 * 运行在ui线程中，在doInBackground()执行完毕后执行
		 */
		@Override
		protected void onPostExecute(Integer integer) {
			
			
			
			isRun = false;
			Toast.makeText(context,
					"抽奖完毕,您抽到了" + prize.getThe_prize_described(),
					Toast.LENGTH_SHORT).show();
			if (!prize.getThe_prize_ID().equals("0")
					&& !prize.getThe_prize_ID().equals("1")
					&& !prize.getThe_prize_ID().equals("2")) {
				Toast.makeText(context, "您的奖品将在活动页中公布", Toast.LENGTH_SHORT)
						.show();
			} else {
			}
			sendReqLotteryResultsSubmitted(prize.getThe_prize_ID());
			// finish();
		}

		/**
		 * 在publishProgress()被调用以后执行，publishProgress()用于更新进度
		 */
		@Override
		protected void onProgressUpdate(Integer... values) {
			int v = values[0];
			int k = v % 14;
			switch (k) {
			case 0:
				groupLayout.check(R.id.radioButton1);
				break;
			case 1:
				groupLayout.check(R.id.radioButton2);
				break;
			case 2:
				groupLayout.check(R.id.radioButton3);
				break;
			case 3:
				groupLayout.check(R.id.radioButton4);
				break;
			case 4:
				groupLayout.check(R.id.radioButton5);
				break;
			case 5:
				groupLayout.check(R.id.radioButton7);
				break;
			case 6:
				groupLayout.check(R.id.radioButton9);
				break;
			case 7:
				groupLayout.check(R.id.radioButton14);
				break;
			case 8:
				groupLayout.check(R.id.radioButton13);
				break;
			case 9:
				groupLayout.check(R.id.radioButton12);
				break;
			case 10:
				groupLayout.check(R.id.radioButton11);
				break;
			case 11:
				groupLayout.check(R.id.radioButton10);
				break;
			case 12:
				groupLayout.check(R.id.radioButton8);
				break;
			case 13:
				groupLayout.check(R.id.radioButton6);
				break;

			}
		}
	}
}
