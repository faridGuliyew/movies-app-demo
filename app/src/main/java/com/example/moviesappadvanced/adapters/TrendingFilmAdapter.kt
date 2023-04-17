package com.example.moviesappadvanced.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesappadvanced.Constants.IMAGE_BASE_URL
import com.example.moviesappadvanced.additionalFragments.BottomSheetFragment
import com.example.moviesappadvanced.databinding.ItemTrendingFilmBinding
import com.squareup.picasso.Picasso

class TrendingFilmAdapter (val supportFragmentManager: FragmentManager) : RecyclerView.Adapter<TrendingFilmAdapter.TrendingViewHolder>() {
    inner class TrendingViewHolder (val itemTrendingFilmBinding: ItemTrendingFilmBinding) : RecyclerView.ViewHolder(itemTrendingFilmBinding.root)

    private val filmList = arrayListOf<com.example.moviesappadvanced.models.trending.Result>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingViewHolder {
        val binding = ItemTrendingFilmBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TrendingViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return filmList.size
    }

    override fun onBindViewHolder(holder: TrendingViewHolder, position: Int) {
        val film = filmList[position]
        val binding = holder.itemTrendingFilmBinding
        //Picasso
        Picasso.get().load(IMAGE_BASE_URL+film.backdropPath).resize(500*2,281*2).into(binding.imageViewTrending)
        binding.textView5.text = film.voteAverage.toString() + "/10"

        binding.imageViewTrending.setOnClickListener {
            BottomSheetFragment().show(supportFragmentManager,"hi")
        }
    }
    fun updateAdapter (newList : ArrayList<com.example.moviesappadvanced.models.trending.Result>){
        filmList.clear()
        filmList.addAll(newList)
        notifyDataSetChanged()
    }
}