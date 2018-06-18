package com.arrk.shishir.arrktest.rest;

import com.arrk.shishir.arrktest.taskdownloder.model.StarWars;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RetroInterface {

    @GET("api/people")
    Call<StarWars> getAllCharacters();

    @GET
    Call<StarWars> getCharacters(@Url String url);
}
