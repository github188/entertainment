package com.BC.entertainmentgravitation.fragment;

import java.util.HashMap;
import java.util.List;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.BC.androidtool.BaseFragment;
import com.BC.androidtool.HttpThread.WorkerManager;
import com.BC.androidtool.JSON.BaseEntity;
import com.BC.entertainmentgravitation.R;
import com.BC.entertainmentgravitation.HttpThread.InfoSource;
import com.BC.entertainmentgravitation.HttpThread.SimpleHttpTask;
import com.BC.entertainmentgravitation.activity.JiaGeQuXianActivity;
import com.BC.entertainmentgravitation.activity.MainActivity;
import com.BC.entertainmentgravitation.activity.TopUpActivity;
import com.BC.entertainmentgravitation.json.response.kLink.KLink;
import com.BC.entertainmentgravitation.json.response.kLink.Point;
import com.BC.entertainmentgravitation.json.response.starInformation.StarInformation;
import com.BC.entertainmentgravitation.utl.ApplauseGiveConcern;
import com.BC.entertainmentgravitation.utl.HttpUtil;
import com.BC.entertainmentgravitation.utl.ToastUtil;
import com.BC.entertainmentgravitation.utl.UpdataMainActivity;
import com.BC.entertainmentgravitation.view.CoordinateSystemView;
import com.BC.entertainmentgravitation.view.dialog.WarningDialog;
import com.BC.entertainmentgravitation.view.graphs.LineChart;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JiaGeQuXianFragment2 extends BaseFragment implements
		OnClickListener {
	View contentView, Applause, BigBird, FocusOn;
	// 涨跌点数
	private TextView tv_Difference;
	// ImageView star_Head_portrait;
	UpdataMainActivity updataMainActivity;
	int type = 1;
	ApplauseGiveConcern applauseGiveConcern;
	KLink kLink;
	// ImageView applauseAn, bigbirdAn, FocusOnAn;
	// private AnimationDrawable animationDrawable;

	CoordinateSystemView coordinateSystemView;
	LineChart lineChart;

	public UpdataMainActivity getUpdataMainActivity() {
		return updataMainActivity;
	}

	public void setUpdataMainActivity(UpdataMainActivity updataMainActivity) {
		this.updataMainActivity = updataMainActivity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		contentView = inflater.inflate(R.layout.fragment_jia_ge_qu_xian2, null);
		lineChart = new LineChart();

		return contentView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		init();
		// sendReqMessage(1);
		super.onViewCreated(view, savedInstanceState);
	}

	public void showStarInformation() {
		// TODO Auto-generated method stub
		if (MainActivity.starInformation == null) {
			sendReqStarInformation();
		} else {
			initStarInformation();
		}
	}

	private void init() {
		// TODO Auto-generated method stub
		coordinateSystemView = (CoordinateSystemView) contentView
				.findViewById(R.id.coordinateSystemView);
		// star_Head_portrait = (ImageView) contentView
		// .findViewById(R.id.star_Head_portrait);
		Applause = contentView.findViewById(R.id.applause);
		BigBird = contentView.findViewById(R.id.bigbird);
		FocusOn = contentView.findViewById(R.id.FocusOn);
		// applauseAn = (ImageView) contentView.findViewById(R.id.applauseAn);
		// bigbirdAn = (ImageView) contentView.findViewById(R.id.bigbirdAn);
		// FocusOnAn = (ImageView) contentView.findViewById(R.id.FocusOnAn);
		tv_Difference = (TextView) getView().findViewById(R.id.Different_name);

		Applause.setOnClickListener(this);
		BigBird.setOnClickListener(this);
		FocusOn.setOnClickListener(this);
	}

	public void initPrice_movements() {
		// TODO Auto-generated method stub
		if (kLink == null || kLink.getPoint() == null
				|| kLink.getPoint().size() == 0) {
			ToastUtil.show(getActivity(), "暂无数据");
			return;
		}
		List<Point> price_movements = kLink.getPoint();
		int l = kLink.getMax().length();
		String o = "21";
		for (int i = 1; i < l - 1; i++) {
			o += "0";
		}
		long over = Long.valueOf(o);
		long max = Long.valueOf(kLink.getMax());
		max = over + max;
		max = max / over;
		max = max * over;
		coordinateSystemView.setyShowMax(max);
		// coordinateSystemView.setyPartingName(new String[] { max / 7 + "",
		// max / 6 + "", max / 5 + "", max / 4 + "", max / 3 + "",
		// max / 2 + "", max / 1 + "" });
		long yLin1 = (max / 7);
		long yLin2 = yLin1 * 2;
		long yLin3 = yLin1 * 3;
		long yLin4 = yLin1 * 4;
		long yLin5 = yLin1 * 5;
		long yLin6 = yLin1 * 6;
		long yLin7 = yLin1 * 7;
		coordinateSystemView.setyPartingName(new String[] { yLin1 + "",
				yLin2 + "", yLin3 + "", yLin4 + "", yLin5 + "", yLin6 + "",
				yLin7 + "" });
		lineChart.getCoordinates().clear();
		switch (type) {
		case 1:
			coordinateSystemView.setxParting(48);
			String[] s = new String[48];
			for (int i = 0; i < s.length; i++) {
				if (i % 2 == 0) {
					s[i] = i / 2 + ":00";
				} else {
					s[i] = i / 2 + ":30";
				}
			}
			coordinateSystemView.setxPartingName(s);
			lineChart.setNum(48);
			break;
		}
		for (int i = 0; i < price_movements.size(); i++) {
			lineChart.addPoint(i,
					Float.valueOf(price_movements.get(i).getPrice()), null);
		}
		coordinateSystemView.setLineChart(lineChart);

	}

	public void initStarInformation() {
		// TODO Auto-generated method stub
		if (MainActivity.starInformation != null) {
			setText(R.id.Stage_name,
					MainActivity.starInformation.getStage_name());

			// setText(R.id.star_information,
			// MainActivity.starInformation.getThe_constellation() + " | "
			// + MainActivity.starInformation.getHeight()
			// + "cm | "
			// + MainActivity.starInformation.getWeight() + "kg");
			setText(R.id.professional,
					MainActivity.starInformation.getProfessional());
			setText(R.id.prices,
					"当前指数："
							+ MainActivity.starInformation
									.getThe_current_hooted_thumb_up_prices()
							+ "\n点击查看大图");
			contentView.findViewById(R.id.prices).setOnClickListener(
					new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Intent intent = new Intent(v.getContext(),
									JiaGeQuXianActivity.class);
							JiaGeQuXianActivity.starID = MainActivity.starInformation
									.getStar_ID();
							startActivity(intent);
						}
					});
			// Glide.with(this)
			// .load(MainActivity.starInformation.getHead_portrait())
			// .centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)
			// .placeholder(R.drawable.home_image)
			// .into(star_Head_portrait);
			applauseGiveConcern = new ApplauseGiveConcern(getActivity(),
					MainActivity.starInformation.getStar_ID(), this,
					MainActivity.starInformation
							.getThe_current_hooted_thumb_up_prices(),
					MainActivity.starInformation.getStage_name());
			// initPrice_movements();
			type = 1;
			sendReqKLineGraph();

		} else {
			ToastUtil.show(getActivity(), "获取失败");
		}
	}

	/**
	 * 获取价格曲线
	 */
	public void sendReqKLineGraph() {
		if (MainActivity.starInformation == null) {
			ToastUtil.show(getActivity(), "无法获取数据");
			return;
		}
		HashMap<String, String> entity = new HashMap<String, String>();
		entity.put("star_id", MainActivity.starInformation.getStar_ID());
		entity.put("type", type + "");

		SimpleHttpTask httpTask = HttpUtil.packagingHttpTask(entity,
				InfoSource.k_line_graph);
		showProgressDialog("获取折线图...");
		WorkerManager.addTask(httpTask, this);
	}

	private void showWarningDialog(String title, String message) {
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
				Intent intent = new Intent(getActivity(), TopUpActivity.class);
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
		if (message.equals("娱币不足！")) {
			showWarningDialog("是否购买娱币", "您的娱币不足是否去购买");
		}
	}

	@Override
	public void requestSuccessful(String jsonString, int taskType) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		switch (taskType) {
		case InfoSource.give_applause_booed:
			ToastUtil.show(getActivity(), "提交成功");
			if (updataMainActivity != null) {
				updataMainActivity.updataMainActivity();
			}
			sendReqStarInformation();
			switch (applauseGiveConcern.getType()) {
			case 1:
				applauseGiveConcern.showAnimationDialog(R.drawable.circle4,
						R.raw.applaud);
				break;
			case 2:
				applauseGiveConcern.showAnimationDialog(R.drawable.circle5,
						R.raw.give_back);
				break;
			default:
				applauseGiveConcern.showAnimationDialog(R.drawable.circle4,
						R.raw.applaud);
				break;
			}

			break;
		case InfoSource.and_attention:
			ToastUtil.show(getActivity(), "提交成功");
			applauseGiveConcern.showAnimationDialog(R.drawable.circle6,
					R.raw.concern);
			if (updataMainActivity != null) {
				updataMainActivity.updataMainActivity();
			}
			sendReqStarInformation();
			break;
		case InfoSource.star_information:

			BaseEntity<StarInformation> baseEntity2 = gson.fromJson(jsonString,
					new TypeToken<BaseEntity<StarInformation>>() {
					}.getType());
			MainActivity.starInformation = baseEntity2.getData();
			initStarInformation();
			break;
		case InfoSource.k_line_graph:
			BaseEntity<KLink> baseEntity3 = gson.fromJson(jsonString,
					new TypeToken<BaseEntity<KLink>>() {
					}.getType());
			kLink = baseEntity3.getData();
			int diff = Integer.parseInt(kLink.getDifference());
			tv_Difference.setText("昨日涨跌"+diff+"点");
			SpannableStringBuilder builder = new 
					SpannableStringBuilder(tv_Difference.getText().toString());
			int difflength = tv_Difference.getText().length()-1;
			ForegroundColorSpan colorSpan = null;
			if (diff == 0) {
				colorSpan = new ForegroundColorSpan(getResources().getColor(R.color.gray));
			}else if (diff > 0) {
				colorSpan = new ForegroundColorSpan(getResources().getColor(R.color.red2));
			}else if (diff < 0) {
				colorSpan = new ForegroundColorSpan(getResources().getColor(R.color.green));
			}
			builder.setSpan(colorSpan, 4, difflength, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			tv_Difference.setText(builder);
			initPrice_movements();
			
			break;
		}
	}

	/**
	 * 获取明星信息
	 */
	public void sendReqStarInformation() {
		if (MainActivity.starInformation == null) {
			ToastUtil.show(getActivity(), "无法获取数据");
			return;
		}
		HashMap<String, String> entity = new HashMap<String, String>();

		entity.put("clientID", MainActivity.user.getClientID());
		entity.put("Star_ID", MainActivity.starInformation.getStar_ID());

		SimpleHttpTask httpTask = HttpUtil.packagingHttpTask(entity,
				InfoSource.star_information);
		showProgressDialog("获取明星基本信息...");
		WorkerManager.addTask(httpTask, this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (applauseGiveConcern == null) {
			return;
		}
		switch (v.getId()) {
		/**
		 * 鼓掌
		 */
		case R.id.applause:
			// sendReqGiveApplauseBooed(1);

			applauseGiveConcern.showApplaudDialog(1);
			// applauseAn.setVisibility(View.VISIBLE);
			// animationDrawable = (AnimationDrawable) applauseAn.getDrawable();
			// // animationDrawable.start();
			// animationDrawable(animationDrawable);
			// animationDrawable.set
			// applauseGiveConcern.showAnimationDialog(R.drawable.circle4,
			// R.raw.applaud);
			break;
		/**
		 * 倒彩
		 */
		case R.id.bigbird:
			// sendReqGiveApplauseBooed(2);
			applauseGiveConcern.showApplaudDialog(2);
			// bigbirdAn.setVisibility(View.VISIBLE);
			// animationDrawable = (AnimationDrawable) bigbirdAn.getDrawable();
			// // animationDrawable.start();
			// animationDrawable(animationDrawable);
			// applauseGiveConcern.showAnimationDialog(R.drawable.circle5,
			// R.raw.give_back);
			break;
		/**
		 * 关注
		 */
		case R.id.FocusOn:
			applauseGiveConcern.sendReqAndAttention();
			// FocusOnAn.setVisibility(View.VISIBLE);
			// animationDrawable = (AnimationDrawable) FocusOnAn.getDrawable();
			// // animationDrawable.start();
			// animationDrawable(animationDrawable);
			// applauseGiveConcern.showAnimationDialog(R.drawable.circle6,
			// R.raw.concern);
			break;
		}
	}
}
