package com.douzone.mysite.vo;

import java.sql.Timestamp;

public class BoardVo {

	private long bo_num;
	private String bo_title;
	private UserVo bo_writer;
	private String bo_content;
	private Timestamp bo_timestamp;
	private long bo_count;
	private long bo_ref;
	private long bo_dept;
	private long bo_order;

	public BoardVo() {
		super();
		// TODO Auto-generated constructor stub
	}


	

	public BoardVo(long bo_num, String bo_title, UserVo bo_writer, String bo_content, Timestamp bo_timestamp,
			long bo_count, long bo_ref, long bo_dept, long bo_order) {
		super();
		this.bo_num = bo_num;
		this.bo_title = bo_title;
		this.bo_writer = bo_writer;
		this.bo_content = bo_content;
		this.bo_timestamp = bo_timestamp;
		this.bo_count = bo_count;
		this.bo_ref = bo_ref;
		this.bo_dept = bo_dept;
		this.bo_order = bo_order;
	}




	public String getBo_title() {
		return bo_title;
	}

	public void setBo_title(String bo_title) {
		this.bo_title = bo_title;
	}

	public long getBo_num() {
		return bo_num;
	}

	public void setBo_num(long bo_num) {
		this.bo_num = bo_num;
	}

	public UserVo getBo_writer() {
		return bo_writer;
	}

	public void setBo_writer(UserVo bo_writer) {
		this.bo_writer = bo_writer;
	}

	public String getBo_content() {
		return bo_content;
	}

	public void setBo_content(String bo_content) {
		this.bo_content = bo_content;
	}

	public Timestamp getBo_timestamp() {
		return bo_timestamp;
	}

	public void setBo_timestamp(Timestamp bo_timestamp) {
		this.bo_timestamp = bo_timestamp;
	}


	

	public long getBo_count() {
		return bo_count;
	}

	public void setBo_count(long bo_count) {
		this.bo_count = bo_count;
	}

	public long getBo_ref() {
		return bo_ref;
	}

	public void setBo_ref(long bo_ref) {
		this.bo_ref = bo_ref;
	}



	public long getBo_dept() {
		return bo_dept;
	}




	public void setBo_dept(long bo_dept) {
		this.bo_dept = bo_dept;
	}




	public long getBo_order() {
		return bo_order;
	}



	public void setBo_order(long bo_order) {
		this.bo_order = bo_order;
	}




	@Override
	public String toString() {
		return "BoardVo [bo_num=" + bo_num + ", bo_title=" + bo_title + ", bo_writer=" + bo_writer + ", bo_content="
				+ bo_content + ", bo_timestamp=" + bo_timestamp + ", bo_count=" + bo_count + ", bo_ref=" + bo_ref
				+ ", bo_dept=" + bo_dept + ", bo_order=" + bo_order + "]";
	}

	
	
}
