package com.example.moviesappadvanced.mainFragments

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.moviesappadvanced.LoginActivity
import com.example.moviesappadvanced.R
import com.example.moviesappadvanced.databinding.AlertDialogBinding
import com.example.moviesappadvanced.databinding.FragmentAccountBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class AccountFragment : Fragment() {
    private var _binding : FragmentAccountBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var auth : FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Your code here...
        auth = FirebaseAuth.getInstance()

        binding.buttonSignout.setOnClickListener {
            signOut()
        }
        binding.buttonAbout.setOnClickListener {
            about()
        }
        binding.buttonContribute.setOnClickListener {
            contribute()
        }
        binding.buttonRate.setOnClickListener {
            rate()
        }
        binding.textViewMail.text = auth.currentUser!!.email
    }
    private fun clearData(){
        val sharedPreferences = requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
    }

    private fun about(){
        Toast.makeText(requireContext(),"Developed by Farid G.", Toast.LENGTH_SHORT).show()
    }
    private fun contribute(){
        Snackbar.make(binding.textViewMail,"We appreciate your support, but contribution is not yet implemented",Snackbar.LENGTH_SHORT).show()
    }
    private fun rate(){
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setTitle("Rate!")
        alertDialog.setView(AlertDialogBinding.inflate(layoutInflater).root)
        alertDialog.setPositiveButton("Submit") { _, _ ->
            Snackbar.make(binding.textViewMail,"Thank you for your feedback!",Snackbar.LENGTH_SHORT).show()
        }
        alertDialog.show()
    }
    private fun signOut(){
        auth.signOut()
        clearData()
        startActivity(Intent(requireContext(),LoginActivity::class.java))
        requireActivity().finish()
        requireActivity().overridePendingTransition(com.airbnb.lottie.R.anim.abc_grow_fade_in_from_bottom,
            com.airbnb.lottie.R.anim.abc_shrink_fade_out_from_bottom)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}