package telmoapp.com.telmoapp;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class ListarMotelesActivityFragment extends Fragment {


    private static final String  URL = "https://infinite-atoll-7499.herokuapp.com/api/v1/motel";
    private ProgressDialog dialog;
    private ListView listView;
    private MotelAdapter adapter;
    public static List<Motel> array = new ArrayList<Motel>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_listar_moteles, container, false);

        listView = (ListView) v.findViewById(R.id.list_item);
        adapter=new MotelAdapter(getActivity(),array);
        listView.setAdapter(adapter);


        Cursor c=RefreshService.mLocationsDB.getAllLocations();
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                Motel item = new Motel();
                item.setId(c.getInt(0));
                item.setName(c.getString(4));
                item.setImage(c.getString(5));
                item.setDescription(c.getString(3));
                item.setType(c.getString(6));
                item.setAddres(c.getString(7));
                //add to array
                Log.v("prueba", item.getName());
                array.add(item);
            } while (c.moveToNext());
            adapter.notifyDataSetChanged();
        }
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
        return v;
    }
    public void hideDialog(){
        if(dialog !=null){
            dialog.dismiss();
            dialog=null;
        }
    }



}
