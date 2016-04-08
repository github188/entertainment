package com.BC.entertainmentgravitation.view;

import java.util.ArrayList;
import java.util.List;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.BC.entertainmentgravitation.R;
import com.BC.entertainmentgravitation.util.CitydbUtil;
import com.BC.entertainmentgravitation.util.MyListItem;

public class CitySelectWheel2 extends BaseSelcetWheel {
	private WheelView country;
	private WheelView city;
	private WheelView county;
	// private WheelView street;
	private CitydbUtil citydbUtil;
	private List<MyListItem> countryList;
	private List<MyListItem> cityList;
	private boolean countryScrolling;
	private boolean cityScrolling;

	// WheelInterfasc wheelInterfasc;

	// private boolean countyScrolling;

	public CitySelectWheel2(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		citydbUtil = CitydbUtil.structureCitydbUtil(context);
	}

	public CitySelectWheel2(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		citydbUtil = CitydbUtil.structureCitydbUtil(context);
	}

	public CitySelectWheel2(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		citydbUtil = CitydbUtil.structureCitydbUtil(context);
	}

	@Override
	public void addView(View child, int index,
			android.view.ViewGroup.LayoutParams params) {
		// TODO Auto-generated method stub
		if (child instanceof WheelView) {
			CountryAdapter countryAdapter;
			switch (child.getId()) {
			case R.id.country:
				countryList = citydbUtil.selectCountry();
				country = (WheelView) child;
				country.setVisibleItems(3);
				countryAdapter = new CountryAdapter(getContext(), countryList);
				country.setViewAdapter(countryAdapter);
				country.setDrawShadows(false);
				break;
			case R.id.city:
				city = (WheelView) child;
				city.setVisibleItems(3);
				city.setDrawShadows(false);

				country.addChangingListener(new OnWheelChangedListener() {
					@Override
					public void onChanged(WheelView wheel, int oldValue,
							int newValue) {
						if (!countryScrolling) {
							updateCity(newValue);
							// updateCounty(0);
							// updateStreet(0);
						}
						if (wheelInterfasc != null) {
							wheelInterfasc.selectValue(1,
									countryList.get(newValue).getName(), false);
						}
					}
				});

				// country.addScrollingListener(new OnWheelScrollListener() {
				// @Override
				// public void onScrollingStarted(WheelView wheel) {
				// countryScrolling = true;
				// }
				//
				// @Override
				// public void onScrollingFinished(WheelView wheel) {
				// countryScrolling = false;
				// updateCity(country.getCurrentItem());
				// updateCounty(0);
				// // updateStreet(0);
				// }
				// });

				country.setCurrentItem(1);
				country.setCurrentItem(0);
				break;
			// case R.id.county:
			// county = (WheelView) child;
			// county.setVisibleItems(3);
			// county.setDrawShadows(false);
			// city.addChangingListener(new OnWheelChangedListener() {
			// @Override
			// public void onChanged(WheelView wheel, int oldValue,
			// int newValue) {
			// if (!cityScrolling) {
			// updateCounty(newValue);
			// // updateStreet(0);
			// }
			// if (wheelInterfasc != null) {
			// wheelInterfasc.selectValue(2, cityList
			// .get(newValue).getName(), false);
			// }
			// }
			// });
			//
			// city.addScrollingListener(new OnWheelScrollListener() {
			// @Override
			// public void onScrollingStarted(WheelView wheel) {
			// cityScrolling = true;
			// }
			//
			// @Override
			// public void onScrollingFinished(WheelView wheel) {
			// cityScrolling = false;
			// updateCounty(city.getCurrentItem());
			// // updateStreet(0);
			// }
			// });
			// city.setCurrentItem(0);
			// updateCounty(0);
			//
			// county.addChangingListener(new OnWheelChangedListener() {
			// @Override
			// public void onChanged(WheelView wheel, int oldValue,
			// int newValue) {
			// if (wheelInterfasc != null) {
			// wheelInterfasc.selectValue(3,
			// countyList.get(newValue).getName(), false);
			// }
			// }
			// });
			//
			// break;
			// case R.id.street:
			// street = (WheelView) child;
			// street.setVisibleItems(3);
			// street.setDrawShadows(false);
			// county.addChangingListener(new OnWheelChangedListener() {
			// @Override
			// public void onChanged(WheelView wheel, int oldValue,
			// int newValue) {
			// if (!countyScrolling) {
			// updateStreet(newValue);
			// }
			// if (wheelInterfasc != null) {
			// wheelInterfasc.selectValue(3,
			// countyList.get(newValue).getName(), false);
			// }
			// }
			// });
			//
			// county.addScrollingListener(new OnWheelScrollListener() {
			// @Override
			// public void onScrollingStarted(WheelView wheel) {
			// countyScrolling = true;
			// }
			//
			// @Override
			// public void onScrollingFinished(WheelView wheel) {
			// countyScrolling = false;
			// updateStreet(county.getCurrentItem());
			// }
			// });
			// county.setCurrentItem(0);
			// updateStreet(0);
			// street.addChangingListener(new OnWheelChangedListener() {
			//
			// @Override
			// public void onChanged(WheelView wheel, int oldValue,
			// int newValue) {
			// // TODO Auto-generated method stub
			// if (wheelInterfasc != null) {
			// wheelInterfasc.selectValue(4,
			// streetList.get(newValue).getName(), false);
			// }
			// }
			// });
			// break;
			}
		}
		super.addView(child, index, params);
	}

