package telmoapp.com.telmoapp;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class ListarMotelesActivityFragment extends Fragment {

    TextView linkview;

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
         return v;
    }
}
