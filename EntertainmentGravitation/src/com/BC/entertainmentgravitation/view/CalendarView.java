package com.BC.entertainmentgravitation.view;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.BC.entertainmentgravitation.R;
import com.BC.entertainmentgravitation.util.Day;

public class CalendarView extends GridView {

	int mTheMonth = -2;
	int mTheYear = 2013;

	/**
	 * 日期适配器
	 */
	CalendarAdapter calendarAdapter;

	Context context;

	/**
	 * 构造一个包含当月信息的日历
	 * 
	 * @param context
	 */
	public CalendarView(Context context) {
		super(context);
		setAdapter();
		setNumColumns(7);
		setVerticalSpacing(1);
		setHorizontalSpacing(1);
		this.context = context;
		// this.setBackgroundColor(Color.GRAY);
	}

	/**
	 * 构造一个指定月份的日历
	 * 
	 * @param context
	 * @param month
	 *            指定的月份 0-11
	 */
	public CalendarView(Context context, int month) {
		super(context);
		setAdapter(month);
		setNumColumns(7);
		setVerticalSpacing(1);
		setHorizontalSpacing(1);
		this.context = context;
		// this.setBackgroundColor(Color.GRAY);
	}

	public CalendarView(Context context, AttributeSet attrs) {
		// TODO Auto-generated constructor stub
		super(context, attrs);
		setAdapter(mTheMonth);
		setNumColumns(7);
		setVerticalSpacing(1);
		setHorizontalSpacing(1);
		this.context = context;
		// this.setBackgroundColor(Color.GRAY);
	}

	/**
	 * 一周七天
	 */
	@Override
	public void setNumColumns(int numColumns) {
		// TODO Auto-generated method stub
		super.setNumColumns(7);
	}

	/**
	 * 阻止系统设置适配器
	 */
	@Override
	public void setAdapter(ListAdapter adapter) {
		// TODO Auto-generated method stub
		calendarAdapter = new CalendarAdapter(context);
		mTheYear = calendarAdapter.getCalendar().get(Calendar.YEAR);
		super.setAdapter(calendarAdapter);
	}

	/**
	 * 设置以当前日期为标准的日期适配器
	 */
	public void setAdapter() {
		calendarAdapter = new CalendarAdapter(context);
		mTheYear = calendarAdapter.getCalendar().get(Calendar.YEAR);
		super.setAdapter(calendarAdapter);
	}

	/**
	 * ָ指定月份显示数据
	 * 
	 * @param month
	 *            0~11间的数，-1或12将自动换算年份
	 */
	public void setAdapter(int month) {
		Log.d("shuzhi", "month" + month);
		mTheMonth = month;
		if (calendarAdapter != null) {
			if (month == -1) {
				month = 11;
				mTheYear = calendarAdapter.getYear() - 1;
			} else if (month == 12) {
				month = 0;
				mTheYear = calendarAdapter.getYear() + 1;
				Log.d("shuzhi", "mTheYear = " + mTheYear);
			}
		} else {
			mTheYear = Calendar.getInstance().get(Calendar.YEAR);
		}

		calendarAdapter = new CalendarAdapter(context, month, mTheYear);
		super.setAdapter(calendarAdapter);
	}

	public int getmTheMonth() {
		return mTheMonth;
	}

	public Calendar getCalendar() {
		return calendarAdapter.getCalendar();
	}
}

/**
 * 提供日期数据的适配器
 * 
 * @author Administrator
 * 
 */
class CalendarAdapter extends BaseAdapter {

	/**
	 * 阴历对象
	 */
	// private LunarCalendar lunarCalendar = new LunarCalendar();

	private Calendar calendar;
	private Date selectDate;


	private Context context;

