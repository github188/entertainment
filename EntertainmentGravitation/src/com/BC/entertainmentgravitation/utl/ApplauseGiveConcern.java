package com.BC.entertainmentgravitation.utl;

import java.util.HashMap;

import android.content.Context;
import android.content.DialogInterface;
import android.sax.StartElementListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.BC.androidtool.HttpThread.WorkerManager;
import com.BC.androidtool.HttpThread.InfoHandler.InfoReceiver;
import com.BC.entertainmentgravitation.R;
import com.BC.entertainmentgravitation.HttpThread.InfoSource;
import com.BC.entertainmentgravitation.HttpThread.SimpleHttpTask;
import com.BC.entertainmentgravitation.activity.MainActivity;
import com.BC.entertainmentgravitation.json.response.starInformation.StarInformation;
import com.BC.entertainmentgravitation.view.SelectWheel4;
import com.BC.entertainmentgravitation.view.dialog.AnimationDialog;
import com.BC.entertainmentgravitation.view.dialog.ApplaudDialog;
import com.BC.entertainmentgravitation.view.dialog.AnimationDialog.Builder.AnimationOver;

public class ApplauseGiveConcern {
	Context context;
	String Star_ID;
	InfoReceiver infoReceiver;
	int type = 1;
	int price = 0;
	String name = "";

	// StarInformation starInformation;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public ApplauseGiveConcern(Context context, String star_ID,
			InfoReceiver infoReceiver, int price, String name) {
		super();
		this.context = context;
		Star_ID = star_ID;
		this.infoReceiver = infoReceiver;
		// this.starInformation = starInformation;
		this.price = price;
		this.name = name;
	}

	public void showApplaudDialog(final int type) {
		// TODO Auto-generated method stub
		final ApplaudDialog.Builder builder = new ApplaudDialog.Builder(context);
		if (type == 1) {
			builder.setTitle("请输入鼓掌次数");
		} else {
			builder.setTitle("请输入踢红包个数");
		}
		builder.setMessage("请输入次数");

		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

				EditText message = (EditText) builder
						.findViewById(R.id.message);
				if (message != null && !message.getText().toString().equals("")
						&& !message.getText().toString().equals("0")) {
					sendReqGiveApplauseBooed(type, message.getText().toString());
					dialog.dismiss();
				} else {
					ToastUtil.show(context, "抱歉至少送一个掌声");
				}
			}
		});

		builder.setNegativeButton("取消",
				new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

		ApplaudDialog animationDialog = builder.create();
		EditText editText = (EditText) builder.findViewById(R.id.message);
		final TextView textView1 = (TextView) builder
				.findViewById(R.id.textView1);
		final TextView textView2 = (TextView) builder
				.findViewById(R.id.textView2);
		final TextView textView3 = (TextView) builder
				.findViewById(R.id.TextView03);
		final TextView textView4 = (TextView) builder
				.findViewById(R.id.TextView04);
		if (type == 1) {
			editText.setText("6");
			textView1.setText("需要花费娱币数量：");
			if (name != null) {
				textView2.setText("" + (price * 6 + 15));
				textView4.setText("能兑换" + name + "的红包数量：");
				textView3.setText(6 + "");
				textView4.setVisibility(View.GONE);
				textView3.setVisibility(View.GONE);
			}
			editText.addTextChangedListener(new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
					// TODO Auto-generated method stub

				}

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
					// TODO Auto-generated method stub

				}

				@Override
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub
					String ts = s.toString();
					if (!ts.equals("")) {
						int v = Integer.valueOf(ts);
						textView2.setText("" + (price * v + v * (v - 1) / 2));
						textView4.setText("能兑换" + name + "的红包数量：");
						textView3.setText(v + "");
					}
				}
			});
		} else {
			editText.setText("1");
			textView1.setText("当前选择红包数可兑换回娱币数量：");
			// if (name != null) {
			textView2.setText("" + (price - 1));
			// textView4.setText("拥有" + name
			// + "的红包数量：");
			// textView3.setText(starInformation.getBonus());
			textView4.setVisibility(View.GONE);
			textView3.setVisibility(View.GONE);
			SelectWheel4 selectWheel4 = (SelectWheel4) builder
					.findViewById(R.id.selectWheel);
			selectWheel4.setSelectItem4(1);
			// }
			editText.addTextChangedListener(new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
					// TODO Auto-generated method stub

				}

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
					// TODO Auto-generated method stub

				}

				@Override
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub
					String ts = s.toString();
					if (!ts.equals("")) {
						int v = Integer.valueOf(ts);
						// textView2.setText("" + 100 * v);
						textView2.setText("" + count(price, v));
					}
				}

				private int count(int x, int y) {
					// TODO Auto-generated method stub
					return x * y - y * (y + 1) / 2;
				}
			});
		}
		animationDialog.show();
	}

	public void showAnimationDialog(int id, int audio) {
		// TODO Auto-generated method stub
		final AnimationDialog.Builder builder = new AnimationDialog.Builder(
				context);

		builder.setAnimationDrawable(context, id);
		builder.setAudioFile(audio);
		final AnimationDialog animationDialog = builder.create();
		builder.setAnimationOver(new AnimationOver() {

			@Override
			public void AnimationOver() {
				// TODO Auto-generated method stub
				animationDialog.dismiss();
			}
		});
		animationDialog.show();
		builder.startAnimation();
	}

	/**
	 * 加关注
	 */
	public void sendReqAndAttention() {
		if (MainActivity.user == null || Star_ID == null) {
			ToastUtil.show(context, "抱歉，提交失败");
			return;
		}
		HashMap<String, String> entity = new HashMap<String, String>();

		entity.put("clientID", MainActivity.user.getClientID());
		entity.put("Star_ID", Star_ID);

		SimpleHttpTask httpTask = HttpUtil.packagingHttpTask(entity,
				InfoSource.and_attention);

		WorkerManager.addTask(httpTask, infoReceiver);
	}

	/**
	 * 鼓掌、喝倒彩
	 */
	public void sendReqGiveApplauseBooed(int type, String number) {
		if (MainActivity.user == null) {
			ToastUtil.show(context, "抱歉，提交失败");
			return;
		}
		this.type = type;
		HashMap<String, String> entity = new HashMap<String, String>();

		entity.put("clientID", MainActivity.user.getClientID());
		entity.put("Star_ID", Star_ID);
		entity.put("Type", "" + type);
		entity.put("number", number);

		SimpleHttpTask httpTask = HttpUtil.packagingHttpTask(entity,
				InfoSource.give_applause_booed);
		WorkerManager.addTask(httpTask, infoReceiver);
	}
}
