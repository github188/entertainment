package com.BC.entertainmentgravitation.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import com.BC.entertainmentgravitation.json.Goods;
import com.BC.entertainmentgravitation.utl.HttpUtil;
import com.BC.entertainmentgravitation.utl.ToastUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GoodsListFragment extends BaseFragment implements
		OnItemClickListener {

	View contentView;
	PullToRefreshListView pullToRefreshListView1;

	List<Goods> messageItems;
	private CommonAdapter<Goods> adapter;
	private int pageIndex = 1;
	private Button edit, exit;
	private View content;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		contentView = inflater.inflate(R.layout.fragment_goods_list, null);
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
		edit = (Button) contentView.findViewById(R.id.editButton);
		exit = (Button) contentView.findViewById(R.id.exitEditButton);
		content = contentView.findViewById(R.id.content);
		pullToRefreshListView1 = (PullToRefreshListView) contentView
				.findViewById(R.id.pullToRefreshListView1);
		pullToRefreshListView1.setOnRefreshListener(refreshListener);
		adapter = new CommonAdapter<Goods>(getActivity(),
				R.layout.item_list_goods, new ArrayList<Goods>()) {

			@Override
			public void convert(ViewHolder helper, final Goods item) {

				helper.setText(R.id.describe, item.getDescribe() + "");
				helper.setText(R.id.The_number_of, item.getThe_number_of() + "");
				helper.setText(R.id.Release_people, item.getRelease_people()
						+ "");
				helper.setText(R.id.entertainment_coins,
						item.getEntertainment_coins() + "");

				// helper.setText(R.id.The_picture, item.getThe_picture() + "");

				// helper.setText(R.id.describe, item.getDescribe());
				// helper.getView(R.id.If_there_is_a_picture).setVisibility(
				// "".equals(item.getIf_there_is_a_picture()) ? View.GONE
				// : View.VISIBLE);
				//
				ImageView imageView = helper.getView(R.id.The_picture);
				Glide.with(getActivity()).load(item.getThe_picture())
						.centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)
						.placeholder(R.drawable.home_image)
						.into(imageView);

				helper.getView(R.id.button1).setOnClickListener(
						new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								content.setVisibility(View.VISIBLE);
								edit.setOnClickListener(new OnClickListener() {

									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										sendReqForGoods(item.getProduct_ID(),
												item.getPublisher_ID());
										content.setVisibility(View.GONE);
									}
								});
								exit.setOnClickListener(new OnClickListener() {

									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										content.setVisibility(View.GONE);
									}
								});
							}
						});
			}
		};
		pullToRefreshListView1.setAdapter(adapter);
		pullToRefreshListView1.setOnItemClickListener(this);

	}

	public void initPersonalInformation() {
		// TODO Auto-generated method stub
		if (messageItems == null) {
			return;
		}
		if (pageIndex == 1) {// 第一页时，先清空数据集
			adapter.clearAll();
		}
		pageIndex++;
		adapter.add(messageItems);

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

		entity.put("clientID", "" + MainActivity.starInformation.getStar_ID());
		entity.put("Goods_on_time", "2015-11-2");
		entity.put("type", "1");
		entity.put("Keywords", "");
		entity.put("The_page_number", "" + pageIndex);

		SimpleHttpTask httpTask = HttpUtil.packagingHttpTask(entity,
				InfoSource.goods_list);
		showProgressDialog("获取信息...");
		WorkerManager.addTask(httpTask, this);
	}

	private void sendReqForGoods(String Product_ID, String Publisher_ID) {
		// TODO Auto-generated method stub
		if (MainActivity.user == null) {
			ToastUtil.show(getActivity(), "无法获取信息");
			return;
		}
		HashMap<String, String> entity = new HashMap<String, String>();

		entity.put("clientID", "" + MainActivity.user.getClientID());
		entity.put("Shipping_address_ID",
				getTextViewContent(R.id.Shipping_address_ID));
		entity.put("Shipping_name", getTextViewContent(R.id.Shipping_name));
		entity.put("phone", getTextViewContent(R.id.phone));
		entity.put("Product_ID", Product_ID);
		entity.put("Publisher_ID", Publisher_ID);
		entity.put("quantity", "" + pageIndex);

		SimpleHttpTask httpTask = HttpUtil.packagingHttpTask(entity,
				InfoSource.for_goods);
		showProgressDialog("兑换中...");
		WorkerManager.addTask(httpTask, this);
	}

	@Override
	public void requestSuccessful(String jsonString, int taskType) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		switch (taskType) {
		case InfoSource.goods_list:
			BaseEntity<List<Goods>> baseEntity4 = gson.fromJson(jsonString,
					new TypeToken<BaseEntity<List<Goods>>>() {
					}.getType());
			messageItems = baseEntity4.getData();
			if (messageItems != null) {
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
