package edu.jhu.cs.oose.project.group14.ihungry.androidapp.activities;

import java.util.*;

import com.example.androidihungry.R;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.Overlay;
import com.readystatesoftware.maps.*;

import edu.jhu.cs.oose.project.group14.ihungry.androidapp.ActivitySwitchSignals;
import edu.jhu.cs.oose.project.group14.ihungry.androidapp.FileHandler;
import edu.jhu.cs.oose.project.group14.ihungry.androidapp.MyItemizedOverlay;
import edu.jhu.cs.oose.project.group14.ihungry.androidapp.MyLocation;
import edu.jhu.cs.oose.project.group14.ihungry.androidapp.MyOverlayItem;
import edu.jhu.cs.oose.project.group14.ihungry.androidapp.MyLocation.*;
import edu.jhu.cs.oose.project.group14.ihungry.androidclientmodel.*;
import edu.jhu.cs.oose.project.group14.ihungry.model.*;

import android.location.*;
import android.os.AsyncTask;
import android.os.Bundle;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.*;
import android.view.Menu;
import android.view.View.*;
import android.widget.*;

/**
 * This is the view that shows the map with current location and restaurants
 * around.
 * 
 * @author SuNFloWer
 * 
 */
public class NearbyActivity extends MapActivity {
	static final private int ZOOMTIME = 15;

	private AndroidClientModel clientModel;
	private List<Restaurant> restaurants;
	
	static final private int[][] restaurant_locations = {
			{ 39337482, -76634559 }, { 39337249, -76624322 },
			{ 39344429, -76631478 }, { 39334798, -76620687 },
			{ 39313321, -76617787 }, { 39330855, -76633269 },
			{ 39329058, -76615716 }, { 39328962, -76609548 } };
	

	private TapControlledMapView mapView;
	private MapController mapController;
	private Location currentLocation;
	private GeoPoint currentPoint;

	private List<Overlay> mapOverlays;
	private Drawable drawable;
	private Drawable drawable2;
	private MyItemizedOverlay itemizedoverlay;
	private MyItemizedOverlay itemizedoverlay2;
	private MyOverlayItem overlayitem;
	private MyOverlayItem overlayitem2;
	private List<MyOverlayItem> overlayitem2_multi;

