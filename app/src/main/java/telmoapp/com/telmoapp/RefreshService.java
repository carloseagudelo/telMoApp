package telmoapp.com.telmoapp;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class RefreshService extends IntentService {
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_LOGO = "logo";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_TYPE_ID = "type_id";
    public static final String KEY_ADDRES = "addres";
    public static final String KEY_LAT = "latitude";
    public static final String KEY_LNG = "longitude";
    private static final String TAG = RefreshService.class.getSimpleName();
    private static final String  URL = "https://infinite-atoll-7499.herokuapp.com/api/v1/motel";
    public static boolean isUpdate = false;
    public static LocationsDB mLocationsDB;
    public static List<Motel> array = new ArrayList<Motel>();

    public RefreshService() {
        super("RefreshService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreated");
    }

    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onStarted");
        mLocationsDB = new LocationsDB(this);
        array=ParseJSON.parseJSON();
        //Create volley request obj
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //parsing json

                ContentValues values=new ContentValues();
                for(int i=0;i<response.length();i++){
                    try{
                        JSONObject jo=response.getJSONObject(i);
                        values.put(LocationsDB.FIELD_ROW_ID,jo.getString(KEY_ID));
                        values.put(LocationsDB.FIELD_NAME,jo.getString(KEY_NAME));
                        values.put(LocationsDB.FIELD_LOGO,jo.getString(KEY_LOGO));
                        values.put(LocationsDB.FIELD_DESCRIPTION,jo.getString(KEY_DESCRIPTION));
                        values.put(LocationsDB.FIELD_TYPE_ID,jo.getString(KEY_TYPE_ID));
                        values.put(LocationsDB.FIELD_ADDRES,jo.getString(KEY_ADDRES));
                        values.put(LocationsDB.FIELD_LAT,jo.getString(KEY_LAT));
                        values.put(LocationsDB.FIELD_LNG, jo.getString(KEY_LNG));
                        Log.d("JSON", values.toString());
                        mLocationsDB.insert(values);
                    }catch(JSONException ex){
                        ex.printStackTrace();
                    }
                }
                Toast.makeText(RefreshService.this, "informacion actualizada",
                        Toast.LENGTH_LONG).show();
                IndexActivityFragment.btnlista.setEnabled(true);
                IndexActivityFragment.btncercanos.setEnabled(true);
                IndexActivityFragment.btnsexshop.setEnabled(true);
                IndexActivityFragment.btnrecomendados.setEnabled(true);
                IndexActivityFragment.btnrecomendaciones.setEnabled(true);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(RefreshService.this, "Ocurrio un error al establecer conexion con el servidor, intente nuevamente con opcion actualizar",
                        Toast.LENGTH_LONG).show();
            }
        });
        ListarMotelesController.getmInstance().addToRequesQueue(jsonArrayRequest);

    }




}
