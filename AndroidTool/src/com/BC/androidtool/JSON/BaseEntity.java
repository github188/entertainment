package com.BC.androidtool.JSON;

import java.lang.reflect.Type;

import com.BC.androidtool.utils.JsonUtils;

public class BaseEntity<T> {
	private String message;

	private String status;

	private T data;

	public BaseEntity() {
	}

	public BaseEntity(String message, String status) {
		super();
		this.message = message;
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public static <T> BaseEntity<T> parse(String json, Type type) {
		try {
			return JsonUtils.getObject(json, type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		BaseEntity<T> result = new BaseEntity<T>("数据格式错误", "2");
		return result;
	}

}
