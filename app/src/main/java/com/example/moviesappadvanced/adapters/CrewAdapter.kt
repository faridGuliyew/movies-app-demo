package com.example.moviesappadvanced.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesappadvanced.Constants.IMAGE_BASE_URL
import com.example.moviesappadvanced.databinding.ItemCrewBinding
import com.example.moviesappadvanced.databinding.ItemPerformerBinding
import com.example.moviesappadvanced.models.search.credits.Crew
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class CrewAdapter : RecyclerView.Adapter<CrewAdapter.CrewViewHolder>() {
    inner class CrewViewHolder (val itemCrewBinding: ItemCrewBinding) : RecyclerView.ViewHolder(itemCrewBinding.root)

    private val crewList  = arrayListOf<Crew>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrewViewHolder {
        val binding = ItemCrewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CrewViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return crewList.size
    }

    override fun onBindViewHolder(holder: CrewViewHolder, position: Int) {
        val crew = crewList[position]
        val binding = holder.itemCrewBinding
        //Picasso
        Picasso.get().load(IMAGE_BASE_URL + crew.profilePath).resize(300,370).into(binding.imageViewCrew,object : Callback{
            override fun onSuccess() {
                //Cool!
            }

            override fun onError(e: Exception?) {
                val pos = holder.adapterPosition
                if (pos >= 0){
                    crewList.removeAt(pos)
                    notifyItemRemoved(pos)
                    notifyItemRangeChanged(pos,crewList.size)
                }
            }
        })
        if (position == crewList.size-1){
            crewList.addAll(crewList)
        }
    }

    fun updateAdapter (newList : ArrayList<Crew>){
        crewList.clear()
        crewList.addAll(newList)
        notifyDataSetChanged()
    }
}