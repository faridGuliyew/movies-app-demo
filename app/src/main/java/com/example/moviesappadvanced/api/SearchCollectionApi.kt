package com.example.moviesappadvanced.api

import com.example.moviesappadvanced.models.search.collections.CollectionsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchCollectionApi {
    @GET("search/collection")
    fun getSearchedCollections(@Query("api_key") API_KEY : String, @Query("query") query : String, @Query("page") page : Int) : Call<CollectionsResponse>
}