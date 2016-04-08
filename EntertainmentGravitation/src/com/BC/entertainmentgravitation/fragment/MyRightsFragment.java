package com.BC.entertainmentgravitation.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.BC.entertainmentgravitation.activity.Details4UserActivity;
import com.BC.entertainmentgravitation.activity.DetailsActivity;
import com.BC.entertainmentgravitation.activity.MainActivity;
import com.BC.entertainmentgravitation.activity.GiftActivity2.GiftChange;
import com.BC.entertainmentgravitation.json.MyRights;
import com.BC.entertainmentgravitation.utl.BaiduMapUtil;
import com.BC.entertainmentgravitation.utl.HttpUtil;
import com.BC.entertainmentgravitation.utl.ToastUtil;
import com.BC.entertainmentgravitation.view.BaseSelectItem;
import com.BC.entertainmentgravitation.view.dialog.ApplaudDialog;
import com.BC.entertainmentgravitation.view.dialog.WarningDialog;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult.AddressComponent;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yuntongxun.ecsdk.ECMessage;
import com.yuntongxun.ecsdk.im.ECTextMessageBody;
import com.yuntongxun.kitsdk.ECDeviceKit;
import com.yuntongxun.kitsdk.core.CCPAppManager;
import com.yuntongxun.kitsdk.ui.chatting.model.IMChattingHelper;

public class MyRightsFragment extends BaseFragment {

	View contentView;
	public int pageIndex = 1;
	PullToRefreshListView pullToRefreshListView1;
	List<MyRights> equitylists;
	private CommonAdapter<MyRights> adapter;
	String cityString = "全部";
	BaseSelectItem region;
	GiftChange change;

	public void setChange(GiftChange change) {
		this.change = change;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		contentView = inflater.inflate(R.layout.fragment_rights, null);
		return contentView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
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
		adapter = new CommonAdapter<MyRights>(getActivity(),
				R.layout.item_page_my_rights, new ArrayList<MyRights>()) {

			@Override
			public void convert(ViewHolder helper, final MyRights item) {
				helper.setText(R.id.quantity, item.getQuantity() + "个小时");
				int ap = item.getPrice() * item.getQuantity();
				helper.setText(R.id.price, ap + "个红包");
				int va = 0;
				int price = item.getBid() - 1;
				int count = ap;
				for(int i = 0;i < count;i++){
					va += price;
					price--;
				}
				helper.setText(R.id.valuation, va + "个娱币");
				int c = va - item.getArchive_price();
				if (c > 0) {
					helper.setText(R.id.change1, "涨 " + c);
					helper.setText(R.id.change2, "跌 0");
				} else {
					helper.setText(R.id.change1, "涨 0");
					helper.setText(R.id.change2, "跌 " + c);
				}
				helper.setText(R.id.describes, item.getDescribes() + "");
				helper.setText(R.id.target, item.getTarget() + "");
				helper.setText(R.id.label, item.getLabel() + "");

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
						if (item.getPermission().equals("2")) {
							Intent intent = new Intent(getActivity(),
									DetailsActivity.class);
							intent.putExtra("userID", item.getUserID());
							startActivity(intent);
						} else {
							Intent intent = new Intent(getActivity(),
									Details4UserActivity.class);
							intent.putExtra("userID", item.getUserID());
							startActivity(intent);
						}
					}
				});
				Button button1 = helper.getView(R.id.button1);
				Button button2 = helper.getView(R.id.button2);
				Button button3 = helper.getView(R.id.button3);

