package com.android.simplechatapp.depth.service.model

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("message")
    val message:String
)