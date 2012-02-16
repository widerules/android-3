package com.footy.Board;

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

import android.util.Log;

import com.footy.Facebook.FacebookHelper;
import com.footy.FoodBook.Constants;
import com.footy.Util.ImageUploader;

public class BoardHelper {
	
	public BoardHelper() {
		// nothing to do yet
	}

	/**
	 * @return ArrayList of posts
	 */
	public ArrayList<BoardVO> getList() {
		ArrayList<BoardVO> list = new ArrayList<BoardVO>();
		String urlStr = Constants.WEB_SERVER_URL + "getList.jsp";
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
				String category = jobj.getString("category");
				int likeCnt = jobj.getInt("like_cnt");
				String regDate = jobj.getString("reg_date");
				
				if( FacebookHelper.isFriend(writerId) )
					list.add(new BoardVO(postNo, writer, writerId, title, category, likeCnt, regDate));
				else if( !Constants.FILTER_BOARD ) 
					list.add(new BoardVO(postNo, writer, writerId, title, category, likeCnt, regDate));
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
		String uploadUrl = Constants.WEB_SERVER_URL + "imageUpload.jsp";
		String urlStr = Constants.WEB_SERVER_URL + "writePost.jsp";
		String defaultImg = Constants.WEB_SERVER_URL + "noimage.gif";
		String path = Constants.WEB_SERVER_URL + "uploadImages/";
		if( !boardVO.getImgUrl().equals("")) {
			String url = ImageUploader.upload(uploadUrl, boardVO.getImgUrl());
			try {
				url = URLEncoder.encode(url, "utf-8");
				url = url.replaceAll("\\+", "%20");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			boardVO.setImgUrl(path + url);
			Log.d("myDebug", "최종 저장 url" + boardVO.getImgUrl());
		}
		else
			boardVO.setImgUrl(defaultImg);
		
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("writer", boardVO.getWriter()));
		params.add(new BasicNameValuePair("writerId", boardVO.getWriterId()));
		params.add(new BasicNameValuePair("title", boardVO.getTitle()));
		params.add(new BasicNameValuePair("category", boardVO.getCategory()));
		params.add(new BasicNameValuePair("latitude", String.valueOf(boardVO.getLatitude())));
		params.add(new BasicNameValuePair("longitude", String.valueOf(boardVO.getLongitude())));
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
		String urlStr = Constants.WEB_SERVER_URL + "getBoardContent.jsp?postNo=" + postNo;
		String jsonStr = call(urlStr);
		try {
			JSONObject jobj = new JSONObject(jsonStr);
			String writer = jobj.getString("writer");
			String writerId = jobj.getString("writer_id");
			String title = jobj.getString("title");
			String category = jobj.getString("category");
//			double latitude = Double.parseDouble(jobj.getString("latitude"));
//			double longitude = Double.parseDouble(jobj.getString("longitude"));
			double latitude = 30.43;
			double longitude = 130.43;
			String content = jobj.getString("content");
			String imgUrl = jobj.getString("img_url");
			int likeCnt = jobj.getInt("like_cnt");
			String regDate = jobj.getString("reg_date");
			return new BoardVO(postNo, writer, writerId, title, category, latitude, longitude, content, imgUrl, likeCnt, regDate);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}
