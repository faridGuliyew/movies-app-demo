package com.example.moviesappadvanced.adapters

import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesappadvanced.Constants
import com.example.moviesappadvanced.R
import com.example.moviesappadvanced.databinding.ItemPerformerBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class SearchPeopleAdapter : RecyclerView.Adapter<SearchPeopleAdapter.SearchViewHolder>() {
    inner class SearchViewHolder (val itemPerformerBinding: ItemPerformerBinding) : RecyclerView.ViewHolder(itemPerformerBinding.root)

    private val peopleList = arrayListOf<com.example.moviesappadvanced.models.search.people.Result>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ItemPerformerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SearchViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return peopleList.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val person = peopleList[position]
        val binding = holder.itemPerformerBinding

        binding.main.startAnimation(AnimationUtils.loadAnimation(binding.main.context, R.anim.rv_performer_anim))
        //Picasso
        Picasso.get().load(Constants.IMAGE_BASE_URL + person.profilePath)
            .into(binding.imageViewPerformer,object : Callback{

                override fun onSuccess() {
                    //Success
                }

                override fun onError(e: Exception?) {
                    if (holder.adapterPosition >= 0){
                        peopleList.removeAt(holder.adapterPosition)
                        notifyItemRemoved(holder.adapterPosition)
                        notifyItemRangeChanged(holder.adapterPosition, peopleList.size);
                        Log.e("picasso","failed to load, no image exists!")
                    }
                }
            })
    }
    fun updateAdapter (newList : ArrayList<com.example.moviesappadvanced.models.search.people.Result>){
        peopleList.clear()
        peopleList.addAll(newList)
        notifyDataSetChanged()
    }
}