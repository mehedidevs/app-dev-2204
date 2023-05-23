package com.ju.simplequiz2204.class29

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ju.simplequiz2204.ProfileFragment

import com.ju.simplequiz2204.R
import com.ju.simplequiz2204.databinding.FragmentHomeBinding


class HomeFragment : Fragment(), UserAdapter.UserListener {

    lateinit var binding: FragmentHomeBinding
    lateinit var mAuth: FirebaseAuth

    var firebaseUser: FirebaseUser? = null

    var userList = mutableListOf<User>()

    lateinit var adapter: UserAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        mAuth = FirebaseAuth.getInstance()
        adapter = UserAdapter(this@HomeFragment)

        firebaseUser = FirebaseAuth.getInstance().currentUser

        binding.logoutBtn.setOnClickListener {
            mAuth.signOut();
            findNavController().navigate(R.id.action_homeFragment_to_signInFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        FirebaseDatabase.getInstance().reference.child("User")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    userList.clear()
                    snapshot.children.forEach {


                        val user: User = it.getValue(User::class.java)!!

                        Log.i("TAG", "onDataChange: ${user.email}")


                        if (firebaseUser != null) {
                            if (user.userId != firebaseUser!!.uid) {
                                userList.add(user)
                            }


                        }


                    }

                    adapter.submitList(userList)

                    binding.userRcv.adapter = adapter


                }

                override fun onCancelled(error: DatabaseError) {

                }
            })


    }

    override fun moveUser(user: User) {
        //var userEmail = user.email

        var bundle = Bundle()

        bundle.putParcelable(ProfileFragment.USER, user)




        findNavController().navigate(R.id.action_homeFragment_to_profileFragment, bundle)


    }


}