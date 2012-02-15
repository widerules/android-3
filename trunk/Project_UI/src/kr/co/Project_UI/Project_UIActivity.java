package kr.co.Project_UI;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class Project_UIActivity extends TabActivity {
    /** Called when the activity is first created. */
	public TabHost tabHost = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        tabHost = (TabHost) findViewById(android.R.id.tabhost);
		
		tabHost.addTab(tabHost.newTabSpec("tab1")
				  .setIndicator("Main")
				  .setContent(new Intent(this, FirstTab.class)));
		tabHost.addTab(tabHost.newTabSpec("tab2")
				  .setIndicator("Ŀ�´�Ƽ")
				  .setContent(new Intent(this, SecondTab.class)));
		tabHost.addTab(tabHost.newTabSpec("tab3")
				  .setIndicator("����")
				  .setContent(new Intent(this, ThirdTab.class)));
		tabHost.addTab(tabHost.newTabSpec("tab4")
				.setIndicator("�ֺ�����")
				.setContent(new Intent(this, FourthTab.class)));
		tabHost.addTab(tabHost.newTabSpec("tab5")
				.setIndicator("ȯ�漳��")
				.setContent(new Intent(this, FifthTab.class)));
		
		tabHost.setCurrentTab(0);
		
    }
    
//    @Override
//    protected void onResume() {
//    	super.onResume();
//    	Log.d("myDebug", "logout...");
//    	Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//    	intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//    	startActivity(intent);
//    	finish();
//    }
}