				switch (item.getState()) {
				case 1:// 未开始
					button1.setVisibility(View.VISIBLE);
					button2.setVisibility(View.VISIBLE);
					button3.setVisibility(View.GONE);
					button1.setText("发起兑换");
					button2.setText("放弃权益");
					button1.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							showWarningDialog(2, item.getOrder_sn(),
									item.getLabel() + "权益兑换",
									"您确认要向" + item.getStage_name() + "要求权益兑换？",
									item.getUserID());
						}
					});
					button2.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							showWarningDialog(5, item.getOrder_sn(), "放弃权益",
									"您确认要放弃" + item.getStage_name()
											+ "的权益？\n（放弃成功后将退还相应红包）",
									item.getUserID());
						}
					});
					break;
				case 2:// 已申请
					button1.setVisibility(View.VISIBLE);
					button2.setVisibility(View.GONE);
					button3.setVisibility(View.VISIBLE);
					button1.setText("在线沟通");
					button1.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							// ToastUtil.show(getActivity(), "开发调试中");
							// ECDeviceKit
							// .getIMKitManager()
							// .startConversationActivity(item.getUserID());
							CCPAppManager.startChattingAction(v.getContext(),
									item.getUserID(),
									item.getStage_name().equals("") ? "海绵娱用户"
											: item.getStage_name());
						}
					});
					button3.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							showWarningDialog(5, item.getOrder_sn(), "放弃权益",
									"您确认要放弃" + item.getStage_name()
											+ "的权益？\n（放弃成功后将退还相应红包）",
									item.getUserID());
						}
					});
					break;
				case 3:// 已开始
					button1.setVisibility(View.VISIBLE);
					button2.setVisibility(View.VISIBLE);
					button3.setVisibility(View.VISIBLE);
					button1.setText("确认完成");
					button2.setText("在线沟通");
					button1.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							showWarningDialog(4, item.getOrder_sn(), "确认完成",
									"您确认与" + item.getStage_name() + "的权益兑换完成？",
									item.getUserID());
						}
					});
					button3.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							showWarningDialog(5, item.getOrder_sn(), "放弃权益",
									"您确认要放弃" + item.getStage_name()
											+ "的权益？\n（放弃成功后将退还相应红包）",
									item.getUserID());
						}
					});
					button2.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							// ToastUtil.show(getActivity(), "开发调试中");
							// ECDeviceKit
							// .getIMKitManager()
							// .startConversationActivity(item.getUserID());
							CCPAppManager.startChattingAction(v.getContext(),
									item.getUserID(),
									item.getStage_name().equals("") ? "海绵娱用户"
											: item.getStage_name());
						}
					});
					break;
				case 4:// 交易结束
					button1.setVisibility(View.VISIBLE);
					button2.setVisibility(View.GONE);
					button3.setVisibility(View.GONE);
					button1.setText("兑换完成");
					button1.setOnClickListener(null);
					break;
				case 5:// 退回
					button1.setVisibility(View.VISIBLE);
					button2.setVisibility(View.GONE);
					button3.setVisibility(View.GONE);
					button1.setText("已放弃");
					button1.setOnClickListener(null);
					break;
				case 6:// 未结算
					button1.setVisibility(View.VISIBLE);
					button2.setVisibility(View.GONE);
					button3.setVisibility(View.GONE);
					button1.setText("兑换完成");
					button1.setOnClickListener(null);
					break;
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
		entity.put("star_id", "" + MainActivity.starInformation.getStar_ID());
		entity.put("region", "" + region.getContent());
		entity.put("pageIndex", "" + pageIndex);
		SimpleHttpTask httpTask = HttpUtil.packagingHttpTask(entity,
				InfoSource.my_rights);
		showProgressDialog("获取信息...");
		WorkerManager.addTask(httpTask, this);
	}

	private void sendReqMessage2(int state, String order_sn) {
		// TODO Auto-generated method stub
		if (MainActivity.user == null && MainActivity.starInformation != null) {
			ToastUtil.show(getActivity(), "无法获取信息");
			return;
		}
		HashMap<String, String> entity = new HashMap<String, String>();

		entity.put("userID", "" + MainActivity.user.getClientID());
		entity.put("order_sn", "" + order_sn);
		entity.put("state", state + "");
		SimpleHttpTask httpTask = HttpUtil.packagingHttpTask(entity,
				InfoSource.changeState);
		showProgressDialog("获取信息...");
		WorkerManager.addTask(httpTask, this);
	}

	@Override
	public void requestSuccessful(String jsonString, int taskType) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		switch (taskType) {
		case InfoSource.my_rights:
			BaseEntity<List<MyRights>> baseEntity4 = gson.fromJson(jsonString,
					new TypeToken<BaseEntity<List<MyRights>>>() {
					}.getType());
			equitylists = baseEntity4.getData();
			if (equitylists != null) {
				initPersonalInformation();
			} else {
				ToastUtil.show(getActivity(), "获取数据失败");
			}
			break;
		case InfoSource.changeState:
			ToastUtil.show(getActivity(), "成功");
			pageIndex = 1;
			sendReqMessage(1);
			if (change != null) {
				change.change();
			}
			break;
		}
	}

	private void showWarningDialog(final int state, final String order_sn,
			final String title, String message, final String starID) {
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
				if (state == 2) {
					handleSendTextMessage(
							"海绵娱用户" + MainActivity.user.getNickName()
									+ "请求与您发起" + title + "" + order_sn, starID,
							MainActivity.user.getClientID());
				}
				sendReqMessage2(state, order_sn);
				dialog.dismiss();
			}
		});
		WarningDialog dialog = builder.create();

		dialog.show();

	}

	/**
	 * 处理文本发送方法事件通知
	 * 
	 * @param text
	 */
	private void handleSendTextMessage(CharSequence text, String mRecipients,
			String userID) {
		if (text == null) {
			return;
		}
		// 组建一个待发送的ECMessage
		ECMessage msg = ECMessage.createECMessage(ECMessage.Type.TXT);
		// 设置消息的属性：发出者，接受者，发送时间等
		msg.setForm(userID);
		msg.setMsgTime(System.currentTimeMillis());
		// 设置消息接收者
		msg.setTo(mRecipients);
		msg.setSessionId(mRecipients);
		// 设置消息发送类型（发送或者接收）
		msg.setDirection(ECMessage.Direction.SEND);
		// 创建一个文本消息体，并添加到消息对象中
		ECTextMessageBody msgBody = new ECTextMessageBody(text.toString());
		msg.setBody(msgBody);
		try {
			// 发送消息，该函数见上
			long rowId = IMChattingHelper.sendECMessage(msg);
			// 通知列表刷新
			msg.setId(rowId);
		} catch (Exception e) {
			e.printStackTrace();
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

	/*private void geoCode() {
		if (BaiduMapUtil.getNowAddressComponent() != null) {
			AddressComponent addressComponent = BaiduMapUtil
					.getNowAddressComponent();
			cityString = addressComponent.province + "-"
					+ addressComponent.city;
			// cityString = "北京市-北京市";
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
