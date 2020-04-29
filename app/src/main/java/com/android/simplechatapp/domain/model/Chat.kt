package com.android.simplechatapp.domain.model

import android.os.Parcelable
import com.android.simplechatapp.utils.getCurrentFirestoreTimestamp
import com.google.firebase.Timestamp
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Chat(
    val chatRoomId:String = "",
    val senderId: String = "",
    val date:Timestamp = getCurrentFirestoreTimestamp(),
    val content:String = ""
):Parcelable