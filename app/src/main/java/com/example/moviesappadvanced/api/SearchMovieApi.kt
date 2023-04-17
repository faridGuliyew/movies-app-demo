package com.example.moviesappadvanced.api

import com.example.moviesappadvanced.models.search.movies.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchMovieApi {

    @GET("search/movie")
    fun getSearchedMovies(@Query("api_key") API_KEY : String, @Query("query") query : String, @Query("page") page : Int,@Query("include_adult") include_adult : Boolean) : Call<MovieResponse>
}