package telmoapp.com.telmoapp;


import android.content.ContentValues;
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

public class ParseJSON {
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_LOGO = "logo";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_TYPE_ID = "type_id";
    public static final String KEY_ADDRES = "addres";
    public static final String KEY_LAT = "latitude";
    public static final String KEY_LNG = "longitude";
    public static String TAG= ParseJSON.class.getSimpleName();


    public static List<Motel> array = new ArrayList<Motel>();
    private static final String  URL = "https://infinite-atoll-7499.herokuapp.com/api/v1/motel";
    public ParseJSON(){
        Log.d("JSON", "parsed");

    }

    public static List<Motel> parseJSON(){

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
                        RefreshService.mLocationsDB.insert(values);
                    }catch(JSONException ex){
                        ex.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error: " + error.getMessage());
            }
        });
        ListarMotelesController.getmInstance().addToRequesQueue(jsonArrayRequest);
        return array;
    }
}

