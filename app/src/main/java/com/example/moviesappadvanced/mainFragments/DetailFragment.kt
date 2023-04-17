package com.example.moviesappadvanced.mainFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.moviesappadvanced.Constants.API_KEY
import com.example.moviesappadvanced.Constants.IMAGE_BASE_URL
import com.example.moviesappadvanced.R
import com.example.moviesappadvanced.adapters.CrewAdapter
import com.example.moviesappadvanced.adapters.SearchPeopleAdapter
import com.example.moviesappadvanced.api.ApiUtils
import com.example.moviesappadvanced.base.BaseFragment
import com.example.moviesappadvanced.databinding.FragmentDetailBinding
import com.example.moviesappadvanced.models.search.credits.CreditsResponse
import com.example.moviesappadvanced.models.search.credits.Crew
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Picasso

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailFragment : BaseFragment<FragmentDetailBinding>(){
    override fun layoutID(): Int {
        return R.layout.fragment_detail
    }

    private val args:DetailFragmentArgs by navArgs()
    private val crewAdapter = CrewAdapter()
    private val crewApi = ApiUtils.getCreditsApi()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViews()
        setRv()

        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNav).visibility = View.GONE
        binding.button.setOnClickListener {
            args.movieResult?.let {
                findNavController().navigate(DetailFragmentDirections.actionDetailFragmentToBottomSheetFragment(it.overview))
            }
            args.recommendedResult?.let {
                findNavController().navigate(DetailFragmentDirections.actionDetailFragmentToBottomSheetFragment(it.overview))
            }
        }
        binding.button2.setOnClickListener {
            args.movieResult?.let {
                findNavController().navigate(DetailFragmentDirections.actionDetailFragmentToReviewFragment(args.movieResult as com.example.moviesappadvanced.models.search.movies.Result))
            }
        }
    }

    private fun bindViews(){
        args.recommendedResult?.let {
            Picasso.get().load(IMAGE_BASE_URL+ it.backdropPath).into(binding.imageViewBack)
            Picasso.get().load(IMAGE_BASE_URL+ it.posterPath).into(binding.imageViewPoster)
            binding.textView8.text = (it.voteAverage / 2).toString()
            binding.ratingBar.rating = it.voteAverage.toFloat()
            //Api
            getPeople(it.id)
        }
        args.movieResult?.let {
            Picasso.get().load(IMAGE_BASE_URL+ it.backdropPath).into(binding.imageViewBack)
            Picasso.get().load(IMAGE_BASE_URL+ it.posterPath).into(binding.imageViewPoster)
            binding.textView8.text = (it.voteAverage / 2).toString()
            binding.ratingBar.rating = it.voteAverage.toFloat()
            //Api
            getPeople(it.id)
        }
    }

    private fun getPeople(movie_id : Int){
        crewApi.getCrewMembers(movie_id, API_KEY).enqueue(object : Callback<CreditsResponse>{
            override fun onResponse(
                call: Call<CreditsResponse>,
                response: Response<CreditsResponse>
            ) {
                if (response.isSuccessful){
                    response.body()?.let {
                        crewAdapter.updateAdapter(it.crew as ArrayList<Crew>)
                    }
                }
            }
            override fun onFailure(call: Call<CreditsResponse>, t: Throwable) {
                //Error 404
            }
        })
    }

    private fun setRv(){
        val rv = binding.rv
        rv.layoutManager = LinearLayoutManager(context,RecyclerView.HORIZONTAL,false)
        rv.adapter = crewAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNav).visibility = View.VISIBLE
    }
}