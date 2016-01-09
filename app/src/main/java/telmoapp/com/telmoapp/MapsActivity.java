package telmoapp.com.telmoapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    LocationsDB mLocationsDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        /** This content provider does the database operations by this object */
        mLocationsDB = new LocationsDB(this);

        // Creating an instance of ContentValues
        ContentValues contentValues = new ContentValues();

        // Setting latitude in ContentValues
        contentValues.put(LocationsDB.FIELD_ROW_ID,1);

        // Setting latitude in ContentValues
        contentValues.put(LocationsDB.FIELD_LAT, 6.268137);

        // Setting longitude in ContentValues
        contentValues.put(LocationsDB.FIELD_LNG, -75.567234);

        // Setting zoom in ContentValues
        contentValues.put(LocationsDB.FIELD_ZOOM, 15);

        ContentValues contentValues2 = new ContentValues();

        // Setting latitude in ContentValues
        contentValues2.put(LocationsDB.FIELD_ROW_ID,2);

        // Setting latitude in ContentValues
        contentValues2.put(LocationsDB.FIELD_LAT, 6.267699);

        // Setting longitude in ContentValues
        contentValues2.put(LocationsDB.FIELD_LNG, -75.5633685);

        // Setting zoom in ContentValues
        contentValues2.put(LocationsDB.FIELD_ZOOM, 15);
        mLocationsDB.insert(contentValues);
        mLocationsDB.insert(contentValues2);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Enabling MyLocation Layer of Google Map
        mMap.setMyLocationEnabled(true);

        Cursor c=mLocationsDB.getAllLocations();
        LatLng latlong;
        //Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                latlong = new LatLng(c.getDouble(1),c.getDouble(2));
                drawMarker(latlong);
            } while(c.moveToNext());
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlong,13));
        }

    }
    private void drawMarker(LatLng point){
        // Creating an instance of MarkerOptions
        MarkerOptions markerOptions = new MarkerOptions();

        // Setting latitude and longitude for the marker
        markerOptions.position(point);

        // Adding marker on the Google Map
        mMap.addMarker(markerOptions);
    }


    @Override
    public void onLocationChanged(Location location) {
        String Text = "Mi ubicaci—n actual es: " + "\n Lat = "
                + location.getLatitude() + "\n Long = " + location.getLongitude();
        LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
        Toast.makeText(getApplicationContext(),Text,Toast.LENGTH_LONG).show();
        mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(),location.getLongitude())).title("You are here!"));

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