	private Geocoder geocoder;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nearby);

		/* ############ Map Handler ############ */
		mapView = (TapControlledMapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		mapView.setSatellite(false);
		mapView.setTraffic(false);
		mapController = mapView.getController();
		mapController.setZoom(ZOOMTIME);

		// dismiss balloon upon single tap of MapView (iOS behavior)
		mapView.setOnSingleTapListener(new OnSingleTapListener() {
			public boolean onSingleTap(MotionEvent e) {
				itemizedoverlay.hideAllBalloons();
				return true;
			}
		});

		/* ############ Map Overlays ############ */
		mapOverlays = mapView.getOverlays();
		drawable = getResources().getDrawable(R.drawable.marker);
		itemizedoverlay = new MyItemizedOverlay(drawable, mapView);
		// set iOS behavior attributes for overlay
		itemizedoverlay.setShowClose(false);
		itemizedoverlay.setShowDisclosure(true);
		itemizedoverlay.setSnapToCenter(true);

		drawable2 = getResources().getDrawable(R.drawable.blue_pin);
		itemizedoverlay2 = new MyItemizedOverlay(drawable2, mapView);
		// set iOS behavior attributes for overlay
		itemizedoverlay2.setShowClose(false);
		itemizedoverlay2.setShowDisclosure(true);
		itemizedoverlay2.setSnapToCenter(true);

		/* ############ My Location Retriever ############ */
		LocationResult locationResult = new LocationResult() {
			@Override
			public void gotLocation(Location location) {
				// Got the location!
				getLocationInfo(location); // display log
				setCurrentLocation(location);
				animateToCurrentLocation();

				if (itemizedoverlay != null) {
					itemizedoverlay.clear();
					mapOverlays.remove(itemizedoverlay);
					mapView.invalidate();
				}

				overlayitem = new MyOverlayItem(currentPoint,
						"Current Location", "", null);
				itemizedoverlay.addOverlay(overlayitem);
				mapOverlays.add(itemizedoverlay);
				mapView.postInvalidate();
			}
		};
		MyLocation myLocation = new MyLocation();
		myLocation.getLocation(this, locationResult);

		/* ############ Geocoder ############ */
		geocoder = new Geocoder(this);

		/* ############ Connect server ############ */
		clientModel = new AndroidClientModelImpl();
		//String responseSvr = clientmodel.getResponseFromServerT();
		//Log.v("[Response]", responseSvr);

		/* ############ Add some restaurant locations on map ############ */
		overlayitem2_multi = new ArrayList<MyOverlayItem>();
		NetworkSearchAddressTask task_search = new NetworkSearchAddressTask();
		task_search.execute();

		/*
		 * ##### Hook up button presses to the appropriate event handler. #####
		 */
		((ImageButton) findViewById(R.id.imgbtn_refresh))
				.setOnClickListener(imgbtn_refresh_Listener);

	}

	/**
	 * This is a AsyncTask used to search addresses.
	 * 
	 * @author SuNFloWer
	 * 
	 */
	private class NetworkSearchAddressTask extends
			AsyncTask<Void, MyOverlayItem, String> {

		@Override
		protected String doInBackground(Void... params) {
			try {
				/* Get a list of restaurants infos */
				restaurants = clientModel.retrieveRestaurants(new LocationInfo(0,0));
				
				for (int i = 0; i < restaurants.size(); i++) {
					Restaurant rest = (Restaurant)restaurants.get(i);
					
					Log.v("RestInfo", rest.getContactInfo().getAddress()+" "+ i+" "+ rest.getContactInfo().getRealName()+" "+
							rest.getAccountInfo().getId());
					overlayitem2 = getLocationByAddress(
							rest.getContactInfo().getAddress(), i, rest.getContactInfo().getRealName(),
							rest.getAccountInfo().getId());
					if (overlayitem2 != null) {
						overlayitem2_multi.add(overlayitem2);
						publishProgress(overlayitem2);
					}
				}
			} catch (Exception e) {
				Log.e("[doInBackground]", "Error");
			}

			/*
			 * for (int i = 0; i < overlayitem2_multi.size(); i++) {
			 * MyOverlayItem overlayitem_one = overlayitem2_multi.get(i);
			 * Log.i("[Map Items]", overlayitem_one.getRestaurantID() + " " +
			 * overlayitem_one.getTitle() + " " + overlayitem_one.getPoint()); }
			 */
			return "Done Searching";
		}

		@Override
		protected void onProgressUpdate(MyOverlayItem... item) {
			addsingleOverlayitemToMap(item[0]);
		}

		@Override
		protected void onPostExecute(String result) {
			Log.i("[on Progress Execute]", "Called " + result);
			// addOverlayitemsToMap(overlayitem2_multi);
		}


	}

	/**
	 * Get the location of the input address and pin the location on the map.
	 * 
	 * @param geocoder
	 * @param locationName
	 * @param index
	 * @param restaurantName
	 * @param restaurantID
	 * @return
	 */
	public MyOverlayItem getLocationByAddress(
			String locationName, int index, String restaurantName,
			String restaurantID) {
		Log.v("[Search address]", locationName);

		// Log.v("[Cache]", FileHandler.loadFile(this,
		// FileHandler.f_rest_location_cache));
		/* Check if in the cache */
		if (locationInCache(locationName)) {
			// return;
		} else {
			// GeoPoint pt = getLocationByGeocoder(locationName);
			GeoPoint pt = new GeoPoint(restaurant_locations[index][0],
					restaurant_locations[index][1]);
			if (pt != null)
				return new MyOverlayItem(pt, restaurantName, locationName,
						restaurantID);
			else
				return null;
		}
		return null;
	}

	/**
	 * Use Geocoder to get the GeoPoint according to the street address of the
	 * restaurant.
	 * 
	 * @param locationName
	 * @return
	 */
	private GeoPoint getLocationByGeocoder(String locationName) {
		/* Better not put Toast display in try catch block */
		try {
			// Log.v("[Geocoder enabled]",geocoder.isPresent()+""); // API 9
			// above
			List<Address> addressList = geocoder.getFromLocationName(
					locationName, 1);
			if (addressList != null && addressList.size() > 0) {
				int lat = (int) (addressList.get(0).getLatitude() * 1e6);
				int lng = (int) (addressList.get(0).getLongitude() * 1e6);

				Log.v("[Search Result]", "lat: " + lat + " lng: " + lng);

				/* Write to cache */
				FileHandler.saveFile(this, FileHandler.f_rest_location_cache,
						locationName + "||" + lat + " " + lng + "\n");

				GeoPoint pt = new GeoPoint(lat, lng);
				return pt;
			}
		} catch (Exception e) {
			Log.e("[getLocationByAddress]", "Search Error!!!");
			e.printStackTrace();
		}
		return null;
	}

	private boolean locationInCache(String locationName) {

		return false;
	}

	/**
	 * Add all the overlay items in the arraylist onto the map, specifically,
	 * itemizedoverlay2.
	 * 
	 * @param overlayitems
	 */
	private void addOverlayitemsToMap(List<MyOverlayItem> overlayitems) {
		for (int i = 0; i < overlayitems.size(); i++) {
			MyOverlayItem overlayitem_one = overlayitems.get(i);
			itemizedoverlay2.addOverlay(overlayitem_one);
			mapOverlays.add(itemizedoverlay2);
			mapView.postInvalidate();
		}
	}

	/**
	 * Add a single overlay item to the map, specifically, itemizedoverlay2.
	 * 
	 * @param item_2
	 */
	private void addsingleOverlayitemToMap(MyOverlayItem item_2) {
		itemizedoverlay2.addOverlay(item_2);
		mapOverlays.add(itemizedoverlay2);
		mapView.postInvalidate();

	}

	OnClickListener imgbtn_refresh_Listener = new OnClickListener() {
		public void onClick(View v) {
			Log.v("[NearbyActivity]", "fresh btn clicked");

			mapController.setZoom(ZOOMTIME);

			animateToCurrentLocation();

		}
	};

	@Override
	protected void onResume() {
		super.onResume();
		// lm.requestLocationUpdates(getBestProvider_cust(), 1000, 1, this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		// lm.removeUpdates(this);
	}

	/**
	 * Animate center of the map to the currentPoint.
	 */
	private void animateToCurrentLocation() {
		if (currentPoint != null) {
			mapController.animateTo(currentPoint);
		}
	}

	/**
	 * Set the currentPoint and currentLocation given the input location.
	 * 
	 * @param location
	 */
	private void setCurrentLocation(Location location) {
		int currLatitude = (int) (location.getLatitude() * 1E6);
		int currLongitude = (int) (location.getLongitude() * 1E6);
		currentPoint = new GeoPoint(currLatitude, currLongitude);

		currentLocation = new Location("");
		currentLocation.setLatitude(currentPoint.getLatitudeE6() / 1e6);
		currentLocation.setLongitude(currentPoint.getLongitudeE6() / 1e6);
	}

	/**
	 * Display changed location.
	 * 
	 * @param location
	 */
	private void getLocationInfo(Location location) {
		Log.v("LOCATION CHANGED", location.getLatitude() + "");
		Log.v("LOCATION CHANGED", location.getLongitude() + "");

		// ToastDisplay.DisplayToastOnScr(this, location.getLatitude() + "" +
		// location.getLongitude());

	}

	/*
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		Log.v("[Nearby onActivityResult]", "Nearby_PRE");

		if (resultCode == ActivitySwitchSignals.RESTAURANTINFOCLOSESWH) {
			Log.v("[Nearby onActivityResult]", "Nearby!!");
			this.finish();
		}
	}
	*/

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// do something on back.
			setResult(ActivitySwitchSignals.MAINSCREENSWH);
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_nearby, menu);
		return true;
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

}
