package com.midominio.ferre.chucknorrisretrofit;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements Callback<Chiste> {

    static final String BASE_URL = "https://api.chucknorris.io/";
    private TextView textoChiste;
    private Button botonChiste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        crearTab();
        //pedirChiste();
        textoChiste = (TextView) findViewById(R.id.textoChiste);
        botonChiste = (Button) findViewById(R.id.botonChiste);

        botonChiste.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                pedirChisteRandom();
            }
        });

        controladorCategorias cont = new controladorCategorias();
        cont.cargarCats();
    }

    public void pedirChisteRandom() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        NorrisAPI api = retrofit.create(NorrisAPI.class);

        Call<Chiste> call = api.cargarChiste();
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<Chiste> call, Response<Chiste> response) {
        if(response.isSuccessful()) {
            Chiste c = response.body();
            textoChiste.setText(c.getValue());
        }else{
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<Chiste> call, Throwable t) {
        Log.d("Error", "Error al pedir un chiste");
    }

    public void crearTab(){
        Resources res = getResources();

        TabHost tabs=(TabHost)findViewById(android.R.id.tabhost);
        tabs.setup();

        TabHost.TabSpec spec=tabs.newTabSpec("mitab1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Chiste al azar",
                res.getDrawable(android.R.drawable.ic_dialog_map));
        tabs.addTab(spec);

        spec=tabs.newTabSpec("mitab2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Categorias",
                res.getDrawable(android.R.drawable.ic_dialog_map));
        tabs.addTab(spec);

        tabs.setCurrentTab(0);

    }
}
