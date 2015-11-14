package telmoapp.com.telmoapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import java.util.List;


public class MotelAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Activity activity;
    private List<Motel> items;
    ImageLoader imageLoader=ListarMotelesController.getmInstance().getmImageLoader();
    public MotelAdapter(Activity activity,List<Motel> items){
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
            convertView=inflater.inflate(R.layout.fragment_costum_listar,null);
        }
        if(imageLoader==null)
            imageLoader=ListarMotelesController.getmInstance().getmImageLoader();
            NetworkImageView imageView= (NetworkImageView) convertView.findViewById(R.id.image_view);
            TextView name= (TextView) convertView.findViewById(R.id.motel_name);
            TextView description= (TextView) convertView.findViewById(R.id.motel_description);
            TextView type= (TextView) convertView.findViewById(R.id.motel_type);
            TextView addres= (TextView) convertView.findViewById(R.id.motel_addres);
            //getting data for row
            Motel item=items.get(position);
        //name
            name.setText(item.getName());
        //description
            description.setText(item.getDescription());
        //type
            type.setText(item.getType());
        //year
            addres.setText(item.getAddres());
        //image
            imageView.setImageUrl(item.getImage(), imageLoader);

        return convertView;
    }
}
