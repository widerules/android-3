package kr.co.Project_UI;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class SecondTab_1 extends Activity {
	ArrayList<BoardVO> bList;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.secondtab_1_layout);
		
		bList = new ArrayList<BoardVO>();
		bList.add(new BoardVO(null, "윤영석1", null, "첫번쨰 글", null, 4, null));
		bList.add(new BoardVO(null, "윤영석2", null, "첫번쨰 글", null, 4, null));
		bList.add(new BoardVO(null, "윤영석3", null, "첫번쨰 글", null, 4, null));
		BoardAdapter adapter = new BoardAdapter(this, R.layout.boardui , bList);
		ListView firstListView = (ListView)findViewById(R.id.list_view1);
		firstListView.setAdapter(adapter);
		Log.d("mydebug", "setadapter");
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