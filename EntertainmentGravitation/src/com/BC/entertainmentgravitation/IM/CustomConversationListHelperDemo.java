package com.BC.entertainmentgravitation.IM;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.BC.entertainmentgravitation.HttpThread.DemoDataConstance;
import com.yuntongxun.kitsdk.custom.provider.conversation.ECCustomConversationListActionProvider;
import com.yuntongxun.kitsdk.custom.provider.conversation.ECCustomConversationListUIProvider;
import com.yuntongxun.kitsdk.ui.chatting.model.ECConversation;
import com.yuntongxun.kitsdk.utils.ToastUtil;

/**
 * @author shan
 * @date time：2015年7月16日 下午3:27:55
 */
public class CustomConversationListHelperDemo implements
		ECCustomConversationListActionProvider,
		ECCustomConversationListUIProvider {

	/**
	 * 自定义长按会话子条目列表内容
	 */
	@Override
	public List<String> getCustomConversationItemLongClickMenu(Fragment f,// ok
			ECConversation conversation) {
		
		if(DemoDataConstance.isShowCustom){
		List<String> list = new ArrayList<String>();
		list.add("测试1");
		list.add("测试2");
		list.add("测试3");
		return list;
		}
		return null;
	}

	
    /**
     * 当想重写点击会话子条目的时候需要重写该方法、kit sdk默认会进入聊天界面
     */
	@Override
	public boolean onCustomConversationItemClick(Context context,// ok
			ECConversation conversation) {
		if (DemoDataConstance.isShowCustom) {
			ToastUtil.showMessage("自定义点击会话子条目");
			return true;
		}
		return false;
	}

	/**
	 * 当想自定义长按会话子item操作、需要重写该方法
	 */
	@Override
	public boolean onCustonConversationLongClick(Context context,// ok
			ECConversation conversation) {
		if (DemoDataConstance.isShowCustom) {
			ToastUtil.showMessage("自定义长按会话子条目");
			return true;
		}
		return false;
	}

	/**
	 * 当自定义扩展了长按会话子item列表内容、重写该方法获取点击的是哪个item
	 */
	@Override
	public boolean onCustomConversationMenuItemClick(Context context,// ok
			ECConversation conversation, int position) {
		if (DemoDataConstance.isShowCustom) {
			ToastUtil.showMessage("点击的是-->" + position);
			return true;
		}
		return false;
	}

	/**
	 * 当想要自定义会话列表右边导航按钮操作、需要重写该方法
	 */
	@Override
	public boolean onCustomConversationListRightavigationBarClick(
			Context context) {
		if (DemoDataConstance.isShowCustom) {
			ToastUtil.showMessage("自定义会话列表右边点击事件");
			return true;
		}
		return false;
	}

}
