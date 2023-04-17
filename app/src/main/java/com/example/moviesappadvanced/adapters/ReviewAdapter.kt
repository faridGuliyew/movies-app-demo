package com.example.moviesappadvanced.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesappadvanced.R
import com.example.moviesappadvanced.databinding.ItemReviewBinding

class ReviewAdapter : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {
    inner class ReviewViewHolder (val itemReviewBinding: ItemReviewBinding) : RecyclerView.ViewHolder(itemReviewBinding.root)
    private val reviewList = arrayListOf<com.example.moviesappadvanced.models.reviews.movie.Result>()
    lateinit var controlLottie : (Boolean)->Unit
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ReviewViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return reviewList.size
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviewList[position]
        val binding = holder.itemReviewBinding
        binding.textViewAuthor.text = review.author
        binding.textViewScore.text = "Score: " + review.authorDetails.rating
        binding.textViewContent.text = review.content
        val imageButton = binding.imageButton
        imageButton.setOnClickListener {
            if (it.tag == "expand"){
                imageButton.setImageResource(R.drawable.ic_shrink)
                binding.textViewContent.visibility = View.VISIBLE
                it.tag = "shrink"
            }else{
                imageButton.setImageResource(R.drawable.ic_expand)
                binding.textViewContent.visibility = View.GONE
                it.tag = "expand"
            }
        }
    }

    fun updateAdapter(newList : ArrayList<com.example.moviesappadvanced.models.reviews.movie.Result>){
        if (newList.isNullOrEmpty()){
            controlLottie.invoke(true)
        }else{
            controlLottie.invoke(false)
        }
        reviewList.clear()
        reviewList.addAll(newList)
        notifyDataSetChanged()
    }
}
