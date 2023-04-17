package com.example.moviesappadvanced.additionalFragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.example.moviesappadvanced.Constants.API_KEY
import com.example.moviesappadvanced.R
import com.example.moviesappadvanced.adapters.ReviewAdapter
import com.example.moviesappadvanced.api.ApiUtils
import com.example.moviesappadvanced.databinding.FragmentReviewBinding
import com.example.moviesappadvanced.models.reviews.movie.Result
import com.example.moviesappadvanced.models.reviews.movie.ReviewResponse
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewFragment : Fragment() {
    private var _binding : FragmentReviewBinding? = null
    private val binding
        get() = _binding!!
    private val movieReviewApi = ApiUtils.getMovieReviewApi()
    private val movieReviewAdapter = ReviewAdapter()
    private lateinit var rv : RecyclerView
    private val args : ReviewFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReviewBinding.inflate(inflater,container,false)
        rv = binding.rvReviews
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Hello how are you my dude?
        //Set adapter anim.
        movieReviewAdapter.controlLottie={
            if (it){
                binding.lottie.visibility = View.VISIBLE
            }else{
                binding.lottie.visibility = View.GONE
            }
        }

        getReview(args.movie.id.toString())
        setRv()
        rvStateChange()
    }


    private fun getReview(id : String){
        movieReviewApi.getReviews(id,API_KEY).enqueue(object : Callback<ReviewResponse>{
            override fun onResponse(
                call: Call<ReviewResponse>,
                response: Response<ReviewResponse>
            ) {
                if (response.isSuccessful){
                    response.body()?.let {
                        Log.e("myTest",it.results.toString())
                        movieReviewAdapter.updateAdapter(it.results as ArrayList<Result>)
                    }
                }
            }

            override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {
                //Error 404
            }
        })
    }

    private fun rvStateChange(){
        rv.addOnScrollListener(object : OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    requireActivity().findViewById<BottomNavigationView>(R.id.bottomNav).visibility = View.GONE
                } else {
                    requireActivity().findViewById<BottomNavigationView>(R.id.bottomNav).visibility = View.VISIBLE
                }
            }
        })
    }

    private fun setRv(){
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = movieReviewAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}