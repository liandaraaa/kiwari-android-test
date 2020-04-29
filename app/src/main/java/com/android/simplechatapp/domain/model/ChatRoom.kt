package com.android.simplechatapp.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ChatRoom(
    val id: String = "",
    val ownerOneId: String = "",
    val ownerTwoId: String = ""
) : Parcelable