package com.ju.simplequiz2204.class26

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ju.simplequiz2204.class26.room_config.User
import com.ju.simplequiz2204.databinding.ItemUserBinding

class UserAdapter (var  listener: UserClickListener) : ListAdapter<User, UserAdapter.UserViewHolder>(comparator) {


    class UserViewHolder(var binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {

        var comparator = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.userId == newItem.userId
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {

        return UserViewHolder(
            ItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        getItem(position).let {


            holder.binding.nameTv.text = it.name
            holder.binding.ageTV.text = "${it.age}"
            holder.binding.mobileTv.text = it.mobile
            holder.binding.editBtn.setOnClickListener { v ->
                listener.userUpdate(it)
            }

            holder.binding.deleteBTn.setOnClickListener { v ->
                listener.userDelete(it)
            }


        }


    }


}