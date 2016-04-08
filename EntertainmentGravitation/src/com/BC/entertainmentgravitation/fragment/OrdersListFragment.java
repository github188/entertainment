package com.BC.entertainmentgravitation.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.BC.androidtool.BaseFragment;
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
import com.BC.entertainmentgravitation.json.TheOrderList;
import com.BC.entertainmentgravitation.json.The_order_list;
import com.BC.entertainmentgravitation.utl.HttpUtil;
import com.BC.entertainmentgravitation.utl.ToastUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class OrdersListFragment extends BaseFragment implements
		OnItemClickListener {

	View contentView;
	PullToRefreshListView pullToRefreshListView1;

	List<The_order_list> The_order_list;
	private CommonAdapter<The_order_list> adapter;
	private int pageIndex = 1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		contentView = inflater.inflate(R.layout.fragment_order_list, null);
		return contentView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		init();
		sendReqMessage(1);
		super.onViewCreated(view, savedInstanceState);
	}

	private void init() {
		// TODO Auto-generated method stub
		pullToRefreshListView1 = (PullToRefreshListView) contentView
				.findViewById(R.id.pullToRefreshListView1);
		pullToRefreshListView1.setOnRefreshListener(refreshListener);
		adapter = new CommonAdapter<The_order_list>(getActivity(),
				R.layout.item_list_goods, new ArrayList<The_order_list>()) {

			@Override
			public void convert(ViewHolder helper, final The_order_list item) {
				// helper.setText(R.id.The_picture, item.getThe_picture() + "");
				if (MainActivity.user.getPermission().equals("2")) {
					helper.setText(R.id.button1, "编辑");
				} else {
					helper.setText(R.id.button1, "确认收货");
				}
				helper.setText(R.id.describe, item.getDescribe() + "");
				helper.setText(R.id.The_number_of, item.getThe_number_of() + "");
				helper.setText(R.id.Release_people, item.getShipping_name()
						+ "");
				helper.setText(R.id.entertainment_coins,
						item.getThe_order_number() + "");
				// helper.setText(R.id.describe, item.getDescribe());
				// helper.getView(R.id.If_there_is_a_picture).setVisibility(
				// "".equals(item.getIf_there_is_a_picture()) ? View.GONE
				// : View.VISIBLE);
				//

				helper.getView(R.id.imageView1).setVisibility(View.VISIBLE);
				ImageView imageView = helper.getView(R.id.The_picture);
				Glide.with(getActivity()).load(item.getThe_picture())
						.centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)
						.placeholder(R.drawable.home_image)
						.into(imageView);
			}
		};
		pullToRefreshListView1.setAdapter(adapter);
		pullToRefreshListView1.setOnItemClickListener(this);
	}

	public void initPersonalInformation() {
		// TODO Auto-generated method stub
		if (The_order_list == null) {
			return;
		}
		if (pageIndex == 1) {// 第一页时，先清空数据集
			adapter.clearAll();
		}
		pageIndex++;
		adapter.add(The_order_list);

	}

	@Override
	public void onInfoReceived(int errcode, HashMap<String, Object> items) {
		// TODO Auto-generated method stub
		super.onInfoReceived(errcode, items);
		pullToRefreshListView1.onRefreshComplete();
	}

	private void sendReqMessage(int pageIndex) {
		// TODO Auto-generated method stub
		if (MainActivity.user == null) {
			ToastUtil.show(getActivity(), "无法获取信息");
			return;
		}
		HashMap<String, String> entity = new HashMap<String, String>();

		entity.put("clientID", "" + MainActivity.user.getClientID());
		entity.put("The_page_number", "" + pageIndex);
		SimpleHttpTask httpTask = HttpUtil.packagingHttpTask(entity,
				InfoSource.list_of_orders);
		showProgressDialog("获取信息...");
		WorkerManager.addTask(httpTask, this);
	}

	@Override
	public void requestSuccessful(String jsonString, int taskType) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		switch (taskType) {
		case InfoSource.list_of_orders:
			BaseEntity<TheOrderList> baseEntity4 = gson.fromJson(jsonString,
					new TypeToken<BaseEntity<TheOrderList>>() {
					}.getType());
			The_order_list = baseEntity4.getData().getThe_order_lists();
			if (The_order_list != null) {
				initPersonalInformation();
			} else {
				ToastUtil.show(getActivity(), "获取数据失败");
			}
			break;
		}
	}

	OnRefreshListener2<ListView> refreshListener = new OnRefreshListener2<ListView>() {

		@Override
		public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
			// 下拉刷新
			String time = DateUtils.formatDateTime(getActivity(),
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
			sendReqMessage(pageIndex);

		}

		@Override
		public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
			// 上拉翻页
			String time = DateUtils.formatDateTime(getActivity(),
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
			sendReqMessage(pageIndex);
		}
	};

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		// MessageItem item = adapter.getItem(position - 1);
	}
}
