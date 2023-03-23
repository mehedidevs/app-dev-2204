package com.ju.simplequiz2204.class29

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth

import com.ju.simplequiz2204.R
import com.ju.simplequiz2204.databinding.FragmentHomeBinding
import com.ju.simplequiz2204.databinding.FragmentRegisterBinding


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        mAuth = FirebaseAuth.getInstance()

        binding.logoutBtn.setOnClickListener {
            mAuth.signOut();
            findNavController().navigate(R.id.action_homeFragment_to_signInFragment)
        }

        return  binding.root
    }


}