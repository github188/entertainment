package com.BC.entertainmentgravitation.fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
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
import com.BC.entertainmentgravitation.activity.MainActivity;
import com.BC.entertainmentgravitation.json.response.outConnect.AllOutConnect;
import com.BC.entertainmentgravitation.json.response.outConnect.OutConnect;
import com.BC.entertainmentgravitation.utl.HttpUtil;
import com.BC.entertainmentgravitation.utl.ToastUtil;
import com.BC.entertainmentgravitation.utl.ValidateUtil;
import com.BC.entertainmentgravitation.view.SelectWheel;
import com.BC.entertainmentgravitation.view.WheelInterfasc;
import com.BC.entertainmentgravitation.view.dialog.ConnectDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class CenterConnectUtl extends BaseFragment implements OnClickListener,
		OnItemClickListener {
	Activity activity;
	View contentView;// editConnect
	Button addButton;// editButton
	PullToRefreshListView pullToRefreshListView1;
	PullToRefreshListView pullToRefreshListView2;

	ImageView The_picture;
	private Bitmap Head_portraitbmp;

	OutConnect baidu, qq, sina;

	AllOutConnect allOutConnect;
	List<OutConnect> connects1;
	List<OutConnect> connects2;
	private CommonAdapter<OutConnect> adapter1;
	private CommonAdapter<OutConnect> adapter2;
	private int pageIndex = 1;
	private int type = 1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		activity = getActivity();
		contentView = inflater.inflate(R.layout.item_center_connect, null);
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
						type = 4;
						showConnectDialog(true, connect.getTitle(),
								connect.getLink(), true,
								connect.getConnectID(), connect.getIcon());

					}

				});
		pullToRefreshListView2
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						OutConnect connect = adapter2.getItem(position - 1);
						type = 5;
						showConnectDialog(true, connect.getTitle(),
								connect.getLink(), true,
								connect.getConnectID(), connect.getIcon());

					}
				});
		addButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				type = 4;
				showConnectDialog(true, "", "", false, "", "");
			}
		});
		contentView.findViewById(R.id.baidu).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						String oldLink = "";
						type = 1;
						if ((baidu != null && baidu.getLink() != null)) {
							oldLink = baidu.getLink();
							showConnectDialog(false, "个人百度贴吧地址", oldLink, true,
									baidu.getConnectID(), baidu.getIcon());
						} else {
							showConnectDialog(false, "个人百度贴吧地址", oldLink,
									false, "", "");
						}

					}
				});
		contentView.findViewById(R.id.QQ).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						String oldLink = "";
						type = 2;
						if ((qq != null && qq.getLink() != null)) {
							oldLink = qq.getLink();
							showConnectDialog(false, "个人QQ空间地址", oldLink, true,
									qq.getConnectID(), qq.getIcon());
						} else {
							showConnectDialog(false, "个人QQ空间地址", oldLink,
									false, "", "");
						}

					}
				});
		contentView.findViewById(R.id.sina).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						String oldLink = "";
						type = 3;
						if ((sina != null && sina.getLink() != null)) {
							oldLink = sina.getLink();
							showConnectDialog(false, "个人新浪微博地址", oldLink, true,
									sina.getConnectID(), sina.getIcon());
						} else {
							showConnectDialog(false, "个人新浪微博地址", oldLink,
									false, "", "");
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

	private void showConnectDialog(boolean showWhell, final String titleString,
			String oldLink, final boolean isUpdate, final String connectID,
			final String icon) {
		final ConnectDialog.Builder builder = new ConnectDialog.Builder(
				activity);

		builder.setNegativeButton(new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		final ConnectDialog connectDialog = builder.create();
		SelectWheel selectWheel = (SelectWheel) builder
				.findViewById(R.id.selectWheel);
		if (showWhell) {
			final String[] s = new String[] { "视屏连接", "其他连接" };
			selectWheel.setContentList(Arrays.asList(s));
			selectWheel.wheelInterfasc = new WheelInterfasc() {

				@Override
				public void selectValue(int selectID, String selectItem,
						boolean oneString) {
					// TODO Auto-generated method stub
					for (int i = 0; i < s.length; i++) {
						if (s[i].equals(selectItem)) {
							type = i + 4;
						}
					}
				}
			};

			if (type == 5) {
				selectWheel.setCurrentItem(1);
			}
		} else {
			selectWheel.setVisibility(View.GONE);
		}

		The_picture = (ImageView) builder.findViewById(R.id.The_picture);
		if (showWhell) {
			The_picture.setVisibility(View.VISIBLE);
			The_picture.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					showAlertDialog(R.layout.dialog_alert3, R.id.button3,
							R.id.button1, R.id.button2, CenterConnectUtl.this);
				}
			});
		} else {
			The_picture.setVisibility(View.GONE);
		}

		final EditText title = (EditText) builder.findViewById(R.id.title);
		final EditText link = (EditText) builder.findViewById(R.id.link);

		if (showWhell) {
			title.setVisibility(View.VISIBLE);
			title.setText(titleString);
			link.setText(oldLink);
		} else {
			title.setVisibility(View.GONE);
			TextView titleName = (TextView) builder
					.findViewById(R.id.titleName);
			titleName.setText(titleString);
			link.setText(oldLink);
		}
		builder.findViewById(R.id.editButton).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (ValidateUtil.isEmpty(link, "连接地址")) {
							return;
						}

						if (isUpdate) {
							sendReqUpdateConnect(connectID, title.getText()
									.toString(), link.getText().toString(),
									icon);
						} else {
							sendReqSaveConnect(MainActivity.user.getClientID(),
									title.getText().toString(), link.getText()
											.toString(), type + "", "");
						}

						connectDialog.dismiss();
					}
				});
		connectDialog.show();
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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		showAlertDialog(R.layout.dialog_alert3, R.id.button3, R.id.button1,
				R.id.button2, this);
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
		if (MainActivity.user == null) {
			ToastUtil.show(activity, "无法获取信息");
			return;
		}
		HashMap<String, String> entity = new HashMap<String, String>();

		entity.put("starID", MainActivity.user.getClientID());
		entity.put("pageIndex", pageIndex + "");

		SimpleHttpTask httpTask = HttpUtil.packagingHttpTask(entity,
				InfoSource.outconnect);
		showProgressDialog("获取外部链接信息...");
		WorkerManager.addTask(httpTask, this);
	}

	/**
	 * 保存外部链接信息
	 */
	private void sendReqSaveConnect(String starID, String title, String link,
			String type, String Icon) {
		if (MainActivity.user == null) {
			ToastUtil.show(activity, "无法获取信息");
			return;
		}
		HashMap<String, String> entity = new HashMap<String, String>();

		entity.put("starID", starID);
		entity.put("title", title);
		entity.put("link", link);
		entity.put("type", type);
		if (Head_portraitbmp != null) {
			entity.put("Icon",
					Head_portraitbmp == null ? "" : "data:image/jpg;base64,"
							+ HttpUtil.getBtye64String(Head_portraitbmp));
			Head_portraitbmp = null;
		} else {
			entity.put("Icon", "");
		}

		SimpleHttpTask httpTask = HttpUtil.packagingHttpTask(entity,
				InfoSource.addoutconnect);
		showProgressDialog("提交外部链接信息...");
		WorkerManager.addTask(httpTask, this);
	}

	/**
	 * 修改外部链接信息
	 */
	private void sendReqUpdateConnect(String connectID, String title,
			String link, String Icon) {
		if (MainActivity.user == null) {
			ToastUtil.show(activity, "无法获取信息");
			return;
		}
		HashMap<String, String> entity = new HashMap<String, String>();

		entity.put("connectID", connectID);
		entity.put("title", title);
		entity.put("link", link);
		entity.put("type", type + "");
		if (Head_portraitbmp != null) {
			entity.put("Icon",
					Head_portraitbmp == null ? "" : "data:image/jpg;base64,"
							+ HttpUtil.getBtye64String(Head_portraitbmp));
			Head_portraitbmp = null;
		} else {
			entity.put("Icon", Icon);
		}

		SimpleHttpTask httpTask = HttpUtil.packagingHttpTask(entity,
				InfoSource.updateoutconnect);
		showProgressDialog("提交外部链接信息...");
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
		case InfoSource.addoutconnect:
		case InfoSource.dltoutconnect:
		case InfoSource.updateoutconnect:
			ToastUtil.show(activity, "数据提交成功");
			break;
		}
	}

	@Override
	public void obtainImage(String imagePath) {
		// TODO Auto-generated method stub
		Glide.clear(The_picture);
		File fe = new File(Environment.getExternalStorageDirectory(), imagePath);
		Head_portraitbmp = BitmapFactory.decodeFile(fe.getPath());
		The_picture.setImageBitmap(Head_portraitbmp);
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
