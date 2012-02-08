package co.kr.BasicAlertDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class BasicAlertDialogActivity extends Activity {
	
	private final static int ALERT_TYPE_CONFIRM = 1;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    public void myDialog(View view) {
    	switch (view.getId()) {
		case R.id.btn1:
			showDialog(ALERT_TYPE_CONFIRM);
			break;
		case R.id.btn2:
			Intent intent = new Intent(this, OtherAlertDialogActivity.class);
			startActivity(intent);
			break;
		}
    }
	@Override
	protected Dialog onCreateDialog(int id) {
		AlertDialog dialog = null;
		AlertDialog.Builder alertBld = new AlertDialog.Builder(this);
		alertBld.setIcon(R.drawable.ic_launcher);
		alertBld.setTitle("확인창");
		alertBld.setMessage("안녕하십니까");
		alertBld.setPositiveButton("예", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(getApplicationContext(), "예를 눌렀네요", Toast.LENGTH_SHORT).show();
			}
		});
		alertBld.setNeutralButton("그저그렇다", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(getApplicationContext(), "그저그렇다를 눌렀네요", Toast.LENGTH_SHORT).show();
			}
		}); 
		alertBld.setNegativeButton("안좋다", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(getApplicationContext(), "안좋다를 눌렀네요", Toast.LENGTH_SHORT).show();
			}
		}); 
		dialog = alertBld.create();
		return dialog;
	}
}