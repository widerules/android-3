package com.footy.Store;

public class StoreVO {
	
	private String name;
	private double latitude;
	private double longitude;
	private String id;
	private String addr;
	private String reference; 
	
	@Override
	public String toString() {
		return "StoreVO [name=" + name + ", latitude=" + latitude
				+ ", longitude=" + longitude + ", id=" + id + ", addr=" + addr
				+ ", reference=" + reference + "]";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public StoreVO(String name, double latitude, double longitude, String id,
			String addr, String reference) {
		super();
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.id = id;
		this.addr = addr;
		this.reference = reference;
	}
	
	
}
