package com.example.moviesappadvanced.introFragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.moviesappadvanced.MainActivity
import com.example.moviesappadvanced.R
import com.example.moviesappadvanced.base.BaseFragment
import com.example.moviesappadvanced.databinding.FragmentRegisterBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {
    override fun layoutID(): Int {
        return R.layout.fragment_register
    }
    private lateinit var auth : FirebaseAuth
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Write your code here...
        auth = FirebaseAuth.getInstance()

        binding.button3.setOnClickListener {
            if (isValid()){
                register()
            }
        }
    }

    private fun register(){
        val email = binding.editTextMail.text.toString()
        val pwd = binding.editTextPwd.text.toString()

        auth.createUserWithEmailAndPassword(email,pwd).addOnSuccessListener {
            //Success!
            setSp()
            startActivity(Intent(requireActivity(),MainActivity::class.java))
            requireActivity().overridePendingTransition(com.airbnb.lottie.R.anim.abc_grow_fade_in_from_bottom,
                com.airbnb.lottie.R.anim.abc_shrink_fade_out_from_bottom)
            requireActivity().finish()
        }.addOnFailureListener {
            Snackbar.make(binding.button3,it.localizedMessage!!,Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun isValid() : Boolean{
        val email = binding.editTextMail
        val pwd = binding.editTextPwd
        val pwdAgain = binding.editTextPwdAgain

        if (email.text.isNullOrEmpty()){
            email.error = "Email cannot be blank"
            return false
        }else if (pwd.text.isNullOrEmpty()){
            pwd.error = "Password cannot be blank"
            return false
        }else if (pwdAgain.text.isNullOrEmpty() || pwdAgain.text.toString() != pwd.text.toString()){
            pwd.error = "Passwords do not match"
            return false
        }else{
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