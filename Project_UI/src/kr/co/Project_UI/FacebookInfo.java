package kr.co.Project_UI;

import java.net.URL;

import com.facebook.android.Facebook;

public class FacebookInfo {
	
	public static final String FACEBOOK_APP_ID = "350552578299151";
	public static final String FACEBOOK_APP_SECRET = "2a3f119edc9d8e19f1e9e73624667ad5";
	public static String FACEBOOK_ACCESS_TOKEN = "";
	public static String FACEBOOK_PROFILE_IMG = null;
	public static String FACEBOOK_NAME = "";
	public static String FACEBOOK_ID = "";
	
	public static boolean isLogin = false;
	public static Facebook facebook;
	
	public static Facebook getInstance() {
		return facebook;
	}
	
}
