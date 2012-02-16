package com.footy.Map;

import com.google.android.maps.GeoPoint;


public class MapInfo{
	public static MapInfo mapInfo = new MapInfo();
	private static String address;
	private static GeoPoint point;
	private MapInfo(){
	}
	public static MapInfo getMapInfo(){
		return mapInfo;
	}
	
	public GeoPoint getPoint() {
		return point;
	}
	public void setPoint(GeoPoint point) {
		this.point = point;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public static void setMapInfo(MapInfo mapInfo) {
		MapInfo.mapInfo = mapInfo;
	}
	@Override
	public String toString() {
		return "MapInfo [getPoint()=" + getPoint() + ", getAddress()="
				+ getAddress() + "]";
	}

}