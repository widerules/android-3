package kr.co.Project_UI;

import android.net.Uri;

public class PhotoInfo {
	
	public static PhotoInfo photoInfo = new PhotoInfo();
	private Uri mAlbumPhotoUri;
	private PhotoInfo() {
	}
	public static PhotoInfo getPhotoInfo() {
		return photoInfo;
	}
	public void setUri(Uri mAlbumPhotoUri) {
		this.mAlbumPhotoUri = mAlbumPhotoUri;
	}
	public Uri getUri() {
		return mAlbumPhotoUri;
	}
}
