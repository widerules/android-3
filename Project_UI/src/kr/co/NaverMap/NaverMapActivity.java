package kr.co.NaverMap;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import kr.co.Project_UI.R;
import kr.co.Project_UI.SecondTab;
import kr.co.Project_UI.SecondTab_1;
import kr.co.Project_UI.SecondTab_2;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.Toast;

import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapCompassManager;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapLocationManager;
import com.nhn.android.maps.NMapOverlay;
import com.nhn.android.maps.NMapOverlayItem;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.maps.nmapmodel.NMapPlacemark;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.maps.overlay.NMapPOIitem;
import com.nhn.android.mapviewer.overlay.NMapCalloutOverlay;
import com.nhn.android.mapviewer.overlay.NMapMyLocationOverlay;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay.OnStateChangeListener;

public class NaverMapActivity extends NMapActivity {
	/** Called when the activity is first created. */
	private static final boolean DEBUG = false;
	private NMapView mMapView;
	private NMapController mMapController;
	private static final String LOG_TAG = "NMapViewer";
	private static final String API_KEY_MAP = "8dc141d2a19465f9452b2e0eba61c717";
	private static final String API_KEY_SEARCH = "c8799621af3fadce9f75b1aef0d5f915";
	private static final String API_KEY_TRANS = "8099cc2e507732990c7cfeb86c6de5dde5808ff6";


	//private MapContainerView mMapContainerView;

	private NMapViewerResourceProvider mMapViewerResourceProvider;
	private static final NGeoPoint NMAP_LOCATION_DEFAULT = new NGeoPoint(126.97274837740011, 37.298098415149845);
	private static final int NMAP_ZOOMLEVEL_DEFAULT = 9;
	private static final int NMAP_VIEW_MODE_DEFAULT = NMapView.VIEW_MODE_VECTOR;

	private static final String KEY_ZOOM_LEVEL = "NMapViewer.zoomLevel";
	private static final String KEY_CENTER_LONGITUDE = "NMapViewer.centerLongitudeE6";
	private static final String KEY_CENTER_LATITUDE = "NMapViewer.centerLatitudeE6";
	private static final String KEY_VIEW_MODE = "NMapViewer.viewMode";


	private NMapOverlayManager mOverlayManager;
	private NMapMyLocationOverlay mMyLocationOverlay;
	private NMapCompassManager mMapCompassManager;
	private NMapLocationManager mMapLocationManager;
	private NMapPOIitem mFloatingPOIitem;
	private NMapPOIdataOverlay mFloatingPOIdataOverlay;

	private String rssAddr; //url값

	private SharedPreferences mPreferences;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// create map view
		// mMapView = new NMapView(this);
		
		
		setContentView(R.layout.searchaddress);
		NMapView mMapView = (NMapView)findViewById(R.id.mapView);
		mMapView.setApiKey(API_KEY_MAP);
		

		// initialize map view
		mMapView.setClickable(true);
		mMapView.setEnabled(true);
		mMapView.setFocusable(true);
		mMapView.setFocusableInTouchMode(true);
		mMapView.requestFocus();

		// register listener for map state changes
		mMapView.setOnMapStateChangeListener(onMapViewStateChangeListener);
		mMapView.setOnMapViewTouchEventListener(onMapViewTouchEventListener);
		//mMapView.setOnMapViewDelegate(onMapViewTouchDelegate);

		// use map controller to zoom in/out, pan and set map center, zoom level etc.
		mMapController = mMapView.getMapController();

