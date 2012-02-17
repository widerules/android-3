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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.footy.Board.BoardHelper;
import com.footy.Board.BoardVO;
import com.footy.Map.MyBoardOverlay;
import com.footy.Map.MyPartyOverlay;
import com.footy.Store.SearchHelper;
import com.footy.Store.StoreVO;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class MapTab extends MapActivity {
	
	BoardHelper boardHelper = new BoardHelper();

	MapView mapView;
	EditText findPlaceTxt;
	List<Overlay> mapOverlays;
	Drawable drawable;
	Drawable drawable2;
	OverlayItem overlayItem;
	MyPartyOverlay partyOverlay;
	MyBoardOverlay boardOverlay;
	GeoPoint point;
	
	LinearLayout layout1;
	LinearLayout layout2;
	@Override
    public void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maptab);
        mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		
		layout1 = (LinearLayout)findViewById(R.id.maptab1);
//		layout2 = (LinearLayout)findViewById(R.id.maptab2);

		BroadcastReceiver myReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				Toast.makeText(context, "브로드캐스팅", 1000).show();
//				layout1.setVisibility(View.GONE);
//				layout2.setVisibility(View.VISIBLE);
			}
		};
		IntentFilter filter = new IntentFilter("bbb");
		registerReceiver(myReceiver, filter);

		
		final MapController mc = mapView.getController();
		GeoPoint point = new GeoPoint(37298025, 126972817);  
		mc.animateTo(point);
		mc.setZoom(16);
		
		putBoardMarker();
	}
//	
	public void process(View view){
		mapOverlays = mapView.getOverlays();
		//////////////
		drawable = getResources().getDrawable(R.drawable.marker);
		partyOverlay = new MyPartyOverlay(drawable, mapView);
		findPlaceTxt = (EditText)findViewById(R.id.findPlaceTxt);
		String searchPlace = findPlaceTxt.getText().toString();
		
		try {
			ArrayList<StoreVO> storeList= SearchHelper.search(searchPlace, 37.29646223, 126.97779519);
			for(StoreVO storeVO : storeList) {
				setMarker(storeVO);
				
			}
			mapOverlays.add(partyOverlay);
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
		partyOverlay.addOverlay(overlayItem);
		
	}
	
	public void putBoardMarker(){
		mapOverlays = mapView.getOverlays();
		drawable = getResources().getDrawable(R.drawable.marker2);
		boardOverlay = new MyBoardOverlay(drawable, mapView);
		
		ArrayList<BoardVO> bList = boardHelper.getList();
		Log.d("test11", bList.size()+"");
		
		try {
			for(BoardVO boardVO : bList) {
				double intLatitude = boardVO.getLatitude() * 1000000; 
		 		double intLongitude = boardVO.getLongitude() * 1000000;
		 		
		 		point = new GeoPoint((int)intLatitude,(int)intLongitude);
				Log.d("test11", boardVO.toString());
				overlayItem = new OverlayItem(point, boardVO.getTitle(), boardVO.getContent());
				boardOverlay.addOverlay(overlayItem);
				
				
			}
			mapOverlays.add(boardOverlay);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	

}
