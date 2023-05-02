package com.ju.simplequiz2204.class29

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.ju.simplequiz2204.R
import com.ju.simplequiz2204.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    lateinit var binding: FragmentRegisterBinding
    lateinit var mAuth: FirebaseAuth


    lateinit var myRef: DatabaseReference

    lateinit var userId: String
    lateinit var firebaseUser: FirebaseUser


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        mAuth = FirebaseAuth.getInstance()


        val database = Firebase.database
        myRef = database.reference.child("User")

        binding.signInBtn.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_signInFragment)
        }

        binding.registerBtn.setOnClickListener {
            val email = binding.emailEdt.text.toString()
            val password = binding.passEdt.text.toString()
            val name = binding.nameEdt.text.toString()
            val phone = binding.phoneEdt.text.toString()
            val address = binding.addressEdt.text.toString()
            registerUser(email, password, name, phone, address)
        }






        return binding.root
    }

    private fun registerUser(
        email: String,
        password: String,
        name: String,
        phone: String,
        address: String
    ) {

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    firebaseUser = FirebaseAuth.getInstance().currentUser!!
                    userId = firebaseUser.uid
                    val map = mapOf(
                        "name" to name,
                        "email" to email,
                        "phone" to phone,
                        "password" to password,
                        "address" to address
                    )

                    if (userId != null) {
                        myRef.child(userId).setValue(map).addOnCompleteListener {
                            if (it.isSuccessful) {
                                findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    "${it.exception?.message}",
                                    Toast.LENGTH_LONG
                                )
                                    .show()
                            }
                        }
                    }


                } else {
                    Toast.makeText(requireContext(), "${it.exception?.message}", Toast.LENGTH_LONG)
                        .show()
                }

            }


    }

}