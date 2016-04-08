package com.BC.entertainmentgravitation.view;

import java.util.ArrayList;
import java.util.List;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.BC.entertainmentgravitation.R;

public class SelectWheelLinkage2 extends BaseSelcetWheel {
	WheelView content1;
	WheelView content2;

	public WheelInterfasc wheelInterfasc1;
	public List<String> contentList1 = new ArrayList<String>();
	public List<List<String>> contentList2 = new ArrayList<List<String>>();
	// String content1String = "";
	List<String> contentLists2;
	String content2String = "";

	public List<String> getContentList1() {
		return contentList1;
	}

	public void setContentList1(List<String> contentList1) {
		if (contentList1 == null) {
			return;
		}
		this.contentList1 = contentList1;
		// if (contentList1.size() > 0) {
		// // content1String = contentList1.get(0);
		// }
		content1.setViewAdapter(new SelectAdapter(getContext(), contentList1));
		content1.addChangingListener(new OnWheelChangedListener() {
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				// content2String = SelectWheelLinkage2.this.contentList2.get(
				// newValue).get(0);
				upDataContentList2(newValue);
			}
		});
	}

	public List<List<String>> getContentList2() {
		return contentList2;
	}

	public void setContentList2(List<List<String>> contentList2) {
		if (contentList2 == null) {
			return;
		}
		this.contentList2 = contentList2;
		if (contentList2.size() > 0 && contentList2.get(0).size() > 0) {
			content2String = contentList2.get(0).get(0);
			changedUp();
		}
		upDataContentList2(0);
	}

	// public String getContent1String() {
	// return content1String;
	// }
	//
	// public void setContent1String(String content1String) {
	// this.content1String = content1String;
	// changedUp();
	// }

	public String getContent2String() {
		return content2String;
	}

	public void setContent2String(String content2String) {
		this.content2String = content2String;
		changedUp();
	}

	public SelectWheelLinkage2(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public SelectWheelLinkage2(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public SelectWheelLinkage2(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addView(View child, int index,
			android.view.ViewGroup.LayoutParams params) {
		// TODO Auto-generated method stub
		if (child instanceof WheelView) {
			switch (child.getId()) {
			case R.id.content:
				content1 = (WheelView) child;

				content1.setViewAdapter(new SelectAdapter(getContext(),
						contentList1));
				content1.addChangingListener(new OnWheelChangedListener() {
					@Override
					public void onChanged(WheelView wheel, int oldValue,
							int newValue) {
						// content1String = contentList1.get(newValue);
						// changedUp();
						content2.setCurrentItem(0, true);
					}
				});
				content1.setVisibleItems(3);
				content1.setDrawShadows(false);
				break;
			case R.id.content2:
				content2 = (WheelView) child;
				content2.setViewAdapter(new SelectAdapter(getContext(),
						new ArrayList<String>()));
				content2.addChangingListener(new OnWheelChangedListener() {
					@Override
					public void onChanged(WheelView wheel, int oldValue,
							int newValue) {
						// content2String = contentList2.get(newValue).;
						changedUp();
					}
				});
				content2.setVisibleItems(3);
				content2.setDrawShadows(false);

				break;
			}
		}
		super.addView(child, index, params);
	}

	private void upDataContentList2(final int i) {
		// TODO Auto-generated method stub
		contentLists2 = contentList2.get(i);
		if (contentLists2 != null && contentLists2.size() > 0) {
			content2.setViewAdapter(new SelectAdapter(getContext(),
					contentLists2));
			content2.addChangingListener(new OnWheelChangedListener() {
				@Override
				public void onChanged(WheelView wheel, int oldValue,
						int newValue) {
					content2String = contentLists2.get(newValue);
					changedUp();
				}
			});
		}

	}

	private void changedUp() {
		if (wheelInterfasc != null) {
			wheelInterfasc.selectValue(1, content2String, true);
		}
	}

	/**
	 * Adapter for countries
	 */
	private class SelectAdapter extends AbstractWheelTextAdapter {

		private List<String> list;

		// Countries names
		protected SelectAdapter(Context context, List<String> list) {
			super(context, R.layout.wheel_text_item);
			this.list = list;
		}

		@Override
		public int getItemsCount() {
			return list.size();
		}

		@Override
		protected CharSequence getItemText(int index) {
			return list.get(index);
		}
	}
}
