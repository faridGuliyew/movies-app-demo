package com.example.moviesappadvanced.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieReviewApi {

    @GET("movie/{movie_id}/reviews")
    fun getReviews(@Path("movie_id") movie_id : String, @Query("api_key")  API_KEY : String) : Call<com.example.moviesappadvanced.models.reviews.movie.ReviewResponse>
}