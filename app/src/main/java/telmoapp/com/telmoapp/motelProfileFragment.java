package telmoapp.com.telmoapp;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */

public class motelProfileFragment extends Fragment {

    GridView ListV;
    //falta instanciar los demas objetos
    private ProgressDialog dialog;
    private static final String URL = "https://infinite-atoll-7499.herokuapp.com/api/v1/motel/";
    private profileAdapter adapter;
    private List<Motel> array = new ArrayList<Motel>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_motel_profile, container, false);
        Bundle b = getActivity().getIntent().getExtras();

        //Completo la URL a donde debera ir a buscar El JSONObject
        final int x = b.getInt("IdMotel");
        String par = Integer.toString(x)+".json";
        String URIF = URL+par;

        ListV = (GridView) v.findViewById(R.id.gridView);
        adapter=new profileAdapter(getActivity(),array);
        ListV.setAdapter(adapter);

        //Creo un nuevo cuadro de diaogo para esperar que cargue la data
        dialog=new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
        dialog.show();

        //Creo un request object con Volley
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(URIF, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                hideDialog();
                //parsing json
                    try{
                        Motel item = new Motel();
                        item.setName(response.getString("name"));
                        item.setImage(response.getString("logo"));
                        item.setDescription(response.getString("description"));
                        item.setType(response.getString("type_id"));
                        item.setAddres(response.getString("addres"));
                        item.setUrlPage(response.getString("URL_WebPage"));
                        item.setUrlVideo(response.getString("URL_Video"));
                        item.setType(response.getString("type_id"));
                        item.setCity(response.getString("city_id"));
                        array.add(item);
                    }catch(JSONException ex){
                        ex.printStackTrace();
                    }
                adapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        ListarMotelesController.getmInstance().addToRequesQueue(jsonArrayRequest);
        return v;
    }
    public void hideDialog(){
        if(dialog !=null){
            dialog.dismiss();
            dialog=null;
        }
    }

}
