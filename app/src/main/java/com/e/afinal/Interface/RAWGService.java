package com.e.afinal.Interface;

import com.e.afinal.Model.Games.GamesResponse;
import com.e.afinal.Model.Games.VideogameExtraInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;


public interface RAWGService {

    // Interfaz necesaria para el uso de Retrofit
    // Aquí se colocan los endpoints de la API a utilizar

    @GET
    Call<GamesResponse> getVideogames(@Url String url);

    @GET("games/{id}")
    Call<VideogameExtraInfo> getVideogameExtraInfo(@Path(value = "id", encoded = false) String id);
}
