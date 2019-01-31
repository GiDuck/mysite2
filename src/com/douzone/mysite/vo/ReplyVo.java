package com.douzone.mysite.vo;

import java.sql.Timestamp;

public class ReplyVo {

	private long rp_num;
	private long rp_refBo;
	private UserVo rp_writer;
	private String rp_content;
	private Timestamp rp_timestamp;
	private long rp_ref;
	
	
	public ReplyVo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ReplyVo(long rp_num, long rp_refBo, UserVo rp_writer, String rp_content, Timestamp rp_timestamp, long rp_ref) {
		super();
		this.rp_num = rp_num;
		this.rp_refBo = rp_refBo;
		this.rp_writer = rp_writer;
		this.rp_content = rp_content;
		this.rp_timestamp = rp_timestamp;
		this.rp_ref = rp_ref;
	}
	public long getRp_num() {
		return rp_num;
	}
	public void setRp_num(long rp_num) {
		this.rp_num = rp_num;
	}
	public long getRp_refBo() {
		return rp_refBo;
	}
	public void setRp_refBo(long rp_refBo) {
		this.rp_refBo = rp_refBo;
	}
	public UserVo getRp_writer() {
		return rp_writer;
	}
	public void setRp_writer(UserVo rp_writer) {
		this.rp_writer = rp_writer;
	}
	public String getRp_content() {
		return rp_content;
	}
	public void setRp_content(String rp_content) {
		this.rp_content = rp_content;
	}
	public Timestamp getRp_timestamp() {
		return rp_timestamp;
	}
	public void setRp_timestamp(Timestamp rp_timestamp) {
		this.rp_timestamp = rp_timestamp;
	}
	public long getRp_ref() {
		return rp_ref;
	}
	public void setRp_ref(long rp_ref) {
		this.rp_ref = rp_ref;
	}
	@Override
	public String toString() {
		return "ReplyVo [rp_num=" + rp_num + ", rp_refBo=" + rp_refBo + ", rp_writer=" + rp_writer.getNo() + ", rp_content="
				+ rp_content + ", rp_timestamp=" + rp_timestamp + ", rp_ref=" + rp_ref + "]";
	}
	
	

	
}
