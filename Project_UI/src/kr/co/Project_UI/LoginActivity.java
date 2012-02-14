package kr.co.Project_UI;

import java.net.URL;

import org.json.JSONObject;

import util.ImageDownloader;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;

public class LoginActivity extends Activity {
	Facebook facebook = null;

	SharedPreferences pref = null;
	String permissions[] = {"user_location", "friends_location", "user_work_history", "friends_work_history",
			"user_education_history", "friends_education_history"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		ImageView testImg = (ImageView)findViewById(R.id.testImg);
		ImageDownloader imageDownloader = new ImageDownloader();
		imageDownloader.download("http://graph.facebook.com/100002183492586/picture?type=square", testImg);
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
					FacebookInfo.FACEBOOK_PROFILE_IMG = "http://graph.facebook.com/" + FacebookInfo.FACEBOOK_ID + "/picture?type=square";
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
		Intent intent = new Intent(getApplicationContext(), Project_UIActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
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
				FacebookInfo.FACEBOOK_PROFILE_IMG = "http://graph.facebook.com/" + FacebookInfo.FACEBOOK_ID + "/picture?type=square";
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
