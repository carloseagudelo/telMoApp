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
public class motelProfileFragment extends Fragment {

    TextView linkcalification;

    public motelProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_motel_profile, container, false);

        linkcalification = (TextView) v.findViewById(R.id.link_calification2);

        linkcalification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent("telmoapp.calification"));
            }
        });
        return v;
    }
}
