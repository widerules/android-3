package com.footy.FoodBook;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;

public class MainActivity extends TabActivity implements OnTabChangeListener {
	TabHost tabHost = null;
	TabSpec tabSpec1 = null;
	TabSpec tabSpec2 = null;
	TabSpec tabSpec3 = null;
	TabSpec tabSpec4 = null;
	TabWidget tabWidget = null;

	TextView tabTitle = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		tabHost = getTabHost();
		tabSpec1 = tabHost.newTabSpec("tab1");
		tabSpec1.setIndicator("", getResources().getDrawable(R.drawable.home00));
		Intent intent1 = new Intent(getApplicationContext(), HomeTab.class);
		tabSpec1.setContent(intent1);

		tabSpec2 = tabHost.newTabSpec("tab2");
		tabSpec2.setIndicator("", getResources().getDrawable(R.drawable.comm00));
		Intent intent2 = new Intent(getApplicationContext(), NoteTab.class);
		tabSpec2.setContent(intent2);

		tabSpec3 = tabHost.newTabSpec("tab3");
		tabSpec3.setIndicator("", getResources().getDrawable(R.drawable.map00));
		Intent intent3 = new Intent(getApplicationContext(), MapTab.class);
		tabSpec3.setContent(intent3);

		tabSpec4 = tabHost.newTabSpec("tab4");
		tabSpec4.setIndicator("",
				getResources().getDrawable(R.drawable.config00));
		Intent intent4 = new Intent(getApplicationContext(), SettingsTab.class);
		tabSpec4.setContent(intent4);

		tabHost.addTab(tabSpec1);
		tabHost.addTab(tabSpec2);
		tabHost.addTab(tabSpec3);
		tabHost.addTab(tabSpec4);

		tabHost.setOnTabChangedListener(this);
		for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
			tabHost.getTabWidget().getChildAt(i)
					.setBackgroundColor(Color.parseColor("#6EBDFD"));
		}
		tabHost.getTabWidget().setCurrentTab(0);
		tabHost.getTabWidget().getChildAt(0)
				.setBackgroundColor(Color.parseColor("#b0c4de"));

	}

	public void onTabChanged(String tabId) {
		int menu_off[] = { R.drawable.home00, R.drawable.comm00,
				R.drawable.map00, R.drawable.config00 };
		int menu_on[] = { R.drawable.home01, R.drawable.comm01,
				R.drawable.map01, R.drawable.config01 };

		for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
			tabHost.getTabWidget().getChildAt(i)
					.setBackgroundColor(Color.parseColor("#6EBDFD"));
			ImageView iv = (ImageView) tabHost.getTabWidget().getChildAt(i)
					.findViewById(android.R.id.icon);
			iv.setImageDrawable(getResources().getDrawable(menu_off[i]));
		}
		tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab())
				.setBackgroundColor(Color.parseColor("#b0c4de"));
		ImageView ip = (ImageView) tabHost.getTabWidget()
				.getChildAt(tabHost.getCurrentTab())
				.findViewById(android.R.id.icon);
		ip.setImageDrawable(getResources().getDrawable(
				menu_on[tabHost.getCurrentTab()]));
	}

}
