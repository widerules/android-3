package kr.co.NaverMap;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import kr.co.NaverMap.RssVO;

public class RssReader {
	public RssReader(){
		
	}
	public ArrayList<RssVO> process(String rssAddr)throws Exception{
//		String rssAddr =  "http://www.chosun.com/site/data/rss/ent.xml";
		
		URL url = new URL(rssAddr);	//접속!
		URLConnection urlCon = url.openConnection();
//		
//		BufferedReader br = new BufferedReader(
//				new InputStreamReader(urlCon.getInputStream(), "utf-8"));
//		String str = "";
//		while((str=br.readLine())!=null){
//			System.out.println(str);
//		}
		// Dom 파서  documentbuilderfactory 추상
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setIgnoringElementContentWhitespace(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(urlCon.getInputStream());
		Element root = doc.getDocumentElement();
		NodeList itemList = root.getElementsByTagName("item");
		
		ArrayList<RssVO> rssList = new ArrayList<RssVO>();
		for(int i=0; i<itemList.getLength(); i++){
			Element itemEt = (Element)itemList.item(i);		//item까지 접근완료!
	
//			Element linkEt = (Element)itemEt.getElementsByTagName("link").item(0);
//			//link 까지 접근완료!
//			Text linkText= (Text)linkEt.getFirstChild();
//			System.out.println("link: " + linkText.getData());
//			
			
			String title = getDataByTagName(itemEt, "title");
			String addr = getDataByTagName(itemEt, "address");
			String desc = getDataByTagName(itemEt, "description");
			String mapx = getDataByTagName(itemEt, "mapx");
			String mapy = getDataByTagName(itemEt, "mapy");
			
			System.out.println(title);
			System.out.println(addr);
			System.out.println(desc);
			System.out.println(mapx);
			System.out.println(mapy);
			
			RssVO rssVO = new RssVO(title, addr, desc, mapx, mapy);
			rssList.add(rssVO);
			

		}
		return rssList;
	}
	
	
	private String getDataByTagName(Element element, String tagName){
		Element childEt = (Element)element.getElementsByTagName(tagName).item(0);
		//link 까지 접근완료!
		Text childText= (Text)childEt.getFirstChild();
		return childText.getData();
	}
	
//	public static void main(String[] args) {
//		try {
//			new RssReader().process("http://openapi.naver.com/search?key=c8799621af3fadce9f75b1aef0d5f915&query=%EA%B0%88%EB%B9%84%EC%A7%91&target=local&start=1&display=10");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}
