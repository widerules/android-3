package com.footy.Store;

public class PartyMemberVO {
	
	private int partyNo;
	private String writer;
	private String writerId;
	private String regDate;
	
	@Override
	public String toString() {
		return "partyMemberVO [partyNo=" + partyNo + ", writer=" + writer
				+ ", writerId=" + writerId + ", regDate=" + regDate + "]";
	}
	public int getPartyNo() {
		return partyNo;
	}
	public void setPartyNo(int partyNo) {
		this.partyNo = partyNo;
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
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public PartyMemberVO(int partyNo, String writer, String writerId,
			String regDate) {
		super();
		this.partyNo = partyNo;
		this.writer = writer;
		this.writerId = writerId;
		this.regDate = regDate;
	}


}
