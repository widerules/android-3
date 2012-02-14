package kr.co.Project_UI;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class SecondTab_2 extends Activity {
	Button prevBtn;
	String [] name = { "한식", "중식", "일식", "양식", "커피", "패스트푸드" };
	Spinner spinVw;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.secondtab_2_layout);
		prevBtn = (Button) findViewById(R.id.prevBtn);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, name);
		
		spinVw = (Spinner)findViewById(R.id.spinVw);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		spinVw.setAdapter(adapter);
		
		spinVw.setSelection(0);

		prevBtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				SecondTab parent = ((SecondTab) getParent());
				parent.onBackPressed();
			}
		});
	}
}