package kr.co.Project_UI;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import util.ImageUploader;

import android.util.Log;

public class BoardHelper {
	
	
	public BoardHelper() {
		// nothing to do yet
	}

	/**
	 * @return ArrayList of posts
	 */
	public ArrayList<BoardVO> getList() {
		ArrayList<BoardVO> list = new ArrayList<BoardVO>();
		String urlStr = "http://115.145.172.123:8000/android-3/getList.jsp";
		String jsonStr = call(urlStr);
		try {
			JSONObject jobj = new JSONObject(jsonStr);
			JSONArray jarr = jobj.getJSONArray("data");
			for(int i=0; i<jarr.length(); i++) {
				jobj = (JSONObject)jarr.get(i);
				int postNo = jobj.getInt("post_no");
				String writer = jobj.getString("writer");
				String writerId = jobj.getString("writer_id");
				String title = jobj.getString("title");
				int likeCnt = jobj.getInt("like_cnt");
				String regDate = jobj.getString("reg_date");
				list.add(new BoardVO(postNo, writer, writerId, title, likeCnt, regDate));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * @param urlStr
	 * @return : JSON String which response of urlStr
	 */
	private String call(String urlStr) {
    	StringBuilder output = new StringBuilder();
    	try {
    		HttpClient client = new DefaultHttpClient();
    		HttpPost post = new HttpPost(urlStr);
    		HttpResponse response = client.execute(post);
    		InputStream is = response.getEntity().getContent();
    		
    		BufferedReader br = new BufferedReader(new InputStreamReader(is,"euc-kr"));
    		String str = null;
    		while( (str = br.readLine()) != null) {
    			output.append(str + "\n");
    		}
    		br.close();
    		
		} catch (Exception e) {
			Log.e("myDebug", e.toString());
		}
    	return output.toString();
    }
    
	/**
	 * @param boardVO
	 * @return : true if write done else false
	 */
	public boolean writePost(BoardVO boardVO) {
		String uploadUrl = "http://115.145.172.123:8000/android-3/imageUpload.jsp";
		String urlStr = "http://115.145.172.123:8000/android-3/writePost.jsp";
		String defaultImg = "http://115.145.172.123:8000/android-3/noimage.gif";
		String path = "http://115.145.172.123:8000/android-3/uploadImages/";
		if( !boardVO.getImgUrl().equals("")) {
			String url = ImageUploader.upload(uploadUrl, boardVO.getImgUrl());
			try {
				url = URLEncoder.encode(boardVO.getImgUrl(), "utf-8");
				url = url.replaceAll("\\+", "%20");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			boardVO.setImgUrl(path + url);
		}
		else
			boardVO.setImgUrl(defaultImg);
		
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("writer", boardVO.getWriter()));
		params.add(new BasicNameValuePair("writerId", boardVO.getWriterId()));
		params.add(new BasicNameValuePair("title", boardVO.getTitle()));
		params.add(new BasicNameValuePair("content", boardVO.getContent()));
		params.add(new BasicNameValuePair("imgUrl", boardVO.getImgUrl()));
		UrlEncodedFormEntity entity = null;
		try {
			entity = new UrlEncodedFormEntity(params, "euc-kr");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return write(urlStr, entity);
	}
	private boolean write(String urlStr, UrlEncodedFormEntity params) {
		StringBuilder output = new StringBuilder();
		try {
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(urlStr);
			post.setEntity(params);
			HttpResponse response = client.execute(post);
			
			InputStream is = response.getEntity().getContent();
			BufferedReader br = new BufferedReader(new InputStreamReader(is,"euc-kr"));
			String str = null;
			while( (str = br.readLine()) != null) {
				output.append(str + "\n");
			}
    		br.close();
    		if(output.toString().trim().equals("done")) {
    			return true;
    		} else {
    			return false;
    		}
		} catch (Exception e) {
			Log.e("myDebug", e.toString());
		}
		return false;
    }
	
	public BoardVO getContent(int postNo) {
		String urlStr = "http://115.145.172.123:8000/android-3/getBoardContent.jsp?postNo=" + postNo;
		String jsonStr = call(urlStr);
		try {
			JSONObject jobj = new JSONObject(jsonStr);
			String writer = jobj.getString("writer");
			String writerId = jobj.getString("writer_id");
			String title = jobj.getString("title");
			int likeCnt = jobj.getInt("like_cnt");
			String content = jobj.getString("content");
			String regDate = jobj.getString("reg_date");
			String imgUrl = jobj.getString("img_url");
			return new BoardVO(postNo, writer, writerId, title, content, likeCnt, regDate, imgUrl);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}
