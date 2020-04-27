package com.android.simplechatapp.application

import android.app.Application
import com.android.simplechatapp.depth.koin.KoinContext
import com.android.simplechatapp.depth.module.featureModule
import com.android.simplechatapp.depth.module.rxModule
import com.android.simplechatapp.depth.module.serviceModule
import com.android.simplechatapp.depth.module.utilityModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SimpleChatApplication :Application(){
    override fun onCreate() {
        super.onCreate()

        KoinContext.initialize(applicationContext)

        startKoin {
            androidContext(this@SimpleChatApplication)
            modules(
                listOf(
                    serviceModule,
                    rxModule,
                    featureModule,
                    utilityModule
                )
            )
        }

    }
}