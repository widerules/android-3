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
		
		Log.d("myDebug", "���ͳ�Ʈ : " + Constants.FILTER_BOARD);
		Log.d("myDebug", "����Ǫ����Ƽ: " + Constants.FILTER_FOODPARTY);
		
		boardCBox.setChecked(Constants.FILTER_BOARD);
		partyCBox.setChecked(Constants.FILTER_FOODPARTY);
		boardCBox.setOnCheckedChangeListener(this);
		partyCBox.setOnCheckedChangeListener(this);
	
		if(Constants.FILTER_BOARD) {
			filterBoardText.setText("ģ���� ���� ��Ʈ�� ǥ��");
		} else {
			filterBoardText.setText("��� ���� ��Ʈ ǥ��");
		}
		if(Constants.FILTER_FOODPARTY) {
			filterPartyText.setText("ģ���� ����� Ǫ�� ��Ƽ�� ǥ��");
		} else {
			filterPartyText.setText("��� Ǫ�� ��Ƽ ǥ��");
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
				filterBoardText.setText("ģ���� ���� ��Ʈ�� ǥ��");
				editor.putBoolean("filterBoard", true);
			} else {
				filterBoardText.setText("��� ���� ��Ʈ ǥ��");
				editor.putBoolean("filterBoard", false);
			}
			break;
		case R.id.partyCBox :
			Constants.FILTER_FOODPARTY = isChecked;
			if(Constants.FILTER_FOODPARTY) {
				filterPartyText.setText("ģ���� ����� Ǫ�� ��Ƽ�� ǥ��");
				editor.putBoolean("filterFoodParty", true);
			} else {
				filterPartyText.setText("��� Ǫ�� ��Ƽ ǥ��");
				editor.putBoolean("filterFoodParty", false);
			}
			break;
		}
		editor.commit();
	}
}
