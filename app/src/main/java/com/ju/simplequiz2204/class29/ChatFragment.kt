package com.ju.simplequiz2204.class29

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import coil.load
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.ju.simplequiz2204.ProfileFragment

import com.ju.simplequiz2204.databinding.FragmentChatBinding

class ChatFragment : Fragment() {


    lateinit var binding: FragmentChatBinding

    lateinit var user: User
    var firebaseUser: FirebaseUser? = null
    lateinit var firebaseDatabaseReference: DatabaseReference
    var currentUserID = ""


    var chats: MutableList<Chat> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChatBinding.inflate(inflater, container, false)

        user = requireArguments().getParcelable<User>(ProfileFragment.USER)!!

        FirebaseAuth.getInstance().currentUser?.let {
            firebaseUser = it
            currentUserID = it.uid

        }

        val manager = LinearLayoutManager(requireContext())

        manager.stackFromEnd = true

        binding.chatRcv.layoutManager = manager


        val database = Firebase.database
        firebaseDatabaseReference = database.reference.child("chat")


        firebaseDatabaseReference.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    chats.clear()

                    snapshot.children.forEach {

                        val chat: Chat = it.getValue<Chat>(Chat::class.java)!!

                        if (chat.sender == currentUserID && chat.receiver == user.userId
                            || chat.sender == user.userId && chat.receiver == currentUserID
                        ) {

                            chats.add(chat)

                        }


                    }

                    val adapter = ChatAdapter(chats, currentUserID)

                    binding.chatRcv.adapter = adapter


                }

                override fun onCancelled(error: DatabaseError) {

                }

            })



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