package com.BC.androidtool.HttpThread;

import java.io.InputStream;

public class SimpleHttpTask extends BaseSimpleHttpTask {

	public void setUrl(String url) {
		this.url = url;
	}

	public SimpleHttpTask(int taskType, String content, String url) {
		super(taskType, content, url);
		// TODO Auto-generated constructor stub
	}

	@Override
	public InputStream getData() {
		return getInputStream(url, params);
	}

}
