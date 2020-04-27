package com.android.simplechatapp.domain

import com.android.simplechatapp.domain.model.User
import com.android.simplechatapp.repository.Repository
import io.reactivex.Single

class Interactor (val repository: Repository):UseCase{
    override fun getUsers(query: String): Single<List<User>> {
        return repository.getUsers(query)
            .map { it.map {usertItem->
                User(
                    name = usertItem.login.orEmpty(),
                    avatar = usertItem.avatarUrl.orEmpty()
                )
            } }
    }

}