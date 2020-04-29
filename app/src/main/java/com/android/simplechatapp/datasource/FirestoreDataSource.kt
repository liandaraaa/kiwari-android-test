package com.android.simplechatapp.datasource

import com.google.firebase.firestore.Query

abstract class FirestoreDataSource<K>{
    abstract fun saveToFirestoreWithoutId(collectionName:String, document:K)
    abstract fun saveToFirestoreWithId(collectionName:String, documentId:String, document:K)
    abstract fun getRealtimeCollection(collection:MutableList<K>, collectionName:String, orderField:String, direction: Query.Direction)
    abstract fun getRealtimeWithSepcificField(collection:MutableList<K>, collectionName:String, document:K, paramField:String, paramValue:String,  orderField:String, direction: Query.Direction)
    abstract fun getRealtimeDocument(collectionName:String, documentName:String)
}