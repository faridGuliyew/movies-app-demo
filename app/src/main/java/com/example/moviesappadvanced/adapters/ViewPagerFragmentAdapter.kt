package com.example.moviesappadvanced.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.moviesappadvanced.introFragments.LoginFragment
import com.example.moviesappadvanced.introFragments.RegisterFragment

class ViewPagerFragmentAdapter (supportFragmentManager: FragmentManager, lifecycle : Lifecycle) : FragmentStateAdapter(supportFragmentManager,lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position){
            0->RegisterFragment()
            1->LoginFragment()
            else->Fragment()
        }
    }
}