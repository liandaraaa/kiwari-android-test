package com.android.simplechatapp.datasource

import com.android.simplechatapp.domain.model.Chat
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class ChatDataSource(var listener: OnFirestoreListener<Chat>? = null) : FirestoreDataSource<Chat>() {

    val db= FirebaseFirestore.getInstance()

    override fun saveToFirestoreWithoutId(collectionName:String, document:Chat) {
        db.collection(collectionName)
            .add(document)
            .addOnSuccessListener {
                listener?.onSaveDocumentSuccess()
            }
            .addOnFailureListener {
                listener?.onSaveDocumentFailed(it.message.toString())
            }
    }

    override fun saveToFirestoreWithId(collectionName:String, documentId:String, document:Chat) {
        db.collection(collectionName)
            .document(documentId)
            .set(document)
            .addOnSuccessListener {
                listener?.onSaveDocumentSuccess()
            }
            .addOnFailureListener {
                listener?.onSaveDocumentFailed(it.message.toString())
            }
    }

    override fun getRealtimeCollection(collections:MutableList<Chat>, collectionName:String, orderField:String, direction: Query.Direction){
        db.collection(collectionName)
            .orderBy(orderField, direction)
            .addSnapshotListener { querySnapshot, e ->
                if (e != null){
                    listener?.onGetCollectionFailed(e.message.toString())
                }

                if (querySnapshot != null){
                    if (!querySnapshot.isEmpty){
                        collections.addAll(querySnapshot.toObjects(Chat::class.java))
                        listener?.onGetCollectionSuccess(collections)
                    }else{
                        listener?.onGetCollectionEmpty()
                    }
                }
            }
    }

    override fun getRealtimeWithSepcificField(collections:MutableList<Chat>, collectionName:String, document:Chat, paramField:String, paramValue:String,  orderField:String, direction: Query.Direction) {
        db.collection(collectionName)
            .whereEqualTo(paramField, paramValue)
            .orderBy(orderField, direction)
            .addSnapshotListener {
                    querySnapshot, e ->
                if (e != null){
                    listener?.onGetCollectionFailed(e.message.toString())
                }

                if (querySnapshot !=null){
                    if (!querySnapshot.isEmpty){
                        collections.addAll(querySnapshot.toObjects(Chat::class.java))
                        listener?.onGetCollectionSuccess(collections)
                    }else{
                        listener?.onGetCollectionEmpty()
                    }
                }
            }
    }

    override fun getRealtimeDocument(
        collectionName: String,
        documentName: String
    ) {
        db.collection(collectionName).document(documentName).get()
            .addOnSuccessListener {
                val document = it.toObject(Chat::class.java)
                document?.let { it1 -> listener?.onGetDocumentSuccess(it1) }
            }
            .addOnFailureListener {
                listener?.onGetDocumentFailed(it.message.toString())
            }
    }

}