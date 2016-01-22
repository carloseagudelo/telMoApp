package telmoapp.com.telmoapp;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        Log.v("on create","creating");
        /** This content provider does the database operations by this object */
        mLocationsDB = new LocationsDB(this);

            //Create volley request obj
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    hideDialog();
                    //parsing json
                    // Creating an instance of ContentValues
                    ContentValues contentValues = new ContentValues();
                    for(int i=0;i<response.length();i++){
                        try{

                            JSONObject obj=response.getJSONObject(i);

                            // Setting latitude in ContentValues
                            contentValues.put(LocationsDB.FIELD_ROW_ID, obj.getInt("id"));
                            Log.v("on create",obj.getInt("id")+ "creating");
                            // Setting latitude in ContentValues
                            contentValues.put(LocationsDB.FIELD_LAT, obj.getDouble("latitude"));

                            // Setting longitude in ContentValues
                            contentValues.put(LocationsDB.FIELD_LNG, obj.getDouble("longitude"));

                            // Setting zoom in ContentValues
                            contentValues.put(LocationsDB.FIELD_DESC, obj.getString("description"));


                            mLocationsDB.insert(contentValues);
                        }catch(JSONException ex){
                            ex.printStackTrace();
                        }
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Enabling MyLocation Layer of Google Map
        mMap.setMyLocationEnabled(true);

        Cursor c=mLocationsDB.getAllLocations();
        LatLng latlong;
        String description;
        //Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                latlong = new LatLng(c.getDouble(1),c.getDouble(2));
                description=c.getString(3);
                drawMarker(latlong,description);
            } while(c.moveToNext());
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlong,13));
        }

    }
    private void drawMarker(LatLng point, String description){
        // Creating an instance of MarkerOptions
        MarkerOptions markerOptions = new MarkerOptions();

        // Setting latitude and longitude for the marker
        markerOptions.position(point);
        markerOptions.title(description);

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
            public void hideDialog(){
                if(dialog !=null){
                    dialog.dismiss();
                    dialog=null;
                }
            }
}