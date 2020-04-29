package com.android.simplechatapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.simplechatapp.R
import com.android.simplechatapp.domain.model.Chat
import com.android.simplechatapp.utils.toReadableDate
import kotlinx.android.synthetic.main.item_owner_chat.view.*

class ChatAdapter (val context: Context, val datas:MutableList<Chat>)
    :RecyclerView.Adapter<ChatAdapter.ChatViewHolder>(){

    val type = OWNER_CHAT

    companion object{
        const val OWNER_CHAT = 23
        const val OPPONENT_CHAT = 34
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        return ChatViewHolder(LayoutInflater.from(context).inflate(viewType, parent, false))
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemViewType(position: Int): Int {
        return when(type){
            OWNER_CHAT -> R.layout.item_owner_chat
            OPPONENT_CHAT -> R.layout.item_opponent_chat
            else -> R.layout.item_owner_chat
        }
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    inner class ChatViewHolder(view:View):RecyclerView.ViewHolder(view){
        fun bind(data:Chat){
            with(itemView){
                tvContent.text = data.content
                tvDate.text =data.date.toReadableDate("dd MMMM yyyy (hh:mm)")
            }
        }
    }
}