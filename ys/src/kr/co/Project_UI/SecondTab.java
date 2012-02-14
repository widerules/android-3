package kr.co.Project_UI;

import java.util.ArrayList;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SecondTab extends ActivityGroup {
	public static SecondTab SecondTabHGroup;
	private ArrayList<View> history;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		history = new ArrayList<View>();
		SecondTabHGroup = this;
		
		Intent intent = new Intent(SecondTab.this, SecondTab_1.class);
		View view = getLocalActivityManager().startActivity("SecondTab_1", intent
										.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView();
		replaceView(view);
	}
	
	// 새로운 Level의 Activity를 추가하는 경우
	public void replaceView(View view) {
		history.add(view);
		setContentView(view);
	}
	
	// Back Key가 눌러졌을 경우에 대한 처리
	public void back() {
		if(history.size() > 0) {
			history.remove(history.size()-1);
			if(history.size() ==  0)
				finish();
			else 
				setContentView(history.get(history.size()-1));
		} 
		else
		{
			finish();
		}
	}
	
	// Back Key에 대한 Event Handler
	@Override
	public void onBackPressed() {
		SecondTabHGroup.back();
		return ;
	}
}
