package co.kr.SampleGoogleMap;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

public class SampleGPSListener implements LocationListener {

	@Override
	public void onLocationChanged(Location location) {
		Double latitude = location.getLatitude();
		Double longitude = location.getLongitude();

		String msg = "Last Known Location -> Latitude : " + latitude
				+"\nLongitude : " + longitude;
		Log.d("myDebug", msg);
		
	}

	@Override
	public void onProviderDisabled(String provider) {
	}
	@Override
	public void onProviderEnabled(String provider) {
	}
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

}
