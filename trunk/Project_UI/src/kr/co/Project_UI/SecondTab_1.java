package kr.co.Project_UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class SecondTab_1 extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.secondtab_1_layout);
		
		String[] firstArray = getResources().getStringArray(R.array.test);
		ListView firstListView = (ListView) findViewById(R.id.list_view1);
		ArrayAdapter<String> firstAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, firstArray);
		firstListView.setAdapter(firstAdapter);
		Button nextBtn = (Button)findViewById(R.id.nextBtn);
	
		nextBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent(SecondTab_1.this, SecondTab_2.class);
        		view = SecondTab.SecondTabHGroup.getLocalActivityManager()
        						.startActivity("FirstTab_2", intent
        						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView();
        		
        		SecondTab.SecondTabHGroup.replaceView(view);
			}
		});
	}
}