package com.BC.entertainmentgravitation.fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.BC.androidtool.BaseFragment;
import com.BC.androidtool.HttpThread.WorkerManager;
import com.BC.androidtool.JSON.BaseEntity;
import com.BC.androidtool.adapter.CommonAdapter;
import com.BC.androidtool.views.pull.PullToRefreshBase;
import com.BC.androidtool.views.pull.PullToRefreshBase.OnRefreshListener2;
import com.BC.androidtool.views.pull.PullToRefreshGridView;
import com.BC.entertainmentgravitation.R;
import com.BC.entertainmentgravitation.HttpThread.InfoSource;
import com.BC.entertainmentgravitation.HttpThread.SimpleHttpTask;
import com.BC.entertainmentgravitation.activity.MainActivity;
import com.BC.entertainmentgravitation.json.request.EditPersonal;
import com.BC.entertainmentgravitation.json.response.album.Album;
import com.BC.entertainmentgravitation.json.response.album.More_pictures;
import com.BC.entertainmentgravitation.json.response.album.Photo_images;
import com.BC.entertainmentgravitation.json.response.album.Photographs;
import com.BC.entertainmentgravitation.utl.HttpUtil;
import com.BC.entertainmentgravitation.utl.ToastUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class CenterAlbumUtl extends BaseFragment implements OnClickListener,
		OnItemClickListener {
	Activity activity;
	View contentView;

	Album album;

	PullToRefreshGridView pullToRefreshGridView1;

	boolean canEdit = false;
	String fileName = "";

	ArrayList<Photo_images> more_pictures = new ArrayList<Photo_images>();
	ArrayList<Photo_images> more_picturesImages = new ArrayList<Photo_images>();
	ArrayList<Photo_images> more_picturesPhotographs = new ArrayList<Photo_images>();

	private CommonAdapter<Photo_images> adapter1;
	private CommonAdapter<Photo_images> adapter2;
	private CommonAdapter<Photo_images> adapter3;
	private RadioGroup radio;
	private int pageIndex = 1;
	private String setType = "1";

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		activity = getActivity();
		contentView = inflater.inflate(R.layout.item_center_album, null);
		return contentView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		// more_pictures.add(new More_pictures());
		// more_picturesImages.add(new Photo_images());
		// more_picturesPhotographs.add(new Photographs());
		init();
		sendReqAlbum();
		super.onViewCreated(view, savedInstanceState);
	}

	private void init() {
		// TODO Auto-generated method stub
		pullToRefreshGridView1 = (PullToRefreshGridView) contentView
				.findViewById(R.id.pullToRefreshGridView1);
		pullToRefreshGridView1.getRefreshableView().setNumColumns(7);
		pullToRefreshGridView1.setOnRefreshListener(refreshListener);
		initAdapter();
		pullToRefreshGridView1.setAdapter(adapter1);
		pullToRefreshGridView1.setOnItemClickListener(this);

		radio = (RadioGroup) contentView.findViewById(R.id.radioGroup1);
		radio.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.radio0:
					pullToRefreshGridView1.setAdapter(adapter1);
					break;
				case R.id.radio1:
					pullToRefreshGridView1.setAdapter(adapter2);
					break;
				case R.id.radio2:
					pullToRefreshGridView1.setAdapter(adapter3);
					break;

				}
			}
		});
	}

	private void initAdapter() {
		// TODO Auto-generated method stub
		adapter1 = new CommonAdapter<Photo_images>(activity,
				R.layout.item_list_album2, more_pictures) {

			@Override
			public void convert(ViewHolder helper, final Photo_images item) {

				ImageView imageView = helper.getView(R.id.Picture_address);
				if (item.getPicture_address() == null) {
					Glide.clear(imageView);
					imageView.setImageResource(R.drawable.add_image);
					imageView.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							showAlertDialog(R.layout.dialog_alert3,
									R.id.button3, R.id.button1, R.id.button2,
									CenterAlbumUtl.this);
							setType = "1";
						}
					});
				} else {
					Glide.with(activity).load(item.getPicture_address())
							.centerCrop()
							.diskCacheStrategy(DiskCacheStrategy.ALL)
							.placeholder(R.drawable.home_image).into(imageView);
					imageView.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							// item.getPicture_address()
							showAlertDialog1(R.layout.dialog_alert6,
									R.id.button3, R.id.button1, R.id.button2,
									CenterAlbumUtl.this,item.getPicture_ID());
						}
					});
				}
			}
		};
		adapter2 = new CommonAdapter<Photo_images>(activity,
				R.layout.item_list_album2, more_picturesImages) {

			@Override
			public void convert(ViewHolder helper, final Photo_images item) {

				ImageView imageView = helper.getView(R.id.Picture_address);
				if (item.getPicture_address() == null) {
					Glide.clear(imageView);
					imageView.setImageResource(R.drawable.add_image);
					imageView.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							showAlertDialog(R.layout.dialog_alert3,
									R.id.button3, R.id.button1, R.id.button2,
									CenterAlbumUtl.this);
							setType = "2";
						}
					});
				} else {
					Glide.with(activity).load(item.getPicture_address())
							.centerCrop()
							.diskCacheStrategy(DiskCacheStrategy.ALL)
							.placeholder(R.drawable.home_image).into(imageView);
					imageView.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							showAlertDialog1(R.layout.dialog_alert6,
									R.id.button3, R.id.button1, R.id.button2,
									CenterAlbumUtl.this,item.getPicture_ID());
						}
					});
				}
			}
		};
		adapter3 = new CommonAdapter<Photo_images>(activity,
				R.layout.item_list_album2, more_picturesPhotographs) {

			@Override
			public void convert(ViewHolder helper, final Photo_images item) {

				ImageView imageView = helper.getView(R.id.Picture_address);
				if (item.getPicture_address() == null) {
					Glide.clear(imageView);
					imageView.setImageResource(R.drawable.add_image);
					imageView.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							showAlertDialog(R.layout.dialog_alert3,
									R.id.button3, R.id.button1, R.id.button2,
									CenterAlbumUtl.this);
						}
					});
					setType = "3";
				} else {
					Glide.with(activity).load(item.getPicture_address())
							.centerCrop()
							.diskCacheStrategy(DiskCacheStrategy.ALL)
							.placeholder(R.drawable.home_image).into(imageView);
					imageView.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							showAlertDialog1(R.layout.dialog_alert6,
									R.id.button3, R.id.button1, R.id.button2,
									CenterAlbumUtl.this,item.getPicture_ID());
						}
					});
				}

			}
		};
	}

	public void initPersonalInformation() {
		// TODO Auto-generated method stub
		if (album == null) {
			ToastUtil.show(activity, "获取数据失败");
			return;
		}
		if (pageIndex == 1) {// 第一页时，先清空数据集
			more_pictures.clear();
			more_picturesImages.clear();
			more_picturesPhotographs.clear();

			adapter1.clearAll();
			adapter2.clearAll();
			adapter3.clearAll();

			more_pictures.add(new Photo_images());
			more_picturesImages.add(new Photo_images());
			more_picturesPhotographs.add(new Photo_images());
		}

		more_pictures
				.addAll(more_pictures.size() - 1, album.getMore_pictures());
		more_picturesImages.addAll(more_picturesImages.size() - 1,
				album.getPhoto_images());
		more_picturesPhotographs.addAll(more_picturesPhotographs.size() - 1,
				album.getPhotographs());

		pageIndex++;

		adapter1.setList(more_pictures);
		adapter2.setList(more_picturesImages);
		adapter3.setList(more_picturesPhotographs);
	}

	public void save() {
		// TODO Auto-generated method stub
		if (album == null) {
			album = new Album();
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	/**
	 * 获取相册信息
	 */
	private void sendReqAlbum() {
		if (MainActivity.user == null) {
			ToastUtil.show(activity, "无法获取信息");
			return;
		}
		HashMap<String, String> entity = new HashMap<String, String>();

		entity.put("clientID", MainActivity.user.getClientID());

		SimpleHttpTask httpTask = HttpUtil.packagingHttpTask(entity,
				InfoSource.photo_album_management);
		showProgressDialog("获取相册信息...");
		WorkerManager.addTask(httpTask, this);
	}

	/**
	 * 保存相册信息
	 */
	private void sendReqSaveAlbum(Bitmap bitmap) {
		if (MainActivity.user == null) {
			ToastUtil.show(activity, "无法获取信息");
			return;
		}
		HashMap<String, String> entity = new HashMap<String, String>();

		entity.put("clientID", MainActivity.user.getClientID());
		// entity.put("Image_name",
		// "data:image/jpg;base64," + HttpUtil.getBtye64String(bitmap));
		entity.put("Image_name", fileName);
		entity.put("Image_type", setType);
		entity.put("Image_location",
				"data:image/jpg;base64," + HttpUtil.getBtye64String(bitmap));

		SimpleHttpTask httpTask = HttpUtil.packagingHttpTask(entity,
				InfoSource.edit_photo_albums);
		showProgressDialog("保存图片...");
		WorkerManager.addTask(httpTask, this);
	}

	// private void sendRemoveAlbum(String type) {
	// if (MainActivity.user == null) {
	// ToastUtil.show(activity, "无法获取信息");
	// return;
	// }
	// HashMap<String, String> entity = new HashMap<String, String>();
	//
	// entity.put("clientID", MainActivity.user.getClientID());
	//
	// SimpleHttpTask httpTask = HttpUtil.packagingHttpTask(entity,
	// InfoSource.photo_album_management);
	// showProgressDialog("获取相册信息...");
	// WorkerManager.addTask(httpTask, this);
	// }

	OnRefreshListener2<GridView> refreshListener = new OnRefreshListener2<GridView>() {

		@Override
		public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
			// 下拉刷新
			String time = DateUtils.formatDateTime(activity,
					System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
							| DateUtils.FORMAT_SHOW_DATE
							| DateUtils.FORMAT_ABBREV_ALL);
			pullToRefreshGridView1.getLoadingLayoutProxy().setRefreshingLabel(
					"正在刷新");
			pullToRefreshGridView1.getLoadingLayoutProxy().setPullLabel("下拉刷新");
			pullToRefreshGridView1.getLoadingLayoutProxy().setReleaseLabel(
					"释放开始刷新");
			refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(
					"最后更新时间:" + time);
			pageIndex = 1;
			// 调用数据

			// sendReq(pageIndex);
			sendReqAlbum();

		}

		@Override
		public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
			// 上拉翻页
			String time = DateUtils.formatDateTime(activity,
					System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
							| DateUtils.FORMAT_SHOW_DATE
							| DateUtils.FORMAT_ABBREV_ALL);
			pullToRefreshGridView1.getLoadingLayoutProxy().setRefreshingLabel(
					"正在加载");
			pullToRefreshGridView1.getLoadingLayoutProxy().setPullLabel("上拉翻页");
			pullToRefreshGridView1.getLoadingLayoutProxy().setReleaseLabel(
					"释放开始加载");
			refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(
					"最后加载时间:" + time);
			// 调用数据
			sendReqAlbum();
		}

	};

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		// ToastUtil.show(activity, "准备删除");
	}

	@Override
	public void onInfoReceived(int errcode, HashMap<String, Object> items) {
		// TODO Auto-generated method stub
		super.onInfoReceived(errcode, items);
		pullToRefreshGridView1.onRefreshComplete();
	}

	@Override
	public void requestSuccessful(String jsonString, int taskType) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		switch (taskType) {
		case InfoSource.edit_photo_albums:
			ToastUtil.show(getActivity(), "保存成功");
			pageIndex = 1;
			sendReqAlbum();
			break;
		case InfoSource.photo_album_management:
			// if (MainActivity.user.getPermission().equals("2")) {
			BaseEntity<Album> baseEntity5 = gson.fromJson(jsonString,
					new TypeToken<BaseEntity<Album>>() {
					}.getType());
			Album album = baseEntity5.getData();
			if (album != null) {
				setAlbum(album);
				initPersonalInformation();
			} else {
				ToastUtil.show(activity, "获取数据失败");
			}
			// } else {
			// BaseEntity<List<More_pictures>> baseEntity5 = gson.fromJson(
			// jsonString,
			// new TypeToken<BaseEntity<List<More_pictures>>>() {
			// }.getType());
			// Album album = new Album();
			// List<More_pictures> more_pictures = baseEntity5.getData();
			// album.setMore_pictures(more_pictures);
			// album.setPhoto_images(new ArrayList<Photo_images>());
			// album.setPhotographs(new ArrayList<Photographs>());
			// if (album != null) {
			// setAlbum(album);
			// initPersonalInformation();
			// } else {
			// ToastUtil.show(activity, "获取数据失败");
			// }
			// radio.setVisibility(View.GONE);
			// }

			break;
		case InfoSource.image:
			ToastUtil.show(activity, "设置成功");
			break;
		case InfoSource.delete_picture:
			pageIndex = 1;
			sendReqAlbum();
			break;	
			
		}
	}

	@Override
	public void obtainImage(String imagePath) {
		// TODO Auto-generated method stub
		fileName = imagePath;
		File fe = new File(Environment.getExternalStorageDirectory(), imagePath);
		Bitmap bmp = BitmapFactory.decodeFile(fe.getPath());
		sendReqSaveAlbum(bmp);
	}

	private void showAlertDialog1(int layoutId, int noId, int phoneImageId,
			int takePictureImageId, final BaseFragment flag,final String image) {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		final AlertDialog ad = builder.create();
		ad.show();
		Window window = ad.getWindow();

		View view = LayoutInflater.from(getActivity()).inflate(layoutId, null);
		window.setContentView(view);
		window.findViewById(noId).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ad.dismiss();
			}
		});
		window.findViewById(phoneImageId).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						deletepicture(image);
						ad.dismiss();
					}
				});
		window.findViewById(takePictureImageId).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						sendReqSaveUser(image);
						ad.dismiss();
					}
				});
	}

	/**
	 * 提交用户信息
	 */
	private void sendReqSaveUser(String image) {
		if (MainActivity.user == null) {
			ToastUtil.show(activity, "无法获取信息");
			return;
		}
		HashMap<String, String> entity = new HashMap<String, String>();

		entity.put("clientID", MainActivity.user.getClientID());
		entity.put("pictureID", image);
		SimpleHttpTask httpTask = HttpUtil.packagingHttpTask(entity,
				InfoSource.image);
		showProgressDialog("提交基本信息...");
		WorkerManager.addTask(httpTask, this);
	}
	/**
	 * 删除照片
	 */
	private void deletepicture(String image) {
		if (MainActivity.user == null) {
			ToastUtil.show(activity, "无法获取信息");
			return;
		}
		HashMap<String, String> entity = new HashMap<String, String>();
		
		entity.put("clientID", ""+MainActivity.user.getClientID());
		entity.put("pictureID", ""+image);
		SimpleHttpTask httpTask = HttpUtil.packagingHttpTask(entity,
				InfoSource.delete_picture);
		
		showProgressDialog("提交基本信息...");
		WorkerManager.addTask(httpTask, this);
	}
}
