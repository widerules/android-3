package com.footy.Store;

public class PartyVO {
	private int partyNo;
	private String store;
	private Double latitude;
	private Double longitude;
	private String writer;
	private String writerId;
	private int memberCnt;
	private String regDate;

    
    @Override
	public String toString() {
		return "PartyVO [party_no=" + partyNo + ", store=" + store
				+ ", latitude=" + latitude + ", longitude=" + longitude
				+ ", writer=" + writer + ", writer_id=" + writerId
				+ ", member_cnt=" + memberCnt + ", reg_date=" + regDate + "]";
	}
	public int getParty_no() {
		return partyNo;
	}
	public void setParty_no(int party_no) {
		this.partyNo = party_no;
	}
	public String getStore() {
		return store;
	}
	public void setStore(String store) {
		this.store = store;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getWriter_id() {
		return writerId;
	}
	public void setWriter_id(String writer_id) {
		this.writerId = writer_id;
	}
	public int getMember_cnt() {
		return memberCnt;
	}
	public void setMember_cnt(int member_cnt) {
		this.memberCnt = member_cnt;
	}
	public String getReg_date() {
		return regDate;
	}
	public void setReg_date(String reg_date) {
		this.regDate = reg_date;
	}
	public PartyVO(int party_no, String store, Double latitude,
			Double longitude, String writer, String writer_id, int member_cnt,
			String reg_date) {
		super();
		this.partyNo = party_no;
		this.store = store;
		this.latitude = latitude;
		this.longitude = longitude;
		this.writer = writer;
		this.writerId = writer_id;
		this.memberCnt = member_cnt;
		this.regDate = reg_date;
	}
	
}
