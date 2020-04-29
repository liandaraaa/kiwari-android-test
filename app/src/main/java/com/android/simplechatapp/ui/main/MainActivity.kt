package com.android.simplechatapp.ui.main

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.simplechatapp.R
import com.android.simplechatapp.datasource.ChatRoomDataSource
import com.android.simplechatapp.datasource.FirebaseAuthDataSource
import com.android.simplechatapp.datasource.OnFirebaseAuthListener
import com.android.simplechatapp.datasource.OnFirestoreListener
import com.android.simplechatapp.domain.model.ChatRoom
import com.android.simplechatapp.domain.model.User
import com.android.simplechatapp.preference.SimpleChatPreference
import com.android.simplechatapp.ui.adapter.ChatRoomAdapter
import com.android.simplechatapp.ui.chat.ChatActivity
import com.android.simplechatapp.ui.login.LoginActivity
import com.android.simplechatapp.utils.Constans
import com.android.simplechatapp.utils.gone
import com.android.simplechatapp.utils.showToast
import com.android.simplechatapp.utils.visible
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity(), OnFirestoreListener<ChatRoom>,
    ChatRoomAdapter.OnChatRoomClickListener,
    OnFirebaseAuthListener {

    companion object {
        fun start(context: Context) {
            context.startActivity<MainActivity>()
        }
    }

    private val chatRoomAdapter by lazy {
        ChatRoomAdapter(
            context = this,
            datas = mutableListOf(),
            listener = this
        )
    }

    private val preference: SimpleChatPreference by lazy {
        SimpleChatPreference(this)
    }

    private val chatRoomDataSource by lazy {
        ChatRoomDataSource(this)
    }

    private val authDataSource by lazy {
        FirebaseAuthDataSource(this)
    }

    private val chatRooms = mutableListOf<ChatRoom>()

    private val collectionName = "room"
    private val documentId = "id"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        toolbar.title = getString(R.string.app_name)
        toolbar.setTitleTextColor(resources.getColor(R.color.colorWhite))

        getChatRoom()
        showChatRooms()
    }

    private fun getChatRoom() {
        pbChatRoom.visible()
        chatRoomDataSource.getRealtimeCollection(
            collections = chatRooms,
            collectionName = collectionName,
            orderField = documentId,
            direction = Query.Direction.ASCENDING
        )
    }

    private fun showChatRooms() {
        val currentUserId = preference.getString(Constans.KEY_USER_ID)
        chatRoomAdapter.currentUserId = currentUserId
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

    override fun onChatRoomClicked(id: String, sender: User) {
        ChatActivity.start(this, id, sender)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_logout -> doLogut()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun doLogut() {
        authDataSource.logOut()
        preference.saveBoolean(Constans.KEY_IS_LOGGED_IN, false)
        preference.saveString(Constans.KEY_USER_ID, "")
        LoginActivity.start(this)
        finishAffinity()
    }

    override fun onLoginSuccess(userId: String) {

    }

    override fun onLoginFailed(message: String) {

    }

}
