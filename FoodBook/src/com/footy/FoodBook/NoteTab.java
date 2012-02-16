package com.footy.FoodBook;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class NoteTab extends ListActivity implements OnClickListener {
	
	LinearLayout notetab1;
	LinearLayout notetab2;
	LinearLayout notetab3;
	LinearLayout notetab4;
	
	Button writeBtn;
	Button prevBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notetab);
		
		notetab1 = (LinearLayout)findViewById(R.id.notetab1);
		notetab2 = (LinearLayout)findViewById(R.id.notetab2);
		notetab3 = (LinearLayout)findViewById(R.id.notetab3);
		
		writeBtn = (Button)findViewById(R.id.writeBtn);
		writeBtn.setOnClickListener(this);
		prevBtn = (Button)findViewById(R.id.prevBtn);
		prevBtn.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		notetab1.setVisibility(View.GONE);
		notetab2.setVisibility(View.GONE);
		notetab3.setVisibility(View.GONE);
		switch(v.getId()) {
		case R.id.writeBtn:
			notetab2.setVisibility(View.VISIBLE);
			break;
		case R.id.prevBtn:
			notetab1.setVisibility(View.VISIBLE);
			break;
		}
	}
	
}
