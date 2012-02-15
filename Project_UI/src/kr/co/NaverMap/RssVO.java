package kr.co.NaverMap;

public class RssVO {
	private String title;
	private String addr;
	private String des;
	private String mapx;
	private String mapy;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	@Override
	public String toString() {
		return "RssVO [title=" + title + ", addr=" + addr + ", des=" + des
				+ ", mapx=" + mapx + ", mapy=" + mapy + "]";
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public String getMapx() {
		return mapx;
	}
	public void setMapx(String mapx) {
		this.mapx = mapx;
	}
	public String getMapy() {
		return mapy;
	}
	public void setMapy(String mapy) {
		this.mapy = mapy;
	}
	public RssVO(String title, String addr, String des, String mapx, String mapy) {
		super();
		this.title = title;
		this.addr = addr;
		this.des = des;
		this.mapx = mapx;
		this.mapy = mapy;
	}
	
	
}
