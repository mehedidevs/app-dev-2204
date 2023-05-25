package com.ju.simplequiz2204.class29

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ju.simplequiz2204.R


class ChatViewHolder(var iteView: View) : RecyclerView.ViewHolder(iteView) {

     var messageTv: TextView = iteView.findViewById(R.id.messageTv)


}