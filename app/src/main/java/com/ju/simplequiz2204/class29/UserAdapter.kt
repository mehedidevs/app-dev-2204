package com.ju.simplequiz2204.class29

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.ju.simplequiz2204.databinding.ItemUserFrBinding

class UserAdapter : ListAdapter<User, UserAdapter.UserViewHolder>(compartor) {


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