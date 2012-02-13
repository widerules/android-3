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
	
	// ���ο� Level�� Activity�� �߰��ϴ� ���
	public void replaceView(View view) {
		history.add(view);
		setContentView(view);
	}
	
	// Back Key�� �������� ��쿡 ���� ó��
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
	
	// Back Key�� ���� Event Handler
	@Override
	public void onBackPressed() {
		SecondTabHGroup.back();
		return ;
	}
}
