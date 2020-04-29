package com.android.simplechatapp.datasource

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class FirebaseAuthDataSource(val listener: OnFirebaseAuthListener?= null){

    val auth = FirebaseAuth.getInstance()

    fun signIn(email:String, password:String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                listener?.onLoginSuccess()
            }
            .addOnFailureListener {
                listener?.onLoginFailed(it.message.toString())
            }
    }

    fun logOut(){
        auth.signOut()
    }


}