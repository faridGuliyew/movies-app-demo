package com.example.moviesappadvanced.api

import com.example.moviesappadvanced.models.recommended.RecommendedResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RecommendedFilmApi {

    @GET("movie/now_playing")
    fun getRecommendedFilms(@Query("api_key") API_KEY : String, @Query("page") page : Int) : Call<RecommendedResponse>
}