package com.android.simplechatapp.datasource

interface OnFirebaseAuthListener {
    fun onLoginSuccess(userId:String)
    fun onLoginFailed(message:String)
}