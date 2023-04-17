package com.example.moviesappadvanced

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.moviesappadvanced.adapters.ViewPagerFragmentAdapter
import com.example.moviesappadvanced.databinding.ActivityLoginBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.math.abs

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private lateinit var viewPager : ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewPager = binding.viewPager
        setFragmentViewPager()
        setPageTransformer(viewPager)
    }


    private fun setFragmentViewPager(){
        val tabLayout = binding.tabLayout
        val adapter = ViewPagerFragmentAdapter(supportFragmentManager,lifecycle)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout,viewPager){tab,position->
            when (position){
                0->tab.text = "Register"
                1->tab.text = "Log in"
            }
        }.attach()
    }

    private fun setPageTransformer(viewPager2 : ViewPager2){
        val pageTransformer = CompositePageTransformer()

        pageTransformer.addTransformer { page, position ->
            Log.e("transform",position.toString())
            val r = 1 - abs(position) * 0.2
            page.scaleY = r.toFloat()
        }
        viewPager2.setPageTransformer(pageTransformer)
    }
}