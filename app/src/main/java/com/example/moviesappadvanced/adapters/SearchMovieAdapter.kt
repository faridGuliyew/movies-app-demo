package com.example.moviesappadvanced.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesappadvanced.Constants
import com.example.moviesappadvanced.R
import com.example.moviesappadvanced.databinding.ItemRecommendedBinding
import com.example.moviesappadvanced.mainFragments.HomeFragmentDirections
import com.example.moviesappadvanced.mainFragments.SearchFragmentDirections
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class SearchMovieAdapter : RecyclerView.Adapter<SearchMovieAdapter.SearchViewHolder>() {

    inner class SearchViewHolder (val itemRecommendedBinding: ItemRecommendedBinding) : RecyclerView.ViewHolder(itemRecommendedBinding.root)

    private val filmList = arrayListOf<com.example.moviesappadvanced.models.search.movies.Result>()
    private val notAvailable = arrayListOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ItemRecommendedBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SearchViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return filmList.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val film = filmList[position]
        val binding = holder.itemRecommendedBinding

        if (film.backdropPath.isNullOrEmpty()){
            if (holder.adapterPosition !in notAvailable){
                notAvailable.add(holder.adapterPosition)
            }
        }
        //Picasso
        binding.main.startAnimation(AnimationUtils.loadAnimation(binding.main.context, R.anim.rv_movie_anim))

        Picasso.get().load(Constants.IMAGE_BASE_URL +film.posterPath).into(binding.imageView,object :
            Callback {
            override fun onSuccess() {
                //Success
            }

            override fun onError(e: Exception?) {
                if (holder.adapterPosition >= 0){
                    filmList.removeAt(holder.adapterPosition)
                    notifyItemRemoved(holder.adapterPosition)
                    notifyItemRangeChanged(holder.adapterPosition, filmList.size);
                    Log.e("picasso","failed to load, no image exists!")
                }
            }
        })

        binding.imageView.setOnClickListener {
            if (holder.adapterPosition !in notAvailable){
                Navigation.findNavController(it).navigate(SearchFragmentDirections.actionSearchFragmentToDetailFragment(null,film))
            }else{
                Snackbar.make(it,"Content not available", Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.textView4.text = film.title


    }
    fun updateAdapter (newList : ArrayList<com.example.moviesappadvanced.models.search.movies.Result>){
        filmList.clear()
        filmList.addAll(newList)
        notifyDataSetChanged()
    }
}