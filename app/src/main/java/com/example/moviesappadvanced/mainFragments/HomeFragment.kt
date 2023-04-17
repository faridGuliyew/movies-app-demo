package com.example.moviesappadvanced.mainFragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.PageTransformer
import com.example.moviesappadvanced.Constants.API_KEY
import com.example.moviesappadvanced.LoginActivity
import com.example.moviesappadvanced.R
import com.example.moviesappadvanced.adapters.RecommendedFilmAdapter
import com.example.moviesappadvanced.adapters.TrendingFilmAdapter
import com.example.moviesappadvanced.api.ApiUtils
import com.example.moviesappadvanced.base.BaseFragment
import com.example.moviesappadvanced.databinding.FragmentHomeBinding
import com.example.moviesappadvanced.models.recommended.RecommendedResponse
import com.example.moviesappadvanced.models.recommended.Result
import com.example.moviesappadvanced.models.trending.TrendingFilmResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.abs

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override fun layoutID(): Int {
        return R.layout.fragment_home
    }
    private val recommendedFilmApi = ApiUtils.getRecommendedFilmApi()
    private val trendingFilmApi = ApiUtils.getTrendingFilmApi()
    private val adapter = RecommendedFilmAdapter()
    private lateinit var trendingAdapter : TrendingFilmAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Code here...
        trendingAdapter = TrendingFilmAdapter(requireActivity().supportFragmentManager)

        if (userExists() == null){
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
            requireActivity().overridePendingTransition(com.airbnb.lottie.R.anim.abc_fade_in, com.airbnb.lottie.R.anim.abc_fade_out)
        }
        getRecommendedResponse()
        setRv()
        getTrendingResponse()
        setViewPager()
        setPageTransformer(binding.viewPager)
    }

    private fun getRecommendedResponse(){
        recommendedFilmApi.getRecommendedFilms(API_KEY,25).enqueue(object : Callback<RecommendedResponse>{
            override fun onResponse(
                call: Call<RecommendedResponse>,
                response: Response<RecommendedResponse>
            ) {
                if (response.isSuccessful){
                    response.body()?.let {
                        adapter.updateAdapter(it.results as ArrayList<Result>)
                    }
                }
            }

            override fun onFailure(call: Call<RecommendedResponse>, t: Throwable) {
                //Error 404
            }
        })
    }

    private fun getTrendingResponse(){
        trendingFilmApi.getTrendingFilms(API_KEY).enqueue(object : Callback<TrendingFilmResponse>{
            override fun onResponse(
                call: Call<TrendingFilmResponse>,
                response: Response<TrendingFilmResponse>
            ) {
                if (response.isSuccessful){
                    response.body()?.let {
                        //Log.e("heyyo",it.results.toString())
                        trendingAdapter.updateAdapter(it.results as ArrayList<com.example.moviesappadvanced.models.trending.Result>)
                    }
                }
            }

            override fun onFailure(call: Call<TrendingFilmResponse>, t: Throwable) {
                //Error 404
            }
        })
    }

    private fun setRv(){
        val rv = binding.rv
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = adapter
    }

    private fun setViewPager(){
        val viewPager = binding.viewPager
        viewPager.clipChildren = false
        viewPager.clipToPadding = false
        viewPager.offscreenPageLimit = 3
        viewPager.overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        viewPager.adapter = trendingAdapter
    }

    private fun setPageTransformer(viewPager : ViewPager2){
        val pageTransformer = CompositePageTransformer()
        pageTransformer.addTransformer { page, position ->
            val r = 0.85 + abs(position) * 0.15
            page.scaleY = r.toFloat()
        }
        viewPager.setPageTransformer(pageTransformer)
    }

    private fun userExists(): String? {
        val sharedPreferences = requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE)
        return sharedPreferences.getString("user", null)
    }

}