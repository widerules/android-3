package kr.co.Project_UI;


import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class SecondTab_1 extends Activity implements OnClickListener{
	BoardHelper boardHelper = new BoardHelper();
	ArrayList<BoardVO> bList;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.secondtab_1_layout);
		
		bList = boardHelper.getList();
		
		BoardAdapter adapter = new BoardAdapter(this, R.layout.boardui , bList);
		ListView firstListView = (ListView)findViewById(R.id.list_view1);
		firstListView.setAdapter(adapter);
		Button nextBtn = (Button)findViewById(R.id.nextBtn);
		nextBtn.setOnClickListener(this);
		
		firstListView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View v, int arg2,
					long arg3) {
				Intent intent = new Intent(SecondTab_1.this, SecondTab_3.class);
				v = SecondTab.SecondTabHGroup.getLocalActivityManager()
						.startActivity("SecondTab_3", intent
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView();
				SecondTab.SecondTabHGroup.replaceView(v);
			}
		});
	}
	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.nextBtn:
			Intent intent = new Intent(SecondTab_1.this, SecondTab_2.class);
    		v = SecondTab.SecondTabHGroup.getLocalActivityManager()
    						.startActivity("SecondTab_2", intent
    						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView();
    		
    		SecondTab.SecondTabHGroup.replaceView(v);
		}
	}
}