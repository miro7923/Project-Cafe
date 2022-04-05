package com.project.cafe.board.db;

import java.sql.Date;

public class BoardDTO 
{
	private int num;
	private String id;
	private String title;
	private String content;
	private int readcount;
	private int re_ref;
	private int re_lev;
	private int re_seq;
	private Date date;
	private String ip;
	private String image;
	private String file;
	private int comment_count;
	private String image_uid;
	private String file_uid;
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getReadcount() {
		return readcount;
	}
	public void setReadcount(int readcount) {
		this.readcount = readcount;
	}
	public int getRe_ref() {
		return re_ref;
	}
	public void setRe_ref(int re_ref) {
		this.re_ref = re_ref;
	}
	public int getRe_lev() {
		return re_lev;
	}
	public void setRe_lev(int re_lev) {
		this.re_lev = re_lev;
	}
	public int getRe_seq() {
		return re_seq;
	}
	public void setRe_seq(int re_seq) {
		this.re_seq = re_seq;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public int getComment_count() {
		return comment_count;
	}
	public void setComment_count(int comment_count) {
		this.comment_count = comment_count;
	}
	public String getImage_uid() {
		return image_uid;
	}
	public void setImage_uid(String image_uid) {
		this.image_uid = image_uid;
	}
	public String getFile_uid() {
		return file_uid;
	}
	public void setFile_uid(String file_uid) {
		this.file_uid = file_uid;
	}
	@Override
	public String toString() {
		return "BoardDTO [num=" + num + ", id=" + id + ", title=" + title + ", content=" + content + ", readcount="
				+ readcount + ", re_ref=" + re_ref + ", re_lev=" + re_lev + ", re_seq=" + re_seq + ", date=" + date
				+ ", ip=" + ip + ", image=" + image + ", file=" + file + ", comment_count=" + comment_count
				+ ", image_uid=" + image_uid + ", file_uid=" + file_uid + "]";
	}
}
