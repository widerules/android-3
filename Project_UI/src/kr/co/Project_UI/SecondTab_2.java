package kr.co.Project_UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SecondTab_2 extends Activity implements OnClickListener {
	BoardHelper boardHelper = new BoardHelper();
	Button prevBtn;
	ImageButton camera;
	Button saveBtn;
	EditText title;
	EditText content;
	String [] name = { "한식", "중식", "일식", "양식", "커피", "패스트푸드" };
	Spinner spinVw;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View pView = LayoutInflater.from(this.getParent()).inflate(R.layout.secondtab_2_layout, null);
		setContentView(pView);
		
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, name);
		
		spinVw = (Spinner)findViewById(R.id.spinVw);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinVw.setPrompt("음식의 종류를 선택하세요.");
		spinVw.setAdapter(adapter);
		spinVw.setSelection(0);

		prevBtn = (Button)findViewById(R.id.prevBtn);
		prevBtn.setOnClickListener(this);
		saveBtn = (Button)findViewById(R.id.saveBtn);
		saveBtn.setOnClickListener(this);
		camera = (ImageButton)findViewById(R.id.cameraBtn);
		camera.setOnClickListener(this);
		
		title = (EditText)findViewById(R.id.title);
		content = (EditText)findViewById(R.id.content);
	}

	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.prevBtn :
			SecondTab parent = ((SecondTab) getParent());
			parent.onBackPressed();
			break;
		case R.id.cameraBtn:
			Intent i = new Intent(SecondTab_2.this, Camera.class);
			startActivity(i);
			break;
		case R.id.saveBtn :
			if(boardHelper.writePost(new BoardVO(0, "장상윤", "777777", 
					title.getText().toString(), content.getText().toString(), 0, null)))
				Toast.makeText(getApplicationContext(), "등록 성공", 2000).show();
			else {
				Toast.makeText(getApplicationContext(), "등록 실패", 2000).show();
			}
			finish();
			break;
		}
		
	}
}