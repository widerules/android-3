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

package com.footy.Map;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;


public class MyItemizedOverlay extends BalloonItemizedOverlay<OverlayItem> {

	private ArrayList<OverlayItem> m_overlays = new ArrayList<OverlayItem>();
	private Context c;
	
	public MyItemizedOverlay(Drawable defaultMarker, MapView mapView) {
		super(boundCenter(defaultMarker), mapView);
		c = mapView.getContext();
	}

	public void addOverlay(OverlayItem overlay) {
	    m_overlays.add(overlay);
	    populate();
	}

	@Override
	protected OverlayItem createItem(int i) {
		return m_overlays.get(i);
	}

	@Override
	public int size() {
		return m_overlays.size();
	}

	@Override
	protected boolean onBalloonTap(int index) {
		MapInfo mapInfo = MapInfo.getMapInfo();
		mapInfo.setAddress(m_overlays.get(index).getTitle());
		mapInfo.setPoint(m_overlays.get(index).getPoint());
		Intent intent = new Intent(c, SecondTab_2.class);
		Log.d("test", "클래스 인식성공");
		View v = SecondTab.SecondTabHGroup.getLocalActivityManager()
						.startActivity("SecondTab_2", intent
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
						 ).getDecorView();
		Activity activity = (Activity)c;
		activity.setContentView(v);
		
		Log.d("mydebug", "onBalloonTap for overlay index " + index);
//		Intent intent = new Intent(c, SecondTab_2.class);
//		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		Log.d("mydebug", "intent created");
		Toast.makeText(c, "onBalloonTap for overlay index " + index,
				Toast.LENGTH_LONG).show();
//		c.startActivity(intent);
//		Activity activity = (Activity)c;
//		activity.finish();
		return true;
	}
	
}
