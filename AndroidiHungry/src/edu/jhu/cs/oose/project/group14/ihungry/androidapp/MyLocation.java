package edu.jhu.cs.oose.project.group14.ihungry.androidapp;

import java.util.Timer;
import java.util.TimerTask;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;


/**
 * A class mainly for getting current location in which Network and GPS listeners and a threadtask for updating location are created.
 * Get the user's current location:<p>
 * 1. First of all I check what providers are enabled. Some may be disabled on the device, some may be disabled in application manifest.
 * <p>2. If any provider is available I start location listeners and timeout timer. It's 20 seconds in my example, may not be enough for GPS so you can enlarge it.
 * <p>3. If I get update from location listener I use the provided value. I stop listeners and timer.
 * <p>4. If I don't get any updates and timer elapses I have to use last known values.
 * <p>5. I grab last known values from available providers and choose the most recent of them.
 *
 * @author SuNFloWer
 *
 */
public class MyLocation {
    Timer timer1;
    LocationManager lm;
    LocationResult locationResult;
    boolean gps_enabled=false;
    boolean network_enabled=false;

    /**
     * Start two listeners: Network and GPS listener to get location.
     * @param context
     * @param result
     * @return
     */
    public boolean getLocation(Context context, LocationResult result)
    {
        //I use LocationResult callback class to pass location value from MyLocation to user code.
        locationResult=result;
        if(lm==null)
            lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        //exceptions will be thrown if provider is not permitted.
        try{gps_enabled=lm.isProviderEnabled(LocationManager.GPS_PROVIDER);}catch(Exception ex){}
        try{network_enabled=lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);}catch(Exception ex){}

        //don't start listeners if no provider is enabled
        if(!gps_enabled && !network_enabled)
            return false;

        if(gps_enabled)
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListenerGps);
        if(network_enabled)
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListenerNetwork);
        timer1=new Timer();
        timer1.schedule(new GetLastLocation(), 20000);
        return true;
    }

    /**
     * Network provider listener.
     */
    LocationListener locationListenerGps = new LocationListener() {
        public void onLocationChanged(Location location) {
            timer1.cancel();
            locationResult.gotLocation(location);
            lm.removeUpdates(this);
            lm.removeUpdates(locationListenerNetwork);
        }
        public void onProviderDisabled(String provider) {}
        public void onProviderEnabled(String provider) {}
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    };

    /**
     * GPS provider listener.
     */
    LocationListener locationListenerNetwork = new LocationListener() {
        public void onLocationChanged(Location location) {
            timer1.cancel();
            locationResult.gotLocation(location);
            lm.removeUpdates(this);
            lm.removeUpdates(locationListenerGps);
        }
        public void onProviderDisabled(String provider) {}
        public void onProviderEnabled(String provider) {}
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    };

    /**
     * A timertask thread working on getting the last location.
     * @author SuNFloWer
     *
     */
    class GetLastLocation extends TimerTask {
        @Override
        public void run() {
             lm.removeUpdates(locationListenerGps);
             lm.removeUpdates(locationListenerNetwork);

             Location net_loc=null, gps_loc=null;
             if(gps_enabled)
                 gps_loc=lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
             if(network_enabled)
                 net_loc=lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

             //if there are both values use the latest one
             if(gps_loc!=null && net_loc!=null){
                 if(gps_loc.getTime()>net_loc.getTime())
                     locationResult.gotLocation(gps_loc);
                 else
                     locationResult.gotLocation(net_loc);
                 return;
             }

             if(gps_loc!=null){
                 locationResult.gotLocation(gps_loc);
                 return;
             }
             if(net_loc!=null){
                 locationResult.gotLocation(net_loc);
                 return;
             }
             locationResult.gotLocation(null);
        }
    }

    /**
     * An abstract class LocationResult with one abstract method getLocation(Location location).
     * @author SuNFloWer
     *
     */
    public static abstract class LocationResult{
        public abstract void gotLocation(Location location);
    }
}