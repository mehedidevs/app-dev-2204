package com.ju.simplequiz2204.class29

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ju.simplequiz2204.R

class ChatAdapter(var chats: MutableList<Chat>, var currentUserId: String) :
    RecyclerView.Adapter<ChatViewHolder>() {

    private val LEFT = 1
    private val RIGHT = 2


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {

        if (viewType == RIGHT) {
            val view: View =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_chat_ui_right, parent, false)


            return ChatViewHolder(view)

        } else {
            val view: View =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_chat_ui_left, parent, false)


            return ChatViewHolder(view)
        }


    }

    override fun getItemCount(): Int {

        return chats.size

    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chat: Chat = chats[position]

        holder.messageTv.text = chat.message


    }


    override fun getItemViewType(position: Int): Int {
        return if (chats[position].sender == currentUserId) {

            RIGHT
        } else {
            LEFT
        }


    }


}