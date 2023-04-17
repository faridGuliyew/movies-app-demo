package com.example.moviesappadvanced.api

import com.example.moviesappadvanced.models.search.shows.ShowsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchShowApi {

    @GET("search/tv")
    fun getSearchedShows(@Query("api_key") API_KEY : String,@Query("query") query : String,@Query("page") page:Int, @Query("include_adult") include_adult : Boolean) : Call<ShowsResponse>
}