package com.android.simplechatapp.domain.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Chat(
    val chatRoomId:String,
    val senderId: String,
    val date:Timestamp,
    val content:String
):Parcelable