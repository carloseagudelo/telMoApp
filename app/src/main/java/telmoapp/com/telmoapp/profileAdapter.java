package telmoapp.com.telmoapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

/**
 * Created by agude on 07/12/2015.
 */
public class profileAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Activity activity;
    private List<Motel> items;
    ImageLoader imageLoader=ListarMotelesController.getmInstance().getmImageLoader();
    public profileAdapter(Activity activity,List<Motel> items){
        this.activity=activity;
        this.items=items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater==null){
            inflater=(LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView ==null){
            convertView=inflater.inflate(R.layout.fragment_custom_profile, null);
        }
        if(imageLoader==null)
            imageLoader=ListarMotelesController.getmInstance().getmImageLoader();
            NetworkImageView imageView= (NetworkImageView) convertView.findViewById(R.id.logoProfile);
            TextView name= (TextView) convertView.findViewById(R.id.name);
            TextView description = (TextView) convertView.findViewById(R.id.descripcion);
            TextView addres = (TextView) convertView.findViewById(R.id.addres);
            TextView city = (TextView) convertView.findViewById(R.id.city);
            TextView webpage = (TextView) convertView.findViewById(R.id.webpage);
            TextView video = (TextView) convertView.findViewById(R.id.video);
            Button btn = (Button) convertView.findViewById(R.id.calificar);

            //getting data for row
            final Motel item=items.get(position);
            //image
            imageView.setImageUrl(item.getImage(), imageLoader);
            //name
            name.setText(item.getName());
            //description
            description.setText(item.getDescription());
            //adddres
            addres.setText(item.getAddres());
            //webpage
            webpage.setText(item.getUrlPage());
            //video
            video.setText(item.getUrlVideo());
            //city
            city.setText(item.getCity());
            //boton para calificar motel
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                Intent intent = new Intent("telmoapp.calification");
                Bundle b = new Bundle();
                b.putInt("IdMotel", item.getId());
                b.putString("NameMotel",item.getName() );
                intent.putExtras(b);
                activity.startActivity(intent);
                }
            });
            //boton para hacer la llamada al motel
            // XXXXXXXXXXXXXX falta hacer el analisis e implementacion de esto XXXXXXXXXXXXX

        return convertView;
    }
}
