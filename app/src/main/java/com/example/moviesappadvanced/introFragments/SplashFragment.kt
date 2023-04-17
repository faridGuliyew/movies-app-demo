package com.example.moviesappadvanced.introFragments

import android.animation.Animator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.moviesappadvanced.LoginActivity
import com.example.moviesappadvanced.R
import com.example.moviesappadvanced.base.BaseFragment
import com.example.moviesappadvanced.databinding.FragmentSplashBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class SplashFragment : Fragment() {
   private var _binding : FragmentSplashBinding?  = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideBottomNav()
        binding.lottie.addAnimatorListener(object : Animator.AnimatorListener{
            override fun onAnimationStart(p0: Animator) {
                //
            }

            override fun onAnimationEnd(p0: Animator) {
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
                enterTransition = com.airbnb.lottie.R.anim.abc_grow_fade_in_from_bottom
            }

            override fun onAnimationCancel(p0: Animator) {
                //
            }

            override fun onAnimationRepeat(p0: Animator) {
                //
            }
        })
    }

    private fun hideBottomNav(){
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNav).visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNav).visibility = View.VISIBLE
    }
}