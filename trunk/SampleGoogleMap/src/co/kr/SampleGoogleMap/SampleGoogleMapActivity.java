package co.kr.SampleGoogleMap;

import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;

public class SampleGoogleMapActivity extends MapActivity {
	MapView mapView;
	MapController controller;
	MyLocationOverlay myOverlay;
	List<Overlay> mapOverlays;
	
	Geocoder gc;
	EditText searchEdit;
	Button searchBtn;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // 맵뷰 객체 얻어오기
        mapView = (MapView)findViewById(R.id.mapView);
        mapView.setBuiltInZoomControls(true);

        // LocationService 시작
        startLoctaionService();
        
        // 내 위치 오버레이 얻어오기, 오버레이 리스트에 추가하기
        mapOverlays = mapView.getOverlays();
        myOverlay = new MyLocationOverlay(getApplicationContext(), mapView);
        mapOverlays.add(myOverlay);
        
        // 내 위치 오버레이 보여주기
        myOverlay.enableMyLocation();
        
        gc = new Geocoder(getApplicationContext(), Locale.KOREAN);
        searchEdit = (EditText)findViewById(R.id.searchEdit);
        searchBtn = (Button)findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String searchStr = searchEdit.getText().toString().trim();
				searchLocation(searchStr);
			}
		});
       
    }

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
	private void startLoctaionService() {
		// 리스너 설정
		SampleGPSListener gpsListener = new SampleGPSListener();
		long minTime = 10000;
		float minDistance = 0;
		LocationManager manager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, gpsListener);
		Toast.makeText(getApplicationContext(), "Location Service started", 2000).show();

		// 현재 위치 가져오기
		Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		String msg = "Last Known Location -> Latitude : " + location.getLatitude()
					+"\nLongitude : " + location.getLongitude();
		Log.d("myDebug", msg);
		Toast.makeText(getApplicationContext(), msg, 2000).show();
		
		// 현재 위치 지도에 표시, 축적 설정
		int maxZoomLevel = mapView.getMaxZoomLevel();
		int zoomLevel = (int) ((maxZoomLevel + 1)/1.15);
		GeoPoint geoPt = new GeoPoint((int)location.getLatitude(), (int)location.getLongitude());
		controller = mapView.getController();
		controller.setCenter(geoPt);
		controller.setZoom(zoomLevel);
		mapView.setSatellite(false);
		mapView.setTraffic(false);
		
		showCurrentLocation(location.getLatitude(), location.getLongitude());
	}
	
	private void showCurrentLocation(Double latitude, Double longitude) {
		double intLatitude = latitude.doubleValue() * 1000000;
		double intLongitude = longitude.doubleValue() * 1000000;
		
		GeoPoint geoPt = new GeoPoint((int)intLatitude, (int)intLongitude);

		controller.animateTo(geoPt);
		controller.setCenter(geoPt);
	}
	
	private void searchLocation(String searchStr) {
		final int maxResults = 10;
		List<Address> addressList = null;
		
		StringBuilder output = new StringBuilder();
		
		Log.d("myDebug", "검색 키워드 : " + searchStr);
		
		try {
			addressList = gc.getFromLocationName(searchStr, maxResults);
			
			if(addressList != null) {
				Address addr;
				output.append("Count of Address for [" + searchStr + "] : " + addressList.size());
				for(int i=0; i<addressList.size(); i++) {
					addr = addressList.get(i);
					output.append("\nAddress #" + i + " : " + addr.getAddressLine(i));
					output.append("\nLatitude : " + addr.getLatitude() + ", Longitude : " + addr.getLongitude());
				}
				Log.d("myDebug", output.toString());
			}
		} catch (Exception e) {
			Log.e("myDebug", e.toString());
		}
		
		Log.d("myDebug", "검색 완료 : " + searchStr);
	}
}