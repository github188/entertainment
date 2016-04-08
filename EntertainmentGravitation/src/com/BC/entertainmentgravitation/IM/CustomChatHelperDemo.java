package com.BC.entertainmentgravitation.IM;

import android.content.Context;

import com.BC.entertainmentgravitation.R;
import com.BC.entertainmentgravitation.HttpThread.DemoDataConstance;
import com.yuntongxun.ecsdk.ECMessage;
import com.yuntongxun.kitsdk.custom.provider.chat.ECCustomChatActionProvider;
import com.yuntongxun.kitsdk.custom.provider.chat.ECCustomChatPlusExtendProvider;
import com.yuntongxun.kitsdk.custom.provider.chat.ECCustomChatUIProvider;
import com.yuntongxun.kitsdk.utils.ToastUtil;

/**
 * @author shan
 * @date time：2015年7月16日 下午5:39:33
 */
public class CustomChatHelperDemo implements ECCustomChatUIProvider,
		ECCustomChatActionProvider, ECCustomChatPlusExtendProvider {

	
	/**
	 * 当想扩展点击聊天底部'+'号、在这里添加标题内容
	 */
	@Override
	public String[] getCustomPlusTitleArray(Context context) {
		String[] arr = null;
		if (DemoDataConstance.isShowCustom) {
			arr = new String[] { "测试1", "测试2", "测试3" };
			return arr;
		}
		return null;
	}

	/**
	 * 当想扩展点击聊天底部'+'号、在这里添加图标
	 */
	@Override
	public int[] getCustomPlusDrawableArray(Context context) {

		int[] arr = null;
		if (DemoDataConstance.isShowCustom) {
			arr = new int[] { R.drawable.custom_chattingfooter_file_selector,
					R.drawable.custom_chattingfooter_image_selector,
					R.drawable.custom_chattingfooter_takephoto_selector,
			};
			return arr;
		}
		return null;
	}
    
	/**
	 * 点击扩展的自定义item会触发该事件,index代表当前点击是第几个
	 */
	@Override
	public boolean onPlusExtendedItemClick(Context context, String title,
			int index) {
		if (DemoDataConstance.isShowCustom) {
			ToastUtil.showMessage("点击的是index=" + index + ";title=" + title);
			return true;
		}
		return false;
	}

	/**
	 * 当想自定义长按消息item需要重写该方法
	 */
	@Override
	public boolean onCustomChatMessageItemLongClick(Context context,
			ECMessage message) {
		if (DemoDataConstance.isShowCustom) {
			ToastUtil.showMessage("自定义长按消息子条目触发事件");
			return true;
		}
		return false;
	}

	/**
	 * 当想自定义聊天界面导航右边按钮点击事件时触发
	 */
	@Override
	public boolean onRightavigationBarClick(Context context,// ok
			String sessionId) {
		if (DemoDataConstance.isShowCustom) {
			ToastUtil.showMessage("自定义聊天右边导航按钮事件");
			return true;
		}
		return false;
	}

	/**
	 * 当想自定义点击聊天消息item的头像的时候触发
	 */
	@Override
	public boolean onMessagePortRaitClick(Context context, ECMessage message) {// ok
		if (DemoDataConstance.isShowCustom) {
			ToastUtil.showMessage("自定义聊天头像按钮事件");
			return true;
		}
		return false;
	}

}
