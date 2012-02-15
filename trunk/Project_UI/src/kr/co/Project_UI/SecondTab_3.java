package kr.co.Project_UI;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import util.ImageDownloader;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SecondTab_3 extends Activity implements OnClickListener{
	BoardVO boardVO = null;
	BoardHelper boardHelper = new BoardHelper();
	ImageDownloader imageDownloader = new ImageDownloader();
	int postNo;
	
	Button prevBtn;
	TextView title;
	TextView content;
	ImageView image;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.secondtab_3_layout);
		
		prevBtn = (Button)findViewById(R.id.prevBtn);
		prevBtn.setOnClickListener(this);
		
		Intent intent = new Intent(getIntent());
		postNo = intent.getIntExtra("postNo", -1);
		if(postNo != -1) {
			boardVO = boardHelper.getContent(postNo);
		}
		
		title = (TextView)findViewById(R.id.title);
		content = (TextView)findViewById(R.id.content);
		image = (ImageView)findViewById(R.id.image);
		
		title.setText(boardVO.getTitle());
		content.setText(boardVO.getContent());
		imageDownloader.download(boardVO.getImgUrl(), image);
	}
	
	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.prevBtn:
			SecondTab parent = ((SecondTab) getParent());
			parent.onBackPressed();
			break;
		}
	}
}
