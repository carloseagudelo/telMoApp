package telmoapp.com.telmoapp;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by agude on 11/12/2015.
 */
public class calificationFragment extends Fragment {

    TextView Tittle;
    Button Calificar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_calification, container, false);
        Calificar = (Button) v.findViewById(R.id.votar);
        //Recupero lo que viene del otro fragment
        Bundle b = getActivity().getIntent().getExtras();
        int id = b.getInt("IdMotel")+1; //id del motel para armar la url
        String nameMotel = b.getString("NameMotel");
        Tittle = (TextView) v.findViewById(R.id.textViewTittle);
        Tittle.setText(nameMotel);
        String idM = String.valueOf(id);
        final String URL = "https://infinite-atoll-7499.herokuapp.com/api/v1/motel/"+idM;
        Calificar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendRequest(URL);
                Intent intent = new Intent("telmoapp.ListarMotelesActivity");
                startActivity(intent);
            }
        });

        return v;
    }


    private void sendRequest(String URL)
    {
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("calification", 3);
        } catch (JSONException e) {
            // handle exception
        }

        JsonObjectRequest putRequest = new JsonObjectRequest(Request.Method.PUT, URL, jsonObject,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response
                        Log.d("Response", response.toString());
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {

            @Override
            public Map<String, String> getHeaders()
            {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                return headers;
            }

            @Override
            public byte[] getBody() {

                try {
                    Log.i("json", jsonObject.toString());
                    return jsonObject.toString().getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        queue.add(putRequest);
    }

}

