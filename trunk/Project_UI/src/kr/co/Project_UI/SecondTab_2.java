package kr.co.Project_UI;

import java.io.File;
import java.io.FileNotFoundException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class SecondTab_2 extends Activity implements OnClickListener {
	BoardHelper boardHelper = new BoardHelper();
	Button prevBtn;
	ImageButton camera;
	Button saveBtn;
	EditText title;
	EditText content;
	
	private static final int PICK_FROM_CAMERA = 0;
	private static final int PICK_FROM_ALBUM = 1;
	private static final int CROP_FROM_CAMERA = 2;
	
	private Uri mImageCaptureUri;
	private ImageView mPhotoImageView;
	
	String [] name = { "한식", "중식", "일식", "양식", "커피", "패스트푸드" };
	Spinner spinVw;

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		Intent intent = getIntent();
		
		if(intent != null) {
			Uri photoUri = PhotoInfo.getPhotoInfo().getUri();
			if(photoUri != null) {
				Bitmap bitmap = null;
				try {
					bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(photoUri), null, null);
					Log.d("photo", photoUri + "");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
		
				mPhotoImageView.setImageBitmap(bitmap);
				mPhotoImageView.invalidate();
				PhotoInfo.getPhotoInfo().setUri(null);
				
			}
		}
	}

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
		mPhotoImageView = (ImageView) findViewById(R.id.image);
	}
	
	private void doTakePhotoAction() {

		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		String url = "tmp_" + String.valueOf(System.currentTimeMillis())
				+ ".jpg";
		mImageCaptureUri = Uri.fromFile(new File(Environment
				.getExternalStorageDirectory(), url));

		intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
				mImageCaptureUri);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, PICK_FROM_CAMERA);
	}

	private void doTakeAlbumAction() {
		Intent intent = new Intent(getApplicationContext(), PhotoSelectionActivity.class);
		startActivityForResult(intent, PICK_FROM_ALBUM);		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if (resultCode != RESULT_OK) {
			return;
		}

		switch (requestCode) {
		case CROP_FROM_CAMERA: {
			final Bundle extras = data.getExtras();

			if (extras != null) {
				Bitmap photo = extras.getParcelable("data");
				
				mPhotoImageView.setImageBitmap(photo);
			}

			File f = new File(mImageCaptureUri.getPath());
			if (f.exists()) {
				f.delete();
			}

			break;
		}

		case PICK_FROM_ALBUM: {
			
			Log.d("mycamera","결과 직전");
			mImageCaptureUri = data.getData();
			mPhotoImageView.setImageURI(mImageCaptureUri);
		}

		case PICK_FROM_CAMERA: {
			Intent intent = new Intent("com.android.camera.action.CROP");
			intent.setDataAndType(mImageCaptureUri, "image/*");

			intent.putExtra("outputX", 245);
			intent.putExtra("outputY", 100);
			intent.putExtra("aspectX", 1);
			intent.putExtra("aspectY", 1);
			intent.putExtra("scale", true);
			intent.putExtra("return-data", true);
			getParent().startActivityForResult(intent, CROP_FROM_CAMERA);

			break;
		}
		}
	}
	

	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.prevBtn :
			SecondTab parent = ((SecondTab) getParent());
			parent.onBackPressed();
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
			new AlertDialog.Builder(getParent()).setTitle("업로드할 이미지 선택")
					.setPositiveButton("사진촬영", cameraListener)
					.setNeutralButton("앨범선택", albumListener)
					.setNegativeButton("취소", cancelListener).show();
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