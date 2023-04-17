package com.example.moviesappadvanced.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesappadvanced.Constants.IMAGE_BASE_URL
import com.example.moviesappadvanced.R
import com.example.moviesappadvanced.databinding.ItemRecommendedBinding
import com.example.moviesappadvanced.mainFragments.HomeFragmentDirections
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

class RecommendedFilmAdapter : RecyclerView.Adapter<RecommendedFilmAdapter.RecommendedViewHolder>() {
    inner class RecommendedViewHolder (val itemRecommendedBinding: ItemRecommendedBinding) : RecyclerView.ViewHolder(itemRecommendedBinding.root)


    private val filmList = arrayListOf<com.example.moviesappadvanced.models.recommended.Result>()

    private val notAvailable = arrayListOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendedViewHolder {
        val binding = ItemRecommendedBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RecommendedViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return filmList.size
    }

    override fun onBindViewHolder(holder: RecommendedViewHolder, position: Int) {
        val film = filmList[position]
        val binding = holder.itemRecommendedBinding
        binding.main.startAnimation(AnimationUtils.loadAnimation(binding.main.context, R.anim.rv_movie_anim))

        if (film.backdropPath.isNullOrEmpty()){
            Log.e("testo",position.toString())
            if (holder.adapterPosition !in notAvailable){
                notAvailable.add(holder.adapterPosition)
            }
        }
        //Picasso
        Picasso.get().load(IMAGE_BASE_URL+film.posterPath).into(binding.imageView)
        binding.textView4.text = film.title

        binding.imageView.setOnClickListener {
            if (holder.adapterPosition !in notAvailable){
                Navigation.findNavController(it).navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(film,null))
            }else{
                Snackbar.make(it,"Content not available",Snackbar.LENGTH_SHORT).show()
            }
        }
    }
    fun updateAdapter (newList : ArrayList<com.example.moviesappadvanced.models.recommended.Result>){
        filmList.clear()
        filmList.addAll(newList)
        notifyDataSetChanged()
    }
}