	private Day[] days;
	private int month = -2;
	private int year = 2013;

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public Calendar getCalendar() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(selectDate);
		return calendar;
	}

	public CalendarAdapter(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		calendar = Calendar.getInstance();
		year = calendar.get(Calendar.YEAR);
		days = monthData();
	}

	public CalendarAdapter(Context context, int month, int year) {
		this.context = context;
		this.month = month;
		this.year = year;
		calendar = Calendar.getInstance();
		days = monthData();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return days.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return days[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = newItem(position, parent);
		} else {
			Log.d("shuzhi", "convertView.getTag() = " + convertView.getTag());
			TextView textView = (TextView) convertView
					.findViewById((Integer) convertView.getTag());
			if (textView == null) {
				convertView = newItem(position, parent);
				textView = (TextView) convertView.findViewById(100 + position);
			}
			textView.setText(getDayOfMonth(days[position].dayOfMonth + "",
					days[position].dayOfLunar));
			if (days[position].isBlack) {
				textView.setTextColor(Color.BLACK);
			} else {
				textView.setTextColor(Color.parseColor("#EAEAEA"));
			}
			if (days[position].isSelect) {
				((LinearLayout) textView.getParent()).setBackgroundColor(Color
						.parseColor("#e55331"));
			} else {
				((LinearLayout) textView.getParent())
						.setBackgroundResource(R.drawable.date_bg_c);
			}
		}
		// if (position == 21) {
		// int childHeight = parent.getChildAt(7).getBottom()
		// - parent.getChildAt(7).getTop();
		// int weekHeight = parent.getChildAt(0).getBottom()
		// - parent.getChildAt(0).getTop();
		// parent.getLayoutParams().height = weekHeight + childHeight * 6 + 6;
		// }
		convertView.setTag(100 + position);
		return convertView;
	}

	/**
	 * 创建一个Item
	 * 
	 * @param position
	 * @param parent
	 * @return
	 */
	public View newItem(int position, ViewGroup parent) {
		LinearLayout layout = new LinearLayout(parent.getContext());
		layout.setOrientation(LinearLayout.VERTICAL);
		// layout.setBackgroundColor(Color.WHITE);
		// layout.setPadding(0, 15, 0, 15);
		layout.setGravity(Gravity.CENTER_HORIZONTAL);
		TextView textView = new TextView(parent.getContext());
		textView.setGravity(Gravity.CENTER_HORIZONTAL);
		textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
		if (position / 7 > 0) {
			textView.setText(getDayOfMonth(days[position].dayOfMonth + "",
					days[position].dayOfLunar));
			if (days[position].isBlack) {
				textView.setTextColor(Color.BLACK);
			} else {
				textView.setTextColor(Color.parseColor("#EAEAEA"));
			}
			if (days[position].isSelect) {
				layout.setBackgroundColor(Color.parseColor("#e55331"));
			} else {
				layout.setBackgroundResource(R.drawable.date_bg_c);
			}
		} else {
			setWeek(position, textView);
			layout.setBackgroundColor(Color.parseColor("#00000000"));
		}
		textView.setId(100 + position);
		layout.addView(textView);
		layout.setTag(100 + position);
		return layout;
	}

	/**
	 * 设置日历数据
	 */
	public Day[] monthData() {
		Day[] days = new Day[42];
		Date date = calendar.getTime();
		int theDayOfMonth;
		int theMonth;
		if (month == -2
				|| (month == calendar.get(Calendar.MONTH) && year == calendar
						.get(Calendar.YEAR))) {
			theDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
			theMonth = calendar.get(Calendar.MONTH);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			selectDate = calendar.getTime();
			calendar.setTime(getFirstDayOfWeek(calendar.getTime()));
		} else {
			calendar.set(year, month, 1);
			theDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
			theMonth = month;
			selectDate = calendar.getTime();
			calendar.setTime(getFirstDayOfWeek(calendar.getTime()));
		}
		Calendar calendarNow = Calendar.getInstance();
		for (int i = 0; i < days.length; i++) {
			Day day = new Day();

			// day.dayOfLunar = lunarCalendar.getLunarDate(
			// calendar.get(Calendar.YEAR),
			// calendar.get(Calendar.MONTH) + 1,
			// calendar.get(Calendar.DAY_OF_MONTH), false);
			day.dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
			day.dayOfweek = calendar.get(Calendar.DAY_OF_WEEK);
			day.month = calendar.get(Calendar.MONTH);
			day.year = calendar.get(Calendar.YEAR);
			if (day.dayOfMonth >= theDayOfMonth && theMonth == day.month) {
				day.isBlack = true;
				if (day.dayOfMonth == calendarNow.get(Calendar.DAY_OF_MONTH)
						&& day.month == calendarNow.get(Calendar.MONTH)
						&& day.year == calendarNow.get(Calendar.YEAR)) {
					day.isSelect = true;
				}
			}

			days[i] = day;
			calendar.set(Calendar.DAY_OF_MONTH,
					calendar.get(Calendar.DAY_OF_MONTH) + 1);
		}
		calendar.setTime(date);
		return days;
	}

	public static Date getFirstDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.SUNDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
		return c.getTime();
	}

	public void setWeek(int position, TextView view) {
		switch (position) {
		case 0:
			view.setText("星期天");
			view.setTextColor(Color.WHITE);
			break;
		case 1:
			view.setText("星期一");
			view.setTextColor(Color.WHITE);
			break;
		case 2:
			view.setText("星期二");
			view.setTextColor(Color.WHITE);
			break;
		case 3:
			view.setText("星期三");
			view.setTextColor(Color.WHITE);
			break;
		case 4:
			view.setText("星期四");
			view.setTextColor(Color.WHITE);
			break;
		case 5:
			view.setText("星期五");
			view.setTextColor(Color.WHITE);
			break;
		case 6:
			view.setText("星期六");
			view.setTextColor(Color.WHITE);
			break;
		}
	}

	public SpannableString getDayOfMonth(String d, String dv) {
		String lunarAndSolar = d + "\n" + dv;
		SpannableString sp = new SpannableString(lunarAndSolar);
		sp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0,
				d.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		sp.setSpan(new RelativeSizeSpan(1.2f), 0, d.length(),
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		if (dv != null || dv != "") {
			sp.setSpan(new RelativeSizeSpan(0.75f), d.length() + 1,
					lunarAndSolar.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		return sp;
	}
}
