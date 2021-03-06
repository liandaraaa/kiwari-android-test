package com.android.simplechatapp.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val id:String = "",
    val name:String = "",
    val avatar:String = ""
):Parcelable