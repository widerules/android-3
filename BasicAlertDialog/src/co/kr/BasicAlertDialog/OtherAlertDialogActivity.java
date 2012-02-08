package co.kr.BasicAlertDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class OtherAlertDialogActivity extends Activity {
	
	private final static int ALERT_TYPE_CONFIRM = 1;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.other);
    }
    public void close(View view) {
    	Toast.makeText(getApplicationContext(), "메시지 창을 닫습니다.", Toast.LENGTH_LONG).show();
    	finish();
    }
    
}