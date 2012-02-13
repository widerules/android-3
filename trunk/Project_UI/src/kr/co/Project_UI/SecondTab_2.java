package kr.co.Project_UI;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SecondTab_2 extends Activity{
	Button prevBtn;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.secondtab_2_layout);
		prevBtn = (Button)findViewById(R.id.prevBtn);
		
		prevBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SecondTab parent = ((SecondTab)getParent());
				parent.onBackPressed();
			}
		});
	}
	
}