	@Override
	public void getAll() {
		// TODO Auto-generated method stub
		if (wheelInterfasc != null) {
			if (countryList.size() > 0) {
				wheelInterfasc.selectValue(1, countryList.get(0).getName(),
						false);
			}
			if (cityList != null && cityList.size() > 0) {
				wheelInterfasc.selectValue(2, cityList.get(0).getName(), false);
			}
			// if (countyList.size() > 0) {
			// wheelInterfasc.selectValue(3, countyList.get(0).getName(),
			// false);
			// }
			// if (streetList.size() > 0) {
			// wheelInterfasc.selectValue(4, streetList.get(0).getName(),
			// false);
			// }
		}
	}

	protected void updateCity(int currentItem) {
		// TODO Auto-generated method stub
		if (countryList == null && countryList.size() <= 0) {
			return;
		}
		MyListItem item = countryList.get(currentItem);
		cityList = citydbUtil.selectCity(item.getPcode());
		CountryAdapter adapter = new CountryAdapter(getContext(), cityList);
		city.setViewAdapter(adapter);
		// city.setCurrentItem(1);
		if (cityList.size() > 0) {
			city.setCurrentItem(0, true);
		}
		if (wheelInterfasc != null) {
			if (cityList.size() > 0) {
				wheelInterfasc.selectValue(2, cityList.get(0).getName(), false);
			} else {
				wheelInterfasc.selectValue(2, "", false);
			}
		}
	}

	// protected void updateCounty(int currentItem) {
	// // TODO Auto-generated method stub
	// if (cityList != null && cityList.size() > 0) {
	// MyListItem item = cityList.get(currentItem);
	// countyList = citydbUtil.selectCounty(item.getPcode());
	// } else {
	// countyList = new ArrayList<MyListItem>();
	// }
	// if (county != null) {
	// county.setViewAdapter(new CountryAdapter(getContext(), countyList));
	// if (countyList.size() > 0) {
	// county.setCurrentItem(0, true);
	// }
	// }
	// if (wheelInterfasc != null) {
	// if (countyList.size() > 0) {
	// wheelInterfasc.selectValue(3, countyList.get(0).getName(),
	// false);
	// } else {
	// wheelInterfasc.selectValue(3, "", false);
	// }
	// }
	// }

	// protected void updateStreet(int currentItem) {
	// // TODO Auto-generated method stub
	// if (countyList != null && countyList.size() > 0) {
	// MyListItem item = countyList.get(currentItem);
	// streetList = citydbUtil.selectStreet(item.getPcode());
	// } else {
	// streetList = new ArrayList<MyListItem>();
	// }
	// if (street != null) {
	// street.setViewAdapter(new CountryAdapter(getContext(), streetList));
	// if (streetList.size() > 0) {
	// street.setCurrentItem(0, true);
	// }
	// }
	// if (wheelInterfasc != null) {
	// if (streetList.size() > 0) {
	// wheelInterfasc.selectValue(4, streetList.get(0).getName(),
	// false);
	// } else {
	// wheelInterfasc.selectValue(4, "", false);
	// }
	// }
	// }

	/**
	 * Adapter for countries
	 */
	private class CountryAdapter extends AbstractWheelTextAdapter {

		private List<MyListItem> list;

		// Countries names
		protected CountryAdapter(Context context, List<MyListItem> list) {
			super(context);
			this.list = list;

			this.setItemResource(R.layout.wheel_text_item);
			this.setItemTextResource(R.id.text);

		}

		@Override
		public int getItemsCount() {
			return list.size();
		}

		@Override
		protected CharSequence getItemText(int index) {
			return list.get(index).getName();
		}
	}
}
