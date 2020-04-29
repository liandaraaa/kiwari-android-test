package com.android.simplechatapp.datasource

interface OnFirebaseAuthListener {
    fun onLoginSuccess()
    fun onLoginFailed(message:String)
}