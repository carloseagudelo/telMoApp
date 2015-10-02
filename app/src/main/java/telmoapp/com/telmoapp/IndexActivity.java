package telmoapp.com.telmoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class IndexActivity extends Activity implements View.OnClickListener {
    Button btnlista;
    Button btncercanos;
    Button btnsexshop;
    Button btnrecomendados;
    Button btnrecomendaciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        btnlista = (Button) findViewById(R.id.btn_lista);
        btncercanos = (Button) findViewById(R.id.btn_cercanos);
        btnrecomendaciones = (Button) findViewById(R.id.btn_recomendaciones);
        btnrecomendados = (Button) findViewById(R.id.btn_recomendados);
        btnsexshop = (Button) findViewById(R.id.btn_sshop);

        btnlista.setOnClickListener(this);
        btnsexshop.setOnClickListener(this);
        btnrecomendados.setOnClickListener(this);
        btncercanos.setOnClickListener(this);
        btnrecomendaciones.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_lista:

                startActivity(new Intent("telmoapp.ListarMotelesActivity"));

                break;
            case R.id.btn_recomendaciones:
                startActivity(new Intent("telmoapp.recomendations"));
                break;
            case R.id.btn_cercanos:
                startActivity(new Intent("telmoapp.motelsNearby"));
                break;
            case R.id.btn_recomendados:
                startActivity(new Intent("telmoapp.recomendations"));
                break;
            case R.id.btn_sshop:
                startActivity(new Intent("telmoapp.sexShop"));
                break;
            default:
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_index, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
