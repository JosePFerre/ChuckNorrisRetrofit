package com.midominio.ferre.chucknorrisretrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by JosePablo on 13/11/2017.
 */

public interface NorrisAPI {
    @GET("jokes/random")
    Call<Chiste> cargarChiste();
    @GET("jokes/categories")
    Call<List<Chiste>> cargarCategorias();
}
