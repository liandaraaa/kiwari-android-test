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

class ChatAdapter (val context: Context,
                   val datas:MutableList<Chat>,
                   var currentUserId:String = "")
    :RecyclerView.Adapter<ChatAdapter.ChatViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        return ChatViewHolder(LayoutInflater.from(context).inflate(viewType, parent, false))
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemViewType(position: Int): Int {
        return if (datas[position].senderId == currentUserId ){
            R.layout.item_owner_chat
        }else{
            R.layout.item_opponent_chat
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

    fun add(item: Chat) {
        datas.add(item)
        notifyItemInserted(datas.size - 1)
    }

    fun addOrUpdate(items: List<Chat>) {
        val size = items.size
        for (i in 0 until size) {
            val item = items[i]
            val x = datas.indexOf(item)
            if (x >= 0) {
                datas.set(x, item)
            } else {
                add(item)
            }
        }
        notifyDataSetChanged()
    }
}