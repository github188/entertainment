package com.BC.entertainmentgravitation.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
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
import com.BC.entertainmentgravitation.adapter.PictureAdapter;
import com.BC.entertainmentgravitation.fragment.MessageListFragment.OnSelectMessageItem;
import com.BC.entertainmentgravitation.json.CommentOnTheList;
import com.BC.entertainmentgravitation.json.MessageItem;
import com.BC.entertainmentgravitation.json.response.comment.Comment_on_the_list;
import com.BC.entertainmentgravitation.utl.HttpUtil;
import com.BC.entertainmentgravitation.utl.TimestampTool;
import com.BC.entertainmentgravitation.utl.ToastUtil;
import com.BC.entertainmentgravitation.utl.ValidateUtil;
import com.BC.entertainmentgravitation.view.dialog.PictureDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MessageCommentFragment extends BaseFragment implements
		OnSelectMessageItem, OnItemClickListener {
	View contentView, outWeb;
	EditText Comment_on_the_content;
	Button editButton;

	PullToRefreshListView pullToRefreshListView1;

	MessageItem messageItem;
	// Comment comment;
	List<Comment_on_the_list> Comment_on_the_list;
	private CommonAdapter<Comment_on_the_list> adapter;
	private int pageIndex = 1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		contentView = inflater.inflate(R.layout.fragment_message_comment, null);
		return contentView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		init();
		super.onViewCreated(view, savedInstanceState);
	}

	private void init() {
		// TODO Auto-generated method stub
		outWeb = contentView.findViewById(R.id.outWeb);
		editButton = (Button) contentView.findViewById(R.id.editButton);
		Comment_on_the_content = (EditText) contentView
				.findViewById(R.id.Comment_on_the_content);

		pullToRefreshListView1 = (PullToRefreshListView) contentView
				.findViewById(R.id.pullToRefreshListView1);
		pullToRefreshListView1.setOnRefreshListener(refreshListener);
		adapter = new CommonAdapter<Comment_on_the_list>(getActivity(),
				R.layout.item_list_message_comment,
				new ArrayList<Comment_on_the_list>()) {

			@Override
			public void convert(ViewHolder helper,
					final Comment_on_the_list item) {
				// helper.setText(R.id.The_picture, item.getThe_picture() + "");

				helper.setText(R.id.nickname, item.getNickname() + "：");
				helper.setText(R.id.Comment_on_the_content,
						item.getComment_on_the_content());
				helper.setText(R.id.Comment_on_time, item.getComment_on_time());

				ImageView imageView = helper.getView(R.id.Head_portrait);
				Glide.with(getActivity())
						.load(item.getEvaluation_of_the_pictures())
						.centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)
						.placeholder(R.drawable.home_image)
						.into(imageView);
			}
		};
		pullToRefreshListView1.setAdapter(adapter);
		pullToRefreshListView1.setOnItemClickListener(this);
		editButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (ValidateUtil.isEmpty(Comment_on_the_content, "评论")) {
					return;
				}
				sendReqComment(Comment_on_the_content.getText().toString());
			}
		});

	}

	public void initPersonalInformation() {
		// TODO Auto-generated method stub
		if (MainActivity.starInformation == null) {
			return;
		}
		if (Comment_on_the_list == null) {
			return;
		}
		if (pageIndex == 1) {// 第一页时，先清空数据集
			adapter.clearAll();
		}
		pageIndex++;
		adapter.add(Comment_on_the_list);

	}

	@Override
	public void onInfoReceived(int errcode, HashMap<String, Object> items) {
		// TODO Auto-generated method stub
		super.onInfoReceived(errcode, items);
		pullToRefreshListView1.onRefreshComplete();
	}

	private void sendReqMessage(int pageIndex) {
		// TODO Auto-generated method stub
		if (messageItem == null) {
			ToastUtil.show(getActivity(), "无法获取信息");
			pullToRefreshListView1.onRefreshComplete();
			return;
		}
		HashMap<String, String> entity = new HashMap<String, String>();

		entity.put("Message_ID", "" + messageItem.getMessage_ID());
		entity.put("The_page_number", "" + pageIndex);
		SimpleHttpTask httpTask = HttpUtil.packagingHttpTask(entity,
				InfoSource.star_release_information_details);
		showProgressDialog("获取信息...");
		WorkerManager.addTask(httpTask, this);
	}

	private void sendReqComment(String content) {
		// TODO Auto-generated method stub
		if (messageItem == null || MainActivity.user == null) {
			ToastUtil.show(getActivity(), "无法提交信息");
			return;
		}

		HashMap<String, String> entity = new HashMap<String, String>();

		entity.put("clientID", "" + MainActivity.user.getClientID());
		entity.put("Message_ID", "" + messageItem.getMessage_ID());
		entity.put("Comment_on_the_content", "" + content);
		entity.put("Comment_on_time", TimestampTool.getCurrentDateTime());

		SimpleHttpTask httpTask = HttpUtil.packagingHttpTask(entity,
				InfoSource.comment);
		showProgressDialog("获取信息...");
		WorkerManager.addTask(httpTask, this);
	}

	@Override
	public void requestSuccessful(String jsonString, int taskType) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		switch (taskType) {
		case InfoSource.star_release_information_details:
			BaseEntity<CommentOnTheList> baseEntity4 = gson.fromJson(
					jsonString, new TypeToken<BaseEntity<CommentOnTheList>>() {
					}.getType());
			Comment_on_the_list = baseEntity4.getData()
					.getComment_on_the_list();
			if (Comment_on_the_list != null) {
				initPersonalInformation();
			} else {
				ToastUtil.show(getActivity(), "获取数据失败");
			}
			break;
		case InfoSource.comment:
			pageIndex = 1;
			sendReqMessage(1);
			Comment_on_the_content.setText("");
			ToastUtil.show(getActivity(), "提交成功");
			break;
		}
	}

	@Override
	public void selectMessageItem(MessageItem messageItem) {
		// TODO Auto-generated method stub
		this.messageItem = messageItem;
		initTitle();
		pageIndex = 1;
		sendReqMessage(1);
	}

	private void initTitle() {
		// TODO Auto-generated method stub
		setText(R.id.describe, messageItem.getDescribe());

		ImageView imageView = (ImageView) contentView
				.findViewById(R.id.Head_portrait);
		Glide.with(getActivity()).load(messageItem.getHead_portrait())
				.centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)
				.placeholder(R.drawable.home_image).into(imageView);

		ImageView imageView2 = (ImageView) contentView
				.findViewById(R.id.The_picture);
		if ("".equals(messageItem.getIf_there_is_a_picture())) {
			imageView2.setVisibility(View.GONE);
		} else {
			imageView2.setVisibility(View.VISIBLE);
			Glide.with(getActivity())
					.load(messageItem.getIf_there_is_a_picture()).centerCrop()
					.diskCacheStrategy(DiskCacheStrategy.ALL)
					.placeholder(R.drawable.home_image).into(imageView2);
		}
		imageView2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if ("".equals(messageItem.getIf_there_is_a_picture())) {
					ToastUtil.show(getActivity(), "抱歉，该消息没有图片");
				} else {
					showImageDialog();
				}

			}
		});
	}

	private void showImageDialog() {
		// TODO Auto-generated method stub
		final PictureDialog.Builder builder = new PictureDialog.Builder(
				getActivity());
		builder.setNegativeButton(new android.content.DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		PictureDialog dialog = builder.create();
		ViewPager pager = (ViewPager) builder.findViewById(R.id.picture);
		ArrayList<String> images = new ArrayList<String>();
		images.add(messageItem.getIf_there_is_a_picture());
		pager.setAdapter(new PictureAdapter(images, getActivity()));
		dialog.show();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Comment_on_the_list comment_on_the_list = (Comment_on_the_list) parent
				.getAdapter().getItem(position);
		if (comment_on_the_list.getPermission().equals("2")) {
			Intent intent = new Intent(getActivity(), DetailsActivity.class);
			intent.putExtra("userID", comment_on_the_list.getUserID());
			startActivity(intent);
		} else {
			Intent intent = new Intent(getActivity(),
					Details4UserActivity.class);
			intent.putExtra("userID", comment_on_the_list.getUserID());
			startActivity(intent);
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
			pageIndex++;
			sendReqMessage(pageIndex);
		}
	};
}
