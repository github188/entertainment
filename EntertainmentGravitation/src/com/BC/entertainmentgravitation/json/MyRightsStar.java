package com.BC.entertainmentgravitation.json;

public class MyRightsStar {
	/**
	 * 对方ID
	 **/
	String userID;
	/**
	 * 明星或平民
	 **/
	String permission;

	/**
	 * 明星艺名
	 **/
	String Stage_name;
	/**
	 * 明星头像
	 **/
	String head;
	/**
	 * 性别
	 **/
	String gender;
	/**
	 * 年龄
	 **/
	String age;
	/**
	 * 职业
	 **/
	String professional;
	/**
	 * 数量
	 **/
	int quantity;
	/**
	 * 价格
	 **/
	int price;
	/**
	 * 标签
	 **/
	String label;
	/**
	 * 状态
	 **/
	int state;
	/**
	 * 描述
	 **/
	String describes;
	/**
	 * 目标
	 **/
	String target;
	/**
	 * 下单时间
	 **/
	String order_time;
	/**
	 * 订单号
	 **/
	String order_sn;
	/**
	 * 身价
	 **/
	int bid;

	public String getOrder_sn() {
		return order_sn;
	}

	public void setOrder_sn(String order_sn) {
		this.order_sn = order_sn;
	}

	public String getOrder_time() {
		return order_time;
	}

	public void setOrder_time(String order_time) {
		this.order_time = order_time;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStage_name() {
		return Stage_name;
	}

	public void setStage_name(String stage_name) {
		Stage_name = stage_name;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getProfessional() {
		return professional;
	}

	public void setProfessional(String professional) {
		this.professional = professional;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDescribes() {
		return describes;
	}

	public void setDescribes(String describes) {
		this.describes = describes;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public int getBid() {
		return bid;
	}

	public void setBid(int bid) {
		this.bid = bid;
	}
	
}
