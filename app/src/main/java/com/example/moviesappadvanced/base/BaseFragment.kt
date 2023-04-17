package com.example.moviesappadvanced.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.moviesappadvanced.R

abstract class BaseFragment <T : ViewDataBinding> : Fragment() {
    protected var _binding : T? = null
    protected val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater,layoutID(),container,false)
        return binding.root
    }

    abstract fun layoutID() : Int

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}