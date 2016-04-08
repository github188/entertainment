package com.BC.entertainmentgravitation.json.response.comment;

import java.util.List;

public class Comment {
	private String Head_portrait;

	private String describe;

	private String The_picture;

	private String External_links;

	private List<Comment_on_the_list> Comment_on_the_list;

	public void setHead_portrait(String Head_portrait) {
		this.Head_portrait = Head_portrait;
	}

	public String getHead_portrait() {
		return this.Head_portrait;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getDescribe() {
		return this.describe;
	}

	public void setThe_picture(String The_picture) {
		this.The_picture = The_picture;
	}

	public String getThe_picture() {
		return this.The_picture;
	}

	public void setExternal_links(String External_links) {
		this.External_links = External_links;
	}

	public String getExternal_links() {
		return this.External_links;
	}

	public void setComment_on_the_list(
			List<Comment_on_the_list> Comment_on_the_list) {
		this.Comment_on_the_list = Comment_on_the_list;
	}

	public List<Comment_on_the_list> getComment_on_the_list() {
		return this.Comment_on_the_list;
	}
}
