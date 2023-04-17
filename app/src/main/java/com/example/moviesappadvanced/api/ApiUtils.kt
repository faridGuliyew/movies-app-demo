package com.example.moviesappadvanced.api

import com.example.moviesappadvanced.Constants.BASE_URL
import retrofit2.create

class ApiUtils {
    companion object{
        fun getRecommendedFilmApi() : RecommendedFilmApi{
            return RetrofitClient.getRetrofitClient(BASE_URL).create(RecommendedFilmApi::class.java)
        }
        fun getTrendingFilmApi() : TrendingFilmApi{
            return RetrofitClient.getRetrofitClient(BASE_URL).create(TrendingFilmApi::class.java)
        }
        fun getSearchMovieApi() : SearchMovieApi{
            return RetrofitClient.getRetrofitClient(BASE_URL).create(SearchMovieApi::class.java)
        }
        fun getSearchShowApi() : SearchShowApi{
            return RetrofitClient.getRetrofitClient(BASE_URL).create(SearchShowApi::class.java)
        }
        fun getSearchCollectionApi() : SearchCollectionApi{
            return RetrofitClient.getRetrofitClient(BASE_URL).create(SearchCollectionApi::class.java)
        }
        fun getSearchPeopleApi() : SearchPeopleApi{
            return RetrofitClient.getRetrofitClient(BASE_URL).create(SearchPeopleApi::class.java)
        }
        fun getCreditsApi() : CreditsApi{
            return RetrofitClient.getRetrofitClient(BASE_URL).create(CreditsApi::class.java)
        }
        fun getMovieReviewApi() : MovieReviewApi{
            return RetrofitClient.getRetrofitClient(BASE_URL).create(MovieReviewApi::class.java)
        }
    }
}