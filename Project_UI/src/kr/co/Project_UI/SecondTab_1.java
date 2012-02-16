package kr.co.Project_UI;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class SecondTab_1 extends ListActivity implements OnClickListener{
	BoardHelper boardHelper = new BoardHelper();
	ListView listView;
	ArrayList<BoardVO> bList;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.secondtab_1_layout);
		
		bList = boardHelper.getList();
		
		BoardAdapter adapter = new BoardAdapter(this, R.layout.boardui , bList);
		listView = (ListView)findViewById(android.R.id.list);
		listView.setAdapter(adapter);
		Button nextBtn = (Button)findViewById(R.id.nextBtn);
		nextBtn.setOnClickListener(this);
		
	}
	@Override
	protected void onResume() {
		super.onResume();
		listView.invalidateViews();
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent intent = new Intent(SecondTab_1.this, SecondTab_3.class);
		intent.putExtra("postNo", bList.get(position).getPostNo());
		v = SecondTab.SecondTabHGroup.getLocalActivityManager()
				.startActivity("SecondTab_3", intent
				.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView();
		SecondTab.SecondTabHGroup.replaceView(v);
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