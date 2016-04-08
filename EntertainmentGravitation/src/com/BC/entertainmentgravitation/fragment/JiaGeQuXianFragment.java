package com.BC.entertainmentgravitation.fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.BC.androidtool.BaseFragment;
import com.BC.androidtool.HttpThread.WorkerManager;
import com.BC.androidtool.JSON.BaseEntity;
import com.BC.entertainmentgravitation.R;
import com.BC.entertainmentgravitation.HttpThread.InfoSource;
import com.BC.entertainmentgravitation.HttpThread.SimpleHttpTask;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JiaGeQuXianFragment extends BaseFragment implements
		OnClickListener {
	View contentView, Applause, BigBird, FocusOn;
	ImageView star_Head_portrait;
	UpdataMainActivity updataMainActivity;
	int type = 1;
	ApplauseGiveConcern applauseGiveConcern;
	RadioGroup radioGroup2;
	// ImageView applauseAn, bigbirdAn, FocusOnAn;
	// private AnimationDrawable animationDrawable;
	KLink kLink;
	CoordinateSystemView coordinateSystemView;
	LineChart lineChart;
	int coordinateSystemWidth = 0;
	public StarInformation starInformation;
	public String starID;

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
		contentView = inflater.inflate(R.layout.fragment_jia_ge_qu_xian, null);
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

	public void showStarInformation(String starID) {
		// TODO Auto-generated method stub
		this.starID = starID;
		if (starInformation == null) {
			sendReqStarInformation();
		} else {
			initStarInformation();
		}
	}

	private void init() {
		// TODO Auto-generated method stub
		radioGroup2 = (RadioGroup) contentView.findViewById(R.id.radioGroup2);
		coordinateSystemView = (CoordinateSystemView) contentView
				.findViewById(R.id.coordinateSystemView);
		star_Head_portrait = (ImageView) contentView
				.findViewById(R.id.star_Head_portrait);
		Applause = contentView.findViewById(R.id.applause);
		BigBird = contentView.findViewById(R.id.bigbird);
		FocusOn = contentView.findViewById(R.id.FocusOn);
		// applauseAn = (ImageView) contentView.findViewById(R.id.applauseAn);
		// bigbirdAn = (ImageView) contentView.findViewById(R.id.bigbirdAn);
		// FocusOnAn = (ImageView) contentView.findViewById(R.id.FocusOnAn);

		Applause.setOnClickListener(this);
		BigBird.setOnClickListener(this);
		FocusOn.setOnClickListener(this);
		radioGroup2.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.radio0:
					type = 1;
					sendReqKLineGraph();
					break;
				case R.id.radio1:
					type = 2;
					sendReqKLineGraph();
					break;

				}
			}
		});
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
		// long showMax = Long.valueOf(kLink.getMax());
		// long min = Long.valueOf(kLink.getMin());
		// if (min != showMax) {
		// showMax = max - min;
		// } else {
		// min = 0;
		// }

		coordinateSystemView.setyShowMax(max);
		// coordinateSystemView.setyPartingName(new String[] {
		// (max / 7 + min) + "", (max / 6 + min) + "",
		// (max / 5 + min) + "", (max / 4 + min) + "",
		// (max / 3 + min) + "", (max / 2 + min) + "",
		// (max / 1 + min) + "" });
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
			if (coordinateSystemWidth > 0) {
				LayoutParams params = coordinateSystemView.getLayoutParams();
				params.width = coordinateSystemWidth * 2;
				coordinateSystemView.setLayoutParams(params);
			}
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
		case 2:
			LayoutParams params = coordinateSystemView.getLayoutParams();
			params.width = params.width / 2;
			coordinateSystemWidth = params.width;
			coordinateSystemView.setLayoutParams(params);

			coordinateSystemView.setxParting(7);
			String[] s2 = new String[7];
			Calendar c1 = Calendar.getInstance();
			for (int i = s2.length - 1; i >= 0; i--) {
				SimpleDateFormat format = new SimpleDateFormat("MM-dd");
				s2[i] = format.format(c1.getTime());
				c1.set(Calendar.DAY_OF_MONTH, c1.get(Calendar.DAY_OF_MONTH) - 1);
			}
			coordinateSystemView.setxPartingName(s2);
			lineChart.setNum(7);
			break;
		}
		for (int i = 0; i < price_movements.size(); i++) {
			// lineChart.addPoint(i,
			// Float.valueOf(price_movements.get(i).getPrice()) - min,
			// null);
			lineChart.addPoint(i,
					Float.valueOf(price_movements.get(i).getPrice()), null);
		}
		coordinateSystemView.setLineChart(lineChart);

	}

	public void initStarInformation() {
		// TODO Auto-generated method stub
		if (starInformation != null) {
			setText(R.id.Stage_name, starInformation.getStage_name());
			setText(R.id.star_information,
					starInformation.getThe_constellation() + " | "
							+ starInformation.getHeight() + "cm | "
							+ starInformation.getWeight() + "kg");
			setText(R.id.professional, starInformation.getProfessional());
			setText(R.id.prices,
					""
							+ starInformation
									.getThe_current_hooted_thumb_up_prices());
			Glide.with(this).load(starInformation.getHead_portrait())
					.centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)
					.placeholder(R.drawable.home_image)
					.into(star_Head_portrait);
			applauseGiveConcern = new ApplauseGiveConcern(getActivity(),
					starInformation.getStar_ID(), this,
					starInformation.getThe_current_hooted_thumb_up_prices(),
					starInformation.getStage_name());
			// initPrice_movements();

			type = 1;
			sendReqKLineGraph();
		} else {
			ToastUtil.show(getActivity(), "获取失败");
		}
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
			starInformation = baseEntity2.getData();
			initStarInformation();
			break;
		case InfoSource.k_line_graph:
			BaseEntity<KLink> baseEntity3 = gson.fromJson(jsonString,
					new TypeToken<BaseEntity<KLink>>() {
					}.getType());
			kLink = baseEntity3.getData();
			initPrice_movements();
			break;
		}
	}

	/**
	 * 获取明星信息
	 */
	private void sendReqStarInformation() {
		if (starID == null) {
			ToastUtil.show(getActivity(), "无法获取数据");
			return;
		}
		HashMap<String, String> entity = new HashMap<String, String>();
		entity.put("clientID", MainActivity.user.getClientID());
		entity.put("Star_ID", starID);

		SimpleHttpTask httpTask = HttpUtil.packagingHttpTask(entity,
				InfoSource.star_information);
		showProgressDialog("获取明星基本信息...");
		WorkerManager.addTask(httpTask, this);
	}

	/**
	 * 获取价格曲线
	 */
	private void sendReqKLineGraph() {
		if (starInformation == null) {
			ToastUtil.show(getActivity(), "无法获取数据");
			return;
		}
		HashMap<String, String> entity = new HashMap<String, String>();
		entity.put("star_id", starInformation.getStar_ID());
		entity.put("type", type + "");

		SimpleHttpTask httpTask = HttpUtil.packagingHttpTask(entity,
				InfoSource.k_line_graph);
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
