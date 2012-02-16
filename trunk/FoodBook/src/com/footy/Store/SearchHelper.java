package com.footy.Store;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class SearchHelper {
	private static final String RADIUS = "10000";
	private static final String KEY = "AIzaSyCyWOPcsTtzWFdwReYhdSgnWEdlIDS2Ngw";
	private static final String URL = "https://maps.googleapis.com/maps/api/place/search/json?";
	
	public static ArrayList<StoreVO> search(String searchPlace, double latitude, double longitude){
		String query = URL;
		String findPlace = URLEncoder.encode(searchPlace);
		
		query += "location=" + String.valueOf(latitude) + "," + String.valueOf(longitude)
				+ "&radius=" + RADIUS + "&name=" + findPlace 
				+ "&sensor=true&key=" + KEY;

		Log.d("searchDebug", query);
		StringBuilder output = new StringBuilder();
		try {
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(query);
			HttpResponse response = client.execute(post);
			
			InputStream is = response.getEntity().getContent();
			BufferedReader br = new BufferedReader(new InputStreamReader(is,"utf-8"));
			String str = null;
			while( (str = br.readLine()) != null) {
				output.append(str + "\n");
			}
    		br.close();
		} catch (Exception e) {
			Log.e("myDebug", e.toString());
		}
		Log.d("searchDebug", output.toString().trim());
		return parse(output.toString().trim());
	}
	
	private static ArrayList<StoreVO> parse(String jsonStr) {
		ArrayList<StoreVO> list = new ArrayList<StoreVO>();
		try {
			JSONObject jobj = new JSONObject(jsonStr);
			if(!jobj.getString("status").equals("OK")) {
				return null;
			}
			JSONArray jarr = jobj.getJSONArray("results");
			for(int i=0; i<jarr.length(); i++) {
				jobj = (JSONObject)jarr.get(i);
				String name = jobj.getString("name");
				double latitude = Double.parseDouble(jobj.getJSONObject("geometry").getJSONObject("location").getString("lat"));
				double longitude = Double.parseDouble(jobj.getJSONObject("geometry").getJSONObject("location").getString("lng"));
				String id = jobj.getString("id");
				String addr = jobj.getString("vicinity");
				String reference = jobj.getString("reference");
				
				list.add(new StoreVO(name, latitude, longitude, id, addr, reference));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}
}
