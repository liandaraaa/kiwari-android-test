package com.android.simplechatapp.datasource

interface OnFirestoreListener<K>{
    fun onSaveDocumentSuccess()
    fun onSaveDocumentFailed(message:String)
    fun onGetCollectionFailed(message: String)
    fun onGetCollectionSuccess(collection: List<K>)
    fun onGetCollectionEmpty()
    fun onGetDocumentFailed(message: String)
    fun onGetDocumentSuccess(document: K)
}