		// use built in zoom controls
		NMapView.LayoutParams lp = new NMapView.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, NMapView.LayoutParams.BOTTOM_RIGHT);
		mMapView.setBuiltInZoomControls(true, lp);	

		// 현재위치 표시 및 나침판
		// location manager
		mMapLocationManager = new NMapLocationManager(this);
		mMapLocationManager.setOnLocationChangeListener(onMyLocationChangeListener);

		// compass manager
		mMapCompassManager = new NMapCompassManager(this);

		// create resource provider
		mMapViewerResourceProvider = new NMapViewerResourceProvider(this);

		// create overlay manager
		mOverlayManager = new NMapOverlayManager(this, mMapView, mMapViewerResourceProvider);


		// create my location overlay
		mMyLocationOverlay = mOverlayManager.createMyLocationOverlay(mMapLocationManager, mMapCompassManager);	


		
		
		
		/* ======================================================================= */
		// 추가
		/* ======================================================================= */
		// set data provider listener
		super.setMapDataProviderListener(onDataProviderListener);

		// register callout overlay listener to customize it.
		mOverlayManager.setOnCalloutOverlayListener(onCalloutOverlayListener);
		
		/* ======================================================================= */

		//현재위치로 이동
		startMyLocation();	
		
		//원하는 지점 선택 시 말풍선 표시.
		//		testFloatingPOIdataOverlay();

	}
	
	/* NMapDataProvider Listener */
	private final NMapActivity.OnDataProviderListener onDataProviderListener = new NMapActivity.OnDataProviderListener() {

		public void onReverseGeocoderResponse(NMapPlacemark placeMark, NMapError errInfo) {

			if (DEBUG) {
				Log.i(LOG_TAG, "onReverseGeocoderResponse: placeMark="
					+ ((placeMark != null) ? placeMark.toString() : null));
			}

			if (errInfo != null) {
				Log.e(LOG_TAG, "Failed to findPlacemarkAtLocation: error=" + errInfo.toString());

				Toast.makeText(NaverMapActivity.this, errInfo.toString(), Toast.LENGTH_LONG).show();
				return;
			}

			if (mFloatingPOIitem != null && mFloatingPOIdataOverlay != null) {
				mFloatingPOIdataOverlay.deselectFocusedPOIitem();

				if (placeMark != null) {
					mFloatingPOIitem.setTitle(placeMark.toString());
				}
				mFloatingPOIdataOverlay.selectPOIitemBy(mFloatingPOIitem.getId(), false);
			}
		}

	};

	private final NMapOverlayManager.OnCalloutOverlayListener onCalloutOverlayListener = new NMapOverlayManager.OnCalloutOverlayListener() {

		public NMapCalloutOverlay onCreateCalloutOverlay(NMapOverlay itemOverlay, NMapOverlayItem overlayItem,
			Rect itemBounds) {

			// handle overlapped items
			if (itemOverlay instanceof NMapPOIdataOverlay) {
				NMapPOIdataOverlay poiDataOverlay = (NMapPOIdataOverlay)itemOverlay;

				// check if it is selected by touch event
				if (!poiDataOverlay.isFocusedBySelectItem()) {
					int countOfOverlappedItems = 1;

					NMapPOIdata poiData = poiDataOverlay.getPOIdata();
					for (int i = 0; i < poiData.count(); i++) {
						NMapPOIitem poiItem = poiData.getPOIitem(i);

						// skip selected item
						if (poiItem == overlayItem) {
							continue;
						}

						// check if overlapped or not
						if (Rect.intersects(poiItem.getBoundsInScreen(), overlayItem.getBoundsInScreen())) {
							countOfOverlappedItems++;
						}
					}

					if (countOfOverlappedItems > 1) {
						String text = countOfOverlappedItems + " overlapped items for " + overlayItem.getTitle();
						Toast.makeText(NaverMapActivity.this, text, Toast.LENGTH_LONG).show();
						return null;
					}
				}
			}

			// use custom callout overlay
//			return new NMapCalloutCustomOverlay(itemOverlay, overlayItem, itemBounds, mMapViewerResourceProvider);

			// set basic callout overlay
			return new NMapCalloutBasicOverlay(itemOverlay, overlayItem, itemBounds, getResources());			
		}

	};
	
	//
	private String [] transPoint(String x, String y){
		String [] point  = new String[2];

		try {
			URL text = new URL("http://apis.daum.net/maps/transcoord?apikey="+API_KEY_TRANS
					+"&x=" + x
					+"&y=" + y
					+"&fromCoord=KTM"
					+"&toCoord=WGS84"
					+"&output=json");

			InputStream is = text.openStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String resultStr = br.readLine(); 
			//			Log.d("point", resultStr);
			resultStr = resultStr.replaceAll("\\{", "").replaceAll("\\}", "");
			//			Log.d("point", resultStr);
			String [] parseArr = resultStr.split(",");
			if(parseArr[0].indexOf("x") != -1) {
				point[0] = (parseArr[0].split(":"))[1];
				point[1] = (parseArr[1].split(":"))[1];
			} else {
				point[1] = (parseArr[0].split(":"))[1];
				point[0] = (parseArr[1].split(":"))[1];
			}
			//			Log.d("point", "1");
			//			Log.d("point", Arrays.toString(point));
			//			Log.d("point", "2");


		} catch (Exception e) {
			// TODO: handle exception
		}
		return point;
	}

	//검색버튼 이벤트
	public void process(View view){
		
		//url 통해서 필요한 값들 받아오기.
		EditText findPlaceTxt = (EditText)findViewById(R.id.findPlaceTxt);
		String searchPlace = findPlaceTxt.getText().toString();
		String findPlace = URLEncoder.encode(searchPlace);
		//		Log.d("test", findPlace);
		rssAddr = "http://openapi.naver.com/search?key="+ API_KEY_SEARCH + "&query=" + findPlace + "&target=local&start=1&display=10";


		//화면 클리어 시켜주기.
		mOverlayManager.clearOverlays();

		//여러개 오버레이 아이템을 하나에서 관리
		int markerId = NMapPOIflagType.PIN;

		// set POI data
		NMapPOIdata poiData = new NMapPOIdata(2, mMapViewerResourceProvider);

		try {
			ArrayList<RssVO> rssList= new RssReader().process(rssAddr); //rssList에 검색결과값 저장.
			poiData.beginPOIdata(10);

			for(RssVO rssVO : rssList) {
				String[] temp = transPoint(rssVO.getMapx(),rssVO.getMapy());
				poiData.addPOIitem(Double.parseDouble(temp[0]), Double.parseDouble(temp[1]), rssVO.getAddr(), markerId, 0);
				Log.d("test", rssVO.toString());
			}
			poiData.endPOIdata();
		} catch (Exception e) {
			e.printStackTrace();
		}


		// create POI data overlay
		NMapPOIdataOverlay poiDataOverlay = mOverlayManager.createPOIdataOverlay(poiData, null);

		// show all POI data 전체 아이템이 화면에 표시되도록 중심 밑 축적레벨 변경
		poiDataOverlay.showAllPOIdata(0);

		// set event listener to the overlay 아이템의 선택 상태가 변경되거나 말풍선이 선택되는 경우를 처리
		poiDataOverlay.setOnStateChangeListener(new OnStateChangeListener() {

			public void onCalloutClick(NMapPOIdataOverlay poiDataOverlay, NMapPOIitem item) {
				// [[TEMP]] handle a click event of the callout
				Log.d("test", item.getPoint()+"");
				Log.d("test", item.getTitle()+"");
				Toast.makeText(NaverMapActivity.this, "onCalloutClick: " + item.getTitle(), Toast.LENGTH_LONG).show();
				
//				finish();
			}

			public void onFocusChanged(NMapPOIdataOverlay poiDataOverlay, NMapPOIitem item) {
				if (item != null) {
					Log.i(LOG_TAG, "onFocusChanged: " + item.toString());
				} else {
					Log.i(LOG_TAG, "onFocusChanged: ");
				}
			}

		});

	}
	
	public void prev(View v) {
		Intent intent = new Intent(NaverMapActivity.this, SecondTab_2.class);
		v = SecondTab.SecondTabHGroup.getLocalActivityManager()
						.startActivity("SecondTab_2", intent
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView();
		SecondTab.SecondTabHGroup.replaceView(v);
	}
	
	

	private void restoreInstanceState() {
		mPreferences = getPreferences(MODE_PRIVATE);

		int longitudeE6 = mPreferences.getInt(KEY_CENTER_LONGITUDE, NMAP_LOCATION_DEFAULT.getLongitudeE6());
		int latitudeE6 = mPreferences.getInt(KEY_CENTER_LATITUDE, NMAP_LOCATION_DEFAULT.getLatitudeE6());
		int level = mPreferences.getInt(KEY_ZOOM_LEVEL, NMAP_ZOOMLEVEL_DEFAULT);
		int viewMode = mPreferences.getInt(KEY_VIEW_MODE, NMAP_VIEW_MODE_DEFAULT);

		mMapController.setMapViewMode(viewMode);
		mMapController.setMapCenter(new NGeoPoint(longitudeE6, latitudeE6), level);
		mMapController.setMapCenter(NMAP_LOCATION_DEFAULT ,level);
	}

	private final NMapView.OnMapStateChangeListener onMapViewStateChangeListener = new NMapView.OnMapStateChangeListener(){

		public void onAnimationStateChange(NMapView arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		public void onMapCenterChange(NMapView arg0, NGeoPoint arg1) {
			// TODO Auto-generated method stub

		}

		public void onMapCenterChangeFine(NMapView arg0) {
			// TODO Auto-generated method stub

		}

		/* MapView State Change Listener*/
		public void onMapInitHandler(NMapView mapView, NMapError errorInfo) {
			// TODO Auto-generated method stub
			if (errorInfo == null) { // success
				// restore map view state such as map center position and zoom level.
				restoreInstanceState();

			} else { // fail
				Log.e(LOG_TAG, "onFailedToInitializeWithError: " + errorInfo.toString());

				Toast.makeText(NaverMapActivity.this, errorInfo.toString(), Toast.LENGTH_LONG).show();
			}

		}

		public void onZoomLevelChange(NMapView arg0, int arg1) {
			// TODO Auto-generated method stub


		}

	};

	private final NMapView.OnMapViewTouchEventListener onMapViewTouchEventListener = new NMapView.OnMapViewTouchEventListener() {

		public void onLongPress(NMapView mapView, MotionEvent ev) {
			// TODO Auto-generated method stub

		}

		public void onLongPressCanceled(NMapView mapView) {
			// TODO Auto-generated method stub

		}

		public void onSingleTapUp(NMapView mapView, MotionEvent ev) {
			// TODO Auto-generated method stub

		}

		public void onTouchDown(NMapView mapView, MotionEvent ev) {

		}

		public void onScroll(NMapView mapView, MotionEvent e1, MotionEvent e2) {
		}

	};

	/* MyLocation Listener */
	private final NMapLocationManager.OnLocationChangeListener onMyLocationChangeListener = new NMapLocationManager.OnLocationChangeListener() {

		public boolean onLocationChanged(NMapLocationManager locationManager, NGeoPoint myLocation) {

			if (mMapController != null) {
				mMapController.animateTo(myLocation);
			}

			return true;
		}

		public void onLocationUpdateTimeout(NMapLocationManager locationManager) {

			// stop location updating
			//			Runnable runnable = new Runnable() {
			//				public void run() {										
			//					stopMyLocation();
			//				}
			//			};
			//			runnable.run();	

			Toast.makeText(NaverMapActivity.this, "Your current location is temporarily unavailable.", Toast.LENGTH_LONG).show();
		}

		public void onLocationUnavailableArea(NMapLocationManager locationManager, NGeoPoint myLocation) {

			Toast.makeText(NaverMapActivity.this, "Your current location is unavailable area.", Toast.LENGTH_LONG).show();

		}

	};

	private void startMyLocation() {

		if (mMyLocationOverlay != null) {
			if (!mOverlayManager.hasOverlay(mMyLocationOverlay)) {
				mOverlayManager.addOverlay(mMyLocationOverlay);
			}

			if (mMapLocationManager.isMyLocationEnabled()) {

				if (!mMapView.isAutoRotateEnabled()) {
					mMyLocationOverlay.setCompassHeadingVisible(true);

					mMapCompassManager.enableCompass();

					mMapView.setAutoRotateEnabled(true, false);

					//mMapContainerView.requestLayout();
				} else {
					//stopMyLocation();
				}

				mMapView.postInvalidate();
			} else {
				boolean isMyLocationEnabled = mMapLocationManager.enableMyLocation(false);
				if (!isMyLocationEnabled) {
					Toast.makeText(NaverMapActivity.this, "Please enable a My Location source in system settings",
							Toast.LENGTH_LONG).show();

					Intent goToSettings = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
					startActivity(goToSettings);

					return;
				}
			}
		}
	}

	//원하는 지점 선택 시 말풍선 표시
	//	private void testFloatingPOIdataOverlay() {
	//		// Markers for POI item
	//		int marker1 = NMapPOIflagType.PIN;
	//
	//		// set POI data
	//		NMapPOIdata poiData = new NMapPOIdata(1, mMapViewerResourceProvider);
	//		poiData.beginPOIdata(1);
	//		NMapPOIitem item = poiData.addPOIitem(null, "Touch & Drag to Move", marker1, 0);
	//		if (item != null) {
	//			// initialize location to the center of the map view.
	//			item.setPoint(mMapController.getMapCenter());
	//			// set floating mode
	//			item.setFloatingMode(NMapPOIitem.FLOATING_TOUCH | NMapPOIitem.FLOATING_DRAG);
	//			// show right button on callout
	//			item.setRightButton(true);
	//
	//			mFloatingPOIitem = item;
	//		}
	//		poiData.endPOIdata();
	//
	//		// create POI data overlay
	//		NMapPOIdataOverlay poiDataOverlay = mOverlayManager.createPOIdataOverlay(poiData, null);
	//		if (poiDataOverlay != null) {
	//			poiDataOverlay.setOnFloatingItemChangeListener(onPOIdataFloatingItemChangeListener);
	//
	//			// set event listener to the overlay
	//			poiDataOverlay.setOnStateChangeListener(onPOIdataStateChangeListener);
	//
	//			poiDataOverlay.selectPOIitem(0, false);
	//
	//			mFloatingPOIdataOverlay = poiDataOverlay;
	//		}
	//	}

	/* POI data State Change Listener*/
//	private final NMapPOIdataOverlay.OnStateChangeListener onPOIdataStateChangeListener = new NMapPOIdataOverlay.OnStateChangeListener() {
//
//		@Override
//		public void onCalloutClick(NMapPOIdataOverlay poiDataOverlay, NMapPOIitem item) {
//			if (DEBUG) {
//				Log.i(LOG_TAG, "onCalloutClick: title=" + item.getTitle());
//			}
//
//			// [[TEMP]] handle a click event of the callout
//			Toast.makeText(NaverMapActivity.this, "onCalloutClick: " + item.getTitle(), Toast.LENGTH_LONG).show();
//		}
//
//		@Override
//		public void onFocusChanged(NMapPOIdataOverlay poiDataOverlay, NMapPOIitem item) {
//			if (DEBUG) {
//				if (item != null) {
//					Log.i(LOG_TAG, "onFocusChanged: " + item.toString());
//				} else {
//					Log.i(LOG_TAG, "onFocusChanged: ");
//				}
//			}
//		}
//	};
//
//
//	private final NMapPOIdataOverlay.OnFloatingItemChangeListener onPOIdataFloatingItemChangeListener = new NMapPOIdataOverlay.OnFloatingItemChangeListener() {
//
//		@Override
//		public void onPointChanged(NMapPOIdataOverlay poiDataOverlay, NMapPOIitem item) {
//			NGeoPoint point = item.getPoint();
//
//
//			if (DEBUG) {
//				Log.i(LOG_TAG, "onPointChanged: point=" + point.toString());
//			}
//
//			findPlacemarkAtLocation(point.longitude, point.latitude); //여기서 onReverseGeocoderResponse 호출
//
//			item.setTitle(null);
//
//		}
//	};


}
