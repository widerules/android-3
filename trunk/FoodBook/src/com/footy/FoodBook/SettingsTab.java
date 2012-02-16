package com.footy.FoodBook;

import java.io.IOException;
import java.net.MalformedURLException;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.android.Facebook;
import com.footy.Facebook.FacebookInfo;
import com.footy.Util.ImageDownloader;

public class SettingsTab extends Activity implements OnClickListener, OnCheckedChangeListener {
	ImageView profile = null;
	ImageButton logoutBtn = null;
	Facebook facebook = FacebookInfo.getInstance();
	SharedPreferences pref = null;
	
	CheckBox boardCBox = null;
	CheckBox partyCBox = null;
	TextView filterBoardText = null;
	TextView filterPartyText = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settingstab);
		
		profile = (ImageView)findViewById(R.id.profile);
		logoutBtn = (ImageButton)findViewById(R.id.logoutBtn);
		logoutBtn.setOnClickListener(this);
		
		ImageDownloader imageDownloader = new ImageDownloader();
		imageDownloader.download(FacebookInfo.FACEBOOK_PROFILE_IMG_SQUARE, profile);
		
		filterBoardText = (TextView)findViewById(R.id.filterBoardText);
		filterPartyText = (TextView)findViewById(R.id.filterPartyText);
		boardCBox = (CheckBox)findViewById(R.id.boardCBox);
		partyCBox = (CheckBox)findViewById(R.id.partyCBox);
		
		Log.d("myDebug", "필터노트 : " + Constants.FILTER_BOARD);
		Log.d("myDebug", "필터푸드파티: " + Constants.FILTER_FOODPARTY);
		
		boardCBox.setChecked(Constants.FILTER_BOARD);
		partyCBox.setChecked(Constants.FILTER_FOODPARTY);
		boardCBox.setOnCheckedChangeListener(this);
		partyCBox.setOnCheckedChangeListener(this);
	
		if(Constants.FILTER_BOARD) {
			filterBoardText.setText("친구의 맛집 노트만 표시");
		} else {
			filterBoardText.setText("모든 맛집 노트 표시");
		}
		if(Constants.FILTER_FOODPARTY) {
			filterPartyText.setText("친구가 등록한 푸드 파티만 표시");
		} else {
			filterPartyText.setText("모든 푸드 파티 표시");
		}
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

	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		pref = getSharedPreferences("config", Activity.MODE_PRIVATE);
		Editor editor = (Editor)pref.edit();
		switch(buttonView.getId()) {
		case R.id.boardCBox :
			Constants.FILTER_BOARD = isChecked;
			if(Constants.FILTER_BOARD) {
				filterBoardText.setText("친구의 맛집 노트만 표시");
				editor.putBoolean("filterBoard", true);
			} else {
				filterBoardText.setText("모든 맛집 노트 표시");
				editor.putBoolean("filterBoard", false);
			}
			break;
		case R.id.partyCBox :
			Constants.FILTER_FOODPARTY = isChecked;
			if(Constants.FILTER_FOODPARTY) {
				filterPartyText.setText("친구가 등록한 푸드 파티만 표시");
				editor.putBoolean("filterFoodParty", true);
			} else {
				filterPartyText.setText("모든 푸드 파티 표시");
				editor.putBoolean("filterFoodParty", false);
			}
			break;
		}
		editor.commit();
	}
}
