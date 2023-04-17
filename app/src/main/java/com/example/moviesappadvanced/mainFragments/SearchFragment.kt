package com.example.moviesappadvanced.mainFragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.GradientDrawable.Orientation
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.LinearLayout
import android.widget.NumberPicker
import android.widget.PopupMenu
import android.widget.SearchView
import androidx.annotation.RequiresApi
import androidx.appcompat.view.menu.MenuBuilder
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesappadvanced.Constants.API_KEY
import com.example.moviesappadvanced.R
import com.example.moviesappadvanced.adapters.SearchCollectionAdapter
import com.example.moviesappadvanced.adapters.SearchMovieAdapter
import com.example.moviesappadvanced.adapters.SearchPeopleAdapter
import com.example.moviesappadvanced.adapters.SearchShowAdapter
import com.example.moviesappadvanced.api.ApiUtils
import com.example.moviesappadvanced.base.BaseFragment
import com.example.moviesappadvanced.databinding.FragmentSearchBinding
import com.example.moviesappadvanced.models.search.collections.CollectionsResponse
import com.example.moviesappadvanced.models.search.movies.MovieResponse
import com.example.moviesappadvanced.models.search.people.PeopleResponse
import com.example.moviesappadvanced.models.search.shows.Result
import com.example.moviesappadvanced.models.search.shows.ShowsResponse
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    override fun layoutID(): Int {
        return R.layout.fragment_search
    }
    //Movie search
    private val movieAdapter = SearchMovieAdapter()
    private val searchMovieApi = ApiUtils.getSearchMovieApi()
    //TV show search
    private val showAdapter = SearchShowAdapter()
    private val searchShowApi = ApiUtils.getSearchShowApi()
    //Collection search
    private val collectionAdapter = SearchCollectionAdapter()
    private val searchCollectionApi = ApiUtils.getSearchCollectionApi()
    //People search
    private val peopleAdapter = SearchPeopleAdapter()
    private val searchPeopleApi = ApiUtils.getSearchPeopleApi()
    //Latest query
    private var latestQuery : String? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Write your code here...
        latestQuery = "p"
        getSearchMovieResponse(latestQuery!!,1,false)
        setSearchView("movie")
        setMovieRv()

        binding.textViewCategory.setOnClickListener {
            setPopupMenu()
        }
    }
    private fun getSearchMovieResponse(query : String, page : Int, include_adult : Boolean=false){
        searchMovieApi.getSearchedMovies(API_KEY,query,page,include_adult).enqueue(object : Callback<MovieResponse>{
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful){
                    response.body()?.let {
                        //Anim
                        binding.lottie.visibility = View.GONE
                        //
                        if (it.results.isEmpty()){
                            binding.lottie.visibility = View.VISIBLE
                        }
                        setPage(it.totalPages,"movie")
                        movieAdapter.updateAdapter(it.results as ArrayList<com.example.moviesappadvanced.models.search.movies.Result>)
                    }
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                //Error 404
            }
        })
    }
    private fun getSearchShowResponse(query : String, page : Int, include_adult : Boolean=false){
        searchShowApi.getSearchedShows(API_KEY,query,page,include_adult).enqueue(object : Callback<ShowsResponse>{
            override fun onResponse(call: Call<ShowsResponse>, response: Response<ShowsResponse>) {
                if (response.isSuccessful){
                    response.body()?.let {
                        //Anim
                        binding.lottie.visibility = View.GONE
                        //
                        if (it.results.isEmpty()){
                            binding.lottie.visibility = View.VISIBLE
                        }
                        setPage(it.totalPages,"show")
                        showAdapter.updateAdapter(it.results as ArrayList<Result>)
                    }
                }
            }

            override fun onFailure(call: Call<ShowsResponse>, t: Throwable) {
                //Error 404
            }
        })
    }
    private fun getSearchCollectionResponse(query: String,page:Int){
        searchCollectionApi.getSearchedCollections(API_KEY,query,page).enqueue(object : Callback<CollectionsResponse>{
            override fun onResponse(
                call: Call<CollectionsResponse>,
                response: Response<CollectionsResponse>
            ) {
                if (response.isSuccessful){
                    response.body()?.let {
                        //Anim
                        binding.lottie.visibility = View.GONE
                        //
                        if (it.results.isEmpty()){
                            binding.lottie.visibility = View.VISIBLE
                        }
                        setPage(it.totalPages,"collection")
                        collectionAdapter.updateAdapter(it.results as ArrayList<com.example.moviesappadvanced.models.search.collections.Result>)
                    }
                }
            }

            override fun onFailure(call: Call<CollectionsResponse>, t: Throwable) {
                //Error 404
            }
        })
    }
    private fun getSearchPeopleResponse(query : String, page: Int){
        searchPeopleApi.getSearchedPeople(API_KEY,query,page).enqueue(object : Callback<PeopleResponse>{
            override fun onResponse(
                call: Call<PeopleResponse>,
                response: Response<PeopleResponse>
            ) {
                if (response.isSuccessful){
                    response.body()?.let {
                        //Anim
                        binding.lottie.visibility = View.GONE
                        //
                        if (it.results.isEmpty()){
                            binding.lottie.visibility = View.VISIBLE
                        }
                        setPage(it.totalPages,"people")
                        peopleAdapter.updateAdapter(it.results as ArrayList<com.example.moviesappadvanced.models.search.people.Result>)
                    }
                }
            }

            override fun onFailure(call: Call<PeopleResponse>, t: Throwable) {
                //Error 404
            }
        })
    }
    private fun setSearchView(category : String){
        val searchView = binding.searchView

        searchView.setOnQueryTextListener( object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                //Loading anim
                binding.lottie.visibility = View.VISIBLE
                //When user submits!!!
                binding.textViewPage.text = 1.toString()
                val page = binding.textViewPage.text.toString().toInt()
                latestQuery = p0
                when(category){
                    "movie"->getSearchMovieResponse(p0.orEmpty(),page)
                    "show"->getSearchShowResponse(p0.orEmpty(),page)
                    "collection"->getSearchCollectionResponse(p0.orEmpty(),page)
                    "people"->getSearchPeopleResponse(p0.orEmpty(),page)
                }
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                // Do not do anything
                return false
            }
        })
    }
    private fun setMovieRv(){
        val rv = binding.rv
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = movieAdapter

    }
    private fun setShowRv(){
       binding.rv.layoutManager = LinearLayoutManager(context)
       binding.rv.adapter = showAdapter
    }
    private fun setCollectionRv(){
        binding.rv.layoutManager = LinearLayoutManager(context)
        binding.rv.adapter = collectionAdapter
    }
    private fun setPeopleRv(){
        binding.rv.layoutManager = GridLayoutManager(context,2)
        binding.rv.adapter = peopleAdapter
    }
    private fun setPopupMenu(){
        val menu = PopupMenu(context,binding.textViewCategory)
        var categoryText = binding.textViewCategory
        menu.inflate(R.menu.search_categories)
        menu.setOnMenuItemClickListener {
            binding.textViewPage.text = "1"
            when(it.itemId){
                R.id.action_movie->{
                    categoryText.text="Movies"
                    setSearchView("movie")
                    getSearchMovieResponse(latestQuery.orEmpty(),1)
                    setMovieRv()
                }
                R.id.action_tv->{
                    categoryText.text="TV shows"
                    setSearchView("show")
                    getSearchShowResponse(latestQuery.orEmpty(),1)
                    setShowRv()
                }
                R.id.action_person->{
                    categoryText.text="Performers"
                    setSearchView("people")
                    getSearchPeopleResponse(latestQuery.orEmpty(),1)
                    setPeopleRv()
                }
                R.id.action_collection->{
                    categoryText.text="Collections"
                    setSearchView("collection")
                    getSearchCollectionResponse(latestQuery.orEmpty(),1)
                    setCollectionRv()
                }
                else->{}
            }
            true
        }
        menu.show()
    }

    private fun setPage(max : Int,category : String){
        val page = binding.textViewPage
        var current: Int
        val plus = binding.imageViewPlus
        val minus = binding.imageViewMinus

        plus.setOnClickListener {
            binding.lottie.visibility = View.VISIBLE
            current = page.text.toString().toInt()
            if (current < max){
                current ++
                page.text = "$current"
            }else{
                current = 1
                page.text = "1"
            }

            when(category){
                "movie"->getSearchMovieResponse(latestQuery.orEmpty(),current)
                "show"->getSearchShowResponse(latestQuery.orEmpty(),current)
                "collection"->getSearchCollectionResponse(latestQuery.orEmpty(),current)
                "people"->getSearchPeopleResponse(latestQuery.orEmpty(),current)
            }
        }
        minus.setOnClickListener {
            binding.lottie.visibility = View.VISIBLE
            current = page.text.toString().toInt()
            if (current > 1){
                current --
                page.text = "$current"
            }else{
                current = max
                page.text = max.toString()
            }

            when(category){
                "movie"->getSearchMovieResponse(latestQuery.orEmpty(),current)
                "show"->getSearchShowResponse(latestQuery.orEmpty(),current)
                "collection"->getSearchCollectionResponse(latestQuery.orEmpty(),current)
                "people"->getSearchPeopleResponse(latestQuery.orEmpty(),current)
            }
        }
    }
}