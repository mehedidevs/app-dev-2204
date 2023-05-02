package com.ju.simplequiz2204.class29

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.ju.simplequiz2204.R
import com.ju.simplequiz2204.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {


    lateinit var binding: FragmentSignInBinding

    lateinit var mAuth: FirebaseAuth
    private var firebaseUser: FirebaseUser? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentSignInBinding.inflate(layoutInflater, container, false)

        mAuth = FirebaseAuth.getInstance()

        firebaseUser = FirebaseAuth.getInstance().currentUser

        if (firebaseUser != null) {
            findNavController().navigate(R.id.action_signInFragment_to_homeFragment)
        }

        binding.registerBtn.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_registerFragment)
        }

        binding.signInBtn.setOnClickListener {
            val email = binding.emailEdt.text.toString().trim()
            val password = binding.passEdt.text.toString().trim()
            signInUser(email, password)
        }
        return binding.root
    }

    private fun signInUser(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    findNavController().navigate(R.id.action_signInFragment_to_homeFragment)
                } else {
                    Toast.makeText(requireContext(), "${it.exception?.message}", Toast.LENGTH_LONG)
                        .show()
                }

            }

    }


}