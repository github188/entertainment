package com.BC.entertainmentgravitation.utl;

import java.util.ArrayList;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult.AddressComponent;

public class BaiduMapUtil implements OnGetGeoCoderResultListener {

	private static GeoCoder mGeoCoder = null;
	private static BaiduMapUtil baiduMapUtil = null;
	private static Context context = null;
	private static LatLng mPoint = null;

	// 定位相关
	private static LocationClient mLocClient;
	private static MyLocationListenner myListener = new MyLocationListenner();
	private LocationMode mCurrentMode;
	private ArrayList<OnGetGeoCoderResultListener> geoCoderResultListeners = new ArrayList<OnGetGeoCoderResultListener>();
	BitmapDescriptor mCurrentMarker;
	private static MyLocationData locData;

	private static AddressComponent nowAddressComponent;

	public static AddressComponent getNowAddressComponent() {
		return nowAddressComponent;
	}

	public static void setNowAddressComponent(
			AddressComponent nowAddressComponent) {
		BaiduMapUtil.nowAddressComponent = nowAddressComponent;
	}

	public LatLng getmPoint() {
		return mPoint;
	}

	public void setmPoint(LatLng mPoint) {
		this.mPoint = mPoint;
	}

	public BaiduMapUtil(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	public void setGetGeoCodeResultListener(OnGetGeoCoderResultListener listener) {
		geoCoderResultListeners.add(listener);
	}

	public static BaiduMapUtil newInstance(Context context) {
		// TODO Auto-generated method stub
		if (baiduMapUtil == null) {
			baiduMapUtil = new BaiduMapUtil(context);
			mGeoCoder = GeoCoder.newInstance();
			// 定位初始化
			mLocClient = new LocationClient(context);
			mLocClient.registerLocationListener(myListener);
			LocationClientOption option = new LocationClientOption();
			option.setOpenGps(true);// 打开gps
			option.setCoorType("bd09ll"); // 设置坐标类型
			option.setScanSpan(10000);
			mLocClient.setLocOption(option);
			mLocClient.start();
			mGeoCoder.setOnGetGeoCodeResultListener(baiduMapUtil);
		}
		return baiduMapUtil;
	}

	/**
	 * 发起反地理编码请求
	 */
	public static void shareAddr() {
		if (mPoint != null) {
			mGeoCoder.reverseGeoCode(new ReverseGeoCodeOption()
					.location(mPoint));
		}
	}

	/**
	 * 发起地理编码请求
	 * 
	 * @param addr
	 *            包含分隔符的地址
	 */
	public static void shareGeo(String addr) {
		String[] sp = addr.split("[\\W]+");
		if (sp.length == 3) {
			mGeoCoder.geocode(new GeoCodeOption().city(sp[1]).address(sp[2]));
		}
	}

	/**
	 * 定位SDK监听函数
	 */
	private static class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			if (location == null)
				return;

			if (location.getLocType() == BDLocation.TypeNone
					|| location.getLocType() == BDLocation.TypeNetWorkException
					|| location.getLocType() == BDLocation.TypeOffLineLocationFail
					|| location.getLocType() == BDLocation.TypeOffLineLocationNetworkFail
					|| location.getLocType() == BDLocation.TypeServerError) {
				ToastUtil.show(context, "定位失败");
				return;
			}
			locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(100).latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();
			mPoint = new LatLng(location.getLatitude(), location.getLongitude());
			shareAddr();
			mLocClient.stop();

		}

		public void onReceivePoi(BDLocation poiLocation) {

		}
	}

	@Override
	public void onGetGeoCodeResult(GeoCodeResult arg0) {
		// TODO Auto-generated method stub
		for (int i = 0; i < geoCoderResultListeners.size(); i++) {
			if (geoCoderResultListeners.get(i) != null) {
				geoCoderResultListeners.get(i).onGetGeoCodeResult(arg0);
			}
		}
	}

	@Override
	public void onGetReverseGeoCodeResult(ReverseGeoCodeResult arg0) {
		// TODO Auto-generated method stub
		for (int i = 0; i < geoCoderResultListeners.size(); i++) {
			if (geoCoderResultListeners.get(i) != null) {
				geoCoderResultListeners.get(i).onGetReverseGeoCodeResult(arg0);
			}
		}
	}

}
