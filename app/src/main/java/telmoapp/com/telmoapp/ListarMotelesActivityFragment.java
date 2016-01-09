package telmoapp.com.telmoapp;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class ListarMotelesActivityFragment extends Fragment {


    private static final String  URL = "https://infinite-atoll-7499.herokuapp.com/api/v1/motel";
    private ProgressDialog dialog;
    private List<Motel> array = new ArrayList<Motel>();
    private ListView listView;
    private MotelAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_listar_moteles, container, false);

        listView = (ListView) v.findViewById(R.id.list_item);
        adapter=new MotelAdapter(getActivity(),array);
        listView.setAdapter(adapter);

        dialog=new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
        dialog.show();

        //Create volley request obj
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                hideDialog();
                //parsing json
                for(int i=0;i<response.length();i++){
                    try{
                        JSONObject obj=response.getJSONObject(i);
                        Motel item = new Motel();
                        item.setId(obj.getInt("id"));
                        item.setName(obj.getString("name"));
                        item.setImage(obj.getString("logo"));
                        item.setDescription(obj.getString("description"));
                        item.setType(obj.getString("type_id"));
                        item.setAddres(obj.getString("addres"));
                        //add to array
                        array.add(item);
                    }catch(JSONException ex){
                        ex.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();
            //Crea el evento para ir al perfil del motel
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Object o = listView.getItemAtPosition(position);
                    Intent intent = new Intent("telmoapp.motelProfile");
                    Bundle b = new Bundle();
                    b.putInt("IdMotel", ((Motel) o).id);
                    intent.putExtras(b);
                    startActivity(intent);
                    }
                });


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
;
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
