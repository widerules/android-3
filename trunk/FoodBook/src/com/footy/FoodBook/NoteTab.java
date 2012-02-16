package com.footy.FoodBook;

import java.io.File;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class NoteTab extends ListActivity implements OnClickListener {
	
	LinearLayout notetab1;
	LinearLayout notetab2;
	LinearLayout notetab3;
	LinearLayout notetab4;
	
	ImageButton writeBtn, prevBtn, cameraBtn;
	
	private static final int PICK_FROM_CAMERA = 0;
	private static final int PICK_FROM_ALBUM = 1;
	private static final int CROP_FROM_CAMERA = 2;
	
	Uri mImageCaptureUri;
	ImageView mPhotoImageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notetab);
		
		notetab1 = (LinearLayout)findViewById(R.id.notetab1);
		notetab2 = (LinearLayout)findViewById(R.id.notetab2);
		notetab3 = (LinearLayout)findViewById(R.id.notetab3);
		
		writeBtn = (ImageButton)findViewById(R.id.writeBtn);
		writeBtn.setOnClickListener(this);
		prevBtn = (ImageButton)findViewById(R.id.prevBtn);
		prevBtn.setOnClickListener(this);
		cameraBtn = (ImageButton)findViewById(R.id.cameraBtn);
		cameraBtn.setOnClickListener(this);
		mPhotoImageView = (ImageView) findViewById(R.id.cameraImage);
		
	}
	
	private void doTakePhotoAction() {

		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		String folder = "ScreenCapture";
		String url = "tmp_" + String.valueOf(System.currentTimeMillis())
				+ ".jpg";
		mImageCaptureUri = Uri.fromFile(new File(Environment
				.getExternalStorageDirectory(), folder + url));

		intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
				mImageCaptureUri);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, PICK_FROM_CAMERA);
	}

	private void doTakeAlbumAction() {
		// 앨범 호출
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
		startActivityForResult(intent, PICK_FROM_ALBUM);
		Log.d("mycamera","앨범");
	}
	
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK) {
			return;
		}

		switch (requestCode) {
		case CROP_FROM_CAMERA:
			final Bundle extras = data.getExtras();

			if (extras != null) {
				Bitmap photo = extras.getParcelable("data");
				mPhotoImageView.setImageBitmap(photo);
				Log.d("test","왔당");
				
			}

			File f = new File(mImageCaptureUri.getPath());
			if (f.exists()) {
				f.delete();
			}

			break;
		

		case PICK_FROM_ALBUM: 

			Log.d("mycamera","결과 직전");
			mImageCaptureUri = data.getData();
			mPhotoImageView.setImageBitmap(null);
		

		case PICK_FROM_CAMERA: 

			Intent intent = new Intent("com.android.camera.action.CROP");
			intent.setDataAndType(mImageCaptureUri, "image/*");

//			intent.putExtra("outputX", 245);
//			intent.putExtra("outputY", 100);
			intent.putExtra("aspectX", 1);
			intent.putExtra("aspectY", 1);
			intent.putExtra("scale", true);
			intent.putExtra("return-data", true);
			startActivityForResult(intent, CROP_FROM_CAMERA);

			break;
		
		}
		notetab2.setVisibility(View.VISIBLE);
	}

	@Override
	public void onClick(View v) {
		notetab1.setVisibility(View.GONE);
		notetab2.setVisibility(View.GONE);
		notetab3.setVisibility(View.GONE);
		switch(v.getId()) {
		case R.id.writeBtn:
			notetab2.setVisibility(View.VISIBLE);
			break;
		case R.id.prevBtn:
			notetab1.setVisibility(View.VISIBLE);
			break;
		case R.id.cameraBtn:
			
			DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					doTakePhotoAction();
				}
			};
			DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					doTakeAlbumAction();
					
				}

			};

			DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			};
			new AlertDialog.Builder(getParent()).setTitle("이미지 선택")
					.setPositiveButton("사진촬영", cameraListener)
					.setNeutralButton("앨범선택", albumListener)
					.setNegativeButton("취소", cancelListener).show();
			break;
		}
	}
	
}
