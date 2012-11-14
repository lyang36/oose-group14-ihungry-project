package edu.jhu.cs.oose.project.group14.ihungry.androidapp;

import java.util.*;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.readystatesoftware.mapviewballoons.BalloonItemizedOverlay;
import com.readystatesoftware.mapviewballoons.BalloonOverlayView;

import edu.jhu.cs.oose.project.group14.ihungry.androidapp.activities.OrderandRestaurantInfoActivity;;

/**
 * This is an overlay class with customized overlay items on a mapview.
 * 
 * @author SuNFloWer
 *
 */
public class MyItemizedOverlay extends BalloonItemizedOverlay<MyOverlayItem> {
	private static final int BALLOON_BOTTOM_OFFSET = 60;

	private List<MyOverlayItem> m_overlays = new ArrayList<MyOverlayItem>();
	private Context c;

	public MyItemizedOverlay(Drawable defaultMarker, MapView mapView) {
		super(boundCenterBottom(defaultMarker), mapView);
		c = mapView.getContext();
		populate();
	}

	/**
	 * Add an MyOverlayItem to the List m_overlays and then populate on the overlay (or the map).
	 * @param overlay
	 */
	public void addOverlay(MyOverlayItem overlay) {
		m_overlays.add(overlay);
		populate();
	}

	/**
	 * Delete all the overlay items on the overlay and then populate on the overlay (or the map).
	 */
	public void clear() {
		m_overlays.clear();
		setLastFocusedIndex(-1);
		populate();
	}

	@Override
	protected MyOverlayItem createItem(int i) {
		return m_overlays.get(i);
	}

	@Override
	public int size() {
		return m_overlays.size();
	}

	/**
	 * Triggered when user taps on a balloon => OrderandRestaurantInfoActivity would appear.
	 * 
	 */
	@Override
	protected boolean onBalloonTap(int index, MyOverlayItem item) {
		Toast.makeText(
				c,
				"onBalloonTap for overlay index " + index + " "
						+ item.getTitle().toString() + " "
						+ item.getSnippet().toString() + " "
						+ item.getRestaurantID(), Toast.LENGTH_SHORT).show();

		String rest_id = item.getRestaurantID();
		String rest_name = item.getTitle();
		String rest_addr = item.getSnippet();

		if (rest_id != null) { // if null => maybe is current location
			Intent intent = new Intent(c, OrderandRestaurantInfoActivity.class);
			
			intent.putExtra("rest_id", rest_id);
			intent.putExtra("rest_name", rest_name);
			intent.putExtra("rest_addr", rest_addr);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			c.startActivity(intent);
		//	((Activity) c).startActivityForResult(intent, ActivitySwitchSignals.RESTAURANTINFO);
		//	this.nearbyAct.startActivityForResult(intent, ActivitySwitchSignals.RESTAURANTINFO);

		}
		return true;
	}
	
	/**
	 * Reconstruct a BalloonOverlayView with a customized offset value.
	 * 
	 */
	@Override
	protected BalloonOverlayView<MyOverlayItem> createBalloonOverlayView() {
		// use our custom balloon view with our custom overlay item type:
		return new BalloonOverlayView<MyOverlayItem>(getMapView().getContext(),
				getBalloonBottomOffset() + BALLOON_BOTTOM_OFFSET);
	}

	/*
	 * public boolean onTap(GeoPoint p, MapView mapView){ Toast.makeText(c,
	 * "onTap: " + p.getLatitudeE6()+" "+p.getLatitudeE6(),
	 * Toast.LENGTH_SHORT).show(); return true; }
	 */

}
