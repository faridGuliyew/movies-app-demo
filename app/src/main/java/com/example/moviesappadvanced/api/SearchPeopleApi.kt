package com.example.moviesappadvanced.api

import com.example.moviesappadvanced.models.search.people.PeopleResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchPeopleApi {

    @GET("search/person")
    fun getSearchedPeople (@Query("api_key") API_KEY : String, @Query("query") query : String, @Query("page") page : Int) : Call<PeopleResponse>
}