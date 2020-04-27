package com.android.simplechatapp.repository

import com.android.simplechatapp.data.model.UserItem
import io.reactivex.Single

interface Repository {
    fun getUsers(query:String):Single<List<UserItem>>
}