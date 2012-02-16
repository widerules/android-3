package com.footy.Board;

public class BoardVO {
	int postNo;
	String writer;
	String writerId;
	String title;
	String category;
	double latitude;
	double longitude;
	String content;
	String imgUrl;
	int likeCnt;
	String regDate;

	public BoardVO(int postNo, String writer, String writerId, String title,
			double latitude, double longitude, String category, int likeCnt, String regDate) {
		super();
		this.postNo = postNo;
		this.writer = writer;
		this.writerId = writerId;
		this.title = title;
		this.latitude = latitude;
		this.longitude = longitude;
		this.category = category;
		this.likeCnt = likeCnt;
		this.regDate = regDate;
	}

	public BoardVO(int postNo, String writer, String writerId, String title,
			String category, double latitude, double longitude, String content,
			String imgUrl, int likeCnt, String regDate) {
		super();
		this.postNo = postNo;
		this.writer = writer;
		this.writerId = writerId;
		this.title = title;
		this.category = category;
		this.latitude = latitude;
		this.longitude = longitude;
		this.content = content;
		this.imgUrl = imgUrl;
		this.likeCnt = likeCnt;
		this.regDate = regDate;
	}



	public int getPostNo() {
		return postNo;
	}
	public void setPostNo(int postNo) {
		this.postNo = postNo;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getWriterId() {
		return writerId;
	}
	public void setWriterId(String writerId) {
		this.writerId = writerId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public int getLikeCnt() {
		return likeCnt;
	}
	public void setLikeCnt(int likeCnt) {
		this.likeCnt = likeCnt;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	@Override
	public String toString() {
		return "BoardVO [postNo=" + postNo + ", writer=" + writer
				+ ", writerId=" + writerId + ", title=" + title + ", latitude="
				+ latitude + ", longitude=" + longitude + ", content="
				+ content + ", imgUrl=" + imgUrl + ", likeCnt=" + likeCnt
				+ ", regDate=" + regDate + "]";
	}
	
}
