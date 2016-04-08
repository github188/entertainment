package com.BC.entertainmentgravitation.IM;

import com.yuntongxun.kitsdk.core.ECCustomProviderEnum;
import com.yuntongxun.kitsdk.core.ECKitCustomProviderManager;

/**
 * @author shan
 * @date time：2015年7月16日 下午3:28:21
 */
public class CustomUIAndActionManager {

	/**
	 * 将自定义扩展的功能加载到kit sdk进行管理
	 */
	public static void initCustomUI() {

		ECKitCustomProviderManager.addCustomProvider(
				ECCustomProviderEnum.CONVERSATION_PROVIDER,
				CustomConversationListHelperDemo.class);

		ECKitCustomProviderManager.addCustomProvider(
				ECCustomProviderEnum.CHATTING_PROVIDER,
				CustomChatHelperDemo.class);

	}

}
