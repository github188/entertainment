package com.BC.entertainmentgravitation.HttpThread;

import java.io.InputStream;

import com.BC.androidtool.HttpThread.BaseSimpleHttpTask;

public class SimpleHttpTask extends BaseSimpleHttpTask {

	public SimpleHttpTask(int taskType, String content, String url) {
		super(taskType, content, url);
		// TODO Auto-generated constructor stub
	}

	@Override
	public InputStream getData() {
		switch (taskType) {
		// /**
		// * 登录
		// */
		// case InfoSource.LOGIN_TYPE:
		// url = InfoSource.ADDRESS + "Login";
		// break;

		/**
		 * 注册
		 */
		case InfoSource.REGISTER:
			url = InfoSource.ADDRESS + "?_mod=user&_act=register";
			break;
		/**
		 * 忘记密码
		 */
		case InfoSource.forget_password:
			url = InfoSource.ADDRESS + "?_mod=user&_act=forgot_password";
			break;
		/**
		 * 短信验证码
		 */
		case InfoSource.VERIFY:
			url = InfoSource.ADDRESS + "?_mod=user&_act=auth_code";
			break;
		/**
		 * 个人信息（昵称、等级、娱币、基本信息）
		 */
		case InfoSource.personal_information:
			url = InfoSource.ADDRESS + "?_mod=user&_act=base_info";
			break;
		/**
		 * 签到
		 */
		case InfoSource.sign_in:
			url = InfoSource.ADDRESS + "?_mod=user&_act=check_in";
			break;
		/**
		 * 活动、广告
		 */
		case InfoSource.activities:
			url = InfoSource.ADDRESS + "?_mod=topic&_act=subject";
			break;
		/**
		 * 充值
		 */
		case InfoSource.top_up:
			url = InfoSource.ADDRESS + "?_mod=user&_act=recharge";
			break;
		/**
		 * 明星信息
		 */
		case InfoSource.star_information:
			url = InfoSource.ADDRESS + "?_mod=starer&_act=base_info";
			break;
		/**
		 * 给掌声、喝倒彩
		 */
		case InfoSource.give_applause_booed:
			url = InfoSource.ADDRESS + "?_mod=user&_act=dig";
			break;
		/**
		 * 加关注
		 */
		case InfoSource.and_attention:
			url = InfoSource.ADDRESS + "?_mod=user&_act=follow";
			break;
		/**
		 * 好友列表
		 */
		case InfoSource.friends_list:
			url = InfoSource.ADDRESS + "?_mod=user&_act=friends_list";
			break;
		/**
		 * 添加（判断是否为平台用户）、删除好友
		 */
		case InfoSource.add_buddy:
			url = InfoSource.ADDRESS + "?_mod=user&_act=add_rm_friends";
			break;
		/**
		 * 明星发布信息列表
		 */
		case InfoSource.stars_release_information_list:
			url = InfoSource.ADDRESS + "?_mod=starer&_act=release_list";
			break;
		/**
		 * 明星发布信息详情
		 */
		case InfoSource.star_release_information_details:
			url = InfoSource.ADDRESS + "?_mod=starer&_act=release_detail";
			break;
		/**
		 * 评比进行中列表（包含结果和奖励）（按关注度排名）（按注册时间排名）
		 */
		case InfoSource.in_comparison_to_listApply_to_be_a_platform_star_:
			url = InfoSource.ADDRESS + "?_mod=topic&_act=appraise_show_list";
			break;
		/**
		 * 商品列表
		 */
		case InfoSource.goods_list:
			url = InfoSource.ADDRESS + "?_mod=goods&_act=lists";
			break;
		/**
		 * 红包（娱币）兑换
		 */
		case InfoSource.for_goods:
			url = InfoSource.ADDRESS + "?_mod=bonus&_act=exchange";
			break;
		/**
		 * 发布商品（只有明星能操作）
		 */
		case InfoSource.release_goods:
			url = InfoSource.ADDRESS + "?_mod=goods&_act=release";
			break;
		/**
		 * 订单列表
		 */
		case InfoSource.list_of_orders:
			url = InfoSource.ADDRESS + "?_mod=order&_act=lists";
			break;
		/**
		 * 管理订单
		 */

		case InfoSource.order_management:
			url = InfoSource.ADDRESS + "?_mod=order&_act=management";
			break;
		/**
		 * 持有列表
		 */
		case InfoSource.hold_list:
			url = InfoSource.ADDRESS + "?_mod=bonus&_act=lists";
			break;
		/**
		 * 发出列表
		 */
		case InfoSource.a_list:
			url = InfoSource.ADDRESS + "?_mod=bonus&_act=release_list";
			break;
		/**
		 * 奖品清单
		 */
		case InfoSource.the_prize_list:
			url = InfoSource.ADDRESS + "?_mod=prize&_act=lists";
			break;
		/**
		 * 抽奖结果提交
		 */
		case InfoSource.lottery_results_submitted:
			url = InfoSource.ADDRESS + "?_mod=prize&_act=result_submited";
			break;
		/**
		 * 已关注明星列表
		 */
		case InfoSource.has_concerned_star_list:
			url = InfoSource.ADDRESS + "?_mod=user&_act=starer_followed_list";
			break;
		/**
		 * 编辑个人信息（是否申请为明星）
		 */
		case InfoSource.edit_personal_information:
			url = InfoSource.ADDRESS + "?_mod=user&_act=edit_base_info";
			break;
		/**
		 * 相册管理
		 */
		case InfoSource.photo_album_management:
			url = InfoSource.ADDRESS + "?_mod=user&_act=album_management";
			break;
		/**
		 * 编辑相册
		 */
		case InfoSource.edit_photo_albums:
			url = InfoSource.ADDRESS + "?_mod=user&_act=edit_album";
			break;
		/**
		 * 演艺经历
		 */
		case InfoSource.her_career:
			url = InfoSource.ADDRESS + "?_mod=user&_act=career";
			break;
		/**
		 * 编辑演艺经历
		 */
		case InfoSource.editor_of_her_career:
			url = InfoSource.ADDRESS + "?_mod=user&_act=edit_career";
			break;
		/**
		 * 外部链接（图标、标题、链接）
		 */
		case InfoSource.external_links:
			url = InfoSource.ADDRESS + "?_mod=user&_act=external_links";
			break;
		/**
		 * 编辑外部链接
		 */
		case InfoSource.edit_external_links:
			url = InfoSource.ADDRESS + "?_mod=starer&_act=edit_release";
			break;
		/**
		 * 商务信息
		 */
		case InfoSource.business_information:
			url = InfoSource.ADDRESS + "?_mod=user&_act=business_info";
			break;
		/**
		 * 编辑商务信息
		 */
		case InfoSource.edit_business_information:
			url = InfoSource.ADDRESS + "?_mod=user&_act=edit_business_info";
			break;
		/**
		 * 身价定价
		 */
		case InfoSource.value_pricing:
			url = InfoSource.ADDRESS + "?_mod=user&_act=value_pricing";
			break;
		/**
		 * 赠送（红包、娱币）
		 */
		case InfoSource.give:
			url = InfoSource.ADDRESS + "?_mod=bonus&_act=give";
			break;
		/**
		 * 评论
		 */
		case InfoSource.comment:
			url = InfoSource.ADDRESS + "?_mod=user&_act=comment";
			break;
		/**
		 * 签到信息
		 */
		case InfoSource.continuous:
			url = InfoSource.ADDRESS + "?_mod=user&_act=continuous";
			break;
		/**
		 * 搜索明星
		 */
		case InfoSource.search:
			url = InfoSource.ADDRESS + "?_mod=starer&_act=search";
			break;
		/**
		 * 意见反馈
		 */
		case InfoSource.feedback:
			url = InfoSource.ADDRESS + "?_mod=user&_act=log_feed_balk";
			break;
		/**
		 * 交易记录查询
		 */
		case InfoSource.account:
			url = InfoSource.ADDRESS + "?_mod=user&_act=account";
			break;
		/**
		 * 制作权益卡
		 */
		case InfoSource.make_equity_card:
			url = InfoSource.ADDRESS + "?_mod=starer&_act=make_equity_card";
			break;
		/**
		 * 获取权益卡列表
		 */
		case InfoSource.equitylists:
			url = InfoSource.ADDRESS + "?_mod=goods&_act=equitylists";
			break;
		/**
		 * 兑换权益
		 */
		case InfoSource.subscribe:
			url = InfoSource.ADDRESS + "?_mod=order&_act=subscribe";
			break;
		/**
		 * 我的权益（普通）
		 */
		case InfoSource.my_rights:
			url = InfoSource.ADDRESS + "?_mod=order&_act=my_rights";
			break;
		/**
		 * 我的权益（明星）
		 */
		case InfoSource.my_rights_star:
			url = InfoSource.ADDRESS + "?_mod=order&_act=my_rights_star";
			break;
		/**
		 * 权益状态
		 */
		case InfoSource.changeState:
			url = InfoSource.ADDRESS + "?_mod=order&_act=changeState";
			break;
		/**
		 * 获取外部连接
		 **/
		case InfoSource.outconnect:
			url = InfoSource.ADDRESS + "?_mod=starer&_act=outconnect";
			break;
		/**
		 * 添加外部链
		 **/
		case InfoSource.addoutconnect:
			url = InfoSource.ADDRESS + "?_mod=starer&_act=addoutconnect";
			break;
		/**
		 * 删除外部链接
		 **/
		case InfoSource.dltoutconnect:
			url = InfoSource.ADDRESS + "?_mod=starer&_act=dltoutconnect";
			break;
		/**
		 * 修改外部链接
		 **/
		case InfoSource.updateoutconnect:
			url = InfoSource.ADDRESS + "?_mod=starer&_act=updateoutconnect";
			break;
		/**
		 * 贡献排行
		 **/
		case InfoSource.contribution:
			url = InfoSource.ADDRESS + "?_mod=topic&_act=contribution";
			break;
		/**
		 * K线
		 **/
		case InfoSource.k_line_graph:
			url = InfoSource.ADDRESS + "?_mod=starer&_act=k_line_graph";
			break;
		/**
		 * 支付成功
		 **/
		case InfoSource.recharge_success:
			url = InfoSource.ADDRESS + "?_mod=user&_act=recharge_success";
			break;
			/**
			 * 设为头像
			 */
			case InfoSource.image:
				url = InfoSource.ADDRESS + "?_mod=user&_act=edit_headIco";
				break;
			/**
			 * 设为头像
			 */
			case InfoSource.delete_picture:
				url = InfoSource.ADDRESS + "?_mod=user&_act=delete_picture";
				break;
		}

		return getInputStream(url, params);
	}

