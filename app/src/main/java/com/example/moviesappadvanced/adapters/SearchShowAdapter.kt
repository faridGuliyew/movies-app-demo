package com.example.moviesappadvanced.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesappadvanced.Constants
import com.example.moviesappadvanced.R
import com.example.moviesappadvanced.databinding.ItemRecommendedBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class SearchShowAdapter : RecyclerView.Adapter<SearchShowAdapter.SearchShowViewHolder>()  {

    inner class SearchShowViewHolder (val itemRecommendedBinding: ItemRecommendedBinding) : RecyclerView.ViewHolder(itemRecommendedBinding.root)

    private val filmList = arrayListOf<com.example.moviesappadvanced.models.search.shows.Result>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchShowViewHolder {
        val binding = ItemRecommendedBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SearchShowViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return filmList.size
    }

    override fun onBindViewHolder(holder: SearchShowViewHolder, position: Int) {
        val film = filmList[position]
        val binding = holder.itemRecommendedBinding
        binding.main.startAnimation(AnimationUtils.loadAnimation(binding.main.context, R.anim.rv_show_anim))
        //Picasso
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
        binding.textView4.text = film.name
    }
    fun updateAdapter (newList : ArrayList<com.example.moviesappadvanced.models.search.shows.Result>){
        filmList.clear()
        filmList.addAll(newList)
        notifyDataSetChanged()
    }

}