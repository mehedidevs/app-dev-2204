package com.ju.simplequiz2204.class29

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import coil.load
import com.ju.simplequiz2204.databinding.ItemUserFrBinding

class UserAdapter(var user: UserListener) :
    ListAdapter<User, UserAdapter.UserViewHolder>(compartor) {

    interface UserListener {
        fun moveUser(user: User)

    }


    class UserViewHolder(var binding: ItemUserFrBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {


        return UserViewHolder(
            ItemUserFrBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        getItem(position).let {
            holder.binding.nameTv.text = it.name
            holder.binding.mobileTv.text = it.phone
            holder.binding.profileImage.load(it.profileImgUrl)

            holder.itemView.setOnClickListener { _ ->

                user.moveUser(it)


            }

        }

    }

    companion object {
        var compartor = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {

                return oldItem.email == newItem.email
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }


        }


    }


}