package com.footy.FoodBook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class FoodBookActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);
        
        new Thread(new Runnable(){

			public void run() {
				// TODO Auto-generated method stub
				try{
					Thread.sleep(3000);				
				}catch(Throwable ex){
					ex.printStackTrace();
				}
				Intent i = new Intent(getApplicationContext(), LoginActivity.class);
				startActivity(i);
				finish();
			}
		}).start();
    }
}