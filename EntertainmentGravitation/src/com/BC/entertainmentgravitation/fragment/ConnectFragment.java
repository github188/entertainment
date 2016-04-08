package com.BC.entertainmentgravitation.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.BC.androidtool.BaseDialogFragment;
import com.BC.androidtool.BrowserAcitvity;
import com.BC.androidtool.HttpThread.WorkerManager;
import com.BC.androidtool.JSON.BaseEntity;
import com.BC.androidtool.adapter.CommonAdapter;
import com.BC.androidtool.views.pull.PullToRefreshBase;
import com.BC.androidtool.views.pull.PullToRefreshBase.OnRefreshListener2;
import com.BC.androidtool.views.pull.PullToRefreshListView;
import com.BC.entertainmentgravitation.R;
import com.BC.entertainmentgravitation.HttpThread.InfoSource;
import com.BC.entertainmentgravitation.HttpThread.SimpleHttpTask;
import com.BC.entertainmentgravitation.activity.MainActivity;
import com.BC.entertainmentgravitation.json.response.outConnect.AllOutConnect;
import com.BC.entertainmentgravitation.json.response.outConnect.OutConnect;
import com.BC.entertainmentgravitation.utl.HttpUtil;
import com.BC.entertainmentgravitation.utl.ToastUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ConnectFragment extends BaseDialogFragment implements
		OnItemClickListener {
	Activity activity;
	View contentView;// editConnect
	Button addButton;// editButton
	PullToRefreshListView pullToRefreshListView1;
	PullToRefreshListView pullToRefreshListView2;

	OutConnect baidu, qq, sina;

	AllOutConnect allOutConnect;
	List<OutConnect> connects1;
	List<OutConnect> connects2;
	private CommonAdapter<OutConnect> adapter1;
	private CommonAdapter<OutConnect> adapter2;
	private int pageIndex = 1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		activity = getActivity();
		contentView = inflater.inflate(R.layout.item_center_connect, container,
				false);
		return contentView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		init();
		sendReqConnect();
		super.onViewCreated(view, savedInstanceState);
	}

	private void init() {
		// TODO Auto-generated method stub
		
		addButton = (Button) contentView.findViewById(R.id.addButton);
		pullToRefreshListView1 = (PullToRefreshListView) contentView
				.findViewById(R.id.pullToRefreshListView1);
		pullToRefreshListView2 = (PullToRefreshListView) contentView
				.findViewById(R.id.pullToRefreshListView2);
		adapter1 = initPullToRefreshListView(pullToRefreshListView1,
				new OnRefresh(pullToRefreshListView1));
		adapter2 = initPullToRefreshListView(pullToRefreshListView2,
				new OnRefresh(pullToRefreshListView2));

		pullToRefreshListView1
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						OutConnect connect = adapter1.getItem(position - 1);
						Intent intent = new Intent(activity,
								BrowserAcitvity.class);
						intent.putExtra("url", connect.getLink());
						startActivity(intent);
					}

				});
		pullToRefreshListView2
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						OutConnect connect = adapter2.getItem(position - 1);
						Intent intent = new Intent(activity,
								BrowserAcitvity.class);
						intent.putExtra("url", connect.getLink());
						startActivity(intent);

					}
				});
		addButton.setText("返回");
		addButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
			}
		});
		contentView.findViewById(R.id.baidu).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						String oldLink = "";
						if ((baidu != null && baidu.getLink() != null)) {
							oldLink = baidu.getLink();
							Intent intent = new Intent(activity,
									BrowserAcitvity.class);
							intent.putExtra("url", oldLink);
							startActivity(intent);
						} else {
							ToastUtil.show(activity, "没有相关连接");
						}

					}
				});
		contentView.findViewById(R.id.QQ).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						String oldLink = "";
						if ((qq != null && qq.getLink() != null)) {
							oldLink = qq.getLink();
							Intent intent = new Intent(activity,
									BrowserAcitvity.class);
							intent.putExtra("url", oldLink);
							startActivity(intent);
						} else {
							ToastUtil.show(activity, "没有相关连接");
						}
					}
				});
		contentView.findViewById(R.id.sina).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						String oldLink = "";
						if ((sina != null && sina.getLink() != null)) {
							oldLink = sina.getLink();
							Intent intent = new Intent(activity,
									BrowserAcitvity.class);
							intent.putExtra("url", oldLink);
							startActivity(intent);
						} else {
							ToastUtil.show(activity, "没有相关连接");
						}
					}
				});

	}

	private CommonAdapter<OutConnect> initPullToRefreshListView(
			PullToRefreshListView pullToRefreshListView,
			OnRefreshListener2<ListView> refreshListener) {
		// TODO Auto-generated method stub

		pullToRefreshListView.setOnRefreshListener(refreshListener);
		CommonAdapter<OutConnect> adapter = new CommonAdapter<OutConnect>(
				activity, R.layout.item_list_connect,
				new ArrayList<OutConnect>()) {

			@Override
			public void convert(ViewHolder helper, final OutConnect item) {
				helper.setText(R.id.The_title, item.getTitle());
				helper.setText(R.id.Connection_address, item.getLink());
				ImageView imageView = helper.getView(R.id.The_picture);
				Glide.with(activity)
						.load(item.getIcon() == null ? "" : item.getIcon())
						.centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)
						.placeholder(R.drawable.home_image)
						.into(imageView);
			}
		};
		pullToRefreshListView.setAdapter(adapter);
		return adapter;
	}

	public void initPersonalInformation() {
		// TODO Auto-generated method stub
		if (pageIndex == 1) {// 第一页时，先清空数据集
			adapter1.clearAll();
			adapter2.clearAll();
		}
		pageIndex++;
		adapter1.add(allOutConnect.getVideo());
		adapter2.add(allOutConnect.getOther());
		if (allOutConnect != null && allOutConnect.getBaidu().size() > 0) {
			baidu = allOutConnect.getBaidu().get(0);
		}
		if (allOutConnect != null && allOutConnect.getQQspace().size() > 0) {
			qq = allOutConnect.getQQspace().get(0);
		}
		if (allOutConnect != null && allOutConnect.getSina().size() > 0) {
			sina = allOutConnect.getSina().get(0);
		}

		// findViewById(R.id.noContent).setVisibility(View.GONE);
		// } else if (pageIndex == 1) {
		// findViewById(R.id.noContent).setVisibility(View.VISIBLE);
		// }
	}

	public void save() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		// ToastUtil.show(activity, "准备删除");
	}

	/**
	 * 获取外部链接信息
	 */
	private void sendReqConnect() {
		if (MainActivity.starInformation == null) {
			ToastUtil.show(activity, "无法获取信息");
			return;
		}
		HashMap<String, String> entity = new HashMap<String, String>();

		entity.put("starID", MainActivity.starInformation.getStar_ID());
		entity.put("pageIndex", pageIndex + "");

		SimpleHttpTask httpTask = HttpUtil.packagingHttpTask(entity,
				InfoSource.outconnect);
		showProgressDialog("获取外部链接信息...");
		WorkerManager.addTask(httpTask, this);
	}

	@Override
	public void onInfoReceived(int errcode, HashMap<String, Object> items) {
		// TODO Auto-generated method stub
		super.onInfoReceived(errcode, items);
		pullToRefreshListView1.onRefreshComplete();
		pullToRefreshListView2.onRefreshComplete();
	}

	@Override
	public void requestSuccessful(String jsonString, int taskType) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		switch (taskType) {
		case InfoSource.outconnect:
			BaseEntity<AllOutConnect> baseEntity4 = gson.fromJson(jsonString,
					new TypeToken<BaseEntity<AllOutConnect>>() {
					}.getType());
			allOutConnect = baseEntity4.getData();
			if (allOutConnect != null) {
				initPersonalInformation();
			} else {
				ToastUtil.show(activity, "获取数据失败");
			}
			break;
		}
	}

	class OnRefresh implements OnRefreshListener2<ListView> {
		private PullToRefreshListView pullToRefreshListView;

		public OnRefresh(PullToRefreshListView pullToRefreshListView) {
			super();
			this.pullToRefreshListView = pullToRefreshListView;
		}

		@Override
		public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
			// 下拉刷新
			String time = DateUtils.formatDateTime(activity,
					System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
							| DateUtils.FORMAT_SHOW_DATE
							| DateUtils.FORMAT_ABBREV_ALL);
			pullToRefreshListView.getLoadingLayoutProxy().setRefreshingLabel(
					"正在刷新");
			pullToRefreshListView.getLoadingLayoutProxy().setPullLabel("下拉刷新");
			pullToRefreshListView.getLoadingLayoutProxy().setReleaseLabel(
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
			String time = DateUtils.formatDateTime(activity,
					System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
							| DateUtils.FORMAT_SHOW_DATE
							| DateUtils.FORMAT_ABBREV_ALL);
			pullToRefreshListView.getLoadingLayoutProxy().setRefreshingLabel(
					"正在加载");
			pullToRefreshListView.getLoadingLayoutProxy().setPullLabel("上拉翻页");
			pullToRefreshListView.getLoadingLayoutProxy().setReleaseLabel(
					"释放开始加载");
			refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(
					"最后加载时间:" + time);
			// 调用数据
			sendReqConnect();
		}
	}
}
