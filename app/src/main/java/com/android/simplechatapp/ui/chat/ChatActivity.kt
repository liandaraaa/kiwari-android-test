package com.android.simplechatapp.ui.chat

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.simplechatapp.R
import com.android.simplechatapp.datasource.ChatDataSource
import com.android.simplechatapp.datasource.OnFirestoreListener
import com.android.simplechatapp.domain.model.Chat
import com.android.simplechatapp.domain.model.User
import com.android.simplechatapp.preference.SimpleChatPreference
import com.android.simplechatapp.ui.adapter.ChatAdapter
import com.android.simplechatapp.utils.Constans
import com.android.simplechatapp.utils.gone
import com.android.simplechatapp.utils.showToast
import com.android.simplechatapp.utils.visible
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.layout_chat_toolbar.*
import org.jetbrains.anko.startActivity

class ChatActivity : AppCompatActivity(), OnFirestoreListener<Chat> {

    companion object {
        fun start(context: Context, chatRoomId: String, sender: User) {
            context.startActivity<ChatActivity>(
                Constans.KEY_CHAT_ROOM_ID to chatRoomId,
                Constans.KEY_USER to sender
            )
        }
    }

    private var chatRoomId = ""
    private val collectionName = "chats"
    private val dateField = "date"

    private var chats = mutableListOf<Chat>()

    private var sender: User? = null

    private var currentUserId = ""

    private val chatAdapter: ChatAdapter by lazy {
        ChatAdapter(
            context = this,
            datas = mutableListOf()
        )
    }

    private val chatDataSource: ChatDataSource by lazy {
        ChatDataSource(this)
    }

    private val preference: SimpleChatPreference by lazy {
        SimpleChatPreference(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        chatRoomId = intent?.getStringExtra(Constans.KEY_CHAT_ROOM_ID).orEmpty()
        sender = intent?.getParcelableExtra(Constans.KEY_USER)

        setSupportActionBar(toolbar)

        currentUserId = preference.getString(Constans.KEY_USER_ID)

        showOpponentProfileSummary()
        getChats()
        showChats()

        btnSend.setOnClickListener {
            sendChat()
        }
    }

    private fun showOpponentProfileSummary() {
        sender?.let { user ->
            Glide.with(this).load(user.avatar).into(imgAvatar)
            tvName.text = user.name
        }
    }

    private fun getChats() {
        chatDataSource.getRealtimeCollection(
            collections = chats,
            collectionName = collectionName,
            orderField = dateField,
            direction = Query.Direction.ASCENDING
        )
    }

    private fun showChats() {
        chatAdapter.currentUserId = currentUserId
        rvChat.apply {
            layoutManager = LinearLayoutManager(this@ChatActivity)
            adapter = chatAdapter
        }
    }

    private fun sendChat() {
        pbChat.visible()
        val chat = Chat(
            chatRoomId = chatRoomId,
            senderId = currentUserId,
            content = edtChat.text.toString()
        )

        chatDataSource.saveToFirestoreWithoutId(
            collectionName = collectionName,
            document = chat
        )
    }

    override fun onSaveDocumentSuccess() {
        getChats()
    }

    override fun onSaveDocumentFailed(message: String) {
        pbChat.gone()
        showToast(message)
    }

    override fun onGetCollectionFailed(message: String) {
        pbChat.gone()
        showToast(message)
    }

    override fun onGetCollectionSuccess(collection: List<Chat>) {
        pbChat.gone()
        resetChatContainer()
        chatAdapter.addOrUpdate(collection)
        scrollToBottomPosition()
    }

    override fun onGetCollectionEmpty() {
        pbChat.gone()
        showToast(getString(R.string.message_empty_collection))
    }

    override fun onGetDocumentFailed(message: String) {
        pbChat.gone()
        showToast(message)
    }

    override fun onGetDocumentSuccess(document: Chat) {
    }

    private fun resetChatContainer() {
        edtChat.setText("")
    }

    private fun scrollToBottomPosition() {
        rvChat.scrollToPosition(chatAdapter.datas.size - 1)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
