package com.BC.entertainmentgravitation.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.BC.androidtool.BaseActivity;
import com.BC.androidtool.HttpThread.WorkerManager;
import com.BC.androidtool.JSON.BaseEntity;
import com.BC.androidtool.adapter.CommonAdapter;
import com.BC.androidtool.views.pull.PullToRefreshBase;
import com.BC.androidtool.views.pull.PullToRefreshBase.OnRefreshListener2;
import com.BC.androidtool.views.pull.PullToRefreshListView;
import com.BC.entertainmentgravitation.R;
import com.BC.entertainmentgravitation.HttpThread.InfoSource;
import com.BC.entertainmentgravitation.HttpThread.SimpleHttpTask;
import com.BC.entertainmentgravitation.json.Ranking;
import com.BC.entertainmentgravitation.utl.ApplauseGiveConcern;
import com.BC.entertainmentgravitation.utl.HttpUtil;
import com.BC.entertainmentgravitation.utl.ToastUtil;
import com.BC.entertainmentgravitation.view.dialog.WarningDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ToLevelActivity extends BaseActivity implements
		OnItemClickListener {
	PullToRefreshListView pullToRefreshListView1;
	RadioGroup radioGroup1;
	int type = 1;
	List<Ranking> ranking;
	ApplauseGiveConcern applauseGC;

	private CommonAdapter<Ranking> adapter;
	// private RadioGroupLayout radio;
	private int pageIndex = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_min_xin_pai_hang_bang);
		init();
		sendReqConnect();
	}

	private void init() {
		// TODO Auto-generated method stub
		radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
		pullToRefreshListView1 = (PullToRefreshListView) findViewById(R.id.pullToRefreshListView1);
		pullToRefreshListView1.setOnRefreshListener(refreshListener);
		adapter = new CommonAdapter<Ranking>(this, R.layout.item_list_ranking,
				new ArrayList<Ranking>()) {

			@Override
			public void convert(ViewHolder helper, final Ranking item) {
				// helper.setText(R.id.The_picture, item.getThe_picture() + "");
				helper.setText(R.id.Star_names, item.getStar_names());

				ImageView imageView = helper.getView(R.id.Head_portrait);
				Glide.with(ToLevelActivity.this).load(item.getHead_portrait())
						.centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)
						.placeholder(R.drawable.home_image)
						.into(imageView);
				ImageView imageView2 = helper.getView(R.id.rankingImage);
				TextView textView = helper.getView(R.id.rankingText);
				imageView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(v.getContext(),
								DetailsActivity.class);
						intent.putExtra("userID", item.getStar_ID());
						startActivity(intent);
					}
				});

				switch (helper.getPosition()) {
				case 0:
					imageView2.setVisibility(View.VISIBLE);
					textView.setVisibility(View.GONE);
					imageView2.setImageResource(R.drawable.icon_1);
					break;
				case 1:
					imageView2.setVisibility(View.VISIBLE);
					textView.setVisibility(View.GONE);
					imageView2.setImageResource(R.drawable.icon_2);
					break;
				case 2:
					imageView2.setVisibility(View.VISIBLE);
					textView.setVisibility(View.GONE);
					imageView2.setImageResource(R.drawable.icon_3);
					break;

				default:
					imageView2.setVisibility(View.GONE);
					textView.setVisibility(View.VISIBLE);
					textView.setText((helper.getPosition() + 1) + "");
					break;
				}

				final ApplauseGiveConcern applauseGiveConcern = new ApplauseGiveConcern(
						mContext, item.getStar_ID(), ToLevelActivity.this,
						item.getThe_current_hooted_thumb_up_prices(),
						item.getStar_names());
				/**
				 * 鼓掌
				 */
				View applause = helper.getView(R.id.applause);
				if (applause != null) {
					applause.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							applauseGiveConcern.showApplaudDialog(1);
							// applauseGiveConcern.showAnimationDialog(
							// R.drawable.circle4, R.raw.applaud);
							applauseGC = applauseGiveConcern;
						}
					});
				}

				/**
				 * 踢红包
				 */
				View bigbird = helper.getView(R.id.bigbird);
				if (bigbird != null) {
					bigbird.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							applauseGiveConcern.showApplaudDialog(2);
							// applauseGiveConcern.showAnimationDialog(
							// R.drawable.circle5, R.raw.give_back);
							applauseGC = applauseGiveConcern;
						}
					});
				}

				/**
				 * 关注
				 */
				View FocusOn = helper.getView(R.id.FocusOn);
				if (FocusOn != null) {
					FocusOn.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							applauseGiveConcern.sendReqAndAttention();
							// applauseGiveConcern.showAnimationDialog(
							// R.drawable.circle6, R.raw.concern);
							applauseGC = applauseGiveConcern;
						}
					});
				}

			}

		};
		pullToRefreshListView1.setAdapter(adapter);
		pullToRefreshListView1.setOnItemClickListener(this);
		radioGroup1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub

				pageIndex = 1;
				switch (checkedId) {
				case R.id.radio0:
					type = 1;
					sendReqConnect();
					break;
				case R.id.radio1:
					type = 2;
					sendReqConnect();
					break;
				case R.id.radio2:
					type = 3;
					sendReqConnect();
					break;

				}
			}
		});
	}

	public void initPersonalInformation() {
		// TODO Auto-generated method stub
		if (ranking == null) {
			// ToastUtil.show(activity, "获取数据失败");
			return;
		}
		// if (arrayList != null && arrayList.size() > 0) {
		if (pageIndex == 1) {// 第一页时，先清空数据集
			adapter.clearAll();
		}
		pageIndex++;
		adapter.add(ranking);
		// findViewById(R.id.noContent).setVisibility(View.GONE);
		// } else if (pageIndex == 1) {
		// findViewById(R.id.noContent).setVisibility(View.VISIBLE);
		// }
	}

	OnRefreshListener2<ListView> refreshListener = new OnRefreshListener2<ListView>() {

		@Override
		public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
			// 下拉刷新
			String time = DateUtils.formatDateTime(ToLevelActivity.this,
					System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
							| DateUtils.FORMAT_SHOW_DATE
							| DateUtils.FORMAT_ABBREV_ALL);
			pullToRefreshListView1.getLoadingLayoutProxy().setRefreshingLabel(
					"正在刷新");
			pullToRefreshListView1.getLoadingLayoutProxy().setPullLabel("下拉刷新");
			pullToRefreshListView1.getLoadingLayoutProxy().setReleaseLabel(
					"释放开始刷新");
			refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(
					"最后更新时间:" + time);
			pageIndex = 1;
			// 调用数据
			// sendReq(pageIndex);
			sendReqConnect();

		}

		@Override
		public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
			// 上拉翻页
			String time = DateUtils.formatDateTime(ToLevelActivity.this,
					System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
							| DateUtils.FORMAT_SHOW_DATE
							| DateUtils.FORMAT_ABBREV_ALL);
			pullToRefreshListView1.getLoadingLayoutProxy().setRefreshingLabel(
					"正在加载");
			pullToRefreshListView1.getLoadingLayoutProxy().setPullLabel("上拉翻页");
			pullToRefreshListView1.getLoadingLayoutProxy().setReleaseLabel(
					"释放开始加载");
			refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(
					"最后加载时间:" + time);
			// 调用数据
			sendReqConnect();
		}
	};

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		ToastUtil.show(this, "准备删除");
	}

	/**
	 * 获取信息
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
		entity.put("type", "" + type);

		SimpleHttpTask httpTask = HttpUtil.packagingHttpTask(entity,
				InfoSource.in_comparison_to_listApply_to_be_a_platform_star_);
		showProgressDialog("获取信息...");
		WorkerManager.addTask(httpTask, this);
	}

	@Override
	public void onInfoReceived(int errcode, HashMap<String, Object> items) {
		// TODO Auto-generated method stub
		super.onInfoReceived(errcode, items);
		pullToRefreshListView1.onRefreshComplete();
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
				Intent intent = new Intent(ToLevelActivity.this,
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
		case InfoSource.in_comparison_to_listApply_to_be_a_platform_star_:
			BaseEntity<List<Ranking>> baseEntity4 = gson.fromJson(jsonString,
					new TypeToken<BaseEntity<List<Ranking>>>() {
					}.getType());
			ranking = baseEntity4.getData();
			if (ranking != null && ranking.size() > 0) {
				initPersonalInformation();
			} else {
				ToastUtil.show(this, "没有更多数据了");
			}
			break;
		case InfoSource.give_applause_booed:
			ToastUtil.show(this, "提交成功");
			if (applauseGC == null) {
				return;
			}
			switch (applauseGC.getType()) {
			case 1:
				applauseGC.showAnimationDialog(R.drawable.circle4,
						R.raw.applaud);
				break;
			case 2:
				applauseGC.showAnimationDialog(R.drawable.circle5,
						R.raw.give_back);
				break;
			default:
				applauseGC.showAnimationDialog(R.drawable.circle4,
						R.raw.applaud);
				break;
			}

			break;
		case InfoSource.and_attention:
			ToastUtil.show(this, "提交成功");
			if (applauseGC == null) {
				return;
			}
			applauseGC.showAnimationDialog(R.drawable.circle6, R.raw.concern);
			break;
		}
	}
}
