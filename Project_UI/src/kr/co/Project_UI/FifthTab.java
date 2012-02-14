package kr.co.Project_UI;

import java.io.IOException;
import java.net.MalformedURLException;

import util.ImageDownloader;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.facebook.android.Facebook;

public class FifthTab extends Activity implements OnClickListener {
	ImageView profile = null;
	Button logoutBtn = null;
	Facebook facebook = FacebookInfo.getInstance();
	SharedPreferences pref = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fifthtab_1_layout);
		
		profile = (ImageView)findViewById(R.id.profile);
		logoutBtn = (Button)findViewById(R.id.logoutBtn);
		logoutBtn.setOnClickListener(this);
		
		Log.d("myDebug", FacebookInfo.FACEBOOK_PROFILE_IMG);
		ImageDownloader imageDownloader = new ImageDownloader();
		imageDownloader.download(FacebookInfo.FACEBOOK_PROFILE_IMG, profile);
		
	}

	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.logoutBtn :
			logout();
			Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
			startActivity(intent);
			finish();
			break;
		}
	}
	
	private void logout() {
		try {
			facebook.logout(getApplicationContext());
			pref = getSharedPreferences("access_token", Activity.MODE_PRIVATE);
			Editor editor = (Editor)pref.edit(); 
			editor.clear();
			editor.commit();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
