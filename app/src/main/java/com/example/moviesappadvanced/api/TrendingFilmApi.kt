package com.example.moviesappadvanced.api

import com.example.moviesappadvanced.models.trending.TrendingFilmResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TrendingFilmApi {

    @GET("movie/popular")
    fun getTrendingFilms(@Query("api_key") API_KEY : String ) : Call<TrendingFilmResponse>
}