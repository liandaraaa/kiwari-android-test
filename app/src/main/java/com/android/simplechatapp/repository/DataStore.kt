package com.android.simplechatapp.repository

import com.android.simplechatapp.data.model.UserItem
import com.android.simplechatapp.data.remote.Api
import com.android.simplechatapp.depth.rx.singleApiError
import io.reactivex.Single

class DataStore (private val api:Api):Repository{
    override fun getUsers(query: String): Single<List<UserItem>> {
        return api.getUsers(query)
            .lift(singleApiError())
            .map { it.userItems }
    }
}