	public String getUrl() {
		switch (taskType) {
		// /**
		// * 登录
		// */
		// case InfoSource.LOGIN_TYPE:
		// url = InfoSource.ADDRESS + "Login";
		// break;
		/**
		 * 注册
		 */
		case InfoSource.REGISTER:
			url = InfoSource.ADDRESS + "?_mod=user&_act=register";
			break;
		/**
		 * 忘记密码
		 */
		case InfoSource.forget_password:
			url = InfoSource.ADDRESS + "?_mod=user&_act=forgot_password";
			break;
		/**
		 * 短信验证码
		 */
		case InfoSource.VERIFY:
			url = InfoSource.ADDRESS + "?_mod=user&_act=auth_code";
			break;
		/**
		 * 个人信息（昵称、等级、娱币、基本信息）
		 */
		case InfoSource.personal_information:
			url = InfoSource.ADDRESS + "?_mod=user&_act=base_info";
			break;
		/**
		 * 签到
		 */
		case InfoSource.sign_in:
			url = InfoSource.ADDRESS + "?_mod=user&_act=check_in";
			break;
		/**
		 * 活动、广告
		 */
		case InfoSource.activities:
			url = InfoSource.ADDRESS + "?_mod=topic&_act=subject";
			break;
		/**
		 * 充值
		 */
		case InfoSource.top_up:
			url = InfoSource.ADDRESS + "?_mod=user&_act=recharge";
			break;
		/**
		 * 明星信息
		 */
		case InfoSource.star_information:
			url = InfoSource.ADDRESS + "?_mod=starer&_act=base_info";
			break;
		/**
		 * 给掌声、喝倒彩
		 */
		case InfoSource.give_applause_booed:
			url = InfoSource.ADDRESS + "?_mod=user&_act=dig";
			break;
		/**
		 * 加关注
		 */
		case InfoSource.and_attention:
			url = InfoSource.ADDRESS + "?_mod=user&_act=follow";
			break;
		/**
		 * 好友列表
		 */
		case InfoSource.friends_list:
			url = InfoSource.ADDRESS + "?_mod=user&_act=friends_list";
			break;
		/**
		 * 添加（判断是否为平台用户）、删除好友
		 */
		case InfoSource.add_buddy:
			url = InfoSource.ADDRESS + "?_mod=user&_act=add_rm_friends";
			break;
		/**
		 * 明星发布信息列表
		 */
		case InfoSource.stars_release_information_list:
			url = InfoSource.ADDRESS + "?_mod=starer&_act=release_list";
			break;
		/**
		 * 明星发布信息详情
		 */
		case InfoSource.star_release_information_details:
			url = InfoSource.ADDRESS + "?_mod=starer&_act=release_detail";
			break;
		/**
		 * 评比进行中列表（包含结果和奖励）（按关注度排名）（按注册时间排名）
		 */
		case InfoSource.in_comparison_to_listApply_to_be_a_platform_star_:
			url = InfoSource.ADDRESS + "?_mod=topic&_act=appraise_show_list";
			break;
		/**
		 * 商品列表
		 */
		case InfoSource.goods_list:
			url = InfoSource.ADDRESS + "?_mod=topic&_act=appraise_show_list";
			break;
		/**
		 * 红包（娱币）兑换
		 */
		case InfoSource.for_goods:
			url = InfoSource.ADDRESS + "?_mod=bonus&_act=exchange";
			break;
		/**
		 * 发布商品（只有明星能操作）
		 */
		case InfoSource.release_goods:
			url = InfoSource.ADDRESS + "?_mod=goods&_act=release";
			break;
		/**
		 * 订单列表
		 */
		case InfoSource.list_of_orders:
			url = InfoSource.ADDRESS + "?_mod=order&_act=lists";
			break;
		/**
		 * 管理订单
		 */
		case InfoSource.order_management:
			url = InfoSource.ADDRESS + "?_mod=order&_act=management";
			break;
		/**
		 * 持有列表
		 */
		case InfoSource.hold_list:
			url = InfoSource.ADDRESS + "?_mod=bonus&_act=lists";
			break;
		/**
		 * 发出列表
		 */
		case InfoSource.a_list:
			url = InfoSource.ADDRESS + "?_mod=bonus&_act=release_list";
			break;
		/**
		 * 奖品清单
		 */
		case InfoSource.the_prize_list:
			url = InfoSource.ADDRESS + "?_mod=prize&_act=lists";
			break;
		/**
		 * 抽奖结果提交
		 */
		case InfoSource.lottery_results_submitted:
			url = InfoSource.ADDRESS + "?_mod=prize&_act=result_submited";
			break;
		/**
		 * 已关注明星列表
		 */
		case InfoSource.has_concerned_star_list:
			url = InfoSource.ADDRESS + "?_mod=user&_act=starer_followed_list";
			break;
		/**
		 * 编辑个人信息（是否申请为明星）
		 */
		case InfoSource.edit_personal_information:
			url = InfoSource.ADDRESS + "?_mod=user&_act=edit_base_info";
			break;
		/**
		 * 相册管理
		 */
		case InfoSource.photo_album_management:
			url = InfoSource.ADDRESS + "?_mod=user&_act=album_management";
			break;
		/**
		 * 编辑相册
		 */
		case InfoSource.edit_photo_albums:
			url = InfoSource.ADDRESS + "?_mod=user&_act=edit_album";
			break;
		/**
		 * 演艺经历
		 */
		case InfoSource.her_career:
			url = InfoSource.ADDRESS + "?_mod=user&_act=career";
			break;
		/**
		 * 编辑演艺经历
		 */
		case InfoSource.editor_of_her_career:
			url = InfoSource.ADDRESS + "?_mod=user&_act=edit_career";
			break;
		/**
		 * 外部链接（图标、标题、链接）
		 */
		case InfoSource.external_links:
			url = InfoSource.ADDRESS + "?_mod=user&_act=external_links";
			break;
		/**
		 * 编辑外部链接
		 */
		case InfoSource.edit_external_links:
			url = InfoSource.ADDRESS + "?_mod=user&_act=edit_external_links";
			break;
		/**
		 * 商务信息
		 */
		case InfoSource.business_information:
			url = InfoSource.ADDRESS + "?_mod=user&_act=business_info";
			break;
		/**
		 * 编辑商务信息
		 */
		case InfoSource.edit_business_information:
			url = InfoSource.ADDRESS + "?_mod=user&_act=edit_business_info";
			break;
		/**
		 * 身价定价
		 */
		case InfoSource.value_pricing:
			url = InfoSource.ADDRESS + "?_mod=user&_act=value_pricing";
			break;
		/**
		 * 赠送（红包、娱币）
		 */
		case InfoSource.give:
			url = InfoSource.ADDRESS + "?_mod=bonus&_act=give";
			break;
		/**
		 * 评论
		 */
		case InfoSource.comment:
			url = InfoSource.ADDRESS + "?_mod=user&_act=comment";
			break;
		/**
		 * 设为头像
		 *//*
		case InfoSource.image:
			url = InfoSource.ADDRESS + "?_mod=user&_act=edit_headIco";
			break;
		*//**
		 * 设为头像
		 *//*
		case InfoSource.delete_picture:
			url = InfoSource.ADDRESS + "?_mod=user&_act=delete_picture";
			break;*/
		}
		return url;

	}

}
