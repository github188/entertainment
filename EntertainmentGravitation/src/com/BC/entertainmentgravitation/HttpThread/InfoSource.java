package com.BC.entertainmentgravitation.HttpThread;

import com.BC.androidtool.HttpThread.BaseInfoSource;

public class InfoSource extends BaseInfoSource {
	/**
	 * 正式地址
	 */
	// public static final String ADDRESS = "http://119.29.18.195:9999/";
//	public static final String ADDRESS = "http://182.92.179.220:8081/php/index.php";
	public static final String ADDRESS = "http://app.haimianyu.cn/index.php";
	
	/**
	 * 测试地址
	 */
	// public static final String ADDRESS =
	// "http://192.168.1.102/hmy_new/php/index.php";
	// public static final String ADDRESS =
	// "http://192.168.1.102/hmy_new/php/index.php";
//	public static final String ADDRESS = "http://120.25.107.200/php/index.php";
	// public static final String ADDRESS =
	// "http://120.25.120.111/hmy_new/index.php";
	// public static final String ADDRESS =
	// "http://120.25.120.111/hmy/index.php";
	// public static final String IMAGEADDRESS = "http://www.e56hcc.com";
	/**
	 * 终端类型
	 */
	public static final int POS = 1;
	// /**
	// * 登录
	// */
	// public static final int LOGIN_TYPE = 1;
	/**
	 * 短信验证码
	 */
	public static final int VERIFY = 1;
	/**
	 * 注册
	 */
	public static final int REGISTER = 2;
	/**
	 * 忘记密码
	 */
	public static final int forget_password = 3;
	/**
	 * 
	 个人信息（昵称、等级、娱币、基本信息）
	 **/
	public static final int personal_information = 4;
	/**
	 * 签到
	 **/
	public static final int sign_in = 5;
	/**
	 * 活动、广告
	 **/
	public static final int activities = 6;
	/**
	 * 充值
	 **/
	public static final int top_up = 7;
	/**
	 * 明星信息
	 **/
	public static final int star_information = 8;
	/**
	 * 给掌声、喝倒彩
	 **/
	public static final int give_applause_booed = 9;
	/**
	 * 加关注
	 **/
	public static final int and_attention = 10;
	/**
	 * 好友列表
	 **/
	public static final int friends_list = 11;
	/**
	 * 添加（判断是否为平台用户）、删除好友
	 **/
	public static final int add_buddy = 12;
	/**
	 * 明星发布信息列表
	 **/
	public static final int stars_release_information_list = 13;
	/**
	 * 明星发布信息详情
	 **/
	public static final int star_release_information_details = 14;
	/**
	 * 评比进行中列表（包含结果和奖励）（按关注度排名）（按注册时间排名）
	 **/
	public static final int in_comparison_to_listApply_to_be_a_platform_star_ = 15;
	/**
	 * 商品列表
	 **/
	public static final int goods_list = 16;
	/**
	 * 红包（娱币）兑换
	 **/
	public static final int for_goods = 17;
	/**
	 * 发布商品（只有明星能操作）
	 **/
	public static final int release_goods = 18;
	/**
	 * 订单列表
	 **/
	public static final int list_of_orders = 19;
	/**
	 * 管理订单
	 **/
	public static final int order_management = 20;
	/**
	 * 持有列表
	 **/
	public static final int hold_list = 21;
	/**
	 * 发出列表
	 **/
	public static final int a_list = 22;
	/**
	 * 奖品清单
	 **/
	public static final int the_prize_list = 23;
	/**
	 * 抽奖结果提交
	 **/
	public static final int lottery_results_submitted = 24;
	/**
	 * 已关注明星列表
	 **/
	public static final int has_concerned_star_list = 25;
	/**
	 * 编辑个人信息（是否申请为明星）
	 **/
	public static final int edit_personal_information = 26;
	/**
	 * 相册管理
	 **/
	public static final int photo_album_management = 27;
	/**
	 * 编辑相册
	 **/
	public static final int edit_photo_albums = 28;
	/**
	 * 演艺经历
	 **/
	public static final int her_career = 29;
	/**
	 * 编辑演艺经历
	 **/
	public static final int editor_of_her_career = 30;
	/**
	 * 外部链接（图标、标题、链接）
	 **/
	public static final int external_links = 31;
	/**
	 * 编辑外部链接
	 **/
	public static final int edit_external_links = 32;
	/**
	 * 商务信息
	 **/
	public static final int business_information = 33;
	/**
	 * 编辑商务信息
	 **/
	public static final int edit_business_information = 34;
	/**
	 * 身价定价
	 **/
	public static final int value_pricing = 35;
	/**
	 * 赠送（红包、娱币）
	 **/
	public static final int give = 36;
	/**
	 * 评论
	 **/
	public static final int comment = 37;
	/**
	 * 签到
	 **/
	public static final int continuous = 40;
	/**
	 * 搜索
	 **/
	public static final int search = 41;
	/**
	 * 意见反馈
	 **/
	public static final int feedback = 42;
	/**
	 * 交易记录
	 **/
	public static final int account = 43;
	/**
	 * 制作权益卡
	 **/
	public static final int make_equity_card = 45;
	/**
	 * 获取权益卡列表
	 **/
	public static final int equitylists = 46;
	/**
	 * 认购权益卡
	 **/
	public static final int subscribe = 47;
	/**
	 * 我的权益卡（普通用户）
	 **/
	public static final int my_rights = 48;
	/**
	 * 我的权益卡（明星用户）
	 **/
	public static final int my_rights_star = 49;
	/**
	 * 权益状态变更
	 **/
	public static final int changeState = 50;
	/**
	 * 获取外部连接
	 **/
	public static final int outconnect = 51;
	/**
	 * 添加外部链
	 **/
	public static final int addoutconnect = 52;
	/**
	 * 删除外部链接
	 **/
	public static final int dltoutconnect = 53;
	/**
	 * 修改外部链接
	 **/
	public static final int updateoutconnect = 54;
	/**
	 * 贡献排行
	 **/
	public static final int contribution = 55;
	/**
	 * 日K周K
	 **/
	public static final int k_line_graph = 56;
	/**
	 * 充值
	 **/
	public static final int recharge_success = 57;
	
	/**
	 * 设为头像
	 **/
	public static final int image = 58;
	
	
	/**
	 * 删除照片
	 **/
	public static final int delete_picture = 59;
	
	/**
	 * 微信预支付
	 */
	public static final int wx_pre_pay = 60;
	
	/**
	 * 微信支付成功
	 */
	public static final int wx_pay = 61;
}
