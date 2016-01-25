package telmoapp.com.telmoapp;


import android.support.v4.app.FragmentActivity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    LocationsDB mLocationsDB;
    private ProgressDialog dialog;
    private static final String  URL = "https://infinite-atoll-7499.herokuapp.com/api/v1/motel";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Enabling MyLocation Layer of Google Map
        mMap.setMyLocationEnabled(true);

        //LatLng myLocation= new LatLng(mMap.getMyLocation().getLatitude(),mMap.getMyLocation().getLongitude());


        Cursor c=RefreshService.mLocationsDB.getAllLocations();
        LatLng latlong=null;
        //Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                if(c.getDouble(1)> 0 ) {
                    latlong = new LatLng(c.getDouble(1), c.getDouble(2));
                    drawMarker(latlong, c.getString(4));
                }
            } while(c.moveToNext());
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlong,13));
        }

    }
    private void drawMarker(LatLng point,String desc){
        // Creating an instance of MarkerOptions

        MarkerOptions markerOptions = new MarkerOptions();

        // Setting latitude and longitude for the marker
        markerOptions.position(point);
        markerOptions.title(desc);

        // Adding marker on the Google Map
        mMap.addMarker(markerOptions);
    }


    @Override
    public void onLocationChanged(Location location) {
        LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 12));
        if(mMap != null){
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc,16.0f));
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

}