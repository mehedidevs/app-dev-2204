package com.ju.simplequiz2204

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ju.simplequiz2204.class29.User
import com.ju.simplequiz2204.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentProfileBinding.inflate(inflater, container, false)


        var user = requireArguments().getParcelable<User>("email")!!

        binding.mobileTv.text = user.phone
        binding.nameTv.text = user.email







        return binding.root
    }


}