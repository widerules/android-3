package kr.co.Project_UI;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Application;
import android.util.Log;

import com.facebook.android.Facebook;

public class FacebookHelper extends Application {
	
	private static Facebook facebook = null;
	private static ArrayList<String> friendList;
	
	public FacebookHelper() {
		facebook = FacebookInfo.getInstance();
	}
	
	/**
	 * Get current user's Facebook friends list.
	 * It must be called once for filtering.
	 */
	public void getFriendsList() {
		friendList = new ArrayList<String>();
		try {
			String resultStr = facebook.request("me/friends");
			JSONObject jobj = new JSONObject(resultStr);
			JSONArray jarr = jobj.getJSONArray("data");
			int friendsCnt = jarr.length();
			friendList.clear();
			for(int i=0; i<friendsCnt; i++) {
				String id = null;
				jobj = (JSONObject)jarr.get(i);
				id = jobj.getString("id");
				friendList.add(id);
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
	public boolean isFriend(String id) {
		for(String fid : friendList) {
			if(fid.equals(id))
				return true;
		}
		return false;
	}
	
	
}
