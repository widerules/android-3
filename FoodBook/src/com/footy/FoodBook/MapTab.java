/***
 * Copyright (c) 2010 readyState Software Ltd
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package com.footy.FoodBook;

import java.util.ArrayList;
import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.footy.Map.MyItemizedOverlay;
import com.footy.Store.SearchHelper;
import com.footy.Store.StoreVO;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class MapTab extends MapActivity {

	MapView mapView;
	EditText findPlaceTxt;
	List<Overlay> mapOverlays;
	Drawable drawable;
	Drawable drawable2;
	OverlayItem overlayItem;
	MyItemizedOverlay itemizedOverlay;
	MyItemizedOverlay itemizedOverlay2;
	GeoPoint point;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maptab);
        mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		
		final MapController mc = mapView.getController();
		//mc.animateTo();
		mc.setZoom(16);
		
	}
//	
	
	public void process(View view){
		mapOverlays = mapView.getOverlays();
		//////////////
		drawable = getResources().getDrawable(R.drawable.marker);
		itemizedOverlay = new MyItemizedOverlay(drawable, mapView);
		
		findPlaceTxt = (EditText)findViewById(R.id.findPlaceTxt);
		String searchPlace = findPlaceTxt.getText().toString();
		
		try {
			ArrayList<StoreVO> storeList= SearchHelper.search(searchPlace, 37.29646223, 126.97779519);
			for(StoreVO storeVO : storeList) {
				setMarker(storeVO);
				
			}
			mapOverlays.add(itemizedOverlay);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		final MapController mc = mapView.getController();
		mc.setZoom(16);

	}
	
	public void setMarker(StoreVO storeVO) {
		double intLatitude = storeVO.getLatitude() * 1000000; 
 		double intLongitude = storeVO.getLongitude() * 1000000;
		
		point = new GeoPoint((int)intLatitude,(int)intLongitude);
		Log.d("test", storeVO.toString());
		overlayItem = new OverlayItem(point, storeVO.getName(), storeVO.getAddr());
		itemizedOverlay.addOverlay(overlayItem);
		
	}
	
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	

}
