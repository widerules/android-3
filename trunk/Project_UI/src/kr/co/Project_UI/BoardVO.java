package kr.co.Project_UI;

public class BoardVO {
	String postNo;
	String writer;
	String writerId;
	String title;
	String content;
	int likeCnt;
	String regDate;
	
	public BoardVO(String postNo, String writer, String writerId, String title,
			String content, int likeCnt, String regDate) {
		super();
		this.postNo = postNo;
		this.writer = writer;
		this.writerId = writerId;
		this.title = title;
		this.content = content;
		this.likeCnt = likeCnt;
		this.regDate = regDate;
	}
	
	public String getPostNo() {
		return postNo;
	}
	public void setPostNo(String postNo) {
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	

}
