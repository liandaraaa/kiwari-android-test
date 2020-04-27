package com.android.simplechatapp.domain

import com.android.simplechatapp.domain.model.User
import io.reactivex.Single

interface UseCase {
    fun getUsers(query:String):Single<List<User>>
}