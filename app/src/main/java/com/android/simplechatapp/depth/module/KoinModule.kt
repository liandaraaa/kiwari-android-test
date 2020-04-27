package com.android.simplechatapp.depth.module

import com.android.simplechatapp.data.remote.Api
import com.android.simplechatapp.data.remote.ApiClient
import com.android.simplechatapp.depth.service.OkhttpClientFactory
import com.android.simplechatapp.depth.service.RetrofitService
import com.android.simplechatapp.domain.Interactor
import com.android.simplechatapp.domain.UseCase
import com.android.simplechatapp.repository.DataStore
import com.android.simplechatapp.repository.Repository
import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val BASE_URL = "base_url"

val serviceModule = module {
    single { return@single OkhttpClientFactory.create()}
//    single(named(BASE_URL)){BuildConfig.BASE_URL}
}

val rxModule = module{
    factory { CompositeDisposable() }
}

val utilityModule = module{
    single { Gson() }
}

val featureModule = module {
    single { RetrofitService.createReactiveService(
        ApiClient::class.java,
        get(),
        get(named(BASE_URL))
    ) }
    single { Api(get()) }
    single<Repository>{DataStore(get())}
    single<UseCase>{Interactor(get())}
//    viewModel { UserViewModel(get(), get()) }
}