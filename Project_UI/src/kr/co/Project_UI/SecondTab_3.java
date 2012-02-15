package kr.co.Project_UI;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SecondTab_3 extends Activity implements OnClickListener{
	Button prevBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.secondtab_3_layout);
		prevBtn = (Button)findViewById(R.id.prevBtn);
		prevBtn.setOnClickListener(this);

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
