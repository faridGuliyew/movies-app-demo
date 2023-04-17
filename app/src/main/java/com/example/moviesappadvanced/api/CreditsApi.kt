package com.example.moviesappadvanced.api

import com.example.moviesappadvanced.models.search.credits.CreditsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CreditsApi {

    @GET("https://api.themoviedb.org/3/movie/{movie_id}/credits")
    fun getCrewMembers(@Path("movie_id") movie_id : Int, @Query("api_key") API_KEY :String) : Call<CreditsResponse>
}