package com.BC.entertainmentgravitation.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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
import com.BC.entertainmentgravitation.json.MessageItem;
import com.BC.entertainmentgravitation.utl.HttpUtil;
import com.BC.entertainmentgravitation.utl.ToastUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MessageListFragment extends BaseFragment implements
		OnItemClickListener, OnClickListener {

	public interface OnSelectMessageItem {
		public void selectMessageItem(MessageItem messageItem);
	}

	private OnSelectMessageItem onSelectMessageItem;

	Activity activity;
	View contentView, editConnect;
	PullToRefreshListView pullToRefreshListView1;

	MessageItem connect;
	List<MessageItem> connects;
	private CommonAdapter<MessageItem> adapter;
	// private RadioGroupLayout radio;
	private int pageIndex = 1;

	public List<MessageItem> getConnects() {
		return connects;
	}

	public void setConnects(List<MessageItem> connects) {
		this.connects = connects;
	}

	public MessageItem getConnect() {
		return connect;
	}

	public void setConnect(MessageItem connect) {
		this.connect = connect;
	}

	// public CenterConnectUtl(Activity activity, View contentView) {
	// this.activity = activity;
	// this.contentView = contentView;
	// init();
	// }
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		activity = getActivity();
		contentView = inflater.inflate(R.layout.fragment_message_list, null);
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

		pullToRefreshListView1 = (PullToRefreshListView) contentView
				.findViewById(R.id.pullToRefreshListView1);
		pullToRefreshListView1.setOnRefreshListener(refreshListener);
		adapter = new CommonAdapter<MessageItem>(activity,
				R.layout.item_list_message, new ArrayList<MessageItem>()) {

			@Override
			public void convert(ViewHolder helper, final MessageItem item) {
				// helper.setText(R.id.The_picture, item.getThe_picture() + "");
				helper.setText(R.id.describe, item.getDescribe());
				helper.setText(R.id.star_nmae, item.getStar_nmae());
				// helper.setText(R.id.payment, item.getPayment() ? "是" : "否");
				ImageView imageView = helper.getView(R.id.Head_portrait);
				Glide.with(activity)
						.load(item.getHead_portrait() == null ? "" : item
								.getHead_portrait()).centerCrop()
						.diskCacheStrategy(DiskCacheStrategy.ALL)
						.placeholder(R.drawable.home_image)
						.into(imageView);

				imageView = helper.getView(R.id.If_there_is_a_picture);
				if (item.getIf_there_is_a_picture() == null
						|| item.getIf_there_is_a_picture().equals("")) {
					imageView.setVisibility(View.GONE);
				} else {
					imageView.setVisibility(View.VISIBLE);
				}
			}
		};
		pullToRefreshListView1.setAdapter(adapter);
		pullToRefreshListView1.setOnItemClickListener(this);

	}

	public void initPersonalInformation() {
		// TODO Auto-generated method stub
		if (connects == null) {
			// ToastUtil.show(activity, "获取数据失败");
			return;
		}
		// if (arrayList != null && arrayList.size() > 0) {
		if (pageIndex == 1) {// 第一页时，先清空数据集
			adapter.clearAll();
		}
		pageIndex++;
		adapter.add(connects);
		MessageItem item = connects.get(0);
		if (onSelectMessageItem != null) {
			onSelectMessageItem.selectMessageItem(item);
		}
		// findViewById(R.id.noContent).setVisibility(View.GONE);
		// } else if (pageIndex == 1) {
		// findViewById(R.id.noContent).setVisibility(View.VISIBLE);
		// }
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		showAlertDialog(R.layout.dialog_alert3, R.id.button3, R.id.button1,
				R.id.button2, this);
	}

	OnRefreshListener2<ListView> refreshListener = new OnRefreshListener2<ListView>() {

		@Override
		public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
			// 下拉刷新
			String time = DateUtils.formatDateTime(activity,
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
			String time = DateUtils.formatDateTime(activity,
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

	/**
	 * 获取外部链接信息
	 */
	private void sendReqConnect() {
		if (MainActivity.starInformation == null) {
			ToastUtil.show(activity, "无法获取信息");
			return;
		}
		HashMap<String, String> entity = new HashMap<String, String>();

		entity.put("Star_ID", MainActivity.starInformation.getStar_ID());

		SimpleHttpTask httpTask = HttpUtil.packagingHttpTask(entity,
				InfoSource.stars_release_information_list);
		showProgressDialog("获取外部链接信息...");
		WorkerManager.addTask(httpTask, this);
	}

	@Override
	public void onInfoReceived(int errcode, HashMap<String, Object> items) {
		// TODO Auto-generated method stub
		super.onInfoReceived(errcode, items);
		pullToRefreshListView1.onRefreshComplete();
	}

	@Override
	public void requestSuccessful(String jsonString, int taskType) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		switch (taskType) {
		case InfoSource.stars_release_information_list:
			BaseEntity<ArrayList<MessageItem>> baseEntity4 = gson.fromJson(
					jsonString,
					new TypeToken<BaseEntity<ArrayList<MessageItem>>>() {
					}.getType());
			ArrayList<MessageItem> connects = baseEntity4.getData();
			if (connects != null) {
				if (connects.size() > 0) {
					setConnects(connects);
					initPersonalInformation();
				} else {
					ToastUtil.show(activity, "没有消息");
				}

			} else {
				ToastUtil.show(activity, "获取数据失败");
			}
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		MessageItem item = adapter.getItem(position - 1);
		if (onSelectMessageItem != null) {
			onSelectMessageItem.selectMessageItem(item);
		}
	}

	public void setOnSelectMessageItem(MessageCommentFragment commentFragment) {
		// TODO Auto-generated method stub
		this.onSelectMessageItem = commentFragment;
	}
}
