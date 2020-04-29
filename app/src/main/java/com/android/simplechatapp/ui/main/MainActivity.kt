package com.android.simplechatapp.ui.main

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.simplechatapp.R
import com.android.simplechatapp.datasource.ChatRoomDataSource
import com.android.simplechatapp.datasource.OnFirestoreListener
import com.android.simplechatapp.domain.model.ChatRoom
import com.android.simplechatapp.ui.adapter.ChatRoomAdapter
import com.android.simplechatapp.utils.gone
import com.android.simplechatapp.utils.showToast
import com.android.simplechatapp.utils.visible
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity: AppCompatActivity(), OnFirestoreListener<ChatRoom> {
    companion object {
        fun start(context: Context) {
            context.startActivity<MainActivity>()
        }
    }

    private val chatRoomAdapter by lazy{
        ChatRoomAdapter(this, mutableListOf())
    }

    private val chatRoomDataSource by lazy{
        ChatRoomDataSource(this)
    }

    private val chatRooms = mutableListOf<ChatRoom>()

    private val collectionName = "room"
    private val documentId = "id"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        toolbar.title = getString(R.string.app_name)

        getChatRoom()
        showChatRooms()
    }

    private fun getChatRoom(){
        pbChatRoom.visible()
        chatRoomDataSource.getRealtimeCollection(
            collections = chatRooms,
            collectionName = collectionName,
            orderField = documentId,
            direction = Query.Direction.ASCENDING
        )
    }

    private fun showChatRooms(){
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
        chatRoomAdapter.currentUserId = currentUserId.orEmpty()
        rvChatRoom.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = chatRoomAdapter
        }
    }

    override fun onSaveDocumentSuccess() {
    }

    override fun onSaveDocumentFailed(message: String) {
    }

    override fun onGetCollectionFailed(message: String) {
        pbChatRoom.gone()
        showToast(message)
    }

    override fun onGetCollectionSuccess(collection: List<ChatRoom>) {
        pbChatRoom.gone()
        chatRoomAdapter.datas.addAll(collection)
        chatRoomAdapter.notifyDataSetChanged()
    }

    override fun onGetCollectionEmpty() {
        pbChatRoom.gone()
        showToast(getString(R.string.message_empty_chat_room))
    }

    override fun onGetDocumentFailed(message: String) {

    }

    override fun onGetDocumentSuccess(document: ChatRoom) {
    }


}
