/* 
 * NMapCalloutBasicOverlay.java $version 2010. 1. 1
 * 
 * Copyright 2010 NHN Corp. All rights Reserved. 
 * NHN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms. 
 */

package kr.co.NaverMap;

import kr.co.Project_UI.R;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.nhn.android.maps.NMapOverlay;
import com.nhn.android.maps.NMapOverlayItem;
import com.nhn.android.maps.NMapView;
import com.nhn.android.mapviewer.overlay.NMapCalloutOverlay;

/**
 * Basic callout overlay
 * 
 * @author kyjkim 
 */
public class NMapCalloutBasicOverlay extends NMapCalloutOverlay {

	private static final int CALLOUT_TEXT_PADDING_X = 10;
	private static final int CALLOUT_TEXT_PADDING_Y = 10;
	private static final int CALLOUT_TAG_WIDTH = 10;
	private static final int CALLOUT_TAG_HEIGHT = 10;
	private static final int CALLOUT_ROUND_RADIUS_X = 5;
	private static final int CALLOUT_ROUND_RADIUS_Y = 5;

	private final Paint mInnerPaint, mBorderPaint, mTextPaint;
	private final Path mPath;
	private float mOffsetX, mOffsetY;

	Rect myRect;
	
	private Resources resources;
	
	public NMapCalloutBasicOverlay(NMapOverlay itemOverlay, NMapOverlayItem item, Rect itemBounds, Resources resources) {
		super(itemOverlay, item, itemBounds);
		this.resources = resources;
		
		myRect = new Rect(100, 100, (int)mTempRectF.right+100, (int)mTempRectF.bottom);
		
		mInnerPaint = new Paint();
		mInnerPaint.setARGB(225, 75, 75, 75); //gray
		mInnerPaint.setAntiAlias(true);

		mBorderPaint = new Paint();
		mBorderPaint.setARGB(255, 255, 255, 255);
		mBorderPaint.setAntiAlias(true);
		mBorderPaint.setStyle(Style.STROKE);
		mBorderPaint.setStrokeWidth(2);

		mTextPaint = new Paint();
		mTextPaint.setARGB(255, 10, 10, 255);
		mTextPaint.setAntiAlias(true);

		mPath = new Path();
		mPath.setFillType(Path.FillType.WINDING);
	}

	@Override
	protected boolean isTitleTruncated() {
		Log.d("naverMap", "isTitleTruncated");
		return false;
	}

	@Override
	protected int getMarginX() {
		Log.d("naverMap", "getMarginX");
		
		return 10;
	}

	@Override
	protected Rect getBounds(NMapView mapView) {
		Log.d("naverMap", "getBounds");

		adjustTextBounds(mapView);

		mTempRect.set((int)mTempRectF.left, (int)mTempRectF.top, (int)mTempRectF.right, (int)mTempRectF.bottom);
//		mTempRect.set(200, 200, 300, 300);
//		mTempRect.union(mTempPoint.x, mTempPoint.y);
		myRect.union(mTempPoint.x, mTempPoint.y);
//		return mTempRect;
		return myRect;
	}

	@Override
	protected PointF getSclaingPivot() {
		Log.d("naverMap", "getSclaingPivot");
		PointF pivot = new PointF();

		pivot.x = mTempRectF.centerX();
		pivot.y = mTempRectF.bottom + CALLOUT_TAG_HEIGHT;

		return pivot;
	}

	@Override
	protected void drawCallout(Canvas canvas, NMapView mapView, boolean shadow, long when) {
		

		Log.d("naverMap", "drawCallout");
		adjustTextBounds(mapView);

		stepAnimations(canvas, mapView, when);

		// Draw inner info window
		canvas.drawRoundRect(mTempRectF, CALLOUT_ROUND_RADIUS_X, CALLOUT_ROUND_RADIUS_Y, mInnerPaint);

		//이미지 붙여넣기
		Bitmap bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_map_arrive_over);
		Paint paint = new Paint();
		canvas.drawBitmap(bitmap, mOffsetX+100, mOffsetY + 100, paint);
		
		// Draw border for info window
		canvas.drawRoundRect(mTempRectF, CALLOUT_ROUND_RADIUS_X, CALLOUT_ROUND_RADIUS_Y, mBorderPaint);
		canvas.getMatrix();
		

		// Draw bottom tag
//		if (CALLOUT_TAG_WIDTH > 0 && CALLOUT_TAG_HEIGHT > 0) {
//			float x = mTempRectF.centerX();
//			float y = mTempRectF.bottom;
//
//			Path path = mPath;
//			path.reset();
//
//			path.moveTo(x - CALLOUT_TAG_WIDTH, y);
//			path.lineTo(x, y + CALLOUT_TAG_HEIGHT);
//			path.lineTo(x + CALLOUT_TAG_WIDTH, y);
//			path.close();
//
//			canvas.drawPath(path, mInnerPaint);
//			canvas.drawPath(path, mBorderPaint);
//		}

		String title = "안드로이드 과정 dididididididididid<br />안드로이드 과정 dididididididididid";
		
		//  Draw title
//		canvas.drawText(title, mOffsetX + 10, mOffsetY + 10, mTextPaint);
//		canvas.drawText(mOverlayItem.getTitle(), mOffsetX, mOffsetY, mTextPaint);
	}

	/* Internal Functions */

	private void adjustTextBounds(NMapView mapView) {

		//  Determine the screen coordinates of the selected MapLocation
		mapView.getMapProjection().toPixels(mOverlayItem.getPointInUtmk(), mTempPoint);

		final String title = mOverlayItem.getTitle();
		mTextPaint.getTextBounds(title, 0, title.length(), mTempRect);

		//  Setup the callout with the appropriate size & location
		mTempRectF.set(myRect);
//		mTempRectF.set(mTempRect);
		mTempRectF.inset(-CALLOUT_TEXT_PADDING_X, -CALLOUT_TEXT_PADDING_Y);
		mOffsetX = mTempPoint.x - mTempRect.width() / 2;
		mOffsetY = mTempPoint.y - mTempRect.height() - mItemBounds.height() - CALLOUT_TEXT_PADDING_Y;
		mTempRectF.offset(mOffsetX, mOffsetY);
	}

	@Override
	protected Drawable getDrawable(int rank, int itemState) {
		// TODO Auto-generated method stub
		return null;
	}
}
