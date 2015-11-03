package telmoapp.com.telmoapp;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A placeholder fragment containing a simple view.
 */
public class ListarMotelesActivityFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {

    TextView linkview;
    private static final String  URL = "https://infinite-atoll-7499.herokuapp.com/api/v1/motel";
    
    public ListarMotelesActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_listar_moteles, container, false);
        linkview = (TextView) v.findViewById(R.id.link1);
        linkview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent("telmoapp.motelProfile"));
            }
        });

        RequestQueue request = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, this, this);
        request.add(jsonObjectRequest);
        return v;


    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            Log.d("onResponse()", response.toString(0));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("onErrorResponse()", error.toString());
    }
}
