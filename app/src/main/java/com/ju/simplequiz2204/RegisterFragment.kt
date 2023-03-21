package com.ju.simplequiz2204

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.ju.simplequiz2204.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment() {

    lateinit var binding: FragmentRegisterBinding
    lateinit var mAuth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        mAuth = FirebaseAuth.getInstance()

        binding.signInBtn.setOnClickListener {


            findNavController().navigate(R.id.action_registerFragment_to_signInFragment)
        }


        binding.registerBtn.setOnClickListener {
            val email = binding.emailEdt.text.toString()
            val password = binding.passEdt.text.toString()
            registerUser(email, password)
        }






        return binding.root
    }

    private fun registerUser(email: String, password: String) {

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
                } else {
                    Toast.makeText(requireContext(), "${it.exception?.message}", Toast.LENGTH_LONG)
                        .show()
                }

            }


    }

}