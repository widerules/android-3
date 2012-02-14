package kr.co.Project_UI;

import android.app.Application;

import com.facebook.android.Facebook;


public class FacebookHelper extends Application {
	
	Facebook facebook = null;
	
	public FacebookHelper() {
		facebook = FacebookInfo.getInstance();
	}
		
	

	
}
