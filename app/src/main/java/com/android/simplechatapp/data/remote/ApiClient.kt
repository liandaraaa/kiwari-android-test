package com.android.simplechatapp.data.remote

import com.android.simplechatapp.data.model.UserResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiClient {
    @GET("search/users")
    fun getUsers(@Query("q") query:String):Single<Response<UserResponse>>
}