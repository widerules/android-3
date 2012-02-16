package com.footy.FoodBook;

import java.io.File;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.footy.Board.BoardAdapter;
import com.footy.Board.BoardHelper;
import com.footy.Board.BoardVO;
import com.footy.Facebook.FacebookInfo;
import com.footy.Util.ImageDownloader;

public class NoteTab extends ListActivity implements OnClickListener, OnItemClickListener {
	
	BoardHelper boardHelper = new BoardHelper();
	ImageDownloader imageDownloader = new ImageDownloader();
	
	LinearLayout notetab1, notetab2, notetab3, notetab4;
	ImageButton writeBtn, prevBtn, prevBtn2, cameraBtn;
	EditText titleEdit, contentEdit, address;
	
	private static final int PICK_FROM_CAMERA = 0;
	private static final int PICK_FROM_ALBUM = 1;
	private static final int CROP_FROM_CAMERA = 2;
	
	Uri mImageCaptureUri;
	ImageView mPhotoImageView;
	ImageButton saveBtn;
	
	String [] name = { "한식", "중식", "일식", "양식", "커피", "패스트푸드" };
	Spinner spinVw;
	
	ListView listView;
	BoardAdapter boardAdapter;
	ArrayList<BoardVO> bList;
	
	TextView titleText, contentText;
	ImageView image;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notetab);
		
		notetab1 = (LinearLayout)findViewById(R.id.notetab1);
		notetab2 = (LinearLayout)findViewById(R.id.notetab2);
		notetab3 = (LinearLayout)findViewById(R.id.notetab3);
//		notetab4 = (LinearLayout)findViewById(R.id.notetab4);
		
		titleEdit = (EditText)findViewById(R.id.titleEdit);
		contentEdit = (EditText)findViewById(R.id.content);
		
		writeBtn = (ImageButton)findViewById(R.id.writeBtn);
		writeBtn.setOnClickListener(this);
		prevBtn = (ImageButton)findViewById(R.id.prevBtn);
		prevBtn2 = (ImageButton)findViewById(R.id.prevBtn2);
		prevBtn.setOnClickListener(this);
		prevBtn2.setOnClickListener(this);
		cameraBtn = (ImageButton)findViewById(R.id.cameraBtn);
		cameraBtn.setOnClickListener(this);
		mPhotoImageView = (ImageView) findViewById(R.id.cameraImage);
		saveBtn = (ImageButton)findViewById(R.id.saveBtn);
		saveBtn.setOnClickListener(this);
		
		ArrayAdapter<String> spinAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, name);
		
		spinVw = (Spinner)findViewById(R.id.spinVw);
		spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinVw.setPrompt("음식의 종류를 선택하세요.");
		spinVw.setAdapter(spinAdapter);
		spinVw.setSelection(0);
		
		bList = boardHelper.getList();
		listView = (ListView)findViewById(android.R.id.list);
		listView.setOnItemClickListener(this);
		boardAdapter = new BoardAdapter(getApplicationContext(), R.layout.boardui, bList);
		listView.setAdapter(boardAdapter);
		
		titleText = (TextView)findViewById(R.id.title);
		contentText = (TextView)findViewById(R.id.content);
		image = (ImageView)findViewById(R.id.image);
		
	}
	@Override
	protected void onResume() {
		super.onResume();
		bList = boardHelper.getList();
		boardAdapter = new BoardAdapter(getApplicationContext(), R.layout.boardui, bList);
		listView.setAdapter(boardAdapter);
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
		Log.d("test", mImageCaptureUri.toString());
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
		case R.id.prevBtn2:
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
		case R.id.saveBtn:
			if(boardHelper.writePost(new BoardVO(0, FacebookInfo.FACEBOOK_NAME, FacebookInfo.FACEBOOK_ID, titleEdit.getText().toString().trim(),
					name[spinVw.getSelectedItemPosition()], 0, 0, contentEdit.getText().toString().trim(), getRealImagePath(mImageCaptureUri), 0, null))) {
				Toast.makeText(getApplicationContext(), "등록 성공", 2000).show();
				clearLayout2();
			}
			else {
				Toast.makeText(getApplicationContext(), "등록 실패", 2000).show();
			}
			break;
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		BoardVO boardVO = boardHelper.getContent(bList.get(position).getPostNo());
		notetab1.setVisibility(View.GONE);
		notetab3.setVisibility(View.VISIBLE);
		
		titleText.setText(boardVO.getTitle());
		contentText.setText(boardVO.getContent());
		imageDownloader.download(boardVO.getImgUrl(), image);
	}
	
	private void clearLayout2() {
		titleEdit.setText("");
		spinVw.setSelection(0);
//		address.setText("");
		contentEdit.setText("");
		mPhotoImageView.setImageBitmap(null);
		mImageCaptureUri = null;
	}
	
	/**
	 * URI로 부터 실제 파일 경로를 가져온다.
	 * @param uriPath URI : URI 경로
	 * @return String : 실제 파일 경로
	 */
	public String getRealImagePath(Uri uriPath)
	{
		String []proj = {MediaStore.Images.Media.DATA};
		Cursor cursor = managedQuery (uriPath, proj, null, null, null);
		int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

		cursor.moveToFirst();

		String path = cursor.getString(index);
		path = path.substring(5);

		return path;
	}
	
	
}
