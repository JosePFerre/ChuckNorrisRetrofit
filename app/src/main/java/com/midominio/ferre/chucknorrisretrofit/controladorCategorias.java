package com.midominio.ferre.chucknorrisretrofit;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class controladorCategorias implements Callback<List<Chiste>> {

    static final String BASE_URL = "https://api.chucknorris.io/";

    public void cargarCats() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        NorrisAPI api = retrofit.create(NorrisAPI.class);

        Call<List<Chiste>> call = api.cargarCategorias();
        call.enqueue(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onResponse(Call<List<Chiste>> call, Response<List<Chiste>> response) {
        if(response.isSuccessful()) {
            List<Chiste> categorias = response.body();
            Log.d("res", String.valueOf(categorias));
            System.out.println(categorias);

            //categorias.forEach(cat -> System.out.println(String.valueOf(categorias)));
        } else {
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<List<Chiste>> call, Throwable t) {
        t.printStackTrace();
    }
}