package telmoapp.com.telmoapp;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class IndexActivityFragment extends Fragment {
    Button btnlista;
    Button btncercanos;
    Button btnsexshop;
    Button btnrecomendados;
    Button btnrecomendaciones;

    public IndexActivityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_index_activity, container, false);
        btnlista = (Button) v.findViewById(R.id.btn_lista);
        btncercanos = (Button) v.findViewById(R.id.btn_cercanos);
        btnrecomendaciones = (Button) v.findViewById(R.id.btn_recomendaciones);
        btnrecomendados = (Button) v.findViewById(R.id.btn_recomendados);
        btnsexshop = (Button) v.findViewById(R.id.btn_sshop);

        TextView t2 = (TextView) v.findViewById(R.id.webpage);
        t2.setMovementMethod(LinkMovementMethod.getInstance());

        btnlista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent("telmoapp.ListarMotelesActivity"));
            }
        });
        btnsexshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent("telmoapp.sexShop"));
            }
        });
        btnrecomendados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent("telmoapp.bestCalification"));
            }
        });
        btncercanos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent("telmoapp.MapsActivity"));
            }
        });
        btnrecomendaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent("telmoapp.recomendations"));
            }
        });
        return v;
    }

}


