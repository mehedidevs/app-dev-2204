package com.ju.simplequiz2204.class29

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.ju.simplequiz2204.ProfileFragment

import com.ju.simplequiz2204.databinding.FragmentChatBinding

class ChatFragment : Fragment() {


    lateinit var binding: FragmentChatBinding

    lateinit var user: User
    var firebaseUser: FirebaseUser? = null
    lateinit var firebaseDatabaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChatBinding.inflate(inflater, container, false)

        user = requireArguments().getParcelable<User>(ProfileFragment.USER)!!

        firebaseUser = FirebaseAuth.getInstance().currentUser


        val database = Firebase.database
        firebaseDatabaseReference = database.reference.child("chat")


        binding.sendBtn.setOnClickListener {

            val messageId = firebaseDatabaseReference.push().key!!

            firebaseUser?.let {
                val message = Chat(
                    binding.messageEdt.text.toString().trim(),
                    firebaseUser!!.uid,
                    user.userId,
                    messageId
                )

                firebaseDatabaseReference.child(messageId).setValue(message)
                    .addOnSuccessListener {
                        Toast.makeText(requireContext(), "Message Sent", Toast.LENGTH_SHORT).show()
                        binding.messageEdt.setText("")
                    }

            }


        }






        return binding.root
    }

    companion object {

        const val USER = "user"


    }


}