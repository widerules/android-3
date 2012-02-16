package com.footy.FoodBook;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;

public class MainActivity extends TabActivity {
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
        tabSpec1.setIndicator("1");
//        Intent intent1 = new Intent(getApplicationContext(), tab1.class);
//        tabSpec1.setContent(intent1);
        
        tabSpec2 = tabHost.newTabSpec("tab2");
        tabSpec2.setIndicator("2");
//        Intent intent2 = new Intent(getApplicationContext(), tab2.class);
//        tabSpec2.setContent(intent2);
        
        tabSpec3 = tabHost.newTabSpec("tab3");
        tabSpec3.setIndicator("3");
//        Intent intent3 = new Intent(getApplicationContext(), tab3.class);
//        tabSpec3.setContent(intent3);
        
        tabSpec4 = tabHost.newTabSpec("tab4");
        tabSpec4.setIndicator("4");
//        Intent intent4 = new Intent(getApplicationContext(), tab4.class);
//        tabSpec4.setContent(intent4);
        
        tabHost.addTab(tabSpec1);
        tabHost.addTab(tabSpec2);
        tabHost.addTab(tabSpec3);
        tabHost.addTab(tabSpec4);
        
        tabHost.setCurrentTab(0);
        
    }
}
