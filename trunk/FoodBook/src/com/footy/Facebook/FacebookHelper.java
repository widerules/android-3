package com.footy.Facebook;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Application;
import android.util.Log;

import com.facebook.android.Facebook;

public class FacebookHelper extends Application {
	
	private static Facebook facebook = FacebookInfo.getInstance();
	private static ArrayList<String> friendsList;
	
	public FacebookHelper() {
		
	}
	
	/**
	 * Get current user's Facebook friends list.
	 * It must be called once for filtering.
	 */
	public static void getFriendsList() {
		friendsList = new ArrayList<String>();
		try {
			String resultStr = facebook.request("me/friends");
			JSONObject jobj = new JSONObject(resultStr);
			JSONArray jarr = jobj.getJSONArray("data");
			int friendsCnt = jarr.length();
			friendsList.clear();
			friendsList.add(FacebookInfo.FACEBOOK_ID);
			for(int i=0; i<friendsCnt; i++) {
				String id = null;
				jobj = (JSONObject)jarr.get(i);
				id = jobj.getString("id");
				friendsList.add(id);
			}
			Log.d("myDebug", friendsCnt + " friends' id loaded.");
		} catch (Exception e) {
			Log.e("myDebug", e.toString());
		}
	}
	
	/**
	 * @param id : writer's id
	 * @return : true if id is user's friends, else false
	 */
	public static boolean isFriend(String id) {
		for(String fid : friendsList) {
			if(fid.equals(id))
				return true;
		}
		return false;
	}
	
	
}
