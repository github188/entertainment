package com.BC.entertainmentgravitation.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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
import com.BC.entertainmentgravitation.activity.DetailsActivity;
import com.BC.entertainmentgravitation.activity.GiftActivity2.GiftChange;
import com.BC.entertainmentgravitation.activity.MainActivity;
import com.BC.entertainmentgravitation.json.Equitylists;
import com.BC.entertainmentgravitation.utl.HttpUtil;
import com.BC.entertainmentgravitation.utl.ToastUtil;
import com.BC.entertainmentgravitation.view.BaseSelectItem;
import com.BC.entertainmentgravitation.view.dialog.GiftDialog;
import com.BC.entertainmentgravitation.view.dialog.WarningDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class RightsFragment extends BaseFragment {

	View contentView;
	public int pageIndex = 1;
	PullToRefreshListView pullToRefreshListView1;
	List<Equitylists> equitylists;
	private CommonAdapter<Equitylists> adapter;
	private String star_id;
	String cityString = "全部";
	BaseSelectItem region;
	GiftChange change;

	public void setChange(GiftChange change) {
		this.change = change;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		contentView = inflater.inflate(R.layout.fragment_rights, null);
		return contentView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		init();
		sendReqMessage(pageIndex);

		super.onViewCreated(view, savedInstanceState);
	}

	private void init() {
		// TODO Auto-generated method stub
		region = (BaseSelectItem) contentView.findViewById(R.id.region);
		// geoCode();
		pullToRefreshListView1 = (PullToRefreshListView) contentView
				.findViewById(R.id.pullToRefreshListView1);
		pullToRefreshListView1.setOnRefreshListener(refreshListener);
		adapter = new CommonAdapter<Equitylists>(getActivity(),
				R.layout.item_page_rights, new ArrayList<Equitylists>()) {

			@Override
			public void convert(ViewHolder helper, final Equitylists item) {
				helper.setText(R.id.total, item.getTotal() + "");
				helper.setText(R.id.surplus, item.getSurplus() + "");
				helper.setText(R.id.price, "1小时/" + item.getPrice() + "个红包");
				helper.setText(R.id.describe, item.getDescribe() + "");
				helper.setText(R.id.target, item.getTarget() + "");
				helper.setText(R.id.label, item.getLabel() + "");
				int price = item.getBid() - 1;
				int count = item.getPrice();
				int allPrice = 0;
				for(int i = 0;i<count;i++){
					allPrice += price;
					price--;
				}
				helper.setText(R.id.allprice,
						"价值" + allPrice + "个娱币");

				ImageView imageView = helper.getView(R.id.star_Head_portrait);
				Glide.with(getActivity()).load(item.getHead()).centerCrop()
						.diskCacheStrategy(DiskCacheStrategy.ALL)
						.placeholder(R.drawable.home_image).into(imageView);
				helper.setText(R.id.Stage_name, item.getStage_name() + "");
				helper.setText(R.id.gender, item.getGender() + "");
				helper.setText(R.id.age, item.getAge() + "");
				helper.setText(R.id.professional, item.getProfessional() + "");
				imageView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(getActivity(),
								DetailsActivity.class);
						intent.putExtra("userID", item.getStar_id());
						startActivity(intent);
					}
				});

				if (item.getStar_id().equals(MainActivity.user.getClientID())) {
					helper.getView(R.id.button1).setVisibility(View.GONE);
				} else {
					helper.getView(R.id.button1).setVisibility(View.VISIBLE);
					helper.getView(R.id.button1).setOnClickListener(
							new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									int i = Integer.valueOf(item.getSurplus());
									if (i > 0) {
										showLabelDialog(item.getCardID(),
												item.getPrice(),
												item.getStar_id());
										star_id = item.getStar_id();
									} else {
										ToastUtil.show(v.getContext(),
												"抱歉，权益卡已兑换完了");
									}

								}
							});
				}
			}
		};
		pullToRefreshListView1.setAdapter(adapter);
		// pullToRefreshListView1.setOnItemClickListener(this);
	}

	public void initPersonalInformation() {
		// TODO Auto-generated method stub
		if (equitylists == null) {
			return;
		}
		if (pageIndex == 1) {// 第一页时，先清空数据集
			adapter.clearAll();
		}
		pageIndex++;
		adapter.add(equitylists);

	}

	@Override
	public void onInfoReceived(int errcode, HashMap<String, Object> items) {
		// TODO Auto-generated method stub
		super.onInfoReceived(errcode, items);
		pullToRefreshListView1.onRefreshComplete();
	}

	public void sendReqMessage(int pageIndex) {
		// TODO Auto-generated method stub
		if (MainActivity.starInformation == null) {
			ToastUtil.show(getActivity(), "无法获取信息");
			return;
		}
		HashMap<String, String> entity = new HashMap<String, String>();

		entity.put("userID", "" + MainActivity.user.getClientID());
		// entity.put("star_id", "" +
		// MainActivity.starInformation.getStar_ID());
		entity.put("region", "" + region.getContent());
		entity.put("pageIndex", "" + pageIndex);
		SimpleHttpTask httpTask = HttpUtil.packagingHttpTask(entity,
				InfoSource.equitylists);
		showProgressDialog("获取信息...");
		WorkerManager.addTask(httpTask, this);
	}

	private void sendReqMessage2(int cardID, int price, String quantity,
			String star_id) {
		// TODO Auto-generated method stub
		if (MainActivity.user == null) {
			ToastUtil.show(getActivity(), "无法获取信息");
			return;
		}
		HashMap<String, String> entity = new HashMap<String, String>();

		entity.put("userID", "" + MainActivity.user.getClientID());
		entity.put("star_id", "" + star_id);
		entity.put("cardID", "" + cardID);
		entity.put("quantity", "" + quantity);
		entity.put("price", price + "");
		SimpleHttpTask httpTask = HttpUtil.packagingHttpTask(entity,
				InfoSource.subscribe);
		showProgressDialog("获取信息...");
		WorkerManager.addTask(httpTask, this);
	}

	private void showWarningDialog2(String title, String message) {
		// TODO Auto-generated method stub
		final WarningDialog.Builder builder = new WarningDialog.Builder(
				getActivity());
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
				Intent intent = new Intent(getActivity(), DetailsActivity.class);
				intent.putExtra("userID", star_id);
				startActivity(intent);
			}
		});
		WarningDialog dialog = builder.create();

		dialog.show();

	}

	@Override
	public void requestFailed(int errcode, String message) {
		// TODO Auto-generated method stub
		super.requestFailed(errcode, message);
		if (message.equals("您的红包不足！") || message.equals("您还没有该明星红包！")) {
			showWarningDialog2("鼓掌获得红包", "您的红包不足是否去鼓掌获得");
		}
	}

	@Override
	public void requestSuccessful(String jsonString, int taskType) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		switch (taskType) {
		case InfoSource.equitylists:
			BaseEntity<List<Equitylists>> baseEntity4 = gson.fromJson(
					jsonString, new TypeToken<BaseEntity<List<Equitylists>>>() {
					}.getType());
			equitylists = baseEntity4.getData();
			if (equitylists != null) {
				initPersonalInformation();
			} else {
				ToastUtil.show(getActivity(), "获取数据失败");
			}
			break;
		case InfoSource.subscribe:
			ToastUtil.show(getActivity(), "认购成功");
			pageIndex = 1;
			sendReqMessage(1);
			if (change != null) {
				change.change();
			}
			break;
		}
	}

	private void showLabelDialog(final int cardID, final int price,
			final String star_id) {
		// TODO Auto-generated method stub
		final GiftDialog.Builder builder = new GiftDialog.Builder(getActivity());
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
				EditText e = (EditText) builder.findViewById(R.id.message);
				if ((e != null && !"".equals(e.getText().toString()) && !"0"
						.equals(e.getText().toString()))) {
					sendReqMessage2(cardID, price, e.getText().toString(),
							star_id);
					dialog.dismiss();
				} else {
					ToastUtil.show(getActivity(), "最少购买一个");
				}
			}
		});
		GiftDialog dialog = builder.create();
		EditText editText = (EditText) builder.findViewById(R.id.message);
		final TextView textView2 = (TextView) builder
				.findViewById(R.id.textView2);
		textView2.setText(price + "");
		editText.setText("1");
		editText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				String ts = s.toString();
				if (!ts.equals("")) {
					int v = Integer.valueOf(ts);
					textView2.setText("" + (price * v));
				}
			}
		});
		dialog.show();

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

	/*private void geoCode() {
		if (BaiduMapUtil.getNowAddressComponent() != null) {
			AddressComponent addressComponent = BaiduMapUtil
					.getNowAddressComponent();
			cityString = addressComponent.province + "-"
					+ addressComponent.city;
			region.setContent(cityString);
		} else {
			showProgressDialog("正在获取位置");
			BaiduMapUtil.newInstance(getActivity())
					.setGetGeoCodeResultListener(
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
									cityString = addressComponent.province
											+ "-" + addressComponent.city;
									region.setContent(cityString);
								}

								@Override
								public void onGetGeoCodeResult(
										GeoCodeResult arg0) {
									// TODO Auto-generated method stub

								}
							});
			BaiduMapUtil.newInstance(getActivity()).shareAddr();
		}
	}*/
}
