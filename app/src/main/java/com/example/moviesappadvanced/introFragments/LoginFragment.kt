package com.example.moviesappadvanced.introFragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moviesappadvanced.MainActivity
import com.example.moviesappadvanced.R
import com.example.moviesappadvanced.base.BaseFragment
import com.example.moviesappadvanced.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    override fun layoutID(): Int {
        return R.layout.fragment_login
    }

    private lateinit var auth : FirebaseAuth
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        binding.button3.setOnClickListener {
            if (isValid()){
                login()
            }
        }
    }

    private fun login(){
        val email = binding.editTextMail.text.toString()
        val pwd = binding.editTextPwd.text.toString()
        auth.signInWithEmailAndPassword(email,pwd).addOnFailureListener {
            Snackbar.make(binding.button3,it.localizedMessage!!,Snackbar.LENGTH_SHORT).show()
        }.addOnSuccessListener {
            //Success!
            setSp()
            startActivity(Intent(requireActivity(), MainActivity::class.java))
            requireActivity().overridePendingTransition(com.airbnb.lottie.R.anim.abc_grow_fade_in_from_bottom,
                com.airbnb.lottie.R.anim.abc_shrink_fade_out_from_bottom)
            requireActivity().finish()
        }
    }

    private fun isValid() : Boolean{
        val email = binding.editTextMail
        val pwd = binding.editTextPwd

        if (email.text.isNullOrEmpty()){
            email.error = "Email cannot be blank"
            return false
        }else if (pwd.text.isNullOrEmpty()){
            pwd.error = "Password cannot be blank"
            return false
        } else{
            return true
        }
    }

    private fun setSp(){
        val sharedPreferences = requireContext().getSharedPreferences("user", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("user","logged in")
        editor.apply()
    }

}