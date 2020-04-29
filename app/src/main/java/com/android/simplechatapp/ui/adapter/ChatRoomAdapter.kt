package com.android.simplechatapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.simplechatapp.R
import com.android.simplechatapp.datasource.OnFirestoreListener
import com.android.simplechatapp.datasource.UserDataSource
import com.android.simplechatapp.domain.model.Chat
import com.android.simplechatapp.domain.model.ChatRoom
import com.android.simplechatapp.domain.model.User
import com.android.simplechatapp.utils.toReadableDate
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.item_chat_room.view.*
import kotlinx.android.synthetic.main.item_owner_chat.view.*

class ChatRoomAdapter (val context: Context, val datas:MutableList<ChatRoom>, var currentUserId:String = "")
    :RecyclerView.Adapter<ChatRoomAdapter.ChatViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        return ChatViewHolder(LayoutInflater.from(context).inflate(R.layout.item_chat_room, parent, false))
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    inner class ChatViewHolder(view:View):RecyclerView.ViewHolder(view){
        fun bind(data:ChatRoom){
            with(itemView){

                val senderId = if(data.ownerOneId == currentUserId) data.ownerTwoId else  data.ownerOneId

                val userDataSource = UserDataSource()

                userDataSource.getRealtimeDocument(
                    collectionName = "users",
                    documentName = senderId
                )

                userDataSource.listener = object :OnFirestoreListener<User>{
                    override fun onSaveDocumentSuccess() {
                    }

                    override fun onSaveDocumentFailed(message: String) {
                    }

                    override fun onGetCollectionFailed(message: String) {
                    }

                    override fun onGetCollectionSuccess(collection: List<User>) {
                    }

                    override fun onGetCollectionEmpty() {
                    }

                    override fun onGetDocumentFailed(message: String) {
                    }

                    override fun onGetDocumentSuccess(document: User) {
                        Glide.with(context).load(document.avatar).into(imgAvatar)
                        tvName.text = document.name
                    }

                }
            }
        }
    }
}