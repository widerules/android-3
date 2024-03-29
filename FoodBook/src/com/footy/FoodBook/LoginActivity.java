package com.footy.FoodBook;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.footy.Facebook.FacebookHelper;
import com.footy.Facebook.FacebookInfo;

public class LoginActivity extends Activity {
	Facebook facebook = null;

	SharedPreferences pref = null;
	String permissions[] = {"user_location", "friends_location", "user_work_history", "friends_work_history",
			"user_education_history", "friends_education_history"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		login();
	}
	
	private void login() {
		boolean errorFlag = true;

		pref = getSharedPreferences("access_token", Activity.MODE_PRIVATE);
        FacebookInfo.FACEBOOK_ACCESS_TOKEN = pref.getString("token", "");
        
    	try {
    		
			facebook = new Facebook(FacebookInfo.FACEBOOK_APP_ID);
			FacebookInfo.facebook = facebook;
			if(FacebookInfo.FACEBOOK_ACCESS_TOKEN != null && !FacebookInfo.FACEBOOK_ACCESS_TOKEN.equals("")) {
				facebook.setAccessToken(FacebookInfo.FACEBOOK_ACCESS_TOKEN);
//				if(jobj.getString("verified").equals("true")) {
				if(facebook.isSessionValid()) {
					JSONObject jobj = new JSONObject(facebook.request("me"));
					FacebookInfo.FACEBOOK_NAME = jobj.getString("name");
					FacebookInfo.FACEBOOK_ID = jobj.getString("id");
					FacebookInfo.FACEBOOK_PROFILE_IMG_SQUARE = "http://graph.facebook.com/" + FacebookInfo.FACEBOOK_ID + "/picture?type=square";
					FacebookInfo.FACEBOOK_PROFILE_IMG_NORMAL = "http://graph.facebook.com/" + FacebookInfo.FACEBOOK_ID + "/picture?type=normal";
					Log.d("myDebug", "loaded access_token is valid");
					errorFlag = false;
					startMain();
				}
			}
			if(errorFlag) {
				Log.d("myDebug", "loaded access_token is invalid, call authorize");
				facebook.authorize(this, permissions, Facebook.FORCE_DIALOG_AUTH, new AuthorizationListener());
//				mFacebook.authorize(this, new AuthorizationListener());
			}
		} catch (Exception e) {
			Log.e("myDebug", e.toString());
		}
	}
	
	private void startMain() {
		FacebookHelper.getFriendsList();
		Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		
		// 필터링 설정 불러오기
		pref = getSharedPreferences("config", Activity.MODE_PRIVATE);
		Editor editor = (Editor)pref.edit();
		Constants.FILTER_BOARD = pref.getBoolean("filterBoard", true);
		Constants.FILTER_FOODPARTY = pref.getBoolean("filterFoodParty", true);
		
		startActivity(intent);
		finish();
	}
	
	public class AuthorizationListener implements DialogListener {
		public void onComplete(Bundle values) {
			try {
				String resultStr = facebook.request("me");
				JSONObject jobj = new JSONObject(resultStr);

				FacebookInfo.FACEBOOK_NAME = jobj.getString("name");
				FacebookInfo.FACEBOOK_ID = jobj.getString("id");
				FacebookInfo.FACEBOOK_PROFILE_IMG_SQUARE = "http://graph.facebook.com/" + FacebookInfo.FACEBOOK_ID + "/picture?type=square";
				FacebookInfo.FACEBOOK_PROFILE_IMG_NORMAL = "http://graph.facebook.com/" + FacebookInfo.FACEBOOK_ID + "/picture?type=normal";
				FacebookInfo.FACEBOOK_ACCESS_TOKEN = facebook.getAccessToken();

				// SharedPreference에 ACCESS_TOKEN 저장
				Editor editor = (Editor)pref.edit(); 
				editor.putString("token", FacebookInfo.FACEBOOK_ACCESS_TOKEN);
				editor.commit();

			} catch (Exception e) {
				Log.e("myDebug", e.toString());
			}
			Log.d("myDebug", "Authorization complete : " + FacebookInfo.FACEBOOK_NAME);
			startMain();
		}
		public void onFacebookError(FacebookError e) {}
		public void onError(DialogError e) {}
		public void onCancel() {
			Toast.makeText(getApplicationContext(), "Facebook 로그인이 필요합니다", Toast.LENGTH_SHORT).show();
			finish();
		}
	}
}
